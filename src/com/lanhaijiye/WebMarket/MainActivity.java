package com.lanhaijiye.WebMarket;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Toast;
import com.lanhaijiye.WebMarket.activities.BaseActivity;
import com.lanhaijiye.WebMarket.adapter.PagerAdapter;
import com.lanhaijiye.WebMarket.adapter.inter.PagerAdapterGettable;
import com.lanhaijiye.WebMarket.fragments.LoadingScreenFragment;
import com.lanhaijiye.WebMarket.fragments.MainUIFragment;
import com.lanhaijiye.WebMarket.fragments.abs.BaseFragment;
import com.lanhaijiye.WebMarket.services.UpdateService;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends BaseActivity implements BaseFragment.LoadingListener,PagerAdapterGettable {
    private BaseFragment mContent;
    private LoadingScreenFragment mLoading;
    private boolean isFirstLoading = true;
    private Handler mHandler = new Handler();
    private Intent serviceIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(isFirstLoading){
            mContent = new MainUIFragment();
            mLoading = new LoadingScreenFragment();
            mContent.setOnLoadFinishListener(this);
            getSupportFragmentManager().beginTransaction().add(android.R.id.content, mContent, "content").add(android.R.id.content, mLoading, "loading")
            .show(mLoading)
                    .commit();
            mContent.setUserVisibleHint(false);
        }
    }

    private void checkUpdate() {
        serviceIntent = new Intent(UpdateService.UPDATE_CHECK);
        startService(serviceIntent);
    }

    @Override
    public void loadFinished() {
        if(isFirstLoading){
            android.support.v4.app.FragmentManager manager = getSupportFragmentManager();
            android.support.v4.app.FragmentTransaction transaction = manager.beginTransaction();
            transaction.show(mContent).hide(mLoading).remove(mLoading).commitAllowingStateLoss();
            isFirstLoading=false;
            mContent.setUserVisibleHint(true);
            checkUpdate();
        }
    }

    @Override
    public void loadError(String description) {
        if(isFirstLoading){
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(MainActivity.this, description, Toast.LENGTH_SHORT).show();
                    loadFinished();
                }
            });
//            sendBroadcast(new Intent(SHUT_DOWN_ORDER));
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK)
        if(mContent!=null)
        if(mContent.canGoBack()){
            mContent.goBack();
            return true;
        }else{
            //todo 再次返回则退出
            sendBroadcast(new Intent(SHUT_DOWN_ORDER));
        }
        return super.onKeyDown(keyCode,event);
    }

    @Override
    protected void onDestroy() {
        stopService(serviceIntent);
        super.onDestroy();
    }

    @Override
    public PagerAdapter getPagerAdapter() {
        return ((PagerAdapterGettable)mContent).getPagerAdapter();
    }
}
