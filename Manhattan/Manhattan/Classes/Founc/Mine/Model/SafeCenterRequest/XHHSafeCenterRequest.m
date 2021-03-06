//
//  XHHSafeCenterRequest.m
//  Manhattan
//
//  Created by Apple on 2018/9/11.
//  Copyright © 2018年 Apple. All rights reserved.
//

#import "XHHSafeCenterRequest.h"

#import "XHHSafeCenterModel.h"
#import "XHHUserModelCenter.h"
@implementation XHHSafeCenterRequest

+ (void)requestSecureSettingDetailSuccessBlock:(ChZ_SuccessBlock)successBlock
                                    errorBlock:(ChZ_ErrorCodeBlock)errorBlock
{
    NSString *token = [APPControl sharedAPPControl].token;
    if (!ChZ_StringIsEmpty(token))
    {
        ChZ_MBDismss;
        ChZ_MBError(@"登录过期，请从新登录");
        dispatch_after(dispatch_time(DISPATCH_TIME_NOW, (int64_t)(2 * NSEC_PER_SEC)), dispatch_get_main_queue(), ^{
            [[APPControl sharedAPPControl] toLogin];
        });
        return;
    }
    NSString *urlStr = [NSString stringWithFormat:@"%@?token=%@", RequestURL(kURL_safeSetDetail), token];
    //    urlStr = [[urlStr stringByAddingPercentEncodingWithAllowedCharacters:[NSCharacterSet URLQueryAllowedCharacterSet]] mutableCopy];
    
    [self GetWithURL:urlStr parameters:nil progress:nil success:^(NSURLSessionDataTask * _Nonnull task, id  _Nullable responseObject) {
        NSDictionary *responseDic = responseObject;
        if ([responseDic isKindOfClass:[NSNull class]])
        {
            if (errorBlock) errorBlock(0,@"请求失败"); return;
        }
        int code = [[responseDic objectForKey:@"code"]intValue];
        NSString *msg = [responseDic objectForKey:@"msg"];
        
        
        if (code != kv_JavaRequestSuccess)
        {
            if (ChZ_StringIsEmpty(msg))
            {
                if (errorBlock) errorBlock(code,msg); return;
            }
            if (errorBlock) errorBlock(code,@"请求失败"); return;
        }
        NSDictionary *data = [responseDic objectForKey:@"data"];
        if (data != nil && data.count != 0)
        {
            HCCenterSecuritySettingWrapperModel *wrapperModel = [HCCenterSecuritySettingWrapperModel mj_objectWithKeyValues:data];
            if (successBlock) successBlock(wrapperModel);
            return;
        } else
        {
            if (successBlock) successBlock(nil);
            return;
        }
        
    } failure:^(NSURLSessionDataTask * _Nullable task, NSError * _Nonnull error) {
        if (errorBlock) errorBlock(0,@"请求失败"); return;
    }];
}
#pragma mark - 获取谷歌验证码秘钥 GET （非签名）
+ (void)requestGetGoogleAuthCodeWithSuccessBlock:(ChZ_SuccessBlock)successBlock
                                      errorBlock:(ChZ_ErrorCodeBlock)errorBlock
{
    NSString *token = [APPControl sharedAPPControl].token;
    if (!ChZ_StringIsEmpty(token)) return;
    NSString *urlStr = [NSString stringWithFormat:@"%@?token=%@", RequestURL(kURL_googleCode), token];
    
    [self GetWithURL:urlStr parameters:nil progress:nil success:^(NSURLSessionDataTask * _Nonnull task, id  _Nullable responseObject) {
        NSDictionary *responseDic = responseObject;
        if ([responseDic isKindOfClass:[NSNull class]])
        {
            if (errorBlock) errorBlock(0,@"请求失败"); return;
        }
        int code = [[responseDic objectForKey:@"code"]intValue];
        NSString *msg = [responseDic objectForKey:@"msg"];
        if (code != kv_JavaRequestSuccess)
        {
            //UserLoginTokenExpired
            
            if (ChZ_StringIsEmpty(msg))
            {
                if (errorBlock) errorBlock(code,msg); return;
            }
            if (errorBlock) errorBlock(code,@"请求失败"); return;
        }
        NSDictionary *data = [responseDic objectForKey:@"data"];
        if (data)
        {
            if (successBlock) {
                successBlock(data);
            }
        } else {
            if (errorBlock) errorBlock(0,@"请求失败"); return;
        }
        
    } failure:^(NSURLSessionDataTask * _Nullable task, NSError * _Nonnull error) {
        if (errorBlock) errorBlock(0,@"请求失败"); return;
    }];
}

