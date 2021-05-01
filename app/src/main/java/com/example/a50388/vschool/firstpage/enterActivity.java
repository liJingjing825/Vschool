package com.example.a50388.vschool.firstpage;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a50388.vschool.MainActivity;
import com.example.a50388.vschool.MyApplication;
import com.example.a50388.vschool.R;
import com.example.a50388.vschool.main.homepage.sign.buildingclassActivity;

import java.util.ArrayList;
import java.util.List;


public class enterActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView textViewR;//注册按钮
    private Button btn;//登录按钮
    private EditText editPerson,editCode;//用户名、密码输入框
    private String currentUserName,currentPassword;
    private Intent intent2;//用于加载输入完成后的用户名、密码
    private String type="user";
    private MyApplication myApplication=new MyApplication();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_enter);
        RadioGroup radioGroup = (RadioGroup)findViewById(R.id.rg);

        myApplication.setType("user");
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group,  int checkedId) {
                if (checkedId == R.id.rb1) //rb3设定为正确答案
                {
                    type="user";
                    myApplication.setType("user");
                }
                else if(checkedId == R.id.rb2)
                {
                    type="admin";
                    myApplication.setType("admin");
                }

            }
        });

        inten();
        init();


    }

    private void inten() {
        intent2 = new Intent(enterActivity.this, MainActivity.class);
    }

    private void init() {
        textViewR=(TextView)findViewById(R.id.tv_register);
        textViewR.setOnClickListener(this);
        btn=(Button)findViewById(R.id.entergo);
        btn.setOnClickListener(this);
        editPerson=(EditText)findViewById(R.id.username);
        editCode=(EditText)findViewById(R.id.password);
    }
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_register:
                Intent intent1=new Intent(enterActivity.this,registerActivity.class);
                startActivity(intent1);
                finish();
                break;
            case R.id.entergo:
                login();
                break;
        }

    }
    private void login() {
        currentUserName=editPerson.getText().toString().trim();
        currentPassword=editCode.getText().toString().trim();
        if (TextUtils.isEmpty(currentUserName)){
            Toast.makeText(enterActivity.this,"手机号不能为空！",Toast.LENGTH_SHORT).show();
            return;
        }else if(TextUtils.isEmpty(currentPassword)){
            Toast.makeText(enterActivity.this, "密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        final ProgressDialog pd=new ProgressDialog(enterActivity.this);//等待动画
        pd.setMessage("等待登录……");//显示等待信息
        pd.show();//显示等待条
         //后台请求
        new Thread(new Runnable() {
            @Override
            public void run() {
                String status = NetUtil.loginOfPost(currentUserName, currentPassword,type);

               final String ack[]=status.split("/");
                Log.i("aa", "ack[0]:" + ack[0]);
                if (ack[0].equals("2001")){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            MyApplication app=(MyApplication)getApplication();
                            app.setId(editPerson.getText().toString().trim());
                            app.setName(ack[1]);
                            app.setClassname(ack[2]);
                            app.setType(type);
                            startActivity(intent2);
                            finish();
                            pd.dismiss();//消失等待条
                        }
                    });
                }else if ("2002".equals(ack[0]) ){
                    runOnUiThread(
                            new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(enterActivity.this,"密码错误", Toast.LENGTH_LONG).show();
                                    pd.dismiss();
                                }
                            });
                }else {
                    runOnUiThread(
                            new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(enterActivity.this, "账号不存在或者账号错误", Toast.LENGTH_LONG).show();
                                    pd.dismiss();
                                }
                            });
                }
            }
        }).start();

    }






}
