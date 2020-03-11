//
//  ChZImageItemCell.m
//  tinyhour
//
//  Created by Howe on 2017/8/6.
//  Copyright © 2017年 ChZ. All rights reserved.
//

#import "ChZImageItemCell.h"
#import <CommonCrypto/CommonDigest.h>
#import <Photos/Photos.h>
@interface ChZImageItemCell()<UIScrollViewDelegate>

@property (nonatomic, weak) UIImageView *imageView;

@property (nonatomic, weak) UIScrollView *scrollView;

@property (nonatomic, weak) UIActivityIndicatorView *actView;

@end
@implementation ChZImageItemCell
- (instancetype)init
{
    self = [super init];
    if (self)
    {
        [self _setupView];
    }
    return self;
}
- (instancetype)initWithFrame:(CGRect)frame
{
    self = [super initWithFrame:frame];
    if (self)
    {
        [self _setupView];
    }
    return self;
}
- (void)_setupView
{
    self.backgroundColor = [UIColor clearColor];
    UIScrollView *scrollView = [[UIScrollView alloc]init];
    [self.contentView addSubview:scrollView];
    self.scrollView = scrollView;
    scrollView.delegate = self;
    
    UITapGestureRecognizer *singleTap = [[UITapGestureRecognizer alloc] initWithTarget:self action:@selector(singleTap:)];
    UITapGestureRecognizer *doubleTap = [[UITapGestureRecognizer alloc] initWithTarget:self action:@selector(doubleTap:)];
    doubleTap.numberOfTapsRequired = 2;
    [singleTap requireGestureRecognizerToFail:doubleTap];
    [self.scrollView addGestureRecognizer:singleTap];
    [self.scrollView addGestureRecognizer:doubleTap];
    
    scrollView.bouncesZoom = YES;
    scrollView.maximumZoomScale = 3;
    scrollView.multipleTouchEnabled = YES;
    scrollView.alwaysBounceVertical = NO;
    scrollView.showsVerticalScrollIndicator = YES;
    scrollView.showsHorizontalScrollIndicator = NO;
    scrollView.frame = [UIScreen mainScreen].bounds;
    
    UIImageView *imageView = [[UIImageView alloc]init];
    imageView.contentMode = UIViewContentModeScaleAspectFit;
    [self.scrollView addSubview:imageView];
    self.imageView = imageView;
    [self resizesSubViews];
    
    UIActivityIndicatorView *actView = [[UIActivityIndicatorView alloc]initWithActivityIndicatorStyle:UIActivityIndicatorViewStyleWhiteLarge];
    actView.center = self.contentView.center;
    [self.contentView addSubview:actView];
    self.actView = actView;
    actView.hidesWhenStopped = YES;
}

- (void)setModel:(ChZImageModel *)model
{
    _model = model;
    [self.actView stopAnimating];
    self.scrollView.zoomScale = 1.0f;
    if (!model)
    {
        _imageView.image = nil;
        return;
    }
    if (model.type == ChZImageModelTypeImage && model.image)
    {
        self.imageView.image = model.image;
        [self resizesSubViews];
        return;
    }
    if (model.type == ChZImageModelTypePhotoLibary && model.asset)
    {
        
        PHImageRequestOptions *options = [[PHImageRequestOptions alloc]init];
        options.synchronous = YES;
        __weak __typeof(self)weakSelf = self;
        [[PHImageManager defaultManager] requestImageForAsset:model.asset targetSize:CGSizeMake(model.asset.pixelWidth, model.asset.pixelHeight) contentMode:PHImageContentModeDefault options:options resultHandler:^(UIImage * _Nullable result, NSDictionary * _Nullable info)
         {
             __strong __typeof(weakSelf)_strongSelf = weakSelf;
             _model.image = result;
             _strongSelf.imageView.image = _model.image;
             [_strongSelf resizesSubViews];
             return;
         }];
        return;
    }
    if (model.type == ChZImageModelTypeURL )
    {
        [self _requestImageSD:model];
        return;
    }
}



- (void)singleTap:(UITapGestureRecognizer *)singleTap
{
    if ([self.delegate respondsToSelector:@selector(imageItemCellClick:)])
    {
        [self.delegate imageItemCellClick:self];
    }
}

- (void)doubleTap:(UITapGestureRecognizer *)doubleTap
{
    if (self.scrollView.zoomScale > 1)
    {
        [self.scrollView setZoomScale:1 animated:YES];
    } else
    {
        CGPoint touchPoint = [doubleTap locationInView:self.imageView];
        CGFloat newZoomScale = self.scrollView.maximumZoomScale;
        CGFloat xsize = self.bounds.size.width / newZoomScale;
        CGFloat ysize = self.bounds.size.height / newZoomScale;
        [self.scrollView zoomToRect:CGRectMake(touchPoint.x - xsize/2, touchPoint.y - ysize/2, xsize, ysize) animated:YES];
    }
}

