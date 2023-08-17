package com.techspace.count_days;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    Button btnCal,btnRem,btnBirthday;
    TextView tvTime,tvDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnCal = findViewById(R.id.btn_Cal_date);
        btnRem = findViewById(R.id.btn_Rem_date);
        btnBirthday = findViewById(R.id.btn_birthday);
        tvTime = findViewById(R.id.tv_time);
        tvDate = findViewById(R.id.tv_date);


        String currentTime = new SimpleDateFormat("h:mm", Locale.getDefault())
                .format(new Date());
        tvTime.setText(currentTime);

        String currentDate = new SimpleDateFormat( " EEEE, dd MMM yyyy ", Locale.getDefault())
                .format(new Date());
        tvDate.setText(currentDate);



        btnCal.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), CalculateDays.class);
            startActivity(intent);
        });

        btnRem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RemDays.class);
                startActivity(intent);
            }
        });

        btnBirthday.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), MyBirthday.class);
            startActivity(intent);
        });

    }
}