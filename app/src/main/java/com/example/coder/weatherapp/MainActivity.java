package com.example.coder.weatherapp;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.support.v7.widget.Toolbar;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;


public class MainActivity extends AppCompatActivity {
    Button button,addcity;
    EditText editText;
    TextView result,cityn;
    TextView temp,textView;
    TextView Humidity;
    TextView windSpeed;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    ListView listView;
    DatabaseHelper databaseHelper;
   ArrayList<String> name=new ArrayList<String>();
   ArrayAdapter<String>arrayAdapter;
    RecyclerView.ViewHolder viewHolder;
    String cityName=null;
    Toolbar toolbar;
    Intent cList;
    List Lactivity;
    String Temp1=null;
    String h2=null;
    String main=null;
    String s2=null;
    Db_Weather db_weather=new Db_Weather(MainActivity.this);


    //    https://api.openweathermap.org/data/2.5/weather?q=London,uk&appid=a9f1004c249870912909152556c3c2b4
//    273.15
    String baseURl = "https://api.openweathermap.org/data/2.5/weather?q=";
    String API = "&appid=a9f1004c249870912909152556c3c2b4";



//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        if(toggle.onOptionsItemSelected(item)){
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }

//    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
            toolbar=(Toolbar)findViewById(R.id.my_toolbar);
            setSupportActionBar(toolbar);
            toolbar.setLogo(R.drawable.ic_cloud_icon_24dp);
            toolbar.setBackgroundColor(getResources().getColor(R.color.colorAccent));
//        navigation code
//        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
//        toggle = new ActionBarDrawerToggle(MainActivity.this, drawerLayout, R.string.open, R.string.close);
//        drawerLayout.addDrawerListener(toggle);
//        toggle.syncState();
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        Initialize
        button = (Button) findViewById(R.id.addcity);
        result = (TextView) findViewById(R.id.result);
        temp = (TextView) findViewById(R.id.temp);
        Humidity = (TextView) findViewById(R.id.humidity);
        windSpeed = (TextView) findViewById(R.id.windSpeed);
        cityn=(TextView)findViewById(R.id.cityName);
//        listView = (ListView) findViewById(R.id.listview);
        databaseHelper = new DatabaseHelper(this);
        View inflatedView=getLayoutInflater().inflate(R.layout.listname,null);
//       final View editView=getLayoutInflater().inflate(R.layout.edit_text,null);

        textView=(TextView)inflatedView.findViewById(R.id.textView2);


//        Log.i("cName","City name is:"+editText);
//        deleteCity=(ImageButton)inflatedView.findViewById(R.id.deleteCity);


        displayCity();
        setWeather();

//        addcity=(Button)findViewById(R.id.addCity);
//        save=(Button)findViewById(R.id.save);
//        LayoutInflater li = LayoutInflater.from(MainActivity.this);

//        editText=(EditText)findViewById(R.id.edittext1);


////       Service for notification
        Intent intent = new Intent(getApplicationContext(), MyService.class);
        intent.putExtra("getWether", temp.getText().toString());
        startService(intent);

//        try
////
//        {
//           final String main = getIntent().getStringExtra(Lactivity.main), Temp1 = getIntent().getStringExtra(Lactivity.Temp1), h2 = getIntent().getStringExtra(Lactivity.h2),
//                    s2 = getIntent().getStringExtra(Lactivity.s2);
//            result.setText(main);
//            temp.setText(Temp1);
//            Humidity.setText(h2);
//            windSpeed.setText(s2);
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//          add city in navigation
//        addcity.setOnClickListener(new View.OnClickListener() {
//                                       @Override
//                                       public void onClick(View v) {
//
//                                           String c = editText.getText().toString();
//                                           if (name.contains(c)) {
//                                               Toast.makeText(getApplicationContext(), "City is Aleready present", Toast.LENGTH_LONG).show();
//
//                                           } else if (c == null || c.trim().equals("")) {
//                                               Toast.makeText(getApplicationContext(), "Please enter city", Toast.LENGTH_LONG).show();
//
//                                           } else {
////                                               AddData(c);
//
////                                               populateListView();
////////                                               name.add(c);
////                                               String cityN=String.valueOf(editText.getText());
////                                               databaseHelper.insertNewCity(cityN);
////                                               displayCity();
////                                               editText.setText("");
//
//
//                                           }
//
//
//
//                                       }
//                                   });

