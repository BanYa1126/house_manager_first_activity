package com.example.housemanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.util.Log;


public class MainActivity extends AppCompatActivity {
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

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = editTextId.getText().toString();
                String password = editTextPassword.getText().toString();

                Log.d(TAG, "로그인 버튼 눌러짐");

                Connect_to_Backend backend = new Connect_to_Backend();
                backend.login(username,password);

                if (username.equals("admin") && password.equals("1234")) {
                    // ID와 PW가 일치하면 새로운 액티비티로 이동

                    Intent intent = new Intent(MainActivity.this, AdminActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(MainActivity.this, EmployerActivity.class);
                    startActivity(intent);
                }
            }
        });
    }
}