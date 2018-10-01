package com.example.jchaparro.popularmovies;

import android.os.Parcel;
import android.os.Parcelable;

public class Movie implements Parcelable {
    private String title;
    private String poster;
    private String synopsis;
    private double rating;
    private String date;

    protected Movie (String title, String poster, String synopsis, double rating, String date){
        this.title = title;
        this.poster = poster;
        this.synopsis = synopsis;
        this.rating = rating;
        this.date = date;
    }

    public Movie(Parcel in) {
        title = in.readString();
        poster = in.readString();
        synopsis = in.readString();
        rating = in.readDouble();
        date = in.readString();
    }

    public boolean setTitle(String s){
        if(s != null && !s.equals("")){
            title = s;
            return true;
        }
        return false;
    }

    public boolean setPoster(String s){
        if(s != null && !s.equals("")){
            poster = s;
            return true;
        }
        return false;
    }

    public boolean setSynopsis(String s){
        if(s != null && !s.equals("")){
            synopsis = s;
            return true;
        }
        return false;
    }

    public boolean setRating(double d){
            rating = d;
            return true;
    }

    public boolean setDate(String s){
        if(s != null && !s.equals("")){
            date = s;
            return true;
        }
        return false;
    }

    protected String getTitle(){
        return title;
    }

    protected String getPoster() {
        return poster;
    }

    protected String getSynopsis() {
        return synopsis;
    }

    double getRating() {
        return rating;
    }

    protected String getDate() {
        return date;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(poster);
        dest.writeString(synopsis);
        dest.writeDouble(rating);
        dest.writeString(date);
    }

    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {

        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
}
