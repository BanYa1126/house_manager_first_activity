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

import java.util.ArrayList;

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
        ArrayList<String> items = new ArrayList<>(); // Initialize the ArrayList

        // CustomAdapter2 스타일 선언 및 items 적용
        CustomAdapter2 adapter = new CustomAdapter2(this, items.toArray(new String[0]));
        // listView에 adapter 적용
        listView.setAdapter(adapter);

        // Singleton 인스턴스 가져오기
        backend = Connect_to_Backend.getInstance();
        // EventCallback 등록
        backend.setEventCallback(new EventCallback() {
            @Override
            public void onEventReceived(ReceivedDataEvent event) {
                Log.d(TAG, "Received data: " + event.getMessage());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        items.clear(); // Clear the ArrayList to avoid duplicates
                        adapter.updateData(new String[0]); // Clear the adapter data
                    }
                });

                try {
                    JSONArray jsonArray = new JSONArray(event.getMessage());
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        final String roomNumber = jsonObject.getString("RoomNumber");
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                items.add(roomNumber);
                                adapter.updateData(items.toArray(new String[0]));
                                Log.d(TAG, "Added Room Number: " + roomNumber);
                            }
                        });
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e(TAG, "JSON parsing error: " + e.getMessage());
                }
            }
        });
        backend.read_data_from_Backend_with_socket("Houseinfo_data", null, null, null, null);

        // ListView 아이템 클릭 리스너 설정
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // 선택된 항목의 RoomNumber 가져오기
                String roomNumber = (String) parent.getItemAtPosition(position);

                Intent intent = new Intent(HouseUActivity.this, HouseUActivity1.class);
                intent.putExtra("RoomNumber", roomNumber);
                startActivity(intent);
            }
        });
    }
}