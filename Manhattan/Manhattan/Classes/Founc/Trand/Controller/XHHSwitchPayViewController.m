//
//  XHHSwitchPayViewController.m
//  FuturePurse
//
//  Created by Apple on 2018/8/29.
//  Copyright © 2018年 jbtm. All rights reserved.
//

#import "XHHSwitchPayViewController.h"
#import "XHHMineSetpswViewController.h"
#import "XHHPayPwView.h"
#import "XHHSalesEntrustView.h"
#import "XHHTradBottomBuyView.h"
#import "XHHExplicitPriceView.h"
#import "XHHTrandVolumeView.h"

@interface XHHSwitchPayViewController ()<XHHPayPwViewProtocol>
@property (strong, nonatomic) UIScrollView                *bgScrollView;
@property (strong, nonatomic) XHHPayPwView                *passWordView;//输入交易密码视图
@property (strong, nonatomic) XHHSalesEntrustView         *entrustView;
@property (strong, nonatomic) XHHTradBottomBuyView        *bottomBuyView;
@property (strong, nonatomic) XHHExplicitPriceView        *priceView;
@property (strong, nonatomic) XHHTrandVolumeView          *volumeView;
@property (strong, nonatomic) UIButton                    *buyyButton;

@property (nonatomic , assign) BOOL   isBuy;
@property (nonatomic , assign) double price;
@property (nonatomic , assign) double num;

@property (strong, nonatomic)  NSString *password;
@property (nonatomic , copy)   NSString *canUseMoney;
@property (nonatomic , strong) NSString *canUseBuyMoney;
@property (nonatomic , strong) NSTimer  *timer;
@end

@implementation XHHSwitchPayViewController
-(void)viewWillDisappear:(BOOL)animated
{
    [super viewWillDisappear:animated];
    [self _removeTimer];
}
-(void)viewWillAppear:(BOOL)animated
{
    [super viewWillAppear:animated];
    [self _loadTimer];
}

- (void)viewDidLoad {
    [super viewDidLoad];
    
    [self requestCanUseMoney];
    
    [self zh_setupUI];
    
    [self _refreshData];
}
-(void)zh_setupUI{
    //背景滚动视图
    UIScrollView *bgScrollView = [[UIScrollView alloc]init];
    bgScrollView.showsVerticalScrollIndicator = NO;
    [self.view addSubview:bgScrollView];
    self.bgScrollView = bgScrollView;
    [bgScrollView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.edges.equalTo(self.view);
    }];

    UIView *contentView = [[UIView alloc] init];
    [contentView addSubview:self.priceView];
    [self.priceView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.right.top.equalTo(contentView);
        make.height.mas_equalTo(160);
    }];
    
    [contentView addSubview:self.entrustView];
    [self.entrustView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.right.equalTo(contentView);
        make.top.equalTo(self.priceView.mas_bottom);
        make.height.mas_equalTo(322);
    }];
    
    [contentView addSubview:self.buyyButton];
    [self.buyyButton mas_makeConstraints:^(MASConstraintMaker *make) {
        make.top.equalTo(self.entrustView.mas_bottom).offset(16);
        make.left.equalTo(contentView).offset(16);
        make.right.equalTo(contentView).offset(-16);
        make.height.mas_equalTo(44);
    }];
    
    [contentView addSubview:self.volumeView];
    [self.volumeView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.right.equalTo(contentView);
        make.top.equalTo(self.buyyButton.mas_bottom).offset(16);
        make.height.mas_equalTo(388);
    }];
    
    [bgScrollView addSubview:contentView];
    [contentView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.top.left.bottom.right.equalTo(self.bgScrollView).insets(UIEdgeInsetsZero);
        make.width.equalTo(self.bgScrollView);
        make.bottom.mas_equalTo(self.volumeView);
    }];
}
-(XHHExplicitPriceView *)priceView{
    if (!_priceView) {
        _priceView = [[[NSBundle mainBundle] loadNibNamed:@"XHHExplicitPriceView" owner:nil options:nil] lastObject];
    }
    return _priceView;
}
-(XHHTradBottomBuyView *)bottomBuyView{
    if (!_bottomBuyView) {
        _bottomBuyView = [[[NSBundle mainBundle] loadNibNamed:@"XHHTradBottomBuyView" owner:nil options:nil] lastObject];
    }
    return _bottomBuyView;
}
- (XHHSalesEntrustView *)entrustView{
    if (!_entrustView) {
        _entrustView = [[[NSBundle mainBundle] loadNibNamed:@"XHHSalesEntrustView" owner:nil options:nil] lastObject];
        _entrustView.typeLable.text = self.buyName;
        _entrustView.numTypeLable.text = self.sellName;
        @weakify(self);
        _entrustView.buySellBlock = ^(BOOL isSell)
        {
            @strongify(self);
            if (isSell)
            {
                [self.buyyButton setTitle:@"卖出" forState:UIControlStateNormal];
                [self.buyyButton setBackgroundImage:[UIImage imageNamed:@"mining_explicit_sellbg"] forState:UIControlStateNormal];
            }else
            {
                
                [self.buyyButton setTitle:@"买入" forState:UIControlStateNormal];
                [self.buyyButton setBackgroundImage:[UIImage imageNamed:@"mining_explicit_buybg"] forState:UIControlStateNormal];
            }
        };
    }
    return _entrustView;
}
-(XHHTrandVolumeView *)volumeView{
    if (!_volumeView) {
        _volumeView = [[XHHTrandVolumeView alloc] init];
    }
    return _volumeView;
}
-(UIButton *)buyyButton{
    if (!_buyyButton) {
        _buyyButton = [UIButton buttonWithType:UIButtonTypeCustom];
        [_buyyButton setBackgroundImage:[UIImage imageNamed:@"mining_explicit_buybg"] forState:UIControlStateNormal];
        [_buyyButton setTitle:@"买入" forState:UIControlStateNormal];
        [_buyyButton setTitleColor:[UIColor colorWithHexString:@"ffffff"] forState:UIControlStateNormal];
        @weakify(self);
        [[_buyyButton rac_signalForControlEvents:UIControlEventTouchUpInside] subscribeNext:^(__kindof UIControl * _Nullable x)
         {
             @strongify(self);
             self.isBuy = [self.buyyButton.titleLabel.text isEqualToString:@"买入"] ? YES : NO;
             self.price = [self.entrustView.priceTextFeild.text doubleValue];
             self.num = [self.entrustView.muchTextFeild.text doubleValue];
             if (self.price <= 0 || self.num <= 0)
             {
                 ChZ_MBError(@"委托价格或数量不能为零");
                 return;
             }
             if ([APPControl sharedAPPControl].isSetPayPassword)
             {
                 [self.passWordView showInView:self.view];
                 self.passWordView.height = self.view.bounds.size.height;
             }else
             {
                 [self showTheAlertVCWithStyle:UIAlertControllerStyleAlert title:nil message:@"您还未设置交易密码，快去设置吧！" title1:@"取消" action1:^{
                     
                 } title2:@"马上去" action2:^
                  {
                      XHHMineSetpswViewController *vc = [XHHMineSetpswViewController initWithStoryboard:@"MineStroy"];
                      [[ChZUtil getCurrentVC].navigationController pushViewController:vc animated:YES];
                  } title3:nil action3:nil completion:nil];
             }
             [self.view endEditing:YES];
         }];
    }
    return _buyyButton;
}
-(void)setCoinModel:(ChZRealCoinInfoModel *)coinModel
{
    _coinModel = coinModel;
    self.priceView.coinModel = coinModel;
}
- (XHHPayPwView *)passWordView{
    if (!_passWordView)
    {
        _passWordView = [[[NSBundle mainBundle] loadNibNamed:@"XHHPayPwView" owner:nil options:nil] lastObject];
        _passWordView.delegate = self;
    }
    return _passWordView;
}
-(void)payViewSure:(NSString *)pwd{
    self.password = pwd;
    if (self.isBuy)
    {
        [self buyCoin];
    }else{
        [self sellCoin];
    }
}
- (void)buyCoin
{
    double price = self.price;
    double number = self.num;
    NSString *passwrod = self.password;
    NSString *areaAndCoint = [NSString stringWithFormat:@"%@_%@",self.sellName,self.buyName];
    NSString *name = [areaAndCoint lowercaseString];
    ChZ_MBMsg(nil)
    [ChZHomeRequest requestCreateOrder:name
                                number:number
                                 price:price
                                  type:@"buy"
                              tradePwd:passwrod
                          successBlock:^(id dataSource)
    {
        ChZ_MBDismssSuccess(@"成功!")
    } errorBlock:^(int code, NSString *error) {
        ChZ_MBDismssError(error)
    }];
}

