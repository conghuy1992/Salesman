package com.orion.salesman._object;

/**
 * Created by maidinh on 12/10/2016.
 */
public class LISTPOSITION {
    double lon;
    double lat;
    String shop;
    String name;
    String seq;

    public LISTPOSITION(double lon, double lat, String shop, String name,String seq) {
        this.lon = lon;
        this.lat = lat;
        this.shop = shop;
        this.name = name;
        this.seq=seq;
    }

    public String getSeq() {
        return seq;
    }

    public void setSeq(String seq) {
        this.seq = seq;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public String getShop() {
        return shop;
    }

    public void setShop(String shop) {
        this.shop = shop;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
