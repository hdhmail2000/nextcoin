//
//  UILabel+Custom.h
//  CoinWallet
//
//  Created by Howe on 2018/5/14.
//  Copyright © 2018年 Howe. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface UILabel (Custom)
+ (instancetype)chz_label:(UIFont *)font textColor:(UIColor *)color;
- (CGFloat)chz_textWith;
@end
