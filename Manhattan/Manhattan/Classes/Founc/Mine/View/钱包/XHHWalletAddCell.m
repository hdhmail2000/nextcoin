//
//  XHHWalletAddCell.m
//  Manhattan
//
//  Created by Apple on 2018/8/16.
//  Copyright © 2018年 Apple. All rights reserved.
//

#import "XHHWalletAddCell.h"
#import "XHHWalletHideCoinList.h"
@interface XHHWalletAddCell ()

@property (weak, nonatomic) IBOutlet UISwitch *mySwitch;
@property (weak, nonatomic) IBOutlet UILabel *coinName;
@property (weak, nonatomic) IBOutlet UIImageView *coinImage;
@property (weak, nonatomic) IBOutlet UIButton *coinButton;

@end

@implementation XHHWalletAddCell

- (void)awakeFromNib {
    [super awakeFromNib];
    
    self.selectionStyle = UITableViewCellSelectionStyleNone;
    // Initialization code
}

- (void)setSelected:(BOOL)selected animated:(BOOL)animated {
    [super setSelected:selected animated:animated];

    // Configure the view for the selected state
}
- (IBAction)chooseAction:(UIButton *)sender {
    if(self.block)
    {
        self.block(0, self.hideModel, 0);
    }
}
-(void)setHideModel:(WalletListModel *)hideModel
{
    _hideModel = hideModel;
    self.coinName.text = nil;
    self.coinImage.image = nil;
    if(ChZ_StringIsEmpty(hideModel.coinName)) self.coinName.text = hideModel.coinName;
    if(ChZ_StringIsEmpty(hideModel.logo)) [self.coinImage sd_setImageWithURL:[NSURL URLWithString:hideModel.logo]];
    self.coinButton.selected = NO;
    for(XHHWalletHideCoinList *m in self.hideListArray)
    {
        if ([m.coin_name isEqualToString:hideModel.coinName])
        {
            self.coinButton.selected = YES;
        }
    }
    
}
@end
