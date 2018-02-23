package com.orion.salesman._route._object;

/**
 * Created by maidinh on 4/8/2016.
 */
public class OrderInformation {
    String ColumnsOne;
    String ColumnsTwo;
    String ColumnsThree;
    String ColumnsFour;
    String ColumnsFive;
    String ColumnsSix;

    public OrderInformation(String columnsOne, String columnsTwo, String columnsThree, String columnsFour, String columnsFive, String columnsSix) {
        ColumnsOne = columnsOne;
        ColumnsTwo = columnsTwo;
        ColumnsThree = columnsThree;
        ColumnsFour = columnsFour;
        ColumnsFive = columnsFive;
        ColumnsSix = columnsSix;
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

    public String getColumnsFour() {
        return ColumnsFour;
    }

    public void setColumnsFour(String columnsFour) {
        ColumnsFour = columnsFour;
    }

    public String getColumnsFive() {
        return ColumnsFive;
    }

    public void setColumnsFive(String columnsFive) {
        ColumnsFive = columnsFive;
    }

    public String getColumnsSix() {
        return ColumnsSix;
    }

    public void setColumnsSix(String columnsSix) {
        ColumnsSix = columnsSix;
    }
}
