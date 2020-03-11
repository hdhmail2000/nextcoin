//
//  XHHAuthSuccessController.m
//  Manhattan
//
//  Created by Apple on 2018/8/15.
//  Copyright © 2018年 Apple. All rights reserved.
//

#import "XHHAuthSuccessController.h"
#import "XHHSafeCenterController.h"
@interface XHHAuthSuccessController ()

@end

@implementation XHHAuthSuccessController

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view.
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}
- (IBAction)finishAction:(id)sender {
    BOOL isLoginPod = YES;
    for(UIViewController *vc in self.navigationController.viewControllers){
        if ([vc isKindOfClass:[XHHSafeCenterController class]]) {
            isLoginPod = NO;
            break;
        }
    }
    if (isLoginPod) {
        [self popToVCClassName:@"XHHLoginViewController"];
    }else{
        [self popToVCClassName:@"XHHSafeCenterController"];
    }
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
