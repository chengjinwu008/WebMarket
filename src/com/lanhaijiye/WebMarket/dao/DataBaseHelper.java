package com.lanhaijiye.WebMarket.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2015/5/6.
 */
public class DataBaseHelper extends SQLiteOpenHelper {
    public static final String ACCOUNT_TABLE_NAME="account";
    public static final String ACCOUNT="username";
    private static final String DBNAME="accounts.db";//数据库名称
    private static final int VERSION = 1;//数据库版本

    public DataBaseHelper(Context context) {
        super(context, DBNAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table "+ACCOUNT_TABLE_NAME+"( _id integer primary key autoincrement,"+ACCOUNT+" varchar(20) not null);";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
