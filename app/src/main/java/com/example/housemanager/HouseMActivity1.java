package com.example.housemanager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.Calendar;
import java.util.Locale;

import android.app.DatePickerDialog;
public class HouseMActivity1 extends AppCompatActivity {
    private Button btnStartDate, btnEndDate, btnDateInquiry;
    private TextView txtSelectedDates;
    private Calendar startDate, endDate;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.claimmoney_time);

        LinearLayout headerAdmin = findViewById(R.id.headerAdmin);

        HomeIconClickListener listener = new HomeIconClickListener(this);
        headerAdmin.setOnClickListener(listener);

        MenuClickListener menuClickListener = new MenuClickListener(this);
        headerAdmin.setOnClickListener(menuClickListener);

        btnStartDate = findViewById(R.id.btnStartDate);
        btnEndDate = findViewById(R.id.btnEndDate);
        txtSelectedDates = findViewById(R.id.txtSelectedDates);
        btnDateInquiry = findViewById(R.id.date_inquiry);

        startDate = Calendar.getInstance();
        endDate = Calendar.getInstance();

        final Context context = this;

        btnStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(context, startDate, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        startDate.set(Calendar.YEAR, year);
                        startDate.set(Calendar.MONTH, month);
                        startDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        updateSelectedDates();
                    }
                });
            }
        });

        btnEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(context, endDate, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        endDate.set(Calendar.YEAR, year);
                        endDate.set(Calendar.MONTH, month);
                        endDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        updateSelectedDates();
                    }
                });
            }
        });
        btnDateInquiry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),HouseMActivity2.class);
                startActivity(intent);
            }
        });
    }

    private void showDatePickerDialog(Context context, Calendar calendar, DatePickerDialog.OnDateSetListener listener) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                context,
                listener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }

    private void updateSelectedDates() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String startDateString = sdf.format(startDate.getTime());
        String endDateString = sdf.format(endDate.getTime());
        txtSelectedDates.setText("Selected Dates: " + startDateString + " - " + endDateString);
    }
}