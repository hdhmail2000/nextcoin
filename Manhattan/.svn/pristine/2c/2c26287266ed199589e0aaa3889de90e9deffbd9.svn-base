//
//  XHHRealseBuyViewController.m
//  Manhattan
//
//  Created by Apple on 2018/9/11.
//  Copyright © 2018年 Apple. All rights reserved.
//

#import "XHHRealseBuyViewController.h"
#import "XHHMineSetpswViewController.h"
#import "XHHWillBuyView.h"
#import "XHHPayPwView.h"
@interface XHHRealseBuyViewController ()<YXTextFieldDelegate,XHHPayPwViewProtocol>

@property (strong, nonatomic) XHHWillBuyView  *buyView;

@property (strong, nonatomic) XHHPayPwView     *passWordView;//输入交易密码视图

@end

@implementation XHHRealseBuyViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    
    [self zh_setupUI];
    
    if (self.cnyList.count)
    {
        XHHC2CCnyListModel *model = self.cnyList[0];
        self.cnyId = model.fid;
        self.buyView.maxType.text = model.short_name;
        self.buyView.minType.text = model.short_name;
        self.buyView.numType.text = model.short_name;
        self.buyView.cnyTypLable.text = model.short_name;
        self.buyView.cnyModel = model;
        @weakify(self);
        [ChZHomeRequest requestCoinReleasePriceCoinName:model.short_name successBlock:^(id dataSource)
        {
            @strongify(self);
            if(dataSource)
            {
                self.buyView.currPriceString = [NSString stringWithFormat:@"%@",[dataSource objectForKey:@"price"]];
                self.buyView.currPrice.text = [NSString stringWithFormat:@"%@",[dataSource objectForKey:@"price"]];
            }else
            {
                ChZ_MBError(@"获取当前价格失败");
            }
        } errorBlock:^(NSString *error) {
            
        }];
    }
    self.buyView.cnyList = self.cnyList;
}

-(void)zh_setupUI{
    //背景滚动视图
    UIScrollView *bgScrollView = [[UIScrollView alloc]init];
    bgScrollView.showsVerticalScrollIndicator = NO;
    [self.view addSubview:bgScrollView];
    [bgScrollView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.edges.equalTo(self.view).insets(UIEdgeInsetsMake(0, 16, 0, 16));
    }];
    
    [bgScrollView addSubview:self.buyView];
    [self.buyView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.top.left.bottom.right.equalTo(bgScrollView).insets(UIEdgeInsetsZero);
        make.width.mas_equalTo(ChZ_WIDTH-32);
        make.height.mas_equalTo(500);
    }];
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}
-(XHHWillBuyView *)buyView
{
    if (!_buyView)
    {
        _buyView = [[[NSBundle mainBundle] loadNibNamed:@"XHHWillBuyView" owner:nil options:nil] lastObject];
        @weakify(self);
        _buyView.payBlock = ^{
            @strongify(self);
            if (self.buyView.currPrice.text.length ==0)
            {
                ChZ_MBError(@"请输入当前价格");
                return;
            }
            if (self.buyView.buyNumber.text.length ==0)
            {
                ChZ_MBError(@"请输入购买数量");
                return;
            }
            if (self.buyView.minSellNumber.text.length ==0)
            {
                ChZ_MBError(@"请输入最小出售数量");
                return;
            }
            if (self.buyView.payTypeTextFeild.text.length ==0)
            {
                ChZ_MBError(@"请选择支付方式");
                return;
            }
            if([self.buyView.minSellNumber.text floatValue] > [self.buyView.buyNumber.text floatValue])
            {
                ChZ_MBError(@"最小出售数量不得大于购买数量");
                return;
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
                     [[ChZUtil getCurrentVC].navigationController pushViewController:vc animated:YES];
                 } title3:nil action3:nil completion:nil];
            }
            [self.view endEditing:YES];
        };
    }
    return _buyView;
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
    if (!ChZ_StringIsEmpty(pwd))
    {
        ChZ_MBError(@"请输入密码");
        return;
    }
    [self requestBuyWithPassWord:pwd];
}
//发布买入
-(void)requestBuyWithPassWord:(NSString *)passWord{
    
    if (!ChZ_StringIsEmpty(passWord))
    {
        ChZ_MBError(@"请输入交易密码")
        return;
    }
    ChZ_MBMsg(nil)
    @weakify(self);
    [ChZHomeRequest requestC2CSendBuyUid:[APPControl sharedAPPControl].user.fid
                                passWord:passWord
                                 country:@"China"
                                     num:self.buyView.buyNumber.text
                                   price:self.buyView.currPrice.text
                                   cnyId:self.buyView.cnyModel.fid
                                minValue:self.buyView.minSellNumber.text
                                maxValue:self.buyView.maxSellNumber.text
                                 payType:self.buyView.payTypeTextFeild.text
                              symbolName:self.buyView.minType.text
                            successBlock:^(id dataSource)
     {
         //        ChZ_MBDismssSuccess(@"发布买入成功");
         @strongify(self);
         self.buyView.buyNumber.text = nil;
         self.buyView.currPrice.text = nil;
         self.buyView.minSellNumber.text = nil;
         self.buyView.maxSellNumber.text = nil;
         self.buyView.payTypeTextFeild.text = nil;
         ChZ_MBDismss
         if (self.buySuccessBlock) self.buySuccessBlock();
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
