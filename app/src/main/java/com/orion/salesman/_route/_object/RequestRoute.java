package com.orion.salesman._route._object;

/**
 * Created by maidinh on 15/8/2016.
 */
public class RequestRoute {
    String DATE;
    String WKDAY;

    public RequestRoute(String DATE, String WKDAY) {
        this.DATE = DATE;
        this.WKDAY = WKDAY;
    }

    public String getDATE() {
        return DATE;
    }

    public void setDATE(String DATE) {
        this.DATE = DATE;
    }

    public String getWKDAY() {
        return WKDAY;
    }

    public void setWKDAY(String WKDAY) {
        this.WKDAY = WKDAY;
    }
}
