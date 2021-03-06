package com.example.jchaparro.popularmovies;

import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class NetworkUtils {

    final static String BASE_URL = "https://api.themoviedb.org/3/movie/";

    final static String myApiKey = BuildConfig.THE_MOVIES_API_KEY; //fill in a movie database API key here

    public static URL buildPopularQueryUrl(String page, String sort_by){
        Uri builtUri = Uri.parse(BASE_URL).buildUpon()
                .appendPath(sort_by)
                .appendQueryParameter("api_key",myApiKey)
                .appendQueryParameter("page", page)
                .build();
        URL url = null;
        try {
            url = new URL(builtUri.toString());
            Log.d("URLtest", builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }
}
