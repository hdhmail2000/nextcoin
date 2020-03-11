//
//  UILabel+Custom.m
//  CoinWallet
//
//  Created by Howe on 2018/5/14.
//  Copyright © 2018年 Howe. All rights reserved.
//

#import "UILabel+Custom.h"

@implementation UILabel (Custom)
+ (instancetype)chz_label:(UIFont *)font textColor:(UIColor *)color
{
    UILabel *label = [[self alloc] init];
    label.font = font;
    label.textColor = color;
    label.autoresizingMask = UIViewAutoresizingFlexibleWidth;
    label.textAlignment = NSTextAlignmentCenter;
    label.backgroundColor = [UIColor clearColor];
    return label;
}

- (CGFloat)mj_textWith {
    CGFloat stringWidth = 0;
    CGSize size = CGSizeMake(MAXFLOAT, MAXFLOAT);
    if (self.text.length > 0) {
#if defined(__IPHONE_OS_VERSION_MAX_ALLOWED) && __IPHONE_OS_VERSION_MAX_ALLOWED >= 70000
        stringWidth =[self.text
                      boundingRectWithSize:size
                      options:NSStringDrawingUsesLineFragmentOrigin
                      attributes:@{NSFontAttributeName:self.font}
                      context:nil].size.width;
#else
        
        stringWidth = [self.text sizeWithFont:self.font
                            constrainedToSize:size
                                lineBreakMode:NSLineBreakByCharWrapping].width;
#endif
    }
    return stringWidth;
}
@end
