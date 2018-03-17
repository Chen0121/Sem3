package com.example.chen.androidlabs;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Xml;
import android.view.View;
import android.widget.ProgressBar;

import org.xmlpull.v1.XmlPullParser;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import static java.lang.System.in;

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
        private final String urlString="http://api.openweathermap.org/data/2.5/weather?q=ottawa,ca&APPID=d99666875e0e51521f0040a3d97d0f6a&mode=xml&units=metric";

        @Override
        protected String doInBackground(String ...args){
            try{
                //得到url
                URL url = new URL(urlString);
                //得到访问对象
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                //设置从connection读入
                connection.setDoInput(true);
                //设置请求方式
                connection.setRequestMethod("GET");
                //设置超时时间
                connection.setConnectTimeout(15000);
                connection.setReadTimeout(10000 /* milliseconds */);
                connection.connect();

                InputStream stream=connection.getInputStream();
                XmlPullParser parser= Xml.newPullParser();
                parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES,false);
                parser.setInput(stream,null);

                while(parser.next()!=XmlPullParser.END_DOCUMENT){
                    if(parser.getName().equals("temperature")){
                        curTemp =parser.getAttributeValue(null,"value");
                        publishProgress(25);
                        min=parser.getAttributeValue(null,"min");
                        publishProgress(50);
                        max=parser.getAttributeValue(null,"max");
                        publishProgress(75);
                    }
                    if(parser.getName().equals("weather")){

                    }
                }
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }
}