#pragma mark - 添加谷歌验证码秘钥  POST（签名）
+ (void)requestAddGoogleAuthCode:(NSString *)code
                       googlekey:(NSString *)googleKEy
                    successBlock:(ChZ_SuccessBlock)successBlock
                      errorBlock:(ChZ_ErrorCodeBlock)errorBlock
{
    NSMutableString *url = [NSMutableString string];
    [url appendString:RequestURL(kURL_addGoogleCode)];
    NSMutableDictionary *parameters = [NSMutableDictionary dictionary];
    
    if (ChZ_StringIsEmpty(code))[parameters setObject:code forKey:@"code"];
    if (ChZ_StringIsEmpty(googleKEy))[parameters setObject:googleKEy forKey:@"totpKey"];
    
    NSString *newUrl = [url stringByAddingPercentEncodingWithAllowedCharacters:[NSCharacterSet URLQueryAllowedCharacterSet]];
    [self PostAuthWithURL:newUrl parameters:parameters progress:nil success:^(NSURLSessionDataTask * _Nonnull task, id  _Nullable responseObject)
     {
         NSDictionary *responseDic = responseObject;
         if ([responseDic isKindOfClass:[NSNull class]])
         {
             if (errorBlock) errorBlock(0,@"请求失败"); return;
         }
         int code = [[responseDic objectForKey:@"code"]intValue];
         NSString *msg = [responseDic objectForKey:@"msg"];
         if (code != kv_JavaRequestSuccess)
         {
             if (ChZ_StringIsEmpty(msg))
             {
                 if (errorBlock) errorBlock(code,msg); return;
             }
             if (errorBlock) errorBlock(code,@"请求失败"); return;
         }
         
         if (successBlock) successBlock(msg); return;
     } failure:^(NSURLSessionDataTask * _Nullable task, NSError * _Nonnull error)
     {
         if (errorBlock) errorBlock(0,@"请求失败"); return;
     }];
}
#pragma mark - 修改/绑定，登录和交易密码 POST （签名）
+ (void)requestUpdatePassword:(NSString *)oldPassword
                     password:(NSString *)password
                      msgCode:(NSString *)msgCode
                   googleCode:(NSString *)googleCode
                   cardNumber:(NSString *)cardNumber
                         type:(NSString *)type
                 successBlock:(ChZ_SuccessBlock)successBlock
                   errorBlock:(ChZ_ErrorCodeBlock)errorBlock
{
    
    NSMutableString *url = [NSMutableString string];
    [url appendString:RequestURL(kURL_passWordSetting)];
    NSMutableDictionary *parameters = [NSMutableDictionary dictionary];
    if (ChZ_StringIsEmpty(password))[parameters setObject:password forKey:@"newPwd"];
    if (ChZ_StringIsEmpty(password))[parameters setObject:password forKey:@"reNewPwd"];
    if (ChZ_StringIsEmpty(oldPassword))[parameters setObject:oldPassword forKey:@"originPwd"];
    if (ChZ_StringIsEmpty(msgCode))[parameters setObject:msgCode forKey:@"phoneCode"];
    if (ChZ_StringIsEmpty(googleCode))[parameters setObject:googleCode forKey:@"totpCode"];
    if (ChZ_StringIsEmpty(cardNumber))[parameters setObject:cardNumber forKey:@"identityCode"];
    if (ChZ_StringIsEmpty(type))[parameters setObject:type forKey:@"pwdType"];
    //    NSString *newUrl = [url stringByAddingPercentEncodingWithAllowedCharacters:[NSCharacterSet URLQueryAllowedCharacterSet]];
    [self PostAuthWithURL:url parameters:parameters progress:nil success:^(NSURLSessionDataTask * _Nonnull task, id  _Nullable responseObject)
     {
         NSDictionary *responseDic = responseObject;
         if ([responseDic isKindOfClass:[NSNull class]])
         {
             if (errorBlock) errorBlock(0,@"请求失败"); return;
         }
         int code = [[responseDic objectForKey:@"code"]intValue];
         NSString *msg = [responseDic objectForKey:@"msg"];
         ChZ_DebugLog(@"%@",responseDic[@"msg"]);
         if (code != kv_JavaRequestSuccess)
         {
             if (ChZ_StringIsEmpty(msg))
             {
                 if (errorBlock) errorBlock(code,msg); return;
             }
             if (errorBlock) errorBlock(code,@"请求失败"); return;
         }
         if (successBlock)successBlock(msg);return;
     } failure:^(NSURLSessionDataTask * _Nullable task, NSError * _Nonnull error)
     {
         if (errorBlock) errorBlock(0,@"请求失败"); return;
         
     }];
}
+ (void)requestPhoneMsg:(NSString *)areaNum
                  phone:(NSString *)phone
                   type:(NSString *)type
           successBlock:(ChZ_SuccessBlock)successBlock
             errorBlock:(ChZ_ErrorCodeBlock)errorBlock
{
    NSMutableString *url = [NSMutableString string];
    [url appendString:RequestURL(kURL_phoneMsg)];
    NSMutableDictionary *parameters = [NSMutableDictionary dictionary];
    if (ChZ_StringIsEmpty(areaNum))[parameters setObject:areaNum forKey:@"area"];
    if (ChZ_StringIsEmpty(phone))[parameters setObject:phone forKey:@"phone"];
    if (ChZ_StringIsEmpty(type))[parameters setObject:type forKey:@"type"];
    [self PostWithURL:url parameters:parameters progress:nil success:^(NSURLSessionDataTask * _Nonnull task, id  _Nullable responseObject)
     {
         NSDictionary *responseDic = responseObject;
         if ([responseDic isKindOfClass:[NSNull class]])
         {
             if (errorBlock) errorBlock(0,@"请求失败"); return;
         }
         int code = [[responseDic objectForKey:@"code"]intValue];
         NSString *msg = [responseDic objectForKey:@"msg"];
         if (code != kv_JavaRequestSuccess)
         {
             if (ChZ_StringIsEmpty(msg))
             {
                 if (errorBlock) errorBlock(code,msg); return;
             }
             if (errorBlock) errorBlock(code,@"请求失败"); return;
         }
         if (successBlock)successBlock(nil);return;
         
     } failure:^(NSURLSessionDataTask * _Nullable task, NSError * _Nonnull error)
     {
         if (errorBlock) errorBlock(0,@"请求失败"); return;
     }];
}
#pragma mark - 实名认证  POST（签名）

