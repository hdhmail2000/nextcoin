//
//  XHHSalesEntrustView.m
//  FuturePurse
//
//  Created by Apple on 2018/7/27.
//  Copyright © 2018年 jbtm. All rights reserved.
//

#import "XHHSalesEntrustView.h"
#import "XHHProgressView.h"
@interface XHHSalesEntrustView ()<UITextFieldDelegate>

@property (weak, nonatomic) IBOutlet UILabel *presentLable;//比例
@property (weak, nonatomic) IBOutlet UIView *presentView;//比例view
@property (weak, nonatomic) IBOutlet UILabel *moneyLable;//交易额

@property (weak, nonatomic) IBOutlet UIButton *buyButton;
@property (weak, nonatomic) IBOutlet UIButton *sellButton;


@property (strong, nonatomic) UISlider        *slier;

@property (nonatomic , strong) UIView *blueView;
@property (nonatomic , strong) UIView *grayView;

@property (nonatomic , assign) BOOL isHaveDian;

@property (nonatomic, assign) double canUseMoney;

@property (assign , nonatomic) BOOL isBuy;

@end

@implementation XHHSalesEntrustView

/*
// Only override drawRect: if you perform custom drawing.
// An empty implementation adversely affects performance during animation.
- (void)drawRect:(CGRect)rect {
    // Drawing code
}
*/
-(void)awakeFromNib{
    [super awakeFromNib];
    self.isBuy = YES;
    [self zh_setupUI];
}

-(void)zh_setupUI{
    [self.muchTextFeild configPlaceholderWithFont:[UIFont systemFontOfSize:16] textColor:[UIColor colorWithHexString:@"4B4B80"]];
    [self.priceTextFeild configPlaceholderWithFont:[UIFont systemFontOfSize:16] textColor:[UIColor colorWithHexString:@"4B4B80"]];
    [self.presentView addSubview:self.grayView];
    [self.grayView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.bottom.equalTo(self.presentView);
        make.height.mas_equalTo(30);
        make.left.equalTo(self.presentView).offset(5);
        make.right.equalTo(self.presentView).offset(-15);
    }];
    [self.presentView addSubview:self.blueView];
    [self.blueView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.bottom.equalTo(self.presentView);
        make.left.equalTo(self.presentView).offset(5);
        make.height.mas_equalTo(30);
        make.width.mas_equalTo(0);
    }];
    
    
    [self.presentView addSubview:self.slier];
    [self.slier mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.bottom.right.equalTo(self.presentView);
        make.height.mas_equalTo(30);
    }];
    
    [self.muchTextFeild addTarget:self action:@selector(valueChange:) forControlEvents:UIControlEventEditingChanged];
    [self.priceTextFeild addTarget:self action:@selector(valueChange:) forControlEvents:UIControlEventEditingChanged];
    self.muchTextFeild.delegate = self;
    self.priceTextFeild.delegate = self;
}
-(void)setProgressVlalue:(double)progressVlalue{
    
    self.canUseMoney = progressVlalue;
}
-(void)valueChange:(UITextField *)textField{
    
    if (textField == self.muchTextFeild)
    {
        if ([self.priceTextFeild.text floatValue] == 0)
        {
            ChZ_MBError(@"请输入价格");
            self.muchTextFeild.text = @"";
            return;
        }
    }
    
    
    if (self.isBuy)
    {
        self.canUseMoney= [self.buyMoney floatValue]/[self.priceTextFeild.text floatValue];
    }else
    {
        self.canUseMoney= [self.sellMoney floatValue];
    }
    if ([self.muchTextFeild.text doubleValue] > self.canUseMoney) {
        self.slier.value = 1;
        self.muchTextFeild.text = [NSString stringWithFormat:@"%.4f",self.canUseMoney];
        [self.blueView mas_updateConstraints:^(MASConstraintMaker *make) {
            make.width.mas_equalTo((ChZ_WIDTH-50)*self.slier.value);
        }];
        self.presentLable.text = [NSString stringWithFormat:@"%d%%",(int)(_slier.value * 100)];
    }else{
        self.slier.value = [self.muchTextFeild.text doubleValue]/self.canUseMoney;
        self.presentLable.text = [NSString stringWithFormat:@"%d%%",(int)(_slier.value * 100)];
        [self.blueView mas_updateConstraints:^(MASConstraintMaker *make) {
            make.width.mas_equalTo((ChZ_WIDTH-50)*self.slier.value);
        }];
    }
    
    if ([self.muchTextFeild.text length] > 0 && [self.priceTextFeild.text length] > 0)
    {
        self.moneyLable.text = [NSString stringWithFormat:@"%.4f%@",[self.muchTextFeild.text floatValue] * [self.priceTextFeild.text floatValue],self.typeLable.text];
    }else{
        self.moneyLable.text = @"--";
    }
}
- (BOOL)textField:(UITextField *)textField shouldChangeCharactersInRange:(NSRange)range replacementString:(NSString *)string
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
        if (self.isHaveDian)
        {
            NSArray *textArray = [textField.text componentsSeparatedByString:@"."];
            NSString *afterDian = textArray[1];
            if (afterDian.length == 4 && string.length >0)
            {
                return NO;
            }
        }
