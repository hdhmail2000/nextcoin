//
//  XHHSwithPayListViewController.m
//  FuturePurse
//
//  Created by Apple on 2018/8/29.
//  Copyright © 2018年 jbtm. All rights reserved.
//

#import "XHHSwithPayListViewController.h"
#import "XHHEntrustCell.h"
static NSString *cellIndienfiter = @"XHHEntrustCell";
@interface XHHSwithPayListViewController ()<UITableViewDelegate,UITableViewDataSource>

@property (nonatomic , strong) UITableView *tableView;

@property (nonatomic , strong) NSArray     *dataArray;

@end

@implementation XHHSwithPayListViewController

-(void)viewWillAppear:(BOOL)animated{
    [super viewWillAppear:animated];
    [self.tableView.mj_header beginRefreshing];
}

- (void)viewDidLoad {
    [super viewDidLoad];
    
    [self zh_setupUI];
}
-(void)zh_setupUI
{
    [self.view addSubview:self.tableView];
    [self.tableView mas_makeConstraints:^(MASConstraintMaker *make)
    {
        make.edges.equalTo(self.view);
    }];
}


-(UITableView *)tableView{
    if (!_tableView) {
        _tableView = [[UITableView alloc] init];
        _tableView.delegate = self;
        _tableView.dataSource = self;
        _tableView.rowHeight = 123;
        [_tableView setSeparatorColor:LineCorlor];
        _tableView.tableFooterView = [[UIView alloc] initWithFrame:CGRectZero];
        _tableView.backgroundColor = bgColor;
        [_tableView registerNib:[UINib nibWithNibName:@"XHHEntrustCell" bundle:nil] forCellReuseIdentifier:cellIndienfiter];
        [self setupRefreshWithView:_tableView refreshHeadAction:@selector(headRefsh) refreshFooterAction:nil];
    }
    return _tableView;
}
-(NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
    return self.dataArray.count;
}
-(UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    XHHEntrustCell *cell = [tableView dequeueReusableCellWithIdentifier:cellIndienfiter];
    cell.selectionStyle = UITableViewCellSelectionStyleNone;
    ChZMarketTxsOrderItemModel *model = self.dataArray[indexPath.row];
    [cell IsDealList:YES];
    cell.nameLable.text = [NSString stringWithFormat:@"%@/%@",self.sellName,self.buyName];
    [cell reloadCellWithModel:model];
    return cell;
}
-(void)headRefsh
{
    [self _setupData];
}
- (void)_setupData
{
    NSString *areaAndCoint = [NSString stringWithFormat:@"%@_%@",self.sellName,self.buyName];
    NSString *name = [areaAndCoint lowercaseString];
    @weakify(self);
    [ChZHomeRequest requestTxsOrderListWithSymbol:name type:@"2" successBlock:^(id dataSource)
    {
        @strongify(self);
        self.dataArray = dataSource;
        [self.tableView reloadData];
        [self.tableView.mj_header endRefreshing];
    } errorBlock:^(int code, NSString *error)
    {
        @strongify(self);
        ChZ_MBError(error)
        [self.tableView.mj_header endRefreshing];
        ChZ_DebugLog(@"code = %d error reason: %@", code, error);
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
