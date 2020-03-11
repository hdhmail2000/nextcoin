//
//  ChZBannerView.h
//  ADDemo
//
//  Created by ChZ on 2017/5/31.
//  Copyright © 2017年 ChZ. All rights reserved.
//

#import <UIKit/UIKit.h>



typedef NS_ENUM(NSInteger,ChZBannerImageReqeustType)
{
    ChZBannerImageReqeustTypeNormal = 0,
    ChZBannerImageReqeustTypeID = 1,
    ChZBannerImageReqeustTypeURL = 2,
    ChZBannerImageReqeustTypeDoMainURL = 3,
    ChZBannerImageReqeustTypeFileURL = 4
};

@class ChZBannerView;


NS_ASSUME_NONNULL_BEGIN

@protocol ChZBannerViewDelegate <NSObject>

@optional

- (void)bannerViewDidClick:(ChZBannerView *) bannerView model:(id)model;

- (void)bannerView:(ChZBannerView *) bannerView didClick:(id)data;

- (void)bannerView:(ChZBannerView *) bannerView currentPage:(int)page total:(NSUInteger)total;

@end
NS_ASSUME_NONNULL_END

NS_ASSUME_NONNULL_BEGIN

@interface ChZBannerViewModel : NSObject

@property (nonatomic, assign) ChZBannerImageReqeustType  requestType;
//图片的请求链接 可以是ID 可以是一部分链接可以只 文件地址 
@property (nonatomic, copy) NSString  *requestURL;

@property (nullable, nonatomic, copy) NSString  *title;

@property (nonatomic, assign) BOOL isShowTitle;

@property (nonatomic, strong) id model;

@end
NS_ASSUME_NONNULL_END


NS_ASSUME_NONNULL_BEGIN

@interface ChZBannerView : UIView

@property (nonatomic, strong) NSArray<ChZBannerViewModel *> *bannerModels;

@property (nonatomic, strong) NSArray<NSString *> *imageURLArray;

@property (nullable,nonatomic, weak) id <ChZBannerViewDelegate> delegate;

//pageControl显示的方向是否是垂直显示
@property (nonatomic, assign) BOOL  pageControlVerticalDirection;

@property (nullable, nonatomic,strong) UIColor *pageIndicatorTintColor ;

@property (nullable, nonatomic,strong) UIColor *currentPageIndicatorTintColor;

- (void) stopTimer;

@end

NS_ASSUME_NONNULL_END


NS_ASSUME_NONNULL_BEGIN

//这个类源自YYWeakProxy 因为怕重复导入 所以更名
@interface ChZWeakProxy : NSProxy

/**
 The proxy target.
 */
@property (nonatomic, weak, readonly) id target;

/**
 Creates a new weak proxy for target.
 
 @param target Target object.
 
 @return A new proxy object.
 */
- (instancetype)initWithTarget:(id)target;

/**
 Creates a new weak proxy for target.
 
 @param target Target object.
 
 @return A new proxy object.
 */
+ (instancetype)proxyWithTarget:(id)target;

@end
NS_ASSUME_NONNULL_END


