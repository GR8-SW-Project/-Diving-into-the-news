package com.example.divingintothenews;

import androidx.appcompat.app.AppCompatActivity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Objects;

public class HomeActivity extends AppCompatActivity {

    // 메인 페이지 변수
    private TextView tv_title;

    private Button btn_ctg_sports,btn_ctg_entertainment, btn_ctg_economy, btn_ctg_politics, btn_ctg_culture, btn_ctg_international, btn_ctg_society;

    private Button btn_date_select, btn_date_daily, btn_date_weekly, btn_date_monthly;

    private String date_selected = "2022-09-19";
    private String date_selected_text = "어제";
    private String category_selected = "사회";

    private Toast toastMessage;

    public String getDate_selected()
    {
        return date_selected;
    }

    public String getCategory_selected()
    {
        return category_selected;
    }

    private int btn_ctg_selected;

    GridView gridview;
    WordCloudBtnAdapter adapter;

    private final UnderlineSpan underlineSpan = new UnderlineSpan();
    private final StyleSpan boldSpan = new StyleSpan(Typeface.BOLD);
    SpannableString content;

    private LocalDate now = LocalDate.now();

    private DatePickerDialog date_picker;

    private DateTimeFormatter formatter;

    // 선택된 카테고리 버튼 표시용 메소드
    public void buttonHighlight(Button btn)
    {
        content = new SpannableString(btn.getText());
        content.setSpan(underlineSpan, 0, content.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        content.setSpan(boldSpan, 0, content.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        btn.setText(content);
        btn.setTextSize(20);
    }

    // 타 카테고리 버튼 선택 시 기존 표시 제거
    public void buttonRemoveHighlight(Button btn)
    {
        content = new SpannableString(btn.getText());
        content.removeSpan(underlineSpan);
        content.removeSpan(boldSpan);
        btn.setText(content);
        btn.setTextSize(18);
    }

    public void makeToast(String text)
    {
        if (toastMessage != null){toastMessage.cancel();}
        toastMessage = Toast.makeText(this, text, Toast.LENGTH_SHORT);
        toastMessage.show();
    }

    /*
    // 뉴스 키워드 제목 변경
    public void setTv_title()
    {
        String text = date_selected_text + "의 " + category_selected + " 뉴스 키워드";
        tv_title.setText(text);
    }
     */

    // 워드 클라우드에서 워드 클릭시 화면 변경
    public void onWordClick(String keyword)
    {
        Intent intent = new Intent(this, NewsListActivity.class);
        intent.putExtra("date", date_selected);
        intent.putExtra("category", category_selected);
        intent.putExtra("keyword", keyword);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }

    // 카테고리 선택 버튼 리스너
    class CategoryBtnOnClickListener implements Button.OnClickListener {
        @Override
        public void onClick(View view) {
            Button btn_old = findViewById(btn_ctg_selected);
            Button btn_new = findViewById(view.getId());

            if (btn_ctg_selected != view.getId()) {
                btn_ctg_selected = btn_new.getId();

                buttonHighlight(btn_new);
                buttonRemoveHighlight(btn_old);

                if (view.getId() == R.id.btn_ctg_society) {
                    category_selected = "사회";}
                else if (view.getId() == R.id.btn_ctg_international) {
                    category_selected = "국제";}
                else if (view.getId() == R.id.btn_ctg_sports) {
                    category_selected = "스포츠";}
                else if (view.getId() == R.id.btn_ctg_culture) {
                    category_selected = "생활·문화";}
                else if (view.getId() == R.id.btn_ctg_economy) {
                    category_selected = "경제";}
                else if (view.getId() == R.id.btn_ctg_politics) {
                    category_selected = "정치";}
                else if (view.getId() == R.id.btn_ctg_entertainment) {
                    category_selected = "연예";}

                InitializeWordCloud();
                //setTv_title();
            }
        }
    }

    // 일간, 주간, 월간 선택 버튼 리스너
    class DateBtnOnClickListener implements Button.OnClickListener {
        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.btn_date_daily){
                date_selected_text = "어제";}
            else if (view.getId() == R.id.btn_date_weekly){
                date_selected_text = "지난 한 주";}
            else if (view.getId() == R.id.btn_date_monthly){
                date_selected_text = "지난 한 달";}
            //setTv_title();
        }
    }

