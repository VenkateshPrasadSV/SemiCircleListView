package com.wenable.semicirclelistview.utils;

import android.content.Context;
import android.util.Log;
import android.util.TypedValue;

/**
 * Created by Venkatesh Prasd on 17/11/16.
 */

public class ScreenDimenUtils {

    private static final String TAG = ScreenDimenUtils.class.getName();

    public static int getActionBarHeight(Context context) {
        int actionBarHeight = 0;
        TypedValue tv = new TypedValue();
        if (context.getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
            actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data, context.getResources().getDisplayMetrics());

        }
        Log.v(TAG, "getActionBarHeight() " + actionBarHeight);
        return actionBarHeight;
    }

    public static int getStatusBarHeight(Context context) {
        int statusBarHeight = 0;
        int resource = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resource > 0) {
            statusBarHeight = context.getResources().getDimensionPixelSize(resource);
        }
        Log.v(TAG, "getStatusBarHeight() :: statusBarHeight :: " + statusBarHeight);
        return statusBarHeight;
    }
}
