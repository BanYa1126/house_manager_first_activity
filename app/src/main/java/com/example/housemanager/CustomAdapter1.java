package com.example.housemanager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CustomAdapter1 extends BaseAdapter {

    private Context context;
    private String[] RegNumbers;
    private String[] RegNames;
    private LayoutInflater inflater;

    public CustomAdapter1(Context context, String[] RegNumbers, String[] RegNames) {
        this.context = context;
        this.RegNumbers = RegNumbers;
        this.RegNames = RegNames;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return RegNumbers.length;
    }

    @Override
    public Object getItem(int position) {
        return RegNumbers[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.reg_activity_listview, parent, false);
        }

        TextView RegNumberText = convertView.findViewById(R.id.RegNumber);
        TextView RegNameText = convertView.findViewById(R.id.RegName);

        RegNumberText.setText("계약관리번호 : " + RegNumbers[position]);
        RegNameText.setText("계약자 이름 : " + RegNames[position]);

        return convertView;
    }
}