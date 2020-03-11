//
//  ChZUtil.m
//  zubu
//
//  Created by ChZ on 2016/10/11.
//  Copyright © 2016年 ChZ. All rights reserved.
//

#import "ChZUtil.h"
#import "sys/utsname.h"
#import <CommonCrypto/CommonDigest.h>


@implementation ChZUtil

+ (void)telPhone:(NSString *)phoneNumber
{
    if (ChZ_StringIsEmpty(phoneNumber))
    {
        NSString *url = [NSString stringWithFormat:@"telprompt://%@",phoneNumber];
        [[UIApplication sharedApplication] openURL:[NSURL URLWithString:url]];
    }
}

+ (NSString *)chz_TimeStringWtihDate:(NSDate *)date
{
    NSDateFormatter *formatter = [[NSDateFormatter alloc] init];
    [formatter setDateStyle:NSDateFormatterMediumStyle];
    [formatter setTimeStyle:NSDateFormatterShortStyle];
    [formatter setDateFormat:@"HH:mm:ss"];
    NSTimeZone* timeZone = [NSTimeZone timeZoneWithName:@"Asia/Shanghai"];
    [formatter setTimeZone:timeZone];
    NSString *trTime = [formatter stringFromDate:date];
    return trTime;
}


+ (NSString *)chz_dateStringWtihDate:(NSDate *)date
{
    NSDateFormatter *formatter = [[NSDateFormatter alloc] init];
    [formatter setDateStyle:NSDateFormatterMediumStyle];
    [formatter setTimeStyle:NSDateFormatterShortStyle];
    [formatter setDateFormat:@"YYYY-MM-dd"];
    NSTimeZone* timeZone = [NSTimeZone timeZoneWithName:@"Asia/Shanghai"];
    [formatter setTimeZone:timeZone];
    NSString *trTime = [formatter stringFromDate:date];
    return trTime;
}

+ (NSString *)chz_dateTimeStringWtihDate:(NSDate *)date
{
    NSDateFormatter *formatter = [[NSDateFormatter alloc] init];
    [formatter setDateStyle:NSDateFormatterMediumStyle];
    [formatter setTimeStyle:NSDateFormatterShortStyle];
    [formatter setDateFormat:@"YYYY-MM-dd HH:mm:ss"];
    NSTimeZone* timeZone = [NSTimeZone timeZoneWithName:@"Asia/Shanghai"];
    [formatter setTimeZone:timeZone];
    NSString *trTime = [formatter stringFromDate:date];
    return trTime;
}
+ (NSString *)chz_getTimeWithTimeIntervalSince1970:(NSString *)time;
{
    
    double timeInterval = [time doubleValue];
    NSDate *confromTimesp = [NSDate dateWithTimeIntervalSince1970:timeInterval];
    NSDateFormatter *formatter = [[NSDateFormatter alloc] init];
    [formatter setDateStyle:NSDateFormatterMediumStyle];
    [formatter setTimeStyle:NSDateFormatterShortStyle];
    [formatter setDateFormat:@"HH:mm:ss"];
    NSTimeZone* timeZone = [NSTimeZone timeZoneWithName:@"Asia/Shanghai"];
    [formatter setTimeZone:timeZone];
    NSString *trTime = [formatter stringFromDate:confromTimesp];
    return trTime;
}


+ (NSString *)chz_getDateWithTimeIntervalSince1970:(NSString *)time
{
    double timeInterval = [time doubleValue];
    NSDate *confromTimesp = [NSDate dateWithTimeIntervalSince1970:timeInterval];
    NSDateFormatter *formatter = [[NSDateFormatter alloc] init];
    [formatter setDateStyle:NSDateFormatterMediumStyle];
    [formatter setTimeStyle:NSDateFormatterShortStyle];
    [formatter setDateFormat:@"YYYY-MM-dd"];
    NSTimeZone* timeZone = [NSTimeZone timeZoneWithName:@"Asia/Shanghai"];
    [formatter setTimeZone:timeZone];
    NSString *trTime = [formatter stringFromDate:confromTimesp];
    return trTime;
}

