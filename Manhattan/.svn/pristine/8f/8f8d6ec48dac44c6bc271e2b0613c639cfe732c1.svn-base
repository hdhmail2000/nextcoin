//
//  ChZAccountRequest.m
//  FuturePurse
//
//  Created by Howe on 2018/9/4.
//  Copyright © 2018年 jbtm. All rights reserved.
//

#import "ChZAccountRequest.h"
#import "ChZUserPayAccountModel.h"

@implementation ChZAccountRequest
+ (void)requestPayAccountListSuccessBlock:(ChZ_SuccessBlock)successBlock
                               errorBlock:(ChZ_ErrorCodeBlock)errorBlock
{
    NSMutableString *url = [NSMutableString string];
    [url appendString:RequestURL(kURL_useraccount)];
    
    NSString *uid = [APPControl sharedAPPControl].user.fid;
    NSMutableDictionary *parameters = [NSMutableDictionary dictionary];
    if (ChZ_StringIsEmpty(uid))[parameters setObject:uid forKey:@"uid"];
//    NSMutableDictionary *parameters = [NSMutableDictionary dictionary];
//    if (ChZ_StringIsEmpty(uid))[url appendFormat:@"&uid=%@",uid];
//    if (ChZ_StringIsEmpty(status))[url appendFormat:@"&status=%@",status];
//    [url appendFormat:@"&page=%d",page];
    NSString *newUrl = [url stringByAddingPercentEncodingWithAllowedCharacters:[NSCharacterSet URLQueryAllowedCharacterSet]];
    [self PostWithURL:newUrl parameters:parameters progress:nil success:^(NSURLSessionDataTask * _Nonnull task, id  _Nullable responseObject)
     {
         NSDictionary *responseDic = responseObject;
         if ([responseDic isKindOfClass:[NSNull class]])
         {
             if (errorBlock) errorBlock(0,@"请求失败"); return;
         }
         int code = [[responseDic objectForKey:@"code"]intValue];
         NSString *msg = [responseDic objectForKey:@"msg"];
         if (code != kv_PHPRequestSuccess)
         {
             if (ChZ_StringIsEmpty(msg))
             {
                 if (errorBlock) errorBlock(code,msg); return;
             }
             if (errorBlock) errorBlock(code,@"请求失败"); return;
         }
//         NSArray *data = [responseDic objectForKey:@"return"];
//         NSArray *objArray = [ChZUserPayAccountModel mj_objectArrayWithKeyValuesArray:data];
//         if (successBlock) successBlock(objArray);
         NSDictionary *data = [responseDic objectForKey:@"return"];
         ChZUserPayAccountModel *model = [ChZUserPayAccountModel mj_objectWithKeyValues:data];
         if (successBlock) successBlock(model);
         return;
     } failure:^(NSURLSessionDataTask * _Nullable task, NSError * _Nonnull error)
     {
         if (errorBlock) errorBlock(0,@"请求失败"); return;
     }];
}


