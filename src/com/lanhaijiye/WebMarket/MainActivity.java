package com.lanhaijiye.WebMarket;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.view.KeyEvent;
import android.widget.Toast;
import com.lanhaijiye.WebMarket.activities.BaseActivity;
import com.lanhaijiye.WebMarket.fragments.LoadingScreenFragment;
import com.lanhaijiye.WebMarket.fragments.MainUIFragment;
import com.lanhaijiye.WebMarket.fragments.abs.BaseFragment;
import com.lanhaijiye.WebMarket.services.UpdateService;

public class MainActivity extends BaseActivity implements BaseFragment.LoadingListener {
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
            getFragmentManager().beginTransaction().add(android.R.id.content, mLoading, "loading").add(android.R.id.content, mContent, "content")
                    .show(mLoading).hide(mContent)
                    .commit();
        }
    }

    private void checkUpdate() {
        serviceIntent = new Intent(UpdateService.UPDATE_CHECK);
        startService(serviceIntent);
    }

    @Override
    public void loadFinished() {
        if(isFirstLoading){
            FragmentManager manager = getFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.show(mContent).hide(mLoading).remove(mLoading).commit();
            isFirstLoading=false;
            checkUpdate();
        }
    }

    @Override
    public void loadError(String description) {
        if(isFirstLoading){
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(MainActivity.this,description,Toast.LENGTH_SHORT).show();
                }
            });
            sendBroadcast(new Intent(SHUT_DOWN_ORDER));
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK)
        if(mContent.canGoBack()){
            mContent.goBack();
            return true;
        }
        return super.onKeyDown(keyCode,event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService(serviceIntent);
    }
}
