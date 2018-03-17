package com.example.chen.androidlabs;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class WeatherForecast extends Activity {
    protected static final String ACTIVITY_NAME = "WeatherForecastActivity";
    private ProgressBar progressBar;
    private TextView currentTemp;
    private TextView minTemp;
    private TextView maxTemp;
    private TextView windSpeed;
    private ImageView Image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_forecast);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        Image=findViewById(R.id.curWeather);
        currentTemp=findViewById(R.id.curTemp);
        minTemp=findViewById(R.id.minTemp);
        maxTemp=findViewById(R.id.maxTemp);
        windSpeed=findViewById(R.id.windSpeed);


        ForecastQuery forecast = new ForecastQuery();
        String urlString = "http://api.openweathermap.org/data/2.5/weather?q=ottawa,ca&APPID=d99666875e0e51521f0040a3d97d0f6a&mode=xml&units=metric";
        forecast.execute(urlString);
    }

    public static Bitmap getImage(URL url) {
        HttpURLConnection connection = null;
        try {
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            int responseCode = connection.getResponseCode();
            if (responseCode == 200) {
                return BitmapFactory.decodeStream(connection.getInputStream());
            } else
                return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    public boolean fileExistance(String filename) {
        File file = getBaseContext().getFileStreamPath(filename);
        return file.exists();
    }

    public class ForecastQuery extends AsyncTask<String, Integer, String> {
        private String windSpeed;
        private String min;
        private String max;
        private String curTemp;
        private String iconName;
        Bitmap icon;


        @Override
        protected String doInBackground(String... params) {
            try {
                // 得到url
                URL url = new URL(params[0]);
                // 得到访问对象
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                // 设置从connection读入
                connection.setDoInput(true);
                // 设置请求方式
                connection.setRequestMethod("GET");
                // 设置超时时间
                connection.setConnectTimeout(15000);
                connection.setReadTimeout(10000);
                connection.connect();

                InputStream stream = connection.getInputStream();
                XmlPullParser parser = Xml.newPullParser();
                parser.setInput(stream, null);

                while (parser.next() != XmlPullParser.END_DOCUMENT) {
                    if (parser.getEventType() != XmlPullParser.START_TAG) {
                        continue;
                    }
                    if (parser.getName().equals("temperature")) {
                        curTemp = parser.getAttributeValue(null, "value");
                        publishProgress(25);
                        min = parser.getAttributeValue(null, "min");
                        publishProgress(50);
                        max = parser.getAttributeValue(null, "max");
                        publishProgress(75);
                    }
                    if (parser.getName().equals("weather")) {
                        iconName = parser.getAttributeValue(null, "icon");
                        String iconFile = iconName + ".png";
                        if (fileExistance(iconFile)) {
                            FileInputStream inputStream = null;
                            try {
                                inputStream = openFileInput(iconFile);
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }
                            icon = BitmapFactory.decodeStream(inputStream);
                            Log.i(ACTIVITY_NAME, "Image exists");

                        } else {
                            iconName = parser.getAttributeValue(null, "icon");
                            URL iconUrl = new URL("http://openweathermap.org/img/w/" + iconName + ".png");
                            icon = getImage(iconUrl);
                            FileOutputStream outputStream = openFileOutput(iconName + ".png", Context.MODE_PRIVATE);
                            icon.compress(Bitmap.CompressFormat.PNG, 80, outputStream);
                            outputStream.flush();
                            outputStream.close();

                            Log.i(ACTIVITY_NAME, "Add new image");
                        }
                        publishProgress(100);
                    }
                }  } catch(Exception e){
                    e.printStackTrace();
                }
                return null;
            }

        @Override
        protected void onProgressUpdate(Integer ...value){
            progressBar.setVisibility(View.VISIBLE);
            progressBar.setProgress(value[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            String degree = Character.toString((char) 0x00B0);
            currentTemp.setText(String.format("%s%s%sC", currentTemp.getText(), curTemp, degree));
            minTemp.setText(String.format("%s%s%sC", minTemp.getText(), min, degree));
            maxTemp.setText(String.format("%s%s%sC", maxTemp.getText(), max, degree));
            Image.setImageBitmap(icon);
            progressBar.setVisibility(View.INVISIBLE);
        }
    }
}
