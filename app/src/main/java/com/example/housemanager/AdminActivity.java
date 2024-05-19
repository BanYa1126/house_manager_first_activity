package com.example.housemanager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
public class AdminActivity extends AppCompatActivity {
    private static final String TAG = "AdminActivity"; // 로그를 구분하기 위한 TAG 설정
    private Connect_to_Backend backend;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);

        Context context = this;
        // 헤더 레이아웃 포함
        View header = findViewById(R.id.headerAdmin);
        ImageView imgHomeIcon = header.findViewById(R.id.imgHomeIcon);
        // 홈 아이콘에 클릭 리스너 설정
        imgHomeIcon.setOnClickListener(new HomeIconClickListener(this));

        Button btnHouseC = findViewById(R.id.houseCButton);
        Button btnPerSon = findViewById(R.id.personButton);
        Button btnRegistration = findViewById(R.id.registrationButton);
        Button btnHouseR = findViewById(R.id.houseRButton);
        Button btnHouseM = findViewById(R.id.houseMButton);
        Button btnHouseU = findViewById(R.id.houseUButton);

        backend = Connect_to_Backend.getInstance();
        backend.setEventCallback(new EventCallback() {
            @Override
            public void onEventReceived(ReceivedDataEvent event) {
                Log.d(TAG, "Received data: " + event.getMessage());
                // 받은 데이터의 JSON을 알아서 파싱해서 UI 업데이트 등의 작업 수행
            }
        });

        btnHouseC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),HouseCActivity.class);
                startActivity(intent);
            }
        });
        btnPerSon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),PersonActivity.class);
                startActivity(intent);
            }
        });
        btnRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),RegistrationActivity.class);
                startActivity(intent);
            }
        });
        btnHouseR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),RegistrationActivity.class);
                startActivity(intent);
            }
        });
        btnHouseM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),RegistrationActivity.class);
                startActivity(intent);
            }
        });
        btnHouseU.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),RegistrationActivity.class);
                startActivity(intent);
            }
        });
    }
}