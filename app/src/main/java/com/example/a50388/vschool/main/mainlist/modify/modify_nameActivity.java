package com.example.a50388.vschool.main.mainlist.modify;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.a50388.vschool.R;
import com.example.a50388.vschool.main.myinformationActivity;

public class modify_nameActivity extends AppCompatActivity {
    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_name);
        btn=(Button)findViewById(R.id.nametrue);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String site=((EditText)findViewById(R.id.namesite)).getText().toString();
                if (!"".equals(site)){
                    Intent intent=new Intent(modify_nameActivity.this,myinformationActivity.class);
                    Bundle bundle=new Bundle();
                    bundle.putCharSequence("name",site);
                    intent.putExtras(bundle);
                    setResult(0x11,intent);
                    finish();
                }
            }
        });
    }
}
