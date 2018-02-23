package com.orion.salesman._route._object;

import com.orion.salesman._object.OrderGifItem;
import com.orion.salesman._object.PMPRODUCTLIST;

import java.util.List;

/**
 * Created by maidinh on 16/8/2016.
 */
public class PromotionProduct {
    String V1="";// product name
    String V2="";//PMDES
    String V3="";// QTY
    String V4="";// PM id
    List<OrderGifItem> itemList;
    int CONDITION=0;
    String PMID="";
    boolean DS = false;
    int positionChoose=0;

    public boolean isDS() {
        return DS;
    }

    public void setDS(boolean DS) {
        this.DS = DS;
    }

    public String getPMID() {
        return PMID;
    }

    public void setPMID(String PMID) {
        this.PMID = PMID;
    }

    public int getPositionChoose() {
        return positionChoose;
    }

    public void setPositionChoose(int positionChoose) {
        this.positionChoose = positionChoose;
    }

    public int getCONDITION() {
        return CONDITION;
    }

    public void setCONDITION(int CONDITION) {
        this.CONDITION = CONDITION;
    }

    public List<OrderGifItem> getItemList() {
        return itemList;
    }

    public void setItemList(List<OrderGifItem> itemList) {
        this.itemList = itemList;
    }

    public String getV4() {
        return V4;
    }

    public void setV4(String v4) {
        V4 = v4;
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
