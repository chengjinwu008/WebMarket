package com.lanhaijiye.WebMarket.alertDialog;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
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

    public IPhoneDialog setOnCancelListener(OnClickListener listener) {
        findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(IPhoneDialog.this, 1);
            }
        });
        return this;
    }

    public IPhoneDialog setOnOKListener(OnClickListener listener) {
        findViewById(R.id.ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(IPhoneDialog.this, 2);
            }
        });
        return this;

    }

    public IPhoneDialog changeText(String string) {
        ((TextView) findViewById(R.id.textView)).setText(string);
        return this;

    }

    public IPhoneDialog changeOKText(String string) {
        ((Button) findViewById(R.id.ok)).setText(string);
        return this;

    }

    public IPhoneDialog changeCancelText(String string) {
        ((Button) findViewById(R.id.cancel)).setText(string);
        return this;

    }
}
