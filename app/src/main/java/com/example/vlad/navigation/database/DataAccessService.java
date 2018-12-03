package com.example.vlad.navigation.database;

import com.example.vlad.navigation.database.model.NavigationMap;
import com.example.vlad.navigation.database.model.NavigationWay;

public interface DataAccessService {

    NavigationMap getMap();

    NavigationWay getWay();
}
