//
//  ChZAccountRequest.h
//  FuturePurse
//
//  Created by Howe on 2018/9/4.
//  Copyright © 2018年 jbtm. All rights reserved.
//

#import "ChZBaseHttpRequest.h"

@interface ChZAccountRequest : ChZBaseHttpRequest


/**
 账号列表

 @param successBlock 成功
 @param errorBlock 失败
 */
+ (void)requestPayAccountListSuccessBlock:(ChZ_SuccessBlock)successBlock
                               errorBlock:(ChZ_ErrorCodeBlock)errorBlock;


/**
 提交银行账号

 @param name 开户名
 @param cardNumber 身份证号
 @param bankNumber 银行卡号
 @param bankName 开户行
 @param phone 手机号
 @param successBlock 成功
 @param errorBlock 失败
 */
+ (void)requestSubmitBankPayAccountList:(NSString *)name
                               cardname:(NSString *)cardNumber
                             bankNumber:(NSString *)bankNumber
                               bankName:(NSString *)bankName
                                  phone:(NSString *)phone
                           successBlock:(ChZ_SuccessBlock)successBlock
                             errorBlock:(ChZ_ErrorCodeBlock)errorBlock;


/**
 支付宝

 @param alipayAccount 支付宝账户
 @param qrcode 二维码
 @param successBlock xx
 @param errorBlock xx
 */
+ (void)requestSubmitAlipayPayAccount:(NSString *)alipayAccount
                               qrcode:(NSString *)qrcode
                                 name:(NSString *)name
                         successBlock:(ChZ_SuccessBlock)successBlock
                           errorBlock:(ChZ_ErrorCodeBlock)errorBlock;


/**
 微信

 @param wechatAccount 微信账号
 @param qrcode 二维码
 @param successBlock xx
 @param errorBlock xx
 */
+ (void)requestSubmitWechatPayAccount:(NSString *)wechatAccount
                               qrcode:(NSString *)qrcode
                                 name:(NSString *)name
                         successBlock:(ChZ_SuccessBlock)successBlock
                           errorBlock:(ChZ_ErrorCodeBlock)errorBlock;

/**
 删除支付宝
 
 @param fid id
 @param successBlock xx
 @param errorBlock xx
 */
+ (void)requestRemoveAlipayPayAccount:(NSString *)fid
                         successBlock:(ChZ_SuccessBlock)successBlock
                           errorBlock:(ChZ_ErrorCodeBlock)errorBlock;

/**
 删除银行卡
 
 @param fid id
 @param successBlock xx
 @param errorBlock xx
 */
+ (void)requestRemoveBankPayAccount:(NSString *)fid
                       successBlock:(ChZ_SuccessBlock)successBlock
                         errorBlock:(ChZ_ErrorCodeBlock)errorBlock;

/**
 删除微信

 @param fid id
 @param successBlock xx
 @param errorBlock xx
 */
+ (void)requestRemoveWechatPayAccount:(NSString *)fid
                         successBlock:(ChZ_SuccessBlock)successBlock
                           errorBlock:(ChZ_ErrorCodeBlock)errorBlock;


@end