+ (NSString *)chz_getDateAndTimeWithTimeIntervalSince1970:(NSString *)time;
{
    double timeInterval = [time doubleValue];
    NSDate *confromTimesp = [NSDate dateWithTimeIntervalSince1970:timeInterval];
    NSDateFormatter *formatter = [[NSDateFormatter alloc] init];
    [formatter setDateStyle:NSDateFormatterMediumStyle];
    [formatter setTimeStyle:NSDateFormatterShortStyle];
    [formatter setDateFormat:@"YYYY-MM-dd HH:mm:ss"];
    NSTimeZone* timeZone = [NSTimeZone timeZoneWithName:@"Asia/Shanghai"];
    [formatter setTimeZone:timeZone];
    NSString *trTime = [formatter stringFromDate:confromTimesp];
    return trTime;
}

+ (NSString *)chz_getDateWithTimeIntervalSince1970:(NSString *)time timeStyle:(NSString *)timeStyle
{
    if (!ChZ_StringIsEmpty(timeStyle))
    {
        return nil;
    }
    double timeInterval = [time doubleValue];
    NSDate *confromTimesp = [NSDate dateWithTimeIntervalSince1970:timeInterval];
    NSDateFormatter *formatter = [[NSDateFormatter alloc] init];
    [formatter setDateStyle:NSDateFormatterMediumStyle];
    [formatter setTimeStyle:NSDateFormatterShortStyle];
    [formatter setDateFormat:timeStyle];
    NSTimeZone* timeZone = [NSTimeZone timeZoneWithName:@"Asia/Shanghai"];
    [formatter setTimeZone:timeZone];
    NSString *trTime = [formatter stringFromDate:confromTimesp];
    return trTime;
}
/**
 *  获取当前时间
 *
 */
+ (NSString *)chz_getNowDateString
{
    NSDate *nowDate = [NSDate date];
    NSDateFormatter *formatter = [[NSDateFormatter alloc]init];
    [formatter setDateFormat:@"yyyy-MM-dd HH:mm:ss"];
    NSString *nowDateString = [formatter stringFromDate:nowDate];
    return nowDateString;
}

/**
 *  获取当前时间
 *
 */
+ (NSString *)chz_getNowTimeString
{
    NSDate *nowDate = [NSDate date];
    NSDateFormatter *formatter = [[NSDateFormatter alloc]init];
    [formatter setDateFormat:@"HH:mm:ss"];
    NSString *nowDateString = [formatter stringFromDate:nowDate];
    return nowDateString;
}


+ (NSString *)chz_formateDate:(NSString *)timeString
{
    @try {
        NSDateFormatter *dateFormatter = [[NSDateFormatter alloc] init];
        [dateFormatter setDateFormat:@"yyyy-MM-dd HH:mm:ss"];
        NSDate * nowDate = [NSDate date];
        NSTimeInterval inputTime =  [timeString doubleValue];
        
        NSDate * inputDate = [NSDate dateWithTimeIntervalSince1970:inputTime];
        NSString *inputDateString = [dateFormatter stringFromDate:inputDate];
        NSDate * needFormatDate = [dateFormatter dateFromString:inputDateString];
        NSTimeInterval time = [nowDate timeIntervalSinceDate:needFormatDate];
        NSString *dateStr = [[NSString alloc] init];
        
        if (time<=60) {
            dateStr = @"刚刚";
        }else if(time<=60*60){
            int mins = time/60;
            dateStr = [NSString stringWithFormat:@"%d分钟前",mins];
        }else if(time<=60*60*24){
            [dateFormatter setDateFormat:@"YYYY-MM-dd"];
            NSString * need_yMd = [dateFormatter stringFromDate:needFormatDate];
            NSString *now_yMd = [dateFormatter stringFromDate:nowDate];
            
            [dateFormatter setDateFormat:@"HH:mm"];
            if ([need_yMd isEqualToString:now_yMd]) {
                dateStr = [NSString stringWithFormat:@"今天 %@",[dateFormatter stringFromDate:needFormatDate]];
            }else{
                dateStr = [NSString stringWithFormat:@"昨天 %@",[dateFormatter stringFromDate:needFormatDate]];
            }
        }else {
            
            [dateFormatter setDateFormat:@"yyyy"];
            NSString * yearStr = [dateFormatter stringFromDate:needFormatDate];
            NSString *nowYear = [dateFormatter stringFromDate:nowDate];
            
            if ([yearStr isEqualToString:nowYear]) {
                //在同一年
                [dateFormatter setDateFormat:@"MM-dd"];
                dateStr = [dateFormatter stringFromDate:needFormatDate];
            }else{
                [dateFormatter setDateFormat:@"yyyy-MM-dd"];
                dateStr = [dateFormatter stringFromDate:needFormatDate];
            }
        }
        
        return dateStr;
    }
    @catch (NSException *exception)
    {
        return @"";
    }
}

