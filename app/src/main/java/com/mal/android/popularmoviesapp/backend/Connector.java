package com.mal.android.popularmoviesapp.backend;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;

import com.mal.android.popularmoviesapp.BuildConfig;

/**
 * Created by Alaa Gaber on 9/12/2016.
 */
public class Connector {

    public static Uri.Builder getBuilder(String parameter, Integer id) {

        //Using Uri.Builder to connect to the URL="
//            https://api.themoviedb.org/3/movie/popular?api_key="
//        https://api.themoviedb.org/3/movie/278924/videos?api_key=


        Uri.Builder builder = new Uri.Builder();

        if (id == null) {
            builder.scheme("https")
                    .authority("api.themoviedb.org")
                    .appendPath("3")
                    .appendPath("movie")
                    .appendPath(parameter)
                    .appendQueryParameter("api_key", BuildConfig.MOVIE_APP_DB_API_KEY)
                    .build();
        } else {
            builder.scheme("https")
                    .authority("api.themoviedb.org")
                    .appendPath("3")
                    .appendPath("movie")
                    .appendPath(String.valueOf(id))
                    .appendPath(parameter)
                    .appendQueryParameter("api_key", BuildConfig.MOVIE_APP_DB_API_KEY)
                    .build();
        }
        return builder;
    }

    public static boolean IsConnected(Context context) {

        ConnectivityManager connMgr = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            return networkInfo.isConnected();
        }else {
            return false;
        }
    }
}
