package com.orion.salesman._download._fragment;

/**
 * Created by Huy on 8/9/2016.
 */
public class ObjectDownload {
    String V1 = "";
    String V2 = "";
    boolean isCheck = false;
    boolean isDownload = false;

    public ObjectDownload(String v1, String v2, boolean isCheck, boolean isDownload) {
        this.V1 = v1;
        this.V2 = v2;
        this.isCheck = isCheck;
        this.isDownload = isDownload;
    }

    public ObjectDownload() {
    }

    public boolean isDownload() {
        return isDownload;
    }

    public void setIsDownload(boolean isDownload) {
        this.isDownload = isDownload;
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

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }
}
