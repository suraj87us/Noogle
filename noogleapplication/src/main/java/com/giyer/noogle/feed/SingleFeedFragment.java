package com.giyer.noogle.feed;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.giyer.noogle.R;
import com.giyer.noogle.base.ActionFlowFragment;

import butterknife.Bind;

/**
 * Created by giyer7 on 3/11/17.
 */

public class SingleFeedFragment extends ActionFlowFragment<SingleFeedFragment.SingleFeedListener> {

    @Bind(R.id.single_feed_webview)
    WebView mWebView;

    private static String mUrl;

    public static SingleFeedFragment newInstance(Bundle args) {
        SingleFeedFragment fragment = new SingleFeedFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public String getFragmentTitle() {
        return getString(R.string.single_feed_title);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            mUrl = bundle.getString(FeedActivity.URL_REDIRECT);
        }
    }

    @Override
    public View onInitFragment(View rootView, Bundle savedInstanceState) {
        mWebView.loadUrl(mUrl);
        mWebView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        mWebView.setWebViewClient(new WebViewClient());
        mWebView.setWebChromeClient(new WebChromeClient());
        //TODO Review this
        //mWebView.getSettings().setJavaScriptEnabled(true);
        return rootView;
    }

    @Override
    public int getLayout() {
        return R.layout.single_feed_layout;
    }

    @Override
    protected DisplayRequest actionBarDisplay() {
        return DisplayRequest.ACTION_BACK;
    }

    public interface SingleFeedListener extends ActionFlowFragment.ActionFlowFragmentListener{
    }
}
