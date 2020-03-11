//
//  MiningHomeCell.h
//  Manhattan
//
//  Created by Apple on 2018/8/16.
//  Copyright © 2018年 Apple. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "XHHMinHomeListModel.h"
@interface MiningHomeCell : UITableViewCell

-(void)reloadCellWithDic:(NSDictionary *)dic row:(NSInteger)row;

@property (nonatomic , strong) XHHMinHomeListModel *model;

@end
