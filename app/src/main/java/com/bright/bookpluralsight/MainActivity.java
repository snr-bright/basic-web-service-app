package com.bright.bookpluralsight;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.ContentLoadingProgressBar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.facebook.shimmer.Shimmer;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView = null;
    ShimmerFrameLayout shimmerViewContainer = null;
    TextView textViewEmpty = null;
    FeedAdapter feedAdapter = null;
    ArrayList<Book> books = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerView);
        shimmerViewContainer = findViewById(R.id.shimmerViewContainer);
        textViewEmpty = findViewById(R.id.textViewEmpty);
        EditText editTextSearch = findViewById(R.id.editTextSearch);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);

        feedAdapter = new FeedAdapter(books);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(feedAdapter);

        editTextSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    startNetworkCall(s.toString().trim());
                }
            }
        });
    }

    //MARK: start network call
    private void startNetworkCall(String text) {
        URL url = ApiUtil.buildUrl(text);
        new BooksQueryTask().execute(url);
    }

    public class BooksQueryTask extends AsyncTask<URL, Void, String> {

        @Override
        protected void onPreExecute() {
            books.clear();
            feedAdapter.notifyDataSetChanged();
            textViewEmpty.setVisibility(View.GONE);
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
            if (result.isEmpty()) {
                textViewEmpty.setVisibility(View.VISIBLE);
            } else {
                textViewEmpty.setVisibility(View.GONE);
            }
            shimmerViewContainer.setVisibility(View.GONE);
            shimmerViewContainer.stopShimmer();
            recyclerView.setVisibility(View.VISIBLE);
            feedAdapter.notifyDataSetChanged();
        }
    }
}