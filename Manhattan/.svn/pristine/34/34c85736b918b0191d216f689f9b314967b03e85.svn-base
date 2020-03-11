//
//  BaseTableViewController.m
//  CoinWallet
//
//  Created by Howe on 2018/5/14.
//  Copyright © 2018年 Howe. All rights reserved.
//

#import "BaseTableViewController.h"

@interface BaseTableViewController ()

//@property (nonatomic, strong) UIRefreshControl *refreshControl;

@end

@implementation BaseTableViewController

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
    if ([ChZUtil isIphoneX])
    {
        UIView *headView = self.tableView.tableHeaderView;
        self.tableView.tableHeaderView = nil;
        headView.chz_h = headView.chz_h + 24;
        self.tableView.tableHeaderView = headView;
    }
}

- (void)viewDidDisappear:(BOOL)animated
{
    [super viewDidDisappear:animated];
    ChZ_MBDismss
}
#pragma mark refreshControl
- (void)chz_setupRefresh:(UIView *)view
{
    UIRefreshControl *refreshControl = UIRefreshControl.new;
    [view addSubview:refreshControl];
    self.refreshControl = refreshControl;
    [self.refreshControl addTarget:self action:@selector(private_benginRef) forControlEvents:UIControlEventValueChanged];
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
