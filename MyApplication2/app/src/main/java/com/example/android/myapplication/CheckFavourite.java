package com.example.android.myapplication;

import android.content.Context;
import android.database.Cursor;


import com.example.android.myapplication.Utils.NewsContract;


/**
 * Created by gowth on 1/21/2017.
 */

public class CheckFavourite {

    public static int isFavorited(Context context, String id) {
        Cursor cursor = context.getContentResolver().query(
                NewsContract.NewsEntry.CONTENT_URI,
                null,
                NewsContract.NewsEntry.COLUMN_TITLE + " = ?",
                new String[]{id},
                null
        );
        int numRows = cursor.getCount();
        cursor.close();
        return numRows;
    }


}
