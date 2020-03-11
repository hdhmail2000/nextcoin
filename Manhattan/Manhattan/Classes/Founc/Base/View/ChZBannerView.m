//
//  ChZBannerView.m
//  ADDemo
//
//  Created by ChZ on 2017/5/31.
//  Copyright © 2017年 ChZ. All rights reserved.
//

#import "ChZBannerView.h"

/**
 实现的原理： 使用 NSProxy 持有 NSTimer 的 target
 不再用 NSTimer 直接持有 self，就不会导致 timer 对 self 的循环强引用了
 */
@implementation ChZWeakProxy

- (instancetype)initWithTarget:(id)target
{
    _target = target;
    return self;
}

//类方法
+ (instancetype)proxyWithTarget:(id)target
{
    return [[ChZWeakProxy alloc] initWithTarget:target];
}

#pragma mark - private

- (id)forwardingTargetForSelector:(SEL)selector {
    return _target;
}

#pragma mark - over write

//重写NSProxy如下两个方法，在处理消息转发时，将消息转发给真正的Target处理
- (void)forwardInvocation:(NSInvocation *)invocation {
    void *null = NULL;
    [invocation setReturnValue:&null];
}

- (NSMethodSignature *)methodSignatureForSelector:(SEL)selector {
    return [NSObject instanceMethodSignatureForSelector:@selector(init)];
}

- (BOOL)respondsToSelector:(SEL)aSelector {
    return [_target respondsToSelector:aSelector];
}

#pragma mark - <NSObject>

- (BOOL)isEqual:(id)object {
    return [_target isEqual:object];
}

- (NSUInteger)hash {
    return [_target hash];
}

- (Class)superclass {
    return [_target superclass];
}

- (Class)class {
    return [_target class];
}

- (BOOL)isKindOfClass:(Class)aClass {
    return [_target isKindOfClass:aClass];
}

- (BOOL)isMemberOfClass:(Class)aClass {
    return [_target isMemberOfClass:aClass];
}

- (BOOL)conformsToProtocol:(Protocol *)aProtocol {
    return [_target conformsToProtocol:aProtocol];
}

- (BOOL)isProxy {
    return YES;
}

- (NSString *)description {
    return [_target description];
}

- (NSString *)debugDescription {
    return [_target debugDescription];
}

@end

@implementation ChZBannerViewModel

@end

@interface ChZBannerView()<UIScrollViewDelegate>

@property (nonatomic, strong) UIPageControl *pageControl;

@property (nonatomic, strong) UIScrollView *scrollView;

@property (nonatomic, strong) UIButton  *pic;

@property (nonatomic, assign) bool  flag;

@property (nonatomic, strong) CADisplayLink * scrollTimer;

@property (nonatomic, assign) int scrollTopicFlag;

@property (nonatomic, assign) int currentPage;

@property (nonatomic, assign) CGSize imageSize;

@property (nonatomic, strong) UIImage *image;

@end
@implementation ChZBannerView
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
    UIScrollView *scrollView = [[UIScrollView alloc]initWithFrame:self.bounds];
    [self addSubview:scrollView];
    self.scrollView = scrollView;
    self.scrollView.pagingEnabled = YES;
    self.scrollView.scrollEnabled = YES;
    self.scrollView.delegate = self;
    self.scrollView.showsHorizontalScrollIndicator = NO;
    self.scrollView.showsVerticalScrollIndicator = NO;
    self.scrollView.backgroundColor = [UIColor whiteColor];
    [self.scrollView setContentSize:CGSizeMake(self.chz_w * 5, self.chz_h)];
    //
    UIPageControl *pageControl = [[UIPageControl alloc]initWithFrame:CGRectMake(0.0f, self.frame.size.height - 30.0f , self.frame.size.width, 30.0f)];
    [self addSubview:pageControl];
    self.pageControl = pageControl;
}
- (void)setPageControlVerticalDirection:(BOOL)pageControlVerticalDirection
{
    _pageControlVerticalDirection = pageControlVerticalDirection;
    if (pageControlVerticalDirection)
    {
        self.pageControl.frame = CGRectMake(ChZ_WIDTH- 20 - self.chz_w * 0.5 , (self.chz_h - 30) * 0.5f, self.chz_w, 30.0f);
        self.pageControl.transform = CGAffineTransformMakeRotation(M_PI_2);
    }
}

