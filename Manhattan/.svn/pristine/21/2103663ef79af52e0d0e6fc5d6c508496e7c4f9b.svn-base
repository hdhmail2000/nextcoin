//
//  XHHWalletCell.m
//  Manhattan
//
//  Created by Apple on 2018/8/16.
//  Copyright © 2018年 Apple. All rights reserved.
//

#import "XHHWalletCell.h"
#import "NSString+Custom.h"
#import "ChZHomeRequest.h"
#import "ChZRealCoinInfoModel.h"
@interface XHHWalletCell ()

@property (weak, nonatomic) IBOutlet UIImageView *typeImage;
@property (weak, nonatomic) IBOutlet UILabel *nameLable;
@property (weak, nonatomic) IBOutlet UILabel *moneyLable;
@property (weak, nonatomic) IBOutlet UILabel *aboutLable;
@property (nonatomic, strong) NSTimer  *timer;

@property (nonatomic, strong) ChZRealCoinInfoModel  *coinModel;
@end

@implementation XHHWalletCell

- (void)awakeFromNib {
    [super awakeFromNib];
    self.nameLable.text = nil;
    self.moneyLable.text = nil;
    self.aboutLable.text = nil;
}
- (void)dealloc
{
    [self _removeTimer];
}


- (void)setSelected:(BOOL)selected animated:(BOOL)animated {
    [super setSelected:selected animated:animated];

    // Configure the view for the selected state
}
-(void)setModel:(WalletListModel *)model{
    _model = model;
    if(ChZ_StringIsEmpty(model.logo)){
        [self.typeImage sd_setImageWithURL:[NSURL URLWithString:model.logo]];
    }
    self.nameLable.text = [NSString stringWithFormat:@"%@",model.shortName];
    self.moneyLable.text = [NSString stringWithFormat:@"%.4f",(floor(model.total*10000))/10000];
//    NSArray<ChZMarketListModel *> *listArray  = [APPControl sharedAPPControl].listArray;
//    NSString *moneyString = [NSString stringWithFormat:@"%.2f",model.total * [[APPControl sharedAPPControl].cny floatValue]];
//    self.aboutLable.text = [NSString stringWithFormat:@"≈ ￥%@",[NSString moneyFormat:moneyString]];
    self.aboutLable.text = nil;
    if([model.shortName isEqualToString:@"ETH"])
    {
        [APPControl sharedAPPControl].ETHModel = model;
    }
    self.aboutLable.text = @"更新中...";
    [self _loadTimer];
    
}
- (void)_loadTimer
{
    if (self.timer == nil)
    {
        self.timer = [NSTimer timerWithTimeInterval:0.5 target:self selector:@selector(_refreshData) userInfo:nil repeats:YES];
        [[NSRunLoop currentRunLoop]addTimer:self.timer forMode:NSRunLoopCommonModes];
    }
}
- (void)_removeTimer
{
    if (_timer && _timer.valid)
    {
        [_timer invalidate];
    }
    _timer = nil;
}
- (void)_refreshData
{
    if ([self.model.shortName isEqualToString:@"USDT"])
    {
        double p_new = 1.0f;
        double cny = [[APPControl sharedAPPControl].cny doubleValue];
        double total = self.model.total;
        
        NSString *moneyString = [NSString stringWithFormat:@"%.2f",p_new * cny * total];
        self.aboutLable.text = [NSString stringWithFormat:@"≈ ￥%@",[NSString moneyFormat:moneyString]];
        [self _removeTimer];
        return;
    }
    
    NSArray *array = [APPControl sharedAPPControl].walletNewsArray;
    if (array == nil || array.count == 0) {
        return;
    }
    for (ChZRealCoinInfoModel *coinModel in array)
    {
        
        if ([self.model.shortName isEqualToString:coinModel.sellSymbol])
        {
            double p_new = [coinModel.p_new doubleValue];
            double cny = [[APPControl sharedAPPControl].cny doubleValue];
            double total = self.model.total;
            
            NSString *moneyString = [NSString stringWithFormat:@"%.2f",p_new * cny * total];
            self.aboutLable.text = [NSString stringWithFormat:@"≈ ￥%@",[NSString moneyFormat:moneyString]];
        }
    }
    
}


@end
