package com.giyer.noogle.base;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.giyer.noogle.R;
import com.giyer.noogle.util.LockableScrollView;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.giyer.noogle.util.LogUtil.makeLogTag;

/**
 * Created by giyer7 on 3/9/17.
 */

public abstract class ActionFlowActivity extends BaseActivity implements ActionFlowFragment.ActionFlowFragmentListener, FragmentManager.OnBackStackChangedListener {
    private static final String TAG = makeLogTag(ActionFlowActivity.class);
    private final int ANIMATION_TRANSITION_DURATION = 500;
    private final int ANIMATION_ROTATION_AMT = 0;// -2;
    protected boolean mLoadingDisplay = false;
    boolean mShowingHeader = false;
    private AnimatorSet mAnimSet;
    protected static final int NAV_DRAWER_ITEM_INVALID = -1;

    @Nullable
    @Bind(R.id.common_loading_display)
    ProgressBar mProgressBar;
    @Nullable
    @Bind(R.id.horizontal_progressBar)
    ProgressBar mHorizontalProgressBar;
    @Nullable
    @Bind(R.id.content_frame)
    FrameLayout mContent;
    @Nullable
    @Bind(R.id.content_scrollview_parent)
    LockableScrollView mContentScrollView;
    @Bind(R.id.master_container)
    RelativeLayout mMasterContainer;

    @Bind(R.id.app_bar)
    Toolbar mToolBar;

    @Bind(R.id.header_card_primary_text)
    TextView mTvHeaderCardPrimaryText;

    @Bind(R.id.header_card_secondary_text)
    TextView mTvHeaderCardSecondaryText;

    @Bind(R.id.header_card_end_text)
    TextView mTvHeaderCardEndText;

    @Bind(R.id.card_header)
    CardView mCardViewHeader;

