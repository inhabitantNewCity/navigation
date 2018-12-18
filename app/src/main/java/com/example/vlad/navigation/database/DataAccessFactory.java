package com.example.vlad.navigation.database;

public class DataAccessFactory {

    public static DataAccessService getDataAccess(){
        return new DataAccessServiceStub();
    }
}
