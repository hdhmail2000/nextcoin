//
//  MYHelpListModel.h
//  CoinWallet
//
//  Created by Howe on 2018/8/1.
//  Copyright © 2018年 Howe. All rights reserved.
//

#import "BaseObject.h"

@interface MYHelpListModel : BaseObject
@property(nonatomic, copy) NSString * fid;
@property(nonatomic, copy) NSString * pid;
@property(nonatomic, copy) NSString * pids;
@property(nonatomic, copy) NSString * type;
@property(nonatomic, copy) NSString * name;
@property(nonatomic, copy) NSString * title;
@property(nonatomic, copy) NSString * url;
@property(nonatomic, copy) NSString * thumb;
@property(nonatomic, copy) NSString * show;
@property(nonatomic, copy) NSString * mark;
@property(nonatomic, copy) NSString * extend;
@property(nonatomic, copy) NSString * child;
@property(nonatomic, copy) NSString * childids;
@property(nonatomic, copy) NSString * target;
@property(nonatomic, copy) NSString * displayorder;
@property (nonatomic, assign) BOOL isSelcted;
@end
