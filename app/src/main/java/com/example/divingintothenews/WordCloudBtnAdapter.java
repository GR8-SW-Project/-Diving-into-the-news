package com.example.divingintothenews;

import android.graphics.Color;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Toast;

import androidx.core.content.res.ResourcesCompat;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WordCloudBtnAdapter extends BaseAdapter{
    private HomeActivity mContext;
    private int total_btns;

    Button btn;

    private ArrayList<Keyword> keywords;

    ArrayList<Button> btns = new ArrayList<Button>();

    String date_selected;
    String category_selected;

    public WordCloudBtnAdapter(HomeActivity context, ArrayList<Keyword> keywords) {
        this.keywords = keywords;
        this.mContext = context;

        date_selected = context.getDate_selected();
        category_selected = context.getCategory_selected();
    }

    @Override
    public int getCount() {
        return keywords.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup)
    {
        if (view == null) {
            btn = new Button(mContext);
            btns.add(btn);
            btn.setBackgroundColor(Color.TRANSPARENT);
            btn.setText(keywords.get(i).getKeyword());
            Typeface typeface = mContext.getResources().getFont(R.font.roboto);
            btn.setTypeface(typeface);
            float imp = keywords.get(i).getImportance();
            imp = 12 + (imp*imp*imp*16);
            btn.setTextSize(imp);
            int color = Color.rgb(66+20*i/15, 182 - 110*i/15, 245);
            btn.setTextColor(color);
        } else {
            btn = (Button) view;
        }

        btn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                mContext.onWordClick(keywords.get(i).getKeyword());
                //Toast.makeText(v.getContext(), "Button #" + (i + 1), Toast.LENGTH_SHORT).show();
            }
        });

        return btn;
    }

    /*
    public void link_server_keyword(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://44ce-119-69-162-141.jp.ngrok.io/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService jsonPlaceHOlderApi = retrofit.create(ApiService.class);

        Call<List<KeywordPost>> call = jsonPlaceHOlderApi.getKeywordPosts(date_selected, category_selected);

        call.enqueue(new Callback<List<KeywordPost>>() {
            @Override
            public void onResponse(Call<List<KeywordPost>> call, Response<List<KeywordPost>> response) {

                if (!response.isSuccessful())
                {
                    //tv_temp.setText("Code:" + response.code());

                    return;
                }

                List<KeywordPost> posts = response.body();

                for ( KeywordPost post : posts) {
                    date.add(post.getDate());
                    category.add(post.getCategory());
                    keyword.add(post.getKeyword());
                    importance.add(post.getImportance());
                    float imp = new Float(post.getImportance());

                    imp = 12 + (imp*imp*imp*16);
                    //imp *= imp*28;

                    int color = Color.rgb(66+20*j/15, 182 - 110*j/15, 245);
                    btns.get(j).setTextColor(color);
                    Typeface typeface = mContext.getResources().getFont(R.font.roboto);
                    btns.get(j).setTypeface(typeface);

                    btns.get(j).setText(keyword.get(j));
                    btns.get(j++).setTextSize(imp);
                }

            }

            @Override
            public void onFailure(Call<List<KeywordPost>> call, Throwable t) {
                //tv_temp.setText(t.getMessage());
            }
        });
    }

     */
}

