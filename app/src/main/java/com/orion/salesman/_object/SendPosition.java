package com.orion.salesman._object;

import java.util.List;

/**
 * Created by maidinh on 15/8/2016.
 */
public class SendPosition {
    int ID;
    String DATE;
    String TIME;
    String LON;
    String LAT;
    String SPEED;
    String ANGLE;
    String SEID;


    public SendPosition() {
    }

    public SendPosition(String DATE, String TIME, String LON, String LAT, String SPEED, String ANGLE,String SEID) {
        this.DATE = DATE;
        this.TIME = TIME;
        this.LON = LON;
        this.LAT = LAT;
        this.SPEED = SPEED;
        this.ANGLE = ANGLE;
        this.SEID=SEID;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getSEID() {
        return SEID;
    }

    public void setSEID(String SEID) {
        this.SEID = SEID;
    }

    public String getDATE() {
        return DATE;
    }

    public void setDATE(String DATE) {
        this.DATE = DATE;
    }

    public String getTIME() {
        return TIME;
    }

    public void setTIME(String TIME) {
        this.TIME = TIME;
    }

    public String getLON() {
        return LON;
    }

    public void setLON(String LON) {
        this.LON = LON;
    }

    public String getLAT() {
        return LAT;
    }

    public void setLAT(String LAT) {
        this.LAT = LAT;
    }

    public String getSPEED() {
        return SPEED;
    }

    public void setSPEED(String SPEED) {
        this.SPEED = SPEED;
    }

    public String getANGLE() {
        return ANGLE;
    }

    public void setANGLE(String ANGLE) {
        this.ANGLE = ANGLE;
    }
}
