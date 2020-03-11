//
//  ChZPickerView.m
//  ChZDateSelect
//
//  Created by ChZ on 2017/4/5.
//  Copyright © 2017年 ChZ. All rights reserved.
//

#import "ChZPickerView.h"
@interface ChZPickerView()<UIPickerViewDelegate,UIPickerViewDataSource>
@property (nonatomic, weak) UIView  *chzSuperView;
@property (nonatomic, weak) UIView  *contentView;
@property (nonatomic, weak) UIPickerView  *pickerView;
@property (nonatomic, copy) NSString  *selectText;
@property (nonatomic, weak) UILabel *placeLabel;
@property (nonatomic, assign) NSInteger selectedIndex;
@property (nonatomic, weak) UIButton *doneBtn;
@property (nonatomic, weak) UIButton *cancenBtn;
@end
@implementation ChZPickerView

- (instancetype)init
{
    self = [super init];
    if (self)
    {
        [self _setupView];
    }
    return self;
}

-(void)setDataSource:(NSArray<NSString *> *)dataSource
{
    _dataSource = dataSource;
    if (self.pickerView)
    {
        [self.pickerView reloadAllComponents];
        if (self.dataSource.count> self.defaultSelectIndex)
        {
            [self.pickerView selectRow:self.defaultSelectIndex inComponent:0 animated:NO];
        }
    }
}
- (void)setDefaultSelectIndex:(NSInteger)defaultSelectIndex
{
    _defaultSelectIndex = defaultSelectIndex;
    if (self.dataSource.count> self.defaultSelectIndex)
    {
        [self.pickerView selectRow:self.defaultSelectIndex inComponent:0 animated:NO];
    }
}
- (void)setPlaceholder:(NSString *)placeholder
{
    _placeholder = placeholder;
    if (self.placeLabel)
    {
        self.placeLabel.text = placeholder;
    }
}

