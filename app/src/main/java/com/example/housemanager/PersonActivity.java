package com.example.housemanager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
public class PersonActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.person_activity_main);

        Context context = this;
        // 헤더 레이아웃 포함
        View header = findViewById(R.id.headerAdmin);
        ImageView imgHomeIcon = header.findViewById(R.id.imgHomeIcon);
        // 홈 아이콘에 클릭 리스너 설정
        imgHomeIcon.setOnClickListener(new HomeIconClickListener(this));
    }
}