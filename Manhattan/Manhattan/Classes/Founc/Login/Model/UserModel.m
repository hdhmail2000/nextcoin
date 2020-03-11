//
//  UserModel.m
//  css
//
//  Created by Howe on 2018/4/2.
//  Copyright © 2018年 Howe. All rights reserved.
//

#import "UserModel.h"

@implementation UserModel
MJCodingImplementation
-(void)chz_synchronize{
    [self chz_saveObjToKye:kv_USER_MODEL];
}
-(void)chz_clear{
    [UserModel chz_removeObjForKey:kv_USER_MODEL];
}
@end
