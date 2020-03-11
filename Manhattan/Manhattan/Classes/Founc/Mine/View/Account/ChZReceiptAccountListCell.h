//
//  ChZReceiptAccountListCell.h
//  FuturePurse
//
//  Created by Howe on 2018/8/3.
//  Copyright © 2018年 jbtm. All rights reserved.
//

#import "BaseTableViewCell.h"
#import "ChZUserPayAccountModel.h"
@interface ChZReceiptAccountListCell : BaseTableViewCell
@property (nonatomic, strong) ChZUserPayBankAccountModel *bankModel;
@property (nonatomic, strong) ChZUserPayAlipayAccountModel *alipayModel;
@property (nonatomic, strong) ChZUserPayWechatAccountModel *wechatModel;
@end
