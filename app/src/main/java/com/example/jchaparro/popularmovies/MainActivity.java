package com.example.jchaparro.popularmovies;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;

public class MainActivity extends AppCompatActivity implements MovieAdapter.GridItemClickListener{

    private int page = 1;
    private String sort_by = "popular"; //or vote_average.desc
    private int posterWidth = 400;

    private TextView mErrorMessageDisplay;

    private RecyclerView mRecyclerViewPosters;
    private MovieAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mErrorMessageDisplay = (TextView) findViewById(R.id.tv_error_message_display);

        mRecyclerViewPosters = (RecyclerView) findViewById(R.id.rv_posters);
        GridLayoutManager posterLayoutManager = new GridLayoutManager(this, calculateBestSpanCount(posterWidth));
        mRecyclerViewPosters.setLayoutManager(posterLayoutManager);
        mRecyclerViewPosters.setHasFixedSize(true);

        mAdapter = new MovieAdapter(this);
        mRecyclerViewPosters.setAdapter(mAdapter);

        loadPosterData();
    }

    void loadPosterData(){
        URL url = NetworkUtils.buildPopularQueryUrl(String.valueOf(page), sort_by);
        ConnectivityManager cm =
                (ConnectivityManager)(this).getSystemService(Context.CONNECTIVITY_SERVICE);

        @SuppressLint("MissingPermission")
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnected();
        Log.d("NetworkCheck", String.valueOf(isConnected));
        if(isConnected) {
            new MovieLoad().execute(url);
            showRecyclerView();
        }
        else {
            showErrorMessage();
        }
    }

    private void showErrorMessage() {
        /* First, hide the currently visible data */
        mRecyclerViewPosters.setVisibility(View.INVISIBLE);
        /* Then, show the error */
        mErrorMessageDisplay.setVisibility(View.VISIBLE);
    }

    private void showRecyclerView() {
        mRecyclerViewPosters.setVisibility(View.VISIBLE);
        mErrorMessageDisplay.setVisibility(View.INVISIBLE);
    }

    private int calculateBestSpanCount(int posterWidth) {
        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);
        float screenWidth = outMetrics.widthPixels;
        return Math.round(screenWidth / posterWidth);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int itemId = item.getItemId();

        switch (itemId) {
            case R.id.action_nextpage:
                page++;
                loadPosterData();
                return true;
            case R.id.action_previouspage:
                if(page > 1){
                    page--;
                loadPosterData();
                }
                return true;
            case R.id.action_votesort:
                sort_by = "top_rated";
                page = 1;
                loadPosterData();
                return true;
            case R.id.action_popularsort:
                sort_by = "popular";
                page = 1;
                loadPosterData();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onGridItemClick(Movie movieClicked) {
        Context context = this;
        Class destinationClass = MovieDetail.class;
        Intent intentToStartDetailActivity = new Intent(context, destinationClass);

        intentToStartDetailActivity.putExtra("Movie", movieClicked);
        startActivity(intentToStartDetailActivity);
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

        @Override
        protected void onPostExecute(String httpResult) {
            if (httpResult != null && !httpResult.equals("")) {
                String jsonString = httpResult;
                try {
                    mAdapter.setMovieArray(JsonUtils.parseJsonResults(jsonString));
                    int width = mRecyclerViewPosters.getMeasuredWidth();
                    mAdapter.setWidth(width);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            else {
                Log.d("JSONpile", "Blank or null results");
                showErrorMessage();
            }
        }
    }
}
