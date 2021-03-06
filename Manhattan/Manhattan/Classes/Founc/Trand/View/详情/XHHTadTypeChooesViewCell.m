//
//  XHHTadTypeChooesViewCell.m
//  FuturePurse
//
//  Created by Apple on 2018/7/27.
//  Copyright © 2018年 jbtm. All rights reserved.
//

#import "XHHTadTypeChooesViewCell.h"

@interface XHHTadTypeChooesViewCell()

@property (weak, nonatomic) IBOutlet UIImageView *imageView;

@property (weak, nonatomic) IBOutlet UILabel *titleLable;

@end


@implementation XHHTadTypeChooesViewCell

- (void)awakeFromNib {
    [super awakeFromNib];
    // Initialization code
}

-(void)reloadCellWithDic:(NSDictionary *)dic{
    self.imageView.image = [UIImage imageNamed:[dic objectForKey:@"image"]];
    self.titleLable.text = [dic objectForKey:@"title"];
}
-(void)reloadCellWithModel:(XHHCoinListModel *)model{
    if (model.thumb) {
        [self.imageView sd_setImageWithURL:[NSURL URLWithString:model.thumb]];
    }
    self.titleLable.text = model.coin;
}
@end
