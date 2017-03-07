package com.giyer.noogleplatform.interceptor;

import com.giyer.noogleplatform.BuildConfig;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by giyer7 on 3/6/17.
 */

public class RequestInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();

        HttpUrl url = originalRequest.url().newBuilder().addQueryParameter("token", BuildConfig.WEBHOSE_TOKEN).build();
        Request newRequest = originalRequest.newBuilder()
                .url(url)
                .build();

        return chain.proceed(newRequest);
    }
}
