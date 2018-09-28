package com.example.jchaparro.popularmovies;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    ImageView paintingImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        paintingImageView = (ImageView) findViewById(R.id.iv_painting);
        Picasso.Builder picassoBuilder = new Picasso.Builder(this);

        Picasso picasso = picassoBuilder.listener(new Picasso.Listener() {
            @Override
            public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
                Log.d("PopularMovies Picasso", exception.toString());
            }
        }).build();

        picasso.load("http://i.imgur.com/DvpvklR.png")
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher_round)
                .into(paintingImageView);

        URL url = NetworkUtils.buildPopularQueryUrl();
        new MovieLoad().execute(url);
    }

    private class MovieLoad extends AsyncTask<URL, Void, String>{
        @Override
        protected String doInBackground(URL... params) {
            URL searchUrl = params[0];
            String httpResult = null;
            try {
                httpResult = NetworkUtils.getResponseFromHttpUrl(searchUrl);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return httpResult;
        }

        // COMPLETED (3) Override onPostExecute to display the results in the TextView
        @Override
        protected void onPostExecute(String httpResult) {
            if (httpResult != null && !httpResult.equals("")) {
                //mSearchResultsTextView.setText(githubSearchResults);
                Log.d("JSONpile", httpResult);
            }
            else {
                Log.d("JSONpile", "Blank or null results");
            }
        }
    }
}
