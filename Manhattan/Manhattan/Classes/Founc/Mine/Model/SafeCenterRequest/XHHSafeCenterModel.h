//
//  XHHSafeCenterModel.h
//  FuturePurse
//
//  Created by muye01 on 2018/8/23.
//  Copyright © 2018年 jbtm. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface XHHSafeCenterModel : NSObject

@end


@interface HCCenterLoginRecordItemModel : NSObject

@property (nonatomic ,copy)NSString * uuid;
@property (nonatomic ,assign)NSInteger  fid;
@property (nonatomic ,assign)NSInteger  fuid;
@property (nonatomic ,copy)NSString * fagentid;
@property (nonatomic ,assign)NSInteger  ftype;
@property (nonatomic ,copy)NSString * ftype_s;
@property (nonatomic ,assign)NSInteger  fdatatype;
@property (nonatomic ,copy)NSString * fdatatype_s;
@property (nonatomic ,copy)NSString * fcapitaltype;
@property (nonatomic ,copy)NSString * fdata;
@property (nonatomic ,copy)NSString * ffees;
@property (nonatomic ,copy)NSString * fbtcfees;
@property (nonatomic ,copy)NSString * fcontent;
@property (nonatomic ,copy)NSString * fip;
@property (nonatomic ,copy)NSString * fupdatetime;
@property (nonatomic ,copy)NSString * fcreatetime;
@property (nonatomic ,copy)NSString * num;

@end

@interface HCCenterSecureSettingLogItemModel : NSObject

@property (nonatomic ,copy)NSString * uuid;
@property (nonatomic ,assign)NSInteger  fid;
@property (nonatomic ,assign)NSInteger  fuid;
@property (nonatomic ,copy)NSString * fagentid;
@property (nonatomic ,assign)NSInteger  ftype;
@property (nonatomic ,copy)NSString * ftype_s;
@property (nonatomic ,copy)NSString * fdatatype;
@property (nonatomic ,copy)NSString * fdatatype_s;
@property (nonatomic ,copy)NSString * fcapitaltype;
@property (nonatomic ,copy)NSString * fdata;
@property (nonatomic ,copy)NSString * ffees;
@property (nonatomic ,copy)NSString * fbtcfees;
@property (nonatomic ,copy)NSString * fcontent;
@property (nonatomic ,copy)NSString * fip;
@property (nonatomic ,copy)NSString * fupdatetime;
@property (nonatomic ,copy)NSString * fcreatetime;
@property (nonatomic ,copy)NSString * num;

@end


@interface HCUserDataModel : NSObject
@property (nonatomic, copy) NSString * fid;
@property (nonatomic, copy) NSString * fshowid;
@property (nonatomic, copy) NSString * floginname;
@property (nonatomic, copy) NSString * fnickname;
@property (nonatomic, copy) NSString * floginpassword;
@property (nonatomic, copy) NSString * ftradepassword;
@property (nonatomic, copy) NSString * ftelephone;
@property (nonatomic, copy) NSString * femail;
@property (nonatomic, copy) NSString * frealname;
@property (nonatomic, copy) NSString * fidentityno;
@property (nonatomic, copy) NSString * fidentitytype;
@property (nonatomic, copy) NSString * fgoogleauthenticator;
@property (nonatomic, copy) NSString * fgoogleurl;
@property (nonatomic, copy) NSString * fstatus;
@property (nonatomic, copy) NSString * fstatus_s;
@property (nonatomic, copy) NSString * fhasrealvalidate;
@property (nonatomic, copy) NSString * fhasrealvalidatetime;
@property (nonatomic, copy) NSString * fistelephonebind;
@property (nonatomic, copy) NSString * fismailbind;
@property (nonatomic, copy) NSString * fgooglebind;
@property (nonatomic, copy) NSString * isVideo;
@property (nonatomic, copy) NSString * videoTime;
@property (nonatomic, copy) NSString * fupdatetime;
@property (nonatomic, copy) NSString * fareacode;
@property (nonatomic, copy) NSString * version;
@property (nonatomic, copy) NSString * fintrouid;
@property (nonatomic, copy) NSString * finvalidateintrocount;
@property (nonatomic, copy) NSString * fiscny;
@property (nonatomic, copy) NSString * fiscny_s;
@property (nonatomic, copy) NSString * fiscoin;
@property (nonatomic, copy) NSString * fiscoin_s;
@property (nonatomic, copy) NSString * fbirth;
@property (nonatomic, copy) NSString * flastlogintime;
@property (nonatomic, copy) NSString * fregistertime;
@property (nonatomic, copy) NSString * ftradepwdtime;
@property (nonatomic, copy) NSString * fleverlock;
@property (nonatomic, copy) NSString * fqqopenid;
@property (nonatomic, copy) NSString * funionid;
@property (nonatomic, copy) NSString * fagentid;
@property (nonatomic, copy) NSString * folduid;
@property (nonatomic, copy) NSString * fplatform;
@property (nonatomic, copy) NSString * ip;
@property (nonatomic, copy) NSString * score;
@property (nonatomic, copy) NSString * level;
@property (nonatomic, copy) NSString * flastip;
@end
@interface HCCenterSecuritySettingDetailModel : NSObject

