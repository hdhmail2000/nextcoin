//
//  XHHTrunOutController.m
//  Manhattan
//
//  Created by Apple on 2018/8/17.
//  Copyright © 2018年 Apple. All rights reserved.
//

#import "XHHTrunOutController.h"
#import "XHHAreaListController.h"
#import "XHHPublicNavView.h"
#import "XHHTrunOutView.h"
#import <IQKeyboardManager.h>

#import <TYAlertController/TYAlertController.h>
#import "XTTMyPurseView.h"
#import "XHHPTChooseController.h"
#import "XHHMineFrogetPswViewController.h"
#import "XHHMineSetpswViewController.h"
#import "ChZScanQRController.h"
static inline UIEdgeInsets sgm_safeAreaInset(UIView *view) {
    if (@available(iOS 11.0, *)) {
        return view.safeAreaInsets;
    }
    return UIEdgeInsetsZero;
}
@interface XHHTrunOutController ()<YXTextFieldDelegate,XHHPublicNavViewProtocol,UITextFieldDelegate>
@property (nonatomic , strong) XHHPublicNavView           *navView;
@property (strong, nonatomic) IBOutlet XHHTrunOutView     *trunOutView;
@property (nonatomic , strong) HCWithdraGESTFeeModel      *serviceFeeModdel;
@property (nonatomic , strong) AddressListModel           *addressModel;

@property (weak, nonatomic) IBOutlet UITextField     *area;

@property (weak, nonatomic) IBOutlet UITextField     *money;

@property (weak, nonatomic) IBOutlet UILabel         *hadMoney;

@property (weak, nonatomic) IBOutlet UILabel         *realMoney;

@property (weak, nonatomic) IBOutlet UILabel         *useMoney;
// 输入框的透明背景视图
@property (strong, nonatomic)  UIView             *inputBackgroundView;
@property (strong, nonatomic)  NSString           *coinAddressId;
@property (nonatomic , strong) NSString           *phoneCode;
@property (nonatomic , strong) NSString           *tradeCode;
@property (nonatomic , strong) TYAlertController  *controller;

@property (nonatomic , assign) double withdrawFee;
@property (nonatomic , strong) NSString *coinHadMoney;

@property (nonatomic , assign) BOOL isHaveDian;

@end

@implementation XHHTrunOutController

