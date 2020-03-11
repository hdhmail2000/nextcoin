//
//  XHHFindPassWordController.m
//  Manhattan
//
//  Created by Apple on 2018/8/15.
//  Copyright © 2018年 Apple. All rights reserved.
//

#import "XHHFindPassWordController.h"
#import "XHHAuthViewController.h"
@interface XHHFindPassWordController ()
@property (weak, nonatomic) IBOutlet UITextField *phone;
@property (weak, nonatomic) IBOutlet UITextField *code;
@property (weak, nonatomic) IBOutlet UITextField *password;
@property (weak, nonatomic) IBOutlet UIScrollView *bgScrollView;
@property (weak, nonatomic) IBOutlet UILabel *navTitle;
@property (weak, nonatomic) IBOutlet UIButton *clearButton;
@property (weak, nonatomic) IBOutlet UITextField *googleTextFeild;
@property (weak, nonatomic) IBOutlet UIButton *timeButton;
@end

@implementation XHHFindPassWordController

- (void)viewDidLoad {
    [super viewDidLoad];
    [_phone configPlaceholderWithFont:[UIFont systemFontOfSize:16.0] textColor:PlaceholderColor];
    [_password configPlaceholderWithFont:[UIFont systemFontOfSize:16.0] textColor:PlaceholderColor];
    [_code configPlaceholderWithFont:[UIFont systemFontOfSize:16.0] textColor:PlaceholderColor];
    [_googleTextFeild configPlaceholderWithFont:[UIFont systemFontOfSize:16.0] textColor:PlaceholderColor];
    
    @weakify(self);
    [[_phone rac_textSignal] subscribeNext:^(NSString * _Nullable x) {
        @strongify(self);
        if (x.length> 0 )
        {
            self.clearButton.hidden = NO;
        }
    }];
    
    NSNotificationCenter *center = [NSNotificationCenter defaultCenter];
    [center addObserver:self selector:@selector(keyboardDidShow) name:UIKeyboardDidShowNotification object:nil];
    [center addObserver:self selector:@selector(keyboardDidHide) name:UIKeyboardWillHideNotification object:nil];
}

//  键盘弹出触发该方法
- (void)keyboardDidShow
{
    if (_bgScrollView.contentOffset.y > 0) {
        _navTitle.text = @"找回密码";
    }
    
}
//  键盘隐藏触发该方法
- (void)keyboardDidHide
{
    _navTitle.text = @"";
}
- (IBAction)clearPhone:(UIButton *)sender {
    _phone.text = @"";
    sender.hidden = YES;
}
- (IBAction)seePassWordAction:(UIButton *)sender {
    sender.selected = !sender.selected;
    _password.secureTextEntry = !sender.selected;
}
- (IBAction)getCodeAction:(UIButton *)sender {
    if (![ChZUtil chz_isTelPhoneNumber:self.phone.text]) {
        ChZ_MBError(@"请输入正确的手机号码");return;
        
    }
    [self requestPhoneMsg:self.phone.text];
    
}
- (IBAction)registAction:(id)sender {
    if (![ChZUtil chz_isTelPhoneNumber:self.phone.text]) {
        ChZ_MBError(@"请输入正确的手机号码");return;
    }
    if (self.code.text.length == 0) {
         ChZ_MBError(@"请输入验证码");return;
    }
    if (self.password.text.length == 0) {
        ChZ_MBError(@"请输入新密码");return;
    }
    [self requestLoginPasswordForget:self.phone.text
                                code:self.code.text
                            totpCode:self.googleTextFeild.text
                         newPassword:self.password.text
                        newPassword2:self.password.text];
}
- (IBAction)loginAction:(id)sender {
    [self.navigationController popViewControllerAnimated:YES];
}
- (IBAction)back:(id)sender {
    [self.navigationController popViewControllerAnimated:YES];
}
#pragma ----获取手机验证码
- (void)requestPhoneMsg:(NSString *)phone
{
    ChZ_MBMsg(nil);
    @weakify(self);
    [XHHSafeCenterRequest requestPhoneMsg:@"86"
                                    phone:phone
                                     type:@"109"
                             successBlock:^(id dataSource)
    {
        ChZ_MBDismss;
        ChZ_MBSuccess(@"验证码已发送");
        @strongify(self);
        [self.timeButton startWithTime:59
                                 title:@"获取验证码"
                        countDownTitle:@" s"
                             mainColor:[UIColor colorWithHexString:@"2E7AF1"]
                            countColor:PlaceholderColor];
    } errorBlock:^(int code, NSString *error) {
        ChZ_MBDismss;
        ChZ_MBError(error);
    }];
}
- (void)requestLoginPasswordForget:(NSString *)phone
                              code:(NSString *)code
                          totpCode:(NSString *)totpCode
                       newPassword:(NSString *)newPassword
                      newPassword2:(NSString *)newPassword2{
    ChZ_MBMsg(nil)
    [XHHSafeCenterRequest requestLoginPasswordForget:phone
                                                area:@"86"
                                                code:code
                                            totpCode:totpCode
                                         newPassword:newPassword
                                        newPassword2:newPassword2
                                        successBlock:^(id dataSource)
    {
        ChZ_MBDismss;
        ChZ_MBSuccess(@"密码修改成功")
        dispatch_after(dispatch_time(DISPATCH_TIME_NOW, (int64_t)(1.0 * NSEC_PER_SEC)), dispatch_get_main_queue(), ^{
            [[APPControl sharedAPPControl] toLogin];
        });
    } errorBlock:^(int code, NSString *error) {
        ChZ_MBDismss;
        ChZ_MBError(error)
    }];
}
- (void)dealloc
{
    [[NSNotificationCenter defaultCenter] removeObserver:self name:UIKeyboardDidShowNotification object:nil];
    [[NSNotificationCenter defaultCenter] removeObserver:self name:UIKeyboardDidHideNotification object:nil];
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
