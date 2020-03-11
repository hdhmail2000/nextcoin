//
//  ChZCoinInfoController.m
//  FuturePurse
//
//  Created by Howe on 2018/8/3.
//  Copyright © 2018年 jbtm. All rights reserved.
//

#import "ChZCoinInfoController.h"
#import "ChZCoinInfoCell.h"
#import "ChZCoinInfoDetailController.h"
#import "XTTMineRequest.h"
#import "XTTMineModel.h"

@interface ChZCoinInfoController ()<UITableViewDataSource,UITableViewDelegate>
@property (weak, nonatomic) IBOutlet UITableView *tableview;
@property (nonatomic, strong) NSArray  *dataSource;

@end

@implementation ChZCoinInfoController

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
    self.tableview.tableFooterView = UIView.new;
    [ChZCoinInfoCell registerCellWithTableView:self.tableview identifier:@"cell" nibName:@"ChZCoinInfoCell"];
    [self.tableview setSeparatorColor:[UIColor clearColor]];
    self.tableview.dataSource = self;
    self.tableview.delegate = self;
}


- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
    return self.dataSource.count;
}
- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    XTTMineAppHelpDetailModel *model = self.dataSource[indexPath.row];
    ChZCoinInfoCell *cell = [tableView dequeueReusableCellWithIdentifier:@"cell"];
    cell.nameLabel.text = model.title;
    return cell;
}
- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath
{
    XTTMineAppHelpDetailModel *model = self.dataSource[indexPath.row];
    ChZCoinInfoDetailController *vc = [ChZCoinInfoDetailController initWithStoryboard:@"HelpCenter"];
    vc.html = model.huobiziliao;
    [self.navigationController pushViewController:vc animated:YES];
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
        [self.tableview reloadData];
    } errorBlock:^(NSString *error) {
        
        ChZ_MBDismss
        ChZ_MBError(error);
    }];
}

@end
