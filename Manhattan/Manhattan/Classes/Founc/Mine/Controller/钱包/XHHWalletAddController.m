//
//  XHHWalletAddController.m
//  Manhattan
//
//  Created by Apple on 2018/8/16.
//  Copyright © 2018年 Apple. All rights reserved.
//

#import "XHHWalletAddController.h"

#import "XHHPublicNavView.h"

#import "XHHWalletAddCell.h"

#import "XHHWalletHideCoinList.h"
static NSString *cellIndifier = @"XHHWalletAddCell";

@interface XHHWalletAddController ()<UITableViewDelegate,UITableViewDataSource>

@property (nonatomic , strong) XHHPublicNavView           *navView;

@property (nonatomic , strong) UITableView                *tableView;

@property (nonatomic , strong) NSArray                     *dataArray;

@end

@implementation XHHWalletAddController

- (void)viewDidLoad {
    [super viewDidLoad];
    
    [self zh_setupUI];
    
    [self requestHideCoinList];
    // Do any additional setup after loading the view.
}
-(void)zh_setupUI{
    CGFloat navHeight = ChZ_IsiPhoneX ? 24 : 0;
    
    [self.view addSubview:self.navView];
    [self.navView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.top.right.equalTo(self.view);
        make.height.mas_equalTo(navHeight +64);
    }];
    
    [self.view addSubview:self.tableView];
    [self.tableView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.right.equalTo(self.view);
        make.top.equalTo(self.navView.mas_bottom);
        make.bottom.equalTo(self.view).offset(-navHeight);
    }];
}
-(XHHPublicNavView *)navView{
    if (!_navView) {
        _navView = [[[NSBundle mainBundle] loadNibNamed:@"XHHPublicNavView" owner:nil options:nil] lastObject];
        [_navView setLeftButtonTitle:@"添加币种" image:@"public_nav_leftBack"];
    }
    return _navView;
}
-(UITableView *)tableView{
    if (!_tableView) {
        _tableView = [[UITableView alloc] init];
        _tableView.delegate = self;
        _tableView.dataSource = self;
        _tableView.rowHeight = 96 ;
        [_tableView setSeparatorColor:LineCorlor];
        [_tableView setBackgroundColor:[UIColor clearColor]];
        [_tableView registerNib:[UINib nibWithNibName:@"XHHWalletAddCell" bundle:nil] forCellReuseIdentifier:cellIndifier];
    }
    return _tableView;
}
#pragma tableView delegate
-(NSInteger)numberOfSectionsInTableView:(UITableView *)tableView{
    return 1;
}
-(NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section{
    return self.walletCoinList.count;
}
-(UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath{
    XHHWalletAddCell *cell = [tableView dequeueReusableCellWithIdentifier:cellIndifier];
    cell.selectionStyle = UITableViewCellSelectionStyleNone;
    if(self.walletCoinList.count > indexPath.row)
    {
        cell.hideListArray = self.dataArray;
        cell.hideModel = self.walletCoinList[indexPath.row];
        @weakify(self);
        cell.block = ^(NSInteger index, WalletListModel *model, int type)
        {
            @strongify(self);
            [self requestHideCoinName:model.coinName];
        };
    }
    return cell;
}
-(void)requestHideCoinList{
    @weakify(self);
    [WalletNetWorkControl requestHideCoinListsuccessBlock:^(id dataSource)
    {
        @strongify(self);
        
        
        self.dataArray = dataSource;
        [self.tableView reloadData];

    } errorBlock:^(int code, NSString *error)
     {
        ChZ_DebugLog(@"%@--",error);
    }];
}
-(void)requestHideCoinName:(NSString *)coinName{
    
    
    ChZ_MBMsg(nil);
    @weakify(self);
    [WalletNetWorkControl requestHideCoinWithName:coinName
                                     successBlock:^(id dataSource)
    {
        @strongify(self);
        ChZ_MBDismss;
        [self requestHideCoinList];
        if(self.callBackBlock)
        {
            self.callBackBlock(nil);
        }
    } errorBlock:^(int code, NSString *error) {
        ChZ_MBDismss;
        ChZ_MBSuccess(error)
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
