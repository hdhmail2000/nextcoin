//
//  ChZMarketListModel.h
//  FuturePurse
//
//  Created by Howe on 2018/8/8.
//  Copyright © 2018年 jbtm. All rights reserved.
//

#import "BaseObject.h"
#import "ChZRealCoinInfoModel.h"

@interface ChZMarketListModel : BaseObject

@property (nonatomic, copy) NSString * fid;
@property (nonatomic, copy) NSString * sortId;
//type
@property (nonatomic, copy) NSString * type;
@property (nonatomic, copy) NSString * status;
@property (nonatomic, copy) NSString * buyCoinId;
@property (nonatomic, copy) NSString * buySymbol;
@property (nonatomic, copy) NSString * buyName;
@property (nonatomic, copy) NSString * buyShortName;
@property (nonatomic, copy) NSString * buyWebLogo;
@property (nonatomic, copy) NSString * buyAppLogo;
@property (nonatomic, copy) NSString * sellCoinId;
@property (nonatomic, copy) NSString * sellSymbol;
@property (nonatomic, copy) NSString * sellName;
@property (nonatomic, copy) NSString * sellShortName;
@property (nonatomic, copy) NSString * sellWebLogo;
@property (nonatomic, copy) NSString * sellAppLogo;
@property (nonatomic, copy) NSString * isShare;
@property (nonatomic, copy) NSString * isStop;
@property (nonatomic, copy) NSString * openTime;
@property (nonatomic, copy) NSString * stopTime;
@property (nonatomic, copy) NSString * buyFee;
@property (nonatomic, copy) NSString * sellFee;
@property (nonatomic, copy) NSString * remoteId;
@property (nonatomic, copy) NSString * priceWave;
@property (nonatomic, copy) NSString * priceRange;
@property (nonatomic, copy) NSString * minCount;
@property (nonatomic, copy) NSString * maxCount;
@property (nonatomic, copy) NSString * minPrice;
@property (nonatomic, copy) NSString * maxPrice;
@property (nonatomic, copy) NSString * amountOffset;
@property (nonatomic, copy) NSString * priceOffset;
@property (nonatomic, copy) NSString * digit;
@property (nonatomic, copy) NSString * openPrice;
@property (nonatomic, copy) NSString * agentId;
@property (nonatomic, copy) NSString * gmtCreate;
@property (nonatomic, copy) NSString * gmtModified;
@property (nonatomic, copy) NSString * version;
@property (nonatomic, copy) NSString * typeName;
@property (nonatomic, copy) NSString * blockName;
//==============
- (void)syncCoin;

@property (nonatomic, strong) ChZRealCoinInfoModel  *model;
@end
