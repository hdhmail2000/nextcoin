package com.ifenduo.mahattan_x.controller.transaction;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ifenduo.mahattan_x.R;
import com.ifenduo.mahattan_x.controller.home.HomeActivity;
import com.ifenduo.mahattan_x.entity.Coin;
import com.ifenduo.mahattan_x.entity.TradingCenterDataEntity;
import com.ifenduo.mahattan_x.tools.NumberUtil;

import java.util.ArrayList;
import java.util.List;

import static com.ifenduo.mahattan_x.Constant.RMB_PRICE_CACHE;

public class TransactionCenterAdapter extends RecyclerView.Adapter {
    public static final int ITEM_TYPE_TITLE = 0;
    public static final int ITEM_TYPE_COIN = 1;

    int type;
    OnItemClickListener mOnItemClickListener;
    List<TradingCenterDataEntity> mDataEntityList;
    Context mContext;

    public TransactionCenterAdapter(Context context, int type) {
        this.mContext = context;
        mDataEntityList = new ArrayList<>();
        this.type = type;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        if (viewType == ITEM_TYPE_TITLE) {
            View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_trading_center_title, parent, false);
            viewHolder = new TitleViewHolder(itemView);
        } else {
            View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_trading_center_list, parent, false);
            viewHolder = new CoinViewHolder(itemView);
        }
        return viewHolder;
    }

    public void setData(List<TradingCenterDataEntity> dataEntityList) {
        mDataEntityList.clear();
        if (dataEntityList != null && dataEntityList.size() > 0) {
            mDataEntityList.addAll(dataEntityList);
        }
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final TradingCenterDataEntity dataEntity = mDataEntityList.get(position);
        if (holder instanceof TitleViewHolder) {
            if (dataEntity != null) {
                ((TitleViewHolder) holder).textView.setText(dataEntity.getTitle());
            } else {
                ((TitleViewHolder) holder).textView.setText("");
            }
        } else if (holder instanceof CoinViewHolder) {
            ((CoinViewHolder) holder).itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(dataEntity.getCoin());
                    }
                }
            });

            if (dataEntity != null && dataEntity.getCoin() != null) {
                Coin coin = dataEntity.getCoin();
                if (coin != null) {
                    Glide.with(mContext).load(coin.getSellAppLogo()).into(((CoinViewHolder) holder).imageView);
                    ((CoinViewHolder) holder).nameTextView.setText(coin.getSellShortName());
                    if (coin.getCoinBean() != null) {//实时行情不为空
                        ((CoinViewHolder) holder).numTextView.setText("量  " + NumberUtil.double2String2(coin.getCoinBean().getTotal()));
                        ((CoinViewHolder) holder).dollarTextView.setText(NumberUtil.formatMoney(coin.getCoinBean().getP_new()));

                        //涨幅
                        double rise = coin.getCoinBean().getChg();
                        if (rise < 0) {
                            ((CoinViewHolder) holder).riseTextView.setText(NumberUtil.double2String2(rise) + "%");
                            ((CoinViewHolder) holder).riseTextView.setBackground(mContext.getResources().getDrawable(R.drawable.shape_rise_bg_less));
                        } else {
                            ((CoinViewHolder) holder).riseTextView.setText("+" + NumberUtil.double2String2(rise) + "%");
                            ((CoinViewHolder) holder).riseTextView.setBackground(mContext.getResources().getDrawable(R.drawable.shape_rise_bg_add));
                        }
                    }
                    //转化人民币价格
                    if (coin.getCoinBean() != null && !TextUtils.isEmpty(coin.getCoinBean().getSellSymbol())) {
                        if (HomeActivity.EXCHANGE_RATE == -1) {
                            ((CoinViewHolder) holder).rmbTextView.setText("");
                        } else {
                            String price = getRMBPrice(coin.getCoinBean().getBuySymbol(), coin.getCoinBean().getP_new());
                            ((CoinViewHolder) holder).rmbTextView.setText("¥" + price);
                        }
                    } else {
                        ((CoinViewHolder) holder).rmbTextView.setText("");
                    }
                } else {
                    Glide.with(mContext).load("").into(((CoinViewHolder) holder).imageView);
                    ((CoinViewHolder) holder).numTextView.setText("");
                    ((CoinViewHolder) holder).numTextView.setText("量()");
                    ((CoinViewHolder) holder).riseTextView.setText("%");
                    ((CoinViewHolder) holder).dollarTextView.setText("");
                    ((CoinViewHolder) holder).rmbTextView.setText("¥");
                    ((CoinViewHolder) holder).riseTextView.setBackground(mContext.getResources().getDrawable(R.drawable.shape_rise_bg_less));
                }

            } else {
                Glide.with(mContext).load("").into(((CoinViewHolder) holder).imageView);
                ((CoinViewHolder) holder).numTextView.setText("");
                ((CoinViewHolder) holder).numTextView.setText("量()");
                ((CoinViewHolder) holder).riseTextView.setText("%");
                ((CoinViewHolder) holder).dollarTextView.setText("");
                ((CoinViewHolder) holder).rmbTextView.setText("¥");
                ((CoinViewHolder) holder).riseTextView.setBackground(mContext.getResources().getDrawable(R.drawable.shape_rise_bg_less));
            }
        }
    }

    /**
     * 获取币的RMB价格
     *
     * @param buyName
     * @param price
     * @return
     */
    private String getRMBPrice(String buyName, double price) {
        double rmbPrice = 0.00;
        if (!TextUtils.isEmpty(buyName) && RMB_PRICE_CACHE.containsKey(buyName) && !"USDT".equals(buyName)) {
            rmbPrice = RMB_PRICE_CACHE.get(buyName) * price;
        } else {
            rmbPrice = price * HomeActivity.EXCHANGE_RATE;
        }
        return NumberUtil.formatMoney(rmbPrice);
    }

    @Override
    public int getItemCount() {
        return mDataEntityList.size();
    }

    @Override
    public int getItemViewType(int position) {
        TradingCenterDataEntity dataEntity = mDataEntityList.get(position);
        if (dataEntity != null) {
            return dataEntity.getItemType();
        }
        return super.getItemViewType(position);
    }

    class TitleViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public TitleViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.title_text_view);
        }
    }

    class CoinViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView nameTextView;
        TextView numTextView;
        TextView riseTextView;
        TextView dollarTextView;
        TextView rmbTextView;

        public CoinViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_item_quotes_list);
            nameTextView = itemView.findViewById(R.id.text_item_quotes_list_name);
            numTextView = itemView.findViewById(R.id.text_item_quotes_list_message);
            riseTextView = itemView.findViewById(R.id.text_item_quotes_list_rise);
            dollarTextView = itemView.findViewById(R.id.text_item_quotes_list_price_dollar);
            rmbTextView = itemView.findViewById(R.id.text_item_quotes_list_price_rmb);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Coin coin);
    }
}
