//
//  XHHC2CMyOrderModel.m
//  FuturePurse
//
//  Created by Apple on 2018/9/5.
//  Copyright © 2018年 jbtm. All rights reserved.
//

#import "XHHC2CMyOrderModel.h"

@implementation XHHC2CMyOrderModel
+ (NSDictionary *)mj_objectClassInArray
{
    return @{@"bank":@"ChZUserPayBankAccountModel",@"alipay":@"ChZUserPayAlipayAccountModel",@"weixin":@"ChZUserPayWechatAccountModel"};
}


@end
