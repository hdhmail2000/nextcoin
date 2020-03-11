//
//  XHHTadTypeChooesView.m
//  FuturePurse
//
//  Created by Apple on 2018/7/27.
//  Copyright © 2018年 jbtm. All rights reserved.
//

#import "XHHTadTypeChooesView.h"
#import "XHHTadTypeChooesViewCell.h"

@interface XHHTadTypeChooesView ()<UICollectionViewDelegate,UICollectionViewDataSource>

@property (strong, nonatomic) UICollectionView        *collectionView;

@property (strong, nonatomic) NSArray                 *dataArray;

@property (strong, nonatomic) UILabel                  *dLable;

@property (strong, nonatomic) UIButton                 *grayButton;

@end

@implementation XHHTadTypeChooesView


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
-(NSArray *)dataArray{
    
    if (!_dataArray) {
        _dataArray = @[@{@"image":@"trad_home_btc",@"title":@"ETH/USDT"},
                       @{@"image":@"trad_home_eth",@"title":@"XPR/USDT"},
                       @{@"image":@"trad_home_ltc",@"title":@"BTC/USDT"},
                       @{@"image":@"trad_home_eth",@"title":@"DASHUSDT"},
                       @{@"image":@"trad_home_btc",@"title":@"LTC/USDT"},
                       @{@"image":@"trad_home_btc",@"title":@"ETH/USDT"},
                       @{@"image":@"trad_home_ltc",@"title":@"ETH/USDT"},];
    }
    return _dataArray;
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
    NSDictionary *dic = self.dataArray[indexPath.row];
    [item reloadCellWithDic:dic];
    
    return item;
}
-(void)showInView:(UIView *)view{
    CGFloat navHeight = ChZ_IsiPhoneX ? 20 : 0;
    [view addSubview:self]; 
    self.frame = CGRectMake(-ChZ_WIDTH, 46, ChZ_WIDTH, ChZ_HEIGHT-2*navHeight-46);
    [UIView animateWithDuration:0.5 animations:^{
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
        self.alpha = 1;
        [self removeFromSuperview];
    }];
}
@end
