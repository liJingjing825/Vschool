package com.example.a50388.vschool.repairbean.report;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.example.a50388.vschool.R;
import com.example.a50388.vschool.main.homepage.studentguide.studentguideActivity;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;


public class damagelocalActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView IVreturnhome;
    private PieChart pieChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_damageloca);
        pieChart=findViewById(R.id.chart2);
        drawPie();
        intn();
    }

    private void intn() {
        IVreturnhome=(ImageView)findViewById(R.id.damagelocalreturn);
        IVreturnhome.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.navigationreturn:
                Intent mhrintent=new Intent(damagelocalActivity.this,studentguideActivity.class);
                startActivity(mhrintent);
                finish();
                break;
        }
    }

    /**
     -    Function:    drawPie()
     -    Description:    绘制饼状图
     **/
    public void drawPie(){

        //名字
        ArrayList<String> names=new ArrayList<String>();
        names.add("宿舍");
        names.add("教室");
        names.add("公共设施");
        names.add("图书馆");
        names.add("餐厅");
        names.add("其他");

        //大小
        ArrayList<Entry> sizes=new ArrayList<Entry>();
        sizes.add(new Entry(4,0));
        sizes.add(new Entry(5,1));
        sizes.add(new Entry(4,2));
        sizes.add(new Entry(2,3));
        sizes.add(new Entry(5,4));
        sizes.add(new Entry(3,5));
        //颜色
        ArrayList<Integer> colors=new ArrayList<Integer>();

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


        //    colors.add(Color.parseColor("#B0E0E6"));
        //    colors.add(Color.parseColor("#8A2BE2"));

        PieDataSet pieDataSet=new PieDataSet(sizes,"");//参数：颜色栏显示颜色目录
        pieDataSet.setValueTextSize(10f);
        //pieDataSet.setDrawValues(true);//是否在块上面显示值以及百分百
        pieDataSet.setSliceSpace(3f);//块间距
        pieDataSet.setColors(colors);


        //DisplayMetrics metrics=this.getResources().getDisplayMetrics();
        PieData pieData=new PieData(names,pieDataSet);


        pieChart.setHoleRadius(120f);  //半径
        // 半透明圈
        // pieChart.setTransparentCircleRadius(5f);//设置大圆里面透明小圆半径，和洞不是一个圆

        pieChart.setDrawHoleEnabled(true);
        //   pieChart.setHoleColorTransparent(true);//设置中心洞是否透明：true为黑，false为白
        pieChart.setHoleRadius(55f);//设置大圆里面的无色圆的半径（洞...）
        pieChart.setTransparentCircleRadius(61f);
        pieChart.setCenterText("报修损坏修复情况");  //饼状图中间的文字



        pieChart.setDescription("");//参数：右下角显示图形描述

        //pieChart.setDrawCenterText(false);//不显示图中心文字
        //pieChart.setCenterText("traffic graph");//图中心文字
        pieChart.setRotationEnabled(true);//手动旋转

        //pieChart.setDrawMarkerViews(false);
        //pieChart.setDrawSliceText(false);//块的文本是否显示



        pieChart.setData(pieData);

        Legend legend=pieChart.getLegend();
        legend.setEnabled(true);//是否显示图形说明，必须要放在setData后,否则出错
        legend.setTextSize(15f);

        //两个参数有不同的意思：
        //durationMillisX：每个块运行到固定初始位置的时间
        //durationMillisY: 每个块到绘制结束时间
        pieChart.animateXY(1000, 1000);//设置动画（参数为时间）
    }
}
