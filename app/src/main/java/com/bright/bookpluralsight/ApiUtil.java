package com.bright.bookpluralsight;

import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

import javax.net.ssl.HttpsURLConnection;

public class ApiUtil {

    private ApiUtil() {
    }

    //MARK: base url to call
    public static final String BASE_URL = "https://www.googleapis.com/books/v1/volumes";
    public static final String QUERY_PARAM_KEY = "q";

    //MARK: build connection
    public static URL buildUrl(String title) {
        Uri uri = Uri.parse(BASE_URL).buildUpon().appendQueryParameter(QUERY_PARAM_KEY, title).build();
        URL url = null;
        try {
            url = new URL(uri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    //MARK: network connection
    public static String getJson(URL url) throws IOException {
        HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url.openConnection();
        InputStream inputStream = httpsURLConnection.getInputStream();
        Scanner scanner = new Scanner(inputStream);
        scanner.useDelimiter("\\A");
        try {
            boolean hasNext = scanner.hasNext();
            if (hasNext) {
                return scanner.next();
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("", e.toString());
            return null;
        } finally {
            httpsURLConnection.disconnect();
        }
    }

    //MARK: return list of books from json
    public static ArrayList<Book> getBooksFromJson(String books) {
        final String ID = "id";
        final String TITLE = "title";
        final String ITEMS = "items";
        final String VOLUME_INFO = "volumeInfo";
        final String AUTHOR = "authors";
        ArrayList<Book> listOfBooks = new ArrayList<>();
        try {
            JSONObject jsonBooks = new JSONObject(books);
            JSONArray jsonArray = jsonBooks.getJSONArray(ITEMS);
            int numberOfBooks = jsonArray.length();
            //MARK: loop  over json books
            for (int i = 0; i < numberOfBooks; i++) {
                JSONObject jsonObjectBook = jsonArray.getJSONObject(i);
                JSONObject jsonObjectVolumeInfo = jsonObjectBook.getJSONObject(VOLUME_INFO);
                int numberOfAuthors = jsonObjectVolumeInfo.length();
                String[] authors = new String[numberOfAuthors];
                for (int j = 0; j < numberOfAuthors; j++) {
                    authors[j] = jsonObjectVolumeInfo.getJSONArray(AUTHOR).get(j).toString();
                }
                Book book = new Book(jsonObjectBook.getString(ID), jsonObjectBook.getString(TITLE), authors);
                listOfBooks.add(book);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return listOfBooks;
    }

}
