package com.orion.salesman._route._object;

/**
 * Created by maidinh on 18/8/2016.
 */
public class PromotionPieObject {
    String V1 = "";
    String V2 = "";
    String V3 = "";
    String V4 = "";
    String LEVEL = "";
    String GIFTAMTPERCENT = "";

    public PromotionPieObject(String v1, String v2, String v3, String v4, String LEVEL, String GIFTAMTPERCENT) {
        V1 = v1;
        V2 = v2;
        V3 = v3;
        V4 = v4;
        this.LEVEL = LEVEL;
        this.GIFTAMTPERCENT = GIFTAMTPERCENT;
    }

    public String getLEVEL() {
        return LEVEL;
    }

    public void setLEVEL(String LEVEL) {
        this.LEVEL = LEVEL;
    }

    public String getGIFTAMTPERCENT() {
        return GIFTAMTPERCENT;
    }

    public void setGIFTAMTPERCENT(String GIFTAMTPERCENT) {
        this.GIFTAMTPERCENT = GIFTAMTPERCENT;
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
}
