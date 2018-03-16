package com.example.chen.androidlabs;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import java.net.HttpURLConnection;
import java.net.URL;

public class WeatherForecast extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_forecast);

        ProgressBar progressBar=findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
    }

    private class ForecastQuery extends AsyncTask<String, Integer, String> {
        private String speed;
        private String min;
        private String max;
        private String curTemp;
        Bitmap pic;

        @Override
        protected String doInBackground(String ...args){
            try{
                URL url = new URL("http://api.openweathermap.org/data/2.5/weather?q=ottawa,ca&APPID=d99666875e0e51521f0040a3d97d0f6a&mode=xml&units=metric");
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.getInputStream();
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }
}
