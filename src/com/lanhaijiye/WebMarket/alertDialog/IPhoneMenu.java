package com.lanhaijiye.WebMarket.alertDialog;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import com.lanhaijiye.WebMarket.R;
import com.lanhaijiye.WebMarket.adapter.IPhoneMenuAdapter;

import java.util.List;

/**
 * Created by android on 2015/5/12.
 */
public class IPhoneMenu extends AlertDialog implements View.OnClickListener {
    private IPhoneMenuListener listener;
    List<Pair<Integer,String>> data;
    Context context;

    public interface IPhoneMenuListener{
        void onclick(AdapterView<?> parent, View view, int position, long id,AlertDialog dialog);
    }

    public IPhoneMenu(Context context, List<Pair<Integer, String>> data,IPhoneMenuListener listener) {
        super(context);
        this.data = data;
        this.context = context;
        this.listener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.iphone_menu);
        ListView list = (ListView) findViewById(R.id.menu);
        list.setAdapter(new IPhoneMenuAdapter(data, context));
        if(listener!=null)
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(listener!=null)
                    listener.onclick(parent,view,position,id,IPhoneMenu.this);
            }
        });
        findViewById(R.id.cancel).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        this.dismiss();
    }
}