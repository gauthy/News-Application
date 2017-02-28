package com.example.android.myapplication;

import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.example.android.myapplication.Utils.NetWorkUtils;
import com.example.android.myapplication.Utils.NewsContract;
import com.example.android.myapplication.Utils.NewsJson;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;


import java.net.URL;

/**
 * Created by gowth on 2/24/2017.
 */

public class PlaceholderFragment {
    public static class PlaceholderFragments extends Fragment implements NewsAdapter.NewsAdapterOnClickHandler {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";
        private static final String ARG_SECTION_TYPE = "section_type";
        private static int position;
        private RecyclerView mRecyclerView;
        private NewsAdapter mNewsAdapter;
        private ProgressBar mLoadingIndicator;
        private TextView mErrorMessageDisplay;
        private TextView mErrorText;
        public static final String[] NEWS_DETAIL = {
                NewsContract.NewsEntry.COLUMN_IMAGEID,
                NewsContract.NewsEntry.COLUMN_TITLE,
                NewsContract.NewsEntry.COLUMN_PLOT,
                NewsContract.NewsEntry.COLUMN_AUTHOR,
                NewsContract.NewsEntry.COLUMN_URL,

        };

        public PlaceholderFragments() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragments newInstance(int sectionNumber, String typee) {
            PlaceholderFragments fragment = new PlaceholderFragments();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            args.putString(ARG_SECTION_TYPE, typee);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            mErrorMessageDisplay = (TextView) rootView.findViewById(R.id.section_label);
            mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerviewnews);
            String strtext = getArguments().getString("keyy");
            // Toast.makeText(getContext(), strtext,Toast.LENGTH_LONG).show();
            if (strtext == null) {
                strtext = getArguments().getString(ARG_SECTION_TYPE);
            }
            Context context = rootView.getContext();
            mNewsAdapter = new NewsAdapter(context, this);
            AdView mAdView = (AdView) rootView.findViewById(R.id.adView);
            //       Create an ad request. Check logcat output for the hashed device ID to
            //      get test ads on a physical device. e.g.
            //     "Use AdRequest.Builder.addTestDevice("ABCDEF012345") to get test ads on this device."
            AdRequest adRequest = new AdRequest.Builder()
                    .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                    .build();
            mAdView.loadAd(adRequest);

            //  GridLayoutManager layoutManager=new GridLayoutManager(getContext(),2);
            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
            mRecyclerView.setLayoutManager(layoutManager);


            mRecyclerView.setAdapter(mNewsAdapter);

            mErrorMessageDisplay = (TextView) rootView.findViewById(R.id.tv_error_message_display);

            mLoadingIndicator = (ProgressBar) rootView.findViewById(R.id.pb_loading_indicator);

            position = getArguments().getInt(ARG_SECTION_NUMBER);
            loadNews(strtext);


            //  mErrorText.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
            return rootView;
        }

        private void loadNews(String txt) {
            switch (txt) {
                case "bookmark":
                    new GetFavouriteNewsDetails(getContext()).execute();
                    break;
                default:
                    new GetNewsDetails().execute(txt);
            }
        }

        public class GetNewsDetails extends AsyncTask<String, Void, String[]> {
            String order;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                mLoadingIndicator.setVisibility(View.VISIBLE);
            }

            @Override
            protected String[] doInBackground(String... strings) {
                order = strings[0];
                URL newssurl = NetWorkUtils.getnewsUrl(order);
                try {
                    String Jsonresponse = NetWorkUtils.getResponseFromHttpUrl(newssurl);
                    String[] SimpleJsondata = NewsJson.getSimpleNewsStringsFromJson(getContext(), Jsonresponse);

                    return SimpleJsondata;
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }


            }

            @Override
            protected void onPostExecute(String[] newsData) {
                mLoadingIndicator.setVisibility(View.INVISIBLE);
                if (newsData != null) {
                    showNewsData();
                    mNewsAdapter.setNewsData(newsData);
                } else {
                    //  showErrorMessage();

                }

            }
        }

        public class GetFavouriteNewsDetails extends AsyncTask<Void, Void, String[]> {
            private Context mContext;

            public GetFavouriteNewsDetails(Context context) {
                mContext = context;

            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                mLoadingIndicator.setVisibility(View.VISIBLE);
            }

            private String[] getFavoriteNewsDataFromCursor(Cursor cursor) {
                String[] results = new String[cursor.getCount()];
                int i = 0;
                if (cursor != null && cursor.moveToFirst()) {
                    do {
                        results[i] = cursor.getString(cursor.getColumnIndex("image_id")) + ">"
                                + cursor.getString(cursor.getColumnIndex("title")) + ">"
                                + cursor.getString(cursor.getColumnIndex("description")) + ">"
                                + cursor.getString(cursor.getColumnIndex("url")) + ">"
                                + cursor.getString(cursor.getColumnIndex("author"));
                        i++;
                    } while (cursor.moveToNext());
                    cursor.close();
                }
                return results;
            }

            @Override
            protected String[] doInBackground(Void... voids) {


                Cursor cursor = mContext.getContentResolver().query(NewsContract.NewsEntry.CONTENT_URI,
                        NEWS_DETAIL,
                        null,
                        null,
                        null);


                return getFavoriteNewsDataFromCursor(cursor);
            }

            @Override
            protected void onPostExecute(String[] news) {


                mLoadingIndicator.setVisibility(View.INVISIBLE);
                if (news != null) {
                    showNewsData();
                    mNewsAdapter.setNewsData(news);
                } else {

                    mErrorMessageDisplay.setText(MainActivity.text);
                    //    showErrorMessage();
                    //   mErrorMessageDisplay.setText(MainActivity.text);

                }
            }
        }

        private void showErrorMessage() {

            mRecyclerView.setVisibility(View.INVISIBLE);

            mErrorMessageDisplay.setVisibility(View.VISIBLE);
        }


        private void showNewsData() {

            mErrorMessageDisplay.setVisibility(View.INVISIBLE);

            mRecyclerView.setVisibility(View.VISIBLE);
        }


        @Override
        public void onClick(String newsdetail) {

        }
    }

}
