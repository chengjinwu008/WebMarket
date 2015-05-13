package com.lanhaijiye.WebMarket.utils;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by CJQ on 2015/5/11.
 */
public class InputMethodUtil {

    public static void showSoftInputMethod(Context context,Handler mHandler,View editTextView,int timeDelay){
        editTextView.requestFocus();
        InputMethodManager manager = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                manager.showSoftInput(editTextView, 0);
            }
        }, timeDelay);
    }

    public static void hideSoftInputMethod(Activity activityContext){
        if(activityContext!=null){
            View view = activityContext.getWindow().peekDecorView();
            if (view != null) {
                InputMethodManager inputmanger = (InputMethodManager)activityContext.getSystemService(Context.INPUT_METHOD_SERVICE);
                inputmanger.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
    }
}
