//
//  XHHReleaseSellViewController.h
//  Manhattan
//
//  Created by Apple on 2018/9/11.
//  Copyright © 2018年 Apple. All rights reserved.
//

#import "BaseViewController.h"

@interface XHHReleaseSellViewController : BaseViewController

@property (nonatomic , strong) NSArray *cnyList;

@property (nonatomic , strong) NSString *cnyId;

@property (copy , nonatomic) void (^SellSuccessBlock)(void);

@end
