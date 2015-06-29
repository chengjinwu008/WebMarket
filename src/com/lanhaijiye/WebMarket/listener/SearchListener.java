package com.lanhaijiye.WebMarket.listener;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;
import com.lanhaijiye.WebMarket.CommonDataObject;
import com.lanhaijiye.WebMarket.R;
import com.lanhaijiye.WebMarket.activities.CommonWebViewActivity;

/**
 * Created by android on 2015/5/13.
 */
public class SearchListener implements TextView.OnEditorActionListener {

    public static final String SEARCH_URL="/mobile/search.php?keywords=";
    private Context context;

    public SearchListener(Context context) {
        this.context = context;
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if((actionId== EditorInfo.IME_ACTION_SEARCH ||(event!=null&&event.getKeyCode()== KeyEvent.KEYCODE_ENTER))&&event.getAction()==KeyEvent.ACTION_DOWN){
            //todo Ö´ÐÐËÑË÷
            Log.i("search", v.getText().toString());
            String s =v.getText().toString();
            NewWebview(new Intent(),s+context.getString(R.string.search_result), CommonDataObject.MAIN_URL+SEARCH_URL+s);
            return true;
        }
        return false;
    }

    public void NewWebview(Intent intent,String title,String url) {
        intent.setClass(context.getApplicationContext(), CommonWebViewActivity.class);
        intent.putExtra(CommonWebViewActivity.INTENT_TITLE_KEY,title);
        intent.putExtra(CommonWebViewActivity.INTENT_WEB_URL_KEY,url);
        context.startActivity(intent);
    }
}
