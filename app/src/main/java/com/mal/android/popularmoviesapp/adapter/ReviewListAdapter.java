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
public class ReviewListAdapter extends BaseAdapter {

    private static final String TAG = ReviewListAdapter.class.getSimpleName();

    private final Activity activity;
    private final ArrayList<TRbase> listData;


    public ReviewListAdapter(Activity activity, ArrayList<TRbase> listData) {

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
                    .inflate(R.layout.movie_review_list_row, viewGroup, false);

            if (listData.get(i) instanceof Reviews) {
                Log.v(TAG, "INSTANCE OF reviews: " + ((Reviews) listData.get(i)).getAuthor());
//
//
//
                TextView trailer = (TextView) grid.findViewById(R.id.trTextView);
//
                trailer.setText(((Reviews) listData.get(i)).getAuthor());

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
