package com.orion.salesman._object;

/**
 * Created by maidinh on 26/10/2016.
 */
public class OrderTable {
    String PRDCD;
    int UNIT;
    int QTY;
    boolean isCheck=false;

    public boolean isCheck() {
        return isCheck;
    }

    public void setIsCheck(boolean isCheck) {
        this.isCheck = isCheck;
    }

    public OrderTable(String PRDCD, int UNIT, int QTY,boolean isCheck) {
        this.PRDCD = PRDCD;
        this.UNIT = UNIT;
        this.QTY = QTY;
        this.isCheck=isCheck;
    }

    public String getPRDCD() {
        return PRDCD;
    }

    public void setPRDCD(String PRDCD) {
        this.PRDCD = PRDCD;
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