+ (void)requestSubmitBankPayAccountList:(NSString *)name
                               cardname:(NSString *)cardNumber
                             bankNumber:(NSString *)bankNumber
                               bankName:(NSString *)bankName
                                  phone:(NSString *)phone
                           successBlock:(ChZ_SuccessBlock)successBlock
                             errorBlock:(ChZ_ErrorCodeBlock)errorBlock
{
    NSMutableString *url = [NSMutableString string];
    [url appendString:RequestURL(kURL_submitbankinfo)];
    NSString *uid = [APPControl sharedAPPControl].user.fid;
    NSMutableDictionary *parameters = [NSMutableDictionary dictionary];
    if (ChZ_StringIsEmpty(uid))[parameters setObject:uid forKey:@"uid"];
    if (ChZ_StringIsEmpty(name))[parameters setObject:name forKey:@"data[khm]"];
    if (ChZ_StringIsEmpty(cardNumber))[parameters setObject:cardNumber forKey:@"data[idCard]"];
    if (ChZ_StringIsEmpty(bankNumber))[parameters setObject:bankNumber forKey:@"data[zhhm]"];
    if (ChZ_StringIsEmpty(bankName))[parameters setObject:bankName forKey:@"data[khh]"];
    if (ChZ_StringIsEmpty(phone))[parameters setObject:phone forKey:@"data[phone]"];
    //    NSMutableDictionary *parameters = [NSMutableDictionary dictionary];
    //    if (ChZ_StringIsEmpty(uid))[url appendFormat:@"&uid=%@",uid];
    //    if (ChZ_StringIsEmpty(status))[url appendFormat:@"&status=%@",status];
    //    [url appendFormat:@"&page=%d",page];
    NSString *newUrl = [url stringByAddingPercentEncodingWithAllowedCharacters:[NSCharacterSet URLQueryAllowedCharacterSet]];
    [self PostWithURL:newUrl parameters:parameters progress:nil success:^(NSURLSessionDataTask * _Nonnull task, id  _Nullable responseObject)
     {
         NSDictionary *responseDic = responseObject;
         if ([responseDic isKindOfClass:[NSNull class]])
         {
             if (errorBlock) errorBlock(0,@"请求失败"); return;
         }
         int code = [[responseDic objectForKey:@"code"]intValue];
         NSString *msg = [responseDic objectForKey:@"msg"];
         if (code != kv_PHPRequestSuccess)
         {
             if (ChZ_StringIsEmpty(msg))
             {
                 if (errorBlock) errorBlock(code,msg); return;
             }
             if (errorBlock) errorBlock(code,@"请求失败"); return;
         }
         if (successBlock) successBlock(nil);
         return;
     } failure:^(NSURLSessionDataTask * _Nullable task, NSError * _Nonnull error)
     {
         if (errorBlock) errorBlock(0,@"请求失败"); return;
     }];
}


+ (void)requestSubmitAlipayPayAccount:(NSString *)alipayAccount
                               qrcode:(NSString *)qrcode
                                 name:(NSString *)name
                             successBlock:(ChZ_SuccessBlock)successBlock
                               errorBlock:(ChZ_ErrorCodeBlock)errorBlock
{
    NSMutableString *url = [NSMutableString string];
    [url appendString:RequestURL(kURL_submitalipayinfo)];
    NSString *uid = [APPControl sharedAPPControl].user.fid;
    NSMutableDictionary *parameters = [NSMutableDictionary dictionary];
    if (ChZ_StringIsEmpty(uid))[parameters setObject:uid forKey:@"uid"];
    if (ChZ_StringIsEmpty(alipayAccount))[parameters setObject:alipayAccount forKey:@"data[username]"];
    if (ChZ_StringIsEmpty(name))[parameters setObject:name forKey:@"data[name]"];
    if (ChZ_StringIsEmpty(qrcode))[parameters setObject:qrcode forKey:@"data[qrcode]"];
    NSString *newUrl = [url stringByAddingPercentEncodingWithAllowedCharacters:[NSCharacterSet URLQueryAllowedCharacterSet]];
    [self PostWithURL:newUrl parameters:parameters progress:nil success:^(NSURLSessionDataTask * _Nonnull task, id  _Nullable responseObject)
     {
         NSDictionary *responseDic = responseObject;
         if ([responseDic isKindOfClass:[NSNull class]])
         {
             if (errorBlock) errorBlock(0,@"请求失败"); return;
         }
         int code = [[responseDic objectForKey:@"code"]intValue];
         NSString *msg = [responseDic objectForKey:@"msg"];
         if (code != kv_PHPRequestSuccess)
         {
             if (ChZ_StringIsEmpty(msg))
             {
                 if (errorBlock) errorBlock(code,msg); return;
             }
             if (errorBlock) errorBlock(code,@"请求失败"); return;
         }
         if (successBlock) successBlock(nil);
         return;
     } failure:^(NSURLSessionDataTask * _Nullable task, NSError * _Nonnull error)
     {
         if (errorBlock) errorBlock(0,@"请求失败"); return;
     }];
}

