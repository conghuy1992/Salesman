package com.orion.salesman._result._object;

import java.util.List;

/**
 * Created by maidinh on 5/8/2016.
 */
public class RouteSalesShop {
    String V1; // brand
    String V2; // visit
    String V3; // bill
    String V4; // %
    String V5; // mtd 1
    String V6; // mtd 2
    String V7; // dis 1
    String V8; // dis 2
    boolean isShow = false;

    public boolean isShow() {
        return isShow;
    }

    public void setIsShow(boolean isShow) {
        this.isShow = isShow;
    }

    List<RouteSalesShop> arrSalesShops;

    public List<RouteSalesShop> getArrSalesShops() {
        return arrSalesShops;
    }

    public void setArrSalesShops(List<RouteSalesShop> arrSalesShops) {
        this.arrSalesShops = arrSalesShops;
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

}
