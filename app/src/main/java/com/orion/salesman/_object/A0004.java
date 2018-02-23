package com.orion.salesman._object;

/**
 * Created by maidinh on 28/10/2016.
 */
public class A0004 {
    int id = 0;
    String CDATE = "";
    String CTIME = "";
    String AUTHOR = "";
    String AUTHORNM = "";
    String MSGBODY = "";
    String ISREAD = "0";
    String OUTBOX = "0"; //0:inbox , 1:outbox
    String DATEADD = "";

    public String getDATEADD() {
        return DATEADD;
    }

    public void setDATEADD(String DATEADD) {
        this.DATEADD = DATEADD;
    }

    public String getOUTBOX() {
        return OUTBOX;
    }

    public void setOUTBOX(String OUTBOX) {
        this.OUTBOX = OUTBOX;
    }

    public A0004() {
    }

    public A0004(String CDATE, String CTIME, String AUTHOR, String AUTHORNM, String MSGBODY, String ISREAD, String OUTBOX, String DATEADD) {
        this.CDATE = CDATE;
        this.CTIME = CTIME;
        this.AUTHOR = AUTHOR;
        this.AUTHORNM = AUTHORNM;
        this.MSGBODY = MSGBODY;
        this.ISREAD = ISREAD;
        this.OUTBOX = OUTBOX;
        this.DATEADD = DATEADD;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCDATE() {
        return CDATE;
    }

    public void setCDATE(String CDATE) {
        this.CDATE = CDATE;
    }

    public String getCTIME() {
        return CTIME;
    }

    public void setCTIME(String CTIME) {
        this.CTIME = CTIME;
    }

    public String getAUTHOR() {
        return AUTHOR;
    }

    public void setAUTHOR(String AUTHOR) {
        this.AUTHOR = AUTHOR;
    }

    public String getAUTHORNM() {
        return AUTHORNM;
    }

    public void setAUTHORNM(String AUTHORNM) {
        this.AUTHORNM = AUTHORNM;
    }

    public String getMSGBODY() {
        return MSGBODY;
    }

    public void setMSGBODY(String MSGBODY) {
        this.MSGBODY = MSGBODY;
    }

    public String getISREAD() {
        return ISREAD;
    }

    public void setISREAD(String ISREAD) {
        this.ISREAD = ISREAD;
    }
}