@property (nonatomic ,assign)NSInteger  fid;
@property (nonatomic ,assign)NSInteger  fshowid;
@property (nonatomic ,copy)NSString * floginname;
@property (nonatomic ,copy)NSString * fnickname;
/** 手机号码*/
@property (nonatomic ,copy)NSString * ftelephone;
@property (nonatomic ,copy)NSString * femail;
@property (nonatomic ,copy)NSString * frealname;
@property (nonatomic ,copy)NSString * fidentityno;
@property (nonatomic ,assign)NSInteger  fidentitytype;
@property (nonatomic ,copy)NSString * fgoogleurl;
@property (nonatomic ,assign)NSInteger  fstatus;
@property (nonatomic ,copy)NSString * fstatus_s;
/** 是否实名认证*/
@property (nonatomic ,assign)BOOL  fhasrealvalidate;
@property (nonatomic ,copy)NSString * fhasrealvalidatetime;
/** 是否绑定手机号*/
@property (nonatomic ,assign)BOOL  fistelephonebind;
/** 是否绑定邮箱*/
@property (nonatomic ,assign)BOOL  fismailbind;
/** 是否绑定谷歌验证*/
@property (nonatomic ,assign)BOOL  fgooglebind;
@property (nonatomic ,assign)NSInteger  fupdatetime;
@property (nonatomic ,copy)NSString * fareacode;
@property (nonatomic ,copy)NSString * fintrouid;
@property (nonatomic ,assign)NSInteger  finvalidateintrocount;
@property (nonatomic ,assign)NSInteger  fiscny;
@property (nonatomic ,copy)NSString * fiscny_s;
@property (nonatomic ,assign)NSInteger  fiscoin;
@property (nonatomic ,copy)NSString * fiscoin_s;
@property (nonatomic ,copy)NSString * fbirth;
@property (nonatomic ,assign)NSInteger  flastlogintime;
@property (nonatomic ,assign)NSInteger  fregistertime;
@property (nonatomic ,copy)NSString * fqqopenid;
@property (nonatomic ,copy)NSString * funionid;
@property (nonatomic ,assign)NSInteger  fagentid;
@property (nonatomic ,assign)NSInteger  fleverlock;
@property (nonatomic ,copy)NSString * ip;
@property (nonatomic ,assign)NSInteger  score;
@property (nonatomic ,assign)NSInteger  level;

@end
@interface HCCenterSecuritySettingIdentityModel:NSObject

@property (nonatomic ,copy)NSString * fid;
@property (nonatomic ,copy)NSString * fuid;
@property (nonatomic ,copy)NSString * fcountry;
@property (nonatomic ,copy)NSString * fname;
@property (nonatomic ,copy)NSString * fcode;
@property (nonatomic ,copy)NSString * ftype;
@property (nonatomic ,copy)NSString * fstatus;
@property (nonatomic ,copy)NSString * fcreatetime;
@property (nonatomic ,copy)NSString * fupdatetime;
@property (nonatomic ,copy)NSString * fstatus_s;
@property (nonatomic ,copy)NSString * ftype_s;
@property (nonatomic ,copy)NSString * ip;
@property (nonatomic ,copy)NSString * idCardZmImgURL;
@property (nonatomic ,copy)NSString * idCardFmImgURL;
@property (nonatomic ,copy)NSString * idCardScImgURL;
@end

@interface HCCenterSecuritySettingWrapperModel : NSObject

@property (nonatomic ,assign) NSInteger  securityLevel;
@property (nonatomic ,copy) NSString * device_name;
@property (nonatomic ,assign) BOOL  bindTrade;
@property (nonatomic ,assign) NSInteger  bindcount;
@property (nonatomic ,strong) HCCenterSecuritySettingDetailModel * fuser;
@property (nonatomic ,copy) NSString * identity;
@property (nonatomic ,copy) NSString * loginName;
@property (nonatomic ,copy) NSString * phoneString;
@property (nonatomic ,assign) BOOL  bindLogin;
@property (nonatomic ,strong) HCCenterSecuritySettingIdentityModel * fidentity;

@end


@interface HCCenterRechargeCodeRecordItemModel : NSObject
@property (nonatomic ,assign)NSInteger  fid;
@property (nonatomic ,assign)NSInteger  fuid;
@property (nonatomic ,copy)NSString * floginname;
@property (nonatomic ,assign)NSInteger  ftype;
@property (nonatomic ,copy)NSString * ftype_s;
@property (nonatomic ,copy)NSString * fcode;
@property (nonatomic ,assign)NSInteger  famount;
@property (nonatomic ,assign)BOOL  fstate;
@property (nonatomic ,copy)NSString * fstate_s;
@property (nonatomic ,assign)NSInteger  fcreatetime;
@property (nonatomic ,assign)NSInteger  fupdatetime;
@property (nonatomic ,assign)NSInteger  version;
@property (nonatomic ,copy)NSString * fbatch;
@property (nonatomic ,assign)BOOL  fislimituser;
@property (nonatomic ,copy)NSString * fislimituse;
@property (nonatomic ,copy)NSString * fusenum;
@property (nonatomic ,copy)NSString * fusedate;
@end

