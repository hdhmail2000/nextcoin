//
//  XHHAreaListController.m
//  Manhattan
//
//  Created by Apple on 2018/8/17.
//  Copyright © 2018年 Apple. All rights reserved.
//

#import "XHHAreaListController.h"
#import "XHHPublicNavView.h"
#import "XHHAreaListCell.h"
#import "XHHAddAreaController.h"
static NSString *cellindifier = @"XHHAreaListCell";
@interface XHHAreaListController ()<UITableViewDelegate,UITableViewDataSource>
@property (strong, nonatomic) IBOutlet UIView *bottomView;
@property (nonatomic , strong) XHHPublicNavView           *navView;
@property (nonatomic , strong) UITableView *tableView;
@property (nonatomic , strong) NSArray *dataArray;
@property (nonatomic , copy) NSString *imgURL;
@end

@implementation XHHAreaListController

- (void)viewDidLoad {
    [super viewDidLoad];
    
    [self zh_setupUI];
    
    [self requestCoinAddressList];
    // Do any additional setup after loading the view from its nib.
}

-(void)zh_setupUI{
    CGFloat navHeight = ChZ_IsiPhoneX ? 24 : 0;
    CGFloat bottomHeight = ChZ_IsiPhoneX ? 34 : 0;
    
    [self.view addSubview:self.navView];
    [self.navView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.top.right.equalTo(self.view);
        make.height.mas_equalTo(navHeight +64);
    }];
    
    [self.view addSubview:self.bottomView];
    [self.bottomView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.right.equalTo(self.view);
        make.bottom.equalTo(self.view).offset(-bottomHeight);
        make.height.mas_equalTo(78);
    }];
    
    [self.view addSubview:self.tableView];
    [self.tableView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.right.equalTo(self.view);
        make.top.equalTo(self.navView.mas_bottom);
        make.bottom.equalTo(self.bottomView.mas_top);
    }];
}

-(XHHPublicNavView *)navView{
    if (!_navView) {
        _navView = [[[NSBundle mainBundle] loadNibNamed:@"XHHPublicNavView" owner:nil options:nil] lastObject];
        [_navView setLeftButtonTitle:@"地址簿" image:@"public_nav_leftBack"];
    }
    return _navView;
}
-(UITableView *)tableView{
    if (!_tableView) {
        _tableView = [[UITableView alloc] init];
        _tableView.delegate = self;
        _tableView.dataSource = self;
        _tableView.rowHeight = 70;
        [_tableView setBackgroundColor:[UIColor clearColor]];
        [_tableView setSeparatorColor:LineCorlor];
        _tableView.tableFooterView = [[UIView alloc] initWithFrame:CGRectZero];
        [_tableView registerNib:[UINib nibWithNibName:@"XHHAreaListCell" bundle:nil] forCellReuseIdentifier:cellindifier];
    }
    return _tableView;
}

#pragma tableView delegate
-(NSInteger)numberOfSectionsInTableView:(UITableView *)tableView{
    return 1;
}
-(NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section{
    return self.dataArray.count;
}
-(UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath{
    XHHAreaListCell *cell = [tableView dequeueReusableCellWithIdentifier:cellindifier];
    cell.selectionStyle = UITableViewCellSelectionStyleNone;
    if (self.dataArray.count > indexPath.row)
    {
        AddressListModel *model = self.dataArray[indexPath.row];
        cell.title.text = model.fremark;
        cell.detail.text = model.fadderess;
        if (ChZ_StringIsEmpty(self.imgURL))
        {
            [cell.img sd_setImageWithURL:[NSURL URLWithString:self.imgURL] placeholderImage:[UIImage imageNamed:@""]];
        }
    }
    return cell;
}
-(void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath{
    if (self.dataArray.count > indexPath.row)
    {
        AddressListModel *model = self.dataArray[indexPath.row];
        if (self.callBackBlock)
        {
            self.callBackBlock(model);
            [self pop];
        }
    }
}
- (IBAction)addAreaAction:(id)sender {
    XHHAddAreaController *vc = [[XHHAddAreaController alloc] init];
    @weakify(self);
    vc.callBackBlock = ^(id Obj)
    {
        @strongify(self);
        [self requestCoinAddressList];
    };
    [self.navigationController pushViewController:vc animated:YES];
}
- (void)requestCoinAddressList{

    @weakify(self);
    [WalletNetWorkControl requestCoinAddressList:self.coinId successBlock:^(id dataSource)
     {
         @strongify(self);
         NSDictionary *data = dataSource;
         NSArray *itemArray = [data objectForKey:@"withdrawAddress"];
         if (itemArray == nil || itemArray.count == 0)
         {
             self.dataArray = nil;
             [self.tableView reloadData];
             return;
         }
         NSArray *objArray = [AddressListModel mj_objectArrayWithKeyValuesArray:itemArray];
         
         self.dataArray = objArray;
         
         self.imgURL = [dataSource objectForKey:@"appLogo"];
         [self.tableView reloadData];
     } errorBlock:^(int code, NSString *error)
     {
         ChZ_MBError(error);
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
