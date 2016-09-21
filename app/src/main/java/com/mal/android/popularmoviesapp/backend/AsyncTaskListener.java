package com.mal.android.popularmoviesapp.backend;

/**
 * Created by Alaa Gaber on 7/26/2016.
 */
public interface AsyncTaskListener {

    void notifyUpdate(String data);

    void notifyUpdateReviews(String data);

    void notifyUpdateVidoes(String data);
}