- (void)viewDidLoad {
    [super viewDidLoad];
    
    self.coinAddressId = nil;
    
    [self zh_setupUI];
    
    [self requestWithdraGESTFee:self.model.coinId];
    [_area configPlaceholderWithFont:[UIFont systemFontOfSize:16.0] textColor:PlaceholderColor];
    
    [self.money addTarget:self action:@selector(valueChange:) forControlEvents:UIControlEventEditingChanged];
    
    self.money.delegate = self;
}
-(void)valueChange:(UITextField *)textFeild
{
    if ([textFeild.text floatValue] > [self.coinHadMoney floatValue])
    {
        textFeild.text = self.coinHadMoney;
    }
    self.realMoney.text = [NSString stringWithFormat:@"实际到账 %.4f",[textFeild.text floatValue]*(1 - self.withdrawFee)];
}
- (BOOL)textField:(UITextField *)textField shouldChangeCharactersInRange:(NSRange)range replacementString:(NSString *)string
{
    /*
     * 不能输入.0-9以外的字符。
     * 设置输入框输入的内容格式
     * 只能有一个小数点
     * 小数点后最多能输入两位
     * 如果第一位是.则前面加上0.
     * 如果第一位是0则后面必须输入点，否则不能输入。
     */
    
    // 判断是否有小数点
    if ([textField.text containsString:@"."]) {
        self.isHaveDian = YES;
    }else{
        self.isHaveDian = NO;
    }
    
    if (string.length > 0) {
        
        //当前输入的字符
        unichar single = [string characterAtIndex:0];
        ChZ_DebugLog(@"single = %c",single);
        
        // 不能输入.0-9以外的字符
        if (!((single >= '0' && single <= '9') || single == '.'))
        {
            ChZ_MBError(@"您的输入格式不正确")
            return NO;
        }
        
        // 只能有一个小数点
        if (self.isHaveDian && single == '.') {
            ChZ_MBError(@"最多只能输入一个小数点")
            return NO;
        }
        
        // 如果第一位是.则前面加上0.
        if ((textField.text.length == 0) && (single == '.')) {
            textField.text = @"0";
        }
        
        // 如果第一位是0则后面必须输入点，否则不能输入。
        if ([textField.text hasPrefix:@"0"]) {
            if (textField.text.length > 1) {
                NSString *secondStr = [textField.text substringWithRange:NSMakeRange(1, 1)];
                if (![secondStr isEqualToString:@"."]) {
                    ChZ_MBError(@"第二个字符需要是小数点")
                    return NO;
                }
            }else{
                if (![string isEqualToString:@"."]) {
                    ChZ_MBError(@"第二个字符需要是小数点")
                    return NO;
                }
            }
        }
        if (self.isHaveDian)
        {
            NSArray *textArray = [textField.text componentsSeparatedByString:@"."];
            NSString *afterDian = textArray[1];
            if (afterDian.length == 4 && string.length >0)
            {
                return NO;
            }
        }
        //        // 小数点后最多能输入两位
        //        if (self.isHaveDian) {
        //            NSRange ran = [textField.text rangeOfString:@"."];
        //            // 由于range.location是NSUInteger类型的，所以这里不能通过(range.location - ran.location)>2来判断
        //            if (!ChZ_StringIsEmpty(self.model.digit))
        //            {
        //                return YES;
        //            }
        //            NSArray *array = [self.model.digit componentsSeparatedByString:@"#"];
        //            if (array == nil || array.count !=2)
        //            {
        //                return YES;
        //            }
        //            NSString *constraintPriceString = [array firstObject];
        //            NSString *constraintNumberString = [array lastObject];
        //            int constraintPrice = [constraintPriceString intValue];
        //            int constraintNumber = [constraintNumberString intValue];
        //            if (range.location > ran.location) {
        //                if ((self.muchTextFeild == textField || self.priceTextFeild == textField) && [self.priceTextFeild.text pathExtension].length > constraintPrice -1)
        //                {
        //                    NSString *msg = [NSString stringWithFormat:@"委托价格最多有%@位小数",constraintPriceString];
        //                    ChZ_MBError(msg)
        //                    return NO;
        //                }
        //            }
        //        }
    }
    return YES;
}
- (IBAction)allIn:(id)sender
{
    self.money.text = self.coinHadMoney;
    self.realMoney.text = [NSString stringWithFormat:@"实际到账 %.4f",[self.money.text floatValue]*(1 - self.withdrawFee)];
}
- (IBAction)areaListAction:(id)sender
{
    XHHAreaListController *vc = [[XHHAreaListController alloc] init];
    vc.coinId = self.model.coinId;
    @weakify(self);
    vc.callBackBlock = ^(AddressListModel *addressModel)
    {
        @strongify(self);
        self.area.text = addressModel.fadderess;
        self.addressModel = addressModel;
    };
    [self.navigationController pushViewController:vc animated:YES];
}

