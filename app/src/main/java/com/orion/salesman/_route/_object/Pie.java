package com.orion.salesman._route._object;

import android.util.Log;

import com.bignerdranch.expandablerecyclerview.Model.ParentListItem;

import java.util.List;

/**
 * Created by maidinh on 5/8/2016.
 */
public class Pie implements ParentListItem {

    String ColumnsOne="";
    String ColumnsTwo="";
    String ColumnsThree = "";
    String ColumnsFour="";
    String ColumnsFive = "";
    String ColumnsSix;
    String ColumnsSeven;
    boolean isCheck = false;
    List<Pie> arrPies;
    String BOXSALE;
    String CASESALE;
    String EASALE;
    String STOREPRICE;
    String CASEPRICE;
    String EAPRICE;
    String BXCEQTY = "0";
    String BXEAQTY = "0";
    String CSEEAQTY = "0";
    String BXCSEQTY = "0";
    String PRDCD = "";
    String TPRDCD;
    String PMID = "";
    String PMTPRDCD = "";
    String GIFTAMTPERCENT = "";
    String GOODS = "";
    boolean isSave = false; // save item reOrder
    String PRDCLS1 = "";

    public String getPRDCLS1() {
        return PRDCLS1;
    }

    public void setPRDCLS1(String PRDCLS1) {
        this.PRDCLS1 = PRDCLS1;
    }

    public boolean isSave() {
        return isSave;
    }

    public void setIsSave(boolean isSave) {
        this.isSave = isSave;
    }

    public String getGOODS() {
        return GOODS;
    }

    public void setGOODS(String GOODS) {
        this.GOODS = GOODS;
    }

    public String getGIFTAMTPERCENT() {
        return GIFTAMTPERCENT;
    }

    public void setGIFTAMTPERCENT(String GIFTAMTPERCENT) {
        this.GIFTAMTPERCENT = GIFTAMTPERCENT;
    }

    public String getPMID() {
        return PMID;
    }

    public void setPMID(String PMID) {
        this.PMID = PMID;
    }

    public String getPMTPRDCD() {
        return PMTPRDCD;
    }

    public void setPMTPRDCD(String PMTPRDCD) {
        this.PMTPRDCD = PMTPRDCD;
    }

    public String getBXCSEQTY() {
        return BXCSEQTY;
    }

    public void setBXCSEQTY(String BXCSEQTY) {
        this.BXCSEQTY = BXCSEQTY;
    }

    public String getCSEEAQTY() {
        return CSEEAQTY;
    }

    public void setCSEEAQTY(String CSEEAQTY) {
        this.CSEEAQTY = CSEEAQTY;
    }

    public String getTPRDCD() {
        return TPRDCD;
    }

    public void setTPRDCD(String TPRDCD) {
        this.TPRDCD = TPRDCD;
    }

    boolean ispmt = false;

    public boolean ispmt() {
        return ispmt;
    }

    public void setIspmt(boolean ispmt) {
        this.ispmt = ispmt;
    }

    public String getPRDCD() {
        return PRDCD;
    }

    public void setPRDCD(String PRDCD) {
        this.PRDCD = PRDCD;
    }

    public String getColumnsFive() {
        return ColumnsFive;
    }

    public void setColumnsFive(String columnsFive) {
        ColumnsFive = columnsFive;
    }

    public String getColumnsSix() {
        return ColumnsSix;
    }

    public void setColumnsSix(String columnsSix) {
        ColumnsSix = columnsSix;
    }

    public String getColumnsSeven() {
        return ColumnsSeven;
    }

    public void setColumnsSeven(String columnsSeven) {
        ColumnsSeven = columnsSeven;
    }

