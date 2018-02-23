package com.orion.salesman._object;

/**
 * Created by Huy on 11/9/2016.
 */
public class API_121 {
    int id=0;
    String DATE="";
    String DATA="";


    public API_121(String DATE, String DATA) {
        this.DATE = DATE;
        this.DATA = DATA;
    }

    public API_121() {
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
