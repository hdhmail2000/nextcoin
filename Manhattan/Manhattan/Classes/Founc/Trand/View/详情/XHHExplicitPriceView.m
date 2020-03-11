//
//  XHHExplicitPriceView.m
//  FuturePurse
//
//  Created by Apple on 2018/7/26.
//  Copyright © 2018年 jbtm. All rights reserved.
//

#import "XHHExplicitPriceView.h"

@interface XHHExplicitPriceView ()
@property (weak, nonatomic) IBOutlet UILabel *currPrice;
@property (weak, nonatomic) IBOutlet UILabel *upMuchLable;
@property (weak, nonatomic) IBOutlet UILabel *heighPricLable;
@property (weak, nonatomic) IBOutlet UILabel *downPriceLale;
@property (weak, nonatomic) IBOutlet UILabel *profitSumLable;
@property (weak, nonatomic) IBOutlet UIImageView *typeImageView;

@end

@implementation XHHExplicitPriceView

-(void)setCoinModel:(ChZRealCoinInfoModel *)coinModel
{
    _coinModel = coinModel;
    self.heighPricLable.text = [NSString stringWithFormat:@"%.4f",[coinModel.sell floatValue]];
    self.downPriceLale.text = [NSString stringWithFormat:@"%.4f",[coinModel.buy floatValue]];
    self.currPrice.text = [NSString stringWithFormat:@"%.4f",[coinModel.p_new floatValue]];
    self.profitSumLable.text = [NSString stringWithFormat:@"%.4f",[coinModel.total floatValue]];
    CGFloat up = [coinModel.chg floatValue];
    if (up > 0) {//涨
        self.currPrice.textColor = [UIColor colorWithHexString:@"2E7AF1"];
        self.typeImageView.image = [UIImage imageNamed:@"trad_explicprice_up"];
        self.upMuchLable.textColor = [UIColor colorWithHexString:@"2E7AF1"];
    }else{//跌
        up = up;
        self.currPrice.textColor = [UIColor colorWithHexString:@"FF67D2"];
        self.typeImageView.image = [UIImage imageNamed:@"trad_explicprice_down"];
        self.upMuchLable.textColor = [UIColor colorWithHexString:@"FF67D2"];
    }
    self.upMuchLable.text = [NSString stringWithFormat:@"%0.2f%%",up];
}

@end
