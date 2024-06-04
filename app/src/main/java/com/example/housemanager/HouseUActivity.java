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

import org.json.JSONArray;
import org.json.JSONException;

public class HouseUActivity extends AppCompatActivity {

    private static final String TAG = "HouseUActivity";
    private Connect_to_Backend backend;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.usage_activity);

        LinearLayout headerAdmin = findViewById(R.id.headerAdmin);
        ImageView imgMenuIcon = findViewById(R.id.imgMenuIcon);

        HomeIconClickListener listener = new HomeIconClickListener(this);
        headerAdmin.setOnClickListener(listener);

        // MenuClickListener 설정
        MenuClickListener menuClickListener = new MenuClickListener(this);
        imgMenuIcon.setOnClickListener(menuClickListener);

        ListView listView = findViewById(R.id.building_listview);

        backend.read_data_from_Backend_with_socket("Houseinfo_data", null, null, null, null);
        // 표시할 단일 데이터
        String[] data = {};

        CustomAdapter2 adapter2 = new CustomAdapter2(this, data);

        // ListView에 어댑터 설정
        listView.setAdapter(adapter2);

        // ListView 아이템 클릭 리스너 설정
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Intent를 사용하여 SecondActivity로 전환
                Intent intent = new Intent(HouseUActivity.this, HouseUActivity1.class);
                startActivity(intent);

                // Singleton 인스턴스 가져오기
                backend = Connect_to_Backend.getInstance();
                backend.setEventCallback(new EventCallback() {
                    @Override
                    public void onEventReceived(ReceivedDataEvent event) {
                        Log.d(TAG, "Received data: " + event.getMessage());
                        // 받은 데이터의 JSON을 알아서 파싱해서 UI 업데이트 등의 작업 수행
                        // JSON 데이터를 파싱하여 String 배열로 변환하는 로직
                        String jsonData = event.getMessage();
                        try {

                            JSONArray jsonArray = new JSONArray(jsonData);
                            String[] houseInfoArray = new String[jsonArray.length()];
                            for (int i = 0; i < jsonArray.length(); i++) {
                                houseInfoArray[i] = jsonArray.getString(i);
                            }
                            // 어댑터 데이터 업데이트
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    CustomAdapter2 newAdapter = new CustomAdapter2(HouseUActivity.this, houseInfoArray);
                                    listView.setAdapter(newAdapter);
                                }
                            });
                        } catch (JSONException e) {
                            Log.e(TAG, "JSON parsing error: " + e.getMessage());
                        }
                    }
                });
            }
        });
    }
}
