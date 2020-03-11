//
//  APPPublicDefineConfig.h
//  CoinWallet
//
//  Created by Howe on 2018/5/15.
//  Copyright © 2018年 Howe. All rights reserved.
//

#ifndef APPPublicDefineConfig_h
#define APPPublicDefineConfig_h

//--------------------------全局Block定义区----------------------------------
//用于回调
typedef void (^ChZ_CallBackBlock)(id Obj);
//用于cell 等类型回调
typedef void (^ChZ_CellSelectedCallBackBlock)(NSInteger index , id obj ,int type);
/**
 * callObj 回调的对象。value 带回的值
 **/
typedef void (^ChZ_HandleCallBackBlock)(int type,id callObj ,id value);

typedef void (^ChZ_SuccessBlock)(id dataSource);

typedef void (^ChZ_ErrorBlock)(NSString *error);

typedef void (^ErrorCodeBlock)(int code,NSString *error);

typedef void (^ChZ_ErrorCodeBlock)(int code,NSString *error);

#define ChZ_ErrorBlock(message) if (errorBlock){ errorBlock(message); }

#define ChZ_NotDataBlock if (successBlock)\
{\
successBlock(nil);\
}

#define ChZ_DataBlock(obj) if (successBlock)\
{\
successBlock(obj);\
}
//--------------------------尺寸定义区----------------------------------

//屏幕宽高
#define ChZ_WIDTH    [UIScreen mainScreen].bounds.size.width
#define ChZ_HEIGHT   [UIScreen mainScreen].bounds.size.height
#define ChZ_STATUSH  [UIApplication sharedApplication].statusBarFrame.size.height

//---------------------------方法定义区-----------------------------------

//============>颜色方法<===============

//RGB颜色 不带透明度
#define  ChZ_Color(r, g, b) [UIColor colorWithRed:(r)/255.0 green:(g)/255.0 blue:(b)/255.0 alpha:1.0]
//RGB颜色 带透明度
#define  ChZ_ColorRGBA(r, g, b, a) [UIColor colorWithRed:(r)/255.0 green:(g)/255.0 blue:(b)/255.0 alpha:a]
//随机色  不带透明度
#define  ChZ_RandomColor ChZ_Color(arc4random_uniform(256), arc4random_uniform(256), arc4random_uniform(256))
//随机色  带透明度
#define ChZ_RandomColorAlpha(alpha) ChZ_ColorRGBA(arc4random_uniform(256), arc4random_uniform(256), arc4random_uniform(256),alpha)

//============>日志方法<===============

//错误打印
#ifdef DEBUG

#define ChZ_ErrorLog(format, ...) NSLog(@"\n################### Error ################## \n line %d in function:%s\n" format,__LINE__,__FUNCTION__,## __VA_ARGS__)
#else

#define ChZ_ErrorLog(format, ...)

#endif

//信息打印
#ifdef DEBUG

#define ChZ_DebugLog(format, ...) NSLog(@"\n################### Msg ################### \n line %d in function:%s\n" format,__LINE__,__FUNCTION__,## __VA_ARGS__)

#else

#define ChZ_DebugLog(format, ...)

#endif


//打印
#ifdef DEBUG

#define ChZ_InfoLog(format, ...) NSLog(@"\n################### Info ################### \n line %d in function:%s\n" format,__LINE__,__FUNCTION__,## __VA_ARGS__)

#else

#define ChZ_InfoLog(format, ...)

#endif

//============>HUD显示<===============

#define ChZ_MBError(msg)    [MBProgressHUD showError:msg];

#define ChZ_MBMsg(msg)      [MBProgressHUD showMessag:msg];

#define ChZ_MBSuccess(msg)  [MBProgressHUD showSuccess:msg];

#define ChZ_MBDismss        [MBProgressHUD dismss];

#define ChZ_MBDismssError(msg)  [MBProgressHUD dismss];\
[MBProgressHUD showError:msg];

#define ChZ_MBDismssMsg(msg)    [MBProgressHUD dismss];\
[MBProgressHUD showMessag:msg];

#define ChZ_MBDismssSuccess(msg) [MBProgressHUD dismss];\
[MBProgressHUD showSuccess:msg];

//============>空判断方法<===============

//判断字符串是否为空
#define ChZ_StringIsEmpty(value) ((value != nil && ![value isKindOfClass:[NSNull class]] && [value stringByTrimmingCharactersInSet:[NSCharacterSet whitespaceCharacterSet]].length != 0 && value.length != 0 ) ? YES : NO)

#define ChZ_CoordinateIsNull(value) ((value.latitude == 0.0f || value.longitude == 0.0f) ? YES : NO)

#define ChZ_IsiPhoneX ([UIScreen instancesRespondToSelector:@selector(currentMode)] ? CGSizeEqualToSize(CGSizeMake(1125, 2436), [[UIScreen mainScreen] currentMode].size) : NO)

#define ChZ_StringByAddingPercentEncoding(string,characterSet) [[string stringByAddingPercentEncodingWithAllowedCharacters:[NSCharacterSet characterSet]] mutableCopy]
//============>Block<===============

#define ChZ_Weak __weak __typeof(self) _self = self;

#define ChZ_Strong __strong __typeof(_self) _strongSelf = _self;\
if(!_strongSelf)return;

#define ChZ_ReturnStrong __strong __typeof(_self) _strongSelf = _self;\
if(!_strongSelf)return NO;

#define ChZ_ReturnYESStrong __strong __typeof(_self) _strongSelf = _self;\
if(!_strongSelf)return YES;
typedef NS_ENUM(NSInteger , C2CTradType)
{
    C2CTradTypeSell = 1,//卖出
    C2CTradTypeBuy = 2 //买入
};

#define TLLiveAvatarImagePlaceHolder  [UIImage imageNamed:@"default_avatar_user"]
#define TLLiveBackGroudImagePlaceHolder  [UIImage imageNamed:@"test_video"]
#define TLLiveImagePlaceHolderNone  [UIImage imageNamed:@""]


#endif /* APPPublicDefineConfig_h */