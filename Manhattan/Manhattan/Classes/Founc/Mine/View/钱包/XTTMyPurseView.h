//
//  XTTMyPurseView.h
//  FuturePurse
//
//  Created by muye01 on 2018/7/31.
//  Copyright © 2018年 jbtm. All rights reserved.
//我的钱包视图

#import <UIKit/UIKit.h>
#import "XHHWalletModel.h"
@interface XTTMyPurseView : UIView

@end

@interface XTTMyPurseHeaderView : UIView
@property (nonatomic, strong) NSString *amount;
@property (weak, nonatomic) IBOutlet UILabel *amountLabel;
+(instancetype)loadFromNib;
@end

@interface XTTMyPurseTableViewCell : UITableViewCell
@property (nonatomic, strong) NSArray *bottomTitles;

@property (nonatomic, copy) void (^didSelectBottomItemBlock)(NSInteger index, NSString *title);
@property (nonatomic, strong) WalletListModel  *model;


@end


@interface XTTMPSDepositRecordTableCell : UITableViewCell
@property (nonatomic, strong) RecordModel  *model;

@end


@interface XTTMPSAddressListTableCell : UITableViewCell
@property (weak, nonatomic) IBOutlet UILabel *nameLabel;

@property (weak, nonatomic) IBOutlet UILabel *addressLabel;
@property (weak, nonatomic) IBOutlet UIImageView *logoImageView;

@end

@interface XTTMPSAddAddressTelVerCodeView : UIView
@property (nonatomic, copy) void (^verCodeInputBlock)(NSString *text);

@property (nonatomic, copy)void (^dismissBlock)(void);
@property (nonatomic, copy)void (^sendTelCodeBlock)(void);
+(instancetype)loadFromNib;

@end

@interface XTTMPSTradePwdView: UIView

@property (nonatomic, copy) void (^forgetTradePwdBlock)(void);


@property (nonatomic, copy) void (^sureBlock)(NSString *text);

@property (nonatomic, copy)void (^dismissBlock)(void);


+(instancetype)loadFromNib;



@end
@interface XTTMPSAddAddressGoogleCodeView : UIView
@property (nonatomic, copy) void (^verCodeInputBlock)(NSString *text);

@property (nonatomic, copy)void (^dismissBlock)(void);
+(instancetype)loadFromNib;

@end
