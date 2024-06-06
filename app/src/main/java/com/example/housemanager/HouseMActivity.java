package com.example.housemanager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class HouseMActivity extends AppCompatActivity {
    private static final String TAG = "HouseMActivity"; // 로그를 구분하기 위한 TAG 설정
    private Connect_to_Backend backend;
    private boolean isLoading = false;
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
        ArrayList<String> items = new ArrayList<>();

        // CustomAdapter2 스타일 선언 및 items 적용
        CustomAdapter3 adapter = new CustomAdapter3(this, items.toArray(new String[0]));
        listView.setAdapter(adapter);

        // Singleton 인스턴스 가져오기
        backend = Connect_to_Backend.getInstance();
        backend.setEventCallback(new EventCallback() {
            @Override
            public void onEventReceived(ReceivedDataEvent event) {
                Log.d(TAG, "Received data: " + event.getMessage());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONArray jsonArray = new JSONArray(event.getMessage());
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                final String UnitID = jsonObject.getString("UnitId");
                                items.add(UnitID);
                            }
                            adapter.updateData(items.toArray(new String[0]));
                            Log.d(TAG, "Data updated, size: " + items.size());
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e(TAG, "JSON parsing error: " + e.getMessage());
                            Toast.makeText(HouseMActivity.this, "Server Error: "+ event.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                        isLoading = false; // 데이터 로드 완료 후 로딩 상태 해제
                    }
                });
            }
        });

        loadMoreData(); // 초기 데이터 로드

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String UnitID = (String) parent.getItemAtPosition(position);

                Intent intent = new Intent(HouseMActivity.this, HouseMActivity1.class);
                intent.putExtra("UnitId", UnitID);
                startActivity(intent);
            }
        });

        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {}

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (!isLoading && firstVisibleItem + visibleItemCount >= totalItemCount && totalItemCount > 0) {
                }
            }
        });
    }

    private void loadMoreData() {
        if (isLoading) return; // 이미 로딩 중이면 무시
        isLoading = true;

        backend.read_data_from_Backend_with_socket("Houseinfo_data", null, null, null, null);
    }
}