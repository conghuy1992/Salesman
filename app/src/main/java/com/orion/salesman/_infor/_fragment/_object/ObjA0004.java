package com.orion.salesman._infor._fragment._object;

/**
 * Created by maidinh on 28/10/2016.
 */
public class ObjA0004 {
    String V1="";
    String V2="";
    String V3="";
    String V4="";
    String V5="";
    String isRead = "0";
    int id=0;

    public String getIsRead() {
        return isRead;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ObjA0004(String v1, String v2, String v3, String v4, String v5, String isRead,int id) {
        V1 = v1;
        V2 = v2;
        V3 = v3;
        V4 = v4;
        V5 = v5;
        this.isRead = isRead;
        this.id=id;
    }

    public String isRead() {
        return isRead;
    }

    public void setIsRead(String isRead) {
        this.isRead = isRead;
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
}
