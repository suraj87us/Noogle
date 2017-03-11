package com.giyer.noogle.feed;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.giyer.noogle.R;
import com.giyer.noogle.base.ActionFlowActivity;
import com.giyer.noogle.network.HttpUtil;
import com.giyer.noogle.network.dao.GetNewsRequest;
import com.giyer.noogle.network.dao.GetNewsResponse;
import com.giyer.noogle.network.dao.SortType;

import org.greenrobot.eventbus.Subscribe;

/**
 * Created by giyer7 on 3/7/17.
 */

public class FeedActivity extends ActionFlowActivity implements FeedFragment.FeedFragmentListener {

    public static final String URL_REDIRECT = "URL_REDIRECT";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeFeedActivity(savedInstanceState);
    }

    private void initializeFeedActivity(Bundle savedInstanceState) {
        swapFragment(FeedFragment.newInstance(new Bundle()), false, false);
    }

    @Override
    public void makeFeedRequest() {
        showUIBlockingProgress();
        HttpUtil.SendMessage(this, buildNewsFeedRequest());
    }

    @Subscribe
    public void onMessageEvent(final GetNewsResponse getNewsResponse) {
        hideUIBlockingProgress();
        if (getNewsResponse.getResponseCode() != 200) {
            Toast.makeText(this, "Unable to fetch feed. Please trt again later", Toast.LENGTH_SHORT).show();
        } else {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.content_frame);
                    if (fragment.getTag().equals(FeedFragment.FRAGMENT_TAG)) {
                        ((FeedFragment) fragment).getNewsFeedDataFromActivity(getNewsResponse);
                    }
                }
            });
        }
    }

    @Override
    public void onFeedItemClick(int position, String url) {
        Bundle bundle = new Bundle();
        bundle.putString(URL_REDIRECT, url);
        swapFragment(SingleFeedFragment.newInstance(bundle), false, false);
    }

    private GetNewsRequest buildNewsFeedRequest() {
        GetNewsRequest request = new GetNewsRequest();
        request.setQuery("Obama"); // Testing
        request.setSortType(SortType.FB_LIKES.getSortType());
        return request;
    }
}
