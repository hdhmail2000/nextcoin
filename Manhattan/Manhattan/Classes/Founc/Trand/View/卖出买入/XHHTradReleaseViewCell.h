//
//  XHHTradReleaseViewCell.h
//  FuturePurse
//
//  Created by Apple on 2018/7/30.
//  Copyright © 2018年 jbtm. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "XHHC2CMyReleaseModel.h"
@interface XHHTradReleaseViewCell : UITableViewCell

-(void)reloadCellWithDic:(NSDictionary *)dic;

-(void)reloadCellWithModel:(XHHC2CMyReleaseModel *)model;

@end
