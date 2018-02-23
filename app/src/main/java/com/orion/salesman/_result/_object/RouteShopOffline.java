package com.orion.salesman._result._object;

/**
 * Created by maidinh on 5/9/2016.
 */
public class RouteShopOffline {
    String DATE;
    String WEEK;
    String DATA;
    int SM_ID;

    public int getSM_ID() {
        return SM_ID;
    }

    public void setSM_ID(int SM_ID) {
        this.SM_ID = SM_ID;
    }

    public RouteShopOffline(String DATE, String WEEK, String DATA,int SM_ID) {
        this.DATE = DATE;
        this.WEEK = WEEK;
        this.DATA = DATA;
        this.SM_ID=SM_ID;
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
