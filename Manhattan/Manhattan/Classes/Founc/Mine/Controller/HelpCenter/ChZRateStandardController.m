//
//  ChZRateStandardController.m
//  FuturePurse
//
//  Created by Howe on 2018/8/3.
//  Copyright © 2018年 jbtm. All rights reserved.
//

#import "ChZRateStandardController.h"
#import "ChZRateStandardCell.h"
#import "XTTMineRequest.h"
#import "XTTMineModel.h"

@interface ChZRateStandardController ()<UITableViewDataSource,UITableViewDelegate>
@property (weak, nonatomic) IBOutlet UITableView *tableView;
@property (nonatomic, strong) NSArray  *dataSource;

@end

@implementation ChZRateStandardController

- (void)viewDidLoad {
    [super viewDidLoad];
    [self _setupView];
    [self requestMineHelpDetail:self.catid];
}

- (IBAction)backAction:(id)sender
{
    [self pop];
}
- (void)_setupView
{
//    [self.refreshControl beginRefreshing];
//    [self chz_setupRefresh:self.tableView];
    [ChZRateStandardCell registerCellWithTableView:self.tableView identifier:@"cell" nibName:@"ChZRateStandardCell"];
    self.tableView.dataSource = self;
    self.tableView.delegate = self;
}
- (void)chz_beginRefresh
{
//    [self.refreshControl endRefreshing];
}
- (void)refreshData
{
    
}


- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
    return self.dataSource.count;
}
- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    XTTMineAppHelpDetailModel *model = self.dataSource[indexPath.row];
    ChZRateStandardCell *cell = [tableView dequeueReusableCellWithIdentifier:@"cell"];
    cell.model = model;
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
