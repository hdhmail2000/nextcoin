//
//  ChZCoinInfoDetailController.m
//  FuturePurse
//
//  Created by Howe on 2018/8/3.
//  Copyright © 2018年 jbtm. All rights reserved.
//

#import "ChZCoinInfoDetailController.h"
//#import <WebKit/WebKit.h>

@interface ChZCoinInfoDetailController ()
@property (weak, nonatomic) IBOutlet UIWebView *webView;

@property (nonatomic, strong) NSString * html5;
@end

@implementation ChZCoinInfoDetailController

- (void)viewDidLoad {
    [super viewDidLoad];
    [self.webView loadHTMLString: self.html baseURL:nil];
}
- (IBAction)backAction:(id)sender
{
    [self pop];
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
