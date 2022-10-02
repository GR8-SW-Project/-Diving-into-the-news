package com.example.divingintothenews;

import androidx.appcompat.app.AppCompatActivity;
import android.app.DatePickerDialog;
import android.app.Dialog;
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
import android.widget.Toast;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class HomeActivity extends AppCompatActivity {

    private TextView tv_title;
    private TextView tv_temp;

    private Button btn_ctg_sports,btn_ctg_entertainment, btn_ctg_economy, btn_ctg_politics, btn_ctg_esports;

    private Button btn_date_select, btn_date_daily, btn_date_weekly, btn_date_monthly;

    private Button btn_start_date, btn_end_date;

    private Button btn_confirm, btn_cancel;

    private String date_selected = "어제";
    private String category_selected = "스포츠";
    private Button picked_button;

    private LocalDate startDate, endDate;
    private LocalDate now;

    private DatePickerDialog date_picker;

    private DateTimeFormatter formatter;

    UnderlineSpan underlineSpan = new UnderlineSpan();
    StyleSpan boldSpan = new StyleSpan(Typeface.BOLD);

    private int btn_ctg_selected;

    // 선택된 카테고리 버튼 표시용 메소드
    public void buttonHighlight(Button btn)
    {
        SpannableString content = new SpannableString(btn.getText());
        content.setSpan(underlineSpan, 0, content.length(), 0);
        content.setSpan(boldSpan, 0, content.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        btn.setText(content);
        btn.setTextSize(20);
    }

    // 타 카테고리 버튼 선택 시 기존 표시 제거
    public void buttonRemoveHighlight(Button btn)
    {
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

    class CategoryBtnOnClickListener implements Button.OnClickListener {
        @Override
        public void onClick(View view) {
            Button btn_old = findViewById(btn_ctg_selected);
            Button btn_new = (Button) findViewById(view.getId());

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

    class DatePickerBtnOnClickListener implements Button.OnClickListener {
        @Override
        public void onClick(View view) {
            startDate = LocalDate.now();
            endDate = LocalDate.now();

            date_picker.show();
            if(view.getId() == R.id.btn_start_date){
                picked_button = btn_start_date;}
            else if(view.getId() == R.id.btn_end_date){
                picked_button = btn_end_date;}
        }
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
    }

    public void InitializeDatePicker()
    {
        Dialog popup = new Dialog(this);

        date_picker = new DatePickerDialog(this, null, now.getYear(), now.getMonthValue()-1, now.getDayOfMonth());

        DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                LocalDate local_date = LocalDate.of(year, monthOfYear, dayOfMonth);
                String local_date_text = local_date.format(formatter);

                picked_button.setText(local_date_text);

                if (picked_button == btn_start_date){
                    startDate = local_date;}
                else{
                    endDate = local_date;}

                btn_confirm.setEnabled(startDate != null && endDate != null && ((startDate.compareTo(endDate) <= 0)));

                Toast.makeText(getApplicationContext(), year + "년" + monthOfYear + "월" + dayOfMonth +"일", Toast.LENGTH_SHORT).show();
            }
        };

        date_picker.setOnDateSetListener(listener);

        btn_date_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popup.setContentView(R.layout.popup_layout);

                popup.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                popup.show();

                DatePickerBtnOnClickListener onClickListener3 = new DatePickerBtnOnClickListener();

                btn_start_date = (Button) popup.findViewById(R.id.btn_start_date);
                btn_end_date = (Button) popup.findViewById(R.id.btn_end_date);

                btn_start_date.setOnClickListener(onClickListener3);
                btn_end_date.setOnClickListener(onClickListener3);

                String local_date_text = now.format(formatter);
                btn_start_date.setText(local_date_text);
                btn_end_date.setText(local_date_text);

                btn_confirm = (Button) popup.findViewById(R.id.btn_confirm);
                btn_cancel = (Button) popup.findViewById(R.id.btn_cancel);

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
        });
    }

    public void InitializeVariable()
    {
        now = LocalDate.now();
        formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        btn_ctg_selected = R.id.btn_ctg_sports;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Objects.requireNonNull(getSupportActionBar()).hide();
        InitializeVariable();
        InitializeView();
        InitializeListener();
        InitializeDatePicker();

        buttonHighlight(btn_ctg_sports);
    }
}
