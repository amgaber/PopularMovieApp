package com.mal.android.popularmoviesapp;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.mal.android.popularmoviesapp.adapter.MovieListGridAdapter;
import com.mal.android.popularmoviesapp.backend.AsyncTaskListener;
import com.mal.android.popularmoviesapp.backend.FetchMovieData;
import com.mal.android.popularmoviesapp.backend.MovieClickListener;
import com.mal.android.popularmoviesapp.model.Movies;


public class ActivityMain extends AppCompatActivity implements MovieClickListener {

    private static final String DETAILFRAGMENT_TAG = "DETAILFRAGMENT";
    private static final String TAG = ActivityMain.class.getSimpleName();
    private static final String SAVED_INSTANCE_DATA_MAIN = "SAVED_INSTANCE_DATA_MAIN";
    public static boolean mTwoPane = false;
    private Parcelable savedInstanceDataMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (findViewById(R.id.movie_detail_container) != null) {
            // The detail container view will be present only in the layout-sw600dp
            //  If this view is present, then the activity is  in two-pane mode.
            mTwoPane = true;

//            ActivityMainFragment fragment = new ActivityMainFragment();


        } else {
            mTwoPane = false;

        }

        if (savedInstanceState == null) {
            //send listener to fragment to use it onclick
            ActivityMainFragment fragment = new ActivityMainFragment();
            fragment.setMovieListener(this);
            fragment.setIfTwoPane(mTwoPane);
            getFragmentManager().beginTransaction().replace(R.id.fragment, fragment).commit();

        } else if (savedInstanceState != null) {
            savedInstanceDataMain = savedInstanceState.getParcelable(SAVED_INSTANCE_DATA_MAIN);
            ActivityMainFragment fragment= (ActivityMainFragment) getFragmentManager().findFragmentById(R.id.fragment);
            fragment.setMovieListener(this);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void movieDataSelected(Movies data) {
        Log.v(TAG, "MOVIE data selected Interface: " + data);

        //In case TWO PANE UI
        if (mTwoPane) {

//                savedInstanceDataMain = data;
            //send data using Parcelable
            Bundle detailsDataBundle = new Bundle();
            detailsDataBundle.putParcelable(MovieListGridAdapter.MOVIE_OBJECT, data);
            DetailsActivityFragment detailsActivityFragment = new DetailsActivityFragment();
            detailsActivityFragment.setArguments(detailsDataBundle);
            getFragmentManager().beginTransaction().replace(R.id.movie_detail_container, detailsActivityFragment).commit();
        } else {
            //In case Single PANE UI
            Intent detailsMovies = new Intent(this, DetailsActivity.class);
            detailsMovies.putExtra(MovieListGridAdapter.MOVIE_OBJECT, data);
            startActivity(detailsMovies);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(SAVED_INSTANCE_DATA_MAIN, savedInstanceDataMain);
        super.onSaveInstanceState(outState);
    }

}
