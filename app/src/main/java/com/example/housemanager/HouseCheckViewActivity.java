package com.example.housemanager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.LinearLayout;
public class HouseCheckViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_housecheck_main);

        LinearLayout headerEmployer = findViewById(R.id.headerEmployer);

        HomeIconClickListener listener = new HomeIconClickListener(this);
        headerEmployer.setOnClickListener(listener);
    }
}