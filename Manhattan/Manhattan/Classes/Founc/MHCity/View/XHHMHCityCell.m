//
//  XHHMHCityCell.m
//  Manhattan
//
//  Created by Apple on 2018/8/16.
//  Copyright © 2018年 Apple. All rights reserved.
//

#import "XHHMHCityCell.h"
@interface XHHMHCityCell()
@property (weak, nonatomic) IBOutlet UIImageView *image;
@property (weak, nonatomic) IBOutlet UILabel *title;

@end
@implementation XHHMHCityCell

- (void)awakeFromNib {
    [super awakeFromNib];
    // Initialization code
}

-(void)reloadCellWithDic:(NSDictionary *)dic{
    _image.image = [UIImage imageNamed:dic[@"image"]];
    _title.text = dic[@"title"];
}

@end
