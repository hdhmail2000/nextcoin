//
//  ChZSelectDateView.h
//  ChZDateSelect
//
//  Created by ChZ on 2017/4/1.
//  Copyright © 2017年 ChZ. All rights reserved.
//

#import <UIKit/UIKit.h>

#define SelectDateViewH 260

typedef BOOL(^SelectedBlock) (NSDate *date);

@interface ChZSelectDateView : UIView

@property (nonatomic, strong) NSDate *minimumDate;

@property (nonatomic, strong) NSDate *maximumDate;

@property (nonatomic, assign) UIDatePickerMode datePickerMode;

@property (nonatomic, copy) SelectedBlock  block;

- (void)setDate:(NSDate *)date animated:(BOOL)animated;

- (void)show;

- (void)dismiss;

@end
