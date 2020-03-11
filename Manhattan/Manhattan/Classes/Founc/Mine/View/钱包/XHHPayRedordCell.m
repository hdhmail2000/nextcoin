//
//  XHHPayRedordCell.m
//  Manhattan
//
//  Created by Apple on 2018/8/17.
//  Copyright © 2018年 Apple. All rights reserved.
//

#import "XHHPayRedordCell.h"

@interface XHHPayRedordCell ()

@property (weak, nonatomic) IBOutlet UIImageView *img;

@property (weak, nonatomic) IBOutlet UILabel *title;

@property (weak, nonatomic) IBOutlet UILabel *detail;

@property (weak, nonatomic) IBOutlet UILabel *time;

@property (weak, nonatomic) IBOutlet UILabel *money;

@property (weak, nonatomic) IBOutlet UILabel *type;

@end


@implementation XHHPayRedordCell

- (void)awakeFromNib {
    [super awakeFromNib];
    self.backgroundColor = [UIColor clearColor];
    
    //转入moneycolor  4EE2FE  image：
    //转出moneycolor  FF67D2  image：
    
    
    
    // Initialization code
}
- (void)setModel:(RecordListModel *)model{
    _model = model;
    if (ChZ_StringIsEmpty(model.operationDesc))self.type.text = model.relationCoinName;
    if (ChZ_StringIsEmpty(model.statusDesc))self.detail.text = model.statusDesc;
    double coinNumber = [model.amount doubleValue];
    self.money.text = [NSString stringWithFormat:@"%.4f",coinNumber];
    self.time.text = [ChZUtil chz_getDateAndTimeWithTimeIntervalSince1970:[NSString stringWithFormat:@"%.0f",[model.createDate doubleValue]/1000]];
    NSString *smbolString = @"";
    switch ([model.operation integerValue])
    {
        case 1://充值
            self.title.text  = @"充值";
            self.img.image = [UIImage imageNamed:@"mine_wallet_moneyin"];
            smbolString = @"+";
            break;
        case 2://提现
            self.title.text  = @"转出";
            self.img.image = [UIImage imageNamed:@"mine_wallet_moneyout"];
            smbolString = @"-";
            break;
        case 3://转账
            self.title.text  = @"转账";
            self.img.image = [UIImage imageNamed:@"mine_wallet_moneyout"];
            smbolString = @"-";
            break;
        case 4://收款
            self.title.text  = @"转入";
            self.img.image = [UIImage imageNamed:@"mine_wallet_moneyin"];
            smbolString = @"+";
            break;
        case 6://其它
            self.title.text  = @"其它";
            self.img.image = [UIImage imageNamed:@"mine_wallet_moneyin"];
            smbolString = @"+";
            break;
        default:
            break;
    }
    self.money.text = [smbolString stringByAppendingString:self.money.text];
}
- (void)setSelected:(BOOL)selected animated:(BOOL)animated {
    [super setSelected:selected animated:animated];

    // Configure the view for the selected state
}

@end
