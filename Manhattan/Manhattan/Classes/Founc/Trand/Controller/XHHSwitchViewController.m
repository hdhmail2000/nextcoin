//
//  XHHSwitchViewController.m
//  FuturePurse
//
//  Created by Apple on 2018/8/29.
//  Copyright © 2018年 jbtm. All rights reserved.
//

#import "XHHSwitchViewController.h"
#import "XLSlideSwitch.h"
#import "XHHSwitchDetailViewController.h"
#import "XHHSwitchPayViewController.h"
#import "XHHSwithExplictViewController.h"
#import "XHHSwithPayListViewController.h"
#import "XHHSwithTypeView.h"
#import "HCMarketAreaModel.h"
#import "XHHPublicNavView.h"
@interface XHHSwitchViewController ()<XLSlideSwitchDelegate>
@property (nonatomic , weak)   XLSlideSwitch                 *slideSwitch;
@property (nonatomic , strong) XHHPublicNavView              *navView;
@property (nonatomic , strong) XHHSwitchPayViewController    *payVc;
@property (nonatomic , strong) XHHSwitchDetailViewController *detailVc;

@property (nonatomic, strong)  NSTimer  *timer;
@property (nonatomic , copy)   NSString *code;
@end

@implementation XHHSwitchViewController
-(void)viewWillDisappear:(BOOL)animated
{
    [super viewWillDisappear:animated];
    [self _removeTimer];
}

- (void)viewDidLoad {
    [super viewDidLoad];
    
    [self _setupView];
    
    [self requestCNYDetail];
    
    [self _loadTimer];
    
    self.fd_interactivePopDisabled = YES;
    for(UIView *v in self.slideSwitch.subviews)
    {
        for(UIView *t in v.subviews){
            if ([t isKindOfClass:[UIScrollView class]])
            {
                UIScrollView *scrll = (UIScrollView *)t;
                scrll.scrollEnabled = NO;
                
            }
        }
    }
    
    [[NSNotificationCenter defaultCenter] addObserver:self selector:@selector(buySellAction) name:@"BUYSELLACTION" object:nil];
    
}
-(void)buySellAction{
    [self.slideSwitch setSelectedIndex:1];
}
- (void)viewWillAppear:(BOOL)animated
{
    [super viewWillAppear:animated];
     [self.navigationController setNavigationBarHidden:NO animated:YES];
    [self.slideSwitch setSelectedIndex:0];
}

- (void)_setupView
{
    
    CGFloat navHeight = ChZ_IsiPhoneX ? 24 : 0;
    
    [self.view addSubview:self.navView];
    [self.navView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.top.right.equalTo(self.view);
        make.height.mas_equalTo(navHeight +64);
    }];
    
    if (self.slideSwitch)
    {
        [self.slideSwitch removeFromSuperview];
    }
    NSArray *itemArray = @[@"详情",@"交易",@"当前委托",@"成交记录"];
    NSMutableArray *viewControllers = [NSMutableArray array];
    XHHSwitchDetailViewController *vc1 = [[XHHSwitchDetailViewController alloc] init];
    self.detailVc = vc1;
    [viewControllers addObject:vc1];
    
    XHHSwitchPayViewController *vc2 = [[XHHSwitchPayViewController alloc] init];
    vc2.buyName = self.buyName;
    vc2.sellName = self.sellName;
    self.payVc = vc2;
    [viewControllers addObject:vc2];
    
    XHHSwithExplictViewController *vc3 = [[XHHSwithExplictViewController alloc] init];
    vc3.buyName = self.buyName;
    vc3.sellName = self.sellName;
    [viewControllers addObject:vc3];
    
    XHHSwithPayListViewController *vc4 = [[XHHSwithPayListViewController alloc] init];
    vc4.sellName = self.sellName;
    vc4.buyName = self.buyName;
    [viewControllers addObject:vc4];
    
    vc1.symblId = self.symblId;
    vc2.symblId = self.symblId;
    
    CGFloat viewH = ChZ_HEIGHT - 64.0f;
    if ([ChZUtil isIphoneX])
    {
        viewH = ChZ_HEIGHT - 88.0f;
    }
    XLSlideSwitch *slideSwitch = [[XLSlideSwitch alloc] initWithFrame:CGRectMake(0, navHeight +64, self.view.bounds.size.width,viewH) lineColor:[UIColor colorWithHexString:@"308AF5"] Titles:itemArray viewControllers:viewControllers];
    slideSwitch.delegate = self;
    slideSwitch.itemSelectedColor = [UIColor colorWithHexString:@"308AF5"];
    slideSwitch.itemNormalColor = [UIColor colorWithHexString:@"4B4B80"];
    slideSwitch.customTitleSpacing = 25*(ChZ_WIDTH/320);
    [self.view addSubview:slideSwitch];
    self.slideSwitch = slideSwitch;
}

-(XHHPublicNavView *)navView{
    if (!_navView) {
        _navView = [[[NSBundle mainBundle] loadNibNamed:@"XHHPublicNavView" owner:nil options:nil] lastObject];
        [_navView setLeftButtonTitle:[NSString stringWithFormat:@"%@/%@",self.sellName,self.buyName] image:@"public_nav_leftBack"];
    }
    return _navView;
}
-(void)back{
    [self popToVCClassName:@"TradCenterController"];
    [self.navigationController setNavigationBarHidden:YES animated:YES];
}

#pragma ------获取币种价格详情
-(void)requestCNYDetail{

    @weakify(self);
    [ChZHomeRequest requestRealCoin:self.symblId successBlock:^(id dataSource){
        @strongify(self);
        [self _handleData:dataSource];
    } errorBlock:^(int code, NSString *error){
        
    }];
}
- (void)_handleData:(NSArray *)array
{
    if (array == nil || array.count == 0) {
        return;
    }
    ChZRealCoinInfoModel *model = [array firstObject];
    self.detailVc.coinModel = model;
    self.payVc.coinModel = model;

}

- (void)_refreshData
{
    [self requestCNYDetail];
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
-(void)dealloc
{
    [[NSNotificationCenter defaultCenter] removeObserver:self name:@"BUYSELLACTION" object:nil];
    
    [self _removeTimer];
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
