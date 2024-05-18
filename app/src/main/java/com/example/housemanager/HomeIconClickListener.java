package com.example.housemanager;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

public class HomeIconClickListener implements View.OnClickListener {
    private final Context context;

    public HomeIconClickListener(Context context) {
        this.context = context;
    }

    @Override
    public void onClick(View view) {
        // headerAdmin과 headerEmployer 뷰의 ID를 가져와서 처리
        int headerId = view.getId();

        ImageView homeIcon;
        if (headerId == R.id.headerAdmin) {
            homeIcon = view.findViewById(R.id.imgHomeIcon);
            if (homeIcon != null) {
                homeIcon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, AdminActivity.class);
                        context.startActivity(intent);
                    }
                });
            }
        } else if (headerId == R.id.headerEmployer) {
            homeIcon = view.findViewById(R.id.imgHomeIcon);
            if (homeIcon != null) {
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
}