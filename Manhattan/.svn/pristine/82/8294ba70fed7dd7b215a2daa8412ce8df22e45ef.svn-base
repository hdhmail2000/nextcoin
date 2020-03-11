//
//  ChZPinYinGroup.m
//  CoinBlock
//
//  Created by Howe on 2018/8/8.
//  Copyright © 2018年 Howe. All rights reserved.
//

#import "ChZPinYinGroup.h"

@implementation ChZPinYinGroup

+ (NSArray *) getFriendListDataBy:(NSArray <ChZPinYinGroupModelProtocol >*)array
{
    NSMutableArray *ans = [[NSMutableArray alloc] init];
    
    NSArray *serializeArray = [(NSArray *)array sortedArrayUsingComparator:^NSComparisonResult(id obj1, id obj2) {//排序
        int i;
        NSObject<ChZPinYinGroupModelProtocol> *model1 = obj1;
        NSObject<ChZPinYinGroupModelProtocol> *model2 = obj1;
        NSString *strA = model1.pinyin;
        NSString *strB = model2.pinyin;
        for (i = 0; i < strA.length && i < strB.length; i ++) {
            char a = [strA characterAtIndex:i];
            char b = [strB characterAtIndex:i];
            if (a > b) {
                return (NSComparisonResult)NSOrderedDescending;//上升
            }
            else if (a < b) {
                return (NSComparisonResult)NSOrderedAscending;//下降
            }
        }
        
        if (strA.length > strB.length) {
            return (NSComparisonResult)NSOrderedDescending;
        }else if (strA.length < strB.length){
            return (NSComparisonResult)NSOrderedAscending;
        }else{
            return (NSComparisonResult)NSOrderedSame;
        }
    }];
    
    char lastC = '1';
    NSMutableArray *data;
    NSMutableArray *oth = [[NSMutableArray alloc] init];
    for (NSString<ChZPinYinGroupModelProtocol> *model in serializeArray)
    {
        char c = [model.pinyin characterAtIndex:0];
        if (!isalpha(c))
        {
            [oth addObject:model];
        }
        else if (c != lastC)
        {
            lastC = c;
            if (data && data.count > 0)
            {
                [ans addObject:data];
            }
            data = [[NSMutableArray alloc] init];
            [data addObject:model];
        }
        else {
            [data addObject:model];
        }
    }
    if (data && data.count > 0) {
        [ans addObject:data];
    }
    if (oth.count > 0)
    {
        [ans addObject:oth];
    }
    return ans;
}

+ (NSArray *)getFriendListSectionBy:(NSArray <NSArray <ChZPinYinGroupModelProtocol>*>*)array{
    NSMutableArray *section = [[NSMutableArray alloc] init];
    //    [section addObject:UITableViewIndexSearch];
    for (NSArray *item in array) {
        NSObject<ChZPinYinGroupModelProtocol> *model = [item objectAtIndex:0];
        char c = [model.pinyin characterAtIndex:0];
        if (!isalpha(c)) {
            c = '#';
        }
        [section addObject:[NSString stringWithFormat:@"%c", toupper(c)]];
    }
    return section;
}
@end