- (IBAction)sendAction:(id)sender
{
    if(self.area.text.length == 0)
    {
        ChZ_MBError(@"请输入/选择收款地址");
        return;
    }
    if (self.money.text.length == 0) {
        ChZ_MBError(@"请输入转出数量");
        return;
    }
    if ([APPControl sharedAPPControl].isSetPayPassword)
    {
        [self showPhoneCodeView];
    }else
    {
        [self showTheAlertVCWithStyle:UIAlertControllerStyleAlert title:nil message:@"您还未设置交易密码，快去设置吧！" title1:@"取消" action1:^{
            
        } title2:@"马上去" action2:^{
            XHHMineSetpswViewController *vc = [XHHMineSetpswViewController initWithStoryboard:@"MineStroy"];
            [self.navigationController pushViewController:vc animated:YES];
        } title3:nil action3:nil completion:nil];
    }
    [self.view endEditing:YES];
}
-(void)zh_setupUI{
    CGFloat navHeight = ChZ_IsiPhoneX ? 24 : 0;
    
    [self.view addSubview:self.navView];
    [self.navView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.top.right.equalTo(self.view);
        make.height.mas_equalTo(navHeight +64);
    }];
    
    [self.view addSubview:self.trunOutView];
    [self.trunOutView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.right.bottom.equalTo(self.view);
        make.top.equalTo(self.navView.mas_bottom);
    }];
}
-(XHHPublicNavView *)navView
{
    if (!_navView) {
        _navView = [[[NSBundle mainBundle] loadNibNamed:@"XHHPublicNavView" owner:nil options:nil] lastObject];
        [_navView setLeftButtonTitle:[NSString stringWithFormat:@"发送%@",self.model.shortName] image:@"public_nav_leftBack"];
        _navView.leftWithd.constant += 60;
        [_navView setrightButtonTitles:nil images:@[@"mine_wallet_sao"]];
        _navView.rightOneWithd.constant = 44;
        _navView.delegete = self;
    }
    return _navView;
}
-(void)rightButtonActionIndex:(NSInteger)index{
    ChZScanQRController *vc = [ChZScanQRController initWithStoryboard:@"Scan"];;
    @weakify(self)
    vc.callBackBlock = ^(NSString *obj)
    {
        @strongify(self)
        self.area.text = obj;
    };
    [self.navigationController pushViewController:vc animated:YES];
}
-(void)showPhoneCodeView{
    [self requestSignMsgCode];
    
    CGFloat bottom = sgm_safeAreaInset(self.view).bottom;
    XTTMPSAddAddressTelVerCodeView *telVerCodeView = [XTTMPSAddAddressTelVerCodeView loadFromNib];
    telVerCodeView.frame = CGRectMake(0, 0, self.view.width, 234 + bottom);
    self.controller = [TYAlertController alertControllerWithAlertView:telVerCodeView preferredStyle:TYAlertControllerStyleActionSheet];
    self.controller.backgoundTapDismissEnable = YES;
    [self presentViewController:self.controller animated:YES completion:nil];
    
    telVerCodeView.verCodeInputBlock = ^(NSString *text) {
        if (text.length == 6)
        {
            self.phoneCode = text;
            [self.controller dismissViewControllerAnimated:YES];
            [self showTradeView];
            
        }
    };
    telVerCodeView.dismissBlock = ^{
        [self.controller dismissViewControllerAnimated:YES];
        
    };
    telVerCodeView.sendTelCodeBlock = ^{
        [self requestSignMsgCode];
        
    };
}
-(void)showTradeView{
    
    CGFloat bottom = sgm_safeAreaInset(self.view).bottom;
    
    XTTMPSTradePwdView *tradePwdView = [XTTMPSTradePwdView loadFromNib];
    tradePwdView.frame = CGRectMake(0, 0, self.view.width, 253 + bottom);
    self.controller = [TYAlertController alertControllerWithAlertView:tradePwdView preferredStyle:TYAlertControllerStyleActionSheet];
    self.controller.backgoundTapDismissEnable = YES;
    [self presentViewController:self.controller animated:YES completion:nil];
    
    tradePwdView.sureBlock = ^(NSString *text)
    {
        UserModel *user = [APPControl sharedAPPControl].user;
        if ([user.fgooglebind isEqualToString:@"1"])
        {
            self.tradeCode = text;
            [self.controller dismissViewControllerAnimated:YES];
            [self showGoogleCodeView];
        }else
        {
            [self.controller dismissViewControllerAnimated:YES];
            [self requestOutCoinPhoneCode:self.phoneCode googleCode:nil address:self.area.text password:text amount:self.money.text addressId:self.addressModel.fid symbolId:self.addressModel.fcoinid];
        }
    };
    tradePwdView.dismissBlock = ^{
        [self.controller dismissViewControllerAnimated:YES];
        
    };
    tradePwdView.forgetTradePwdBlock = ^{
        [self.controller dismissViewControllerAnimated:YES];
        [self forgetTradePwdAction];
    };
}
-(void)forgetTradePwdAction{
    XHHMineFrogetPswViewController *vc = [XHHMineFrogetPswViewController initWithStoryboard:@"MineStroy"];
    [self.navigationController pushViewController:vc animated:YES];
}
-(void)showGoogleCodeView{
    CGFloat bottom = sgm_safeAreaInset(self.view).bottom;
    XTTMPSAddAddressGoogleCodeView *googleCodeView = [XTTMPSAddAddressGoogleCodeView loadFromNib];
    googleCodeView.frame = CGRectMake(0, 0, self.view.width, 170 + bottom);
    self.controller = [TYAlertController alertControllerWithAlertView:googleCodeView preferredStyle:TYAlertControllerStyleActionSheet];
    self.controller.backgoundTapDismissEnable = YES;
    [self presentViewController:self.controller animated:YES completion:nil];
    
    googleCodeView.verCodeInputBlock = ^(NSString *text)
    {
        if (text.length == 6)
        {
            [self.controller dismissViewControllerAnimated:YES];
            [self requestOutCoinPhoneCode:self.phoneCode googleCode:text address:self.area.text password:self.tradeCode amount:self.money.text addressId:self.addressModel.fid symbolId:self.addressModel.fcoinid];
            
        }
    };
    googleCodeView.dismissBlock = ^{
        [self.controller dismissViewControllerAnimated:YES];
    };
    
}
- (void)requestOutCoinPhoneCode:(NSString *)phoneCode
                     googleCode:(NSString *)googleCode
                        address:(NSString *)address
                       password:(NSString *)password
                         amount:(NSString *)amout
                      addressId:(NSString *)addressId
                       symbolId:(NSString *)symbolId{
    ChZ_MBMsg(nil)
    @weakify(self);
    [WalletNetWorkControl requestOutCoinPhoneCode:phoneCode googleCode:googleCode address:address password:password amount:amout addressId:addressId symbolId:self.model.coinId successBlock:^(id dataSource) {
        @strongify(self);
        ChZ_MBDismss
        ChZ_MBSuccess(@"转出成功")
        [[NSNotificationCenter defaultCenter] postNotificationName:@"THROUNTCOINSUCCESS" object:nil];
        [self popToVCClassName:@"XHHWalletController"];
    } errorBlock:^(int code, NSString *error) {
        ChZ_MBDismss
        ChZ_MBError(error)
    }];
}
- (void)requestWithdraGESTFee:(NSString *)coinId{
    ChZ_MBMsg(nil)
    @weakify(self);
    [WalletNetWorkControl requestWithdraGESTFee:coinId successBlock:^(id dataSource)
    {
        @strongify(self);
        ChZ_MBDismss
        self.serviceFeeModdel = dataSource;
        self.withdrawFee = self.serviceFeeModdel.withdrawFee;
        self.useMoney.text = [NSString stringWithFormat:@"手续费：%.4f",self.serviceFeeModdel.withdrawFee ];
    } errorBlock:^(int code, NSString *error) {
        ChZ_MBDismss
        ChZ_MBError(error)
    }];
    [ChZHomeRequest requestCnyHaveNumUid:[APPControl sharedAPPControl].user.fid cnyId:coinId successBlock:^(NSString *dataSource)
     {
        @strongify(self);
        if (ChZ_StringIsEmpty(dataSource))
        {
            self.hadMoney.text = [NSString stringWithFormat:@"%.4f%@",[dataSource doubleValue],self.model.shortName];
            self.coinHadMoney = dataSource;
        }else
        {
            self.hadMoney.text = [NSString stringWithFormat:@"0.0000%@",self.model.shortName];
        }
        
    } errorBlock:^(int code, NSString *error) {
        ChZ_MBError(@"查询币种余额失败");
    }];
}
-(void)requestSignMsgCode{
    
    [WalletNetWorkControl requestSignMsgCodeSuccessBlock:^(id dataSource)
     {
         
     } errorBlock:^(int code, NSString *error)
     {
         ChZ_MBError(error)
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
