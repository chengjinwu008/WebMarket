package com.lanhaijiye.WebMarket.utils;

import android.content.DialogInterface;
import android.content.Intent;
import com.lanhaijiye.WebMarket.activities.BaseActivity;
import com.lanhaijiye.WebMarket.activities.LoginTableActivity;
import com.lanhaijiye.WebMarket.alertDialog.IPhoneDialog;
import com.lanhaijiye.WebMarket.fragments.abs.BaseFragment;

/**
 * Created by android on 2015/5/8.
 */
public class LoginUtil {

    public static void showLoginAlertForFragment(BaseFragment activityContext){
        IPhoneDialog iPhoneDialog = new IPhoneDialog(activityContext.getActivity());
        iPhoneDialog.show();
        iPhoneDialog.setOnCancelListener(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        iPhoneDialog.setOnOKListener(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                doShowLoginForFragment(activityContext);
            }
        });
    }

    public static void doShowLoginForFragment(BaseFragment activityContext) {
        Intent intent =new Intent(activityContext.getActivity(),LoginTableActivity.class);
        activityContext.startActivityForResult(intent, BaseActivity.LOGIN_CODE);
    }
}
