package com.example.a50388.vschool.main.homepage.studentguide;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.a50388.vschool.MainActivity;
import com.example.a50388.vschool.R;
import com.example.a50388.vschool.main.homepage.fleamarker.fleamarkerActivity;
import com.example.a50388.vschool.main.homepage.helperActivity;
import com.example.a50388.vschool.main.homepage.lostfoundActivity;
import com.example.a50388.vschool.main.homepage.myschoolActivity;
import com.example.a50388.vschool.main.homepage.schoolnewsActivity;
import com.example.a50388.vschool.main.homepage.sign.sign_inActivity;
import com.example.a50388.vschool.main.homepage.todayfoodActivity;
import com.example.a50388.vschool.repairbean.reportrepairsActivity;

/**
 * Created by 50388 on 2018/3/4.
 */

public class newstudentguideFragment extends Fragment implements View.OnClickListener{
    private ImageButton traffic,map,navigation,Reporting;
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.newstudent_fragment,null);

        return view;
    }

    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        intn();
    }

    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.traffic:
                Intent insign = new Intent(getActivity(), trafficActivity.class);
                startActivity(insign);
              //  getActivity().finish();
                break;
            case R.id.map:
                Intent sgintent = new Intent(getActivity(), schoolmapActivity.class);
                startActivity(sgintent);
             //   getActivity().finish();
                break;
            case R.id.navigation:
                Intent kkintent = new Intent(getActivity(), navigationActivity.class);
                startActivity(kkintent);
              //  getActivity().finish();
                break;
            case R.id.Reporting:
                Intent msintent = new Intent(getActivity(), reportingActivity.class);
                startActivity(msintent);
             //   getActivity().finish();
                break;



        }
    }

    private void intn() {
        traffic=(ImageButton)getActivity().findViewById(R.id.traffic);
        traffic.setOnClickListener(this);
        map=(ImageButton)getActivity().findViewById(R.id.map);
        map.setOnClickListener(this);
        navigation=(ImageButton)getActivity().findViewById(R.id.navigation);
        navigation.setOnClickListener(this);
        Reporting=(ImageButton)getActivity().findViewById(R.id.Reporting);
        Reporting.setOnClickListener(this);


    }


}
