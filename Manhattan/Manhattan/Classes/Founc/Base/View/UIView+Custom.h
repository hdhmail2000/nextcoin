//
//  UIView+Custom.h
//  CoinWallet
//
//  Created by Howe on 2018/5/14.
//  Copyright © 2018年 Howe. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface UIView (Custom)

- (UIViewController *)viewController;

+ (instancetype)nibLoadSelf;

@end

@interface UIView (Frame)

@property (nonatomic, assign) CGFloat chz_x;

@property (nonatomic, assign) CGFloat chz_y;

@property (nonatomic, assign) CGFloat chz_w;

@property (nonatomic, assign) CGFloat chz_h;

@property (nonatomic, assign) CGFloat chz_centerX;

@property (nonatomic, assign) CGFloat chz_centerY;

@property (nonatomic, assign) CGSize  chz_size;

// Width : Height = ratio

- (void)chz_ratioFrameWtihWidth:(CGFloat)width ratio:(CGFloat)ratio;

// ScreenWidth : Height = ratio
- (void)chz_ratioFrameScreenWidth:(CGFloat)ratio;

@end

@interface UIView (Corner)

@property (nonatomic, assign) IBInspectable CGFloat cornerRadius;

@property (nonatomic, assign) IBInspectable UIColor *borderColor;

@property (nonatomic, assign) IBInspectable CGFloat borderWidth;

@property (nonatomic, assign) IBInspectable BOOL isShadow;

@property (nonatomic, assign) IBInspectable BOOL isShadowNoOffSet;

@property (nonatomic, assign) IBInspectable BOOL isShadowNOFilletAndNoOffSet;

@end
