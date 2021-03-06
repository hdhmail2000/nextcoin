//
//  XHHCurrencyCell.m
//  Manhattan
//
//  Created by Apple on 2018/8/20.
//  Copyright © 2018年 Apple. All rights reserved.
//

#import "XHHCurrencyCell.h"

@interface XHHCurrencyCell ()

@property (weak, nonatomic) IBOutlet UILabel *tLable;

@property (weak, nonatomic) IBOutlet UIImageView *choseImage;

@end

@implementation XHHCurrencyCell

- (void)awakeFromNib {
    [super awakeFromNib];
    // Initialization code
}

- (void)setSelected:(BOOL)selected animated:(BOOL)animated {
    [super setSelected:selected animated:animated];

    // Configure the view for the selected state
}
-(void)setModel:(XHHC2CCnyListModel *)model{
    _model = model;
    self.tLable.text = model.short_name;
}
-(void)choseImageHiddle:(BOOL)hiddle{
    _choseImage.hidden = hiddle;
}

@end
