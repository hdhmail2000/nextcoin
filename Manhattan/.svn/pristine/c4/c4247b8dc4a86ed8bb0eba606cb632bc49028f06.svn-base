//
//  UIImage+Custom.h
//  Utils
//
//  Created by Howe on 2018/8/4.
//  Copyright © 2018年 Howe. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface UIImage (Custom)

+ (UIImage *)imageWithColor:(UIColor *)color;

+ (UIImage *)imageWithColor:(UIColor *)color size:(CGSize)size;

- (UIImage *)scaleToWidth:(CGFloat)width;

+ (UIImage *)imageMiddleAddImage:(UIImage *)image middleImage:(UIImage *)middleImage middleImageSize:(CGSize)size;

+ (UIImage *)imageWithImage:(UIImage *)image waterImage:(UIImage *)waterImage waterImageRect:(CGRect)rect;

+ (void)imageWithURL:(NSString *)imageURL finishBlock:(void (^)(UIImage *image,NSString *error))block;

+ (UIImage*) getVideoPreViewImage:(NSString *)path;

@end
