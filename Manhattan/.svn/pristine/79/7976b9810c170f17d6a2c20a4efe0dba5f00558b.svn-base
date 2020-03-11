//
//  UIView+Custom.m
//  CoinWallet
//
//  Created by Howe on 2018/5/14.
//  Copyright © 2018年 Howe. All rights reserved.
//

#import "UIView+Custom.h"

@implementation UIView (Custom)

- (UIViewController *)viewController
{
    for (UIView * next = [self superview]; next; next = next.superview)
    {
        UIResponder * nextResponder = [next nextResponder];
        if ([nextResponder isKindOfClass:[UIViewController class]]) {
            return (UIViewController *)nextResponder;
        }
    }
    return nil;
}

+ (instancetype)nibLoadSelf
{
    NSString *className = NSStringFromClass([self class]);
    NSArray *viewArray = [[NSBundle mainBundle]loadNibNamed:className owner:nil options:nil];
    if (viewArray && viewArray.count >0)
    {
        return [viewArray lastObject];
    }
    return nil;
}
+(instancetype)loadViewWithNibName:(NSString *)nibName atIndex:(NSInteger)index{
    
    NSArray *nibs = [[UINib nibWithNibName:nibName bundle:nil] instantiateWithOwner:nil options:nil];
    return  nibs[index];
    
}
@end
