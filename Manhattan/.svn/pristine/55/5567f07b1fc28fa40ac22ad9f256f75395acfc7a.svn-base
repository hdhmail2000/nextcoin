//
//  ChZAreaPikerView.h
//  hotcoin
//
//  Created by Howe on 2018/4/20.
//  Copyright © 2018年 Howe. All rights reserved.
//

#import <UIKit/UIKit.h>



@interface ChZAreaDataModel : NSObject

@property (nonatomic, copy) NSString  *areaName;

@property (nonatomic, copy) NSString  *areaId;

@property (nonatomic, assign) BOOL  isSelected;

@property (nonatomic, strong) NSArray<ChZAreaDataModel *>  *cities;

@property (nonatomic, strong) NSArray<ChZAreaDataModel *>  *counties;

@end

@interface ChzAreaSelectedDataModel : NSObject

@property (nonatomic, strong) ChZAreaDataModel  *selectedModel1;

@property (nonatomic, strong) ChZAreaDataModel  *selectedModel2;

@property (nonatomic, strong) ChZAreaDataModel  *selectedModel3;

@property (nonatomic, assign) NSInteger  selectedIndex1;

@property (nonatomic, assign) NSInteger  selectedIndex2;

@property (nonatomic, assign) NSInteger  selectedIndex3;

@end

typedef void(^AreaPickerDoneBlock) (ChzAreaSelectedDataModel *selectedModel);

@interface ChZAreaPikerView : UIView

/**
 只需要设置 selectedIndex... 即可
 */
@property (nonatomic, strong) ChzAreaSelectedDataModel  *defaultModel;

@property (nonatomic, strong) UIColor *buttonTextColor;

@property (nonatomic, strong) UIColor *finishButtonTextColor;

@property (nonatomic, strong) UIColor *cancelButtonTextColor;

@property (nonatomic, copy) NSString  *placeholder;

@property (nonatomic, copy) AreaPickerDoneBlock  block;

- (void)show;

- (void)dismiss;
@end
