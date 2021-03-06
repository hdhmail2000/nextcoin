//
//  XHHEntrustBuyTypeView.m
//  FuturePurse
//
//  Created by Apple on 2018/7/27.
//  Copyright © 2018年 jbtm. All rights reserved.
//

#import "XHHEntrustBuyTypeView.h"
@interface XHHEntrustBuyTypeView()

@property (assign, nonatomic) NSInteger count;

@end

@implementation XHHEntrustBuyTypeView

-(instancetype)initWithItems:(NSArray *)items{
    self = [super init];
    if (self) {
        self.count = items.count;
        for(int i = 0;i < items.count;i ++){
            UIButton *btn = [[UIButton alloc] init];
            [btn setTitle:items[i] forState:UIControlStateNormal];
            [btn setTitleColor:[UIColor colorWithHexString:@"8F8F8F"] forState:UIControlStateNormal];
            if (i== 0) {
                [btn setTitleColor:[UIColor colorWithHexString:@"2394FE"] forState:UIControlStateNormal];
            }
            btn.contentHorizontalAlignment = UIControlContentHorizontalAlignmentLeft;
            [btn.titleLabel setFont:[UIFont systemFontOfSize:14.0]];
            CGFloat width = btn.titleLabel.text.length == 4 ? 70 : 50;
            btn.frame = CGRectMake(16 + i * width, 0, width, 40);
            btn.tag = 200 + i;
            [btn addTarget:self action:@selector(clickAcion:) forControlEvents:UIControlEventTouchUpInside];
            [self addSubview:btn];
        }
    }
    return self;
}
-(void)clickAcion:(UIButton *)sender{
    for(int i= 200;i < 200 +self.count;i ++){
        UIButton *btn = [self viewWithTag:i];
        [btn setTitleColor:[UIColor colorWithHexString:@"8F8F8F"] forState:UIControlStateNormal];
    }
    [sender setTitleColor:[UIColor colorWithHexString:@"2394FE"] forState:UIControlStateNormal];
    
    
    if (_delegate && [_delegate respondsToSelector:@selector(chooseTypeIndex:)]) {
        [_delegate chooseTypeIndex:sender.tag-200];
    }
    
}
-(void)setSeleectIndex:(NSInteger)seleectIndex{
    _seleectIndex = seleectIndex;
    for(int i= 200;i < 200 +self.count;i ++){
        UIButton *btn = [self viewWithTag:i];
        [btn setTitleColor:[UIColor colorWithHexString:@"8F8F8F"] forState:UIControlStateNormal];
    }
    UIButton *btn = [self viewWithTag:200];
    [btn setTitleColor:[UIColor colorWithHexString:@"2394FE"] forState:UIControlStateNormal];
}
/*
// Only override drawRect: if you perform custom drawing.
// An empty implementation adversely affects performance during animation.
- (void)drawRect:(CGRect)rect {
    // Drawing code
}
*/

@end