+ (BOOL)chz_preedicateWithRegex:(NSString *)regex text:(NSString *)text
{
    NSPredicate *predicate = [NSPredicate predicateWithFormat:@"SELF MATCHES %@",regex];
    return [predicate evaluateWithObject:text];
}
+ (BOOL)chz_isCoinNumber:(NSString *)text
{
    NSString *regex = @"^[0-9]+(\\.[0-9]{1,6})?$";
    return [self chz_preedicateWithRegex:regex text:text];
}
+ (BOOL)chz_isAmount:(NSString *)text
{
    NSString *regex = @"^[0-9]+(\\.[0-9]{1,2})?$";
    return [self chz_preedicateWithRegex:regex text:text];
}
+ (BOOL)chz_isTelPhoneNumber:(NSString *)text
{
    NSString * ALL = @"^1(3|4|5|6|7|8|9)\\d{9}$";
    return [self chz_preedicateWithRegex:ALL text:text];
    
    /**
     * 手机号码
     * 移动：134[0-8],135,136,137,138,139,150,151,157,158,159,182,187,188
     * 联通：130,131,132,152,155,156,185,186
     * 电信：133,1349,153,180,189,181(增加)
     */
    NSString * MOBIL = @"^1(3[0-9]|5[0-35-9]|8[025-9])\\d{8}$";
    /**
     10         * 中国移动：China Mobile
     11         * 134[0-8],135,136,137,138,139,150,151,157,158,159,182,187,188
     12         */
    NSString * CM = @"^1(34[0-8]|(3[5-9]|5[017-9]|8[2378])\\d)\\d{7}$";
    /**
     15         * 中国联通：China Unicom
     16         * 130,131,132,152,155,156,185,186
     17         */
    NSString * CU = @"^1(3[0-2]|5[256]|8[56])\\d{8}$";
    /**
     20         * 中国电信：China Telecom
     21         * 133,1349,153,180,189,181(增加)
     22         */
    NSString * CT = @"^1((77|33|53|8[019])[0-9]|349)\\d{7}$";
    
    NSPredicate *regextestmobile = [NSPredicate predicateWithFormat:@"SELF MATCHES %@", MOBIL];
    NSPredicate *regextestcm = [NSPredicate predicateWithFormat:@"SELF MATCHES %@", CM];
    NSPredicate *regextestcu = [NSPredicate predicateWithFormat:@"SELF MATCHES %@", CU];
    NSPredicate *regextestct = [NSPredicate predicateWithFormat:@"SELF MATCHES %@", CT];
    
    if (([regextestmobile evaluateWithObject:text]
         || [regextestcm evaluateWithObject:text]
         || [regextestct evaluateWithObject:text]
         || [regextestcu evaluateWithObject:text]))
    {
        return YES;
    }
    return NO;
}


+ (BOOL)chz_isIdNumber:(NSString *)text
{
//    NSString *regex = @"\\d{15}|\\d{18}";
//    return [self chz_preedicateWithRegex:regex text:text];

    NSString *regex = @"^(\\d{14}|\\d{17})(\\d|[xX])$";
    return [self chz_preedicateWithRegex:regex text:text];
}

+ (BOOL)chz_isNumberAndLetter:(NSString *)text
{
    NSString * regex = @"^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,16}$";
    return [self chz_preedicateWithRegex:regex text:text];
}

+ (BOOL)chz_isNumber:(NSString *)text
{
    NSString *regex = @"^[0-9]\\d*$";
    return [self chz_preedicateWithRegex:regex text:text];;
}


