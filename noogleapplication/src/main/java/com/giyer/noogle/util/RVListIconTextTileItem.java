package com.giyer.noogle.util;

/**
 * Created by giyer7 on 3/13/17.
 */

/**
 * To create a list item with an icon on the left and a single line of text
 */
public class RVListIconTextTileItem extends RVListItem {

    private int iconResource;

    /**
     * Creates a ListIconTextTileItem object
     *
     * @param primaryText text to be displayed in the list item
     * @param iconResource resource for icon
     */
    public RVListIconTextTileItem(String primaryText, int iconResource) {
        super(primaryText);
        this.iconResource = iconResource;
    }

    public String getText() {
        return super.getPrimaryText();
    }

    public void setText(String text) {
        super.setPrimaryText(text);
    }

    public int getIconResource() {
        return iconResource;
    }

    public void setIconResource(int iconResource) {
        this.iconResource = iconResource;
    }



    @Override
    public int getItemType() {
        return TYPE_ICON_TEXT_TILE;
    }
}
