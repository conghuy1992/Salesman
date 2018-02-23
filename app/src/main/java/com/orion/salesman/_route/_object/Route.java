package com.orion.salesman._route._object;

/**
 * Created by maidinh on 4/8/2016.
 */

public class Route {
    private String V1 = "";
    private String V2 = "";
    private String V3 = "";
    private String V4 = "0";
    private String V5 = "0.0";
    private String V6 = "0.0";
    private String Order = "";
    private String Omi = "";
    boolean isShow = false;
    boolean isClose = false;
    boolean visit = false;
    boolean isTouch = false;

    public String getOmi() {
        return Omi;
    }

    public void setOmi(String omi) {
        Omi = omi;
    }

    public boolean isVisit() {
        return visit;
    }

    public void setVisit(boolean visit) {
        this.visit = visit;
    }

    public boolean isTouch() {
        return isTouch;
    }

    public void setIsTouch(boolean isTouch) {
        this.isTouch = isTouch;
    }

    public String getOrder() {
        return Order;
    }

    public void setOrder(String order) {
        Order = order;
    }

    public boolean isClose() {
        return isClose;
    }

    public void setIsClose(boolean isClose) {
        this.isClose = isClose;
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


    public boolean isShow() {
        return isShow;
    }

    public void setIsShow(boolean isShow) {
        this.isShow = isShow;
    }
}
