package com.example.a50388.vschool.main.mainlist.address;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.a50388.vschool.R;

import java.util.List;

/**
 * Created by 50388 on 2018/3/1.
 */

public class cashbooklistAdapte extends BaseAdapter {

    private List<CashbookBean> mList;
    private Context mContext;
    private LayoutInflater mLayoutInflater;

    public  cashbooklistAdapte(Context context,List<CashbookBean> list){

        mContext=context;
        mList=list;
        mLayoutInflater=LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (convertView==null){
            viewHolder=new ViewHolder();
            convertView=mLayoutInflater.inflate(R.layout.addresslist,null);
            viewHolder.mTvCostTitle=(TextView)convertView.findViewById(R.id.tv_name);
            viewHolder.mTvCostDate=(TextView)convertView.findViewById(R.id.tv_phone);
            viewHolder.mTvCostMoney=(TextView)convertView.findViewById(R.id.tv_id);
            convertView.setTag(viewHolder);
        }else {
            viewHolder=(ViewHolder)convertView.getTag();
        }
        CashbookBean bean=mList.get(position);
        viewHolder.mTvCostTitle.setText(bean.costTitle);
        viewHolder.mTvCostDate.setText(bean.costDate);
        viewHolder.mTvCostMoney.setText(bean.costMoney);
        return convertView;
    }

    private  static class ViewHolder{
        public TextView mTvCostTitle;
        public TextView mTvCostDate;
        public TextView mTvCostMoney;
    }
}
