package com.example.a50388.vschool.repairbean;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.a50388.vschool.R;

import java.util.List;

/**
 * Created by 50388 on 2018/8/25.
 */

public class repairAdapter extends BaseAdapter {


    private Context context;
    private List<repairValueItem> valueList;


    public
    repairAdapter(Context context, List<repairValueItem> valueList) {
        this.context = context;
        this.valueList = valueList;
    }

    @Override
    public
    int getCount() {


        return this.valueList.size();
    }

    @Override
    public
    Object getItem(int position) {
        return this.valueList.get(position);
    }

    @Override
    public
    long getItemId(int position) {
        return position;
    }

    @Override
    public
    View getView(int position, View convertView, ViewGroup parent) {

       View view = null;
       if(view == null){
           view = View.inflate(context, R.layout.plusrepairlist, null);
       }else{
           view = convertView;
       }

        repairValueItem value = this.valueList.get(position);

        TextView numberStamp = (TextView)view.findViewById(R.id.messagenumber);
        TextView isrepire = (TextView) view.findViewById(R.id.messagemark);
        TextView objectName = (TextView) view.findViewById(R.id.messageres);
        TextView hostNO = (TextView) view.findViewById(R.id.messagehousenumber);

        numberStamp.setText(value.getNumberStamp());
        isrepire.setText(value.getIsRepaire());
        objectName.setText(value.getObject());
        hostNO.setText(value.getHostNO());

        return  view;
    }
}
