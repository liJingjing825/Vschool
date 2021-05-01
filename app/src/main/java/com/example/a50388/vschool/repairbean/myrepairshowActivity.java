package com.example.a50388.vschool.repairbean;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a50388.vschool.MyApplication;
import com.example.a50388.vschool.R;

import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SpeechUtility;
import com.iflytek.cloud.SynthesizerListener;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by 50388 on 2018/8/18.
 * 点开后的界面
 */

public  class myrepairshowActivity extends Activity {
    private String str,details,res,mark,site,number,housenumber,speaks,tellphone;
    private TextView TVmessage,deleteme;
    private ImageView IVmessage,rrAreturn;
    private SpeechSynthesizer mTts;
    private TextView metext1,metext2,metext3,metext4;
    private Netmyrepair netmyrepair=new Netmyrepair();
    private char[] h1;
    @Override
    protected
    void onCreate(Bundle savedInstanceState) {
        // TODO: Implement this method
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myrepairshow);



        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectLeakedSqlLiteObjects().detectLeakedClosableObjects().penaltyLog().penaltyDeath().build());




        MyApplication myApplication=(MyApplication)getApplication();
        String newmess=netmyrepair.getmess(myApplication.getId());
        if(!newmess.equals("")&&!newmess.equals("0"))
        {
            String [] arr=newmess.split(" ");
            number=arr[0];
            mark=arr[1];
            res=arr[2];
            site=arr[3];
            tellphone=arr[4];
            details=arr[5];
            str=arr[6];

        }



        IVmessage=(ImageView)findViewById(R.id.meageimage);  //图片
        rrAreturn=(ImageView)findViewById(R.id.myrepairreturn);  //返回按钮
        rrAreturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public
            void onClick(View v) {
                Intent irrAreturn=new Intent(myrepairshowActivity.this,reportrepairsActivity.class);
                startActivity(irrAreturn);
                finish();
            }
        });
        deleteme=(TextView) findViewById(R.id.deleteme);
        deleteme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(netmyrepair.deleteme(number).equals("1"))
                {

                    Intent irrAreturn=new Intent(myrepairshowActivity.this,reportrepairsActivity.class);
                    startActivity(irrAreturn);
                    Toast.makeText(getApplicationContext(), "删除成功", Toast.LENGTH_SHORT).show();
                    finish();
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "删除失败", Toast.LENGTH_SHORT).show();
                }

            }
        });


      /*  str=getIntent().getStringExtra("Image");
        details=getIntent().getStringExtra("details");
        res=getIntent().getStringExtra("res");
        site=getIntent().getStringExtra("site");
        number=getIntent().getStringExtra("number");
        housenumber=getIntent().getStringExtra("housenumber");
        tellphone=getIntent().getStringExtra("tellphone");
        mark=getIntent().getStringExtra("mark");
*/

        TVmessage=(TextView)findViewById(R.id.meagedetails) ;
        TVmessage.setText(details);
        metext1=(TextView)findViewById(R.id.metext1);
        metext1.setText(tellphone);
        metext2=(TextView)findViewById(R.id.metext2);
        metext2.setText(res);
        metext3=(TextView)findViewById(R.id.metext3);
        metext3.setText(site);
        metext4=(TextView)findViewById(R.id.metext4);
        metext4.setText(mark);





       new Thread(){
            @Override
            public void run() {
                try {
                    Log.i("aaaaaaaaaaa","http://"+getApplication().getString(R.string.serverid)+"/repair"+str.substring(1));
                    // 将图片地址转化为URL对象
                    URL picUrl = new URL("http://"+getApplication().getString(R.string.serverid)+"/repair"+str.substring(1));
                    // 获取连接网络的对象（HTTP协议）
                    HttpURLConnection connection = (HttpURLConnection) picUrl.openConnection();
                    // 设置连接超时时间，5秒
                    connection.setConnectTimeout(5000);
                    // 设置连接获取输入流
                    connection.setDoInput(true);
                    // 设置连接使用缓存
                    connection.setUseCaches(true);
                    // 连接网络，貌似可写可不写
                    connection.connect();
                    // 获取连接后的输入流
                    InputStream is = connection.getInputStream();
                    // 将该输入流转化为Bitmap对象
                    final Bitmap bitmap = BitmapFactory.decodeStream(is);
                    // 这里是子线程，需要回归主线程更新UI
                    runOnUiThread(new Runnable() {
                        public void run() {
                            // 将Bitmap对象转换为Drawable对象
                            Drawable pic = new BitmapDrawable(bitmap);
                            // 设置rl_content背景
                            IVmessage.setImageDrawable(pic);
                        }
                    });
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }


        }.start();

    }

    public class Netmyrepair {
        private static final String TAG = "NetUtil";
        /**
         * 使用POST提交方式
         * @param
         * @param
         * @return
         */
        public  String deleteme(String messid) {
            HttpURLConnection conn = null;
            try {
                URL url =  new URL("http://"+getApplication().getString(R.string.serverid)+"/repair/deletemess.php");

                conn = (HttpURLConnection) url.openConnection();

                conn.setRequestMethod("GET");//GET和POST必须全大写
                conn.setConnectTimeout(10000);//连接的超时时间
                conn.setReadTimeout(5000);//读数据的超时时间
                conn.setDoOutput(true);//必须设置此方法  允许输出
                //	conn.setRequestProperty("Content-Length", 234);//设置请求消息头  可以设置多个

                //post请求的参数
                String data = "messid="+messid;
                OutputStream out = conn.getOutputStream();
                out.write(data.getBytes());
                out.flush();
                out.close();

                int responseCode = conn.getResponseCode();
                if(responseCode==200){
                    //访问成功，通过流取的页面的数据信息
                    InputStream is = conn.getInputStream();
                    String status = getStringFromInputStream(is);
                    Log.i(TAG, "asdasd："+status);
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
         *
         * */

        public  String getmess(String userid) {
            HttpURLConnection conn = null;
            try {
                URL url =  new URL("http://"+getApplication().getString(R.string.serverid)+"/repair/getmess.php");

                conn = (HttpURLConnection) url.openConnection();

                conn.setRequestMethod("GET");//GET和POST必须全大写
                conn.setConnectTimeout(10000);//连接的超时时间
                conn.setReadTimeout(5000);//读数据的超时时间
                conn.setDoOutput(true);//必须设置此方法  允许输出
                //	conn.setRequestProperty("Content-Length", 234);//设置请求消息头  可以设置多个

                //post请求的参数
                //   String data = "username="+username+"&password="+password;
                String data = "userid="+userid;
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

