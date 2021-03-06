//
//  ChZReceiptAccountListController.m
//  FuturePurse
//
//  Created by Howe on 2018/8/3.
//  Copyright © 2018年 jbtm. All rights reserved.
//

#import "ChZReceiptAccountListController.h"
#import "ChZReceiptAccountListCell.h"
#import "ChZAddWechatAccountController.h"
#import "ChZAddAlipayAccountController.h"
#import "ChZAddBankAccountController.h"
#import "ChZAccountRequest.h"
#import "ChZUserPayAccountModel.h"
#import "ChZPopQRCodeView.h"

@interface ChZReceiptAccountListController ()<UITableViewDataSource,UITableViewDelegate>
@property (weak, nonatomic) IBOutlet UITableView *tableView;
@property (nonatomic, strong) NSArray *dataSource;
@property (weak, nonatomic) IBOutlet UIButton *addButton;
@property (nonatomic, strong) ChZUserPayAccountModel *model;
@end

@implementation ChZReceiptAccountListController

- (void)viewDidLoad {
    [super viewDidLoad];
    [self _setupView];
    [self chz_beginRefresh];
}
- (IBAction)backAction:(id)sender
{
    [self pop];
}

- (void)chz_beginRefresh
{
    ChZ_Weak
    [ChZAccountRequest requestPayAccountListSuccessBlock:^(ChZUserPayAccountModel *model)
     {
         ChZ_Strong
         _strongSelf.model = model;
         [_strongSelf.refreshControl endRefreshing];
     } errorBlock:^(int code, NSString *error)
     {
         ChZ_Strong
         [_strongSelf.refreshControl endRefreshing];
         ChZ_MBError(error)
     }];
}

- (void)setModel:(ChZUserPayAccountModel *)model
{
    _model = model;
    if ( model != nil && model.bank != nil && model.alipay != nil && model.weixin != nil)
    {
        self.addButton.hidden = YES;
    }else{
        self.addButton.hidden = NO;
    }
    [self.tableView reloadData];
}

- (IBAction)addAccountAction:(id)sender
{
    UIAlertController *alertVC = [UIAlertController alertControllerWithTitle:@"添加账号" message:nil preferredStyle:UIAlertControllerStyleActionSheet];
    if (self.model.alipay == nil)
    {
        [alertVC addAction:[UIAlertAction actionWithTitle:@"支付宝" style:UIAlertActionStyleDefault handler:^(UIAlertAction * _Nonnull action)
                            {
                                ChZAddAlipayAccountController *vc = [ChZAddAlipayAccountController initWithStoryboard:@"ReceiptAccount"];
                                ChZ_Weak
                                vc.callBackBlock = ^(id Obj) {
                                    ChZ_Strong
                                    [_strongSelf chz_beginRefresh];
                                };
                                [self.navigationController pushViewController:vc animated:YES];
                            }]];
    }
    if (self.model.weixin == nil)
    {
        [alertVC addAction:[UIAlertAction actionWithTitle:@"微信" style:UIAlertActionStyleDefault handler:^(UIAlertAction * _Nonnull action)
                            {
                                ChZAddWechatAccountController *vc = [ChZAddWechatAccountController initWithStoryboard:@"ReceiptAccount"];
                                ChZ_Weak
                                vc.callBackBlock = ^(id Obj) {
                                    ChZ_Strong
                                    [_strongSelf chz_beginRefresh];
                                };
                                [self.navigationController pushViewController:vc animated:YES];
                            }]];
    }
    if (self.model.bank == nil)
    {
        [alertVC addAction:[UIAlertAction actionWithTitle:@"银行卡" style:UIAlertActionStyleDefault handler:^(UIAlertAction * _Nonnull action)
                            {
                                ChZAddBankAccountController *vc = [ChZAddBankAccountController initWithStoryboard:@"ReceiptAccount"];
                                ChZ_Weak
                                vc.callBackBlock = ^(id Obj) {
                                    ChZ_Strong
                                    [_strongSelf chz_beginRefresh];
                                };
                                [self.navigationController pushViewController:vc animated:YES];
                                
                            }]];
    }

    if ( self.model != nil && self.model.bank != nil && self.model.alipay != nil && self.model.weixin != nil)
    {
        ChZ_MBError(@"你已添加完毕，请删除后在添加");
        return;
    }

    [alertVC addAction:[UIAlertAction actionWithTitle:@"取消" style:UIAlertActionStyleCancel handler:^(UIAlertAction * _Nonnull action)
    {
        
    }]];
    [self presentViewController:alertVC animated:YES completion:nil];
}

