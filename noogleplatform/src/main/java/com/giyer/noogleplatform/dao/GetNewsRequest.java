package com.giyer.noogleplatform.dao;

import android.os.Parcel;
import android.os.Parcelable;

import com.giyer.noogleplatform.APIEndpoints;
import com.giyer.noogleplatform.base.MainServiceRequest;

import java.util.Map;

/**
 * Created by giyer7 on 3/8/17.
 */

public class GetNewsRequest extends MainServiceRequest implements Parcelable {

    private String query;
    private SortType sortType;

    protected GetNewsRequest(Parcel in) {
        query = in.readString();
        sortType = (SortType) in.readValue(SortType.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(query);
        dest.writeValue(sortType);
    }

    public static final Creator<GetNewsRequest> CREATOR = new Creator<GetNewsRequest>() {
        @Override
        public GetNewsRequest createFromParcel(Parcel in) {
            return new GetNewsRequest(in);
        }

        @Override
        public GetNewsRequest[] newArray(int size) {
            return new GetNewsRequest[size];
        }
    };

    @Override
    public APIEndpoints.RequestMethod getRequestMethod() {
        return APIEndpoints.RequestMethod.GET;
    }

    @Override
    public String getPath() {
        return APIEndpoints.GET_FEED_ENDPOINT;
    }

    @Override
    public Class<?> getResponseMessageClass() {
        return GetNewsResponse.class;
    }

    @Override
    public String getApiID() {
        return APIEndpoints.GET_FEED_ID;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public Map<String, String> getParams() {
        Map<String, String> params = super.getParams();
        params.put("q", query);
        params.put("sort", sortType.getSortType());
        return params;
    }
}
