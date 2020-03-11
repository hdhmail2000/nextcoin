//
//  XHHPayRecordFootView.h
//  Manhattan
//
//  Created by Apple on 2018/8/17.
//  Copyright © 2018年 Apple. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface XHHPayRecordFootView : UIView

@property(copy, nonatomic) void (^inBlock)(void);

@property(copy, nonatomic) void (^outBlock)(void);


@end
