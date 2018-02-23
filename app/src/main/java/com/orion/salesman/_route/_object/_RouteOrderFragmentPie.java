package com.orion.salesman._route._object;

import java.util.List;

/**
 * Created by maidinh on 10/8/2016.
 */
public class _RouteOrderFragmentPie {
    String ColumnsOne;
    String ColumnsTwo;
    String ColumnsThree;
    String ColumnsFour;
    String ColumnsFive;
    String ColumnsSix;
    String ColumnsSeven;
    boolean isCheck = false;

    public _RouteOrderFragmentPie() {
    }

    List<_RouteOrderFragmentPie> ListStock;

    public List<_RouteOrderFragmentPie> getListStock() {
        return ListStock;
    }

    public void setListStock(List<_RouteOrderFragmentPie> listStock) {
        ListStock = listStock;
    }

    public _RouteOrderFragmentPie(String columnsOne, String columnsTwo, String columnsThree, String columnsFour, String columnsFive,
                                  String columnsSix, String columnsSeven, boolean isCheck, List<_RouteOrderFragmentPie> listStock) {
        ColumnsOne = columnsOne;
        ColumnsTwo = columnsTwo;
        ColumnsThree = columnsThree;
        ColumnsFour = columnsFour;
        ColumnsFive = columnsFive;
        ColumnsSix = columnsSix;
        ColumnsSeven = columnsSeven;
        this.isCheck = isCheck;
        ListStock = listStock;
    }

    public _RouteOrderFragmentPie(String columnsOne, String columnsTwo, String columnsThree, String columnsFour, String columnsFive, String columnsSix, String columnsSeven) {
        ColumnsOne = columnsOne;
        ColumnsTwo = columnsTwo;
        ColumnsThree = columnsThree;
        ColumnsFour = columnsFour;
        ColumnsFive = columnsFive;
        ColumnsSix = columnsSix;
        ColumnsSeven = columnsSeven;
    }

    public _RouteOrderFragmentPie(String columnsOne, String columnsTwo, String columnsThree, String columnsFour, String columnsFive, String columnsSix, String columnsSeven, boolean isCheck) {
        ColumnsOne = columnsOne;
        ColumnsTwo = columnsTwo;
        ColumnsThree = columnsThree;
        ColumnsFour = columnsFour;
        ColumnsFive = columnsFive;
        ColumnsSix = columnsSix;
        ColumnsSeven = columnsSeven;
        this.isCheck = isCheck;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
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

    public String getColumnsSeven() {
        return ColumnsSeven;
    }

    public void setColumnsSeven(String columnsSeven) {
        ColumnsSeven = columnsSeven;
    }
}
