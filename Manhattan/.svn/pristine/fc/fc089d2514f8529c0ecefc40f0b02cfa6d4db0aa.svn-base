//
//  ChZReceiptAccountListCell.m
//  FuturePurse
//
//  Created by Howe on 2018/8/3.
//  Copyright © 2018年 jbtm. All rights reserved.
//

#import "ChZReceiptAccountListCell.h"
@interface ChZReceiptAccountListCell()
@property (weak, nonatomic) IBOutlet UILabel *nameLabel;
@property (weak, nonatomic) IBOutlet UILabel *accountLabel;

@property (weak, nonatomic) IBOutlet UILabel *userNameLable;

@end

@implementation ChZReceiptAccountListCell

- (void)awakeFromNib {
    [super awakeFromNib];
    // Initialization code
}
- (void)setBankModel:(ChZUserPayBankAccountModel *)bankModel
{
    _bankModel = bankModel;
    self.nameLabel.text = bankModel.khm;
    self.accountLabel.text = bankModel.zhhm;
    self.userNameLable.text = bankModel.khh;
    
}
- (void)setAlipayModel:(ChZUserPayAlipayAccountModel *)alipayModel
{
    _alipayModel = alipayModel;
    self.nameLabel.text = @"支付宝";
    self.userNameLable.text = alipayModel.name;
    self.accountLabel.text = alipayModel.username;
}

- (void)setWechatModel:(ChZUserPayWechatAccountModel *)wechatModel
{
    _wechatModel = wechatModel;
    self.nameLabel.text = @"微信";
    self.userNameLable.text = wechatModel.name;
    self.accountLabel.text = wechatModel.username;
    
}


- (IBAction)qrAction:(id)sender
{
    if (self.block) {
        self.block(self.index, nil, 1);
    }

}
- (IBAction)removeAction:(id)sender
{
    if (self.block) {
        self.block(self.index, nil, 2);
    }
}

- (void)setSelected:(BOOL)selected animated:(BOOL)animated
{
    [super setSelected:selected animated:animated];

    // Configure the view for the selected state
}

@end
