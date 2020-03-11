//
//  XHHEntrustRowTableCell.m
//  FuturePurse
//
//  Created by Apple on 2018/7/27.
//  Copyright © 2018年 jbtm. All rights reserved.
//

#import "XHHEntrustRowTableCell.h"

@interface XHHEntrustRowTableCell ()
@property (weak, nonatomic) IBOutlet UILabel *priceLable;
@property (weak, nonatomic) IBOutlet UILabel *sumLable;
@property (weak, nonatomic) IBOutlet UILabel *muchLable;
@property (weak, nonatomic) IBOutlet UILabel *chargeLable;

@end

@implementation XHHEntrustRowTableCell

- (void)awakeFromNib {
    [super awakeFromNib];
    // Initialization code
}

- (void)setSelected:(BOOL)selected animated:(BOOL)animated {
    [super setSelected:selected animated:animated];

    // Configure the view for the selected state
}

@end
