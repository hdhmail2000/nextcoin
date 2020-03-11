//
//  HelpCenterController.m
//  Manhattan
//
//  Created by Apple on 2018/8/16.
//  Copyright © 2018年 Apple. All rights reserved.
//

#import "HelpCenterController.h"

#import "XHHTranHelpTiltleCell.h"

#import "XHHMineHelpTitleView.h"

static  NSString *cellindentifier = @"XHHTranHelpTiltleCell";

@interface HelpCenterController ()<UITableViewDelegate,UITableViewDataSource>

@property (strong, nonatomic)  UITableView *tableView;

@property (assign, nonatomic) NSInteger selectedRow;

@property (strong, nonatomic) NSArray   *dataArray;
@property (weak, nonatomic) IBOutlet UIView *contentView;
//@property (weak, nonatomic) IBOutlet UIView *titleView;

@property (nonatomic , strong) UIView *tView;

@property (nonatomic , strong) XHHMineHelpTitleView *titLeView;

@end

@implementation HelpCenterController
-(void)viewDidAppear:(BOOL)animated{
    
}
- (void)viewDidLoad {
    [super viewDidLoad];
    
    [self zh_setupUI];
    // Do any additional setup after loading the view.
}
- (IBAction)back:(id)sender {
    [self.navigationController popViewControllerAnimated:YES];
}
-(void)zh_setupUI{
    self.selectedRow = 1000;
    
    CGFloat navHeight = ChZ_IsiPhoneX ? 24 : 0;
    CGFloat bottomHeight = ChZ_IsiPhoneX ? 34 : 0;
    [self.view addSubview:self.tableView];
    [self.tableView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.edges.equalTo(self.view).insets(UIEdgeInsetsMake(64 + navHeight, 0, bottomHeight, 0));
    }];
    [self.tableView setTableHeaderView:self.titLeView];
}
-(void)viewDidLayoutSubviews{
    self.titLeView.frame = CGRectMake(0, 0, ChZ_WIDTH, 100);
    [self.tableView setTableHeaderView:self.titLeView];
}
-(UITableView *)tableView{
    if (!_tableView) {
        _tableView = [[UITableView alloc] init];
        _tableView.delegate = self;
        _tableView.dataSource = self;
        [_tableView setSeparatorColor:[UIColor colorWithHexString:@"4B4B80"]];
        _tableView.backgroundColor = [UIColor clearColor];
        [_tableView registerNib:[UINib nibWithNibName:@"XHHTranHelpTiltleCell" bundle:nil] forCellReuseIdentifier:cellindentifier];
    }
    return _tableView;
}
-(XHHMineHelpTitleView *)titLeView{
    if (!_titLeView) {
        _titLeView = [[[NSBundle mainBundle] loadNibNamed:@"XHHMineHelpTitleView" owner:nil options:nil] lastObject];
        _titLeView.titLable.text = @"帮助中心";
    }
    return _titLeView;
}

