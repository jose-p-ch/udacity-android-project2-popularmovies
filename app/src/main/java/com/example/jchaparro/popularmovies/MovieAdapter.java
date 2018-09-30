package com.example.jchaparro.popularmovies;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.View.OnClickListener;

import com.squareup.picasso.Picasso;

import org.json.JSONException;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder>{
    private static final String TAG = MovieAdapter.class.getSimpleName();
    final private GridItemClickListener mOnClickListener;
    Movie[] movieArray;
    int width;

    public interface GridItemClickListener {
        void onListItemClick(int clickedItemIndex);
    }

    public MovieAdapter (GridItemClickListener listener){
        //mNumberItems = numberOfItems;
        mOnClickListener = listener;
        //viewHolderCount = 0;
        //width = w;
        //Log.d("RV width", String.valueOf(width));
    }
    public void setWidth(int w){
        width = w;
        Log.d("RV width", String.valueOf(width));
    }

    public void setMovieArray(List<String> movies){
        movieArray = new Movie[movies.size()];
        for(int i = 0; i < movieArray.length; i++){
            try {
                movieArray[i] = JsonUtils.parseMovieJson(movies.get(i));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        notifyDataSetChanged();
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForGridItem = R.layout.poster_grid_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachImmediately = false;

        View view = inflater.inflate(layoutIdForGridItem, parent, shouldAttachImmediately);

        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        int targetWidth = 300;
        if(width > 0)
            targetWidth = width / 3;
        int targetHegiht = targetWidth * 278 / 185;
        Picasso.Builder picassoBuilder = new Picasso.Builder(holder.posterView.getContext());

        Picasso picasso = picassoBuilder.listener(new Picasso.Listener() {
            @Override
            public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
                Log.d("PopularMovies Picasso", exception.toString());
            }
        }).build();
        String path = "https://image.tmdb.org/t/p/w185" + movieArray[position].getPoster();
        picasso.load(path)
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher_round)
                .resize(targetWidth,targetHegiht)
                .into(holder.posterView);
    }

    @Override
    public int getItemCount() {
        if(movieArray == null)
            return 0;
        return movieArray.length;
    }

    class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        //public TextView movieString;
        public ImageView posterView;

        public MovieViewHolder(View v){
            super(v);
            //movieString = (TextView) v.findViewById(R.id.tv_movie_string);
            posterView = (ImageView) v.findViewById(R.id.iv_poster);
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

        }
    }
}
