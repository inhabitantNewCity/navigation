package com.example.vlad.navigation.connection.rest;

import com.example.vlad.navigation.connection.rest.parser.MapJsonHandler;
import com.example.vlad.navigation.connection.rest.parser.WayJsonHandler;
import com.example.vlad.navigation.database.model.NavigationMap;
import com.example.vlad.navigation.database.model.NavigationWay;
import com.example.vlad.navigation.database.model.Point;
import com.example.vlad.navigation.database.model.Template;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestHandle;
import com.loopj.android.http.RequestParams;

public class DatabaseClient extends RestClient {

    private static final String GET_MAP = "map/";
    private static final String GET_WAY = "way/";
    private static final String GET_TEMPLATE = "template/";

    private static DatabaseClient instance;

    private DatabaseClient(){}

    public static DatabaseClient getInstance(){
        if(instance==null){
            instance = new DatabaseClient();
        }
        return instance;
    }

    public NavigationMap getMap(String name) {
        RequestParams rp = new RequestParams();
        RequestHandle handle = get(GET_MAP, rp, new MapJsonHandler());
        return (NavigationMap) handle.getTag();
    }

    public NavigationWay getWay(Point pointStart, Point pointFinish, String mapName){
        RequestParams rp = new RequestParams();
        RequestHandle handle = get(GET_WAY, rp, new WayJsonHandler());
        return (NavigationWay) handle.getTag();
    }

    public Template getTemplate(int id){
        RequestParams rp = new RequestParams();
        rp.put("id", id);
        RequestHandle handle = get(GET_TEMPLATE, rp, new WayJsonHandler());
        return (Template) handle.getTag();
    }
}
