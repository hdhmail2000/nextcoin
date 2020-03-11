//
//  XHHCoinListModel.h
//  FuturePurse
//
//  Created by Apple on 2018/8/10.
//  Copyright © 2018年 jbtm. All rights reserved.
//

#import "BaseObject.h"

@interface XHHCoinListModel : BaseObject
@property (nonatomic , copy) NSString * fid;
@property (nonatomic , copy) NSString * coin;
@property (nonatomic , copy) NSString *thumb;
@property (nonatomic , assign) CGFloat balance;
@end
