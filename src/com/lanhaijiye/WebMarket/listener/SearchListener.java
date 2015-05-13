package com.lanhaijiye.WebMarket.listener;

import android.util.Log;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

/**
 * Created by android on 2015/5/13.
 */
public class SearchListener implements TextView.OnEditorActionListener {

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if(actionId== EditorInfo.IME_ACTION_SEARCH ||(event!=null&&event.getKeyCode()== KeyEvent.KEYCODE_ENTER)){
            //todo Ö´ÐÐËÑË÷
            Log.i("search", v.getText().toString());
            return true;
        }
        return false;
    }
}
