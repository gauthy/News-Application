package com.example.android.myapplication.Utils;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by gowth on 2/27/2017.
 */

public class NewsContract {
    public static final String CONTENT_AUTHORITY = "com.example.android.myapplication";

    /*
     * Use CONTENT_AUTHORITY to create the base of all URI's which apps will use to contact
     * the content provider .
     */
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);


    public static final String PATH_News = "news";

    /* Inner class that defines the table contents of the  table */
    public static final class NewsEntry implements BaseColumns {

        /* The base CONTENT_URI used to query the  table from the content provider */
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_News)
                .build();

        /* Used internally as the name of our  table. */
        public static final String TABLE_NAME = "new";


        public static final String COLUMN_TITLE = "title";


        public static final String COLUMN_IMAGEID = "image_id";

        public static final String COLUMN_PLOT = "description";


        public static final String COLUMN_AUTHOR = "author";
        public static final String COLUMN_URL = "url";

    }
}
