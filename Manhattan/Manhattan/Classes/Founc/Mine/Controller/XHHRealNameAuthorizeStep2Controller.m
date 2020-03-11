//
//  XHHRealNameAuthorizeStep2Controller.m
//  FuturePurse
//
//  Created by muye01 on 2018/7/27.
//  Copyright © 2018年 jbtm. All rights reserved.
//

#import "XHHRealNameAuthorizeStep2Controller.h"
#import "UIViewController+ChZExtension.h"
#import "XHHAuthSuccessController.h"
//#import "XHHRealNameAuthorizeStep3Controller.h"
//#import "CenterNetWorkControl.h"

@interface XHHRealNameAuthorizeStep2Controller ()
@property (weak, nonatomic) IBOutlet NSLayoutConstraint *bottonView_constraint_height;

@property (weak, nonatomic) IBOutlet UIButton *submitBtn;
@property (weak, nonatomic) IBOutlet UIImageView *facedPhotoImageView;
@property (weak, nonatomic) IBOutlet UIImageView *backsidePhotoImageView;
@property (weak, nonatomic) IBOutlet UIImageView *handheldPhotoImageView;

@property (weak, nonatomic) IBOutlet UIImageView *facePlaceholder;
@property (weak, nonatomic) IBOutlet UILabel *faceLabel;

@property (weak, nonatomic) IBOutlet UIImageView *backsidePlaceholder;
@property (weak, nonatomic) IBOutlet UILabel *backsideLabel;

@property (weak, nonatomic) IBOutlet UIImageView *handheldPlaceholder;
@property (weak, nonatomic) IBOutlet UILabel *handheldLabel;

@end

@implementation XHHRealNameAuthorizeStep2Controller

- (void)viewDidLoad {
    [super viewDidLoad];
    
    //[self setLeftItemWithImage:[UIImage imageNamed:@"login_froget_back"] withAction:@selector(back)];
//    [self setNavigationBarBackgroundWithImageName:@"public_nav_bg"];
    self.navigationController.navigationBar.shadowImage = [UIImage imageNamed:@"public_nav_shadowImage"];
    
    [self.submitBtn setBackgroundImage:[UIImage imageNamed:@"login_loginbutton_bg"] forState:UIControlStateNormal];
//    [self.submitBtn setBackgroundImage:[UIImage imageWithColor:CHZ_ColorFromHex(0xF1F2F3)] forState:UIControlStateDisabled];
    [self config];
    [self setRACFormat];
}
- (IBAction)back:(id)sender {
    [self pop];
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

-(void)viewDidLayoutSubviews{
    [super viewDidLayoutSubviews];
    _bottonView_constraint_height.constant = CGRectGetMaxY(self.submitBtn.frame) + 40;
}

-(void)config
{
    [self configImageView:self.facedPhotoImageView];
    [self configImageView:self.backsidePhotoImageView];
    [self configImageView:self.handheldPhotoImageView];
}
-(void)configImageView:(UIImageView *)photoImageView
{
    photoImageView.userInteractionEnabled = YES;
    UITapGestureRecognizer *gesture = [[UITapGestureRecognizer alloc] initWithTarget:self action:@selector(didClickPhotoImageView:)];
    [photoImageView addGestureRecognizer:gesture];
    
    
}

-(void)setRACFormat{
    RACSignal *faceSigal = RACObserve(self,self.facedPhotoImageView.image);
    RACSignal *faceValid = [faceSigal map:^id (UIImage * value) {
        return @(value != nil);
    }];
    
    RACSignal *backsideSignal = RACObserve(self, self.backsidePhotoImageView.image);
    RACSignal *backsideValid = [backsideSignal map:^id (UIImage * value) {
        return @(value != nil);
    }];
    
    RACSignal *handheldSignal = RACObserve(self, self.handheldPhotoImageView.image);
    RACSignal *handheldValid =  [handheldSignal map:^id (UIImage * value) {
        return @(value != nil);

    }];

    RACSignal *formatValid = [RACSignal combineLatest:@[
                                                        faceSigal,
                                                        backsideSignal,
                                                        handheldSignal
                                                        
                                                        ] reduce:^(UIImage *face, UIImage *backside, UIImage *handHeld){
                                                            return @(face != nil && backside != nil && handHeld != nil );
                                                        }];
    
    RAC(self.submitBtn, enabled) = formatValid;
    

    RAC(self.facePlaceholder, hidden) = faceValid;
    RAC(self.faceLabel, hidden) = faceValid;
    
    RAC(self.backsidePlaceholder, hidden) = backsideValid;
    RAC(self.backsideLabel, hidden) = backsideValid;

    
    RAC(self.handheldPlaceholder, hidden) = handheldValid;
    RAC(self.handheldLabel, hidden) = handheldValid;
}
#pragma mark- action
-(void)didClickPhotoImageView:(UITapGestureRecognizer *)sender
{
  __block UIImageView *photoImageView = (UIImageView *)sender.view;
    @weakify(self);
    [self selectImageEdit:YES selectedFinish:^(UIImage *image) {
        @strongify(self);
        
        if ([photoImageView isEqual:self.facedPhotoImageView]) {
            
            self.facedPhotoImageView.image = image;
            
        }else if ([photoImageView isEqual:self.backsidePhotoImageView]){
            self.backsidePhotoImageView.image = image;
            
        }else if([photoImageView isEqual:self.handheldPhotoImageView]){
            self.handheldPhotoImageView.image = image;
        }
        
    }];
}
- (IBAction)submitAction:(id)sender
{
    [self requestRealNameAuthWithName:self.realName identityNum:self.IDNum addressStr:nil];
}

- (void)requestRealNameAuthWithName:(NSString *)realName
                        identityNum:(NSString *)identityNum
                         addressStr:(NSString *)addressStr{
    
    ChZ_MBMsg(nil)
    [XHHSafeCenterRequest requestRealNameAuthWithName:realName identityType:@"0" identityNum:identityNum addressStr:addressStr successBlock:^(id dataSource) {
        ChZ_MBDismssSuccess(@"提交成功!");
        [[NSNotificationCenter defaultCenter] postNotificationName:@"AuthSuccess" object:nil];
        XHHAuthSuccessController *vc = [XHHAuthSuccessController initWithStoryboard:@"LoginStroy"];
        [self.navigationController pushViewController:vc animated:YES];

    } errorBlock:^(int code, NSString *error) {
        ChZ_MBDismssError(error)

    }];

}
@end