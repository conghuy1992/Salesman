package com.orion.salesman._object;

import java.util.List;

/**
 * Created by maidinh on 6/9/2016.
 */
public class PMPRODUCTLIST {
    String V1 = "";
    String V2 = "";
    String V3 = "";
    String V4 = "";
    String V5 = "";
    String V6 = "";
    String V7 = "";
    String V8 = "";
    String V9 = "";
    String V10 = "";
    String V11 = "";
    String V12 = "";
    String V13 = "";
    String V14 = "";
    String V15 = "";
    String V16 = "";
    String V17 = "";
    String V18 = "";
    List<BUY> buy;
    List<GIFT> gift;
    List<PMPRODUCTLIST> PMPRODUCTLIST;
    int CONDITION = 1; // 1-> OR   0->AND
    int sumQty = 0;
    String PRDCD = "";
    int PRICE = 0;
    int hightGift=0;

    public int getHightGift() {
        return hightGift;
    }

    public void setHightGift(int hightGift) {
        this.hightGift = hightGift;
    }

    public int getPRICE() {
        return PRICE;
    }

    public void setPRICE(int PRICE) {
        this.PRICE = PRICE;
    }

    public List<com.orion.salesman._object.PMPRODUCTLIST> getPMPRODUCTLIST() {
        return PMPRODUCTLIST;
    }

    public void setPMPRODUCTLIST(List<com.orion.salesman._object.PMPRODUCTLIST> PMPRODUCTLIST) {
        this.PMPRODUCTLIST = PMPRODUCTLIST;
    }

    public String getPRDCD() {
        return PRDCD;
    }

    public void setPRDCD(String PRDCD) {
        this.PRDCD = PRDCD;
    }

    public int getSumQty() {
        return sumQty;
    }

    public void setSumQty(int sumQty) {
        this.sumQty = sumQty;
    }

    public int getCONDITION() {
        return CONDITION;
    }

    public void setCONDITION(int CONDITION) {
        this.CONDITION = CONDITION;
    }

    public String getV18() {
        return V18;
    }

    public void setV18(String v18) {
        V18 = v18;
    }

    public String getV14() {
        return V14;
    }

    public void setV14(String v14) {
        V14 = v14;
    }

    public String getV15() {
        return V15;
    }

    public void setV15(String v15) {
        V15 = v15;
    }

    public String getV16() {
        return V16;
    }

    public void setV16(String v16) {
        V16 = v16;
    }

    public String getV17() {
        return V17;
    }

    public void setV17(String v17) {
        V17 = v17;
    }

    public String getV1() {
        return V1;
    }

    public void setV1(String v1) {
        V1 = v1;
    }

    public String getV2() {
        return V2;
    }

    public void setV2(String v2) {
        V2 = v2;
    }

    public String getV3() {
        return V3;
    }

    public void setV3(String v3) {
        V3 = v3;
    }

    public String getV4() {
        return V4;
    }

    public void setV4(String v4) {
        V4 = v4;
    }

    public String getV5() {
        return V5;
    }

    public void setV5(String v5) {
        V5 = v5;
    }

    public String getV6() {
        return V6;
    }

    public void setV6(String v6) {
        V6 = v6;
    }

    public String getV7() {
        return V7;
    }

    public void setV7(String v7) {
        V7 = v7;
    }

    public String getV8() {
        return V8;
    }

    public void setV8(String v8) {
        V8 = v8;
    }

    public String getV9() {
        return V9;
    }

    public void setV9(String v9) {
        V9 = v9;
    }

    public String getV10() {
        return V10;
    }

    public void setV10(String v10) {
        V10 = v10;
    }

    public String getV11() {
        return V11;
    }

    public void setV11(String v11) {
        V11 = v11;
    }

    public String getV12() {
        return V12;
    }

    public void setV12(String v12) {
        V12 = v12;
    }

    public String getV13() {
        return V13;
    }

    public void setV13(String v13) {
        V13 = v13;
    }

    public List<BUY> getBuy() {
        return buy;
    }

    public void setBuy(List<BUY> buy) {
        this.buy = buy;
    }

    public List<GIFT> getGift() {
        return gift;
    }

    public void setGift(List<GIFT> gift) {
        this.gift = gift;
    }
}
