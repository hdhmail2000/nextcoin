//
//  ChZUserPayAccountModel.h
//  FuturePurse
//
//  Created by Howe on 2018/9/4.
//  Copyright © 2018年 jbtm. All rights reserved.
//

#import "BaseObject.h"
@class ChZUserPayAlipayAccountModel;
@class ChZUserPayWechatAccountModel;
@class ChZUserPayBankAccountModel;

@interface ChZUserPayAccountModel : BaseObject

@property (nonatomic, assign) int count;

@property (nonatomic, copy) NSString *m;

@property (nonatomic, strong) ChZUserPayAlipayAccountModel *alipay;


@property (nonatomic, strong) ChZUserPayWechatAccountModel *weixin;


@property (nonatomic, strong) ChZUserPayBankAccountModel *bank;

@end

@interface ChZUserPayBankAccountModel : BaseObject

@property (nonatomic, copy) NSString * fid;
@property (nonatomic, copy) NSString * uid;
@property (nonatomic, copy) NSString * author;
@property (nonatomic, copy) NSString * inputip;
@property (nonatomic, copy) NSString * inputtime;
@property (nonatomic, copy) NSString * khh;
@property (nonatomic, copy) NSString * khm;
@property (nonatomic, copy) NSString * zhhm;
@property (nonatomic, copy) NSString * idCard;
@property (nonatomic, copy) NSString * phone;

@end


@interface ChZUserPayWechatAccountModel : BaseObject
@property (nonatomic, copy) NSString * fid;
@property (nonatomic, copy) NSString * uid;
@property (nonatomic, copy) NSString * author;
@property (nonatomic, copy) NSString * inputip;
@property (nonatomic, copy) NSString * inputtime;
@property (nonatomic, copy) NSString * name;
@property (nonatomic, copy) NSString * username;
@property (nonatomic, copy) NSString * qrcode;
@end


@interface ChZUserPayAlipayAccountModel : BaseObject
@property (nonatomic, copy) NSString * fid;
@property (nonatomic, copy) NSString * uid;
@property (nonatomic, copy) NSString * author;
@property (nonatomic, copy) NSString * inputip;
@property (nonatomic, copy) NSString * inputtime;
@property (nonatomic, copy) NSString * name;
@property (nonatomic, copy) NSString * username;
@property (nonatomic, copy) NSString * qrcode;
@end
