package com.giyer.noogleplatform.dao;

/**
 * Created by giyer7 on 3/6/17.
 */

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Social implements Parcelable {

    @SerializedName("facebook")
    @Expose
    private Facebook facebook;
    @SerializedName("gplus")
    @Expose
    private Gplus gplus;
    @SerializedName("pinterest")
    @Expose
    private Pinterest pinterest;
    @SerializedName("linkedin")
    @Expose
    private Linkedin linkedin;
    @SerializedName("stumbledupon")
    @Expose
    private Stumbledupon stumbledupon;
    @SerializedName("vk")
    @Expose
    private Vk vk;

    protected Social(Parcel in) {
        facebook = (Facebook) in.readValue(Facebook.class.getClassLoader());
        gplus = (Gplus) in.readValue(Gplus.class.getClassLoader());
        pinterest = (Pinterest) in.readValue(Pinterest.class.getClassLoader());
        linkedin = (Linkedin) in.readValue(Linkedin.class.getClassLoader());
        stumbledupon = (Stumbledupon) in.readValue(Stumbledupon.class.getClassLoader());
        vk = (Vk) in.readValue(Vk.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(facebook);
        dest.writeValue(gplus);
        dest.writeValue(pinterest);
        dest.writeValue(linkedin);
        dest.writeValue(stumbledupon);
        dest.writeValue(vk);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Social> CREATOR = new Parcelable.Creator<Social>() {
        @Override
        public Social createFromParcel(Parcel in) {
            return new Social(in);
        }

        @Override
        public Social[] newArray(int size) {
            return new Social[size];
        }
    };
}