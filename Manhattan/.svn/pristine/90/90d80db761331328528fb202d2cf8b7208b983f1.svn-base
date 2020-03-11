//
//  XHHReablesAreaController.m
//  Manhattan
//
//  Created by Apple on 2018/8/17.
//  Copyright © 2018年 Apple. All rights reserved.
//

#import "XHHReablesAreaController.h"
#import "XHHRecevableView.h"
#import "XHHPublicNavView.h"
#import "UIImage+ChZExtension.h"
@interface XHHReablesAreaController ()
@property (nonatomic , strong) XHHPublicNavView           *navView;

@property (nonatomic , strong) XHHRecevableView *contentView;
@end

@implementation XHHReablesAreaController

- (void)viewDidLoad {
    [super viewDidLoad];
    
    [self zh_setupUI];
    
    [self requestRecharge];
    // Do any additional setup after loading the view.
}

-(void)zh_setupUI{
    CGFloat navHeight = ChZ_IsiPhoneX ? 24 : 0;
    
    [self.view addSubview:self.navView];
    [self.navView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.top.right.equalTo(self.view);
        make.height.mas_equalTo(navHeight +64);
    }];
    
    [self.view addSubview:self.contentView];
    [self.contentView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.right.bottom.equalTo(self.view);
        make.top.equalTo(self.navView.mas_bottom);
    }];

}
-(XHHPublicNavView *)navView{
    if (!_navView) {
        _navView = [[[NSBundle mainBundle] loadNibNamed:@"XHHPublicNavView" owner:nil options:nil] lastObject];
        [_navView setLeftButtonTitle:[NSString stringWithFormat:@"我的 %@ 收款地址",self.model.shortName] image:@"public_nav_leftBack"];
        _navView.leftWithd.constant += 60;
    }
    return _navView;
}
- (XHHRecevableView *)contentView{
    if (!_contentView) {
        _contentView = [[[NSBundle mainBundle] loadNibNamed:@"XHHRecevableView" owner:nil options:nil] lastObject];
        _contentView.backgroundColor = [UIColor clearColor];
    }
    return _contentView;
}

-(void)requestRecharge{
    
    @weakify(self);
    ChZ_MBMsg(nil);
    [WalletNetWorkControl requestRecharge:self.model.coinId successBlock:^(id dataSource)
     {
         @strongify(self);
         ChZ_MBDismss
         if (dataSource == nil)
         {
             [self requestEthArea];
         }else
         {
             NSDictionary *dict = dataSource;
             NSDictionary *rechargeAddressDict = dict[@"rechargeAddress"];
             if (rechargeAddressDict == nil) return;
             NSString * faddress = rechargeAddressDict[@"fadderess"];
             if (!(ChZ_StringIsEmpty(faddress))) return;
             
             self.contentView.areaQrImage.image = [UIImage qrImageForString:faddress imageSize:self.contentView.areaQrImage.width];
             
             self.contentView.areaLable.text = faddress;
         }
     } errorBlock:^(int code, NSString *error)
     {
         @strongify(self);
         ChZ_MBDismss
         [self requestEthArea];
        // ChZ_MBError(error)
     }];
}
-(void)requestEthArea
{
    //ChZ_MBMsg(nil);
    [WalletNetWorkControl requestRecharge:[APPControl sharedAPPControl].ETHModel.coinId successBlock:^(id dataSource)
     {
         ChZ_MBDismss
         if (dataSource == nil) return ;
         NSDictionary *dict = dataSource;
         NSDictionary *rechargeAddressDict = dict[@"rechargeAddress"];
         if (rechargeAddressDict == nil) return;
         NSString * faddress = rechargeAddressDict[@"fadderess"];
         if (!(ChZ_StringIsEmpty(faddress))) return;
         
         self.contentView.areaQrImage.image = [UIImage qrImageForString:faddress imageSize:self.contentView.areaQrImage.width];
         
         self.contentView.areaLable.text = faddress;
         
     } errorBlock:^(int code, NSString *error) {
         ChZ_MBDismss
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
