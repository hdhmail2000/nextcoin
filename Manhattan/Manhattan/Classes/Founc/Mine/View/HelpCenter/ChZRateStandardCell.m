//
//  ChZRateStandardCell.m
//  FuturePurse
//
//  Created by Howe on 2018/8/3.
//  Copyright © 2018年 jbtm. All rights reserved.
//

#import "ChZRateStandardCell.h"
@interface ChZRateStandardCell ()
@property (weak, nonatomic) IBOutlet UILabel *titleLabel;

@property (weak, nonatomic) IBOutlet UILabel *guaLabel;

@property (weak, nonatomic) IBOutlet UILabel *maiLabel;

@end
@implementation ChZRateStandardCell

- (void)awakeFromNib {
    [super awakeFromNib];
    // Initialization code
}
- (void)setModel:(XTTMineAppHelpDetailModel *)model{
    self.titleLabel.text = model.title;
    self.guaLabel.text = [NSString stringWithFormat:@"挂: %@", model.gua];
    self.maiLabel.text = [NSString stringWithFormat:@"买: %@", model.mai];
}
- (void)setSelected:(BOOL)selected animated:(BOOL)animated {
    [super setSelected:selected animated:animated];

    // Configure the view for the selected state
}

@end
