package com.example.a50388.vschool.repairbean.report;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.example.a50388.vschool.R;
import com.example.a50388.vschool.main.homepage.studentguide.studentguideActivity;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;


public class damagethingActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView IVreturnhome;
    private BarChart barChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_damagething);
        barChart=findViewById(R.id.chart3);
        drawHistogram();
        intn();
    }

    private void intn() {
        IVreturnhome=(ImageView)findViewById(R.id.damagethingreturn);
        IVreturnhome.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.navigationreturn:
                Intent mhrintent=new Intent(damagethingActivity.this,studentguideActivity.class);
                startActivity(mhrintent);
                finish();
                break;
        }
    }

    public void drawHistogram(){
        //名称
        ArrayList<String> mMonths=new ArrayList<String>();
        mMonths.add("空调");
        mMonths.add("门面");
        mMonths.add("风扇");
        mMonths.add("开关");
        mMonths.add("桌椅");
        mMonths.add("供水供暖");
        mMonths.add("卫生间");
        //大小（高低）
        ArrayList<BarEntry> sizes=new ArrayList<BarEntry>();
        sizes.add(new BarEntry(6,0));
        sizes.add(new BarEntry(10,1));
        sizes.add(new BarEntry(9,2));
        sizes.add(new BarEntry(7,3));
        sizes.add(new BarEntry(11,4));
        sizes.add(new BarEntry(10,5));
        sizes.add(new BarEntry(8,6));

        //颜色
        BarDataSet barDataSet=new BarDataSet(sizes,"");
        barDataSet.setValueTextSize(10f);
        ArrayList<Integer> colors = new ArrayList<Integer>();

        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());

        barDataSet.setColors(colors);
        BarData barData=new BarData(mMonths,barDataSet);
        barChart.setDescription("本月损坏物品统计图");//数据描述
        barChart.setDescriptionTextSize(12f);
        barChart.setNoDataTextDescription("No data for the chart");// 如果没有数据的时候，会显示这个，类似ListView的EmptyView
        barChart.setPinchZoom(true);// 集双指缩放
        barChart.setScaleEnabled(true);//手动缩放效果
        barChart.setPinchZoom(false);//xy轴同时缩放,和setScaleEnabled一起使用
        barChart.setDrawGridBackground(false);
        //barChart.setDrawBorders(false);//画布边
        //barChart.setVisibleXRange(7);
        //barChart.setMaxVisibleValueCount(6);
        barChart.setDrawBarShadow(false);//设置矩形阴影不显示
        //barChart.setBackgroundColor(Color.parseColor("#FFFFFF"));//设置背景颜色

        //barChart.setMinOffset(0);//=padding
        barChart.setDrawValueAboveBar(true);

        barChart.setData(barData);
        barChart.animateXY(1000, 1000);//设置动画
        Legend legend=barChart.getLegend();//取消图形说明
        legend.setEnabled(false);

        //获取X轴坐标
        XAxis xAxis=barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);//X坐标位于图标底部
        xAxis.setDrawGridLines(false);
        xAxis.setSpaceBetweenLabels(2);//设置名字names之间的间距
        //xAxis.

        //获取Y轴右坐标
        YAxis yAxisR=barChart.getAxisRight();
        yAxisR.setEnabled(true);
        yAxisR.setDrawGridLines(true);

        //获取Y轴左坐标
        YAxis yAxisL=barChart.getAxisLeft();
        yAxisL.setEnabled(true);
        yAxisL.setDrawGridLines(true);
    }
}
