package com.example.a50388.vschool.main.homepage;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.a50388.vschool.MainActivity;
import com.example.a50388.vschool.R;


public class lostfoundActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView IVreturnhome;

    @Override
    protected
    void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lostfound);
        intn();
    }

    private
    void intn() {
        IVreturnhome = (ImageView) findViewById(R.id.lostfundhomereturn);
        IVreturnhome.setOnClickListener(this);
    }

    @Override
    public
    void onClick(View v) {
        switch (v.getId()) {
            case R.id.lostfundhomereturn:
                Intent mhrintent = new Intent(lostfoundActivity.this, MainActivity.class);
                startActivity(mhrintent);
                finish();
                break;
        }
    }

}
