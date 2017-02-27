package com.example.android.myapplication;

import android.app.FragmentTransaction;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.view.ViewGroup;

import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.example.android.myapplication.Utils.NetWorkUtils;
import com.example.android.myapplication.Utils.NewsContract;
import com.example.android.myapplication.Utils.NewsJson;

import java.net.URL;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    public static String text;
    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */

    private SectionsPagerAdapter mSectionsPagerAdapter;

    public static final String[] NEWS_DETAIL= {
            NewsContract.NewsEntry.COLUMN_IMAGEID,
    };
    private DrawerLayout mDrawerLayout;
    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    public RecyclerView mRecyclerView;


    public TextView mErrorMessageDisplay;
    public static final int INDEX_ID = 0;
    public ProgressBar mLoadingIndicator;
    String typee="technology";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
      //  final ActionBar ab = getSupportActionBar();
       // ab.setHomeAsUpIndicator(R.drawable.ic_menu);
       // ab.setDisplayHomeAsUpEnabled(true);




        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(),typee);

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setOffscreenPageLimit(1);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
        mErrorMessageDisplay= (TextView) findViewById(R.id.tv_error_message_display);
        getSupportLoaderManager().initLoader(101, null, this);

    }

    @Override
    protected void onResume() {
        super.onResume();
       int n= mViewPager.getCurrentItem();
        if(n==5)
        mViewPager.getAdapter().notifyDataSetChanged();
    }




    @Override
    public Loader onCreateLoader(int id, Bundle args) {


        Uri newsuri=NewsContract.NewsEntry.CONTENT_URI;

        return new CursorLoader(this,newsuri,NEWS_DETAIL,null,null,null);
    }

    @Override
    public void onLoadFinished(Loader loader, Cursor data) {

        if(data.getCount()==0)
    text="No favourite items present";
    }

    @Override
    public void onLoaderReset(Loader loader) {

    }
    /**
     * A placeholder fragment containing a simple view.
     */

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {


         String typeee;

        public SectionsPagerAdapter(FragmentManager fm, String fp) {

            super(fm);
            this.typeee=fp;
        }

        @Override
        public Fragment getItem(int position) {

            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            switch (position) {
                case 0:
                return Placeholderfragment.PlaceholderFragment.newInstance(position + 1, "general");

                case 1:
                    return Placeholderfragment.PlaceholderFragment.newInstance(position + 1, "sport");

                case 2:
                    return Placeholderfragment.PlaceholderFragment.newInstance(position + 1, "technology");
                case 3:
                  //  mViewPager.getAdapter().notifyDataSetChanged();
                    return Placeholderfragment.PlaceholderFragment.newInstance(position + 1, "business");

                case 4:
                    //  mViewPager.getAdapter().notifyDataSetChanged();
                    return Placeholderfragment.PlaceholderFragment.newInstance(position + 1, "entertainment");

                case 5:
                    //  mViewPager.getAdapter().notifyDataSetChanged();
                    return Placeholderfragment.PlaceholderFragment.newInstance(position + 1, "bookmark");


            }
           // return null;
            return Placeholderfragment.PlaceholderFragment.newInstance(position + 1, "technology");
        }



        @Override
        public int getCount() {

            return 6;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Feed";
                case 1:
                    return "Sport";
                case 2:
                    return "Technology";
                case 3:
                    return "Business";
                case 4:
                    return "Entertainment";
                case 5:
                    return "Bookmarked";

            }
            return null;

        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;

        }

    }



}



