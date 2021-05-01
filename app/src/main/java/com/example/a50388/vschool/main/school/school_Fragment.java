package com.example.a50388.vschool.main.school;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.a50388.vschool.R;

/**
 * Created by 50388 on 2018/3/6.
 */

public class school_Fragment extends Fragment {
    public
    View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.school_fragment, null);
        return view;
    }
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }
}
