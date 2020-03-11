//
//  XHHSwithTypeView.m
//  FuturePurse
//
//  Created by Apple on 2018/8/29.
//  Copyright © 2018年 jbtm. All rights reserved.
//

#import "XHHSwithTypeView.h"
#import "XHHTadTypeChooesViewCell.h"
#import "ChZMarketListModel.h"
#import "XHHCoinListModel.h"
@interface XHHSwithTypeView()<UICollectionViewDelegate,UICollectionViewDataSource>

@property (strong, nonatomic) UICollectionView        *collectionView;

@property (strong, nonatomic) UILabel                  *dLable;

@property (strong, nonatomic) UIButton                 *grayButton;
@end
@implementation XHHSwithTypeView

-(instancetype)init{
    self = [super init];
    if (self) {
        [self zh_setupUI];
    }
    return self;
}

-(void)zh_setupUI{
    [self addSubview:self.grayButton];
    [self.grayButton mas_makeConstraints:^(MASConstraintMaker *make) {
        make.edges.equalTo(self);
    }];
    
    UIView *lineView1 = [[UIView alloc] init];
    lineView1.backgroundColor = [UIColor colorWithHexString:@"E7EBEE"];
    [self addSubview:lineView1];
    [lineView1 mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.right.top.equalTo(self);
        make.height.mas_equalTo(ChZ_WIDTH*2/3);
    }];
    
    [lineView1 addSubview:self.collectionView];
    [self.collectionView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.edges.equalTo(lineView1).insets(UIEdgeInsetsMake(1, 1, 1, 1));
    }];
}
-(void)setDataArray:(NSArray *)dataArray{
    _dataArray = dataArray;
    NSMutableArray *arrar = [NSMutableArray array];
    for(ChZMarketListModel *model in dataArray){
        XHHCoinListModel *m = [[XHHCoinListModel alloc] init];
        m.thumb = model.sellWebLogo;
        m.coin = model.sellShortName;
        [arrar addObject:m];
    }
    _dataArray = arrar;
    
    [self.collectionView reloadData];
}
-(UILabel *)dLable{
    if (!_dLable) {
        _dLable = [UILabel newSingleFrame:CGRectZero title:@"热卖推荐跟随潮流" fontS:12.0 color: [UIColor colorWithHexString:@"8F8F8F"]];
    }
    return _dLable;
}
-(UIButton *)grayButton{
    if (!_grayButton) {
        _grayButton = [UIButton buttonWithType:UIButtonTypeCustom];
        _grayButton.backgroundColor = [UIColor blackColor];
        _grayButton.alpha = 0.3;
        _grayButton.hidden = YES;
        @weakify(self);
        [[_grayButton rac_signalForControlEvents:UIControlEventTouchUpInside] subscribeNext:^(__kindof UIControl * _Nullable x) {
            @strongify(self);
            [self dismiss];
        }];
    }
    return _grayButton;
}
-(UICollectionView *)collectionView{
    if (!_collectionView) {
        UICollectionViewFlowLayout *flowLayout = [[UICollectionViewFlowLayout alloc]init];
        flowLayout.itemSize = CGSizeMake((ChZ_WIDTH-3)/3-0.5, ChZ_WIDTH/3-0.5);
        flowLayout.minimumLineSpacing = 1;
        flowLayout.minimumInteritemSpacing = 0;
        flowLayout.scrollDirection = UICollectionViewScrollDirectionVertical;
        
        _collectionView = [[UICollectionView alloc]initWithFrame:CGRectZero collectionViewLayout:flowLayout];
        _collectionView.delegate = self;
        _collectionView.dataSource = self;
        _collectionView.showsVerticalScrollIndicator = NO;
        _collectionView.showsHorizontalScrollIndicator = NO;
        _collectionView.backgroundColor = [UIColor colorWithHexString:@"E7EBEE"];
        [_collectionView registerNib:[UINib nibWithNibName:@"XHHTadTypeChooesViewCell" bundle:nil] forCellWithReuseIdentifier:@"XHHTadTypeChooesViewCell"];
    }
    return _collectionView;
}

-(NSInteger)numberOfSectionsInCollectionView:(UICollectionView *)collectionView{
    return 1;
}
- (NSInteger)collectionView:(UICollectionView *)collectionView numberOfItemsInSection:(NSInteger)section{
    return self.dataArray.count;
}

- (__kindof UICollectionViewCell *)collectionView:(UICollectionView *)collectionView cellForItemAtIndexPath:(NSIndexPath *)indexPath
{
    XHHTadTypeChooesViewCell *item = [collectionView dequeueReusableCellWithReuseIdentifier:@"XHHTadTypeChooesViewCell" forIndexPath:indexPath];
    XHHCoinListModel *model = self.dataArray[indexPath.row];
    [item reloadCellWithModel:model];
    return item;
}
-(void)collectionView:(UICollectionView *)collectionView didSelectItemAtIndexPath:(NSIndexPath *)indexPath{
    if (_selectedIndexBlock) {
        _selectedIndexBlock(indexPath.row);
    }
}
-(void)showInView:(UIView *)view{
    CGFloat navHeight = ChZ_IsiPhoneX ? 20 : 0;
    [view addSubview:self];
    self.frame = CGRectMake(-ChZ_WIDTH, 46, ChZ_WIDTH, ChZ_HEIGHT-2*navHeight-46);
    [UIView animateWithDuration:0.5 animations:^{
        self.alpha = 1;
        self.x = 0;
    } completion:^(BOOL finished) {
        self.grayButton.hidden = NO;
        
    }];
}
-(void)dismiss{
    [UIView animateWithDuration:0.5 animations:^{
        self.alpha = 0;
        self.x = -ChZ_WIDTH;
        self.grayButton.hidden = YES;
    } completion:^(BOOL finished) {
        
        [self removeFromSuperview];
    }];
}

@end
