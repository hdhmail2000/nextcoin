//
//  UIViewController+ChZExtension.h
//  lzg
//
//  Created by Howe on 2017/10/16.
//  Copyright © 2017年 Howe. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "ChZNavigationView.h"

typedef void(^AlertActionBlock)(void);

typedef void(^SelectedImageFinshBlock) (UIImage *image);

@interface UIViewController (ChZExtension)<UINavigationControllerDelegate,UIImagePickerControllerDelegate>

+ (instancetype)initWithStoryboard:(NSString *)storyboardName;

- (void)pop;

- (void)popToVCClassName:(NSString *)className;

- (BOOL)popToMaybeVCClassNames:(NSArray<NSString *> *)vcClassNameArray;

- (BOOL)popToVC:(NSString *)popVCClassName pushToVC:(NSString *)pushVCClassName;

- (BOOL)popToVC:(NSString *)popVCClassName pushToStoryboard:(NSString *)storyboardName vcID:(NSString *)vcID;

- (instancetype)pushToStoryboard:(NSString *)storyboardName vcID:(NSString *)vcID;

- (void)showTheAlertVCWithStyle:(UIAlertControllerStyle)sytle
                          title:(NSString *)title
                        message:(NSString *)msg
                         title1:(NSString *)title1
                        action1:(AlertActionBlock)action1Block
                         title2:(NSString *)title2
                        action2:(AlertActionBlock)action2Block
                         title3:(NSString *)title3
                        action3:(AlertActionBlock)action3Block
                     completion:(AlertActionBlock)completionBlock;

- (void)selectImageEdit:(BOOL)edit selectedFinish:(SelectedImageFinshBlock)block;

/**
 给滑动视图添加上拉加载和下拉刷新
 
 @param needAddRefView 所有继承至UIScrollView 的视图
 @param refreshAction  刷新
 @param loadMoreAction 加载
 */

- (void)setupRefreshWithView:(id )needAddRefView refreshHeadAction:(SEL)refreshAction refreshFooterAction:(SEL)loadMoreAction;

@end
