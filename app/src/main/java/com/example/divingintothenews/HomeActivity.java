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

    Button btn_date_select;
    Button btn_date_daily;
    Button btn_date_weekly;
    Button btn_date_monthly;

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
        btn.setTextSize(20);
    }

    public void buttonRemoveUnderline(Button btn){
        SpannableString content = new SpannableString(btn.getText());
        content.removeSpan(underlineSpan);
        content.removeSpan(boldSpan);
        btn.setText(content);
        btn.setTextSize(18);
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

    class DateBtnOnClickListener implements Button.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.btn_date_select:
                    date_selected = "지정하신 기간";
                    break;
                case R.id.btn_date_daily:
                    date_selected = "어제";
                    break;
                case R.id.btn_date_weekly:
                    date_selected = "지난 한 주";
                    break;
                case R.id.btn_date_monthly:
                    date_selected = "지난 한 달";
                    break;
            }
                setTv_title();
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

        CategoryBtnOnClickListener onClickListener1 = new CategoryBtnOnClickListener() ;

        btn_ctg_sports.setOnClickListener(onClickListener1);
        btn_ctg_entertainment.setOnClickListener(onClickListener1);
        btn_ctg_economy.setOnClickListener(onClickListener1);
        btn_ctg_politics.setOnClickListener(onClickListener1);
        btn_ctg_esports.setOnClickListener(onClickListener1);

        buttonUnderline(btn_ctg_sports);

        btn_date_select = findViewById(R.id.btn_date_select);
        btn_date_daily = findViewById(R.id.btn_date_daily);
        btn_date_weekly = findViewById(R.id.btn_date_weekly);
        btn_date_monthly = findViewById(R.id.btn_date_monthly);

        DateBtnOnClickListener onClickListener2 = new DateBtnOnClickListener();

        btn_date_select.setOnClickListener(onClickListener2);
        btn_date_daily.setOnClickListener(onClickListener2);
        btn_date_weekly.setOnClickListener(onClickListener2);
        btn_date_monthly.setOnClickListener(onClickListener2);

        tv_title = findViewById(R.id.tv_title);
    }
}