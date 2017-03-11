package com.giyer.noogle.network.dao;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by giyer7 on 3/6/17.
 */

public class Entities implements Parcelable {

    @SerializedName("persons")
    @Expose
    private List<Object> persons = null;
    @SerializedName("organizations")
    @Expose
    private List<Object> organizations = null;
    @SerializedName("locations")
    @Expose
    private List<Object> locations = null;

    protected Entities(Parcel in) {
        if (in.readByte() == 0x01) {
            persons = new ArrayList<Object>();
            in.readList(persons, Object.class.getClassLoader());
        } else {
            persons = null;
        }
        if (in.readByte() == 0x01) {
            organizations = new ArrayList<Object>();
            in.readList(organizations, Object.class.getClassLoader());
        } else {
            organizations = null;
        }
        if (in.readByte() == 0x01) {
            locations = new ArrayList<Object>();
            in.readList(locations, Object.class.getClassLoader());
        } else {
            locations = null;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (persons == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(persons);
        }
        if (organizations == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(organizations);
        }
        if (locations == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(locations);
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Entities> CREATOR = new Parcelable.Creator<Entities>() {
        @Override
        public Entities createFromParcel(Parcel in) {
            return new Entities(in);
        }

        @Override
        public Entities[] newArray(int size) {
            return new Entities[size];
        }
    };
}