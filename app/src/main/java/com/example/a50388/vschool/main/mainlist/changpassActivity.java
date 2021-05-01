package com.example.a50388.vschool.main.mainlist;

import android.content.Context;
import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.a50388.vschool.MainActivity;
import com.example.a50388.vschool.MyApplication;
import com.example.a50388.vschool.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public
class changpassActivity extends AppCompatActivity implements View.OnClickListener{
    private ImageView IVreturnhome;
    private Button changepass;
    private EditText oldpass,newpass1,newpass2;
    @Override
    protected
    void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changpass);

        StrictMode.setThreadPolicy(new
                StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());
        StrictMode.setVmPolicy(
                new StrictMode.VmPolicy.Builder().detectLeakedSqlLiteObjects().detectLeakedClosableObjects().penaltyLog().penaltyDeath().build());



        changepass=findViewById(R.id.changepass);
        changepass.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                oldpass=findViewById(R.id.oldpass);
                newpass1=findViewById(R.id.newpass1);
                newpass2=findViewById(R.id.newpass2);
                if(oldpass.getText()==null){
                    Toast.makeText(getApplicationContext(), "请输入旧密码", Toast.LENGTH_LONG).show();
                }
                else if(!newpass1.getText().toString().equals(newpass2.getText().toString()))
                {
                    Toast.makeText(getApplicationContext(), "两次密码不一致", Toast.LENGTH_LONG).show();
                }
                else {
                    MyApplication myapp=new MyApplication();
                    Netchange netchange=new Netchange();
                    String res=netchange.changing(myapp.getId(),oldpass.getText().toString(),newpass1.getText().toString());
                    if(res.equals("succ"))
                    {
                        Toast.makeText(getApplicationContext(), "修改成功", Toast.LENGTH_LONG).show();
                        Intent intet = new Intent(changpassActivity.this,MainActivity.class);
                        startActivity(intet);
                        finish();
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(), "修改失败", Toast.LENGTH_LONG).show();
                    }

                }

            }

        });
        intn();
    }
    private void intn() {
        IVreturnhome = (ImageView) findViewById(R.id.changhomereturn);
        IVreturnhome.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.changhomereturn:
                Intent returnintent = new Intent(changpassActivity.this, MainActivity.class);
                startActivity(returnintent);
                finish();
                break;
        }
    }




    public class Netchange {
        private static final String TAG = "NetUtil";
        private Context resources;

        /**
         * 使用POST提交方式
         *
         * @param username
         * @param password
         * @return
         */
        public String changing(String id,String oldpass,String newpass) {
            HttpURLConnection conn = null;
            try {
                URL url = new URL("http://"+getApplication().getString(R.string.serverid)+"/cashbook/changepass.php");
                conn = (HttpURLConnection) url.openConnection();

                conn.setRequestMethod("GET");//GET和POST必须全大写
                conn.setConnectTimeout(10000);//连接的超时时间
                conn.setReadTimeout(5000);//读数据的超时时间
                conn.setDoOutput(true);//必须设置此方法  允许输出
                //	conn.setRequestProperty("Content-Length", 234);//设置请求消息头  可以设置多个

                //post请求的参数
                String data = "username=" +id+"&oldpass="+oldpass+"&newpass="+newpass;
                OutputStream out = conn.getOutputStream();
                out.write(data.getBytes());
                out.flush();
                out.close();

                int responseCode = conn.getResponseCode();

                if (responseCode == 200) {
                    //访问成功，通过流取的页面的数据信息
                    InputStream is = conn.getInputStream();
                    String status = getStringFromInputStream(is);
                    Log.i(TAG, "访问失败：2" + status);
                    return status;
                } else {
                    Log.i(TAG, "访问失败：" + responseCode);
                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (conn != null) {
                    conn.disconnect();//释放链接
                }

            }
            return null;

        }

        /**
         * 通过字节输入流返回一个字符串信息
         *
         * @param is
         * @return
         * @throws IOException
         */
        private  String getStringFromInputStream(InputStream is) throws IOException {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = is.read(buffer)) != -1) {
                baos.write(buffer, 0, len);
            }
            is.close();
            String status = baos.toString("utf-8");// 把流中的数据转换成字符串, 采用的编码是: utf-8
            baos.close();
            int l = status.length();
            return status;
        }

        public Context getResources() {
            return resources;
        }
    }


}