- (void)_setupView
{
    [self chz_setupRefresh:self.tableView];
    self.tableView.dataSource = self;
    self.tableView.delegate = self;
}

- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView
{
    return 3;
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
    if (section == 0)
    {
        if (self.model.alipay)return 1;
        return 0;
    }
    if (section == 1)
    {
        if (self.model.weixin)return 1;
        return 0;
    }
    if (section == 2)
    {
        if (self.model.bank)return 1;
        return 0;
    }
    return 0;
}
- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    
    if (indexPath.section == 0) {
        ChZReceiptAccountListCell *cell = [tableView dequeueReusableCellWithIdentifier:@"qrCell"];
        cell.index = indexPath.section;
        cell.alipayModel = self.model.alipay;
        ChZ_Weak
        cell.block = ^(NSInteger index, id obj, int type)
        {
            ChZ_Strong
            [_strongSelf _handleCell:index type:type];
        };
        return cell;
    }
    if (indexPath.section == 1) {
        ChZReceiptAccountListCell *cell = [tableView dequeueReusableCellWithIdentifier:@"qrCell"];
        cell.index = indexPath.section;
        cell.wechatModel = self.model.weixin;
        ChZ_Weak
        cell.block = ^(NSInteger index, id obj, int type)
        {
            ChZ_Strong
            [_strongSelf _handleCell:index type:type];
        };
        return cell;
    }
    if (indexPath.section == 2) {
        ChZReceiptAccountListCell *cell = [tableView dequeueReusableCellWithIdentifier:@"bankCell"];
        cell.index = indexPath.section;
        cell.bankModel = self.model.bank;
        ChZ_Weak
        cell.block = ^(NSInteger index, id obj, int type)
        {
            ChZ_Strong
            [_strongSelf _handleCell:index type:type];
        };
        return cell;
    }
    return UITableViewCell.new;
    
}

- (void)_handleCell:(NSInteger)index type:(int)type
{
    if (type == 1)
    {
        if (index == 0) {
            if (ChZ_StringIsEmpty(self.model.alipay.qrcode))
            {
                [ChZPopQRCodeView showToView:self.navigationController.view qrcodeurl:self.model.alipay.qrcode];
            }
        }
        if (index == 1)
        {
            if (ChZ_StringIsEmpty(self.model.weixin.qrcode))
            {
                [ChZPopQRCodeView showToView:self.navigationController.view qrcodeurl:self.model.weixin.qrcode];
            }
        }
    }
    if (type == 2)
    {
        ChZ_Weak
        [self showTheAlertVCWithStyle:UIAlertControllerStyleAlert title:@"提示" message:@"是否删除？" title1:@"取消" action1:^{
            
        } title2:@"删除" action2:^{
            ChZ_Strong
            [_strongSelf handleRemove:index];
        } title3:nil action3:nil completion:nil];
    }
}

- (void)handleRemove:(NSInteger)index
{
    if (index == 0) {
        ChZ_Weak
        ChZ_MBMsg(nil)
        [ChZAccountRequest requestRemoveAlipayPayAccount:self.model.alipay.fid successBlock:^(id dataSource)
        {
            ChZ_Strong
            ChZ_MBDismssSuccess(@"删除成功");
            [_strongSelf chz_beginRefresh];
            
        } errorBlock:^(int code, NSString *error)
        {
            ChZ_MBDismssError(error)
        }];
        return;
    }
    if (index == 1) {
        ChZ_Weak
        ChZ_MBMsg(nil)
        [ChZAccountRequest requestRemoveWechatPayAccount:self.model.weixin.fid successBlock:^(id dataSource)
         {
             ChZ_Strong
             ChZ_MBDismssSuccess(@"删除成功");
             [_strongSelf chz_beginRefresh];
             
         } errorBlock:^(int code, NSString *error)
         {
             ChZ_MBDismssError(error)
         }];
        return;
    }
    if (index == 2) {
        ChZ_Weak
        ChZ_MBMsg(nil)
        [ChZAccountRequest requestRemoveBankPayAccount:self.model.bank.fid successBlock:^(id dataSource)
         {
             ChZ_Strong
             ChZ_MBDismssSuccess(@"删除成功");
             [_strongSelf chz_beginRefresh];
             
         } errorBlock:^(int code, NSString *error)
         {
             ChZ_MBDismssError(error)
         }];
        return;
    }
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
