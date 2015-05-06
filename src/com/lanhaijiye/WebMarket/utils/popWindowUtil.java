package com.lanhaijiye.WebMarket.utils;

import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.widget.PopupWindow;

/**
 * Created by Administrator on 2015/5/6.
 */
public class popWindowUtil {

    public static void show(View viewToShow,View viewToAttach,PopupWindow window,int AnimationStyle){
        window.setContentView(viewToShow);
        window.setAnimationStyle(AnimationStyle);
        window.setTouchable(true);
        window.setOutsideTouchable(false);
        window.setFocusable(true);
        window.setBackgroundDrawable(new ColorDrawable(0));
        window.showAtLocation(viewToAttach, Gravity.CENTER, 0, 0);
    }

}
