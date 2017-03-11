package com.giyer.noogle.network.dao;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by giyer7 on 3/6/17.
 */

public class Facebook implements Parcelable {

    @SerializedName("likes")
    @Expose
    private Integer likes;
    @SerializedName("comments")
    @Expose
    private Integer comments;
    @SerializedName("shares")
    @Expose
    private Integer shares;

    public Integer getLikes() {
        return likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }

    public Integer getComments() {
        return comments;
    }

    public void setComments(Integer comments) {
        this.comments = comments;
    }

    public Integer getShares() {
        return shares;
    }

    public void setShares(Integer shares) {
        this.shares = shares;
    }

    protected Facebook(Parcel in) {
        likes = in.readByte() == 0x00 ? null : in.readInt();
        comments = in.readByte() == 0x00 ? null : in.readInt();
        shares = in.readByte() == 0x00 ? null : in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (likes == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(likes);
        }
        if (comments == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(comments);
        }
        if (shares == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(shares);
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Facebook> CREATOR = new Parcelable.Creator<Facebook>() {
        @Override
        public Facebook createFromParcel(Parcel in) {
            return new Facebook(in);
        }

        @Override
        public Facebook[] newArray(int size) {
            return new Facebook[size];
        }
    };

}