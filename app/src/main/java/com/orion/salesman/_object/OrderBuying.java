package com.orion.salesman._object;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by maidinh on 24/10/2016.
 */
public class OrderBuying {
    String PRDCD = "";
    String SKUCD = "";
    String BRNCD = "";
    int UNIT = 0;
    int QTY = 0;
    int CSES = 0;
    int BXCS = 0;
    int BXES = 0;
    boolean DS = false;
    List<PMPRODUCTLIST> LIST_PROMOTION = new ArrayList<>();

    public List<PMPRODUCTLIST> getLIST_PROMOTION() {
        return LIST_PROMOTION;
    }

    public void setLIST_PROMOTION(List<PMPRODUCTLIST> LIST_PROMOTION) {
        this.LIST_PROMOTION = LIST_PROMOTION;
    }

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

    public int getCSES() {
        return CSES;
    }

    public void setCSES(int CSES) {
        this.CSES = CSES;
    }

    public int getBXCS() {
        return BXCS;
    }

    public void setBXCS(int BXCS) {
        this.BXCS = BXCS;
    }

    public int getBXES() {
        return BXES;
    }

    public void setBXES(int BXES) {
        this.BXES = BXES;
    }
}
