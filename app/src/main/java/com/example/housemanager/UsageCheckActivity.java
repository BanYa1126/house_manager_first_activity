package com.example.housemanager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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

        // 두 개의 데이터를 동시에 요청
        backend.read_data_from_Backend_with_socket("Houseinfo_data", null,null, "personal", null);
        backend.read_data_from_Backend_with_socket("UtilUsage_data", null, null,"personal", null);

        backend.setEventCallback(new EventCallback() {
            @Override
            public void onEventReceived(ReceivedDataEvent event) {
                Log.d(TAG, "Received data: " + event.getMessage());
                try {
                    String data = event.getMessage();
                    if (data.contains("RentalArea")) { // Houseinfo_data 확인
                        JSONArray houseInfoArray = new JSONArray(data);
                        if (houseInfoArray.length() > 0) {
                            JSONObject dataObject = houseInfoArray.getJSONObject(0);
                            final double rentalArea = dataObject.optDouble("RentalArea", 0); // 기본값 0
                            Log.d(TAG, "RentalArea: " + rentalArea);

                            // UtilUsage_data 요청 후 처리
                            backend.read_data_from_Backend_with_socket("UtilUsage_data",null, null, "personal", null);
                            backend.setEventCallback(new EventCallback() {
                                @Override
                                public void onEventReceived(ReceivedDataEvent event) {
                                    Log.d(TAG, "Received UtilUsage_data: " + event.getMessage());
                                    try {
                                        String utilData = event.getMessage();
                                        JSONArray utilUsageData = new JSONArray(utilData);

                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                try {
                                                    HashMap<String, Double> utilityValues = new HashMap<>();
                                                    for (int i = 0; i < utilUsageData.length(); i++) {
                                                        JSONObject jsonObject = utilUsageData.getJSONObject(i);
                                                        if (jsonObject.has("UtilityType") && jsonObject.has("MeasurementValue")) {
                                                            String utilityType = jsonObject.getString("UtilityType");
                                                            double measurementValue = jsonObject.getDouble("MeasurementValue");
                                                            Log.d(TAG, "UtilityType: " + utilityType + ", MeasurementValue: " + measurementValue);

                                                            if (!utilityValues.containsKey(utilityType)) {
                                                                utilityValues.put(utilityType, measurementValue);
                                                            }
                                                        } else {
                                                            Log.e(TAG, "Missing keys in JSON object at index " + i);
                                                        }
                                                    }

                                                    for (String utilityType : utilityValues.keySet()) {
                                                        double value = utilityValues.get(utilityType);
                                                        Log.d(TAG, "Processing UtilityType: " + utilityType + ", Value: " + value);

                                                        switch (utilityType) {
                                                            case "Electricity":
                                                                text1.setText(text1.getText().toString() + " " + value + " W");
                                                                text2.setText(text2.getText().toString() + " " + Calculation.calculateElectricityBill(value) + "원");
                                                                break;
                                                            case "Gas":
                                                                text3.setText(text3.getText().toString() + " " + value + " L");
                                                                text4.setText(text4.getText().toString() + " " + Calculation.calculateGasBill(value) + "원");
                                                                break;
                                                            case "Heating":
                                                                text5.setText(text5.getText().toString() + " " + (value*1000)  + " Mcal");
                                                                text6.setText(text6.getText().toString() + " " + Calculation.calculateHeatingBill(rentalArea, value) + "원");
                                                                break;
                                                            case "Water":
                                                                text8.setText(text8.getText().toString() + " " + value + " L");
                                                                text9.setText(text9.getText().toString() + " " + Calculation.calculateWaterBill(value) + "원");
                                                                break;
                                                            default:
                                                                break;
                                                        }
                                                    }
                                                    text7.setText(text7.getText().toString() + " " + 20000 + "원");
                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                    Log.e(TAG, "JSON parsing error inside UI thread: " + e.getMessage());
                                                }
                                            }
                                        });
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                        Log.e(TAG, "JSON parsing error: " + e.getMessage());
                                        Toast.makeText(UsageCheckActivity.this, "Server Error: "+ event.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                    } else { // UtilUsage_data 확인
                        JSONArray utilUsageData = new JSONArray(data);
                        Log.d(TAG, "UtilUsage_data: " + utilUsageData.toString());

                        // Houseinfo_data 요청 후 처리
                        backend.read_data_from_Backend_with_socket("Houseinfo_data", null, null,"personal", null);
                        backend.setEventCallback(new EventCallback() {
                            @Override
                            public void onEventReceived(ReceivedDataEvent event) {
                                Log.d(TAG, "Received Houseinfo_data: " + event.getMessage());
                                try {
                                    String houseData = event.getMessage();
                                    JSONArray houseInfoArray = new JSONArray(houseData);
                                    if (houseInfoArray.length() > 0) {
                                        JSONObject dataObject = houseInfoArray.getJSONObject(0);
                                        final double rentalArea = dataObject.optDouble("RentalArea", 0); // 기본값 0
                                        Log.d(TAG, "RentalArea: " + rentalArea);

                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                try {
                                                    HashMap<String, Double> utilityValues = new HashMap<>();
                                                    for (int i = 0; i < utilUsageData.length(); i++) {
                                                        JSONObject jsonObject = utilUsageData.getJSONObject(i);
                                                        if (jsonObject.has("UtilityType") && jsonObject.has("MeasurementValue")) {
                                                            String utilityType = jsonObject.getString("UtilityType");
                                                            double measurementValue = jsonObject.getDouble("MeasurementValue");
                                                            Log.d(TAG, "UtilityType: " + utilityType + ", MeasurementValue: " + measurementValue);

                                                            if (!utilityValues.containsKey(utilityType)) {
                                                                utilityValues.put(utilityType, measurementValue);
                                                            }
                                                        } else {
                                                            Log.e(TAG, "Missing keys in JSON object at index " + i);
                                                        }
                                                    }

                                                    for (String utilityType : utilityValues.keySet()) {
                                                        double value = utilityValues.get(utilityType);
                                                        Log.d(TAG, "Processing UtilityType: " + utilityType + ", Value: " + value);

                                                        switch (utilityType) {
                                                            case "Electricity":
                                                                text1.setText(text1.getText().toString() + " " + value + " kwh");
                                                                text2.setText(text2.getText().toString() + " " + Calculation.calculateElectricityBill(value) + "원");
                                                                break;
                                                            case "Gas":
                                                                text3.setText(text3.getText().toString() + " " + value + " L");
                                                                text4.setText(text4.getText().toString() + " " + Calculation.calculateGasBill(value) + "원");
                                                                break;
                                                            case "Heating":
                                                                text5.setText(text5.getText().toString() + " " + (value*1000) + " Mcal");
                                                                text6.setText(text6.getText().toString() + " " + Calculation.calculateHeatingBill(rentalArea, value) + "원");
                                                                break;
                                                            case "Water":
                                                                text8.setText(text8.getText().toString() + " " + value + " L");
                                                                text9.setText(text9.getText().toString() + " " + Calculation.calculateWaterBill(value) + "원");
                                                                break;
                                                            default:
                                                                break;
                                                        }
                                                    }
                                                    text7.setText(text7.getText().toString() + " " + 20000 + "원");
                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                    Log.e(TAG, "JSON parsing error inside UI thread: " + e.getMessage());
                                                }
                                            }
                                        });
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    Log.e(TAG, "JSON parsing error: " + e.getMessage());
                                }
                            }
                        });
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e(TAG, "JSON parsing error: " + e.getMessage());
                }
            }
        });
    }
}
