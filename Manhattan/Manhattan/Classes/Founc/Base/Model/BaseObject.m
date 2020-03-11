//
//  BaseObject.m
//  tonglian
//
//  Created by Howe on 2018/6/3.
//  Copyright © 2018年 Howe. All rights reserved.
//

#import "BaseObject.h"

@implementation BaseObject
MJCodingImplementation
+ (NSDictionary *)mj_replacedKeyFromPropertyName
{
    return @{@"fid":@"id",@"desc":@"description",@"defau":@"default",@"number":@"value",@"userid":@"fid"};
}
@end
