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
    ArrayList<Article> articleList;
    LayoutInflater inflater;

    public CustomBaseAdapter(Context ctx, ArrayList<Article> articleList) {
        this.context = ctx;
        this.articleList = articleList;
        inflater = LayoutInflater.from(ctx);
    }


    @Override
    public int getCount() {
        return articleList.size();
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
        titleView.setText(articleList.get(position).getTitle());
        companyView.setText(articleList.get(position).getCompany());
        dateView.setText(articleList.get(position).getDate());
        return convertView;
    }
}