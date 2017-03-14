package com.giyer.noogle.util;

import com.giyer.noogle.R;

/**
 * Created by giyer7 on 3/13/17.
 */

public abstract class RVListItem {

    public static final int TYPE_HEADER = 0;
    public static final int TYPE_ICON_TEXT_TILE = 1;
    public static final int TYPE_EMPTY = 2;

    private String primaryText;


    public RVListItem(String primaryText) {
        this.primaryText = primaryText;
    }


    public String getPrimaryText() {
        return primaryText;
    }

    protected void setPrimaryText(String primaryText) {
        this.primaryText = primaryText;
    }


    public static int getLayoutResource(int viewType) {
        switch (viewType) {
            case TYPE_HEADER:
                return R.layout.list_header_item;
            case TYPE_ICON_TEXT_TILE:
                return R.layout.list_icon_text_tile_view;
            default:
            case TYPE_EMPTY:
                return R.layout.empty_item;
        }
    }

    /**
     * @return the unique integer type for this list item
     */
    public abstract int getItemType();


}