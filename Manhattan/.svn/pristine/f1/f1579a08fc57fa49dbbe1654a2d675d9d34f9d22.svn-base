//
//  XTTMyPurseBottomView.m
//  FuturePurse
//
//  Created by muye01 on 2018/7/31.
//  Copyright © 2018年 jbtm. All rights reserved.
//

#import "XTTMyPurseBottomView.h"
//#import "XTTCollectionViewEqualSpaceFlowLayout.h"

@interface XTTMyPurseTableBottomCell()
@property (weak, nonatomic) IBOutlet UILabel *titleLabel;

@end

@implementation XTTMyPurseTableBottomCell
-(instancetype)initWithFrame:(CGRect)frame{
    self = [super initWithFrame:frame];
    if (self) {
//        self = [XTTMyPurseTableBottomCell loadViewWithNibName:@"MyPurseView" atIndex:2];

        
    }
    return self;

}
-(void)awakeFromNib{
    [super awakeFromNib];
   
    
}
- (void)setTitle:(NSString *)title{
    _titleLabel.text = title;
}

@end

@interface XTTMyPurseBottomView()<UICollectionViewDelegate, UICollectionViewDataSource>

@property (nonatomic, strong) UICollectionView *collectionView;

@end

@implementation XTTMyPurseBottomView

-(void)awakeFromNib{
    [super awakeFromNib];
    [self addSubview:self.collectionView];
}
-(UICollectionView *)collectionView{
    if (!_collectionView) {
//        XTTCollectionViewEqualSpaceFlowLayout *layout = [[XTTCollectionViewEqualSpaceFlowLayout alloc] initWthType:XTTFlowLayoutAlignWithCenter];
//        layout.minimumInteritemSpacing = 0.;
//        layout.minimumLineSpacing = 0.;//水平
//        layout.scrollDirection = UICollectionViewScrollDirectionHorizontal;
//
//        _collectionView = [[UICollectionView alloc]initWithFrame:self.bounds collectionViewLayout:layout];
//        _collectionView.delegate = self;
//        _collectionView.dataSource = self;
//        _collectionView.showsHorizontalScrollIndicator = NO;
//        [_collectionView registerClass:XTTMyPurseTableBottomCell.class forCellWithReuseIdentifier:@"XTTMyPurseTableBottomCell"];
//        _collectionView.autoresizingMask = UIViewAutoresizingFlexibleWidth | UIViewAutoresizingFlexibleHeight;
//        _collectionView.backgroundColor = [UIColor whiteColor];
    }
    return _collectionView;
}
-(void)setTitles:(NSArray *)titles{
    _titles = titles;
    [_collectionView reloadData];
}
- (NSInteger)collectionView:(UICollectionView *)collectionView numberOfItemsInSection:(NSInteger)section{
    return _titles.count;
}

- (CGSize)collectionView:(UICollectionView *)collectionView layout:(UICollectionViewLayout*)collectionViewLayout sizeForItemAtIndexPath:(NSIndexPath *)indexPath {
    CGFloat width = collectionView.width / _titles.count;
    return CGSizeMake(width, collectionView.height);
}
- (UICollectionViewCell *)collectionView:(UICollectionView *)collectionView cellForItemAtIndexPath:(NSIndexPath *)indexPath{
    XTTMyPurseTableBottomCell *cell = [collectionView dequeueReusableCellWithReuseIdentifier:@"XTTMyPurseTableBottomCell" forIndexPath:indexPath];
    cell.title = _titles[indexPath.row];
    return cell;
    
}

- (void)collectionView:(UICollectionView *)collectionView didSelectItemAtIndexPath:(NSIndexPath *)indexPath{
    if (self.didSelectItemBlock) {
        self.didSelectItemBlock(indexPath.item, _titles[indexPath.item]);
    }
}
@end
