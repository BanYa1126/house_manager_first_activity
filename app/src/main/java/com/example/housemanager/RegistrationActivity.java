package com.example.housemanager;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.widget.AdapterView;

public class RegistrationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registeration_activity_main);

        LinearLayout headerAdmin = findViewById(R.id.headerAdmin);

        HomeIconClickListener listener = new HomeIconClickListener(this);
        headerAdmin.setOnClickListener(listener);

        ListView listView = findViewById(R.id.person_listview);

        // 표시할 단일 데이터
        String[] data = {"test1","test2","test3","test4","test5"};
        String[] name ={"이준화","박진혁","이진형","최영재","김강희"};

        CustomAdapter1 adapter = new CustomAdapter1(this, data, name);

        // ListView에 어댑터 설정
        listView.setAdapter(adapter);

        // ListView 아이템 클릭 리스너 설정
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Intent를 사용하여 SecondActivity로 전환
                Intent intent = new Intent(RegistrationActivity.this, RegistrationActivity2.class);
                startActivity(intent);
            }
        });
    }
}
