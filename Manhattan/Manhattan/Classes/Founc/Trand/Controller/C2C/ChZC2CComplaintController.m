
//
//  ChZC2CComplaintController.m
//  CoinWallet
//
//  Created by Howe on 2018/9/13.
//  Copyright © 2018年 Howe. All rights reserved.
//

#import "ChZC2CComplaintController.h"
#import "UIImage+ChZExtension.h"
@interface ChZC2CComplaintController ()
@property (weak, nonatomic) IBOutlet UITextView *textView;
@property (weak, nonatomic) IBOutlet UIImageView *imgView;
@property (nonatomic, strong) UIImage *upImage;
@end

@implementation ChZC2CComplaintController


- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view from its nib.
}

- (IBAction)backAction:(id)sender {
    [self pop];
}

- (IBAction)upimgAction:(id)sender
{
    @weakify(self);
    [self selectImageEdit:NO selectedFinish:^(UIImage *image)
    {
        @strongify(self);
        self.upImage = image;
        self.imgView.image = image;
    }];
}
- (IBAction)submitAction:(id)sender
{
    NSString *remark = self.textView.text;
    if (!ChZ_StringIsEmpty(remark)) {
        ChZ_MBError(@"请输入申述原因");
        return;
    }
    if (self.upImage == nil) {
        ChZ_MBError(@"上传相关图片证据");
        return;
    }
    NSString *imgData = [self.upImage base64String];
    @weakify(self);
    ChZ_MBMsg(nil)
    [ChZHomeRequest requestC2CcomplaintWithOrderId:self.orderId
                                          imgdata:imgData
                                           remark:remark
                                     successBlock:^(id dataSource)
    {
        @strongify(self);
        if (self.callBackBlock) {
            self.callBackBlock(nil);
        }
        ChZ_MBDismssSuccess(@"提交成功");
        dispatch_after(dispatch_time(DISPATCH_TIME_NOW, (int64_t)(1 * NSEC_PER_SEC)), dispatch_get_main_queue(), ^{
            [self pop];
        });
    } errorBlock:^(NSString *error)
    {
        ChZ_MBDismssError(error);
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
