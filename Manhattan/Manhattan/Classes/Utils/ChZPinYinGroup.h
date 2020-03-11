//
//  ChZPinYinGroup.h
//  CoinBlock
//
//  Created by Howe on 2018/8/8.
//  Copyright © 2018年 Howe. All rights reserved.
//

#import <Foundation/Foundation.h>
@protocol ChZPinYinGroupModelProtocol<NSObject>
@required
@property (nonatomic, copy) NSString *pinyin;

@end

@interface ChZPinYinGroup : NSObject

+ (NSArray *) getFriendListDataBy:(NSArray <ChZPinYinGroupModelProtocol >*)array;

+ (NSArray *) getFriendListSectionBy:(NSArray <NSArray <ChZPinYinGroupModelProtocol>*>*)array;

@end