    public Pie(String columnsOne, String columnsTwo, String columnsThree, String columnsFour, boolean isCheck,
               String BOXSALE, String CASESALE, String EASALE, String STOREPRICE, String CASEPRICE, String EAPRICE,
               String BXCEQTY, String BXEAQTY, String PRDCD, String TPRDCD, String CSEEAQTY, String BXCSEQTY, String PRDCLS1,String GOODS,String ColumnsFive) {
        ColumnsOne = columnsOne;
        ColumnsTwo = columnsTwo;
        ColumnsThree = columnsThree;
        ColumnsFour = columnsFour;
        this.isCheck = isCheck;
        this.BOXSALE = BOXSALE;
        this.CASESALE = CASESALE;
        this.EASALE = EASALE;
        this.STOREPRICE = STOREPRICE;
        this.CASEPRICE = CASEPRICE;
        this.EAPRICE = EAPRICE;
        this.BXCEQTY = BXCEQTY;
        this.BXEAQTY = BXEAQTY;
        this.PRDCD = PRDCD;
        this.TPRDCD = TPRDCD;
        this.CSEEAQTY = CSEEAQTY;
        this.BXCSEQTY = BXCSEQTY;
        this.PRDCLS1 = PRDCLS1;
        this.GOODS=GOODS;
        this.ColumnsFive=ColumnsFive;
    }

    public String getBOXSALE() {
        return BOXSALE;
    }

    public void setBOXSALE(String BOXSALE) {
        this.BOXSALE = BOXSALE;
    }

    public String getCASESALE() {
        return CASESALE;
    }

    public void setCASESALE(String CASESALE) {
        this.CASESALE = CASESALE;
    }

    public String getEASALE() {
        return EASALE;
    }

    public void setEASALE(String EASALE) {
        this.EASALE = EASALE;
    }

    public String getSTOREPRICE() {
        return STOREPRICE;
    }

    public void setSTOREPRICE(String STOREPRICE) {
        this.STOREPRICE = STOREPRICE;
    }

    public String getCASEPRICE() {
        return CASEPRICE;
    }

    public void setCASEPRICE(String CASEPRICE) {
        this.CASEPRICE = CASEPRICE;
    }

    public String getEAPRICE() {
        return EAPRICE;
    }

    public void setEAPRICE(String EAPRICE) {
        this.EAPRICE = EAPRICE;
    }

    public String getBXCEQTY() {
        return BXCEQTY;
    }

    public void setBXCEQTY(String BXCEQTY) {
        this.BXCEQTY = BXCEQTY;
    }

    public String getBXEAQTY() {
        return BXEAQTY;
    }

    public void setBXEAQTY(String BXEAQTY) {
        this.BXEAQTY = BXEAQTY;
    }

    public Pie() {
    }

    public List<Pie> getArrPies() {
        return arrPies;
    }

    public void setArrPies(List<Pie> arrPies) {
        this.arrPies = arrPies;
    }

    public Pie(String columnsOne, String columnsTwo, String columnsThree, String columnsFour, boolean isCheck, List<Pie> arrPies) {
        ColumnsOne = columnsOne;
        ColumnsTwo = columnsTwo;
        ColumnsThree = columnsThree;
        ColumnsFour = columnsFour;
        this.isCheck = isCheck;
        this.arrPies = arrPies;
    }

    public Pie(String columnsOne, String columnsTwo, String columnsThree, String columnsFour, boolean isCheck) {
        ColumnsOne = columnsOne;
        ColumnsTwo = columnsTwo;
        ColumnsThree = columnsThree;
        ColumnsFour = columnsFour;
        this.isCheck = isCheck;
    }

    public String getColumnsOne() {
        return ColumnsOne;
    }

    public void setColumnsOne(String columnsOne) {
        ColumnsOne = columnsOne;
    }

    public String getColumnsTwo() {
        return ColumnsTwo;
    }

    public void setColumnsTwo(String columnsTwo) {
        ColumnsTwo = columnsTwo;
    }

    public String getColumnsThree() {
        return ColumnsThree;
    }

    public void setColumnsThree(String columnsThree) {
        ColumnsThree = columnsThree;
    }

    public String getColumnsFour() {
        return ColumnsFour;
    }

    public void setColumnsFour(String columnsFour) {
        ColumnsFour = columnsFour;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setIsCheck(boolean isCheck) {
        this.isCheck = isCheck;
    }


    @Override
    public List<Pie> getChildItemList() {
        return arrPies;
    }

    @Override
    public boolean isInitiallyExpanded() {
        return isCheck();
    }
}
