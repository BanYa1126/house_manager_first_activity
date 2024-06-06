package com.example.housemanager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class HouseMActivity2 extends AppCompatActivity {
    private static final String TAG = "HouseMActivity2"; // 로그를 구분하기 위한 TAG 설정
    private Connect_to_Backend backend;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.claimmoney_activity_main);

        TextView text1 = findViewById(R.id.Mtext1);
        TextView text2 = findViewById(R.id.Mtext2);
        TextView text3 = findViewById(R.id.Mtext3);
        TextView text4 = findViewById(R.id.Mtext4);
        TextView text5 = findViewById(R.id.Mtext5);
        TextView text6 = findViewById(R.id.Mtext6);
        TextView text7 = findViewById(R.id.Mtext7);
        TextView text8 = findViewById(R.id.Mtext8);
        TextView text9 = findViewById(R.id.Mtext9);
        TextView text10 = findViewById(R.id.Mtext10);
        TextView text11 = findViewById(R.id.Mtext11);
        TextView text12 = findViewById(R.id.Mtext12);
        TextView text13 = findViewById(R.id.Mtext13);
        TextView text14 = findViewById(R.id.Mtext14);
        TextView text15 = findViewById(R.id.Mtext15);
        TextView text16 = findViewById(R.id.Mtext16);
        TextView text17 = findViewById(R.id.Mtext17);

        EditText edit1 = findViewById(R.id.Edit_Mtext1);
        EditText edit2 = findViewById(R.id.Edit_Mtext2);
        EditText edit3 = findViewById(R.id.Edit_Mtext3);
        EditText edit4 = findViewById(R.id.Edit_Mtext4);
        EditText edit5 = findViewById(R.id.Edit_Mtext5);
        EditText edit6 = findViewById(R.id.Edit_Mtext6);
        EditText edit7 = findViewById(R.id.Edit_Mtext7);
        EditText edit8 = findViewById(R.id.Edit_Mtext8);
        EditText edit9 = findViewById(R.id.Edit_Mtext9);
        EditText edit10 = findViewById(R.id.Edit_Mtext10);
        EditText edit11 = findViewById(R.id.Edit_Mtext11);
        EditText edit12 = findViewById(R.id.Edit_Mtext12);
        EditText edit13 = findViewById(R.id.Edit_Mtext13);
        EditText edit14 = findViewById(R.id.Edit_Mtext14);
        EditText edit15 = findViewById(R.id.Edit_Mtext15);

        LinearLayout headerAdmin = findViewById(R.id.headerAdmin);
        ImageView imgMenuIcon = findViewById(R.id.imgMenuIcon);

        HomeIconClickListener listener = new HomeIconClickListener(this);
        headerAdmin.setOnClickListener(listener);

        // MenuClickListener 설정
        MenuClickListener menuClickListener = new MenuClickListener(this);
        imgMenuIcon.setOnClickListener(menuClickListener);

        String UnitID = getIntent().getStringExtra("UnitId");
        text1.setText(text1.getText().toString() + UnitID);

        int unitID = Integer.parseInt(UnitID);

        backend = Connect_to_Backend.getInstance();

        // Fetch Contract_data to get unitId
        backend.read_data_from_Backend_with_socket("Contract_data", null, null, null, null);
        backend.setEventCallback(new EventCallback() {
            @Override
            public void onEventReceived(ReceivedDataEvent event) {
                Log.d(TAG, "Received Contract_data : " + event.getMessage());
                try {
                    String data = event.getMessage();
                    JSONArray contractInfoArray = new JSONArray(data);
                    int unitId = -1;
                    for (int i = 0; i < contractInfoArray.length(); i++) {
                        JSONObject dataObject = contractInfoArray.getJSONObject(i);
                        if (dataObject.getInt("ContratId") == unitID) {
                            unitId = dataObject.getInt("UnitId");
                            break;
                        }
                    }

                    if (unitId != -1) {
                        int finalUnitId = unitId;
                        backend.read_data_from_Backend_with_socket("Bill_data", null, null, null, null);
                        backend.setEventCallback(new EventCallback() {
                            @Override
                            public void onEventReceived(ReceivedDataEvent event) {
                                Log.d(TAG, "Received Bill_data: " + event.getMessage());
                                try {
                                    String billDataString = event.getMessage();
                                    JSONArray billDataArray = new JSONArray(billDataString);
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            try {
                                                for (int i = 0; i < billDataArray.length(); i++) {
                                                    JSONObject billDataObject = billDataArray.getJSONObject(i);
                                                    if (billDataObject.has("UnitId") && billDataObject.getInt("UnitId") == finalUnitId) {
                                                        // 파싱된 데이터를 TextView에 설정
                                                        String periodStartDate = billDataObject.optString("PeriodStartDate", "");
                                                        String periodEndDate = billDataObject.optString("PeriodEndDate", "");
                                                        String billDate = billDataObject.optString("BillDate", "");
                                                        int managementFee = billDataObject.optInt("ManagementFee", 0);
                                                        int unpaidAmount = billDataObject.optInt("UnpaidAmount", 0);
                                                        int electricityBill = billDataObject.optInt("ElectricityBill", 0);
                                                        int gasBill = billDataObject.optInt("GasBill", 0);
                                                        int heatingBill = billDataObject.optInt("HeatingBill", 0);
                                                        int communicationBill = billDataObject.optInt("CommunicationBill", 0);
                                                        int waterBill = billDataObject.optInt("WaterBill", 0);
                                                        int adjustment = parseAdjustment(billDataObject.optString("Adjustment", "0,0"));
                                                        String paymentDueDate = billDataObject.optString("PaymentDueDate", "");
                                                        String aiComment = billDataObject.optString("AIComment", "");

                                                        text2.setText("Period: " + periodStartDate + " ~ " + periodEndDate);
                                                        text3.setText("Bill Date: " + billDate);
                                                        text4.setText("Management Fee: " + managementFee + "원");
                                                        text5.setText("Unpaid Amount: " + unpaidAmount + "원");
                                                        text6.setText("Electricity Bill: " + electricityBill + "원");
                                                        text7.setText("Gas Bill: " + gasBill + "원");
                                                        text8.setText("Heating Bill: " + heatingBill + "원");
                                                        text9.setText("Communication Bill: " + communicationBill + "원");
                                                        text10.setText("Water Bill: " + waterBill + "원");
                                                        text12.setText("Payment Due Date: " + paymentDueDate);
                                                        text13.setText("AI Comment: " + aiComment);
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
                        Log.e(TAG, "UnitId not found in Contract_data");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e(TAG, "JSON parsing error: " + e.getMessage());
                }
            }
        });
    }

    private int parseAdjustment(String adjustment) {
        String[] values = adjustment.split(",");
        return values.length > 1 ? Integer.parseInt(values[1]) : 0;
    }
}
