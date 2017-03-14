package com.giyer.noogle.util;

import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.giyer.noogle.R;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by giyer7 on 3/13/17.
 */

public class NavigationAdapter extends RecyclerView.Adapter<NavigationAdapter.ViewHolder> {

    // Dataset for titles (strings) types (header or not) and icons (image resources)

    List<RVListItem> mListItems;

    private static int selectedPos = 1;

    NavigationClickListener mListener;

    //constructor sets data
    public NavigationAdapter(List<RVListItem> listItems, NavigationClickListener listener) {
        mListItems = listItems;
        mListener = listener;
    }

    @Override
    public NavigationAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflates appropriate view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.navigation_header_view, parent, false);

        if (viewType == RVListItem.TYPE_ICON_TEXT_TILE) {
            v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.navigation_menu_view, parent, false);
        }


        ViewHolder vh = new ViewHolder(v);
        return vh;

    }

    @Override
    public void onBindViewHolder(NavigationAdapter.ViewHolder holder, final int position) {


        final RVListItem item = mListItems.get(position);
        holder.mTextView.setText(item.getPrimaryText());

        if (getItemViewType(position) == RVListItem.TYPE_ICON_TEXT_TILE) {
            if (selectedPos == position) {
                //Item selected
                holder.itemView.setBackgroundColor(Color.parseColor("#EDEDED"));
                if (position == 1 || position == 2 || position == 4) {
                    holder.mTextView.setTextColor(Color.parseColor("#0471b2"));
                }
            } else {
                //Item no longer selected
                holder.itemView.setBackgroundColor(Color.TRANSPARENT);
                if (position == 1 || position == 2 || position == 4) {
                    holder.mTextView.setTextColor(Color.parseColor("#de000000"));
                }
            }
            holder.mImageView.setImageResource(((RVListIconTextTileItem)item).getIconResource());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    notifyItemChanged(selectedPos);
                    if (position != 8) {
                        selectedPos = position;
                    } else {
                        selectedPos = 1;
                    }
                    notifyItemChanged(selectedPos);
                    mListener.onNavigationItemClicked(v, position, item);
                }
            });
        } else {
            // Header
            if (((RVListHeaderItem) item).hasDividerSeparator()) {
                holder.mDivider.setVisibility(View.VISIBLE);
            }
            if (item.getPrimaryText().isEmpty()) {
                holder.mTextView.setVisibility(View.GONE);
            }
        }


    }

    @Override
    public int getItemViewType(int position) {
        return mListItems.get(position).getItemType();
    }

    @Override
    public int getItemCount() {
        return mListItems.size();
    }

    /**
     * View Holder class for navigation recyclerview
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {


        @Bind(R.id.navigation_text)
        public TextView mTextView;
        @Bind(R.id.navigation_item_icon)
        @Nullable
        public ImageView mImageView;
        @Nullable
        @Bind(R.id.header_divider)
        public View mDivider;


        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }
    }

    public interface NavigationClickListener {

        void onNavigationItemClicked(View v, int pos, RVListItem item);
    }

}
