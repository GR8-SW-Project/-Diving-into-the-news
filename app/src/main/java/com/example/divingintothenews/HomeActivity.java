package com.example.divingintothenews;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class HomeActivity extends AppCompatActivity {

    TextView tv_title;

    Button btn_ctg_sports;
    Button btn_ctg_entertainment;
    Button btn_ctg_economy;
    Button btn_ctg_politics;
    Button btn_ctg_esports;

    String date_selected = "어제";
    String category_selected = "스포츠";

    UnderlineSpan underlineSpan = new UnderlineSpan();
    StyleSpan boldSpan = new StyleSpan(Typeface.BOLD);

    int btn_ctg_selected;

    public void buttonUnderline(Button btn) {
        SpannableString content = new SpannableString(btn.getText());
        content.setSpan(underlineSpan, 0, content.length(), 0);
        content.setSpan(boldSpan, 0, content.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        btn.setText(content);
    }

    public void buttonRemoveUnderline(Button btn){
        SpannableString content = new SpannableString(btn.getText());
        content.removeSpan(underlineSpan);
        content.removeSpan(boldSpan);
        btn.setText(content);
    }

    public void setTv_title(){
        String text = date_selected + "의 " + category_selected + " 뉴스 키워드";
        tv_title.setText(text);
    }

    class CategoryBtnOnClickListener implements Button.OnClickListener {;
        @Override
        public void onClick(View view) {
            Button btn_old = findViewById(btn_ctg_selected);
            Button btn_new = (Button) findViewById(view.getId());

            if (btn_ctg_selected != view.getId()) {
                btn_ctg_selected = btn_new.getId();

                buttonUnderline(btn_new);
                buttonRemoveUnderline(btn_old);

                switch (view.getId()) {
                    case R.id.btn_ctg_sports:
                        category_selected = "스포츠";
                        break;
                    case R.id.btn_ctg_entertainment:
                        category_selected = "연예";
                        break;
                    case R.id.btn_ctg_economy:
                        category_selected = "경제";
                        break;
                    case R.id.btn_ctg_politics:
                        category_selected = "정치";
                        break;
                    case R.id.btn_ctg_esports:
                        category_selected = "E스포츠";
                        break;

                }
                setTv_title();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getSupportActionBar().hide();

        btn_ctg_selected = R.id.btn_ctg_sports;

        btn_ctg_sports = findViewById(R.id.btn_ctg_sports);
        btn_ctg_entertainment = findViewById(R.id.btn_ctg_entertainment);
        btn_ctg_economy =  findViewById(R.id.btn_ctg_economy);
        btn_ctg_politics = findViewById(R.id.btn_ctg_politics);
        btn_ctg_esports = findViewById(R.id.btn_ctg_esports);

        CategoryBtnOnClickListener onClickListener = new CategoryBtnOnClickListener() ;

        btn_ctg_sports.setOnClickListener(onClickListener);
        btn_ctg_entertainment.setOnClickListener(onClickListener);
        btn_ctg_economy.setOnClickListener(onClickListener);
        btn_ctg_politics.setOnClickListener(onClickListener);
        btn_ctg_esports.setOnClickListener(onClickListener);

        buttonUnderline(btn_ctg_sports);

        tv_title = findViewById(R.id.tv_title);
    }
}