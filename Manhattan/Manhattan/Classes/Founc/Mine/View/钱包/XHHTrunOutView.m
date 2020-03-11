//
//  XHHTrunOutView.m
//  Manhattan
//
//  Created by Apple on 2018/8/17.
//  Copyright © 2018年 Apple. All rights reserved.
//

#import "XHHTrunOutView.h"
#import "XHHAreaListController.h"
@interface XHHTrunOutView ()



@end

@implementation XHHTrunOutView

- (void)awakeFromNib{
    [super awakeFromNib];
    self.backgroundColor = [UIColor clearColor];
    [_area configPlaceholderWithFont:[UIFont systemFontOfSize:16.0] textColor:PlaceholderColor];
    
    [self.money addTarget:self action:@selector(valueChange:) forControlEvents:UIControlEventEditingChanged];
}
-(void)valueChange:(UITextField *)textFeild{
    if ([textFeild.text floatValue] > [self.coinHadMoney floatValue]) {
        textFeild.text = self.coinHadMoney;
    }
    self.realMoney.text = [NSString stringWithFormat:@"实际到账 %.4f",[textFeild.text floatValue]*(1 - self.withdrawFee)];
}
- (IBAction)allIn:(id)sender
{
    self.money.text = self.coinHadMoney;
    self.realMoney.text = [NSString stringWithFormat:@"实际到账 %.4f",[self.money.text floatValue]*(1 - self.withdrawFee)];
}
/*
// Only override drawRect: if you perform custom drawing.
// An empty implementation adversely affects performance during animation.
- (void)drawRect:(CGRect)rect {
    // Drawing code
}
*/
- (IBAction)areaListAction:(id)sender {
    
    if (self.selectAreaBlock)
    {
        self.selectAreaBlock();
    }
    
    
}

- (IBAction)sendAction:(id)sender {
    NSLog(@"2131");
    
    if (self.sendBlock)
    {
        _sendBlock();
    }
}

@end
