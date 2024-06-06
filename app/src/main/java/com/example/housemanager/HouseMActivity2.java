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
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class HouseMActivity2 extends AppCompatActivity {
    private static final String TAG = "HouseMActivity2"; // 로그를 구분하기 위한 TAG 설정
    private Connect_to_Backend backend;
    private String BillId; // 클래스 변수로 선언하여 전역에서 접근 가능하게 함


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

        Button btn1 = findViewById(R.id.Modbtn);

        LinearLayout headerAdmin = findViewById(R.id.headerAdmin);
        ImageView imgMenuIcon = findViewById(R.id.imgMenuIcon);

        HomeIconClickListener listener = new HomeIconClickListener(this);
        headerAdmin.setOnClickListener(listener);

        // MenuClickListener 설정
        MenuClickListener menuClickListener = new MenuClickListener(this);
        imgMenuIcon.setOnClickListener(menuClickListener);

        String UnitID = getIntent().getStringExtra("UnitId");

        int unitID = Integer.parseInt(UnitID);

        backend = Connect_to_Backend.getInstance();

        backend.read_data_from_Backend_with_socket("Contract_data", null, null, null, null);
        backend.setEventCallback(new EventCallback() {
            @Override
            public void onEventReceived(ReceivedDataEvent event) {
                Log.d(TAG, "Received Contract_data : " + event.getMessage());
                try {
                    String data = event.getMessage();
                    JSONArray contractInfoArray = new JSONArray(data);
                    String contractId = null;
                    for (int i = 0; i < contractInfoArray.length(); i++) {
                        JSONObject dataObject = contractInfoArray.getJSONObject(i);
                        Log.d(TAG, "Contract_data object: " + dataObject.toString());
                        if (dataObject.getInt("UnitId") == unitID) {
                            contractId = dataObject.getString("ContractId");
                            Log.d(TAG, "Match found: UnitId = " + unitID + ", ContractId = " + contractId);
                            break;
                        }
                    }

                    if (contractId != null) {
                        Log.d(TAG, "ContractId found: " + contractId);

                        // Fetch Bill_data using ContractId
                        final String finalContractId = contractId; // Make contractId final
                        backend.read_data_from_Backend_with_socket("Bill_data", null, null, null, null);
                        Log.d(TAG, "Bill_data requested...");
                        backend.setEventCallback(new EventCallback() {
                            @Override
                            public void onEventReceived(ReceivedDataEvent event) {
                                Log.d(TAG, "Event callback triggered for Bill_data...");
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
                                                    Log.d(TAG, "Bill_data object: " + billDataObject.toString());
                                                    if (billDataObject.has("ContractId")) {
                                                        String billContractId = billDataObject.getString("ContractId");
                                                        if (billContractId.equals(finalContractId)) {
                                                            BillId = billDataObject.optString("BillId", "");
                                                            Log.d(TAG, "BillId: " + BillId);
                                                            Log.d(TAG, "Bill_data matched for ContractId: " + finalContractId);
                                                            // 파싱된 데이터를 TextView에 설정
                                                            String periodStartDate = billDataObject.optString("PeriodStartDate", "");
                                                            String periodEndDate = billDataObject.optString("PeriodEndDate", "");
                                                            String billDate = billDataObject.optString("BillDate", "");
                                                            String paymentDueDate = billDataObject.optString("PaymentDueDate", "");
                                                            String paymentMethod = billDataObject.optString("PaymentMethod", "");
                                                            String billRemarks = billDataObject.optString("BillRemarks", "");
                                                            String lastPaymentDate = billDataObject.optString("LastPaymentDate", "");
                                                            int rent = billDataObject.optInt("Rent", 0);
                                                            int managementFee = billDataObject.optInt("ManagementFee", 0);
                                                            int unpaidAmount = billDataObject.optInt("UnpaidAmount", 0);
                                                            int electricityBill = billDataObject.optInt("ElectricityBill", 0);
                                                            int gasBill = billDataObject.optInt("GasBill", 0);
                                                            int heatingBill = billDataObject.optInt("HeatingBill", 0);
                                                            int communicationBill = billDataObject.optInt("CommunicationBill", 0);
                                                            int waterBill = billDataObject.optInt("WaterBill", 0);
                                                            int adjustment = parseAdjustment(billDataObject.optString("Adjustment", "0,0"));
                                                            int paidAmount = billDataObject.optInt("PaidAmount", 0);

                                                            text2.setText(text2.getText().toString() + periodStartDate + " ~ " + periodEndDate);
                                                            text3.setText(text3.getText().toString() + billDate);
                                                            text4.setText(text4.getText().toString() + rent + "원");
                                                            text5.setText(text5.getText().toString() + managementFee + "원");
                                                            text6.setText(text6.getText().toString() + unpaidAmount + "원");
                                                            text7.setText(text7.getText().toString() + adjustment + "원");
                                                            text8.setText(text8.getText().toString() + electricityBill + "원");
                                                            text9.setText(text9.getText().toString() + gasBill + "원");
                                                            text10.setText(text10.getText().toString() + heatingBill + "원");
                                                            text11.setText(text11.getText().toString() + communicationBill + "원");
                                                            text12.setText(text12.getText().toString() + waterBill + "원");
                                                            text13.setText(text13.getText().toString() + billRemarks);
                                                            text14.setText(text14.getText().toString() + paymentMethod);
                                                            text15.setText(text15.getText().toString() + paymentDueDate);
                                                            text16.setText(text16.getText().toString() + lastPaymentDate);
                                                            text17.setText(text17.getText().toString() + paidAmount + "원");
                                                        } else {
                                                            Log.d(TAG, "Bill_data ContractId does not match: " + billContractId);
                                                        }
                                                    } else {
                                                        Log.d(TAG, "Bill_data does not contain ContractId");
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
                        Log.e(TAG, "ContractId not found for UnitId: " + unitID);
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
                HashMap<String, Object> dataMap = new HashMap<>();

                try {
                    if (!edit1.getText().toString().isEmpty()) dataMap.put("Rent", Integer.parseInt(edit1.getText().toString()));
                    if (!edit2.getText().toString().isEmpty()) dataMap.put("ManagementFee", Integer.parseInt(edit2.getText().toString()));
                    if (!edit3.getText().toString().isEmpty()) dataMap.put("UnpaidAmount", Integer.parseInt(edit3.getText().toString()));
                    if (!edit4.getText().toString().isEmpty()) dataMap.put("Adjustment", edit4.getText().toString());
                    if (!edit5.getText().toString().isEmpty()) dataMap.put("ElectricityBill", Integer.parseInt(edit5.getText().toString()));
                    if (!edit6.getText().toString().isEmpty()) dataMap.put("GasBill", Integer.parseInt(edit6.getText().toString()));
                    if (!edit7.getText().toString().isEmpty()) dataMap.put("HeatingBill", Integer.parseInt(edit7.getText().toString()));
                    if (!edit8.getText().toString().isEmpty()) dataMap.put("CommunicationBill", Integer.parseInt(edit8.getText().toString()));
                    if (!edit9.getText().toString().isEmpty()) dataMap.put("WaterBill", Integer.parseInt(edit9.getText().toString()));
                    if (!edit10.getText().toString().isEmpty()) dataMap.put("BillRemarks", edit10.getText().toString());
                    if (!edit11.getText().toString().isEmpty()) dataMap.put("PaymentMethod", edit11.getText().toString());
                    if (!edit12.getText().toString().isEmpty()) dataMap.put("PaymentDueDate", edit12.getText().toString());
                    if (!edit13.getText().toString().isEmpty()) dataMap.put("LastPaymentDate", edit13.getText().toString());
                    if (!edit14.getText().toString().isEmpty()) dataMap.put("PaidAmount", Integer.parseInt(edit14.getText().toString()));
                    if (!edit15.getText().toString().isEmpty()) dataMap.put("PeriodStartDate", edit15.getText().toString());

                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    Toast.makeText(HouseMActivity2.this, "오류 발생: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "Error creating data map: " + e.getMessage());
                    return; // 오류가 발생하면 업데이트를 중단
                }

                if (!dataMap.isEmpty()) {
                    // 데이터 전송
                    StringBuilder set = new StringBuilder();
                    for (Map.Entry<String, Object> entry : dataMap.entrySet()) {
                        if (set.length() > 0) {
                            set.append(", ");
                        }
                        if (entry.getValue() instanceof String) {
                            set.append(String.format("%s='%s'", entry.getKey(), entry.getValue()));
                        } else {
                            set.append(String.format("%s=%s", entry.getKey(), entry.getValue()));
                        }
                    }


                    String where = String.format("BillId='%s'", BillId);
                    backend.update_data_from_Backend_with_socket("Bill_data", null, where, null, set.toString());
                    Log.d(TAG, "Sent update data: " + dataMap.toString());
                } else {
                    Toast.makeText(HouseMActivity2.this, "변경된 데이터가 없습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private int parseAdjustment(String adjustment) {
        String[] values = adjustment.split(",");
        return values.length > 1 ? Integer.parseInt(values[1]) : 0;
    }
}
