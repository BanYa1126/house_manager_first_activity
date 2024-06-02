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

import org.json.JSONException;
import org.json.JSONObject;

public class MoneyCheckActivity extends AppCompatActivity {
    private static final String TAG = "MoneyCheckActivity"; // 로그를 구분하기 위한 TAG 설정
    private Connect_to_Backend backend;

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

        backend = Connect_to_Backend.getInstance();
        backend.setEventCallback(new EventCallback() {
            @Override
            public void onEventReceived(ReceivedDataEvent event) {
                Log.d(TAG, "Received data: " + event.getMessage());
                // 받은 데이터의 JSON을 알아서 파싱해서 UI 업데이트 등의 작업 수행
                parseAndDisplayMoney(event.getMessage());
            }
        });
    }
    private void parseAndDisplayMoney(String jsonString) {
        try {
            // JSON 파싱
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONObject usageObject = jsonObject.getJSONObject("usage");
            int data = usageObject.getInt("data");
            String unit = usageObject.getString("unit");

            // UI 업데이트
            String usageText = "Data Usage: " + data + " " + unit;
            runOnUiThread(() -> text1.setText(usageText));
            runOnUiThread(() -> text2.setText(usageText));
        } catch (JSONException e) {
            Log.e(TAG, "JSON 파싱 오류: " + e.getMessage());
        }
    }
}