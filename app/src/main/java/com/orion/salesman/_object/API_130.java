package com.orion.salesman._object;

/**
 * Created by maidinh on 13/9/2016.
 */
public class API_130 {
    int id;
    String DATE;
    String DATA;


    public API_130() {

    }

    public API_130(String DATE, String DATA) {
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
