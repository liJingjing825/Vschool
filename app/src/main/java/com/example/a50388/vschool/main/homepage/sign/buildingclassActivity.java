package com.example.a50388.vschool.main.homepage.sign;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.Poi;
import com.example.a50388.vschool.MyApplication;
import com.example.a50388.vschool.R;
import com.example.a50388.vschool.firstpage.enterActivity;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class buildingclassActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btnbuild;
    private EditText ETbcnumber,ETbcpassword;
    private ImageView IMreturn;
    private TextView LocationResult;
    private Spinner classlist;
    private List<String> list;
    private ArrayAdapter<String> adapter;
    public  String  ack[];
    private double Latitude,Longitude;
    private Netclass a=new Netclass();

    public MyApplication myApplication=(MyApplication)getApplication();
    public LocationClient mLocationClient = null;
    private MyLocationListener myListener = new MyLocationListener();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.setThreadPolicy(new
                StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());
        StrictMode.setVmPolicy(
                new StrictMode.VmPolicy.Builder().detectLeakedSqlLiteObjects().detectLeakedClosableObjects().penaltyLog().penaltyDeath().build());
        MyApplication app=(MyApplication)getApplication();


        Netclass netclass=new Netclass();
        String getres= netclass.isclass(app.getId());
        if(getres.equals(""))
        {
            setContentView(R.layout.activity_buildingclass);
            Toast.makeText(buildingclassActivity.this,"网络连接失败", Toast.LENGTH_SHORT).show();
        }
        else
        {
            final String ack[]=getres.split("/");
            if(ack[0].equals("2002"))
            {
                setContentView(R.layout.activity_buildingclass);
            }
            else
            {
                app.setSignclass(ack[0]);
                Intent btintent=new Intent(buildingclassActivity.this,signclassActivity.class);
                startActivity(btintent);
                finish();
                return;
            }
        }



        classlist=(Spinner) findViewById(R.id.classlist);



       setclass();



        LocationResult = (TextView) findViewById(R.id.textView1);
        LocationResult.setMovementMethod(ScrollingMovementMethod.getInstance());


        mLocationClient = new LocationClient(getApplicationContext());
        //声明LocationClient类
        mLocationClient.registerLocationListener(myListener);

        LocationClientOption option = new LocationClientOption();

        option.setIsNeedLocationDescribe(true);
        option.setIsNeedLocationPoiList(true);
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        //可选，设置定位模式，默认高精度
        //LocationMode.Hight_Accuracy：高精度；
        //LocationMode. Battery_Saving：低功耗；
        //LocationMode. Device_Sensors：仅使用设备；
        option.setCoorType("bd09ll");
        //可选，设置返回经纬度坐标类型，默认gcj02
        //gcj02：国测局坐标；
        //bd09ll：百度经纬度坐标；
        //bd09：百度墨卡托坐标；
        //海外地区定位，无需设置坐标类型，统一返回wgs84类型坐标
        option.setScanSpan(1000);
        //可选，设置发起定位请求的间隔，int类型，单位ms
        //如果设置为0，则代表单次定位，即仅定位一次，默认为0
        //如果设置非0，需设置1000ms以上才有效
        option.setOpenGps(true);
        //可选，设置是否使用gps，默认false
        //使用高精度和仅用设备两种定位模式的，参数必须设置为true
        option.setLocationNotify(true);
        //可选，设置是否当GPS有效时按照1S/1次频率输出GPS结果，默认false
        option.setIgnoreKillProcess(false);
        //可选，定位SDK内部是一个service，并放到了独立进程。
        //设置是否在stop的时候杀死这个进程，默认（建议）不杀死，即setIgnoreKillProcess(true)
        option.SetIgnoreCacheException(false);
        //可选，设置是否收集Crash信息，默认收集，即参数为false
        option.setWifiCacheTimeOut(5*60*1000);
        //可选，7.2版本新增能力
        //如果设置了该接口，首次启动定位时，会先判断当前WiFi是否超出有效期，若超出有效期，会先重新扫描WiFi，然后定位
        option.setEnableSimulateGps(false);
        //可选，设置是否需要过滤GPS仿真结果，默认需要，即参数为false
        mLocationClient.setLocOption(option);
        //mLocationClient为第二步初始化过的LocationClient对象
        //需将配置好的LocationClientOption对象，通过setLocOption方法传递给LocationClient对象使用
        //更多LocationClientOption的配置，请参照类参考中LocationClientOption类的详细说明
        mLocationClient.start();

        intn();
    }

    private void intn() {
        btnbuild=(Button)findViewById(R.id.buildtrue);
        btnbuild.setOnClickListener(this);
        IMreturn=(ImageView)findViewById(R.id.signhomereturn);
        IMreturn.setOnClickListener(this);

    }

