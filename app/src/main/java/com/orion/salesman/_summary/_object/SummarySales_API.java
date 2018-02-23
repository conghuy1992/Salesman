package com.orion.salesman._summary._object;

import java.util.ArrayList;

/**
 * Created by maidinh on 10/8/2016.
 */
public class SummarySales_API {
    int RESULT;
    String FINISH;
    ArrayList<SalesReport> LIST;

    public int getRESULT() {
        return RESULT;
    }

    public void setRESULT(int RESULT) {
        this.RESULT = RESULT;
    }

    public String getFINISH() {
        return FINISH;
    }

    public void setFINISH(String FINISH) {
        this.FINISH = FINISH;
    }

    public ArrayList<SalesReport> getLIST() {
        return LIST;
    }

    public void setLIST(ArrayList<SalesReport> LIST) {
        this.LIST = LIST;
    }
}
