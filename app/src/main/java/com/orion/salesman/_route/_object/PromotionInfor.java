package com.orion.salesman._route._object;

import com.orion.salesman._object.PMPRODUCTLIST;

/**
 * Created by maidinh on 16/8/2016.
 */
public class PromotionInfor {
    String V1 = "";
    String V2 = "";
    String V3 = "";
    PMPRODUCTLIST pmproductlist;
    String PM_LEVEL = "";
    String GIFTAMTPERCENT = "";

    public PromotionInfor(String v1, String v2, String v3, PMPRODUCTLIST pmproductlist, String PM_LEVEL, String GIFTAMTPERCENT) {
        this.V1 = v1;
        this.V2 = v2;
        this.V3 = v3;
        this.pmproductlist = pmproductlist;
        this.PM_LEVEL = PM_LEVEL;
        this.GIFTAMTPERCENT = GIFTAMTPERCENT;
    }

    public String getGIFTAMTPERCENT() {
        return GIFTAMTPERCENT;
    }

    public void setGIFTAMTPERCENT(String GIFTAMTPERCENT) {
        this.GIFTAMTPERCENT = GIFTAMTPERCENT;
    }

    public String getPM_LEVEL() {
        return PM_LEVEL;
    }

    public void setPM_LEVEL(String PM_LEVEL) {
        this.PM_LEVEL = PM_LEVEL;
    }

    public PMPRODUCTLIST getPmproductlist() {
        return pmproductlist;
    }

    public void setPmproductlist(PMPRODUCTLIST pmproductlist) {
        this.pmproductlist = pmproductlist;
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
}
