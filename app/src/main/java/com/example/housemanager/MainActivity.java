package com.example.housemanager;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity implements LoginCallback {
    private static final String TAG = "MainActivity"; // 로그를 구분하기 위한 TAG 설정
    private Connect_to_Backend backend;
    private EditText editTextId, editTextPassword;
    private Button buttonLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextId = findViewById(R.id.IDText);
        editTextPassword = findViewById(R.id.PWText);
        buttonLogin = findViewById(R.id.loginButton);

        // Singleton 인스턴스 가져오기
        backend = Connect_to_Backend.getInstance();
        backend.setEventCallback(new EventCallback() {
            @Override
            public void onEventReceived(ReceivedDataEvent event) {
                Log.d(TAG, "Received data: " + event.getMessage());
                // 받은 데이터의 JSON을 알아서 파싱해서 UI 업데이트 등의 작업 수행
            }
        });

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "로그인 버튼 눌러짐");

                String username = editTextId.getText().toString();
                String password = editTextPassword.getText().toString();
                if (username.equals("admin") && password.equals("1q2w3e4r")) {
                    Log.d(TAG, "Login successful by Landlord for DEBUG ");
                    // 로그인 서버없이 디버그용으로 성공 처리
                    Intent intent = new Intent(MainActivity.this, AdminActivity.class);
                    startActivity(intent);
                } else if (username.equals("guest") && password.equals("1q2w3e4r")) {
                    Log.d(TAG, "Login successful by Tenant for DEBUG ");
                    Intent intent = new Intent(MainActivity.this, EmployerActivity.class);
                    startActivity(intent);
                } else {
                    backend.login(MainActivity.this, username, password, MainActivity.this);
                }
            }
        });
    }

    @Override
    public void onLoginResult(String result) {
        if (result.equals("Successful by Landlord")) {
            Log.d(TAG, "Login successful by Landlord");
            // 로그인 성공 처리
            Intent intent = new Intent(MainActivity.this, AdminActivity.class);
            startActivity(intent);
        } else if (result.equals("Successful by Tenant")) {
            Log.d(TAG, "Login successful by Tenant");
            Intent intent = new Intent(MainActivity.this, EmployerActivity.class);
            startActivity(intent);
        } else {
            Log.d(TAG, "Login failed: " + result);
            // 로그인 실패 처리
        }
    }
}
