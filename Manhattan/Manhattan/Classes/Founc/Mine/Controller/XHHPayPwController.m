//
//  XHHPayPwController.m
//  Manhattan
//
//  Created by Apple on 2018/8/16.
//  Copyright © 2018年 Apple. All rights reserved.
//

#import "XHHPayPwController.h"


@interface XHHPayPwController ()<YXTextFieldDelegate>
@property (weak, nonatomic) IBOutlet UITextField *textFeildOne;
@property (weak, nonatomic) IBOutlet UITextField *TextFeildTwo;
@property (weak, nonatomic) IBOutlet UITextField *textFeildThree;
@property (weak, nonatomic) IBOutlet UITextField *textFeildFoure;
@property (weak, nonatomic) IBOutlet UITextField *textFeildFive;
@property (weak, nonatomic) IBOutlet UITextField *textFeildSix;
@property (weak, nonatomic) IBOutlet UILabel *alertLable;
@property (weak, nonatomic) IBOutlet UIView *codeView;
@property (weak, nonatomic) IBOutlet UIButton *codeButton;
@property (weak, nonatomic) IBOutlet UIButton *forgrtButton;
@property (weak, nonatomic) IBOutlet UILabel *typeTitle;


@property (weak, nonatomic) IBOutlet UIButton *sureButton;


@property (assign , nonatomic) NSInteger step;

@property (nonatomic , copy) NSString * stepText1;
@property (nonatomic , copy) NSString * stepText2;
@property (nonatomic , copy) NSString * stepText3;

@property (nonatomic , copy) NSString *username;

@end

@implementation XHHPayPwController

