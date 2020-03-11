//
//  UITableViewCell+Register.m
//  lzg
//
//  Created by Howe on 2017/10/11.
//  Copyright © 2017年 Howe. All rights reserved.
//

#import "UITableViewCell+Register.h"

@implementation UITableViewCell (Register)
+ (void)registerCellWithTableView:(UITableView *)tableView identifier:(NSString *)identifier nibName:(NSString *)nibName
{
    if (!ChZ_StringIsEmpty(identifier))return;
    if (ChZ_StringIsEmpty(nibName))
    {
//        NSString *nibName = NSStringFromClass([self class]);
        UINib *nib = [UINib nibWithNibName:nibName bundle:[NSBundle mainBundle]];
        [tableView registerNib:nib forCellReuseIdentifier:identifier];
    }else
    {
        if (!ChZ_StringIsEmpty(identifier))return;
        [tableView registerClass:[self class] forCellReuseIdentifier:identifier];
    }
}
@end
