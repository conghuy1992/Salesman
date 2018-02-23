package com.orion.salesman._object;

/**
 * Created by maidinh on 4/12/2017.
 */

public class ConvertUnit {
    String CSES = "0";
    String BXCS = "0";
    String BXES = "0";

    public ConvertUnit() {
    }

    public ConvertUnit(String CSES, String BXCS, String BXES) {
        this.CSES = CSES;
        this.BXCS = BXCS;
        this.BXES = BXES;
    }

    public String getCSES() {
        return CSES;
    }

    public void setCSES(String CSES) {
        this.CSES = CSES;
    }

    public String getBXCS() {
        return BXCS;
    }

    public void setBXCS(String BXCS) {
        this.BXCS = BXCS;
    }

    public String getBXES() {
        return BXES;
    }

    public void setBXES(String BXES) {
        this.BXES = BXES;
    }
}
