//
//  ChZOrderDetailBottomView.m
//  FuturePurse
//
//  Created by Howe on 2018/9/13.
//  Copyright © 2018年 jbtm. All rights reserved.
//

#import "ChZOrderDetailBottomView.h"
@interface ChZOrderDetailBottomView()
@property (weak, nonatomic) IBOutlet UIButton *handleBtn;
@property (weak, nonatomic) IBOutlet UIButton *complaintBtn;

@end

@implementation ChZOrderDetailBottomView

- (IBAction)handleAction:(id)sender
{
    if (self.block) {
        self.block(YES);
    }
}

- (IBAction)complaintAction:(id)sender
{
    if (self.block) {
        self.block(NO);
    }
}


- (void)setOrderModel:(XHHC2CMyOrderModel *)orderModel
{
    _orderModel = orderModel;
    NSInteger payType = [self.orderModel.order_status integerValue];
    switch (payType)
    {
        case 1:
        {
            if (self.type == C2CTradTypeBuy)
            {
                self.hidden = NO;
                [self mas_updateConstraints:^(MASConstraintMaker *make)
                 {
                     make.height.mas_equalTo(88.0f);
                 }];
                [self.handleBtn setTitle:@"我已付款" forState:UIControlStateNormal];
                self.complaintBtn.hidden =YES;
            }
            if (self.type == C2CTradTypeSell)
            {
                self.hidden = YES;
                [self mas_updateConstraints:^(MASConstraintMaker *make)
                 {
                     make.height.mas_equalTo(0.0f);
                 }];
                
            }
            
        }
            break;
        case 2:
        {
            self.hidden = NO;
            [self mas_updateConstraints:^(MASConstraintMaker *make)
             {
                 make.height.mas_equalTo(88.0f);
             }];
            if (self.type == C2CTradTypeBuy)
            {//买家可申述
                self.complaintBtn.hidden = NO;
                self.handleBtn.hidden = YES;
            }
            if (self.type == C2CTradTypeSell)
            {//卖家可申述 可确认收款
                self.handleBtn.hidden = NO;
                self.complaintBtn.hidden = NO;
                [self.handleBtn setTitle:@"我已收款" forState:UIControlStateNormal];
            }
        }
            break;
        case 3:
        {
            self.hidden = YES;
            [self mas_updateConstraints:^(MASConstraintMaker *make)
             {
                 make.height.mas_equalTo(0.0f);
             }];
        }
            break;
        case 4:
        {
            self.hidden = YES;
            [self mas_updateConstraints:^(MASConstraintMaker *make)
             {
                 make.height.mas_equalTo(0.0f);
             }];
        }
            break;
        case 9:
        {
            self.hidden = YES;
            [self mas_updateConstraints:^(MASConstraintMaker *make)
             {
                 make.height.mas_equalTo(0.0f);
             }];
        }
            break;
            
        default:
            break;
    }
}
/*
// Only override drawRect: if you perform custom drawing.
// An empty implementation adversely affects performance during animation.
- (void)drawRect:(CGRect)rect {
    // Drawing code
}
*/

@end
