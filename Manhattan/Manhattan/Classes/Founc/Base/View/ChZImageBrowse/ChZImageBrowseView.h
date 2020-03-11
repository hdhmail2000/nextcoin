//
//  ChZImageBrowseView.h
//  tinyhour
//
//  Created by Howe on 2017/8/5.
//  Copyright © 2017年 ChZ. All rights reserved.
//

#import <UIKit/UIKit.h>
@class ChZImageModel;

typedef void(^ChZImageBrowseViewCloseCallBack) ();

typedef void(^ChZImageBrowseViewCloseReturnImageCallBack) (NSMutableArray *imageArray);

@interface ChZImageBrowseView : UIView
//数据源
@property (nonatomic, strong) NSMutableArray<ChZImageModel *> *imageItemArray;
//是否可以删除
@property (nonatomic, assign) BOOL isEdit;
//默认显示那一张 0~imageItemArray.count
@property (nonatomic, assign) NSInteger defaultSelectedIndex;
//关闭回调
@property (nonatomic, copy) ChZImageBrowseViewCloseCallBack block;

@property (nonatomic, copy) ChZImageBrowseViewCloseReturnImageCallBack imageBlock;
//显示
- (void)show;
//关闭
- (void)dismss;

@end
