//
//  XHHSwitchDetailViewController.h
//  FuturePurse
//
//  Created by Apple on 2018/8/29.
//  Copyright © 2018年 jbtm. All rights reserved.
//

#import "BaseViewController.h"
#import "ChZRealCoinInfoModel.h"
@interface XHHSwitchDetailViewController : BaseViewController
//修改
@property (nonatomic , strong) ChZRealCoinInfoModel *coinModel;
//交易ID
@property (nonatomic , copy) NSString *symblId;

@end
