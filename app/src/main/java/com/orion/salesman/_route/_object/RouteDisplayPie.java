package com.orion.salesman._route._object;

/**
 * Created by maidinh on 10/8/2016.
 */
public class RouteDisplayPie {
    String ColumnsOne = "";
    String ColumnsTwo = "";
    String ColumnsThree = "";
    String TOOLCD = "";
    boolean isSave = false;

    public RouteDisplayPie(String columnsOne, String columnsTwo, String columnsThree, String TOOLCD) {
        ColumnsOne = columnsOne;
        ColumnsTwo = columnsTwo;
        ColumnsThree = columnsThree;
        this.TOOLCD = TOOLCD;
    }

    public boolean isSave() {
        return isSave;
    }

    public void setIsSave(boolean isSave) {
        this.isSave = isSave;
    }

    public String getTOOLCD() {
        return TOOLCD;
    }

    public void setTOOLCD(String TOOLCD) {
        this.TOOLCD = TOOLCD;
    }

    public String getColumnsOne() {
        return ColumnsOne;
    }

    public void setColumnsOne(String columnsOne) {
        ColumnsOne = columnsOne;
    }

    public String getColumnsTwo() {
        return ColumnsTwo;
    }

    public void setColumnsTwo(String columnsTwo) {
        ColumnsTwo = columnsTwo;
    }

    public String getColumnsThree() {
        return ColumnsThree;
    }

    public void setColumnsThree(String columnsThree) {
        ColumnsThree = columnsThree;
    }
}
