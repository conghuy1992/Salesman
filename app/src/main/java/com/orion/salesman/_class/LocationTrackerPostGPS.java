package com.orion.salesman._class;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.orion.salesman._interface.LocationChangeCallback;

/**
 * Created by Huy on 20/9/2016.
 */

public class LocationTrackerPostGPS implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {
    private static final String TAG = LocationTrackerPostGPS.class.getSimpleName();

    private static LocationTrackerPostGPS mInstance;

    private Context mContext;

    private Location mLastLocation;
    private LocationChangeCallback callback = null;

    // Google client to interact with Google API
    private GoogleApiClient mGoogleApiClient;


    private LocationRequest mLocationRequest;

    // Location updates intervals in sec
    private int UPDATE_INTERVAL = 5000; // 19 sec
    private int FASTEST_INTERVAL = 5000; // 5 sec
    private int DISPLACEMENT = 10; // 10 meters

    private LocationTrackerPostGPS() {

    }

    /**
     * only call this function when run app
     *
     * @param context
     */
    public void registerLocationService(Context context) {
        Log.d(TAG, "[registerLocationService]");

        mContext = context;

        if (checkPlayServices()) {

            buildGoogleApiClient();
            createLocationRequest();

            if (mGoogleApiClient != null) {
                mGoogleApiClient.connect();
            }
        }
    }

    public void registerLocationService(Context context, int UPDATE_INTERVAL, int FASTEST_INTERVAL, int DISPLACEMENT, LocationChangeCallback callback) {
        Log.d(TAG, "[registerLocationService]");

        mContext = context;
        this.UPDATE_INTERVAL = UPDATE_INTERVAL;
        this.FASTEST_INTERVAL = FASTEST_INTERVAL;
        this.DISPLACEMENT = DISPLACEMENT;
        this.callback = callback;
        if (checkPlayServices()) {

            buildGoogleApiClient();
            createLocationRequest();

            if (mGoogleApiClient != null) {
                mGoogleApiClient.connect();
            }
        }
    }

    public static LocationTrackerPostGPS getInstance() {
        if (null == mInstance) {
            mInstance = new LocationTrackerPostGPS();
        }
        return mInstance;
    }


    public Location getLocation() {
        return mLastLocation;
    }

    @Override
    public void onConnected(Bundle bundle) {
        Log.d(TAG, "[onConnected]");

        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);

        startLocationUpdates();
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.d(TAG, "[onConnectionSuspended][i]: " + i);

        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.d(TAG, "[onConnectionFailed]: " + connectionResult.getErrorCode());
    }

    /**
     * Method to verify google play services on the device
     */
    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(mContext);

        return resultCode == ConnectionResult.SUCCESS;

    }

    /**
     * Creating google api client object
     */
    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(mContext)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API).build();
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.d(TAG, "[onLocationChanged][lat]: " + location.getLatitude() + " [long]: " + location.getLongitude());
        mLastLocation = location;
        if (callback != null) {
            callback.onLocationChangeCallBack(location);
        }
    }

    /**
     * Creating location request object
     */
    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(UPDATE_INTERVAL);
        mLocationRequest.setFastestInterval(FASTEST_INTERVAL);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        mLocationRequest.setSmallestDisplacement(DISPLACEMENT); // 10 meters
    }

    /**
     * Starting the location updates
     */
    protected void startLocationUpdates() {
        if (null != mGoogleApiClient && mGoogleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.requestLocationUpdates(
                    mGoogleApiClient, mLocationRequest, this);
        }
    }

    public void stopLocationUpdates() {
        Log.d(TAG, "[stopLocationUpdates]");

        if (null != mGoogleApiClient && mGoogleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(
                    mGoogleApiClient, this);

            mGoogleApiClient.disconnect();
        }

    }
}
