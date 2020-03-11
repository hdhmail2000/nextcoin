//
//  XHHPayPwController.h
//  Manhattan
//
//  Created by Apple on 2018/8/16.
//  Copyright © 2018年 Apple. All rights reserved.
//

#import "BaseViewController.h"
typedef NS_ENUM(NSInteger,SetPayWordType){
    SetPayWordTypeNoSet = 1,//设置交易密码
    SetPayWordTypeHadSet = 2,//重设交易密码
    SetPayWordTypeFroget = 3// 忘记交易密码
};

@interface XHHPayPwController : BaseViewController

@property (assign, nonatomic) NSInteger type;//1 设置交易密码  2. 重设交易密码  3.忘记交易密码

@property (assign , nonatomic) SetPayWordType payWordSetType;


@end
