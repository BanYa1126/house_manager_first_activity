package com.example.housemanager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

        TextView text1 = findViewById(R.id.text1);

        // Singleton 인스턴스 가져오기
        backend = Connect_to_Backend.getInstance();
        //backend.read_data_from_Backend_with_socket("Houseinfo_data",null,null,null);
        backend.read_data_from_Backend_with_socket("Houseinfo_data",null,null,"personal",null);
        backend.setEventCallback(new EventCallback() {
            @Override
            public void onEventReceived(ReceivedDataEvent event) {
                Log.d(TAG, "Received data: " + event.getMessage());
                // 받은 데이터의 JSON을 알아서 파싱해서 UI 업데이트 등의 작업 수행
                try {
                    // JSON 데이터는 event에서 받은 메시지라고 가정합니다.
                    String JSON_DATA = event.getMessage();

                    // JSON 데이터를 로그로 출력하여 확인
                    Log.d(TAG, "JSON_DATA: " + JSON_DATA);

                    // JSON 데이터가 배열 형태인지 확인
                    JSONArray jsonArray = new JSONArray(JSON_DATA);

                    if (jsonArray.length() > 0) {
                        JSONObject dataObject = jsonArray.getJSONObject(0);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    // 파싱된 데이터를 TextView에 설정
                                    String existingText1 = text1.getText().toString();
                                    String newText1 = existingText1 + " " + dataObject.getString("UnitId");
                                    text1.setText(newText1);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    Log.e(TAG, "JSON parsing error inside UI thread: " + e.getMessage());
                                }
                                // 나머지 필드도 동일한 방식으로 설정
                            }
                        });
                    }
                    else
                    {
                        Log.d(TAG, "JSON array is empty");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e(TAG, "JSON parsing error: " + e.getMessage());
                }
            }
        });
    }
}