//
//  XHHProgressView.m
//  FuturePurse
//
//  Created by Apple on 2018/7/26.
//  Copyright © 2018年 jbtm. All rights reserved.
//

#import "XHHProgressView.h"


#define currimagename @"trad_detail_proImage"

#define passimagename @"trad_pay_passpprogress"

#define grayimagename @"trad_pay_graypprogress"

@interface XHHProgressView ()

@property (strong, nonatomic) NSMutableArray *images;

@end

@implementation XHHProgressView



-(instancetype)initProgressCurrIndex:(NSInteger)currIndex;{
    self = [super init];
    if (self) {
        if (self.images.count > currIndex && currIndex >=0) {
            
            for(int i = 0; i < self.images.count + 1;i++){
                UIView *v;
                if (currIndex == i) {
                    v = [self CreatProgressageImageType:0];
                }else if (currIndex > i){
                    v = [self CreatProgressageImageType:1];
                }else{
                    v = [self CreatProgressageImageType:2];
                }
                
                if (i == self.images.count - 1 && i == currIndex){
                    v = [self CreatProgressageImageType:3];
                }
                if (i == self.images.count - 1 && i != currIndex){
                    v = [self CreatProgressageImageType:4];
                }
                [self addSubview:v];
                v.frame = CGRectMake(((ChZ_WIDTH-32)/4 - 5)*i, 0, (ChZ_WIDTH-32)/4-5, 23);
            }
            
        }
        UITapGestureRecognizer *tap = [[UITapGestureRecognizer alloc] initWithTarget:self action:@selector(tapAction:)];
        [self addGestureRecognizer:tap];
    }
    return self;
}


- (void)tapAction:(UITapGestureRecognizer *)tap
{
    //当前坐标
    CGPoint p = [tap locationInView:tap.view];
    NSLog(@"%f",p.x);

}
-(UIView *)CreatProgressageImageType:(NSInteger)type{
    UIView *v = [[UIView alloc] init];
    UIImageView *imageView = [[UIImageView alloc] init];
    [v addSubview:imageView];
    
    UIView *pView = [[UIView alloc] init];
    
    [v addSubview:pView];
    
    
    switch (type) {
        case 0:
            imageView.image = [UIImage imageNamed:currimagename];
            pView.backgroundColor = [UIColor colorWithHexString:@"D6D6D6"];
            [self layoutImageWitdh:20 view:v imageView:imageView pView:pView];
            break;
        case 1:
            imageView.image = [UIImage imageNamed:passimagename];
            pView.backgroundColor = [UIColor colorWithHexString:@"2395FF"];
            [self layoutImageWitdh:7 view:v imageView:imageView pView:pView];
            break;
        case 2:
            imageView.image = [UIImage imageNamed:grayimagename];
            pView.backgroundColor = [UIColor colorWithHexString:@"D6D6D6"];
            [self layoutImageWitdh:7 view:v imageView:imageView pView:pView];
            break;
        case 3:
            imageView.image = [UIImage imageNamed:currimagename];
            pView.backgroundColor = [UIColor clearColor];
            [self layoutImageWitdh:20 view:v imageView:imageView pView:pView];
            break;
        case 4:
            imageView.image = [UIImage imageNamed:grayimagename];
            pView.backgroundColor = [UIColor clearColor];
            [self layoutImageWitdh:7 view:v imageView:imageView pView:pView];
            break;
            
        default:
            break;
    }
    
    return v;
}
-(void)layoutImageWitdh:(CGFloat)whitd view:(UIView *)v imageView:(UIImageView *)imageView pView:(UIView *)pView{
    [imageView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(v);
        make.centerY.equalTo(v);
        make.size.mas_equalTo(CGSizeMake(whitd, whitd));
    }];
    [pView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(imageView.mas_right).offset(2);
        make.right.equalTo(v).offset(-2);
        make.height.mas_equalTo(4);
        make.centerY.equalTo(v);
    }];
}

-(NSMutableArray *)images{
    if (!_images) {
        _images = [NSMutableArray arrayWithArray:@[grayimagename,grayimagename,grayimagename,grayimagename,grayimagename]];
    }
    return _images;
}
/*
// Only override drawRect: if you perform custom drawing.
// An empty implementation adversely affects performance during animation.
- (void)drawRect:(CGRect)rect {
    // Drawing code
}
*/

@end
