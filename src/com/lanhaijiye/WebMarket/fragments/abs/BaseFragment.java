package com.lanhaijiye.WebMarket.fragments.abs;

import android.support.v4.app.Fragment;

/**
 * Created by Administrator on 2015/4/28.
 */
public abstract class BaseFragment extends Fragment {

    public static final String USER_INFO="user_info";
    public static final String USER_STATE = "user_state";

    public abstract boolean canGoBack();
    public abstract void goBack();

    public interface LoadingListener{
        void loadFinished();
        void loadError(String string);
    }

    public abstract void setOnLoadFinishListener(LoadingListener listener);

    public void changeContent(BaseFragment fragment,int animatorIn, int animatorOut){

    }
}
