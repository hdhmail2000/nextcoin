//
//  UITextField+Custom.m
//  CoinWallet
//
//  Created by Howe on 2018/5/14.
//  Copyright © 2018年 Howe. All rights reserved.
//

#import "UITextField+Custom.h"

@implementation UITextField (Custom)

- (void)configPlaceholder:(NSString *)placeholder fontName:(NSString *)fontName fontSize:(CGFloat)fontSize textColor:(UIColor *)textColor
{
    if (!ChZ_StringIsEmpty(placeholder))return;
    if (!ChZ_StringIsEmpty(fontName))return;
    if (textColor == nil) return;
    UIFont *font = [UIFont fontWithName:fontName size:fontSize];
    NSMutableAttributedString *attString = [[NSMutableAttributedString alloc]initWithString:placeholder];
    [attString addAttribute:NSFontAttributeName value:font range:NSMakeRange(0, placeholder.length)];
    [attString addAttribute:NSForegroundColorAttributeName value:textColor range:NSMakeRange(0, placeholder.length)];
    self.attributedPlaceholder = attString;
}
- (void)configPlaceholderWithFontName:(NSString *)fontName fontSize:(CGFloat)fontSize textColor:(UIColor *)textColor
{
    if (!ChZ_StringIsEmpty(self.placeholder))return;
    if (!ChZ_StringIsEmpty(fontName))return;
    if (textColor == nil) return;
    UIFont *font = [UIFont fontWithName:fontName size:fontSize];
    NSMutableAttributedString *attString = [[NSMutableAttributedString alloc]initWithString:self.placeholder];
    [attString addAttribute:NSFontAttributeName value:font range:NSMakeRange(0, self.placeholder.length)];
    [attString addAttribute:NSForegroundColorAttributeName value:textColor range:NSMakeRange(0, self.placeholder.length)];
    self.attributedPlaceholder = attString;
}
- (void)configPlaceholderWithFont:(UIFont *)font textColor:(UIColor *)textColor
{
    if (!ChZ_StringIsEmpty(self.placeholder))return;
    if (font == nil)return;
    if (textColor == nil) return;
    NSMutableAttributedString *attString = [[NSMutableAttributedString alloc]initWithString:self.placeholder];
    [attString addAttribute:NSFontAttributeName value:font range:NSMakeRange(0, self.placeholder.length)];
    [attString addAttribute:NSForegroundColorAttributeName value:textColor range:NSMakeRange(0, self.placeholder.length)];
    self.attributedPlaceholder = attString;
}

@end
