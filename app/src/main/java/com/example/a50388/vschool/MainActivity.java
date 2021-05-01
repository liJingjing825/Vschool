package com.example.a50388.vschool;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;

import android.os.Bundle;
import android.text.Layout;
import android.transition.Slide;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.a50388.vschool.firstpage.enterActivity;
import com.example.a50388.vschool.main.entertain.entertainment_Fragment;
import com.example.a50388.vschool.main.homepage.homepage_Fragment;
import com.example.a50388.vschool.main.mainlist.CourseActivity;
import com.example.a50388.vschool.main.mainlist.address.addressActivity;
import com.example.a50388.vschool.main.mainlist.cashbook.cashbookActivity;
import com.example.a50388.vschool.main.mainlist.changpassActivity;
import com.example.a50388.vschool.main.mainlist.help_feedbackActivity;
import com.example.a50388.vschool.main.mainlist.mydynamicActivity;
import com.example.a50388.vschool.main.mainlist.planActivity;
import com.example.a50388.vschool.main.myhomeHorizon;
import com.example.a50388.vschool.main.school.school_Fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends Activity implements View.OnClickListener {
    private ImageView home, school, study, entertainment;
    private myhomeHorizon mLeftMenue;

    private TextView username,userid;
    private FragmentManager manager;
    private FragmentTransaction transaction;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*
        * 在界面显示名字和学号*/
        MyApplication app=(MyApplication)getApplication();
        username=(TextView)findViewById(R.id.username);
        username.setText(app.getName());
        userid=(TextView)findViewById(R.id.userid) ;
        userid.setText(app.getId());

        manager=getFragmentManager();
        transaction=manager.beginTransaction();
        transaction.add(R.id.mainfragment,new homepage_Fragment());

        transaction.addToBackStack(null);
        transaction.commit();

        list();
        show();
        mLeftMenue=(myhomeHorizon) findViewById(R.id.menue);
    }
    private void list() {
        int[] Imageid=new int[]{R.drawable.dynamic,R.drawable.cashbook,R.drawable.planner,
                R.drawable.syllabus,R.drawable.myclass,R.drawable.changpass,R.drawable.help_feedback};
        String[] title=new String[]{"我的动态","记账本","计划本","课程表","通讯录","修改密码","重新登录"};
        List<Map<String,Object>> listitem=new ArrayList<Map<String, Object>>();
        for (int i=0;i<Imageid.length;i++){
            Map<String,Object> map=new HashMap<String, Object>();
            map.put("image",Imageid[i]);
            map.put("name",title[i]);
            listitem.add(map);
        }
        SimpleAdapter adapter=new SimpleAdapter(this,listitem,R.layout.myhomelist,new String[]{"name","image"},
                new int[]{R.id.mhtitle,R.id.mhImage});
        ListView listView=(ListView)findViewById(R.id.myhomelist);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Map<String,Object> map= (Map<String, Object>) parent.getItemAtPosition(position);
                switch (position){
                    case 0:
                        Intent naintent=new Intent(MainActivity.this,mydynamicActivity.class);
                        startActivity(naintent);
                      //  finish();
                        break;
                    case 1:
                        Intent cbintent=new Intent(MainActivity.this,cashbookActivity.class);
                        startActivity(cbintent);
                      //  finish();
                        break;
                    case 2:
                        Intent plintent=new Intent(MainActivity.this,planActivity.class);
                        startActivity(plintent);
                      //  finish();
                        break;
                    case 3:
                        Intent syintent=new Intent(MainActivity.this, CourseActivity.class);
                        startActivity(syintent);
                     //   finish();
                        break;
                    case 4:
                        Intent mcintent=new Intent(MainActivity.this,addressActivity.class);
                        startActivity(mcintent);
                     //   finish();
                        break;
                    case 5:
                        Intent cfintent=new Intent(MainActivity.this,changpassActivity.class);
                        startActivity(cfintent);
                     //   finish();
                        break;
                    case 6:
                        Intent hfintent=new Intent(MainActivity.this,enterActivity.class);
                        startActivity(hfintent);
                        finish();
                        break;

                }
            }
        });

    }

    private void show() {
        home = (ImageView) findViewById(R.id.comehome);
        home.setOnClickListener(this);

        school=(ImageView)findViewById(R.id.school);
        school.setOnClickListener(this);

        entertainment=(ImageView)findViewById(R.id.entertainment);
        entertainment.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {
       transaction=manager.beginTransaction();
        Fragment f = null;
        reseImg();
        switch (view.getId()) {
            case R.id.comehome:
                home.setImageResource(R.drawable.home_page2);
                transaction.replace(R.id.mainfragment,new homepage_Fragment());
                break;
            case R.id.school:
                school.setImageResource(R.drawable.school2);
                transaction.replace(R.id.mainfragment,new school_Fragment());
                break;
            case R.id.entertainment:
                entertainment.setImageResource(R.drawable.entertainment2);
                transaction.replace(R.id.mainfragment,new entertainment_Fragment());
                break;

        }
        transaction.commit();
    }


    private void reseImg() {
        home.setImageResource(R.drawable.home_page);
        entertainment.setImageResource(R.drawable.entertainment);
        school.setImageResource(R.drawable.school);


    }


    public void tomyhome(View view) {

        mLeftMenue.toggle();
    }
}



