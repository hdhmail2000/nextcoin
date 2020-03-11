//
//  XHHC2CItemView.m
//  Manhattan
//
//  Created by Apple on 2018/9/19.
//  Copyright © 2018年 Apple. All rights reserved.
//

#import "XHHC2CItemView.h"



@interface XHHC2CItemView ()

@property (strong, nonatomic) UIView  *lineView;

@property (strong, nonatomic) NSArray  *items;

@end
@implementation XHHC2CItemView

/*
// Only override drawRect: if you perform custom drawing.
// An empty implementation adversely affects performance during animation.
- (void)drawRect:(CGRect)rect {
    // Drawing code
}
*/
-(void)zh_setupUIWithSpaceItems:(NSArray *)items{
    
    self.items = items;
    for (NSInteger i = 0; i < items.count; i++) {
        UIButton *btn = [UIButton buttonWithType:UIButtonTypeCustom];
        [btn setTitle:items[i] forState:UIControlStateNormal];
        [btn setTag:i + 100];
        if (i == 0) {
            [btn setTitleColor:[UIColor colorWithHexString:@"308AF5"] forState:UIControlStateNormal];
        }else{
            [btn setTitleColor:[UIColor colorWithHexString:@"4B4B80"] forState:UIControlStateNormal];
        }
        
        [btn.titleLabel setFont:[UIFont systemFontOfSize:15.0]];
        btn.contentHorizontalAlignment = UIControlContentHorizontalAlignmentLeft;
        [btn setFrame:CGRectMake(i*50 + 16, 0, 50, 44)];
        [btn addTarget:self action:@selector(chooseTypeSpaseAction:) forControlEvents:UIControlEventTouchUpInside];
        [self addSubview:btn];
    }
    
    
    self.lineView = [[UIView alloc] init];
    self.lineView.frame = CGRectMake(16, 42, [items[0] length]*[self itemWitdh:items[0]], 2);
    self.lineView.backgroundColor = [UIColor colorWithHexString:@"2E7AF1"];
    [self addSubview:self.lineView];
    
    UIView *grayView = [[UIView alloc] init];
    grayView.alpha = 0.1;
    grayView.backgroundColor = [UIColor colorWithHexString:@"FFFFFF"];
    [self addSubview:grayView];
    [grayView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.bottom.right.equalTo(self);
        make.height.mas_equalTo(1);
    }];
}

-(void)chooseTypeSpaseAction:(UIButton *)sender{
    for(NSInteger i = 100; i < 100 +self.items.count;i++){
        
        UIButton *btn = (UIButton *)[self viewWithTag:i];
        [btn setTitleColor:[UIColor colorWithHexString:@"4B4B80"] forState:UIControlStateNormal];
    }
    [sender setTitleColor:[UIColor colorWithHexString:@"308AF5"] forState:UIControlStateNormal];
    [self moveLineViewToX:sender.x witdh:[self.items[sender.tag - 100] length]*[self itemWitdh:self.items[sender.tag - 100]] ];
    
    if (_delegate && [_delegate respondsToSelector:@selector(itemActionIndex:)]) {
        [_delegate itemActionIndex:sender.tag - 100];
    }
}

-(void)setItemSelectedIndex:(NSInteger)index{
    for(NSInteger i = 100; i < 100 +self.items.count;i++){
        
        UIButton *btn = (UIButton *)[self viewWithTag:i];
        [btn setTitleColor:[UIColor colorWithHexString:@"4B4B80"] forState:UIControlStateNormal];
    }
    UIButton *sender = [self viewWithTag:index+100];
    [sender setTitleColor:[UIColor colorWithHexString:@"308AF5"] forState:UIControlStateNormal];
    [self moveLineViewToX:sender.centerX - [self.items[index] length]*[self itemWitdh:self.items[index]]/2 witdh:[self.items[index] length]*[self itemWitdh:self.items[index]] ];
}
-(void)moveLineViewToX:(CGFloat)x witdh:(CGFloat)witdh{
    [UIView animateWithDuration:0.5 animations:^{
        self.lineView.x = x;
        self.lineView.width = witdh;
    }];
}
-(CGFloat)itemWitdh:(NSString *)title{
    NSString *match = @"(^[\u4e00-\u9fa5]+$)";
    NSPredicate *predicate = [NSPredicate predicateWithFormat:@"SELF matches %@", match];
    CGFloat witdh;
    if ([predicate evaluateWithObject:title]) {
        witdh = 15;
    }else{
        witdh = 10;
    }
    return witdh;
}
-(void)setlineViewHiddle{
    self.lineView.hidden = YES;
}
@end
