package com.lanhaijiye.WebMarket.adapter;

import android.content.Context;
import android.util.Pair;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.lanhaijiye.WebMarket.R;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by android on 2015/5/12.
 */
public class IPhoneMenuAdapter extends BaseAdapter {

    List<Pair<Integer,String>> list;
    Context context;
    private final int height = 50;

    public IPhoneMenuAdapter(List<Pair<Integer, String>> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            convertView = View.inflate(context, R.layout.iphone_menu_item,null);
            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,(int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,height,context.getResources().getDisplayMetrics()));
            convertView.setLayoutParams(layoutParams);
            ViewHolder holder = new ViewHolder();
            holder.image = (ImageView) convertView.findViewById(R.id.image);
            holder.text = (TextView) convertView.findViewById(R.id.menu_text);
            convertView.setTag(holder);
            if(position==getCount()-1){
                convertView.findViewById(R.id.divider).setVisibility(View.GONE);
            }
        }

        ViewHolder holder = (ViewHolder) convertView.getTag();
        Integer first=list.get(position).first;
        if(first!=null)
        holder.image.setImageResource(list.get(position).first);
        String second=list.get(position).second;
        if(second!=null)
        holder.text.setText(list.get(position).second);//
        return convertView;
    }

    class ViewHolder{
        ImageView image;
        TextView text;
    }
}
