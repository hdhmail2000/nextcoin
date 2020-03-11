//
//  XHHMovePointView.h
//  Manhattan
//
//  Created by Apple on 2018/8/30.
//  Copyright © 2018年 Apple. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "XHHMiningHomeModel.h"


typedef NS_ENUM(NSInteger , XHHRoteType)
{
    XHHRoteTypeRoteAndBubble,
    XHHRoteTypeUpDownAndBubble,
    XHHRoteTypeUpDownAnRoteAnddBubble,
    XHHRoteTypeWithnotBubbleAndRote
};

@interface XHHMovePointView : UIView



@property (nonatomic , strong) XHHMiningHomeModel *model;

@property (copy , nonatomic)  void (^clickBlock)(XHHMiningHomeModel *model);

@property (nonatomic , assign) XHHRoteType isRotate;

-(void)moddleImage:(UIImage *)image;
-(void)topImage:(UIImage *)image;
-(void)lightColor:(UIColor *)color;



-(void)beginRotate;

-(void)imageUpDown;

- (void)_removeTimer;

- (void)showImageRotate;

- (void)removeImageRotate;
@end
