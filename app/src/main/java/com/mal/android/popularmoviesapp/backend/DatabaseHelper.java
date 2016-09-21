package com.mal.android.popularmoviesapp.backend;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Alaa Gaber on 9/19/2016.
 *
 * That class is used to save favorite movies in SQLdatabase
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "MoviesDB";
    public static final String TABLE_NAME = "Movies";

    public static final String COLUMN_ID = "movie_id";
    public static final String COLUMN_TITLE ="movie_title";
    public static final String COLUMN_PATH = "poster_path";
    public static final String COLUMN_OVERVIEW = "movie_overview";
    public static final String COLUMN_DATE = "movie_date";



    private static final int DB_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context,DB_NAME,null,DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " +TABLE_NAME
                +"(" +COLUMN_ID+
                " INTEGER PRIMARY KEY AUTOINCREMENT, " +COLUMN_TITLE+
                " VARCHAR, " +COLUMN_PATH+
                " VARCHAR, "+COLUMN_OVERVIEW+
                " VARCHAR, "+COLUMN_DATE+
                " VARCHAR);";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS Movies";
        db.execSQL(sql);
        onCreate(db);
    }

    //When we are adding the movie data to database
    public boolean addToFavorite(String title, String urlPath,String overview, String date){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_TITLE,title);
        contentValues.put(COLUMN_PATH, urlPath);
        contentValues.put(COLUMN_OVERVIEW, overview);
        contentValues.put(COLUMN_DATE, date);

        db.insert(TABLE_NAME, null, contentValues);
        db.close();
        return true;
    }

    //we get movie data from databse by sending id
    public Cursor getMovie(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM Movies WHERE id="+id+";";
        Cursor c = db.rawQuery(sql, null);
        return c;
    }

    //we get movie data list from databse by sending id
    public Cursor getMovies(){
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM Movies;";
        Cursor c = db.rawQuery(sql, null);
        return c;
    }

    public  boolean CheckIsDataAlreadyInDBorNot(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String Query = "SELECT * FROM Movies WHERE "+COLUMN_ID+"="+id+";";
        Cursor cursor = db.rawQuery(Query, null);
        if(cursor.getCount() <= 0){
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }
}