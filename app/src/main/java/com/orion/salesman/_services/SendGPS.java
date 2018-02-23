package com.orion.salesman._services;

import android.app.IntentService;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.orion.salesman.MainActivity;
import com.orion.salesman._class.Const;
import com.orion.salesman._class.HttpRequest;
import com.orion.salesman._class.LocationTracker;
import com.orion.salesman._class.LocationTrackerSendGPS;
import com.orion.salesman._interface.IF_100;
import com.orion.salesman._interface.LocationChangeCallback;
import com.orion.salesman._object.DataLogin;
import com.orion.salesman._object.SendPosition;
import com.orion.salesman._pref.PrefManager;
import com.orion.salesman._sqlite.DatabaseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by maidinh on 17/10/2016.
 */
public class SendGPS extends Service {
    String TAG = "SendGPS";
    //    ArrayList<SendPosition> arrayList;
    Context context;
    int GPSINTERVALSEND = 30000;
    Location location;
    Location locationPre;
    PrefManager prefManager;
    int SENDINTERVAL = 30;
    DataLogin dataLogin;
    String compareStringOne = "07:00";
    String compareStringTwo = "18:00";
    Date dateCompareOne;
    Date dateCompareTwo;
    int MAXSENDGPSCOUNT = 50;

    String SEID = "";

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;

        prefManager = new PrefManager(context);
        compareStringOne = prefManager.getcompareStringOne();
        compareStringTwo = prefManager.getcompareStringTwo();
        if (compareStringOne == null || compareStringOne.length() == 0) {
            compareStringOne = "07:00";
        }
        if (compareStringTwo == null || compareStringTwo.length() == 0) {
            compareStringTwo = "18:00";
        }
//        compareStringTwo = "23:00";
        if (dateCompareOne == null) {
            dateCompareOne = Const.parseDate(compareStringOne);
        }
        if (dateCompareTwo == null) {
            dateCompareTwo = Const.parseDate(compareStringTwo);
        }
        if (prefManager.getInfoLoginSE().length() == 0) {
            dataLogin = new Gson().fromJson(prefManager.getInfoLogin(), DataLogin.class);
            Log.d(TAG, "not SE");
        } else {
            Log.d(TAG, "is SE");
            dataLogin = new Gson().fromJson(prefManager.getInfoLoginSE(), DataLogin.class);
            SEID = "" + dataLogin.getUSERID();
        }
        MAXSENDGPSCOUNT = Integer.parseInt(dataLogin.getMAXSENDGPSCOUNT());
        Log.d(TAG, "MAXSENDGPSCOUNT:" + MAXSENDGPSCOUNT);
        GPSINTERVALSEND = prefManager.getGPSINTERVALSEND();
        SENDINTERVAL = prefManager.getSENDINTERVAL();
        if (SENDINTERVAL <= 0) {
            SENDINTERVAL = 30;
        }
        if (GPSINTERVALSEND <= 0) {
            GPSINTERVALSEND = 30000;
        }
        SENDINTERVAL = SENDINTERVAL * 1000;
//        arrayList = new ArrayList<>();


        getGPSINTERVAL();

