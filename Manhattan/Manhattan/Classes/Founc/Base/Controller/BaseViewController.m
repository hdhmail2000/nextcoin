//
//  BaseViewController.m
//  CoinWallet
//
//  Created by Howe on 2018/5/14.
//  Copyright © 2018年 Howe. All rights reserved.
//

#import "BaseViewController.h"
#import "ChZNavigationView.h"

@interface BaseViewController ()
@property (nonatomic, strong) UILabel *chz_titleLabel;
@property (nonatomic, strong) UIView *chz_lineView;
@property (nonatomic, strong) ChZNavigationView *chz_navView;

@property (nonatomic, strong) ChZBottomContentView *chz_bottomView;
@property (nonatomic, strong) UIScrollView *chz_scrollView;
@end

@implementation BaseViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    if (self.navigationController.navigationBar)
    {
        self.navigationController.navigationBar.hidden = !self.noHiddenNav;
    }
    
    if (@available(iOS 11.0, *)) {
        
        UIScrollView.appearance.contentInsetAdjustmentBehavior = UIScrollViewContentInsetAdjustmentNever;
        
    } else {

        self.automaticallyAdjustsScrollViewInsets = NO;
        
    }
    
    if (self.noHandleNav == NO)
    {
        for (UIView *view  in self.view.subviews)
        {
            if ([view isKindOfClass:[ChZNavigationView class]])
            {
                self.chz_navView = (ChZNavigationView *)view;
                if ([ChZUtil isIphoneX])
                {
                    [self handleNavView:(ChZNavigationView *)view];
                }
                break;
            }
        }
    }
    if (self.noHandleBottom == NO)
    {
        for (UIView *view  in self.view.subviews)
        {
            if ([view isKindOfClass:[ChZBottomContentView class]])
            {
                self.chz_bottomView = (ChZBottomContentView *)view;
                if ([ChZUtil isIphoneX])
                {
                    [self handleBottomView:self.chz_bottomView];
                }
                break;
            }
        }
    }
    self.view.backgroundColor = bgColor;
    
}
- (void)handleBottomView:(ChZBottomContentView *)view
{
    for (NSLayoutConstraint *layout in view.constraints)
    {
        if (layout.firstAttribute == NSLayoutAttributeHeight)
        {
            layout.constant = layout.constant + 24.0f;
            break;
        }
    }
}

- (void)handleNavView:(ChZNavigationView *)view
{
    
    
    for (NSLayoutConstraint *layout in view.constraints)
    {
        if (layout.firstAttribute == NSLayoutAttributeHeight)
        {
            layout.constant = layout.constant + 24.0f;
            break;
        }
    }
}

- (void)viewWillAppear:(BOOL)animated
{
    [super viewWillAppear:animated];
    
}
- (void)viewWillDisappear:(BOOL)animated
{
    [super viewWillDisappear:animated];
    ChZ_MBDismss
    if (self.outEndEdit)
    {
        [self.view endEditing:YES];
    }
}
- (void)viewDidDisappear:(BOOL)animated
{
    [super viewDidDisappear:animated];
//    ChZ_MBDismss
}
#pragma mark refreshControl
- (void)chz_setupRefresh:(UIView *)view
{
    UIRefreshControl *refreshControl = UIRefreshControl.new;
    [view addSubview:refreshControl];
    _refreshControl = refreshControl;
    [refreshControl addTarget:self action:@selector(private_benginRef) forControlEvents:UIControlEventValueChanged];
}
- (void)private_benginRef
{
    if (self.refreshControl.refreshing)
    {
        [self chz_beginRefresh];
    }
}
- (void)chz_beginRefresh
{
    ChZ_InfoLog(@"子类没有继承该方法");
}

- (BOOL)prefersStatusBarHidden
{
    return NO;
}

- (UIStatusBarStyle)preferredStatusBarStyle
{
    return UIStatusBarStyleLightContent;
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