- (void)show
{
    CGFloat H = [UIScreen mainScreen].bounds.size.height;
    CGFloat W = [UIScreen mainScreen].bounds.size.width;
    self.contentView.frame = CGRectMake(0, H, W, ViewH);
    [UIView animateWithDuration:0.25f animations:^{
        self.contentView.frame = CGRectMake(0, H - ViewH, W, ViewH);
        self.backgroundColor = [UIColor colorWithRed:0 green:0 blue:0 alpha:0.2];
    }];
}
- (void)dismiss
{
    
    [UIView animateWithDuration:0.25f animations:^{
        CGFloat H = [UIScreen mainScreen].bounds.size.height;
        CGFloat W = [UIScreen mainScreen].bounds.size.width;
        self.contentView.frame = CGRectMake(0, H, W, ViewH);
        self.backgroundColor = [UIColor clearColor];
        
    }completion:^(BOOL finished)
     {
         [self removeFromSuperview];
         self.chzSuperView = nil;
     }];
    
}
- (void)setFinishButtonTextColor:(UIColor *)finishButtonTextColor
{
    [self.doneBtn setTitleColor:finishButtonTextColor forState:UIControlStateNormal];
}
- (void)setCancelButtonTextColor:(UIColor *)cancelButtonTextColor
{
    _cancelButtonTextColor =cancelButtonTextColor;
    [self.cancenBtn setTitleColor:cancelButtonTextColor forState:UIControlStateNormal];
}
- (void)setButtonTextColor:(UIColor *)buttonTextColor
{
    _buttonTextColor = buttonTextColor;
    [self.doneBtn setTitleColor:buttonTextColor forState:UIControlStateNormal];
    
}
- (void)_setupView
{
    
    CGFloat H = [UIScreen mainScreen].bounds.size.height;
    CGFloat W = [UIScreen mainScreen].bounds.size.width;
    UIViewController *vc = [UIApplication sharedApplication].keyWindow.rootViewController;
//    UIViewController *vc = [LZGAPPControl appRootVC];
    self.frame = vc.view.bounds;
    [vc.view addSubview:self];
    self.chzSuperView = vc.view;
    
    UIButton *btn = [UIButton buttonWithType:UIButtonTypeCustom];
    btn.frame = CGRectMake(0 , 0, W, H - ViewH);
    [self addSubview:btn];
    [btn addTarget:self action:@selector(doneAction) forControlEvents:UIControlEventTouchUpInside];
    
    UIView *contentView = [[UIView alloc]initWithFrame:CGRectMake(0, H - ViewH, W, ViewH)];
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
    doneBtn.titleLabel.font = [UIFont systemFontOfSize:17];
    [toolView addSubview:doneBtn];
    self.doneBtn = doneBtn;
    [doneBtn addTarget:self action:@selector(doneAction) forControlEvents:UIControlEventTouchUpInside];
    
    UIButton *cancelBtn = [UIButton buttonWithType:UIButtonTypeCustom];
    cancelBtn.frame = CGRectMake(10, 0, 60, 44);
    [cancelBtn setTitle:@"取消" forState:UIControlStateNormal];
    [cancelBtn setTitleColor:[UIColor colorWithRed:100/255.0f green:100/255.0f blue:100/255.0f alpha:1.0f] forState:UIControlStateNormal];
    [toolView addSubview:cancelBtn];
    self.cancenBtn = cancelBtn;
    [cancelBtn addTarget:self action:@selector(dismiss) forControlEvents:UIControlEventTouchUpInside];
    
    UILabel *placeLabel = [[UILabel alloc]initWithFrame:CGRectMake(80, 0, W - 160, 44)];
    placeLabel.font = [UIFont systemFontOfSize:14];
    placeLabel.textAlignment = NSTextAlignmentCenter;
    placeLabel.textColor = [UIColor colorWithRed:140/255.0f green:140/255.0f blue:140/255.0f alpha:1.0f];
    [toolView addSubview:placeLabel];
    self.placeLabel = placeLabel;
    
    UIView *bottomLineView = [[UIView alloc]initWithFrame:CGRectMake(0, 43, W, 0.5)];
    bottomLineView.backgroundColor = [UIColor colorWithRed:200/255.0f green:200/255.0f blue:200/255.0f alpha:1.0f];
    [toolView addSubview:bottomLineView];
    
    UIPickerView *pickerView = [[UIPickerView alloc]initWithFrame:CGRectMake(0, 44,W, 216)];
    pickerView.dataSource = self;
    pickerView.delegate = self;
    [contentView addSubview:pickerView];
    self.pickerView = pickerView;
    
}

- (NSInteger)numberOfComponentsInPickerView:(UIPickerView *)pickerView
{
    return 1;
}

- (NSInteger)pickerView:(UIPickerView *)pickerView numberOfRowsInComponent:(NSInteger)component
{
    return self.dataSource.count;
}

- (nullable NSString *)pickerView:(UIPickerView *)pickerView titleForRow:(NSInteger)row forComponent:(NSInteger)component
{
    if (self.dataSource.count > row)
    {
        return self.dataSource[row];
    }
    return nil;
}
- (void)pickerView:(UIPickerView *)pickerView didSelectRow:(NSInteger)row inComponent:(NSInteger)component
{
    if (self.dataSource.count > row)
    {
        self.selectText = self.dataSource[row];
        self.selectedIndex = row;
        
    }
}
- (void)doneAction
{
    if(!ChZ_StringIsEmpty(self.selectText))
    {
        if(self.dataSource != nil && self.dataSource.count> self.defaultSelectIndex)
        {
            self.selectText = [self.dataSource objectAtIndex:self.defaultSelectIndex];
            self.selectedIndex = self.defaultSelectIndex;
        }
    }
    if (self.block)
    {
        if (self.block(self.selectText,self.selectedIndex))
        {
            [self dismiss];
        }
    }else
    {
        [self dismiss];
    }
}


@end
