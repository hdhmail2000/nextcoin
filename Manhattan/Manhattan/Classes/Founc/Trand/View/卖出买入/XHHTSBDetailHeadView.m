//
//  XHHTSBDetailHeadView.m
//  FuturePurse
//
//  Created by Apple on 2018/7/30.
//  Copyright © 2018年 jbtm. All rights reserved.
//

#import "XHHTSBDetailHeadView.h"

@interface XHHTSBDetailHeadView ()
@property (weak, nonatomic) IBOutlet UILabel *buyNumLable;

@property (weak, nonatomic) IBOutlet UILabel *buyNumTitleLable;

@property (weak, nonatomic) IBOutlet UILabel *profitNumLable;

@property (weak, nonatomic) IBOutlet UILabel *profitTitleLable;


@property (weak, nonatomic) IBOutlet UILabel *title;

@end


@implementation XHHTSBDetailHeadView

/*
// Only override drawRect: if you perform custom drawing.
// An empty implementation adversely affects performance during animation.
- (void)drawRect:(CGRect)rect {
    // Drawing code
}
*/

-(void)reloadViewWithType:(NSInteger)type
{
    switch (type) {
        case 1:
            self.buyNumTitleLable.text = @"买入量";
            _title.text = @"购买ETH";
            break;
        case 2:
            self.buyNumTitleLable.text = @"出售量";
            _title.text = @"出售ETH";
            break;
            
        default:
            break;
    }
}
-(void)reloadViewWithModel:(XHHC2CMyReleaseModel *)model{
    if ([model.deal_type isEqualToString:@"2"]) {
        self.buyNumTitleLable.text = @"买入量";
        self.title.text = [NSString stringWithFormat:@"购买%@",model.symbol_name];
        
    }else{
        self.buyNumTitleLable.text = @"出售量";
        self.title.text = [NSString stringWithFormat:@"出售%@",model.symbol_name];
    }
    self.buyNumLable.text = [NSString stringWithFormat:@"%.4f",[model.trade_total floatValue]];
    self.profitNumLable.text = [NSString stringWithFormat:@"%.4f",[model.success_total floatValue]];
    self.profitTitleLable.text = [NSString stringWithFormat:@"已成交量(%@)",model.symbol_name];
}
@end
