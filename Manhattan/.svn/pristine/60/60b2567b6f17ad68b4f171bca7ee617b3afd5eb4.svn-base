//
//  XHHMiningAreaCell.m
//  Manhattan
//
//  Created by Apple on 2018/8/17.
//  Copyright © 2018年 Apple. All rights reserved.
//

#import "XHHMiningAreaCell.h"
@interface XHHMiningAreaCell ()

@property (weak, nonatomic) IBOutlet UILabel *title;
@property (weak, nonatomic) IBOutlet UIButton *typeButton;
@property (weak, nonatomic) IBOutlet UILabel *allMoney;
@property (weak, nonatomic) IBOutlet UILabel *allMonetTitle;
@property (weak, nonatomic) IBOutlet UIProgressView *pregress;
@property (weak, nonatomic) IBOutlet UILabel *doMoney;
@property (weak, nonatomic) IBOutlet UILabel *doMoneyTitle;
@property (weak, nonatomic) IBOutlet UILabel *monthTitle;
@property (weak, nonatomic) IBOutlet UILabel *monthAdd;
@property (weak, nonatomic) IBOutlet UIButton *inputButton;
@property (weak, nonatomic) IBOutlet UIButton *oneKeyInputButton;

@end

#define noOnColor [UIColor colorWithHexString:@"6363A7"]

@implementation XHHMiningAreaCell

- (void)awakeFromNib {
    [super awakeFromNib];
    self.backgroundColor = [UIColor clearColor];
    
    // Initialization code
    //如果是未激活状态下颜色显示

}

- (void)setSelected:(BOOL)selected animated:(BOOL)animated {
    [super setSelected:selected animated:animated];

    // Configure the view for the selected state
}
-(void)setModel:(XHHMiningAreaModle *)model{
    _model = model;
    
    if (self.index == 0)//MHT矿区
    {
        _title.text = @"MHT-X矿区";
//        if ([model.mhtx integerValue] > 0)
//        {
            _allMoney.text = @"20 ";
            _allMonetTitle.text = @"万MHT-X";
            _allMonetTitle.textColor = ChZ_Color(188, 190, 196);
            _doMoney.text = [NSString stringWithFormat:@"%.5f",[model.mhtx floatValue] /10000];
            _pregress.progress = [model.mhtx floatValue]/200000;
            _monthAdd.text = [NSString stringWithFormat:@"%.2f%%",[model.mhtxmor floatValue]*100];
            _oneKeyInputButton.userInteractionEnabled = YES;
            _oneKeyInputButton.selected = [model.is_onekey_mhtx integerValue] == 0 ? YES : NO;
            [_typeButton setBackgroundImage:[UIImage imageNamed:@"mining_area_typing"] forState:UIControlStateNormal];
            if ([model.mhtx floatValue] == 200000)
            {
                [_typeButton setTitle:@"完全投入" forState:UIControlStateNormal];
            }else{
                [_typeButton setTitle:@"投入中" forState:UIControlStateNormal];
            }
            [_typeButton setTitleColor:[UIColor whiteColor] forState:UIControlStateNormal];
            [_inputButton setBackgroundColor:[UIColor colorWithHexString:@"308AF5"]];
            [_inputButton setTitleColor:[UIColor whiteColor] forState:UIControlStateNormal];
            _inputButton.userInteractionEnabled = YES;
//        }else
//        {
//            [self setMiningUnUse];
//        }
    }else//USDT矿区
    {
        _title.text = @"USDT矿区";
        if ([model.usdt integerValue] > 0 || [model.is_complete_usdt integerValue] == 1)
        {
            _allMoney.text = @"20 ";
            _allMonetTitle.text = @"万USDT";
            _allMonetTitle.textColor = ChZ_Color(188, 190, 196);
            _doMoney.text = [NSString stringWithFormat:@"%.5f",[model.usdt floatValue] /10000];
            _pregress.progress = [model.usdt floatValue]/200000;
            _monthAdd.text = [NSString stringWithFormat:@"%.2f%%",[model.usdtmor floatValue]*100];
            _oneKeyInputButton.userInteractionEnabled = YES;
            _oneKeyInputButton.selected = [model.is_onekey_usdt integerValue] == 0 ? YES : NO;
            [_typeButton setBackgroundImage:[UIImage imageNamed:@"mining_area_typing"] forState:UIControlStateNormal];
            if ([model.usdt floatValue] == 200000)
            {
                [_typeButton setTitle:@"完全投入" forState:UIControlStateNormal];
            }else{
                [_typeButton setTitle:@"投入中" forState:UIControlStateNormal];
            }
            [_typeButton setTitleColor:[UIColor whiteColor] forState:UIControlStateNormal];
            [_inputButton setBackgroundColor:[UIColor colorWithHexString:@"308AF5"]];
            [_inputButton setTitleColor:[UIColor whiteColor] forState:UIControlStateNormal];
            _inputButton.userInteractionEnabled = YES;
        }else
        {
            [self setMiningUnUse];
        }
    }
}
-(void)setMiningUnUse{
    _allMoney.text = nil;
    _allMonetTitle.text = @"不限MHT-X额度";
    _allMonetTitle.textColor = noOnColor;
    _doMoney.textColor = noOnColor;
    _doMoney.text = @"0";
    _doMoneyTitle.textColor = noOnColor;
    _pregress.progress = 0;
    _monthTitle.textColor = noOnColor;
    _monthAdd.textColor = noOnColor;
    _monthAdd.text = @"0%";
    [_typeButton setTitle:@"未激活" forState:UIControlStateNormal];
    [_typeButton setTitleColor:ChZ_ColorRGBA(255, 255, 255, 0.5) forState:UIControlStateNormal];
    [_typeButton setBackgroundImage:[UIImage imageNamed:@"mining_area_typnoing"] forState:UIControlStateNormal];
    [_inputButton setTitleColor:[UIColor colorWithHexString:@"A5A5C0"] forState:UIControlStateNormal];
    [_inputButton setBackgroundColor:[UIColor colorWithHexString:@"4B4B80"]];
    _oneKeyInputButton.selected = YES;
    _oneKeyInputButton.userInteractionEnabled = NO;
//    _inputButton.userInteractionEnabled = NO;
}
- (IBAction)oneKeyAction:(UIButton *)sender {
    
    
    if (self.oneKeyBlock)
    {
        self.oneKeyBlock(self.index, self.model);
    }
}
- (IBAction)inputAction:(id)sender
{
    if(self.block)
    {
        self.block(self.index, self.model, 1);
    }
}

@end
