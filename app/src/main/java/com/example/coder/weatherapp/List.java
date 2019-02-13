package com.example.coder.weatherapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatViewInflater;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;

import java.util.ArrayList;

public class List extends AppCompatActivity {
    SwipeMenuListView listView1;
    ArrayList<String> list = new ArrayList<>();
    DatabaseHelper db = new DatabaseHelper(List.this);
    TextView textView;
    MainActivity activity;
    String main, Temp1, h2, s2;
    ArrayAdapter<String> arrayAdapter;
    String baseURl = "https://api.openweathermap.org/data/2.5/weather?q=";
    String API = "&appid=a9f1004c249870912909152556c3c2b4";
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        getSupportActionBar().setTitle("City List");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        listView1 = (SwipeMenuListView) findViewById(R.id.listview1);
        list = getIntent().getStringArrayListExtra("l");
        View inflatedView = getLayoutInflater().inflate(R.layout.listname, null);
        arrayAdapter = new ArrayAdapter<>(this, R.layout.listname, R.id.textView2, list);
        listView1.setAdapter(arrayAdapter);
        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String cn = list.get(position);
                Intent intent = new Intent(List.this, MainActivity.class);
                intent.putExtra("cityname", cn);
                startActivity(intent);

            }


        });


//        swipeCreator

        SwipeMenuCreator creator = new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu menu) {
                SwipeMenuItem deletecity = new SwipeMenuItem(getApplicationContext());
                deletecity.setBackground(new ColorDrawable(Color.rgb(0x00, 0x66, 0xff)));
                deletecity.setWidth(170);
                deletecity.setIcon(R.drawable.ic_delete_black_24dp);
                menu.addMenuItem(deletecity);

            }
        };
        listView1.setMenuCreator(creator);

        listView1.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {

                switch (index) {
                    case 0:
                        try {
                            String cn = arrayAdapter.getItem(position);
                            db.deleteCity(cn);
                            displayCity();
                            Toast.makeText(List.this, "delete " + cn, Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                }


                return false;
            }
        });


    }
//
    private void displayCity() {
       list = db.getCityList();
        if (arrayAdapter == null) {
            arrayAdapter = new ArrayAdapter<String>(this, R.layout.listname, R.id.textView2, list);
            listView1.setAdapter(arrayAdapter);
        } else {
            arrayAdapter.clear();
            arrayAdapter.addAll(list);
            arrayAdapter.notifyDataSetChanged();
        }

    }
}



