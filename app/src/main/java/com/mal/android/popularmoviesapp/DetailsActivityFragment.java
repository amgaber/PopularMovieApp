package com.mal.android.popularmoviesapp;

import android.app.Fragment;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.mal.android.popularmoviesapp.adapter.DetailsListAdapter;
import com.mal.android.popularmoviesapp.adapter.MovieListGridAdapter;
import com.mal.android.popularmoviesapp.adapter.ReviewListAdapter;
import com.mal.android.popularmoviesapp.adapter.TrailerListAdapter;
import com.mal.android.popularmoviesapp.backend.AsyncTaskListener;
import com.mal.android.popularmoviesapp.backend.Connector;
import com.mal.android.popularmoviesapp.backend.DatabaseHelper;
import com.mal.android.popularmoviesapp.backend.FetchDetailsData;
import com.mal.android.popularmoviesapp.backend.FetchMovieData;
import com.mal.android.popularmoviesapp.model.Movies;
import com.mal.android.popularmoviesapp.model.Reviews;
import com.mal.android.popularmoviesapp.model.TRbase;
import com.mal.android.popularmoviesapp.model.Trailers;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class DetailsActivityFragment extends Fragment implements AsyncTaskListener, View.OnClickListener {

    private static final String TAG = DetailsActivityFragment.class.getSimpleName();
    public static final String DETAILS_VIDEOS = "videos";
    public static final String DETAILS_REVIEWS = "reviews";
    private String TYPE_OF_DETAIL = null;
    private ListView detailsDataList;
    private DatabaseHelper dataBase;
    private Movies detailData;
    private Button btn_add_to_fav;
    private ListView listOne;
    private ListView listTwo;
    private RelativeLayout trailerHeaderLayout;
    private RelativeLayout reviewHeaderLayout;


    public DetailsActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        View v = inflater.inflate(R.layout.fragment_details, container, false);
        View v = inflater.inflate(R.layout.fragment_details_main_list, container, false);
        detailsDataList = (ListView) v.findViewById(R.id.details_listView_movie);
//        listOne = (ListView) v.findViewById(R.id.listView1);
        listTwo = (ListView) v.findViewById(R.id.listView2);

        //Here we get bundle sent from grid click with movie details
        Bundle bundle = getArguments();
        Parcelable details = null;
        if (bundle != null) {
            //Using Parcelable class to get data from bundle & then cast it Movies
            details = bundle.getParcelable(MovieListGridAdapter.MOVIE_OBJECT);
            Log.v(TAG, "Details Data:: " + details);

        }

        //Here we call openDbhelper to use it to save favorite movies
        dataBase = new DatabaseHelper(getActivity());

        detailData = (Movies) details;
        Log.v(TAG, "Details Data:: " + detailData.getTitle());

        //FETCH data for reviews & videos
        fetchMovies(detailData);

        fetchReviews(detailData);

        //Here we set the layout of movie details as a header for our details page layout list
        View header = getActivity().getLayoutInflater().inflate(R.layout.fragment_details, detailsDataList, false);

        detailsDataList.addHeaderView(header, null, false);


        //here we init the header view with its data
        initView(v, detailData);


        return v;
    }

    private String fetchReviews(Movies detailData) {
//        FetchDetailsData fetchDetailsData = new FetchDetailsData(this, detailData.getId());
        FetchDetailsData fetchDetailsData = new FetchDetailsData(this, detailData.getId(),DETAILS_REVIEWS);
        fetchDetailsData.execute(Connector.getBuilder(DETAILS_REVIEWS, detailData.getId()));
        return DETAILS_REVIEWS;
    }

    private String fetchMovies(Movies detailData) {
//        FetchDetailsData fetchDetailsData = new FetchDetailsData(this, detailData.getId());
        FetchDetailsData fetchDetailsData = new FetchDetailsData(this, detailData.getId(),DETAILS_VIDEOS);
        fetchDetailsData.execute(Connector.getBuilder(DETAILS_VIDEOS, detailData.getId()));
        return DETAILS_VIDEOS;
    }

    private void initView(View v, Movies detailData) {


        ImageView imgMoviePic = (ImageView) v.findViewById(R.id.imgMoviePic);
        TextView movieTitle = (TextView) v.findViewById(R.id.movieTitle);
        TextView txtMovieDate = (TextView) v.findViewById(R.id.txtMovieDate);
        TextView txtPostRate = (TextView) v.findViewById(R.id.txtPostRate);
        TextView txtPostDiscription = (TextView) v.findViewById(R.id.txtPostDiscription);



        //WE init fav button
        btn_add_to_fav = (Button) v.findViewById(R.id.btn_add_to_fav);

        //Here we call openDbhelper to use it to check favorite movies button is clicked or not
        DatabaseHelper dataBase = new DatabaseHelper(getActivity());
        boolean isFavorite = dataBase.CheckIsDataAlreadyInDBorNot(detailData.getId());
        Log.v(TAG, "ISFAV:: " + isFavorite);
        if (isFavorite) {
            btn_add_to_fav.setText(getString(R.string.added_fav));
        } else {
            btn_add_to_fav.setText(getString(R.string.add_fav));
        }

        String posterPath = detailData.getPoster_path();
        String base_url = "http://image.tmdb.org/t/p/w154/";
        String imgPoster = base_url + posterPath;

        movieTitle.setText(detailData.getTitle());
        Picasso.with(getActivity()).load(imgPoster).centerCrop().resize(150, 150)
                .placeholder(R.drawable.abc_ic_star_black_36dp)
                .error(R.drawable.abc_ic_star_black_36dp).into(imgMoviePic);

        txtMovieDate.setText(detailData.getRelease_date());
        if (detailData.getVote_average() != 0) {
            txtPostRate.setText((int) detailData.getVote_average() + "/ 10");
        } else {
            txtPostRate.setText("0 / 10");
        }
        txtPostDiscription.setText(detailData.getOverview());

        //here we call clickListener for favButton
        btn_add_to_fav.setOnClickListener(this);
    }

    @Override
    public void notifyUpdateReviews(String data) {
        Log.v(TAG, "FETCH data::Reviews:   " + data);
//        if (TYPE_OF_DETAIL.equals(DETAILS_REVIEWS)){
        Log.v(TAG, "tYPE IS : " + TYPE_OF_DETAIL);

        ArrayList<TRbase> reviewsDataList = null;


        //Initialize arrayList if null

        if (null == reviewsDataList) {
            reviewsDataList = new ArrayList<TRbase>();
        }


        try {
            JSONObject result = new JSONObject(data);
            JSONArray results = result.getJSONArray("results");
            reviewsDataList.clear();

            for (int k = 0; k < results.length(); k++) {
                JSONObject trailerObj = new JSONObject(results.get(k).toString());

//                    //TODO:be sure to use GSON library
                Gson gson = new Gson();
                TRbase reviewsJson = gson.fromJson(String.valueOf(trailerObj), Reviews.class);

                reviewsDataList.add(reviewsJson);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        for (int k=0;k < reviewsDataList.size();k++){
            if (reviewsDataList.get(k) instanceof Trailers){
                TrailerListAdapter trailerListAdapter = new TrailerListAdapter(getActivity(),reviewsDataList);
                detailsDataList.setAdapter(trailerListAdapter);
                trailerListAdapter.notifyDataSetChanged();


            }else if (reviewsDataList.get(k) instanceof Reviews){
                ReviewListAdapter reviewListAdapter  = new ReviewListAdapter(getActivity(),reviewsDataList);
                listTwo.setAdapter(reviewListAdapter);
                reviewListAdapter.notifyDataSetChanged();

            }
        }
    }

    @Override
    public void notifyUpdateVidoes(String data) {
        Log.v(TAG, "FETCH data::Videoes:   " + data);
//        if (TYPE_OF_DETAIL.equals(DETAILS_REVIEWS)){
        Log.v(TAG, "tYPE IS : " + TYPE_OF_DETAIL);

        ArrayList<TRbase> trailerDataList = null;


        //Initialize arrayList if null

        if (null == trailerDataList) {
            trailerDataList = new ArrayList<TRbase>();
        }


        try {
            JSONObject result = new JSONObject(data);
            JSONArray results = result.getJSONArray("results");
            trailerDataList.clear();

            for (int k = 0; k < results.length(); k++) {
                JSONObject trailerObj = new JSONObject(results.get(k).toString());

                Gson gson = new Gson();
                Trailers trailerJson = gson.fromJson(String.valueOf(trailerObj), Trailers.class);

                trailerDataList.add(trailerJson);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //here we sent the rest of data e.g: reviews & retailers
        DetailsListAdapter detatailsListAdapter = new DetailsListAdapter(getActivity(), trailerDataList);
        detailsDataList.setAdapter(detatailsListAdapter);
//

    }

    @Override
    public void notifyUpdate(String data) {
//        Log.v(TAG, "FETCH data:: " + data);
////        if (TYPE_OF_DETAIL.equals(DETAILS_REVIEWS)){
//        Log.v(TAG, "tYPE IS : " + TYPE_OF_DETAIL);
//        ArrayList<TRbase> reviewsDataList = null;
//
//
//        //Initialize arrayList if null
//
//        if (null == reviewsDataList) {
//            reviewsDataList = new ArrayList<TRbase>();
//        }
//
//
//        try {
//            JSONObject result = new JSONObject(data);
//            JSONArray results = result.getJSONArray("results");
//            reviewsDataList.clear();
//
//            for (int k = 0; k < results.length(); k++) {
//                JSONObject trailerObj = new JSONObject(results.get(k).toString());
//
////                    //TODO:be sure to use GSON library
//                Gson gson = new Gson();
//                TRbase reviewsJson = gson.fromJson(String.valueOf(trailerObj), Reviews.class);
//
//                reviewsDataList.add(reviewsJson);
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        //TODO:sending data of both reviews & videos in DetailsListAdapter
//        //here we sent the rest of data e.g: reviews & retailers
//        DetailsListAdapter detatailsListAdapter = new DetailsListAdapter(getActivity(), reviewsDataList);
//        detailsDataList.setAdapter(detatailsListAdapter);

//        }else if (TYPE_OF_DETAIL.equals(DETAILS_VIDEOS)){
//            Log.v(TAG,"tYPE IS : "+TYPE_OF_DETAIL);
//            ArrayList<Trailers> trailerDataList = null;
//
//
//            //Initialize arrayList if null
//
//            if (null == trailerDataList) {
//                trailerDataList = new ArrayList<Trailers>();
//            }
//
//
//            try {
//                JSONObject result = new JSONObject(data);
//                JSONArray results = result.getJSONArray("results");
//                trailerDataList.clear();
//
//                for (int k = 0; k < results.length(); k++) {
//                    JSONObject trailerObj = new JSONObject(results.get(k).toString());
//
//                    //TODO:be sure to use GSON library
//                    Gson gson = new Gson();
//                    Trailers trailerJson = gson.fromJson(String.valueOf(trailerObj), Trailers.class);
//
//                    trailerDataList.add(trailerJson);
//                }
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//            //TODO:sending data of both reviews & videos in DetailsListAdapter
//            //here we sent the rest of data e.g: reviews & retailers
//            DetailsListAdapter detatailsListAdapter=new DetailsListAdapter(getActivity(),trailerDataList,null);
//            detailsDataList.setAdapter(detatailsListAdapter);
//        }

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_add_to_fav) {
            //Method called to add favMovie to DB
            addToFav();
        }
    }

    private void addToFav() {
        //here we add data to Database
        dataBase.addToFavorite(detailData.getTitle(), detailData.getPoster_path(), detailData.getOverview(), detailData.getRelease_date());
        Toast.makeText(getActivity(), "Inserted Successfully to DB", Toast.LENGTH_LONG).show();
        btn_add_to_fav.setText(getString(R.string.added_fav));
    }
}
