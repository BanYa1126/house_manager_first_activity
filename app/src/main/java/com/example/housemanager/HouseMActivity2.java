package com.example.housemanager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;
public class HouseMActivity2 extends AppCompatActivity {
    private static final String TAG = "HouseMActivity"; // 로그를 구분하기 위한 TAG 설정
    private Connect_to_Backend backend;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.claimmoney_activity_main);

        LinearLayout headerAdmin = findViewById(R.id.headerAdmin);

        HomeIconClickListener listener = new HomeIconClickListener(this);
        headerAdmin.setOnClickListener(listener);

        MenuClickListener menuClickListener = new MenuClickListener(this);
        headerAdmin.setOnClickListener(menuClickListener);
    }
}