package com.example.a50388.vschool.repairbean;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a50388.vschool.MainActivity;
import com.example.a50388.vschool.MyApplication;
import com.example.a50388.vschool.R;
import com.example.a50388.vschool.main.homepage.sign.buildingclassActivity;

import java.util.ArrayList;
import java.util.List;

public class reportrepairsActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView IVreturnhome,IVrepairschoolroon,IVrepairdorm,puls;
    private TextView infor,myinfor;
    private FragmentManager manager;
    private FragmentTransaction transaction;
    private Intent Itdorm,Itschoolroom;
    private Spinner localspin;
    private List<String> list;
    private ArrayAdapter<String> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reportrepairs);
        manager = getFragmentManager();
        transaction=manager.beginTransaction();
        transaction.add(R.id.reportrefragment,new schoolroomfragment());
        transaction.commit();


        myinfor=(TextView)findViewById(R.id.myinfor);
        infor=(TextView)findViewById(R.id.infor);
        MyApplication myApplication1=(MyApplication)getApplication();
        if(myApplication1.getType().equals("admin"))
        {
            infor.setVisibility(View.VISIBLE);
        }
        else
        {
            myinfor.setVisibility(View.VISIBLE);
        }

        intn();
    }

    public void setfleatypr(){

        list=new ArrayList<String>();
        list.add("全部");
        list.add("宿舍");
        list.add("教室");
        list.add("公共设施");
        list.add("图书馆");
        list.add("餐厅");
        list.add("其他");



        /*新建适配器*/
        adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,list);

        /*adapter设置一个下拉列表样式，参数为系统子布局*/
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        /*spDown加载适配器*/
        localspin.setAdapter(adapter);
    }


    private void intn() {
        IVreturnhome=(ImageView)findViewById(R.id.reportrepairshomereturn);
        IVreturnhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public
            void onClick(View v) {
                Intent mhrintent=new Intent(reportrepairsActivity.this,MainActivity.class);
                startActivity(mhrintent);
                finish();
            }
        });


        infor.setOnClickListener(new View.OnClickListener() {
            @Override
            public
            void onClick(View v) {
                Intent analintent=new Intent(reportrepairsActivity.this,analyze.class);
                startActivity(analintent);
              //finish();
            }
        });

        myinfor.setOnClickListener(new View.OnClickListener() {
            @Override
            public
            void onClick(View v) {
                Intent analintent=new Intent(reportrepairsActivity.this,myrepairshowActivity.class);
                startActivity(analintent);
                //finish();
            }
        });


        puls=(ImageView)findViewById(R.id.plussign);
        puls.setOnClickListener(new View.OnClickListener() {
            @Override
            public
            void onClick(View v) {
                Intent plusintent=new Intent(reportrepairsActivity.this,plusrepairsActivity.class);
                startActivity(plusintent);
             //   finish();
            }
        });






    }

    @Override
    public void onClick(View v) {
        reseImg();
        transaction = manager.beginTransaction();

    }


    private
    void reseImg() {
        IVrepairschoolroon.setImageResource(R.drawable.triangle2);
        IVrepairdorm.setImageResource(R.drawable.triangle2);
    }
}
