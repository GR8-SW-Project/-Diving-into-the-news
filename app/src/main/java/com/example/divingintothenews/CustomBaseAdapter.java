package com.example.divingintothenews;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomBaseAdapter extends BaseAdapter {
    Context context;
    ArrayList<String> titleList;
    ArrayList<String> companyList;
    ArrayList<String> dateList;
    ArrayList<String> urlList;
    LayoutInflater inflater;

    public CustomBaseAdapter(Context ctx, ArrayList<String> titleList, ArrayList<String> companyList, ArrayList<String> dateList) {
        this.context = ctx;
        this.titleList = titleList;
        this.companyList = companyList;
        this.dateList = dateList;
        inflater = LayoutInflater.from(ctx);
    }


    @Override
    public int getCount() {
        return titleList.size();
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
        titleView.setText(titleList.get(position));
        companyView.setText(companyList.get(position));
        dateView.setText(dateList.get(position));
        return convertView;
    }
}