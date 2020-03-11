//
//  ChZAreaPikerView.m
//  hotcoin
//
//  Created by Howe on 2018/4/20.
//  Copyright © 2018年 Howe. All rights reserved.
//

#import "ChZAreaPikerView.h"
#define AreaViewH 260

@implementation ChZAreaDataModel

+ (NSDictionary *)mj_objectClassInArray
{
    return @{@"cities":[self class],@"counties":[self class]};
}
@end

@implementation ChzAreaSelectedDataModel

@end

@interface ChZAreaPikerView()<UIPickerViewDelegate,UIPickerViewDataSource>

@property (nonatomic, weak) UIView  *chzSuperView;

@property (nonatomic, weak) UIView  *contentView;

@property (nonatomic, weak) UIPickerView  *pickerView;

@property (nonatomic, weak) UIButton *doneBtn;

@property (nonatomic, weak) UIButton *cancenBtn;

@property (nonatomic, weak) UILabel *placeLabel;

@property (nonatomic, strong) NSArray  *dataSource;

@property (nonatomic, strong) NSArray  *array1;

@property (nonatomic, strong) NSArray  *array2;

@property (nonatomic, strong) NSArray  *array3;

@end
@implementation ChZAreaPikerView

- (instancetype)init
{
    self = [super init];
    if (self)
    {
        [self _setupView];
        [self _setupData];
    }
    return self;
}

- (void)_setupData
{
    NSString *path = [[NSBundle mainBundle] pathForResource:@"city" ofType:@"json"];
    NSData *data = [NSData dataWithContentsOfFile:path];
    NSArray *array = [NSJSONSerialization JSONObjectWithData:data options:NSJSONReadingMutableLeaves error:NULL];
    self.dataSource = [ChZAreaDataModel mj_objectArrayWithKeyValuesArray:array];
    self.array1 = self.dataSource;
    ChZAreaDataModel *model1 = [self.array1 objectAtIndex:0];
    self.array2 = model1.cities;
    ChZAreaDataModel *model2 = [self.array2 objectAtIndex:0];
    self.array3 = model2.counties;
    [self.pickerView reloadAllComponents];
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
    btn.frame = CGRectMake(0 , 0, W, H - AreaViewH);
    [self addSubview:btn];
    [btn addTarget:self action:@selector(doneAction) forControlEvents:UIControlEventTouchUpInside];
    
    UIView *contentView = [[UIView alloc]initWithFrame:CGRectMake(0, H - AreaViewH, W, AreaViewH)];
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
    [contentView addSubview:pickerView];
    self.pickerView = pickerView;
    self.pickerView.dataSource = self;
    self.pickerView.delegate = self;
}

- (void)setDefaultModel:(ChzAreaSelectedDataModel *)defaultModel
{
    _defaultModel = defaultModel;
    self.array1 = self.dataSource;
    if (self.array1 && self.array1.count>defaultModel.selectedIndex1)
    {
        ChZAreaDataModel *model1 = [self.array1 objectAtIndex:defaultModel.selectedIndex1];
        self.array2 = model1.cities;
        if (self.array2 && self.array2.count>defaultModel.selectedIndex2)
        {
            ChZAreaDataModel *model2 = [self.array2 objectAtIndex:0];
            self.array3 = model2.counties;
        }
    }
    [self.pickerView selectRow:defaultModel.selectedIndex1 inComponent:0 animated:YES];
    [self.pickerView selectRow:defaultModel.selectedIndex2 inComponent:1 animated:YES];
    [self.pickerView selectRow:defaultModel.selectedIndex3 inComponent:2 animated:YES];
}

