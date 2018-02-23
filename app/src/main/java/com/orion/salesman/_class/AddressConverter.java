package com.orion.salesman._class;

import android.app.ProgressDialog;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;

import com.google.android.gms.maps.model.LatLng;
import com.orion.salesman._object.CalculatorDistance;

import java.io.IOException;
import java.util.List;

/**
 * Created by maidinh on 7/9/2016.
 */
public class AddressConverter extends AsyncTask<LatLng, Void, String> {
    private ProgressDialog progress = null;
    private Context context;
    private Geocoder geocoder;
    private CalculatorDistance callback;

    public AddressConverter(Context context, CalculatorDistance callback) {
        this.context = context;
        this.callback = callback;
        geocoder = new Geocoder(context);
    }

    @Override
    protected void onPreExecute() {
//        progress = ProgressDialog.show(context, "Distance calculator", "We are calcultating the distance...", true, false);
    }

    @Override
    protected String doInBackground(LatLng... params) {
        float[] distance = new float[1];
        try {
            Location.distanceBetween(params[0].latitude, params[0].longitude, params[1].latitude, params[1].longitude, distance);
            List<Address> fromResult = geocoder.getFromLocation(params[0].latitude, params[0].longitude, 1);
            List<Address> toResult = geocoder.getFromLocation(params[1].latitude, params[1].longitude, 1);
            if (fromResult.size() > 0 && toResult.size() > 0) {
                return "" + Math.round(distance[0]);
            } else
                return "" + Math.round(distance[0]);
        } catch (IOException e) {
            return "" + Math.round(distance[0]);
        }
    }

    private String getAddressDescription(Address a) {
        String city = a.getLocality();
        String address = a.getAddressLine(0);
        return "'" + address + "' (" + city + ")";
    }

    @Override
    protected void onPostExecute(String message) {
//        if (progress != null)
//            progress.dismiss();
        callback.onCompleted(message);
    }
}