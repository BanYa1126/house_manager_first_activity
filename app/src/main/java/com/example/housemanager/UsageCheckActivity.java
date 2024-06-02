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
                                    // 파싱된 데이터를 TextView에 설정
                                    String existingText1 = text1.getText().toString();
                                    String newText1 = existingText1 + " " + dataObject.getString("BillDate");
                                    text1.setText(newText1);

                                    String existingText2 = text2.getText().toString();
                                    String newText2 = existingText2 + " " + dataObject.getString("PeriodStartDate");
                                    text2.setText(newText2);

                                    String existingText3 = text3.getText().toString();
                                    String newText3 = existingText3 + " " + dataObject.getString("ManagementFee");
                                    text3.setText(newText3);

                                    String existingText4 = text4.getText().toString();
                                    String newText4 = existingText4 + " " + dataObject.getString("UnpaidAmount");
                                    text4.setText(newText4);

                                    String existingText5 = text5.getText().toString();
                                    String newText5 = existingText5 + " " + dataObject.getString("ElectricityBill");
                                    text5.setText(newText5);

                                    String existingText6 = text6.getText().toString();
                                    String newText6 = existingText6 + " " + dataObject.getString("GasBill");
                                    text6.setText(newText6);

                                    String existingText7 = text7.getText().toString();
                                    String newText7 = existingText7 + " " + dataObject.getString("HeatingBill");
                                    text7.setText(newText7);

                                    String existingText8 = text8.getText().toString();
                                    String newText8 = existingText8 + " " + dataObject.getString("CommunicationBill");
                                    text8.setText(newText8);

                                    String existingText9 = text9.getText().toString();
                                    String newText9 = existingText9 + " " + dataObject.getString("WaterBill");
                                    text9.setText(newText9);
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