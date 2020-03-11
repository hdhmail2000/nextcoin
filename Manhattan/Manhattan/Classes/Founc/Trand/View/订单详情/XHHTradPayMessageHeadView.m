//
//  XHHTradPayMessageHeadView.m
//  FuturePurse
//
//  Created by Apple on 2018/7/30.
//  Copyright © 2018年 jbtm. All rights reserved.
//

#import "XHHTradPayMessageHeadView.h"

#import "ChZUserPayAccountModel.h"
@interface XHHTradPayMessageHeadView ()

@property (weak, nonatomic) IBOutlet UILabel *banLable;//银行名称

@property (weak, nonatomic) IBOutlet UILabel *banFromLable;//开户银行

@property (weak, nonatomic) IBOutlet UILabel *nameLable;//开户名

@property (weak, nonatomic) IBOutlet UILabel *numberLable;//开户账号

@end


@implementation XHHTradPayMessageHeadView

/*
// Only override drawRect: if you perform custom drawing.
// An empty implementation adversely affects performance during animation.
- (void)drawRect:(CGRect)rect {
    // Drawing code
}
*/

- (void)setModel:(ChZUserPayBankAccountModel *)model{
    _model = model;
    self.banLable.text = nil;
    self.banFromLable.text = nil;
    self.nameLable.text = nil;
    self.numberLable.text = nil;
    if(ChZ_StringIsEmpty(model.khh))self.banLable.text = model.khh;
    if(ChZ_StringIsEmpty(model.khm))self.banFromLable.text = model.khm;
    if(ChZ_StringIsEmpty(model.author))self.nameLable.text = model.author;
    if(ChZ_StringIsEmpty(model.zhhm))self.numberLable.text = model.zhhm;
}

@end
