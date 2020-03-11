//
//  XHHNotesListModel.h
//  FuturePurse
//
//  Created by Apple on 2018/8/10.
//  Copyright © 2018年 jbtm. All rights reserved.
//

#import "BaseObject.h"

@interface XHHNotesListModel : BaseObject

@property (nonatomic , copy) NSString *fid;
@property (nonatomic , copy) NSString *catid;
@property (nonatomic , copy) NSString *title;
@property (nonatomic , copy) NSString *thumb;
@property (nonatomic , copy) NSString *inputtime;
@property (nonatomic , copy) NSString *hits;
@property (nonatomic , copy) NSString *desc;

@end
