package com.giyer.noogle.base;

/**
 * Created by giyer7 on 3/8/17.
 */

public interface BaseFragmentInterface {
    /**
     * Abstract method to be implemented to get the fragment tag
     * @return
     */
    String getFragmentTag();
    /**
     * Activity will call this if it want to ask the fragment for navigation advice
     */
    void onBackClicked();
    /**
     * Activity will call this if it want to ask the fragment for navigation advice
     */
    void onHomeLogoClick();

    /**
     * Suggested ActionBar title string
     */
    String getFragmentTitle();


}