- (void)viewDidLoad {
    [super viewDidLoad];
    
    [self reloadPassWordInputView];
    
    _textFeildOne.delegate = self;
    _TextFeildTwo.delegate = self;
    _textFeildThree.delegate = self;
    _textFeildFoure.delegate = self;
    _textFeildFive.delegate = self;
    _textFeildSix.delegate = self;
    
    [self setUpAllTextFeild];
    
    self.username = [NSString chz_getObjForKey:kv_LOGIN_USERNAME];
}
-(void)reloadPassWordInputView{
    switch (self.payWordSetType) {
        case SetPayWordTypeNoSet:
            self.codeView.hidden = YES;
            self.forgrtButton.hidden = YES;
            self.typeTitle.text = @"设置交易密码";
            [self showAlertTitle];
            break;
        case SetPayWordTypeHadSet:
            self.codeView.hidden = YES;
            self.alertLable.text = @"为了您的账户安全，请输入原支付密码确认身份";
            self.typeTitle.text = @"重置交易密码";
            break;
        case SetPayWordTypeFroget:
            self.codeView.hidden = YES;
            self.typeTitle.text = @"忘记交易密码";
            break;
            
        default:
            break;
    }
    self.step = 1;
}
-(void)showAlertTitle{
    
    if (ChZ_StringIsEmpty(self.username))
    {
        if (self.username.length == 11)
        {
            self.alertLable.text = [NSString stringWithFormat:@"为账户 +86 %@****%@设置6位数字密码",[self.username substringToIndex:3],[self.username substringFromIndex:7]];
        }
    }
}
- (IBAction)textChange:(UITextField *)sender {
    if (sender.text.length>0) {
        sender.text = [sender.text substringToIndex:1];
        if (sender.tag  != 16)
        {
            UITextField *textfeild = (UITextField *)[self.view viewWithTag:sender.tag + 1];
            textfeild.userInteractionEnabled = YES;
            [textfeild becomeFirstResponder];
        }else
        {
            if (self.step == 1) {
                self.stepText1 = [NSString stringWithFormat:@"%@%@%@%@%@%@",
                                  self.textFeildOne.text,
                                  self.TextFeildTwo.text,
                                  self.textFeildThree.text,
                                  self.textFeildFoure.text,
                                  self.textFeildFive.text,
                                  self.textFeildSix.text];
                self.step = 2;
                [self setUpAllTextFeild];
                switch (self.payWordSetType) {
                    case SetPayWordTypeNoSet:
                        self.alertLable.text = @"再次输入";
                        break;
                    case SetPayWordTypeHadSet:
                        self.alertLable.text = @"请输入新密码";
                        break;
                    case SetPayWordTypeFroget:
                        self.alertLable.text = @"请输入新密码";
                        break;
                        
                    default:
                        break;
                }
                
                
                return;
            }else if (self.step == 2)
            {
                self.stepText2 = [NSString stringWithFormat:@"%@%@%@%@%@%@",
                                  self.textFeildOne.text,
                                  self.TextFeildTwo.text,
                                  self.textFeildThree.text,
                                  self.textFeildFoure.text,
                                  self.textFeildFive.text,
                                  self.textFeildSix.text];
                self.step = 3;
                [self setUpAllTextFeild];
                switch (self.payWordSetType)
                {
                    case SetPayWordTypeNoSet:
                        self.alertLable.text = @"请输入Google验证码(未绑定请直接确认设置)";
                        self.sureButton.hidden = NO;
                        break;
                    case SetPayWordTypeHadSet:
                        self.alertLable.text = @"确认新密码";
                        break;
                    case SetPayWordTypeFroget:
                        self.alertLable.text = @"确认新密码";
                        break;
                        
                    default:
                        break;
                }
                return;
            }else if (self.step == 3) {
                self.stepText3 = [NSString stringWithFormat:@"%@%@%@%@%@%@",
                                  self.textFeildOne.text,
                                  self.TextFeildTwo.text,
                                  self.textFeildThree.text,
                                  self.textFeildFoure.text,
                                  self.textFeildFive.text,
                                  self.textFeildSix.text];

                switch (self.payWordSetType)
                {
                    case SetPayWordTypeNoSet:
                        break;
                    case SetPayWordTypeHadSet:
                        
                        [self updatePassword];
                        
                        break;
                    case SetPayWordTypeFroget:

                        [self forgetPsw];

                        break;

                    default:
                        break;
                }
                ChZ_DebugLog(@"第一次输入---%@  /n 第一次输入---%@ /n 第一次输入---%@",self.stepText1,self.stepText2,self.stepText3);
                //[self setUpAllTextFeild];
            }
        }
    }
}
- (IBAction)back:(id)sender {
    [self.navigationController popViewControllerAnimated:YES];
}
- (void)textFieldDidDeleteBackward:(UITextField *)textField {
    
    switch (self.step) {
        case 1:
            self.stepText1 = @"";
            break;
        case 2:
            self.stepText2 = @"";
            break;
        case 3:
            self.stepText3 = @"";
            break;
        default:
            break;
    }
    [self setUpAllTextFeild];
    
}

