package com.orion.salesman._object;

/**
 * Created by maidinh on 26/10/2016.
 */
public class RESEND_125 {
    int ID = 0;
    String DATA = "";
    String DATE = "";
    String SHOPID = "";

    public String getDATE() {
        return DATE;
    }

    public void setDATE(String DATE) {
        this.DATE = DATE;
    }

    public String getSHOPID() {
        return SHOPID;
    }

    public void setSHOPID(String SHOPID) {
        this.SHOPID = SHOPID;
    }

    public RESEND_125() {
    }

    public RESEND_125(String DATA, String SHOPID, String DATE) {
        this.DATA = DATA;
        this.SHOPID = SHOPID;
        this.DATE = DATE;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getDATA() {
        return DATA;
    }

    public void setDATA(String DATA) {
        this.DATA = DATA;
    }
}
