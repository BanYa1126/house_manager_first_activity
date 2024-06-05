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
import org.json.JSONObject;

public class HouseMActivity extends AppCompatActivity {
    private static final String TAG = "HouseMActivity"; // 로그를 구분하기 위한 TAG 설정
    private Connect_to_Backend backend;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.claimmoney_activity);

        LinearLayout headerAdmin = findViewById(R.id.headerAdmin);
        ImageView imgMenuIcon = findViewById(R.id.imgMenuIcon);

        HomeIconClickListener listener = new HomeIconClickListener(this);
        headerAdmin.setOnClickListener(listener);

        // MenuClickListener 설정
        MenuClickListener menuClickListener = new MenuClickListener(this);
        imgMenuIcon.setOnClickListener(menuClickListener);

        ListView listView = findViewById(R.id.HouseNumber_listview);
        // Singleton 인스턴스 가져오기
        backend = Connect_to_Backend.getInstance();
        //backend.read_data_from_Backend_with_socket("Houseinfo_data",null,null,null);
        backend.read_data_from_Backend_with_socket("Houseinfo_data",null,null,null,null);
        backend.setEventCallback(new EventCallback() {
            @Override
            public void onEventReceived(ReceivedDataEvent event) {
                Log.d(TAG, "Received data: " + event.getMessage());
                try {
                    String jsonData = event.getMessage();
                    JSONArray jsonArray = new JSONArray(jsonData);
                    String[] houseNumArray = new String[jsonArray.length()];
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    houseNumArray[i] = jsonObject.getString("UnitId");
                                }
                                // 새로운 어댑터 생성
                                CustomAdapter3 newAdapter = new CustomAdapter3(HouseMActivity.this, houseNumArray);
                                // 새로운 어댑터 설정
                                listView.setAdapter(newAdapter);
                                Log.d(TAG, "Adapter set with new data");
                            } catch (JSONException e) {
                                Log.e(TAG, "JSON parsing error: " + e.getMessage());
                            }
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e(TAG, "JSON parsing error: " + e.getMessage());
                }
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Intent를 사용하여 SecondActivity로 전환
                Intent intent = new Intent(HouseMActivity.this, HouseMActivity2.class);
                startActivity(intent);
            }
        });
    }
}