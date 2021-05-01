package com.example.a50388.vschool.repairbean;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.a50388.vschool.R;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 50388 on 2018/8/10.
 */

public
class schoolroomfragment extends Fragment {
    private ListView view;

    private Spinner localspin;
    private List<String> list;
    private ArrayAdapter<String> adapter1;
    private String[] valueList;

    private repairAdapter adapter;
    private List<repairValueItem> inforList;

    public
    View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.schoolroomfragment, null);
        localspin=(Spinner) getActivity().findViewById(R.id.localspin);
        setfleatypr();
        localspin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            //当选中某一个数据项时触发该方法
            /*
             * parent接收的是被选择的数据项所属的 Spinner对象，
             * view参数接收的是显示被选择的数据项的TextView对象
             * position接收的是被选择的数据项在适配器中的位置
             * id被选择的数据项的行号
             */
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,int position, long id) {
                //System.out.println(spinner==parent);//true
                //System.out.println(view);
                //String data = adapter.getItem(position);//从适配器中获取被选择的数据项
                //String data = list.get(position);//从集合中获取被选择的数据项
              //  String data = (String)localspin.getItemAtPosition(position);//从spinner中获取被选择的数据
               //  Toast.makeText(getActivity(), data, Toast.LENGTH_SHORT).show();
                onstart();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });
        return view;
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
        adapter1=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item,list);

        /*adapter设置一个下拉列表样式，参数为系统子布局*/
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        /*spDown加载适配器*/
        localspin.setAdapter(adapter1);
    }

    public void onstart(){
        inforList = new ArrayList<repairValueItem>();
        view=(ListView)getActivity().findViewById(R.id.schoolroomlist);
        view.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> p1, View p2, int p3, long p4)
            {
                repairValueItem item = inforList.get(p3);
                Intent i=new Intent(getActivity(),messagerepairshowActivity.class);
                i.putExtra("Image",item.getPictureImage());
                i.putExtra("res",item.getObject());
                i.putExtra("mark",item.getIsRepaire());
                i.putExtra("site",item.getIsDormotory());
                i.putExtra("details",item.getPosition());
                i.putExtra("number",item.getNumberStamp());
                i.putExtra("housenumber", item.getHostNO());
                i.putExtra("tellphone", item.getTellphone());
                i.putExtra("speak", "speak");
                startActivity(i);
                getActivity().finish();
                // TODO: Implement this method
            }
        });

        getData();

    }

   /* public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        inforList = new ArrayList<repairValueItem>();
        view=(ListView)getActivity().findViewById(R.id.schoolroomlist);
        view.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> p1, View p2, int p3, long p4)
            {
                repairValueItem item = inforList.get(p3);
                Intent i=new Intent(getActivity(),messagerepairshowActivity.class);
                i.putExtra("Image",item.getPictureImage());
                i.putExtra("res",item.getObject());
                i.putExtra("mark",item.getIsRepaire());
                i.putExtra("site",item.getIsDormotory());
                i.putExtra("details",item.getPosition());
                i.putExtra("number",item.getNumberStamp());
                i.putExtra("housenumber", item.getHostNO());
                i.putExtra("speak", "speak");
                startActivity(i);
                getActivity().finish();
                // TODO: Implement this method
            }
        });

        getData();


    }*/
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            // TODO: Implement this method
            super.handleMessage(msg);
            if(msg.what==1){
                Log.i("Test","Ok");
                inforList = repairValueUtils.getValue((String[])msg.obj);
                //mda=new repairsDataAdapter(getActivity(), valueList);
                adapter = new repairAdapter(getActivity(), inforList);
                view.setAdapter(adapter);

            }
        }

    };

    private void getData() {
        Toast.makeText(getActivity(), "正在加载，请稍后，请保证信号良好", Toast.LENGTH_LONG).show();
        new Thread(new Runnable(){

            @Override
            public void run()
            {

                try
                {
                    String[] data;
                    URL url=new URL("http://"+getActivity().getApplication().getString(R.string.serverid)+"/repair/repiregetData2.php");
                    HttpURLConnection huc=(HttpURLConnection) url.openConnection();
                    huc.setRequestMethod("POST");
                    huc.setDoOutput(true);
                    huc.connect();

                    String data1;
                    if(localspin.getSelectedItem().toString().equals("全部"))
                    {
                        data1 = "local="+"1 or '1'='1'";
                    }
                    else
                    {
                        data1 = "local="+"\'"+localspin.getSelectedItem().toString()+"\'";
                    }

                    OutputStream out=huc.getOutputStream();
                    out.write(data1.getBytes());
                    out.flush();
                    out.close();


                    String line;
                    BufferedReader read=new BufferedReader(new InputStreamReader(huc.getInputStream()));
                    StringBuffer buf=new StringBuffer();
                    while((line=read.readLine())!=null){
                        buf.append(line);
                        Log.i("dfhsh",line);
                    }

                    data=buf.toString().split("</br>");
                    Message msg=new Message();
                    msg.what=1;
                    msg.obj=data;
                    handler.sendMessage(msg);
                    read.close();

                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }

                // TODO: Implement this method
            }
        }).start();

    }



}
