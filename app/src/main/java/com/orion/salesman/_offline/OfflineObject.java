package com.orion.salesman._offline;

public class OfflineObject {
    String DATE;
    String DATA;


    public OfflineObject(String DATE, String DATA) {
        this.DATE = DATE;
        this.DATA = DATA;
    }

    public String getDATE() {
        return DATE;
    }

    public void setDATE(String DATE) {
        this.DATE = DATE;
    }

    public String getDATA() {
        return DATA;
    }

    public void setDATA(String DATA) {
        this.DATA = DATA;
    }
}
