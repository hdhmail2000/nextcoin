package com.ifenduo.mahattan_x.controller.me;


import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ifenduo.common.adapter.base.ViewHolder;
import com.ifenduo.common.tool.DimensionTool;
import com.ifenduo.mahattan_x.R;
import com.ifenduo.mahattan_x.api.Api;
import com.ifenduo.mahattan_x.api.Callback;
import com.ifenduo.mahattan_x.api.DataResponse;
import com.ifenduo.mahattan_x.base.BaseListFragment;
import com.ifenduo.mahattan_x.entity.Help;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class HelpCenterDetailListFragment extends BaseListFragment<Help> {
    Help mHelp;
    int mImageHeight;
    int mImageWidth;
    public static HelpCenterDetailListFragment newInstance(Help help) {
        Bundle args = new Bundle();
        HelpCenterDetailListFragment fragment = new HelpCenterDetailListFragment();
        args.putParcelable("help",help);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public boolean isSupportRefresh() {
        return false;
    }

    @Override
    public boolean isSupportLoadMore() {
        return false;
    }

    @Override
    public void onItemClick(View view, RecyclerView.ViewHolder holder, int position, Help help) {
        if ("2".equals(mHelp.getId())){
            String css = "<style>table{width: 100% !important;} img {width: 100% !important;height: auto;} h1{font-size: 30px !important;color: #00BFFF!important;} p{font-size: 15px !important;} a{text-decoration: none ; color: #0086b3}</style>";
            String data = "<!DOCTYPE html><html><head><meta charset=\"UTF-8\">" + css + "</head><body>" + help.getHuobiziliao() + "</body><script>window.location.hash = 1;$(function(){document.title = $(window).height();});</script></html>";
            WebViewActivity.openWebView(getContext(),help.getTitle(),data,"html");
        }
//        else if ("3".equals(mHelp.getId())){
//            Intent intent = new Intent(getActivity(),BillWayListActivity.class);
//            Bundle bundle = new Bundle();
//            bundle.putParcelable(Constant.BUNDLE_KEY_COMMON,help);
//            intent.putExtras(bundle);
//            startActivity(intent);
//        }
    }

    @Override
    protected void initView(View parentView) {
        handleIntent();
        super.initView(parentView);

        View view = LayoutInflater.from(getContext()).inflate(R.layout.item_helpcenter,null);
        TextView textView = view.findViewById(R.id.text_help_content);
        int pageWidth = DimensionTool.getScreenWidth(getContext());
        mImageWidth = pageWidth - DimensionTool.dp2px(getContext(), 36);
        ViewGroup.LayoutParams layoutParams = textView.getLayoutParams();
        mImageHeight = (int) (mImageWidth * 1.0 * 316 / 840);
        mRecyclerView.setBackgroundColor(Color.TRANSPARENT);
        mRecyclerView.setPadding(DimensionTool.dp2px(getContext(),16),0, DimensionTool.dp2px(getContext(),16),0);
    }

    private void handleIntent(){
        Bundle bundle = getArguments();
        if (bundle!=null){
            mHelp = bundle.getParcelable("help");
        }
    }

    @Override
    public void onRequest(int page) {
        if (mHelp==null)return;
        Api.getInstance().fetchHelpCenterDetailList(mHelp.getId(), new Callback<List<Help>>() {
            @Override
            public void onComplete(boolean isSuccess, int code, String msg, DataResponse<List<Help>> response) {
                if (isSuccess){
                    dispatchResult(response.data);
                }else {
                    showToast(msg);
                }
            }
        });
    }

    @Override
    public int getItemLayoutResId() {
        if (mHelp==null)return 0;
        if ("1".equals(mHelp.getId())){
            return R.layout.item_rate_standard_list;
        }else if ("2".equals(mHelp.getId())){
            return R.layout.item_coin_info_list;
        }
//        else if ("3".equals(mHelp.getId())){
//            return R.layout.item_bill_way_list;
//        }
        else if ("4".equals(mHelp.getId())){
            return R.layout.item_helpcenter;
        }else {
            return 0;
        }
    }

    @Override
    public void onBindView(ViewHolder holder, Help help, int position) {
        if (help!=null){
            if ("1".equals(mHelp.getId())){
                TextView textTitle = holder.getView(R.id.text_rate_standard_title);
                TextView textGua = holder.getView(R.id.text_rate_standard_gua);
                TextView textChi = holder.getView(R.id.text_rate_standard_chi);
                textTitle.setText(help.getTitle());
                textGua.setText(help.getGua());
                textChi.setText(help.getMai());
            }else if ("2".equals(help.getId())){
                TextView textCoinTitle = holder.getView(R.id.text_coin_info_name);
                textCoinTitle.setText(help.getTitle());
            }
//            else if ("3".equals(mHelp.getId())){
//                TextView textBillway = holder.getView(R.id.text_bill_way);
//            }
            else if ("4".equals(mHelp.getId())){
                TextView textHelpTitle = holder.getView(R.id.text_help_title);
                TextView textContent = holder.getView(R.id.text_help_content);
                RelativeLayout rlHelp = holder.getView(R.id.rl_help);
                final TextView textHelpContent=holder.getView(R.id.text_help_content);
                final ImageView imageHelpAction = holder.getView(R.id.image_help_action);

                textHelpTitle.setText(help.getTitle());
                textContent.setText(help.getDa());
                rlHelp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showAnimator(textHelpContent,imageHelpAction);
                    }
                });
            }
        }
    }

    private void showAnimator(final View view1, View view2) {
        ValueAnimator animator1;
        ObjectAnimator animator2;

        if (view1.getHeight() > 0) {
            animator1 = ValueAnimator.ofFloat(1.0f, 0.0f);
            animator2 = ObjectAnimator.ofFloat(view2, "rotation", 180.0f, 0f);
        }
        else {
            animator1 = ValueAnimator.ofFloat(0.0f, 1.0f);
            animator2 = ObjectAnimator.ofFloat(view2, "rotation", 0f, 180f);
        }
        animator1.setDuration(400);
        animator2.setDuration(400);
        animator1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float animatorValue = (float) animation.getAnimatedValue();
                view1.getLayoutParams().height = (int) (animatorValue * mImageHeight);
                view1.getLayoutParams().width = mImageWidth;
                view1.requestLayout();

            }
        });

        animator1.start();
        animator2.start();
    }
}
