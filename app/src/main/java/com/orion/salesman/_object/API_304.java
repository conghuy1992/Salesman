package com.orion.salesman._object;

/**
 * Created by Huy on 17/9/2016.
 */
public class API_304 {
    int id;
    String DATE;
    String HIDEPTCD;
    String DEALERID;
    String DATA;

    public API_304(String DATE, String HIDEPTCD, String DEALERID, String DATA) {
        this.DATE = DATE;
        this.HIDEPTCD = HIDEPTCD;
        this.DEALERID = DEALERID;
        this.DATA = DATA;
    }

    public API_304() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDATE() {
        return DATE;
    }

    public void setDATE(String DATE) {
        this.DATE = DATE;
    }

    public String getHIDEPTCD() {
        return HIDEPTCD;
    }

    public void setHIDEPTCD(String HIDEPTCD) {
        this.HIDEPTCD = HIDEPTCD;
    }

    public String getDEALERID() {
        return DEALERID;
    }

    public void setDEALERID(String DEALERID) {
        this.DEALERID = DEALERID;
    }

    public String getDATA() {
        return DATA;
    }

    public void setDATA(String DATA) {
        this.DATA = DATA;
    }
}
