package com.example.jchaparro.popularmovies;

public class Movie {
    private String title;
    private String poster;
    private String synopsis;
    private double rating;
    private String date;

    public Movie (String title, String poster, String synopsis, double rating, String date){
        this.title = title;
        this.poster = poster;
        this.synopsis = synopsis;
        this.rating = rating;
        this.date = date;
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

    public String getTitle(){
        return title;
    }

    public String getPoster() {
        return poster;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public double getRating() {
        return rating;
    }

    public String getDate() {
        return date;
    }
}
