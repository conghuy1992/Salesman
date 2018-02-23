package com.orion.salesman._object;

/**
 * Created by maidinh on 24/10/2016.
 */
public class OrderGifItem {
    String PRDCD = "";
    String PRDNM = "";
    String SKUCD = "";
    String SKUNM = "";
    String BRNCD = "";
    String BRNNM = "";
    int UNIT = 0;
    int QTY = 0;
    boolean DS=false;

    public boolean isDS() {
        return DS;
    }

    public void setDS(boolean DS) {
        this.DS = DS;
    }

    public String getPRDCD() {
        return PRDCD;
    }

    public void setPRDCD(String PRDCD) {
        this.PRDCD = PRDCD;
    }

    public String getPRDNM() {
        return PRDNM;
    }

    public void setPRDNM(String PRDNM) {
        this.PRDNM = PRDNM;
    }

    public String getSKUCD() {
        return SKUCD;
    }

    public void setSKUCD(String SKUCD) {
        this.SKUCD = SKUCD;
    }

    public String getSKUNM() {
        return SKUNM;
    }

    public void setSKUNM(String SKUNM) {
        this.SKUNM = SKUNM;
    }

    public String getBRNCD() {
        return BRNCD;
    }

    public void setBRNCD(String BRNCD) {
        this.BRNCD = BRNCD;
    }

    public String getBRNNM() {
        return BRNNM;
    }

    public void setBRNNM(String BRNNM) {
        this.BRNNM = BRNNM;
    }

    public int getUNIT() {
        return UNIT;
    }

    public void setUNIT(int UNIT) {
        this.UNIT = UNIT;
    }

    public int getQTY() {
        return QTY;
    }

    public void setQTY(int QTY) {
        this.QTY = QTY;
    }
}
