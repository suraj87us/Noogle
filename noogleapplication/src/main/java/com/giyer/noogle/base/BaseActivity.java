package com.giyer.noogle.base;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.giyer.noogle.NoogleApplication;
import com.giyer.noogle.Preferences;
import com.giyer.noogle.R;

import java.util.ArrayList;

import javax.inject.Inject;

/**
 * Created by giyer7 on 3/7/17.
 */

public abstract class BaseActivity extends BaseEventBusActivity implements BaseFragment.BaseFragmentListener {

    public static final int ANIMATION_ENTER_IN = R.anim.slide_enter_right_to_left;
    public static final int ANIMATION_ENTER_OUT = R.anim.slide_enter_left_to_right;
    public static final int ANIMATION_EXIT_IN = R.anim.slide_exit_right_to_left;
    public static final int ANIMATION_EXIT_OUT = R.anim.slide_exit_left_to_right;
    private final int ACTIVITY_TRANSITION_IN = android.R.anim.fade_in;
    private final int ACTIVITY_TRANSITION_OUT = android.R.anim.fade_out;
    private static final String FRAGMENT_LIST = "mSaveFragmentFinishToList";
    private static final int REQUEST_CODE_TRAVELER = 64586;
    protected ArrayList<Integer> mSaveFragmentFinishToList = new ArrayList<Integer>();
    public static long LAST_TOUCH_EVENT_TIME=10000000;//dummy initial time, it will set automatic on touch event
    public static long APP_LOGOUT_TIME=600000;//fix given time to logout app after 1o minute

    protected Handler mHandler = new Handler(Looper.getMainLooper());

