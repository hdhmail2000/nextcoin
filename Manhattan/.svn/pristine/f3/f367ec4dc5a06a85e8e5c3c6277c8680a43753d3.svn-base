//
//  AppDelegate.m
//  Manhattan
//
//  Created by Apple on 2018/8/14.
//  Copyright © 2018年 Apple. All rights reserved.
//

#import "AppDelegate.h"

#import "XHHLoginViewController.h"

#import "HCMarketAreaModel.h"

@interface AppDelegate ()

@property (nonatomic , strong) NSMutableArray *allCoins;

@end

@implementation AppDelegate


- (BOOL)application:(UIApplication *)application didFinishLaunchingWithOptions:(NSDictionary *)launchOptions {
    XHHLoginViewController *loginVC =[XHHLoginViewController initWithStoryboard:@"LoginStroy"];
    
    
    if ([APPControl sharedAPPControl].token)
    {
        [[APPControl sharedAPPControl] toHome];
    }else
    {
        BaseNavigationController *nav = [[BaseNavigationController alloc] initWithRootViewController:loginVC];
        self.window = [[UIWindow alloc] initWithFrame:[UIScreen mainScreen].bounds];
        self.window.rootViewController = nav;
    }
    [self.window makeKeyAndVisible];
    
    
    [self requestCoinList];
    
    return YES;
}
-(void)requestCoinList{
    [[APPControl sharedAPPControl] cny];
    @weakify(self);
    [ChZHomeRequest requestAreaSuccessBlock:^(id dataSource)
     {
         @strongify(self);
         [APPControl sharedAPPControl].areaArray = dataSource;
         [[APPControl sharedAPPControl] requestUSDT:dataSource];
         [self handleData:dataSource];
     } errorBlock:^(int code, NSString *error)
     {
         ChZ_MBError(error)
     }];
}
-(void)handleData:(NSArray *)array
{
    [self.allCoins removeAllObjects];
    for(HCMarketAreaModel *modle in array)
    {
        [self requestMarketListCoin:modle.code];
    }
}
-(void)requestMarketListCoin:(NSString *)coinId
{
    
    
    @weakify(self);
    [ChZHomeRequest requestAreaList:coinId successBlock:^(id dataSource)
     {
         @strongify(self);
         if(dataSource)
         {
             [self.allCoins addObjectsFromArray:dataSource];
             [APPControl sharedAPPControl].allCoins = self.allCoins;
         }
         
         
     } errorBlock:^(int code, NSString *error) {
     }];
    
}
-(NSMutableArray *)allCoins
{
    if (!_allCoins)
    {
        _allCoins = [NSMutableArray array];
    }
    return _allCoins;
}
- (void)applicationWillResignActive:(UIApplication *)application {
    // Sent when the application is about to move from active to inactive state. This can occur for certain types of temporary interruptions (such as an incoming phone call or SMS message) or when the user quits the application and it begins the transition to the background state.
    // Use this method to pause ongoing tasks, disable timers, and invalidate graphics rendering callbacks. Games should use this method to pause the game.
}


- (void)applicationDidEnterBackground:(UIApplication *)application {
    // Use this method to release shared resources, save user data, invalidate timers, and store enough application state information to restore your application to its current state in case it is terminated later.
    // If your application supports background execution, this method is called instead of applicationWillTerminate: when the user quits.
}


- (void)applicationWillEnterForeground:(UIApplication *)application {
    // Called as part of the transition from the background to the active state; here you can undo many of the changes made on entering the background.
}


- (void)applicationDidBecomeActive:(UIApplication *)application {
    // Restart any tasks that were paused (or not yet started) while the application was inactive. If the application was previously in the background, optionally refresh the user interface.
}


- (void)applicationWillTerminate:(UIApplication *)application {
    // Called when the application is about to terminate. Save data if appropriate. See also applicationDidEnterBackground:.
}


@end
