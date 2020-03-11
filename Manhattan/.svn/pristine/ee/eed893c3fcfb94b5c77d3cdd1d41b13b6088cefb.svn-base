//
//  XHHForseTaskCell.m
//  Manhattan
//
//  Created by Apple on 2018/8/17.
//  Copyright © 2018年 Apple. All rights reserved.
//

#import "XHHForseTaskCell.h"
@interface XHHForseTaskCell ()

@property (weak, nonatomic) IBOutlet UILabel *titleLable;
@property (weak, nonatomic) IBOutlet UILabel *timeLable;
@property (weak, nonatomic) IBOutlet UILabel *moneyLable;

@end

@implementation XHHForseTaskCell

- (void)awakeFromNib {
    [super awakeFromNib];
    self.selectionStyle = UITableViewCellSelectionStyleNone;
    // Initialization code
}

- (void)setSelected:(BOOL)selected animated:(BOOL)animated {
    [super setSelected:selected animated:animated];

    // Configure the view for the selected state
}
-(void)setListModel:(XHHFroseWorkListModel *)listModel{
    _listModel = listModel;
    self.titleLable.text =nil;
    self.timeLable.text =nil;
    self.moneyLable.text =nil;
    
    if(ChZ_StringIsEmpty(listModel.note)) self.titleLable.text = listModel.note;
    if(ChZ_StringIsEmpty(listModel.inputtime)) self.timeLable.text = [ChZUtil chz_formateDate:listModel.inputtime];
    if(ChZ_StringIsEmpty(listModel.value)) self.moneyLable.text = [NSString stringWithFormat:@"+%@",listModel.value];
}

@end
