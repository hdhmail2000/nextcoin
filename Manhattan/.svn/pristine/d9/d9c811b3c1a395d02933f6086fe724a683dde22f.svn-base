//
//  UIView+Custom.m
//  CoinWallet
//
//  Created by Howe on 2018/5/14.
//  Copyright © 2018年 Howe. All rights reserved.
//

#import "UIView+Custom.h"

@implementation UIView (Custom)

- (UIViewController *)viewController
{
    for (UIView * next = [self superview]; next; next = next.superview)
    {
        UIResponder * nextResponder = [next nextResponder];
        if ([nextResponder isKindOfClass:[UIViewController class]])
        {
            return (UIViewController *)nextResponder;
        }
    }
    return nil;
}

+ (instancetype)nibLoadSelf
{
    NSString *className = NSStringFromClass([self class]);
    NSArray *viewArray = [[NSBundle mainBundle]loadNibNamed:className owner:nil options:nil];
    if (viewArray && viewArray.count >0)
    {
        return [viewArray lastObject];
    }
    return nil;
}

@end

@implementation UIView (Frame)

- (void)setChz_x:(CGFloat)chz_x
{
    CGRect frame = self.frame;
    frame.origin.x = chz_x;
    self.frame = frame;
}

- (CGFloat)chz_x
{
    return self.frame.origin.x;
}

- (void)setChz_y:(CGFloat)chz_y
{
    CGRect frame = self.frame;
    frame.origin.y = chz_y;
    self.frame = frame;
}

- (CGFloat)chz_y
{
    return self.frame.origin.y;
}

- (void)setChz_w:(CGFloat)chz_w
{
    CGRect frame = self.frame;
    frame.size.width = chz_w;
    self.frame = frame;
    
}

- (CGFloat)chz_w
{
    return self.frame.size.width;
}

- (void)setChz_h:(CGFloat)chz_h
{
    CGRect frame = self.frame;
    frame.size.height = chz_h;
    self.frame = frame;
}

- (CGFloat)chz_h
{
    return self.frame.size.height;
}

- (void)setChz_centerX:(CGFloat)chz_centerX
{
    CGPoint center = self.center;
    center.x = chz_centerX;
    self.center = center;
}

- (CGFloat)chz_centerX
{
    return self.center.x;
}

- (void)setChz_centerY:(CGFloat)chz_centerY
{
    CGPoint center = self.center;
    center.y = chz_centerY;
    self.center = center;
}

- (CGFloat)chz_centerY
{
    return self.center.y;
}

- (void)setChz_size:(CGSize)chz_size
{
    CGRect frame = self.frame;
    frame.size = chz_size;
    self.frame = frame;
}

- (CGSize)chz_size
{
    return self.frame.size;
}

- (void)chz_ratioFrameWtihWidth:(CGFloat)width ratio:(CGFloat)ratio
{
    self.chz_w = width;
    self.chz_h = width/ratio;
}

- (void)chz_ratioFrameScreenWidth:(CGFloat)ratio
{
    self.chz_w = [UIScreen mainScreen].bounds.size.width;
    self.chz_h = [UIScreen mainScreen].bounds.size.width/ratio;
}


@end


@implementation UIView (Corner)

- (CGFloat)cornerRadius {
    return self.layer.cornerRadius;
}

- (void)setCornerRadius:(CGFloat)cornerRadius {
    self.layer.cornerRadius = cornerRadius;
    self.layer.masksToBounds = YES;
}

- (UIColor *)borderColor {
    return [UIColor colorWithCGColor:self.layer.borderColor];
}

- (void)setBorderColor:(UIColor *)borderColor {
    self.layer.borderColor = borderColor.CGColor;
}

- (CGFloat)borderWidth {
    return self.layer.borderWidth;
}

- (void)setBorderWidth:(CGFloat)borderWidth {
    self.layer.borderWidth = borderWidth;
}
- (void)setIsShadow:(BOOL)isShadow
{
    self.layer.cornerRadius = 5;
    self.layer.shadowColor = [UIColor blackColor].CGColor;
    self.layer.shadowOpacity = 0.1f;
    self.layer.shadowRadius = 2;
    self.layer.shadowOffset = CGSizeMake(0, 1);
    if (@available(iOS 11.0, *))
    {
        self.layer.maskedCorners = kCALayerMinXMinYCorner|kCALayerMaxXMinYCorner|kCALayerMinXMaxYCorner | kCALayerMaxXMaxYCorner;
    }
}
-(void)setIsShadowNoOffSet:(BOOL)isShadowNoOffSet
{
    self.layer.cornerRadius = 5;
    self.layer.shadowColor = [UIColor blackColor].CGColor;
    self.layer.shadowOpacity = 0.1f;
    self.layer.shadowRadius = 2;
    self.layer.shadowOffset = CGSizeMake(0, 0);
    if (@available(iOS 11.0, *))
    {
        self.layer.maskedCorners = kCALayerMinXMinYCorner|kCALayerMaxXMinYCorner|kCALayerMinXMaxYCorner | kCALayerMaxXMaxYCorner;
    }
}

-(void)setIsShadowNOFilletAndNoOffSet:(BOOL)isShadowNoOffSet
{
    self.layer.shadowColor = [UIColor blackColor].CGColor;
    self.layer.shadowOpacity = 0.1f;
    self.layer.shadowRadius = 2;
    self.layer.shadowOffset = CGSizeMake(0, 0);
    if (@available(iOS 11.0, *))
    {
        self.layer.maskedCorners = kCALayerMinXMinYCorner|kCALayerMaxXMinYCorner|kCALayerMinXMaxYCorner | kCALayerMaxXMaxYCorner;
    }
}
- (BOOL)isShadowNOFilletAndNoOffSet
{
    if (self.layer.shadowColor != nil)
    {
        return YES;
    }
    return NO;
}
- (BOOL)isShadowNoOffSet
{
    if (self.layer.shadowColor != nil)
    {
        return YES;
    }
    return NO;
}
- (BOOL)isShadow
{
    if (self.layer.shadowColor != nil)
    {
        return YES;
    }
    return NO;
}

@end

