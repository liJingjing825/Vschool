package com.example.a50388.vschool.firstpage;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.a50388.vschool.MainActivity;
import com.example.a50388.vschool.R;



public class registerActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btregister,strCode;
    private ImageView returnImage;
    private EditText editPerson,editCode,editCode2,editname,editID;//用户名、密码输入框
    private String currentPhone,currentPassword,currentPassword2,currentname,currentID;//用于加载输入完成后的用户名、密码

    @Override
    protected void  onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        init();

        register();
    }



    private void register() {

        btregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public
            void onClick(View v) {
                currentPhone=editPerson.getText().toString().trim();
                currentPassword=editCode.getText().toString().trim();
                currentPassword2=editCode2.getText().toString().trim();
                currentname=editname.getText().toString().trim();
                currentID=editID.getText().toString().trim();
                if (TextUtils.isEmpty(currentPhone)){
                    Toast.makeText(getApplicationContext(),"账号不能为空",Toast.LENGTH_SHORT).show();
                    return;
                }else if(TextUtils.isEmpty(currentPassword)){
                    Toast.makeText(getApplicationContext(), "密码不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }else if (currentPassword2.equals(currentPassword)==false){
                    Toast.makeText(getApplicationContext(), "两次密码不一样请确认", Toast.LENGTH_SHORT).show();
                    return;
                }
                final ProgressDialog pd=new ProgressDialog(registerActivity.this);//等待动画
                pd.setMessage("等待登录……");//显示等待信息
                pd.show();//显示等待条
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String status = NetUtilregister.loginOfPost(currentPhone, currentname,currentID,currentPassword);
                        Log.i("adsgag",status);
                        if ("3001".equals(status) ) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Log.i("adsgag","dfgsd");
                                    Intent intent2 = new Intent(registerActivity.this, MainActivity.class);
                                    startActivity(intent2);
                                    finish();
                                    pd.dismiss();//消失等待条
                                }
                            });
                        }else if ("3002".equals(status)){
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(registerActivity.this,"该账号已有", Toast.LENGTH_LONG).show();
                                    pd.dismiss();//消失等待条
                                }
                            });
                        }else if ("3003".equals(status)){
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(registerActivity.this,"未注册成功", Toast.LENGTH_LONG).show();
                                    pd.dismiss();//消失等待条
                                }
                            });
                        }
                    }
                }).start();
            }
        });
    }


    private void init() {
        returnImage=(ImageView) findViewById(R.id.returnenter);
        returnImage.setOnClickListener(this);

        editPerson=(EditText)findViewById(R.id.number);
        editCode=(EditText)findViewById(R.id.rpassword);
        editCode2=(EditText)findViewById(R.id.rpassword2);
        editID=(EditText)findViewById(R.id.ruserID);
        editname=(EditText)findViewById(R.id.rname);

        btregister=(Button)findViewById(R.id.btregister);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.returnenter:
                Intent intent1=new Intent(registerActivity.this,enterActivity.class);
                startActivity(intent1);
                finish();
                break;
        }
    }
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intentKB=new Intent(registerActivity.this,enterActivity.class);
            startActivity(intentKB);
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
