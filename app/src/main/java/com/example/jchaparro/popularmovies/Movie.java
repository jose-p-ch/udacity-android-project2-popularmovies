package com.example.jchaparro.popularmovies;

import android.os.Parcel;
import android.os.Parcelable;

public class Movie implements Parcelable {
    private String mTitle;
    private String mPoster;
    private String mSynopsis;
    private double mRating;
    private String mDate;

    protected Movie (String title, String poster, String synopsis, double rating, String date){
        this.mTitle = title;
        this.mPoster = poster;
        this.mSynopsis = synopsis;
        this.mRating = rating;
        this.mDate = date;
    }

    public Movie(Parcel in) {
        mTitle = in.readString();
        mPoster = in.readString();
        mSynopsis = in.readString();
        mRating = in.readDouble();
        mDate = in.readString();
    }

    public boolean setTitle(String s){
        if(s != null && !s.equals("")){
            mTitle = s;
            return true;
        }
        return false;
    }

    public boolean setPoster(String s){
        if(s != null && !s.equals("")){
            mPoster = s;
            return true;
        }
        return false;
    }

    public boolean setSynopsis(String s){
        if(s != null && !s.equals("")){
            mSynopsis = s;
            return true;
        }
        return false;
    }

    public boolean setRating(double d){
            mRating = d;
            return true;
    }

    public boolean setDate(String s){
        if(s != null && !s.equals("")){
            mDate = s;
            return true;
        }
        return false;
    }

    protected String getTitle(){
        return mTitle;
    }

    protected String getPoster() {
        return mPoster;
    }

    protected String getSynopsis() {
        return mSynopsis;
    }

    double getRating() {
        return mRating;
    }

    protected String getDate() {
        return mDate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mTitle);
        dest.writeString(mPoster);
        dest.writeString(mSynopsis);
        dest.writeDouble(mRating);
        dest.writeString(mDate);
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
