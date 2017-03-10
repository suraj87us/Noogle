package com.giyer.noogle.base;

import com.giyer.noogle.R;
import com.giyer.noogle.util.PixelUtils;

/**
 * Created by giyer7 on 3/9/17.
 */

public abstract class ActionFlowFragment<E extends ActionFlowFragment.ActionFlowFragmentListener> extends BaseFragment<E> {
    public enum DisplayRequest {TRANSPARENT_TOOLBAR_HAMBURGER, NO_ACTION_BAR, ACTION_BACK, ACTION_HAMBURGER, NO_HOME_BUTTON}

    @Override
    public void onStart() {
        super.onStart();
        if (getFragmentTitle() != null) {
            getCallback().onRequestTitleUpdate(getFragmentTitle());
        }
        getCallback().onRequestBackgroundUpdate(getFragmentBackgroundResource());
        getCallback().setActionBarHeight(getActionBarElevation());

        getCallback().onRequestActionBarDisplayType(actionBarDisplay());
        getCallback().allowViewToScroll(allowViewToScroll());

        getCallback().setHeaderCardVisible(showHeaderCard());


    }

    /**
     * Override to set action bar height to something different from default
     */
    public int getActionBarElevation() {
        return PixelUtils.dipToPixels(getContext(), 8);
    }

    /**
     * Suggest to parent activity what type of action style to display
     *
     * @return
     */
    protected DisplayRequest actionBarDisplay() {
        return DisplayRequest.NO_ACTION_BAR;
    }

    /**
     * To suggest whether view is scrollable or not
     *
     * @return
     */
    protected boolean allowViewToScroll() {
        return true;
    }

    /**
     * Override to set a background
     *
     * @return
     */
    public int getFragmentBackgroundResource() {
        return R.drawable.transparent;
    }

    /**
     * Override to show summary header (for monthly transactions fragment)
     */
    public boolean showHeaderCard() {
        return false;
    }

    /**
     * Call to set header card information
     */
    public void setHeaderCardInfo(CharSequence primaryText, CharSequence secondaryText, CharSequence endText) {
        getCallback().setHeaderCardInfo(primaryText, secondaryText, endText);
    }


    /**
     * To suggest whether available balance balance is to shown or not
     *
     * @return
     */
    public boolean showCardStatusIndicator() {
        return false;
    }

    /**
     * Listener for ServeBaseAction Class
     */
    public interface ActionFlowFragmentListener extends BaseFragment.BaseFragmentListener {

        void onRequestActionBarDisplayType(DisplayRequest request);

        void onRequestBackgroundUpdate(int backgroundResource);

        void setHeaderCardVisible(boolean visible);

        void setHeaderCardInfo(CharSequence primaryText, CharSequence secondaryText, CharSequence endText);

        void setActionBarHeight(int actionBarHeight);
    }
}
