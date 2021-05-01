package com.example.a50388.vschool.main.mainlist.address;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class addressActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView IVreturnhome;
    public  String ack[];
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);

        StrictMode.setThreadPolicy(new
                StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());
        StrictMode.setVmPolicy(
                new StrictMode.VmPolicy.Builder().detectLeakedSqlLiteObjects().detectLeakedClosableObjects().penaltyLog().penaltyDeath().build());

        list();
        intn();


    }
    private void list() {
        MyApplication myapp=(MyApplication)getApplication();

        Netcash a=new Netcash();
        String m=a.getuser(myapp.getClassname());
        if(m!=null)
        {
            ack = m.split("/");
        }
        else
        {
            Toast.makeText(getApplicationContext(), "获取数据失败", Toast.LENGTH_LONG).show();
            return;
        }


        int[] mess=new int[]{R.drawable.email_ico};
        int[] phone=new int[]{R.drawable.cashphone};
        List<Map<String,Object>> listitem=new ArrayList<Map<String, Object>>();
        for (int i=0;i<(ack.length+1)/3;i++){
            Map<String,Object> map=new HashMap<String, Object>();
            map.put("name",ack[3*i]);
            map.put("userid",ack[3*i+1]);
            map.put("userphone",ack[3*i+2]);
            map.put("imagemess",mess[0]);
            map.put("imagephone",phone[0]);
            listitem.add(map);
        }
        /*
        * 重写SimpleAdapter类
        * */
        MyAdapter adapter=new MyAdapter(this,listitem,R.layout.addresslist,new String[]{"name","userid","userphone","imagemess","imagephone"},
                new int[]{R.id.tv_name,R.id.tv_phone,R.id.tv_id,R.id.tv_Image,R.id.tv_Image2},ack);
        ListView listView=(ListView)findViewById(R.id.userlist);
        listView.setAdapter(adapter);

       /* listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Map<String,Object> map= (Map<String, Object>) parent.getItemAtPosition(position);
                switch (position){
                    case 0:
                        Intent naintent=new Intent(MainActivity.this,mydynamicActivity.class);
                        startActivity(naintent);
                        finish();
                        break;
                    case 1:
                        Intent cbintent=new Intent(MainActivity.this,cashbookActivity.class);
                        startActivity(cbintent);
                        //  finish();
                        break;
                    case 2:
                        Intent plintent=new Intent(MainActivity.this,planActivity.class);
                        startActivity(plintent);
                        finish();
                        break;
                    case 3:
                        Intent syintent=new Intent(MainActivity.this,syllabusActivity.class);
                        startActivity(syintent);
                        finish();
                        break;
                    case 4:
                        Intent mcintent=new Intent(MainActivity.this,myclassActivity.class);
                        startActivity(mcintent);
                        finish();
                        break;
                    case 5:
                        Intent hfintent=new Intent(MainActivity.this,help_feedbackActivity.class);
                        startActivity(hfintent);
                        finish();
                        break;

                }
            }
        });*/

    }

    private void intn() {
        IVreturnhome = (ImageView) findViewById(R.id.cashbookhomereturn);
        IVreturnhome.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cashbookhomereturn:
                Intent returnintent = new Intent(addressActivity.this, MainActivity.class);
                startActivity(returnintent);
                finish();
                break;
        }
    }



    public class Netcash {
        private static final String TAG = "NetUtil";
        private Context resources;

        /**
         * 使用POST提交方式
         *
         * @param username
         * @param password
         * @return
         */
        public String getuser(String classname) {
            HttpURLConnection conn = null;
            try {
                URL url = new URL("http://"+getApplication().getString(R.string.serverid)+"/cashbook/cashbook.php");
                conn = (HttpURLConnection) url.openConnection();

                conn.setRequestMethod("GET");//GET和POST必须全大写
                conn.setConnectTimeout(10000);//连接的超时时间
                conn.setReadTimeout(5000);//读数据的超时时间
                conn.setDoOutput(true);//必须设置此方法  允许输出
                //	conn.setRequestProperty("Content-Length", 234);//设置请求消息头  可以设置多个

                //post请求的参数
                String data = "classname=" + classname;
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
    public class MyAdapter extends SimpleAdapter {

        Context context;
        List<? extends Map<String, ?>> data;
        int resource;
        String[] from;
        int[] to;
        String ack[];
        private LayoutInflater mInflater;

        public MyAdapter(Context context, List<? extends Map<String, ?>> data,
            int resource, String[] from, int[] to,String ack[]) {
                super(context, data, resource, from, to);
                this.mInflater = LayoutInflater.from(context);
                this.context = context;
                this.data = data;
                this.resource = resource;
                this.from = from;
                this.to = to;
                this.ack=ack;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = mInflater.inflate(resource, null);
            for (int i = 0; i < from.length; i++) {//备注1
                if (convertView.findViewById(to[i]) instanceof ImageView) {
                    ImageView iv = (ImageView) convertView.findViewById(to[i]);
                    iv.setBackgroundResource((Integer) data.get(position).get(
                            from[i]));
                } else if (convertView.findViewById(to[i]) instanceof TextView) {
                    TextView tv = (TextView) convertView.findViewById(to[i]);
                    tv.setText((String) data.get(position).get(from[i]));
                }
            }
            addListener(convertView, position);

            return convertView;
        }

        public void addListener(View convertView, int arg) {
            final int arg2 = arg;
            ((ImageView) convertView.findViewById(R.id.tv_Image)).setOnClickListener(
                    new View.OnClickListener() {
                        public void onClick(View v) {
                            Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:"+ack[3*arg2+2]));
                            intent.putExtra("sms_body", "");
                            startActivity(intent);
                        }
                    });
            ((ImageView) convertView.findViewById(R.id.tv_Image2)).setOnClickListener(
                    new View.OnClickListener() {
                        public void onClick(View v) {
                            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+ack[3*arg2+2]));
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);

                        }
                    });
        }
    }

}

