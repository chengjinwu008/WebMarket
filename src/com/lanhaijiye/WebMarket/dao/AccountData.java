package com.lanhaijiye.WebMarket.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Administrator on 2015/5/6.
 */

/**
 * 用于操作账号记录数据库
 */
public class AccountData {

    //存放账号
    public static void putAccount(Context context,String account){
        DataBaseHelper helper = new DataBaseHelper(context);
        SQLiteDatabase database = helper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DataBaseHelper.ACCOUNT,account);
        database.insert(DataBaseHelper.ACCOUNT_TABLE_NAME, null, contentValues);
        database.close();
    }

    //读取前10条账号
    public static String[] getAccounts(Context context){
        DataBaseHelper helper = new DataBaseHelper(context);
        SQLiteDatabase database = helper.getReadableDatabase();
        Cursor cursor =  database.rawQuery("select " + DataBaseHelper.ACCOUNT + " from " + DataBaseHelper.ACCOUNT_TABLE_NAME + " order by _id DESC limit 10", null);
        int size = cursor.getCount();
        if(size<=0) {
            cursor.close();
            database.close();
            return null;
        }
        String[] accounts = new String[size];
        cursor.moveToFirst();
        accounts[0] = cursor.getString(cursor.getColumnIndex(DataBaseHelper.ACCOUNT));
        int i=0;
        while (cursor.moveToNext())
            accounts[++i]=cursor.getString(cursor.getColumnIndex(DataBaseHelper.ACCOUNT));
        cursor.close();
        database.close();
        return accounts;
    }

    public static boolean isSaved(Context context,String account){
        DataBaseHelper helper = new DataBaseHelper(context);
        SQLiteDatabase database = helper.getReadableDatabase();
        Cursor cursor =  database.rawQuery("select " + DataBaseHelper.ACCOUNT + " from " + DataBaseHelper.ACCOUNT_TABLE_NAME + " where "+DataBaseHelper.ACCOUNT+"=?", new String[]{account});
        if(cursor.getCount()==0) {
            cursor.close();
            database.close();
            return false;
        }else{
            cursor.close();
            database.close();
            return true;
        }
    }
}
