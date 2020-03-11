//
//  XTTMineRequest.m
//  FuturePurse
//
//  Created by muye01 on 2018/8/30.
//  Copyright © 2018年 jbtm. All rights reserved.
//

#import "XTTMineRequest.h"
#import "XTTMineModel.h"

@implementation XTTMineRequest
+(void)requestMineHelpList:(ChZ_SuccessBlock)successBlock
                     errorBlock:(ChZ_ErrorBlock)errorBlock{
    NSMutableString *url = [NSMutableString string];
    [url appendString:RequestURL(kURL_Mine_apphelp)];
    url = [[url stringByAddingPercentEncodingWithAllowedCharacters:[NSCharacterSet URLQueryAllowedCharacterSet]] mutableCopy];
    
    [self PostWithURL: url parameters: nil progress: ^(NSProgress * _Nonnull uploadProgress) {
        
    }  success:^(NSURLSessionDataTask * _Nonnull task, id  _Nullable responseObject)
     {
         NSDictionary *responseDic = responseObject;
         if ([responseDic isKindOfClass:[NSNull class]])
         {
             if (errorBlock) errorBlock(@"请求失败"); return;
         }
         int code = [[responseDic objectForKey:@"code"]intValue];
         NSString *msg = [responseDic objectForKey:@"msg"];
         if (code != kv_PHPRequestSuccess)
         {
             if (ChZ_StringIsEmpty(msg))
             {
                 if (errorBlock) errorBlock(@"请求失败"); return;
             }
             if (errorBlock) errorBlock(@"请求失败"); return;
         }
        NSArray *data = [responseDic objectForKey:@"return"];
        if ([data isKindOfClass:[NSNull class]] || data == nil || data.count == 0)
            {
            if (successBlock)successBlock(nil);return;
            }
        NSArray *dataArray = [XTTMineAppHelpListModel mj_objectArrayWithKeyValuesArray:data];
         if (successBlock)successBlock(dataArray);
         return;
         
     } failure:^(NSURLSessionDataTask * _Nullable task, NSError * _Nonnull error)
     {
         if (errorBlock) errorBlock(@"请求失败"); return;
     }];
}
+(void)requestMineHelpDetail:(NSString *)catid
                successBlock:(ChZ_SuccessBlock)successBlock
                  errorBlock:(ChZ_ErrorBlock)errorBlock{
    NSMutableString *url = [NSMutableString string];
    [url appendString:RequestURL(kURL_Mine_apphelpDetail)];
    if (ChZ_StringIsEmpty(catid))[url appendFormat:@"&catid=%@",catid];
    url = [[url stringByAddingPercentEncodingWithAllowedCharacters:[NSCharacterSet URLQueryAllowedCharacterSet]] mutableCopy];

    [self GetWithURL: url parameters: nil progress: ^(NSProgress * _Nonnull uploadProgress) {
        
    }  success:^(NSURLSessionDataTask * _Nonnull task, id  _Nullable responseObject)
     {
         NSDictionary *responseDic = responseObject;
         if ([responseDic isKindOfClass:[NSNull class]])
         {
             if (errorBlock) errorBlock(@"请求失败"); return;
         }
         int code = [[responseDic objectForKey:@"code"]intValue];
         NSString *msg = [responseDic objectForKey:@"msg"];
         if (code != kv_PHPRequestSuccess)
         {
             if (ChZ_StringIsEmpty(msg))
             {
                 if (errorBlock) errorBlock(@"请求失败"); return;
             }
             if (errorBlock) errorBlock(@"请求失败"); return;
         }
         NSArray *data = [responseDic objectForKey:@"return"];
         if ([data isKindOfClass:[NSNull class]] || data == nil || data.count == 0)
         {
             if (successBlock)successBlock(nil);return;
         }
         NSArray *dataArray = [XTTMineAppHelpDetailModel mj_objectArrayWithKeyValuesArray:data];
         if (successBlock)successBlock(dataArray);
         return;
         
     } failure:^(NSURLSessionDataTask * _Nullable task, NSError * _Nonnull error)
     {
         if (errorBlock) errorBlock(@"请求失败"); return;
     }];
}
+(void)requestMineMyTeam:(NSString *)fid
                      successBlock:(ChZ_SuccessBlock)successBlock
                        errorBlock:(ChZ_ErrorBlock)errorBlock{
    NSMutableString *url = [NSMutableString string];
    [url appendString:RequestURL(kURL_Mine_MyTeam)];
    if (ChZ_StringIsEmpty(fid))[url appendFormat:@"&fid=%@",fid];
    url = [[url stringByAddingPercentEncodingWithAllowedCharacters:[NSCharacterSet URLQueryAllowedCharacterSet]] mutableCopy];
    
    [self GetWithURL: url parameters: nil progress: ^(NSProgress * _Nonnull uploadProgress) {
        
    }  success:^(NSURLSessionDataTask * _Nonnull task, id  _Nullable responseObject)
     {
         NSDictionary *responseDic = responseObject;
         if ([responseDic isKindOfClass:[NSNull class]])
         {
             if (errorBlock) errorBlock(@"请求失败"); return;
         }
         int code = [[responseDic objectForKey:@"code"]intValue];
         NSString *msg = [responseDic objectForKey:@"msg"];
         if (code != kv_PHPRequestSuccess)
         {
             if (ChZ_StringIsEmpty(msg))
             {
                 if (errorBlock) errorBlock(@"请求失败"); return;
             }
             if (errorBlock) errorBlock(@"请求失败"); return;
         }
         NSDictionary *data = [responseDic objectForKey:@"return"];
         if ([data isKindOfClass:[NSNull class]] || data == nil || data.count == 0)
         {
             if (successBlock)successBlock(nil);return;
         }
         XTTMineTeamModel *model = [XTTMineTeamModel mj_objectWithKeyValues:data];
         if (successBlock)successBlock(model);
         return;
         
     } failure:^(NSURLSessionDataTask * _Nullable task, NSError * _Nonnull error)
     {
         if (errorBlock) errorBlock(@"请求失败"); return;
     }];
}
@end
