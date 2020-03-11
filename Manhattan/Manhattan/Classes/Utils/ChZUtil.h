//
//  ChZUtil.h
//  zubu
//
//  Created by ChZ on 2016/10/11.
//  Copyright © 2016年 ChZ. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface ChZUtil : NSObject

+ (void)telPhone:(NSString *)phoneNumber;

//==========================>时间<=========================

+ (NSString *)chz_TimeStringWtihDate:(NSDate *)date;


+ (NSString *)chz_dateStringWtihDate:(NSDate *)date;

+ (NSString *)chz_dateTimeStringWtihDate:(NSDate *)date;
/**
 *  根据时间戳返回时间
 *
 */
+ (NSString *)chz_getTimeWithTimeIntervalSince1970:(NSString *)time;

/**
 根据时间戳返回日期
 
 */
+ (NSString *)chz_getDateWithTimeIntervalSince1970:(NSString *)time;
/**
 根据时间戳返回日期 和时间
 
 */
+ (NSString *)chz_getDateAndTimeWithTimeIntervalSince1970:(NSString *)time;

/**
 自定义时间戳
 
 */
+ (NSString *)chz_getDateWithTimeIntervalSince1970:(NSString *)time timeStyle:(NSString *)timeStyle;
/**
 *  获取当前时间
 *  yyyy-MM-dd HH:mm:ss
 */
+ (NSString *)chz_getNowDateString;
/**
 *  获取时间
 *  HH:mm:ss
 */
+ (NSString *)chz_getNowTimeString;

/**
 格式化时间

 @param time 时间戳
 @return 刚刚 昨天、、、
 */
+ (NSString *)chz_formateDate:(NSString *)time;

//==========================>正则校验<=========================
/**
 *  检查是否是正整数
 *
 */
+ (BOOL)chz_isNumber:(NSString *)text;
/**
 *  检查身份证
 *
 */
+ (BOOL)chz_isIdNumber:(NSString *)text;
/**
 *  检查电话号码
 *
 */
+ (BOOL)chz_isTelPhoneNumber:(NSString *)text;

/**
 * 检查是否是字母和数字
 **/
+ (BOOL)chz_isNumberAndLetter:(NSString *)text;

/**
 * 检查币数量
 **/
+ (BOOL)chz_isCoinNumber:(NSString *)text;
/**
 *  检查金额
 *
 */
+ (BOOL)chz_isAmount:(NSString *)text;
/**
 *  正则表达式
 *
 *  @param regex 正则
 *  @param text  需要检查的字符
 *
 */
+ (BOOL)chz_preedicateWithRegex:(NSString *)regex text:(NSString *)text;

//==========================>数字处理<=========================


///**
// 处理数字
//
// @param number 数字
// @param levelNumber 数字等级
// @param suffx 后缀
// @return
// */
//+ (NSString *)chz_number:(NSInteger)number levelNumber:(NSInteger)levelNumber decimal:(int)decimal suffx:(NSString *)suffx;

//==========================>字符处理<=========================
/**
 *  MD5加密
 *
 *  @param str str description
 *
 */
+ (NSString *)chz_MD5WithString:(NSString *)str;



/**
 10进制转为16进制

 @param decimal 10进制
 @return 16
 */
+ (NSString *)chz_HexByDecimal:(NSInteger)decimal;
//==========================>文件处理<=========================
/**
 *  获取用户文档路径
 *
 */
+ (NSString *)chz_documentsPath;

/**
 *  获取缓存路径
 *
 */
+ (NSString *)chz_cachePath;


/**
 *   项目版本号
 *
 */
+ (NSString *)chz_AppVersion;

/**
 *  版本号， 返回3位整数
 *
 */
+ (NSInteger)chz_VersionOfDevice;


/**
 计算文字大小
 
 @param string 字符串
 @param font 字体
 @param constraintSize 占据内容大小
 @return 大小
 */
+ (CGSize)chz_labelSizeWithString:(NSString *)string font:(UIFont * )font width:(CGFloat)width;

/**
 计算文字大小

 @param string 字符串
 @param font 字体
 @param constraintSize 占据内容大小
 @return 大小
 */
+ (CGSize)chz_sizeWithString:(NSString *)string font:(UIFont * )font constraintSize:(CGSize)constraintSize;


/**
 设置文字属性

 @param string 字符串
 @param font 字体
 @param lineSpacing 行间距
 @param columnSpacing 列间距
 @param textAlignment 对齐方式
 @return NSAttributedString
 */
+ (NSAttributedString *)chz_setStringStyle:(NSString *)string font:(UIFont *)font lineSpacing:(CGFloat)lineSpacing columnSpacing:(CGFloat)columnSpacing alignment:(NSTextAlignment)textAlignment;


/**
 计算文字大小

 @param string 字符串
 @param contentSize 预估占用大小
 @param font 字体
 @param lineSpacing 行间距
 @param columnSpacing 列间距
 @param textAlignment 对齐方式
 @return size
 */
+ (CGSize)chz_sizeWithString:(NSString *)string size:(CGSize)contentSize font:(UIFont *)font lineSpacing:(CGFloat)lineSpacing columnSpacing:(CGFloat)columnSpacing alignment:(NSTextAlignment)textAlignment;


/**
 哈希

 @param text 文字
 @return 哈希
 */
+ (NSString *) chz_sha1:(NSString *)text;

+ (NSString *)formatterDate:(NSString *)dateString;

+ (NSString *)formateDate:(NSString *)dateString withFormate:(NSString *) formate;

+ (BOOL)isIphoneX;

//获取文字高度
+ (CGFloat)setDetailHH:(NSString *)detail width:(CGFloat)width font:(CGFloat)font;
+ (UIViewController *)getCurrentVC;

@end
