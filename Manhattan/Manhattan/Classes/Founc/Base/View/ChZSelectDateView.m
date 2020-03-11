//
//  ChZSelectDateView.m
//  ChZDateSelect
//
//  Created by ChZ on 2017/4/1.
//  Copyright © 2017年 ChZ. All rights reserved.
//

#import "ChZSelectDateView.h"
@interface ChZSelectDateView()
@property (nonatomic, weak) UIView  *chzSuperView;
@property (nonatomic, weak) UIView  *contentView;
@property (nonatomic, weak) UIDatePicker  *datePicker;
@end
@implementation ChZSelectDateView

- (instancetype)init
{
    self = [super init];
    if (self)
    {
        [self _setupView];
    }
    return self;
}

- (void)setMaximumDate:(NSDate *)maximumDate
{
    self.datePicker.maximumDate = maximumDate;
}
- (void)setMinimumDate:(NSDate *)minimumDate
{
    self.datePicker.minimumDate = minimumDate;
}
- (void)setDatePickerMode:(UIDatePickerMode)datePickerMode
{
    [self.datePicker setDatePickerMode:datePickerMode];
}

- (void)setDate:(NSDate *)date animated:(BOOL)animated
{
    [self.datePicker setDate:date animated:animated];
}

- (void)show
{
    CGFloat H = [UIScreen mainScreen].bounds.size.height;
    CGFloat W = [UIScreen mainScreen].bounds.size.width;
    self.contentView.frame = CGRectMake(0, H, W, SelectDateViewH);
    [UIView animateWithDuration:0.25f animations:^{
        self.contentView.frame = CGRectMake(0, H - SelectDateViewH, W, SelectDateViewH);
        self.backgroundColor = [UIColor colorWithRed:0 green:0 blue:0 alpha:0.2];
    }];
}
- (void)dismiss
{
    
    [UIView animateWithDuration:0.25f animations:^{
        CGFloat H = [UIScreen mainScreen].bounds.size.height;
        CGFloat W = [UIScreen mainScreen].bounds.size.width;
        self.contentView.frame = CGRectMake(0, H, W, SelectDateViewH);
        self.backgroundColor = [UIColor clearColor];
        
    }completion:^(BOOL finished)
     {
         [self removeFromSuperview];
         self.chzSuperView = nil;
     }];
    
}
- (void)_setupView
{
    CGFloat H = [UIScreen mainScreen].bounds.size.height;
    CGFloat W = [UIScreen mainScreen].bounds.size.width;
    UIViewController *vc = [UIApplication sharedApplication].keyWindow.rootViewController;
    self.frame = vc.view.bounds;
    [vc.view addSubview:self];
    self.chzSuperView = vc.view;
    
    UIButton *btn = [UIButton buttonWithType:UIButtonTypeCustom];
    btn.frame = CGRectMake(0 , 0, W, H - SelectDateViewH);
    [self addSubview:btn];
    [btn addTarget:self action:@selector(doneAction) forControlEvents:UIControlEventTouchUpInside];
    
    UIView *contentView = [[UIView alloc]initWithFrame:CGRectMake(0, H - SelectDateViewH, W, SelectDateViewH)];
    contentView.backgroundColor = [UIColor whiteColor];
    [self addSubview:contentView];
    self.contentView = contentView;
    
    UIView *toolView = [[UIView alloc]initWithFrame:CGRectMake(0, 0, W, 44)];
    [contentView addSubview:toolView];
    
    UIView *topLineView = [[UIView alloc]initWithFrame:CGRectMake(0, 0, W, 0.5)];
    topLineView.backgroundColor = [UIColor colorWithRed:200/255.0f green:200/255.0f blue:200/255.0f alpha:1.0f];
    [toolView addSubview:topLineView];
    
    UIButton *doneBtn = [UIButton buttonWithType:UIButtonTypeSystem];
    doneBtn.frame = CGRectMake(W - 70, 0, 60, 44);
    [doneBtn setTitle:@"完成" forState:UIControlStateNormal];
    [toolView addSubview:doneBtn];
    [doneBtn addTarget:self action:@selector(doneAction) forControlEvents:UIControlEventTouchUpInside];
    
    UIView *bottomLineView = [[UIView alloc]initWithFrame:CGRectMake(0, 43, W, 0.5)];
    bottomLineView.backgroundColor = [UIColor colorWithRed:200/255.0f green:200/255.0f blue:200/255.0f alpha:1.0f];
    [toolView addSubview:bottomLineView];
    
    UIDatePicker *datePicker = [[UIDatePicker alloc]initWithFrame:CGRectMake(0, 44,W, 216)];
    [contentView addSubview:datePicker];
    self.datePicker = datePicker;
    
    
}
- (void)doneAction
{
    if (self.block)
    {
        if (self.block(self.datePicker.date))
        {
            [self dismiss];
        }
    }else
    {
        [self dismiss];
    }
}
/*
// Only override drawRect: if you perform custom drawing.
// An empty implementation adversely affects performance during animation.
- (void)drawRect:(CGRect)rect {
    // Drawing code
}
*/

@end
