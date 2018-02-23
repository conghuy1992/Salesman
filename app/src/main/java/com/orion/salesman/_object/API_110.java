package com.orion.salesman._object;

/**
 * Created by Huy on 11/9/2016.
 */
public class API_110 {
    int id;
    String DATE;
    String KEY_ROUTE;
    String DATA;


    public API_110(String DATE, String KEY_ROUTE, String DATA) {
        this.DATE = DATE;
        this.KEY_ROUTE = KEY_ROUTE;
        this.DATA = DATA;
    }

    public API_110() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDATE() {
        return DATE;
    }

    public void setDATE(String DATE) {
        this.DATE = DATE;
    }

    public String getKEY_ROUTE() {
        return KEY_ROUTE;
    }

    public void setKEY_ROUTE(String KEY_ROUTE) {
        this.KEY_ROUTE = KEY_ROUTE;
    }

    public String getDATA() {
        return DATA;
    }

    public void setDATA(String DATA) {
        this.DATA = DATA;
    }
}
