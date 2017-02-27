package com.example.android.myapplication.Utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by gowth on 2/27/2017.
 */

public class NewsDbHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "news1.db";


    private static final int DATABASE_VERSION = 2;

    public NewsDbHelper(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * @param sqLiteDatabase The database.
     */
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {


        final String SQL_CREATE_NEWS_TABLE =

                "CREATE TABLE " + NewsContract.NewsEntry.TABLE_NAME + " (" +

                        NewsContract.NewsEntry._ID               + " INTEGER PRIMARY KEY AUTOINCREMENT, " +

                        NewsContract.NewsEntry.COLUMN_TITLE       + " TEXT NOT NULL, "                 +
                        NewsContract.NewsEntry.COLUMN_IMAGEID + " TEXT NOT NULL,"                +
                        NewsContract.NewsEntry.COLUMN_PLOT +" TEXT NOT NULL,"         +
                        NewsContract.NewsEntry.COLUMN_AUTHOR+" TEXT NOT NULL,"        +
                        NewsContract.NewsEntry.COLUMN_URL+" TEXT NOT NULL);";





        sqLiteDatabase.execSQL(SQL_CREATE_NEWS_TABLE);
    }

    /**

     * @param sqLiteDatabase Database that is being upgraded
     * @param oldVersion     The old database version
     * @param newVersion     The new database version
     */
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + NewsContract.NewsEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
