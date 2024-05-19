package com.example.housemanager;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

public class HomeIconClickListener implements View.OnClickListener {
    private static final String TAG = "HomeIconClickListener"; // 로그를 구분하기 위한 TAG 설정
    private Connect_to_Backend backend;
    private final Context context;

    public HomeIconClickListener(Context context) {
        this.context = context;
    }

    @Override
    public void onClick(View view) {
        int headerId = view.getId();
        ImageView homeIcon = null;

        if (headerId == R.id.headerAdmin) {
            homeIcon = view.findViewById(R.id.imgHomeIcon);
            homeIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, AdminActivity.class);
                    context.startActivity(intent);
                }
            });
        } else if (headerId == R.id.headerEmployer) {
            homeIcon = view.findViewById(R.id.imgHomeIcon);
            homeIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, EmployerActivity.class);
                    context.startActivity(intent);
                }
            });
        }
    }
}