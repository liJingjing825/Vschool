package com.example.a50388.vschool.main.homepage.sign;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a50388.vschool.MainActivity;
import com.example.a50388.vschool.MyApplication;
import com.example.a50388.vschool.R;
import com.example.a50388.vschool.main.mainlist.address.addressActivity;

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

public class signclassActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView inclass,outclass,listin,listout;
    private String  [] in,out;
    private  MyAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signclass);
        setin();
        setout();
        intn();
    }


    public void setin(){
        inclass=(TextView)findViewById(R.id.innumber);
        Netsign netsign=new Netsign();
        MyApplication myApplication=(MyApplication)getApplication();
        String a=netsign.getin(myApplication.getSignclass());
        if(a.equals(""))
        {
            Toast.makeText(signclassActivity.this,"获取数据失败", Toast.LENGTH_SHORT).show();
        }
        else
        {
             in=a.split("/");
            if(in[0].equals("0"))
            {
                Toast.makeText(signclassActivity.this,"获取数据失败", Toast.LENGTH_SHORT).show();
            }
            else if(in[0].equals("1"))
            {
                inclass.setText(""+(in.length-1)/2);
            }
        }
    }

    public void setout(){
        outclass=(TextView)findViewById(R.id.outnumber);
        Netsign netsign=new Netsign();
        MyApplication myApplication=(MyApplication)getApplication();
        String a=netsign.getout(myApplication.getSignclass());
        if(a.equals(""))
        {
            Toast.makeText(signclassActivity.this,"获取数据失败", Toast.LENGTH_SHORT).show();
        }
        else
        {
            out=a.split("/");
            if(out[0].equals("0"))
            {
                Toast.makeText(signclassActivity.this,"获取数据失败", Toast.LENGTH_SHORT).show();
            }
            else if(out[0].equals("1"))
            {
                outclass.setText(""+(out.length-1)/2);
            }
        }
    }

    private void intn() {
            listin=(TextView) findViewById(R.id.listin);
            listin.setOnClickListener(this);
        listout=(TextView) findViewById(R.id.listout);
        listout.setOnClickListener(this);
    }


    public void onClick(View view) {
        switch (view.getId()){
            case R.id.signhomereturn:
                Intent reintent=new Intent(signclassActivity.this,MainActivity.class);
                startActivity(reintent);
                finish();
                break;
            case R.id.listin:
                list(in);
                break;
            case R.id.listout:
                list(out);
                break;
        }
    }
    private void list(String [] str) {
        MyApplication myapp=new MyApplication();
        int[] mess=new int[]{R.drawable.email_ico};
        int[] phone=new int[]{R.drawable.cashphone};
        List<Map<String,Object>> listitem=new ArrayList<Map<String, Object>>();
        for (int i=0;i<(str.length)/2;i++){
            Map<String,Object> map=new HashMap<String, Object>();
            map.put("name1",str[2*i+1]);
            map.put("userid",str[2*i+2]);
          //  map.put("userphone",ack[3*i+2]);
          //  map.put("imagemess",mess[0]);
          //  map.put("imagephone",phone[0]);
            listitem.add(map);
        }
        /*
         * 重写SimpleAdapter类
         * */
        if(adapter!=null)
        {
            adapter.clear();
        }
        adapter=new MyAdapter(this,listitem,R.layout.signclasslist,new String[]{"name1","userid"},
                new int[]{R.id.name1,R.id.userid},str);

        ListView listView=(ListView)findViewById(R.id.signlist);
        listView.setAdapter(adapter);

    }


    public class Netsign{
        private static final String TAG = "Netsign";
        private Context resources;

        /**
         * 使用POST提交方式
         * @param signclass
         * @return
         */
        public String getin(String signclass) {
            HttpURLConnection conn = null;
            try {
                URL url = new URL("http://"+getApplication().getString(R.string.serverid)+"/class/inclass.php");
                conn = (HttpURLConnection) url.openConnection();

                conn.setRequestMethod("GET");//GET和POST必须全大写
                conn.setConnectTimeout(10000);//连接的超时时间
                conn.setReadTimeout(5000);//读数据的超时时间
                conn.setDoOutput(true);//必须设置此方法  允许输出
                //	conn.setRequestProperty("Content-Length", 234);//设置请求消息头  可以设置多个

                //post请求的参数
                String data = "signclass=" + signclass;
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

            public String getout(String signclass) {
                HttpURLConnection conn = null;
                try {
                    URL url = new URL("http://"+getApplication().getString(R.string.serverid)+"/class/outclass.php");
                    conn = (HttpURLConnection) url.openConnection();

                    conn.setRequestMethod("GET");//GET和POST必须全大写
                    conn.setConnectTimeout(10000);//连接的超时时间
                    conn.setReadTimeout(5000);//读数据的超时时间
                    conn.setDoOutput(true);//必须设置此方法  允许输出
                    //	conn.setRequestProperty("Content-Length", 234);//设置请求消息头  可以设置多个

                    //post请求的参数
                    String data = "signclass=" + signclass;
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

        public void clear() {
            data.clear();
            notifyDataSetChanged();
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
         //   addListener(convertView, position);

            return convertView;
        }

        /*public void addListener(View convertView, int arg) {
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
        }*/
    }

}

