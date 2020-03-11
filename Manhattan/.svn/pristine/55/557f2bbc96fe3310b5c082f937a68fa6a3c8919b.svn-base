//
//  UIViewController+ChZExtension.m
//  lzg
//
//  Created by Howe on 2017/10/16.
//  Copyright © 2017年 Howe. All rights reserved.
//

#import "UIViewController+ChZExtension.h"
#import <WebKit/WebKit.h>

@interface UIViewController()

@property (nonatomic, copy) SelectedImageFinshBlock selectedImageFinshBlock;

@property (nonatomic, assign) BOOL selectedIsEdit;

@end

@implementation UIViewController (ChZExtension)

static const char selectedIsEditKey = '\0';
- (void)setSelectedIsEdit:(BOOL)selectedIsEdit
{
    objc_setAssociatedObject(self, &selectedIsEditKey, @(selectedIsEdit), OBJC_ASSOCIATION_RETAIN);
}
-(BOOL)selectedIsEdit
{
    NSNumber *number = objc_getAssociatedObject(self, &selectedIsEditKey);
    return [number boolValue];
}

static const char selectedImageFinshBlockKey = '\0';
- (void)setSelectedImageFinshBlock:(SelectedImageFinshBlock)selectedImageFinshBlock
{
    objc_setAssociatedObject(self, &selectedImageFinshBlockKey, selectedImageFinshBlock, OBJC_ASSOCIATION_COPY);
}
- (SelectedImageFinshBlock)selectedImageFinshBlock
{
    return objc_getAssociatedObject(self, &selectedImageFinshBlockKey);
}


+ (instancetype)initWithStoryboard:(NSString *)storyboardName
{
    NSString *className = NSStringFromClass([self class]);
    if (!ChZ_StringIsEmpty(className))return nil;
    return [[UIStoryboard storyboardWithName:storyboardName bundle:nil] instantiateViewControllerWithIdentifier:className];
}

- (BOOL)popToVC:(NSString *)popVCClassName pushToVC:(NSString *)pushVCClassName
{
    if (!ChZ_StringIsEmpty(popVCClassName) && !ChZ_StringIsEmpty(pushVCClassName))return NO;
    if (self.navigationController == nil)return NO;
    NSArray *vcArray = self.navigationController.viewControllers;
    if (vcArray == nil)return NO;
    for (UIViewController *vc in vcArray)
    {
        Class clazz = NSClassFromString(popVCClassName);
        if ([vc isMemberOfClass:clazz])
        {
            [self.navigationController popToViewController:vc animated:NO];
            Class pushClass = NSClassFromString(pushVCClassName);
            if (pushClass == NULL) return NO;
            UIViewController *pushVC = [[pushClass alloc]init];
            [vc.navigationController pushViewController:pushVC animated:YES];
            return YES;
        }
    }
    return NO;
}

- (BOOL)popToVC:(NSString *)popVCClassName pushToStoryboard:(NSString *)storyboardName vcID:(NSString *)vcID
{
    if (!ChZ_StringIsEmpty(popVCClassName) && !ChZ_StringIsEmpty(vcID))return NO;
    if (self.navigationController == nil)return NO;
    NSArray *vcArray = self.navigationController.viewControllers;
    if (vcArray == nil)return NO;
    for (UIViewController *vc in vcArray)
    {
        Class clazz = NSClassFromString(popVCClassName);
        if ([vc isMemberOfClass:clazz])
        {
            [self.navigationController popToViewController:vc animated:NO];
            __strong UIViewController *pushVC= [[UIStoryboard storyboardWithName:storyboardName bundle:nil] instantiateViewControllerWithIdentifier:vcID];
            if (pushVC == nil) return NO;
            [vc.navigationController pushViewController:pushVC animated:YES];
            return YES;
        }
    }
    return NO;
}


