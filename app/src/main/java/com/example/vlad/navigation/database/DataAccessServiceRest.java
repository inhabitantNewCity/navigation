package com.example.vlad.navigation.database;

import com.example.vlad.navigation.database.model.NavigationMap;
import com.example.vlad.navigation.database.model.NavigationWay;
import com.example.vlad.navigation.database.model.Point;

public class DataAccessServiceRest implements DataAccessService {

    DatabaseCache cache = DatabaseCache.getInstance();

    @Override
    public NavigationMap getMap() {
        return cache.getMap("CORP1FLOOR2");
    }

    @Override
    public NavigationWay getWay() {
        return cache.getWay(new Point(), new Point(), "CORP1FLOOR2");
    }
}
