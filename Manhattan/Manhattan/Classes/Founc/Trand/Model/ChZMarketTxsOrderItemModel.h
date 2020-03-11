//
//  ChZMarketTxsOrderItemModel.h
//  FuturePurse
//
//  Created by Howe on 2018/8/8.
//  Copyright © 2018年 jbtm. All rights reserved.
//

#import "BaseObject.h"

@interface ChZMarketTxsOrderItemModel : BaseObject
@property (nonatomic ,assign)CGFloat  types;
@property (nonatomic ,assign)CGFloat  leftcount;
@property (nonatomic ,assign)CGFloat  fees;
@property (nonatomic ,assign)CGFloat  last;
@property (nonatomic ,assign)CGFloat  count;
@property (nonatomic ,assign)CGFloat  successamount;
@property (nonatomic ,copy)NSString * source;
@property (nonatomic ,assign)NSInteger  type;
@property (nonatomic ,assign)CGFloat  price;
@property (nonatomic ,copy)NSString * buysymbol;
@property (nonatomic ,copy)NSString * fid;
@property (nonatomic ,copy)NSString * time;
@property (nonatomic ,copy)NSString * sellsymbol;
@property (nonatomic ,assign)NSInteger  status;

///========Custom=========
@property (nonatomic, strong) NSArray  *itemList;
@property (nonatomic, assign) BOOL  isOpenDetail;
@end
