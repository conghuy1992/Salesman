package com.orion.salesman._class;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.util.ArrayList;
import java.util.Observable;

/**
 * 갯수별로 GPS데이터를 전송하려면 handler 사용하지 않음.
 * 시간별로 GPS데이터를 전송하려면 countGpsData() 사용하지 않음.
 * Created by danig_000 on 2016-08-05.
 */
public class GpsManager extends Observable {
    private static final int GPS_COUNT = 5;
    private static final int GPS_TIME = 20 * 1000;

    private static GpsManager instance = null;
    private Context mCtx = null;
    private LocationManager mLocationMgr;
    private ArrayList<Location> mLocationArray;

    public static GpsManager getInstance(Context ctx) {
        if (instance == null) {
            instance = new GpsManager(ctx);
        }
        return instance;
    }

    public static GpsManager getInstance() {
        return instance;
    }

    private GpsManager(Context ctx) {
        this.mCtx = ctx;
        mLocationArray = new ArrayList<Location>();
        mLocationMgr = (LocationManager) mCtx.getSystemService(Context.LOCATION_SERVICE);

        getLocation();
        handler.sendEmptyMessageDelayed(MSG_SEND_GPS_DATA, GPS_TIME); // 설정 시간별 전송
    }

    public Location getLocation() {
        Location location = null;

        boolean isGPSEnabled = mLocationMgr.isProviderEnabled(LocationManager.GPS_PROVIDER);
        boolean isNetworkEnabled = mLocationMgr.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        if (isGPSEnabled) {
            mLocationMgr.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1, listener);
            if (mLocationMgr != null) {
                location = mLocationMgr.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                if (location != null) {

                }
            }
        }

        if (isNetworkEnabled) {
            mLocationMgr.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 1, listener);

            if (mLocationMgr != null) {
                location = mLocationMgr.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                if (location != null) {

                }
            }
        }

        return location;
    }

    public LocationListener listener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            int lat = (int) location.getLatitude();
            int lon = (int) location.getLongitude();

            // 베트남 위경도 범위 대략 포괄적으로 잡았음
            // lat = 8이상 24미만이고
            // lon = 102이상 111미만이어야 함.
            // 수정이 필요하면 현지에서 수정해야 할 듯 함.
            if ((lat >= 8 && lat < 24) && (lon >= 102 && lon < 111))
                addGpsData(location);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            switch (status) {
                case LocationProvider.OUT_OF_SERVICE:

                    break;
                case LocationProvider.TEMPORARILY_UNAVAILABLE:

                    break;
                case LocationProvider.AVAILABLE:

                    break;
            }
        }

        @Override
        public void onProviderEnabled(String s) {
        }

        @Override
        public void onProviderDisabled(String s) {
        }
    };

    private static final int MSG_SEND_GPS_DATA = 1000;

    Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_SEND_GPS_DATA:
                    putObservers();
                    break;
            }
        }
    };

    private void addGpsData(Location location) {
        mLocationArray.add(location);
        countGpsData();
    }

    private void countGpsData() {
        if (mLocationArray.size() == GPS_COUNT) {
            putObservers();
        }
    }

    private void putObservers() {
        setChanged();
        notifyObservers();
    }

    public ArrayList<Location> getGpsData() {
        return mLocationArray;
    }

    public void clearArray() {
        mLocationArray = new ArrayList<Location>();
    }

    // 거리구하기
    public double getDistance(double latA, double lonA, double latB, double lonB) {
        double distance = 0;

        Location locationA = new Location("pointA");
        locationA.setLatitude(latA);
        locationA.setLongitude(lonA);

        Location locationB = new Location("pointB");
        locationB.setLatitude(latB);
        locationB.setLongitude(lonB);

        distance = locationA.distanceTo(locationB);

        return distance;
    }

}
