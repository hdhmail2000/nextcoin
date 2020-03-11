//
//  XHHPayPwView.m
//  FuturePurse
//
//  Created by Apple on 2018/7/24.
//  Copyright © 2018年 jbtm. All rights reserved.
//

#import "XHHPayPwView.h"
#import "XHHMineFrogetPswViewController.h"
@interface XHHPayPwView()

@property (weak, nonatomic) IBOutlet UITextField *pwInputTextField;

@property (weak, nonatomic) IBOutlet UIButton *grayButton;

@end

@implementation XHHPayPwView

/*
// Only override drawRect: if you perform custom drawing.
// An empty implementation adversely affects performance during animation.
- (void)drawRect:(CGRect)rect {
    // Drawing code
}
*/

- (void)awakeFromNib{
    [super awakeFromNib];
    [self.pwInputTextField configPlaceholderWithFont:[UIFont systemFontOfSize:16.0] textColor:PlaceholderColor];
}
- (IBAction)closeAction:(id)sender {
    [self dismiss];
}
- (IBAction)forgetPsAction:(id)sender {
    XHHMineFrogetPswViewController *vc = [XHHMineFrogetPswViewController initWithStoryboard:@"MineStroy"];
    [self.viewController.navigationController pushViewController:vc animated:YES];
}
- (IBAction)sureAction:(id)sender {
    
    if (_delegate && [_delegate respondsToSelector:@selector(payViewSure:)])
    {
        [_delegate payViewSure:_pwInputTextField.text];
        [self dismiss];
    }
}


-(void)showInView:(UIView *)view{
    _pwInputTextField.secureTextEntry = YES;
    CGFloat navHeight = ChZ_IsiPhoneX ? 88 : 64;
//    [[UIApplication sharedApplication].keyWindow addSubview:self];
    [view addSubview:self];
    self.frame = CGRectMake(0, ChZ_HEIGHT, ChZ_WIDTH, ChZ_HEIGHT-navHeight);

//    self.frame = CGRectMake(-ChZ_WIDTH, 0, ChZ_WIDTH, ChZ_HEIGHT);
    [UIView animateWithDuration:0.5 animations:^{
        self.y = 0;
    } completion:^(BOOL finished) {
        self.grayButton.hidden = NO;
    }];
}
-(void)dismiss{
    self.pwInputTextField.text = @"";
    [UIView animateWithDuration:0.5 animations:^{
        self.alpha = 0;
        self.y = ChZ_HEIGHT;
        self.grayButton.hidden = YES;
    } completion:^(BOOL finished) {
        self.alpha = 1;
        [self removeFromSuperview];
    }];
}

@end