+ (void)requestRealNameAuthWithName:(NSString *)realName
                       identityType:(NSString *)identityType
                        identityNum:(NSString *)identityNum
                         addressStr:(NSString *)addressStr
                       successBlock:(ChZ_SuccessBlock)successBlock
                         errorBlock:(ChZ_ErrorCodeBlock)errorBlock{
    NSMutableString *url = [NSMutableString string];
    [url appendString:RequestURL(kURL_verified)];
    NSMutableDictionary *parameters = [NSMutableDictionary dictionary];
    
    //    NSString *token = [APPControl sharedAPPControl].token;
    //    if (ChZ_StringIsEmpty(token))[parameters setObject:token forKey:@"token"];
    
    if (ChZ_StringIsEmpty(realName))[parameters setObject:realName forKey:@"realname"];
    if (ChZ_StringIsEmpty(identityType))[parameters setObject:identityType forKey:@"identitytype"];
    if (ChZ_StringIsEmpty(identityNum))[parameters setObject:identityNum forKey:@"identityno"];
    if (ChZ_StringIsEmpty(addressStr))[parameters setObject:addressStr forKey:@"address"];
    
    NSString *newUrl = [url stringByAddingPercentEncodingWithAllowedCharacters:[NSCharacterSet URLQueryAllowedCharacterSet]];
    [self PostAuthWithURL:newUrl parameters:parameters progress:nil success:^(NSURLSessionDataTask * _Nonnull task, id  _Nullable responseObject)
     {
         NSDictionary *responseDic = responseObject;
         if ([responseDic isKindOfClass:[NSNull class]])
         {
             if (errorBlock) errorBlock(0,@"请求失败"); return;
         }
         int code = [[responseDic objectForKey:@"code"]intValue];
         NSString *msg = [responseDic objectForKey:@"msg"];
         if (code != kv_JavaRequestSuccess)
         {
             if (ChZ_StringIsEmpty(msg))
             {
                 if (errorBlock) errorBlock(code,msg); return;
             }
             if (errorBlock) errorBlock(code,@"请求失败"); return;
         }
         NSDictionary *data = [responseDic objectForKey:@"data"];
         if (data)
         {
             if (successBlock)
             {
                 successBlock(responseDic);
             }
             return;
         }
         if (successBlock)successBlock(nil);return;
     } failure:^(NSURLSessionDataTask * _Nullable task, NSError * _Nonnull error)
     {
         if (errorBlock) errorBlock(0,@"请求失败"); return;
     }];
}
#pragma mark - 忘记登录密码
+ (void)requestLoginPasswordForget:(NSString *)phone
                              area:(NSString *)area
                              code:(NSString *)code
                          totpCode:(NSString *)totpCode
                       newPassword:(NSString *)newPassword
                      newPassword2:(NSString *)newPassword2
                      successBlock:(ChZ_SuccessBlock)successBlock
                        errorBlock:(ChZ_ErrorCodeBlock)errorBlock
{
    
    NSMutableString *url = [NSMutableString string];
    [url appendString:RequestURL(kURL_login_passwordForget)];
    NSMutableDictionary *parameters = [NSMutableDictionary dictionary];
    if (ChZ_StringIsEmpty(phone))[parameters setObject:phone forKey:@"phone"];
    if (ChZ_StringIsEmpty(area))[parameters setObject:area forKey:@"area"];
    if (ChZ_StringIsEmpty(code))[parameters setObject:code forKey:@"code"];
    if (ChZ_StringIsEmpty(totpCode))[parameters setObject:totpCode forKey:@"totpCode"];
    if (ChZ_StringIsEmpty(newPassword))[parameters setObject:newPassword forKey:@"newPassword"];
    if (ChZ_StringIsEmpty(newPassword2))[parameters setObject:newPassword2 forKey:@"newPassword2"];
    //    NSString *newUrl = [url stringByAddingPercentEncodingWithAllowedCharacters:[NSCharacterSet URLQueryAllowedCharacterSet]];
    [self PostAuthWithURL:url parameters:parameters progress:nil success:^(NSURLSessionDataTask * _Nonnull task, id  _Nullable responseObject)
     {
         NSDictionary *responseDic = responseObject;
         if ([responseDic isKindOfClass:[NSNull class]])
         {
             if (errorBlock) errorBlock(0,@"请求失败"); return;
         }
         int code = [[responseDic objectForKey:@"code"]intValue];
         NSString *msg = [responseDic objectForKey:@"msg"];
         if (code != kv_JavaRequestSuccess)
         {
             if (ChZ_StringIsEmpty(msg))
             {
                 if (errorBlock) errorBlock(code,msg); return;
             }
             if (errorBlock) errorBlock(code,@"请求失败"); return;
         }
         if (successBlock)successBlock(msg);return;
         //
         //         NSDictionary *data = [responseDic objectForKey:@"data"];
         //         if (data)
         //         {
         //
         //         }
         //         if (errorBlock) errorBlock(0,@"请求失败"); return;
     } failure:^(NSURLSessionDataTask * _Nullable task, NSError * _Nonnull error)
     {
         if (errorBlock) errorBlock(0,@"请求失败"); return;
         
     }];
}
#pragma mark - 忘记交易密码
+ (void)requestTradePasswordForget:(NSString *)phone
                              area:(NSString *)area
                              code:(NSString *)code
                          totpCode:(NSString *)totpCode
                       newPassword:(NSString *)newPassword
                      newPassword2:(NSString *)newPassword2
                      successBlock:(ChZ_SuccessBlock)successBlock
                        errorBlock:(ChZ_ErrorCodeBlock)errorBlock
{
    
    NSMutableString *url = [NSMutableString string];
    [url appendString:RequestURL(kURL_trade_passwordForget)];
    NSString *token = [APPControl sharedAPPControl].token;
    NSMutableDictionary *parameters = [NSMutableDictionary dictionary];
    if (ChZ_StringIsEmpty(phone))[parameters setObject:phone forKey:@"phone"];
    if (ChZ_StringIsEmpty(area))[parameters setObject:area forKey:@"area"];
    if (ChZ_StringIsEmpty(code))[parameters setObject:code forKey:@"code"];
    if (ChZ_StringIsEmpty(totpCode))[parameters setObject:totpCode forKey:@"totpCode"];
    if (ChZ_StringIsEmpty(newPassword))[parameters setObject:newPassword forKey:@"newPassword"];
    if (ChZ_StringIsEmpty(newPassword2))[parameters setObject:newPassword2 forKey:@"newPassword2"];
    if (ChZ_StringIsEmpty(token)) [parameters setObject:token forKey:@"token"];
    //    NSString *newUrl = [url stringByAddingPercentEncodingWithAllowedCharacters:[NSCharacterSet URLQueryAllowedCharacterSet]];
    [self PostAuthWithURL:url parameters:parameters progress:nil success:^(NSURLSessionDataTask * _Nonnull task, id  _Nullable responseObject)
     {
         NSDictionary *responseDic = responseObject;
         if ([responseDic isKindOfClass:[NSNull class]])
         {
             if (errorBlock) errorBlock(0,@"请求失败"); return;
         }
         int code = [[responseDic objectForKey:@"code"]intValue];
         NSString *msg = [responseDic objectForKey:@"msg"];
         if (code != kv_JavaRequestSuccess)
         {
             if (ChZ_StringIsEmpty(msg))
             {
                 if (errorBlock) errorBlock(code,msg); return;
             }
             if (errorBlock) errorBlock(code,@"请求失败"); return;
         }
         if (successBlock)successBlock(msg);return;
     } failure:^(NSURLSessionDataTask * _Nullable task, NSError * _Nonnull error)
     {
         if (errorBlock) errorBlock(0,@"请求失败"); return;
         
     }];
}
+(void)requestUserMessageSuccessBlock:(ChZ_SuccessBlock)successBlock errorBlock:(ChZ_ErrorCodeBlock)errorBlock{
    NSString *url = RequestURL(kURL_mine_userCenterMessage);
    [self GetPHPWithURL:url parameters:nil progress:nil success:^(NSURLSessionDataTask * _Nonnull task, id  _Nullable responseObject) {
        NSDictionary *responseDic = responseObject;
        ChZ_DebugLog(@"%@---",responseDic);
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
        NSDictionary *data = [responseDic objectForKey:@"return"];
        if (data)
        {
            XHHUserModelCenter *model = [XHHUserModelCenter mj_objectWithKeyValues:data];
            if (successBlock)
            {
                successBlock(model);
            }
            return;
        } else
        {
            if (successBlock) successBlock(nil); return;
        }
    } failure:^(NSURLSessionDataTask * _Nullable task, NSError * _Nonnull error) {
        if (errorBlock) errorBlock(0,@"请求失败"); return;
    }];
}
+(void)requestUpdateUserMessageNickName:(NSString *)nickName sex:(NSString *)sex headImageUrl:(NSString *)headUrl successBlock:(ChZ_SuccessBlock)successBlock errorBlock:(ChZ_ErrorCodeBlock)errorBlock{
    NSMutableDictionary *parma = [NSMutableDictionary dictionary];
    
    NSString *userId = [APPControl sharedAPPControl].user.fid;
    NSString *username = [NSString chz_getObjForKey:kv_LOGIN_USERNAME];
    if (!ChZ_StringIsEmpty(userId))
    {
        [[APPControl sharedAPPControl] toLogin];
        return;
    }
    if(ChZ_StringIsEmpty(nickName)) [parma setObject:nickName forKey:@"data[fnickname]"];
    if(ChZ_StringIsEmpty(userId)) [parma setObject:userId forKey:@"data[fid]"];
    if(ChZ_StringIsEmpty(headUrl)) [parma setObject:headUrl forKey:@"data[favatar]"];
    if(ChZ_StringIsEmpty(sex)) [parma setObject:sex forKey:@"data[fsex]"];
    if(ChZ_StringIsEmpty(username)) [parma setObject:username forKey:@"data[ftelephone]"];
    NSString *url = RequestURL(kURL_mine_updateuserCenterMessage);
    [self PosPHPtWithURL:url parameters:parma progress:nil success:^(NSURLSessionDataTask * _Nonnull task, id  _Nullable responseObject) {
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
        if (successBlock) successBlock(nil); return;
    } failure:^(NSURLSessionDataTask * _Nullable task, NSError * _Nonnull error) {
        if (errorBlock) errorBlock(0,@"请求失败"); return;
    }];
}
@end