- (instancetype)pushToStoryboard:(NSString *)storyboardName vcID:(NSString *)vcID;
{
    
    if (!ChZ_StringIsEmpty(vcID))return nil;
    UIViewController *vc= [[UIStoryboard storyboardWithName:storyboardName bundle:nil] instantiateViewControllerWithIdentifier:vcID];
    if (vc == nil)return nil;
    if (self.navigationController)
        [self.navigationController pushViewController:vc animated:YES];
    else
        [self presentViewController:vc animated:YES completion:nil];
    return vc;
}
- (void)popToVCClassName:(NSString *)className
{
    if (!ChZ_StringIsEmpty(className))return;
    if (self.navigationController == nil)return;
    NSArray *vcArray = self.navigationController.viewControllers;
    if (vcArray == nil)return;
    for (UIViewController *vc in vcArray)
    {
        Class clazz = NSClassFromString(className);
        if ([vc isMemberOfClass:clazz])
        {
            [self.navigationController popToViewController:vc animated:YES];
            return;
        }
    }
}

- (BOOL)popToMaybeVCClassNames:(NSArray<NSString *> *)vcClassNameArray;
{
    if (vcClassNameArray == nil || vcClassNameArray.count == 0)return NO;
    if (self.navigationController == nil) return NO;
    NSArray *navVCArray = self.navigationController.viewControllers;
    if (navVCArray == nil)return NO;
    for (UIViewController *vc in navVCArray)
    {
        for (NSString *vcClassName in vcClassNameArray)
        {
            Class clazz = NSClassFromString(vcClassName);
            if ([vc isMemberOfClass:clazz])
            {
                [self.navigationController popToViewController:vc animated:YES];
                return YES;
            }
        }
    }
    return NO;
}

- (void)pop
{
    if (self.navigationController)
    {
        if (self.navigationController.viewControllers.count == 1)
        {
            [self.navigationController dismissViewControllerAnimated:YES completion:nil];
        }else
        {
            [self.navigationController popViewControllerAnimated:YES];
        }
    }else
    {
        [self dismissViewControllerAnimated:YES completion:nil];
    }
}

- (void)showTheAlertVCWithStyle:(UIAlertControllerStyle)sytle
                          title:(NSString *)title
                        message:(NSString *)msg
                         title1:(NSString *)title1
                        action1:(AlertActionBlock)action1Block
                         title2:(NSString *)title2
                        action2:(AlertActionBlock)action2Block
                         title3:(NSString *)title3
                        action3:(AlertActionBlock)action3Block
                     completion:(AlertActionBlock)completionBlock
{
    
    UIAlertController *alertVC= [UIAlertController alertControllerWithTitle:title message:msg preferredStyle:sytle];
    if (action1Block)
    {
        [alertVC addAction:[UIAlertAction actionWithTitle:title1 style:UIAlertActionStyleDefault handler:^(UIAlertAction * _Nonnull action)
                            {
                                if (action1Block)action1Block();
                            }]];
    }
    if (action2Block)
    {
        [alertVC addAction:[UIAlertAction actionWithTitle:title2 style:UIAlertActionStyleDefault handler:^(UIAlertAction * _Nonnull action)
                            {
                                if (action2Block)action2Block();
                            }]];
    }
    if (action3Block)
    {
        [alertVC addAction:[UIAlertAction actionWithTitle:title3 style:UIAlertActionStyleCancel handler:^(UIAlertAction * _Nonnull action)
                            {
                                if (action3Block)action3Block();
                            }]];
    }
    
    
    [self presentViewController:alertVC animated:YES completion:^{
        if (completionBlock) {
            completionBlock();
        }
    }];
}




