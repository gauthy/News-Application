package com.example.android.myapplication;

import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ShareCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.android.myapplication.Utils.NewsContract;
import com.squareup.picasso.Picasso;

/**
 * Created by gowth on 2/26/2017.
 */
public class DetailView extends AppCompatActivity{

    private ImageView mimg;
    private TextView mtitle;
    private TextView mauthor;
    private TextView murl;
    private TextView mdescription;
    private String mDetails;
    String title,image,author,link,descripton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);

        mtitle = (TextView) findViewById(R.id.display_title);
         mimg= (ImageView) findViewById(R.id.backdrop);
        mdescription = (TextView) findViewById(R.id.description);
        murl= (TextView) findViewById(R.id.urllink);
        mauthor= (TextView) findViewById(R.id.author);

        Intent intentThatStartedThisActivity = getIntent();

        if (intentThatStartedThisActivity != null) {
            if (intentThatStartedThisActivity.hasExtra(Intent.EXTRA_TEXT)) {
                mDetails = intentThatStartedThisActivity.getStringExtra(Intent.EXTRA_TEXT);
                String[] Details = mDetails.split(">");
                image = Details[0];
                 title = Details[1];
                 author = Details[2];
                 link=Details[3];
                 descripton=Details[4];

                mtitle.setText(title);
                Picasso.with(DetailView.this).load(image).into(mimg);
                mdescription.setText(descripton);
                murl.setText("ReadMore at:"+link);
                if(author!=null)
                mauthor.setText("Written by:"+author);
                else
                    mauthor.setText("Written by:Unknown");
            }
            final FloatingActionButton fabButton = (FloatingActionButton) findViewById(R.id.fab);

            int numm= CheckFavourite.isFavorited(getApplicationContext(),title);
            if(numm==1)
            {
                fabButton.setImageResource(R.drawable.ic_bookmark_black_24dp);
            }
            else
            {
                fabButton.setImageResource(R.drawable.ic_bookmark_border_black_24dp);
            }

            fabButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int num= CheckFavourite.isFavorited(getApplicationContext(),title);
                    if(num==1)
                    {
                        int deleted=  getContentResolver().delete(
                                NewsContract.NewsEntry.CONTENT_URI,
                                NewsContract.NewsEntry.COLUMN_TITLE + " = ?",
                                new String[]{title}
                        );

                        fabButton.setImageResource(R.drawable.ic_bookmark_border_black_24dp);
                    }
                    else {
                        ContentValues contentValues = new ContentValues();

                        contentValues.put( NewsContract.NewsEntry.COLUMN_IMAGEID, image);
                        contentValues.put( NewsContract.NewsEntry.COLUMN_TITLE, title);
                        contentValues.put( NewsContract.NewsEntry.COLUMN_PLOT, descripton);
                        contentValues.put( NewsContract.NewsEntry.COLUMN_AUTHOR, author);
                        contentValues.put( NewsContract.NewsEntry.COLUMN_URL, link);

                        // Insert the content values via a ContentResolver
                        Uri uri = getContentResolver().insert( NewsContract.NewsEntry.CONTENT_URI, contentValues);

                        if (uri != null) {
                            //     Toast.makeText(getBaseContext(), uri.toString(), Toast.LENGTH_LONG).show();
                        }
                        fabButton.setImageResource(R.drawable.ic_bookmark_black_24dp);
                    }
                }
            });
            final FloatingActionButton fabButton2 = (FloatingActionButton) findViewById(R.id.share_fab);

            fabButton2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(Intent.createChooser(ShareCompat.IntentBuilder.from(DetailView.this)
                            .setType("text/plain")
                            .setText(title)
                            .getIntent(), getString(R.string.action_share)));
                }
            });

        }
        collapsingToolbar.setTitle(title);
    }

    private void loadBackdrop() {

    }
}
