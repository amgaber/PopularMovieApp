package com.mal.android.popularmoviesapp.backend;

import android.app.ProgressDialog;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;


import com.mal.android.popularmoviesapp.ActivityMainFragment;
import com.mal.android.popularmoviesapp.BuildConfig;
import com.mal.android.popularmoviesapp.DetailsActivityFragment;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Created by Alaa Gaber on 7/26/2016.
 */
public class FetchMovieData extends AsyncTask<Uri.Builder, Void, String> {
    private static final String TAG = FetchMovieData.class.getSimpleName();

    private AsyncTaskListener asyncTaskListener;

    // Will contain the raw JSON response as a string.
    String movieJsonString;

    ActivityMainFragment fragment = new ActivityMainFragment();


    ProgressDialog dialog;




    public FetchMovieData(AsyncTaskListener activityContext) {
        this.asyncTaskListener = activityContext;
//        dialog = new ProgressDialog((Context) activityContext);
    }


    @Override
    protected void onPreExecute() {
//        dialog.setTitle("Please wait");
//        dialog.show();
    }

    @Override
    protected String doInBackground(Uri.Builder... builders) {

        //Setting Connection to the url
        HttpURLConnection urlConnection = null;

        // Read the input stream into a String
        BufferedReader reader = null;

        Log.v(TAG,"parameter send"+builders.length);
        Uri.Builder builder = builders[0];
        URL url = null;
        try {


            url = new URL(builder.toString());


            String urIBuilder = builder.build().toString();
            Log.v(TAG, "urIBuilder:: " + urIBuilder);

            // Create the request to   url, and open the connection
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            //Read inputstream into string
            InputStream is = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (is == null) {
                // Nothing to do.
                movieJsonString = null;
                return movieJsonString;
            }
            reader = new BufferedReader(new InputStreamReader(is));

            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line + "\n");
            }

            Log.v(TAG, "BUFFER Reader: " + buffer.toString());

            if (buffer.length() == 0) {
                return null;
            }

            movieJsonString = buffer.toString();
            return movieJsonString;
        } catch (MalformedURLException e) {

            fragment.message("URL needs to be fixed !" + e.getMessage().toString());
            Log.v(TAG, "URL exception:: " + e.getMessage().toString());
        } catch (ProtocolException e) {
            fragment.message("URL needs to be fixed !" + e.getMessage().toString());
            Log.v(TAG, "URL exception:: " + e.getMessage().toString());
        } catch (FileNotFoundException e) {
            fragment.message("FileNotFoundException !" + e.getMessage().toString());

            try {
                throw new FileNotFoundException("Failed to find file ,please check URL");
            } catch (FileNotFoundException e1) {
                fragment.message("FileNotFoundException !" + e.getMessage().toString());

            }
            Log.v(TAG, "FileNotFoundException: " + e.getMessage().toString());
        } catch (IOException e) {
            fragment.message("Something went wrong ! " + e.getMessage().toString());
            Log.v(TAG, "if the output doesn't return:: " + e.getMessage().toString());
            //If the code doesn't return facebookJsonString ,we can't parse it
            movieJsonString = null;
            return movieJsonString;
        } finally {
            //We have to close connection & reader after both finishing and catching error
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    fragment.message("Error closing stream ! " + e.getMessage().toString());
                    Log.e(TAG, "Error closing stream", e);
                }
            }
        }

        return movieJsonString;
    }


    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
//        dialog.dismiss();
        asyncTaskListener.notifyUpdate(s);
    }
}
