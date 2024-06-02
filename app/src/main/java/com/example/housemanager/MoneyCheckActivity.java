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

public class MoneyCheckActivity extends AppCompatActivity {
    private static final String TAG = "MoneyCheckActivity"; // 로그를 구분하기 위한 TAG 설정
    private Connect_to_Backend backend;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moneycheck_main);

        LinearLayout headerEmployer = findViewById(R.id.headerEmployer);
        ImageView imgMenuIcon = findViewById(R.id.imgMenuIcon);

        HomeIconClickListener listener = new HomeIconClickListener(this);
        headerEmployer.setOnClickListener(listener);

        // MenuClickListener 설정
        MenuClickListener menuClickListener = new MenuClickListener(this);
        imgMenuIcon.setOnClickListener(menuClickListener);

        TextView text1 = findViewById(R.id.moneycheck1);
        TextView text2 = findViewById(R.id.moneycheck2);
        TextView text3 = findViewById(R.id.moneycheck3);
        TextView text4 = findViewById(R.id.moneycheck4);
        TextView text5 = findViewById(R.id.moneycheck5);
        TextView text6 = findViewById(R.id.moneycheck6);
        TextView text7 = findViewById(R.id.moneycheck7);
        TextView text8 = findViewById(R.id.moneycheck8);
        TextView text9 = findViewById(R.id.moneycheck9);
        TextView text10 = findViewById(R.id.moneycheck10);
        TextView text11 = findViewById(R.id.moneycheck11);
        TextView text12 = findViewById(R.id.moneycheck12);
        TextView text13 = findViewById(R.id.moneycheck13);

        backend = Connect_to_Backend.getInstance();
        backend.read_data_from_Backend_with_socket("Bill_data","BillId = 'bill3'","personal",null);
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
                                    String newText2 = existingText2 + " " + dataObject.getString("PeriodStartDate") + " ~ " + dataObject.getString("PeriodEndDate");
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

                                    String existingText10 = text10.getText().toString();
                                    String newText10 = existingText10 + " " + dataObject.getString("Adjustment");
                                    text10.setText(newText10);

                                    int a = dataObject.optInt("ElectricityBill", 0);
                                    int b = dataObject.optInt("GasBill", 0);
                                    int c = dataObject.optInt("HeatingBill", 0);
                                    int d = dataObject.optInt("CommunicationBill", 0);
                                    int e = dataObject.optInt("WaterBill", 0);

                                    // 총합 계산
                                    int total_bill = a + b + c + d + e;

                                    String existingText11 = text11.getText().toString();
                                    String newText11 = existingText11 + " " + total_bill;
                                    text11.setText(newText11);

                                    String existingText12 = text12.getText().toString();
                                    String newText12 = existingText12 + " " + dataObject.getString("PaymentDueDate");
                                    text12.setText(newText12);

                                    String existingText13 = text13.getText().toString();
                                    String newText13 = existingText13 + " " + dataObject.getString("AIComment");
                                    text13.setText(newText13);

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