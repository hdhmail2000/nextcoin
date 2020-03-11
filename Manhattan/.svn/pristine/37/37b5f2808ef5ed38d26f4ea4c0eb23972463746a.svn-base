//
//  XHHTradNoticeController.m
//  FuturePurse
//
//  Created by Apple on 2018/7/30.
//  Copyright © 2018年 jbtm. All rights reserved.
//

#import "XHHTradNoticeController.h"

#import "XHHTradNoriceDeatilController.h"

#import "XHHPublicItemsView.h"

#import "XHHTradNoticeCell.h"

#import "XHHNotesItemModel.h"

static NSString *cellIdentifier = @"XHHTradNoticeCell";

@interface XHHTradNoticeController ()<XHHPublicItemsViewProtocol,UICollectionViewDelegate,UICollectionViewDataSource>

@property (strong, nonatomic) NSArray                     *rightArray;

@property (strong, nonatomic) XHHPublicItemsView          *itemsView;

@property (strong, nonatomic) UICollectionView            *rightCollectionView;

@property (nonatomic , copy) NSArray                      *itemsArray;

@property (nonatomic , copy) NSString                     *catId;

@property (assign, nonatomic) NSInteger                   page;

@end

@implementation XHHTradNoticeController
- (void)viewDidLoad {
    [super viewDidLoad];
    
    [self requestItems];
    // Do any additional setup after loading the view.
}
-(void)zh_setupUI{
    
    CGFloat navHeight = ChZ_IsiPhoneX ? 20 : 0;
    
    [self.view addSubview:self.itemsView];
    [self.itemsView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.right.top.equalTo(self.view);
        make.height.mas_equalTo(46);
    }];
    
    [self.view addSubview:self.rightCollectionView];
    [self.rightCollectionView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(self.view).offset(15);
        make.right.equalTo(self.view).offset(-15);
        make.bottom.equalTo(self.view).offset(-navHeight);
        make.top.equalTo(self.itemsView.mas_bottom);
    }];
}
-(void)back{
    [self.navigationController popViewControllerAnimated:YES];
    [self.navigationController setNavigationBarHidden:YES animated:YES];
}
-(XHHPublicItemsView *)itemsView{
    if (!_itemsView) {
        _itemsView = [[XHHPublicItemsView alloc] init];
        _itemsView.delegate = self;
    }
    return _itemsView;
}
-(void)itemsClickIndex:(NSInteger)index{
    XHHNotesItemModel *model = self.itemsArray[index];
    self.catId = model.fid;
    [self.rightCollectionView.mj_header beginRefreshing];
}

-(UICollectionView *)rightCollectionView{
    if (!_rightCollectionView) {
        UICollectionViewFlowLayout *flowLayout = [[UICollectionViewFlowLayout alloc]init];
        //        flowLayout.itemSize = CGSizeMake(20,60);
        flowLayout.itemSize = CGSizeMake((ChZ_WIDTH-39)/2-1, (ChZ_WIDTH-39)/2 + 50);
        flowLayout.minimumLineSpacing = 7;
        flowLayout.minimumInteritemSpacing = 10;
        flowLayout.scrollDirection = UICollectionViewScrollDirectionVertical;
        
        _rightCollectionView = [[UICollectionView alloc]initWithFrame:CGRectZero collectionViewLayout:flowLayout];
        _rightCollectionView.delegate = self;
        _rightCollectionView.dataSource = self;
        _rightCollectionView.showsVerticalScrollIndicator = NO;
        _rightCollectionView.showsHorizontalScrollIndicator = NO;
        _rightCollectionView.backgroundColor = [UIColor whiteColor];
        [self setupRefreshWithView:_rightCollectionView refreshHeadAction:@selector(headRefresh) refreshFooterAction:@selector(footRefresh)];
        [_rightCollectionView registerClass:[XHHTradNoticeCell class] forCellWithReuseIdentifier:cellIdentifier];
    }
    return _rightCollectionView;
}
#pragma collectionview delegate
-(NSInteger)numberOfSectionsInCollectionView:(UICollectionView *)collectionView{
    return 1;
}
- (NSInteger)collectionView:(UICollectionView *)collectionView numberOfItemsInSection:(NSInteger)section{
    
    return self.rightArray.count;
}
- (__kindof UICollectionViewCell *)collectionView:(UICollectionView *)collectionView cellForItemAtIndexPath:(NSIndexPath *)indexPath
{
    XHHTradNoticeCell *item = [collectionView dequeueReusableCellWithReuseIdentifier:cellIdentifier forIndexPath:indexPath];
    XHHNotesListModel *model = self.rightArray[indexPath.row];
    [item reloadCellWithModel:model];
    return item;
}
-(void)collectionView:(UICollectionView *)collectionView didSelectItemAtIndexPath:(NSIndexPath *)indexPath{
    XHHNotesListModel *model = self.rightArray[indexPath.row];
    XHHTradNoriceDeatilController *vc = [[XHHTradNoriceDeatilController alloc] init];
    vc.contentId = model.fid;
    vc.type = 2;
    [self.navigationController pushViewController:vc animated:YES];
}
-(void)requestItems{
    @weakify(self);
    [ChZHomeRequest requestNotItemsSuccessBlock:^(id dataSource) {
        @strongify(self);
        NSMutableArray *arr = [NSMutableArray array];
        for(XHHNotesItemModel *model in dataSource){
            [arr addObject:model.name];
        }
        [self.itemsView zh_setupUIWithItems:arr];
        self.itemsArray = dataSource;
        XHHNotesItemModel *model = dataSource[0];
        self.catId = model.fid;
        [self.rightCollectionView.mj_header beginRefreshing];
    } errorBlock:^(int code, NSString *error) {
        NSLog(@"%@",error);
    }];
}
-(void)headRefresh{
    self.page = 1;
    
    [self requestNotesList];
}
-(void)footRefresh{
    self.page ++;
    [self requestNotesList];
}

-(void)requestNotesList{
    @weakify(self);
    [ChZHomeRequest rerquestNotesListPage:[NSString stringWithFormat:@"%ld",self.page] catid:self.catId successBlock:^(id dataSource) {
        @strongify(self);
        self.rightArray = dataSource;
        [self.rightCollectionView reloadData];
        [self.rightCollectionView.mj_header endRefreshing];
        [self.rightCollectionView.mj_footer endRefreshing];
    } errorBlock:^(int code, NSString *error) {
        @strongify(self);
        [self.rightCollectionView.mj_header endRefreshing];
        [self.rightCollectionView.mj_footer endRefreshing];
        NSLog(@"%@",error);
    }];
}
- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

/*
#pragma mark - Navigation

// In a storyboard-based application, you will often want to do a little preparation before navigation
- (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender {
    // Get the new view controller using [segue destinationViewController].
    // Pass the selected object to the new view controller.
}
*/

@end
