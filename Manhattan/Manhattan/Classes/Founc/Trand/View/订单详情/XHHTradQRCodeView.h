//
//  XHHTradQRCodeView.h
//  FuturePurse
//
//  Created by Apple on 2018/7/30.
//  Copyright © 2018年 jbtm. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface XHHTradQRCodeView : UIView

@property (weak, nonatomic) IBOutlet UIImageView *qrImageView;

@property (weak, nonatomic) IBOutlet UILabel *alertLabel;

-(void)showInView:(UIView *)view;

-(void)dismiss;

@end
