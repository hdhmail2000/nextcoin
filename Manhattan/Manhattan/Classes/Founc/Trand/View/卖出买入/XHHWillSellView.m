//
//  XHHWillSellView.m
//  FuturePurse
//
//  Created by Apple on 2018/7/30.
//  Copyright © 2018年 jbtm. All rights reserved.
//

#import "XHHWillSellView.h"
#import "XHHChoosePayTypeController.h"
#import "XHHPTChooseController.h"
@interface XHHWillSellView ()<UITextFieldDelegate>

@property (nonatomic , assign) BOOL isHaveDian;

@property (weak, nonatomic) IBOutlet UIView *presentView;//比例view

@property (strong, nonatomic)  UISlider        *slier;

@property (nonatomic , strong) UIView          *sluderBgView;
@property (weak, nonatomic) IBOutlet UILabel *presentLable;

@property (nonatomic , strong) NSArray *allPayTypes;

@end

@implementation XHHWillSellView


-(void)awakeFromNib
{
    [super awakeFromNib];
    
    self.presentLable.text = @"100%";
    
    [self.payTypeTextFeild configPlaceholderWithFont:[UIFont systemFontOfSize:16] textColor:[UIColor colorWithHexString:@"4B4B80"]];
    [self.currPrice configPlaceholderWithFont:[UIFont systemFontOfSize:16] textColor:[UIColor colorWithHexString:@"4B4B80"]];
    [self.buyNumber configPlaceholderWithFont:[UIFont systemFontOfSize:16] textColor:[UIColor colorWithHexString:@"4B4B80"]];
    [self.minSellNumber configPlaceholderWithFont:[UIFont systemFontOfSize:16] textColor:[UIColor colorWithHexString:@"4B4B80"]];
    [self.maxSellNumber configPlaceholderWithFont:[UIFont systemFontOfSize:16] textColor:[UIColor colorWithHexString:@"4B4B80"]];
    
    self.buyNumber.delegate = self;
    self.minSellNumber.delegate = self;
    self.maxSellNumber.delegate = self;
    self.currPrice.delegate = self;
    
    [self.buyNumber addTarget:self action:@selector(vlueChange) forControlEvents:UIControlEventEditingChanged];
    [self.currPrice addTarget:self action:@selector(vlueChange) forControlEvents:UIControlEventEditingChanged];
    
    [self.presentView addSubview:self.sluderBgView];
    [self.sluderBgView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(self.presentView).offset(0);
        make.bottom.equalTo(self.presentView).offset(-13.5);
        make.right.equalTo(self.presentView).offset(0);
        make.height.mas_equalTo(3);
    }];
    
    [self.presentView addSubview:self.slier];
    [self.slier mas_makeConstraints:^(MASConstraintMaker *make) {
        make.bottom.equalTo(self.presentView);
        make.left.equalTo(self.presentView).offset(-4);
        make.right.equalTo(self.presentView).offset(4);
        make.height.mas_equalTo(30);
    }];
}
-(UISlider *)slier{
    if (!_slier) {
        _slier = [[UISlider alloc] init];
        UIImage *imagea= [UIImage imageNamed:@"trad_detail_proImage"];//[self OriginImage:[UIImage imageNamed:@"trad_detail_proImage"] scaleToSize:CGSizeMake(25, 25)];
        [_slier  setThumbImage:imagea forState:UIControlStateNormal];
        UIImage *rightTrack = [[UIImage imageNamed:@"slidertrack_clear"]
                               resizableImageWithCapInsets:UIEdgeInsetsMake(0, 14, 0, 14)];
        
        [_slier setMaximumTrackImage:rightTrack forState:UIControlStateNormal];
        [_slier setMinimumTrackTintColor:[UIColor clearColor]];
        _slier.minimumValue = 0;
        _slier.maximumValue = 0.1;
        _slier.value = 0;
        _slier.tintColor = [UIColor clearColor];
        _slier.backgroundColor = [UIColor clearColor];
        @weakify(self);
        [[_slier rac_signalForControlEvents:UIControlEventValueChanged] subscribeNext:^(__kindof UIControl * _Nullable x) {
            @strongify(self);
            CGFloat pValue = self.slier.value + 1;
            self.presentLable.text = [NSString stringWithFormat:@"%.2f%%",(float)(pValue * 100)];
            self.currPrice.text = [NSString stringWithFormat:@"%.2f",[self.currPriceString floatValue] * pValue];
            [self vlueChange];
        }];
    }
    return _slier;
}
-(UIView *)sluderBgView{
    if (!_sluderBgView) {
        _sluderBgView = [[UIView alloc] init];
        _sluderBgView.backgroundColor = [UIColor colorWithHexString:@"363672"];
    }
    return _sluderBgView;
}
- (IBAction)allInSell:(id)sender {
    self.buyNumber.text = self.canSellNumber.text;
    [self vlueChange];
}
/*
 // Only override drawRect: if you perform custom drawing.
 // An empty implementation adversely affects performance during animation.
 - (void)drawRect:(CGRect)rect {
 // Drawing code
 }
 */
