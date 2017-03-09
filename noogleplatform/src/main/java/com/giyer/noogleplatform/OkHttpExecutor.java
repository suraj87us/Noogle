package com.giyer.noogleplatform;

import com.giyer.noogleplatform.base.BaseManager;
import com.giyer.noogleplatform.base.RequestMessage;
import com.giyer.noogleplatform.base.ResponseMessage;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by giyer7 on 3/8/17.
 */

public class OkHttpExecutor extends BaseManager implements APIExecutor {
    private static final String TAG = OkHttpExecutor.class.toString();

    OkHttpClient mClient = getTrustedClient();
    private Request.Builder builder = new Request.Builder();


    private static okhttp3.OkHttpClient getTrustedClient() {
        try {
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.connectTimeout(10, TimeUnit.SECONDS);
            builder.readTimeout(10, TimeUnit.SECONDS);
            builder.writeTimeout(10, TimeUnit.SECONDS);

            return builder.build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void execute(final RequestMessage apiRequest, ApiCallback listener) {
        builder = new Request.Builder();
        String endpoint = getRequestEndpoint(apiRequest);
        Gson gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .create();
        String requestBody = gson.toJson(apiRequest);


        final MediaType JSONType = MediaType.parse("application/json; charset=utf-8");


        try {

            String params = "Params: ";
            for (String key : apiRequest.getParams().keySet()) {
                params += key + ": " + apiRequest.getParams().get(key) + "; ";
            }
            mLogger.d(TAG, params);
            mLogger.d(TAG, "Request Method: " + apiRequest.getRequestMethod());
            mLogger.d(TAG, "Request ID: " + apiRequest.getApiID());
            mLogger.d(TAG, "Request Endpoint: " + endpoint);
            mLogger.d(TAG, "Request Body: " + requestBody);


            Request request;
            builder = builder.url(endpoint);

            addExtraParams(apiRequest);



            if (apiRequest.getRequestMethod().equals(APIEndpoints.RequestMethod.POST)) {
                //Build the request using the JSON from our GSON stuff.
                request = builder
                        .post(RequestBody.create(JSONType, requestBody))
                        .build();
            } else {
                //GET Request (Do we even do PUT requests or anything else?)
                request = builder.get().build();
            }

            mClient.newCall(request).enqueue(new OkHttpExecutorCallback(apiRequest, listener));
        } catch (Exception e) {
            mLogger.logException(OkHttpExecutor.class.getSimpleName(), e);
        }
    }

    private void addExtraParams(RequestMessage apiRequest) {
        Map<String, String> params = apiRequest.getParams();
        for (String param : params.keySet()) {
            builder = builder.url(new HttpUrl.Builder().addQueryParameter(param, BuildConfig.WEBHOSE_TOKEN).build());
        }
    }

    private String getRequestEndpoint(RequestMessage apiRequest) {
        if (apiRequest.getParams() != null) {
            HttpUrl.Builder urlBuilder = HttpUrl.parse(apiRequest.getHost() + apiRequest.getPath()).newBuilder();
            for (Map.Entry<String, String> query : apiRequest.getParams().entrySet()) {
                urlBuilder.addQueryParameter(query.getKey(), query.getValue());
            }
            return urlBuilder.build().toString();
        } else {
            return apiRequest.getHost() + apiRequest.getPath();
        }
    }

    public class OkHttpExecutorCallback implements Callback {
        RequestMessage mRequestMessage;
        ApiCallback mListener;

        public OkHttpExecutorCallback(RequestMessage requestMessage, ApiCallback listener) {
            mRequestMessage = requestMessage;
            mListener = listener;
        }


        @Override
        public void onFailure(Call call, IOException e) {
            mListener.onExecutorFailure(e);
            mLogger.d(TAG, "Error: " + e.toString());
            mLogger.d(TAG, "Error Request: " + call.toString());
        }

        @Override
        public void onResponse(Call call, Response response) throws IOException {

            String json = response.body().string();
            int responseCode = response.code();

            if (json.length() > 0) {
                if (json.charAt(0) != '{') {
                    if (json.charAt(0) == '<') {
                        json = "{ \"status\": \"" + "FAILURE" + "\" }";
                    } else {
                        json = "{ \"jsonData\": " + json + " }";
                    }
                }
            } else {
                json = "{ \"statusCode\": \"" + response.code() + "\" }";
            }

            mLogger.d(TAG, "Response ID: " + mRequestMessage.getApiID());
            mLogger.d(TAG, "Response Body: " + json);
            mLogger.d(TAG, "Response Code: " + responseCode);

            ResponseMessage responseMessage = (ResponseMessage) (new Gson().fromJson(json, mRequestMessage.getResponseMessageClass()));
            responseMessage.setJsonString(json);
            responseMessage.copyLocalRequestAttributes(mRequestMessage);
            mListener.onResponse(mRequestMessage, responseMessage, responseCode);
        }
    }
}
