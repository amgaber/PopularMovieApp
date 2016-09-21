package com.mal.android.popularmoviesapp.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Alaa Gaber on 9/16/2016.
 */

public class Reviews extends TRbase {
//    {"id":"5794f51392514179d2004179","iso_639_1":"en","iso_3166_1":"US","key":"QF903RaKLvs","name":"Official Trailer","site":"YouTube","size":1080,"type":"Trailer"}]}


    String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    String author;
    String content;
    String url;


    public Reviews(Parcel in) {
        super(in);
        id = in.readString();
        author = in.readString();
        content = in.readString();
        url = in.readString();
    }

    public static final Creator<Reviews> CREATOR = new Creator<Reviews>() {
        @Override
        public Reviews createFromParcel(Parcel in) {
            return new Reviews(in);
        }

        @Override
        public Reviews[] newArray(int size) {
            return new Reviews[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(author);
        parcel.writeString(content);
        parcel.writeString(url);
    }


}
