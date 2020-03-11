//
//  XHHCurrencyCell.h
//  Manhattan
//
//  Created by Apple on 2018/8/20.
//  Copyright © 2018年 Apple. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "XHHC2CCnyListModel.h"

@interface XHHCurrencyCell : UITableViewCell

@property (nonatomic , strong) XHHC2CCnyListModel *model;

-(void)choseImageHiddle:(BOOL)hiddle;

@end
