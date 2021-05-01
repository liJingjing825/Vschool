package com.example.a50388.vschool.main.mainlist.cashbook;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.wifi.aware.DiscoverySession;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;


import com.example.a50388.vschool.MainActivity;
import com.example.a50388.vschool.R;
import com.example.a50388.vschool.repairbean.repairAdapter;
import com.example.a50388.vschool.repairbean.repairValueItem;
import com.example.a50388.vschool.repairbean.repairValueUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class cashbookActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView IVreturnhome,IVcbplus;
    private String[] valueList;
    private ListView view;

    private repairAdapter adapter;
    private List<repairValueItem> inforList;
    String accountNo;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cashbook);
        intn();
        accountNo = getIntent().getStringExtra("accountNo");

    }




    private void intn() {
        IVreturnhome=(ImageView)findViewById(R.id.cashbookhomereturn);
        IVreturnhome.setOnClickListener(this);
        IVcbplus=(ImageView)findViewById(R.id.pluscashbook);
        IVcbplus.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.cashbookhomereturn:
                Intent returnintent=new Intent(cashbookActivity.this,MainActivity.class);
                startActivity(returnintent);
                finish();
                break;
            case R.id.pluscashbook:
                Intent inplus=new Intent(cashbookActivity.this,cashbookplusActivity.class);
                inplus.putExtra("accountNo",accountNo);
                startActivity(inplus);
                finish();
                break;
        }
    }



}