//                        Intent intent1=new Intent(MainActivity.this,List.class);
//                        intent1.putExtra("baseURl",baseURl);
//                        intent1.putExtra("API",API);




        String cn = getIntent().getStringExtra("cityname");
//                              listView item listener

//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if(cn!=null) {

            final String myURl = baseURl + cn + API;
            Log.i("URL", "URL is:" + myURl);
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, myURl, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                String wether = response.getString("weather");
                                JSONArray ar = new JSONArray(wether);
                                for (int i = 0; i < ar.length(); i++) {
                                    JSONObject object = ar.getJSONObject(i);
                                     main= object.getString("main");
                                    result.setText(main);

                                    String Main = response.getString("main");
                                    JSONObject ext = new JSONObject(Main);
                                    String Temp = ext.getString("temp");

                                    double t = Double.parseDouble(Temp);
                                    double t1 = t - 273.15;
                                    final int mt = (int) t1;

                                    Temp1 = String.valueOf("temp: " + mt + "º");
                                    temp.setText(Temp1);

                                    String h1 = ext.getString("humidity");
                                    h2 = "Humidity: " + h1 + " %";
                                    Humidity.setText(h2);

                                    String wind = response.getString("wind");
                                    JSONObject speed = new JSONObject(wind);
                                    String s1 = speed.getString("speed");
                                     s2= "Wind Speed: " + s1 + " km/hr";
                                    windSpeed.setText(s2);

                                 cityName =response.getString("name");
                                    cityn.setText(cityName);
                                    cityn.setAlpha(1);
//                                    databaseHelper.insertWeather(cityName);
//                                    displayWeather();
                                    addWeather(Temp1,cityName,main,h2,s2);


                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },

                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast toast = Toast.makeText(getApplicationContext(), "Please Check Internet Connection", Toast.LENGTH_LONG);
                            toast.show();
                        }
                    }
            );
            MySingleton.getInstance(MainActivity.this).addToRequestQue(jsonObjectRequest);
        }
//            }
//        });

//                Todo: clear the code all be finish
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//
////                   code to get weather info
//
//            final String myURl = baseURl + city.getText().toString() + API;
//
//            Log.i("URL", "URL is:" + myURl);
//            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, myURl, null,
//                    new Response.Listener<JSONObject>() {
//                        @Override
//                        public void onResponse(JSONObject response) {
//                            try {
//                                String wether = response.getString("weather");
//                                JSONArray ar = new JSONArray(wether);
//                                for (int i = 0; i < ar.length(); i++) {
//                                    JSONObject object = ar.getJSONObject(i);
//                                    String main = object.getString("main");
//                                    result.setText(main);
//
//                                    String Main = response.getString("main");
//                                    JSONObject ext = new JSONObject(Main);
//                                    String Temp = ext.getString("temp");
//
//                                    double t = Double.parseDouble(Temp);
//                                    double t1 = t - 273.15;
//                                    final int mt = (int) t1;
//
//                                    String Temp1 = String.valueOf("temp: " + mt + "º");
//                                    temp.setText(Temp1);
//
//                                    String h1 = ext.getString("humidity");
//                                    String h2 = "Humidity: " + h1 + " %";
//                                    Humidity.setText(h2);
//
//                                    String wind = response.getString("wind");
//                                    JSONObject speed = new JSONObject(wind);
//                                    String s1 = speed.getString("speed");
//                                    String s2 = "Wind Speed: " + s1 + " km/hr";
//                                    windSpeed.setText(s2);
//                                }
//
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//                        }
//                    },
//
//                    new Response.ErrorListener() {
//                        @Override
//                        public void onErrorResponse(VolleyError error) {
//                            Toast toast = Toast.makeText(getApplicationContext(), "Please Check Internet Connection", Toast.LENGTH_LONG);
//                            toast.show();
//                        }
//                    }
//            );
//            MySingleton.getInstance(MainActivity.this).addToRequestQue(jsonObjectRequest);
//                }
//            });

//        swipeCreator

