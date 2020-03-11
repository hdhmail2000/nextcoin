//
//  ChZImageModel.m
//  tinyhour
//
//  Created by Howe on 2017/8/5.
//  Copyright © 2017年 ChZ. All rights reserved.
//

#import "ChZImageModel.h"

@implementation ChZImageModel
- (void)setAsset:(PHAsset *)asset
{
    _asset = asset;
    if (_image == nil)
    {
        self.type = ChZImageModelTypePhotoLibary;
    }else
    {
       self.type = ChZImageModelTypeImage;
    }
}

- (void)setUrl:(NSString *)url
{
    _url = [url copy];
    self.type = ChZImageModelTypeURL;
}
- (void)setImage:(UIImage *)image
{
    _image = image;
    self.type = ChZImageModelTypeImage;
}
@end
