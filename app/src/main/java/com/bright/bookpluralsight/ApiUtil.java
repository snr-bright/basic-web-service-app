package com.bright.bookpluralsight;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class ApiUtil {
    private ApiUtil() {
    }

    public static final String BASE_URL = "https://www.googleapi.com/books/v1/volumes";

    public static URL buildUrl(String title) {
        String fullUrl = BASE_URL + "?q=" + title;
        URL url = null;
        try {
            url = new URL(fullUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    public static String getJson(URL url) throws IOException {
        HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url.openConnection();
        InputStream inputStream = httpsURLConnection.getInputStream()

    }

}