//+ (NSString *)chz_number:(NSInteger)number
//             levelNumber:(NSInteger)levelNumber
//                 decimal:(NSInteger)decimal
//                   suffx:(NSString *)suffx
//{
//    // number 123432432 levelNumber 1000
//    //firstNum 123432
//    //lastNum 432
//    NSInteger firstNum = number / levelNumber;
//    NSInteger lastNum = number - firstNum * levelNumber;
//    for (int i = 0; i<decimal; i++)
//    {
//        
//    }
//    
//    
//}


+ (NSString *)chz_MD5WithString:(NSString *)str
{
    if(str == nil || str.length == 0)return @"";
    NSString *string = str;
    const char *cStr = [string UTF8String];
    unsigned char result[CC_MD5_DIGEST_LENGTH];
    
    CC_MD5( cStr, (int)strlen(cStr), result );
    
    return [NSString stringWithFormat:@"%02x%02x%02x%02x%02x%02x%02x%02x%02x%02x%02x%02x%02x%02x%02x%02x",
            result[0], result[1],
            result[2], result[3],
            result[4], result[5],
            result[6], result[7],
            result[8], result[9],
            result[10], result[11],
            result[12], result[13],
            result[14], result[15]
            ];
}


+ (NSString *)chz_HexByDecimal:(NSInteger)decimal
{
    
    NSString *hex =@"";
    NSString *letter;
    NSInteger number;
    for (int i = 0; i<9; i++) {
        
        number = decimal % 16;
        decimal = decimal / 16;
        switch (number) {
                
            case 10:
                letter =@"A"; break;
            case 11:
                letter =@"B"; break;
            case 12:
                letter =@"C"; break;
            case 13:
                letter =@"D"; break;
            case 14:
                letter =@"E"; break;
            case 15:
                letter =@"F"; break;
            default:
                letter = [NSString stringWithFormat:@"%ld", number];
        }
        hex = [letter stringByAppendingString:hex];
        if (decimal == 0) {
            
            break;
        }
    }
    return hex;
}

+ (NSString *)chz_documentsPath
{
    return [NSSearchPathForDirectoriesInDomains(NSDocumentDirectory, NSUserDomainMask, YES) lastObject];
}

+ (NSString *)chz_cachePath
{
    return [NSSearchPathForDirectoriesInDomains(NSCachesDirectory, NSUserDomainMask, YES) lastObject];
}

// 项目名称
+ (NSString *)chz_AppName
{
    NSString *bundleDisplayName = [[[NSBundle mainBundle] infoDictionary] objectForKey:@"CFBundleDisplayName"];
    if (!bundleDisplayName) {
        bundleDisplayName = [[[NSBundle mainBundle] infoDictionary] objectForKey:@"CFBundleName"];
    }
    
    return bundleDisplayName;
}


// 项目版本号
+ (NSString *)chz_AppVersion
{
    NSString *version = [[NSBundle mainBundle] objectForInfoDictionaryKey:@"CFBundleShortVersionString"];
    
    return version;
}



// 版本号， 返回3位整数
+ (NSInteger)chz_VersionOfDevice
{
    NSString *str = [UIDevice currentDevice].systemVersion;
    NSMutableArray *muArray = [NSMutableArray arrayWithArray:[str componentsSeparatedByString:@"."]];
    if (muArray.count < 3) {
        for (int i = 0; i < 3-muArray.count; i ++) {
            [muArray addObject:@"0"];
        }
    }
    NSString *string = [NSString stringWithFormat:@"%@", muArray[0]];
    for (int j = 1; j < muArray.count; j ++) {
        NSString *tempStr = [NSString stringWithFormat:@"%@", muArray[j]];
        string = [string stringByAppendingString:tempStr];
    }
    
    return [string intValue];
}


+ (CGSize)chz_labelSizeWithString:(NSString *)string font:(UIFont * )font  width:(CGFloat)width
{
    if (!ChZ_StringIsEmpty(string))
    {
        return CGSizeZero;
    }
    UILabel *label = [[UILabel alloc]init];
    label.font = font;
    label.text = string;
    label.numberOfLines = 0;
    return [label sizeThatFits:CGSizeMake(width, CGFLOAT_MAX)];
}

