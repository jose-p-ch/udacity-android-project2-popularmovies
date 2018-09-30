package com.example.jchaparro.popularmovies;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.v7.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;

public class MainActivity extends AppCompatActivity implements MovieAdapter.GridItemClickListener{

    String jsonString;
    int page = 1;
    String sort_by = "popularity.desc"; //or vote_average.desc

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
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onListItemClick(int clickedItemIndex) {

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
                jsonString = httpResult;
                //defaultTextView.setText(jsonString);
                //Log.d("JSONpile", httpResult);
                try {
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