- (void)selectImageEdit:(BOOL)edit selectedFinish:(SelectedImageFinshBlock)block
{
    self.selectedIsEdit = edit;
    self.selectedImageFinshBlock = block;
    [self private_chz_selectedImage];
}
- (void)private_chz_selectedImage
{
    __block UIImagePickerController *imageVC = [[UIImagePickerController alloc]init];
    imageVC.allowsEditing = self.selectedIsEdit;
    if (self.selectedIsEdit)
    {
        NSLog(@"======");
    }
    imageVC.delegate = self;
    ChZ_Weak
    [self showTheAlertVCWithStyle:UIAlertControllerStyleActionSheet title:nil message:@"选择图片来源" title1:@"相机" action1:^
     {
         ChZ_Strong
         imageVC.sourceType = UIImagePickerControllerSourceTypeCamera;
         [_strongSelf presentViewController:imageVC animated:YES completion:nil];
         
     } title2:@"图库" action2:^
     {
         ChZ_Strong
         imageVC.sourceType = UIImagePickerControllerSourceTypeSavedPhotosAlbum;
         [_strongSelf presentViewController:imageVC animated:YES completion:nil];
     } title3:@"取消" action3:^
     {
         
     } completion:nil];
    
}

- (void)imagePickerController:(UIImagePickerController *)picker didFinishPickingMediaWithInfo:(NSDictionary<NSString *,id> *)info
{
    if (self.selectedIsEdit)
    {
        UIImage *image = [info objectForKey:UIImagePickerControllerEditedImage];
        if (self.selectedImageFinshBlock)
            self.selectedImageFinshBlock(image);
    }else
    {
        UIImage *image = [info objectForKey:UIImagePickerControllerOriginalImage];
        if (self.selectedImageFinshBlock)
            self.selectedImageFinshBlock(image);
        
    }
    [picker dismissViewControllerAnimated:YES completion:nil];
}
- (void)imagePickerControllerDidCancel:(UIImagePickerController *)picker
{
    [picker dismissViewControllerAnimated:YES completion:nil];
}


- (void)setupRefreshWithView:(id )needAddRefView refreshHeadAction:(SEL)refreshAction refreshFooterAction:(SEL)loadMoreAction
{
    if (refreshAction != nil)
    {
        MJRefreshNormalHeader *header = [MJRefreshNormalHeader headerWithRefreshingTarget:self refreshingAction:refreshAction];
        if ([needAddRefView isMemberOfClass:[UITableView class]])
        {
            UITableView *tableView = needAddRefView;
            tableView.mj_header = header;
        }else
            if ([needAddRefView isMemberOfClass:[UICollectionView class]])
            {
                UICollectionView *collectionView = needAddRefView;
                collectionView.mj_header = header;
            }else
                if ([needAddRefView isMemberOfClass:[UIScrollView class]])
                {
                    UIScrollView *scrollView = needAddRefView;
                    scrollView.mj_header = header;
                }
                else if ([needAddRefView isMemberOfClass:[WKWebView class]])
                {
                    WKWebView *webView = needAddRefView;
                    UIScrollView *scrollView = webView.scrollView;
                    scrollView.mj_header = header;
                }
        
    }
    if (loadMoreAction != nil )
    {
        MJRefreshBackNormalFooter *footer = [MJRefreshBackNormalFooter footerWithRefreshingTarget:self refreshingAction:loadMoreAction];
        [footer setTitle:@"没有更多内容了" forState:MJRefreshStateNoMoreData];
        if ([needAddRefView isMemberOfClass:[UITableView class]])
        {
            UITableView *tableView = needAddRefView;
            tableView.mj_footer = footer;
        }else
            if ([needAddRefView isMemberOfClass:[UICollectionView class]])
            {
                UICollectionView *collectionView = needAddRefView;
                collectionView.mj_footer = footer;
            }else
                if ([needAddRefView isMemberOfClass:[UIScrollView class]])
                {
                    UIScrollView *scrollView = needAddRefView;
                    scrollView.mj_footer = footer;
                }else if ([needAddRefView isMemberOfClass:[WKWebView class]])
                {
                    WKWebView *webView = needAddRefView;
                    UIScrollView *scrollView = webView.scrollView;
                    scrollView.mj_footer = footer;
                }
    }
}

@end
