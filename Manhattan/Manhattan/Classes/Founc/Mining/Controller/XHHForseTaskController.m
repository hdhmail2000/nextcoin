//
//  XHHForseTaskController.m
//  Manhattan
//
//  Created by Apple on 2018/8/17.
//  Copyright © 2018年 Apple. All rights reserved.
//

#import "XHHForseTaskController.h"
#import "XHHPublicNavView.h"
#import "XHHForseTaskCell.h"
#import "XHHForseWorkDetailModel.h"
#import "XHHForseWorkModel.h"
#import "XHHFroseWorkListModel.h"
static NSString *cellIndifier = @"XHHForseTaskCell";
@interface XHHForseTaskController ()<UITableViewDelegate,UITableViewDataSource>
@property (strong, nonatomic) IBOutlet UIView *headView;

@property (weak, nonatomic) IBOutlet UILabel *currFroseNumber;

@property (weak, nonatomic) IBOutlet UILabel *leave1FrendLable;
@property (weak, nonatomic) IBOutlet UILabel *leave2FrendLable;
@property (weak, nonatomic) IBOutlet UILabel *leave1GetFroseNumber;
@property (weak, nonatomic) IBOutlet UILabel *leave2GetFroseNumber;


@property (nonatomic , strong) XHHPublicNavView           *navView;
@property (nonatomic , strong) UITableView *tableView;
@property (nonatomic , strong) XHHForseWorkModel *workModel;
@property (nonatomic , strong) NSMutableArray *dataArray;

@end

@implementation XHHForseTaskController
-(void)viewDidLayoutSubviews{
    self.headView.frame = CGRectMake(0, 0, ChZ_WIDTH, 400);
}
- (void)viewDidLoad {
    [super viewDidLoad];
    
    [self zh_setupUI];
    
    [self.tableView.mj_header beginRefreshing];
}

-(void)zh_setupUI{
    CGFloat navHeight = ChZ_IsiPhoneX ? 24 : 0;
    CGFloat bottomHeight = ChZ_IsiPhoneX ? 34 : 0;
    [self.view addSubview:self.navView];
    [self.navView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.top.right.equalTo(self.view);
        make.height.mas_equalTo(navHeight +64);
    }];
    
    [self.view addSubview:self.tableView];
    [self.tableView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.right.equalTo(self.view);
        make.top.equalTo(self.navView.mas_bottom);
        make.bottom.equalTo(self.view).offset(-bottomHeight);
    }];
}
-(XHHPublicNavView *)navView
{
    if (!_navView)
    {
        _navView = [[[NSBundle mainBundle] loadNibNamed:@"XHHPublicNavView" owner:nil options:nil] lastObject];
        [_navView setLeftButtonTitle:@"算力任务" image:@"public_nav_leftBack"];
    }
    return _navView;
}
-(UITableView *)tableView{
    if (!_tableView) {
        _tableView = [[UITableView alloc] init];
        _tableView.delegate = self;
        _tableView.dataSource = self;
        _tableView.rowHeight = 70 ;
        _tableView.tableFooterView = [[UIView alloc] initWithFrame:CGRectZero];
        [_tableView setSeparatorColor:LineCorlor];
        [_tableView setBackgroundColor:[UIColor clearColor]];
        [_tableView setTableHeaderView:self.headView];
        [_tableView registerNib:[UINib nibWithNibName:@"XHHForseTaskCell" bundle:nil] forCellReuseIdentifier:cellIndifier];
        [self setupRefreshWithView:_tableView refreshHeadAction:@selector(headRefresh) refreshFooterAction:@selector(footRfresh)];
    }
    return _tableView;
}
-(void)setWorkModel:(XHHForseWorkModel *)workModel{
    _workModel = workModel;
    
    if (ChZ_StringIsEmpty(workModel.fability)) self.currFroseNumber.text = workModel.fability;
    NSArray *array = workModel.invite;
    if (array.count == 2)
    {
        XHHForseWorkDetailModel *leave1Model = array[0];
        if (ChZ_StringIsEmpty(leave1Model.num)) self.leave1FrendLable.text = leave1Model.num;
        if (ChZ_StringIsEmpty(leave1Model.total)) self.leave1GetFroseNumber.text = leave1Model.total;
        
        XHHForseWorkDetailModel *leave2Model = array[1];
        if (ChZ_StringIsEmpty(leave2Model.num)) self.leave2FrendLable.text = leave2Model.num;
        if (ChZ_StringIsEmpty(leave2Model.total)) self.leave2GetFroseNumber.text = leave2Model.total;
    }
}
-(NSMutableArray *)dataArray{
    if (!_dataArray) {
        _dataArray = [NSMutableArray array];
    }
    return _dataArray;
}
#pragma tableView delegate
-(NSInteger)numberOfSectionsInTableView:(UITableView *)tableView{
    return 1;
}
-(NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section{
    return self.dataArray.count;
}
-(UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath{
    XHHForseTaskCell *cell = [tableView dequeueReusableCellWithIdentifier:cellIndifier];
    if (self.dataArray.count > indexPath.row)
    {
        XHHFroseWorkListModel *model = self.dataArray[indexPath.row];
        cell.listModel = model;
    }
    return cell;
}
-(void)headRefresh{
    [self requestForseWorkList:YES];
}
-(void)footRfresh{
    [self requestForseWorkList:NO];
}
-(void)requestForseWorkList:(BOOL)refresh{
    NSInteger page = 0;
    if (refresh == NO) {
        page = (NSInteger)ceil(self.dataArray.count/10) +1;
    }
    @weakify(self);
    [XHHMiningRequest requestForseWorkListPage:page success:^(id dataSource)
    {
        @strongify(self);
        if (refresh) [self.dataArray removeAllObjects];
        if (dataSource) [self.dataArray addObjectsFromArray:dataSource];
        [self.tableView reloadData];
        [self.tableView.mj_header endRefreshing];
        [self.tableView.mj_footer endRefreshing];
    } error:^(int code, NSString *error) {
        @strongify(self);
        ChZ_DebugLog(@"%@",error);
        [self.tableView.mj_header endRefreshing];
        [self.tableView.mj_footer endRefreshing];
    }];
    
    [XHHMiningRequest requestForseTaskSuccess:^(id dataSource)
     {
         @strongify(self);
         self.workModel = dataSource;
     } error:^(int code, NSString *error) {
         ChZ_DebugLog(@"%@",error);
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
