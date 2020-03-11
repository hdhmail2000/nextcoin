//
//  NSObject+Custom.m
//  Utils
//
//  Created by Howe on 2018/8/4.
//  Copyright © 2018年 Howe. All rights reserved.
//

#import "NSObject+Custom.h"

@implementation NSObject (Custom)
- (BOOL)chz_saveObjToKye:(NSString *)key
{
    NSString *path = [[self class] path:key];
    if (!ChZ_StringIsEmpty(path))return NO;
    return [NSKeyedArchiver archiveRootObject:self toFile:path];
}
+ (instancetype)chz_getObjForKey:(NSString *)key
{
    NSString *path = [self path:key];
    if (!ChZ_StringIsEmpty(path))return nil;
    return [NSKeyedUnarchiver unarchiveObjectWithFile:path];
}

+ (BOOL)chz_removeObjForKey:(NSString *)key
{
    NSString *path = [self path:key];
    if (!ChZ_StringIsEmpty(path))return NO;
    NSFileManager *fileMgr = [NSFileManager defaultManager];
    BOOL isDirectory = NULL;
    if ([fileMgr fileExistsAtPath:path isDirectory:&isDirectory])
    {
        return [fileMgr removeItemAtPath:path error:NULL];
    }
    return NO;
}

+ (NSString *)path:(NSString *)key
{
    if (key == nil || key.length == 0)return nil;
    NSString *pathString = [[NSSearchPathForDirectoriesInDomains(NSDocumentDirectory, NSUserDomainMask, YES) lastObject] stringByAppendingPathComponent:@"ObjectSave"];
    NSFileManager *fileMgr = [NSFileManager defaultManager];
    if (![fileMgr fileExistsAtPath:pathString isDirectory:NULL])
    {
        [fileMgr createDirectoryAtPath:pathString withIntermediateDirectories:YES attributes:nil error:nil];
    }
    pathString = [NSString stringWithFormat:@"%@/%@.obj", pathString,key];
    return pathString;
}
@end
