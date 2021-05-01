package com.example.a50388.vschool.main.homepage.sign;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.a50388.vschool.MainActivity;
import com.example.a50388.vschool.R;

public class sign_inActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView IMreturn;
    private Button buildbtn,getintobtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        intn();
    }
    private void intn() {
        IMreturn=(ImageView)findViewById(R.id.signhomereturn);
        IMreturn.setOnClickListener(this);
        buildbtn=(Button)findViewById(R.id.build);
        buildbtn.setOnClickListener(this);
        getintobtn=(Button)findViewById(R.id.getinto);
        getintobtn.setOnClickListener(this);
    }


    public boolean OnKeyDown(int keyCode,KeyEvent event){
        if (keyCode==KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            Intent intent=new Intent();
            intent.setClass(sign_inActivity.this, MainActivity.class);
            startActivity(intent);
            sign_inActivity.this.finish();
        }
        return false;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.signhomereturn:
                Intent reintent=new Intent(sign_inActivity.this,MainActivity.class);
                startActivity(reintent);
                finish();
                break;
            case R.id.build:
                Intent buintent=new Intent(sign_inActivity.this,buildingclassActivity.class);
                startActivity(buintent);
                finish();
                break;
            case R.id.getinto:
                Intent gerintent=new Intent(sign_inActivity.this,getintoclassActivity.class);
                startActivity(gerintent);
                finish();
                break;
        }
    }
}
