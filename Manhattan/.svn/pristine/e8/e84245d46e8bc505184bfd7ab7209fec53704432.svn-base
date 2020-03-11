//
//  ChZMarketListModel.m
//  FuturePurse
//
//  Created by Howe on 2018/8/8.
//  Copyright © 2018年 jbtm. All rights reserved.
//

#import "ChZMarketListModel.h"


@interface ChZMarketListModel()

@property (nonatomic, strong) NSTimer  *timer;

@end

@implementation ChZMarketListModel


- (void)syncCoin
{
    self.timer = [NSTimer timerWithTimeInterval:2 target:self selector:@selector(_refreshData) userInfo:nil repeats:YES];
    
    [[NSRunLoop currentRunLoop]addTimer:self.timer forMode:NSRunLoopCommonModes];
}
- (void)_refreshData
{
    if (!ChZ_StringIsEmpty(self.fid)) {
        return;
    }
    ChZ_Weak
    [ChZHomeRequest requestRealCoin:self.fid successBlock:^(id dataSource)
     {
         ChZ_Strong
         [_strongSelf _handleData:dataSource];
     } errorBlock:^(int code, NSString *error)
     {
     }];
}
- (void)_handleData:(NSArray *)array
{
    if (array == nil || array.count == 0) {
        return;
    }
    ChZRealCoinInfoModel *model = [array firstObject];
    self.model = model;
    
}

- (void)_removeTimer
{
    if (_timer && _timer.valid) {
        [_timer invalidate];
    }
    _timer = nil;
}
- (void)dealloc
{
    
    [self _removeTimer];
}

@end
