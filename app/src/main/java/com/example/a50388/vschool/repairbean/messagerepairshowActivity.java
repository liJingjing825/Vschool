package com.example.a50388.vschool.repairbean;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
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

public  class messagerepairshowActivity extends Activity {
    private String str,details,res,mark,site,number,housenumber,tellphone,speaks;
    private TextView TVmessage;
    private ImageView IVmessage,rrAreturn;
    private SpeechSynthesizer mTts;
    private TextView deleteadmin;//删除该条报修的按钮
    private TextView messtext1,messtext2,messtext3,messtext4;
    private Button setalredy;  //将该条报修设置为已修理
    private Thread thread;
    private char[] h1;
    @Override
    protected
    void onCreate(Bundle savedInstanceState) {
        // TODO: Implement this method
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messagerepairshow);
        //图片链接


        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());
        StrictMode.setVmPolicy(
                new StrictMode.VmPolicy.Builder().detectLeakedSqlLiteObjects().detectLeakedClosableObjects().penaltyLog().penaltyDeath().build());





        setadmin();
        speak();

        IVmessage=(ImageView)findViewById(R.id.messageimage);
        rrAreturn=(ImageView)findViewById(R.id.rrAreturn);
        rrAreturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public
            void onClick(View v) {
                Intent irrAreturn=new Intent(messagerepairshowActivity.this,reportrepairsActivity.class);
                startActivity(irrAreturn);
                finish();
            }
        });
        str=getIntent().getStringExtra("Image");
        details=getIntent().getStringExtra("details");  //具体描述
        res=getIntent().getStringExtra("res");
        site=getIntent().getStringExtra("site");
        number=getIntent().getStringExtra("number");
        housenumber=getIntent().getStringExtra("housenumber");
        tellphone=getIntent().getStringExtra("tellphone");
        mark=getIntent().getStringExtra("mark");//是否修复
        h1=new char[tellphone.length()*2];
        for (int i=0;i<tellphone.length();i++){
            h1[2*i]=tellphone.charAt(i);
            h1[2*i+1]=' ';
        }



        TVmessage=(TextView)findViewById(R.id.messagedetails) ;
        TVmessage.setText(details);
        messtext1=(TextView)findViewById(R.id.messtext1);
        messtext1.setText(tellphone);
        messtext2=(TextView)findViewById(R.id.messtext2);
        messtext2.setText(res);
        messtext3=(TextView)findViewById(R.id.messtext3);
        messtext3.setText(site);
        messtext4=(TextView)findViewById(R.id.messtext4);
        messtext4.setText(mark);


        speaks=getIntent().getStringExtra("speak");
        //Log.i("aaaaaaaaaaa","报修的物品为"+site+new String(h1)+"的"+res+","+mark+",他的编号为"+number+"具体情况为"+details);
        if (speaks.equals("speak")){

            mTts.startSpeaking("报修的物品为"+res+"的"+site+","+mark+",他的联系方式为"+new String(h1)+"具体情况为"+details,listener);
        }else{
            mTts.startSpeaking("系统故障，请联系工作人员",listener);
        }

        thread=new Thread(){
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


        };
        thread.start();


    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if(keyCode==KeyEvent.KEYCODE_BACK){
            mTts.stopSpeaking();
      //      thread.stop();
            Intent irrAreturn=new Intent(messagerepairshowActivity.this,reportrepairsActivity.class);
            startActivity(irrAreturn);
            finish();
        }
        return true;
    }


    public void setadmin()
    {
        setalredy=(Button)findViewById(R.id.setalredy);
        deleteadmin=(TextView)findViewById(R.id.deleteadmin);
        MyApplication myApplication1=(MyApplication)getApplication();
        if(myApplication1.getType().equals("admin"))
        {
            deleteadmin.setVisibility(View.VISIBLE);
            setalredy.setVisibility(View.VISIBLE);
        }



        setalredy.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Netmess netmess=new Netmess();
            if(netmess.setmess(number).equals("1"))
            {
                Toast.makeText(getApplicationContext(), "设置成功", Toast.LENGTH_SHORT).show();
                messtext4.setText("已修");
                showExit();
            }
            else
            {
                Toast.makeText(getApplicationContext(), "设置成功", Toast.LENGTH_SHORT).show();
            }
            //finish();
        }
    });

        deleteadmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public
            void onClick(View v) {
                Netmess netmess=new Netmess();
                Log.i("asd", "asdasd："+number);
                if(netmess.deletemess(number).equals("1"))
                {
                    mTts.stopSpeaking();
                    Intent irrAreturn=new Intent(messagerepairshowActivity.this,reportrepairsActivity.class);
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

    }


    private void showExit(){
        new AlertDialog.Builder(this)
                .setTitle("带确定键的提示框")
                .setMessage("确定吗")
                .setPositiveButton("是", null)
                .setNegativeButton("否", null)
                .show();
    }



    private int speak() {
        SpeechUtility.createUtility(messagerepairshowActivity.this, "appid=5b9e7b10");
        //1.创建 SpeechSynthesizer 对象, 第二个参数：本地合成时传 InitListener
        mTts=  SpeechSynthesizer.createSynthesizer(messagerepairshowActivity.this, null);
        //2.合成参数设置
        mTts.setParameter(SpeechConstant.VOICE_NAME,"xiaoqi" );
        mTts.setParameter(SpeechConstant.SPEED, "40");//设置语速
        mTts.setParameter(SpeechConstant.VOLUME, "80");//设置音量，范围 0~100
        mTts.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD); //设置云端

        //开始合成

        return 0;
    }



    private SynthesizerListener listener =new SynthesizerListener() {

        @Override
        public void onSpeakResumed() {
            // TODO Auto-generated method stub

        }

        @Override
        public void onSpeakProgress(int arg0, int arg1, int arg2) {
            // TODO Auto-generated method stub

        }


        @Override
        public void onSpeakPaused() {
            // TODO Auto-generated method stub

        }

        @Override
        public void onSpeakBegin() {
            // TODO Auto-generated method stub

        }

        @Override
        public void onEvent(int arg0, int arg1, int arg2, Bundle arg3) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onCompleted(SpeechError arg0) {
            // TODO Auto-generated method stub
            //arg0.getPlainDescription(true);
        }

        @Override
        public void onBufferProgress(int arg0, int arg1, int arg2, String arg3) {
            // TODO Auto-generated method stub

        }
    };


    public class Netmess {
        private static final String TAG = "NetUtil";
        /**
         * 使用POST提交方式
         * @param
         * @param
         * @return
         */
        public  String deletemess(String messid) {
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
         * 用来判断当前老师是否已经创建了教室
         * */

        public  String setmess(String messid) {
            HttpURLConnection conn = null;
            try {
                URL url =  new URL("http://"+getApplication().getString(R.string.serverid)+"/repair/setmess.php");

                conn = (HttpURLConnection) url.openConnection();

                conn.setRequestMethod("GET");//GET和POST必须全大写
                conn.setConnectTimeout(10000);//连接的超时时间
                conn.setReadTimeout(5000);//读数据的超时时间
                conn.setDoOutput(true);//必须设置此方法  允许输出
                //	conn.setRequestProperty("Content-Length", 234);//设置请求消息头  可以设置多个

                //post请求的参数
                //   String data = "username="+username+"&password="+password;
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

