package com.example.a50388.vschool.main;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.support.v4.widget.ViewDragHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.a50388.vschool.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class myhomeHorizon extends HorizontalScrollView {
    private LinearLayout mWapper;
    private ViewGroup mMenu,mContent;
    private int mScreenWidth;
    private int mMenuWidth;
    private  int mMenuRightPadding=30;
    private boolean isOper;
    private boolean once;

    public myhomeHorizon(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }


    public myhomeHorizon(Context context, AttributeSet attrs, int defSyle){
        super(context,attrs,defSyle);
        //获取定义的属性
        TypedArray a=context.getTheme().obtainStyledAttributes(attrs, R.styleable.myhomeHorizon,defSyle,0);
        int n=a.getIndexCount();
        for (int i=0;i<n;i++){
            int attr=a.getIndex(i);
            switch (attr){
                case R.styleable.myhomeHorizon_rightPadding:
                    mMenuRightPadding=a.getDimensionPixelSize(attr,(int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,50,
                            context.getResources().getDisplayMetrics()));
                    break;
                default:
                        break;
            }
        }
        WindowManager wm=(WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics=new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        mScreenWidth=outMetrics.widthPixels;

    }
    public myhomeHorizon(Context context){
        this(context,null);
    }
    /**
     * 设置子view的宽和高
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (!once){
            mWapper=(LinearLayout)getChildAt(0);
            mMenu=(ViewGroup)mWapper.getChildAt(0);
            mContent=(ViewGroup)mWapper.getChildAt(1);
           mMenuWidth= mMenu.getLayoutParams().width=mScreenWidth-mMenuRightPadding;
            mContent.getLayoutParams().width=mScreenWidth;
            once=true;
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
    /**
     *通过设置偏移量，将menu隐藏
     */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);

        if (changed){
            this.scrollTo(mMenuWidth,0);
        }
    }
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        switch (action){
            //隐藏在左边的宽度
            case MotionEvent.ACTION_UP:
                int scrollX=getScrollX();

                if (scrollX>=mMenuWidth/2){
                    this.smoothScrollTo(mMenuWidth,0);
                    isOper=false;
                }else {
                    this.smoothScrollTo(0,0);
                    isOper=true;
                }

                return true;
        }
        return super.onTouchEvent(ev);
    }
    //打开菜单
    public void openMenu(){
        if (isOper)return;
        this.smoothScrollTo(0,0);
        isOper=true;
    }
    //关闭菜单
    public void closeMenu(){
        if (!isOper)return;;
        this.smoothScrollTo(mMenuWidth,0);
        isOper=false;
    }
    //切换菜单
    public void toggle(){
        if (isOper){
            closeMenu();
        }else {
            openMenu();
        }
    }
}
