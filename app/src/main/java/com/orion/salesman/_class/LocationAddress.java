package com.orion.salesman._class;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.orion.salesman._interface.GetAddressCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Locale;

import javax.net.ssl.HttpsURLConnection;


/**
 * Created by david on 12/28/15.
 */
public class LocationAddress extends AsyncTask<String, Void, String[]> {
    String latLng;
    GetAddressCallback callback;
    String TAG = "LocationAddress";

    public LocationAddress(double lat, double lng, GetAddressCallback callback) {
        super();
        this.latLng = lat + "," + lng;
        this.callback = callback;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String[] doInBackground(String... params) {
        String response;
//        Log.d(TAG, " latLng 111:" + latLng);
        try {

            String url = "http://maps.google.com/maps/api/geocode/json?latlng=" + latLng +
                    "&sensor=false&language=" + Locale.getDefault().getLanguage();
            response = getLatLongByURL(url);
//            Log.d(TAG, "url:" + url);
//            Const.longInfo(TAG, "response:" + response);
            return new String[]{response};
        } catch (Exception e) {
            return new String[]{"error"};
        }
    }

    @Override
    protected void onPostExecute(String... result) {

        String address = null;
        if (!result[0].equalsIgnoreCase("error")) {
            try {
                JSONObject jsonObject = new JSONObject(result[0]);
                address = ((JSONArray) jsonObject.get("results")).getJSONObject(0).getString("formatted_address");
//                Log.d(TAG,new Gson().toJson(jsonObject));
                String removeString = ", Việt Nam";
                if (address.contains(removeString)) {
                    address=address.replace(removeString, "");
                }

                String removeString2 = ", Vietnam";
                if (address.contains(removeString2)) {
                    address=address.replace(removeString2, "");
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if (callback != null) {
            callback.onSuccess(address);
        }
//        BaseActivity.Instance.dismissProgressDialog();
    }


    public String getLatLongByURL(String requestURL) {
        URL url;
        String response = "";
        try {
            url = new URL(requestURL);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(60000);
            conn.setConnectTimeout(60000);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded");
            conn.setDoOutput(true);
            int responseCode = conn.getResponseCode();

            if (responseCode == HttpsURLConnection.HTTP_OK) {
                String line;
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while ((line = br.readLine()) != null) {
                    response += line;
                }
            } else {
                response = "";
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }
}
