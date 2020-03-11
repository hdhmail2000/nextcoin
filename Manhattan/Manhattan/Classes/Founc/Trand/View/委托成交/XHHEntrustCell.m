//
//  XHHEntrustCell.m
//  FuturePurse
//
//  Created by Apple on 2018/7/27.
//  Copyright © 2018年 jbtm. All rights reserved.
//

#import "XHHEntrustCell.h"
#import "XHHEntrustRowTableCell.h"
#import "XHHEntrustRowTableHeadView.h"

static NSString *cellInditifier = @"XHHEntrustRowTableCell";
@interface XHHEntrustCell ()<UITableViewDelegate,UITableViewDataSource>



@property (weak, nonatomic) IBOutlet UILabel *timeLable;
@property (weak, nonatomic) IBOutlet UILabel *muchMoney;
@property (weak, nonatomic) IBOutlet UILabel *allMoney;
@property (weak, nonatomic) IBOutlet UILabel *profitMoney;
@property (weak, nonatomic) IBOutlet UIButton *typeButton;
@property (weak, nonatomic) IBOutlet UILabel *typeLable;
@property (weak, nonatomic) IBOutlet UIImageView *typeImage;
@property (weak, nonatomic) IBOutlet UIView *bgView;
@property (weak, nonatomic) IBOutlet UILabel *sumLable;

@property (strong, nonatomic) UITableView  *tableView;
@property (strong, nonatomic) XHHEntrustRowTableHeadView *headView;
@property (strong, nonatomic) NSArray      *dataArray;
@property (strong , nonatomic) ChZMarketTxsOrderItemModel *orderModel;

@property (assign, nonatomic) BOOL isDeal;

@end

@implementation XHHEntrustCell

- (void)awakeFromNib {
    [super awakeFromNib];
    // Initialization code
}

- (void)setSelected:(BOOL)selected animated:(BOOL)animated {
    [super setSelected:selected animated:animated];

    // Configure the view for the selected state
}
-(void)IsDealList:(BOOL)isDeal
{
    self.isDeal = isDeal;
}

-(void)creatPayDetailListWithList:(NSArray *)list{
    self.typeImage.image = [UIImage imageNamed:@"tead_entrust_show"];
    self.dataArray = list;
    [self.contentView addSubview:self.headView];
    [self.headView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(self.contentView).offset(16);
        make.top.equalTo(self.bgView.mas_bottom);
        make.right.mas_equalTo(self.contentView).offset(-16);
        make.height.mas_equalTo(40);
    }];
    [self.contentView addSubview:self.tableView];
    [self.tableView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.right.equalTo(self.headView);
        make.top.equalTo(self.headView.mas_bottom);
        make.height.mas_equalTo(30*list.count);
    }];
}
-(void)removeDealListView{
    self.typeImage.image = [UIImage imageNamed:@"tead_entrust_close"];
    [self.headView removeFromSuperview];
    [self.tableView removeFromSuperview];
}
-(void)reloadCellWithModel:(ChZMarketTxsOrderItemModel *)model
{
    self.orderModel = model;
    if (model.type == 0)
    {
        self.typeLable.text = @"买入";
        self.typeLable.textColor = [UIColor colorWithHexString:@"4EE2FE"];
        self.nameLable.textColor = [UIColor colorWithHexString:@"4EE2FE"];
    }else if (model.type == 1)
    {
        self.typeLable.text = @"卖出";
        self.typeLable.textColor = [UIColor colorWithHexString:@"FF67D2"];
        self.nameLable.textColor = [UIColor colorWithHexString:@"FF67D2"];
    }
    
    if(ChZ_StringIsEmpty(model.time))self.timeLable.text = model.time;
    
    self.muchMoney.text = [NSString stringWithFormat:@"%.4f",model.price];
    self.sumLable.text = [NSString stringWithFormat:@"X %.4f",model.count];
    self.profitMoney.text = [NSString stringWithFormat:@"成交 %.4f",(model.count - model.leftcount)];
    double sum = model.price * model.count;
    self.allMoney.text = [NSString stringWithFormat:@"总额度 %.4f",sum];
    if(self.isDeal)
    {
        switch (model.status)
        {
            case 1:
                [self.typeButton setTitle:@"未成交" forState:UIControlStateNormal];
                self.profitMoney.hidden = NO;
                break;
            case 2:
                [self.typeButton setTitle:@"部分成交" forState:UIControlStateNormal];
                self.profitMoney.hidden = NO;
                break;
            case 3:
                [self.typeButton setTitle:@"完全成交" forState:UIControlStateNormal];
                self.profitMoney.hidden = NO;
                break;
            case 4:
                [self.typeButton setTitle:@"撤单处理中" forState:UIControlStateNormal];
                self.profitMoney.hidden = YES;
                break;
            case 5:
                [self.typeButton setTitle:@"已撤销" forState:UIControlStateNormal];
                self.profitMoney.hidden = YES;
                break;
                
            default:
                break;
        }
        self.typeImage.hidden = YES;
        self.sumLable.hidden = YES;
    }else
    {
        self.typeImage.hidden = YES;
        self.sumLable.hidden = NO;
    }
}
- (IBAction)cannelAction:(id)sender
{
    if (self.block)
    {
        self.block(1, self.orderModel, 1);
    }
}
-(UITableView *)tableView{
    if (!_tableView) {
        _tableView = [[UITableView alloc] init];
        _tableView.delegate = self;
        _tableView.dataSource = self;
        _tableView.rowHeight = 30;
        _tableView.backgroundColor = [UIColor colorWithHexString:@"363672"];
        [_tableView setSeparatorColor:[UIColor clearColor]];
//        [_tableView setSeparatorColor:[UIColor colorWithHexString:@"363672"]];
        [_tableView registerNib:[UINib nibWithNibName:@"XHHEntrustRowTableCell" bundle:nil] forCellReuseIdentifier:cellInditifier];
    }
    return _tableView;
}
-(XHHEntrustRowTableHeadView *)headView
{
    if (!_headView) {
        _headView = [[[NSBundle mainBundle] loadNibNamed:@"XHHEntrustRowTableHeadView" owner:nil options:nil] lastObject];
        _headView.backgroundColor = [UIColor colorWithHexString:@"363672"];
    }
    return _headView;
}
-(NSInteger)numberOfSectionsInTableView:(UITableView *)tableView{
    return 1;
}
-(NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section{
    return self.dataArray.count;
}
-(UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath{
    XHHEntrustRowTableCell *cell = [tableView dequeueReusableCellWithIdentifier:cellInditifier];
    cell.selectionStyle = UITableViewCellSelectionStyleNone;
    return cell;
}
@end
