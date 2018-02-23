package com.orion.salesman._route._object;

import java.util.List;

/**
 * Created by maidinh on 16/8/2016.
 */
public class RouteConfirm {
    String V1 = "";
    String V2 = "";
    String V3 = "";
    String V4 = "";
    String V5;
    boolean V6 = false; // true: have promotion
    String V7 = "";
    boolean isCheck = false;
    String TPRDCD = "";
    String PRDCD = "";
    int KIND = 1; // 1: EA, 2: Cs, 3: Bx
    String BXEAQTY = "0";
    String CSEEAQTY = "0";
    String BXCSEQTY = "0";
    String PMID = "";
    String GIFTAMTPERCENT = "";
    boolean isSave = false;
    String PRDCLS1 = "";

    public String getPRDCLS1() {
        return PRDCLS1;
    }

    public void setPRDCLS1(String PRDCLS1) {
        this.PRDCLS1 = PRDCLS1;
    }

    public boolean isSave() {
        return isSave;
    }

    public void setIsSave(boolean isSave) {
        this.isSave = isSave;
    }

    public String getGIFTAMTPERCENT() {
        return GIFTAMTPERCENT;
    }

    public void setGIFTAMTPERCENT(String GIFTAMTPERCENT) {
        this.GIFTAMTPERCENT = GIFTAMTPERCENT;
    }

    public String getPMID() {
        return PMID;
    }

    public void setPMID(String PMID) {
        this.PMID = PMID;
    }

    public String getBXEAQTY() {
        return BXEAQTY;
    }

    public void setBXEAQTY(String BXEAQTY) {
        this.BXEAQTY = BXEAQTY;
    }

    public String getCSEEAQTY() {
        return CSEEAQTY;
    }

    public void setCSEEAQTY(String CSEEAQTY) {
        this.CSEEAQTY = CSEEAQTY;
    }

    public String getBXCSEQTY() {
        return BXCSEQTY;
    }

    public void setBXCSEQTY(String BXCSEQTY) {
        this.BXCSEQTY = BXCSEQTY;
    }

    public int getKIND() {
        return KIND;
    }

    public void setKIND(int KIND) {
        this.KIND = KIND;
    }

    public String getPRDCD() {
        return PRDCD;
    }

    public void setPRDCD(String PRDCD) {
        this.PRDCD = PRDCD;
    }

    public String getTPRDCD() {
        return TPRDCD;
    }

    public void setTPRDCD(String TPRDCD) {
        this.TPRDCD = TPRDCD;
    }

    List<RouteConfirm> ListConfirm;

    public boolean isCheck() {
        return isCheck;
    }

    public void setIsCheck(boolean isCheck) {
        this.isCheck = isCheck;
    }

    public List<RouteConfirm> getListConfirm() {
        return ListConfirm;
    }

    public void setListConfirm(List<RouteConfirm> listConfirm) {
        ListConfirm = listConfirm;
    }

    public RouteConfirm() {
    }

    public RouteConfirm(String v1, String v2, String v3, String v4, String v5, boolean v6, String v7) {
        V1 = v1;
        V2 = v2;
        V3 = v3;
        V4 = v4;
        V5 = v5;
        V6 = v6;
        V7 = v7;
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

    public boolean isV6() {
        return V6;
    }

    public void setV6(boolean v6) {
        V6 = v6;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    public String getV7() {
        return V7;
    }

    public void setV7(String v7) {
        V7 = v7;
    }
}
