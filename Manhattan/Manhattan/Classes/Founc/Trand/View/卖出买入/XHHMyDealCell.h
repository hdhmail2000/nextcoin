//
//  XHHMyDealCell.h
//  FuturePurse
//
//  Created by Apple on 2018/7/27.
//  Copyright © 2018年 jbtm. All rights reserved.
//

#import "BaseTableViewCell.h"
#import "XHHC2CBuyModel.h"
@interface XHHMyDealCell : BaseTableViewCell

@property (nonatomic, assign) C2CTradType tradType;

@property (nonatomic, strong) XHHC2CBuyModel *model;

@end
