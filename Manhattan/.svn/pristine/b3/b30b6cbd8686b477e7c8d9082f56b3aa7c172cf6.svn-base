//
//  TLAliyunOSS.h
//  tonglian
//
//  Created by Howe on 2018/6/29.
//  Copyright © 2018年 Howe. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <AliyunOSSiOS/OSSService.h>

typedef void (^UpdateLoadBlock) (NSError *error ,NSString *filename);

@interface TLAliyunOSS : NSObject

SingletonH(TLAliyunOSS)


/**
 上传文件

 @param data  文件数据
 @param suffix 后缀名 如 jpg png mp4
 @param progressBlock 进度回调
 @param callBlock 上传完成回调
 */
- (void)updateData:(NSData *)data
      suffixString:(NSString *)suffix
     progressBlock:(OSSNetworkingUploadProgressBlock)progressBlock
         callBlock:(UpdateLoadBlock)callBlock;

@end
