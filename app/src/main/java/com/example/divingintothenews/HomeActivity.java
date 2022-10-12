package com.example.divingintothenews;

import androidx.appcompat.app.AppCompatActivity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeActivity extends AppCompatActivity {

    // 메인 페이지 변수
    private TextView tv_title;
    private TextView tv_temp;

    private Button btn_ctg_sports,btn_ctg_entertainment, btn_ctg_economy, btn_ctg_politics, btn_ctg_esports;

    private Button btn_date_select, btn_date_daily, btn_date_weekly, btn_date_monthly;

    private String date_selected = "어제";
    private String category_selected = "스포츠";

    private int btn_ctg_selected;

    // 팝업 변수
    private Dialog popup;

    private Button btn_start_date, btn_end_date, btn_confirm, btn_cancel, picked_button;

    private LocalDate startDate, endDate;
    private LocalDate now;

    private DatePickerDialog date_picker;

    private DateTimeFormatter formatter;

    // 선택된 카테고리 버튼 표시용 메소드
    public void buttonHighlight(Button btn)
    {
        UnderlineSpan underlineSpan = new UnderlineSpan();
        StyleSpan boldSpan = new StyleSpan(Typeface.BOLD);
        SpannableString content = new SpannableString(btn.getText());
        content.setSpan(underlineSpan, 0, content.length(), 0);
        content.setSpan(boldSpan, 0, content.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        btn.setText(content);
        btn.setTextSize(20);
    }

    // 타 카테고리 버튼 선택 시 기존 표시 제거
    public void buttonRemoveHighlight(Button btn)
    {
        UnderlineSpan underlineSpan = new UnderlineSpan();
        StyleSpan boldSpan = new StyleSpan(Typeface.BOLD);
        SpannableString content = new SpannableString(btn.getText());
        content.removeSpan(underlineSpan);
        content.removeSpan(boldSpan);
        btn.setText(content);
        btn.setTextSize(18);
    }

    // 뉴스 키워드 제목 변경
    public void setTv_title()
    {
        String text = date_selected + "의 " + category_selected + " 뉴스 키워드";
        tv_title.setText(text);
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

                if (view.getId() == R.id.btn_ctg_sports) {
                    category_selected = "스포츠";}
                else if (view.getId() == R.id.btn_ctg_entertainment) {
                    category_selected = "연예";}
                else if (view.getId() == R.id.btn_ctg_economy) {
                    category_selected = "경제";}
                else if (view.getId() == R.id.btn_ctg_politics) {
                    category_selected = "정치";}
                else if (view.getId() == R.id.btn_ctg_esports) {
                    category_selected = "E스포츠";
                }
                setTv_title();
            }
        }
    }

    // 일간, 주간, 월간 선택 버튼 리스너
    class DateBtnOnClickListener implements Button.OnClickListener {
        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.btn_date_daily){
                date_selected = "어제";}
            else if (view.getId() == R.id.btn_date_weekly){
                date_selected = "지난 한 주";}
            else if (view.getId() == R.id.btn_date_monthly){
                date_selected = "지난 한 달";}
            setTv_title();
        }
    }

    // 기간 선택 버튼 리스너
    class DateSelectBtnOnClickListener implements View.OnClickListener{
        @Override
        public void onClick(View view)
        {
            InitializePopup();
        }
    }

    // 팝업 || 시작일, 종료일 버튼 리스너
    class DatePickerBtnOnClickListener implements Button.OnClickListener {
        @Override
        public void onClick(View view) {
            startDate = LocalDate.now();
            endDate = LocalDate.now();

            date_picker.updateDate(now.getYear(), now.getMonthValue()-1, now.getDayOfMonth());
            date_picker.show();
            if(view.getId() == R.id.btn_start_date){
                picked_button = btn_start_date;}
            else if(view.getId() == R.id.btn_end_date){
                picked_button = btn_end_date;}
        }
    }

    // 팝업 || 날짜 선택 리스너
    class DatePickerOnDateSetListener implements DatePickerDialog.OnDateSetListener{
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            LocalDate local_date = LocalDate.of(year, monthOfYear+1, dayOfMonth);
            String local_date_text = local_date.format(formatter);

            picked_button.setText(local_date_text);

            if (picked_button == btn_start_date){
                startDate = local_date;}
            else{
                endDate = local_date;}

            btn_confirm.setEnabled(startDate != null && endDate != null && ((startDate.compareTo(endDate) <= 0)));
        }
    }

    // 홈 화면 변수 초기화
    public void InitializeVariable()
    {
        btn_ctg_selected = R.id.btn_ctg_sports;
    }

    // 홈 화면 내 View 들을 초기화
    public void InitializeView()
    {
        //카테고리 지정 버튼
        btn_ctg_sports = findViewById(R.id.btn_ctg_sports);
        btn_ctg_entertainment = findViewById(R.id.btn_ctg_entertainment);
        btn_ctg_economy =  findViewById(R.id.btn_ctg_economy);
        btn_ctg_politics = findViewById(R.id.btn_ctg_politics);
        btn_ctg_esports = findViewById(R.id.btn_ctg_esports);

        //기간 지정 버튼
        btn_date_select = findViewById(R.id.btn_date_select);
        btn_date_daily = findViewById(R.id.btn_date_daily);
        btn_date_weekly = findViewById(R.id.btn_date_weekly);
        btn_date_monthly = findViewById(R.id.btn_date_monthly);

        //TextView
        tv_title = findViewById(R.id.tv_title);
        tv_temp = findViewById(R.id.tv_temp);
    }

    // 홈 화면 리스너
    public void InitializeListener()
    {
        CategoryBtnOnClickListener onClickListener1 = new CategoryBtnOnClickListener() ;
        btn_ctg_sports.setOnClickListener(onClickListener1);
        btn_ctg_entertainment.setOnClickListener(onClickListener1);
        btn_ctg_economy.setOnClickListener(onClickListener1);
        btn_ctg_politics.setOnClickListener(onClickListener1);
        btn_ctg_esports.setOnClickListener(onClickListener1);

        DateBtnOnClickListener onClickListener2 = new DateBtnOnClickListener();
        btn_date_daily.setOnClickListener(onClickListener2);
        btn_date_weekly.setOnClickListener(onClickListener2);
        btn_date_monthly.setOnClickListener(onClickListener2);

        DateSelectBtnOnClickListener onClickListener3 = new DateSelectBtnOnClickListener();
        btn_date_select.setOnClickListener(onClickListener3);
    }

    // 팝업 || 팝업 초기화
    public void InitializePopup()
    {
        popup = new Dialog(this);
        popup.setContentView(R.layout.popup_layout);
        popup.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popup.show();

        InitializePopupView();
        InitializePopupListener();
        InitializePopupDatePicker();
    }

    // 팝업 || 팝업 내 View 초기화
    public void InitializePopupView()
    {
        now = LocalDate.now();
        formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");

        btn_start_date = popup.findViewById(R.id.btn_start_date);
        btn_end_date = popup.findViewById(R.id.btn_end_date);

        String local_date_text = now.format(formatter);
        btn_start_date.setText(local_date_text);
        btn_end_date.setText(local_date_text);

        btn_confirm = popup.findViewById(R.id.btn_confirm);
        btn_cancel = popup.findViewById(R.id.btn_cancel);
    }

    // 팝업 || 팝업 내 리스너
    public void InitializePopupListener()
    {
        DatePickerBtnOnClickListener onClickListener4 = new DatePickerBtnOnClickListener();

        btn_start_date.setOnClickListener(onClickListener4);
        btn_end_date.setOnClickListener(onClickListener4);

        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = startDate + " / " + endDate;
                tv_temp.setText(text);

                date_selected = "지정된 기간";
                setTv_title();

                popup.cancel();
            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popup.cancel();
            }
        });
    }

    // 팝업 || 팝업 내 날짜 선택 기능
    public void InitializePopupDatePicker()
    {
        date_picker = new DatePickerDialog(this, null, now.getYear(), now.getMonthValue()-1, now.getDayOfMonth());
        DatePickerOnDateSetListener listener = new DatePickerOnDateSetListener();
        date_picker.setOnDateSetListener(listener);
    }

    // 장고 연동
    public void server_temp(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://9aa2-119-69-162-141.jp.ngrok.io/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService jsonPlaceHOlderApi = retrofit.create(ApiService.class);

        Call<List<Post>> call = jsonPlaceHOlderApi.getPosts();

        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {

                if (!response.isSuccessful())
                {
                    tv_temp.setText("Code:" + response.code());
                    return;
                }

                List<Post> posts = response.body();

                for ( Post post : posts) {
                    String content ="";
                    //content += "ID : " + post.getId() + "\n";
                    content += "title : " + post.getTitle() + "\n" +
                            "headline : " + post.getHeadline().substring(0, 30) + "...\n" +
                            "date_news : " + post.getDate_news() + "\n" +
                            "news_link : " + post.getNews_link() + "\n" +
                            "content  : " + post.getContent().substring(0, 30) + "...\n" +
                            "category : " + post.getCategory() + "\n" +
                            "site : " + post.getSite() + "\n\n";

                    tv_temp.append(content);
                }
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                tv_temp.setText(t.getMessage());
            }
        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Objects.requireNonNull(getSupportActionBar()).hide();
        //server_temp();
        InitializeVariable();
        InitializeView();
        InitializeListener();

        buttonHighlight(btn_ctg_sports);

        Button temp = findViewById(R.id.button);

        temp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, NewsListActivity.class);
                startActivity(intent);
            }
        });
    }
}
