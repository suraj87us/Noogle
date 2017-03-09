package com.giyer.noogleplatform.dao;

import android.os.Parcel;
import android.os.Parcelable;

import com.giyer.noogleplatform.base.MainServiceResponse;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by giyer7 on 3/6/17.
 */

public class GetNewsResponse extends MainServiceResponse implements Parcelable {

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

    protected GetNewsResponse(Parcel in) {
        if (in.readByte() == 0x01) {
            posts = new ArrayList<Post>();
            in.readList(posts, Post.class.getClassLoader());
        } else {
            posts = null;
        }
        totalResults = in.readByte() == 0x00 ? null : in.readInt();
        moreResultsAvailable = in.readByte() == 0x00 ? null : in.readInt();
        next = in.readString();
        requestsLeft = in.readByte() == 0x00 ? null : in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (posts == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(posts);
        }
        if (totalResults == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(totalResults);
        }
        if (moreResultsAvailable == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(moreResultsAvailable);
        }
        dest.writeString(next);
        if (requestsLeft == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(requestsLeft);
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<GetNewsResponse> CREATOR = new Creator<GetNewsResponse>() {
        @Override
        public GetNewsResponse createFromParcel(Parcel in) {
            return new GetNewsResponse(in);
        }

        @Override
        public GetNewsResponse[] newArray(int size) {
            return new GetNewsResponse[size];
        }
    };

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