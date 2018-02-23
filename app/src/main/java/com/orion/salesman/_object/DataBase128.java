package com.orion.salesman._object;

/**
 * Created by maidinh on 23/11/2016.
 */

public class DataBase128 {
    int id;
    String CUSTCD_128;
    String OMI_128;
    String ORDERQTY_128;
    String CDATE;

    public String getCDATE() {
        return CDATE;
    }

    public void setCDATE(String CDATE) {
        this.CDATE = CDATE;
    }

    public DataBase128(String CUSTCD_128, String OMI_128, String ORDERQTY_128, String CDATE) {
        this.CUSTCD_128 = CUSTCD_128;
        this.OMI_128 = OMI_128;
        this.ORDERQTY_128 = ORDERQTY_128;
        this.CDATE=CDATE;
    }

    public DataBase128() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCUSTCD_128() {
        return CUSTCD_128;
    }

    public void setCUSTCD_128(String CUSTCD_128) {
        this.CUSTCD_128 = CUSTCD_128;
    }

    public String getOMI_128() {
        return OMI_128;
    }

    public void setOMI_128(String OMI_128) {
        this.OMI_128 = OMI_128;
    }

    public String getORDERQTY_128() {
        return ORDERQTY_128;
    }

    public void setORDERQTY_128(String ORDERQTY_128) {
        this.ORDERQTY_128 = ORDERQTY_128;
    }
}
