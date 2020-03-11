//
//  XHHSellBuyController.m
//  FuturePurse
//
//  Created by Apple on 2018/7/27.
//  Copyright © 2018年 jbtm. All rights reserved.
//

#import "XHHSellBuyController.h"

#import "XHHTadTypeChooesView.h"

#import "XHHPublicItemsView.h"

#import "XHHWillBuyView.h"

#import "XHHWillSellView.h"

#import "XHHTradReleaseView.h"

#import "XHHPublicNavView.h"

#import "XHHPWInputView.h"

@interface XHHSellBuyController ()<XHHPublicItemsViewProtocol,UIScrollViewDelegate,YXTextFieldDelegate>
@property (nonatomic , strong) XHHPWInputView *pwInputView;
// 输入框的透明背景视图
@property (strong, nonatomic) UIView *inputBackgroundView;

@property (strong, nonatomic) XHHTadTypeChooesView        *typeChooseView;

@property (strong, nonatomic) XHHPublicItemsView          *itemsView;

@property (strong, nonatomic) XHHWillBuyView              *buyView;

@property (strong, nonatomic) XHHWillSellView             *sellView;

@property (strong, nonatomic) XHHTradReleaseView          *releaseView;

@property (nonatomic , strong) XHHPublicNavView           *navView;

@property (strong, nonatomic) UIScrollView                *scrollView;

@property (strong, nonatomic) UILabel                     *alertLable;


@end

@implementation XHHSellBuyController

- (void)viewDidLoad {
    [super viewDidLoad];
    
    
    [self zh_setupUI];
}