//        // 小数点后最多能输入两位
//        if (self.isHaveDian) {
//            NSRange ran = [textField.text rangeOfString:@"."];
//            // 由于range.location是NSUInteger类型的，所以这里不能通过(range.location - ran.location)>2来判断
//            if (!ChZ_StringIsEmpty(self.model.digit))
//            {
//                return YES;
//            }
//            NSArray *array = [self.model.digit componentsSeparatedByString:@"#"];
//            if (array == nil || array.count !=2)
//            {
//                return YES;
//            }
//            NSString *constraintPriceString = [array firstObject];
//            NSString *constraintNumberString = [array lastObject];
//            int constraintPrice = [constraintPriceString intValue];
//            int constraintNumber = [constraintNumberString intValue];
//            if (range.location > ran.location) {
//                if ((self.muchTextFeild == textField || self.priceTextFeild == textField) && [self.priceTextFeild.text pathExtension].length > constraintPrice -1)
//                {
//                    NSString *msg = [NSString stringWithFormat:@"委托价格最多有%@位小数",constraintPriceString];
//                    ChZ_MBError(msg)
//                    return NO;
//                }
//            }
//        }
    }
    return YES;
}
-(UISlider *)slier{
    if (!_slier) {
        _slier = [[UISlider alloc] init];
        UIImage *imagea=[UIImage imageNamed:@"trad_detail_proImage"];//[self OriginImage:[UIImage imageNamed:@"trad_detail_proImage"] scaleToSize:CGSizeMake(25, 25)];
        [_slier  setThumbImage:imagea forState:UIControlStateNormal];
        UIImage *rightTrack = [[UIImage imageNamed:@"slidertrack_clear"]
                               resizableImageWithCapInsets:UIEdgeInsetsMake(0, 14, 0, 14)];
        
        [_slier setMaximumTrackImage:rightTrack forState:UIControlStateNormal];
        [_slier setMinimumTrackTintColor:[UIColor clearColor]];
        _slier.minimumValue = 0;
        _slier.maximumValue = 1;
        _slier.tintColor = [UIColor clearColor];
        _slier.backgroundColor = [UIColor clearColor];
        @weakify(self);
        [[_slier rac_signalForControlEvents:UIControlEventValueChanged] subscribeNext:^(__kindof UIControl * _Nullable x) {
            @strongify(self);
            if ([self.priceTextFeild.text floatValue] == 0)
            {
                ChZ_MBError(@"请输入价格")
                return;
            }
            if (self.isBuy)
            {
                self.canUseMoney= [self.buyMoney floatValue]/[self.priceTextFeild.text floatValue];
            }else
            {
                self.canUseMoney= [self.sellMoney floatValue];
            }
            
            
            self.presentLable.text = [NSString stringWithFormat:@"%d%%",(int)(self.slier.value * 100)];
            self.muchTextFeild.text = [NSString stringWithFormat:@"%.4f",self.slier.value * self.canUseMoney];
            
            NSLog(@"%@----%@",self.muchTextFeild.text,self.buyMoney);
            
            
            if ([self.muchTextFeild.text length] > 0 && [self.priceTextFeild.text length] > 0)
            {
                self.moneyLable.text = [NSString stringWithFormat:@"%.4f%@",[self.muchTextFeild.text floatValue] * [self.priceTextFeild.text floatValue],self.typeLable.text];
            }else
            {
                self.moneyLable.text = @"--";
            }
            [self.blueView mas_updateConstraints:^(MASConstraintMaker *make) {
                make.width.mas_equalTo((ChZ_WIDTH-50)*self.slier.value);
            }];
        }];
    }
    return _slier;
}
-(UIView *)blueView{
    if (!_blueView) {
        _blueView = [[UIView alloc] init];
        _blueView.backgroundColor = [UIColor clearColor];
        _blueView.layer.masksToBounds = YES;
        UIImageView *imageView = [[UIImageView alloc] init];
        imageView.image = [UIImage imageNamed:@"slidertrack_blue"];
        imageView.contentMode = UIViewContentModeScaleAspectFit;
        imageView.frame = CGRectMake(0, 0, ChZ_WIDTH - 45, 30);
        [_blueView addSubview:imageView];
    }
    return _blueView;
}

