package com.orion.salesman._route._object;

/**
 * Created by maidinh on 30/8/2016.
 */
public class StockInputObject {
    String CDATE="";
    String CUSTCD="";
    String PRDCD="";
    String CASEQTY="";
    String EAQTY="";
    String RETURNQTY="";
    String STOCKCATEGORY="";

    public String getSTOCKCATEGORY() {
        return STOCKCATEGORY;
    }

    public void setSTOCKCATEGORY(String STOCKCATEGORY) {
        this.STOCKCATEGORY = STOCKCATEGORY;
    }

    public String getCDATE() {
        return CDATE;
    }

    public void setCDATE(String CDATE) {
        this.CDATE = CDATE;
    }

    public String getCUSTCD() {
        return CUSTCD;
    }

    public void setCUSTCD(String CUSTCD) {
        this.CUSTCD = CUSTCD;
    }

    public String getPRDCD() {
        return PRDCD;
    }

    public void setPRDCD(String PRDCD) {
        this.PRDCD = PRDCD;
    }

    public String getCASEQTY() {
        return CASEQTY;
    }

    public void setCASEQTY(String CASEQTY) {
        this.CASEQTY = CASEQTY;
    }

    public String getEAQTY() {
        return EAQTY;
    }

    public void setEAQTY(String EAQTY) {
        this.EAQTY = EAQTY;
    }

    public String getRETURNQTY() {
        return RETURNQTY;
    }

    public void setRETURNQTY(String RETURNQTY) {
        this.RETURNQTY = RETURNQTY;
    }
}
