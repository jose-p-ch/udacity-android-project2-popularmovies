package com.example.jchaparro.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import android.support.v7.widget.RecyclerView;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;

public class MainActivity extends AppCompatActivity implements MovieAdapter.GridItemClickListener{

    //private String jsonString;
    private int page = 1;
    private String sort_by = "popularity.desc"; //or vote_average.desc
    //ArrayList<String> moviesJson;

    private RecyclerView posterGrid;
    private MovieAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        posterGrid = (RecyclerView) findViewById(R.id.rv_posters);
        GridLayoutManager posterLayoutManager = new GridLayoutManager(this, 3);
        posterGrid.setLayoutManager(posterLayoutManager);
        posterGrid.setHasFixedSize(true);
        //int width = posterGrid.getMeasuredWidth();

        mAdapter = new MovieAdapter(this);
        posterGrid.setAdapter(mAdapter);

        loadPosterData();
    }

    void loadPosterData(){
        URL url = NetworkUtils.buildPopularQueryUrl(String.valueOf(page), sort_by);
        new MovieLoad().execute(url);
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
            /*
             * When you click the reset menu item, we want to start all over
             * and display the pretty gradient again. There are a few similar
             * ways of doing this, with this one being the simplest of those
             * ways. (in our humble opinion)
             */
            case R.id.action_nextpage:
                // COMPLETED (14) Pass in this as the ListItemClickListener to the GreenAdapter constructor
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
                sort_by = "vote_average.desc";
                page = 1;
                loadPosterData();
                return true;
            case R.id.action_popularsort:
                sort_by = "popularity.desc";
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
        // COMPLETED (1) Pass the weather to the DetailActivity
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

        // COMPLETED (3) Override onPostExecute to display the results in the TextView
        @Override
        protected void onPostExecute(String httpResult) {
            if (httpResult != null && !httpResult.equals("")) {
                //mSearchResultsTextView.setText(githubSearchResults);
                String jsonString = httpResult;
                //defaultTextView.setText(jsonString);
                //Log.d("JSONpile", httpResult);
                try {
                    //moviesJson = ;
                    mAdapter.setMovieArray(JsonUtils.parseJsonResults(jsonString));
                    int width = posterGrid.getMeasuredWidth();
                    mAdapter.setWidth(width);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            else {
                Log.d("JSONpile", "Blank or null results");
            }
        }
    }
}
