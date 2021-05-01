package com.example.a50388.vschool.main.mainlist;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.a50388.vschool.MainActivity;
import com.example.a50388.vschool.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public
class planActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView IVreturnhome;
    byte[] buffer=null;//定义保存数据的数组
    File file;//声明一个外部对象
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan);
        final EditText etext=(EditText) findViewById(R.id.edittext);//获取填写备忘信息的编辑框
        Button btn_save=(Button)findViewById(R.id.save);//获取保存按钮
        file=new File(Environment.getExternalStorageDirectory(),"Text.text");//实例化文件对象


        btn_save.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                //保存填写的备忘信息
                FileOutputStream fos=null;

                String text=etext.getText().toString();//获取输入的备忘信息
                try{
                    fos=new FileOutputStream(file);//获得文件输出流对象
                    fos.write(text.getBytes());//保存备忘信息
                    fos.flush();//清除缓存
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }finally {
                    if(fos!=null){
                        try{
                            fos.close();//关闭输出流
                            Toast.makeText(planActivity.this,"保存成功",Toast
                            .LENGTH_LONG).show();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

            }
        });
        //读取保存的备忘录信息
        FileInputStream fis=null;//声明文件输入流对象
        try{
            fis=new FileInputStream(file);
            buffer =new byte[fis.available()];//&#x5b9e;&#x4f8b;&#x5316;&#x5b57;&#x8282;&#x6570;
            fis.read(buffer);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(fis!=null){
                try{
                    fis.close();//关闭输入对象
                    String data=new String(buffer);//把字节数组中的数据转换为字符串
                    etext.setText(data);//显示读取的内容

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        }


        private void intn() {
        IVreturnhome=(ImageView)findViewById(R.id.planhomereturn);
        IVreturnhome.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.planhomereturn:
                Intent returnintent=new Intent(planActivity.this,MainActivity.class);
                startActivity(returnintent);
                finish();
                break;
        }
    }
}
