package com.techspace.count_days;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CalculateDays extends AppCompatActivity {

    EditText etFirstDate, etSecondDate;
    DatePickerDialog datePickerDialog;
    TextView tvNumDay, tvNumYear, tvNumMonth, tvNumWeek;
    Button btnSubmit;
    LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculate_days);

        etFirstDate = findViewById(R.id.et_first_date);
        etSecondDate = findViewById(R.id.et_second_date);
        tvNumDay =  findViewById(R.id.tv_num_days);
        tvNumWeek = findViewById(R.id.tv_num_week);
        tvNumMonth =  findViewById(R.id.tv_num_month);
        tvNumYear =  findViewById(R.id.tv_num_year);
        linearLayout =  findViewById(R.id.l_vertical);
        btnSubmit = findViewById(R.id.btn_submit);

        btnSubmit.setOnClickListener(view -> checkNumber());

        //Move cursor
        etFirstDate.setFocusable(false);
        etSecondDate.setFocusable(false);


        etFirstDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // calender class's instance and get current date , month and year from calender
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day

                datePickerDialog = new DatePickerDialog(CalculateDays.this, new DatePickerDialog.OnDateSetListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        etFirstDate.setText(year + "/" + (monthOfYear + 1) + "/" + dayOfMonth);

                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        etSecondDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // calender class's instance and get current date , month and year from calender
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day

                datePickerDialog = new DatePickerDialog(CalculateDays.this, new DatePickerDialog.OnDateSetListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        etSecondDate.setText(year + "/" + (monthOfYear + 1) + "/" + dayOfMonth);

                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });
    }

    public void checkNumber() {



        String SDate = etFirstDate.getText().toString();
        if (TextUtils.isEmpty(SDate)) {
            etFirstDate.setError("This item cannot be empty");
            return;
        }

        String EDate = etSecondDate.getText().toString();
        if (TextUtils.isEmpty(EDate)) {
            etSecondDate.setError("This item cannot be empty");
            return;
        }

        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");

        try {
            Date date1 = sdf.parse(EDate);
            Date date2 = sdf.parse(SDate);

            long difference = Math.abs(date1.getTime() - date2.getTime());
            long differenceDates = difference / (24 * 60 * 60 * 1000);

            Double remain = (double) differenceDates;
            Double amount = remain / 365.25;
            int years = (int) Math.floor(amount);

            remain = remain - years * 365.25;
            amount = remain / 30.4375;
            int months = (int) Math.floor(amount);

            remain = remain - months * 30.4375;
            amount = remain / 7;
            int Weeks = (int) Math.floor(amount);

            remain = remain - Weeks * 7;
            int days = (int) Math.floor(remain);

            //Convert int to String
            tvNumYear.setText(String.valueOf(years));
            tvNumMonth.setText(String.valueOf(months));
            tvNumWeek.setText(String.valueOf(Weeks));
            tvNumDay.setText(String.valueOf(days));

            linearLayout.setVisibility(View.VISIBLE);

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}