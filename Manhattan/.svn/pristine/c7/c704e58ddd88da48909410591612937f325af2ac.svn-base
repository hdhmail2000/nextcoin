//
//  XHHLoginRequest.h
//  Manhattan
//
//  Created by Apple on 2018/9/10.
//  Copyright © 2018年 Apple. All rights reserved.
//

#import "ChZBaseHttpRequest.h"

@interface XHHLoginRequest : ChZBaseHttpRequest

/**
 登录
 
 @param username 用户名
 @param password 密码
 @param type 0 手机 1邮箱
 @param successBlock 成功
 @param errorBlock 失败
 */
+ (void)requestLogin:(NSString *)username
            password:(NSString *)password
                type:(NSString *)type
        successBlock:(ChZ_SuccessBlock)successBlock
          errorBlock:(ChZ_ErrorCodeBlock)errorBlock;


/**
 注册 绑定  验证码
 
 @param areaNum 区域号
 @param phone 手机号
 @param type 112手机端注册、102绑定手机
 @param successBlock 成功
 @param errorBlock 失败
 */
+ (void)requestPhoneMsg:(NSString *)areaNum
                  phone:(NSString *)phone
                   type:(NSString *)type
           successBlock:(ChZ_SuccessBlock)successBlock
             errorBlock:(ChZ_ErrorCodeBlock)errorBlock;




/**
 注册
 
 @param phone 手机号
 @param areaNum 区域号
 @param password 密码
 @param msgCode 短信验证码
 @param introUserId 推荐人
 @param successBlock 成功
 @param errorBlock 失败
 */
+ (void)requestRegister:(NSString *)phone
                areaNum:(NSString *)areaNum
               password:(NSString *)password
                msgCode:(NSString *)msgCode
            introUserId:(NSString *)introUserId
           successBlock:(ChZ_SuccessBlock)successBlock
             errorBlock:(ChZ_ErrorCodeBlock)errorBlock;

/**
 注册成功后调用接口

 @param userName 用户名
 @param passWord 密码
 @param surePsw 确认密码
 @param type 传2
 @param invId 邀请人ID，没有传0
 @param successBlock 成功回调
 @param errorBlock 失败回调
 */
+(void)requestRegistSuccessUserName:(NSString *)userName
                           passWord:(NSString *)passWord
                            surePsw:(NSString *)surePsw
                               type:(NSString *)type
                              invId:(NSString *)invId
                       successBlock:(ChZ_SuccessBlock)successBlock
                         errorBlock:(ChZ_ErrorCodeBlock)errorBlock;

@end
