//
//  XHHRecevableView.h
//  Manhattan
//
//  Created by Apple on 2018/8/17.
//  Copyright © 2018年 Apple. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface XHHRecevableView : UIView
@property (weak, nonatomic) IBOutlet UIImageView *areaQrImage;

@property (weak, nonatomic) IBOutlet UILabel *areaLable;

@property (copy , nonatomic)  void (^copyWalletAreBlock)(NSString *areaString);

@end
