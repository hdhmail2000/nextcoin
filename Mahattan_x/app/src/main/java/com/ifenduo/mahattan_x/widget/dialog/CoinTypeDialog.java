package com.ifenduo.mahattan_x.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ifenduo.common.adapter.base.ViewHolder;
import com.ifenduo.common.tool.DimensionTool;
import com.ifenduo.mahattan_x.R;
import com.ifenduo.mahattan_x.entity.C2CCoinType;

import java.util.ArrayList;
import java.util.List;

public class CoinTypeDialog extends Dialog {
    RelativeLayout mTitleContainer;
    RecyclerView mRecyclerView;
    ImageView mCancelButton;

    OnItemCheckChangeListener mOnItemCheckChangeListener;
    int mScreenWidth;
    int mCheckedPosition = -1;
    List<C2CCoinType> mData;
    CoinAdapter mCoinAdapter;

    public CoinTypeDialog(@NonNull Context context) {
        super(context, R.style.CoinTypeDialogStyle);
        mScreenWidth = DimensionTool.getScreenWidth(context);
        mData = new ArrayList<C2CCoinType>();
    }

    public void setOnItemCheckChangeListener(OnItemCheckChangeListener onItemCheckChangeListener) {
        mOnItemCheckChangeListener = onItemCheckChangeListener;
        Log.d("TAG", "setOnItemCheckChangeListener: " + (mOnItemCheckChangeListener == null));
    }

    public void setData(List<C2CCoinType> data) {
        mData.clear();
        mCheckedPosition = -1;
        if (data != null && data.size() > 0) {
            mData.addAll(data);
        }
        if (mCoinAdapter != null) {
            mCoinAdapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_coin_type);

        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT;
        getWindow().setAttributes(layoutParams);
        getWindow().setGravity(Gravity.LEFT);

        initView();
    }

    private void initView() {
        mTitleContainer = findViewById(R.id.title_container);
        mRecyclerView = findViewById(R.id.recycler_view);
        mCancelButton = findViewById(R.id.cancel_button);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) mTitleContainer.getLayoutParams();
            layoutParams.setMargins(0, DimensionTool.getStatusBarHeight(getContext()), 0, 0);
            mTitleContainer.setLayoutParams(layoutParams);
        }

        mCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        mCoinAdapter = new CoinAdapter();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(mCoinAdapter);
    }


    class CoinAdapter extends RecyclerView.Adapter<CoinAdapter.CoinViewHolder> {

        @NonNull
        @Override
        public CoinViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(getContext()).inflate(R.layout.item_coin_type_dialog, parent, false);
            return new CoinViewHolder(getContext(), itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull CoinViewHolder holder, final int position) {

            ImageView checkBox = holder.getView(R.id.check_box);

            final C2CCoinType type = mData.get(position);
            if (type != null) {
                holder.setText(R.id.name_text_view, type.getShort_name());
                if (position == mCheckedPosition) {
                    holder.itemView.setBackgroundColor(Color.parseColor("#16134C"));
                    checkBox.setVisibility(View.VISIBLE);
                } else {
                    holder.itemView.setBackgroundColor(Color.TRANSPARENT);
                    checkBox.setVisibility(View.GONE);
                }
            } else {
                holder.setText(R.id.name_text_view, "");
                holder.itemView.setBackgroundColor(Color.TRANSPARENT);
                checkBox.setVisibility(View.GONE);
            }

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("TAG", "onClick: " + type.getShort_name());
                    if (type == null) return;
                    mCheckedPosition = position;
                    notifyDataSetChanged();
                    if (mOnItemCheckChangeListener != null) {
                        mOnItemCheckChangeListener.onItemCheckChange(position, mData.get(position));
                    }
                    dismiss();
                }
            });
        }

        @Override
        public int getItemCount() {
            return mData.size();
        }

        class CoinViewHolder extends ViewHolder {
            public CoinViewHolder(Context context, View itemView) {
                super(context, itemView);
            }
        }
    }

    public interface OnItemCheckChangeListener {
        void onItemCheckChange(int position, C2CCoinType checkedItem);
    }
}
