//
//  XHHReleaseSwithViewController.m
//  Manhattan
//
//  Created by Apple on 2018/9/11.
//  Copyright © 2018年 Apple. All rights reserved.
//

#import "XHHReleaseSwithViewController.h"
#import "XHHPublicNavView.h"
#import "XLSlideSwitch.h"
#import "XHHReleaseSellViewController.h"
#import "XHHRealseBuyViewController.h"
#import "XHHMyRleaseListViewController.h"
@interface XHHReleaseSwithViewController ()<XLSlideSwitchDelegate>

@property (nonatomic, weak) XLSlideSwitch                *slideSwitch;

@property (nonatomic , strong) XHHPublicNavView           *navView;

@property (strong, nonatomic) UILabel                     *alertLable;

@end

@implementation XHHReleaseSwithViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    
    [self _setupView];
    // Do any additional setup after loading the view.
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
    NSArray *itemArray = @[@" 买入",@"卖出",@"我的发布"];
    NSMutableArray *viewControllers = [NSMutableArray array];
    XHHRealseBuyViewController *vc1 = [[XHHRealseBuyViewController alloc] init];
    [viewControllers addObject:vc1];

    XHHReleaseSellViewController *vc2 = [[XHHReleaseSellViewController alloc] init];
    [viewControllers addObject:vc2];
    
    XHHMyRleaseListViewController *vc3 = [[XHHMyRleaseListViewController alloc] init];
    [viewControllers addObject:vc3];
    
    vc1.cnyList = self.cnyList;
    vc2.cnyList = self.cnyList;
    vc3.cnyList = self.cnyList;
    
    CGFloat viewH = ChZ_HEIGHT - 64.0f;
    if ([ChZUtil isIphoneX])
    {
        viewH = ChZ_HEIGHT - 88.0f;
    }
    XLSlideSwitch *slideSwitch = [[XLSlideSwitch alloc] initWithFrame:CGRectMake(0, navHeight +64, self.view.bounds.size.width,viewH) lineColor:[UIColor colorWithHexString:@"308AF5"] Titles:itemArray viewControllers:viewControllers];
    slideSwitch.delegate = self;
    slideSwitch.itemSelectedColor = [UIColor colorWithHexString:@"308AF5"];
    slideSwitch.itemNormalColor = [UIColor colorWithHexString:@"4B4B80"];
    slideSwitch.customTitleSpacing = 40;
    [self.view addSubview:slideSwitch];
    self.slideSwitch = slideSwitch;
    
    
    @weakify(self);
    vc1.buySuccessBlock = ^{
        @strongify(self);
        [self showPaySuccessView];
    };
    vc2.SellSuccessBlock = ^{
        @strongify(self);
      [self showPaySuccessView];
    };
    vc3.slideSwitch = slideSwitch;
    
    
    
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
    self.fd_interactivePopDisabled = YES;
    //    NSMutableArray *tempItemArray = [NSMutableArray array];
    //    HCMarketAreaModel *model = self.areaArray[0]
    //    self.code = model.code;
    
}

-(XHHPublicNavView *)navView{
    if (!_navView) {
        _navView = [[[NSBundle mainBundle] loadNibNamed:@"XHHPublicNavView" owner:nil options:nil] lastObject];
        [_navView setLeftButtonTitle:@"发布" image:@"public_nav_leftBack"];
    }
    return _navView;
}
-(void)showPaySuccessView{
    [self.view addSubview:self.alertLable];
    [self.alertLable mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(self.view).offset(16);
        make.top.equalTo(self.navView.mas_bottom);
        make.right.equalTo(self.view).offset(-16);
        make.height.mas_equalTo(46);
    }];
    dispatch_after(dispatch_time(DISPATCH_TIME_NOW, (int64_t)(3 * NSEC_PER_SEC)), dispatch_get_main_queue(), ^{
        [self.alertLable removeFromSuperview];
    });
}

-(UILabel *)alertLable{
    if (!_alertLable) {
        _alertLable = [UILabel newSingleFrame:CGRectZero title:@"发布成功" fontS:14.0 color:[UIColor colorWithHexString:@"FFFFFF"]];
        _alertLable.textAlignment = NSTextAlignmentCenter;
        _alertLable.backgroundColor = [UIColor colorWithHexString:@"363672"];
    }
    return _alertLable;
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
