//
//  XHHTradBottomBuyView.m
//  FuturePurse
//
//  Created by Apple on 2018/7/26.
//  Copyright © 2018年 jbtm. All rights reserved.
//

#import "XHHTradBottomBuyView.h"
#import "XHHTradBuyOrSellController.h"
@interface XHHTradBottomBuyView ()

@property (weak, nonatomic) IBOutlet UIImageView *addImage;
@property (weak, nonatomic) IBOutlet UILabel *addTitle;
@property (weak, nonatomic) IBOutlet UIButton *collectButton;

@end


@implementation XHHTradBottomBuyView

/*
// Only override drawRect: if you perform custom drawing.
// An empty implementation adversely affects performance during animation.
- (void)drawRect:(CGRect)rect {
    // Drawing code
}
*/
- (IBAction)buyAction:(id)sender {
    XHHTradBuyOrSellController *vc = [[XHHTradBuyOrSellController alloc] init];
//    vc.type = 1;
    [self.viewController.navigationController pushViewController:vc animated:YES];
}
- (IBAction)sellAction:(id)sender {
    XHHTradBuyOrSellController *vc = [[XHHTradBuyOrSellController alloc] init];
//    vc.type = 2;
    [self.viewController.navigationController pushViewController:vc animated:YES];
}
- (IBAction)addAction:(UIButton *)sender
{
    if (self.collectBlock)
    {
        self.collectBlock();
    }
}
-(void)setIsCollect:(BOOL)isCollect{
    _isCollect = isCollect;
    self.collectButton.selected = isCollect;
    if (isCollect) {
        _addImage.image = [UIImage imageNamed:@"trad_explict_addselected"];
        _addTitle.text = @"已选";
        _addTitle.textColor = [UIColor colorWithHexString:@"308AF5"];
    }else{
        _addImage.image = [UIImage imageNamed:@"trad_explict_addSelectedNoemal"];
        _addTitle.text = @"添加自选";
        _addTitle.textColor = [UIColor colorWithHexString:@"4B4B80"];
    }
}
- (IBAction)buySellAction:(id)sender {
    [[NSNotificationCenter defaultCenter] postNotificationName:@"BUYSELLACTION" object:nil];
}

@end
