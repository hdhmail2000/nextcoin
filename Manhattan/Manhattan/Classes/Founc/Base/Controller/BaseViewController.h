//
//  BaseViewController.h
//  CoinWallet
//
//  Created by Howe on 2018/5/14.
//  Copyright © 2018年 Howe. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface BaseViewController : UIViewController


@property (nonatomic, strong) UINavigationController *chz_navigationgVC;

@property (nonatomic, assign) BOOL  outEndEdit;

@property (nonatomic, assign) BOOL  noHiddenNav;

@property (nonatomic, assign) BOOL  noHandleNav;

@property (nonatomic, assign) BOOL  noHandleBottom;

@property (nonatomic, strong, readonly) UIRefreshControl *refreshControl;

@property (nonatomic, copy) ChZ_CallBackBlock callBackBlock;

- (void)chz_setupRefresh:(UIView *)view;
/**
 开始刷新
 */
- (void)chz_beginRefresh;

@end
