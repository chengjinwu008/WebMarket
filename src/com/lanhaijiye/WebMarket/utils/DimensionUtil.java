package com.lanhaijiye.WebMarket.utils;

import android.util.DisplayMetrics;
import android.util.TypedValue;

/**
 * Created by Administrator on 2015/5/7.
 */
public class DimensionUtil {

    public static float getPixel(float dip,DisplayMetrics metrics){
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,dip,metrics);
    }
}
