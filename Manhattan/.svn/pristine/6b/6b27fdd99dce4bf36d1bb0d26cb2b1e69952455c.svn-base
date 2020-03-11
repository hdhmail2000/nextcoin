//
//  XHHTradMyOrderController.m
//  FuturePurse
//
//  Created by Apple on 2018/7/30.
//  Copyright © 2018年 jbtm. All rights reserved.
//

#import "XHHTradMyOrderController.h"
#import "XHHTradOrderBuyDetailController.h"
#import "XHHTradOrderSellDetailController.h"
#import "XHHTSBDetailCell.h"

#import "XHHPublicNavView.h"

static NSString *cellIndienfiter = @"XHHTSBDetailCell";
@interface XHHTradMyOrderController ()<UITableViewDelegate,UITableViewDataSource>

@property (strong, nonatomic) UITableView  *tableView;

@property (strong, nonatomic) NSArray      *dataArray;

@property (nonatomic , strong) XHHPublicNavView           *navView;

@end

@implementation XHHTradMyOrderController

- (void)viewDidLoad {
    [super viewDidLoad];
    
    [self zh_setupUI];
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
        make.left.bottom.right.equalTo(self.view);
        make.top.equalTo(self.navView.mas_bottom);
    }];
}

-(void)back{
    [self.navigationController popViewControllerAnimated:YES];
}
-(XHHPublicNavView *)navView{
    if (!_navView) {
        _navView = [[[NSBundle mainBundle] loadNibNamed:@"XHHPublicNavView" owner:nil options:nil] lastObject];
        [_navView setLeftButtonTitle:@"我的订单" image:@"public_nav_leftBack"];
    }
    return _navView;
}
-(UITableView *)tableView{
    if (!_tableView) {
        _tableView = [[UITableView alloc] init];
        _tableView.delegate = self;
        _tableView.dataSource = self;
        _tableView.rowHeight = 110;
        [_tableView setSeparatorColor:LineCorlor];
        _tableView.backgroundColor = bgColor;
        _tableView.showsVerticalScrollIndicator = NO;
        [_tableView setTableFooterView:[[UIView alloc] initWithFrame:CGRectZero]];
        [_tableView registerNib:[UINib nibWithNibName:@"XHHTSBDetailCell" bundle:nil] forCellReuseIdentifier:cellIndienfiter];
    }
    return _tableView;
}
-(NSArray *)dataArray{
    if (!_dataArray) {
        _dataArray = @[@{@"type":@"1"},@{@"type":@"2"},@{@"type":@"1"},@{@"type":@"2"},@{@"type":@"1"},];
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
    [cell reloadCellWithDic:self.dataArray[indexPath.row] payType:[self.dataArray[indexPath.row][@"type"] integerValue]];
    return cell;
}
-(void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath{
    NSInteger type = [self.dataArray[indexPath.row][@"type"] integerValue];
    if (type == 1) {
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
