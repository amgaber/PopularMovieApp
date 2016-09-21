package com.mal.android.popularmoviesapp.adapter;

import android.app.Activity;
import android.content.Intent;
import android.database.DataSetObserver;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.mal.android.popularmoviesapp.R;
import com.mal.android.popularmoviesapp.model.Reviews;
import com.mal.android.popularmoviesapp.model.TRbase;
import com.mal.android.popularmoviesapp.model.Trailers;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alaa Gaber on 9/12/2016.
 */
public class DetailsListAdapter extends BaseAdapter {

    private static final String TAG = DetailsListAdapter.class.getSimpleName();
    private static final int TYPE_TRAILERS = 0;
    private static final int TYPE_REVIEWS = 1;
    //    private final List<Trailers> parsedMoviesTrailerDataList;
    private final Activity activity;
    private final ArrayList<TRbase> listData;
    private int viewTypeReturn;

//    private final ArrayList<Reviews> parsedMoviesReviewDataList;


    public DetailsListAdapter(Activity activity, ArrayList<TRbase> listData) {
//        this.parsedMoviesTrailerDataList = trailerData;
        this.listData = listData;
//        this.parsedMoviesReviewDataList=reviewData;
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


//        View grid;
//
//        if (convertView == null) {
//
//            grid = LayoutInflater.from(viewGroup.getContext())
//                    .inflate(R.layout.movie_trailer_list_row, viewGroup, false);
//
////            Log.v(TAG, "TRAILER : " + listData.get(i));
//            if (listData.get(i) instanceof Reviews) {
//                Log.v(TAG, "INSTANCE OF reviews: " + ((Reviews) listData.get(i)).getAuthor());
////                String reviewName = ((Reviews) listData.get(i)).getAuthor();
////                TextView trailer = (TextView) grid.findViewById(R.id.textView);
////
////                trailer.setText(reviewName);
//
//
//            } else if (listData.get(i) instanceof Trailers) {
//                Log.v(TAG, "INSTANCE OF TRAILERS: " + ((Trailers) listData.get(i)).getName());
//
//            String trailerName = ((Trailers) listData.get(i)).getName();
//            final TextView trailer = (TextView) grid.findViewById(R.id.textView);
//
//            trailer.setText(trailerName);
//
//                trailer.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//
//                    activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v="+((Trailers) listData.get(i)).getKey())));
//                }
//            });
//
//            }
////            if (parsedMoviesTrailerDataList != null) {
////                final Trailers trailerData = parsedMoviesTrailerDataList.get(i);
////                Log.v(TAG, "TRAILER : " + trailerData.getName());
////            }else if (parsedMoviesReviewDataList !=null ) {
////                final Reviews reviewData = parsedMoviesReviewDataList.get(i);
////                Log.v(TAG, "REVIEWS : " + reviewData.getAuthor());
////            }
////            String trailerName = trailerData.getName();
////            TextView trailer = (TextView) grid.findViewById(R.id.textView);
////
////            trailer.setText(trailerName);
////
////            grid.setOnClickListener(new View.OnClickListener() {
////                @Override
////                public void onClick(View view) {
////
////                    activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v="+trailerData.getKey())));
////                }
////            });
//
//        } else {
//            grid = (View) convertView;
//        }
//
//        return grid;

        ViewHolder holder = null;
        int rowType = getItemViewType(i);
        String Name = null;
        if (convertView == null) {
            holder = new ViewHolder();

            switch (rowType) {
                case TYPE_TRAILERS:
                    convertView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.movie_trailer_list_row, null);
                    holder.textView = (TextView) convertView.findViewById(R.id.trTextView);

                    Log.v(TAG, "INSTANCE OF TRAILERS: " + ((Trailers) listData.get(i)).getName());
//
//                    Name = ((Trailers) listData.get(i)).getName();
                    holder.textView.setText(((Trailers) listData.get(i)).getName());
                    break;
                case TYPE_REVIEWS:
                    convertView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.movie_review_list_row, null);
                    holder.textView = (TextView) convertView.findViewById(R.id.trTextView);
//                    Name = ((Reviews) listData.get(i)).getAuthor();
                    holder.textView.setText(((Reviews) listData.get(i)).getAuthor());
                    break;
            }
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.textView.setText(((Trailers) listData.get(i)).getName());



        return convertView;
    }

    public static class ViewHolder {
        public TextView textView;
    }

    //Both methods are used for Multiple views in same adapter
    @Override
    public int getViewTypeCount() {
        return getCount();
    }

    @Override
    public int getItemViewType(int position) {

        if (listData.get(position) instanceof Trailers) {
            viewTypeReturn = TYPE_TRAILERS;
        } else if (listData.get(position) instanceof Reviews) {
            viewTypeReturn = TYPE_REVIEWS;
        }
        return viewTypeReturn;
    }
}
