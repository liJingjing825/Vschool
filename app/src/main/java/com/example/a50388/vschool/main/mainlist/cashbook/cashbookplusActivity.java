package com.example.a50388.vschool.main.mainlist.cashbook;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a50388.vschool.R;

import java.util.Calendar;

public
class cashbookplusActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText ETshowmoney,ETmessage;
    private Spinner userdirecton;
    private TextView time;
    private Button BTsure;
    private ImageView cbreturn;
    String times,money,message,accountNo,timedata;
    private Intent  careintent;

    @Override
    protected
    void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cashbookplus);
        intn();
        getphonetime();
        accountNo=getIntent().getStringExtra("accountNo");

    }

    private
    void getphonetime() {
        Calendar calendar = Calendar.getInstance();
        String year = String.valueOf(calendar.get(Calendar.YEAR));
        String month = String.valueOf(calendar.get(Calendar.MONTH) + 1);
        String day = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
        times=year+"-"+month+"-"+day;
        timedata=year+month+day;
        time.setText(times);
    }

    private void intn() {
        ETshowmoney = (EditText)findViewById(R.id.cashbookmoney);
        time = (TextView)findViewById(R.id.cashbookday);
        ETmessage = (EditText)findViewById(R.id.cashbookmessage);
        BTsure = (Button)findViewById(R.id.abpsure);
        BTsure.setOnClickListener(this);
        cbreturn = (ImageView)findViewById(R.id.cashbookreturn);
        cbreturn.setOnClickListener(this);
        careintent = new Intent(cashbookplusActivity.this,cashbookActivity.class);
    }


    @Override
    public
    void onClick(View v) {
        switch (v.getId()){
            case R.id.cashbookreturn:

                startActivity(careintent);
                finish();
                break;
            case R.id.abpsure:
                post();
                break;

        }

    }

    private
    void post() {
        money = ETshowmoney.getText().toString();
        message=ETmessage.getText().toString();
        Log.i("xvb",money+message+accountNo);
        if (TextUtils.isEmpty(money)){
            Toast.makeText(cashbookplusActivity.this,"请填写金额！",Toast.LENGTH_SHORT).show();
            return;
        }else if(TextUtils.isEmpty(message)){
            Toast.makeText(cashbookplusActivity.this, "请填写详细信息，方便回忆", Toast.LENGTH_SHORT).show();
            return;
        }
        final ProgressDialog pd=new ProgressDialog(cashbookplusActivity.this);//等待动画
        pd.setMessage("等待登录……");//显示等待信息
        pd.show();//显示等待条
        //后台请求
        new Thread(new Runnable() {
            @Override
            public void run() {
                NetUtilcashbook netUtilcashbook=new NetUtilcashbook();
                String status = netUtilcashbook.loginOfPost(accountNo, money,message,timedata);
                Log.i("ds",timedata);
                Log.i("fghdg",status);
                if ("plustrue".equals(status) ){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            startActivity(careintent);
                            finish();
                            pd.dismiss();//消失等待条
                        }
                    });
                }else if ("plusfalse".equals(status) ){
                    runOnUiThread(
                            new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(cashbookplusActivity.this,"添加失败", Toast.LENGTH_LONG).show();
                                    pd.dismiss();
                                }
                            });
                }
            }
        }).start();
    }
}
