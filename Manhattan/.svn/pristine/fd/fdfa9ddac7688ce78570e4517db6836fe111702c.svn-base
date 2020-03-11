//
//  ChZImageBrowseView.m
//  tinyhour
//
//  Created by Howe on 2017/8/5.
//  Copyright © 2017年 ChZ. All rights reserved.
//

#import "ChZImageBrowseView.h"
#import "ChZImageModel.h"
#import "ChZImageItemCell.h"

@interface ChZImageBrowseView()<UICollectionViewDelegate,UICollectionViewDataSource,ChZImageItemCellDelegate>

@property (nonatomic, strong) UICollectionViewFlowLayout *flowLayout;

@property (nonatomic, weak) UICollectionView *collectionView;

@property (nonatomic, weak) UILabel *navNameLabel;

@property (nonatomic, weak) UIVisualEffectView  *navView;

@property (nonatomic, weak) UIView *contentView;

@property (nonatomic, assign) CGFloat navH;

@end

@implementation ChZImageBrowseView

- (instancetype)init
{
    self = [super init];
    if (self)
    {
        if ([ChZUtil isIphoneX])
        {
            self.navH = 88;
        }else
        {
            self.navH = 64;
        }
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

    self.defaultSelectedIndex = NSNotFound;
    CGFloat kSpacing = 10.0f;
    UICollectionViewFlowLayout *flowLayout = [[UICollectionViewFlowLayout alloc] init];
    self.flowLayout = flowLayout;
    self.flowLayout.minimumLineSpacing = 2 * kSpacing;
    self.flowLayout.minimumInteritemSpacing = 2 * kSpacing;
    self.flowLayout.itemSize = [UIScreen mainScreen].bounds.size;
    self.flowLayout.sectionInset = UIEdgeInsetsMake(0, kSpacing, 0, kSpacing);
    self.flowLayout.scrollDirection = UICollectionViewScrollDirectionHorizontal;
    
    UICollectionView *collectionView = [[UICollectionView alloc]initWithFrame:CGRectZero collectionViewLayout:self.flowLayout];
    collectionView.pagingEnabled = YES;
    [collectionView registerClass:[ChZImageItemCell class] forCellWithReuseIdentifier:@"cell"];
    [self addSubview:collectionView];
    collectionView.dataSource = self;
    collectionView.delegate = self;
    self.collectionView = collectionView;
    
    
//    UIVisualEffectView *navView = [[UIVisualEffectView alloc]initWithFrame:CGRectMake(0, 0, [UIScreen mainScreen].bounds.size.width, 64.0f)];
    UIVisualEffectView *navView = [[UIVisualEffectView alloc]initWithFrame:CGRectMake(0, 0, [UIScreen mainScreen].bounds.size.width, self.navH)];
    navView.effect = [UIBlurEffect effectWithStyle:UIBlurEffectStyleLight];
    
//    UIView *contentView = [[UIView alloc]initWithFrame:CGRectMake(0, 0, [UIScreen mainScreen].bounds.size.width, 64.0f)];
    UIView *contentView = [[UIView alloc]initWithFrame:CGRectMake(0, 0, [UIScreen mainScreen].bounds.size.width, self.navH)];
    [self addSubview:navView];
    self.navView = navView;
    [navView.contentView addSubview:contentView];
    self.contentView = contentView;
    
    
    UIButton *backBtn = [UIButton buttonWithType:UIButtonTypeCustom];
    backBtn.frame = CGRectMake(5, self.navH - 44, 44, 44);
    [backBtn setImage:[UIImage imageNamed:@"public_close"] forState:UIControlStateNormal];
    [backBtn addTarget:self action:@selector(backAction) forControlEvents:UIControlEventTouchUpInside];
    [contentView addSubview:backBtn];

    UILabel *navNameLabel = [[UILabel alloc]initWithFrame:CGRectMake(44, self.navH - 44, [UIScreen mainScreen].bounds.size.width - 88, 44)];
    navNameLabel.textAlignment = NSTextAlignmentCenter;
    navNameLabel.textColor = [UIColor blackColor];
    navNameLabel.font = [UIFont systemFontOfSize:17];
    navNameLabel.text = @"0/0";
    [contentView addSubview:navNameLabel];
    self.navNameLabel = navNameLabel;
    
}

- (void)show
{
    if (self.isEdit)
    {
        UIButton *removeBtn = [UIButton buttonWithType:UIButtonTypeCustom];
        removeBtn.frame = CGRectMake([UIScreen mainScreen].bounds.size.width - 54, self.navH - 44, 44, 44);
        [removeBtn setImage:[UIImage imageNamed:@"feed_delete"] forState:UIControlStateNormal];
        [removeBtn addTarget:self action:@selector(removeAction) forControlEvents:UIControlEventTouchUpInside];
        [self.contentView addSubview:removeBtn];
    }
    
    id appDelegate = [UIApplication sharedApplication].delegate;
    UIWindow *window = [appDelegate valueForKey:@"window"];
    if (!window)
    {
        NSLog(@"初始化错误");
        return;
    }
    UIViewController *vc = window.rootViewController;
    self.frame = vc.view.bounds;
    self.alpha = 0.0f;
    self.collectionView.frame = CGRectMake(-10, 0, [UIScreen mainScreen].bounds.size.width + 20,  [UIScreen mainScreen].bounds.size.height);
    [self _config];
    [vc.view addSubview:self];
    [UIView animateWithDuration:0.25 animations:^
     {
         self.alpha = 1.0f;
     } ];
    
    if (self.defaultSelectedIndex != NSNotFound  && self.defaultSelectedIndex< self.imageItemArray.count)
    {
        [self.collectionView scrollToItemAtIndexPath:[NSIndexPath indexPathForRow:self.defaultSelectedIndex inSection:0] atScrollPosition:UICollectionViewScrollPositionNone animated:NO];
        [self.collectionView setContentOffset:CGPointMake(self.collectionView.contentOffset.x + 10, self.collectionView.contentOffset.y) animated:NO];
        
        
        [self _config];
        
        NSInteger count = self.imageItemArray.count;
        self.navNameLabel.text = [NSString stringWithFormat:@"%ld/%ld",(long)(self.defaultSelectedIndex +1),(long)count];
    }
}

- (void)dismss
{
    if (self.imageBlock)
    {
        self.imageBlock(self.imageItemArray);
    }
    self.alpha = 1.0f;
    [UIView animateWithDuration:0.25 animations:^
    {
        self.alpha = 0.0f;
    } completion:^(BOOL finished)
    {
        if (self.superview)
        {
            [self removeFromSuperview];
        }
        if (self.block)
        {
            self.block();
        }
    }];
    
}
#pragma mark Btn Actions
- (void)backAction
{
    [self dismss];
}

- (void)removeAction
{
    NSArray *visibleCells = [self.collectionView visibleCells];
    if (visibleCells)
    {
        ChZImageItemCell *cell = [visibleCells firstObject];
        [self.imageItemArray removeObject:cell.model];
        [self.collectionView reloadData];
        [self _config];
    }
}

- (void)_config
{
    
    NSArray *visibleCells = [self.collectionView visibleCells];
    if ((visibleCells == nil || visibleCells.count == 0) && self.imageItemArray!= nil && self.imageItemArray.count != 0)
    {
        self.navNameLabel.text = [NSString stringWithFormat:@"1/%ld",(long)self.imageItemArray.count];
        return;
    }
    if (visibleCells)
    {
        ChZImageItemCell *cell = [visibleCells firstObject];
        NSInteger index = [self.imageItemArray indexOfObject:cell.model] + 1;
        NSInteger count = self.imageItemArray.count;
        self.navNameLabel.text = [NSString stringWithFormat:@"%ld/%ld",(long)index,(long)count];
    }else
    {
        self.navNameLabel.text = @"0/0";
    }
    if (self.imageItemArray == nil || self.imageItemArray.count == 0)
    {
        [self dismss];
    }
}

#pragma mark UICollectionView 
- (NSInteger)collectionView:(UICollectionView *)collectionView numberOfItemsInSection:(NSInteger)section
{
    return self.imageItemArray.count;
}

- (__kindof UICollectionViewCell *)collectionView:(UICollectionView *)collectionView cellForItemAtIndexPath:(NSIndexPath *)indexPath
{
    ChZImageItemCell *cell = [collectionView dequeueReusableCellWithReuseIdentifier:@"cell" forIndexPath:indexPath];
    cell.delegate = self;
    if (self.imageItemArray.count> indexPath.row)
    {
        cell.model = self.imageItemArray[indexPath.row];
    }
    return cell;

}
- (void)scrollViewDidEndDecelerating:(UIScrollView *)scrollView
{
    [self _config];
}

- (void)imageItemCellClick:(ChZImageItemCell *)imageItemCell
{
    [UIView animateWithDuration:0.25 animations:^{
        self.navView.hidden = !self.navView.hidden;
    }];

}



@end
