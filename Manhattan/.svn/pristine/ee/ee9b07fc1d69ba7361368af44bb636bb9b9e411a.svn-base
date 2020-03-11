//
//  XHHTradSellBuyDetailController.m
//  FuturePurse
//
//  Created by Apple on 2018/7/30.
//  Copyright © 2018年 jbtm. All rights reserved.
//

#import "XHHTradSellBuyDetailController.h"
#import "XHHTradOrderBuyDetailController.h"
#import "XHHTradOrderSellDetailController.h"

#import "XHHTSBDetailHeadView.h"
#import "XHHTSBDetailCell.h"

#import "XHHPublicNavView.h"

static NSString *cellIndienfiter = @"XHHTSBDetailCell";


@interface XHHTradSellBuyDetailController ()<UITableViewDelegate,UITableViewDataSource>

@property (strong, nonatomic) UITableView  *tableView;

@property (strong, nonatomic) NSArray      *dataArray;

@property (strong, nonatomic) XHHTSBDetailHeadView  *headView;

@property (nonatomic , strong) XHHPublicNavView           *navView;

@end

@implementation XHHTradSellBuyDetailController

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view.
    
    [self zh_setupUI];
}
-(void)viewWillLayoutSubviews{
    self.headView.frame = CGRectMake(0, 0, ChZ_WIDTH, 148);
    [self.tableView setTableHeaderView:self.headView];
}
-(void)zh_setupUI{
    
    CGFloat navHeight = ChZ_IsiPhoneX ? 24 : 0;
    
    [self.view addSubview:self.navView];
    [self.navView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.top.right.equalTo(self.view);
        make.height.mas_equalTo(navHeight +64);
    }];
    
//    [self.view addSubview:self.headView];
//    [self.headView mas_makeConstraints:^(MASConstraintMaker *make) {
//        make.left.right.equalTo(self.view);
//        make.top.equalTo(self.navView.mas_bottom);
//        make.height.mas_equalTo(111);
//    }];
    
    [self.view addSubview:self.tableView];
    [self.tableView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.right.equalTo(self.view);
        make.top.equalTo(self.navView.mas_bottom);
        make.bottom.equalTo(self.view).offset(-navHeight);
    }];
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}
-(XHHTSBDetailHeadView *)headView{
    if (!_headView) {
        _headView = [[[NSBundle mainBundle] loadNibNamed:@"XHHTSBDetailHeadView" owner:nil options:nil] lastObject];
        [_headView reloadViewWithType:self.type];
    }
    return _headView;
}
-(XHHPublicNavView *)navView{
    if (!_navView) {
        _navView = [[[NSBundle mainBundle] loadNibNamed:@"XHHPublicNavView" owner:nil options:nil] lastObject];
        [_navView setLeftButtonTitle:@"购买ETH" image:@"public_nav_leftBack"];
    }
    return _navView;
}
-(UITableView *)tableView{
    if (!_tableView) {
        _tableView = [[UITableView alloc] init];
        _tableView.delegate = self;
        _tableView.dataSource = self;
        _tableView.rowHeight = 110;
        _tableView.showsVerticalScrollIndicator = NO;
        [_tableView setSeparatorColor:LineCorlor];
        _tableView.backgroundColor = [UIColor clearColor];
        [_tableView setTableFooterView:[[UIView alloc] initWithFrame:CGRectZero]];
        [_tableView registerNib:[UINib nibWithNibName:@"XHHTSBDetailCell" bundle:nil] forCellReuseIdentifier:cellIndienfiter];
    }
    return _tableView;
}
-(NSArray *)dataArray{
    if (!_dataArray) {
        _dataArray = @[@{@"type":@"1"},@{@"type":@"2"},@{@"type":@"3"},];
    }
    return _dataArray;
}
-(NSInteger)numberOfSectionsInTableView:(UITableView *)tableView{
    return 1;
}
-(NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section{
    return self.dataArray.count;
}
-(UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath{
    XHHTSBDetailCell *cell = [tableView dequeueReusableCellWithIdentifier:cellIndienfiter];
    cell.selectionStyle = UITableViewCellSelectionStyleNone;
    [cell reloadCellWithDic:self.dataArray[indexPath.row] payType:self.type];
    return cell;
}
-(void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath{
    if (self.type == 1) {
        XHHTradOrderBuyDetailController *vc = [[XHHTradOrderBuyDetailController alloc] init];
        [self.navigationController pushViewController:vc animated:YES];
    }else{
        XHHTradOrderSellDetailController *vc = [[XHHTradOrderSellDetailController alloc] init];
        NSLog(@"22222");
        [self.navigationController pushViewController:vc animated:YES];
        
    }
//    NSDictionary *dic = self.dataArray[indexPath.row];
//    NSInteger type = [[dic objectForKey:@"type"] integerValue];
//    XHHTradSellBuyDetailController *vc = [[XHHTradSellBuyDetailController alloc] init];
//    vc.type = type;
//    [[ChZUtil getViewController:self].navigationController pushViewController:vc animated:YES];
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