    // 날짜선택 버튼 리스너
    class DatePickerBtnOnClickListener implements Button.OnClickListener {
        @Override
        public void onClick(View view) {
            InitializeDatePicker();
            date_picker.updateDate(now.getYear(), now.getMonthValue()-1, now.getDayOfMonth());
            date_picker.show();
        }
    }

    // 날짜 선택 기능
    public void InitializeDatePicker()
    {
        date_picker = new DatePickerDialog(this, null, now.getYear(), now.getMonthValue()-1, now.getDayOfMonth());
        DatePickerOnDateSetListener listener = new DatePickerOnDateSetListener();
        date_picker.setOnDateSetListener(listener);
    }

    // 날짜 선택 리스너
    class DatePickerOnDateSetListener implements DatePickerDialog.OnDateSetListener{
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            LocalDate local_date = LocalDate.of(year, monthOfYear+1, dayOfMonth);
            formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String local_date_text = local_date.format(formatter);
            date_selected = local_date_text;
            InitializeWordCloud();
            date_selected_text = local_date_text;
            //setTv_title();
        }
    }

    public void InitializeWordCloud()
    {
        gridview.setAdapter(null);
        LinkServer link = new LinkServer(this, date_selected, category_selected);
    }

    // 홈 화면 변수 초기화
    public void InitializeVariable()
    {
        btn_ctg_selected = R.id.btn_ctg_society;
    }

    // 홈 화면 내 View 들을 초기화
    public void InitializeView()
    {
        //카테고리 지정 버튼
        btn_ctg_sports = findViewById(R.id.btn_ctg_sports);
        btn_ctg_entertainment = findViewById(R.id.btn_ctg_entertainment);
        btn_ctg_economy =  findViewById(R.id.btn_ctg_economy);
        btn_ctg_politics = findViewById(R.id.btn_ctg_politics);
        btn_ctg_international = findViewById(R.id.btn_ctg_international);
        btn_ctg_society = findViewById(R.id.btn_ctg_society);
        btn_ctg_culture = findViewById(R.id.btn_ctg_culture);

        //기간 지정 버튼
        btn_date_select = findViewById(R.id.btn_date_select);
        btn_date_daily = findViewById(R.id.btn_date_daily);
        btn_date_weekly = findViewById(R.id.btn_date_weekly);
        btn_date_monthly = findViewById(R.id.btn_date_monthly);

        /*
        //TextView
        tv_title = findViewById(R.id.tv_title);
         */

        // 워드클라우드 GridView
        gridview = (GridView) findViewById(R.id.gridView1);
    }

    // 홈 화면 리스너
    public void InitializeListener()
    {
        CategoryBtnOnClickListener onClickListener1 = new CategoryBtnOnClickListener() ;
        btn_ctg_society.setOnClickListener(onClickListener1);
        btn_ctg_sports.setOnClickListener(onClickListener1);
        btn_ctg_entertainment.setOnClickListener(onClickListener1);
        btn_ctg_economy.setOnClickListener(onClickListener1);
        btn_ctg_politics.setOnClickListener(onClickListener1);
        btn_ctg_culture.setOnClickListener(onClickListener1);
        btn_ctg_international.setOnClickListener(onClickListener1);

        DateBtnOnClickListener onClickListener2 = new DateBtnOnClickListener();
        btn_date_daily.setOnClickListener(onClickListener2);
        btn_date_weekly.setOnClickListener(onClickListener2);
        btn_date_monthly.setOnClickListener(onClickListener2);

        DatePickerBtnOnClickListener onClickListener3 = new DatePickerBtnOnClickListener();
        btn_date_select.setOnClickListener(onClickListener3);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Objects.requireNonNull(getSupportActionBar()).hide();

        InitializeVariable();
        InitializeView();
        InitializeListener();

        buttonHighlight(btn_ctg_society);

        InitializeWordCloud();
    }
}
