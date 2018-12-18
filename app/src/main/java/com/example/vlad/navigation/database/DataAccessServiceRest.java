package com.example.vlad.navigation.database;

import com.example.vlad.navigation.connection.rest.DatabaseClient;
import com.example.vlad.navigation.database.model.NavigationMap;
import com.example.vlad.navigation.database.model.NavigationWay;
import com.example.vlad.navigation.database.model.Point;
import com.example.vlad.navigation.database.model.Template;

public class DataAccessServiceRest implements DataAccessService {

    private DatabaseCache cache = DatabaseCache.getInstance();
    private DatabaseClient client = DatabaseClient.getInstance();

    @Override
    public NavigationMap getMap() {
        return cache.getMap("CORP1FLOOR2");
    }

    @Override
    public NavigationWay getWay() {
        return cache.getWay(new Point(), new Point(), "CORP1FLOOR2");
    }

    @Override
    public Template getTemplateById(int id) {
        return client.getTemplate(id);
    }
}
