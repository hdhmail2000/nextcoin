//
//  XHHRecevableView.m
//  Manhattan
//
//  Created by Apple on 2018/8/17.
//  Copyright © 2018年 Apple. All rights reserved.
//

#import "XHHRecevableView.h"
@interface XHHRecevableView ()



@end


@implementation XHHRecevableView

/*
// Only override drawRect: if you perform custom drawing.
// An empty implementation adversely affects performance during animation.
- (void)drawRect:(CGRect)rect {
    // Drawing code
}
*/
- (IBAction)copyAction:(id)sender {
    if (!ChZ_StringIsEmpty(self.areaLable.text))
    {
        ChZ_MBError(@"钱包地址获取失败！")
        return;
    }
    UIPasteboard *pasetBoard = [UIPasteboard generalPasteboard];
    pasetBoard.string = self.areaLable.text;
    ChZ_MBSuccess(@"复制成功")
}

@end
