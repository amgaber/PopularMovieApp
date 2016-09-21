package com.mal.android.popularmoviesapp.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by toshiba1 on 9/16/2016.
 */
public class TRbase implements Parcelable {
    protected TRbase(Parcel in) {
    }

    public static final Creator<TRbase> CREATOR = new Creator<TRbase>() {
        @Override
        public TRbase createFromParcel(Parcel in) {
            return new TRbase(in);
        }

        @Override
        public TRbase[] newArray(int size) {
            return new TRbase[size];
        }
    };



    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
    }
}
