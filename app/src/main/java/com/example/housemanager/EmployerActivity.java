package com.example.housemanager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
public class EmployerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employer_main);

        LinearLayout headerEmployer = findViewById(R.id.headerEmployer);

        HomeIconClickListener listener = new HomeIconClickListener(this);
        headerEmployer.setOnClickListener(listener);

        Button UseButton = findViewById(R.id.useButton);
        Button HouseCheckButton = findViewById(R.id.housecheckButton);
        Button ContractButton = findViewById(R.id.contractButton);
        Button MoneyButton = findViewById(R.id.moneyButton);

        UseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),HouseCActivity.class);
                startActivity(intent);
            }
        });
        HouseCheckButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), HouseCheckViewActivity.class);
                startActivity(intent);
            }
        });
        ContractButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),ContractViewActivity.class);
                startActivity(intent);
            }
        });
        MoneyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),HouseRActivity.class);
                startActivity(intent);
            }
        });
    }
}