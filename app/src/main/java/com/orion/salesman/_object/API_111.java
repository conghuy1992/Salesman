package com.orion.salesman._object;

/**
 * Created by Huy on 13/9/2016.
 */
public class API_111 {
    int id = 0;
    String DATE = "";
    String DATA = "";
    String WKDAY = "";

    public API_111(String DATE, String DATA, String WKDAY) {
        this.DATE = DATE;
        this.DATA = DATA;
        this.WKDAY = WKDAY;
    }
    public String getWKDAY() {
        return WKDAY;
    }

    public void setWKDAY(String WKDAY) {
        this.WKDAY = WKDAY;
    }

    public API_111() {
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
