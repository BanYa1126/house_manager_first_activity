package com.example.housemanager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CustomAdapter2 extends BaseAdapter {

    private Context context;
    private String[] RegNum;
    private LayoutInflater inflater;

    public CustomAdapter2(Context context, String[] RegNum) {
        this.context = context;
        this.RegNum = RegNum;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return RegNum.length;
    }

    @Override
    public Object getItem(int position) {
        return RegNum[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.building_listview_activity, parent, false);
        }

        TextView RegNumberText = convertView.findViewById(R.id.RegNum);

        RegNumberText.setText(RegNumberText.getText().toString() + RegNum[position]);
        return convertView;
    }
    public void updateData(String[] newData) {
        this.RegNum = newData;
        notifyDataSetChanged();
    }
}