package com.example.a50388.vschool.main.homepage.studentguide;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.a50388.vschool.MainActivity;
import com.example.a50388.vschool.R;

public
class studentguideActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView returnhome;
    private Button newstudent,oldstudent;

    private FragmentManager manager;
    private FragmentTransaction transaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_studentguide);
        manager=getFragmentManager();
        transaction=manager.beginTransaction();
        transaction.add(R.id.sgfragment,new newstudentguideFragment());
        transaction.addToBackStack(null);
        transaction.commit();
        intn();

    }

    private void intn() {
        returnhome=(ImageView)findViewById(R.id.studentguidereturn);
        returnhome.setOnClickListener(this);
    }

    public void onClick(View view) {
        transaction=manager.beginTransaction();
        Fragment f = null;
        switch (view.getId()) {
            case R.id.newstudentg:
                transaction.replace(R.id.sgfragment,new newstudentguideFragment());
                transaction.commit();
                break;
            case R.id.studentguidereturn:
                Intent sgintent=new Intent(studentguideActivity.this,MainActivity.class);
                startActivity(sgintent);
                finish();
                break;
        }

    }
}
