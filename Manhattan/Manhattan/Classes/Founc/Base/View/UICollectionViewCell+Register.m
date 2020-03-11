//
//  UICollectionViewCell+Register.m
//  tonglian
//
//  Created by Howe on 2018/5/27.
//  Copyright © 2018年 Howe. All rights reserved.
//

#import "UICollectionViewCell+Register.h"

@implementation UICollectionViewCell (Register)
+ (void)registerCellWithCollectionView:(UICollectionView *)collectionView identifier:(NSString *)identifier nibName:(NSString *)nibName
{
    if (!ChZ_StringIsEmpty(identifier))return;
    if (ChZ_StringIsEmpty(nibName))
    {
        UINib *nib = [UINib nibWithNibName:nibName bundle:[NSBundle mainBundle]];
        [collectionView registerNib:nib forCellWithReuseIdentifier:identifier];
    }else
    {
        if (!ChZ_StringIsEmpty(identifier))return;
        [collectionView registerClass:[self class] forCellWithReuseIdentifier:identifier];
    }
}
@end
