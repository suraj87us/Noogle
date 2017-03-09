package com.giyer.noogleplatform.dao;

/**
 * Created by giyer7 on 3/6/17.
 */

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Linkedin implements Parcelable {

    @SerializedName("shares")
    @Expose
    private Integer shares;

    protected Linkedin(Parcel in) {
        shares = in.readByte() == 0x00 ? null : in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (shares == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(shares);
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Linkedin> CREATOR = new Parcelable.Creator<Linkedin>() {
        @Override
        public Linkedin createFromParcel(Parcel in) {
            return new Linkedin(in);
        }

        @Override
        public Linkedin[] newArray(int size) {
            return new Linkedin[size];
        }
    };
}