- (void)sellCoin
{
    double price = self.price;
    double number = self.num;
    NSString *passwrod = self.password;
    NSString *areaAndCoint = [NSString stringWithFormat:@"%@_%@",self.sellName,self.buyName];
    NSString *name = [areaAndCoint lowercaseString];
    ChZ_MBMsg(nil)
    [ChZHomeRequest requestCreateOrder:name
                                number:number
                                 price:price
                                  type:@"sell"
                              tradePwd:passwrod
                          successBlock:^(id dataSource)
    {
        ChZ_MBDismssSuccess(@"成功!")
    } errorBlock:^(int code, NSString *error) {
        ChZ_MBDismssError(error)
    }];
}

-(void)requestCanUseMoney
{
    @weakify(self);
    [ChZHomeRequest requestUserAssets:self.symblId successBlock:^(id dataSource)
     {
        @strongify(self);
        self.canUseBuyMoney  = dataSource[@"buyCoin"][@"total"];
        self.canUseMoney     = dataSource[@"sellCoin"][@"total"];
        if (self.canUseMoney)
        {
            self.entrustView.progressVlalue = [self.canUseMoney doubleValue];
        }
         self.entrustView.sellMoney = self.canUseMoney;
         self.entrustView.buyMoney = self.canUseBuyMoney;
    } errorBlock:^(int code, NSString *error) {
        ChZ_DebugLog(@"%@--",error);
    }];
}

- (void)_refreshData
{
    @weakify(self);
    [ChZHomeRequest requestDish:self.symblId successBlock:^(id dataSource)
     {
         @strongify(self);
         self.volumeView.dataDic = dataSource;
     } errorBlock:^(int code, NSString *error)
     {
         ChZ_MBError(error)
     }];
    
}

- (void)_loadTimer
{
    if (self.timer == nil)
    {
        self.timer = [NSTimer timerWithTimeInterval:5 target:self selector:@selector(_refreshData) userInfo:nil repeats:YES];
        [[NSRunLoop currentRunLoop]addTimer:self.timer forMode:NSRunLoopCommonModes];
    }
}
- (void)_removeTimer
{
    if (_timer && _timer.valid) {
        [_timer invalidate];
    }
    _timer = nil;
}
-(void)dealloc{
    
    [self _removeTimer];
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
