package com.orion.salesman._object;

/**
 * Created by maidinh on 16/9/2016.
 */
public class API_132 {
    int id;
    String DATE;
    String PRDCLS1;
    String DATA;

    public API_132() {
    }


    public API_132(String DATE, String PRDCLS1, String DATA) {
        this.DATE = DATE;
        this.PRDCLS1 = PRDCLS1;
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

    public String getPRDCLS1() {
        return PRDCLS1;
    }

    public void setPRDCLS1(String PRDCLS1) {
        this.PRDCLS1 = PRDCLS1;
    }

    public String getDATA() {
        return DATA;
    }

    public void setDATA(String DATA) {
        this.DATA = DATA;
    }
}
