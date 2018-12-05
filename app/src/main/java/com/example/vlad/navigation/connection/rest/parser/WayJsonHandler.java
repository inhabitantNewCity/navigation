package com.example.vlad.navigation.connection.rest.parser;

import android.util.Log;

import com.example.vlad.navigation.database.model.Line;
import com.example.vlad.navigation.database.model.NavigationMap;
import com.example.vlad.navigation.database.model.NavigationWay;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;

import java.io.IOException;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class WayJsonHandler extends JsonHttpResponseHandler {

    private ObjectMapper mapper;
    private CollectionType typeReference;

    public WayJsonHandler() {
        this.mapper = new ObjectMapper();
        this.typeReference =
                TypeFactory.defaultInstance().constructCollectionType(List.class, Line.class);
    }

    @Override
    public void onSuccess(int statusCode, Header[] headers, String responseString) {
        try {
            List<Line> lines = mapper.readValue(responseString, typeReference);
        } catch (IOException e) {
            Log.d("error", e.getMessage());
        }
    }

    @Override
    protected NavigationWay parseResponse(byte[] responseBody) throws JSONException {
        try {
            List<Line> lines = mapper.readValue(responseBody, typeReference);
            return new NavigationWay(NavigationMap.convert(lines));
        } catch (IOException e) {
            Log.d("error", e.getMessage());
        }
        return null;
    }
}