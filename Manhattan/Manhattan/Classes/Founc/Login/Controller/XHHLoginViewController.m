//
//  XHHLoginViewController.m
//  Manhattan
//
//  Created by Apple on 2018/8/14.
//  Copyright © 2018年 Apple. All rights reserved.
//

#import "XHHLoginViewController.h"
#import "XHHRgistViewController.h"
#import "XHHFindPassWordController.h"
#import "ChZTabBarController.h"
@interface XHHLoginViewController ()
@property (weak, nonatomic) IBOutlet UITextField *phone;
@property (weak, nonatomic) IBOutlet UITextField *password;
@property (weak, nonatomic) IBOutlet UIButton *clearButton;
@end

@implementation XHHLoginViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    
    self.noHiddenNav = YES;
    
    [_phone configPlaceholderWithFont:[UIFont systemFontOfSize:16.0] textColor:PlaceholderColor];
    [_password configPlaceholderWithFont:[UIFont systemFontOfSize:16.0] textColor:PlaceholderColor];
    NSString *username = [NSString chz_getObjForKey:kv_LOGIN_USERNAME];
    NSString *password = [NSString chz_getObjForKey:kv_LOGIN_PASSWORD];
    if (ChZ_StringIsEmpty(username))
    {
        self.phone.text = username;
        if (ChZ_StringIsEmpty(password))self.password.text = password;
    }
    
    @weakify(self);
    [[_phone rac_textSignal] subscribeNext:^(NSString * _Nullable x) {
        @strongify(self);
        if (x.length> 0 )
        {
            self.clearButton.hidden = NO;
        }
    }];
    [[NSNotificationCenter defaultCenter] addObserver:self selector:@selector(updateLoginPswSuccess) name:@"updateLoginPassWordSuccess" object:nil];
    // Do any additional setup after loading the view.
}
-(void)updateLoginPswSuccess{
    self.password.text = @"";
}
- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}
- (IBAction)seePassWordAction:(UIButton *)sender {
    sender.selected = !sender.selected;
    _password.secureTextEntry = !sender.selected;
}
- (IBAction)clearPhone:(UIButton *)sender {
    _phone.text = @"";
    sender.hidden = YES;
}
- (IBAction)loginAction:(id)sender {
    [self requestLogin];
}
- (IBAction)forgetAction:(id)sender {
    XHHFindPassWordController *vc = [XHHFindPassWordController initWithStoryboard:@"LoginStroy"];
    [self.navigationController pushViewController:vc animated:YES];
}
- (IBAction)registAction:(id)sender {
    XHHRgistViewController *vc = [XHHRgistViewController initWithStoryboard:@"LoginStroy"];
    [self.navigationController pushViewController:vc animated:YES];
}
-(void)requestLogin{
    ChZ_MBMsg(@"登录中...");
    [XHHLoginRequest requestLogin:self.phone.text
                         password:self.password.text
                             type:@"0"
                     successBlock:^(id dataSource)
    {
        dispatch_after(dispatch_time(DISPATCH_TIME_NOW, (int64_t)(1 * NSEC_PER_SEC)), dispatch_get_main_queue(), ^{
            [[APPControl sharedAPPControl] toHome];
            ChZ_MBDismss
        });
    } errorBlock:^(int code, NSString *error) {
        ChZ_MBDismssError(error);
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
