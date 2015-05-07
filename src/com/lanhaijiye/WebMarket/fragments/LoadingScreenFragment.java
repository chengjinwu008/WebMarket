package com.lanhaijiye.WebMarket.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.lanhaijiye.WebMarket.R;

/**
 * Created by Administrator on 2015/4/28.
 */
public class LoadingScreenFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.loading_screen,container,false);
    }
}
