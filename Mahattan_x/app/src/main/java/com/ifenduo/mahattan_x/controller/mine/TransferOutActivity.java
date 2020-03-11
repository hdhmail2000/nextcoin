package com.ifenduo.mahattan_x.controller.mine;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ifenduo.common.tool.StatusBarTool;
import com.ifenduo.mahattan_x.R;
import com.ifenduo.mahattan_x.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class TransferOutActivity extends BaseActivity {
    @BindView(R.id.all_hint_text_view)
    TextView mAllHintTextView;
    @BindView(R.id.all_num_text_view)
    TextView mAllNumTextView;
    @BindView(R.id.name_text_view)
    TextView mNameTextView;
    @BindView(R.id.num_edit_text)
    EditText mNumEditText;
    @BindView(R.id.num_text_view_)
    TextView mNumTextView_;
    @BindView(R.id.submit_button)
    Button mSubmitButton;

    @Override
    protected int getContentViewLayoutId() {
        return R.layout.activity_transfer;
    }

    @Override
    protected void onCreateViewAfter(Bundle savedInstanceState) {
        super.onCreateViewAfter(savedInstanceState);
        setNavigationCenter("立即投入");
        StatusBarTool.setTranslucentForImageView(this, mToolbar);
    }

    @OnClick({R.id.all_text_view, R.id.submit_button})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.all_text_view:
                break;
            case R.id.submit_button:
                break;
        }
    }
}
