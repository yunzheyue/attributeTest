package com.example.app5;

import android.view.View;

/**
 * autour : lbing
 * date : 2018/8/10 0010 10:03
 * className :
 * version : 1.0
 * description :
 */


public class WrapperView {

    private View mTarget;

    public WrapperView(View mTarget) {
        this.mTarget = mTarget;
    }

    public float getWidth() {
        return mTarget.getLayoutParams().width;
    }

    public void setWidth(float width) {
        mTarget.getLayoutParams().width = (int) width;
        mTarget.requestLayout();
    }
}

