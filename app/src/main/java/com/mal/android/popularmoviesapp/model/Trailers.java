package com.mal.android.popularmoviesapp.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Alaa Gaber on 9/12/2016.
 */

public class Trailers extends TRbase {

//    {"id":"5794f51392514179d2004179","iso_639_1":"en","iso_3166_1":"US","key":"QF903RaKLvs","name":"Official Trailer","site":"YouTube","size":1080,"type":"Trailer"}]}


    String id;
    String iso_639_1;
    String iso_3166_1;
    String key;
    String name;
    String site;
    String size;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIso_639_1() {
        return iso_639_1;
    }

    public void setIso_639_1(String iso_639_1) {
        this.iso_639_1 = iso_639_1;
    }

    public String getIso_3166_1() {
        return iso_3166_1;
    }

    public void setIso_3166_1(String iso_3166_1) {
        this.iso_3166_1 = iso_3166_1;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    String type;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Trailers(Parcel in) {
        super(in);
        id = in.readString();
        iso_639_1 = in.readString();
        iso_3166_1 = in.readString();
        key = in.readString();
        name = in.readString();
        site = in.readString();
        size = in.readString();
        type = in.readString();

    }

    public static final Creator<Trailers> CREATOR = new Creator<Trailers>() {
        @Override
        public Trailers createFromParcel(Parcel in) {
            return new Trailers(in);
        }

        @Override
        public Trailers[] newArray(int size) {
            return new Trailers[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(iso_639_1);
        parcel.writeString(iso_3166_1);
        parcel.writeString(key);
        parcel.writeString(name);
        parcel.writeString(site);
        parcel.writeString(size);
        parcel.writeString(type);

    }
}
