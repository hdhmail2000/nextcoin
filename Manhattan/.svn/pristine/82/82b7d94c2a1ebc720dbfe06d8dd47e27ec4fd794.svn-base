//
//  ChZImageItemCell.h
//  tinyhour
//
//  Created by Howe on 2017/8/6.
//  Copyright © 2017年 ChZ. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "ChZImageModel.h"
@class ChZImageItemCell;
@protocol ChZImageItemCellDelegate<NSObject>

@optional
- (void)imageItemCellClick:(ChZImageItemCell *)imageItemCell;

@end

@interface ChZImageItemCell : UICollectionViewCell

@property (nonatomic, weak) id<ChZImageItemCellDelegate> delegate;

@property (nonatomic, strong) ChZImageModel *model;

@end
