//
//  MHCityViewController.m
//  Manhattan
//
//  Created by Apple on 2018/8/14.
//  Copyright © 2018年 Apple. All rights reserved.
//

#import "MHCityViewController.h"
#import "XHHMHCityCell.h"
static NSString *cellIndifier = @"XHHMHCityCell";
@interface MHCityViewController ()<UICollectionViewDelegate,UICollectionViewDataSource>

@property (weak, nonatomic) IBOutlet UICollectionView *collectionView;

@property (nonatomic , strong) NSArray *dataArray;

@end

@implementation MHCityViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    
    self.noHiddenNav = YES;
    
    UICollectionViewFlowLayout *flowyout = [[UICollectionViewFlowLayout alloc] init];
    flowyout.itemSize = CGSizeMake(ChZ_WIDTH/3, ChZ_WIDTH/3);
    flowyout.minimumLineSpacing = 0;
    flowyout.minimumInteritemSpacing = 0;
    flowyout.scrollDirection = UICollectionViewScrollDirectionVertical;
    _collectionView.collectionViewLayout = flowyout;
    _collectionView.delegate = self;
    _collectionView.dataSource = self;
    [_collectionView registerNib:[UINib nibWithNibName:@"XHHMHCityCell" bundle:nil] forCellWithReuseIdentifier:cellIndifier];
    
    UIImageView *img = [[UIImageView alloc] init];
    img.image = [UIImage imageNamed:@"mhcity_home_demo"];
    [self.view addSubview:img];
    [img mas_makeConstraints:^(MASConstraintMaker *make) {
        make.edges.equalTo(self.collectionView);
    }];
    
}
-(NSArray *)dataArray{
    if (!_dataArray) {
        _dataArray = @[@{@"image":@"mhcity_home_recomend",@"title":@"推荐"},
                       @{@"image":@"mhcity_home_market",@"title":@"行情"},
                       @{@"image":@"mhcity_home_sever",@"title":@"服务"},
                       @{@"image":@"mhcity_home_conduct",@"title":@"理财"},
                       @{@"image":@"mhcity_home_live",@"title":@"生活"},
                       @{@"image":@"mhcity_home_vip",@"title":@"VIP"},
                       @{@"image":@"mhcity_home_task",@"title":@"任务"},
                       @{@"image":@"mhcity_home_reward",@"title":@"奖励"},
                       @{@"image":@"mhcity_home_find",@"title":@"发现"},
                       @{@"image":@"mhcity_home_crown",@"title":@"任务"},
                       @{@"image":@"mhcity_home_game",@"title":@"游戏"},
                       @{@"image":@"mhcity_home_hot",@"title":@"热度"},
                       @{@"image":@"mhcity_home_card",@"title":@"卡片"},
                       @{@"image":@"mhcity_home_wallet",@"title":@"钱包"},
                       @{@"image":@"mhcity_home_play",@"title":@"娱乐"},
                       ];
    }
    return _dataArray;
}

-(NSInteger)numberOfSectionsInCollectionView:(UICollectionView *)collectionView{
    return 1;
}
-(NSInteger)collectionView:(UICollectionView *)collectionView numberOfItemsInSection:(NSInteger)section{
    return self.dataArray.count;
}
-(UICollectionViewCell *)collectionView:(UICollectionView *)collectionView cellForItemAtIndexPath:(NSIndexPath *)indexPath{
    XHHMHCityCell *cell = [collectionView dequeueReusableCellWithReuseIdentifier:cellIndifier forIndexPath:indexPath];
    NSDictionary *dic = self.dataArray[indexPath.row];
    [cell reloadCellWithDic:dic];
    return cell;
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
