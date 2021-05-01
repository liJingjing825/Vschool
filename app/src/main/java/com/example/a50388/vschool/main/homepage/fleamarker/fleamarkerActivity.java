package com.example.a50388.vschool.main.homepage.fleamarker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.a50388.vschool.MainActivity;
import com.example.a50388.vschool.R;
import com.example.a50388.vschool.main.homepage.sign.buildingclassActivity;

import java.util.ArrayList;
import java.util.List;

public class fleamarkerActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView IVreturnhome,imageview;
    private List<String> list;
    private ArrayAdapter<String> adapter;
    private Spinner typelist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fleamarker);
        typelist=(Spinner) findViewById(R.id.spinner);
        setfleatypr();
        intn();
    }

    /*
    * 为下拉表单初始内容
    * */
    public void setfleatypr(){

        list=new ArrayList<String>();
        list.add("全部");
        list.add("二手书籍");
        list.add("衣物");
        list.add("头饰");
        list.add("电子产品");
        list.add("学习用品");
        list.add("化妆品");
        list.add("其他");



        /*新建适配器*/
        adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,list);

        /*adapter设置一个下拉列表样式，参数为系统子布局*/
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        /*spDown加载适配器*/
        typelist.setAdapter(adapter);
    }

    private void intn() {
        IVreturnhome = (ImageView) findViewById(R.id.fleamarkerhomereturn);
        imageview =(ImageView) findViewById(R.id.image1);
        IVreturnhome.setOnClickListener(this);
        imageview.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fleamarkerhomereturn:
                Intent mhrintent = new Intent(fleamarkerActivity.this, MainActivity.class);
                startActivity(mhrintent);
                finish();
                break;
            case R.id.image1:
                Intent thrintent = new Intent(fleamarkerActivity.this, fleamarkeraddActivity.class);
                startActivity(thrintent);
                finish();
                break;
        }
    }

}