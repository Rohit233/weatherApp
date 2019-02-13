package com.example.coder.weatherapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static  final String DB_Name="CityName";
    private static final int DB_Ver=1;
    private static final String DB_TABLE="City";
    private static final String DB_COLUMN="Name";
    private static final String DB_COLUMN1="weather";

    public DatabaseHelper(Context context){
        super(context,DB_Name,null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query=String.format("CREATE TABLE %s(ID INTEGER PRIMARY KEY AUTOINCREMENT,%s TEXT NOT NULL);",DB_TABLE,DB_COLUMN);
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            String query = String.format("DELETE TASK IF EXISTS %s", DB_TABLE);
            db.execSQL(query);
            onCreate(db);
    }
    public void insertNewCity(String city){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(DB_COLUMN,city);
        db.insertWithOnConflict(DB_TABLE,null,values,SQLiteDatabase.CONFLICT_REPLACE);
        db.close();
    }

   public void deleteCity(String city){
        SQLiteDatabase db=this.getWritableDatabase();
        db.delete(DB_TABLE,DB_COLUMN+"= ?",new String[]{city});
        db.close();

    }
    public ArrayList<String> getCityList(){
        ArrayList<String> cityList=new ArrayList<>();
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor=db.query(DB_TABLE,new String[]{DB_COLUMN},null,null,null,null,null);
        while(cursor.moveToNext()){
            int index=cursor.getColumnIndex(DB_COLUMN);
            cityList.add(cursor.getString(index));
        }
        cursor.close();
        db.close();


        return cityList;
    }






}
