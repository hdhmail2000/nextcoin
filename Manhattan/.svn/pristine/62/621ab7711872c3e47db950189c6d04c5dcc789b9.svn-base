//
//  XHHCityRequest.m
//  Manhattan
//
//  Created by Apple on 2018/9/13.
//  Copyright © 2018年 Apple. All rights reserved.
//

#import "XHHMiningRequest.h"
#import "XHHMiningHomeModel.h"
#import "XHHMinHomeListModel.h"
#import "XHHMiningAreaModle.h"
#import "XHHForseWorkModel.h"
#import "XHHFroseWorkListModel.h"
#import "XHHHomeBannerModel.h"
@implementation XHHMiningRequest
+(void)requestCityHomeSuccess:(ChZ_SuccessBlock)successBlock error:(ErrorCodeBlock)errorBlock{
    NSString *url = RequestURL(kURL_city_homeDate);
    [self GetPHPWithURL:url parameters:nil progress:nil success:^(NSURLSessionDataTask * _Nonnull task, id  _Nullable responseObject) {
        NSDictionary *responseDic = responseObject;
        if ([responseDic isKindOfClass:[NSNull class]])
        {
            if (errorBlock) errorBlock(0,@"请求失败"); return;
        }
        int code = [[responseDic objectForKey:@"code"]intValue];
        NSString *msg = [responseDic objectForKey:@"msg"];
        if (code != kv_PHPRequestSuccess)
        {
            if (ChZ_StringIsEmpty(msg))
            {
                if (errorBlock) errorBlock(code,msg); return;
            }
            if (errorBlock) errorBlock(code,@"请求失败"); return;
        }
        NSArray *data = [responseDic objectForKey:@"return"];
        if (data)
        {
            NSArray *nArrar = [XHHMiningHomeModel mj_objectArrayWithKeyValuesArray:data];
            if (successBlock)
            {
                successBlock(nArrar);
            }
        } else {
            if (successBlock) successBlock(nil); return;
        }
    } failure:^(NSURLSessionDataTask * _Nullable task, NSError * _Nonnull error) {
       if (errorBlock) errorBlock(0,@"请求失败"); return;
    }];
}
+(void)requestHomeBannerSuccess:(ChZ_SuccessBlock)successBlock error:(ErrorCodeBlock)errorBlock{
    NSString *url = RequestURL(kURL_city_homeBanner);
    [self GetPHPWithURL:url parameters:nil progress:nil success:^(NSURLSessionDataTask * _Nonnull task, id  _Nullable responseObject) {
        NSDictionary *responseDic = responseObject;
        if ([responseDic isKindOfClass:[NSNull class]])
        {
            if (errorBlock) errorBlock(0,@"请求失败"); return;
        }
        int code = [[responseDic objectForKey:@"code"]intValue];
        NSString *msg = [responseDic objectForKey:@"msg"];
        if (code != kv_PHPRequestSuccess)
        {
            if (ChZ_StringIsEmpty(msg))
            {
                if (errorBlock) errorBlock(code,msg); return;
            }
            if (errorBlock) errorBlock(code,@"请求失败"); return;
        }
        NSArray *data = [responseDic objectForKey:@"return"];
        if (data)
        {
            NSArray *nArrar = [XHHHomeBannerModel mj_objectArrayWithKeyValuesArray:data];
            if (successBlock)
            {
                successBlock(nArrar);
            }
        } else {
            if (successBlock) successBlock(nil); return;
        }
    } failure:^(NSURLSessionDataTask * _Nullable task, NSError * _Nonnull error) {
        if (errorBlock) errorBlock(0,@"请求失败"); return;
    }];
}
+(void)requestMinListPage:(NSInteger)page success:(ChZ_SuccessBlock)successBlock error:(ErrorCodeBlock)errorBlock{
    NSString *url = [RequestURL(kURL_city_mineList) stringByAppendingString:[NSString stringWithFormat:@"&page=%ld",page]];
    [self GetPHPWithURL:url parameters:nil progress:nil success:^(NSURLSessionDataTask * _Nonnull task, id  _Nullable responseObject) {
        NSDictionary *responseDic = responseObject;
        if ([responseDic isKindOfClass:[NSNull class]])
        {
            if (errorBlock) errorBlock(0,@"请求失败"); return;
        }
        int code = [[responseDic objectForKey:@"code"]intValue];
        NSString *msg = [responseDic objectForKey:@"msg"];
        if (code != kv_PHPRequestSuccess)
        {
            if (ChZ_StringIsEmpty(msg))
            {
                if (errorBlock) errorBlock(code,msg); return;
            }
            if (errorBlock) errorBlock(code,@"请求失败"); return;
        }
        NSArray *data = [responseDic objectForKey:@"return"];
        if (data)
        {
            NSArray *nArrar = [XHHMinHomeListModel mj_objectArrayWithKeyValuesArray:data];
            if (successBlock)
            {
                successBlock(nArrar);
            }
        } else {
            if (successBlock) successBlock(nil); return;
        }
    } failure:^(NSURLSessionDataTask * _Nullable task, NSError * _Nonnull error) {
        if (errorBlock) errorBlock(0,@"请求失败"); return;
    }];
}
+(void)requestMiningAreaListSuccess:(ChZ_SuccessBlock)successBlock error:(ErrorCodeBlock)errorBlock{
    NSString *url = RequestURL(kURL_city_miningAreaList);
    [self GetPHPWithURL:url parameters:nil progress:nil success:^(NSURLSessionDataTask * _Nonnull task, id  _Nullable responseObject) {
        NSDictionary *responseDic = responseObject;
        if ([responseDic isKindOfClass:[NSNull class]])
        {
            if (errorBlock) errorBlock(0,@"请求失败"); return;
        }
        int code = [[responseDic objectForKey:@"code"]intValue];
        NSString *msg = [responseDic objectForKey:@"msg"];
        if (code != kv_PHPRequestSuccess)
        {
            if (ChZ_StringIsEmpty(msg))
            {
                if (errorBlock) errorBlock(code,msg); return;
            }
            if (errorBlock) errorBlock(code,@"请求失败"); return;
        }
        NSDictionary *data = [responseDic objectForKey:@"return"];
        if (data)
        {
            XHHMiningAreaModle *model = [XHHMiningAreaModle mj_objectWithKeyValues:data];
            if (successBlock)
            {
                successBlock(model);
            }
        } else {
            if (successBlock) successBlock(nil); return;
        }
    } failure:^(NSURLSessionDataTask * _Nullable task, NSError * _Nonnull error) {
        if (errorBlock) errorBlock(0,@"请求失败"); return;
    }];
}
+(void)requestMiningInComeGetId:(NSString *)getId success:(ChZ_SuccessBlock)successBlock error:(ErrorCodeBlock)errorBlock{
    NSString *url = [NSString stringWithFormat:@"%@&getid=%@",RequestURL(kURL_city_miningGetCome),getId];
    [self GetPHPWithURL:url parameters:nil progress:nil success:^(NSURLSessionDataTask * _Nonnull task, id  _Nullable responseObject) {
        NSDictionary *responseDic = responseObject;
        if ([responseDic isKindOfClass:[NSNull class]])
        {
            if (errorBlock) errorBlock(0,@"请求失败"); return;
        }
        int code = [[responseDic objectForKey:@"code"]intValue];
        NSString *msg = [responseDic objectForKey:@"msg"];
        if (code != kv_PHPRequestSuccess)
        {
            if (ChZ_StringIsEmpty(msg))
            {
                if (errorBlock) errorBlock(code,msg); return;
            }
            if (errorBlock) errorBlock(code,@"请求失败"); return;
        }
        if (successBlock) successBlock(nil); return;
    } failure:^(NSURLSessionDataTask * _Nullable task, NSError * _Nonnull error) {
        if (errorBlock) errorBlock(0,@"请求失败"); return;
    }];
}
+(void)requestMiningOneKeyInputType:(NSString *)miningType status:(XHHMiningOnekeyType)status success:(ChZ_SuccessBlock)successBlock error:(ErrorCodeBlock)errorBlock{
    NSInteger nStates = (status == XHHMiningOnekeyTypeInput) ? 1 : 0;

    NSString *url = [NSString stringWithFormat:@"%@&type=%@&status=%ld",RequestURL(kURL_city_miningOneKeyInput),miningType,nStates];
    [self GetPHPWithURL:url parameters:nil progress:nil success:^(NSURLSessionDataTask * _Nonnull task, id  _Nullable responseObject) {
        NSDictionary *responseDic = responseObject;

        if ([responseDic isKindOfClass:[NSNull class]])
        {
            if (errorBlock) errorBlock(0,@"请求失败"); return;
        }
        int code = [[responseDic objectForKey:@"code"]intValue];
        NSString *msg = [responseDic objectForKey:@"msg"];
        if (code != kv_PHPRequestSuccess)
        {
            if (ChZ_StringIsEmpty(msg))
            {
                if (errorBlock) errorBlock(code,msg); return;
            }
            if (errorBlock) errorBlock(code,@"请求失败"); return;
        }
        if (successBlock) successBlock(nil); return;

    } failure:^(NSURLSessionDataTask * _Nullable task, NSError * _Nonnull error) {
        if (errorBlock) errorBlock(0,@"请求失败"); return;
    }];
}
+(void)requestMiningInputMiningType:(NSString *)miningType num:(NSString *)num coin_id:(NSString *)coin_id success:(ChZ_SuccessBlock)successBlock error:(ErrorCodeBlock)errorBlock{
    NSMutableDictionary *parma = [NSMutableDictionary dictionary];
    NSString *userId = [APPControl sharedAPPControl].user.fid;
    if (ChZ_StringIsEmpty(userId)) [parma setObject:userId forKey:@"data[fid]"];
    if (ChZ_StringIsEmpty(miningType)) [parma setObject:miningType forKey:@"data[type]"];
    if (ChZ_StringIsEmpty(num)) [parma setObject:num forKey:@"data[num]"];
    if (ChZ_StringIsEmpty(coin_id)) [parma setObject:coin_id forKey:@"data[coin_id]"];
    if (!ChZ_StringIsEmpty(userId)) {
        [[APPControl sharedAPPControl] toHome];
        return;
    }
    NSString *url = RequestURL(kURL_city_miningBuyInput);
    [self PosPHPtWithURL:url parameters:parma progress:nil success:^(NSURLSessionDataTask * _Nonnull task, id  _Nullable responseObject) {
        NSDictionary *responseDic = responseObject;
        if ([responseDic isKindOfClass:[NSNull class]])
        {
            if (errorBlock) errorBlock(0,@"请求失败"); return;
        }
        int code = [[responseDic objectForKey:@"code"]intValue];
        NSString *msg = [responseDic objectForKey:@"msg"];
        if (code != kv_PHPRequestSuccess)
        {
            if (ChZ_StringIsEmpty(msg))
            {
                if (errorBlock) errorBlock(code,msg); return;
            }
            if (errorBlock) errorBlock(code,@"请求失败"); return;
        }
        if (successBlock) successBlock(nil); return;
    } failure:^(NSURLSessionDataTask * _Nullable task, NSError * _Nonnull error) {
        if (errorBlock) errorBlock(0,@"请求失败"); return;
    }];
}
+ (void)requestForseTaskSuccess:(ChZ_SuccessBlock)successBlock error:(ErrorCodeBlock)errorBlock{
    NSString *url = RequestURL(kURL_city_miningForseWork);
    [self GetPHPWithURL:url parameters:nil progress:nil success:^(NSURLSessionDataTask * _Nonnull task, id  _Nullable responseObject) {
        NSDictionary *responseDic = responseObject;
        
        if ([responseDic isKindOfClass:[NSNull class]])
        {
            if (errorBlock) errorBlock(0,@"请求失败"); return;
        }
        int code = [[responseDic objectForKey:@"code"]intValue];
        NSString *msg = [responseDic objectForKey:@"msg"];
        if (code != kv_PHPRequestSuccess)
        {
            if (ChZ_StringIsEmpty(msg))
            {
                if (errorBlock) errorBlock(code,msg); return;
            }
            if (errorBlock) errorBlock(code,@"请求失败"); return;
        }
        NSDictionary *data = [responseDic objectForKey:@"return"];
        if (data)
        {
            XHHForseWorkModel *model = [XHHForseWorkModel mj_objectWithKeyValues:data];
            if (successBlock)
            {
                successBlock(model);
            }
        } else {
            if (successBlock) successBlock(nil); return;
        }
        
        
    } failure:^(NSURLSessionDataTask * _Nullable task, NSError * _Nonnull error) {
        if (errorBlock) errorBlock(0,@"请求失败"); return;
    }];
}
+(void)requestForseWorkListPage:(NSInteger)page success:(ChZ_SuccessBlock)successBlock error:(ErrorCodeBlock)errorBlock{
    NSString *url = [NSString stringWithFormat:@"%@&page=%ld",RequestURL(kURL_city_miningForseWorkList),page];
    [self GetPHPWithURL:url parameters:nil progress:nil success:^(NSURLSessionDataTask * _Nonnull task, id  _Nullable responseObject) {
        NSDictionary *responseDic = responseObject;
        
        if ([responseDic isKindOfClass:[NSNull class]])
        {
            if (errorBlock) errorBlock(0,@"请求失败"); return;
        }
        int code = [[responseDic objectForKey:@"code"]intValue];
        NSString *msg = [responseDic objectForKey:@"msg"];
        if (code != kv_PHPRequestSuccess)
        {
            if (ChZ_StringIsEmpty(msg))
            {
                if (errorBlock) errorBlock(code,msg); return;
            }
            if (errorBlock) errorBlock(code,@"请求失败"); return;
        }
        NSArray *data = [responseDic objectForKey:@"return"];
        if (data)
        {
            NSArray *objArray = [XHHFroseWorkListModel mj_objectArrayWithKeyValuesArray:data];
            if (successBlock)
            {
                successBlock(objArray);
            }
        } else {
            if (successBlock) successBlock(nil); return;
        }
        
        
    } failure:^(NSURLSessionDataTask * _Nullable task, NSError * _Nonnull error) {
        if (errorBlock) errorBlock(0,@"请求失败"); return;
    }];
}
@end
