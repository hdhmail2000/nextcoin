//
//  UITableViewCell+Register.h
//  lzg
//
//  Created by Howe on 2017/10/11.
//  Copyright © 2017年 Howe. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface UITableViewCell (Register)
+ (void)registerCellWithTableView:(UITableView *)tableView identifier:(NSString *)identifier nibName:(NSString *)nibName;
@end
