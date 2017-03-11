package com.giyer.noogle.feed;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.giyer.noogle.R;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by giyer7 on 3/10/17.
 */

public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.FeedHolder> {
    private ArrayList<FeedItem> mFeedItems;

    FeedFragment.FeedFragmentListener mListener;

    public FeedAdapter(ArrayList<FeedItem> mFeedItems, FeedFragment.FeedFragmentListener listener) {
        this.mFeedItems = mFeedItems;
        this.mListener = listener;
    }

    @Override
    public FeedAdapter.FeedHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new FeedHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.feed_item, parent, false));
    }

    @Override
    public void onBindViewHolder(FeedAdapter.FeedHolder holder, int position) {
        Glide.with(holder.itemView.getContext()).load(mFeedItems.get(position).getmThumbnail()).fitCenter().crossFade().into(holder.mThumbnail);
        holder.mTitle.setText(mFeedItems.get(position).getmTitle());
        holder.mSubTitle.setText(mFeedItems.get(position).getmSubTitle());
        holder.mLastUpdateTs.setText(mFeedItems.get(position).getmLastUpdateTs());
    }

    @Override
    public int getItemCount() {
        return mFeedItems.size();
    }


    class FeedHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @Bind(R.id.feed_thumbnail)
        ImageView mThumbnail;

        @Bind(R.id.feed_title)
        TextView mTitle;

        @Bind(R.id.feed_subtitle)
        TextView mSubTitle;

        @Bind(R.id.feed_timestamp)
        TextView mLastUpdateTs;

        public FeedHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {
            mListener.onFeedItemClick(getAdapterPosition(), mFeedItems.get(getAdapterPosition()).getmFeedUrl());
        }
    }
}
