package com.example.housemanager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
public class HouseCheckViewActivity extends AppCompatActivity {

    private static final String TAG = "HouseCheckViewActivity"; // 로그를 구분하기 위한 TAG 설정
    private Connect_to_Backend backend;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_housecheck_main);

        LinearLayout headerEmployer = findViewById(R.id.headerEmployer);
        ImageView imgMenuIcon = findViewById(R.id.imgMenuIcon);

        HomeIconClickListener listener = new HomeIconClickListener(this);
        headerEmployer.setOnClickListener(listener);

        // MenuClickListener 설정
        MenuClickListener menuClickListener = new MenuClickListener(this);
        imgMenuIcon.setOnClickListener(menuClickListener);

        // Singleton 인스턴스 가져오기
        backend = Connect_to_Backend.getInstance();
        //backend.read_data_from_Backend_with_socket("Houseinfo_data",null,null,null);
        backend.read_data_from_Backend_with_socket("Houseinfo_data","h.UnitId='unit2'","personal",null);
        backend.setEventCallback(new EventCallback() {
            @Override
            public void onEventReceived(ReceivedDataEvent event) {
                Log.d(TAG, "Received data: " + event.getMessage());
                // 받은 데이터의 JSON을 알아서 파싱해서 UI 업데이트 등의 작업 수행
            }
        });
    }
}