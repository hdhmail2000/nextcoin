//
//  ChZOrderDetailBottomView.h
//  FuturePurse
//
//  Created by Howe on 2018/9/13.
//  Copyright © 2018年 jbtm. All rights reserved.
//

#import "BaseView.h"
#import "XHHC2CMyOrderModel.h"
typedef void(^ChZOrderDetailBottomViewBlock) (BOOL isHandle);

@interface ChZOrderDetailBottomView : BaseView

@property (nonatomic, assign) C2CTradType type;

@property (nonatomic , strong)  XHHC2CMyOrderModel *orderModel;

@property (nonatomic, copy) ChZOrderDetailBottomViewBlock  block;


@end
