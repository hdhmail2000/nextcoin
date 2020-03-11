//
//  XHHTradNoticeCell.h
//  FuturePurse
//
//  Created by Apple on 2018/7/30.
//  Copyright © 2018年 jbtm. All rights reserved.
//

#import <UIKit/UIKit.h>

#import "XHHNotesListModel.h"

@interface XHHTradNoticeCell : UICollectionViewCell

-(void)reloadCellWithDic:(NSDictionary *)dic;

-(void)reloadCellWithModel:(XHHNotesListModel *)model;

@end
