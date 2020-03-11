//
//  XHHTradNoriceDeatilController.m
//  FuturePurse
//
//  Created by Apple on 2018/7/30.
//  Copyright © 2018年 jbtm. All rights reserved.
//

#import "XHHTradNoriceDeatilController.h"
#import <WebKit/WebKit.h>


#import "XHHNotesDetailModel.h"
@interface XHHTradNoriceDeatilController ()

@property (strong, nonatomic) WKWebView *webView;

@property (strong, nonatomic) UIProgressView *progressView;

@end

@implementation XHHTradNoriceDeatilController

-(void)viewWillAppear:(BOOL)animated{
    [self.navigationController setNavigationBarHidden:NO animated:YES];
}

- (void)viewDidLoad {
    [super viewDidLoad];
    
    CGFloat navHeight = ChZ_IsiPhoneX ? 20 : 0;
    
    NSURL *url = [NSURL URLWithString:self.urlString];
    NSURLRequest *request = [NSURLRequest requestWithURL:url];
    self.webView = [[WKWebView alloc] initWithFrame:CGRectMake(0, 0, ChZ_WIDTH, ChZ_HEIGHT - 64 -2 *navHeight)];
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
    
    if (self.contentId) {
        [self requestData];
    }else{
        [self.webView loadRequest:request];
    }
}
-(void)back{
    [self.navigationController popViewControllerAnimated:YES];
}
#pragma mark - KVO监听
- (void)observeValueForKeyPath:(NSString *)keyPath ofObject:(id)object change:(NSDictionary *)change context:(void *)context {
    
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

-(void)requestData{
    switch (self.type) {
        case 1:
            [self requestCollegeDetail];
            break;
        case 2:
            [self requestNotesDetail];
            break;
            
        default:
            break;
    }
}


-(void)requestNotesDetail{
    @weakify(self);
    [ChZHomeRequest requestNotesDetailId:self.contentId successBlock:^(id dataSource) {
        @strongify(self);
        XHHNotesDetailModel *model = dataSource;
        [self.webView loadHTMLString:model.content baseURL:nil];
    } errorBlock:^(int code, NSString *error) {
        NSLog(@"%@",error);
    }];
}
-(void)requestCollegeDetail{
    @weakify(self);
//    [XHHCollegeRequest requestCollegeDetailId:self.contentId successBlock:^(id dataSource) {
//        @strongify(self);
//        XHHNotesDetailModel *model = dataSource;
//        [self setNavTitle:model.title];
//        [self.webView loadHTMLString:model.content baseURL:nil];
//    } errorBlock:^(int code, NSString *error) {
//        NSLog(@"%@",error);
//    }];
}

-(void)dealloc{
    [self.webView removeObserver:self forKeyPath:@"estimatedProgress"];
    [self.webView removeObserver:self forKeyPath:@"title"];
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
