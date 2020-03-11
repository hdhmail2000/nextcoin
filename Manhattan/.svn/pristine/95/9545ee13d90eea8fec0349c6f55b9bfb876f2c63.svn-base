//
//  ChZValuationTypeController.m
//  FuturePurse
//
//  Created by Howe on 2018/8/3.
//  Copyright © 2018年 jbtm. All rights reserved.
//

#import "ChZValuationTypeController.h"

@interface ChZValuationTypeController ()
@property (weak, nonatomic) IBOutlet UIImageView *rmbHookImageView;
@property (weak, nonatomic) IBOutlet UIImageView *usdtHookImageView;

@end

@implementation ChZValuationTypeController

- (void)viewDidLoad {
    [super viewDidLoad];
    if ([APPControl sharedAPPControl].conType == CoinCurrencyTypeRMB)
    {
        [self rmbAction:nil];
    }
    if ([APPControl sharedAPPControl].conType == CoinCurrencyTypeUSD)
    {
        [self usdtAction:nil];
    }
}
- (IBAction)backAction:(id)sender
{
    [self pop];
}
- (IBAction)rmbAction:(id)sender
{
    self.rmbHookImageView.hidden = NO;
    self.usdtHookImageView.hidden = YES;
}
- (IBAction)usdtAction:(id)sender
{
    self.rmbHookImageView.hidden = YES;
    self.usdtHookImageView.hidden = NO;
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

/*
#pragma mark - Navigation

// In a storyboard-based application, you will often want to do a little preparation before navigation
- (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender {
    // Get the new view controller using [segue destinationViewController].
    // Pass the selected object to the new view controller.
}
*/

@end