        startGetGPS();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startPostGPS();
            }
        }, 5000);

    }

    public void startGetGPS() {
        if (timerGetGPS != null) {
            return;
        }
        timerGetGPS = new Timer();
        timerGetGPS.scheduleAtFixedRate(timerTaskGetGPS, 0, GPSINTERVALSEND);
    }

    public void startPostGPS() {
        if (timerPost != null) {
            return;
        }
        timerPost = new Timer();
        timerPost.scheduleAtFixedRate(timerTaskPost, 0, SENDINTERVAL);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (timerGetGPS != null)
            timerGetGPS.cancel();
        timerGetGPS = null;
        if (timerPost != null)
            timerPost.cancel();
        timerPost = null;
//        if (arrayList != null)
//            arrayList.clear();
        locationTracker.stopLocationUpdates();

    }

    LocationTrackerSendGPS locationTracker;
    int checkGPS = 0;

    void getGPSINTERVAL() {
        locationTracker = new LocationTrackerSendGPS();
        locationTracker.registerLocationService(this, 5000, 5000, 0, new LocationChangeCallback() {
            @Override
            public void onLocationChangeCallBack(Location lc) {

                checkGPS++;
                if (lc != null) {
                    Log.d(TAG, "[onLocationChanged][lat]: " + lc.getLatitude() + " [long]: " + lc.getLongitude());
                    location = lc;
                    if (MainActivity.instance != null) {
                        MainActivity.LON = location.getLongitude();
                        MainActivity.LAT = location.getLatitude();
                    } else {

                    }
                } else {

                }
            }
        });
    }

    void postGPS() {
        final DatabaseHandler db = new DatabaseHandler(SendGPS.this);
        List<SendPosition> lst = db.getListGPS();
        if (lst == null) {
            lst = new ArrayList<>();
        }
        if (lst.size() > 0) {
            if (Const.checkInternetConnection(context)) {

                JSONObject jObject = new JSONObject();
                try {
                    JSONArray jArray = new JSONArray();
                    for (SendPosition s : lst) {
                        JSONObject studentJSON = new JSONObject();
                        studentJSON.put("DATE", s.getDATE());
                        studentJSON.put("TIME", s.getTIME());
                        studentJSON.put("LON", s.getLON());
                        studentJSON.put("LAT", s.getLAT());
                        studentJSON.put("SPEED", s.getSPEED());
                        studentJSON.put("ANGLE", s.getANGLE());
                        studentJSON.put("SEID", s.getSEID());
                        jArray.put(studentJSON);
                    }
                    jObject.put("LIST", jArray);

                    String paramSend = jObject.toString();
                    Log.d(TAG, paramSend);
                    new HttpRequest(this).new API_1000(dataLogin, paramSend, new IF_100() {
                        @Override
                        public void onSuccess() {
                            db.deleteGPS();
//                            arrayList.clear();
//                            Log.d(TAG, "API_1000 onSuccess");
                        }

                        @Override
                        public void onFail() {
//                            Log.d(TAG, "onFail");
                        }
                    }, "SENDINTERVAL:" + SENDINTERVAL + " GPSINTERVALSEND:" + GPSINTERVALSEND + " GPSWorking:" + checkGPS + " length:" + paramSend.length()).execute();
                } catch (Exception jse) {
                    jse.printStackTrace();
                }
            }
        }
    }


    void GETTIMEINTERVAL() {
        String TIME = Const.getNowTime();
        String DATE = Const.getToday();
        double LAT = 0;
        double LON = 0;
        Calendar now = Calendar.getInstance();
        int hour = now.get(Calendar.HOUR_OF_DAY);
        int minute = now.get(Calendar.MINUTE);
        Date date = Const.parseDate(hour + ":" + minute);

        if (dateCompareOne.before(date) && dateCompareTwo.after(date)) {
            if (location != null) {
                LAT = location.getLatitude();
                LON = location.getLongitude();
                if (LAT != 0 && LON != 0) {
                    SendPosition ob = new SendPosition();
                    ob.setDATE(DATE);
                    ob.setTIME(TIME);
                    ob.setANGLE("" + 0);
                    ob.setSEID(SEID);
                    if (locationPre == null) {
                        locationPre = location;
                    }

                    float minSpeed = (float) (5000 * 1.0 / 3600);
                    float maxSpeed = (float) (60000 * 1.0 / 3600);
                    Log.d(TAG, "minSpeed:" + minSpeed);
                    Log.d(TAG, "maxSpeed:" + maxSpeed);

                    float _speed = location.getSpeed();
                    if (minSpeed <= _speed && _speed <= maxSpeed) {
                        // add new lat,lon
                        locationPre = location;
                        Log.d(TAG, "add new GPS:");
                    } else {
                        // get previous lat,lon
                        Log.d(TAG, "get previous GPS:");
                    }

                    ob.setLAT("" + (int) (locationPre.getLatitude() * Const.GPS_WGS84));
                    ob.setLON("" + (int) (locationPre.getLongitude() * Const.GPS_WGS84));
                    int convertSpeed = (int) ((locationPre.getSpeed() * 1.0 * 3600) / 1000);
                    Log.d(TAG, "convertSpeed:" + convertSpeed);
                    ob.setSPEED("" + convertSpeed);


                    DatabaseHandler db = new DatabaseHandler(SendGPS.this);
                    List<SendPosition> lst = db.getListGPS();
                    if (lst != null && lst.size() >= 1) {
                        Log.d(TAG, "lst.size: " + lst.size());
                        if (lst.size() >= MAXSENDGPSCOUNT) {
                            SendPosition obj = lst.get(0);
                            db.GPS_DELETE_ID(obj.getID());
                        }

                        SendPosition obj = lst.get(lst.size() - 1);
                        if (!TIME.equals(obj.getTIME())) {
                            db.addGPS(ob);
                        }
                    } else {
                        db.addGPS(ob);
                    }

//                    if (arrayList.size() >= 1) {
//                        SendPosition obj = arrayList.get(arrayList.size() - 1);
//                        if (!TIME.equals(obj.getTIME())) {
//                            arrayList.add(ob);
//                        }
//                    } else {
//                        arrayList.add(ob);
//                    }
//
//                    if (arrayList.size() >= 50) {
//                        arrayList.remove(0);
//                    }
                } else {
                }
            } else {
            }
        } else {
        }
    }

    private Timer timerGetGPS, timerPost;
    private TimerTask timerTaskGetGPS = new TimerTask() {
        @Override
        public void run() {
            GETTIMEINTERVAL();
        }
    };
    private TimerTask timerTaskPost = new TimerTask() {
        @Override
        public void run() {
            StartSendGPS();
        }
    };

    private void StartSendGPS() {

        Calendar now = Calendar.getInstance();
        int hour = now.get(Calendar.HOUR_OF_DAY);
        int minute = now.get(Calendar.MINUTE);
        Date date = Const.parseDate(hour + ":" + minute);

        DatabaseHandler db = new DatabaseHandler(SendGPS.this);
        List<SendPosition> lst = db.getListGPS();
        boolean flag = false;
        if (lst != null && lst.size() > 0) {
            flag = true;
        }
        if ((dateCompareOne.before(date) && dateCompareTwo.after(date)) || flag) {
            postGPS();
        } else {
//            if (arrayList.size() > 0)
//                arrayList.clear();
        }
    }


}