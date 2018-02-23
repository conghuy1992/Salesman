package com.orion.salesman._infor._fragment._object;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by maidinh on 31/10/2016.
 */
public class TreeMsg {
    boolean isShow = true;
    boolean checkBox = false;
    int marginLeft = 0;
    String V1="";
    String V2="";
    List<TreeMsg>listChild=new ArrayList<>();
    int image;
    int level=1;

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public int getMarginLeft() {
        return marginLeft;
    }

    public void setMarginLeft(int marginLeft) {
        this.marginLeft = marginLeft;
    }

    public boolean isShow() {
        return isShow;
    }

    public void setIsShow(boolean isShow) {
        this.isShow = isShow;
    }

    public boolean isCheckBox() {
        return checkBox;
    }

    public void setCheckBox(boolean checkBox) {
        this.checkBox = checkBox;
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

    public List<TreeMsg> getListChild() {
        return listChild;
    }

    public void setListChild(List<TreeMsg> listChild) {
        this.listChild = listChild;
    }
}
