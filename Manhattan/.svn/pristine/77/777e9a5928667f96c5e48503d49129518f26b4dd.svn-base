//
//  ChZImageModel.h
//  tinyhour
//
//  Created by Howe on 2017/8/5.
//  Copyright © 2017年 ChZ. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <UIKit/UIKit.h>
#import <PhotosUI/PhotosUI.h>

typedef NS_ENUM(NSInteger,ChZImageModelType)
{
    ChZImageModelTypeNone = 0,
    //本地相册
    ChZImageModelTypePhotoLibary = 1,
    //网络图片
    ChZImageModelTypeURL = 2,
    //图片数据
    ChZImageModelTypeImage = 3
    
};
@interface ChZImageModel : NSObject

//@property (nonatomic, strong) THImageUpModel *upModel;

@property (nonatomic, strong) id model;

@property (nonatomic, assign) ChZImageModelType type;

@property (nonatomic, strong)  PHAsset *asset;

@property (nonatomic, strong) UIImage *image;

@property (nonatomic, copy) NSString *url;

@property (nonatomic, assign) BOOL selected;

@property (nonatomic, assign) CGFloat imgW;

@property (nonatomic, assign) CGFloat imgH;
//=====
//@property (nonatomic, copy) NSString *fileURL;
//
//@property (nonatomic, strong) THImageUpModel *model

@end
