package com.giyer.noogle.network.endpoints;

import com.giyer.noogle.network.dao.GetNewsResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by giyer7 on 3/6/17.
 */

public interface APIClient {

    @GET("search")
    Call<GetNewsResponse> getNews(@Query("format") String format,
                                        @Query("q") String query,
                                        @Query("sort") String sortType);
}
