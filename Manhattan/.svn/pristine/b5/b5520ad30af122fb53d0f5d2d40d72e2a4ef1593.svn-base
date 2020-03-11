//
//  BaseTableViewController.h
//  CoinWallet
//
//  Created by Howe on 2018/5/14.
//  Copyright © 2018年 Howe. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface BaseTableViewController : UITableViewController

@property (nonatomic, strong, readonly) UIRefreshControl *refreshControl;

@property (nonatomic, assign) BOOL  noHiddenNav;

@property (nonatomic, copy) ChZ_CallBackBlock callBackBlock;

- (void)chz_setupRefresh:(UIView *)view;
/**
 开始刷新
 */
- (void)chz_beginRefresh;

@end
