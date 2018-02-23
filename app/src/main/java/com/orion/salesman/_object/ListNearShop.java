package com.orion.salesman._object;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by maidinh on 15/12/2016.
 */

public class ListNearShop {
    List<NearShop> LIST = new ArrayList<>();
    int RESULT;

    public List<NearShop> getLIST() {
        return LIST;
    }

    public void setLIST(List<NearShop> LIST) {
        this.LIST = LIST;
    }

    public int getRESULT() {
        return RESULT;
    }

    public void setRESULT(int RESULT) {
        this.RESULT = RESULT;
    }
}