    @Bind(R.id.bottom_navigation)
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentViewResource());
        ButterKnife.bind(this);
        setupBottomNavigation();
        // Set the Toolbar
        setSupportActionBar(mToolBar);
        // Enable the Home as Up Button
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportFragmentManager().addOnBackStackChangedListener(this);
    }

    @Override
    public void setActionBarHeight(int actionBarHeight) {
        getSupportActionBar().setElevation(actionBarHeight);
    }

    @Override
    public void onBackPressed() {
        if (mLoadingDisplay) {
            showToast("We are currently processing your request at this time. We apologize for the inconvenience.");
        } else {
            super.onBackPressed();
        }
    }

    private void setupBottomNavigation() {
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.action_recent:
                                Toast.makeText(getApplicationContext(), "Recent Clicked", Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.action_feed:
                                Toast.makeText(getApplicationContext(), "Feed Clicked", Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.action_category:
                                Toast.makeText(getApplicationContext(), "Category Clicked", Toast.LENGTH_SHORT).show();
                                break;
                        }
                        return true;
                    }
                });
    }

    protected int getContentViewResource() {
        return R.layout.action_flow_layout;
    }

    @Override
    protected int getFragmentContentFrameResourceID() {
        return R.id.content_frame;
    }

    @Override
    protected View getContentView() {
        return mContent;
    }

    /**
     * To unlock the touch input of the view
     */
    protected void unlockTouchInput() {
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    /**
     * To lock the touch input of the view
     */
    protected void lockTouchInput() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    /**
     * Action flow transition called before fragment swapping
     */
    @SuppressWarnings("unused")
    private void startTransitionDisplay() {
        startTransitionDisplay(false);
    }

    /**
     * Action flow transition called before fragment swapping
     *
     * @param reverse
     */
    private void startTransitionDisplay(boolean reverse) {
        mAnimSet = new AnimatorSet();
        mAnimSet.playTogether(ObjectAnimator.ofFloat(mContent, "rotationY", 0, reverse ? ANIMATION_ROTATION_AMT * -1 : ANIMATION_ROTATION_AMT));
        mAnimSet.setDuration(ANIMATION_TRANSITION_DURATION).start();
    }

    /**
     * Action flow transition called after fragment swapping
     */
    @SuppressWarnings("unused")
    private void stopTransitionDisplay() {
        stopTransitionDisplay(false);
    }

    /**
     * Action flow transition called after fragment swapping
     *
     * @param reverse
     */
    private void stopTransitionDisplay(boolean reverse) {
        mAnimSet = new AnimatorSet();
        mAnimSet.playTogether(ObjectAnimator.ofFloat(mContent, "rotationY", reverse ? ANIMATION_ROTATION_AMT * -1 : ANIMATION_ROTATION_AMT, 0));
        mAnimSet.setDuration(ANIMATION_TRANSITION_DURATION).start();
    }

    /**
     * To get the lockable master layout scroll view
     *
     * @return
     */
    public LockableScrollView getMasterScrollView() {
        return mContentScrollView;
    }

    /**
     * To lock the scroll view
     */
    protected void lockScrollView() {
        if (mContentScrollView != null) {
            mContentScrollView.setScrollContainer(false);
            mContentScrollView.setScrollingEnabled(false);
        }
    }

    /**
     * To unlock the scroll view
     */
    protected void unlockScrollView() {
        if (mContentScrollView != null) {
            mContentScrollView.setScrollContainer(true);
            mContentScrollView.setScrollingEnabled(true);
        }
    }

    @Override
    public void onRequestActionBarDisplayType(ActionFlowFragment.DisplayRequest request) {

        switch (request) {
            case NO_HOME_BUTTON:
                getSupportActionBar().show();
                getSupportActionBar().setDisplayShowTitleEnabled(true);
                getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                getSupportActionBar().setDisplayShowHomeEnabled(false);
                break;
            case NO_ACTION_BAR:
            default:
                getSupportActionBar().hide();
                break;
        }
    }

    public void onRequestTitleUpdate(String title) {
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle(title);
    }

    @Override
    public void onRequestBackgroundUpdate(int backgroundResource) {
        mMasterContainer.setBackgroundResource(backgroundResource);
    }

    @Override
    public void setHeaderCardVisible(boolean visible) {
        int cardVisibility = View.GONE;
        if (visible) {
            cardVisibility = View.VISIBLE;
        }
        mCardViewHeader.setVisibility(cardVisibility);
    }

    @Override
    public void setHeaderCardInfo(CharSequence primaryText, CharSequence secondaryText, CharSequence endText) {
        mTvHeaderCardPrimaryText.setText(primaryText);
        mTvHeaderCardSecondaryText.setText(secondaryText);
        mTvHeaderCardEndText.setText(endText);
    }


    @Override
    public void allowViewToScroll(boolean fragmentScrolling) {
        if (fragmentScrolling) {
            unlockScrollView();
        } else {
            lockScrollView();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void showUIBlockingProgress() {
        if (mLoadingDisplay)
            return;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mProgressBar.setVisibility(View.VISIBLE);
                mLoadingDisplay = true;
                mAnimSet = new AnimatorSet();
                mAnimSet.playTogether(ObjectAnimator.ofFloat(mContent, "alpha", 1.0f, 0.5f));
                mAnimSet.setDuration(ANIMATION_TRANSITION_DURATION).start();
                lockTouchInput();
            }
        });

    }

    @Override
    public void showHorizontalProgressBar() {
        if (mLoadingDisplay)
            return;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mHorizontalProgressBar.setVisibility(View.VISIBLE);
                mLoadingDisplay = true;
                lockTouchInput();
            }
        });

    }

    @Override
    public void hideHorizontalProgressBar() {
        if (!mLoadingDisplay)
            return;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mHorizontalProgressBar.setVisibility(View.GONE);
                mLoadingDisplay = false;
                unlockTouchInput();
            }
        });

    }

    @Override
    public void hideUIBlockingProgress() {
        if (!mLoadingDisplay)
            return;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mProgressBar.setVisibility(View.INVISIBLE);
                mLoadingDisplay = false;
                // setRequestedOrientation(mLastRequestedOrientation);
                mAnimSet = new AnimatorSet();
                mAnimSet.playTogether(ObjectAnimator.ofFloat(mContent, "alpha", 0.5f, 1.0f)// ,
                        // ObjectAnimator.ofFloat(mContent, "rotationY", ANIMATION_ROTATION_AMT, 0)
                );
                unlockTouchInput();
                mAnimSet.setDuration(ANIMATION_TRANSITION_DURATION).start();
            }
        });
    }

    @Override
    public void onBackStackChanged() {

    }
}
