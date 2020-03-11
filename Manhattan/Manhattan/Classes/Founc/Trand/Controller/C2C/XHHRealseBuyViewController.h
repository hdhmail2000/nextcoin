//
//  XHHRealseBuyViewController.h
//  Manhattan
//
//  Created by Apple on 2018/9/11.
//  Copyright © 2018年 Apple. All rights reserved.
//

#import "BaseViewController.h"

@interface XHHRealseBuyViewController : BaseViewController

@property (nonatomic , strong) NSArray *cnyList;

@property (nonatomic , strong) NSString *cnyId;

@property (copy , nonatomic) void (^buySuccessBlock)(void);

@end
