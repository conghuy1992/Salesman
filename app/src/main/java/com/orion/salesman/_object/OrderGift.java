package com.orion.salesman._object;

import java.util.List;

/**
 * Created by maidinh on 24/10/2016.
 */
public class OrderGift {
    List<OrderBuying> BuyList;
    int CONDITION;
    String PMID;
    String PMDES;
    List<OrderGifItem> GiftList;

    public List<OrderBuying> getBuyList() {
        return BuyList;
    }

    public void setBuyList(List<OrderBuying> buyList) {
        BuyList = buyList;
    }

    public int getCONDITION() {
        return CONDITION;
    }

    public void setCONDITION(int CONDITION) {
        this.CONDITION = CONDITION;
    }

    public String getPMID() {
        return PMID;
    }

    public void setPMID(String PMID) {
        this.PMID = PMID;
    }

    public String getPMDES() {
        return PMDES;
    }

    public void setPMDES(String PMDES) {
        this.PMDES = PMDES;
    }

    public List<OrderGifItem> getGiftList() {
        return GiftList;
    }

    public void setGiftList(List<OrderGifItem> giftList) {
        GiftList = giftList;
    }
}