- (void)resizesSubViews
{
    self.imageView.frame = CGRectMake(0, 0,self.scrollView.bounds.size.width,self.imageView.bounds.size.height);
    UIImage *image = self.imageView.image;
    if (image.size.height / image.size.width > self.scrollView.bounds.size.height / self.scrollView.bounds.size.width)
    {
        self.imageView.frame = CGRectMake(self.imageView.frame.origin.x, self.imageView.frame.origin.y, self.imageView.bounds.size.width, (floor(image.size.height / (image.size.width / self.scrollView.bounds.size.width))));
    } else
    {
        CGFloat height = image.size.height / image.size.width * self.scrollView.bounds.size.width;
        if (height < 1 || isnan(height)) height = self.scrollView.bounds.size.height;
        height = floor(height);
        self.imageView.frame = CGRectMake(self.imageView.frame.origin.x, self.imageView.frame.origin.y, self.imageView.bounds.size.width, height);
        self.imageView.center = CGPointMake(self.imageView.center.x, self.scrollView.bounds.size.height / 2);
    }
    if (self.imageView.bounds.size.height > self.scrollView.bounds.size.height && self.imageView.bounds.size.height - self.scrollView.bounds.size.height <= 1)
    {
        self.imageView.frame = CGRectMake(self.imageView.frame.origin.x, self.imageView.frame.origin.y, self.imageView.bounds.size.width, self.scrollView.bounds.size.height);
    }
    self.scrollView.contentSize = CGSizeMake(self.scrollView.bounds.size.width, MAX(self.imageView.bounds.size.height, self.scrollView.bounds.size.height));
    [self.scrollView scrollRectToVisible:self.scrollView.bounds animated:NO];
    
    if (self.imageView.bounds.size.height <= self.scrollView.bounds.size.height)
    {
        self.scrollView.alwaysBounceVertical = NO;
    } else
    {
        self.scrollView.alwaysBounceVertical = YES;
    }
}
-(void)layoutSubviews
{
    [super layoutSubviews];
    self.scrollView.frame = self.bounds;
}
- (UIView *)viewForZoomingInScrollView:(UIScrollView *)scrollView
{
    return self.imageView;
}
#pragma mark UIScrollView
- (void)scrollViewDidZoom:(UIScrollView *)scrollView
{
    
    CGFloat offsetX = (scrollView.bounds.size.width > scrollView.contentSize.width)?
    (scrollView.bounds.size.width - scrollView.contentSize.width) * 0.5 : 0.0;
    
    CGFloat offsetY = (scrollView.bounds.size.height > scrollView.contentSize.height)?
    (scrollView.bounds.size.height - scrollView.contentSize.height) * 0.5 : 0.0;
    
    self.imageView.center = CGPointMake(scrollView.contentSize.width * 0.5 + offsetX,
                                 scrollView.contentSize.height * 0.5 + offsetY);

}


#pragma mark private method

- (void)_requestImageSD:(ChZImageModel *)model
{
    if (model == nil)return;
    
    __weak __typeof(self)weakSelf = self;
    [self.actView startAnimating];
    
    [self.imageView chz_requestImageWithImageURL:model.url completed:^(UIImage *image, NSError *error, SDImageCacheType cacheType, NSURL *imageURL)
    {
        __strong __typeof(weakSelf)_strongSelf = weakSelf;
        [_strongSelf.actView stopAnimating];
        _model.image = image;
        _strongSelf.imageView.image = _model.image;
        [_strongSelf resizesSubViews];
        
    }];
}

- (void)_requestImage:(ChZImageModel *)model
{
    if (model == nil)return;
    NSString *fileName = [self _md5WithString:model.url];
    NSString *filePath = [self _path:fileName];
    NSData *imageData = [NSData dataWithContentsOfFile:filePath];
    if (imageData == nil || imageData.length == 0)
    {
        NSURLSession *session = [NSURLSession sharedSession];
        NSURLRequest *request = [NSURLRequest requestWithURL:[NSURL URLWithString:model.url]];
        __weak __typeof(self)weakSelf = self;
        [self.actView startAnimating];
        NSURLSessionDataTask *dataTask = [session dataTaskWithRequest:request completionHandler:^(NSData * _Nullable data, NSURLResponse * _Nullable response, NSError * _Nullable error)
          {
              __strong __typeof(weakSelf)_strongSelf = weakSelf;
              [_strongSelf.actView stopAnimating];
              if (data != nil && data.length != 0)
              {
                  [data writeToFile:filePath atomically:YES];
                  UIImage *image = [UIImage imageWithData:data];
                  _model.image = image;
                  _strongSelf.imageView.image = _model.image;
                  [_strongSelf resizesSubViews];
                  
                  return;
              }
          }];
        [dataTask resume];
        
        
    }else
    {
        UIImage *image = [UIImage imageWithData:imageData];
        _model.image = image;
        self.imageView.image = _model.image;
        [self resizesSubViews];
    }
}

- (NSString *)_md5WithString:(NSString *)str
{
    if(str == nil || str.length == 0)return @"";
    NSString *string = str;
    const char *cStr = [string UTF8String];
    unsigned char result[CC_MD5_DIGEST_LENGTH];
    
    CC_MD5( cStr, (int)strlen(cStr), result );
    
    return [NSString stringWithFormat:@"%02x%02x%02x%02x%02x%02x%02x%02x%02x%02x%02x%02x%02x%02x%02x%02x",
            result[0], result[1],
            result[2], result[3],
            result[4], result[5],
            result[6], result[7],
            result[8], result[9],
            result[10], result[11],
            result[12], result[13],
            result[14], result[15]
            ];
}
- (NSString *)_path:(NSString *)fileName
{
    if (fileName == nil || fileName.length == 0)return nil;
    NSString *pathString = [[NSSearchPathForDirectoriesInDomains(NSDocumentDirectory, NSUserDomainMask, YES) lastObject] stringByAppendingPathComponent:@"ChZImageBrowseFile"];
    NSFileManager *fileMgr = [NSFileManager defaultManager];
    if (![fileMgr fileExistsAtPath:pathString isDirectory:NULL])
    {
        [fileMgr createDirectoryAtPath:pathString withIntermediateDirectories:YES attributes:nil error:nil];
    }
        pathString = [NSString stringWithFormat:@"%@/%@", pathString,fileName];
    return pathString;
}

@end
