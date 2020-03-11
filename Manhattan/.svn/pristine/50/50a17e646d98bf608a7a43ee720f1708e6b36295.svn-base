//
//  XHHTradOrderSellDetailController.m
//  FuturePurse
//
//  Created by Apple on 2018/7/30.
//  Copyright © 2018年 jbtm. All rights reserved.
//

#import "XHHTradOrderSellDetailController.h"
#import "XHHOrderDetailHeadView.h"
#import "XHHOrderNumberView.h"
#import "XHHTradPayMessageView.h"

#import "XHHTradWaitPayView.h"

#import "XHHPublicNavView.h"
@interface XHHTradOrderSellDetailController ()

@property (strong, nonatomic) XHHOrderDetailHeadView    *topView;

@property (strong, nonatomic) XHHOrderNumberView        *orderNumberView;

@property (strong, nonatomic) XHHTradPayMessageView     *payMsgView;

@property (strong, nonatomic) XHHTradWaitPayView        *waitPayView;

@property (assign, nonatomic) NSInteger                 payState;//1未付款 //2等待卖方确认  3 以未完成

@property (strong, nonatomic) UIButton                  *bottomButton;

@property (nonatomic , strong) XHHPublicNavView           *navView;

@end

@implementation XHHTradOrderSellDetailController

- (void)viewDidLoad {
    [super viewDidLoad];
    
    [self zh_setupUI];
    // Do any additional setup after loading the view.
}
-(void)zh_setupUI{
    
    _payState = 1;
    CGFloat navHeight = ChZ_IsiPhoneX ? 24 : 0;
    
    [self.view addSubview:self.navView];
    [self.navView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.top.right.equalTo(self.view);
        make.height.mas_equalTo(navHeight +64);
    }];
    
    //背景滚动视图
    UIScrollView *bgScrollView = [[UIScrollView alloc]init];
    bgScrollView.showsVerticalScrollIndicator = NO;
    [self.view addSubview:bgScrollView];
    [bgScrollView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.edges.equalTo(self.view).insets(UIEdgeInsetsMake(navHeight + 64, 0, navHeight, 0));
    }];
    //内容视图
    UIView *contentView = [[UIView alloc]init];
    [bgScrollView addSubview:contentView];
    [contentView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.top.left.bottom.right.equalTo(bgScrollView).insets(UIEdgeInsetsZero);
        make.width.equalTo(bgScrollView);
    }];
    
    CGFloat waitHeight = _payState == 1 ? 44 : 0;
    [contentView addSubview:self.waitPayView];
    [self.waitPayView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.right.top.equalTo(contentView);
        make.height.mas_equalTo(waitHeight);
    }];
    
    [contentView addSubview:self.topView];
    [self.topView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.right.equalTo(contentView);
        make.top.equalTo(self.waitPayView.mas_bottom);
        make.height.mas_equalTo(222);
    }];
    
    [contentView addSubview:self.orderNumberView];
    [self.orderNumberView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.right.equalTo(contentView);
        make.top.equalTo(self.topView.mas_bottom);
        make.height.mas_equalTo(95);
    }];
    
    [contentView addSubview:self.payMsgView];
    [self.payMsgView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.right.equalTo(contentView);
        make.top.equalTo(self.orderNumberView.mas_bottom);
        make.bottom.equalTo(self.payMsgView.tableView);
    }];
    
    if (_payState == 2) {
        [contentView addSubview:self.bottomButton];
        [self.bottomButton mas_makeConstraints:^(MASConstraintMaker *make) {
            make.left.equalTo(contentView).offset(16);
            make.right.equalTo(contentView).offset(-16);
            make.top.equalTo(self.payMsgView.mas_bottom).offset(20);
            make.height.mas_equalTo(46);
        }];
        
        [contentView mas_makeConstraints:^(MASConstraintMaker *make) {
            make.bottom.equalTo(self.bottomButton.mas_bottom).offset(20);
        }];
    }else{
        [contentView mas_makeConstraints:^(MASConstraintMaker *make) {
            make.bottom.equalTo(self.payMsgView.mas_bottom);
        }];
    }
}
-(void)canlePay{
    
}
-(void)viewDidLayoutSubviews{
   
}

-(XHHPublicNavView *)navView{
    if (!_navView) {
        _navView = [[[NSBundle mainBundle] loadNibNamed:@"XHHPublicNavView" owner:nil options:nil] lastObject];
        [_navView setLeftButtonTitle:@"发布" image:@"public_nav_leftBack"];
    }
    return _navView;
}
-(XHHOrderDetailHeadView *)topView{
    if (!_topView) {
        _topView = [[[NSBundle mainBundle] loadNibNamed:@"XHHOrderDetailHeadView" owner:nil options:nil] lastObject];
        [_topView reloadSellViewWithType:_payState];
    }
    return _topView;
}
-(XHHOrderNumberView *)orderNumberView{
    if (!_orderNumberView) {
        _orderNumberView = [[[NSBundle mainBundle] loadNibNamed:@"XHHOrderNumberView" owner:nil options:nil] lastObject];
        [_orderNumberView setType:2];
    }
    return _orderNumberView;
}
-(XHHTradPayMessageView *)payMsgView{
    if (!_payMsgView) {
        _payMsgView = [[[NSBundle mainBundle] loadNibNamed:@"XHHTradPayMessageView" owner:nil options:nil] lastObject];
    }
    return _payMsgView;
}
-(XHHTradWaitPayView *)waitPayView{
    if (!_waitPayView) {
        _waitPayView = [[[NSBundle mainBundle] loadNibNamed:@"XHHTradWaitPayView" owner:nil options:nil] lastObject];
    }
    return _waitPayView;
}
-(UIButton *)bottomButton{
    if (!_bottomButton) {
        _bottomButton = [UIButton buttonWithType:UIButtonTypeCustom];
        [_bottomButton setTitle:@"我已收到款" forState:UIControlStateNormal];
        [_bottomButton setTitleColor:[UIColor colorWithHexString:@"ffffff"] forState:UIControlStateNormal];
        [_bottomButton setBackgroundColor:[UIColor colorWithHexString:@"2395FF"]];
        [_bottomButton.titleLabel setFont:[UIFont systemFontOfSize:16.0]];
    }
    return _bottomButton;
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
