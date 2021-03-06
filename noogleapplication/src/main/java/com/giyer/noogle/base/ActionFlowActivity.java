package com.giyer.noogle.base;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.giyer.noogle.R;
import com.giyer.noogle.category.CategoryActivity;
import com.giyer.noogle.feed.FeedActivity;
import com.giyer.noogle.util.LockableScrollView;
import com.giyer.noogle.util.NavigationAdapter;
import com.giyer.noogle.util.RVListItem;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.giyer.noogle.util.LogUtil.makeLogTag;

/**
 * Created by giyer7 on 3/9/17.
 */

public abstract class ActionFlowActivity extends BaseActivity implements ActionFlowFragment.ActionFlowFragmentListener,
        FragmentManager.OnBackStackChangedListener,
        NavigationAdapter.NavigationClickListener, NavigationView.OnNavigationItemSelectedListener {
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

/*    @Bind(R.id.bottom_navigation)
    BottomNavigationView bottomNavigationView;*/

    protected ActionBarDrawerToggle mDrawerToggle;
    @Bind(R.id.drawer_layout)
    protected DrawerLayout mDrawerLayout;
    /*@Bind(R.id.nav_view)
    RecyclerView mNavigationView;*/
    @Bind(R.id.navigation_view)
    NavigationView navigationView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentViewResource());
        ButterKnife.bind(this);
        //setupBottomNavigation();
        //setupNavDrawer();
        // Set the Toolbar
        setSupportActionBar(mToolBar);
        setupNativeNavDrawer();
        // Enable the Home as Up Button
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportFragmentManager().addOnBackStackChangedListener(this);
    }

    /*private void setupNavDrawer() {
        if (mDrawerLayout == null)
            // current activity does not have a drawer
            return;
        if (mNavigationView != null) {
            setupDrawerSelectionListener();
            //setSelectedItem();
            DisplayMetrics metrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(metrics);
            mNavigationView.getLayoutParams().width = Math.min(PixelUtils.dipToPixels(this, 320), metrics.widthPixels - PixelUtils.dipToPixels(this, 56));
            mNavigationView.setLayoutManager(new LinearLayoutManager(this));
            List<RVListItem> navigationItems = new ArrayList<>();
            navigationItems.add(new RVListHeaderItem(getString(R.string.nav_header_title)));
            navigationItems.add(new RVListIconTextTileItem(getString(R.string.nav_my_feed_title), R.drawable.ic_favorite_black_24dp));
            navigationItems.add(new RVListHeaderItem(getString(R.string.nav_explore_title)));
            navigationItems.add(new RVListIconTextTileItem(getString(R.string.nav_categories_title), R.drawable.ic_whatshot_black_24dp));

            navigationItems.add(new RVListHeaderItem("", true));
            navigationItems.add(new RVListIconTextTileItem(getString(R.string.nav_settings_title), R.drawable.ic_settings_black_24dp));

            NavigationAdapter adapter = new NavigationAdapter(navigationItems, this);
            mNavigationView.setAdapter(adapter);
        }
        logD(TAG, "navigation drawer setup finished");
    }*/

    private void setupNativeNavDrawer() {
        mDrawerToggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, mToolBar, R.string.nav_drawer_open, R.string.nav_drawer_closed) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                supportInvalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                supportInvalidateOptionsMenu();
            }
        };
        mDrawerLayout.addDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });

        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getMenu().findItem(getSelectedNavViewItem()).setChecked(true);
    }

/*    private void setupDrawerSelectionListener() {
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, null, R.string.nav_drawer_open, R.string.nav_drawer_closed) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                supportInvalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                supportInvalidateOptionsMenu();
            }
        };
        mDrawerLayout.addDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });
    }*/

    @Override
    public void setActionBarHeight(int actionBarHeight) {
        getSupportActionBar().setElevation(actionBarHeight);
    }

    protected abstract int getSelectedNavViewItem();
//
//    private void setSelectedNavViewItem() {
//        View view = bottomNavigationView.findViewById(getSelectedNavViewItem());
//        view.performClick();
//    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
        if (mLoadingDisplay) {
            showToast("We are currently processing your request at this time. We apologize for the inconvenience.");
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onNavigationItemClicked(View v, int pos, RVListItem item) {
        /*String navigationItemTitle = item.getPrimaryText();
        if (navigationItemTitle.equals(getString(R.string.nav_my_feed_title))) {
            startActivity(new Intent(getApplicationContext(), FeedActivity.class));
            finish();
        } else if (navigationItemTitle.equals(getString(R.string.nav_categories_title))) {
            startActivity(new Intent(getApplicationContext(), CategoryActivity.class));
            finish();
        } else if (navigationItemTitle.equals(getString(R.string.nav_settings_title))) {
            //startActivity(new Intent(getApplicationContext(), CategoryActivity.class));
            Toast.makeText(this, "Settings In Progress", Toast.LENGTH_SHORT).show();
            finish();
        }*/
    }

//    private void setupBottomNavigation() {
//        bottomNavigationView.setOnNavigationItemSelectedListener(
//                new BottomNavigationView.OnNavigationItemSelectedListener() {
//                    @Override
//                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                        switch (item.getItemId()) {
//                            case R.id.action_recent:
//                                startActivity(new Intent(getApplicationContext(), RecentActivity.class));
//                                finish();
//                                break;
//                            case R.id.action_feed:
//                                startActivity(new Intent(getApplicationContext(), FeedActivity.class));
//                                finish();
//                                break;
//                            case R.id.action_category:
//                                startActivity(new Intent(getApplicationContext(), CategoryActivity.class));
//                                finish();
//                                break;
//                        }
//                        return true;
//                    }
//                });
//    }

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
            case ACTION_BACK:
                getSupportActionBar().show();
                getSupportActionBar().setDisplayShowTitleEnabled(true);
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                mDrawerToggle.setDrawerIndicatorEnabled(false);
                /**
                 * This is a hack to make the UP Arrow color white. Needs to be this way because
                 * DrawerToggle.setDrawerIndicatorEnabled overrides the Up Arrow color set by the
                 * Theme.
                 */
                Drawable upArrow = ContextCompat.getDrawable(this, R.drawable.ic_back_arrow_material);
                upArrow.setColorFilter(ContextCompat.getColor(this, R.color.white), PorterDuff.Mode.SRC_ATOP);
                getSupportActionBar().setHomeAsUpIndicator(upArrow);
                break;

            case ACTION_HAMBURGER:
                getSupportActionBar().show();
                getSupportActionBar().setDisplayShowTitleEnabled(true);
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                mDrawerToggle.setDrawerIndicatorEnabled(true);
                break;
            case NO_HOME_BUTTON:
                getSupportActionBar().show();
                getSupportActionBar().setDisplayShowTitleEnabled(true);
                getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                getSupportActionBar().setDisplayShowHomeEnabled(false);
                mDrawerToggle.setDrawerIndicatorEnabled(false);
                break;
            case NO_ACTION_BAR:
            default:
                getSupportActionBar().hide();
                break;
        }
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
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

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_my_feed) {
            startActivity(new Intent(getApplicationContext(), FeedActivity.class));
            finish();
        } else if (id == R.id.nav_category) {
            startActivity(new Intent(getApplicationContext(), CategoryActivity.class));
            finish();
        } else if (id == R.id.nav_settings) {
            Toast.makeText(this, "Settings In Progress", Toast.LENGTH_SHORT).show();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