- (void)setPageIndicatorTintColor:(UIColor *)pageIndicatorTintColor
{
    _pageIndicatorTintColor = pageIndicatorTintColor;
    self.pageControl.pageIndicatorTintColor = pageIndicatorTintColor;
}

- (void)setCurrentPageIndicatorTintColor:(UIColor *)currentPageIndicatorTintColor
{
    _currentPageIndicatorTintColor = currentPageIndicatorTintColor;
    self.pageControl.currentPageIndicatorTintColor = currentPageIndicatorTintColor;
}

- (void)setBannerModels:(NSArray<ChZBannerViewModel *> *)bannerModels
{
    _bannerModels = bannerModels;
    [self _setupSubviews];
}
- (void)_setupSubviews
{
    if (self.scrollView.subviews.count >0)
    {
        [self stopTimer];
    }
    for (UIView *view  in self.scrollView.subviews)
    {
        [view removeFromSuperview];
    }
    if (_bannerModels == nil || _bannerModels.count == 0)
    {
        ChZ_ErrorLog(@"滚动图片数组为空");
        return;
    }
    self.pageControl.numberOfPages = _bannerModels.count;
    self.pageControl.currentPage = 0;
    NSMutableArray *tempModelArray = [NSMutableArray array];
    ChZBannerViewModel *firstModel = [_bannerModels firstObject];
    ChZBannerViewModel *lastModel = [_bannerModels lastObject];
    [tempModelArray addObject:lastModel];
    for (ChZBannerViewModel *model  in _bannerModels)
    {
        [tempModelArray addObject:model];
    }
    [tempModelArray addObject:firstModel];
    _bannerModels = nil;
    _bannerModels = tempModelArray;
    
    
    int i = 0;
    
    for (ChZBannerViewModel  *model in _bannerModels)
    {
        self.pic= nil;
        self.pic = [UIButton buttonWithType:UIButtonTypeCustom];
        self.pic.imageView.contentMode = UIViewContentModeTop;
        [self.pic setFrame:CGRectMake(i * self.scrollView.frame.size.width,0, self.scrollView.frame.size.width, self.scrollView.frame.size.height)];
        UIImageView * tempImage = [[UIImageView alloc]initWithFrame:CGRectMake(0, 0, self.pic.frame.size.width, self.pic.frame.size.height)];
        tempImage.contentMode = UIViewContentModeScaleAspectFill;
        [tempImage setClipsToBounds:YES];
        switch (model.requestType)
        {
            case ChZBannerImageReqeustTypeID:
                [tempImage chz_requestImageWithImageId:model.requestURL];
                break;
            case ChZBannerImageReqeustTypeURL:
                [tempImage chz_requestImageWithImageURL:model.requestURL];
                break;
            case ChZBannerImageReqeustTypeFileURL:
                tempImage.image = [UIImage imageWithContentsOfFile:model.requestURL];
                break;
            case ChZBannerImageReqeustTypeDoMainURL:
//                [tempImage sd_setImageWithURL:[NSURL URLWithString:model.requestURL]];
                [tempImage chz_requestImageWithImageURL:model.requestURL];
                break;
            case ChZBannerImageReqeustTypeNormal:
                ChZ_DebugLog(@"模型没有设置图片请求链接");
                break;
            default:
                break;
        }
        [self.pic addSubview:tempImage];
        [self.pic setBackgroundColor:[UIColor grayColor]];
        self.pic.tag = i;
        [self.pic addTarget:self action:@selector(click:) forControlEvents:UIControlEventTouchUpInside];
        [self.scrollView addSubview:self.pic];
        
        if (model.isShowTitle)
        {
            UILabel * title = [[UILabel alloc]initWithFrame:CGRectMake(i*self.frame.size.width, self.frame.size.height-30, self.frame.size.width,30)];
            [title setBackgroundColor:[UIColor blackColor]];
            [title setAlpha:0.7f];
            [title setText:model.title];
            [title setTextColor:[UIColor whiteColor]];
            [title setFont:[UIFont fontWithName:@"Helvetica" size:12]];
            [self addSubview:title];
        }
        i ++;
    }
    [self.scrollView setContentSize:CGSizeMake(self.frame.size.width*[_bannerModels count], self.frame.size.height)];
    [self.scrollView setContentOffset:CGPointMake(self.frame.size.width, 0) animated:NO];
    
    if (self.scrollTimer)
    {
        [self.scrollTimer invalidate];
        self.scrollTimer = nil;
    }
    if ([_bannerModels count]>3)
    {
        [self _setpTimer];
    }
}

