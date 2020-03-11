//
//  XHHTradBuyOrSellController.m
//  FuturePurse
//
//  Created by Apple on 2018/7/30.
//  Copyright © 2018年 jbtm. All rights reserved.
//

#import "XHHTradBuyOrSellController.h"

#import "XHHTradMyOrderController.h"

#import "XHHTradMyOrderController.h"

#import "XHHMineSetpswViewController.h"

#import "XHHPublicNavView.h"

#import "XHHPayPwView.h"

@interface XHHTradBuyOrSellController ()<XHHPublicNavViewProtocol,YXTextFieldDelegate,XHHPayPwViewProtocol,UITextFieldDelegate>
@property (strong, nonatomic) XHHPayPwView                *passWordView;//输入交易密码视图
// 输入框的透明背景视图
@property (strong, nonatomic) UIView *inputBackgroundView;

@property (weak, nonatomic) IBOutlet UITextField *priceLable;

@property (weak, nonatomic) IBOutlet UITextField *numLable;

@property (weak, nonatomic) IBOutlet UILabel *allMoneyLable;

@property (weak, nonatomic) IBOutlet UIButton *tButton;

@property (weak, nonatomic) IBOutlet NSLayoutConstraint *navHeight;

@property (weak, nonatomic) IBOutlet UILabel *cnyTypeLable;

@property (nonatomic , strong) XHHPublicNavView           *navView;

@property (nonatomic , strong) UILabel *successLable;

@property (nonatomic , assign) BOOL isHaveDian;

@end

@interface XHHTradBuyOrSellController ()

@end

@implementation XHHTradBuyOrSellController

- (void)viewDidLoad {
    [super viewDidLoad];
    
    [self zh_setupUI];
    
    [self.numLable addTarget:self action:@selector(valueAction) forControlEvents:UIControlEventEditingChanged];
    
    self.numLable.delegate = self;
    
}
-(void)valueAction
{
    if (self.numLable.text.length > 0)
    {
        self.allMoneyLable.text = [NSString stringWithFormat:@"%.2f",[self.numLable.text floatValue] * [self.model.order_price floatValue]];
    }
}
-(BOOL)textField:(UITextField *)textField shouldChangeCharactersInRange:(NSRange)range replacementString:(NSString *)string
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
    
    return YES;
}
-(XHHPublicNavView *)navView
{
    if (!_navView)
    {
        _navView = [[[NSBundle mainBundle] loadNibNamed:@"XHHPublicNavView" owner:nil options:nil] lastObject];
        if (self.tradType == C2CTradTypeBuy)
        {
            [_navView setLeftButtonTitle:@"买入" image:@"public_nav_leftBack"];
        }else
        {
            [_navView setLeftButtonTitle:@"卖出" image:@"public_nav_leftBack"];
        }
        
        [_navView setrightButtonTitles:@[@"我的订单"] images:nil];
        _navView.delegete = self;
    }
    return _navView;
}
-(UILabel *)successLable{
    if (!_successLable) {
        _successLable = [UILabel newSingleFrame:CGRectZero title:@"交易发起成功，请在订单中查看详情。" fontS:14.0 color:[UIColor whiteColor]];
        _successLable.backgroundColor = LineCorlor;
        _successLable.textAlignment = NSTextAlignmentCenter;
    }
    return _successLable;
}
-(void)zh_setupUI{
    self.priceLable.text = self.model.order_price;
    self.priceLable.userInteractionEnabled = NO;
    self.cnyTypeLable.text = self.model.symbol_name;
    if (ChZ_IsiPhoneX) {
        _navHeight.constant+=24;
    }
    [self.view addSubview:self.navView];
    [self.navView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.top.right.equalTo(self.view);
        make.height.mas_equalTo(self.navHeight.constant);
    }];
    if (self.tradType == C2CTradTypeBuy)
    {
        self.numLable.placeholder = [NSString stringWithFormat:@"购买数量不小于%.4f",[self.model.min_value floatValue]];
    }else if (self.tradType == C2CTradTypeSell)
    {
        self.numLable.placeholder = [NSString stringWithFormat:@"卖出数量不小于%.4f",[self.model.min_value floatValue]];
        [_tButton setBackgroundImage:[UIImage imageNamed:@"trad_c2c_sellbgimage"] forState:UIControlStateNormal];
        [_tButton setTitle:@"确定卖出" forState:UIControlStateNormal];
    }
    [_numLable configPlaceholderWithFont:[UIFont systemFontOfSize:16] textColor:[UIColor colorWithHexString:@"4B4B80"]];
}
-(void)back{
    [self.navigationController popViewControllerAnimated:YES];
}
-(void)myOrder{
    XHHTradMyOrderController *vc = [[XHHTradMyOrderController alloc] init];
    [self.navigationController pushViewController:vc animated:YES];
}
-(void)rightButtonActionIndex:(NSInteger)index{
    XHHTradMyOrderController *vc = [[XHHTradMyOrderController alloc] init];
    vc.tradType = self.tradType;
    vc.buySmbolName = self.model.symbol_name;
    [self.navigationController pushViewController:vc animated:YES];
}
- (IBAction)payAction:(id)sender {
    if (self.tradType == C2CTradTypeBuy)
    {
        if (self.numLable.text.length == 0)
        {
            
            ChZ_MBError(@"请输入购买数量");
            return;
        }
        if ([self.numLable.text floatValue] < [self.model.min_value floatValue]) {
            NSString *errString = [NSString stringWithFormat:@"购买数量不能低于%@",self.model.min_value];
            ChZ_MBError(errString);
            return;
        }
    }else if(self.tradType == C2CTradTypeSell)
    {
        if (self.numLable.text.length == 0)
        {
            
            ChZ_MBError(@"请输入卖出数量");
            return;
        }
        if ([self.numLable.text floatValue] < [self.model.min_value floatValue]) {
            NSString *errString = [NSString stringWithFormat:@"卖出数量不能低于%@",self.model.min_value];
            ChZ_MBError(errString);
            return;
        }
    }
    if ([APPControl sharedAPPControl].isSetPayPassword)
    {
        [self.passWordView showInView:self.view];
        self.passWordView.height = self.view.bounds.size.height;
    }else
    {
        [self showTheAlertVCWithStyle:UIAlertControllerStyleAlert title:nil message:@"您还未设置交易密码，快去设置吧！" title1:@"取消" action1:^{
            
        } title2:@"马上去" action2:^
        {
            XHHMineSetpswViewController *vc = [XHHMineSetpswViewController initWithStoryboard:@"MineStroy"];
            [self.navigationController pushViewController:vc animated:YES];
        } title3:nil action3:nil completion:nil];
    }
    [self.view endEditing:YES];
    
}

