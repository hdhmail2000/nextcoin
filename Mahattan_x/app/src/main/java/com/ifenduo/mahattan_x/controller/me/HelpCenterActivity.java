package com.ifenduo.mahattan_x.controller.me;

import android.os.Bundle;
import android.widget.ExpandableListView;

import com.ifenduo.common.tool.StatusBarTool;
import com.ifenduo.mahattan_x.R;
import com.ifenduo.mahattan_x.base.BaseActivity;
import com.ifenduo.mahattan_x.entity.HelpCenterEntity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class HelpCenterActivity extends BaseActivity {
//    @BindView(R.id.list_view)
//    ExpandableListView mListView;
//    HelpCenterAdapter mAdapter;

    @Override
    protected int getContentViewLayoutId() {
        return R.layout.activity_help_center;
    }

    @Override
    protected void onCreateViewAfter(Bundle savedInstanceState) {
        super.onCreateViewAfter(savedInstanceState);
        StatusBarTool.setTranslucentForImageView(this, mToolbar);
        getSupportFragmentManager().beginTransaction().add(R.id.fl_container,HelpCenterListFragment.newInstance()).commit();
//        mAdapter = new HelpCenterAdapter(this);
//        mListView.setAdapter(mAdapter);
//        fetchData();
    }

//    private void fetchData() {
//        List<HelpCenterEntity> entityList = new ArrayList<>();
//
//        HelpCenterEntity entity1 = new HelpCenterEntity();
//        List<String> list1 = new ArrayList<>();
//        list1.add("1、违反飞视公约，将被禁言或封号；\n" +
//                "2、也有可能被盗号，请通过“留言反馈”说明情况，并留下账号绑定的【邮箱】或【手机号】，以便查询。");
//        entity1.setChild(list1);
//        entity1.setTitle("为什么被禁言/封号？");
//
//        HelpCenterEntity entity2 = new HelpCenterEntity();
//        List<String> list2 = new ArrayList<>();
//        list2.add("1、违反飞视公约，将被禁言或封号；");
//        entity2.setChild(list2);
//        entity2.setTitle("回答被删除/回答不存在");
//
//        HelpCenterEntity entity3 = new HelpCenterEntity();
//        List<String> list3 = new ArrayList<>();
//        list3.add("反飞视公约，将被禁言或封号");
//        entity3.setChild(list3);
//        entity3.setTitle("问题能删除吗？");
//
//        HelpCenterEntity entity4 = new HelpCenterEntity();
//        List<String> list4 = new ArrayList<>();
//        list4.add("1、违反飞视公约，将被禁言或封号；\n" +
//                "2、也有可能被盗号，请通过“留言反馈”说明情况，并留下账号绑定的【邮箱】或【手机号】，以便查询。");
//        entity4.setChild(list4);
//        entity4.setTitle("原有手机失效，想重新绑定现有手机号。");
//
//        HelpCenterEntity entity5 = new HelpCenterEntity();
//        List<String> list5 = new ArrayList<>();
//        list5.add("1、违反飞视公约，将被禁言或封号；\n" +
//                "2、也有可能被盗号，请通过“留言反馈”说明情况，并留下账号绑定的【邮箱】或【手机号】，以便查询。");
//        entity5.setChild(list5);
//        entity5.setTitle("如何绑定手机？");
//
//        HelpCenterEntity entity6 = new HelpCenterEntity();
//        List<String> list6 = new ArrayList<>();
//        list6.add("1、违反飞视公约，将被禁言或封号；\n" +
//                "2、也有可能被盗号，请通过“留言反馈”说明情况，并留下账号绑定的【邮箱】或【手机号】，以便查询。");
//        entity6.setChild(list6);
//        entity6.setTitle("有多个账号，想合并或者注销。");
//
//        entityList.add(entity1);
//        entityList.add(entity2);
//        entityList.add(entity3);
//        entityList.add(entity4);
//        entityList.add(entity5);
//        entityList.add(entity6);
//
//        mAdapter.setData(entityList);
//    }


}
