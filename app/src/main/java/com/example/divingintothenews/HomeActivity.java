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

    TextView tv_title;
    TextView tv_temp;

    Button btn_ctg_sports;
    Button btn_ctg_entertainment;
    Button btn_ctg_economy;
    Button btn_ctg_politics;
    Button btn_ctg_esports;

    Button btn_date_select;
    Button btn_date_daily;
    Button btn_date_weekly;
    Button btn_date_monthly;

    Button btn_start_date;
    Button btn_end_date;

    Button btn_confirm;
    Button btn_cancel;

    String date_selected = "어제";
    String category_selected = "스포츠";
    Button picked_button;

    LocalDate startDate;
    LocalDate endDate;

    DatePickerDialog dialog;

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

    class CategoryBtnOnClickListener implements Button.OnClickListener {
        @Override
        public void onClick(View view) {
            Button btn_old = findViewById(btn_ctg_selected);
            Button btn_new = (Button) findViewById(view.getId());

            if (btn_ctg_selected != view.getId()) {
                btn_ctg_selected = btn_new.getId();

                buttonUnderline(btn_new);
                buttonRemoveUnderline(btn_old);

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
            dialog.show();
            if(view.getId() == R.id.btn_start_date){
                picked_button = btn_start_date;}
            else if(view.getId() == R.id.btn_end_date){
                picked_button = btn_end_date;}
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Objects.requireNonNull(getSupportActionBar()).hide();

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

        btn_date_daily.setOnClickListener(onClickListener2);
        btn_date_weekly.setOnClickListener(onClickListener2);
        btn_date_monthly.setOnClickListener(onClickListener2);

        tv_title = findViewById(R.id.tv_title);
        tv_temp = findViewById(R.id.tv_temp);

        Dialog mDialog = new Dialog(this);

        dialog = new DatePickerDialog(this, null, 2022, 9, 28);

        DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                LocalDate local_date = LocalDate.of(year, monthOfYear, dayOfMonth);
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
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

        dialog.setOnDateSetListener(listener);

        btn_date_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDialog.setContentView(R.layout.popup_layout);

                mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                mDialog.show();

                DatePickerBtnOnClickListener onClickListener3 = new DatePickerBtnOnClickListener();

                btn_start_date = (Button) mDialog.findViewById(R.id.btn_start_date);
                btn_end_date = (Button) mDialog.findViewById(R.id.btn_end_date);

                btn_start_date.setOnClickListener(onClickListener3);
                btn_end_date.setOnClickListener(onClickListener3);

                btn_confirm = (Button) mDialog.findViewById(R.id.btn_confirm);
                btn_cancel = (Button) mDialog.findViewById(R.id.btn_cancel);

                btn_confirm.setEnabled(false);

                btn_confirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String text = startDate + " / " + endDate;
                        tv_temp.setText(text);

                        startDate = null;
                        endDate = null;

                        date_selected = "지정된 기간";
                        setTv_title();

                        mDialog.cancel();
                    }
                });

                btn_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startDate = null;
                        endDate = null;
                        mDialog.cancel();
                    }
                });
            }
        });
    }
}
