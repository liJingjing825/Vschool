package com.example.a50388.vschool.main.homepage.fleamarker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.BaseAdapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.a50388.vschool.R;

import java.text.BreakIterator;

public class fleamarkerDataAdapter extends BaseAdapter {

    Context con;
    String[] mydata;


    public fleamarkerDataAdapter(Context con, String[] data) {
        this.con = con;
        mydata = data;
    }

    @Override
    public int getCount() {
        // TODO: Implement this method
        return mydata.length;
    }

    @Override
    public Object getItem(int p1) {
        // TODO: Implement this method
        return mydata[p1];
    }

    @Override
    public long getItemId(int p1) {
        // TODO: Implement this method
        return 0;
    }

    public String getImage(int cout) {
        return mydata[cout].split(" ")[6];
    }

    public String getmessage(int cout) {
        return mydata[cout].split(" ")[4];
    }

       @Override
    public View getView(int p1, View p2, ViewGroup p3) {
       //  TODO: Implement this method
        String[]itemData=mydata[p1].split(" ");
        View v=LayoutInflater.from(con).inflate(R.layout.fleamarkerlist,null);
        TextView t0=(TextView)v.findViewById(R.id.messagenumber);
        t0.setText(itemData[5]);
        TextView t1=(TextView) v.findViewById(R.id.messagemark);
           Log.i("fleamker", itemData[0]);
           Log.i("fleamker", itemData[1]);
           Log.i("fleamker", itemData[2]);
           Log.i("fleamker", itemData[3]);
           Log.i("fleamker", itemData[4]);
           Log.i("fleamker", itemData[5]);

        if (itemData[0].equals("1")) {
            t1.setText("未修");
        }else if (itemData[0].equals("2")){
            t1.setText("已修");
        }
        TextView t2=(TextView) v.findViewById(R.id.messageres);
        t2.setText(itemData[1]);

        TextView t3=(TextView) v.findViewById(R.id.messagehousenumber);
        t3.setText(itemData[3]);


      return v;
   }


    }
