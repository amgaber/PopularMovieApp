package com.mal.android.popularmoviesapp.adapter;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Parcel;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.mal.android.popularmoviesapp.ActivityMain;
import com.mal.android.popularmoviesapp.ActivityMainFragment;
import com.mal.android.popularmoviesapp.DetailsActivity;
import com.mal.android.popularmoviesapp.R;
import com.mal.android.popularmoviesapp.backend.Connector;
import com.mal.android.popularmoviesapp.backend.MovieClickListener;
import com.mal.android.popularmoviesapp.model.Movies;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alaa Gaber on 8/31/2016.
 */
public class MovieListGridAdapter extends BaseAdapter {

    private static final String TAG = MovieListGridAdapter.class.getSimpleName();
    public static final String MOVIE_OBJECT = "MOVIE_OBJECT";
    private final List<Movies> parsedMoviesDataList;
    private final Activity activity;



    public MovieListGridAdapter(Activity activity, List<Movies> moviesData) {
        this.parsedMoviesDataList = moviesData;
        this.activity = activity;
        Log.v(TAG, "JSON LIST: " + moviesData);
    }

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEnabled(int i) {
        return false;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver dataSetObserver) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver dataSetObserver) {

    }

    @Override
    public int getCount() {
        return parsedMoviesDataList.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getView(final int i, View convertView, ViewGroup viewGroup) {
        View grid;


        if (convertView == null) {

            grid = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.movie_grid_list_row, viewGroup, false);
            ImageView imgPoster = (ImageView) grid.findViewById(R.id.imgPoster);


            Movies movieData = parsedMoviesDataList.get(i);
            String posterPath = movieData.getPoster_path();
            String base_url = "http://image.tmdb.org/t/p/w185/";
            String imgPosterString = base_url + posterPath;
            Picasso.with(activity).load(imgPosterString)
                    .placeholder(R.drawable.abc_ic_star_black_36dp)
                    .error(R.drawable.abc_ic_star_black_36dp).into(imgPoster);

            final Movies movies=new Movies(Parcel.obtain());
            final ArrayList<Movies> listToPass = new ArrayList<Movies>();
            listToPass.add(movies);

            imgPoster.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (Connector.IsConnected(activity.getApplicationContext())) {
                        //getFragment by id to set clickListener
                        ActivityMainFragment fragment = (ActivityMainFragment) activity.getFragmentManager().findFragmentById(R.id.fragment);
                        fragment.choosedData(parsedMoviesDataList.get(i), i);
                    }else {
                        //popup alert when there is No connection
                        Log.v(TAG, "No network connection available.");
                        openAlertDialogConnectionError();
                    }
                }
            });

        } else {
            grid = (View) convertView;
        }

        return grid;
    }

    private void openAlertDialogConnectionError() {
        new AlertDialog.Builder(activity)
                .setTitle("Connection Error")
                .setMessage("No network connection available!")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

}
