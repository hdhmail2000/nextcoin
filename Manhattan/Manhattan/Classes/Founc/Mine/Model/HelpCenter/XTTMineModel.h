//
//  XTTMineModel.h
//  FuturePurse
//
//  Created by muye01 on 2018/8/30.
//  Copyright © 2018年 jbtm. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "BaseObject.h"

@interface XTTMineModel : NSObject

@end

@interface XTTMineAppHelpListModel : BaseObject

@property (nonatomic , copy) NSString * fid;

@property (nonatomic , copy) NSString * name;


@end

/**
 title    string    主题（问）
 gua    string    挂
 mai    string    买
 huobiziliao    string    货币资料
 da    string    答
 */
@interface XTTMineAppHelpDetailModel : BaseObject

@property (nonatomic , copy) NSString * fid;

@property (nonatomic , copy) NSString * catid;

@property (nonatomic , copy) NSString * title;

@property (nonatomic , copy) NSString * inputtime;

@property (nonatomic , copy) NSString * uid;

@property (nonatomic , copy) NSString * gua;

@property (nonatomic , copy) NSString * mai;

@property (nonatomic , copy) NSString * huobiziliao;

@property (nonatomic , copy) NSString * da;

@property (nonatomic, assign) BOOL isExpand;

@end

@interface  XTTMineTeamLogModel : BaseObject

@property (nonatomic , copy) NSString * uid;

@property (nonatomic , copy) NSString * introuid;

@property (nonatomic , copy) NSString * coinid;

@property (nonatomic , copy) NSString * amount;

@property (nonatomic , copy) NSString * createtime;

@property (nonatomic , copy) NSString * fnickname;

@property (nonatomic , copy) NSString * favatar;

@end

@interface  XTTMineTeamModel : BaseObject

@property (nonatomic , copy) NSString * total;

@property (nonatomic, strong) NSArray <XTTMineTeamLogModel *> *logs;

@end

