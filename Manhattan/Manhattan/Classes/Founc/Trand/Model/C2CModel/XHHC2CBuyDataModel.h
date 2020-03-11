//
//  XHHC2CBuyDataModel.h
//  FuturePurse
//
//  Created by Apple on 2018/9/4.
//  Copyright © 2018年 jbtm. All rights reserved.
//

#import "BaseObject.h"
#import "XHHC2CBuyModel.h"
@interface XHHC2CBuyDataModel : BaseObject
@property (nonatomic , copy) NSString *m;
@property (nonatomic , copy) NSString *fid;
@property (nonatomic , copy) NSString *type;
@property (nonatomic , copy) NSArray <XHHC2CBuyModel *>*data;
@property (nonatomic , copy) NSString *pagecount;
@property (nonatomic , copy) NSString *page;
@end
