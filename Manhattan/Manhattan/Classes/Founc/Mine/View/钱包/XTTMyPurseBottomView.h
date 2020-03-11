//
//  XTTMyPurseBottomView.h
//  FuturePurse
//
//  Created by muye01 on 2018/7/31.
//  Copyright © 2018年 jbtm. All rights reserved.
//

#import <UIKit/UIKit.h>
@interface XTTMyPurseTableBottomCell : UICollectionViewCell
@property (nonatomic, strong) NSString *title;
@end

@interface XTTMyPurseBottomView : UIView

@property (nonatomic, strong) NSArray *titles;

@property (nonatomic, copy) void (^didSelectItemBlock)(NSInteger index, NSString *title);

@end
