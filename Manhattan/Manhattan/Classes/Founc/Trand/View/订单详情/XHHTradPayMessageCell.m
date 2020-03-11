//
//  XHHTradPayMessageCell.m
//  FuturePurse
//
//  Created by Apple on 2018/7/30.
//  Copyright © 2018年 jbtm. All rights reserved.
//

#import "XHHTradPayMessageCell.h"
@interface XHHTradPayMessageCell ()

@property (weak, nonatomic) IBOutlet UILabel *payTypeLable;//支付方式

@property (weak, nonatomic) IBOutlet UILabel *nameLable;//姓名

@property (weak, nonatomic) IBOutlet UILabel *numberLable;//账号

@end


@implementation XHHTradPayMessageCell

- (void)awakeFromNib {
    [super awakeFromNib];
    // Initialization code
}
- (void)setModel:(XHHC2CMyOrderModel *)model
{
    _model = model;
    self.nameLable.text = nil;
    self.numberLable.text = nil;
    
    if (self.isAlipay)
    {
        self.payTypeLable.text = @"支付宝";
        if (model.alipay)
        {
            if(ChZ_StringIsEmpty(model.alipay.name))self.nameLable.text = model.alipay.name;
            if(ChZ_StringIsEmpty(model.alipay.username))self.numberLable.text = model.alipay.username;
        }
    }else
    {
        self.payTypeLable.text = @"微信";
        if (model.weixin)
        {
            if(ChZ_StringIsEmpty(model.weixin.name))self.nameLable.text = model.weixin.name;
            if(ChZ_StringIsEmpty(model.weixin.username))self.numberLable.text = model.weixin.username;
        }
    }
    
    
}
- (void)setSelected:(BOOL)selected animated:(BOOL)animated {
    [super setSelected:selected animated:animated];

    // Configure the view for the selected state
}

@end
