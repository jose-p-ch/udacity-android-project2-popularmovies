package com.example.jchaparro.popularmovies;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

class JsonUtils {
    public static Movie parseMovieJson(String json) throws JSONException{
        JSONObject jMovie = new JSONObject(json);
        return new Movie(
                jMovie.optString("title"),
                jMovie.optString("poster_path"),
                jMovie.optString("overview"),
                jMovie.optDouble("vote_average"),
                jMovie.optString("release_date")
        );
    }

    static ArrayList<String> parseJsonResults(String json) throws JSONException {
        JSONObject resultsPage = new JSONObject(json);
        JSONArray results = resultsPage.getJSONArray("results");
        ArrayList<String> stringResults = new ArrayList<String>();
        if(results != null && results.length() != 0){
            for(int i = 0; i < results.length(); i++){
                try {
                    stringResults.add(results.getString(i));
                    //Log.d("MovieString",results.getString(i));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        return stringResults;
    }
}
