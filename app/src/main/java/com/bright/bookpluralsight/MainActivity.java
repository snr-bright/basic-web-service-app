package com.bright.bookpluralsight;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.io.IOException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    TextView text_view_result = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text_view_result = findViewById(R.id.text_view_result);
        URL url = ApiUtil.buildUrl("cook");
        Log.e("URL", url.toString());
        new BooksQueryTask().execute(url);
    }

    public class BooksQueryTask extends AsyncTask<URL,Void,String>{
        @Override
        protected String doInBackground(URL... urls) {
            URL searchUrl = urls[0];
            Log.e("URL", searchUrl.toString());
            String result = null;
            try {
                result = ApiUtil.getJson(searchUrl);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return result;
        }
        @Override
        protected void onPostExecute(String s) {
            text_view_result.setText(s);
        }
    }
}