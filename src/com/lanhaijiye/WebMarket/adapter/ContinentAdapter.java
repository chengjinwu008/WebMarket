package com.lanhaijiye.WebMarket.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.lanhaijiye.WebMarket.R;
import com.lanhaijiye.WebMarket.entities.Continent;

import java.util.List;

/**
 * Created by android on 2015/5/12.
 */
public class ContinentAdapter extends BaseAdapter {

    private List<Continent> continents;
    private Context context;
    private AdapterView.OnItemClickListener listener;

    public ContinentAdapter(List<Continent> continents, Context context,AdapterView.OnItemClickListener listener) {
        this.continents = continents;
        this.context = context;
        this.listener=listener;
    }

    @Override
    public int getCount() {
        return continents.size();
    }

    @Override
    public Object getItem(int position) {
        return continents.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            convertView = View.inflate(context, R.layout.country_list_category,null);
            ViewHolder holder = new ViewHolder();
            holder.name = (TextView) convertView.findViewById(R.id.name);
            holder.item = (ListView) convertView.findViewById(R.id.country_category_item);
            convertView.setTag(holder);
            holder.item.setOnItemClickListener(listener);
        }
        ViewHolder holder = (ViewHolder) convertView.getTag();
        holder.name.setText(continents.get(position).getChineseName());
        holder.item.setAdapter(new CountryCodeAdapter(continents.get(position),context));
        return convertView;
    }

    private class ViewHolder{
        TextView name;
        ListView item;
    }


}
