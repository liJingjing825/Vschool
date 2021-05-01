package com.example.a50388.vschool.firstpage;

import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by 50388 on 2018/8/8.
 */

public
class NetUtilregister {
    private static final String TAG = "NetUtil";
    /**
     * 使用POST提交方式
     * @param username
     * @param password
     * @param userID
     * @param password
     * @return
     */
    public static String loginOfPost(String userphone, String username,String userID,String password) {
        HttpURLConnection conn = null;
        try {
            URL url =  new URL("http://dlplayer.cn/regse.php");
            conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("GET");//GET和POST必须全大写
            conn.setConnectTimeout(10000);//连接的超时时间
            conn.setReadTimeout(5000);//读数据的超时时间
            conn.setDoOutput(true);//必须设置此方法  允许输出
            //	conn.setRequestProperty("Content-Length", 234);//设置请求消息头  可以设置多个

            //post请求的参数
            String data = "userphone="+userphone+"&username="+username+"&userID="+userID+"&password="+password;
            Log.i("dsfg",data);
            OutputStream out = conn.getOutputStream();
            out.write(data.getBytes());
            out.flush();
            out.close();

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
    /**
     * 通过字节输入流返回一个字符串信息
     * @param is
     * @return
     * @throws IOException
     */
    private static String getStringFromInputStream(InputStream is) throws IOException {
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
