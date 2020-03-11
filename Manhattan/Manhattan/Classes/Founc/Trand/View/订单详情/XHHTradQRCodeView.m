//
//  XHHTradQRCodeView.m
//  FuturePurse
//
//  Created by Apple on 2018/7/30.
//  Copyright © 2018年 jbtm. All rights reserved.
//

#import "XHHTradQRCodeView.h"

@interface XHHTradQRCodeView ()




@end

@implementation XHHTradQRCodeView

/*
// Only override drawRect: if you perform custom drawing.
// An empty implementation adversely affects performance during animation.
- (void)drawRect:(CGRect)rect {
    // Drawing code
}
*/
- (IBAction)closeAction:(id)sender {
    [self dismiss];
}

-(void)showInView:(UIView *)view{
    [view addSubview:self];
    self.frame = CGRectMake(0, 0, ChZ_WIDTH, ChZ_HEIGHT);
}
-(void)dismiss{
    [UIView animateWithDuration:0.5 animations:^{
        self.alpha = 0;
    } completion:^(BOOL finished) {
        self.alpha = 1;
        [self removeFromSuperview];
    }];
}

@end
