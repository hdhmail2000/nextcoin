//
//  XHHMyDealController.m
//  FuturePurse
//
//  Created by Apple on 2018/7/27.
//  Copyright © 2018年 jbtm. All rights reserved.
//

#import "XHHMyDealController.h"

#import "XHHTadTypeChooesView.h"

#import "XHHPublicItemsView.h"

#import "XHHMyDealCell.h"

#import "XHHSellBuyController.h"

#import "XHHPublicNavView.h"

#import "XHHSellBuyController.h"

#import "XHHKLineController.h"

#import "XHHCurrencyView.h"

static NSString  *cellInditifier = @"XHHMyDealCell";
@interface XHHMyDealController ()<XHHPublicItemsViewProtocol,UITableViewDelegate,UITableViewDataSource,XHHPublicNavViewProtocol>

@property (strong, nonatomic) XHHTadTypeChooesView        *typeChooseView;

@property (strong, nonatomic) XHHPublicItemsView          *itemsView;

@property (nonatomic , strong) XHHPublicNavView           *navView;

@property (nonatomic , strong) XHHCurrencyView            *currencyView;

@property (strong, nonatomic) UITableView                 *tableView;

@property (strong, nonatomic) NSArray                     *dataArray;

@property (assign, nonatomic) NSInteger                   currIndex;

@end

@implementation XHHMyDealController

- (void)viewDidLoad {
    [super viewDidLoad];
    
    [self zh_setupUI];
}
-(void)zh_setupUI{
    self.currIndex = 0;
    
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
    
    [self.view addSubview:self.tableView];
    [self.tableView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.right.equalTo(self.view);
        make.top.equalTo(self.itemsView.mas_bottom);
        make.bottom.equalTo(self.view).offset(-navHeight);
    }];
}

-(XHHPublicNavView *)navView{
    if (!_navView) {
        _navView = [[[NSBundle mainBundle] loadNibNamed:@"XHHPublicNavView" owner:nil options:nil] lastObject];
        _navView.delegete = self;
        [_navView setLeftButtonTitle:@"BTC" image:@"public_nav_leftBack"];
        [_navView setrightButtonTitles:nil images:@[@"trad_buysell_rightnavone",@"trad_buysell_rightnavtwo"]];
        _navView.rightOneWithd.constant = 44;
        _navView.rightTwoWithd.constant = 44;
    }
    return _navView;
}
-(XHHPublicItemsView *)itemsView{
    if (!_itemsView) {
        _itemsView = [[XHHPublicItemsView alloc] init];
        [_itemsView zh_setupUIWithItems:@[@"我要买入",@"我要卖出"]];
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
-(XHHCurrencyView *)currencyView{
    if (!_currencyView) {
        _currencyView = [[[NSBundle mainBundle] loadNibNamed:@"XHHCurrencyView" owner:nil options:nil] lastObject];
    }
    return _currencyView;
}
-(UITableView *)tableView{
    if (!_tableView) {
        _tableView = [[UITableView alloc] init];
        _tableView.delegate = self;
        _tableView.dataSource = self;
        _tableView.rowHeight = 170;
        _tableView.backgroundColor = bgColor;
        [_tableView setSeparatorColor:[UIColor clearColor]];
        [_tableView registerNib:[UINib nibWithNibName:@"XHHMyDealCell" bundle:nil] forCellReuseIdentifier:cellInditifier];
    }
    return _tableView;
}
-(NSArray *)dataArray{
    if (!_dataArray) {
        _dataArray = @[@"",@"",@"",@""];
    }
    return _dataArray;
}
-(void)itemsClickIndex:(NSInteger)index{
  
    self.currIndex = index;
    
    [self.tableView reloadData];
}
-(NSInteger)numberOfSectionsInTableView:(UITableView *)tableView{
    return 1;
}
-(NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section{
    return self.dataArray.count;
}
-(UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath{
    XHHMyDealCell *cell = [tableView dequeueReusableCellWithIdentifier:cellInditifier];
    cell.selectionStyle = UITableViewCellSelectionStyleNone;
    if (self.currIndex == 0) {
        [cell reloadCellWithDic:@{@"type":@"0"}];
    }else{
        [cell reloadCellWithDic:@{@"type":@"1"}];
    }
    @weakify(self);
    cell.buySellBlock = ^(NSInteger type) {
        @strongify(self);
        XHHSellBuyController *vc = [[XHHSellBuyController alloc] init];
        vc.type = type;
        [self.navigationController pushViewController:vc animated:YES];
    };
    
    return cell;
}
-(void)rightButtonActionIndex:(NSInteger)index{
    if (index == 1) {
        XHHKLineController *vc = [[XHHKLineController alloc] init];
        [self.navigationController pushViewController:vc animated:YES];
    }else{
        [self.currencyView showInView:self.view.window];
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
