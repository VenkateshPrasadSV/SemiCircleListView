package com.wenable.semicirclelistview.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.wenable.semicirclelistview.R;
import com.wenable.semicirclelistview.customviews.CustomLinearLayout;

/**
 * Created by Venkatesh Prasad on 17/11/16.
 */

public class SemiCircleListAdapter extends BaseAdapter {

    private final String TAG = SemiCircleListAdapter.class.getName();
    private int mItemHeight;
    private int mScreenHeight;
    private Context mContext;
    private int[] mImageList;

    public SemiCircleListAdapter(Context context, int screenHeight, int listItemHeight, int[] imagesList) {
        mContext = context;
        mItemHeight = listItemHeight;
        mScreenHeight = screenHeight;
        mImageList = imagesList;
    }

    @Override
    public int getCount() {
        return 15;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            CustomLinearLayout customLinearLayout = (CustomLinearLayout) LayoutInflater.from(mContext).inflate(R.layout.list_item_semi_circle, null);
            customLinearLayout.setParentHeight(mScreenHeight);
            customLinearLayout.setLayoutParams(new ViewGroup.LayoutParams((ViewGroup.LayoutParams.MATCH_PARENT), mItemHeight));
            convertView = customLinearLayout;
        }
        ImageView imageView = (ImageView) convertView.findViewById(R.id.circle_iv);
        imageView.setImageResource(mImageList[position % mImageList.length]);
        convertView.setTag(position + 1); // Using tag to identify child view which is near to center.
        return convertView;
    }

}