- (void)setPlaceholder:(NSString *)placeholder
{
    _placeholder = placeholder;
    if (self.placeLabel)
    {
        self.placeLabel.text = placeholder;
    }
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

- (void)doneAction
{
    NSInteger index1 = [self.pickerView selectedRowInComponent:0];
    NSInteger index2 = [self.pickerView selectedRowInComponent:1];
    NSInteger index3 = [self.pickerView selectedRowInComponent:2];
    ChZAreaDataModel *model1 = [self.array1 objectAtIndex:index1];
    ChZAreaDataModel *model2 = [self.array2 objectAtIndex:index2];
    ChZAreaDataModel *model3 = [self.array3 objectAtIndex:index3];

    NSString *area = [NSString stringWithFormat:@"%@-%@-%@",model1.areaName,model2.areaName,model3.areaName];
    if (area == nil || area.length == 0)
    {
        ChZ_MBError(@"选择地区");
        return;
    }
    ChzAreaSelectedDataModel *selectedDataModel = ChzAreaSelectedDataModel.new;
    selectedDataModel.selectedIndex1 = index1;
    selectedDataModel.selectedIndex2 = index2;
    selectedDataModel.selectedIndex3 = index3;
    selectedDataModel.selectedModel1 = model1;
    selectedDataModel.selectedModel2 = model2;
    selectedDataModel.selectedModel3 = model3;
    if (self.block) {
        self.block(selectedDataModel);
    }
    [self dismiss];
}
- (void)show
{
    CGFloat H = [UIScreen mainScreen].bounds.size.height;
    CGFloat W = [UIScreen mainScreen].bounds.size.width;
    self.contentView.frame = CGRectMake(0, H, W, AreaViewH);
    [UIView animateWithDuration:0.25f animations:^{
        self.contentView.frame = CGRectMake(0, H - AreaViewH, W, AreaViewH);
        self.backgroundColor = [UIColor colorWithRed:0 green:0 blue:0 alpha:0.2];
    }];
}
- (void)dismiss
{
    
    [UIView animateWithDuration:0.25f animations:^{
        CGFloat H = [UIScreen mainScreen].bounds.size.height;
        CGFloat W = [UIScreen mainScreen].bounds.size.width;
        self.contentView.frame = CGRectMake(0, H, W, AreaViewH);
        self.backgroundColor = [UIColor clearColor];
        
    }completion:^(BOOL finished)
     {
         [self removeFromSuperview];
         self.chzSuperView = nil;
     }];
    
}

#pragma mark UIPickerView

- (NSInteger)numberOfComponentsInPickerView:(UIPickerView *)pickerView
{
    return 3;
}


- (NSInteger)pickerView:(UIPickerView *)pickerView numberOfRowsInComponent:(NSInteger)component
{
    if (component == 0)
    {
        return self.array1.count;
    }
    if (component == 1)
    {
        return self.array2.count;
    }
    if (component == 2)
    {
        return self.array3.count;
    }
    
    return component;
}

- (nullable NSAttributedString *)pickerView:(UIPickerView *)pickerView attributedTitleForRow:(NSInteger)row forComponent:(NSInteger)component
{
    ChZAreaDataModel *model1;
    if (component == 0)
    {
        model1 = [self.array1 objectAtIndex:row];
    }
    if (component == 1)
    {
        model1 = [self.array2 objectAtIndex:row];
    }
    
    if (component == 2)
    {
        model1 = [self.array3 objectAtIndex:row];
    }
    
    NSMutableAttributedString *attString = [[NSMutableAttributedString alloc]initWithString:model1.areaName];
    [attString addAttribute:NSFontAttributeName value:[UIFont systemFontOfSize:12] range:NSMakeRange(0, model1.areaName.length)];
    return attString;
}

- (void)pickerView:(UIPickerView *)pickerView didSelectRow:(NSInteger)row inComponent:(NSInteger)component
{
    if (component == 0)
    {
        ChZAreaDataModel *model1 = [self.array1 objectAtIndex:row];
        self.array2 = model1.cities;
        
        ChZAreaDataModel *model2 = [self.array2 objectAtIndex:0];
        self.array3 = model2.counties;
        [pickerView reloadComponent:1];
        [pickerView reloadComponent:2];
    }
    if (component == 1)
    {
        ChZAreaDataModel *model2 = [self.array2 objectAtIndex:row];
        self.array3 = model2.counties;
        [pickerView reloadComponent:2];
    }
    NSInteger index1 = [pickerView selectedRowInComponent:0];
    NSInteger index2 = [pickerView selectedRowInComponent:1];
    NSInteger index3 = [pickerView selectedRowInComponent:2];
    ChZAreaDataModel *model1 = [self.array1 objectAtIndex:index1];
    ChZAreaDataModel *model2 = [self.array2 objectAtIndex:index2];
    ChZAreaDataModel *model3 = [self.array3 objectAtIndex:index3];
    
    NSString *text = [NSString stringWithFormat:@"%@ %@ %@",model1.areaName,model2.areaName,model3.areaName];
    NSLog(@"%@",text);
}


@end
