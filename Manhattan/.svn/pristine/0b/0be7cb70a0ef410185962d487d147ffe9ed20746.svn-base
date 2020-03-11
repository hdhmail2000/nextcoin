//
//  ChZBaseHttpRequest.h
//  zubu
//
//  Created by ChZ on 2016/10/20.
//  Copyright © 2016年 ChZ. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "AFNetworking.h"

NS_ASSUME_NONNULL_BEGIN
@interface ChZBaseHttpRequest : NSObject
+ (void)PostAuthWithURL:(nullable NSString *)url
             parameters:(nullable NSDictionary * )parameters
               progress:(nullable void (^) (NSProgress *_Nonnull uploadProgress) )progress
                success:(nullable void (^)(NSURLSessionDataTask * _Nonnull task, id  _Nullable responseObject))success
                failure:(nullable void (^)(NSURLSessionDataTask * _Nullable task, NSError * _Nonnull error))failure;
/**
 *  POST请求
 *
 *  @param url        请求地址
 *  @param parameters 请求参数
 *  @param progress   上传进度
 *  @param success    成功Block
 *  @param failure    失败Block
 */
+ (void)PostWithURL:(NSString *)url
         parameters:(nullable NSDictionary *  )parameters
           progress:(nullable void (^) (NSProgress *_Nonnull uploadProgress) )progress
            success:(nullable void (^)(NSURLSessionDataTask * _Nonnull task, id  _Nullable responseObject))success
            failure:(nullable void (^)(NSURLSessionDataTask * _Nullable task, NSError * _Nonnull error))failure;
+ (void)PosPHPtWithURL:(NSString *)url
         parameters:(nullable NSDictionary *  )parameters
           progress:(nullable void (^) (NSProgress *_Nonnull uploadProgress) )progress
            success:(nullable void (^)(NSURLSessionDataTask * _Nonnull task, id  _Nullable responseObject))success
            failure:(nullable void (^)(NSURLSessionDataTask * _Nullable task, NSError * _Nonnull error))failure;


/**
 *  Get请求
 *
 *  @param url        请求地址
 *  @param parameters 请求参数
 *  @param progress   下载进度
 *  @param success    成功Block
 *  @param failure    失败Block
 */
+ (void)GetWithURL:(NSString *)url
        parameters:(nullable NSDictionary *  )parameters
          progress:(nullable void (^) (NSProgress *_Nonnull downloadProgress) )progress
           success:(nullable void (^)(NSURLSessionDataTask * _Nonnull task, id  _Nullable responseObject))success
           failure:(nullable void (^)(NSURLSessionDataTask * _Nullable task, NSError * _Nonnull error))failure;
+ (void)GetPHPWithURL:(NSString *)url
        parameters:(nullable NSDictionary *  )parameters
          progress:(nullable void (^) (NSProgress *_Nonnull downloadProgress) )progress
           success:(nullable void (^)(NSURLSessionDataTask * _Nonnull task, id  _Nullable responseObject))success
           failure:(nullable void (^)(NSURLSessionDataTask * _Nullable task, NSError * _Nonnull error))failure;

+ (void)Post2WithURL:(NSString *)url parameters:(NSDictionary * )parameters progress:(void (^) (NSProgress *_Nonnull uploadProgress) )progress success:(void (^)(NSURLSessionDataTask * _Nonnull task, id  _Nullable responseObject))success failure:(void (^)(NSURLSessionDataTask * _Nullable task, NSError * _Nonnull error))failure;

+ (void)XHHuploadImageWithURL:(NSString *)url
                       images:(NSArray *)images
                   parameters:(nullable NSDictionary *  )parameters
                     progress:(nullable void (^) (NSProgress *_Nonnull downloadProgress) )progress
                      success:(nullable void (^)(NSURLSessionDataTask * _Nonnull task, id  _Nullable responseObject))success
                      failure:(nullable void (^)(NSURLSessionDataTask * _Nullable task, NSError * _Nonnull error))failure;

@end
NS_ASSUME_NONNULL_END
