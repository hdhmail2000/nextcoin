//
//  UIButton+ImageTitleSpacing.h
//  CustomTool_Demo
//
//  Created by Legso on 2017/3/30.
//  Copyright © 2017年 Arthur. All rights reserved.
//

#import <UIKit/UIKit.h>

typedef NS_ENUM(NSUInteger,LHButtonEdgeInsetsStyle)
{
    LHButtonEdgeInsetsStyleTop, //image在上，label在下
    LHButtonEdgeInsetsStyleLeft, //image在左，label在右
    LHButtonEdgeInsetsStyleBottom, //image在下，label在上
    LHButtonEdgeInsetsStyleRight //image在右，label在左
};

@interface UIButton (ImageTitleSpacing)

/** 设置button的titleLabel和imageView的布局样式和间距
 *
 * @param style titleLabel和imageView的布局样式
 * @param space titleLabel和imageView的间距
 */
- (void)layoutButtonWithEdgeInsetsStyle:(LHButtonEdgeInsetsStyle)style imageTitleSpace:(CGFloat)space;


/*
 *    倒计时按钮
 *    @param timeLine  倒计时总时间
 *    @param title     还没倒计时的title
 *    @param subTitle  倒计时的子名字 如：时、分
 *    @param mColor    还没倒计时的颜色
 *    @param color     倒计时的颜色
 */

- (void)startWithTime:(NSInteger)timeLine title:(NSString *)title countDownTitle:(NSString *)subTitle mainColor:(UIColor *)mColor countColor:(UIColor *)color;
@end
