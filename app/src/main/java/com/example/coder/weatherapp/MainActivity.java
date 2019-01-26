package com.example.coder.weatherapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;



public class MainActivity extends AppCompatActivity {
    Button button;
    EditText city;
    TextView result;
    TextView temp;
    TextView Humidity;
    TextView windSpeed;

    //    https://api.openweathermap.org/data/2.5/weather?q=London,uk&appid=a9f1004c249870912909152556c3c2b4
//    273.15
    String baseURl = "https://api.openweathermap.org/data/2.5/weather?q=";
    String API = "&appid=a9f1004c249870912909152556c3c2b4";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (Button) findViewById(R.id.button);
        city = (EditText) findViewById(R.id.getCity);
        result = (TextView) findViewById(R.id.result);
        temp = (TextView) findViewById(R.id.temp);
        Humidity = (TextView) findViewById(R.id.humidity);
        windSpeed = (TextView) findViewById(R.id.windSpeed);


            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final String myURl = baseURl + city.getText().toString() + API;

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
                                            String main = object.getString("main");
                                            result.setText(main);

                                            String Main = response.getString("main");
                                            JSONObject ext = new JSONObject(Main);
                                            String Temp = ext.getString("temp");

                                            double t = Double.parseDouble(Temp);
                                            double t1 = t - 273.15;
                                            int mt = (int) t1;

                                            String Temp1 = String.valueOf("temp: " + mt + "º");
                                            temp.setText(Temp1);

                                            String h1 = ext.getString("humidity");
                                            String h2 = "Humidity: " + h1 + " %";
                                            Humidity.setText(h2);

                                            String wind = response.getString("wind");
                                            JSONObject speed = new JSONObject(wind);
                                            String s1 = speed.getString("speed");
                                            String s2 = "Wind Speed: " + s1 + " km/hr";
                                            windSpeed.setText(s2);

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
            });


    }
}
