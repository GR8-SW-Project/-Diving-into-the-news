package com.example.divingintothenews;

import android.graphics.Color;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import androidx.core.content.res.ResourcesCompat;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WordCloudBtnAdapter extends BaseAdapter{
    private HomeActivity mContext;
    private int btn_id;
    private int total_btns = 15;

    int[] id;

    String[] keyword, importance;

    int i = 0;

    Button btn;
    Button[] btns = new Button[16];

    public WordCloudBtnAdapter(HomeActivity context) {
        id = new int[15];
        keyword = new String[15];
        importance = new String[15];

        link_server_keyword();

        this.mContext = context;
    }

    @Override
    public int getCount() {
        return total_btns;
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
            btns[btn_id++] = btn;
            btn.setBackgroundColor(Color.TRANSPARENT);
        } else {
            btn = (Button) view;
        }

        btn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                mContext.onWordClick();
                //Toast.makeText(v.getContext(), "Button #" + (i + 1), Toast.LENGTH_SHORT).show();
            }
        });

        return btn;
    }

    public void link_server_keyword(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://20f9-119-69-162-141.jp.ngrok.io/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService jsonPlaceHOlderApi = retrofit.create(ApiService.class);

        Call<List<KeywordPost>> call = jsonPlaceHOlderApi.getKeywordPosts();

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
                    String content ="";
                    id[i] = post.getId();
                    keyword[i] = post.getKeyword();
                    importance[i] = post.getImportance();
                    float imp = new Float(post.getImportance());

                    imp = 12 + (imp*imp*imp*16);
                    //imp *= imp*28;

                    int color = Color.rgb(66+20*i/15, 182 - 110*i/15, 245);
                    btns[i].setTextColor(color);
                    Typeface typeface = mContext.getResources().getFont(R.font.roboto);
                    btns[i].setTypeface(typeface);

                    btns[i].setText(keyword[i]);
                    btns[i].setTextSize(imp);
                    i++;
                }

            }

            @Override
            public void onFailure(Call<List<KeywordPost>> call, Throwable t) {
                //tv_temp.setText(t.getMessage());
            }
        });
    }
}