    @Inject
    protected Preferences mPreferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(getTransitionIn(), getTransitionOut());
        ((NoogleApplication)getApplication()).getComponent().inject(this);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putIntegerArrayList(FRAGMENT_LIST, mSaveFragmentFinishToList);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        mSaveFragmentFinishToList = savedInstanceState.getIntegerArrayList(FRAGMENT_LIST);
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public void startActivity(Intent intent) {
        startActivityForResult(intent, REQUEST_CODE_TRAVELER);
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);
        overridePendingTransition(getTransitionIn(), getTransitionOut());
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(getTransitionIn(), getTransitionOut());
    }

    /**
     * Action fragment giving navigation to back fragment
     *
     */
    public void onBackPressed() {
        // By default we ask the fragment for navigation advice
        BaseFragmentInterface bf = getBaseFragmentInterface();
        if (bf != null) {
            bf.onBackClicked();
        } else {
            super.onBackPressed();
        }
    }

    protected BaseFragmentInterface getBaseFragmentInterface() {
        return ((BaseFragmentInterface) getSupportFragmentManager().findFragmentById(getFragmentContentFrameResourceID()));
    }

    /**
     * Fragment content frame
     *
     * @return
     */
    protected abstract int getFragmentContentFrameResourceID();
    /**
     * Fragment content view
     *
     * @return
     */
    protected abstract View getContentView();

    protected void unlockTouchInput() {
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    protected void lockTouchInput() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    protected int getTransitionIn() {
        return ACTIVITY_TRANSITION_IN;
    }

    protected int getTransitionOut() {
        return ACTIVITY_TRANSITION_OUT;
    }

    /**
     * Suggested ActionBar title display
     */
    @Override
    public void onRequestTitleUpdate(String titleResource){
        if (titleResource != null )
            getSupportActionBar().setTitle(titleResource);

    }

    /**
     * Swap in a new never leave fragment using the default animation If you are displaying the default loading interface, this method will disable the interface and transition properly
     *
     * @param fragment
     */
    protected void swapFragment(Fragment fragment, boolean popStop) {
        swapFragment(fragment, getFragmentContentFrameResourceID(), ANIMATION_ENTER_IN, ANIMATION_ENTER_OUT, ANIMATION_EXIT_IN, ANIMATION_EXIT_OUT, popStop);
    }

    /**
     * Swap in a new never leave fragment using the default animation If you are displaying the default loading interface, this method will disable the interface and transition properly
     *
     * @param fragment
     */
    public void swapFragment(Fragment fragment) {
        swapFragment(fragment, getFragmentContentFrameResourceID(), ANIMATION_ENTER_IN, ANIMATION_ENTER_OUT, ANIMATION_EXIT_IN, ANIMATION_EXIT_OUT, false);
    }

    /**
     * Swap in a new never leave fragment using no animations
     *
     * @param fragment
     */
    protected void swapFragment(Fragment fragment, boolean popStop, boolean noAnimations) {
        //swapFragment(fragment, getFragmentContentFrameResourceID(), 0, 0, 0, 0, popStop);
        handleSwapFragment(fragment, getFragmentContentFrameResourceID(), 0, 0, 0, 0, popStop, noAnimations);
    }

    /**
     * @param fragment
     * @param fragmentReplaceId
     * @param animationIn
     * @param animationOut
     * @param popAnimationIn
     * @param popAnimationOut
     * @param popStop           - if true, when calling action finish. It will stop stack popping with this fragment.
     */
    protected void swapFragment(Fragment fragment, int fragmentReplaceId, int animationIn, int animationOut, int popAnimationIn, int popAnimationOut, boolean popStop) {
        if (popStop)
            mSaveFragmentFinishToList.add(getSupportFragmentManager().getBackStackEntryCount() + 1);
        swapFragment(fragment, fragmentReplaceId, animationIn, animationOut, popAnimationIn, popAnimationOut, popStop, false);
    }

    protected void swapFragment(final Fragment fragment, final int fragmentReplaceId, final int animationIn, final int animationOut, final int popAnimationIn, final int popAnimationOut, final boolean popStop, final boolean ignoreLoadDelay) {
        handleSwapFragment(fragment, fragmentReplaceId, animationIn, animationOut, popAnimationIn, popAnimationOut, popStop, false);
    }

    private void handleSwapFragment(Fragment fragment, int fragmentReplaceId, int animationIn, int animationOut, int popAnimationIn, int popAnimationOut, boolean popStop, boolean noAnimations) {
        if(popStop){
            popAll();
        }
        String fragmentTag = getFragmentTag(fragment);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.addToBackStack(fragmentTag);
        if(!noAnimations) { // Avoid setting animations for noAnimations to take effect
            ft.setCustomAnimations(animationIn, animationOut, popAnimationIn, popAnimationOut);
        }
        ft.replace(fragmentReplaceId, fragment, fragmentTag);
        try {
            ft.commitAllowingStateLoss();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
    }

    protected String getCurrentFragmentTag() {
        return getSupportFragmentManager().findFragmentById(R.id.content_frame).getTag();
    }

    protected String getFragmentTag(Fragment fragment) {
        if (fragment instanceof BaseFragment.BaseFragmentListener) {
            return ((BaseFragmentInterface) fragment).getFragmentTag();
        } else {
            return ((BaseFragmentInterface) fragment).getClass().getSimpleName();
        }
    }

    @Override
    public void dismissKeyboard(View viewHoldingKeyboardForum) {
        if (viewHoldingKeyboardForum == null)
            return;
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        IBinder token = viewHoldingKeyboardForum.getWindowToken();
        if (token != null)
            imm.hideSoftInputFromWindow(token, 0);
    }

    @Override
    public void showKeyboard(final EditText editText) {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                imm.showSoftInput(editText, InputMethodManager.SHOW_FORCED);
            }
        }, 500);
    }

    @Override
    public void onFragmentActionBack() {
        popStack();
    }

    protected void popStack() {
        int nextBackStackCount = getSupportFragmentManager().getBackStackEntryCount() - 1;
        mSaveFragmentFinishToList.clear();
        if (nextBackStackCount == 0) {
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    finish();
                }
            }, 300);
        }
        getSupportFragmentManager().popBackStack();
    }

    private void popAll(){
        //Number of screens we pop back doesn't change
        int end = getSupportFragmentManager().getBackStackEntryCount();
        for(int i = 0; i < end; ++i){
            getSupportFragmentManager().popBackStack();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showSnackBar(View view, String message) {
        Snackbar.make(view, message, Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    @Override
    public void onFragmentActionFinish() {
        // TODO
    }
}
