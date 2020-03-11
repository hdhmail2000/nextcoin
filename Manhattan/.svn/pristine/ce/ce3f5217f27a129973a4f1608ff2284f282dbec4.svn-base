//
//  ChZPickerView.h
//  ChZDateSelect
//
//  Created by ChZ on 2017/4/5.
//  Copyright © 2017年 ChZ. All rights reserved.
//

#import <UIKit/UIKit.h>

#define ViewH 260

typedef BOOL(^PickerDoneBlock) (NSString *text ,NSInteger selectedIndex);

@interface ChZPickerView : UIView

@property (nonatomic, strong) UIColor *buttonTextColor;

@property (nonatomic, strong) UIColor *finishButtonTextColor;

@property (nonatomic, strong) UIColor *cancelButtonTextColor;

@property (nonatomic, strong) NSArray<NSString *> *dataSource;

@property (nonatomic, copy) PickerDoneBlock  block;

@property (nonatomic, copy) NSString  *placeholder;

@property (nonatomic, assign) NSInteger  defaultSelectIndex;

- (void)show;

- (void)dismiss;
@end
