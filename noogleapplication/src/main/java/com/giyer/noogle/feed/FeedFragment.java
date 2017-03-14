package com.giyer.noogle.feed;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;

import com.giyer.noogle.R;
import com.giyer.noogle.base.ActionFlowFragment;
import com.giyer.noogle.network.dao.GetNewsResponse;
import com.giyer.noogle.util.Time;
import com.giyer.noogle.util.TimeAndDateUtil;

import java.util.ArrayList;

import butterknife.Bind;

/**
 * Created by giyer7 on 3/7/17.
 */

public class FeedFragment extends ActionFlowFragment<FeedFragment.FeedFragmentListener> implements SearchView.OnQueryTextListener {

    protected static final String FRAGMENT_TAG = FeedFragment.class.getSimpleName();

    private FeedAdapter mAdapter;

    private ArrayList<FeedItem> mFeedItems = new ArrayList<>();
    private SearchView searchView = null;
    public String searchQuery;

    @Bind(R.id.rv_feed_home)
    RecyclerView mFeedRecyclerView;

    @Bind(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mPullToRefresh;

    public static FeedFragment newInstance(Bundle args) {
        FeedFragment fragment = new FeedFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public String getFragmentTitle() {
        return getString(R.string.feed_fragment_title);
    }

    @Override
    public View onInitFragment(View rootView, Bundle savedInstanceState) {
        mFeedRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mFeedRecyclerView.setHasFixedSize(true);
//        if (mFeedItems.isEmpty())
//            getCallback().makeFeedRequest(false);
        mPullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getCallback().makeFeedRequest(true, searchQuery);
            }
        });
        initRecyclerView();
        return rootView;
    }

    protected void getNewsFeedDataFromActivity(GetNewsResponse getNewsResponse) {
        mPullToRefresh.setRefreshing(false);
        if (getNewsResponse != null) {
            buildFeed(getNewsResponse);
        }
    }

    private void initRecyclerView() {
        mAdapter = new FeedAdapter(mFeedItems, getCallback());
        mFeedRecyclerView.setAdapter(mAdapter);
        mFeedRecyclerView.setNestedScrollingEnabled(false);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getActivity(), DividerItemDecoration.HORIZONTAL);
        mFeedRecyclerView.addItemDecoration(dividerItemDecoration);
        mAdapter.notifyDataSetChanged();
    }

    private void buildFeed(@NonNull GetNewsResponse getNewsResponse) {
        if (getNewsResponse.getPosts() != null) {
            for (int i = 0; i < 10; i++) {
                String mainImage = getNewsResponse.getPosts().get(i).getThread().getMainImage();
                String title = getNewsResponse.getPosts().get(i).getThread().getTitle();
                String text = getNewsResponse.getPosts().get(i).getText();
                String url = getNewsResponse.getPosts().get(i).getThread().getUrl();

                if (mainImage == null || title == null || text == null || url == null
                        || mainImage.isEmpty() || title.isEmpty() || text.isEmpty() || url.isEmpty()) {
                    continue;
                }
                Time time = TimeAndDateUtil.parseDateAndTime(getNewsResponse.getPosts().get(i).getThread().getPublished());
                int updateTime;
                String updateText;
                if (time.getHours() > 24) {
                    updateTime = time.getDays();
                    updateText = "Last Updated " + updateTime + "days ago";
                } else {
                    updateTime = time.getMinutes();
                    updateText = "Last Updated " + updateTime + "mins ago";
                }
                FeedItem feedItem = new FeedItem(mainImage, title,
                        text.length() < 100 ? text : text.substring(0, 100) + "...", updateText, url);
                mFeedItems.add(feedItem);
            }
            initRecyclerView();
        }
    }

    @Override
    public int getLayout() {
        return R.layout.feed_layout;
    }

    @Override
    protected DisplayRequest actionBarDisplay() {
        return DisplayRequest.ACTION_HAMBURGER;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
        // Associate searchable configuration with the SearchView
        MenuItem searchItem = menu.findItem(R.id.action_search);
        /*SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        if (searchItem != null) {
            searchView = (SearchView) searchItem.getActionView();
        }
        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));

            searchView.setOnQueryTextListener(this);
        }
*/
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        searchQuery = query;
        getCallback().makeFeedRequest(false, searchQuery);
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    public interface FeedFragmentListener extends ActionFlowFragment.ActionFlowFragmentListener {
        void onFeedItemClick(int position, String url);

        void makeFeedRequest(boolean isPullToRefresh, String searchQuery);
    }
}
