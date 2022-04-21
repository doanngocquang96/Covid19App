package com.example.applicationcv;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.database.QRhistory;

import java.util.ArrayList;
import java.util.List;

public class QRListActivity extends AppCompatActivity {
    TextView button_back;
    TextView qr_list_title_name;
    LinearLayout qr_list_view;
    TextView qr_list_view_empty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrlist);
        button_back = findViewById(R.id.tos_button_back);
        qr_list_title_name = findViewById(R.id.qr_list_title_name);
        qr_list_view = findViewById(R.id.qr_list_view);
        qr_list_view_empty = findViewById(R.id.qr_list_view_empty);

        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Intent intent = getIntent();
        qr_list_title_name.setText(intent.getStringExtra("title"));
//        List<String> listTime = QRhistory.getAllTime(this);

        List<String> listQR = QRhistory.getAllQR(this);

        listQR.add("123456789");
//        listQR.add("123456788");
//        listQR.add("123456787");
//        listQR.add("123456786");
        LayoutInflater factory = LayoutInflater.from(this);
        if (listQR.size() != 0) {
            for (String qr : listQR) {
                View view = factory.inflate(R.layout.item_qr, null);
                ((TextView) view.findViewById(R.id.qr_name)).setText(qr);
                qr_list_view.addView(view);
            }
            qr_list_view_empty.setVisibility(View.GONE);
            qr_list_view.setVisibility(View.VISIBLE);
        } else {
            qr_list_view_empty.setVisibility(View.VISIBLE);
            qr_list_view.setVisibility(View.GONE);
        }
    }
}