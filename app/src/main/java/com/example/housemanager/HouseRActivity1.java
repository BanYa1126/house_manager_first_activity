package com.example.housemanager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class HouseRActivity1 extends AppCompatActivity {
    private static final String TAG = "HouseRActivity1"; // 로그를 구분하기 위한 TAG 설정
    private Connect_to_Backend backend;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.house_reg_activity);

        EditText text1 = findViewById(R.id.houseR1);
        EditText text2 = findViewById(R.id.houseR2);
        EditText text3 = findViewById(R.id.houseR3);
        EditText text4 = findViewById(R.id.houseR4);
        EditText text5 = findViewById(R.id.houseR5);
        EditText text6 = findViewById(R.id.houseR6);
        EditText text7 = findViewById(R.id.houseR7);
        EditText text8 = findViewById(R.id.houseR8);
        EditText text9 = findViewById(R.id.houseR9);
        EditText text10 = findViewById(R.id.houseR10);

        Button Randombtn = findViewById(R.id.PWRandomButton);
        Button Regbtn = findViewById(R.id.PRButton);

        LinearLayout headerAdmin = findViewById(R.id.headerAdmin);
        ImageView imgMenuIcon = findViewById(R.id.imgMenuIcon);

        HomeIconClickListener listener = new HomeIconClickListener(this);
        headerAdmin.setOnClickListener(listener);

        // MenuClickListener 설정
        MenuClickListener menuClickListener = new MenuClickListener(this);
        imgMenuIcon.setOnClickListener(menuClickListener);

        backend = Connect_to_Backend.getInstance();
        Randombtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backend.read_data_from_Backend_with_socket("Houseinfo_data", null, null, null, null);
                backend.setEventCallback(new EventCallback() {
                    @Override
                    public void onEventReceived(ReceivedDataEvent event) {
                        String message = event.getMessage();
                        Log.d(TAG, "Received house data: " + message);

                        try {
                            // Assuming the message is a JSON array
                            JSONArray houseArray = new JSONArray(message);
                            int lastNumber = -1;
                            for (int i = 0; i < houseArray.length(); i++) {
                                JSONObject houseObject = houseArray.getJSONObject(i);
                                int roomNumber = houseObject.getInt("RoomNumber");
                                if (roomNumber > lastNumber) {
                                    lastNumber = roomNumber;
                                }
                            }
                            if (lastNumber != -1) {
                                final int newNumber = lastNumber + 1;
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        text1.setText(String.valueOf(newNumber));
                                    }
                                });
                            } else {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(HouseRActivity1.this, "No house numbers found", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        } catch (JSONException e) {
                            Log.e(TAG, "Failed to parse house data: " + message, e);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(HouseRActivity1.this, "Failed to parse house data", Toast.LENGTH_SHORT).show();
                                }
                            });
                        } catch (Exception e) {
                            Log.e(TAG, "Unexpected error occurred: " + message, e);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(HouseRActivity1.this, "An unexpected error occurred", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                });
            }
        });
        Regbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // EditText에 입력된 데이터 가져오기
                String data1 = text1.getText().toString();
                String data2 = text2.getText().toString();
                String data3 = text3.getText().toString();
                String data4 = text4.getText().toString();
                String data5 = text5.getText().toString();
                String data6 = text6.getText().toString();
                String data7 = text7.getText().toString();
                String data8 = text8.getText().toString();
                String data9 = text9.getText().toString();
                String data10 = text10.getText().toString();

                // JSON 객체로 변환
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("data1", data1);
                    jsonObject.put("data2", data2);
                    jsonObject.put("data3", data3);
                    jsonObject.put("data4", data4);
                    jsonObject.put("data5", data5);
                    jsonObject.put("data6", data6);
                    jsonObject.put("data7", data7);
                    jsonObject.put("data8", data8);
                    jsonObject.put("data9", data9);
                    jsonObject.put("data10", data10);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                // 백엔드로 데이터 전송
                backend.create_data_from_Backend_with_socket("SaveHouseData", null, null, null, jsonObject.toString());

                backend.setEventCallback(new EventCallback() {
                    @Override
                    public void onEventReceived(ReceivedDataEvent event) {
                        Log.d(TAG, "Data saved: " + event.getMessage());
                    }
                });
            }
        });
    }
}
