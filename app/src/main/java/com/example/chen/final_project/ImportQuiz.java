package com.example.chen.final_project;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;
import android.view.View;

import org.xmlpull.v1.XmlPullParser;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ImportQuiz extends Activity {
    protected static final String ACTIVITY_NAME = "ImportQuizActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_import_quiz);
    }


    public class QuizQuery extends AsyncTask<String, Integer, String> {
        private String answerA;
        private String answerB;
        private String answerC;
        private String answerD;
        private String correct;
        private String question;
        private String answer;
        private String accuracy;

        @Override
        protected String doInBackground(String... params) {
            try {
                URL url = new URL("http://torunski.ca/CST2335/QuizInstance.xml");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.setRequestMethod("GET");
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
                    swtich() {
                        if (parser.getName().equals("MultipleChoiceQuestion")) {
                            correct = parser.getAttributeValue(null, "correct");
                            question = parser.getAttributeValue(null, "question");
                             if(correct.equals(1)){
                                correct=answerA;
                             }else if(correct.equals(2)){
                                correct=answerB;
                             }else if(correct.equals(3)){
                                correct=answerC;
                             }else{
                                correct=answerD;
                            }
                        }
                        if (parser.getName().equals("NumericQuestion")) {

                            accuracy = parser.getAttributeValue(null, "accuracy");
                            question = parser.getAttributeValue(null, "question");
                            answer = parser.getAttributeValue(null, "answer");


                        }
                        if (parser.getName().equals("TrueFalseQuestion")) {
                            question = parser.getAttributeValue(null, "question");
                            answer = parser.getAttributeValue(null, "answer");

                        }
                    }
                }
            } catch(Exception e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer ...value){
            super.onProgressUpdate(value);
        }

        @Override
        protected void onPostExecute(String args){
            progressBar.setVisibility(View.INVISIBLE);
            currentTemp.setText(String.format("Current temperature: %sC", curTemp));
            minTemp.setText(String.format("Min temperature: %sC", min));
            maxTemp.setText(String.format("Max temperature: %sC", max));
            windSpeed.setText(String.format("Wind speed: %s", wind));
            Image.setImageBitmap(icon);
        }
    }





}
