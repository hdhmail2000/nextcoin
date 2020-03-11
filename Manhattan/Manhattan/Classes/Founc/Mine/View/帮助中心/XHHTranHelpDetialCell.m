//
//  XHHTranHelpDetialCell.m
//  FuturePurse
//
//  Created by Apple on 2018/7/25.
//  Copyright © 2018年 jbtm. All rights reserved.
//

#import "XHHTranHelpDetialCell.h"

@interface XHHTranHelpDetialCell()

@property (weak, nonatomic) IBOutlet UILabel *dLable;

@end

@implementation XHHTranHelpDetialCell

- (void)awakeFromNib {
    [super awakeFromNib];
    self.selectionStyle = UITableViewCellSelectionStyleNone;
    // Initialization code
}

- (void)setSelected:(BOOL)selected animated:(BOOL)animated {
    [super setSelected:selected animated:animated];

    // Configure the view for the selected state
}
-(void)reladCellWithDic:(NSDictionary *)dic{
    self.dLable.text = [dic objectForKey:@"title"];
    NSLog(@"%@------",self.dLable.text);
}
@end
