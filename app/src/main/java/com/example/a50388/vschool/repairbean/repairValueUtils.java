package com.example.a50388.vschool.repairbean;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 50388 on 2018/8/25.
 */

public
class repairValueUtils {


    public static List<repairValueItem> getValue(String[] infor){

        List<repairValueItem> valueList = new ArrayList<repairValueItem>();

        for(String  s : infor){

            String[] items = s.split(" ");
            repairValueItem bean = new repairValueItem();
            bean.setNumberStamp(items[0]);       //编号
            bean.setObject(items[2]);
            bean.setIsDormotory(items[3]);
            bean.setHostNO(items[3]);   //报修物品
            bean.setPosition(items[5]);  //具体描述
            bean.setIsRepaire(items[1]);//是否修复
            bean.setPictureImage(items[6]);
            bean.setTellphone(items[4]);//联系方式
            //items[5]是报修详情
            valueList.add(bean);
        }

       return valueList;
    }

}
