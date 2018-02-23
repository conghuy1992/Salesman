package com.orion.salesman._result._object;

/**
 * Created by maidinh on 5/8/2016.
 */
public class ResultPie {
    String ColumnsOne;
    String ColumnsTwo;
    String ColumnsThree;
    String ColumnsFour;
    String ColumnsFive;

    public ResultPie(String columnsOne, String columnsTwo, String columnsThree, String columnsFour, String columnsFive) {
        ColumnsOne = columnsOne;
        ColumnsTwo = columnsTwo;
        ColumnsThree = columnsThree;
        ColumnsFour = columnsFour;
        ColumnsFive = columnsFive;
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
}
