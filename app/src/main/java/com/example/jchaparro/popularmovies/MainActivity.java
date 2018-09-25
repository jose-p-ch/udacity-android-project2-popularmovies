package com.example.jchaparro.popularmovies;

import android.content.Context;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

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
                Log.d("PopularMovies - Picasso Log: ", exception.toString());
            }
        }).build();

        picasso.load("http://i.imgur.com/DvpvklR.png")
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher_round)
                .into(paintingImageView);
    }
}
