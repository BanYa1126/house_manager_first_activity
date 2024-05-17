package com.example.housemanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.util.Log;


public class MainActivity extends AppCompatActivity implements LoginCallback {
    private static final String TAG = "MainActivity"; // 로그를 구분하기 위한 TAG 설정
    private EditText editTextId, editTextPassword;
    private Button buttonLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextId = findViewById(R.id.IDText);
        editTextPassword = findViewById(R.id.PWText);
        buttonLogin = findViewById(R.id.loginButton);
        Connect_to_Backend backend = new Connect_to_Backend();

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
                }else if(username.equals("guest") && password.equals("1q2w3e4r")){
                    Log.d(TAG, "Login successful by Tenant for DEBUG ");
                    Intent intent = new Intent(MainActivity.this, EmployerActivity.class);
                    startActivity(intent);
                }


                backend.login(MainActivity.this,username, password, MainActivity.this);

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
            Log.d(TAG, "Login successful by Method...");
            Intent intent = new Intent(MainActivity.this, EmployerActivity.class);
            startActivity(intent);
        } else {
            //Toast.makeText(this, "Hello, Toast!", Toast.LENGTH_SHORT).show()
            Log.d(TAG, "Login failed: " + result);
            // 로그인 실패 처리
        }
    }
}