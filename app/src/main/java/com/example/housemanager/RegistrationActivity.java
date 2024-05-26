package com.example.housemanager;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.widget.AdapterView;

public class RegistrationActivity extends AppCompatActivity {
    private static final String TAG = "RegistrationActivity"; // 로그를 구분하기 위한 TAG 설정
    private Connect_to_Backend backend;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registeration_activity_main);

        LinearLayout headerAdmin = findViewById(R.id.headerAdmin);
        ImageView imgMenuIcon = findViewById(R.id.imgMenuIcon);

        HomeIconClickListener listener = new HomeIconClickListener(this);
        headerAdmin.setOnClickListener(listener);

        // MenuClickListener 설정
        MenuClickListener menuClickListener = new MenuClickListener(this);
        imgMenuIcon.setOnClickListener(menuClickListener);

        ListView listView = findViewById(R.id.person_listview);

        // 표시할 단일 데이터
        String[] data = {"test1", "test2", "test3", "test4", "test5"};
        String[] name = {"이준화", "박진혁", "이진형", "최영재", "김강희"};

        CustomAdapter1 adapter = new CustomAdapter1(this, data, name);

        // ListView에 어댑터 설정
        listView.setAdapter(adapter);

        // ListView 아이템 클릭 리스너 설정
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Intent를 사용하여 SecondActivity로 전환
                Intent intent = new Intent(RegistrationActivity.this, RegistrationActivity1.class);
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
