//
//  XHHOrderDetailHeadView.m
//  FuturePurse
//
//  Created by Apple on 2018/7/30.
//  Copyright © 2018年 jbtm. All rights reserved.
//

#import "XHHOrderDetailHeadView.h"

@interface XHHOrderDetailHeadView ()
@property (weak, nonatomic) IBOutlet UILabel *payStateLable;

@property (weak, nonatomic) IBOutlet UIButton *timeButton;

@property (weak, nonatomic) IBOutlet UILabel *payNumLable;

@property (weak, nonatomic) IBOutlet UILabel *profitLable;

@property (weak, nonatomic) IBOutlet UILabel *profitNumLable;

@property (weak, nonatomic) IBOutlet UILabel *profitPriceLable;

@property (nonatomic, strong) NSTimer  *tim;

@end

@implementation XHHOrderDetailHeadView

-(void)awakeFromNib{
    [super awakeFromNib];
}
- (void)startWithTime:(NSInteger)timeLine title:(NSString *)title countDownTitle:(NSString *)subTitle mainColor:(UIColor *)mColor countColor:(UIColor *)color {
    // 倒计时时间
    __block NSInteger timeOut = timeLine;
    dispatch_queue_t queue = dispatch_get_global_queue(DISPATCH_QUEUE_PRIORITY_DEFAULT, 0);
    dispatch_source_t _timer = dispatch_source_create(DISPATCH_SOURCE_TYPE_TIMER, 0, 0, queue);

    // 每秒执行一次
    dispatch_source_set_timer(_timer, dispatch_walltime(NULL, 0), 1.0 * NSEC_PER_SEC, 0);
    dispatch_source_set_event_handler(_timer, ^{

        // 倒计时结束，关闭
        if (timeOut <= 0) {
            dispatch_source_cancel(_timer);

            dispatch_async(dispatch_get_main_queue(), ^{
                [self.timeButton setTitle:@" 交易已关闭" forState:UIControlStateNormal];

            });

        } else {
            NSString *secondsStr;
            NSString *minteStr;

            int seconds = timeOut % 60;
            NSInteger minite = timeOut / 60;

            if (seconds < 10) {
                secondsStr = [NSString stringWithFormat:@"0%d",seconds];
            }else{
                secondsStr = [NSString stringWithFormat:@"%d",seconds];
            }

            if (minite < 10) {
                minteStr = [NSString stringWithFormat:@"0%ld",minite];
            }else{
                minteStr = [NSString stringWithFormat:@"%ld",minite];
            }
            dispatch_async(dispatch_get_main_queue(), ^{
                [self.timeButton setTitle:[NSString stringWithFormat:@" %@:%@",minteStr,secondsStr] forState:UIControlStateNormal];
            });

            timeOut--;
        }
    });

    dispatch_resume(_timer);
}
-(void)reloadCellWithSellModel:(XHHC2CMyOrderModel *)model{
    NSInteger payType = [model.order_status integerValue];
    switch (payType)
    {
        case 1:
        {
            self.payStateLable.text = @"未付款";
            if (self.type == C2CTradTypeBuy)
            {
                self.timeButton.hidden = NO;
            }
            if (self.type == C2CTradTypeSell)
            {
                self.timeButton.hidden = YES;
            }
        }
            break;
        case 2:
        {
            if (self.type == C2CTradTypeBuy)
            {
                self.payStateLable.text = @"等待卖方确认";
                self.timeButton.hidden = YES;
            }
            if (self.type == C2CTradTypeSell)
            {
                self.payStateLable.text = @"待确认";
                //                self.timeButton.hidden = NO;
                self.timeButton.hidden = YES;
            }
        }
            //            self.payStateLable.text = @"已付款";
            break;
        case 3:
            self.payStateLable.text = @"已完成";
            self.payStateLable.textColor = [UIColor colorWithHexString:@"02C188"];
            //            [self.timeButton removeFromSuperview];
            self.timeButton.hidden = YES;
            break;
        case 4:
            self.payStateLable.text = @"申诉中";
            self.payStateLable.textColor = [UIColor colorWithHexString:@"02C188"];
            //            [self.timeButton removeFromSuperview];
            self.timeButton.hidden = YES;
            break;
        case 9:
            self.payStateLable.text = @"已取消";
            self.payStateLable.textColor = [UIColor colorWithHexString:@"02C188"];
            //            [self.timeButton removeFromSuperview];
            self.timeButton.hidden = YES;
            break;
            
        default:
            break;
    }
    self.payNumLable.text = nil;
    self.profitLable.text = nil;
    self.profitNumLable.text = nil;
    self.profitPriceLable.text = nil;
    
    if(ChZ_StringIsEmpty(model.pay_no))self.payNumLable.text = model.pay_no;
    if(ChZ_StringIsEmpty(model.order_price))self.profitLable.text = model.order_price;
    if(ChZ_StringIsEmpty(model.order_volume))self.profitNumLable.text = [NSString stringWithFormat:@"%.4f",[model.order_volume floatValue]];
    if(ChZ_StringIsEmpty(model.price))self.profitPriceLable.text = model.price;
    
    if ((payType == 1 && self.type == C2CTradTypeBuy))
    {
        NSTimeInterval thisTime = [[NSDate date] timeIntervalSince1970];
        NSTimeInterval orderTime = 0;
        if (self.type == C2CTradTypeBuy)
        {
            orderTime = [model.order_time doubleValue];
        }
        if (self.type == C2CTradTypeSell)
        {
            orderTime = [model.pay_time doubleValue];
        }
        
        NSTimeInterval time = thisTime - orderTime;
        NSTimeInterval sumTime = 15 * 60;
        if (time > sumTime)
        {//订单超过15分钟 订单取消
            return;
        }
        NSTimeInterval toTime = sumTime - time;
        
        [self startWithTime:(NSInteger)toTime  title:nil countDownTitle:nil mainColor:nil countColor:nil];
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