- (XHHPayPwView *)passWordView{
    if (!_passWordView)
    {
        _passWordView = [[[NSBundle mainBundle] loadNibNamed:@"XHHPayPwView" owner:nil options:nil] lastObject];
        _passWordView.delegate = self;
    }
    return _passWordView;
}
-(void)payViewSure:(NSString *)pwd{
    if (self.tradType == C2CTradTypeBuy)
    {
        [self requestBuyWithPassWord:pwd];
    }
    if(self.tradType == C2CTradTypeSell)
    {
        [self requestSellWithPassWord:pwd];
    }
}
-(void)requestSellWithPassWord:(NSString *)passWord
{
    ChZ_MBMsg(nil)
    @weakify(self);
    [ChZHomeRequest requestC2CSellOtherCnyUid:[APPControl sharedAPPControl].user.fid
                                       volume:self.numLable.text
                                        price:self.model.order_price
                                     trade_id:self.model.fid
                                      dealpsw:passWord
                                 successBlock:^(NSString *orderId)
     {
         @strongify(self);
         ChZ_MBDismss;
         [self showPaySuccessView];
     } errorBlock:^(int code, NSString *error)
     {
         ChZ_MBDismssError(error);
     }];
}
-(void)requestBuyWithPassWord:(NSString *)passWord
{
    ChZ_MBMsg(nil)
    @weakify(self);
    [ChZHomeRequest requestC2CBuyOtherCnyUid:[APPControl sharedAPPControl].user.fid
                                      volume:self.numLable.text
                                       price:self.model.order_price
                                    trade_id:self.model.fid
                                     dealpsw:passWord
                                successBlock:^(NSString *orderId)
     {
         @strongify(self);
         ChZ_MBDismss;
         [self showPaySuccessView];
     } errorBlock:^(int code, NSString *error) {
         ChZ_MBDismssError(error);
     }];
}
-(void)showPaySuccessView{
    [self.navView addSubview:self.successLable];
    [self.successLable mas_makeConstraints:^(MASConstraintMaker *make)
    {
        make.left.equalTo(self.navView).offset(16);
        make.right.equalTo(self.navView).offset(-16);
        make.bottom.equalTo(self.navView);
        make.height.mas_equalTo(44);
    }];
    self.navView.userInteractionEnabled = NO;
    @weakify(self);
    dispatch_after(dispatch_time(DISPATCH_TIME_NOW, (int64_t)(3 * NSEC_PER_SEC)), dispatch_get_main_queue(), ^{
        @strongify(self);
        [self.successLable removeFromSuperview];
        [self pop];
    });
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
