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
import android.widget.LinearLayout;
import android.widget.Toast;
public class HouseRActivity extends AppCompatActivity {
    private static final String TAG = "HouseRActivity"; // 로그를 구분하기 위한 TAG 설정
    private Connect_to_Backend backend;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.house_reg_activity_main);

        Context context = this;

        LinearLayout headerAdmin = findViewById(R.id.headerAdmin);

        HomeIconClickListener listener = new HomeIconClickListener(this);
        headerAdmin.setOnClickListener(listener);

        MenuClickListener menuClickListener = new MenuClickListener(this);
        headerAdmin.setOnClickListener(menuClickListener);

        Button House1 = findViewById(R.id.house1);
        Button House2 = findViewById(R.id.house2);
        Button House3 = findViewById(R.id.house3);

        House1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), HouseRActivity2.class);
                startActivity(intent);
            }
        });
        House2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), HouseRActivity.class);
                startActivity(intent);
            }
        });
        House3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), HouseRActivity.class);
                startActivity(intent);

                // Singleton 인스턴스 가져오기
                backend = Connect_to_Backend.getInstance();
                backend.setEventCallback(new EventCallback() {
                    @Override
                    public void onEventReceived(ReceivedDataEvent event) {
                        Log.d(TAG, "Received data: " + event.getMessage());
                        // 받은 데이터의 JSON을 알아서 파싱해서 UI 업데이트 등의 작업 수행
                    }
                });
            }
        });
    }
}