package com.example.housemanager;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class RegistrationActivity extends AppCompatActivity {
    private static final String TAG = "RegistrationActivity"; // 로그를 구분하기 위한 TAG 설정
    private Connect_to_Backend backend;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registeration_activity);

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
}
