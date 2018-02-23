package com.orion.salesman._result._object;

import java.util.List;

/**
 * Created by maidinh on 13/9/2016.
 */
public class DATA_API_130 {
    int RESULT;
    String TTLSHOP;
    List<BRANDLIST_OBJECT> BRANDLIST;
    List<SKULIST_OBJECT>SKULIST;

    public int getRESULT() {
        return RESULT;
    }

    public void setRESULT(int RESULT) {
        this.RESULT = RESULT;
    }

    public String getTTLSHOP() {
        return TTLSHOP;
    }

    public void setTTLSHOP(String TTLSHOP) {
        this.TTLSHOP = TTLSHOP;
    }

    public List<BRANDLIST_OBJECT> getBRANDLIST() {
        return BRANDLIST;
    }

    public void setBRANDLIST(List<BRANDLIST_OBJECT> BRANDLIST) {
        this.BRANDLIST = BRANDLIST;
    }

    public List<SKULIST_OBJECT> getSKULIST() {
        return SKULIST;
    }

    public void setSKULIST(List<SKULIST_OBJECT> SKULIST) {
        this.SKULIST = SKULIST;
    }
}
