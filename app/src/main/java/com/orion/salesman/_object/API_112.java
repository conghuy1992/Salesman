package com.orion.salesman._object;

/**
 * Created by Huy on 11/9/2016.
 */
public class API_112 {
    int id;
    String DATE;
    String WEEK;
    String DATA; // list


    public API_112() {
    }

    public API_112(String DATE, String WEEK, String DATA) {
        this.DATE = DATE;
        this.WEEK = WEEK;
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

    public String getWEEK() {
        return WEEK;
    }

    public void setWEEK(String WEEK) {
        this.WEEK = WEEK;
    }

    public String getDATA() {
        return DATA;
    }

    public void setDATA(String DATA) {
        this.DATA = DATA;
    }
}
