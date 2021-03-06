//
//  XHHMiningHeadView.h
//  Manhattan
//
//  Created by Apple on 2018/8/16.
//  Copyright © 2018年 Apple. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "XHHMiningHomeModel.h"
@interface XHHMiningHeadView : UIView

@property (nonatomic , strong) NSArray *dataArray;

@property (nonatomic , strong) NSArray *bannerArray;

@property (nonatomic , strong) NSString *workNumber;

@property (copy , nonatomic) void (^miningAreaBlock)(void);

@end
