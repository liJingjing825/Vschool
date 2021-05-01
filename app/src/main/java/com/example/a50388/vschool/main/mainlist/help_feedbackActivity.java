package com.example.a50388.vschool.main.mainlist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.a50388.vschool.MainActivity;
import com.example.a50388.vschool.R;

public
class help_feedbackActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView IVreturnhome;


    @Override
    protected
    void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_feedback);
        intn();



    }

    private void intn() {
        IVreturnhome=(ImageView)findViewById(R.id.helpfeedbackhomereturn);
        IVreturnhome.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.helpfeedbackhomereturn:
                Intent returnintent=new Intent(help_feedbackActivity.this,MainActivity.class);
                startActivity(returnintent);
                finish();
                break;
        }
    }

}
