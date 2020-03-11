//
//  XHHTradReleaseView.h
//  FuturePurse
//
//  Created by Apple on 2018/7/30.
//  Copyright © 2018年 jbtm. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface XHHTradReleaseView : UIView

@property (strong, nonatomic) NSMutableArray      *dataArray;

@property (strong, nonatomic) UITableView  *tableView;

@property (strong, nonatomic) void (^downLineIdBlock)(NSString *downLineId);

@end
