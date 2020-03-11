//
//  XHHTradPayMessageCell.h
//  FuturePurse
//
//  Created by Apple on 2018/7/30.
//  Copyright © 2018年 jbtm. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "XHHC2CMyOrderModel.h"
@interface XHHTradPayMessageCell : UITableViewCell
@property (nonatomic, assign) int isAlipay;
@property (nonatomic, strong) XHHC2CMyOrderModel *model;
@end
