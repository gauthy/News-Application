package com.example.android.myapplication;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Created by gowth on 2/27/2017.
 */

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsAdapterViewHolder> {
    private String[] newsDetails;
    private Context context;

    private final NewsAdapter.NewsAdapterOnClickHandler mClickHandler;

    public interface NewsAdapterOnClickHandler {
        void onClick(String newsdetail);
    }

    public NewsAdapter(Context context, NewsAdapter.NewsAdapterOnClickHandler clicklistener) {
        mClickHandler = clicklistener;
        this.context = context;
    }

    @Override
    public NewsAdapter.NewsAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        int layoutid = R.layout.card_item1;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldattachtoparent = false;
        View view = inflater.inflate(layoutid, parent, shouldattachtoparent);


        return new NewsAdapter.NewsAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NewsAdapter.NewsAdapterViewHolder holder, int position) {

        final String newsdetails = newsDetails[position];
        String[] Splitdata = newsdetails.split(">");
        String imageurl = Splitdata[0];
        String text1 = Splitdata[1];
        holder.mTextview.setText(text1);
        holder.mview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = view.getContext();
                Intent intent = new Intent(context, DetailView.class);
                intent.putExtra(Intent.EXTRA_TEXT, newsdetails);

                context.startActivity(intent);
            }
        });


        Picasso.with(context).load(imageurl).into(holder.mImageview);

    }

    @Override
    public int getItemCount() {
        if (null == newsDetails)
            return 0;

        return newsDetails.length;
    }

    public class NewsAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public final ImageView mImageview;
        public final View mview;
        public final TextView mTextview;

        public NewsAdapterViewHolder(View itemView) {
            super(itemView);
            mview = itemView;
            mImageview = (ImageView) itemView.findViewById(R.id.newsimage);
            mTextview = (TextView) itemView.findViewById(R.id.tv_text);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();

            String news = newsDetails[adapterPosition];
            mClickHandler.onClick(news);

        }
    }

    public void setNewsData(String[] newsData) {
        newsDetails = newsData;
        notifyDataSetChanged();
    }

}
