package com.lanhaijiye.WebMarket.alertDialog;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import com.lanhaijiye.WebMarket.R;

/**
 * Created by android on 2015/5/8.
 */
public class IPhoneDialog extends AlertDialog {

    public IPhoneDialog(Context context) {
        super(context);
    }

    public IPhoneDialog(Context context, int theme) {
        super(context, theme);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.iphone_alert_dialog);
    }

    public void setOnCancelListener(OnClickListener listener){
        findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(IPhoneDialog.this,1);
            }
        });
    }

    public void setOnOKListener(OnClickListener listener){
        findViewById(R.id.ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(IPhoneDialog.this,2);
            }
        });
    }
}
