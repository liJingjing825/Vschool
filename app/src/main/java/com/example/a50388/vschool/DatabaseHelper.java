package com.example.a50388.vschool;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by 50388 on 2018/8/5.
 */

public class DatabaseHelper extends SQLiteOpenHelper{
    public static final String USER_TABLE_NAME="user";
    public static final String USERNANME="username";
    public static final String PASSWORD="password";
    public static final String DATABASE_NAME="test.db";

    public DatabaseHelper(Context context, String test_db) {
        super(context,DATABASE_NAME, null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+ USER_TABLE_NAME+"("+USERNANME+" varchar(20) not null,"+PASSWORD+" varchar(60) not null);");

    }

    @Override
    public
    void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
