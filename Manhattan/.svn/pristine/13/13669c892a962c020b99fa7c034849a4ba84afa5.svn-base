//
//  XHHMineCell.m
//  FuturePurse
//
//  Created by 葛志豪 on 18/7/18.
//  Copyright © 2018年 jbtm. All rights reserved.
//

#import "XHHMineCell.h"

@interface XHHMineCell()

@property (strong, nonatomic) UIImageView *iconImageView;

@property (strong, nonatomic) UILabel     *tLable;

@property (strong, nonatomic) UIImageView *rightImageView;

@property (strong, nonatomic) UIView      *lineView;

@end

@implementation XHHMineCell

-(instancetype)initWithStyle:(UITableViewCellStyle)style reuseIdentifier:(NSString *)reuseIdentifier{
    self = [super initWithStyle:style reuseIdentifier:reuseIdentifier];
    if (self) {
        
        [self zh_setupUI];
        
        self.backgroundColor = [UIColor clearColor];
    }
    return self;
}

-(void)zh_setupUI{
    
    [self addSubview:self.iconImageView];
    [self.iconImageView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(self).offset(30);
        make.centerY.equalTo(self);
        make.size.mas_equalTo(CGSizeMake(20, 20));
    }];
    
    [self addSubview:self.tLable];
    [self.tLable mas_makeConstraints:^(MASConstraintMaker *make) {
        make.centerY.equalTo(self);
        make.left.equalTo(self.iconImageView.mas_right).offset(16);
    }];
    
    [self addSubview:self.rightImageView];
    [self.rightImageView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.right.equalTo(self).offset(-16);
        make.centerY.equalTo(self);
        make.size.mas_equalTo(CGSizeMake(15, 15 ));
    }];
    
    [self addSubview:self.lineView];
    [self.lineView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(self.iconImageView.mas_right).offset(15);
        make.bottom.equalTo(self);
        make.right.equalTo(self).offset(-10);
        make.height.mas_equalTo(1);
    }];
    
}
-(UIImageView *)iconImageView{
    if (!_iconImageView) {
        _iconImageView = [[UIImageView alloc] init];
        _iconImageView.contentMode = UIViewContentModeScaleAspectFit;
        _iconImageView.image = [UIImage imageNamed:@"mine_home_NorMalHead"];
    }
    return _iconImageView;
}
-(UIImageView *)rightImageView{
    if (!_rightImageView) {
        _rightImageView = [[UIImageView alloc] init];
        _rightImageView.contentMode = UIViewContentModeScaleAspectFit;
        _rightImageView.image = [UIImage imageNamed:@"mine_normal_right"];
    }
    return _rightImageView;
}
-(UILabel *)tLable{
    if (!_tLable) {
        _tLable = [UILabel newSingleFrame:CGRectZero title:@"John Doe" fontS:16.0 color:[UIColor colorWithHexString:@"2E7AF1"]];
    }
    return _tLable;
}
-(UIView *)lineView{
    if (!_lineView) {
        _lineView = [[UIView alloc] init];
        _lineView.backgroundColor = [UIColor colorWithHexString:@"35335D"];
    }
    return _lineView;
}

-(void)reloadCellWithDic:(NSDictionary *)dic{
    self.iconImageView.image = [UIImage imageNamed:[dic objectForKey:@"image"]];
    self.tLable.text = [dic objectForKey:@"name"];
}
- (void)awakeFromNib {
    [super awakeFromNib];
    // Initialization code
}

- (void)setSelected:(BOOL)selected animated:(BOOL)animated {
    [super setSelected:selected animated:animated];

    // Configure the view for the selected state
}

@end
