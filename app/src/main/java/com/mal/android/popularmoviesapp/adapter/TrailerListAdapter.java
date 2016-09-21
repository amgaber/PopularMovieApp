package com.mal.android.popularmoviesapp.adapter;

import android.app.Activity;
import android.database.DataSetObserver;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mal.android.popularmoviesapp.R;
import com.mal.android.popularmoviesapp.model.Reviews;
import com.mal.android.popularmoviesapp.model.TRbase;
import com.mal.android.popularmoviesapp.model.Trailers;

import java.util.ArrayList;

/**
 * Created by toshiba1 on 9/21/2016.
 */
public class TrailerListAdapter extends BaseAdapter {

    private static final String TAG = TrailerListAdapter.class.getSimpleName();
    private static final int TYPE_TRAILERS = 0;
    private static final int TYPE_REVIEWS = 1;
    //    private final List<Trailers> parsedMoviesTrailerDataList;
    private final Activity activity;
    private final ArrayList<TRbase> listData;
    private int viewTypeReturn;

//    private final ArrayList<Reviews> parsedMoviesReviewDataList;


    public TrailerListAdapter(Activity activity, ArrayList<TRbase> listData) {

        this.listData = listData;
        this.activity = activity;
        Log.v(TAG, "trailerData JSON LIST: " + listData);
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

        return listData != null ? listData.size() : 0;
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
                    .inflate(R.layout.movie_trailer_list_row, viewGroup, false);

            if (listData.get(i) instanceof Trailers) {
                Log.v(TAG, "INSTANCE OF reviews: " + ((Reviews) listData.get(i)).getAuthor());
//
                Log.v(TAG, "INSTANCE OF TRAILERS: " + ((Trailers) listData.get(i)).getName());
//
//
                TextView trailer = (TextView) grid.findViewById(R.id.trTextView);
//
                trailer.setText(((Trailers) listData.get(i)).getName());
//
//            grid.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//
//                    activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v="+trailerData.getKey())));
//                }
//            });
            }
        } else {
            grid = (View) convertView;
        }

        return grid;
    }

    public static class ViewHolder {
        public TextView textView;
    }


}
