package com.example.a50388.vschool.main.mainlist.address;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by 50388 on 2018/3/1.
 */

public class CashbookDatabassHelper extends SQLiteOpenHelper{

    public CashbookDatabassHelper(Context context){
        super(context, "cashbook_daily", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table if not exists cashbook_cast("+
                "id integer primary key, "+
                "cost_title varchar, "+
                "cost_data varchar, "+
                "cost_money varchar)");

    }

    public void insertCost(CashbookBean cashbookBean){
        SQLiteDatabase database=getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put("cost_title",cashbookBean.costTitle);
        cv.put("cost_title",cashbookBean.costDate);
        cv.put("cost_title",cashbookBean.costMoney);
        database.insert("cashbook_cast",null,cv);
    }

    public Cursor getAllCostDat(){
        SQLiteDatabase database=getWritableDatabase();
        return database.query("cashbook_cost",null,null,null,
                null,null,"cost_data "+"ASC");
    }

    public void deleteAllData(){
        SQLiteDatabase database=getWritableDatabase();
        database.delete("cashbook_cost",null,null);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {



    }
}
