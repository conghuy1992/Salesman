package com.orion.salesman._object;

/**
 * Created by maidinh on 9/8/2016.
 */
public class Login {
    String ID = "";
    String PWD = "";
    String DEVICEVERSION = "";

    public Login(String ID, String PWD, String DEVICEVERSION) {
        this.ID = ID;
        this.PWD = PWD;
        this.DEVICEVERSION = DEVICEVERSION;
    }

    public String getDEVICEVERSION() {
        return DEVICEVERSION;
    }

    public void setDEVICEVERSION(String DEVICEVERSION) {
        this.DEVICEVERSION = DEVICEVERSION;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getPWD() {
        return PWD;
    }

    public void setPWD(String PWD) {
        this.PWD = PWD;
    }
}
