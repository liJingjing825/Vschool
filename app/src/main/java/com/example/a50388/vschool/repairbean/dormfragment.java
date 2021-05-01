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
class dormfragment extends Fragment {
    private ListView view;

    private String[] valueList;

    private repairAdapter adapter;
    private List<repairValueItem> inforList;
    private Spinner localspin;

    public
    View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dormfragment, null);
        return view;
    }

    public
    void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        localspin=(Spinner)getActivity().findViewById(R.id.localspin);
        inforList = new ArrayList<repairValueItem>();
        view=(ListView)getActivity().findViewById(R.id.dormlist);
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
                // TODO: Implement this method
            }
        });

        getData();



    }
    Handler handler = new Handler() {
        @Override
        public void handleMessage(final Message msg) {
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
                    URL url=new URL("http://"+getActivity().getApplication().getString(R.string.serverid)+"/repiregetData2.php");
                    HttpURLConnection huc=(HttpURLConnection) url.openConnection();
                    huc.setRequestMethod("GET");
                    huc.connect();

                    String data1;
                    if(localspin.getSelectedItem().toString().equals("全部"))
                    {
                        data1 = "local="+"any";
                    }
                    else
                    {
                        data1 = "local="+localspin.getSelectedItem().toString();
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
                    }
                    Log.i("wety",line);
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
