package com.mal.android.popularmoviesapp;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Parcel;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.google.gson.Gson;
import com.mal.android.popularmoviesapp.adapter.MovieListGridAdapter;
import com.mal.android.popularmoviesapp.backend.AsyncTaskListener;
import com.mal.android.popularmoviesapp.backend.Connector;
import com.mal.android.popularmoviesapp.backend.DatabaseHelper;
import com.mal.android.popularmoviesapp.backend.FetchMovieData;
import com.mal.android.popularmoviesapp.backend.MovieClickListener;
import com.mal.android.popularmoviesapp.model.Movies;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class ActivityMainFragment extends Fragment implements AsyncTaskListener {

    private static final String TAG = ActivityMainFragment.class.getSimpleName();
    private static final String SAVED_INSTANCE_DATA = "SAVED_INSTANCE_DATA";
    private static final String SHARED_SAVED = "SHARED_SAVED";

    private Movies savedInstanceData;
    private RecyclerView movieRecyclerView;
    private GridView movieGridView;
    MovieClickListener clickListener;
    private boolean twoPaneUI;
    private ArrayList<Movies> movieDataList;
    private int lastSavedPosition;
    private Movies dataSelected;

    public ActivityMainFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (savedInstanceState != null && savedInstanceData == null) {
            savedInstanceData = savedInstanceState.getParcelable(SAVED_INSTANCE_DATA);
        } else {
            lastSavedPosition = 0;
        }


        View v = inflater.inflate(R.layout.fragment_main, container, false);

//        // using a Grid layout to view posters
        movieGridView = (GridView) v.findViewById(R.id.grid_view_movie);

        return v;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        if (Connector.IsConnected(getActivity().getApplicationContext())) {

            //Using sharedPrefernces to get the lastClick saved
            SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
            String defaultValue = sharedPref.getString(SHARED_SAVED, null);

            Log.v(TAG, "sharedPref: " + defaultValue);

            //Check if there is shared Data or not
            if (defaultValue != null) {
                FetchMovieData fetchMovieData = new FetchMovieData(this);
                fetchMovieData.execute(Connector.getBuilder(defaultValue, null));
            } else {
                FetchMovieData fetchMovieData = new FetchMovieData(this);
                fetchMovieData.execute(Connector.getBuilder("popular", null));
            }

        } else {
            //TODO:Create popup message
            Log.v(TAG, "No network connection available.");
        }
    }

    public void message(String s) {
        Log.v(TAG, "message: " + s);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (Connector.IsConnected(getActivity().getApplicationContext())) {

            if (id == R.id.action_popular) {
                FetchMovieData fetchMovieData = new FetchMovieData(this);
                fetchMovieData.execute(Connector.getBuilder("popular", null));

                //Using sharedPrefernce to save the lastClick
                sharedPreference("popular");
                return true;

            } else if (id == R.id.action_rate) {
                FetchMovieData fetchMovieData = new FetchMovieData(this);
                fetchMovieData.execute(Connector.getBuilder("top_rated", null));

                //Using sharedPrefernce to save the lastClick
                sharedPreference("top_rated");
                return true;

            } else if (id == R.id.action_favorite) {

                //Here we call openDbhelper to use it to get favorite movies
                DatabaseHelper dataBase = new DatabaseHelper(getActivity());
                Cursor cursor = dataBase.getMovies();


                //Initialize arrayList if null
                if (null == movieDataList) {
                    movieDataList = new ArrayList<Movies>();
                }


                // looping through all rows and adding to list
                if (cursor.moveToFirst()) {
                //In case there are data in db we clear the list to add the new list
                    movieDataList.clear();
                    do {
                        Log.v(TAG, "DB: " + cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_TITLE)));

                        //here we create a new instance of Movies & added it to movieDataList
                        Movies movie = new Movies(Parcel.obtain());
                        movie.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_ID))));
                        movie.setTitle(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_TITLE)));
                        movie.setPoster_path(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_PATH)));
                        movie.setOverview(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_OVERVIEW)));
                        movie.setRelease_date(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_DATE)));


//                        // Adding contact to list
                        movieDataList.add(movie);

                    } while (cursor.moveToNext());
                }

                //using gridlist adapter with gridview
                MovieListGridAdapter movieListGridadapter = new MovieListGridAdapter(getActivity(), movieDataList);
                movieGridView.setAdapter(movieListGridadapter);
            }


        } else {
            //TODO:Create popup message
            Log.v(TAG, "No network connection available.");
        }


        return super.onOptionsItemSelected(item);
    }

    private void sharedPreference(String sharedPreferenceTitle) {
        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(SHARED_SAVED, sharedPreferenceTitle);
        editor.commit();
    }

    @Override
    public void notifyUpdate(String data) {

        Log.v(TAG, "notify update: " + data);
        Log.v(TAG, "Implementing Interface to use AsyncTASK: " + data);

        //Initialize arrayList if null
        if (null == movieDataList) {
            movieDataList = new ArrayList<Movies>();
        }


        try {
            JSONObject result = new JSONObject(data);
            JSONArray results = result.getJSONArray("results");
            movieDataList.clear();

            for (int k = 0; k < results.length(); k++) {
                JSONObject movieObj = new JSONObject(results.get(k).toString());

                //using GSON library
                Gson gson = new Gson();
                Movies movieJson = gson.fromJson(String.valueOf(movieObj), Movies.class);

                movieDataList.add(movieJson);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //using gridlist adapter with gridview
        MovieListGridAdapter movieListGridadapter = new MovieListGridAdapter(getActivity(), movieDataList);
        movieGridView.setAdapter(movieListGridadapter);


        //We check if it is two pane UI
        if (ActivityMain.mTwoPane) {
            //Check data from backend
            if (movieDataList != null) {
                //Check if there are no savedinstance or not
                if (savedInstanceData == null) {

                    //As savedinstance is null we get first position in my list
                    savedInstanceData = movieDataList.get(lastSavedPosition);
                    choosedData(savedInstanceData, lastSavedPosition);

                } else {
                    //As there is savedinstancedata there is a position clicked
                    choosedData(savedInstanceData, lastSavedPosition);
                }
            }
        }
    }

    @Override
    public void notifyUpdateReviews(String data) {

    }

    @Override
    public void notifyUpdateVidoes(String data) {

    }

    public void choosedData(final Movies parsedMoviesDataList, int lastSavedPosition) {
        //Here we check clickListener implemented by MainActivity
        if (clickListener != null) {
            clickListener.movieDataSelected(parsedMoviesDataList);
            this.lastSavedPosition = lastSavedPosition;
            dataSelected = parsedMoviesDataList;
        } else {
            Log.v(TAG, "cLICKlISTEBER IS NULL");
        }
    }

    //create set method for the used listener
    public void setMovieListener(MovieClickListener movieClickListener) {
        clickListener = movieClickListener;
    }

    //onsaveInstanceState
    //save selected item
    @Override
    public void onSaveInstanceState(Bundle outState) {

        outState.putParcelable(SAVED_INSTANCE_DATA, dataSelected);
        super.onSaveInstanceState(outState);
    }

    public void setIfTwoPane(boolean twoPane) {
        twoPaneUI = twoPane;
    }
}
