package com.example.divingintothenews;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomBaseAdapter extends BaseAdapter {

    Context context;
    String listTitle[];
    String listCompany[];
    String listDate[];
    LayoutInflater inflater;

    public CustomBaseAdapter(Context ctx, String [] titleList, String [] companyList, String [] dateList) {
        this.context = ctx;
        this.listTitle = titleList;
        this.listCompany = companyList;
        this.listDate = dateList;
        inflater = LayoutInflater.from(ctx);
    }


    @Override
    public int getCount() {
        return listTitle.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = inflater.inflate(R.layout.activity_custom_list_view, null);
        TextView titleView = (TextView) convertView.findViewById(R.id.titleView);
        TextView companyView = (TextView) convertView.findViewById(R.id.company);
        TextView dateView = (TextView) convertView.findViewById(R.id.date);
        titleView.setText(listTitle[position]);
        companyView.setText(listCompany[position]);
        dateView.setText(listDate[position]);
        return convertView;
    }
}