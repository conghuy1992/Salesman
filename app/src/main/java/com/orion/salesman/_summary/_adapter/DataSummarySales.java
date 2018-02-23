package com.orion.salesman._summary._adapter;

/**
 * Created by maidinh on 9/8/2016.
 */
public class DataSummarySales {
    String DATE="";
    String MNGEMPID="";

    public DataSummarySales(String DATE, String MNGEMPID) {
        this.DATE = DATE;
        this.MNGEMPID = MNGEMPID;
    }

    public String getDATE() {
        return DATE;
    }

    public void setDATE(String DATE) {
        this.DATE = DATE;
    }

    public String getMNGEMPID() {
        return MNGEMPID;
    }

    public void setMNGEMPID(String MNGEMPID) {
        this.MNGEMPID = MNGEMPID;
    }
}