-(void)zh_setupUI{
    CGFloat navHeight = ChZ_IsiPhoneX ? 24 : 0;
    
    [self.view addSubview:self.navView];
    [self.navView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.top.right.equalTo(self.view);
        make.height.mas_equalTo(navHeight +64);
    }];
    
    [self.view addSubview:self.itemsView];
    [self.itemsView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.right.equalTo(self.view);
        make.top.equalTo(self.navView.mas_bottom);
        make.height.mas_equalTo(46);
    }];
    
    [self.view addSubview:self.scrollView];
    [self.scrollView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.right.equalTo(self.view);
        make.top.equalTo(self.itemsView.mas_bottom);
        make.bottom.equalTo(self.view).offset(-navHeight);
    }];
    [self.scrollView addSubview:self.buyView];
    [self.scrollView addSubview:self.sellView];
    [self.scrollView addSubview:self.releaseView];
    [self.scrollView setContentSize:CGSizeMake(ChZ_WIDTH*3, 0)];
    
    
}
- (void)viewDidLayoutSubviews{
    CGFloat navHeight = ChZ_IsiPhoneX ? 20 : 0;
    [self.buyView setFrame:CGRectMake(16, 0, ChZ_WIDTH - 32, 420)];
    [self.sellView setFrame:CGRectMake(16 + ChZ_WIDTH, 0, ChZ_WIDTH - 32, 420)];
    [self.releaseView setFrame:CGRectMake(2*ChZ_WIDTH, 0, ChZ_WIDTH, ChZ_HEIGHT - 2*navHeight - 46 - 64)];
}
-(UIScrollView *)scrollView{
    if (!_scrollView) {
        _scrollView = [[UIScrollView alloc] init];
        _scrollView.showsVerticalScrollIndicator = NO;
        _scrollView.showsHorizontalScrollIndicator = NO;
        _scrollView.pagingEnabled = YES;
        _scrollView.scrollEnabled = NO;
        _scrollView.delegate = self;
    }
    return _scrollView;
}
-(XHHPublicNavView *)navView{
    if (!_navView) {
        _navView = [[[NSBundle mainBundle] loadNibNamed:@"XHHPublicNavView" owner:nil options:nil] lastObject];
        [_navView setLeftButtonTitle:@"发布" image:@"public_nav_leftBack"];
    }
    return _navView;
}
-(UILabel *)alertLable{
    if (!_alertLable) {
        _alertLable = [UILabel newSingleFrame:CGRectZero title:@"发布成功" fontS:14.0 color:[UIColor colorWithHexString:@"FFFFFF"]];
        _alertLable.textAlignment = NSTextAlignmentCenter;
        _alertLable.backgroundColor = [UIColor colorWithHexString:@"363672"];
    }
    return _alertLable;
}
-(XHHWillSellView *)sellView{
    if (!_sellView) {
        _sellView = [[[NSBundle mainBundle] loadNibNamed:@"XHHWillSellView" owner:nil options:nil] lastObject];
        @weakify(self);
        _sellView.payBlock = ^{
            @strongify(self);
            [self.pwInputView clearValue];
            [UIView animateWithDuration:0.2 animations:^{
                self.inputBackgroundView.hidden = NO;
                self.pwInputView.frame = CGRectMake(0, CGRectGetHeight(self.inputBackgroundView.frame) - 247, CGRectGetWidth(self.inputBackgroundView.frame), 247);
                [self.pwInputView makeFirstInputBecomeFirstResponder];
            }];
        };
        
    }
    return _sellView;
}
-(XHHWillBuyView *)buyView{
    if (!_buyView) {
        _buyView = [[[NSBundle mainBundle] loadNibNamed:@"XHHWillBuyView" owner:nil options:nil] lastObject];
        @weakify(self);
        _buyView.payBlock = ^{
            @strongify(self);
            [self.pwInputView clearValue];
            [UIView animateWithDuration:0.2 animations:^{
                self.inputBackgroundView.hidden = NO;
                self.pwInputView.frame = CGRectMake(0, CGRectGetHeight(self.inputBackgroundView.frame) - 247, CGRectGetWidth(self.inputBackgroundView.frame), 247);
                [self.pwInputView makeFirstInputBecomeFirstResponder];
            }];
        };
    }
    return _buyView;
}
-(XHHTradReleaseView *)releaseView{
    if (!_releaseView) {
        _releaseView = [[XHHTradReleaseView alloc] init];
    }
    return _releaseView;
}
-(XHHPublicItemsView *)itemsView{
    if (!_itemsView) {
        _itemsView = [[XHHPublicItemsView alloc] init];
        [_itemsView zh_setupUIWithItems:@[@"买入",@"卖出",@"我的发布"]];
        [_itemsView setItemSelectedIndex:self.type-1];
        [self itemsClickIndex:self.type -1];
        _itemsView.delegate = self;
    }
    return _itemsView;
}
-(XHHTadTypeChooesView *)typeChooseView{
    if (!_typeChooseView) {
        _typeChooseView = [[XHHTadTypeChooesView alloc] init];
    }
    return _typeChooseView;
}
-(void)itemsClickIndex:(NSInteger)index{
    switch (index) {
        case 0:
            [self.scrollView setContentOffset:CGPointMake(0, 0) animated:YES];
            break;
        case 1:
            [self.scrollView setContentOffset:CGPointMake(ChZ_WIDTH, 0) animated:YES];
            break;
        case 2:
            [self.scrollView setContentOffset:CGPointMake(2*ChZ_WIDTH, 0) animated:YES];
            
            break;
            
        default:
            break;
    }
}
-(void)scrollViewDidScroll:(UIScrollView *)scrollView{
    
    
    if (scrollView.contentOffset.x == 0) {
        [self.itemsView setItemSelectedIndex:0];
    }else if (scrollView.contentOffset.x == ChZ_WIDTH){
        [self.itemsView setItemSelectedIndex:1];
    }else if (scrollView.contentOffset.x == ChZ_WIDTH*2){
        [self.itemsView setItemSelectedIndex:2];
    }
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
        self.itemsView.hidden = YES;
        [self.view addSubview:self.alertLable];
        [self.alertLable mas_makeConstraints:^(MASConstraintMaker *make) {
            make.left.equalTo(self.view).offset(16);
            make.top.equalTo(self.navView.mas_bottom);
            make.right.equalTo(self.view).offset(-16);
            make.height.mas_equalTo(46);
        }];
        dispatch_after(dispatch_time(DISPATCH_TIME_NOW, (int64_t)(3 * NSEC_PER_SEC)), dispatch_get_main_queue(), ^{
            self.itemsView.hidden = NO;
            [self.alertLable removeFromSuperview];
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
