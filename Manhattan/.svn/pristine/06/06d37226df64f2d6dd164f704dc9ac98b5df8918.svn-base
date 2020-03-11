//
//  XHHTradNotesView.m
//  FuturePurse
//
//  Created by Apple on 2018/7/25.
//  Copyright © 2018年 jbtm. All rights reserved.
//

#import "XHHTradNotesView.h"

@interface XHHTradNotesView()<UIScrollViewDelegate>

@property (weak, nonatomic) IBOutlet UIScrollView *bgScrollow;

@property (nonatomic, strong)NSTimer *timer; //定时器

@property (strong, nonatomic) NSArray *notes;

@end

@implementation XHHTradNotesView

/*
// Only override drawRect: if you perform custom drawing.
// An empty implementation adversely affects performance during animation.
- (void)drawRect:(CGRect)rect {
    // Drawing code
}
*/

-(void)creatNotes:(NSArray *)notes{
    self.notes = notes;
    for(int i = 0; i < notes.count;i++){
        UILabel *titleLable = [UILabel newSingleFrame:CGRectMake(0, 40 * i, self.bgScrollow.width, 40) title:notes[i] fontS:12.0 color:[UIColor colorWithHexString:@"56504D"]];
        [_bgScrollow addSubview:titleLable];
    }
    _bgScrollow.contentSize  = CGSizeMake(ChZ_WIDTH-36, notes.count * 40);
    _bgScrollow.delegate = self;
    [self addTimer];
}
//开启定时器
- (void)addTimer{
    //设置定时器
    self.timer = [NSTimer scheduledTimerWithTimeInterval:3 target:self selector:@selector(change:) userInfo:nil repeats:YES];
    
}
//定时器执行方法
- (void)change:(NSTimer *)time{
    [self.bgScrollow setContentOffset:CGPointMake(0, 40+self.bgScrollow.contentOffset.y) animated:YES];
}

-(void)scrollViewDidScroll:(UIScrollView *)scrollView{
    if (scrollView.contentOffset.y == (self.notes.count - 1) * 40){
        scrollView.contentOffset = CGPointMake(0, 0);
    }
    
}
@end