+ (CGSize)chz_sizeWithString:(NSString *)string font:(UIFont * )font constraintSize:(CGSize)constraintSize
{
    CGSize stringSize = CGSizeZero;
    
    NSDictionary *attributes = @{NSFontAttributeName:font};
    NSInteger options = NSStringDrawingUsesFontLeading | NSStringDrawingTruncatesLastVisibleLine | NSStringDrawingUsesLineFragmentOrigin;
    CGRect stringRect = [string boundingRectWithSize:constraintSize options:options attributes:attributes context:NULL];
    stringSize = stringRect.size;
    
    return stringSize;
}

+ (NSAttributedString *)chz_setStringStyle:(NSString *)string font:(UIFont *)font lineSpacing:(CGFloat)lineSpacing columnSpacing:(CGFloat)columnSpacing alignment:(NSTextAlignment)textAlignment
{
    NSMutableParagraphStyle *paraStyle = [[NSMutableParagraphStyle alloc] init];
//    contentLabel.lineBreakMode = ;
    paraStyle.lineBreakMode = NSLineBreakByTruncatingTail;
    paraStyle.alignment = textAlignment;
    paraStyle.lineSpacing = lineSpacing;
    paraStyle.hyphenationFactor = 1.0;
    paraStyle.firstLineHeadIndent = 0.0;
    paraStyle.paragraphSpacingBefore = 0.0;
    paraStyle.headIndent = 0;
    paraStyle.tailIndent = 0;
    NSDictionary *dic = @{NSFontAttributeName:font, NSParagraphStyleAttributeName:paraStyle, NSKernAttributeName:@(columnSpacing)};
    NSAttributedString *attributeStr = [[NSAttributedString alloc] initWithString:string attributes:dic];
    return attributeStr;
}

+ (CGSize)chz_sizeWithString:(NSString *)string size:(CGSize)contentSize font:(UIFont *)font lineSpacing:(CGFloat)lineSpacing columnSpacing:(CGFloat)columnSpacing alignment:(NSTextAlignment)textAlignment
{
    NSMutableParagraphStyle *paraStyle = [[NSMutableParagraphStyle alloc] init];
    paraStyle.lineBreakMode = NSLineBreakByCharWrapping;
    paraStyle.alignment = textAlignment;
    paraStyle.lineSpacing = lineSpacing;
    paraStyle.hyphenationFactor = 1.0;
    paraStyle.firstLineHeadIndent = 0.0;
    paraStyle.paragraphSpacingBefore = 0.0;
    paraStyle.headIndent = 0;
    paraStyle.tailIndent = 0;
    NSDictionary *dic = @{NSFontAttributeName:font, NSParagraphStyleAttributeName:paraStyle, NSKernAttributeName:@(columnSpacing)};
    CGSize size = [string boundingRectWithSize:contentSize options:NSStringDrawingUsesLineFragmentOrigin attributes:dic context:nil].size;
    return size;
}

+ (NSString *) chz_sha1:(NSString *)text
{
    NSData *data = [text dataUsingEncoding:NSUTF8StringEncoding];
    
    uint8_t digest[CC_SHA1_DIGEST_LENGTH];
    
    CC_SHA1(data.bytes, (unsigned int)data.length, digest);
    
    NSMutableString *output = [NSMutableString stringWithCapacity:CC_SHA1_DIGEST_LENGTH *2];
    
    for(int i=0; i<CC_SHA1_DIGEST_LENGTH; i++)
    {
        [output appendFormat:@"%02x", digest[i]];
    }
    return output;
}

+ (NSString *) formatterDate:(NSString *)dateString
{
    return [self  formateDate:dateString withFormate:@"YYYY-MM-dd HH:mm:ss"];
}

