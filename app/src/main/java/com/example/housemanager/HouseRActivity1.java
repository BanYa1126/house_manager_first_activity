package com.example.housemanager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class HouseRActivity1 extends AppCompatActivity {
    private static final String TAG = "HouseRActivity1"; // 로그를 구분하기 위한 TAG 설정
    private Connect_to_Backend backend;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.house_reg_activity);

        EditText text1 = findViewById(R.id.houseR1);
        EditText text2 = findViewById(R.id.houseR2);
        EditText text3 = findViewById(R.id.houseR3);
        EditText text4 = findViewById(R.id.houseR4);
        EditText text5 = findViewById(R.id.houseR5);
        EditText text6 = findViewById(R.id.houseR6);
        EditText text7 = findViewById(R.id.houseR7);
        EditText text8 = findViewById(R.id.houseR8);
        EditText text9 = findViewById(R.id.houseR9);
        EditText text10 = findViewById(R.id.houseR10);
        Button randombtn = findViewById(R.id.PWRandomButton);
        Button regbtn = findViewById(R.id.PRButton);

        LinearLayout headerAdmin = findViewById(R.id.headerAdmin);
        ImageView imgMenuIcon = findViewById(R.id.imgMenuIcon);

        HomeIconClickListener listener = new HomeIconClickListener(this);
        headerAdmin.setOnClickListener(listener);

        // MenuClickListener 설정
        MenuClickListener menuClickListener = new MenuClickListener(this);
        imgMenuIcon.setOnClickListener(menuClickListener);
    }
}