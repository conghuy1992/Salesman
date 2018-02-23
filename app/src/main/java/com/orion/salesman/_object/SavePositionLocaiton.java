package com.orion.salesman._object;

/**
 * Created by maidinh on 7/9/2016.
 */


public class SavePositionLocaiton {
    int position;
    long LON;
    long LAT;
    String ADDR1;
    String ADDR2;
    String ADDR3;


    public SavePositionLocaiton(int position, long LON, long LAT, String ADDR1, String ADDR2, String ADDR3) {
        this.position = position;
        this.LON = LON;
        this.LAT = LAT;
        this.ADDR1 = ADDR1;
        this.ADDR2 = ADDR2;
        this.ADDR3 = ADDR3;
    }

    public String getADDR1() {
        return ADDR1;
    }

    public void setADDR1(String ADDR1) {
        this.ADDR1 = ADDR1;
    }

    public String getADDR2() {
        return ADDR2;
    }

    public void setADDR2(String ADDR2) {
        this.ADDR2 = ADDR2;
    }

    public String getADDR3() {
        return ADDR3;
    }

    public void setADDR3(String ADDR3) {
        this.ADDR3 = ADDR3;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public long getLON() {
        return LON;
    }

    public void setLON(long LON) {
        this.LON = LON;
    }

    public long getLAT() {
        return LAT;
    }

    public void setLAT(long LAT) {
        this.LAT = LAT;
    }
}