+ (NSString *)formateDate:(NSString *)dateString withFormate:(NSString *) formate
{
    
    @try {
        NSDateFormatter *dateFormatter = [[NSDateFormatter alloc] init];
        [dateFormatter setDateFormat:formate];
        
        NSDate * nowDate = [NSDate date];
        
        NSDate * needFormatDate = [dateFormatter dateFromString:dateString];
        NSTimeInterval time = [nowDate timeIntervalSinceDate:needFormatDate];
        
        //// 再然后，把间隔的秒数折算成天数和小时数：
        
        NSString *dateStr = @"";
        
        if (time<=60) {  // 1分钟以内的
            
            dateStr = @"刚刚";
        }else if(time<=60*60){  //  一个小时以内的
            
            int mins = time/60;
            dateStr = [NSString stringWithFormat:@"%d分钟前",mins];
            
        }else if(time<=60*60*24){   // 在两天内的
            
            [dateFormatter setDateFormat:@"YYYY-MM-dd"];
            NSString * need_yMd = [dateFormatter stringFromDate:needFormatDate];
            NSString *now_yMd = [dateFormatter stringFromDate:nowDate];
            
            [dateFormatter setDateFormat:@"HH:mm"];
            if ([need_yMd isEqualToString:now_yMd]) {
                // 在同一天
                dateStr = [NSString stringWithFormat:@"今天 %@",[dateFormatter stringFromDate:needFormatDate]];
                NSLog(@"%@", dateStr);
            }else{
                //  昨天
                dateStr = [NSString stringWithFormat:@"昨天 %@",[dateFormatter stringFromDate:needFormatDate]];
            }
        }else {
            
            [dateFormatter setDateFormat:@"yyyy"];
            NSString * yearStr = [dateFormatter stringFromDate:needFormatDate];
            NSString *nowYear = [dateFormatter stringFromDate:nowDate];
            
            if ([yearStr isEqualToString:nowYear]) {
                //  在同一年
                [dateFormatter setDateFormat:@"MM月dd日"];
                dateStr = [dateFormatter stringFromDate:needFormatDate];
            }else{
                [dateFormatter setDateFormat:@"yyyy-MM-dd"];
                dateStr = [dateFormatter stringFromDate:needFormatDate];
            }
            
        }
        
        return dateStr;
    }
    @catch (NSException *exception) {
        return @"";
    }
}
+ (BOOL)isIphoneX
{
    CGFloat w = [UIScreen mainScreen].bounds.size.width;
    CGFloat h = [UIScreen mainScreen].bounds.size.height;
    if(w == 375 && h == 812)
        return YES;
    return NO;
}
+ (CGFloat)setDetailHH:(NSString *)detail width:(CGFloat)width font:(CGFloat)font{
    CGFloat height = 0;
    height = [self getLabelHeightByWidth:width Title:detail font:[UIFont systemFontOfSize:font]];
    return height;
}
+ (CGFloat)getLabelHeightByWidth:(CGFloat)width Title:(NSString *)title font:(UIFont *)font {
    
    UILabel *label = [[UILabel alloc] initWithFrame:CGRectMake(0, 0, width, 0)];
    label.text = title;
    label.font = font;
    label.numberOfLines = 0;
    [label sizeToFit];
    CGFloat height = label.frame.size.height;
    
    return height;
}
+  (UIViewController *)getCurrentVC {
    UIViewController *rootViewController = [UIApplication sharedApplication].keyWindow.rootViewController;
    
    UIViewController *currentVC = [self getCurrentVCFrom:rootViewController];
    
    return currentVC;
}

+ (UIViewController *)getCurrentVCFrom:(UIViewController *)rootVC
{
    UIViewController *currentVC;
    
    if ([rootVC presentedViewController]) {
        // 视图是被presented出来的
        
        rootVC = [rootVC presentedViewController];
    }
    
    if ([rootVC isKindOfClass:[UITabBarController class]]) {
        // 根视图为UITabBarController
        
        currentVC = [self getCurrentVCFrom:[(UITabBarController *)rootVC selectedViewController]];
        
    } else if ([rootVC isKindOfClass:[UINavigationController class]]){
        // 根视图为UINavigationController
        
        currentVC = [self getCurrentVCFrom:[(UINavigationController *)rootVC visibleViewController]];
        
    } else {
        // 根视图为非导航类
        
        currentVC = rootVC;
    }
    
    return currentVC;
}
@end
