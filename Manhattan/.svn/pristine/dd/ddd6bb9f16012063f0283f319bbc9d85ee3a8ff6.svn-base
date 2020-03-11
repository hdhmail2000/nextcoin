//
//  NSString+Custom.m
//  Utils
//
//  Created by Howe on 2018/8/4.
//  Copyright © 2018年 Howe. All rights reserved.
//

#import "NSString+Custom.h"

@implementation NSString (Custom)
+ (NSString *)moneyFormat:(NSString *)money
{
    NSArray *moneys = [money componentsSeparatedByString:@"."];
    if (moneys.count > 2) {
        return money;
    }
    else if (moneys.count < 2) {
        return [self stringFormatToThreeBit:money];
    }
    else {
        NSString *frontMoney = [self stringFormatToThreeBit:moneys[0]];
        if([frontMoney isEqualToString:@""]){
            frontMoney = @"0";
        }
        return [NSString stringWithFormat:@"%@.%@", frontMoney,moneys[1]];
    }
}

+ (NSString *)stringFormatToThreeBit:(NSString *)string
{
    if (string.length <= 0) {
        return @"".mutableCopy;
    }
    
    NSString *tempRemoveD = [string stringByReplacingOccurrencesOfString:@"," withString:@""];
    NSMutableString *stringM = [NSMutableString stringWithString:tempRemoveD];
    NSInteger n = 2;
    for (NSInteger i = tempRemoveD.length - 3; i > 0; i--) {
        n++;
        if (n == 3) {
            [stringM insertString:@"," atIndex:i];
            n = 0;
        }
    }
    
    return stringM;
}
+ (NSString *)hiddenMiddenString:(NSString *)string
{
    if (string.length >= 3)
    {
        int i = (int)string.length/3;
        return [string stringByReplacingCharactersInRange:NSMakeRange(i+1, i) withString:@"****"];
    }
    return nil;
}

+ (NSString *)phoneString:(NSString *)phone
{
    if (phone.length == 11) {
        return [phone stringByReplacingCharactersInRange:NSMakeRange(3, 4) withString:@"****"];
    }
    return nil;
}

+ (NSString *)numberFormatString:(id)string
{
    double number = 0.0;
    if ([string isKindOfClass:[NSNumber class]])
    {
        NSNumber *num = string;
        number = [num doubleValue];
    }else
        if ([string isKindOfClass:[NSString class]])
        {
            NSString *num = string;
            number = [num doubleValue];
        }else
        {
            return nil;
        }
    if (number <10000.0f) {
        return [NSString stringWithFormat:@"%.0f",number];
    }
    return [NSString stringWithFormat:@"%.1fw",number/10000];
}

//汉字的拼音
- (NSString *)pinyin
{
    NSMutableString *str = [self mutableCopy];
    CFStringTransform(( CFMutableStringRef)str, NULL, kCFStringTransformMandarinLatin, NO);
    CFStringTransform((CFMutableStringRef)str, NULL, kCFStringTransformStripDiacritics, NO);
    return [str stringByReplacingOccurrencesOfString:@" " withString:@""];
}


@end
