package com.example.a50388.vschool.repairbean;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.a50388.vschool.MyApplication;
import com.example.a50388.vschool.R;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;


public
class plusrepairsActivity extends AppCompatActivity {

    public final int TYPE_TAKE_PHOTO = 1;//Uri获取类型判断

    public final int CODE_TAKE_PHOTO = 1;//相机RequestCode
    private EditText ETres, ETsite, EThousenumber, ETdetails;
    private ImageView prpicture1;
    private String  housenumber, number, details, mark;
    private File file;
    private Button btplusture;
    private List<String> list;
    private ArrayAdapter<String> adapter;
    private Spinner typelist1,typelist2;

    private static String TAG;

    @Override
    protected
    void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plusrepair);
        gettype1();
        gettype2();
        inin();
    }

    public void gettype1(){

        List<String> list=new ArrayList<String>();
        list.add("宿舍");
        list.add("教室");
        list.add("公共设施");
        list.add("图书馆");
        list.add("餐厅");
        list.add("其他");


        /*新建适配器*/
        adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,list);

        /*adapter设置一个下拉列表样式，参数为系统子布局*/
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typelist1=(Spinner)findViewById(R.id.type1);
        /*spDown加载适配器*/
        typelist1.setAdapter(adapter);
    }

    public void gettype2(){


        List<String> list=new ArrayList<String>();
        list.add("桌椅板凳");
        list.add("门面");
        list.add("风扇");
        list.add("开关");
        list.add("空调");
        list.add("供水供暖");
        list.add("卫生间");
        list.add("其他");


        /*新建适配器*/
        adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,list);

        /*adapter设置一个下拉列表样式，参数为系统子布局*/
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typelist2=(Spinner)findViewById(R.id.type2);
        /*spDown加载适配器*/
        typelist2.setAdapter(adapter);
    }



    private void inin() {
        EThousenumber = (EditText) findViewById(R.id.cellphone);
        ETdetails = (EditText) findViewById(R.id.details);


        btplusture = (Button) findViewById(R.id.plusture);
        btplusture.setOnClickListener(new View.OnClickListener() {

            @Override
            public
            void onClick(View v) {
                housenumber = EThousenumber.getText().toString().trim();
              //  res = ETres.getText().toString().trim();
                details = ETdetails.getText().toString().trim();
                getnumber();
                mark = "未修";
                Log.i("housnumber", housenumber);

                Log.i("details", details);
                Log.i("number", number);
                /*if (TextUtils.isEmpty(res)) {
                    Toast.makeText(getApplicationContext(), "请提供物品", Toast.LENGTH_SHORT).show();
                    return;
                } else if (TextUtils.isEmpty(site)) {
                    Toast.makeText(getApplicationContext(), "请提供地点", Toast.LENGTH_SHORT).show();
                    return;
                } else*/ if (TextUtils.isEmpty(housenumber)) {
                    Toast.makeText(getApplicationContext(), "请提联系方式", Toast.LENGTH_SHORT).show();
                    return;
                } else if (TextUtils.isEmpty(housenumber)) {
                    Toast.makeText(getApplicationContext(), "请提供详细信息", Toast.LENGTH_SHORT).show();
                    return;
                } else if (prpicture1.getDrawable().getCurrent().getConstantState().equals(getResources().getDrawable(R.drawable.pluspicture).getConstantState())) {
                    Toast.makeText(getApplicationContext(), "请提供图片", Toast.LENGTH_SHORT).show();
                    return;
                }
                final ProgressDialog pd = new ProgressDialog(plusrepairsActivity.this);//等待动画
                pd.setMessage("等待添加……");//显示等待信息
                pd.show();//显示等待条
                new Thread(new Runnable() {
                    @Override
                    public
                    void run() {
                        String url2 = uploadFile("http://"+getApplication().getString(R.string.serverid)+ "/repair/update.php", file.getAbsolutePath());
                        MyApplication myApplication1=(MyApplication)getApplication();
                        NetUtilplusrepair netUtilplusrepair=new NetUtilplusrepair();
                        String status = netUtilplusrepair.loginOfPost(mark, typelist1.getSelectedItem().toString(), typelist2.getSelectedItem().toString(), housenumber, details, number, url2,myApplication1.getId());//status="ok"
                        Log.i("dfssdh",status);
                        if ("6".equals(status)) {//"ok"==status inn   string
                            runOnUiThread(new Runnable() {
                                @Override
                                public
                                void run() {
                                    Log.i("adsgag", "dfgsd");
                                    Intent intent2 = new Intent(plusrepairsActivity.this, reportrepairsActivity.class);
                                    startActivity(intent2);
                                    finish();
                                    pd.dismiss();//消失等待条
                                }
                            });
                            // TODO: Implement this method
                        }
                    }
                }).start();

                // TODO: Implement this method
            }


            public boolean isEmpty(EditText editext) {
                return TextUtils.isEmpty(editext.getText());
            }
        });
        prpicture1 = (ImageView) findViewById(R.id.prpicture1);
        prpicture1.setOnClickListener(new View.OnClickListener() {

            @Override
            public
            void onClick(View v) {
                applyWritePermission();
            }
        });

    }

    private void getnumber() {
        Calendar calendar = Calendar.getInstance();
        String year = String.valueOf(calendar.get(Calendar.YEAR));
        String month = String.valueOf(calendar.get(Calendar.MONTH) + 1);
        String day = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
        String hour = String.valueOf(calendar.get(Calendar.HOUR_OF_DAY));
        int min = 10;
        int max = 99;
        Random random = new Random();
        String num = String.valueOf(random.nextInt(max) % (max - min + 1) + min);
        number = year + month + day + hour + num;
    }


    private
    void useCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        file = new File(Environment.getExternalStorageDirectory().getAbsolutePath()
                + "/file/" + System.currentTimeMillis() + ".jpg");
        file.getParentFile().mkdirs();

        //改变Uri  com.xykj.customview.fileprovider注意和xml中的一致
        Uri uri = FileProvider.getUriForFile(this, "com.example.a50388.vschool.fileprovider", file);
        //添加权限
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        startActivityForResult(intent, 2);
    }

    public
    void applyWritePermission() {

        String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};

        if (Build.VERSION.SDK_INT >= 23) {
            int check = ContextCompat.checkSelfPermission(this, permissions[0]);
            // 权限是否已经 授权 GRANTED---授权  DINIED---拒绝
            if (check == PackageManager.PERMISSION_GRANTED) {
                //调用相机
                useCamera();
            } else {
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }
        } else {
            useCamera();
        }
    }

    @Override
    protected
    void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2) {
            //Log.e("TAG", "---------" + FileProvider.getUriForFile(this, "com.xykj.customview.fileprovider", file));
            //file.getAbsolutePath();
            prpicture1.setImageBitmap(BitmapFactory.decodeFile(file.getAbsolutePath()));

        }
    }

    @Override
    public
    void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                    @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            useCamera();
        } else {
            // 没有获取 到权限，从新请求，或者关闭app
            Toast.makeText(this, "需要存储权限", Toast.LENGTH_SHORT).show();
        }
    }

    private
    String uploadFile(String uploadUrl, String srcPath) {
        String end = "\r\n";
        String twoHyphens = "--";
        String boundary = "******";
        try {
            URL url = new URL(uploadUrl);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url
                    .openConnection();
            // 设置每次传输的流大小，可以有效防止手机因为内存不足崩溃
            // 此方法用于在预先不知道内容长度时启用没有进行内部缓冲的 HTTP 请求正文的流。
            httpURLConnection.setChunkedStreamingMode(128 * 1024);// 128K
            // 允许输入输出流
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setUseCaches(false);
            // 使用POST方法
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("Connection", "Keep-Alive");
            httpURLConnection.setRequestProperty("Charset", "UTF-8");
            httpURLConnection.setRequestProperty("Content-Type",
                    "multipart/form-data;boundary=" + boundary);

            DataOutputStream dos = new DataOutputStream(
                    httpURLConnection.getOutputStream());
            dos.writeBytes(twoHyphens + boundary + end);
            dos.writeBytes("Content-Disposition: form-data; name=\"uploadedfile\"; filename=\""
                    + srcPath.substring(srcPath.lastIndexOf("/") + 1)
                    + "\""
                    + end);
            dos.writeBytes(end);

            FileInputStream fis = new FileInputStream(srcPath);
            byte[] buffer = new byte[8192]; // 8k
            int count = 0;
            // 读取文件
            while ((count = fis.read(buffer)) != -1) {
                dos.write(buffer, 0, count);
            }
            fis.close();

            dos.writeBytes(end);
            dos.writeBytes(twoHyphens + boundary + twoHyphens + end);
            dos.flush();

            InputStream is = httpURLConnection.getInputStream();
            InputStreamReader isr = new InputStreamReader(is, "utf-8");
            BufferedReader br = new BufferedReader(isr);
            String result = br.readLine();
            dos.close();
            is.close();
            return result;

        } catch (Exception e) {
            e.printStackTrace();

        }
        return null;
    }
}

