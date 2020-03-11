//
//  XHHMyDealCell.m
//  FuturePurse
//
//  Created by Apple on 2018/7/27.
//  Copyright © 2018年 jbtm. All rights reserved.
//

#import "XHHMyDealCell.h"
@interface XHHMyDealCell ()
@property (weak, nonatomic) IBOutlet UILabel *nameLable;
@property (weak, nonatomic) IBOutlet UILabel *sumLable;
@property (weak, nonatomic) IBOutlet UILabel *priceLable;
@property (weak, nonatomic) IBOutlet UILabel *lessLable;

@property (weak, nonatomic) IBOutlet UIImageView *walletImageView;

@property (weak, nonatomic) IBOutlet UIImageView *aliPayImageView;

@property (weak, nonatomic) IBOutlet UIImageView *weiChatImageLable;

@property (weak, nonatomic) IBOutlet UIButton *buyButton;

@end


@implementation XHHMyDealCell

- (void)awakeFromNib {
    [super awakeFromNib];
    self.buyButton.layer.cornerRadius = 3.0;
    // Initialization code
}

- (void)setSelected:(BOOL)selected animated:(BOOL)animated {
    [super setSelected:selected animated:animated];

    // Configure the view for the selected state
}

-(void)setTradType:(C2CTradType)tradType
{
    _tradType = tradType;
    if (tradType == C2CTradTypeBuy)
    {
        [self.buyButton setTitle:@"购买" forState:UIControlStateNormal];
        [self.buyButton setBackgroundImage:[UIImage imageNamed:@"trad_c2c_samllBuybgimage"] forState:UIControlStateNormal];
    }else
    {
        [self.buyButton setTitle:@"出售" forState:UIControlStateNormal];
        [self.buyButton setBackgroundImage:[UIImage imageNamed:@"trad_c2c_smallSellbgimage"] forState:UIControlStateNormal];
    }
}
- (void)setModel:(XHHC2CBuyModel *)model
{
    _model = model;
    
    _nameLable.text = model.author;
    _sumLable.text = [NSString stringWithFormat:@"%.4f%@",[model.order_volume floatValue],model.symbol_name];
    _priceLable.text = [NSString stringWithFormat:@"%.2fCNY",[model.order_price floatValue]];
    _lessLable.text = [NSString stringWithFormat:@"%.4f%@",[model.min_value floatValue],model.symbol_name];
    
    NSArray *payTypeArray = [model.pay_type componentsSeparatedByString:@","];
    
    if (payTypeArray.count == 2)
    {
        if (![payTypeArray containsObject:@"支付宝"])
        {
            _aliPayImageView.image = [UIImage imageNamed:@"trad_vip_wechat"];
            _weiChatImageLable.hidden = YES;
        }else{
            _walletImageView.image = [UIImage imageNamed:@"trad_vip_zhifubao"];
            _walletImageView.image = [UIImage imageNamed:@"trad_vip_wechat"];
        }
        _weiChatImageLable.hidden = YES;
    }else if (payTypeArray.count == 1)
    {
        _aliPayImageView.hidden = YES;
        _weiChatImageLable.hidden = YES;
        if ([payTypeArray containsObject:@"支付宝"])
        {
            _walletImageView.image = [UIImage imageNamed:@"trad_vip_zhifubao"];
        }else if ([payTypeArray containsObject:@"微信"])
        {
            _walletImageView.image = [UIImage imageNamed:@"trad_vip_wechat"];
        }else
        {
            _walletImageView.image = [UIImage imageNamed:@"trad_vip_ban"];
        }
    }else
    {
        _walletImageView.image = [UIImage imageNamed:@"trad_vip_ban"];
        _aliPayImageView.image = [UIImage imageNamed:@"trad_vip_zhifubao"];
        _weiChatImageLable.image = [UIImage imageNamed:@"trad_vip_wechat"];
    }
}
- (IBAction)buySellAction:(UIButton *)sender {
    if (self.block) {
        self.block(self.index, self.model, self.tradType);
    }
}
@end
