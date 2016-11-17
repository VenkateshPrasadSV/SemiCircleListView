package com.wenable.semicirclelistview.customviews;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.widget.LinearLayout;


public class CustomLinearLayout extends LinearLayout {
    private final String TAG = CustomLinearLayout.class.getName();
    private int mParentHeight = 0;

    public CustomLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setParentHeight(int parentHeight) {
        mParentHeight = parentHeight;
    }

    /**
     * Dispatch draw is Called by parent view just before its children are drawn
     *
     * @param canvas
     */

    @Override
    protected void dispatchDraw(Canvas canvas) {
        int topDistanceFromParent = getTop(); // Distance from parent to top position of child view
        float scalingFactor = getScaleFactor(topDistanceFromParent);
        int xTranslation = getXTranslation(topDistanceFromParent);
        Matrix m = canvas.getMatrix();
        m.setScale(scalingFactor, scalingFactor);
        m.postTranslate(xTranslation, xTranslation / 3);
        canvas.concat(m);
        super.dispatchDraw(canvas);
    }

    /**
     * This method returns scaling factor based on view position on screen with respect to its parent view.
     * <p>
     * Example : Maximum scaling factor is 1f;
     * Assume that parent height is mParentHeight = 18000 pixel then mParentHeight / 2 =  900 pixel;
     * topDistanceFromParent = 100 pixel,getHeight() = 100 pixel then getHeight() / 2 = 50 pixel.
     * <p>
     * Formulae :
     * scalingFactor = 1f - (Math.abs(mParentHeight / 2 - topDistanceFromParent - getHeight() / 2)) / halfHeightOfParent;
     * Note : Subtracting "getHeight() / 2" to get maximum scale for child view when it is exactly center of the screen.
     * and Subtracting from 1f because scaling factor should between [minimum = 0f and maximum = 1f]
     * if scaling factor is more then 1f,actual size of view changes
     * <p>
     * Applying formula :
     * = 1f - (900 - 100 - 50)/900
     * = 1f - 0.833 = 0.166
     * Here scalingFactor = 0.166
     * <p>
     * As topDistanceFromParent increases scaling factor increases.[topDistanceFromParent = 200,300....900 the scaling factor are 0.3,0.4....0.95 respectively]
     * <p>
     * Using Math.abs to get same scaling factor though view are below the center point
     * i.e, 1f - (900 - 100 - 50)/900 = 0.166 and 1f - (900 - 1000 - 50)/900 = -0.166
     * so Math.abs(-0.166) = 0.166
     *
     * @param topDistanceFromParent
     * @return scalingFactor
     */

    private float getScaleFactor(int topDistanceFromParent) {
        float scalingFactor = 1f;
        float halfHeightOfParent = mParentHeight / 2;
        scalingFactor = 1f - (Math.abs(mParentHeight / 2 - topDistanceFromParent - getHeight() / 2)) / halfHeightOfParent;
        return scalingFactor;
    }

    /**
     * This method returns x axis translation based on view position on screen with respect to its parent view.
     * <p>
     * Example : Staring point view is (0,0) [if Gravity or padding or margin are applied then it varies based on specifications]
     * Assume that parent height is mParentHeight = 18000 pixel then mParentHeight / 2 =  900 pixel;
     * topDistanceFromParent = 100 pixel,getHeight() = 100 pixel then getHeight() / 2 = 50 pixel.
     * <p>
     * Formulae :
     * xAxisTranslation = Math.abs(mParentHeight / 2 - topDistanceFromParent - getHeight() / 2)
     * Note : Subtracting "getHeight() / 2" to get minimum translation for child view when it is exactly center of the screen.
     * <p>
     * Applying formula :
     * = (Math.abs(900 - 100) - 50)= 750 pixel
     * Here x Transl  750 pixel.
     * So, now child view is moved 0 to 750 pixels along x axis.
     * <p>
     * As topDistanceFromParent increases,translation along x axis decreases .[topDistanceFromParent = 200,300....900 the scaling factor are 675,550....50 respectively]
     * <p>
     * Using Math.abs to get same translation value though view are below the center point
     * i.e,  ((Math.abs(900 - 800)) - 50) = 50 and ((Math.abs(900 - 900))- 50) =  (Math.abs(0) - 50) =  50
     * so (900 - 800 - 50) = (900 - 900- 50) = 50
     *
     * @param topDistanceFromParent
     * @return x axis translation
     */
    private int getXTranslation(int topDistanceFromParent) {
        int halfHeightView = getHeight() / 2;
//        Math.abs(Math.abs(mParentHeight / 2 - topDistanceFromParent) - halfHeightView);
        return Math.abs(mParentHeight / 2 - topDistanceFromParent - halfHeightView);
    }


}


