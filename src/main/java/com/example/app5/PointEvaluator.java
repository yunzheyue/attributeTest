package com.example.app5;

import android.animation.TypeEvaluator;
import android.util.Log;

/**
 * autour : lbing
 * date : 2018/8/10 0010 14:47
 * className :
 * version : 1.0
 * description :
 */


public class PointEvaluator implements TypeEvaluator {
    @Override
    public Object evaluate(float fraction, Object startValue, Object endValue) {

        Point startPoint = (Point) startValue;
        Point endPoint = (Point) endValue;
        Log.e("TAG", "fraction==="+fraction);
        float x=startPoint.getX()+fraction*(endPoint.getX()-startPoint.getX());
        float y=startPoint.getY()+fraction*(endPoint.getY()-startPoint.getY());
        return new Point(x,y);
    }
}
