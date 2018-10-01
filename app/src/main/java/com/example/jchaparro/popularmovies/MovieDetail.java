package com.example.jchaparro.popularmovies;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.v7.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

public class MovieDetail extends AppCompatActivity{
    private Movie mMovie;
    private TextView mMovieTitle;
    private ImageView mMoviePoster;
    private TextView mMovieSynopsis;
    private TextView mMovieRating;
    private TextView mMovieDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_detail);

        mMovieTitle = (TextView) findViewById(R.id.tv_movie_title);
        mMoviePoster = (ImageView) findViewById(R.id.iv_poster_detail);
        mMovieSynopsis = (TextView) findViewById(R.id.tv_synopsis);
        mMovieRating = (TextView) findViewById(R.id.tv_rating);
        mMovieDate = (TextView) findViewById(R.id.tv_release_date);

        Intent intentThatStartedThisActivity = getIntent();

        // COMPLETED (2) Display the weather forecast that was passed from MainActivity
        if (intentThatStartedThisActivity != null) {
            if (intentThatStartedThisActivity.hasExtra("Movie")) {
                //mForecast = intentThatStartedThisActivity.getStringExtra(Intent.EXTRA_TEXT);
                mMovie = intentThatStartedThisActivity.getParcelableExtra("Movie");
                mMovieTitle.setText(mMovie.getTitle());
                mMovieSynopsis.setText("Synopsis: " + mMovie.getSynopsis());
                mMovieRating.setText("Rating: " + String.valueOf(mMovie.getRating()));
                mMovieDate.setText("Release Date: " + mMovie.getDate());

                Picasso.Builder picassoBuilder = new Picasso.Builder(this);

                Picasso picasso = picassoBuilder.listener(new Picasso.Listener() {
                    @Override
                    public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
                        Log.d("PopularMovies Picasso", exception.toString());
                    }
                }).build();
                String path = "https://image.tmdb.org/t/p/w500" + mMovie.getPoster();
                picasso.load(path)
                        .placeholder(R.mipmap.ic_launcher)
                        .error(R.mipmap.ic_launcher_round)
                        .into(mMoviePoster);
            }
        }
    }
}
