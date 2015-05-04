package com.lanhaijiye.WebMarket.adapter;

import android.content.Context;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.lanhaijiye.WebMarket.R;

import java.util.List;

/**
 * Created by Administrator on 2015/4/29.
 */
public class IconAdapter extends BaseAdapter {

    private List<Pair<Integer,String>> data;
    private int id;
    private Context context;

    public IconAdapter(List<Pair<Integer,String>> data,int layoutID,Context context) {
        this.data = data;
        id=layoutID;
        this.context = context;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view==null){
            view = LayoutInflater.from(context).inflate(id,null);
            view.setTag(new ViewHolder((ImageView)view.findViewById(R.id.item_icon), (TextView) view.findViewById(R.id.item_text)));
        }
        ImageView image = ((ViewHolder) view.getTag()).item_icon;
        TextView text = ((ViewHolder) view.getTag()).item_text;
        image.setImageResource(data.get(i).first);
        text.setText(data.get(i).second);
        return view;
    }

    private class ViewHolder{
        public ImageView item_icon;
        public TextView item_text;

        public ViewHolder(ImageView item_icon, TextView text) {
            this.item_icon = item_icon;
            this.item_text = text;
        }
    }
}
