package com.example.a50388.vschool.main;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.a50388.vschool.MainActivity;
import com.example.a50388.vschool.R;
import com.example.a50388.vschool.main.mainlist.modify.modify_classActivity;
import com.example.a50388.vschool.main.mainlist.modify.modify_genderActivity;
import com.example.a50388.vschool.main.mainlist.modify.modify_idActivity;
import com.example.a50388.vschool.main.mainlist.modify.modify_majorActivity;
import com.example.a50388.vschool.main.mainlist.modify.modify_nameActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class myinformationActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView returnImage;
    private TextView TVname,TVclass,TVgender,TVID;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myinformation);
        String[] title=new String[]{"姓名","性别","学号","班级","专业"};
        String[] mytitle=new String[]{"xxx","男","xxxxxxxxxxxx","xxxxxxxx","xxxxxx"};
        List<Map<String,Object>> listitem1=new ArrayList<Map<String, Object>>();
        for (int i=0;i<title.length;i++){
            Map<String,Object> map=new HashMap<String, Object>();
            map.put("message",title[i]);
            map.put("myself",mytitle[i]);
            listitem1.add(map);
        }
        SimpleAdapter adapter=new SimpleAdapter(this,listitem1,R.layout.myinformationlist,new String[]{"message","myself"},
                new int[]{R.id.intext,R.id.modmyinformation});
        ListView listView=(ListView)findViewById(R.id.myinformationlist);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Map<String,Object> map= (Map<String, Object>) parent.getItemAtPosition(position);
                switch (position){
                    case 0:
                        Intent intentname=new Intent(myinformationActivity.this,modify_nameActivity.class);
                        startActivity(intentname);
                        finish();
                        break;
                    case 1:
                        Intent intentgender=new Intent(myinformationActivity.this,modify_genderActivity.class);
                        startActivity(intentgender);
                        finish();
                        break;
                    case 2:
                        Intent intentID=new Intent(myinformationActivity.this,modify_idActivity.class);
                        startActivity(intentID);
                        finish();
                        break;
                    case 3:
                        Intent intentclass=new Intent(myinformationActivity.this,modify_classActivity.class);
                        startActivity(intentclass);
                        finish();
                        break;
                    case 4:
                        Intent intentmajor=new Intent(myinformationActivity.this,modify_majorActivity.class);
                        startActivity(intentmajor);
                        finish();
                        break;

                }

            }
        });
        init();


    }



    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intentKB = new Intent(myinformationActivity.this, MainActivity.class);
            startActivity(intentKB);
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void init() {
        returnImage=(ImageView) findViewById(R.id.mationmyhomereturn);
        returnImage.setOnClickListener(this);
    }
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.mationmyhomereturn:
                Intent intent1=new Intent(myinformationActivity.this,MainActivity.class);
                startActivity(intent1);
                finish();
                break;
        }

    }


}