-(NSArray *)dataArray{
    if (!_dataArray) {
        _dataArray = @[@{@"title":@"为什么被禁言/封号？",@"details":@[@"1.违反飞视公约,将被禁言或封号",@"2、也有可能被盗号，请通过“留言反馈”说明情况，并留下账号绑定的【邮箱】或【手机号】，以便查询。",@"2、也有可能被盗号，请通过“留言反馈”说明情况，并留下账号绑定的【邮箱】或【手机号】，以便查询。",@"2、也有可能被盗号，请通过“留言反馈”说明情况，并留下账号绑定的【邮箱】或【手机号】，以便查询。",@"2、也有可能被盗号，请通过“留言反馈”说明情况，并留下账号绑定的【邮箱】或【手机号】，以便查询。",@"2、也有可能被盗号，请通过“留言反馈”说明情况，并留下账号绑定的【邮箱】或【手机号】，以便查询。",@"2、也有可能被盗号，请通过“留言反馈”说明情况，并留下账号绑定的【邮箱】或【手机号】，以便查询。",@"2、也有可能被盗号，请通过“留言反馈”说明情况，并留下账号绑定的【邮箱】或【手机号】，以便查询。",@"2、也有可能被盗号，请通过“留言反馈”说明情况，并留下账号绑定的【邮箱】或【手机号】，以便查询。",@"2、也有可能被盗号，请通过“留言反馈”说明情况，并留下账号绑定的【邮箱】或【手机号】，以便查询。",@"2、也有可能被盗号，请通过“留言反馈”说明情况，并留下账号绑定的【邮箱】或【手机号】，以便查询也有可能被盗号，请通过“留言反馈”说明情况，并留下账号绑定的【邮箱】或【手机号】，以便查询也有可能被盗号，请通过“留言反馈”说明情况，并留下账号绑定的【邮箱】或【手机号】，以便查询也有可能被盗号，请通过“留言反馈”说明情况，并留下账号绑定的【邮箱】或【手机号】，以便查询也有可能被盗号，请通过“留言反馈”说明情况，并留下账号绑定的【邮箱】或【手机号】，以便查询也有可能被盗号，请通过“留言反馈”说明情况，并留下账号绑定的【邮箱】或【手机号】，以便查询也有可能被盗号，请通过“留言反馈”说明情况，并留下账号绑定的【邮箱】或【手机号】，以便查询也有可能被盗号，请通过“留言反馈”说明情况，并留下账号绑定的【邮箱】或【手机号】，以便查询也有可能被盗号，请通过“留言反馈”说明情况，并留下账号绑定的【邮箱】或【手机号】，以便查询也有可能被盗号，请通过“留言反馈”说明情况，并留下账号绑定的【邮箱】或【手机号】，以便查询也有可能被盗号，请通过“留言反馈”说明情况，并留下账号绑定的【邮箱】或【手机号】，以便查询。",]},
                       @{@"title":@"回答被删除/回答不存在",@"details":@[@"1.违反飞视公约,将被禁言或封号",@"2、也有可能被盗号，请通过“留言反馈”说明情况，并留下账号绑定的【邮箱】或【手机号】，以便查询。"]},
                       @{@"title":@"问题能删除吗？",@"details":@[@"1.违反飞视公约,将被禁言或封号",@"2、也有可能被盗号，请通过“留言反馈”说明情况，并留下账号绑定的【邮箱】或【手机号】，以便查询。"]},
                       @{@"title":@"原有手机失效，想重新绑定现有手机号。",@"details":@[@"1.违反飞视公约,将被禁言或封号",@"2、也有可能被盗号，请通过“留言反馈”说明情况，并留下账号绑定的【邮箱】或【手机号】，以便查询。"]},
                       @{@"title":@"如何绑定手机？",@"details":@[@"1.违反飞视公约,将被禁言或封号",@"2、也有可能被盗号，请通过“留言反馈”说明情况，并留下账号绑定的【邮箱】或【手机号】，以便查询。"]},
                       @{@"title":@"有多个账号，想合并或者注销。",@"details":@[@"1.违反飞视公约,将被禁言或封号",@"2、也有可能被盗号，请通过“留言反馈”说明情况，并留下账号绑定的【邮箱】或【手机号】，以便查询。"]},
                       @{@"title":@"为什么被禁言/封号？",@"details":@[@"1.违反飞视公约,将被禁言或封号",@"2、也有可能被盗号，请通过“留言反馈”说明情况，并留下账号绑定的【邮箱】或【手机号】，以便查询。",@"2、也有可能被盗号，请通过“留言反馈”说明情况，并留下账号绑定的【邮箱】或【手机号】，以便查询。",@"2、也有可能被盗号，请通过“留言反馈”说明情况，并留下账号绑定的【邮箱】或【手机号】，以便查询。",@"2、也有可能被盗号，请通过“留言反馈”说明情况，并留下账号绑定的【邮箱】或【手机号】，以便查询。",@"2、也有可能被盗号，请通过“留言反馈”说明情况，并留下账号绑定的【邮箱】或【手机号】，以便查询。",@"2、也有可能被盗号，请通过“留言反馈”说明情况，并留下账号绑定的【邮箱】或【手机号】，以便查询。",@"2、也有可能被盗号，请通过“留言反馈”说明情况，并留下账号绑定的【邮箱】或【手机号】，以便查询。",@"2、也有可能被盗号，请通过“留言反馈”说明情况，并留下账号绑定的【邮箱】或【手机号】，以便查询。",@"2、也有可能被盗号，请通过“留言反馈”说明情况，并留下账号绑定的【邮箱】或【手机号】，以便查询。",@"2、也有可能被盗号，请通过“留言反馈”说明情况，并留下账号绑定的【邮箱】或【手机号】，以便查询也有可能被盗号，请通过“留言反馈”说明情况，并留下账号绑定的【邮箱】或【手机号】，以便查询也有可能被盗号，请通过“留言反馈”说明情况，并留下账号绑定的【邮箱】或【手机号】，以便查询也有可能被盗号，请通过“留言反馈”说明情况，并留下账号绑定的【邮箱】或【手机号】，以便查询也有可能被盗号，请通过“留言反馈”说明情况，并留下账号绑定的【邮箱】或【手机号】，以便查询也有可能被盗号，请通过“留言反馈”说明情况，并留下账号绑定的【邮箱】或【手机号】，以便查询也有可能被盗号，请通过“留言反馈”说明情况，并留下账号绑定的【邮箱】或【手机号】，以便查询也有可能被盗号，请通过“留言反馈”说明情况，并留下账号绑定的【邮箱】或【手机号】，以便查询也有可能被盗号，请通过“留言反馈”说明情况，并留下账号绑定的【邮箱】或【手机号】，以便查询也有可能被盗号，请通过“留言反馈”说明情况，并留下账号绑定的【邮箱】或【手机号】，以便查询也有可能被盗号，请通过“留言反馈”说明情况，并留下账号绑定的【邮箱】或【手机号】，以便查询。",]},];
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
    XHHTranHelpTiltleCell *cell = [tableView dequeueReusableCellWithIdentifier:cellindentifier];
    [cell reladCellWithDic:self.dataArray[indexPath.row]];
    if (indexPath.row == self.selectedRow) {
        [cell setImageUpWithDetials:self.dataArray[indexPath.row][@"details"]];
    }
    return cell;
}
-(void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath{
    if (indexPath.row == self.selectedRow) {
        self.selectedRow = 1000;
    }else{
        self.selectedRow = indexPath.row;
    }
    
    [self.tableView reloadData];
}
-(CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath{
    CGFloat tableHeiglt = 0;
    if (indexPath.row == self.selectedRow) {
        NSArray *details = self.dataArray[indexPath.row][@"details"];
        for(int i = 0; i < details.count;i++){
            tableHeiglt = tableHeiglt + [ChZUtil setDetailHH:details[i] width:ChZ_WIDTH - 32 font:14.0] + 10;
        }
        tableHeiglt = tableHeiglt;
    }
    CGFloat cellHeight = [ChZUtil setDetailHH:self.dataArray[indexPath.row][@"title"] width:ChZ_WIDTH - 56 font:18.0];
    return 40 + tableHeiglt + cellHeight;
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