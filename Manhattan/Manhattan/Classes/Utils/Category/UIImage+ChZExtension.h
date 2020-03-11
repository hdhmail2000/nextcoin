//
//  UIImage+ChZExtension.h
//  aliLibrary
//
//  Created by ChZ on 2017/1/13.
//  Copyright © 2017年 ChZ. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface UIImage (ChZExtension)

- (NSData *)zipNSDataWithImage;

+ (UIImage *)imageWithColor:(UIColor *)color;

- (UIImage *)scaleToWidth:(CGFloat)width;

+ (UIImage *)imageMiddleAddImage:(UIImage *)image middleImage:(UIImage *)middleImage middleImageSize:(CGSize)size;

+ (UIImage *)imageWithImage:(UIImage *)image waterImage:(UIImage *)waterImage waterImageRect:(CGRect)rect;

+ (void)imageWithURL:(NSString *)imageURL finishBlock:(void (^)(UIImage *image,NSString *error))block;

//+ (UIImage*) getVideoPreViewImage:(NSString *)path;

- (NSString *)base64String;

/**
 根据字符串生成二维码图片

 @param string 字符串
 @param Imagesize 图片宽度
 @return 二维码图片
 */
+ (UIImage *)qrImageForString:(NSString *)string imageSize:(CGFloat)Imagesize;

/**
 根据字符串生成二维码图片 加logo

 @param string 字符串
 @param Imagesize 图片宽度
 @param waterImagesize logo宽度
 @return 二维码图片
 */
+ (UIImage *)qrImageForString:(NSString *)string imageSize:(CGFloat)Imagesize logoImageSize:(CGFloat)waterImagesize;

/**
 修改二维码的颜色,把生成的二维码图片传入，再传入想要的颜色即可

 @param image 生成的二维码图片
 @param red red
 @param green green
 @param blue blue
 @return 着色的二维码
 */
+ (UIImage*)imageBlackToTransparent:(UIImage*)image withRed:(CGFloat)red andGreen:(CGFloat)green andBlue:(CGFloat)blue;
@end
