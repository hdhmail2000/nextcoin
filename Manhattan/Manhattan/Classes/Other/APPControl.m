//
//  APPControl.m
//  Manhattan
//
//  Created by Apple on 2018/9/10.
//  Copyright © 2018年 Apple. All rights reserved.
//

#import "APPControl.h"
#import "XHHLoginViewController.h"
#import "BaseNavigationController.h"
#import "ChZTabBarController.h"
#import "XHHSafeCenterModel.h"
@implementation APPControl

SingletonM(APPControl)
-(AppDelegate *)appDelegate{
   return (AppDelegate *) [[UIApplication sharedApplication] delegate];
}
- (UserModel *)user
{
    if (_user == nil) {
        _user = [UserModel chz_getObjForKey:kv_USER_MODEL];
    }
    return _user;
}
- (void)toLogin
{
    self.isLogin = NO;
    self.user = nil;
    [self.user chz_clear];
    XHHLoginViewController *loginVC = [XHHLoginViewController initWithStoryboard:@"LoginStroy"];
    BaseNavigationController *loginNav = [[BaseNavigationController alloc] initWithRootViewController:loginVC];
    self.appDelegate.window.rootViewController = loginNav;
}

- (void)toHome
{
    self.appDelegate.window.rootViewController = ChZTabBarController.new;
}
- (NSString *)cny
{
    if (!ChZ_StringIsEmpty(_cny)) {
        [self requestCNY];
    }
    return _cny;
}
- (void)requestCNY
{
    ChZ_Weak
    [ChZHomeRequest requestCNYSuccessBlock:^(id dataSource)
     {
         ChZ_Strong
         _strongSelf.cny = dataSource;
         
     } errorBlock:nil];
}
-(BOOL)isSetPayPassword
{
    [self requestPayPassword];
    return _isSetPayPassword;
}
-(void)requestPayPassword
{
    @weakify(self);
    [XHHSafeCenterRequest requestSecureSettingDetailSuccessBlock:^(id dataSource)
     {
         @strongify(self);
         HCCenterSecuritySettingWrapperModel *model = dataSource;
         self.isSetPayPassword = model.bindTrade;
     } errorBlock:^(int code, NSString *error)
     {
         
     }];
}
- (void)requestUSDT:(NSArray *)areaList
{
    if (self.listArray != nil) {
        return;
    }
    NSString *code;
    for (ChZMarketAreaModel *model in areaList)
    {
        if ([model.name isEqualToString:@"USDT"])
        {
            code = model.code;
            break;
        }
    }
    if (ChZ_StringIsEmpty(code))
    {
        ChZ_Weak
        [ChZHomeRequest requestAreaList:code successBlock:^(id dataSource)
         {
             ChZ_Strong
             [_strongSelf _handleMarket:dataSource];
         } errorBlock:^(int code, NSString *error)
         {
         }];
    }
}
- (void)_handleMarket:(NSArray *)array
{
    self.listArray = array;
    for (ChZMarketListModel *model in array)
    {
        [model syncCoin];
    }
}

- (void)checkUpdate
{
    NSString *downURL = @"https://fir.im/mhtxapp";
    NSString *appId = @"5baa1506959d695cf9e70779";
    NSString *tokenId = @"ab40f086d83cc05bbcb07fa93505cb14";
    NSString *url = [NSString stringWithFormat:@"https://api.fir.im/apps/latest/%@?api_token=%@",appId,tokenId];
    [ChZBaseHttpRequest GetWithURL:url parameters:nil progress:nil success:^(NSURLSessionDataTask * _Nonnull task, id  _Nullable responseObject)
     {
         NSDictionary *dic = responseObject;
         if (dic != nil && dic.count != 0)
         {
             NSString * localVersionString = [ChZUtil chz_AppVersion];
             NSString * version = [dic objectForKey:@"versionShort"];
             if (!ChZ_StringIsEmpty(localVersionString) || !ChZ_StringIsEmpty(version))return ;
             localVersionString = [localVersionString stringByReplacingOccurrencesOfString:@"." withString:@""];
             version = [version stringByReplacingOccurrencesOfString:@"." withString:@""];
             NSInteger serverVersion = [version integerValue];
             NSInteger localVersion = [localVersionString integerValue];
             if (serverVersion > localVersion) {
                 
                 UIAlertController *alertVC = [UIAlertController alertControllerWithTitle:@"提示" message:@"检查到有版本更新，是否前往更新？" preferredStyle:UIAlertControllerStyleAlert];
                 [alertVC addAction:[UIAlertAction actionWithTitle:@"取消" style:UIAlertActionStyleCancel handler:nil]];
                 [alertVC addAction:[UIAlertAction actionWithTitle:@"前往更新" style:UIAlertActionStyleDefault handler:^(UIAlertAction * _Nonnull action) {
                     [[UIApplication sharedApplication] openURL:[NSURL URLWithString:downURL]];
                 }]];
                 [[APPControl sharedAPPControl].appDelegate.window.rootViewController presentViewController:alertVC animated:YES completion:nil];
             }
         }
     } failure:^(NSURLSessionDataTask * _Nullable task, NSError * _Nonnull error)
     {
         ChZ_DebugLog(@"版本请求错误");
     }];
    
}

@end
