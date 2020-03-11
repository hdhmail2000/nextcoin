//
//  XHHTSBDetailHeadView.h
//  FuturePurse
//
//  Created by Apple on 2018/7/30.
//  Copyright © 2018年 jbtm. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "XHHC2CMyReleaseModel.h"
@interface XHHTSBDetailHeadView : UIView

-(void)reloadViewWithModel:(XHHC2CMyReleaseModel *)model;

-(void)reloadViewWithType:(NSInteger)type;

@end
