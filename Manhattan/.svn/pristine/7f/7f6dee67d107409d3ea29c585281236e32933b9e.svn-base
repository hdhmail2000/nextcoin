//
//  UIImageView+Custom.m
//  Utils
//
//  Created by Howe on 2018/8/4.
//  Copyright © 2018年 Howe. All rights reserved.
//

#import "UIImageView+Custom.h"
#import "UIImageView+WebCache.h"

@implementation UIImageView (Custom)
- (void)chz_requestImageWithImageId:(NSString *)imageId
{
    if (!ChZ_StringIsEmpty(imageId))
    {
        ChZ_ErrorLog(@"%@ 设置图片ID为空",self);
        return;
    }
    //    [self sd_setImageWithURL:[NSURL URLWithString:RequestImageURL(imageId) ]];
}

- (void)chz_requestImageWithLastImageURL:(NSString *)lastImageURL
{
    if (lastImageURL == nil || lastImageURL.length == 0)
    {
        ChZ_ErrorLog(@"%@ 设置图片地址为空",self);
        return;
    }
    //    [self sd_setImageWithURL:[NSURL URLWithString:RequestLastImageURL(lastImageURL)] placeholderImage:LZG_DEFAULT_IMG];
}
- (void)chz_requestImageWithImageURL:(NSString *)imageURL
{
    if (imageURL == nil || imageURL.length == 0)
    {
        ChZ_ErrorLog(@"%@ 设置图片地址为空",self);
        return;
    }
    if ([imageURL hasSuffix:@"http"] || [imageURL hasSuffix:@"https"])
    {
        ChZ_ErrorLog(@"%@ 设置图片地址错误",self);
        return;
    }
    [self sd_setImageWithURL:[NSURL URLWithString:imageURL] ];
}

//

- (void)chz_requestUserHeadImageWithImageURL:(NSString *)imageURL
{
    UIImage *image = [UIImage imageNamed:@"public_head_default"];
    if (image) self.image = image;
    if (imageURL == nil || imageURL.length == 0)
    {
        ChZ_ErrorLog(@"%@ 设置图片地址为空",self);
        return;
    }
    if ([imageURL hasSuffix:@"http"] || [imageURL hasSuffix:@"https"])
    {
        ChZ_ErrorLog(@"%@ 设置图片地址错误",self);
        return;
    }
    [self sd_setImageWithURL:[NSURL URLWithString:imageURL] placeholderImage:image];
}

- (void)chz_requestImageWithImageURL:(NSString *)imageURL placeholderImage:(UIImage *)image
{
    self.image = image;
    if (imageURL == nil || imageURL.length == 0)
    {
        ChZ_ErrorLog(@"%@ 设置图片地址为空",self);
        return;
    }
    if ([imageURL hasSuffix:@"http"] || [imageURL hasSuffix:@"https"])
    {
        ChZ_ErrorLog(@"%@ 设置图片地址错误",self);
        return;
    }
    [self sd_setImageWithURL:[NSURL URLWithString:imageURL] placeholderImage:image];
    //    [self sd_setImageWithURL:[NSURL URLWithString:imageURL] completed:^(UIImage * _Nullable image, NSError * _Nullable error, SDImageCacheType cacheType, NSURL * _Nullable imageURL) {
    //
    //    }];
}

- (void)chz_requestImageWithImageURL:(NSString *)imageURL completed:(SDExternalCompletionBlock)completedBlock
{
    if (imageURL == nil || imageURL.length == 0)
    {
        ChZ_ErrorLog(@"%@ 设置图片地址为空",self);
        return;
    }
    if ([imageURL hasSuffix:@"http"] || [imageURL hasSuffix:@"https"])
    {
        ChZ_ErrorLog(@"%@ 设置图片地址错误",self);
        return;
    }
    [self sd_setImageWithURL:[NSURL URLWithString:imageURL] completed:^(UIImage * _Nullable image, NSError * _Nullable error, SDImageCacheType cacheType, NSURL * _Nullable imageURL)
     {
         if (completedBlock)
         {
             completedBlock(image,error,cacheType,imageURL);
         }
     }];
}
@end
