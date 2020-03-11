//
//  ChZAddBankAccountController.m
//  FuturePurse
//
//  Created by Howe on 2018/8/3.
//  Copyright © 2018年 jbtm. All rights reserved.
//

#import "ChZAddBankAccountController.h"
#import "ChZAccountRequest.h"

@interface ChZAddBankAccountController ()
@property (weak, nonatomic) IBOutlet UITextField *bankNameField;
@property (weak, nonatomic) IBOutlet UITextField *accountField;
@property (weak, nonatomic) IBOutlet UITextField *nameField;
@property (weak, nonatomic) IBOutlet UITextField *cardField;
@property (weak, nonatomic) IBOutlet UITextField *phoneField;




@end

@implementation ChZAddBankAccountController

- (void)viewDidLoad {
    [super viewDidLoad];
    [self.accountField configPlaceholderWithFont:[UIFont systemFontOfSize:16.0] textColor:ChZ_Color(75, 75, 128)];
    [self.bankNameField configPlaceholderWithFont:[UIFont systemFontOfSize:16.0] textColor:ChZ_Color(75, 75, 128)];
    [self.nameField configPlaceholderWithFont:[UIFont systemFontOfSize:16.0] textColor:ChZ_Color(75, 75, 128)];
    [self.cardField configPlaceholderWithFont:[UIFont systemFontOfSize:16.0] textColor:ChZ_Color(75, 75, 128)];
    [self.phoneField configPlaceholderWithFont:[UIFont systemFontOfSize:16.0] textColor:ChZ_Color(75, 75, 128)];
    
    @weakify(self);
    [[self.phoneField rac_textSignal] subscribeNext:^(NSString * _Nullable x) {
        @strongify(self);
        if (x.length > 11)
        {
            self.phoneField.text = [x substringToIndex:11];
        }
    }];
    [[self.cardField rac_textSignal] subscribeNext:^(NSString * _Nullable x) {
        @strongify(self);
        if (x.length > 19)
        {
            self.cardField.text = [x substringToIndex:19];
        }
    }];
    [[self.accountField rac_textSignal] subscribeNext:^(NSString * _Nullable x) {
        @strongify(self);
        if (x.length > 20)
        {
            self.accountField.text = [x substringToIndex:20];
        }
    }];
    
    // Do any additional setup after loading the view.
}
- (IBAction)backAction:(id)sender
{
    [self pop];
}
- (IBAction)saveAction:(id)sender
{
    NSString *bankName = self.bankNameField.text;
    if (!ChZ_StringIsEmpty(bankName)) {
        ChZ_MBError(@"请输入开户行");
        return;
    }
    
    NSString *account = self.accountField.text;
    if (!ChZ_StringIsEmpty(account)) {
        ChZ_MBError(@"请输入银行卡卡号");
        return;
    }
    
    NSString *name = self.nameField.text;
    if (!ChZ_StringIsEmpty(name)) {
        ChZ_MBError(@"请输入开户人姓名");
        return;
    }
    
    NSString *card = self.cardField.text;
    card = [card stringByTrimmingCharactersInSet:[NSCharacterSet whitespaceCharacterSet]];
    if (!ChZ_StringIsEmpty(card)) {
        ChZ_MBError(@"请输入身份证号");
        return;
    }
    if (![ChZUtil chz_isIdNumber:card]) {
        ChZ_MBError(@"请输入正确身份证号");
        return;
    }
    
    NSString *phone = self.phoneField.text;
    if (!ChZ_StringIsEmpty(phone)) {
        ChZ_MBError(@"请输入手机号");
        return;
    }
    if (![ChZUtil chz_isNumber:phone] || phone.length != 11)
    {
        ChZ_MBError(@"请输入正确的手机号");
        return;
    }
    ChZ_MBMsg(nil)
    ChZ_Weak
    [ChZAccountRequest requestSubmitBankPayAccountList:name cardname:card bankNumber:account bankName:bankName phone:phone successBlock:^(id dataSource) {
        ChZ_Strong
        ChZ_MBDismssSuccess(@"添加成功")
        if (_strongSelf.callBackBlock) {
            _strongSelf.callBackBlock(nil);
        }
        dispatch_after(dispatch_time(DISPATCH_TIME_NOW, (int64_t)(1 * NSEC_PER_SEC)), dispatch_get_main_queue(), ^{
            [_strongSelf pop];
        });
    } errorBlock:^(int code, NSString *error) {
        ChZ_MBDismssError(error)
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
