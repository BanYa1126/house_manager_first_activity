package com.example.housemanager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.WindowDecorActionBar;

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
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class HouseUActivity1 extends AppCompatActivity {
    private static final String TAG = "HouseUActivity1";
    private Connect_to_Backend backend;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.usage_building_activity);

        TextView text1 = findViewById(R.id.House_Ho);
        TextView text2 = findViewById(R.id.eusage);
        TextView text3 = findViewById(R.id.gusage);
        TextView text4 = findViewById(R.id.husage);
        TextView text5 = findViewById(R.id.wusage);

        EditText edit1 = findViewById(R.id.Edit_eusage);
        EditText edit2 = findViewById(R.id.Edit_gusage);
        EditText edit3 = findViewById(R.id.Edit_husage);
        EditText edit4 = findViewById(R.id.Edit_wusage);

        Button btn1 = findViewById(R.id.ebtn);
        Button btn2 = findViewById(R.id.gbtn);
        Button btn3 = findViewById(R.id.hbtn);
        Button btn4 = findViewById(R.id.wbtn);

        LinearLayout headerAdmin = findViewById(R.id.headerAdmin);
        ImageView imgMenuIcon = findViewById(R.id.imgMenuIcon);

        HomeIconClickListener listener = new HomeIconClickListener(this);
        headerAdmin.setOnClickListener(listener);

        // MenuClickListener 설정
        MenuClickListener menuClickListener = new MenuClickListener(this);
        imgMenuIcon.setOnClickListener(menuClickListener);

        String roomNumberStr = getIntent().getStringExtra("RoomNumber");
        text1.setText(text1.getText().toString() + roomNumberStr);

        int roomNumber = Integer.parseInt(roomNumberStr);

        backend = Connect_to_Backend.getInstance();

        // Fetch Houseinfo_data to get unitId
        backend.read_data_from_Backend_with_socket("Houseinfo_data", null, null, null, null);
        backend.setEventCallback(new EventCallback() {
            @Override
            public void onEventReceived(ReceivedDataEvent event) {
                Log.d(TAG, "Received Houseinfo_data: " + event.getMessage());
                try {
                    String data = event.getMessage();
                    JSONArray houseInfoArray = new JSONArray(data);
                    int unitId = -1;
                    for (int i = 0; i < houseInfoArray.length(); i++) {
                        JSONObject dataObject = houseInfoArray.getJSONObject(i);
                        if (dataObject.getInt("RoomNumber") == roomNumber) {
                            unitId = dataObject.getInt("UnitId");
                            break;
                        }
                    }

                    if (unitId != -1) {
                        // Fetch UtilUsage_data using UnitId
                        int finalUnitId = unitId;
                        backend.read_data_from_Backend_with_socket("UtilUsage_data", null, null, null, null);
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
                                                    if (jsonObject.has("UnitId") && jsonObject.getInt("UnitId") == finalUnitId) {
                                                        if (jsonObject.has("UtilityType") && jsonObject.has("MeasurementValue")) {
                                                            String utilityType = jsonObject.getString("UtilityType");
                                                            double measurementValue = jsonObject.getDouble("MeasurementValue");

                                                            utilityValues.put(utilityType, measurementValue);
                                                        } else {
                                                            Log.e(TAG, "Missing keys in JSON object at index " + i);
                                                        }
                                                    }
                                                }

                                                for (String utilityType : utilityValues.keySet()) {
                                                    double value = utilityValues.get(utilityType);
                                                    Log.d(TAG, "Processing UtilityType: " + utilityType + ", Value: " + value);

                                                    switch (utilityType) {
                                                        case "Electricity":
                                                            text2.setText(text2.getText().toString() + " " + value + " W");
                                                            break;
                                                        case "Gas":
                                                            text3.setText(text3.getText().toString() + " " + value + " L");
                                                            break;
                                                        case "Heating":
                                                            text4.setText(text4.getText().toString() + " " + (value * 1000) + " Mcal");
                                                            break;
                                                        case "Water":
                                                            text5.setText(text5.getText().toString() + " " + value + " L");
                                                            break;
                                                        default:
                                                            break;
                                                    }
                                                }
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                                Log.e(TAG, "JSON parsing error inside UI thread: " + e.getMessage());
                                            }
                                        }
                                    });
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    Log.e(TAG, "JSON parsing error: " + e.getMessage());
                                }
                            }
                        });
                    } else {
                        Log.e(TAG, "RoomNumber not found in Houseinfo_data");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e(TAG, "JSON parsing error: " + e.getMessage());
                }
            }
        });
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchUnitIdAndUpdateUtilityData("Electricity", edit1.getText().toString(), roomNumber);
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchUnitIdAndUpdateUtilityData("Gas", edit2.getText().toString(), roomNumber);
            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchUnitIdAndUpdateUtilityData("Heating", edit3.getText().toString(), roomNumber);
            }
        });
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchUnitIdAndUpdateUtilityData("Water", edit4.getText().toString(), roomNumber);
            }
        });

    }
    private void fetchUnitIdAndUpdateUtilityData(String utilityType, String newValue, int roomNumber) {
        backend.read_data_from_Backend_with_socket("Houseinfo_data", null, null, null, null);
        backend.setEventCallback(new EventCallback() {
            @Override
            public void onEventReceived(ReceivedDataEvent event) {
                Log.d(TAG, "Received Houseinfo_data: " + event.getMessage());
                try {
                    String data = event.getMessage();
                    JSONArray houseInfoArray = new JSONArray(data);
                    int unitId = -1;
                    for (int i = 0; i < houseInfoArray.length(); i++) {
                        JSONObject dataObject = houseInfoArray.getJSONObject(i);
                        if (dataObject.getInt("RoomNumber") == roomNumber) {
                            unitId = dataObject.getInt("UnitId");
                            break;
                        }
                    }

                    if (unitId != -1) {
                        updateUtilityData(utilityType, newValue, unitId);
                    } else {
                        Log.e(TAG, "RoomNumber not found in Houseinfo_data");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e(TAG, "JSON parsing error: " + e.getMessage());
                }
            }
        });
    }

    private void updateUtilityData(String utilityType, String newValue, int unitId) {
        try {
            double value = Double.parseDouble(newValue);
            JSONObject updateObject = new JSONObject();
            updateObject.put("UnitId", unitId);
            updateObject.put("UtilityType", utilityType);
            updateObject.put("MeasurementValue", value);

            Log.d(TAG, "Updating Utility Data: " + updateObject.toString());

            String where = String.format("unitId='%s' AND UtilityType='%s'", unitId, utilityType);
            String set = String.format("MeasurementValue=%s", value);
            backend.update_data_from_Backend_with_socket("UtilUsage_data", null, where, null, set);
            backend.setEventCallback(new EventCallback() {
                @Override
                public void onEventReceived(ReceivedDataEvent event) {
                    Log.d(TAG, "Update response: " + event.getMessage());
                    runOnUiThread(() -> {
                        if (event.getMessage().contains("success")) {
                            Toast.makeText(HouseUActivity1.this, "수정되었습니다.", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(HouseUActivity1.this, "수정에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                        }
                        // Refresh activity to show updated data
                        recreate();
                    });
                }
            });
        } catch (JSONException | NumberFormatException e) {
            e.printStackTrace();
            Toast.makeText(this, "Invalid input", Toast.LENGTH_SHORT).show();
        }
    }
}