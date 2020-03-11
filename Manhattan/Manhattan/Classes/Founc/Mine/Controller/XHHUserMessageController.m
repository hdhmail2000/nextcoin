//
//  XHHUserMessageController.m
//  Manhattan
//
//  Created by Apple on 2018/8/16.
//  Copyright © 2018年 Apple. All rights reserved.
//

#import "XHHUserMessageController.h"
#import "UIImage+ChZExtension.h"
#import "TLAliyunOSS.h"
@interface XHHUserMessageController ()
@property (weak, nonatomic) IBOutlet UIImageView *headImageView;
@property (weak, nonatomic) IBOutlet UILabel *sexLable;
@property (weak, nonatomic) IBOutlet UITextField *nickName;
@property (weak, nonatomic) IBOutlet UILabel *phoneLable;

@property (nonatomic , copy) NSString *imgeAURL;

@end

@implementation XHHUserMessageController

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view.
    
    [self showAlertTitle];
    
    [self requestUserMessage];
}
-(void)showAlertTitle{
    NSString *username = [NSString chz_getObjForKey:kv_LOGIN_USERNAME];
    if (ChZ_StringIsEmpty(username))
    {
        if (username.length == 11)
        {
            self.phoneLable.text = [NSString stringWithFormat:@"+86 %@****%@",[username substringToIndex:3],[username substringFromIndex:7]];
        }
    }
}
- (IBAction)headAction:(id)sender {
    @weakify(self);
    [self selectImageEdit:YES selectedFinish:^(UIImage *image)
     {
        @strongify(self);
        if (image)
        {
            NSData *imgData = [image zipNSDataWithImage];
            ChZ_MBMsg(nil)
            [[TLAliyunOSS sharedTLAliyunOSS] updateData:imgData suffixString:@"jpg" progressBlock:^(int64_t bytesSent, int64_t totalBytesSent, int64_t totalBytesExpectedToSend)
             {
             } callBlock:^(NSError *error, NSString *filename)
             {
                 dispatch_async(dispatch_get_main_queue(), ^{
                     if (error == nil) {
                         ChZ_MBDismss
                         self.headImageView.image = image;
                         self.imgeAURL = filename;
                     }else
                     {
                         ChZ_MBDismssError(error.localizedDescription)
                     }
                 });
             }];
        }

    }];
}

- (IBAction)back:(id)sender {
    [self.navigationController popViewControllerAnimated:YES];
}

-(void)setUserModel:(XHHUserModelCenter *)userModel{
    _userModel = userModel;
    self.nickName.text = userModel.fnickname;
    if (ChZ_StringIsEmpty(userModel.favatar))
    {
        [self.headImageView sd_setImageWithURL:[NSURL URLWithString:userModel.favatar]];
    }
    if(ChZ_StringIsEmpty(userModel.fsex))
    {
        self.sexLable.text = userModel.fsex;
    }else{
        self.sexLable.text = @"未设置";
    }
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}
- (IBAction)sexAction:(UIButton *)sender {
    UIAlertController *sheetController = [UIAlertController alertControllerWithTitle:@"请选择性别" message:nil preferredStyle:UIAlertControllerStyleActionSheet];
    UIAlertAction *action2 = [UIAlertAction actionWithTitle:@"男" style:UIAlertActionStyleDefault handler:^(UIAlertAction * _Nonnull action) {
        self.sexLable.text = @"男";
    }];
    UIAlertAction *action3 = [UIAlertAction actionWithTitle:@"女" style:UIAlertActionStyleDefault handler:^(UIAlertAction * _Nonnull action) {
        self.sexLable.text = @"女";
    }];
    
    UIAlertAction *actionCancel = [UIAlertAction actionWithTitle:@"取消" style:UIAlertActionStyleDefault handler:^(UIAlertAction * _Nonnull action) {
        
    }];
    [sheetController addAction: action2];
    [sheetController addAction: action3];
    [sheetController addAction: actionCancel];
    [self presentViewController:sheetController animated:YES completion:nil];
}
- (IBAction)keepAction:(id)sender {
    [self updateUserMessage];
}
-(void)requestUserMessage{
    @weakify(self);
    [XHHSafeCenterRequest requestUserMessageSuccessBlock:^(id dataSource)
     {
         @strongify(self);
         XHHUserModelCenter *model = dataSource;
         self.userModel = model;
     } errorBlock:^(int code, NSString *error)
     {
         ChZ_DebugLog(@"%@--",error);
     }];
}
-(void)updateUserMessage{
    ChZ_MBMsg(nil)
//    NSString *imageBase64 = [self.headImageView.image base64String];
    @weakify(self);
    [XHHSafeCenterRequest requestUpdateUserMessageNickName:self.nickName.text sex:self.sexLable.text headImageUrl:self.imgeAURL successBlock:^(id dataSource) {
        ChZ_MBDismss
        @strongify(self);
        [[NSNotificationCenter defaultCenter] postNotificationName:@"UPDATEUSERMESSAGESUCCESS" object:nil userInfo:nil];
        [self pop];
    } errorBlock:^(int code, NSString *error) {
        ChZ_MBDismss
        ChZ_DebugLog(@"%@--",error);
    }];
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
