//
//  XHHOneKeyShareViewController.m
//  Manhattan
//
//  Created by Apple on 2018/9/27.
//  Copyright © 2018年 Apple. All rights reserved.
//

#import "XHHOneKeyShareViewController.h"
#import <WebKit/WebKit.h>
@interface XHHOneKeyShareViewController ()

@property (strong, nonatomic) WKWebView *webView;

@property (strong, nonatomic) UIProgressView *progressView;

@end

@implementation XHHOneKeyShareViewController

- (void)viewDidLoad
{
    [super viewDidLoad];
    CGFloat navHeight = ChZ_IsiPhoneX ? 44 : 0;
    NSString *uid = [APPControl sharedAPPControl].user.fid;
    NSString *urlStr = [NSString stringWithFormat:@"%@&intro=%@",RequestURL(kURL_square_shareUrl),uid];
    // 加载请求
    NSURL *url = [NSURL URLWithString:urlStr];
    NSURLRequest *request = [NSURLRequest requestWithURL:url];
    self.webView = [[WKWebView alloc] initWithFrame:CGRectMake(0, -navHeight, ChZ_WIDTH, ChZ_HEIGHT +2 *navHeight)];
    // 加载请求
    //    [self.webView loadRequest:request];
    [self.view addSubview:self.webView];
    [self.webView addObserver:self forKeyPath:@"estimatedProgress" options:NSKeyValueObservingOptionNew context:nil];
    [self.webView addObserver:self forKeyPath:@"title" options:NSKeyValueObservingOptionNew context:nil];
    // UIProgressView初始化
    self.progressView = [[UIProgressView alloc] initWithProgressViewStyle:UIProgressViewStyleDefault];
    self.progressView.frame = CGRectMake(0, 0, self.webView.frame.size.width, 2);
    self.progressView.trackTintColor = [UIColor clearColor];
    self.progressView.progressTintColor = [UIColor colorWithHexString:@"2395FF"];
    [self.progressView setProgress:0.1 animated:YES];
    [self.webView addSubview:self.progressView];
    self.webView.scrollView.backgroundColor = [UIColor clearColor];
    [self.view sendSubviewToBack:self.webView];
    [self.webView loadRequest:request];
    
    
}
- (IBAction)back:(id)sender
{
    [self pop];
}
#pragma mark - KVO监听
- (void)observeValueForKeyPath:(NSString *)keyPath ofObject:(id)object change:(NSDictionary *)change context:(void *)context
{
    
    if ([object isEqual:self.webView] && [keyPath isEqualToString:@"estimatedProgress"]) { // 进度条
        
        CGFloat newprogress = [[change objectForKey:NSKeyValueChangeNewKey] doubleValue];
        NSLog(@"打印测试进度值：%f", newprogress);
        
        if (newprogress == 1) { // 加载完成
            // 首先加载到头
            [self.progressView setProgress:newprogress animated:YES];
            // 之后0.3秒延迟隐藏
            __weak typeof(self) weakSelf = self;
            dispatch_after(dispatch_time(DISPATCH_TIME_NOW, 0.3 * NSEC_PER_SEC), dispatch_get_main_queue(), ^{
                
                weakSelf.progressView.hidden = YES;
                [weakSelf.progressView setProgress:0 animated:NO];
            });
            
        } else { // 加载中
            self.progressView.hidden = NO;
            [self.progressView setProgress:newprogress animated:YES];
        }
    } else if ([object isEqual:self.webView] && [keyPath isEqualToString:@"title"]) { // 标题
        
    } else { // 其他
        
        [super observeValueForKeyPath:keyPath ofObject:object change:change context:context];
    }
}
-(void)dealloc{
    [self.webView removeObserver:self forKeyPath:@"estimatedProgress"];
    [self.webView removeObserver:self forKeyPath:@"title"];
}
- (IBAction)shareAction:(id)sender
{
    UIPasteboard *pasetBoard = [UIPasteboard generalPasteboard];
    NSString *uid = [APPControl sharedAPPControl].user.fid;
    pasetBoard.string = [NSString stringWithFormat:@"%@&intro=%@",RequestURL(kURL_square_shareUrl),uid];
    ChZ_MBSuccess(@"复制分享地址成功")
}
- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

/*
#pragma mark - Navigation

// In a storyboard-based application, you will often want to do a little preparation before navigation
- (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender {
    // Get the new view controller using [segue destinationViewController].
    // Pass the selected object to the new view controller.
}
*/

@end
