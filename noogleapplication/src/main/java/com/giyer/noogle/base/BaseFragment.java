package com.giyer.noogle.base;

import android.content.pm.ActivityInfo;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.TouchDelegate;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.giyer.noogle.util.PixelUtils;

import butterknife.ButterKnife;

import static com.giyer.noogle.util.LogUtil.makeLogTag;

/**
 * Created by giyer7 on 3/7/17.
 */

public abstract class BaseFragment<E extends BaseFragment.BaseFragmentListener> extends BaseEventBusFragment<E> implements BaseFragmentInterface {

    private static final String TAG = makeLogTag(BaseFragment.class);
    public enum DisplayRequest {TRANSPARENT_TOOLBAR_HAMBURGER,NO_ACTION_BAR, ACTION_BACK, ACTION_HAMBURGER, NO_HOME_BUTTON}



    @Override
    public void onStart() {
        super.onStart();
        if (getFragmentTitle() != null)
            getCallback().onRequestTitleUpdate(getFragmentTitle());
        getCallback().allowViewToScroll(allowViewToScroll());
    }


    private boolean showCardStatusIndicator() {
        return false;
    }

    /**
     * Initialization of the view
     *
     * @param rootView           - layout to be shown
     * @param savedInstanceState - use this if the savedInstanceState is not null
     * @return View
     */
    public abstract View onInitFragment(View rootView, Bundle savedInstanceState);

    /**
     * suggested layout
     *
     * @return int
     */
    public abstract int getLayout();

    @Override
    public void onStop() {
        if (dismissKeyboardOnStop()) {
            dismissKeyboard(getView());
        }
        super.onStop();
    }

    /**
     * Override to change dismiss keyboard behavior
     *
     * @return true if the keyboard should be dismissed when this fragment is stopped
     */
    public boolean dismissKeyboardOnStop() {
        return true;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(getLayout(), container, false);
        ButterKnife.bind(this, rootView);
        onInitFragment(rootView, savedInstanceState);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    protected void dismissKeyboard(View viewHoldingKeyboardForum) {
        getCallback().dismissKeyboard(viewHoldingKeyboardForum);
    }

    protected void showKeyboard(EditText editText) {
        getCallback().showKeyboard(editText);
    }


    /**
     * Suggested ActionBar display to activity
     */
    public boolean requestDisplayHomeAsUpEnabled() {
        return true;
    }

    /**
     * Suggested ActionBar display to activity
     */
    public boolean requestDisplayShowHomeEnabled() {
        return false;
    }


    @Override
    public String getFragmentTag() {
        return null;
    }

    @Override
    public void onBackClicked() {
        // Default fragment nav is back
        getCallback().onFragmentActionBack();
    }

    /* Use this method to make your fragment orientation free */
    public void removeFixedOrientationFromFragment() {
        mActivity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
    }

    /**
     * To suggest whether view is scrollable or not
     *
     * @return
     */
    protected boolean allowViewToScroll() {
        return true;
    }

    @Override
    public void onHomeLogoClick() {
        // Default fragment nav is back
        getCallback().dismissKeyboard(getView());
        getCallback().onFragmentActionBack();
    }

    public void showToast(String message) {
        getCallback().showToast(message);
    }


    public void increaseTouchableArea(final View view) {
        if (view == null)
            return;
        final View parent = (View) view.getParent();
        if (parent == null)
            return;
        parent.post(new Runnable() {
            public void run() {
                if (getActivity() != null) {
                    final Rect r = new Rect();
                    view.getHitRect(r);
                    r.bottom += PixelUtils.dipToPixels(getActivity(), 10);
                    r.right += PixelUtils.dipToPixels(getActivity(), 10);
                    r.left -= PixelUtils.dipToPixels(getActivity(), 10);
                    r.top -= PixelUtils.dipToPixels(getActivity(), 10);
                    parent.setTouchDelegate(new TouchDelegate(r, view));
                }
            }
        });
    }


    interface BaseFragmentListener extends BaseEventBusFragmentListener {
        /**
         * Suggested view to be scrollable
         *
         * @param allowViewToScroll
         */
        void allowViewToScroll(boolean allowViewToScroll);

        void dismissKeyboard(View viewHoldingKeyboardForum);

        void showKeyboard(EditText editText);

        /**
         * Let the activity know we want to pop the stack
         */
        void onFragmentActionBack();

        /**
         * Suggested ActionBar title display
         */
        void onRequestTitleUpdate(String titleResource);

        /**
         * Let the activity know we want to finish the flow
         */
        void onFragmentActionFinish();

        void showToast(String message);

        void showSnackBar(View view, String message);

    }
}
