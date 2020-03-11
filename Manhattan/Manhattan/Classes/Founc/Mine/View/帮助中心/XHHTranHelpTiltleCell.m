//
//  XHHTranHelpTiltleCell.m
//  FuturePurse
//
//  Created by Apple on 2018/7/25.
//  Copyright © 2018年 jbtm. All rights reserved.
//

#import "XHHTranHelpTiltleCell.h"
#import "XHHTranHelpDetialCell.h"

static NSString *cellindentifier = @"XHHTranHelpDetialCell";
@interface XHHTranHelpTiltleCell()<UITableViewDelegate,UITableViewDataSource>

@property (weak, nonatomic) IBOutlet UILabel *tLable;
@property (weak, nonatomic) IBOutlet UIImageView *isUpImageView;

@property (strong, nonatomic) UITableView  *tableView;

@property (strong, nonatomic) NSArray      *dataArray;

@end

@implementation XHHTranHelpTiltleCell



- (void)awakeFromNib {
    [super awakeFromNib];
    self.selectionStyle = UITableViewCellSelectionStyleNone;
    
    
    [self addSubview:self.tableView];
    [self.tableView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.right.equalTo(self);
        make.top.equalTo(self.tLable.mas_bottom).offset(7);
        make.height.mas_equalTo(0);
    }];
}

- (void)setSelected:(BOOL)selected animated:(BOOL)animated {
    [super setSelected:selected animated:animated];

    // Configure the view for the selected state
}

-(void)reladCellWithDic:(NSDictionary *)dic{
    self.tLable.text = [dic objectForKey:@"title"];
    self.isUpImageView.image = [UIImage imageNamed:@"tran_costmer_down"];
    [self.tableView mas_updateConstraints:^(MASConstraintMaker *make) {
        make.height.mas_equalTo(0);
    }];
    
    [self.tableView reloadData];
    
}
-(void)setImageUpWithDetials:(NSArray *)array{
    self.dataArray = [array copy];
    NSLog(@"%ld=====",self.dataArray.count);
    self.isUpImageView.image = [UIImage imageNamed:@"tran_costmer_up"];
    
    CGFloat tableHeiglt = 0;
    for(int i = 0; i < self.dataArray.count;i++){
        tableHeiglt = tableHeiglt + [ChZUtil setDetailHH:self.dataArray[i] width:ChZ_WIDTH - 32 font:14.0];
    }
    [self.tableView mas_updateConstraints:^(MASConstraintMaker *make) {
        make.height.mas_equalTo(tableHeiglt + self.dataArray.count * 10);
    }];
    
    [self.tableView reloadData];
}
-(UITableView *)tableView{
    if (!_tableView) {
        _tableView = [[UITableView alloc] init];
        _tableView.delegate = self;
        _tableView.dataSource = self;
        [_tableView setSeparatorColor:[UIColor clearColor]];
        _tableView.backgroundColor = [UIColor clearColor];
        _tableView.scrollEnabled = NO;
        [_tableView registerNib:[UINib nibWithNibName:@"XHHTranHelpDetialCell" bundle:nil] forCellReuseIdentifier:cellindentifier];
    }
    return _tableView;
}
-(NSInteger)numberOfSectionsInTableView:(UITableView *)tableView{
    return 1;
}
-(NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section{
    return self.dataArray.count;
}
-(UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath{
    XHHTranHelpDetialCell *cell = [tableView dequeueReusableCellWithIdentifier:cellindentifier];
    [cell reladCellWithDic:@{@"title":self.dataArray[indexPath.row]}];
    NSLog(@"123");
    return cell;
}
-(CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath{
    return [ChZUtil setDetailHH:self.dataArray[indexPath.row] width:ChZ_WIDTH- 32 font:14.0] + 10;
}
@end