+ (void)requestSubmitWechatPayAccount:(NSString *)wechatAccount
                               qrcode:(NSString *)qrcode
                                 name:(NSString *)name
                             successBlock:(ChZ_SuccessBlock)successBlock
                               errorBlock:(ChZ_ErrorCodeBlock)errorBlock
{
    NSMutableString *url = [NSMutableString string];
    [url appendString:RequestURL(kURL_submitwechatinfo)];
    NSString *uid = [APPControl sharedAPPControl].user.fid;
    NSMutableDictionary *parameters = [NSMutableDictionary dictionary];
    if (ChZ_StringIsEmpty(uid))[parameters setObject:uid forKey:@"uid"];
    if (ChZ_StringIsEmpty(wechatAccount))[parameters setObject:wechatAccount forKey:@"data[username]"];
    if (ChZ_StringIsEmpty(name))[parameters setObject:name forKey:@"data[name]"];
    if (ChZ_StringIsEmpty(qrcode))[parameters setObject:qrcode forKey:@"data[qrcode]"];
    NSString *newUrl = [url stringByAddingPercentEncodingWithAllowedCharacters:[NSCharacterSet URLQueryAllowedCharacterSet]];
    [self PostWithURL:newUrl parameters:parameters progress:nil success:^(NSURLSessionDataTask * _Nonnull task, id  _Nullable responseObject)
     {
         NSDictionary *responseDic = responseObject;
         if ([responseDic isKindOfClass:[NSNull class]])
         {
             if (errorBlock) errorBlock(0,@"请求失败"); return;
         }
         int code = [[responseDic objectForKey:@"code"]intValue];
         NSString *msg = [responseDic objectForKey:@"msg"];
         if (code != kv_PHPRequestSuccess)
         {
             if (ChZ_StringIsEmpty(msg))
             {
                 if (errorBlock) errorBlock(code,msg); return;
             }
             if (errorBlock) errorBlock(code,@"请求失败"); return;
         }
         if (successBlock) successBlock(nil);
         return;
     } failure:^(NSURLSessionDataTask * _Nullable task, NSError * _Nonnull error)
     {
         if (errorBlock) errorBlock(0,@"请求失败"); return;
     }];
}



+ (void)requestRemoveWechatPayAccount:(NSString *)fid
                         successBlock:(ChZ_SuccessBlock)successBlock
                           errorBlock:(ChZ_ErrorCodeBlock)errorBlock
{
    NSMutableString *url = [NSMutableString string];
    [url appendString:RequestURL(kURL_removewechatinfo)];
    NSString *uid = [APPControl sharedAPPControl].user.fid;
    NSMutableDictionary *parameters = [NSMutableDictionary dictionary];
    if (ChZ_StringIsEmpty(uid))[parameters setObject:uid forKey:@"uid"];
    if (ChZ_StringIsEmpty(fid))[parameters setObject:fid forKey:@"id"];
    NSString *newUrl = [url stringByAddingPercentEncodingWithAllowedCharacters:[NSCharacterSet URLQueryAllowedCharacterSet]];
    [self PostWithURL:newUrl parameters:parameters progress:nil success:^(NSURLSessionDataTask * _Nonnull task, id  _Nullable responseObject)
     {
         NSDictionary *responseDic = responseObject;
         if ([responseDic isKindOfClass:[NSNull class]])
         {
             if (errorBlock) errorBlock(0,@"请求失败"); return;
         }
         int code = [[responseDic objectForKey:@"code"]intValue];
         NSString *msg = [responseDic objectForKey:@"msg"];
         if (code != kv_PHPRequestSuccess)
         {
             if (ChZ_StringIsEmpty(msg))
             {
                 if (errorBlock) errorBlock(code,msg); return;
             }
             if (errorBlock) errorBlock(code,@"请求失败"); return;
         }
         if (successBlock) successBlock(nil);
         return;
     } failure:^(NSURLSessionDataTask * _Nullable task, NSError * _Nonnull error)
     {
         if (errorBlock) errorBlock(0,@"请求失败"); return;
     }];
}


