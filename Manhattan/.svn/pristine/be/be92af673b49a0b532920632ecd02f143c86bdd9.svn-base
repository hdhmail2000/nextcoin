//
//  XHHGoogleVerifyCodeStep2Controller.m
//  FuturePurse
//
//  Created by muye01 on 2018/7/30.
//  Copyright © 2018年 jbtm. All rights reserved.
//

#import "XHHGoogleVerifyCodeStep2Controller.h"
#import "XTVerCodeInput.h"
//#import "CenterNetWorkControl.h"
#define  verCodeTime 60

@interface XHHGoogleVerifyCodeStep2Controller ()
@property (weak, nonatomic) IBOutlet XTVerCodeInput *verCodeInput;

@property (weak, nonatomic) IBOutlet UILabel *telLabel;

@property (assign, nonatomic) int remainSeconds;
@property (assign, nonatomic) int startCheckTimer;
@property (weak, nonatomic) IBOutlet UIButton *verCodeBtn;

@end

@implementation XHHGoogleVerifyCodeStep2Controller


- (void)viewDidLoad {
    [super viewDidLoad];
    UserModel *user = [APPControl sharedAPPControl].user;
   
    _telLabel.text = @"请输入有效的谷歌验证码";
    self.remainSeconds = verCodeTime;
    self.startCheckTimer = 1;
    
    [self.verCodeBtn setTitle:[NSString stringWithFormat:@"未收到验证码?重新发送%d秒",verCodeTime] forState:UIControlStateNormal];
    [self.verCodeBtn setTitleColor:[UIColor lightGrayColor] forState:UIControlStateDisabled];
    [self.verCodeBtn setTitleColor:[UIColor darkGrayColor] forState:UIControlStateNormal];
    self.verCodeBtn.hidden = YES;
    [self config];
//    [self setTime];
    
}
- (IBAction)back:(id)sender
{
    [self pop];
}
-(void)config{
    _verCodeInput.inputType = 6;

    _verCodeInput.keyboardType = UIKeyboardTypeNumberPad;
    [_verCodeInput  setupSubviews];
    @weakify(self);
    _verCodeInput.verCodeBlock = ^(NSString *text) {
        NSLog(@"您输入的验证码是%@",text);
        @strongify(self);
        if (text.length == 6) {
            
//            XHHPayPwdSettingController *vc = [XHHPayPwdSettingController initWithStoryboard:@"Mine"];
//            vc.title = @"设置密码";
//            vc.verifyCode = text;
//            [self.navigationController pushViewController:vc animated:YES];
            [self requestAddGoogleAuthCode:text googlekey:self.totpKey];
            
        }
    };
}
- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}
#pragma mark - button倒计时
//- (void)setTime{
//    @weakify(self);
//    RACSignal* signal = [[[RACSignal interval:1
//                                  onScheduler:[RACScheduler mainThreadScheduler]] startWith:[NSDate date]] map:^id(NSDate* value) {
//        @strongify(self);
//        if(self.remainSeconds > 0 && self.startCheckTimer){
//            self.remainSeconds --;
//            if(self.remainSeconds == 0){
//                self.startCheckTimer = 0;
//
//            }
//        }
//        NSString *text = self.startCheckTimer == 1? [NSString stringWithFormat:@"未收到验证码?重新发送%d秒",self.remainSeconds]:[NSString stringWithFormat:@"未收到验证码?重新发送%d秒",verCodeTime];
//        return text;
//
//    }];
//    [signal subscribeNext:^(NSString* x) {
//        @strongify(self);
//
//        _verCodeBtn.titleLabel.text = x;
//    }];
//    RAC(self.verCodeBtn, enabled) = [RACSignal
//                                     combineLatest:@[
//                                                     RACObserve(self, remainSeconds),
//                                                     RACObserve(self, startCheckTimer)
//                                                     ]
//                                     reduce:^id(NSNumber* remain, NSNumber* startCheck) {
//                                         return@( remain.intValue == 0 && startCheck.intValue == 0);
//                                     }];
//}
//-(void)setVerCodeBtn:(UIButton *)verCodeBtn
//{
//    _verCodeBtn = verCodeBtn;
//    @weakify(self);
//    [[self.verCodeBtn rac_signalForControlEvents:UIControlEventTouchUpInside] subscribeNext:^(id x) {
//        @strongify(self);
//        self.verCodeBtn.enabled = NO;
//        self.remainSeconds = verCodeTime;
//        self.startCheckTimer = 1;
//
//    }];
//}
-(void)requestAddGoogleAuthCode:(NSString *)code
                      googlekey:(NSString *)googleKEy
{
    @weakify(self);
    [XHHSafeCenterRequest requestAddGoogleAuthCode:code
                                         googlekey:googleKEy
                                      successBlock:^(id dataSource)
    {
        @strongify(self);
        [[NSNotificationCenter defaultCenter] postNotificationName:@"AuthSuccess" object:nil];
        ChZ_MBSuccess(@"成功")
        [self popToVCClassName:@"XHHSafeCenterController"];
    } errorBlock:^(int code, NSString *error) {
        ChZ_MBError(error);
    }];
}
@end
