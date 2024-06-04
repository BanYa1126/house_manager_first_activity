package com.example.housemanager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class HouseUActivity extends AppCompatActivity {

    private static final String TAG = "HouseUActivity";
    private Connect_to_Backend backend;
    private ListView listView;

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

        listView = findViewById(R.id.building_listview);

        // Null check
        if (listView == null) {
            Log.e(TAG, "ListView is null!");
            return;
        }

        // Singleton 인스턴스 가져오기
        backend = Connect_to_Backend.getInstance();
        if (backend == null) {
            Log.e(TAG, "Backend instance is null!");
            return;
        }

        // 백엔드로부터 데이터 요청
        try {
            backend.read_data_from_Backend_with_socket("Houseinfo_data", null, null, null, null);
        } catch (Exception e) {
            Log.e(TAG, "Error reading data from backend: " + e.getMessage());
        }

        backend.setEventCallback(new EventCallback() {
            @Override
            public void onEventReceived(ReceivedDataEvent event) {
                String jsonData = event.getMessage();
                Log.d(TAG, "Received data: " + jsonData);

                try {
                    JSONArray jsonArray = new JSONArray(jsonData);
                    String[] houseInfoArray = new String[jsonArray.length()];
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        houseInfoArray[i] = jsonObject.getString("UnitId");
                    }
                    // 데이터가 올바르게 파싱되었는지 로그 출력
                    for (String info : houseInfoArray) {
                        Log.d(TAG, "House Info: " + info);
                    }
                    // 새로운 어댑터 생성
                    CustomAdapter2 newAdapter = new CustomAdapter2(HouseUActivity.this, houseInfoArray);
                    // 새로운 어댑터 설정
                    listView.setAdapter(newAdapter);
                    Log.d(TAG, "Adapter set with new data");
                } catch (JSONException e) {
                    Log.e(TAG, "JSON parsing error: " + e.getMessage());
                }
            }
        });

        // ListView 아이템 클릭 리스너 설정
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Intent를 사용하여 SecondActivity로 전환
                Intent intent = new Intent(HouseUActivity.this, HouseUActivity1.class);
                startActivity(intent);
            }
        });
    }
}