//        SwipeMenuCreator creator=new SwipeMenuCreator() {
//            @Override
//            public void create(SwipeMenu menu) {
//                SwipeMenuItem deletecity=new SwipeMenuItem(getApplicationContext());
//                deletecity.setBackground(new ColorDrawable(Color.rgb(0x00,0x66,0xff)));
//               deletecity.setWidth(170);
//               deletecity.setIcon(R.drawable.ic_delete_black_24dp);
//               menu.addMenuItem(deletecity);
//
//            }
//        };
//        listView.setMenuCreator(creator);
//
//        listView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
//                switch (index){
//                    case 0:
//                        Log.d("swap","you click on delete button");
//                }
//                return false;
//            }
//        });

//          delete City

//        deleteCity.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Toast.makeText(MainActivity.this,"city has been deleted",Toast.LENGTH_LONG).show();
//            }
//        });




    }
//    private void displayWeather(){
//        cityName=databaseHelper.getWeather();
//    }

   public void displayCity(){
        name=databaseHelper.getCityList();
        if (arrayAdapter == null) {
            arrayAdapter=new ArrayAdapter<String>(this,R.layout.listname,R.id.textView2,name);
//            listView.setAdapter(arrayAdapter);
        }
        else {
            arrayAdapter.clear();
            arrayAdapter.addAll(name);
            arrayAdapter.notifyDataSetChanged();
        }

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.nav_menu,menu);
        return super.onCreateOptionsMenu(menu);

    }
//    TODO: add city function incomplete

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//        if(toggle.onOptionsItemSelected(item)){
//
//        }
        switch (item.getItemId() ){
            case R. id.addcity:
//                final EditText editText=new EditText(this);

//                Intent intent1=new Intent(MainActivity.this,List.class);
////                intent1.putStringArrayListExtra("list",name);
//                intent1.putExtra("name",editText.getText().toString());
////                startActivity(intent1);
                LayoutInflater inflater=this.getLayoutInflater();
                View view=inflater.inflate(R.layout.edit_text,null);
                editText=(EditText)view.findViewById(R.id.editText);
                    AlertDialog alertDialog = new AlertDialog.Builder(this)

                            .setTitle("Add New City")
                            .setMessage("Enter City Name")
                            .setView(view)
                            .setPositiveButton("Add", new DialogInterface.OnClickListener() {
//                              TODO:
//                                Toast error correction
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if(name.contains(editText.getText().toString())){
                                        Toast.makeText(MainActivity.this,"city is aleready present",Toast.LENGTH_LONG).show();
                                    }
                                    else if(editText.getText().toString()==null||editText.getText().toString().trim().equals("")){
                                        Toast.makeText(MainActivity.this,"Please enter city name",Toast.LENGTH_LONG).show();

                                    }
                                    else {

                                        String cname = String.valueOf(editText.getText());
                                        databaseHelper.insertNewCity(cname);
                                        displayCity();
                                    }

                                }
                            })
                            .setNegativeButton("Cancel", null)
                            .create();
                    alertDialog.show();
                    return true;

            case (R.id.cityList):
                 cList=new Intent(MainActivity.this,List.class);
                cList.putExtra("l",name);
               startActivity(cList);
        }
        return super.onOptionsItemSelected(item);


    }

//    code for store weather data in sqlite database

    public void addWeather(String temp,String city,String weather,String humidity,String windSpeed){
        boolean insertData=db_weather.addData(temp,city,weather,humidity,windSpeed);
        if(insertData==true){
            Toast.makeText(MainActivity.this,"data has been store",Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(MainActivity.this,"something went wrong",Toast.LENGTH_SHORT).show();
        }
        }

        public void setWeather(){
                String t,c,w,s,h;
            Cursor data=db_weather.getweather();
            try {
                while (data.moveToNext()) {
                    t = data.getString(1);
                    c = data.getString(2);
                    w=data.getString(3);
                    h=data.getString(4);
                    s=data.getString(5);
                    temp.setText(t);
                    cityn.setText(c);
                    result.setText(w);
                    Humidity.setText(h);
                    windSpeed.setText(s);
                }
            }catch (Exception e){
                e.printStackTrace();
            }

        }

    }











