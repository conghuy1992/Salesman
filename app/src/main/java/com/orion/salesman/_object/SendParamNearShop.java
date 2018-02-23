package com.orion.salesman._object;

/**
 * Created by maidinh on 16/12/2016.
 */

public class SendParamNearShop {
    double lon=0;
    double lat=0;
    String shop="";
    String name="";
    String time="";
    String code="";
    String address="";

    public SendParamNearShop(double lon, double lat, String shop, String name, String time, String code, String address) {
        this.lon = lon;
        this.lat = lat;
        this.shop = shop;
        this.name = name;
        this.time = time;
        this.code = code;
        this.address = address;
    }

    public SendParamNearShop() {
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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
