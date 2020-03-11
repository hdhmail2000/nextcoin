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

#import "XHHPublicNavView.h"

#import "XHHPWInputView.h"

@interface XHHTradBuyOrSellController ()<XHHPublicNavViewProtocol,YXTextFieldDelegate>
@property (nonatomic , strong) XHHPWInputView *pwInputView;
// 输入框的透明背景视图
@property (strong, nonatomic) UIView *inputBackgroundView;

@property (weak, nonatomic) IBOutlet UITextField *priceLable;

@property (weak, nonatomic) IBOutlet UITextField *numLable;

@property (weak, nonatomic) IBOutlet UILabel *allMoneyLable;

@property (weak, nonatomic) IBOutlet UIButton *tButton;

@property (weak, nonatomic) IBOutlet NSLayoutConstraint *navHeight;

@property (nonatomic , strong) XHHPublicNavView           *navView;

@property (nonatomic , strong) UILabel *successLable;

@end

@interface XHHTradBuyOrSellController ()

@end

@implementation XHHTradBuyOrSellController

- (void)viewDidLoad {
    [super viewDidLoad];
    [_numLable configPlaceholderWithFont:[UIFont systemFontOfSize:16] textColor:[UIColor colorWithHexString:@"4B4B80"]];
    if (ChZ_IsiPhoneX) {
        _navHeight.constant+=24;
    }
    [self.view addSubview:self.navView];
    [self.navView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.top.right.equalTo(self.view);
        make.height.mas_equalTo(self.navHeight.constant);
    }];
    // Do any additional setup after loading the view from its nib.
}
-(XHHPublicNavView *)navView{
    if (!_navView) {
        _navView = [[[NSBundle mainBundle] loadNibNamed:@"XHHPublicNavView" owner:nil options:nil] lastObject];
        if (self.type == 1) {
            [_navView setLeftButtonTitle:@"买入" image:@"public_nav_leftBack"];
        }else{
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
    if (_type == 2) {
        [_tButton setBackgroundImage:[UIImage imageNamed:@"trad_explicit_botton_sell"] forState:UIControlStateNormal];
        [_tButton setTitle:@"确定卖出" forState:UIControlStateNormal];
    }
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
    [self.navigationController pushViewController:vc animated:YES];
}
- (IBAction)payAction:(id)sender {
    [self.pwInputView clearValue];
    [UIView animateWithDuration:0.2 animations:^{
        self.inputBackgroundView.hidden = NO;
        self.pwInputView.frame = CGRectMake(0, CGRectGetHeight(self.inputBackgroundView.frame) - 247, CGRectGetWidth(self.inputBackgroundView.frame), 247);
        [self.pwInputView makeFirstInputBecomeFirstResponder];
    }];
}



-(XHHPWInputView *)pwInputView{
    if (!_pwInputView) {
        _pwInputView = [XHHPWInputView initXibFileWithDelegate:self];
        _pwInputView.backgroundColor = [UIColor colorWithHexString:@"16263E"];
        _pwInputView.frame = CGRectMake(0, CGRectGetMaxY(self.view.frame), CGRectGetWidth(self.view.frame), 247);
        [self.inputBackgroundView addSubview:_pwInputView];
        @weakify(self);
        _pwInputView.closeBlock = ^{
            @strongify(self);
            [self hideInputBackgroundViewAndInputView];
        };
        _pwInputView.finishBlock = ^{
            @strongify(self);
            [self hideInputBackgroundViewAndInputView];
        };
        
    }
    return _pwInputView;
}
#pragma mark - 视图创建
/**
 *  输入视图的背景视图
 *
 */
- (UIView *)inputBackgroundView{
    if(_inputBackgroundView == nil){
        _inputBackgroundView = [[UIView alloc] initWithFrame:self.view.bounds];
        _inputBackgroundView.backgroundColor = [UIColor clearColor];
        [self.view addSubview:_inputBackgroundView];
    }
    return _inputBackgroundView;
}


/**
 *  隐藏输入视图
 */
- (void)hideInputBackgroundViewAndInputView{
    [self.view endEditing:YES];
    [UIView animateWithDuration:0.3 animations:^{
        self.pwInputView.frame = CGRectMake(0, CGRectGetMaxY(self.view.frame), CGRectGetWidth(self.view.frame), 400);
    }];
    self.inputBackgroundView.hidden = YES;
}

#pragma mark - 代理
// 输入框代理
- (BOOL)textField:(UITextField *)textField shouldChangeCharactersInRange:(NSRange)range replacementString:(NSString *)string{

    if([string isEqualToString:@""]){// 删除
        [self.pwInputView clearValue];
        [self.pwInputView makeFirstInputBecomeFirstResponder];
    }else{// 填写
        textField.text = string;
        [self.pwInputView moveFirstResponder];
    }
    if([self.pwInputView isSixthInputFinished]){
        // 密码输入完成，网络请求提现
        // 密码是
        NSString *cashPwd = [NSString stringWithFormat:@"%@%@%@%@%@%@", [self.pwInputView returnFirstInputViewValue], [self.pwInputView returnSecondInputViewValue], [self.pwInputView returnThirdInputViewValue], [self.pwInputView returnFourthInputViewValue], [self.pwInputView returnFifthInputViewValue], [self.pwInputView returnSixthInputViewValue]];
        NSLog(@"cashPwd is %@", cashPwd);
        [self.navView addSubview:self.successLable];
        [self.successLable mas_makeConstraints:^(MASConstraintMaker *make) {
            make.left.equalTo(self.navView).offset(16);
            make.right.equalTo(self.navView).offset(-16);
            make.bottom.equalTo(self.navView);
            make.height.mas_equalTo(44);
        }];
        dispatch_after(dispatch_time(DISPATCH_TIME_NOW, (int64_t)(3 * NSEC_PER_SEC)), dispatch_get_main_queue(), ^{
            [self.successLable removeFromSuperview];
        });
        
        [self hideInputBackgroundViewAndInputView];
    }
    return NO;
}
- (void)textFieldDidDeleteBackward:(UITextField *)textField {
    
    [self.pwInputView clearValue];
    [self.pwInputView makeFirstInputBecomeFirstResponder];
    
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
