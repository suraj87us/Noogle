package com.giyer.noogleplatform.dao;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by giyer7 on 3/6/17.
 */

public class GetNewsResponse {

    @SerializedName("posts")
    @Expose
    private List<Post> posts = null;
    @SerializedName("totalResults")
    @Expose
    private Integer totalResults;
    @SerializedName("moreResultsAvailable")
    @Expose
    private Integer moreResultsAvailable;
    @SerializedName("next")
    @Expose
    private String next;
    @SerializedName("requestsLeft")
    @Expose
    private Integer requestsLeft;

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public Integer getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(Integer totalResults) {
        this.totalResults = totalResults;
    }

    public Integer getMoreResultsAvailable() {
        return moreResultsAvailable;
    }

    public void setMoreResultsAvailable(Integer moreResultsAvailable) {
        this.moreResultsAvailable = moreResultsAvailable;
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }

    public Integer getRequestsLeft() {
        return requestsLeft;
    }

    public void setRequestsLeft(Integer requestsLeft) {
        this.requestsLeft = requestsLeft;
    }

}