//
//  XHHChoosePayTypeController.m
//  FuturePurse
//
//  Created by Apple on 2018/7/23.
//  Copyright © 2018年 jbtm. All rights reserved.
//

#import "XHHChoosePayTypeController.h"

#import "XHHPTChooseCell.h"

#import "XHHPayTypeModel.h"

#import "XHHPublicNavView.h"

static NSString *cellIdentifier = @"XHHPTChooseCell";
@interface XHHChoosePayTypeController ()<UITableViewDelegate,UITableViewDataSource,XHHPublicNavViewProtocol>

@property (strong, nonatomic) UITableView *tableView;

@property (strong, nonatomic) NSArray     *dataArray;

@property (assign, nonatomic) NSInteger   currIndex;

@property (nonatomic , strong) XHHPublicNavView           *navView;

@property (strong, nonatomic) UILabel   *titleLable;



@end

@implementation XHHChoosePayTypeController


- (void)viewDidLoad {
    [super viewDidLoad];
    
    _currIndex = 0;
    [self zh_setupUI];
    // Do any additional setup after loading the view.
}
-(void)zh_setupUI{
    
    CGFloat navHeight = ChZ_IsiPhoneX ? 24 : 0;
    CGFloat bottomHeight = ChZ_IsiPhoneX ? 34 : 0;
    [self.view addSubview:self.navView];
    [self.navView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.top.right.equalTo(self.view);
        make.height.mas_equalTo(navHeight +64);
    }];
    [self.view addSubview:self.titleLable];
    [self.titleLable mas_makeConstraints:^(MASConstraintMaker *make) {
        make.top.equalTo(self.navView.mas_bottom).offset(30);
        make.left.equalTo(self.view).offset(16);
    }];
    
    [self.view addSubview:self.tableView];
    [self.tableView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.right.equalTo(self.view);
        make.top.equalTo(self.titleLable.mas_bottom).offset(30);
        make.bottom.equalTo(self.view).offset(-bottomHeight);
    }];
    
}
-(XHHPublicNavView *)navView{
    if (!_navView) {
        _navView = [[[NSBundle mainBundle] loadNibNamed:@"XHHPublicNavView" owner:nil options:nil] lastObject];
        [_navView setLeftButtonTitle:@"选择币" image:@"public_nav_leftBack"];
        [_navView setrightButtonTitles:@[@"确认"] images:nil];
        _navView.delegete = self;
    }
    return _navView;
}
-(UITableView *)tableView{
    if (!_tableView) {
        _tableView = [[UITableView alloc] init];
        _tableView.delegate = self;
        _tableView.dataSource = self;
        _tableView.rowHeight = 57;
        [_tableView setSeparatorColor:LineCorlor];
        _tableView.backgroundColor = [UIColor clearColor];
        _tableView.tableFooterView = [[UIView alloc] initWithFrame:CGRectZero];
        [_tableView registerNib:[UINib nibWithNibName:@"XHHPTChooseCell" bundle:nil] forCellReuseIdentifier:cellIdentifier];
    }
    return _tableView;
}
-(NSMutableArray *)allPayTypes{
    if (!_allPayTypes) {
        _allPayTypes = [NSMutableArray array];
    }
    return _allPayTypes;
}
-(UILabel *)titleLable{
    if (!_titleLable) {
        _titleLable = [UILabel newSingleFrame:CGRectZero title:@"收款方式" fontS:24.0 color:[UIColor colorWithHexString:@"2E7AF1"]];
    }
    return _titleLable;
}
-(void)rightButtonActionIndex:(NSInteger)index{
    if (self.payType) {
        if (self.allPayTypes.count == 0)
        {
            ChZ_MBError(@"请选择至少一种支付方式");
            return;
        }
        _payType(self.allPayTypes);
        [self.navigationController popViewControllerAnimated:YES];
    }
}
-(NSArray *)dataArray{
    if (!_dataArray){
        _dataArray = @[@{@"image":@"trad_mydeal_zhifubaopay",@"name":@"支付宝"},
                       @{@"image":@"trad_mydeal_weixinpay",@"name":@"微信"},
                       @{@"image":@"trad_mydeal_qianbaopay",@"name":@"银行卡"},];
    }
    return _dataArray;
}
#pragma tableview delegate
-(NSInteger)numberOfSectionsInTableView:(UITableView *)tableView{
    return 1;
}
-(NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section{
    return self.dataArray.count;
}
-(UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath{
    XHHPTChooseCell *cell = [tableView dequeueReusableCellWithIdentifier:cellIdentifier];
    cell.selectionStyle = UITableViewCellSelectionStyleNone;
    NSDictionary *dic = self.dataArray[indexPath.row];
    cell.cellDic = dic;
    if ([self.allPayTypes containsObject:dic[@"name"]])
    {
        [cell setSelectedCell:YES];
    }else{
        [cell setSelectedCell:NO];
    }
    return cell;
}
-(void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath{
    _currIndex = indexPath.row;
    NSDictionary *dic = self.dataArray[indexPath.row];
    if ([self.allPayTypes containsObject:dic[@"name"]])
    {
        [self.allPayTypes removeObject:dic[@"name"]];
    }else{
        [self.allPayTypes addObject:dic[@"name"]];
    }
    [self.tableView reloadData];
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
