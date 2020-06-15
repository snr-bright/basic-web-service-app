package com.bright.bookpluralsight;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.facebook.shimmer.Shimmer;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView = null;
    ShimmerFrameLayout shimmerViewContainer = null;
    ArrayList<Book> books = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerView);
        shimmerViewContainer = findViewById(R.id.shimmerViewContainer);
        URL url = ApiUtil.buildUrl("cook");
        new BooksQueryTask().execute(url);
    }

    public class BooksQueryTask extends AsyncTask<URL, Void, String> {

        @Override
        protected void onPreExecute() {
            shimmerViewContainer.setVisibility(View.VISIBLE);
            shimmerViewContainer.startShimmer();
        }

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
        protected void onPostExecute(String result) {
            books.addAll(ApiUtil.getBooksFromJson(result));

            shimmerViewContainer.setVisibility(View.GONE);
            shimmerViewContainer.stopShimmer();

            recyclerView.setVisibility(View.VISIBLE);
        }
    }
}