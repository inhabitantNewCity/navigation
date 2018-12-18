package com.example.vlad.navigation.database;

import com.example.vlad.navigation.database.model.NavigationMap;
import com.example.vlad.navigation.database.model.NavigationWay;
import com.example.vlad.navigation.database.model.Template;

public interface DataAccessService {

    NavigationMap getMap();

    NavigationWay getWay();

    Template getTemplateById(int id);
}
