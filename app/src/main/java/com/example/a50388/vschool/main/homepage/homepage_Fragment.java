package com.example.a50388.vschool.main.homepage;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.a50388.vschool.MainActivity;
import com.example.a50388.vschool.R;
import com.example.a50388.vschool.main.homepage.fleamarker.fleamarkerActivity;
import com.example.a50388.vschool.main.homepage.sign.sign_inActivity;
import com.example.a50388.vschool.main.homepage.studentguide.studentguideActivity;
import com.example.a50388.vschool.repairbean.reportrepairsActivity;

import java.util.ArrayList;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by 50388 on 2018/2/20.
 */

public class homepage_Fragment extends Fragment implements View.OnClickListener {
    private ImageButton IBsign_in,IBstudentguide,IBmyschool,IBkankanshow;
    private ImageView IVschoolnews;
    private TextView TVtodayfood,TVlostfound,TVhelp,TVfleamarker;

    private View mView;
    private ViewPager mViewPaper;
    private List<ImageView> images;
    private List<View> dots;
    private int currentItem;
    //记录上一次点的位置
    private int oldPosition = 0;
    //存放图片的id
    private int[] imageIds = new int[]{
            R.drawable.frist1,
            R.drawable.frist3,
            R.drawable.frist2,
            R.drawable.frist4,

    };
    //存放图片的标题
    private String[] titles = new String[]{
            "轮播1",
            "轮播2",
            "轮播3",
            "轮播4"
    };
    private TextView title;
    private ViewPagerAdapter adapter;
    private ScheduledExecutorService scheduledExecutorService;




    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        mView=inflater.inflate(R.layout.homepage,null);

        setView();
        return mView;
    }
    /**
     * 利用线程池定时执行动画轮播
     */
    @Override
    public void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.scheduleWithFixedDelay(
                new ViewPageTask(),
                2,
                2,
                TimeUnit.SECONDS);
    }
    private void setView(){
        mViewPaper = (ViewPager)mView.findViewById(R.id.vp);

        //显示的图片
        images = new ArrayList<ImageView>();
        for(int i = 0; i < imageIds.length; i++){
            ImageView imageView = new ImageView(getActivity());
            imageView.setBackgroundResource(imageIds[i]);
            images.add(imageView);
        }
        //显示的小点
       /* dots = new ArrayList<View>();
        dots.add(mView.findViewById(R.id.dot_0));
        dots.add(mView.findViewById(R.id.dot_1));
        dots.add(mView.findViewById(R.id.dot_2));
        dots.add(mView.findViewById(R.id.dot_3));
        dots.add(mView.findViewById(R.id.dot_4));*/

        title = (TextView) mView.findViewById(R.id.title);
       /* title.setText(titles[0]);*/

        adapter = new ViewPagerAdapter();
        mViewPaper.setAdapter(adapter);

        mViewPaper.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {


            @Override
            public void onPageSelected(int position) {
              /*  title.setText(titles[position]);
                dots.get(position).setBackgroundResource(R.drawable.cashbook);
                dots.get(oldPosition).setBackgroundResource(R.drawable.cashbook);
*/
                oldPosition = position;
                currentItem = position;
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {

            }
        });
    }

    /*定义的适配器*/
    public class ViewPagerAdapter extends PagerAdapter{

        @Override
        public int getCount() {
            return images.size();
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public void destroyItem(ViewGroup view, int position, Object object) {
            // TODO Auto-generated method stub
//          super.destroyItem(container, position, object);
//          view.removeView(view.getChildAt(position));
//          view.removeViewAt(position);
            view.removeView(images.get(position));
        }

        @Override
        public Object instantiateItem(ViewGroup view, int position) {
            // TODO Auto-generated method stub
            view.addView(images.get(position));
            return images.get(position);
        }

    }




    /**
     * 图片轮播任务
     * @author liuyazhuang
     *
     */
    private class ViewPageTask implements Runnable{

        @Override
        public void run() {
            currentItem = (currentItem + 1) % imageIds.length;
            mHandler.sendEmptyMessage(0);
        }
    }

    /**
     * 接收子线程传递过来的数据
     */
    private Handler mHandler = new Handler(){
        public void handleMessage(android.os.Message msg) {
            mViewPaper.setCurrentItem(currentItem);
        };
    };
    @Override
    public void onStop() {
        // TODO Auto-generated method stub
        super.onStop();
        if(scheduledExecutorService != null){
            scheduledExecutorService.shutdown();
            scheduledExecutorService = null;
        }
    }





    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        intn();
    }

    private void intn() {
        IBsign_in=(ImageButton)getActivity().findViewById(R.id.signin);
        IBstudentguide=(ImageButton)getActivity().findViewById(R.id.studentguideIB);
        IBkankanshow=(ImageButton) getActivity().findViewById(R.id.kankanshow);
        IBmyschool=(ImageButton) getActivity().findViewById(R.id.myschool);


        TVtodayfood=(TextView)getActivity().findViewById(R.id.Ttodayfood);
        TVlostfound=(TextView)getActivity().findViewById(R.id.Tlostfound);
        TVhelp=(TextView)getActivity().findViewById(R.id.Thelphelp);
        TVfleamarker=(TextView)getActivity().findViewById(R.id.Tfleamarker);

        IVschoolnews=(ImageView)getActivity().findViewById(R.id.schoolnews);
        IBsign_in.setOnClickListener(this);
        IBstudentguide.setOnClickListener(this);
        IBmyschool.setOnClickListener(this);
        IBkankanshow.setOnClickListener(this);

        TVtodayfood.setOnClickListener(this);
        TVfleamarker.setOnClickListener(this);
        TVhelp.setOnClickListener(this);
        TVlostfound.setOnClickListener(this);

        IVschoolnews.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        MainActivity activity=(MainActivity)getActivity();

        switch (view.getId()){
            case R.id.signin:
                Intent insign=new Intent(getActivity(),sign_inActivity.class);
                startActivity(insign);
              //  getActivity().finish();
                break;
            case R.id.studentguideIB:
                Intent sgintent=new Intent(getActivity(),studentguideActivity.class);
                startActivity(sgintent);
              //  getActivity().finish();
                break;
            case R.id.kankanshow:
                Intent kkintent=new Intent(getActivity(),reportrepairsActivity.class);
                startActivity(kkintent);
              //  getActivity().finish();
                break;
            case R.id.myschool:
                Intent msintent=new Intent(getActivity(),myschoolActivity.class);
                startActivity(msintent);
             //   getActivity().finish();
                break;


            case R.id.schoolnews:
                Intent snintent=new Intent(getActivity(),schoolnewsActivity.class);
                startActivity(snintent);
             //   getActivity().finish();
                break;
            case R.id.Ttodayfood:
                Intent ifintent=new Intent(getActivity(),todayfoodActivity.class);
                startActivity(ifintent);
             //   getActivity().finish();
                break;
            case R.id.Tlostfound:
                Intent lfintent=new Intent(getActivity(),lostfoundActivity.class);
                startActivity(lfintent);
             //   getActivity().finish();
                break;
            case R.id.Thelphelp:
                Intent hhintent=new Intent(getActivity(),helperActivity.class);
                startActivity(hhintent);
             //   getActivity().finish();
                break;
            case R.id.Tfleamarker:
                Intent flintent=new Intent(getActivity(),fleamarkerActivity.class);
                startActivity(flintent);
             //   getActivity().finish();
                break;




        }

    }




}