/*
* 下拉表单适配器
*
* */
    public void setclass(){

        Netclass a=new Netclass();

        String m=a.getclass();
        if(m!=null)
        {
              ack= m.split("/");
        }
        else
        {
            Toast.makeText(getApplicationContext(), "获取数据失败", Toast.LENGTH_LONG).show();
            return;
        }



        list=new ArrayList<String>();
        for(int i=0;i<ack.length;i++)
        {

            list.add(ack[i]);
        }

        /*新建适配器*/
        adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,list);

        /*adapter设置一个下拉列表样式，参数为系统子布局*/
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        /*spDown加载适配器*/
        classlist.setAdapter(adapter);
       /* classlist.setOnItemClickListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {

                //拿到被选择项的值
                str = (String) sp.getSelectedItem();
                //把该值传给 TextView
                tv.setText(str);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub

            }
        });*/


    }




    public void onClick(View view) {
        switch (view.getId()){
            case R.id.buildtrue:

                MyApplication myApplication1=(MyApplication)getApplication();
                if(myApplication1.getType().equals("user"))
                {

                    Toast.makeText(buildingclassActivity.this,"您无权创建教室", Toast.LENGTH_LONG).show();
                    break;
                }
                else{

                    String res=a.creating((String) classlist.getSelectedItem(),myApplication1.getId(),Latitude,Longitude);

                    if(res.equals("2003"))
                    {
                        Toast.makeText(buildingclassActivity.this,"创建教室失败", Toast.LENGTH_LONG).show();
                        break;
                    }
                    else{
                        myApplication1.setSignclass(classlist.getSelectedItem().toString());
                        Intent btintent=new Intent(buildingclassActivity.this,signclassActivity.class);
                        startActivity(btintent);
                        finish();
                        break;
                    }

                }

            case R.id.signhomereturn:
                Intent shrintent=new Intent(buildingclassActivity.this,sign_inActivity.class);
                startActivity(shrintent);
                finish();
                break;
        }
    }


    public void logMsg(String str) {
        final String s = str;
        try {
            if (LocationResult != null){
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        LocationResult.post(new Runnable() {
                            @Override
                            public void run() {
                                LocationResult.setText(s);
                            }
                        });

                    }
                }).start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public class MyLocationListener extends BDAbstractLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location){
            //此处的BDLocation为定位结果信息类，通过它的各种get方法可获取定位相关的全部结果
            //以下只列举部分获取经纬度相关（常用）的结果信息
            //更多结果信息获取说明，请参照类参考中BDLocation类中的说明

            StringBuffer sb = new StringBuffer(256);
            Latitude=location.getLatitude();
            sb.append("\n当前纬度: ");// 纬度
            sb.append(Latitude);
            sb.append("\n当前经度: ");// 经度
            Longitude=location.getLongitude();
            sb.append(Longitude);
            if (location.getPoiList() != null && !location.getPoiList().isEmpty()) {
                sb.append("\n所在位置: ");
                    Poi poi = (Poi) location.getPoiList().get(1);
                    sb.append(poi.getName());

            }
            logMsg(sb.toString());

            mLocationClient.stop();
        }

    }
 /*
 *  实现服务器交互
 * */
    public class Netclass {
        private static final String TAG = "NetUtil";
        /**
         * 使用POST提交方式
         * @param
         * @param
         * @return
         */
        public  String getclass() {
            HttpURLConnection conn = null;
            try {
                URL url =  new URL("http://"+getApplication().getString(R.string.serverid)+"/class/getclass.php");

                conn = (HttpURLConnection) url.openConnection();

                conn.setRequestMethod("GET");//GET和POST必须全大写
                conn.setConnectTimeout(10000);//连接的超时时间
                conn.setReadTimeout(5000);//读数据的超时时间
                conn.setDoOutput(true);//必须设置此方法  允许输出
                //	conn.setRequestProperty("Content-Length", 234);//设置请求消息头  可以设置多个

                //post请求的参数
                //   String data = "username="+username+"&password="+password;
                //   OutputStream out = conn.getOutputStream();
                //   out.write(data.getBytes());
                //  out.flush();
                //  out.close();

                int responseCode = conn.getResponseCode();
                if(responseCode==200){
                    //访问成功，通过流取的页面的数据信息
                    InputStream is = conn.getInputStream();
                    String status = getStringFromInputStream(is);
                    return status;
                }else{
                    Log.i(TAG, "访问失败："+responseCode);
                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally{
                if(conn!=null){
                    conn.disconnect();//释放链接
                }
            }
            return null;

        }
        /*
        * 用来判断当前老师是否已经创建了教室
        * */

     public  String isclass(String adminid) {
         HttpURLConnection conn = null;
         try {
             URL url =  new URL("http://"+getApplication().getString(R.string.serverid)+"/class/isclass.php");

             conn = (HttpURLConnection) url.openConnection();

             conn.setRequestMethod("GET");//GET和POST必须全大写
             conn.setConnectTimeout(10000);//连接的超时时间
             conn.setReadTimeout(5000);//读数据的超时时间
             conn.setDoOutput(true);//必须设置此方法  允许输出
             //	conn.setRequestProperty("Content-Length", 234);//设置请求消息头  可以设置多个

             //post请求的参数
             //   String data = "username="+username+"&password="+password;
              String data = "adminid="+adminid;
             OutputStream out = conn.getOutputStream();
             out.write(data.getBytes());
             out.flush();
             out.close();
             int responseCode = conn.getResponseCode();
             if(responseCode==200){
                 //访问成功，通过流取的页面的数据信息
                 InputStream is = conn.getInputStream();
                 String status = getStringFromInputStream(is);
                 Log.i(TAG, "isclass：" + status);
                 return status;
             }else{
                 Log.i(TAG, "isclass："+responseCode);
             }

         } catch (Exception e) {
             e.printStackTrace();
         } finally{
             if(conn!=null){
                 conn.disconnect();//释放链接
             }
         }
         return null;

     }


        /*
        * 用来创建教室
        * */
        public  String creating(String calssname, String adminid,double Latitude,double Longitude) {
            HttpURLConnection conn = null;
            try {
                URL url =  new URL("http://123.207.145.214/class/createclass.php");
                conn = (HttpURLConnection) url.openConnection();

                conn.setRequestMethod("GET");//GET和POST必须全大写
                conn.setConnectTimeout(10000);//连接的超时时间
                conn.setReadTimeout(5000);//读数据的超时时间
                conn.setDoOutput(true);//必须设置此方法  允许输出
                //	conn.setRequestProperty("Content-Length", 234);//设置请求消息头  可以设置多个

                //post请求的参数
                String data = "classname="+calssname+"&adminid="+adminid+"&Latitude="+Latitude+"&Longitude="+Longitude;
                OutputStream out = conn.getOutputStream();
                out.write(data.getBytes());
                out.flush();
                out.close();

                int responseCode = conn.getResponseCode();
                if(responseCode==200){
                    //访问成功，通过流取的页面的数据信息
                    InputStream is = conn.getInputStream();
                    String status = getStringFromInputStream(is);
                    Log.i(TAG, "访问失败：" + status);
                    return status;
                }else{
                    Log.i(TAG, "访问失败："+responseCode);
                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally{
                if(conn!=null){
                    conn.disconnect();//释放链接
                }
            }
            return null;

        }
        /**
         * 通过字节输入流返回一个字符串信息
         * @param is
         * @return
         * @throws IOException
         */
        private  String getStringFromInputStream(InputStream is) throws IOException {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len=0;
            while((len=is.read(buffer))!=-1){
                baos.write(buffer, 0, len);
            }
            is.close();
            String status = baos.toString("utf-8");// 把流中的数据转换成字符串, 采用的编码是: utf-8
            baos.close();
            int l = status.length();
            return status;
        }
    }


}
