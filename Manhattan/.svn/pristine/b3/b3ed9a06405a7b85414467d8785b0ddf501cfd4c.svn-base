//
//  XHHNacTitleChooseView.m
//  Manhattan
//
//  Created by Apple on 2018/9/10.
//  Copyright © 2018年 Apple. All rights reserved.
//

#import "XHHNacTitleChooseView.h"
@interface XHHNacTitleChooseView()

@property (weak, nonatomic) IBOutlet UILabel *titleLable;

@end

@implementation XHHNacTitleChooseView

/*
// Only override drawRect: if you perform custom drawing.
// An empty implementation adversely affects performance during animation.
- (void)drawRect:(CGRect)rect {
    // Drawing code
}
*/
- (IBAction)back:(id)sender
{
    if (self.backBlock)
    {
        self.backBlock();
    }
}
- (IBAction)itemAction:(id)sender
{
    if (self.itemBlock)
    {
        self.itemBlock();
    }
}
-(void)setTitle:(NSString *)title{
    _title = title;
    self.titleLable.text = title;
}

@end
