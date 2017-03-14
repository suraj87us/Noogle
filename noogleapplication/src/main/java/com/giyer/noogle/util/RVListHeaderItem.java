package com.giyer.noogle.util;

/**
 * Created by giyer7 on 3/13/17.
 */

public class RVListHeaderItem extends RVListItem {

    boolean dividerSeparator;

    /**
     * Creates a ListHeaderItem
     *
     * @param title   header text
     * @param divider true if the header should have a divider separator
     */
    public RVListHeaderItem(String title, boolean divider) {
        //All headers we use are in all caps
        super(title.toUpperCase());
        dividerSeparator = divider;
    }

    public RVListHeaderItem(String title) {
        this(title, false);
    }


    public boolean hasDividerSeparator() {
        return dividerSeparator;
    }

    @Override
    public int getItemType() {
        return TYPE_HEADER;
    }

    public String getHeaderText() {
        return super.getPrimaryText();
    }

    public void setHeaderText(String title) {
        super.setPrimaryText(title);
    }
}
