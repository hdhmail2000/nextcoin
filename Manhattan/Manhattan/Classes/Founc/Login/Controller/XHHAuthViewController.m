//
//  XHHAuthViewController.m
//  Manhattan
//
//  Created by Apple on 2018/8/14.
//  Copyright © 2018年 Apple. All rights reserved.
//

#import "XHHAuthViewController.h"
#import "XHHAuthSuccessController.h"
#import "XHHSafeCenterController.h"

#import "XHHRealNameAuthorizeStep2Controller.h"
@interface XHHAuthViewController ()
@property (weak, nonatomic) IBOutlet UITextField *name;
@property (weak, nonatomic) IBOutlet UITextField *idCard;


@property (weak, nonatomic) IBOutlet UIButton *rightButton;
@property (weak, nonatomic) IBOutlet UIButton *leftButton;
@property (weak, nonatomic) IBOutlet UIImageView *leftImageView;

@end

@implementation XHHAuthViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    
    [_name configPlaceholderWithFont:[UIFont systemFontOfSize:16.0] textColor:PlaceholderColor];
    [_idCard configPlaceholderWithFont:[UIFont systemFontOfSize:16.0] textColor:PlaceholderColor];
    
    @weakify(self);
    [[self.idCard rac_textSignal] subscribeNext:^(NSString * _Nullable x)
    {
        @strongify(self);
       if(x.length > 18)
       {
           self.idCard.text = [x substringToIndex:18];
       }
    }];
    
    if(self.isFromeMine)
    {
        self.rightButton.hidden = YES;
        self.leftButton.hidden = NO;
        self.leftImageView.hidden = NO;
    }
    
    
    // Do any additional setup after loading the view.
}
- (IBAction)back:(id)sender
{
    [self pop];
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}
- (IBAction)nextAction:(id)sender {
    if(!ChZ_StringIsEmpty(self.name.text)){
        ChZ_MBError(@"请输入真实姓名");
        return;
    }
    if(!ChZ_StringIsEmpty(self.idCard.text)){
        ChZ_MBError(@"身份证号码");
        return;
    }
    if(self.idCard.text.length != 15 && self.idCard.text.length != 18)
    {
        ChZ_MBError(@"请输入正确的身份证号码");
        return;
    }
    XHHRealNameAuthorizeStep2Controller *vc = [XHHRealNameAuthorizeStep2Controller initWithStoryboard:@"MineStroy"];
    vc.realName = self.name.text;
    vc.IDNum = self.idCard.text;
    [self.navigationController pushViewController:vc animated:YES];
    
    
//    XHHAuthSuccessController *vc = [XHHAuthSuccessController initWithStoryboard:@"LoginStroy"];
//    [self.navigationController pushViewController:vc animated:YES];
}
- (IBAction)jumpAction:(id)sender {
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