-(void)scrollViewDidEndDragging:(UIScrollView *)scrollView willDecelerate:(BOOL)decelerate
{
    [self _setpTimer];
}

- (void)_setpTimer
{
    _scrollTimer = [CADisplayLink displayLinkWithTarget:[ChZWeakProxy proxyWithTarget:self] selector:@selector(scrollTopic)];
    [_scrollTimer addToRunLoop:[NSRunLoop mainRunLoop] forMode:NSRunLoopCommonModes];

}

-(void)scrollViewWillBeginDragging:(UIScrollView *)scrollView
{
    if (self.scrollTimer)
    {
        count = 0;
        [self.scrollTimer invalidate];
        self.scrollTimer = nil;
    }
}

- (void)scrollViewDidScroll:(UIScrollView *)scrollView
{
    CGFloat Width=self.frame.size.width;
    if (scrollView.contentOffset.x == self.frame.size.width)
    {
        self.flag = YES;
    }
    if (self.flag)
    {
        if (scrollView.contentOffset.x <= 0)
        {
            [scrollView setContentOffset:CGPointMake(Width*([self.bannerModels count]-2), 0) animated:NO];
        }else if (scrollView.contentOffset.x >= Width*([self.bannerModels count]-1)) {
            [scrollView setContentOffset:CGPointMake(self.frame.size.width, 0) animated:NO];
        }
    }
    self.currentPage = scrollView.contentOffset.x/self.frame.size.width-1;
    if ([self.delegate respondsToSelector:@selector(bannerView:currentPage:total:)])
    {
        [self.delegate bannerView:self currentPage:self.currentPage total:self.bannerModels.count-2];
    }
    self.pageControl.numberOfPages = self.bannerModels.count-2;
    self.pageControl.currentPage = self.currentPage;
    self.scrollTopicFlag = self.currentPage + 2 == 2 ? 2 : self.currentPage+2;
}
- (void)click:(UIButton *)sender
{
    
    if ([self.delegate respondsToSelector:@selector(bannerView:didClick:)])
    {
        [self.delegate bannerView:self didClick:[self.bannerModels objectAtIndex:sender.tag]];
    }
    if ([self.delegate respondsToSelector:@selector(bannerViewDidClick:model:)])
    {
        ChZBannerViewModel *model = [self.bannerModels objectAtIndex:sender.tag];
        [self.delegate bannerViewDidClick:self model:model.model];
    }
}
long long count = 0;
- (void)scrollTopic
{
    count ++;
//    long long i = (long long)count%120;
    if (count%180 != 0)return;
    [self.scrollView setContentOffset:CGPointMake(self.frame.size.width * self.scrollTopicFlag, 0) animated:YES];
    if (self.scrollTopicFlag > [self.bannerModels count])
    {
        self.scrollTopicFlag = 1;
    }else
    {
        self.scrollTopicFlag++;
    }
}
- (void) stopTimer
{
    if (_scrollTimer)
    {
        ChZ_DebugLog(@"定时器释放");
        [_scrollTimer invalidate];
        _scrollTimer = nil;
    }
}
- (void)dealloc
{
    if (_scrollTimer)
    {
        ChZ_DebugLog(@"定时器释放");
        [_scrollTimer invalidate];
        _scrollTimer = nil;
    }
}


@end
