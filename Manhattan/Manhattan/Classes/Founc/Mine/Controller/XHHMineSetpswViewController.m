//
//  XHHMineSetpswViewController.m
//  Manhattan
//
//  Created by Apple on 2018/9/15.
//  Copyright © 2018年 Apple. All rights reserved.
//

#import "XHHMineSetpswViewController.h"

@interface XHHMineSetpswViewController ()
@property (weak, nonatomic) IBOutlet UILabel *alerLable;
@property (weak, nonatomic) IBOutlet UITextField *pswTextFeild;
@property (weak, nonatomic) IBOutlet UITextField *sureTextFeild;
@property (weak, nonatomic) IBOutlet UITextField *gooleTextFeild;



@end

@implementation XHHMineSetpswViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    
    [self showAlertTitle];
    
    [self.pswTextFeild configPlaceholderWithFont:[UIFont systemFontOfSize:16.0] textColor:PlaceholderColor];
    [self.sureTextFeild configPlaceholderWithFont:[UIFont systemFontOfSize:16.0] textColor:PlaceholderColor];
    [self.gooleTextFeild configPlaceholderWithFont:[UIFont systemFontOfSize:16.0] textColor:PlaceholderColor];
    // Do any additional setup after loading the view.
}
-(void)showAlertTitle{
    NSString *username = [NSString chz_getObjForKey:kv_LOGIN_USERNAME];
    if (ChZ_StringIsEmpty(username))
    {
        if (username.length == 11)
        {
            self.alerLable.text = [NSString stringWithFormat:@"为账户 +86 %@****%@设置6-16位密码",[username substringToIndex:3],[username substringFromIndex:7]];
        }
    }
}
- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}
- (IBAction)back:(id)sender {
    [self pop];
}
- (IBAction)sureAction:(id)sender {
    if (self.pswTextFeild.text.length < 6) {
        ChZ_MBError(@"密码长度必须大于6位");
        return;
    }
    if (![self.pswTextFeild.text isEqualToString:self.sureTextFeild.text]) {
        ChZ_MBError(@"密码不一致");
        return;
    }
    if (self.gooleTextFeild.text.length == 0)
    {
        self.gooleTextFeild.text = @"";
    }
    [self requestUpdatePassWordOlderPassWord:nil
                                        nPsw:self.pswTextFeild.text
                                        msgCode:nil
                                    googleCode:self.gooleTextFeild.text
                                    cardNumber:nil
                                        type:@"1"];
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
         ChZ_MBDismss
         ChZ_MBSuccess(dataSource);
         self.callBackBlock(nil);
         [self pop];
     } errorBlock:^(int code, NSString *error)
     {
         ChZ_MBDismss
         ChZ_MBError(error)
     }];
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
