package com.wenable.semicirclelistview.activites;

import android.animation.ObjectAnimator;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.AbsListView.OnScrollListener;

import com.wenable.semicirclelistview.R;
import com.wenable.semicirclelistview.adapters.SemiCircleListAdapter;
import com.wenable.semicirclelistview.utils.ScreenDimenUtils;

/**
 * Created by Venkatesh Prasad on 17/11/16.
 */


public class MainActivity extends AppCompatActivity implements OnScrollListener {


    private final String TAG = MainActivity.class.getName();
    private int mScreenHeight;
    private ListView mSemiCircleListView;
    private boolean mIsCenteringView = true;
    private ImageView mArrowImage;
    private FrameLayout mArrowContainer;
    private int mListViewItemHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getScreenDimens();
        initViews();
        inflateHeaderAndFooterView();
        setUpUi();
        setUpListeners();
    }

    private void getScreenDimens() {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        mScreenHeight = displaymetrics.heightPixels;
        mListViewItemHeight = (mScreenHeight - ScreenDimenUtils.getStatusBarHeight(this)) / 3; // To show three items per screen dividing by "3"
    }

    private void initViews() {
        mArrowImage = (ImageView) findViewById(R.id.arrow_iv);
        mSemiCircleListView = (ListView) findViewById(R.id.semi_circle_lv);
        mArrowContainer = (FrameLayout) findViewById(R.id.arrow_container_fl);
    }

    private void inflateHeaderAndFooterView() {
        LayoutInflater inflater = getLayoutInflater();
        ViewGroup header = (ViewGroup) inflater.inflate(R.layout.semi_circle_list_fotter_header_layout, mSemiCircleListView, false);
        header.setTag(0);
        ViewGroup footer = (ViewGroup) inflater.inflate(R.layout.semi_circle_list_fotter_header_layout, mSemiCircleListView, false);
        footer.setTag(16);
        LinearLayout linearLayoutHeader = (LinearLayout) header.findViewById(R.id.root_ll);
        linearLayoutHeader.setMinimumHeight(mListViewItemHeight);
        LinearLayout linearLayoutFooter = (LinearLayout) footer.findViewById(R.id.root_ll);
        linearLayoutFooter.setMinimumHeight(mListViewItemHeight);
        mSemiCircleListView.addHeaderView(header, null, false);
        mSemiCircleListView.addFooterView(footer, null, false);

    }

    private void setUpListeners() {
        mSemiCircleListView.setOnScrollListener(this);
    }

    private void setUpUi() {
        mSemiCircleListView.setAdapter(new SemiCircleListAdapter(this, mScreenHeight, mListViewItemHeight, new int[]{R.drawable.p1, R.drawable.p2, R.drawable.p3}));
    }

    private void animateArrow() {
        int maxYPositionToMoveArrow = mArrowContainer.getWidth() - mArrowImage.getWidth();
        ObjectAnimator anim = ObjectAnimator.ofFloat(mArrowImage, "translationX", 0, maxYPositionToMoveArrow, 0, maxYPositionToMoveArrow, 0);
        anim.setDuration(3000); // 3 seconds
        anim.start();
    }

    @Override
    public void onScrollStateChanged(final AbsListView listView, int scrollState) {

        if (scrollState == SCROLL_STATE_IDLE) {
            int centerX = listView.getWidth() / 2;
            int centerY = listView.getHeight() / 2;
            for (int i = 0; i <= listView.getLastVisiblePosition() - listView.getFirstVisiblePosition(); i++) {
                final View listItemView = listView.getChildAt(i);
                if (listItemView != null) {
                    Rect rect = new Rect();
                    listItemView.getHitRect(rect);
                    if (rect.contains(centerX, centerY)) {   // Verifies child view contains center.
                        if (mIsCenteringView) {
                            mIsCenteringView = false;
                            new Handler().post(new Runnable() {  // Using Handler to get smooth scrolling
                                @Override
                                public void run() {
                                    // Moves child view exactly "x" pixels from top of parent view with in specified duration.
                                    //In moves [x = center =  listView.getHeight() / 2 - child.getHeight() / 2]
                                    listView.smoothScrollToPositionFromTop((int) listItemView.getTag(), listView.getHeight() / 2 - listItemView.getHeight() / 2, 500);
                                }
                            });
                            animateArrow();
                            return;
                        }
                    }
                }
            }
        }

        if (scrollState == SCROLL_STATE_TOUCH_SCROLL) {
            mIsCenteringView = true;
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

        for (int i = 0; i < mSemiCircleListView.getChildCount(); i++) {  // Every list item will be drawn when list view is scrolled.
            mSemiCircleListView.getChildAt(i).invalidate();
        }

    }

}
