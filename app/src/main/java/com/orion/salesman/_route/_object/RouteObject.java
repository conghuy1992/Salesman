package com.orion.salesman._route._object;

/**
 * Created by Huy on 19/9/2016.
 */
public class RouteObject {
    String SEQ="";
    String SHOPCODE;
    String SHOPNAME;
    String ADDRESS;
    String MAG;
    String MTD;
    String OMI = "0";
    String ORDER = "0";
    String NONSHOP;
    String OWNER;
    String TEL;
    String LON;
    String LAT;
    String GRADE;
    String SHOPTYPE;
    String DISTANCE = "";// "": set background red color  -1: before shop dont have position,

    public String getSEQ() {
        return SEQ;
    }

    public void setSEQ(String SEQ) {
        this.SEQ = SEQ;
    }

    public String getDISTANCE() {
        return DISTANCE;
    }

    public void setDISTANCE(String DISTANCE) {
        this.DISTANCE = DISTANCE;
    }

    boolean visit = false;

    public boolean isVisit() {
        return visit;
    }

    public void setVisit(boolean visit) {
        this.visit = visit;
    }

    InforShopDetails inforShopDetails;

    public InforShopDetails getInforShopDetails() {
        return inforShopDetails;
    }

    public void setInforShopDetails(InforShopDetails inforShopDetails) {
        this.inforShopDetails = inforShopDetails;
    }

    boolean isShow = false;
    boolean isClose = false;
    boolean isTouch = false;

    public boolean isClose() {
        return isClose;
    }

    public void setClose(boolean close) {
        isClose = close;
    }

    public boolean isTouch() {
        return isTouch;
    }

    public void setTouch(boolean touch) {
        isTouch = touch;
    }

    public boolean isShow() {
        return isShow;
    }

    public void setShow(boolean show) {
        isShow = show;
    }

    public String getSHOPCODE() {
        return SHOPCODE;
    }

    public void setSHOPCODE(String SHOPCODE) {
        this.SHOPCODE = SHOPCODE;
    }

    public String getSHOPNAME() {
        return SHOPNAME;
    }

    public void setSHOPNAME(String SHOPNAME) {
        this.SHOPNAME = SHOPNAME;
    }

    public String getADDRESS() {
        return ADDRESS;
    }

    public void setADDRESS(String ADDRESS) {
        this.ADDRESS = ADDRESS;
    }

    public String getMAG() {
        return MAG;
    }

    public void setMAG(String MAG) {
        this.MAG = MAG;
    }

    public String getMTD() {
        return MTD;
    }

    public void setMTD(String MTD) {
        this.MTD = MTD;
    }

    public String getOMI() {
        return OMI;
    }

    public void setOMI(String OMI) {
        this.OMI = OMI;
    }

    public String getORDER() {
        return ORDER;
    }

    public void setORDER(String ORDER) {
        this.ORDER = ORDER;
    }

    public String getNONSHOP() {
        return NONSHOP;
    }

    public void setNONSHOP(String NONSHOP) {
        this.NONSHOP = NONSHOP;
    }

    public String getOWNER() {
        return OWNER;
    }

    public void setOWNER(String OWNER) {
        this.OWNER = OWNER;
    }

    public String getTEL() {
        return TEL;
    }

    public void setTEL(String TEL) {
        this.TEL = TEL;
    }

    public String getLON() {
        return LON;
    }

    public void setLON(String LON) {
        this.LON = LON;
    }

    public String getLAT() {
        return LAT;
    }

    public void setLAT(String LAT) {
        this.LAT = LAT;
    }

    public String getGRADE() {
        return GRADE;
    }

    public void setGRADE(String GRADE) {
        this.GRADE = GRADE;
    }

    public String getSHOPTYPE() {
        return SHOPTYPE;
    }

    public void setSHOPTYPE(String SHOPTYPE) {
        this.SHOPTYPE = SHOPTYPE;
    }
}
