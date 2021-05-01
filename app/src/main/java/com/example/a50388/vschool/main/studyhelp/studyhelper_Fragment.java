package com.example.a50388.vschool.main.studyhelp;

import android.app.Fragment;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.a50388.vschool.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public
class studyhelper_Fragment extends Fragment {
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.studyhelper, null);
        return view;
    }
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        list();
    }

    private void list() {
        String[] title=new String[]{"英语四六级专区","英语六级专区","计算机二三四级专区","教室资格证专区","专升本专区","考研专区","普通话考试专区"};
        List<Map<String,Object>> listitem=new ArrayList<Map<String, Object>>();
        for (int i=0;i<title.length;i++){
            Map<String,Object> map=new HashMap<String, Object>();
            map.put("exam",title[i]);
            listitem.add(map);
        }
        SimpleAdapter adapter=new SimpleAdapter(getActivity(),listitem,R.layout.examlist,new String[]{"exam"},
                new int[]{R.id.TVexamlist});
        ListView listView=(ListView)getActivity().findViewById(R.id.examlist);
        listView.setAdapter(adapter);
    }
}