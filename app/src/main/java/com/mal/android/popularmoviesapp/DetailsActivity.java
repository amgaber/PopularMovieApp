package com.mal.android.popularmoviesapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.mal.android.popularmoviesapp.adapter.MovieListGridAdapter;

public class DetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        if (savedInstanceState == null) {
            Bundle detailsDataBundle=getIntent().getExtras();
            DetailsActivityFragment detailsActivityFragment=new DetailsActivityFragment();
            detailsActivityFragment.setArguments(detailsDataBundle);
            getFragmentManager().beginTransaction().add(R.id.detail_container, detailsActivityFragment).commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();


        return super.onOptionsItemSelected(item);
    }
}
