//
//  NSObject+Custom.h
//  Utils
//
//  Created by Howe on 2018/8/4.
//  Copyright © 2018年 Howe. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface NSObject (Custom)

- (BOOL)chz_saveObjToKye:(NSString *)key;

+ (BOOL)chz_removeObjForKey:(NSString *)key;

+ (instancetype)chz_getObjForKey:(NSString *)key;

@end
