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
public class PersonActivity extends AppCompatActivity {
    private static final String TAG = "PersonActivity"; // 로그를 구분하기 위한 TAG 설정
    private Connect_to_Backend backend;

    Context context = this;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.person_activity_main);

        LinearLayout headerAdmin = findViewById(R.id.headerAdmin);

        HomeIconClickListener listener = new HomeIconClickListener(this);
        headerAdmin.setOnClickListener(listener);

        MenuClickListener menuClickListener = new MenuClickListener(this);
        headerAdmin.setOnClickListener(menuClickListener);

        Button Person1 = findViewById(R.id.person1);
        Button Person2 = findViewById(R.id.person2);
        Button Person3 = findViewById(R.id.person3);

        Person1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PersonActivity2.class);
                startActivity(intent);
            }
        });
        Person2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PersonActivity.class);
                startActivity(intent);
            }
        });
        Person3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PersonActivity.class);
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