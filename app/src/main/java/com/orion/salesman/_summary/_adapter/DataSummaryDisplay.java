package com.orion.salesman._summary._adapter;

/**
 * Created by maidinh on 12/8/2016.
 */
public class DataSummaryDisplay {
    private String DATE;
    private String TEAM;
    private String MNGEMPID;

    public DataSummaryDisplay(String DATE, String TEAM, String MNGEMPID) {
        this.DATE = DATE;
        this.TEAM = TEAM;
        this.MNGEMPID = MNGEMPID;
    }

    public String getDATE() {
        return DATE;
    }

    public void setDATE(String DATE) {
        this.DATE = DATE;
    }

    public String getTEAM() {
        return TEAM;
    }

    public void setTEAM(String TEAM) {
        this.TEAM = TEAM;
    }

    public String getMNGEMPID() {
        return MNGEMPID;
    }

    public void setMNGEMPID(String MNGEMPID) {
        this.MNGEMPID = MNGEMPID;
    }
}
