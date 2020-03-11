//
//  XHHPWInputView.m
//  Manhattan
//
//  Created by Apple on 2018/8/17.
//  Copyright © 2018年 Apple. All rights reserved.
//

#import "XHHPWInputView.h"

#import "XHHPayPwController.h"

@interface XHHPWInputView ()
@property (strong, nonatomic) IBOutlet UITextField *firstInputTxtField;
@property (strong, nonatomic) IBOutlet UITextField *secondInputTxtField;
@property (strong, nonatomic) IBOutlet UITextField *thirdInputTxtField;
@property (strong, nonatomic) IBOutlet UITextField *fourthInputTxtField;
@property (strong, nonatomic) IBOutlet UITextField *fifthInputTxtField;
@property (strong, nonatomic) IBOutlet UITextField *sixthInputTxtField;

@end


@implementation XHHPWInputView
+ (instancetype)initXibFileWithDelegate:(id)target{
    XHHPWInputView *inputView = [[[NSBundle mainBundle] loadNibNamed:@"XHHPWInputView" owner:nil options:nil] firstObject];
    inputView.firstInputTxtField.delegate = target;
    inputView.secondInputTxtField.delegate = target;
    inputView.thirdInputTxtField.delegate = target;
    inputView.fourthInputTxtField.delegate = target;
    inputView.fifthInputTxtField.delegate = target;
    inputView.sixthInputTxtField.delegate = target;
    inputView.secondInputTxtField.userInteractionEnabled = NO;
    inputView.thirdInputTxtField.userInteractionEnabled = NO;
    inputView.fourthInputTxtField.userInteractionEnabled = NO;
    inputView.fifthInputTxtField.userInteractionEnabled = NO;
    inputView.sixthInputTxtField.userInteractionEnabled = NO;
    return inputView;
}
- (void)clearValue{
    self.firstInputTxtField.text = @"";
    self.secondInputTxtField.text = @"";
    self.thirdInputTxtField.text = @"";
    self.fourthInputTxtField.text = @"";
    self.fifthInputTxtField.text = @"";
    self.sixthInputTxtField.text = @"";
    
    self.secondInputTxtField.userInteractionEnabled = NO;
    self.thirdInputTxtField.userInteractionEnabled = NO;
    self.fourthInputTxtField.userInteractionEnabled = NO;
    self.fifthInputTxtField.userInteractionEnabled = NO;
    self.sixthInputTxtField.userInteractionEnabled = NO;
    
}

- (void)makeFirstInputBecomeFirstResponder{
    [self.firstInputTxtField becomeFirstResponder];
}

- (NSString *)returnFirstInputViewValue{
    return self.firstInputTxtField.text;
}

- (NSString *)returnSecondInputViewValue{
    return self.secondInputTxtField.text;
}

- (NSString *)returnThirdInputViewValue{
    return self.thirdInputTxtField.text;
}

- (NSString *)returnFourthInputViewValue{
    return self.fourthInputTxtField.text;
}

- (NSString *)returnFifthInputViewValue{
    return self.fifthInputTxtField.text;
}

- (NSString *)returnSixthInputViewValue{
    return self.sixthInputTxtField.text;
}
- (void)moveFirstResponder{
    if([self.firstInputTxtField isFirstResponder]){
//        [self.firstInputTxtField resignFirstResponder];
        self.secondInputTxtField.userInteractionEnabled = YES;
        [self.secondInputTxtField becomeFirstResponder];
    }else if([self.secondInputTxtField isFirstResponder]){
//        [self.secondInputTxtField resignFirstResponder];
        self.thirdInputTxtField.userInteractionEnabled = YES;
        [self.thirdInputTxtField becomeFirstResponder];
    }else if([self.thirdInputTxtField isFirstResponder]){
        self.fourthInputTxtField.userInteractionEnabled = YES;
//        [self.thirdInputTxtField resignFirstResponder];
        [self.fourthInputTxtField becomeFirstResponder];
    }else if([self.fourthInputTxtField isFirstResponder]){
        self.fifthInputTxtField.userInteractionEnabled = YES;
//        [self.fourthInputTxtField resignFirstResponder];
        [self.fifthInputTxtField becomeFirstResponder];
    }else if([self.fifthInputTxtField isFirstResponder]){
        self.sixthInputTxtField.userInteractionEnabled = YES;
//        [self.fifthInputTxtField resignFirstResponder];
        [self.sixthInputTxtField becomeFirstResponder];
    }
}

