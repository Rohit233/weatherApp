package com.example.coder.weatherapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.Format;

public class Db_Weather extends SQLiteOpenHelper {
    private static  final String DB_Name="weather";
    private static final int DB_Ver=1;
    private static final String DB_TABLE="weatherInfo";
    private static final String DB_COLUMN="temp";
    private static final String DB_COLUMN1="city";
    private static final String DB_COLUMN2="w";
    private static final String DB_COLUMN3="humidity";
    private static final String DB_COLUMN4="windspeed";
    public Db_Weather(Context context){
        super(context,DB_Name,null,1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String query=String.format("CREATE TABLE %s(ID INTEGER PRIMARY KEY AUTOINCREMENT,%s TEXT NOT NULL,%s TEXT NOT NULL,%s TEXT NOT NULL,%s TEXT NOT NULL,%s TEXT NOT NULL);",DB_TABLE,DB_COLUMN,DB_COLUMN1,DB_COLUMN2,DB_COLUMN3,DB_COLUMN4);
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query = String.format("DELETE TASK IF EXISTS %s", DB_TABLE);
        db.execSQL(query);
        onCreate(db);

    }
    public boolean addData(String temp,String city,String weather,String humidity,String windSpeed){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(DB_COLUMN,temp);
        values.put(DB_COLUMN1,city);
        values.put(DB_COLUMN2,weather);
        values.put(DB_COLUMN3,humidity);
        values.put(DB_COLUMN4,windSpeed);

        long result=db.insert(DB_TABLE,null,values);
        if(result==-1){
            return false;
        }
        else {
            return true;
        }

    }
    public Cursor getweather(){
        SQLiteDatabase db=this.getWritableDatabase();
        String q= String.format("SELECT * FROM "+DB_TABLE);
        Cursor data=db.rawQuery(q,null);
        return data;
    }
}
