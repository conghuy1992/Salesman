package com.orion.salesman._route._object;

/**
 * Created by maidinh on 17/8/2016.
 */
public class ParamsRouteShop {
    String MODE;
    String CUSTCD;
    String DATE;

    public ParamsRouteShop(String MODE, String CUSTCD, String DATE) {
        this.MODE = MODE;
        this.CUSTCD = CUSTCD;
        this.DATE = DATE;
    }

    public String getMODE() {
        return MODE;
    }

    public void setMODE(String MODE) {
        this.MODE = MODE;
    }

    public String getCUSTCD() {
        return CUSTCD;
    }

    public void setCUSTCD(String CUSTCD) {
        this.CUSTCD = CUSTCD;
    }

    public String getDATE() {
        return DATE;
    }

    public void setDATE(String DATE) {
        this.DATE = DATE;
    }
}
