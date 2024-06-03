package com.example.housemanager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class UsageCheckActivity extends AppCompatActivity {
    private static final String TAG = "UsageCheckActivity"; // 로그를 구분하기 위한 TAG 설정
    private Connect_to_Backend backend;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usagecheck_main);

        TextView text1 = findViewById(R.id.usagecheck1);
        TextView text2 = findViewById(R.id.usagecheck2);
        TextView text3 = findViewById(R.id.usagecheck3);
        TextView text4 = findViewById(R.id.usagecheck4);
        TextView text5 = findViewById(R.id.usagecheck5);
        TextView text6 = findViewById(R.id.usagecheck6);
        TextView text7 = findViewById(R.id.usagecheck7);
        TextView text8 = findViewById(R.id.usagecheck8);
        TextView text9 = findViewById(R.id.usagecheck9);

        LinearLayout headerEmployer = findViewById(R.id.headerEmployer);
        ImageView imgMenuIcon = findViewById(R.id.imgMenuIcon);

        HomeIconClickListener listener = new HomeIconClickListener(this);
        headerEmployer.setOnClickListener(listener);

        // MenuClickListener 설정
        MenuClickListener menuClickListener = new MenuClickListener(this);
        imgMenuIcon.setOnClickListener(menuClickListener);

        backend = Connect_to_Backend.getInstance();
        backend.read_data_from_Backend_with_socket("UtilUsage_data", null, "personal", null);
        backend.setEventCallback(new EventCallback() {
            @Override
            public void onEventReceived(ReceivedDataEvent event) {
                Log.d(TAG, "Received data: " + event.getMessage());
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
                                    HashMap<String, Integer> utilityValues = new HashMap<>();

                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        if (jsonArray.length() == 0) {
                                            break; // 배열의 길이가 0이 되면 반복문 종료
                                        }
                                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                                        String utilityType = jsonObject.getString("UtilityType");
                                        int measurementValue = jsonObject.getInt("MeasurementValue");

                                        if (utilityValues.containsKey(utilityType)) {
                                            // 이미 키가 존재할 때 로직
                                        } else {
                                            // 키가 없으면 새로 추가
                                            utilityValues.put(utilityType, measurementValue);
                                        }
                                    }
                                    for (String utilityType : utilityValues.keySet()) {
                                        int value = utilityValues.get(utilityType);

                                        if (utilityType.equals("Electricity")) {
                                            text1.setText(text1.getText().toString() + " " + value);
                                        }else if (utilityType.equals("Gas")) {
                                            text3.setText(text3.getText().toString() + " " + value);
                                        } else if (utilityType.equals("Heating")) {
                                            text5.setText(text5.getText().toString() + " " + value);
                                        } else if (utilityType.equals("Water")) {
                                            text8.setText(text8.getText().toString() + " " + value);
                                        }
                                    }
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