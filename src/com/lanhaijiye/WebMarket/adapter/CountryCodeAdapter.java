package com.lanhaijiye.WebMarket.adapter;

import android.content.Context;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.lanhaijiye.WebMarket.R;
import com.lanhaijiye.WebMarket.entities.Continent;

/**
 * Created by android on 2015/5/12.
 */
public class CountryCodeAdapter extends BaseAdapter {

    private Continent continent;
    private Context context;

    public CountryCodeAdapter(Continent continent, Context context) {
        this.continent = continent;
        this.context = context;
    }

    @Override
    public int getCount() {
        return continent.size();
    }

    @Override
    public Object getItem(int position) {
        return continent.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //判断高度每个子view高度为20sp，则至少25dp
        int size = getCount();
        int height = (int) (size* TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,48,context.getResources().getDisplayMetrics()));
        LinearLayout.LayoutParams  layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,height);
        parent.setLayoutParams(layoutParams);

        if(convertView == null){
            convertView = View.inflate(context, R.layout.country_category_item_item,null);
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.code= (TextView) convertView.findViewById(R.id.code);
            viewHolder.name = (TextView) convertView.findViewById(R.id.name);
            convertView.setTag(viewHolder);
        }
        ViewHolder holder =(ViewHolder)convertView.getTag();
        holder.code.setText(continent.get(position).getCode());
        holder.name.setText(continent.get(position).getChineseName());
        return convertView;
    }

    public class ViewHolder{
        TextView name;
        TextView code;

        public TextView getCode() {
            return code;
        }
    }
}
