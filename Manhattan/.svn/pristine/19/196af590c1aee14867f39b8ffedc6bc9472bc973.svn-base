//
//  XHHPWInputView.h
//  Manhattan
//
//  Created by Apple on 2018/8/17.
//  Copyright © 2018年 Apple. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface XHHPWInputView : UIView

/**
 *  初始化“输入密码”视图
 *
 *  @param target 代理对象
 *
 */
+ (instancetype)initXibFileWithDelegate:(id)target;

@property (copy, nonatomic) void (^closeBlock)();

@property (copy, nonatomic) void (^finishBlock)();

// 返回第一个密码
- (NSString *)returnFirstInputViewValue;
// 返回第二个密码
- (NSString *)returnSecondInputViewValue;
// 返回第三个密码
- (NSString *)returnThirdInputViewValue;
// 返回第四个密码
- (NSString *)returnFourthInputViewValue;
// 返回第五个密码
- (NSString *)returnFifthInputViewValue;
// 返回第六个密码
- (NSString *)returnSixthInputViewValue;

/**
 *  清除所有密码输入框
 */
- (void)clearValue;

/**
 *  让第一个密码输入框成为第一响应者
 */
- (void)makeFirstInputBecomeFirstResponder;

/**
 *  判断6位密码是否输入完成
 *
 */
- (BOOL)isSixthInputFinished;

/**
 *  输入完一位密码就让下一个输入框成为响应者
 */
- (void)moveFirstResponder;

/**
 *  删除一位密码后，让上一个输入框成为响应者
 */
- (void)moveBackDeleteResponder;

@end