+ (void)requestRemoveBankPayAccount:(NSString *)fid
                         successBlock:(ChZ_SuccessBlock)successBlock
                           errorBlock:(ChZ_ErrorCodeBlock)errorBlock
{
    NSMutableString *url = [NSMutableString string];
    [url appendString:RequestURL(kURL_removebankinfo)];
    NSString *uid = [APPControl sharedAPPControl].user.fid;
    NSMutableDictionary *parameters = [NSMutableDictionary dictionary];
    if (ChZ_StringIsEmpty(uid))[parameters setObject:uid forKey:@"uid"];
    if (ChZ_StringIsEmpty(fid))[parameters setObject:fid forKey:@"id"];
    NSString *newUrl = [url stringByAddingPercentEncodingWithAllowedCharacters:[NSCharacterSet URLQueryAllowedCharacterSet]];
    [self PostWithURL:newUrl parameters:parameters progress:nil success:^(NSURLSessionDataTask * _Nonnull task, id  _Nullable responseObject)
     {
         NSDictionary *responseDic = responseObject;
         if ([responseDic isKindOfClass:[NSNull class]])
         {
             if (errorBlock) errorBlock(0,@"请求失败"); return;
         }
         int code = [[responseDic objectForKey:@"code"]intValue];
         NSString *msg = [responseDic objectForKey:@"msg"];
         if (code != kv_PHPRequestSuccess)
         {
             if (ChZ_StringIsEmpty(msg))
             {
                 if (errorBlock) errorBlock(code,msg); return;
             }
             if (errorBlock) errorBlock(code,@"请求失败"); return;
         }
         if (successBlock) successBlock(nil);
         return;
     } failure:^(NSURLSessionDataTask * _Nullable task, NSError * _Nonnull error)
     {
         if (errorBlock) errorBlock(0,@"请求失败"); return;
     }];
}


+ (void)requestRemoveAlipayPayAccount:(NSString *)fid
                         successBlock:(ChZ_SuccessBlock)successBlock
                           errorBlock:(ChZ_ErrorCodeBlock)errorBlock
{
    NSMutableString *url = [NSMutableString string];
    [url appendString:RequestURL(kURL_removealipayinfo)];
    NSString *uid = [APPControl sharedAPPControl].user.fid;
    NSMutableDictionary *parameters = [NSMutableDictionary dictionary];
    if (ChZ_StringIsEmpty(uid))[parameters setObject:uid forKey:@"uid"];
    if (ChZ_StringIsEmpty(fid))[parameters setObject:fid forKey:@"id"];
    NSString *newUrl = [url stringByAddingPercentEncodingWithAllowedCharacters:[NSCharacterSet URLQueryAllowedCharacterSet]];
    [self PostWithURL:newUrl parameters:parameters progress:nil success:^(NSURLSessionDataTask * _Nonnull task, id  _Nullable responseObject)
     {
         NSDictionary *responseDic = responseObject;
         if ([responseDic isKindOfClass:[NSNull class]])
         {
             if (errorBlock) errorBlock(0,@"请求失败"); return;
         }
         int code = [[responseDic objectForKey:@"code"]intValue];
         NSString *msg = [responseDic objectForKey:@"msg"];
         if (code != kv_PHPRequestSuccess)
         {
             if (ChZ_StringIsEmpty(msg))
             {
                 if (errorBlock) errorBlock(code,msg); return;
             }
             if (errorBlock) errorBlock(code,@"请求失败"); return;
         }
         if (successBlock) successBlock(nil);
         return;
     } failure:^(NSURLSessionDataTask * _Nullable task, NSError * _Nonnull error)
     {
         if (errorBlock) errorBlock(0,@"请求失败"); return;
     }];
}


@end
