package com.orion.salesman._object;

/**
 * Created by maidinh on 23/11/2016.
 */

public class DataBase129 {
    int id;
    String DATA = "";
    String CDATE = "";

    public DataBase129() {
    }

    public DataBase129(String DATA, String CDATE) {
        this.DATA = DATA;
        this.CDATE = CDATE;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDATA() {
        return DATA;
    }

    public void setDATA(String DATA) {
        this.DATA = DATA;
    }

    public String getCDATE() {
        return CDATE;
    }

    public void setCDATE(String CDATE) {
        this.CDATE = CDATE;
    }
}
