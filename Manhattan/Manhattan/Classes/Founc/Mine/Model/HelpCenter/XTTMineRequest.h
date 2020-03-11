//
//  XTTMineRequest.h
//  FuturePurse
//
//  Created by muye01 on 2018/8/30.
//  Copyright © 2018年 jbtm. All rights reserved.
//

#import "ChZBaseHttpRequest.h"

@interface XTTMineRequest : ChZBaseHttpRequest

/**
 帮助中心

 @param successBlock successBlock
 @param errorBlock errorBlock
 */
+(void)requestMineHelpList:(ChZ_SuccessBlock)successBlock
            errorBlock:(ChZ_ErrorBlock)errorBlock;

/**
 列表（详情）页

 @param catid 前一个接口id
 @param successBlock successBlock
 @param errorBlock errorBlock
 */
+(void)requestMineHelpDetail:(NSString *)catid
                successBlock:(ChZ_SuccessBlock)successBlock
                  errorBlock:(ChZ_ErrorBlock)errorBlock;

/**
 团队

 @param fid 用户fid
 @param successBlock successBlock
 @param errorBlock successBlock
 */
+(void)requestMineMyTeam:(NSString *)fid
            successBlock:(ChZ_SuccessBlock)successBlock
              errorBlock:(ChZ_ErrorBlock)errorBlock;
@end
