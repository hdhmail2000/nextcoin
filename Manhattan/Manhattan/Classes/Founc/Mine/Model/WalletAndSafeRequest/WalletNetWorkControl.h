//
//  WalletNetWorkControl.h
//  css
//
//  Created by Howe on 2018/4/3.
//  Copyright © 2018年 Howe. All rights reserved.
//

#import "ChZBaseHttpRequest.h"
//#import "HCBindingBankInfoModel.h"
//#import "HCWithdrawGESTSubmitModel.h"
#import "XHHWalletModel.h"

@interface WalletNetWorkControl : ChZBaseHttpRequest


/**
    获取钱包列表
 
 @param successBlock 成功 <<< +++
 @param errorBlock 失败
 */
+ (void)requestWalletListsuccessBlock:(ChZ_SuccessBlock)successBlock
                           errorBlock:(ErrorCodeBlock)errorBlock;


/**
 充值 记录

 @param fid 币ID  <<<
 @param successBlock 成功
 @param errorBlock 失败
 */
+ (void)requestRecharge:(NSString *)fid
           successBlock:(ChZ_SuccessBlock)successBlock
             errorBlock:(ErrorCodeBlock)errorBlock;


/**
 添加谷歌验证码秘钥

 @param code Google 验证码
 @param totpKey Google 秘钥
 @param successBlock 成功
 @param errorBlock 失败
 */
+ (void)requestAddGoogleCode:(NSString *)code
                   googleKey:(NSString *)totpKey
                successBlock:(ChZ_SuccessBlock)successBlock
                  errorBlock:(ErrorCodeBlock)errorBlock;


/**
 发送需要签名的短信的  <<<  +++

 @param successBlock 成功
 @param errorBlock 失败
 */
+ (void)requestSignMsgCodeSuccessBlock:(ChZ_SuccessBlock)successBlock
                            errorBlock:(ErrorCodeBlock)errorBlock;


/**
 获取提币地址列表  <<<  +++

 @param fid 币ID
 @param successBlock 成功
 @param errorBlock 失败
 */
+ (void)requestCoinAddressList:(NSString *)fid
                  successBlock:(ChZ_SuccessBlock)successBlock
                    errorBlock:(ErrorCodeBlock)errorBlock;


/**
 添加提现地址 POST （签名）<<<  +++


 @param phoneCode 手机验证码
 @param googleCode Google 验证码
 @param address 提现地址
 @param password 交易密码
 @param remark 备注
 @param symbolId 币ID
 @param successBlock 成功
 @param errorBlock 失败
 */
+ (void)requestCoinAddressAddPhoneCode:(NSString *)phoneCode
                            googleCode:(NSString *)googleCode
                               address:(NSString *)address
                              password:(NSString *)password
                                remark:(NSString *)remark
                              symbolId:(NSString *)symbolId
                          successBlock:(ChZ_SuccessBlock)successBlock
                            errorBlock:(ErrorCodeBlock)errorBlock;

/**
 提币      <<<  +++

 @param phoneCode 手机验证码
 @param googleCode Google验证码
 @param address 地址
 @param password 密码
 @param amout 金额
 @param addressId 地址
 @param symbolId symbolId
 @param successBlock 成功
 @param errorBlock 失败
 */
+ (void)requestOutCoinPhoneCode:(NSString *)phoneCode
                     googleCode:(NSString *)googleCode
                        address:(NSString *)address
                       password:(NSString *)password
                         amount:(NSString *)amout
                      addressId:(NSString *)addressId
                       symbolId:(NSString *)symbolId
                   successBlock:(ChZ_SuccessBlock)successBlock
                     errorBlock:(ErrorCodeBlock)errorBlock;

/**
 提币记录

 @param fid 币ID
 @param successBlock successBlock
 @param errorBlock errorBlock
 */
+ (void)requestWithdrawRecord:(NSString *)fid
                 successBlock:(ChZ_SuccessBlock)successBlock
                   errorBlock:(ErrorCodeBlock)errorBlock;

+ (void)requestRechargeCCNY:(NSString *)amout
                     coinId:(NSString *)coinId
               successBlock:(ChZ_SuccessBlock)successBlock
                 errorBlock:(ErrorCodeBlock)errorBlock;


+ (void)requestTestAuthSuccessBlock:(ChZ_SuccessBlock)successBlock
                         errorBlock:(ErrorCodeBlock)errorBlock;




/**
 获取银行卡类型列表
 
 @param successBlock 成功
 @param errorBlock 失败
 */
+ (void)requestBankListSuccessBlock:(ChZ_SuccessBlock)successBlock
                         errorBlock:(ErrorCodeBlock)errorBlock;


/**
 发送绑定银行卡的短信的
 
 @param successBlock 成功
 @param errorBlock 失败
 */
+ (void)requestBaingMsgCodeSuccessBlock:(ChZ_SuccessBlock)successBlock
                             errorBlock:(ErrorCodeBlock)errorBlock;

/**
 获取已经绑定的银行卡
 
 @param coinId 币ID
 @param successBlock 成功
 @param errorBlock 失败
 */
+ (void)requestBaingMsgCodeCoinId:(NSString *)coinId
                     successBlock:(ChZ_SuccessBlock)successBlock
                       errorBlock:(ErrorCodeBlock)errorBlock;

/**
 绑定银行卡
 
 @param model 模型
 @param successBlock 成功
 @param errorBlock 失败
 */
+ (void)requestBindingBank:(HCBindingBankInfoModel *)model
              successBlock:(ChZ_SuccessBlock)successBlock
                errorBlock:(ErrorCodeBlock)errorBlock;


/**
 提现
 
 @param model 模型
 @param successBlock 成功
 @param errorBlock 失败
 */
+ (void)requestWithdraGEST:(HCWithdrawGESTSubmitModel *)model
              successBlock:(ChZ_SuccessBlock)successBlock
                errorBlock:(ErrorCodeBlock)errorBlock;

/**
 提现记录
 
 @param coinId 币ID
 @param successBlock 成功
 @param errorBlock 失败
 */
+ (void)requestWithdraGESTList:(NSString *)coinId
                  successBlock:(ChZ_SuccessBlock)successBlock
                    errorBlock:(ErrorCodeBlock)errorBlock;

/**
 提现手续费
 
 @param coinId 币ID
 @param successBlock 成功
 @param errorBlock 失败
 */
+ (void)requestWithdraGESTFee:(NSString *)coinId
                 successBlock:(ChZ_SuccessBlock)successBlock
                   errorBlock:(ErrorCodeBlock)errorBlock;

/**
 隐藏币种列表

 @param successBlock 成功回调
 @param errorBlock 失败回调
 */
+(void)requestHideCoinListsuccessBlock:(ChZ_SuccessBlock)successBlock
                            errorBlock:(ErrorCodeBlock)errorBlock;

/**
 隐藏币种

 @param coinName 币种名称
 @param successBlock 成功回调
 @param errorBlock 失败回调
 */
+(void)requestHideCoinWithName:(NSString *)coinName
                  successBlock:(ChZ_SuccessBlock)successBlock
                    errorBlock:(ErrorCodeBlock)errorBlock;

/**
 钱包交易列表

 @param coinId 币种ID
 @param successBlock 成功回调
 @param errorBlock 失败回调
 */
+ (void)requestRechargeList:(NSString *)coinId
               successBlock:(ChZ_SuccessBlock)successBlock
                 errorBlock:(ErrorCodeBlock)errorBlock;

@end
