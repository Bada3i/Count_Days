package com.techspace.count_days;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class RemDays extends AppCompatActivity {

    EditText tvDate,tvNumDay,tvNumWeek,tvNumMonth,tvNumYear;
    DatePickerDialog datePickerDialog;
    String myDate, myDay;
    Button btnSubmit;
    Dialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rem_days);

        tvDate = findViewById(R.id.et_date);
        tvNumDay = findViewById(R.id.et_after_days);
        tvNumWeek = findViewById(R.id.et_after_week);
        tvNumMonth = findViewById(R.id.et_after_month);
        tvNumYear = findViewById(R.id.et_after_year);
        btnSubmit= findViewById(R.id.btn_submit);

        btnSubmit.setOnClickListener(v -> checkDate());


        //Keyboard over EditText
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        //Move cursor
        tvDate.setFocusable(false);

        tvDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // calender class's instance and get current date , month and year from calender
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day

                datePickerDialog = new DatePickerDialog(RemDays.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        tvDate.setText(year + "/" + (monthOfYear + 1) + "/" + dayOfMonth);
                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

    }

    public void checkDate () {
        String aDate = tvDate.getText().toString();
        if (TextUtils.isEmpty(aDate)) {
            tvDate.setError("This item cannot be empty");
            return;
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");


        String noDay = tvNumDay.getText().toString();
        String noWeek = tvNumWeek.getText().toString();
        String noMonth = tvNumMonth.getText().toString();
        String noYear = tvNumYear.getText().toString();

        try {
            Calendar cal = Calendar.getInstance();
            cal.setTime(sdf.parse(aDate));
            if (noDay.isEmpty()) {
                cal.add(Calendar.DAY_OF_MONTH, 0);
            } else {
                int IntDay = new Integer(noDay).intValue();
                cal.add(Calendar.DAY_OF_MONTH, IntDay);
            }

            if (noWeek.isEmpty()) {
                cal.add(Calendar.WEEK_OF_MONTH, 0);
            } else {
                int IntWeek = new Integer(noWeek).intValue();
                cal.add(Calendar.WEEK_OF_MONTH, IntWeek);
            }

            if (noMonth.isEmpty()) {
                cal.add(Calendar.MONTH, 0);
            } else {
                int IntMonth = new Integer(noMonth).intValue();
                cal.add(Calendar.MONTH, IntMonth);
            }

            if (noYear.isEmpty()) {
                cal.add(Calendar.YEAR, 0);
            } else {
                int IntYear = new Integer(noYear).intValue();
                cal.add(Calendar.YEAR, IntYear);
            }

            myDate = sdf.format(cal.getTime());
            //check day
            Date day = sdf.parse(myDate);
            myDay = (String) DateFormat.format("EEEE", day);

            showResultDialog(myDate,myDay);

        } catch (ParseException e) {
            e.printStackTrace();

        }
    }

    private void showResultDialog(String myDate,String myDay) {
        dialog = new Dialog(RemDays.this);
        dialog.setContentView(R.layout.activity_result_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getDate(myDate,myDay);
        dialog.show();

    }

    private void getDate(String myDate,String myDay) {
        TextView tvDate = dialog.findViewById(R.id.tv_my_date);
        TextView tvDay = dialog.findViewById(R.id.tv_my_day);
        tvDate.setText(myDate);
        tvDay.setText(myDay);

        exit();
        share(myDate, myDay);
        getCalendar(myDate);

    }

    private void exit() {
        ImageView ivExit = dialog.findViewById(R.id.iv_exit);
        ivExit.setOnClickListener(view -> dialog.dismiss());
    }

    private void share(String myDate,String myDay){
        Button btnShareTo = dialog.findViewById(R.id.btn_share);
        btnShareTo.setOnClickListener(view -> {
            Intent share = new Intent(Intent.ACTION_SEND);
            share.setType("text/plain");
            share.putExtra(android.content.Intent.EXTRA_SUBJECT, "Date");
            String details = myDay +"   "+ myDate;
            share.putExtra(android.content.Intent.EXTRA_TEXT, details);
            Log.d("Date",myDate);
            startActivity(Intent.createChooser(share, "Choose App To share Date"));
        });
    }

    private void getCalendar(String myDate) {
        Button btnSetCalender = dialog.findViewById(R.id.btn_calendar);

        btnSetCalender.setOnClickListener(view -> {
            SimpleDateFormat dt = new SimpleDateFormat("yyyy/MM/dd");
            try {
                Date date = dt.parse(myDate);
                Calendar cal = Calendar.getInstance();
                if (date != null) {
                    cal.setTime(date);
                }
                cal.get(Calendar.DATE);

                Intent intent = new Intent(Intent.ACTION_EDIT);
                intent.setType("vnd.android.cursor.item/event");
                intent.putExtra("Date", date);
                intent.putExtra("beginTime", cal.getTimeInMillis());

                startActivity(intent);

            } catch (ParseException e) {
                e.printStackTrace();
            }

        });

    }

}