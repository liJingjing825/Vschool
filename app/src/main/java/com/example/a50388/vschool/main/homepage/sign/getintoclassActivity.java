package com.example.a50388.vschool.main.homepage.sign;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.example.a50388.vschool.MyApplication;
import com.example.a50388.vschool.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class getintoclassActivity extends AppCompatActivity implements SensorEventListener,View.OnClickListener {
    private MapView mMapView;
    private BaiduMap mBaiduMap;
    private ImageView getintoreturn;
    private Button getinclass;

    // 定位相关
    LocationClient mLocClient;
    public MyLocationListenner myListener = new MyLocationListenner();
    private MyLocationConfiguration.LocationMode mCurrentMode;
    BitmapDescriptor mCurrentMarker;
    private static final int accuracyCircleFillColor = 0xAAFFFF88;
    private static final int accuracyCircleStrokeColor = 0xAA00FF00;
    private SensorManager mSensorManager;
    private Double lastX = 0.0;
    private int mCurrentDirection = 0;
    private double mCurrentLat = 0.0;
    private double mCurrentLon = 0.0;
    private float mCurrentAccracy;
    private double Latitude,Longitude;

    boolean isFirstLoc = true; // 是否首次定位
    private MyLocationData locData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        SDKInitializer.setCoordType(CoordType.BD09LL);
        setContentView(R.layout.activity_getintoclass);

        StrictMode.setThreadPolicy(new
                StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());
        StrictMode.setVmPolicy(
                new StrictMode.VmPolicy.Builder().detectLeakedSqlLiteObjects().detectLeakedClosableObjects().penaltyLog().penaltyDeath().build());


        mMapView = (MapView) findViewById(R.id.mmap);
        mBaiduMap = mMapView.getMap();
        // 开启定位图层
        mBaiduMap.setMyLocationEnabled(true);

        // 设置地图放大缩小参数
       // MapStatusUpdate msu = MapStatusUpdateFactory.zoomTo(5.0f);
       // mBaiduMap.setMapStatus(msu);

        // 定位初始化
        mLocClient = new LocationClient(this);
        mLocClient.registerLocationListener(myListener);
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true); // 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setScanSpan(1000);
        mLocClient.setLocOption(option);
        mLocClient.start();

        intn();
}

    private void intn() {
        getinclass=(Button)findViewById(R.id.getinclass);
        getinclass.setOnClickListener(this);
        getintoreturn=(ImageView)findViewById(R.id.getintoreturn);
        getintoreturn.setOnClickListener(this);

    }

  /*  public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            //启动一个意图,回到桌面
            Intent backHome = new Intent(Intent.ACTION_MAIN);
            backHome.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            backHome.addCategory(Intent.CATEGORY_HOME);
            startActivity(backHome);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
*/
    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        double x = sensorEvent.values[SensorManager.DATA_X];
        if (Math.abs(x - lastX) > 1.0) {
            mCurrentDirection = (int) x;
            locData = new MyLocationData.Builder()
                    .accuracy(mCurrentAccracy)
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(mCurrentDirection).latitude(mCurrentLat)
                    .longitude(mCurrentLon).build();
            mBaiduMap.setMyLocationData(locData);
        }
        lastX = x;

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    /*
    * 监听器
    * */
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.getintoreturn:
                Intent mhrintent=new Intent(getintoclassActivity.this,sign_inActivity.class);
                startActivity(mhrintent);
                finish();
                break;
            case R.id.getinclass:
                getin();
        }
    }



    public void getin(){
        MyApplication myApplication1=(MyApplication)getApplication();
        Joinclass joinclass=new Joinclass();
        String a=joinclass.joining(myApplication1.getClassname(),myApplication1.getId(),myApplication1.getName(),Latitude,Longitude);
        Log.i("hahhahah", "加入教室访问信息：" + a);
        if(a.equals(""))
        {
            Toast.makeText(getintoclassActivity.this,"获取数据失败", Toast.LENGTH_LONG).show();
        }
        else
        {
            String ack[]=a.split("/");
            if(ack[0].equals("0"))
            {
                Toast.makeText(getintoclassActivity.this,ack[1], Toast.LENGTH_LONG).show();
            }
            else if(ack[0].equals("1"))
            {
                Toast.makeText(getintoclassActivity.this,ack[1], Toast.LENGTH_LONG).show();
            }
        }

    }
    /**
     * 定位SDK监听函数
     */
    public class MyLocationListenner implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            // map view 销毁后不在处理新接收的位置
            if (location == null || mMapView == null) {
                return;
            }
            mCurrentLat = location.getLatitude();
            mCurrentLon = location.getLongitude();
            mCurrentAccracy = location.getRadius();
            locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(mCurrentDirection).latitude(location.getLatitude())
                    .longitude(location.getLongitude()).build();
            mBaiduMap.setMyLocationData(locData);
            if (isFirstLoc) {
                isFirstLoc = false;
                Latitude=location.getLatitude();
                Longitude=location.getLongitude();
                LatLng ll = new LatLng(Latitude,
                        Longitude);
                MapStatus.Builder builder = new MapStatus.Builder();
                builder.target(ll).zoom(13.0f);
                mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
            }
        }

        public void onReceivePoi(BDLocation poiLocation) {
        }
    }

    @Override
    protected void onPause() {
        mMapView.onPause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        mMapView.onResume();
        super.onResume();
        //为系统的方向传感器注册监听器

    }

    @Override
    protected void onStop() {
        //取消注册传感器监听
        if( mSensorManager!=null) {
            mSensorManager.unregisterListener(this);
        }
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        // 退出时销毁定位
        mLocClient.stop();
        // 关闭定位图层
        mBaiduMap.setMyLocationEnabled(false);
        mMapView.onDestroy();
        mMapView = null;
        super.onDestroy();
    }

    public class Joinclass {
        private static final String TAG = "NetUtil";
        /**
         * 使用POST提交方式
         * @param
         * @param
         * @return
         */
        public  String joining(String calssname, String userid,String username,double Latitude,double Longitude) {
            HttpURLConnection conn = null;
            try {
                URL url =  new URL("http://"+getApplication().getString(R.string.serverid)+"/class/joining.php");
                conn = (HttpURLConnection) url.openConnection();

                conn.setRequestMethod("GET");//GET和POST必须全大写
                conn.setConnectTimeout(10000);//连接的超时时间
                conn.setReadTimeout(5000);//读数据的超时时间
                conn.setDoOutput(true);//必须设置此方法  允许输出
                //	conn.setRequestProperty("Content-Length", 234);//设置请求消息头  可以设置多个

                //post请求的参数
                String data = "classname="+calssname+"&userid="+userid+"&username="+username+"&Latitude="+Latitude+"&Longitude="+Longitude;
                OutputStream out = conn.getOutputStream();
                out.write(data.getBytes());
                out.flush();
                out.close();

                int responseCode = conn.getResponseCode();
                if(responseCode==200){
                    //访问成功，通过流取的页面的数据信息
                    InputStream is = conn.getInputStream();
                    String status = getStringFromInputStream(is);
                    Log.i(TAG, "加入教室访问信息：" + status);
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
