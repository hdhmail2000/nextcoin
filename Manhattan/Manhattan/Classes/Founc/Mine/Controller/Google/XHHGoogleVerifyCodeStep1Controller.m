//
//  XHHGoogleVerifyCodeStep1Controller.m
//  FuturePurse
//
//  Created by muye01 on 2018/7/30.
//  Copyright © 2018年 jbtm. All rights reserved.
//

#import "XHHGoogleVerifyCodeStep1Controller.h"
#import "XHHGoogleVerifyCodeStep2Controller.h"
//#import "CenterNetWorkControl.h"
#import "UIImage+ChZExtension.h"
@interface XHHGoogleVerifyCodeStep1Controller ()
@property (weak, nonatomic) IBOutlet UIImageView *QRCodeImageView;
@property (weak, nonatomic) IBOutlet UILabel *accountLabel;
@property (weak, nonatomic) IBOutlet UILabel *pwdLabel;

@end

@implementation XHHGoogleVerifyCodeStep1Controller

- (void)viewDidLoad {
    [super viewDidLoad];
//    self.view.backgroundColor = CHZ_ColorFromHex(0xe7eced);
    _accountLabel.text = self.settingModel.device_name;
    
    UITapGestureRecognizer *gesture = [[UITapGestureRecognizer alloc]initWithTarget:self action:@selector(copyAction:)];
    _accountLabel.userInteractionEnabled = YES;
    [_accountLabel addGestureRecognizer:gesture];
    
    UITapGestureRecognizer *gesture1 = [[UITapGestureRecognizer alloc]initWithTarget:self action:@selector(copyAction:)];
    _pwdLabel.userInteractionEnabled = YES;
    [_pwdLabel addGestureRecognizer:gesture1];
    
    [self requestQrcode];
}
- (IBAction)back:(id)sender
{
    [self pop];
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

- (IBAction)downloadGoogleAction:(id)sender
{
    [[UIApplication sharedApplication] openURL:[NSURL URLWithString:@"http://app.weigongju.org/965563"]];
}
-(void)copyAction:(UIGestureRecognizer *)sender
{
    UILabel *lable = (UILabel *)sender.view;
    UIPasteboard *pasetBoard = [UIPasteboard generalPasteboard];
    pasetBoard.string = lable.text;
    ChZ_MBSuccess(@"复制成功")
}
-(void)requestQrcode
{
    @weakify(self);
    [XHHSafeCenterRequest requestGetGoogleAuthCodeWithSuccessBlock:^(id dataSource)
     {
         @strongify(self);
         NSDictionary *dict = dataSource;
         NSString *qrCode = dict[@"qecode"];
         NSString *totpKey = dict[@"totpKey"];
         if(ChZ_StringIsEmpty(totpKey)) self.pwdLabel.text = totpKey;
         if(ChZ_StringIsEmpty(qrCode)) self.QRCodeImageView.image = [UIImage qrImageForString:qrCode imageSize:self.QRCodeImageView.width];
         
     } errorBlock:^(int code, NSString *error)
     {
         ChZ_MBError(error);
     }];
}
- (IBAction)nextActrion:(id)sender
{
    XHHGoogleVerifyCodeStep2Controller *vc = [XHHGoogleVerifyCodeStep2Controller initWithStoryboard:@"MineStroy"];
    vc.totpKey= self.pwdLabel.text;
    [self.navigationController pushViewController:vc animated:YES];
}


#pragma mark - Navigation

// In a storyboard-based application, you will often want to do a little preparation before navigation
- (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender
{
    NSString *identifier = segue.identifier;
    if ([identifier isEqualToString:NSStringFromClass(XHHGoogleVerifyCodeStep2Controller.class)])
    {
        XHHGoogleVerifyCodeStep2Controller *vc = segue.destinationViewController;
        vc.title = @"Google验证码";
        vc.totpKey= self.pwdLabel.text;
    }
}
@end