-(void)setUpAllTextFeild{
    for(int tag = 11;tag < 17;tag ++){
        UITextField *textfeild = (UITextField *)[self.view viewWithTag:tag];
        textfeild.userInteractionEnabled = YES;
        textfeild.text = @"";
        if (textfeild.tag == 11){
            [textfeild becomeFirstResponder];
        }else{
            textfeild.userInteractionEnabled = NO;
        }
    }
}
- (IBAction)forgetAction:(UIButton *)sender {
    
    [self getCode];
    
}
- (IBAction)codeAction:(UIButton *)sender {
    [sender startWithTime:59
                    title:@"获取验证码"
           countDownTitle:@" s后重新发送"
                mainColor:[UIColor colorWithHexString:@"308AF5"]
               countColor:[UIColor colorWithHexString:@"4B4B80"]];
}
- (IBAction)sureAction:(UIButton *)sender {
    
    if (self.payWordSetType == SetPayWordTypeNoSet) {
        if (self.stepText1.length != 6) {
            ChZ_MBError(@"请输入交易密码");
            [self reloadPassWordInputView];
            return;
        }
        if (![self.stepText1 isEqualToString:self.stepText2]) {
            ChZ_MBError(@"密码不一致");
            [self reloadPassWordInputView];
            return;
        }
        [self requestUpdatePassWordOlderPassWord:nil
                                            nPsw:self.stepText1
                                         msgCode:nil
                                      googleCode:self.stepText3
                                      cardNumber:nil
                                            type:@"1"];
    }
}
-(void)updatePassword{
    if (self.payWordSetType == SetPayWordTypeHadSet) {
        if ([self.stepText1 isEqualToString:self.stepText2]) {
            ChZ_MBError(@"新密码不能与旧密码一致");
            [self reloadPassWordInputView];
            return;
        }
        if (![self.stepText2 isEqualToString:self.stepText3]) {
            ChZ_MBError(@"密码不一致");
            [self reloadPassWordInputView];
            return;
        }
        if (self.stepText2.length != 6) {
            ChZ_MBError(@"新密码输入错误");
            [self reloadPassWordInputView];
            return;
        }
        [self requestUpdatePassWordOlderPassWord:self.stepText1
                                            nPsw:self.stepText2
                                         msgCode:nil
                                      googleCode:nil
                                      cardNumber:nil
                                            type:@"1"];
    }
}
#pragma --忘记交易密码
-(void)forgetPsw{
    if (self.payWordSetType == SetPayWordTypeFroget) {
        if (![self.stepText2 isEqualToString:self.stepText3]) {
            ChZ_MBError(@"密码不一致");
            [self reloadPassWordInputView];
            return;
        }
        [XHHSafeCenterRequest requestTradePasswordForget:self.username
                                                    area:@"86"
                                                    code:self.stepText1
                                                totpCode:nil
                                             newPassword:self.stepText2
                                            newPassword2:self.stepText3
                                            successBlock:^(id dataSource)
        {
            ChZ_MBDismssSuccess(dataSource);
            self.callBackBlock(nil);
            [self popToVCClassName:@"XHHSafeCenterController"];
            
        } errorBlock:^(int code, NSString *error) {
            ChZ_MBDismssError(error)
            [self reloadPassWordInputView];
            
        }];
    }
}
#pragma --更改交易密码
-(void)requestUpdatePassWordOlderPassWord:(NSString *)oldePsw
                                     nPsw:(NSString *)npsw
                                  msgCode:(NSString *)msgCode
                               googleCode:(NSString *)gooleCode
                               cardNumber:(NSString *)cardNumber
                                     type:(NSString *)type{
    ChZ_MBMsg(nil)
    @weakify(self);
    [XHHSafeCenterRequest requestUpdatePassword:oldePsw
                                       password:npsw
                                        msgCode:msgCode
                                     googleCode:gooleCode
                                     cardNumber:cardNumber
                                           type:type
                                   successBlock:^(id dataSource)
     {
         @strongify(self);
         ChZ_MBDismssSuccess(dataSource);
         self.callBackBlock(nil);
         [self popToVCClassName:@"XHHSafeCenterController"];
     } errorBlock:^(int code, NSString *error)
     {
         @strongify(self);
         [self reloadPassWordInputView];
         ChZ_MBDismssError(error)
     }];
}
#pragma --获取验证码
-(void)getCode{
    ChZ_MBMsg(nil);
    @weakify(self);
    [XHHSafeCenterRequest requestPhoneMsg:@"86" phone:self.username type:@"107" successBlock:^(id dataSource) {
        @strongify(self);
        self.payWordSetType = SetPayWordTypeFroget;
        [self reloadPassWordInputView];
        self.forgrtButton.hidden = YES;
        self.codeView.hidden = NO;
        self.alertLable.text =[NSString stringWithFormat:@"验证码已发送至 +86 %@****%@",[self.username substringToIndex:3],[self.username substringFromIndex:7]];;
        [self.codeButton startWithTime:59
                                 title:@"获取验证码"
                        countDownTitle:@" s后重新发送"
                             mainColor:[UIColor colorWithHexString:@"308AF5"]
                            countColor:[UIColor colorWithHexString:@"4B4B80"]];
        ChZ_MBDismss;
    } errorBlock:^(int code, NSString *error) {
        ChZ_MBDismssError(error);
    }];
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
