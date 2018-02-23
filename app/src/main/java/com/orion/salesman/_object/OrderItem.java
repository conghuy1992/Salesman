package com.orion.salesman._object;

/**
 * Created by maidinh on 24/10/2016.
 */
public class OrderItem {
    String PRID = "";
    String PRDCD = "";
    String SKUCD = "";
    String BRNCD = "";
    int UNIT = 0;
    int QTY = 0;

    public String getPRID() {
        return PRID;
    }

    public void setPRID(String PRID) {
        this.PRID = PRID;
    }

    public String getPRDCD() {
        return PRDCD;
    }

    public void setPRDCD(String PRDCD) {
        this.PRDCD = PRDCD;
    }

    public String getSKUCD() {
        return SKUCD;
    }

    public void setSKUCD(String SKUCD) {
        this.SKUCD = SKUCD;
    }

    public String getBRNCD() {
        return BRNCD;
    }

    public void setBRNCD(String BRNCD) {
        this.BRNCD = BRNCD;
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
