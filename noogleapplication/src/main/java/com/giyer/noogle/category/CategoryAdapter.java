package com.giyer.noogle.category;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.giyer.noogle.R;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by giyer7 on 3/8/17.
 */

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryHolder> {

    private ArrayList<CategoryItem> mItems;
    CategoryFragment.CategoryListener mListener;

    public CategoryAdapter(ArrayList<CategoryItem> mItems, CategoryFragment.CategoryListener listener) {
        this.mItems = mItems;
        this.mListener = listener;
    }

    @Override
    public CategoryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CategoryHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item, parent, false));
    }

    @Override
    public void onBindViewHolder(CategoryHolder holder, int position) {
        holder.mCategoryImageView.setImageResource(mItems.get(position).getCategoryImage());
        holder.mCategoryTv.setText(mItems.get(position).getCategoryText());
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    class CategoryHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @Bind(R.id.category_iv)
        ImageView mCategoryImageView;

        @Bind(R.id.category_title)
        TextView mCategoryTv;

        public CategoryHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mListener.onCategoryClicked(getAdapterPosition(), mItems.get(getAdapterPosition()).getCategoryText());
        }
    }
}
