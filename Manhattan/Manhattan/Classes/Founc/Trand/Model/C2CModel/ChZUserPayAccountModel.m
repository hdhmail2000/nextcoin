//
//  ChZUserPayAccountModel.m
//  FuturePurse
//
//  Created by Howe on 2018/9/4.
//  Copyright © 2018年 jbtm. All rights reserved.
//

#import "ChZUserPayAccountModel.h"

@implementation ChZUserPayAccountModel
- (int)count
{
    int i = 0;
    if (_alipay)i++;
    if (_weixin)i++;
    if (_bank)i++;
    return i;
}
@end
@implementation ChZUserPayAlipayAccountModel 

@end


@implementation ChZUserPayWechatAccountModel

@end


@implementation ChZUserPayBankAccountModel

@end
