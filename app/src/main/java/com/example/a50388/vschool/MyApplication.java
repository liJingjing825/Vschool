package com.example.a50388.vschool;

import android.app.Application;
/*
* 这个类用来实现全局变量*/
public class MyApplication extends Application {

    private  String name="忘了嘘寒";
    private String classname="18级计算机科学";
    private String id="17863275260";
    private String type="user";//默认为user用户
    private String Signclass="";

    public String getSignclass() {
        return Signclass;
    }

    public void setSignclass(String signclass) {
        Signclass = signclass;
    }



    public String getType() {

        return type;

    }
    public void setType(String type)
    {
        this.type = type;
    }
    public String getId() {

        return id;
    }
    public void setId(String id) {

        this.id = id;
    }



    public String getClassname() {

        return classname;
    }

    public void setClassname(String classname) {

        this.classname = classname;
    }




    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
