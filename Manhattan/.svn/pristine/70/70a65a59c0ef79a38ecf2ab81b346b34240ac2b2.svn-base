//
//  XHHSafeCenterRequest.h
//  Manhattan
//
//  Created by Apple on 2018/9/11.
//  Copyright © 2018年 Apple. All rights reserved.
//

#import "ChZBaseHttpRequest.h"

@interface XHHSafeCenterRequest : ChZBaseHttpRequest

/**
 获取安全设置详情 GET （非签名） << ++++
 
 @param successBlock 成功
 @param errorBlock 失败
 */
+ (void)requestSecureSettingDetailSuccessBlock:(ChZ_SuccessBlock)successBlock
                                    errorBlock:(ChZ_ErrorCodeBlock)errorBlock;
/**
 获取谷歌验证码秘钥 GET （非签名） << ++++
 
 @param successBlock 成功
 @param errorBlock 失败
 */
+ (void)requestGetGoogleAuthCodeWithSuccessBlock:(ChZ_SuccessBlock)successBlock
                                      errorBlock:(ChZ_ErrorCodeBlock)errorBlock;

/**
 添加谷歌验证码秘钥  POST（签名）
 
 @param code Google 验证码
 @param googleKEy Google 密钥
 @param successBlock 成功
 @param errorBlock 失败
 */
+ (void)requestAddGoogleAuthCode:(NSString *)code
                       googlekey:(NSString *)googleKEy
                    successBlock:(ChZ_SuccessBlock)successBlock
                      errorBlock:(ChZ_ErrorCodeBlock)errorBlock;

/**
 修改/绑定，登录和交易密码 POST （签名）  << ++++
 
 @param oldPassword 原密码
 @param password 新密码
 @param msgCode 手机验证码
 @param googleCode Google 验证码
 @param cardNumber 身份证号码
 @param type 类型 0登录密码，1交易密码
 @param successBlock 成功
 @param errorBlock 失败
 */
+ (void)requestUpdatePassword:(NSString *)oldPassword
                     password:(NSString *)password
                      msgCode:(NSString *)msgCode
                   googleCode:(NSString *)googleCode
                   cardNumber:(NSString *)cardNumber
                         type:(NSString *)type
                 successBlock:(ChZ_SuccessBlock)successBlock
                   errorBlock:(ChZ_ErrorCodeBlock)errorBlock;

/**
 注册 绑定
 
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
 实名认证  POST（签名）   << ++++
 
 @param realName 真实姓名
 @param identityType 证件类型
 @param identityNum 证件号码
 @param addressStr 居住地址
 @param successBlock 成功
 @param errorBlock 失败
 */
+ (void)requestRealNameAuthWithName:(NSString *)realName
                       identityType:(NSString *)identityType
                        identityNum:(NSString *)identityNum
                         addressStr:(NSString *)addressStr
                       successBlock:(ChZ_SuccessBlock)successBlock
                         errorBlock:(ChZ_ErrorCodeBlock)errorBlock;
/**
 找回交易密码接口
 
 @param phone 手机号
 @param area 区域号
 @param code 验证码
 @param totpCode Google 验证码
 @param newPassword 密码
 @param newPassword2 确认密码
 @param successBlock successBlock
 @param errorBlock errorBlock
 */
+ (void)requestTradePasswordForget:(NSString *)phone
                              area:(NSString *)area
                              code:(NSString *)code
                          totpCode:(NSString *)totpCode
                       newPassword:(NSString *)newPassword
                      newPassword2:(NSString *)newPassword2
                      successBlock:(ChZ_SuccessBlock)successBlock
                        errorBlock:(ChZ_ErrorCodeBlock)errorBlock;
/**
 手机端修改密码
 
 @param phone 手机号
 @param area 区域号
 @param code 验证码
 @param totpCode Google 验证码
 @param newPassword 密码
 @param newPassword2 确认密码
 @param successBlock successBlock
 @param errorBlock errorBlock
 */
+ (void)requestLoginPasswordForget:(NSString *)phone
                              area:(NSString *)area
                              code:(NSString *)code
                          totpCode:(NSString *)totpCode
                       newPassword:(NSString *)newPassword
                      newPassword2:(NSString *)newPassword2
                      successBlock:(ChZ_SuccessBlock)successBlock
                        errorBlock:(ChZ_ErrorCodeBlock)errorBlock;

/**
 获取个人信息

 @param successBlock 成功回调
 @param errorBlock 失败回调
 */
+(void)requestUserMessageSuccessBlock:(ChZ_SuccessBlock)successBlock
                           errorBlock:(ChZ_ErrorCodeBlock)errorBlock;

/**
 更改个人信息

 @param nickName 昵称
 @param sex 性别
 @param headUrl 用户头像
 @param successBlock 成功回调
 @param errorBlock 失败回调
 */
+(void)requestUpdateUserMessageNickName:(NSString *)nickName
                                    sex:(NSString *)sex
                           headImageUrl:(NSString *)headUrl
                           successBlock:(ChZ_SuccessBlock)successBlock
                             errorBlock:(ChZ_ErrorCodeBlock)errorBlock;


@end
