package com.orion.salesman._object;

/**
 * Created by maidinh on 19/9/2016.
 */
public class API_303 {
    int id;
    String DATE;
    String DATA;

    public API_303() {
    }

    public API_303(String DATE, String DATA) {
        this.DATE = DATE;
        this.DATA = DATA;
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

    public String getDATA() {
        return DATA;
    }

    public void setDATA(String DATA) {
        this.DATA = DATA;
    }
}
