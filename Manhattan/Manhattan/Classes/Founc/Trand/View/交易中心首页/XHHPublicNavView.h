//
//  XHHPublicNavView.h
//  Manhattan
//
//  Created by Apple on 2018/8/15.
//  Copyright © 2018年 Apple. All rights reserved.
//

#import <UIKit/UIKit.h>

@protocol XHHPublicNavViewProtocol<NSObject>

-(void)rightButtonActionIndex:(NSInteger)index;

@end


@interface XHHPublicNavView : UIView

@property (nonatomic , weak) id<XHHPublicNavViewProtocol> delegete;


-(void)setLeftButtonTitle:(NSString *)title image:(NSString *)image;

-(void)setrightButtonTitles:(NSArray *)titles images:(NSArray *)images;
@property (weak, nonatomic) IBOutlet NSLayoutConstraint *rightTwoWithd;
@property (weak, nonatomic) IBOutlet NSLayoutConstraint *rightOneWithd;
@property (weak, nonatomic) IBOutlet NSLayoutConstraint *leftWithd;

@end
