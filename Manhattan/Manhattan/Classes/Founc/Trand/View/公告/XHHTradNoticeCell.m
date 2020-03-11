//
//  XHHTradNoticeCell.m
//  FuturePurse
//
//  Created by Apple on 2018/7/30.
//  Copyright © 2018年 jbtm. All rights reserved.
//

#import "XHHTradNoticeCell.h"

@interface XHHTradNoticeCell()

@property (strong, nonatomic) UIImageView *productImageView;

@property (strong, nonatomic) UILabel     *productNameLable;
@end


@implementation XHHTradNoticeCell
-(instancetype)initWithFrame:(CGRect)frame{
    self = [super initWithFrame:frame];
    if (self) {
        
        [self zh_setupUI];
        
        self.backgroundColor = [UIColor whiteColor];
        
        
    }
    return self;
}

-(void)zh_setupUI{
    
    [self addSubview:self.productImageView];
    [self.productImageView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.top.right.equalTo(self);
        make.height.mas_equalTo((ChZ_WIDTH-39)/2);
    }];
    
    [self addSubview:self.productNameLable];
    [self.productNameLable mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(self).offset(10);
        make.top.equalTo(self.productImageView.mas_bottom).offset(5);
        make.right.equalTo(self).offset(-10);
//        make.bottom.equalTo(self).offset(-10);
    }];
}

-(UIImageView *)productImageView{
    if (!_productImageView) {
        _productImageView = [[UIImageView alloc] init];
        _productImageView.image = [UIImage imageNamed:@"public_shop_demoshop"];
        _productImageView.layer.borderColor = [UIColor colorWithHexString:@"E7EBEE"].CGColor;
        _productImageView.layer.borderWidth = 1;
    }
    return _productImageView;
}
-(UILabel *)productNameLable{
    if (!_productNameLable) {
        _productNameLable = [UILabel newSingleFrame:CGRectZero title:@"商品名称" fontS:12.0 color:[UIColor colorWithHexString:@"8F8F8F"]];
        _productNameLable.numberOfLines = 2;
    }
    return _productNameLable;
}

-(void)reloadCellWithDic:(NSDictionary *)dic{
    self.productNameLable.text = [dic objectForKey:@"name"];
    self.productImageView.image = [UIImage imageNamed:[dic objectForKey:@"image"]];
}
-(void)reloadCellWithModel:(XHHNotesListModel *)model{
    self.productNameLable.text = model.title;
    if (model.thumb) {
        [self.productImageView sd_setImageWithURL:[NSURL URLWithString:model.thumb]];
    }
}
@end