-(UIView *)grayView{
    if (!_grayView) {
        _grayView = [[UIView alloc] init];
        UIImageView *imageView = [[UIImageView alloc] init];
        imageView.image = [UIImage imageNamed:@"slidertrack_gray"];
        imageView.contentMode = UIViewContentModeScaleAspectFit;
        imageView.frame = CGRectMake(0, 0, ChZ_WIDTH - 45, 30);
        [_grayView addSubview:imageView];
    }
    return _grayView;
}
- (IBAction)buyAction:(id)sender {
    self.buyButton.layer.borderColor = [UIColor colorWithHexString:@"308AF5"].CGColor;
    [self.buyButton setTitleColor:[UIColor colorWithHexString:@"308AF5"] forState:UIControlStateNormal];
    self.sellButton.layer.borderColor = [UIColor colorWithHexString:@"4B4B80"].CGColor;
    [self.sellButton setTitleColor:[UIColor colorWithHexString:@"4B4B80"] forState:UIControlStateNormal];
    if (self.buySellBlock)
    {
        _buySellBlock(NO);
    }
    self.isBuy = YES;
    [self clearInputData];
}
- (IBAction)sellAction:(id)sender {
    self.sellButton.layer.borderColor = [UIColor colorWithHexString:@"308AF5"].CGColor;
    [self.sellButton setTitleColor:[UIColor colorWithHexString:@"308AF5"] forState:UIControlStateNormal];
    self.buyButton.layer.borderColor = [UIColor colorWithHexString:@"4B4B80"].CGColor;
    [self.buyButton setTitleColor:[UIColor colorWithHexString:@"4B4B80"] forState:UIControlStateNormal];
    if (self.buySellBlock)
    {
        _buySellBlock(YES);
    }
    self.isBuy = NO;
    [self clearInputData];
}
-(void)clearInputData{
    self.priceTextFeild.text = @"";
    self.muchTextFeild.text = @"";
    self.slier.value = 0;
    self.moneyLable.text = @"--";
    self.presentLable.text = [NSString stringWithFormat:@"%d%%",(int)(_slier.value * 100)];
    [self.blueView mas_updateConstraints:^(MASConstraintMaker *make) {
        make.width.mas_equalTo((ChZ_WIDTH-50)*self.slier.value);
    }];
}
-(UIImage *)OriginImage:(UIImage *)image scaleToSize:(CGSize)size
{
    UIGraphicsBeginImageContext(size);
    [image drawInRect:CGRectMake(0,0, size.width, size.height)];
    UIImage *scaleImage=UIGraphicsGetImageFromCurrentImageContext();
    UIGraphicsEndImageContext();
    return scaleImage;
}
@end
