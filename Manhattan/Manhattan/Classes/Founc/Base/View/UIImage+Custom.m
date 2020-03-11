//
//  UIImage+Custom.m
//  Utils
//
//  Created by Howe on 2018/8/4.
//  Copyright © 2018年 Howe. All rights reserved.
//

#import "UIImage+Custom.h"
#import <AVKit/AVKit.h>
@implementation UIImage (Custom)

+ (UIImage *)imageWithColor:(UIColor *)color size:(CGSize)size
{
    UIGraphicsBeginImageContextWithOptions(size, NO, 0.0);
    [color set];
    UIRectFill(CGRectMake(0, 0, size.width, size.height));
    UIImage *image = UIGraphicsGetImageFromCurrentImageContext();
    UIGraphicsEndImageContext();
    return image;
}

+ (UIImage *)imageWithColor:(UIColor *)color
{
    return  [self imageWithColor:color size:CGSizeMake(10, 10)];
}

- (UIImage *)scaleToWidth:(CGFloat)width
{
    if (width > self.size.width)
    {
        return self;
    }
    CGFloat height = (width / self.size.width) * self.size.height;
    CGRect rect = CGRectMake(0, 0, width, height);
    UIGraphicsBeginImageContext(rect.size);
    [self drawInRect:rect];
    UIImage * image = UIGraphicsGetImageFromCurrentImageContext();
    UIGraphicsEndImageContext();
    return image;
    
}

+ (UIImage *)imageMiddleAddImage:(UIImage *)image middleImage:(UIImage *)middleImage middleImageSize:(CGSize)size
{
    CGFloat imageX = (image.size.width - size.width) * 0.5f;
    CGFloat imageY = (image.size.height - size.height) * 0.5f;
    CGRect rect = CGRectMake(imageX, imageY, size.width, size.height);
    return [self imageWithImage:image waterImage:middleImage waterImageRect:rect];
}

+ (UIImage *)imageWithImage:(UIImage *)image waterImage:(UIImage *)waterImage waterImageRect:(CGRect)rect
{
    
    //1.获取图片
    
    //2.开启上下文
    UIGraphicsBeginImageContextWithOptions(image.size, NO, 0);
    //3.绘制背景图片
    [image drawInRect:CGRectMake(0, 0, image.size.width, image.size.height)];
    //绘制水印图片到当前上下文
    [waterImage drawInRect:rect];
    //4.从上下文中获取新图片
    UIImage * newImage = UIGraphicsGetImageFromCurrentImageContext();
    //5.关闭图形上下文
    UIGraphicsEndImageContext();
    //返回图片
    return newImage;
}

+ (void)imageWithURL:(NSString *)imageURL finishBlock:(void (^)(UIImage *image,NSString *error))block
{
    if (imageURL == nil || imageURL.length == 0)
    {
        if (block)
        {
            block(nil,@"图片地址为空");
        }
        return;
    }
    if ([imageURL hasSuffix:@"http"] || [imageURL hasSuffix:@"https"])
    {
        if (block)
        {
            block(nil,@"图片地址不正确");
        }
        return;
    }
    NSURLSession *session = [NSURLSession sharedSession];
    NSURLRequest *request = [NSURLRequest requestWithURL:[NSURL URLWithString:imageURL]];
    NSURLSessionDataTask *dataTask = [session dataTaskWithRequest:request completionHandler:^(NSData * _Nullable data, NSURLResponse * _Nullable response, NSError * _Nullable error)
                                      {
                                          if (data != nil && data.length != 0)
                                          {
                                              UIImage *image = [UIImage imageWithData:data];
                                              if (block)
                                              {
                                                  block(image,nil);
                                              }
                                              return;
                                          }else
                                          {
                                              if (block)
                                              {
                                                  block(nil,@"图片请求失败");
                                              }
                                              return;
                                          }
                                      }];
    [dataTask resume];
}



+ (UIImage*) getVideoPreViewImage:(NSString *)path
{
    if (!ChZ_StringIsEmpty(path)) {
        return nil;
    }
    
    NSURL *url;
    if ([path hasPrefix:@"http"])
    {
        url = [NSURL URLWithString:path];
    }else
    {
        url = [NSURL fileURLWithPath:path];
    }
    AVURLAsset *asset = [[AVURLAsset alloc] initWithURL:url options:nil];
    AVAssetImageGenerator *assetGen = [[AVAssetImageGenerator alloc] initWithAsset:asset];
    
    assetGen.appliesPreferredTrackTransform = YES;
    CMTime time = CMTimeMakeWithSeconds(0.0, 600);
    NSError *error = nil;
    CMTime actualTime;
    CGImageRef image = [assetGen copyCGImageAtTime:time actualTime:&actualTime error:&error];
    UIImage *videoImage = [[UIImage alloc] initWithCGImage:image];
    CGImageRelease(image);
    return videoImage;
}

@end
