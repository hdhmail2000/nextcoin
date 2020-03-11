//
//  ChZCommonProblemController.m
//  FuturePurse
//
//  Created by Howe on 2018/8/3.
//  Copyright © 2018年 jbtm. All rights reserved.
//

#import "ChZCommonProblemController.h"
#import "MYHelpListCell.h"
#import "MYHelpListHeaderView.h"
#import "XTTMineRequest.h"
#import "XTTMineModel.h"

@interface ChZCommonProblemController ()<UITableViewDelegate,UITableViewDataSource>
@property (weak, nonatomic) IBOutlet UITableView *tableView;
@property (nonatomic, strong) NSArray *dataSource;
@end

@implementation ChZCommonProblemController

- (void)viewDidLoad {
    [super viewDidLoad];
    self.tableView.tableFooterView = UIView.new;
    [MYHelpListCell registerCellWithTableView:self.tableView identifier:@"cell" nibName:@"MYHelpListCell"];
    [self.tableView registerNib:[UINib nibWithNibName:@"MYHelpListHeaderView" bundle:nil] forHeaderFooterViewReuseIdentifier:@"MYHelpListHeaderView"];
    self.tableView.tableFooterView = UIView.new;
    self.tableView.dataSource = self;
    self.tableView.delegate = self;
    [self requestMineHelpDetail:self.catid];

}
- (IBAction)backAction:(id)sender {
    [self pop];
    
}

//- (CGFloat)tableView:(UITableView *)tableView heightForHeaderInSection:(NSInteger)section
//{
//
////    if (self.dataSource.count>section)
////    {
////        MYHelpListModel *model = self.dataSource[section];
////        CGFloat width = tableView.chz_w - 16 -16 - 12 - 10;
////        return [ChZUtil chz_labelSizeWithString:model.name font:[UIFont systemFontOfSize:14] width:width].height + 15 + 15;
////    }
//    return 50;
//}
- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView
{
    return self.dataSource.count;
}
- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
//    if (self.dataSource.count> section)
//    {
//        MYHelpListModel *model = self.dataSource[section];
//        if (model.isSelcted) {
//            return 1;
//        }
//    }
    XTTMineAppHelpDetailModel * model = self.dataSource[section];
   
    return model.isExpand ? 1: 0;
}
- (UIView *)tableView:(UITableView *)tableView viewForHeaderInSection:(NSInteger)section
{
    
    MYHelpListHeaderView *headerView = [tableView dequeueReusableHeaderFooterViewWithIdentifier:@"MYHelpListHeaderView"];
//    if (headerView == nil) {
//        headerView = [[MYHelpListHeaderView alloc]initWithReuseIdentifier:@"MYHelpListHeaderView"];;
//    }
    if (self.dataSource.count>section)
    {
        headerView.model = self.dataSource[section];
    }
    ChZ_Weak
    headerView.block = ^(id Obj) {
        ChZ_Strong
        [_strongSelf.tableView reloadData];
    };
    return headerView;
}
- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{

    MYHelpListCell *cell = [tableView dequeueReusableCellWithIdentifier:@"cell"];
    if (self.dataSource.count>indexPath.section)
    {
        cell.model = self.dataSource[indexPath.section];
    }
    return cell;
}


- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}


-(void)requestMineHelpDetail:(NSString *)catid{
    ChZ_MBMsg(nil)
    [XTTMineRequest requestMineHelpDetail:catid successBlock:^(id dataSource) {
        ChZ_MBDismss
        self.dataSource = dataSource;
        [self.tableView reloadData];
    } errorBlock:^(NSString *error) {
        
        ChZ_MBDismss
        ChZ_MBError(error);
    }];
}


@end
