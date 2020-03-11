package com.ifenduo.mahattan_x.controller.me;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ifenduo.mahattan_x.R;
import com.ifenduo.mahattan_x.entity.HelpCenterEntity;

import java.util.ArrayList;
import java.util.List;

public class HelpCenterAdapter extends BaseExpandableListAdapter {
    Context mContext;
    List<HelpCenterEntity> mData;

    public HelpCenterAdapter(Context mContext) {
        this.mContext = mContext;
        mData = new ArrayList<>();
    }

    public void setData(List<HelpCenterEntity> data) {
        mData.clear();
        if (data != null && data.size() > 0) {
            mData.addAll(data);
        }
        notifyDataSetChanged();
    }


    @Override
    public int getGroupCount() {
        return mData.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        HelpCenterEntity entity = (HelpCenterEntity) getGroup(groupPosition);
        return (entity == null || entity.getChild() == null) ? 0 : entity.getChild().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return mData.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        String child = "";
        HelpCenterEntity entity = (HelpCenterEntity) getGroup(groupPosition);
        if (entity != null && entity.getChild() != null && childPosition < entity.getChild().size()) {
            child = entity.getChild().get(childPosition);
        }
        return child;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_help_center_group, parent, false);
            viewHolder = new GroupViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (GroupViewHolder) convertView.getTag();
        }
        HelpCenterEntity entity = (HelpCenterEntity) getGroup(groupPosition);
        viewHolder.indicatorImageView.setImageResource(isExpanded ? R.drawable.ic_up : R.drawable.ic_down);
        if (entity != null) {
            viewHolder.contentTextView.setText(entity.getTitle());
        }
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_help_center_child, parent, false);
            viewHolder = new ChildViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ChildViewHolder) convertView.getTag();
        }
        String content = (String) getChild(groupPosition, childPosition);
        viewHolder.contentTextView.setText(content);
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    class GroupViewHolder {
        TextView contentTextView;
        ImageView indicatorImageView;

        public GroupViewHolder(View itemView) {
            contentTextView = itemView.findViewById(R.id.content_text_view);
            indicatorImageView = itemView.findViewById(R.id.indicator_image_view);
        }
    }

    class ChildViewHolder {
        TextView contentTextView;

        public ChildViewHolder(View itemView) {
            contentTextView = itemView.findViewById(R.id.content_text_view);
        }
    }
}