-(void)vlueChange{
    if ([self.buyNumber.text floatValue] > [self.canSellNumber.text floatValue])
    {
        self.buyNumber.text = self.canSellNumber.text;
        
    }
    if ([self.currPrice.text floatValue] > 0 && [self.buyNumber.text floatValue] > 0) {
        self.willUseMoney.text = [NSString stringWithFormat:@"%.2f CNY",[self.currPrice.text floatValue] * [self.buyNumber.text floatValue]];
    }else{
        self.willUseMoney.text = @"--";
    }
}
-(BOOL)textField:(UITextField *)textField shouldChangeCharactersInRange:(NSRange)range replacementString:(NSString *)string
{
    /*
     * 不能输入.0-9以外的字符。
     * 设置输入框输入的内容格式
     * 只能有一个小数点
     * 小数点后最多能输入两位
     * 如果第一位是.则前面加上0.
     * 如果第一位是0则后面必须输入点，否则不能输入。
     */
    
    // 判断是否有小数点
    if ([textField.text containsString:@"."]) {
        self.isHaveDian = YES;
    }else{
        self.isHaveDian = NO;
    }
    
    if (string.length > 0) {
        
        //当前输入的字符
        unichar single = [string characterAtIndex:0];
        ChZ_DebugLog(@"single = %c",single);
        
        // 不能输入.0-9以外的字符
        if (!((single >= '0' && single <= '9') || single == '.'))
        {
            ChZ_MBError(@"您的输入格式不正确")
            return NO;
        }
        
        // 只能有一个小数点
        if (self.isHaveDian && single == '.') {
            ChZ_MBError(@"最多只能输入一个小数点")
            return NO;
        }
        
        // 如果第一位是.则前面加上0.
        if ((textField.text.length == 0) && (single == '.')) {
            textField.text = @"0";
        }
        
        // 如果第一位是0则后面必须输入点，否则不能输入。
        if ([textField.text hasPrefix:@"0"]) {
            if (textField.text.length > 1) {
                NSString *secondStr = [textField.text substringWithRange:NSMakeRange(1, 1)];
                if (![secondStr isEqualToString:@"."]) {
                    ChZ_MBError(@"第二个字符需要是小数点")
                    return NO;
                }
            }else{
                if (![string isEqualToString:@"."]) {
                    ChZ_MBError(@"第二个字符需要是小数点")
                    return NO;
                }
            }
        }
    }
    
    if (self.isHaveDian)
    {
        NSArray *textArray = [textField.text componentsSeparatedByString:@"."];
        NSString *afterDian = textArray[1];
        if (afterDian.length == 4 && string.length >0)
        {
            return NO;
        }
    }
    return YES;
}
/*
// Only override drawRect: if you perform custom drawing.
// An empty implementation adversely affects performance during animation.
- (void)drawRect:(CGRect)rect {
    // Drawing code
}
*/
- (IBAction)choosePayType:(id)sender {
    XHHChoosePayTypeController *vc = [[XHHChoosePayTypeController alloc] init];
    vc.allPayTypes = [self.allPayTypes mutableCopy];
    @weakify(self);
    vc.payType = ^(NSArray *payTypes) {
        @strongify(self);
        NSString *pString = @"";
        for(int i = 0;i < payTypes.count;i ++)
        {
            if (i == 0) {
                pString = payTypes[0];
            }else{
                pString = [NSString stringWithFormat:@"%@,%@",pString,payTypes[i]];
            }
        }
        self.payTypeTextFeild.text = pString;
        self.allPayTypes = payTypes;
    };
    [[ChZUtil getCurrentVC].navigationController pushViewController:vc animated:YES];
}

- (IBAction)chooseCNYType:(id)sender {
    XHHPTChooseController *vc = [[XHHPTChooseController alloc] init];
    vc.selectedCoinmodel = self.cnyModel;
    @weakify(self);
    vc.chooseCnyBlock = ^(XHHC2CCnyListModel *model)
    {
        @strongify(self);
        self.cnyTypLable.text = model.short_name;
        self.minType.text = model.short_name;
        self.maxType.text = model.short_name;
        self.numType.text = model.short_name;
        self.cnyModel = model;
        
        self.slier.value = 0.0;
        self.buyNumber.text = nil;
        self.minSellNumber.text = nil;
        self.presentLable.text = @"100%";
        
        @weakify(self);
        if (self.cnyBlock)
        {
            @strongify(self);
            self.cnyBlock(model.fid);
        }
        [ChZHomeRequest requestCoinReleasePriceCoinName:model.short_name successBlock:^(id dataSource)
         {
             @strongify(self);
             if(dataSource)
             {
                 ChZ_DebugLog(@"%@",dataSource);
                 self.currPriceString = [NSString stringWithFormat:@"%@",[dataSource objectForKey:@"price"]];
                 self.currPrice.text = [NSString stringWithFormat:@"%@",[dataSource objectForKey:@"price"]];
             }else
             {
                 ChZ_MBError(@"获取币种价格失败");
             }
         } errorBlock:^(NSString *error) {
             ChZ_MBError(@"请求失败");
         }];
    };
    
    vc.cnyList = self.cnyList;
    [[ChZUtil getCurrentVC].navigationController pushViewController:vc animated:YES];
}
- (IBAction)sellAction:(id)sender {
    _payBlock();
}

@end
