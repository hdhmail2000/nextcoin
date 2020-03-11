//
//  XHHPublicNavView.m
//  Manhattan
//
//  Created by Apple on 2018/8/15.
//  Copyright © 2018年 Apple. All rights reserved.
//

#import "XHHPublicNavView.h"

@interface XHHPublicNavView()

@property (weak, nonatomic) IBOutlet UIButton *backButton;
@property (weak, nonatomic) IBOutlet UIButton *rightButton;
@property (weak, nonatomic) IBOutlet UIButton *rightTwoButton;

@end

@implementation XHHPublicNavView

/*
// Only override drawRect: if you perform custom drawing.
// An empty implementation adversely affects performance during animation.
- (void)drawRect:(CGRect)rect {
    // Drawing code
}
*/
- (IBAction)back:(id)sender {
    [self.viewController.navigationController popViewControllerAnimated:YES];
}

-(void)setLeftButtonTitle:(NSString *)title image:(NSString *)image{
    [self.backButton setTitleColor:[UIColor whiteColor] forState:(UIControlState)UIControlStateNormal];
    [self.backButton setTitle:title forState:UIControlStateNormal];
    if (image) {
        [self.backButton setImage:[UIImage imageNamed:image] forState:UIControlStateNormal];
    }
}
-(void)setrightButtonTitles:(NSArray *)titles images:(NSArray *)images{
    self.rightButton.hidden = NO;
    self.rightTwoButton.hidden = NO;
    if (titles) {
        if (titles.count == 2)
        {
            [self.rightButton setTitle:titles[0] forState:UIControlStateNormal];
            [self.rightTwoButton setTitle:titles[1] forState:UIControlStateNormal];
        }else{
            [self.rightButton setTitle:titles[0] forState:UIControlStateNormal];
        }
    }
    if (images)
    {
        if (images.count == 2) {
            [self.rightButton setImage:[UIImage imageNamed:images[0]] forState:UIControlStateNormal];
            [self.rightTwoButton setImage:[UIImage imageNamed:images[1]] forState:UIControlStateNormal];
        }else{
            [self.rightButton setImage:[UIImage imageNamed:images[0]] forState:UIControlStateNormal];
        }
    }
    
    if (!titles && !images)
    {
        self.rightButton.hidden = YES;
        self.rightTwoButton.hidden = YES;
    }
    
}

- (IBAction)oneAction:(id)sender {
    if (_delegete && [_delegete respondsToSelector:@selector(rightButtonActionIndex:)]) {
        [_delegete rightButtonActionIndex:1];
    }
    
}

- (IBAction)twoAction:(id)sender {
    if (_delegete && [_delegete respondsToSelector:@selector(rightButtonActionIndex:)]) {
        [_delegete rightButtonActionIndex:2];
    }
}


@end
