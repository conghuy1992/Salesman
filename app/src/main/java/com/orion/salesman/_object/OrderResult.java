package com.orion.salesman._object;

import java.util.List;

/**
 * Created by maidinh on 24/10/2016.
 */
public class OrderResult {
     List<OrderGift> lstBuy ;
     List<OrderBuying> lstEx ;

    public List<OrderGift> getLstBuy() {
        return lstBuy;
    }

    public void setLstBuy(List<OrderGift> lstBuy) {
        this.lstBuy = lstBuy;
    }

    public List<OrderBuying> getLstEx() {
        return lstEx;
    }

    public void setLstEx(List<OrderBuying> lstEx) {
        this.lstEx = lstEx;
    }
}
