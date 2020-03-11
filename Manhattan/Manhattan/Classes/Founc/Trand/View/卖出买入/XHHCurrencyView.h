//
//  XHHCurrencyView.h
//  Manhattan
//
//  Created by Apple on 2018/8/20.
//  Copyright © 2018年 Apple. All rights reserved.
//

#import <UIKit/UIKit.h>
@class XHHC2CCnyListModel;

@interface XHHCurrencyView : UIView

@property (nonatomic , strong) NSArray *cnyList;

@property (copy, nonatomic) void (^selectCnyBlock)(XHHC2CCnyListModel *cnyModel);

-(void)showInView:(UIView *)view;

-(void)dismiss;
@end
