package com.giyer.noogle.feed;

/**
 * Created by giyer7 on 3/10/17.
 */

public class FeedItem {
    private String mThumbnail;
    private String mTitle;
    private String mSubTitle;
    private String mLastUpdateTs;
    private String mFeedUrl;

    public FeedItem(String mThumbnail, String mTitle, String mSubTitle, String mLastUpdateTs, String mFeedUrl) {
        this.mThumbnail = mThumbnail;
        this.mTitle = mTitle;
        this.mSubTitle = mSubTitle;
        this.mLastUpdateTs = mLastUpdateTs;
        this.mFeedUrl = mFeedUrl;
    }

    public String getmThumbnail() {
        return mThumbnail;
    }

    public void setmThumbnail(String mThumbnail) {
        this.mThumbnail = mThumbnail;
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getmLastUpdateTs() {
        return mLastUpdateTs;
    }

    public void setmLastUpdateTs(String mLastUpdateTs) {
        this.mLastUpdateTs = mLastUpdateTs;
    }

    public String getmSubTitle() {
        return mSubTitle;
    }

    public void setmSubTitle(String mSubTitle) {
        this.mSubTitle = mSubTitle;
    }

    public String getmFeedUrl() {
        return mFeedUrl;
    }

    public void setmFeedUrl(String mFeedUrl) {
        this.mFeedUrl = mFeedUrl;
    }
}
