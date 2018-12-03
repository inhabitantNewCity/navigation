package com.example.vlad.navigation.database;

import com.example.vlad.navigation.database.model.NavigationMap;
import com.example.vlad.navigation.database.model.NavigationWay;

public class DataAccessServiceStub implements DataAccessService {

    @Override
    public NavigationMap getMap() {
        return null;
    }

    @Override
    public NavigationWay getWay() {
        return null;
    }
}
