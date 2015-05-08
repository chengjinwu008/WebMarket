package com.lanhaijiye.WebMarket.fragments.abs;

import android.content.Intent;
import android.support.v4.app.Fragment;
import com.lanhaijiye.WebMarket.activities.BaseActivity;

/**
 * Created by Administrator on 2015/4/28.
 */
public abstract class BaseFragment extends Fragment {
    protected LoadingListener listener;

    public abstract boolean canGoBack();
    public abstract void goBack();

    public interface LoadingListener{
        void loadFinished();
        void loadError(String string);
    }

    public void setOnLoadFinishListener(LoadingListener listener){
        this.listener = listener;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case BaseActivity.LOGIN_CODE:
                //todo 处理登录活动返回的参数
                if(resultCode == BaseActivity.RESULT_OK)
                    if(listener!=null)
                        listener.loadFinished();
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
