//
//  MYHelpListHeaderView.m
//  CoinWallet
//
//  Created by Howe on 2018/8/1.
//  Copyright © 2018年 Howe. All rights reserved.
//

#import "MYHelpListHeaderView.h"
@interface MYHelpListHeaderView()
//@property (weak, nonatomic)  UIImageView *iconImageView;
//@property (weak, nonatomic)  UILabel *nameLabel;
//@property (nonatomic, weak) UIButton *tapBtn;
//@property (nonatomic, weak) UIView *lineView;
@property (weak, nonatomic) IBOutlet UILabel *nameLabel;
//@property (weak, nonatomic) IBOutlet UIButton *tapBtn;
@property (weak, nonatomic) IBOutlet UIImageView *iconImageView;

@property (weak, nonatomic) IBOutlet UIView *lineView;

@end

@implementation MYHelpListHeaderView
- (instancetype)initWithReuseIdentifier:(NSString *)reuseIdentifier
{
    self = [super initWithReuseIdentifier:reuseIdentifier];
    if (self) {
//        [self _setupView];
    }
    return self;
}
//- (void)_setupView
//{
////    self.tintColor = [UIColor clearColor];
//    UIView *bgView = [UIView new];
//    bgView.backgroundColor = [UIColor clearColor];;
//    self.backgroundView = bgView;
////    self.contentView.backgroundColor = [UIColor clearColor];
////    self.backgroundColor = [UIColor clearColor];
//    UILabel *nameLabel = [[UILabel alloc]init];
//    nameLabel.textAlignment = NSTextAlignmentLeft;
//    nameLabel.numberOfLines = 0;
//    nameLabel.font = [UIFont systemFontOfSize:14];
//    nameLabel.textColor = [UIColor whiteColor];
//    [self.contentView addSubview:nameLabel];
//    self.nameLabel = nameLabel;
////    UIView *bgView = [self valueForKey:@"_UITableViewHeaderFooterViewBackground"];
////    if (bgView) {
////        bgView.backgroundColor = [UIColor clearColor];
////    }
//    UIImageView *iconImageView = [[UIImageView alloc]init];
//    iconImageView.contentMode = UIViewContentModeScaleAspectFit;
//    [self.contentView addSubview:iconImageView];
//    self.iconImageView = iconImageView;
//
//    UIView *lineView = [UIView new];
//    lineView.backgroundColor = ChZ_ColorRGBA(255, 255, 255, 4/100.0f);
//    [self.contentView addSubview:lineView];
//    self.lineView = lineView;
//
//    UIButton *btn = [UIButton buttonWithType:UIButtonTypeCustom];
//    [btn addTarget:self action:@selector(selectAction:) forControlEvents:UIControlEventTouchUpInside];
//    [self.contentView addSubview:btn];
//    self.tapBtn = btn;
//
//}
//- (void)layoutSubviews
//{
//    [super layoutSubviews];
//
//    self.tapBtn.frame = self.contentView.bounds;
//    CGFloat width = self.contentView.chz_w - 16 -16 - 12 - 10;
//    CGSize textSize = [ChZUtil chz_labelSizeWithString:self.model.title font:[UIFont systemFontOfSize:14] width:width];
//    self.nameLabel.frame = CGRectMake(16, 15, width, textSize.height);
//    CGFloat iconY = ((textSize.height + 15 + 15)-12) * 0.5f;
//    CGFloat iconX = self.chz_w - 12 - 16;
//    self.iconImageView.frame = CGRectMake(iconX, iconY, 12, 12);
//    self.lineView.frame = CGRectMake(16, self.contentView.chz_h - 0.5, self.contentView.chz_w - 32, 0.5);
//}
- (void)setModel:(XTTMineAppHelpDetailModel *)model
{
    _model = model;
    self.nameLabel.text = model.title;
    if (model.isExpand) {
        self.iconImageView.image = [UIImage imageNamed:@"center_help_close"];
        self.lineView.hidden = YES;
    }else
    {
        self.iconImageView.image = [UIImage imageNamed:@"center_help_open"];
        self.lineView.hidden = NO;

    }
}
- (IBAction)selectAction:(id)sender
{
    self.model.isExpand = !self.model.isExpand;
    if (self.block)
    {
        self.block(nil);
    }
}

/*
// Only override drawRect: if you perform custom drawing.
// An empty implementation adversely affects performance during animation.
- (void)drawRect:(CGRect)rect {
    // Drawing code
}
*/

@end
