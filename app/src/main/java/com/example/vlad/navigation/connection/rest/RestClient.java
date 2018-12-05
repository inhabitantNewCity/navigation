package com.example.vlad.navigation.connection.rest;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestHandle;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.ResponseHandlerInterface;
import com.loopj.android.http.SyncHttpClient;

public class RestClient {

    private static final String BASE_URL = "http://192.168.1.37:8080/";

    private SyncHttpClient client = new SyncHttpClient();

    public RequestHandle get(String url, RequestParams params, ResponseHandlerInterface responseHandler) {
        return client.get(getAbsoluteUrl(url), params, responseHandler);
    }

    public void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.post(getAbsoluteUrl(url), params, responseHandler);
    }

    public void getByUrl(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.get(url, params, responseHandler);
    }

    public void postByUrl(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.post(url, params, responseHandler);
    }

    protected String getAbsoluteUrl(String relativeUrl) {
        return BASE_URL + relativeUrl;
    }
}
