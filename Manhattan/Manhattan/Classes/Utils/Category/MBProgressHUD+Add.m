//
//  MBProgressHUD+Add.m
//
//  Created by mj on 13-4-18.
//  Copyright (c) 2013年 itcast. All rights reserved.
//

#import "MBProgressHUD+Add.h"

@implementation MBProgressHUD (Add)

+ (void)show:(NSString *)text icon:(NSString *)icon view:(UIView *)view
{
//    if (view == nil) view = [UIApplication sharedApplication].keyWindow;
    if (view == nil) view = [self showView];
    MBProgressHUD *hud = [MBProgressHUD showHUDAddedTo:view animated:YES];
    hud.label.text = text;
    hud.customView = [[UIImageView alloc] initWithImage:[UIImage imageNamed:[NSString stringWithFormat:@"MBProgressHUD.bundle/%@", icon]]];
    hud.mode = MBProgressHUDModeCustomView;
    hud.animationType = MBProgressHUDAnimationZoom;
    hud.removeFromSuperViewOnHide = YES;
    
    [hud hideAnimated:YES afterDelay:1.0];
}

+ (void)showError:(NSString *)error toView:(UIView *)view{
    [self show:error icon:@"error.png" view:view];
}

+ (void)showSuccess:(NSString *)success toView:(UIView *)view
{
    [self show:success icon:@"success.png" view:view];
}

+ (MBProgressHUD *)showMessag:(NSString *)message toView:(UIView *)view {
//    if (view == nil) view = [UIApplication sharedApplication].keyWindow;
    if (view == nil) view = [self showView];
    MBProgressHUD *hud = [MBProgressHUD showHUDAddedTo:view animated:YES];
    hud.label.text = message;
    hud.animationType = MBProgressHUDAnimationZoom;
//    hud.removeFromSuperViewOnHide = YES;
//    hud.dimBackground = YES;
    return hud;
}


+ (void)showError:(NSString *)error
{
    [self showError:error toView:[self showView]];
}
+ (void)showSuccess:(NSString *)success
{
    [self showSuccess:success toView:[self showView]];
    
}
+ (void)showMessag:(NSString *)message
{
    [self showMessag:message toView:[self showView]];
}


+ (BOOL)dismss
{
    return [MBProgressHUD hideHUDForView:[self showView] animated:YES];
}

+ (UIView *)showView
{
    
    return [UIApplication sharedApplication].keyWindow;
    
//    if ([UIApplication sharedApplication].keyWindow.rootViewController)
//    {
//        return  [UIApplication sharedApplication].keyWindow.rootViewController.view;
//    }else
//    {
//        return [UIApplication sharedApplication].keyWindow;
//    }
}


@end
