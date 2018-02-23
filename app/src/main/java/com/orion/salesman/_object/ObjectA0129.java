package com.orion.salesman._object;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by maidinh on 23/11/2016.
 */

public class ObjectA0129 {
    List<DISPLAYLIST> DISPLAYLIST = new ArrayList<>();
    List<STOCKLIST> STOCKLIST = new ArrayList<>();
    List<ORDERLIST> ORDERLIST = new ArrayList<>();
    int RESULT;

    public int getRESULT() {
        return RESULT;
    }

    public void setRESULT(int RESULT) {
        this.RESULT = RESULT;
    }

    public List<com.orion.salesman._object.DISPLAYLIST> getDISPLAYLIST() {
        return DISPLAYLIST;
    }

    public void setDISPLAYLIST(List<com.orion.salesman._object.DISPLAYLIST> DISPLAYLIST) {
        this.DISPLAYLIST = DISPLAYLIST;
    }

    public List<com.orion.salesman._object.STOCKLIST> getSTOCKLIST() {
        return STOCKLIST;
    }

    public void setSTOCKLIST(List<com.orion.salesman._object.STOCKLIST> STOCKLIST) {
        this.STOCKLIST = STOCKLIST;
    }

    public List<com.orion.salesman._object.ORDERLIST> getORDERLIST() {
        return ORDERLIST;
    }

    public void setORDERLIST(List<com.orion.salesman._object.ORDERLIST> ORDERLIST) {
        this.ORDERLIST = ORDERLIST;
    }
}