- (void)moveBackDeleteResponder{
    if([self.sixthInputTxtField isFirstResponder]){
        [self.sixthInputTxtField resignFirstResponder];
        [self.fifthInputTxtField becomeFirstResponder];
    }else if([self.fifthInputTxtField isFirstResponder]){
        [self.fifthInputTxtField resignFirstResponder];
        [self.fourthInputTxtField becomeFirstResponder];
    }else if([self.fourthInputTxtField isFirstResponder]){
        [self.fourthInputTxtField resignFirstResponder];
        [self.thirdInputTxtField becomeFirstResponder];
    }else if([self.thirdInputTxtField isFirstResponder]){
        [self.thirdInputTxtField resignFirstResponder];
        [self.secondInputTxtField becomeFirstResponder];
    }else if([self.secondInputTxtField isFirstResponder]){
        [self.secondInputTxtField resignFirstResponder];
        [self.firstInputTxtField becomeFirstResponder];
    }else if([self.firstInputTxtField isFirstResponder]){
        
    }
}
- (BOOL)isSixthInputFinished{
    if([self.firstInputTxtField.text isEqualToString:@""]){
        return NO;
    }else if([self.secondInputTxtField.text isEqualToString:@""]){
        return NO;
    }else if([self.thirdInputTxtField.text isEqualToString:@""]){
        return NO;
    }else if([self.fourthInputTxtField.text isEqualToString:@""]){
        return NO;
    }else if([self.fifthInputTxtField.text isEqualToString:@""]){
        return NO;
    }else if([self.sixthInputTxtField.text isEqualToString:@""]){
        return NO;
    }
    return YES;
}

// 向左开始响应
- (IBAction)leftBackBtnClicked:(UIButton *)sender {
    if([self.firstInputTxtField isFirstResponder]){
        return;
    }else if([self.secondInputTxtField isFirstResponder]){
        [self.secondInputTxtField resignFirstResponder];
        [self.firstInputTxtField becomeFirstResponder];
    }else if([self.thirdInputTxtField isFirstResponder]){
        [self.thirdInputTxtField resignFirstResponder];
        [self.secondInputTxtField becomeFirstResponder];
    }else if([self.fourthInputTxtField isFirstResponder]){
        [self.fourthInputTxtField resignFirstResponder];
        [self.thirdInputTxtField becomeFirstResponder];
    }else if([self.fifthInputTxtField isFirstResponder]){
        [self.fifthInputTxtField resignFirstResponder];
        [self.fourthInputTxtField becomeFirstResponder];
    }else if([self.sixthInputTxtField isFirstResponder]){
        [self.sixthInputTxtField resignFirstResponder];
        [self.fifthInputTxtField becomeFirstResponder];
    }
}
- (IBAction)closeAction:(id)sender {
    if (_closeBlock) {
        _closeBlock();
    }
}
- (IBAction)finishAction:(id)sender {
    if (_finishBlock) {
        _finishBlock();
    }
}
- (IBAction)forgetPwAction:(UIButton *)sender {
    XHHPayPwController *vc = [XHHPayPwController initWithStoryboard:@"MineStroy"];
    vc.type = 3;
    [self.viewController.navigationController pushViewController:vc animated:YES];
}
/*
// Only override drawRect: if you perform custom drawing.
// An empty implementation adversely affects performance during animation.
- (void)drawRect:(CGRect)rect {
    // Drawing code
}
*/

@end
