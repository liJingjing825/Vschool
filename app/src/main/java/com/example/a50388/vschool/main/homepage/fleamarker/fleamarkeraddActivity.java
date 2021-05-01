package com.example.a50388.vschool.main.homepage.fleamarker;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.example.a50388.vschool.MyApplication;
import com.example.a50388.vschool.R;
import com.example.a50388.vschool.repairbean.NetUtilplusrepair;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class fleamarkeraddActivity extends AppCompatActivity implements View.OnClickListener  {
    private EditText ETres, ETsite, EThousenumber, ETdetails;
    private ImageView prpicture1, plusrepairsreturn;
    private PopupWindow popupWindow;
    private View popupView;
    private TranslateAnimation animation;
    private Button btplusture;
    private String res, site, housenumber, number, details, mark;
    private Bitmap bitmap;
    private File tempFile;
    private Intent reintent;
    public static final int PHOTO_REQUEST_CAREMA = 1;// 拍照
    public static final int CROP_PHOTO = 2;

    private Uri imageUri;

    String host = "";//填写你的域名
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fleamakeradd);
        inin();
    }
    private void inin() {
        ETsite = (EditText) findViewById(R.id.repairsite);
        ETdetails = (EditText) findViewById(R.id.details);
        EThousenumber = (EditText) findViewById(R.id.repairhousenumber);
        prpicture1 = (ImageView) findViewById(R.id.prpicture1);
        prpicture1.setOnClickListener(this);
        plusrepairsreturn = (ImageView) findViewById(R.id.plusrepairsreturn);
        plusrepairsreturn.setOnClickListener(this);
        btplusture = (Button) findViewById(R.id.plusture);
        btplusture.setOnClickListener(this);
        reintent = new Intent(fleamarkeraddActivity.this, fleamarkerActivity.class);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.plusrepairsreturn:
                startActivity(reintent);
                finish();
                break;
            case R.id.prpicture1:
                openCamera(fleamarkeraddActivity.this);
                break;
            case R.id.plusture:
                plus();
                break;
        }
    }

    private void plus() {
        housenumber = EThousenumber.getText().toString().trim();
        res = ETres.getText().toString().trim();
        site = ETsite.getText().toString().trim();
        details = ETdetails.getText().toString().trim();
        Calendar calendar = Calendar.getInstance();
        String year = String.valueOf(calendar.get(Calendar.YEAR));
        String month = String.valueOf(calendar.get(Calendar.MONTH) + 1);
        String day = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
        String hour = String.valueOf(calendar.get(Calendar.HOUR_OF_DAY));
        int min = 10;
        int max = 99;
        Random random = new Random();
        String num = String.valueOf(random.nextInt(max) % (max - min + 1) + min);
        number = year + month + day + hour + site + num;
        mark = "1";
        Log.i("housnumber", housenumber);
        Log.i("res", res);
        Log.i("site", site);
        Log.i("number", number);
        if (TextUtils.isEmpty(res)) {
            Toast.makeText(getApplicationContext(), "请提供物品", Toast.LENGTH_SHORT).show();
            return;
        } else if (TextUtils.isEmpty(site)) {
            Toast.makeText(getApplicationContext(), "请提供地点", Toast.LENGTH_SHORT).show();
            return;
        } else if (TextUtils.isEmpty(housenumber)) {
            Toast.makeText(getApplicationContext(), "请提供门牌号", Toast.LENGTH_SHORT).show();
            return;
        } else if (TextUtils.isEmpty(housenumber)) {
            Toast.makeText(getApplicationContext(), "请提供详细信息", Toast.LENGTH_SHORT).show();
            return;
        } else if (prpicture1.getDrawable().getCurrent().getConstantState().equals(getResources().getDrawable(R.drawable.pluspicture).getConstantState())) {
            Toast.makeText(getApplicationContext(), "请提供图片", Toast.LENGTH_SHORT).show();
            return;
        }
        final ProgressDialog pd = new ProgressDialog(fleamarkeraddActivity.this);//等待动画
        pd.setMessage("等待添加……");//显示等待信息
        pd.show();//显示等待条
        new Thread(new Runnable() {
            @Override
            public void run() {
                String url2 = uploadFile(host + "/update.php",  tempFile.getAbsolutePath());
                MyApplication myApplication1=(MyApplication)getApplication();
                NetUtilplusrepair netUtilplusrepair=new NetUtilplusrepair();
                String status = netUtilplusrepair.loginOfPost(mark, res, site, housenumber, details, number,url2,myApplication1.getId());
                Log.i("adsgag", status);

                if ("6".equals(status)) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Log.i("adsgag", "dfgsd");
                            startActivity(reintent);
                            finish();
                            pd.dismiss();//消失等待条
                        }
                    });
                } else if ("7".equals(status)) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(fleamarkeraddActivity.this, "添加失败", Toast.LENGTH_LONG).show();
                            pd.dismiss();//消失等待条
                        }
                    });
                } else if ("7".equals(status)) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(fleamarkeraddActivity.this, "添加失败", Toast.LENGTH_LONG).show();
                            pd.dismiss();//消失等待条
                        }
                    });
                }
            }
        }).start();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    Intent intent = new Intent("com.android.camera.action.CROP");
                    intent.setDataAndType(imageUri, "image/*");
                    intent.putExtra("scale", true);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                    startActivityForResult(intent, 2); // 启动裁剪程序
                }
                break;
            case 2:
                if (resultCode == RESULT_OK) {
                    try {
                        final File file = new File(new URI(imageUri.toString()));
                        new Thread(new Runnable() {

                            @Override
                            public void run() {
                                String name = uploadFile(host + "update.php", file.getAbsolutePath());
                                String url = host + "upload/" + name;//Url地址

                                // TODO: Implement this method
                            }
                        }).start();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
        }
    }

    public void openCamera(Activity activity) {
        //獲取系統版本
        int currentapiVersion = android.os.Build.VERSION.SDK_INT;
        // 激活相机
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // 判断存储卡是否可以用，可用进行存储
        if (hasSdcard()) {
            SimpleDateFormat timeStampFormat = new SimpleDateFormat(
                    "yyyy_MM_dd_HH_mm_ss");
            String filename = timeStampFormat.format(new Date());
            tempFile = new File(Environment.getExternalStorageDirectory(),
                    filename + ".jpg");
            if (currentapiVersion < 24) {
                // 从文件中创建uri
                imageUri = Uri.fromFile(tempFile);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            } else {
                //兼容android7.0 使用共享文件的形式
                ContentValues contentValues = new ContentValues(1);
                contentValues.put(MediaStore.Images.Media.DATA, tempFile.getAbsolutePath());
                //检查是否有存储权限，以免崩溃

                imageUri = activity.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            }
        }
        // 开启一个带有返回值的Activity，请求码为PHOTO_REQUEST_CAREMA
        activity.startActivityForResult(intent, PHOTO_REQUEST_CAREMA);
    }

    /*
     * 判断sdcard是否被挂载
     */
    public static boolean hasSdcard() {
        return Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);
    }

    private String uploadFile(String uploadUrl, String srcPath) {
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

