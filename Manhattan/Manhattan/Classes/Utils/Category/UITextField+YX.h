//
//  UITextField+YX.h
//  Manhattan
//
//  Created by Apple on 2018/8/16.
//  Copyright © 2018年 Apple. All rights reserved.
//

#import <UIKit/UIKit.h>
@protocol YXTextFieldDelegate <UITextFieldDelegate>
@optional
- (void)textFieldDidDeleteBackward:(UITextField *)textField;
@end
@interface UITextField (YX)
@property (weak, nonatomic) id<YXTextFieldDelegate> delegate;
@end
/**
 *  监听删除按钮
 *  object:UITextField
 */
extern NSString * const YXTextFieldDidDeleteBackwardNotification;
