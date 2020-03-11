//
//  XTVerCode4.h
//  XTVerCode4
//
//  Created by zjwang on 2018/2/7.
//  Copyright © 2018年 summerxx.com. All rights reserved.
//
/* 一个演示单个数字验证码的输入框 4位或者6位
 初始化一个textView用来获取输入的文字
 通过Label显示输入的文字使用CAShapeLayer绘制光标
 通过光标的显示隐藏来控制光标的移动
 基础动画控制光标闪动
 通过Block回调回调输入的Value值
 
 */

#import <UIKit/UIKit.h>
typedef void(^XTVerCodeBlock)(NSString *text);
@interface XTVerCodeInput : UIView
@property (nonatomic, copy) XTVerCodeBlock verCodeBlock;
@property (nonatomic, assign) NSInteger inputType;
@property (nonatomic, assign) CGRect frame;
@property (nonatomic, assign) UIKeyboardType keyboardType;
- (void)setupSubviews;
@end
