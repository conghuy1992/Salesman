package com.orion.salesman._object;

/**
 * Created by maidinh on 12/9/2016.
 */
public class CheckUpdate {
    String DATE;
    boolean check;

    public CheckUpdate() {
    }

    public CheckUpdate(String DATE, boolean check) {
        this.DATE = DATE;
        this.check = check;
    }

    public String getDATE() {
        return DATE;
    }

    public void setDATE(String DATE) {
        this.DATE = DATE;
    }

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }
}
