package com.mal.android.popularmoviesapp.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by toshiba1 on 8/12/2016.
 */
public class Movies  implements Parcelable{


    boolean adult;
    String backdrop_path;
//    int []  genre_ids;
    int id;
    String original_language;
    String overview;
    float popularity;
    String poster_path;
    String release_date;
    String title;
    boolean video;
    float vote_average;
    int vote_count;


    public Movies(Parcel in) {
        backdrop_path = in.readString();
        original_language = in.readString();
        overview = in.readString();
        popularity = in.readFloat();
        poster_path = in.readString();
        release_date = in.readString();
        title = in.readString();
        vote_average = in.readFloat();

        id=in.readInt();
        vote_count=in.readInt();

        adult= in.readByte() != 0;
        video= in.readByte() != 0;


//        genre_ids=in.readIntArray(genre_ids);
    }

    public static final Creator<Movies> CREATOR = new Creator<Movies>() {
        @Override
        public Movies createFromParcel(Parcel in) {
            return new Movies(in);
        }

        @Override
        public Movies[] newArray(int size) {
            return new Movies[size];
        }
    };

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(backdrop_path);
        parcel.writeString(original_language);
        parcel.writeString(overview);
        parcel.writeFloat(popularity);
        parcel.writeString(poster_path);
        parcel.writeString(release_date);
        parcel.writeString(title);
        parcel.writeFloat(vote_average);

        parcel.writeInt(id);
        parcel.writeInt(vote_count);

        parcel.writeByte((byte) (adult ? 1 : 0));
        parcel.writeByte((byte) (video ? 1 : 0));


//        parcel.writeIntArray(genre_ids);
    }


    public boolean isAdult() {
        return adult;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }


    public String getBackdrop_path() {
        return backdrop_path;
    }

    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

//    public int [] getGenre_ids() {
//        return genre_ids;
//    }
//
//    public void setGenre_ids(int [] genre_ids) {
//        this.genre_ids = genre_ids;
//    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOriginal_language() {
        return original_language;
    }

    public void setOriginal_language(String original_language) {
        this.original_language = original_language;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public float getPopularity() {
        return popularity;
    }

    public void setPopularity(float popularity) {
        this.popularity = popularity;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isVideo() {
        return video;
    }

    public void setVideo(boolean video) {
        this.video = video;
    }

    public float getVote_average() {
        return vote_average;
    }

    public void setVote_average(float vote_average) {
        this.vote_average = vote_average;
    }

    public Integer getVote_count() {
        return vote_count;
    }

    public void setVote_count(Integer vote_count) {
        this.vote_count = vote_count;
    }

    @Override
    public int describeContents() {
        return 0;
    }

}
