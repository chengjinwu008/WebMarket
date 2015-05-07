package com.lanhaijiye.WebMarket.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.lanhaijiye.WebMarket.utils.DimensionUtil;

/**
 * Created by Administrator on 2015/5/7.
 */

/**
 * 纯文本list适配器，只有一个textview
 */
public class TextAdapter extends BaseAdapter {

    private String[] data;
    private Context context;

    public TextAdapter(String[] data,Context context) {
        this.data = data;
        this.context = context;
    }

    @Override
    public int getCount() {
        return data.length;
    }

    @Override
    public String getItem(int position) {
        return data[0];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null) {
            TextView text = new TextView(context);
//            text.setTextSize(DimensionUtil.getPixel(10, context.getResources().getDisplayMetrics()));//设置字体大小
            text.setGravity(Gravity.LEFT);
            text.setTextColor(Color.BLACK);
            text.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            convertView = text;
//            parent.addView(convertView);
        }
        ((TextView)convertView).setText(data[position]);
        return convertView;
    }
}
