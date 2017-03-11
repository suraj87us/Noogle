package com.giyer.noogle.network.dao;

import android.support.annotation.NonNull;

/**
 * Created by giyer7 on 3/8/17.
 */
public enum SortType {
    RELEVANCY("relevancy"),
    FB_LIKES("social.facebook.likes"),
    FB_SHARES("social.facebook.shares"),
    FB_COMMENTS("social.facebook.comments"),
    GPLUS_SHARES("social.gplus.shares"),
    PINTEREST_SHARES("social.pinterest.shares"),
    LINKEDIN_SHARES("social.linkedin.shares"),
    STUMBLEDUPON_SHARES("social.stumbledupon.shares"),
    PERF_SCORE("performance_score"),
    RATING("rating");

    private String sort;

    SortType(String sort) { this.sort = sort;}

    @NonNull
    public String getSortType() {
        return sort;
    }

}
