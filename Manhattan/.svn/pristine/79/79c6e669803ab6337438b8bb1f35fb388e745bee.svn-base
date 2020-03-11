//
//  NSString+Custom.h
//  Utils
//
//  Created by Howe on 2018/8/4.
//  Copyright © 2018年 Howe. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface NSString (Custom)


/**
 金额格式化
 123456.12321 =>123,456.123,21
 @param money 金额
 @return 格式化金额
 */
+ (NSString *)moneyFormat:(NSString *)money;

+ (NSString *)stringFormatToThreeBit:(NSString *)string;

/**
 隐藏电话中间部分
 13512345678 => 135****5678
 @param phone 电话
 @return 电话
 */
+ (NSString *)phoneString:(NSString *)phone;

+ (NSString *)hiddenMiddenString:(NSString *)string;


/**
 格式化大额数字
 10000 =>1w
 @param string 数字
 @return 数字
 */
+ (NSString *)numberFormatString:(id)string;


/**
 汉字的拼音

 @return 汉字的拼音
 */
- (NSString *)pinyin;
@end
