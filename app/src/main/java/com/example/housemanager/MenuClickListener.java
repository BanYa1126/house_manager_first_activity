package com.example.housemanager;

import android.content.Context;
import android.content.Intent;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;

public class MenuClickListener implements View.OnClickListener {
    private final Context context;

    public MenuClickListener(Context context) {
        this.context = context;
    }

    @Override
    public void onClick(View v) {
        PopupMenu popup = new PopupMenu(context, v);
        popup.getMenuInflater().inflate(R.menu.menu_example, popup.getMenu());

        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.one) {
                    // 메인 화면
                    Intent intentMainScreen = new Intent(context, MainActivity.class);
                    context.startActivity(intentMainScreen);
                } else if (itemId == R.id.two) {
                    System.exit(0);
                }
                return true;
            }
        });
        popup.show();
    }
}