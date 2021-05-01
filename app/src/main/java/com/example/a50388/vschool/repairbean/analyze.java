package com.example.a50388.vschool.repairbean;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.a50388.vschool.R;
import com.example.a50388.vschool.repairbean.report.alreadyreActivity;
import com.example.a50388.vschool.repairbean.report.damagelocalActivity;
import com.example.a50388.vschool.repairbean.report.damagethingActivity;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public
class analyze extends AppCompatActivity {

    @Override
    protected
    void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analyze);
        list();
    }

    private void list() {
        String[] title=new String[]{"修复情况分析","损坏地点分析","宿舍物品分析"};
        List<Map<String,Object>> listitem=new ArrayList<Map<String, Object>>();
        for (int i=0;i<title.length;i++){
            Map<String,Object> map=new HashMap<String, Object>();
            map.put("name",title[i]);
            listitem.add(map);
        }
        SimpleAdapter adapter=new SimpleAdapter(this,listitem,R.layout.analyzelist,new String[]{"name"},
                new int[]{R.id.TVanalyze});
        ListView listView=(ListView)findViewById(R.id.LVanalyze);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Map<String,Object> map= (Map<String, Object>) parent.getItemAtPosition(position);
                switch (position){
                    case 0:
                        Intent dualintent=new Intent(analyze.this,alreadyreActivity.class);
                        startActivity(dualintent);
                        break;
                    case 1:
                        Intent classlocationintent=new Intent(analyze.this,damagelocalActivity.class);
                        startActivity(classlocationintent);
                        break;
                    case 2:
                        Intent plintent=new Intent(analyze.this,damagethingActivity.class);
                        startActivity(plintent);
                        break;


                }
            }
        });

    }
}
