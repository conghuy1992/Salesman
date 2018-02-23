package com.orion.salesman._class;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Vibrator;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.orion.salesman.MainActivity;
import com.orion.salesman.R;
import com.orion.salesman._app.MyApplication;
import com.orion.salesman._infor._fragment._object.CpnInfo_ListT;
import com.orion.salesman._infor._fragment._object.ListObjA0004;
import com.orion.salesman._infor._fragment._object.ObjA0004;
import com.orion.salesman._infor._fragment._object.OrionFamily;
import com.orion.salesman._interface.IF_10;
import com.orion.salesman._interface.IF_100;
import com.orion.salesman._interface.IF_103;
import com.orion.salesman._interface.IF_104;
import com.orion.salesman._interface.IF_110;
import com.orion.salesman._interface.IF_111;
import com.orion.salesman._interface.IF_112;
import com.orion.salesman._interface.IF_112_FIRST;
import com.orion.salesman._interface.IF_114;
import com.orion.salesman._interface.IF_115;
import com.orion.salesman._interface.IF_117;
import com.orion.salesman._interface.IF_118;
import com.orion.salesman._interface.IF_119;
import com.orion.salesman._interface.IF_121;
import com.orion.salesman._interface.IF_123;
import com.orion.salesman._interface.IF_124;
import com.orion.salesman._interface.IF_127;
import com.orion.salesman._interface.IF_129;
import com.orion.salesman._interface.IF_130;
import com.orion.salesman._interface.IF_131;
import com.orion.salesman._interface.IF_140;
import com.orion.salesman._interface.IF_2;
import com.orion.salesman._interface.IF_303;
import com.orion.salesman._interface.IF_7;
import com.orion.salesman._interface.IF_DELETE_IMAGE;
import com.orion.salesman._interface.IF_SE_BRANCH;
import com.orion.salesman._object.A0004;
import com.orion.salesman._object.API_103;
import com.orion.salesman._object.API_104;
import com.orion.salesman._object.API_110;
import com.orion.salesman._object.API_120;
import com.orion.salesman._object.API_121;
import com.orion.salesman._object.API_122;
import com.orion.salesman._object.API_123;
import com.orion.salesman._object.API_130;
import com.orion.salesman._object.API_131;
import com.orion.salesman._object.API_303;
import com.orion.salesman._object.DATA_303;
import com.orion.salesman._object.DATA_API_131;
import com.orion.salesman._object.DataBase128;
import com.orion.salesman._object.DataBase129;
import com.orion.salesman._object.DataLogin;
import com.orion.salesman._object.DownData;
import com.orion.salesman._object.DownList;
import com.orion.salesman._object.GetNewShopPromotionList;
import com.orion.salesman._object.ListA0127;
import com.orion.salesman._object.ListNearShop;
import com.orion.salesman._object.ListObj140;
import com.orion.salesman._object.ListSeBranch;
import com.orion.salesman._object.ObjectA0129;
import com.orion.salesman._object.OrderNonShopListAPI;
import com.orion.salesman._object.PmList;
import com.orion.salesman._object.PositionAPI;
import com.orion.salesman._object.RESULTOBJECT;
import com.orion.salesman._object.ResultAddMap;
import com.orion.salesman._object.ResultAddShop;
import com.orion.salesman._object.SeBranch;
import com.orion.salesman._object.VisitShop;
import com.orion.salesman._offline.OfflineObject;
import com.orion.salesman._pref.PrefManager;
import com.orion.salesman._result._object.DATA_API_130;
import com.orion.salesman._route._object.IF_128;
import com.orion.salesman._route._object.InforShopDetails;
import com.orion.salesman._route._object.InputStock;
import com.orion.salesman._route._object.ListA1028;
import com.orion.salesman._route._object.ListInforShop;
import com.orion.salesman._route._object.ObjectA0128;
import com.orion.salesman._route._object.RequestRoute;
import com.orion.salesman._route._object.RouteList;
import com.orion.salesman._route._object.RouteScheduleList;
import com.orion.salesman._route._object.getSMWorkingDayAPI;
import com.orion.salesman._route._object.getSMWorkingDayObject;
import com.orion.salesman._sqlite.DatabaseHandler;
import com.orion.salesman._summary._adapter.DataSummaryDisplay;
import com.orion.salesman._summary._adapter.DataSummarySales;
import com.orion.salesman._summary._object.SummaryDelivery;
import com.orion.salesman._summary._object.SummarySales_API;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

import kr.co.eksys.nativelib.EksysNetworkException;
import kr.co.eksys.nativelib.NetworkManager;

/**
 * Created by maidinh on 21/9/2016.
 */
public class HttpRequest {
    String TAG = "HttpRequest";
    Context context;
    boolean flag = false;

    //    String contents_112 = "";
    public HttpRequest(Context context) {
        this.context = context;
    }

    public class API_112 extends AsyncTask<String, String, String> {
        DataLogin dataLogin;
        String USE_DATE;
        IF_112 callback;

        public API_112(DataLogin dataLogin, String USE_DATE, IF_112 callback) {
            this.dataLogin = dataLogin;
            this.USE_DATE = USE_DATE;
            this.callback = callback;
        }

        @Override
        protected String doInBackground(String... strings) {
            String s = call_112(dataLogin, USE_DATE, callback);
            return s;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            ListInforShop routeList = new Gson().fromJson(s, ListInforShop.class);
            if (routeList != null) {
                List<InforShopDetails> LIST = routeList.getLIST();
                if (routeList.getRESULT() == 0) {
                    callback.onSuccess(LIST);
                } else {
                    callback.onFail();
                }
            } else {
                callback.onFail();
            }

        }
    }

    public String call_112(DataLogin dataLogin, String USE_DATE, IF_112 callback) {
        String contents_112 = "";
        String idLogin = dataLogin.getUSERID() + "";
        if ((Const.checkInternetConnection(context)
                && Const.PHONENUMBER.length() > 0
                && idLogin.trim().length() > 0
                && dataLogin.getDEPTCD().trim().length() > 0
                && dataLogin.getJOBCODE().trim().length() > 0)
                || (Const.checkInternetConnection(context) && dataLogin.getJOBGRADE().trim().equals(Const.SE))) {
            NetworkManager nm = new NetworkManager(Const.IP, Const.PORT);
            nm.setTerminalInfo(Const.PHONENUMBER, idLogin, dataLogin.getDEPTCD(), dataLogin.getJOBCODE());
            try {
                nm.setTR('A', Const.getRouteShop);
                nm.connect(10);// set timeout
//                ParamsRouteShop ob = new ParamsRouteShop("0", "", USE_DATE);
                Map<String, Object> map = new HashMap<>();
                map.put("MODE", "0");
                map.put("CUSTCD", "");
                map.put("DATE", USE_DATE);
                String data = new Gson().toJson(map);
                Const.WriteFileLog(dataLogin.getUSERID(), Const.getRouteShop, data);
                nm.send(data);
                contents_112 = nm.receive();
                Log.d(TAG, "getRouteShop: " + contents_112);
//                Const.longInfo("getRoute", contents);
            } catch (EksysNetworkException e) {
                Log.e(TAG, "getRouteShop Error(" + e.getErrorCode() + "): " + e.toString());
            } finally {
                nm.close();
            }
        }
        return contents_112;
    }

    public class API_116 extends AsyncTask<String, String, String> {
        DataLogin dataLogin;
        String json;
        IF_100 callback;

        public API_116(DataLogin dataLogin, String json, IF_100 callback) {
            this.dataLogin = dataLogin;
            this.json = json;
            this.callback = callback;
        }

        @Override
        protected String doInBackground(String... params) {
            String s = call_116(dataLogin, json);
            return s;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (s.equals(Const.OK))
                callback.onSuccess();
            else
                callback.onFail();

        }
    }

    public String call_116(DataLogin dataLogin, String json) {
        String contents = "";
        String result = Const.FAIL;

        String idLogin = dataLogin.getUSERID() + "";
        if ((Const.checkInternetConnection(context)
                && Const.PHONENUMBER.length() > 0
                && idLogin.trim().length() > 0
                && dataLogin.getDEPTCD().trim().length() > 0
                && dataLogin.getJOBCODE().trim().length() > 0)
                || (Const.checkInternetConnection(context) && dataLogin.getJOBGRADE().trim().equals(Const.SE))) {
            NetworkManager nm = new NetworkManager(Const.IP, Const.PORT);
            nm.setTerminalInfo(Const.PHONENUMBER, idLogin, dataLogin.getDEPTCD(), dataLogin.getJOBCODE());
            try {
                nm.setTR('A', Const.shopLocaltionInput);
                nm.connect(10);          // set timeout
                Const.WriteFileLog(dataLogin.getUSERID(), Const.shopLocaltionInput, json);
                nm.send(json);// send body
                contents = nm.receive();
                ResultAddMap summarySales_api = new Gson().fromJson(contents, ResultAddMap.class);
                if (summarySales_api.getRESULT() == 0) {
                    result = Const.OK;
                } else {
                    Log.d(TAG, "sendPosition fail");
                }
                Log.d(TAG, "sendPosition: " + contents);
            } catch (EksysNetworkException e) {
                Log.e(TAG, "sendPosition Error(" + e.getErrorCode() + "): " + e.toString());
            } finally {
                nm.close();
            }
        }

        return result;
    }

    public class API_100 extends AsyncTask<String, String, String> {
        String DATE;
        DataLogin dataLogin;
        IF_100 callback;

        public API_100(String DATE, DataLogin dataLogin, IF_100 callback) {
            this.DATE = DATE;
            this.dataLogin = dataLogin;
            this.callback = callback;
        }

        @Override
        protected String doInBackground(String... strings) {
            String s = call_100(DATE, dataLogin);
            return s;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (s.equals(Const.OK))
                callback.onSuccess();
            else callback.onFail();
        }
    }

    public String call_100(String DATE, DataLogin dataLogin) {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;
        int date = cal.get(Calendar.DATE);
        String TODAY = year + Const.formatFullDate(month) + Const.formatFullDate(date);
        Log.d(TAG, "TODAY:" + TODAY);

        String result = Const.FAIL;

        String MNGEMPID = dataLogin.getMNGEMPID();
        String contents = "";
        DatabaseHandler db = new DatabaseHandler(context);
        List<com.orion.salesman._object.API_100> api_100List = db.getListAPI_100();
        String idLogin = dataLogin.getUSERID() + "";
        if ((Const.checkInternetConnection(context)
                && Const.PHONENUMBER.length() > 0
                && idLogin.trim().length() > 0
                && dataLogin.getDEPTCD().trim().length() > 0
                && dataLogin.getJOBCODE().trim().length() > 0)
                || (Const.checkInternetConnection(context) && dataLogin.getJOBGRADE().trim().equals(Const.SE))) {
            NetworkManager nm = new NetworkManager(Const.IP, Const.PORT);
            nm.setTerminalInfo(Const.PHONENUMBER, idLogin, dataLogin.getDEPTCD(), dataLogin.getJOBCODE());
            try {
                nm.setTR('A', Const.SummarySales);
                nm.connect(10);          // set timeout
//                DataSummarySales ob = new DataSummarySales(DATE, MNGEMPID);
                Map<String, Object> map = new HashMap<>();
                map.put("DATE", DATE);
                map.put("MNGEMPID", MNGEMPID);
                String data = new Gson().toJson(map);
                Const.WriteFileLog(dataLogin.getUSERID(), Const.SummarySales, data);
                Log.d(TAG, "PARAM 100:" + data);
                nm.send(data);// send body
                contents = nm.receive();
                SummarySales_API summarySales_api = new Gson().fromJson(contents, SummarySales_API.class);
                if (summarySales_api.getRESULT() == 0) {
                    result = Const.OK;
                    if (!TODAY.equals(DATE)) {
                        if (api_100List != null && api_100List.size() > 0) {
                            if (Const.API_100_contains(api_100List, DATE)) {
                                for (int i = 0; i < api_100List.size(); i++) {
                                    if (api_100List.get(i).getDATE().equalsIgnoreCase(DATE)) {
                                        int id = api_100List.get(i).getId();
                                        db.delete_API_100(id);

                                        break;
                                    }
                                }
                            }
                        }
                        com.orion.salesman._object.API_100 offlineObject = new com.orion.salesman._object.API_100(DATE, contents);
                        db.addContact_100(offlineObject);
                    }
                } else if (summarySales_api.getRESULT() == 1) {
                    if (!TODAY.equals(DATE)) {
                        if (api_100List != null && api_100List.size() > 0) {
                            if (Const.API_100_contains(api_100List, DATE)) {
                                for (int i = 0; i < api_100List.size(); i++) {
                                    if (api_100List.get(i).getDATE().equalsIgnoreCase(DATE)) {
                                        int id = api_100List.get(i).getId();
                                        db.delete_API_100(id);
                                        break;
                                    }
                                }
                            }
                        }
                    }
                    result = Const.OK;
                }
                Log.d(TAG, "SummarySales online: " + contents);
            } catch (EksysNetworkException e) {
                Log.e(TAG, "SummarySales Error(" + e.getErrorCode() + "): " + e.toString());
            } finally {
                nm.close();
            }
        }
        return result;
    }

    public class API_130_ extends AsyncTask<String, String, String> {
        String DATE;
        DataLogin dataLogin;
        IF_130 callback;

        public API_130_(String DATE, DataLogin dataLogin, IF_130 callback) {
            this.DATE = DATE;
            this.dataLogin = dataLogin;
            this.callback = callback;
        }

        @Override
        protected String doInBackground(String... strings) {
            String s = call_130(DATE, dataLogin);
            return s;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (s.equals(Const.OK))
                callback.onSuccess();
            else callback.onFail();

        }
    }

    public String call_130(String DATE, DataLogin dataLogin) {

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;
        int date = cal.get(Calendar.DATE);
        String TODAY = year + Const.formatFullDate(month) + Const.formatFullDate(date);
        Log.d(TAG, "TODAY:" + TODAY);

        String result = Const.FAIL;
        String contents = "";
        DatabaseHandler db = new DatabaseHandler(context);
        List<API_130> api_130List = db.getListAPI_130();

        String idLogin = dataLogin.getUSERID() + "";
        if ((Const.checkInternetConnection(context)
                && Const.PHONENUMBER.length() > 0
                && idLogin.trim().length() > 0
                && dataLogin.getDEPTCD().trim().length() > 0
                && dataLogin.getJOBCODE().trim().length() > 0)
                || (Const.checkInternetConnection(context) && dataLogin.getJOBGRADE().trim().equals(Const.SE))) {
            NetworkManager nm = new NetworkManager(Const.IP, Const.PORT);
            nm.setTerminalInfo(Const.PHONENUMBER, idLogin, dataLogin.getDEPTCD(), dataLogin.getJOBCODE());
            try {
                nm.setTR('A', Const.API_130);
                nm.connect(10);// set timeout
                Map<String, Object> map = new HashMap<>();
                map.put("DATE", DATE);
                String data = new Gson().toJson(map);
                Const.WriteFileLog(dataLogin.getUSERID(), Const.API_130, data);
                nm.send(data);
                contents = nm.receive();
                DATA_API_130 data_api_130 = new Gson().fromJson(contents, DATA_API_130.class);
                if (data_api_130.getRESULT() == 0) {
                    result = Const.OK;
                    if (!TODAY.equals(DATE)) {
                        if (api_130List != null && api_130List.size() > 0) {
                            if (Const.API_130_contains(api_130List, DATE)) {
                                for (int i = 0; i < api_130List.size(); i++) {
                                    if (DATE.equals(api_130List.get(i).getDATE())) {
                                        int id = api_130List.get(i).getId();
                                        db.delete_API_130(id);
                                        break;
                                    }
                                }
                            }
                        }

                        API_130 api_130 = new API_130(DATE, contents);
                        db.addContact_130(api_130);
                    }
                } else if (data_api_130.getRESULT() == 1) {
                    if (!TODAY.equals(DATE)) {
                        if (api_130List != null && api_130List.size() > 0) {
                            if (Const.API_130_contains(api_130List, DATE)) {
                                for (int i = 0; i < api_130List.size(); i++) {
                                    if (DATE.equals(api_130List.get(i).getDATE())) {
                                        int id = api_130List.get(i).getId();
                                        db.delete_API_130(id);
                                        break;
                                    }
                                }
                            }
                        }
                    }
                    result = Const.OK;
                }
                Log.d(TAG, "API_130: " + contents);
//            Const.longInfo("getRoute",contents);
            } catch (EksysNetworkException e) {
                contents = "API_130 Error(" + e.getErrorCode() + ")";
                Log.e(TAG, "API_130 Error(" + e.getErrorCode() + "): " + e.toString());
            } finally {
                nm.close();
            }
        }
        return result;
    }

    public class API_104_ extends AsyncTask<String, String, String> {
        String DATE;
        DataLogin dataLogin;
        IF_104 callback;

        public API_104_(String DATE, DataLogin dataLogin, IF_104 callback) {
            this.DATE = DATE;
            this.dataLogin = dataLogin;
            this.callback = callback;
        }

        @Override
        protected String doInBackground(String... strings) {
            String s = call_104(DATE, dataLogin);
            return s;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (s.equals(Const.OK))
                callback.onSuccess();
            else callback.onFail();
        }
    }

    public String call_104(String DATE, DataLogin dataLogin) {
        String result = Const.FAIL;
        DatabaseHandler db = new DatabaseHandler(context);
        List<API_104> api_104List = db.getListAPI_104();

        String idLogin = dataLogin.getUSERID() + "";
        if ((Const.checkInternetConnection(context)
                && Const.PHONENUMBER.length() > 0
                && idLogin.trim().length() > 0
                && dataLogin.getDEPTCD().trim().length() > 0
                && dataLogin.getJOBCODE().trim().length() > 0)
                || (Const.checkInternetConnection(context) && dataLogin.getJOBGRADE().trim().equals(Const.SE))) {
            NetworkManager nm = new NetworkManager(Const.IP, Const.PORT);
            nm.setTerminalInfo(Const.PHONENUMBER, idLogin, dataLogin.getDEPTCD(), dataLogin.getJOBCODE());
            String contents = "";
            String MNGEMPID = dataLogin.getMNGEMPID();
            try {
                nm.setTR('A', Const.SummaryDelivery);
                nm.connect(10);          // set timeout
                DataSummarySales ob = new DataSummarySales(DATE, MNGEMPID);
                String data = new Gson().toJson(ob);
                Log.d(TAG, "getDataSummaryDelivery: " + data);
                Const.WriteFileLog(dataLogin.getUSERID(), Const.SummaryDelivery, data);
                nm.send(data);// send body
                contents = nm.receive();
                SummaryDelivery summarySales_api = new Gson().fromJson(contents, SummaryDelivery.class);
                if (summarySales_api.getRESULT() == 0) {
                    if (api_104List != null && api_104List.size() > 0) {
                        if (Const.API_104_contains(api_104List, DATE)) {
                            for (int i = 0; i < api_104List.size(); i++) {
                                if (api_104List.get(i).getDATE().equalsIgnoreCase(DATE)) {
                                    int id = api_104List.get(i).getId();
                                    db.delete_API_104(id);
                                    break;
                                }
                            }
                        }
                    }
                    result = Const.OK;
                    API_104 offlineObject = new API_104(DATE, contents);
                    db.addContact_104(offlineObject);
                } else if (summarySales_api.getRESULT() == 1) {
                    if (api_104List != null && api_104List.size() > 0) {
                        if (Const.API_104_contains(api_104List, DATE)) {
                            for (int i = 0; i < api_104List.size(); i++) {
                                if (api_104List.get(i).getDATE().equalsIgnoreCase(DATE)) {
                                    int id = api_104List.get(i).getId();
                                    db.delete_API_104(id);
                                    break;
                                }
                            }
                        }
                    }
                    result = Const.OK;
                }
                Const.longInfo(TAG, "getDataSummaryDelivery online: " + contents);
            } catch (EksysNetworkException e) {
                Log.e(TAG, "getDataSummaryDelivery Error(" + e.getErrorCode() + "): " + e.toString());
            } finally {
                nm.close();
            }
        }
        return result;
    }

    public class API_103_ extends AsyncTask<String, String, String> {
        String DATE;
        DataLogin dataLogin;
        IF_103 callback;

        public API_103_(String DATE, DataLogin dataLogin, IF_103 callback) {
            this.DATE = DATE;
            this.dataLogin = dataLogin;
            this.callback = callback;
        }

        @Override
        protected String doInBackground(String... strings) {
            String s = call_103(DATE, dataLogin);
            return s;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (s.equals(Const.OK))
                callback.onSuccess();
            else callback.onFail();
        }
    }

    public String call_103(String DATE, DataLogin dataLogin) {
        String result = Const.FAIL;

        String MNGEMPID = dataLogin.getMNGEMPID();
        String contents = "";
        DatabaseHandler db = new DatabaseHandler(context);
        List<API_103> api_103List = db.getListAPI_103();

        String idLogin = dataLogin.getUSERID() + "";
        if ((Const.checkInternetConnection(context)
                && Const.PHONENUMBER.length() > 0
                && idLogin.trim().length() > 0
                && dataLogin.getDEPTCD().trim().length() > 0
                && dataLogin.getJOBCODE().trim().length() > 0)
                || (Const.checkInternetConnection(context) && dataLogin.getJOBGRADE().trim().equals(Const.SE))) {
            NetworkManager nm = new NetworkManager(Const.IP, Const.PORT);
            nm.setTerminalInfo(Const.PHONENUMBER, idLogin, dataLogin.getDEPTCD(), dataLogin.getJOBCODE());
            try {
                String TEAM = dataLogin.getTEAM();
                nm.setTR('A', Const.SummaryDisplay);
                nm.connect(10);          // set timeout
                DataSummaryDisplay ob = new DataSummaryDisplay(DATE, TEAM, MNGEMPID);
                String data = new Gson().toJson(ob);
                Const.WriteFileLog(dataLogin.getUSERID(), Const.SummaryDisplay, data);
                nm.send(data);// send body
                contents = nm.receive();
                SummarySales_API summarySales_api = new Gson().fromJson(contents, SummarySales_API.class);
                if (summarySales_api.getRESULT() == 0) {
                    if (api_103List != null && api_103List.size() > 0) {
                        if (Const.API_103_contains(api_103List, DATE)) {
                            for (int i = 0; i < api_103List.size(); i++) {
                                Log.d(TAG, api_103List.get(i).getDATE());
                                if (api_103List.get(i).getDATE().equalsIgnoreCase(DATE)) {
                                    int id = api_103List.get(i).getId();
                                    db.delete_API_103(id);
                                    break;
                                }
                            }
                        }
                    }
                    result = Const.OK;
                    API_103 offlineObject = new API_103(DATE, contents);
                    db.addContact_103(offlineObject);
                } else if (summarySales_api.getRESULT() == 1) {
                    if (api_103List != null && api_103List.size() > 0) {
                        if (Const.API_103_contains(api_103List, DATE)) {
                            for (int i = 0; i < api_103List.size(); i++) {
                                Log.d(TAG, api_103List.get(i).getDATE());
                                if (api_103List.get(i).getDATE().equalsIgnoreCase(DATE)) {
                                    int id = api_103List.get(i).getId();
                                    db.delete_API_103(id);
                                    break;
                                }
                            }
                        }
                    }
                    result = Const.OK;
                }
                Log.d(TAG, "DisplayFragment online: " + contents);
            } catch (EksysNetworkException e) {
                Log.e(TAG, "DisplayFragment Error(" + e.getErrorCode() + "): " + e.toString());
            } finally {
                nm.close();
            }
        }
        return result;
    }

    public class API_303_ extends AsyncTask<String, String, String> {
        String DATE;
        DataLogin dataLogin;
        IF_303 callback;

        public API_303_(String DATE, DataLogin dataLogin, IF_303 callback) {
            this.DATE = DATE;
            this.dataLogin = dataLogin;
            this.callback = callback;
        }

        @Override
        protected String doInBackground(String... strings) {
            String s = call_303(DATE, dataLogin);
            return s;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (s.equals(Const.OK))
                callback.onSuccess();
            else callback.onFail();
        }
    }

    public String call_303(String DATE, DataLogin dataLogin) {
        String result = Const.FAIL;
        String contents = "";
        DatabaseHandler db = new DatabaseHandler(context);
        List<API_303> api_303List = db.getListAPI_303();
        String idLogin = dataLogin.getUSERID() + "";
        if ((Const.checkInternetConnection(context)
                && Const.PHONENUMBER.length() > 0
                && idLogin.trim().length() > 0
                && dataLogin.getDEPTCD().trim().length() > 0
                && dataLogin.getJOBCODE().trim().length() > 0)
                || (Const.checkInternetConnection(context) && dataLogin.getJOBGRADE().trim().equals(Const.SE))) {
            NetworkManager nm = new NetworkManager(Const.IP, Const.PORT);
            nm.setTerminalInfo(Const.PHONENUMBER, idLogin, dataLogin.getDEPTCD(), dataLogin.getJOBCODE());
            try {
                nm.setTR('A', Const.API_303);
                nm.connect(10);          // set timeout
                Map<String, Object> map = new HashMap<>();
                map.put("MODE", "S");
                map.put("DATE", DATE);
                map.put("GSID", dataLogin.getMNGEMPID());
//                map.put("GSID", "2090469");
                String senddata = new Gson().toJson(map);
                Const.WriteFileLog(dataLogin.getUSERID(), Const.API_303, senddata);
                Log.d(TAG, "A0133" + senddata);
                nm.send(senddata);// send body
                contents = nm.receive();
                DATA_303 data_303 = new Gson().fromJson(contents, DATA_303.class);
                if (data_303.getRESULT() == 0) {
//                    if (api_303List != null && api_303List.size() > 0) {
//                        if (Const.API_303_contains(api_303List, DATE)) {
//                            for (int i = 0; i < api_303List.size(); i++) {
//                                API_303 ob = api_303List.get(i);
//                                if (ob.getDATE().equals(DATE)) {
//                                    int id = ob.getId();
//                                    db.delete_API_303(id);
//                                    break;
//                                }
//                            }
//                        }
//                    }
                    result = Const.OK;
                    db.delete_All_API_303();
                    db.delete_All_API_304();
//                    API_303 api_303 = new API_303(DATE, contents);
//                    db.addContact_303(api_303);
                } else if (data_303.getRESULT() == 1) {
//                    if (api_303List != null && api_303List.size() > 0) {
//                        if (Const.API_303_contains(api_303List, DATE)) {
//                            for (int i = 0; i < api_303List.size(); i++) {
//                                API_303 ob = api_303List.get(i);
//                                if (ob.getDATE().equals(DATE)) {
//                                    int id = ob.getId();
//                                    db.delete_API_303(id);
//                                    break;
//                                }
//                            }
//                        }
//                    }
                    result = Const.OK;
                    db.delete_All_API_303();
                    db.delete_All_API_304();
                }
                Log.d(TAG, "303 online: " + contents);
            } catch (EksysNetworkException e) {
                Log.e(TAG, "303 Error(" + e.getErrorCode() + "): " + e.toString());
            } finally {
                nm.close();
            }
        }
        return result;
    }

    public class API_131_ extends AsyncTask<String, String, String> {
        String DATE;
        DataLogin dataLogin;
        IF_131 callback;

        public API_131_(String DATE, DataLogin dataLogin, IF_131 callback) {
            this.DATE = DATE;
            this.dataLogin = dataLogin;
            this.callback = callback;
        }

        @Override
        protected String doInBackground(String... strings) {
            String s = call_131(DATE, dataLogin);
            return s;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (s.equals(Const.OK))
                callback.onSuccess();
            else callback.onFail();
        }
    }

    public String call_131(String DATE, DataLogin dataLogin) {
        String result = Const.FAIL;
        String contents = "";
        DatabaseHandler db = new DatabaseHandler(context);
        List<API_131> api_131List = db.getListAPI_131();
        String idLogin = dataLogin.getUSERID() + "";
        if ((Const.checkInternetConnection(context)
                && Const.PHONENUMBER.length() > 0
                && idLogin.trim().length() > 0
                && dataLogin.getDEPTCD().trim().length() > 0
                && dataLogin.getJOBCODE().trim().length() > 0)
                || (Const.checkInternetConnection(context) && dataLogin.getJOBGRADE().trim().equals(Const.SE))) {
            NetworkManager nm = new NetworkManager(Const.IP, Const.PORT);
            nm.setTerminalInfo(Const.PHONENUMBER, idLogin, dataLogin.getDEPTCD(), dataLogin.getJOBCODE());
            try {
                nm.setTR('A', Const.API_1311);
                nm.connect(10);// set timeout
                Map<String, Object> map = new HashMap<>();
                map.put("DATE", DATE);
                String data = new Gson().toJson(map);
                Log.d(TAG, "param_131:" + data);
                Const.WriteFileLog(dataLogin.getUSERID(), Const.API_1311, data);
                nm.send(data);
                contents = nm.receive();
                DATA_API_131 data_api_131 = new Gson().fromJson(contents, DATA_API_131.class);
                if (data_api_131.getRESULT() == 0) {
                    if (api_131List != null && api_131List.size() > 0) {
                        db.delete_All_API_131();
                        db.delete_All_API_132();
                    }
                    result = Const.OK;
                    API_131 api_131 = new API_131(DATE, contents);
                    db.addContact_131(api_131);
                } else if (data_api_131.getRESULT() == 1) {
                    if (api_131List != null && api_131List.size() > 0) {
                        db.delete_All_API_131();
                        db.delete_All_API_132();
                    }
                    result = Const.OK;
                }
                Log.d(TAG, "API_131 online: " + contents);
            } catch (EksysNetworkException e) {
                Log.e(TAG, "API_131 Error(" + e.getErrorCode() + "): " + e.toString());
            } finally {
                nm.close();
            }
        }
        return result;
    }

    public class API_112_ extends AsyncTask<String, String, String> {
        DataLogin dataLogin;
        String KEY_ROUTE;
        IF_112_FIRST callback;
        int type;

        public API_112_(DataLogin dataLogin, String KEY_ROUTE, IF_112_FIRST callback, int type) {
            this.dataLogin = dataLogin;
            this.KEY_ROUTE = KEY_ROUTE;
            this.callback = callback;
            this.type = type;
        }

        @Override
        protected String doInBackground(String... strings) {
            String s = call_112(dataLogin, KEY_ROUTE, type);
            return s;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (s.equals(Const.OK))
                callback.onSuccess();
            else callback.onFail();
        }
    }

    public String call_112(DataLogin dataLogin, String KEY_ROUTE, int type) {
        // type = 0 -> yesterdayelse today
//        Calendar calendar = Calendar.getInstance();
//        int year = calendar.get(Calendar.YEAR);
//        int month = calendar.get(Calendar.MONTH) + 1;
//        int dayofmonth = calendar.get(Calendar.DAY_OF_MONTH);
//        String DATE = year + Const.formatFullDate(month) + Const.formatFullDate(dayofmonth);
        String DATE = "";
        String YESTERDAY = "";
        Calendar cal = Calendar.getInstance();
        int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;
        int date = cal.get(Calendar.DATE);
        String TODAY = year + Const.formatFullDate(month) + Const.formatFullDate(date);
        //Sunday = 1, Monday = 2,...

        if (dayOfWeek == 2) {
            YESTERDAY = Const.getToday(-2);
        } else {
            YESTERDAY = Const.getToday(-1);
        }
        if (type == 0)
            DATE = YESTERDAY;
        else DATE = TODAY;

        String result = Const.FAIL;
        DatabaseHandler db = new DatabaseHandler(context);
        List<com.orion.salesman._object.API_112> listAPI_112 = db.getAllContacts();

        String idLogin = dataLogin.getUSERID() + "";
        if ((Const.checkInternetConnection(context)
                && Const.PHONENUMBER.length() > 0
                && idLogin.trim().length() > 0
                && dataLogin.getDEPTCD().trim().length() > 0
                && dataLogin.getJOBCODE().trim().length() > 0)
                || (Const.checkInternetConnection(context) && dataLogin.getJOBGRADE().trim().equals(Const.SE))) {
            NetworkManager nm = new NetworkManager(Const.IP, Const.PORT);
            nm.setTerminalInfo(Const.PHONENUMBER, idLogin, dataLogin.getDEPTCD(), dataLogin.getJOBCODE());
            String contents = "";
            try {
                nm.setTR('A', Const.getRouteShop);
                nm.connect(10);// set timeout
//                ParamsRouteShop ob = new ParamsRouteShop("0", "", DATE);
                Map<String, Object> map = new HashMap<>();
                map.put("MODE", "0");
                map.put("CUSTCD", "");
                map.put("DATE", DATE);
                String data = new Gson().toJson(map);
                Const.WriteFileLog(dataLogin.getUSERID(), Const.getRouteShop, data);
                Log.d(TAG, "getRouteShop:" + data);
                nm.send(data);
                contents = nm.receive();
                ListInforShop routeList = new Gson().fromJson(contents, ListInforShop.class);
                if (routeList.getRESULT() == 0) {
                    if (listAPI_112 == null || listAPI_112.size() == 0) {
                    } else {
                        if (Const.API_112_contains(listAPI_112, DATE, KEY_ROUTE)) {
                            for (int i = 0; i < listAPI_112.size(); i++) {
                                if (listAPI_112.get(i).getDATE().equalsIgnoreCase(DATE)
                                        && listAPI_112.get(i).getWEEK().equalsIgnoreCase(KEY_ROUTE)) {
                                    int id = listAPI_112.get(i).getId();
                                    db.deleteContact(id);
                                    break;
                                }
                            }
                        }
                    }
                    result = Const.OK;
                    com.orion.salesman._object.API_112 api_112 = new com.orion.salesman._object.API_112(DATE, KEY_ROUTE, new Gson().toJson(routeList));
                    db.addContact(api_112);
                } else if (routeList.getRESULT() == 1) {
                    if (listAPI_112 == null || listAPI_112.size() == 0) {
                    } else {
                        if (Const.API_112_contains(listAPI_112, DATE, KEY_ROUTE)) {
                            for (int i = 0; i < listAPI_112.size(); i++) {
                                if (listAPI_112.get(i).getDATE().equalsIgnoreCase(DATE)
                                        && listAPI_112.get(i).getWEEK().equalsIgnoreCase(KEY_ROUTE)) {
                                    int id = listAPI_112.get(i).getId();
                                    db.deleteContact(id);
                                    break;
                                }
                            }
                        }
                    }
                    result = Const.OK;
                }
                Const.longInfo(TAG, "getRouteShop: " + contents);
            } catch (EksysNetworkException e) {
                Log.e(TAG, "getRouteShop Error(" + e.getErrorCode() + "): " + e.toString());
            } finally {
                nm.close();
            }
        }
        return result;
    }

    public class API_110_ extends AsyncTask<String, String, String> {
        DataLogin dataLogin;
        String KEY_ROUTE;
        IF_110 callback;
        int type;

        public API_110_(DataLogin dataLogin, String KEY_ROUTE, IF_110 callback, int type) {
            this.dataLogin = dataLogin;
            this.KEY_ROUTE = KEY_ROUTE;
            this.callback = callback;
            this.type = type;
        }

        @Override
        protected String doInBackground(String... strings) {
            String s = call_110(dataLogin, KEY_ROUTE, type);
            return s;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (s.equals(Const.OK))
                callback.onSuccess();
            else callback.onFail();
        }
    }

    public String call_110(DataLogin dataLogin, String KEY_ROUTE, int type) {
        String result = Const.FAIL;
        //type=0; yesterday else todya
        String YESTERDAY = "";
        Calendar cal = Calendar.getInstance();
        int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;
        int date = cal.get(Calendar.DATE);
        String TODAY = year + Const.formatFullDate(month) + Const.formatFullDate(date);
        //Sunday = 1, Monday = 2,...

        if (dayOfWeek == 2) {
            YESTERDAY = Const.getToday(-2);
        } else {
            YESTERDAY = Const.getToday(-1);
        }
        String DATE = "";
        if (type == 0) {
            DATE = YESTERDAY;
        } else {
            DATE = TODAY;
        }


//        Calendar calendar = Calendar.getInstance();
//        int year = calendar.get(Calendar.YEAR);
//        int month = calendar.get(Calendar.MONTH) + 1;
//        int dayofmonth = calendar.get(Calendar.DAY_OF_MONTH);
//        String DATE = year + Const.formatFullDate(month) + Const.formatFullDate(dayofmonth);
        DatabaseHandler db = new DatabaseHandler(context);
        List<API_110> api_110List = db.getListAPI_110();

        String idLogin = dataLogin.getUSERID() + "";
        if ((Const.checkInternetConnection(context)
                && Const.PHONENUMBER.length() > 0
                && idLogin.trim().length() > 0
                && dataLogin.getDEPTCD().trim().length() > 0
                && dataLogin.getJOBCODE().trim().length() > 0)
                || (Const.checkInternetConnection(context) && dataLogin.getJOBGRADE().trim().equals(Const.SE))) {
            NetworkManager nm = new NetworkManager(Const.IP, Const.PORT);
            nm.setTerminalInfo(Const.PHONENUMBER, idLogin, dataLogin.getDEPTCD(), dataLogin.getJOBCODE());
            String contents = "";
            try {
                nm.setTR('A', Const.Route);
                nm.connect(10);          // set timeout
                RequestRoute ob = new RequestRoute(DATE, KEY_ROUTE);

                String data = new Gson().toJson(ob);
                Log.d(TAG, "RouteList param:" + data);
                Const.WriteFileLog(dataLogin.getUSERID(), Const.Route, data);
                nm.send(data);// send body
                contents = nm.receive();
                RouteList routeList = new Gson().fromJson(contents, RouteList.class);
                if (routeList.getRESULT() == 0) {
                    if (api_110List != null && api_110List.size() > 0) {
                        if (Const.API_110_contains(api_110List, DATE, KEY_ROUTE)) {
                            for (int i = 0; i < api_110List.size(); i++) {
                                if (api_110List.get(i).getDATE().equalsIgnoreCase(DATE)
                                        && api_110List.get(i).getKEY_ROUTE().equalsIgnoreCase(KEY_ROUTE)) {
                                    int id = api_110List.get(i).getId();
                                    db.delete_API_110(id);
                                    break;
                                }
                            }
                        }
                    }
                    result = Const.OK;
                    API_110 offlineObject = new API_110(DATE, KEY_ROUTE, contents);
                    db.addContact_110(offlineObject);
                } else if (routeList.getRESULT() == 1) {
                    if (api_110List != null && api_110List.size() > 0) {
                        if (Const.API_110_contains(api_110List, DATE, KEY_ROUTE)) {
                            for (int i = 0; i < api_110List.size(); i++) {
                                if (api_110List.get(i).getDATE().equalsIgnoreCase(DATE)
                                        && api_110List.get(i).getKEY_ROUTE().equalsIgnoreCase(KEY_ROUTE)) {
                                    int id = api_110List.get(i).getId();
                                    db.delete_API_110(id);
                                    break;
                                }
                            }
                        }
                    }
                    result = Const.OK;
                }
                Const.longInfo(TAG, "RouteList: DATE" + contents);
            } catch (EksysNetworkException e) {
                Log.e(TAG, "getRoute Error(" + e.getErrorCode() + "): " + e.toString());
            } finally {
                nm.close();
            }
        }
        return result;
    }

    public class API_121_ extends AsyncTask<String, String, String> {
        DataLogin dataLogin;
        IF_121 callback;
        String DATE;

        public API_121_(DataLogin dataLogin, IF_121 callback, String DATE) {
            this.dataLogin = dataLogin;
            this.callback = callback;
            this.DATE = DATE;
        }

        @Override
        protected String doInBackground(String... strings) {
            String s = call_121(dataLogin, DATE);
            return s;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (s.equals(Const.OK))
                callback.onSuccess();
            else callback.onFail();
        }
    }

    public String call_121(DataLogin dataLogin, String DATE) {
        String result = Const.FAIL;
        DatabaseHandler db = new DatabaseHandler(context);
        List<API_121> api_121List = db.getListAPI_121();

//        Calendar calendar = Calendar.getInstance();
//        int y = calendar.get(Calendar.YEAR);
//        int m = calendar.get(Calendar.MONTH) + 1;
//        int d = calendar.get(Calendar.DATE);
//        String DATE = y + Const.setFullDate(m) + Const.setFullDate(d);
        String idLogin = dataLogin.getUSERID() + "";
        if ((Const.checkInternetConnection(context)
                && Const.PHONENUMBER.length() > 0
                && idLogin.trim().length() > 0
                && dataLogin.getDEPTCD().trim().length() > 0
                && dataLogin.getJOBCODE().trim().length() > 0)
                || (Const.checkInternetConnection(context) && dataLogin.getJOBGRADE().trim().equals(Const.SE))) {
            NetworkManager nm = new NetworkManager(Const.IP, Const.PORT);
            nm.setTerminalInfo(Const.PHONENUMBER, idLogin, dataLogin.getDEPTCD(), dataLogin.getJOBCODE());
            String contents = "";
            try {
                nm.setTR('A', Const.Get_Promotion_List);
                nm.connect(10);    // set timeout
                Map<String, Object> map = new HashMap<>();
                map.put("DATE", DATE);
                map.put("HIDEPTCD", dataLogin.getHIDEPTCD());
                String data = new Gson().toJson(map);
                Log.d(TAG, "GetPromotionList:" + data);
                Const.WriteFileLog(dataLogin.getUSERID(), Const.Get_Promotion_List, data);
                nm.send(data);// send body
                contents = nm.receive();
                PmList summarySales_api = new Gson().fromJson(contents, PmList.class);
                if (summarySales_api.getRESULT() == 0) {
                    if (api_121List != null && api_121List.size() > 0) {
                        for (int i = 0; i < api_121List.size(); i++) {
                            API_121 obj = api_121List.get(i);
                            int id = obj.getId();
                            String MONTH = obj.getDATE().substring(0, 6);
                            Log.d(TAG, "MONTH:" + MONTH);
                            if (MONTH.equals(DATE.substring(0, 6))) {
                                db.delete_API_121(id);
                                Log.d(TAG, "remove:" + DATE.substring(0, 6));
                            } else {
                                Log.d(TAG, "dont remove:" + DATE.substring(0, 6));
                            }
                        }
                    }
                    result = Const.OK;


                    API_121 offlineObject = new API_121(DATE, contents);
                    db.addContact_121(offlineObject);
                } else if (summarySales_api.getRESULT() == 1) {
//                    if (api_121List != null && api_121List.size() > 0)
//                        db.delete_All_API_121();
                    if (api_121List != null && api_121List.size() > 0) {
                        for (int i = 0; i < api_121List.size(); i++) {
                            API_121 obj = api_121List.get(i);
                            int id = obj.getId();
                            String MONTH = obj.getDATE().substring(0, 6);
                            Log.d(TAG, "MONTH:" + MONTH);
                            if (MONTH.equals(DATE.substring(0, 6))) {
                                db.delete_API_121(id);
                                Log.d(TAG, "remove:" + DATE.substring(0, 6));
                            } else {
                                Log.d(TAG, "dont remove:" + DATE.substring(0, 6));
                            }
                        }
                    }
                    result = Const.OK;
                }
                Const.longInfo(TAG, "GetPromotionList:" + contents);
            } catch (EksysNetworkException e) {
                Log.e(TAG, "GetPromotionList Error(" + e.getErrorCode() + "): " + e.toString());
            } finally {
                nm.close();
            }
        }
        return result;
    }

    public class API_123_ extends AsyncTask<String, String, String> {
        DataLogin dataLogin;
        IF_123 callback;

        public API_123_(DataLogin dataLogin, IF_123 callback) {
            this.dataLogin = dataLogin;
            this.callback = callback;
        }

        @Override
        protected String doInBackground(String... strings) {
            String s = call_123(dataLogin);
            return s;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (s.equals(Const.OK))
                callback.onSuccess();
            else callback.onFail();
        }
    }

    public String call_123(DataLogin dataLogin) {
        String result = Const.FAIL;
        DatabaseHandler db = new DatabaseHandler(context);
        Calendar calendar = Calendar.getInstance();
        int y = calendar.get(Calendar.YEAR);
        int m = calendar.get(Calendar.MONTH) + 1;
        int d = calendar.get(Calendar.DAY_OF_MONTH);
        String USE_DATE = y + Const.setFullDate(m) + Const.setFullDate(d);
        List<API_123> listAPI_123 = db.getListAPI_123();

        String idLogin = dataLogin.getUSERID() + "";
        if ((Const.checkInternetConnection(context)
                && Const.PHONENUMBER.length() > 0
                && idLogin.trim().length() > 0
                && dataLogin.getDEPTCD().trim().length() > 0
                && dataLogin.getJOBCODE().trim().length() > 0)
                || (Const.checkInternetConnection(context) && dataLogin.getJOBGRADE().trim().equals(Const.SE))) {
            NetworkManager nm = new NetworkManager(Const.IP, Const.PORT);
            nm.setTerminalInfo(Const.PHONENUMBER, idLogin, dataLogin.getDEPTCD(), dataLogin.getJOBCODE());
            String contents = "";
            try {
                nm.setTR('A', Const.GetNewShopPromotionList);
                nm.connect(10);    // set timeout
                Map<String, Object> map = new HashMap<>();
                map.put("DATE", USE_DATE);
                String data = new Gson().toJson(map);
                Log.d(TAG, "GetNewShopPromotionList:" + data);
                Const.WriteFileLog(dataLogin.getUSERID(), Const.GetNewShopPromotionList, data);
                nm.send(data);// send body
                contents = nm.receive();
                GetNewShopPromotionList summarySales_api = new Gson().fromJson(contents, GetNewShopPromotionList.class);
                if (summarySales_api.getRESULT() == 0) {
                    if (listAPI_123 != null && listAPI_123.size() > 0)
                        db.delete_All_API_123();
                    result = Const.OK;
                    Log.d(TAG, "GetNewShopPromotionList success");
                    API_123 api_123 = new API_123(USE_DATE, contents);
                    db.addContact_123(api_123);
                } else if (summarySales_api.getRESULT() == 1) {
                    if (listAPI_123 != null && listAPI_123.size() > 0)
                        db.delete_All_API_123();
                    result = Const.OK;
                    Log.d(TAG, "GetNewShopPromotionList fail");
                }
//                Log.d(TAG, "GetNewShopPromotionList: " + contents);
                Const.longInfo(TAG, "GetNewShopPromotionList:" + contents);
            } catch (EksysNetworkException e) {
                Log.e(TAG, "GetNewShopPromotionList Error(" + e.getErrorCode() + "): " + e.toString());
            } finally {
                nm.close();
            }
        }
        return result;
    }

    public class API_119 extends AsyncTask<String, String, String> {
        DataLogin dataLogin;
        String json;
        IF_119 callback;

        public API_119(DataLogin dataLogin, String json, IF_119 callback) {
            this.dataLogin = dataLogin;
            this.json = json;
            this.callback = callback;
        }

        @Override
        protected String doInBackground(String... strings) {
            String s = call_119(dataLogin, json);
            return s;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (s.equals(Const.OK))
                callback.onSuccess();
            else callback.onFail();
        }
    }

    public String call_119(DataLogin dataLogin, String json) {
        String result = Const.FAIL;
        String idLogin = dataLogin.getUSERID() + "";
        if ((Const.checkInternetConnection(context)
                && Const.PHONENUMBER.length() > 0
                && idLogin.trim().length() > 0
                && dataLogin.getDEPTCD().trim().length() > 0
                && dataLogin.getJOBCODE().trim().length() > 0)
                || (Const.checkInternetConnection(context) && dataLogin.getJOBGRADE().trim().equals(Const.SE))) {
            NetworkManager nm = new NetworkManager(Const.IP, Const.PORT);
            nm.setTerminalInfo(Const.PHONENUMBER, idLogin, dataLogin.getDEPTCD(), dataLogin.getJOBCODE());
            String contents = "";

            try {
                nm.setTR('A', Const.DisplayInput);
                nm.connect(10);          // set timeout
                Log.d(TAG, "API_119" + json);
                Const.WriteFileLog(dataLogin.getUSERID(), Const.DisplayInput, json);
                nm.send(json);// send body
                contents = nm.receive();
                VisitShop summarySales_api = new Gson().fromJson(contents, VisitShop.class);
                if (summarySales_api.getRESULT() == 0) {
                    result = Const.OK;
                } else {
                }
                Log.d(TAG, "119: " + contents);
            } catch (EksysNetworkException e) {
                Log.e(TAG, "119 Error(" + e.getErrorCode() + "): " + e.toString());
            } finally {
                nm.close();
            }
        }
        return result;
    }

    public class API_124 extends AsyncTask<String, String, String> {
        DataLogin dataLogin;
        String json;
        IF_124 callback;

        public API_124(DataLogin dataLogin, String json, IF_124 callback) {
            this.dataLogin = dataLogin;
            this.json = json;
            this.callback = callback;
        }

        @Override
        protected String doInBackground(String... strings) {
            String s = call_124(dataLogin, json);
            return s;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (s.equals(Const.OK))
                callback.onSuccess();
            else callback.onFail();
        }
    }

    public String call_124(DataLogin dataLogin, String json) {
        Log.d(TAG, "124: " + json);
        String result = Const.FAIL;
        String idLogin = dataLogin.getUSERID() + "";
        if ((Const.checkInternetConnection(context)
                && Const.PHONENUMBER.length() > 0
                && idLogin.trim().length() > 0
                && dataLogin.getDEPTCD().trim().length() > 0
                && dataLogin.getJOBCODE().trim().length() > 0)
                || (Const.checkInternetConnection(context) && dataLogin.getJOBGRADE().trim().equals(Const.SE))) {
            NetworkManager nm = new NetworkManager(Const.IP, Const.PORT);
            nm.setTerminalInfo(Const.PHONENUMBER, idLogin, dataLogin.getDEPTCD(), dataLogin.getJOBCODE());
            String contents = "";
            try {
                nm.setTR('A', Const.InputOrder);
                nm.connect(10);          // set timeout
                Const.WriteFileLog(dataLogin.getUSERID(), Const.InputOrder, json);
                nm.send(json);// send body
                contents = nm.receive();
                RESULTOBJECT resultobject = new Gson().fromJson(contents, RESULTOBJECT.class);
                if (resultobject.getRESULT() == 0) {
                    result = Const.OK;
                } else {
                }
                Log.d(TAG, "124: " + contents);
            } catch (EksysNetworkException e) {
                Log.e(TAG, "124 Error(" + e.getErrorCode() + "): " + e.toString());
            } finally {
                nm.close();
            }
        }
        return result;
    }

    public class API_118 extends AsyncTask<String, String, String> {
        DataLogin dataLogin;
        String json;
        IF_118 callback;

        public API_118(DataLogin dataLogin, String json, IF_118 callback) {
            this.dataLogin = dataLogin;
            this.json = json;
            this.callback = callback;
        }

        @Override
        protected String doInBackground(String... strings) {
            String s = call_118(dataLogin, json);
            return s;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (s.equals(Const.OK))
                callback.onSuccess();
            else callback.onFail();
        }
    }

    public String call_118(DataLogin dataLogin, String json) {
        String result = Const.FAIL;
        String idLogin = dataLogin.getUSERID() + "";
        if ((Const.checkInternetConnection(context)
                && Const.PHONENUMBER.length() > 0
                && idLogin.trim().length() > 0
                && dataLogin.getDEPTCD().trim().length() > 0
                && dataLogin.getJOBCODE().trim().length() > 0)
                || (Const.checkInternetConnection(context) && dataLogin.getJOBGRADE().trim().equals(Const.SE))) {
            NetworkManager nm = new NetworkManager(Const.IP, Const.PORT);
            nm.setTerminalInfo(Const.PHONENUMBER, idLogin, dataLogin.getDEPTCD(), dataLogin.getJOBCODE());
            String contents = "";
            try {
                nm.setTR('A', Const.StockInput);
                nm.connect(10);          // set timeout
                Log.d(TAG, "API 118:" + json);
                Const.WriteFileLog(dataLogin.getUSERID(), Const.StockInput, json);
                nm.send(json);// send body
                contents = nm.receive();
                InputStock summarySales_api = new Gson().fromJson(contents, InputStock.class);
                if (summarySales_api.getRESULT() == 0) {
                    result = Const.OK;
                } else {
                }
                Log.d(TAG, "inputStock: " + contents);
            } catch (EksysNetworkException e) {
                Log.e(TAG, "inputStock Error(" + e.getErrorCode() + "): " + e.toString());
            } finally {
                nm.close();
            }
        }
        return result;
    }

    public class API_115 extends AsyncTask<String, String, String> {
        DataLogin dataLogin;
        String jsondata;
        IF_115 callback;

        public API_115(DataLogin dataLogin, String json, IF_115 callback) {
            this.dataLogin = dataLogin;
            this.jsondata = json;
            this.callback = callback;
        }

        @Override
        protected String doInBackground(String... strings) {
//            Log.d(TAG, "VISIT HTTP:" + jsondata);
            String s = call_115(dataLogin, jsondata);
            return s;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (s.equals(Const.OK))
                callback.onSuccess();
            else callback.onFail();
        }
    }

    public String call_115(DataLogin dataLogin, String json) {
        Log.d(TAG, "VisitShop: LENGHT:" + json.length() + ":" + json);
        String contents = "";
        String result = Const.FAIL;
        String idLogin = dataLogin.getUSERID() + "";
        if ((Const.checkInternetConnection(context)
                && Const.PHONENUMBER.length() > 0
                && idLogin.trim().length() > 0
                && dataLogin.getDEPTCD().trim().length() > 0
                && dataLogin.getJOBCODE().trim().length() > 0)
                || (Const.checkInternetConnection(context) && dataLogin.getJOBGRADE().trim().equals(Const.SE))) {
            NetworkManager nm = new NetworkManager(Const.IP, Const.PORT);
            nm.setTerminalInfo(Const.PHONENUMBER, idLogin, dataLogin.getDEPTCD(), dataLogin.getJOBCODE());
            try {
                nm.setTR('A', Const.VisitShop);
//                Log.d(TAG, "VisitShop HTTP");
                nm.connect(10);          // set timeout
                Const.WriteFileLog(dataLogin.getUSERID(), Const.VisitShop, json);
                nm.send(json);// send body
                contents = nm.receive();
                VisitShop summarySales_api = new Gson().fromJson(contents, VisitShop.class);
                if (summarySales_api.getRESULT() == 0) {
                    result = Const.OK;
                } else {
                }
                Log.d(TAG, "VisitShop: " + contents);
            } catch (EksysNetworkException e) {
                Log.e(TAG, "VisitShop Error(" + e.getErrorCode() + "): " + e.toString());
            } finally {
                nm.close();
            }
        }
        return result;
    }

    public class API_122_ extends AsyncTask<String, String, String> {
        DataLogin dataLogin;
        IF_122 callback;

        public API_122_(DataLogin dataLogin, IF_122 callback) {
            this.dataLogin = dataLogin;
            this.callback = callback;
        }

        @Override
        protected String doInBackground(String... strings) {
            String s = GetNonShopSKUPromotionList(dataLogin);
            return s;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (s.equals(Const.OK))
                callback.onSuccess();
            else callback.onFail();
        }
    }

    public String GetNonShopSKUPromotionList(DataLogin dataLogin) {
        Calendar calendar = Calendar.getInstance();
        int YEAR = calendar.get(Calendar.YEAR);
        int MONTH = calendar.get(Calendar.MONTH) + 1;
        String USE_DATE = YEAR + Const.formatFullDate(MONTH);
        String result = Const.FAIL;
        String idLogin = dataLogin.getUSERID() + "";
        if ((Const.checkInternetConnection(context)
                && Const.PHONENUMBER.length() > 0
                && idLogin.trim().length() > 0
                && dataLogin.getDEPTCD().trim().length() > 0
                && dataLogin.getJOBCODE().trim().length() > 0)
                || (Const.checkInternetConnection(context) && dataLogin.getJOBGRADE().trim().equals(Const.SE))) {
            NetworkManager nm = new NetworkManager(Const.IP, Const.PORT);
            nm.setTerminalInfo(Const.PHONENUMBER, idLogin, dataLogin.getDEPTCD(), dataLogin.getJOBCODE());
            DatabaseHandler db = new DatabaseHandler(context);
            List<API_122> listAPI_122 = db.getListAPI_122();

            String contents = "";
            try {
                nm.setTR('A', Const.GetNonShopSKUPromotionList);
                nm.connect(10);    // set timeout
                Map<String, Object> map = new HashMap<>();

                Log.d(TAG, "GetNonShopSKUPromotionList:" + "USE_DATE:" + USE_DATE);
                map.put("YEARMM", USE_DATE);
                map.put("TEAMCD", MainActivity.TEAM);
                String data = new Gson().toJson(map);
                Log.d(TAG, "ShopSKUPromotionList:" + data);
                Const.WriteFileLog(dataLogin.getUSERID(), Const.GetNonShopSKUPromotionList, data);
                nm.send(data);// send body
                contents = nm.receive();
                PmList summarySales_api = new Gson().fromJson(contents, PmList.class);
                if (summarySales_api.getRESULT() == 0) {
                    if (listAPI_122 != null && listAPI_122.size() > 0)
                        db.delete_All_API_122();
//                    Log.d(TAG, "ShopSKUPromotionList success");
                    result = Const.OK;
                    API_122 offlineObject = new API_122(USE_DATE, contents);
                    db.addContact_122(offlineObject);
                } else if (summarySales_api.getRESULT() == 1) {
                    if (listAPI_122 != null && listAPI_122.size() > 0)
                        db.delete_All_API_122();
                    result = Const.OK;
                    Log.e(TAG, "ShopSKUPromotionList fail");
                }
//                Log.d(TAG, "ShopSKUPromotionList: " + contents);
                Const.longInfo(TAG, "ShopSKUPromotionList:" + contents);
            } catch (EksysNetworkException e) {
                contents = "ShopSKUPromotionList Error(" + e.getErrorCode() + ")";
                Log.e(TAG, "ShopSKUPromotionList Error(" + e.getErrorCode() + "): " + e.toString());
            } finally {
                nm.close();
            }
        }
        return result;
    }

    public class API_120_ extends AsyncTask<String, String, String> {
        DataLogin dataLogin;
        IF_100 callback;

        public API_120_(DataLogin dataLogin, IF_100 callback) {
            this.dataLogin = dataLogin;
            this.callback = callback;
        }

        @Override
        protected String doInBackground(String... strings) {
            String s = callAPINonShop(dataLogin);
            return s;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (s.equals(Const.OK))
                callback.onSuccess();
            else callback.onFail();
        }
    }

    public String callAPINonShop(DataLogin dataLogin) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;
        int date = cal.get(Calendar.DATE);
        String USE_DATE = year + Const.formatFullDate(month) + Const.formatFullDate(date);
        String result = Const.FAIL;
        String idLogin = dataLogin.getUSERID() + "";
        if ((Const.checkInternetConnection(context)
                && Const.PHONENUMBER.length() > 0
                && idLogin.trim().length() > 0
                && dataLogin.getDEPTCD().trim().length() > 0
                && dataLogin.getJOBCODE().trim().length() > 0)
                || (Const.checkInternetConnection(context) && dataLogin.getJOBGRADE().trim().equals(Const.SE))) {
            NetworkManager nm = new NetworkManager(Const.IP, Const.PORT);
            nm.setTerminalInfo(Const.PHONENUMBER, idLogin, dataLogin.getDEPTCD(), dataLogin.getJOBCODE());
            DatabaseHandler db = new DatabaseHandler(context);
            List<API_120> api_120List = db.getListAPI_120();


            String contents = "";
            try {
                nm.setTR('A', Const.GetOrderNonShopList);
                nm.connect(10);// set timeout
//            RouteShopStockAPI ob = new RouteShopStockAPI("" + DAY_OF_WEEK);
                Map<String, Object> map = new HashMap<>();
                String YEARMM = USE_DATE.substring(0, 6);
                map.put("YEARMM", YEARMM);
                map.put("TEAMCD", dataLogin.getTEAM());
                String data = new Gson().toJson(map);
                Log.d(TAG, "getOrderNonShop: " + data);
                Const.WriteFileLog(dataLogin.getUSERID(), Const.GetOrderNonShopList, data);
                nm.send(data);
                contents = nm.receive();
                OrderNonShopListAPI routeList = new Gson().fromJson(contents, OrderNonShopListAPI.class);
                if (routeList.getRESULT() == 0) {
                    result = Const.OK;
                    if (api_120List != null && api_120List.size() > 0)
                        db.delete_All_API_120();
                    Log.d(TAG, "API 120 ok");
                    API_120 offlineObject = new API_120(USE_DATE, contents);
                    db.addContact_120(offlineObject);
                } else if (routeList.getRESULT() == 1) {
                    result = Const.OK;
                    if (api_120List != null && api_120List.size() > 0)
                        db.delete_All_API_120();
                    Log.d(TAG, "API no data");
                } else {
                    Log.e(TAG, "getOrderNonShop Fail");
                }

//                Log.d(TAG, "getOrderNonShop: " + contents);
                Const.longInfo("getOrderNonShop", contents);
            } catch (EksysNetworkException e) {
                Log.e(TAG, "getOrderNonShop Error(" + e.getErrorCode() + "): " + e.toString());
            } finally {
                nm.close();
            }
        }
        return result;
    }

    public class API_10 extends AsyncTask<String, String, String> {
        DataLogin dataLogin;
        IF_10 callback;
        int i;

        public API_10(DataLogin dataLogin, int i, IF_10 callback) {
            this.dataLogin = dataLogin;
            this.callback = callback;
            this.i = i;
        }

        @Override
        protected String doInBackground(String... strings) {
            String s = call_10(dataLogin);
            return s;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            DownList routeList = new Gson().fromJson(s, DownList.class);
            try {
                if (routeList.getRESULT() == 0) {
                    PrefManager prefManager = new PrefManager(context);
                    prefManager.setCheckDownLoad(s);
                    String time = routeList.getLIST().get(i).getV2();
                    String url = routeList.getLIST().get(i).getV3();
                    String time_saved = prefManager.getTimeSaveDb(i);
                    if (!time.equals(time_saved)) {
                        String filename = "";
                        if (i == 0)
                            filename = Const.DATABASE_SQL;
                        else if (i == 1)
                            filename = Const.DATABASE_SQL_2;
                        doDownload(url, filename, time, i, callback);
                        Log.d(TAG, "Download");
                    } else {
                        callback.onSuccess(s);
//                    checkdown = 1;
                        Log.d(TAG, "dont Download");
                    }
                } else {
                    callback.onFail();
                }
            } catch (Exception e) {
                callback.onFail();
            }
        }
    }

    public String call_10(DataLogin dataLogin) {
        String contents = "";
        PrefManager prefManager = new PrefManager(context);
        String idLogin = dataLogin.getUSERID() + "";
        if ((Const.checkInternetConnection(context)
                && Const.PHONENUMBER.length() > 0
                && idLogin.trim().length() > 0
                && dataLogin.getDEPTCD().trim().length() > 0
                && dataLogin.getJOBCODE().trim().length() > 0)
                || (Const.checkInternetConnection(context) && dataLogin.getJOBGRADE().trim().equals(Const.SE))) {
            NetworkManager nm = new NetworkManager(Const.IP, Const.PORT);
            nm.setTerminalInfo(Const.PHONENUMBER, idLogin, dataLogin.getDEPTCD(), dataLogin.getJOBCODE());
            int checkdown = 0;
            try {
                nm.setTR('A', Const.DownloadList);
                nm.connect(10);// set timeout
                DownData ob = new DownData("1");
                String data = new Gson().toJson(ob);
                Log.d(TAG, "DownData:" + data);
                Const.WriteFileLog(dataLogin.getUSERID(), Const.DownloadList, data);
                nm.send(data);
                contents = nm.receive();
//                DownList routeList = new Gson().fromJson(contents, DownList.class);
//                if (routeList.getRESULT() == 0) {
//                    prefManager.setCheckDownLoad(contents);
//                    for (int i = 0; i < routeList.getLIST().size(); i++) {
//                        String time = routeList.getLIST().get(i).getV2();
//                        String url = routeList.getLIST().get(i).getV3();
//                        String time_saved = prefManager.getTimeSaveDb(i);
//                        if (!time.equals(time_saved)) {
//                            String filename = "";
//                            if (i == 0)
//                                filename = Const.DATABASE_SQL;
//                            else if (i == 1)
//                                filename = Const.DATABASE_SQL_2;
//                            doDownload(url, filename, time, i, callback);
//                            Log.d(TAG, "Download");
//                        } else {
//                            checkdown = 1;
//                            Log.d(TAG, "dont Download");
//                        }
//                    }

//                } else {
//                    Log.e(TAG, "checkDownLoad Fail");
//                }
                Log.d(TAG, "checkDownLoad: " + contents);
//            Const.longInfo("getRoute",contents);
            } catch (EksysNetworkException e) {
                Log.e(TAG, "checkDownLoad Error(" + e.getErrorCode() + "): " + e.toString());
            } finally {
                nm.close();
            }
        }
        return contents;
    }

    void doDownload(String mUrl, final String fileName, final String time, final int i, final IF_10 callback) {
        Log.e(TAG, mUrl);
//        String mUrl = "http://192.168.38.249/SALDB.DB.qlz";
        InputStreamVolleyRequest request = new InputStreamVolleyRequest(Request.Method.GET, mUrl,
                new Response.Listener<byte[]>() {
                    @Override
                    public void onResponse(byte[] response) {
                        if (response != null) {
                            QuickLZ quickLZ = new QuickLZ();
                            try {
                                byte[] b = quickLZ.decompress(response);
                                if (b != null) {
                                    String nameDatabase = fileName;
                                    saveFile(context, nameDatabase, b, time, i, callback);
                                }
                                String pathDatabase = context.getFilesDir() + "/" + fileName;
                                File filea = new File(pathDatabase);
                                Log.d(TAG, filea.getName());
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            callback.onFail();
                            Log.e(TAG, "empty response.");
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO handle the error
                error.printStackTrace();
            }
        }, null);
        RequestQueue mRequestQueue = Volley.newRequestQueue(context, new HurlStack());
        mRequestQueue.add(request);
    }

    public void saveFile(Context context, String fileName, byte[] response, String time, int i, IF_10 callback) {
        try {

//            String root = Environment.getExternalStorageDirectory().toString();
//            File myDir = new File(root);
//            myDir.mkdirs();
//            File file = new File(myDir, fileName);
//            FileOutputStream outputStream = new FileOutputStream(file);

            FileOutputStream outputStream;
            outputStream = context.openFileOutput(fileName, Context.MODE_PRIVATE);

            outputStream.write(response);
            outputStream.close();
//            Toast.makeText(context, "Download complete.", Toast.LENGTH_LONG).show();
            new PrefManager(context).setTimeSaveDb(time, i);
            Log.d(TAG, "Download complete");
            callback.onSuccess("");
        } catch (Exception e) {
            callback.onFail();
            Log.e("KEY_ERROR", "UNABLE TO DOWNLOAD FILE");
            e.printStackTrace();
        }
    }

    public class API_117 extends AsyncTask<String, String, String> {
        DataLogin dataLogin;
        String json;
        IF_117 callback;

        public API_117(DataLogin dataLogin, String json, IF_117 callback) {
            this.dataLogin = dataLogin;
            this.json = json;
            this.callback = callback;
        }

        @Override
        protected String doInBackground(String... strings) {
            String s = call_117(dataLogin, json);
            return s;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (s.length() == 0) {
                callback.onFail();
            } else {
                Log.d(TAG, "117:" + s);
                ResultAddShop summarySales_api = new Gson().fromJson(s, ResultAddShop.class);
                if (summarySales_api.getRESULT() == 0) {
                    callback.onSuccess(summarySales_api.getCUSTCD());
                } else {
                    callback.onFail();
                }
            }
        }
    }

    public String call_117(DataLogin dataLogin, String json) {
        Log.d(TAG, "params:" + json);
//        String result = Const.FAIL;
        String contents = "";
        String idLogin = dataLogin.getUSERID() + "";
        if ((Const.checkInternetConnection(context)
                && Const.PHONENUMBER.length() > 0
                && idLogin.trim().length() > 0
                && dataLogin.getDEPTCD().trim().length() > 0
                && dataLogin.getJOBCODE().trim().length() > 0)
                || (Const.checkInternetConnection(context) && dataLogin.getJOBGRADE().trim().equals(Const.SE))) {
            NetworkManager nm = new NetworkManager(Const.IP, Const.PORT);
            nm.setTerminalInfo(Const.PHONENUMBER, idLogin, dataLogin.getDEPTCD(), dataLogin.getJOBCODE());
            try {
                nm.setTR('A', Const.NewShop);
                nm.connect(10);    // set timeout
                Const.WriteFileLog(dataLogin.getUSERID(), Const.NewShop, json);
                nm.send(json);// send body
                contents = nm.receive();
//                Log.d(TAG, "contents:" + contents);
//                ResultAddShop summarySales_api = new Gson().fromJson(contents, ResultAddShop.class);
//                if (summarySales_api.getRESULT() == 0)
//                    result = Const.OK;
            } catch (EksysNetworkException e) {
//                contents = "Error(" + e.getErrorCode() + ")";
                Log.e(TAG, "Error(" + e.getErrorCode() + "): " + e.toString());
            } finally {
                nm.close();
            }
        }
        return contents;
    }

    public class API_1000 extends AsyncTask<String, String, String> {
        DataLogin dataLogin;
        String s;
        IF_100 callback;
        String timer;

        public API_1000(DataLogin dataLogin, String s, IF_100 callback, String timer) {
            this.dataLogin = dataLogin;
            this.s = s;
            this.callback = callback;
            this.timer = timer;
        }

        @Override
        protected String doInBackground(String... strings) {
            String str = call_1000(dataLogin, s, timer);
            return str;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (s.equals(Const.OK))
                callback.onSuccess();
            else callback.onFail();
        }
    }

    public String call_1000(DataLogin dataLogin, String s, String timer) {
//        Log.d(TAG,"data_1000:"+s);
        String result = Const.FAIL;
        String idLogin = dataLogin.getUSERID() + "";
        if ((Const.checkInternetConnection(context)
                && Const.PHONENUMBER.length() > 0
                && idLogin.trim().length() > 0
                && dataLogin.getDEPTCD().trim().length() > 0
                && dataLogin.getJOBCODE().trim().length() > 0)
                || (Const.checkInternetConnection(context) && dataLogin.getJOBGRADE().trim().equals(Const.SE))) {
            String contents = "";

            NetworkManager nm = new NetworkManager(Const.IP, Const.PORT);
            nm.setTerminalInfo(Const.PHONENUMBER, idLogin, dataLogin.getDEPTCD(), dataLogin.getJOBCODE());
            try {
                nm.setTR('A', Const.SendPosition);
                nm.connect(10);    // set timeout
                Const.WriteFileLog_2(dataLogin.getUSERID(), Const.SendPosition, s, timer);
                nm.send(s);// send body
                contents = nm.receive();
//                Log.d(TAG, "contents:" + contents);
                PositionAPI summarySales_api = new Gson().fromJson(contents, PositionAPI.class);
                if (summarySales_api.getRESULT() == 0)
                    result = Const.OK;
            } catch (EksysNetworkException e) {
                Log.e(TAG, "Error(" + e.getErrorCode() + "): " + e.toString());
            } finally {
                nm.close();
            }
            Const.WriteFileLog_2(dataLogin.getUSERID(), Const.SendPosition, "", "RESULT length:" + contents.length());
        }
        return result;
    }

    public class API_111 extends AsyncTask<String, String, String> {
        DataLogin dataLogin;
        String USE_DATE;
        String KEY_ROUTE;
        IF_111 callback;

        public API_111(DataLogin dataLogin, String USE_DATE, String KEY_ROUTE, IF_111 callback) {
            this.dataLogin = dataLogin;
            this.USE_DATE = USE_DATE;
            this.KEY_ROUTE = KEY_ROUTE;
            this.callback = callback;
        }

        @Override
        protected String doInBackground(String... strings) {
            String S = call_111(dataLogin, USE_DATE, KEY_ROUTE);
            return S;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (s.length() == 0) {
                callback.onFail();
            } else {
                RouteScheduleList routeList = new Gson().fromJson(s, RouteScheduleList.class);
                if (routeList.getRESULT() == 0) {
                    callback.onSuccess(s);
                } else {
                    callback.onFail();
                    Log.e(TAG, "getRouteSchedule Fail");
                }
            }

        }
    }

    public String call_111(DataLogin dataLogin, String USE_DATE, String KEY_ROUTE) {
        String contents = "";
        String idLogin = dataLogin.getUSERID() + "";
        if ((Const.checkInternetConnection(context)
                && Const.PHONENUMBER.length() > 0
                && idLogin.trim().length() > 0
                && dataLogin.getDEPTCD().trim().length() > 0
                && dataLogin.getJOBCODE().trim().length() > 0)
                || (Const.checkInternetConnection(context) && dataLogin.getJOBGRADE().trim().equals(Const.SE))) {
            NetworkManager nm = new NetworkManager(Const.IP, Const.PORT);
            nm.setTerminalInfo(Const.PHONENUMBER, idLogin, dataLogin.getDEPTCD(), dataLogin.getJOBCODE());
            try {
                nm.setTR('A', Const.getRouteScheduleShop);
                nm.connect(10);// set timeout
                Map<String, Object> map = new HashMap<>();
                map.put("DATE", USE_DATE);
                map.put("WKDAY", KEY_ROUTE);
                String data = new Gson().toJson(map);
                Log.d(TAG, "API 111 send:" + data);
                Const.WriteFileLog(dataLogin.getUSERID(), Const.getRouteScheduleShop, data);
                nm.send(data);
                contents = nm.receive();

//                RouteScheduleList routeList = new Gson().fromJson(contents, RouteScheduleList.class);
//                if (routeList.getRESULT() == 0) {
//                    ListInfor.clear();
//                    ListInfor = routeList.getLIST();
//                    if (api_111List != null && api_111List.size() > 0)
//                        db.delete_All_API_111();
//                    API_111 offlineObject = new API_111(USE_DATE, contents, "" + KEY_ROUTE);
//                    db.addContact_111(offlineObject);
//                } else {
//                    Log.e(TAG, "getRouteSchedule Fail");
//                }

                Log.d(TAG, "API 111: " + contents);
//            Const.longInfo("getRoute",contents);
            } catch (EksysNetworkException e) {
                Log.e(TAG, "API 111 Error(" + e.getErrorCode() + "): " + e.toString());
            } finally {
                nm.close();
            }
        }
        return contents;
    }

    public class API_2 extends AsyncTask<String, String, String> {
        DataLogin dataLogin;
        IF_2 callback;

        public API_2(DataLogin dataLogin, IF_2 callback) {
            this.dataLogin = dataLogin;
            this.callback = callback;
        }

        @Override
        protected String doInBackground(String... strings) {
            String S = call_2(dataLogin);
            return S;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            callback.onSuccess(s);

        }
    }


    public String call_2(DataLogin dataLogin) {
        String contents = "";


        String idLogin = dataLogin.getUSERID() + "";
        if ((Const.checkInternetConnection(context)
                && Const.PHONENUMBER.length() > 0
                && idLogin.trim().length() > 0
                && dataLogin.getDEPTCD().trim().length() > 0
                && dataLogin.getJOBCODE().trim().length() > 0)
                || (Const.checkInternetConnection(context) && dataLogin.getJOBGRADE().trim().equals(Const.SE))) {
            NetworkManager nm = new NetworkManager(Const.IP, Const.PORT);
            nm.setTerminalInfo(Const.PHONENUMBER, idLogin, dataLogin.getDEPTCD(), dataLogin.getJOBCODE());
            try {
                nm.setTR('A', Const.API_2);
                nm.connect(10);// set timeout
                Const.WriteFileLog(dataLogin.getUSERID(), Const.API_2, "");
                nm.send("");
                contents = nm.receive();
                Log.d(TAG, "API_2: " + contents);
//                Const.longInfo("getRoute", contents);
            } catch (EksysNetworkException e) {
                Log.e(TAG, "API_2 Error(" + e.getErrorCode() + "): " + e.toString());
            } finally {
                nm.close();
            }
        }
        return contents;
    }


    public class API_125 extends AsyncTask<String, String, String> {
        DataLogin dataLogin;
        String json;
        IF_124 callback;

        public API_125(DataLogin dataLogin, String json, IF_124 callback) {
            this.dataLogin = dataLogin;
            this.json = json;
            this.callback = callback;
        }

        @Override
        protected String doInBackground(String... strings) {
            String s = call_125(dataLogin, json);
            return s;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (s.equals(Const.OK))
                callback.onSuccess();
            else callback.onFail();
        }
    }

    public String call_125(DataLogin dataLogin, String json) {
        Log.d(TAG, "125: " + json);
        String result = Const.FAIL;
        String idLogin = dataLogin.getUSERID() + "";
        if ((Const.checkInternetConnection(context)
                && Const.PHONENUMBER.length() > 0
                && idLogin.trim().length() > 0
                && dataLogin.getDEPTCD().trim().length() > 0
                && dataLogin.getJOBCODE().trim().length() > 0)
                || (Const.checkInternetConnection(context) && dataLogin.getJOBGRADE().trim().equals(Const.SE))) {
            NetworkManager nm = new NetworkManager(Const.IP, Const.PORT);
            nm.setTerminalInfo(Const.PHONENUMBER, idLogin, dataLogin.getDEPTCD(), dataLogin.getJOBCODE());
            String contents = "";
            try {
                nm.setTR('A', Const.InputHistoryOrder);
                nm.connect(10);          // set timeout
                Const.WriteFileLog(dataLogin.getUSERID(), Const.InputHistoryOrder, json);
                nm.send(json);// send body
                contents = nm.receive();
                RESULTOBJECT resultobject = new Gson().fromJson(contents, RESULTOBJECT.class);
                if (resultobject.getRESULT() == 0) {
                    result = Const.OK;
                } else {
                }
                Log.d(TAG, "125: " + contents);
            } catch (EksysNetworkException e) {
                Log.e(TAG, "125 Error(" + e.getErrorCode() + "): " + e.toString());
            } finally {
                nm.close();
            }
        }
        return result;
    }

    public class API_126 extends AsyncTask<String, String, String> {
        DataLogin dataLogin;
        String json;
        IF_124 callback;

        public API_126(DataLogin dataLogin, String json, IF_124 callback) {
            this.dataLogin = dataLogin;
            this.json = json;
            this.callback = callback;
        }

        @Override
        protected String doInBackground(String... strings) {
            String s = call_126(dataLogin, json);
            return s;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (s.equals(Const.OK))
                callback.onSuccess();
            else callback.onFail();
        }
    }

    public String call_126(DataLogin dataLogin, String json) {
        Log.d(TAG, "126: " + json);
        String result = Const.FAIL;
        String idLogin = dataLogin.getUSERID() + "";
        if ((Const.checkInternetConnection(context)
                && Const.PHONENUMBER.length() > 0
                && idLogin.trim().length() > 0
                && dataLogin.getDEPTCD().trim().length() > 0
                && dataLogin.getJOBCODE().trim().length() > 0)
                || (Const.checkInternetConnection(context) && dataLogin.getJOBGRADE().trim().equals(Const.SE))) {
            NetworkManager nm = new NetworkManager(Const.IP, Const.PORT);
            nm.setTerminalInfo(Const.PHONENUMBER, idLogin, dataLogin.getDEPTCD(), dataLogin.getJOBCODE());
            String contents = "";
            try {
                nm.setTR('A', Const.ResetPromotionHistory);
                nm.connect(10);          // set timeout
                Const.WriteFileLog(dataLogin.getUSERID(), Const.ResetPromotionHistory, json);
                nm.send(json);// send body
                contents = nm.receive();
                RESULTOBJECT resultobject = new Gson().fromJson(contents, RESULTOBJECT.class);
                if (resultobject.getRESULT() == 0 || resultobject.getRESULT() == 1) {
                    result = Const.OK;
                } else {
                }
                Log.d(TAG, "126: " + contents);
            } catch (EksysNetworkException e) {
                Log.e(TAG, "126 Error(" + e.getErrorCode() + "): " + e.toString());
            } finally {
                nm.close();
            }
        }
        return result;
    }

    public class API_4 extends AsyncTask<String, String, String> {
        DataLogin dataLogin;
        IF_2 callback;

        public API_4(DataLogin dataLogin, IF_2 callback) {
            this.dataLogin = dataLogin;
            this.callback = callback;
        }

        @Override
        protected String doInBackground(String... strings) {
            String S = call_4(dataLogin);
            return S;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            callback.onSuccess(s);
            if (s != null && s.length() > 0) {
                Log.d(TAG, "A0004:" + s);
                ListObjA0004 ob = new Gson().fromJson(s, ListObjA0004.class);
                DatabaseHandler db = new DatabaseHandler(context);
                if (ob.getRESULT() == 0) {
                    List<ObjA0004> list = ob.getLIST();
                    if (list != null && list.size() > 0) {
                        int newMsg = list.size();
                        int oldMsg = 0;
                        MainActivity.instance.showTvMsg("" + list.size());


                        List<A0004> getTABLE_A0004 = db.getTABLE_A0004();
                        if (getTABLE_A0004 != null && getTABLE_A0004.size() > 0) {
                            for (A0004 A4 : getTABLE_A0004) {
                                if (A4.getISREAD().equals("0"))
                                    oldMsg++;
                            }
//                            oldMsg = getTABLE_A0004.size();
                            if (oldMsg < newMsg) {
                                int remaning = newMsg - oldMsg;
                                for (int i = remaning - 1; i >= 0; i--) {
                                    ObjA0004 obj = list.get(i);
                                    A0004 a0004 = new A0004(obj.getV1(), obj.getV2(), obj.getV3(), obj.getV4(), obj.getV5(), "0", "0", Const.getToday());
                                    db.ADD_TABLE_A0004(a0004);
                                }
                            }
                        } else {
                            for (int i = list.size() - 1; i >= 0; i--) {
                                ObjA0004 obj = list.get(i);
                                A0004 a0004 = new A0004(obj.getV1(), obj.getV2(), obj.getV3(), obj.getV4(), obj.getV5(), "0", "0", Const.getToday());
                                db.ADD_TABLE_A0004(a0004);

                            }
                        }

//                        db.DELETE_TABLE_A0004();
//                        A0004 a0004 = new A0004(s);
//                        db.ADD_TABLE_A0004(a0004);
                        Log.d(TAG, "newMsg:" + newMsg + " oldMsg:" + oldMsg);
                        if (newMsg > oldMsg) {
                            // show notify
                            Vibrator v = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
                            v.vibrate(700);

                            PendingIntent contentIntent;
                            Intent intent = new Intent(context, MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            contentIntent = PendingIntent.getActivity(context, 0, intent, 0);

                            NotificationManager mNotificationManager = (NotificationManager)
                                    context.getSystemService(Context.NOTIFICATION_SERVICE);
                            Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(),
                                    R.drawable.icon_main);

                            NotificationCompat.Builder mBuilder =
                                    new NotificationCompat.Builder(context)
                                            .setSmallIcon(R.drawable.icon_main)
                                            .setLargeIcon(bitmap)
                                            .setContentTitle("SNSV Message")
                                            .setStyle(new NotificationCompat.BigTextStyle()
                                                    .bigText(context.getResources().getString(R.string.newMsg)))
                                            .setContentText(context.getResources().getString(R.string.newMsg))
                                            .setNumber(newMsg);
                            mBuilder.setAutoCancel(true);
                            mBuilder.setDefaults(Notification.DEFAULT_SOUND);
                            mBuilder.setContentIntent(contentIntent);
                            Random random = new Random();
                            int m = random.nextInt(9999 - 1000) + 1000;
                            mNotificationManager.notify(m, mBuilder.build());
                        }
                    } else {
//                        db.DELETE_TABLE_A0004();
//                        noData();
                        MainActivity.instance.hideTvMsg();
                    }
                } else {
//                    db.DELETE_TABLE_A0004();
                    MainActivity.instance.hideTvMsg();
//                    noData();
                }
            } else {
//                progressbar.setVisibility(View.GONE);
            }
        }
    }


    public String call_4(DataLogin dataLogin) {
        String contents = "";
        String idLogin = dataLogin.getUSERID() + "";
        if ((Const.checkInternetConnection(context)
                && Const.PHONENUMBER.length() > 0
                && idLogin.trim().length() > 0
                && dataLogin.getDEPTCD().trim().length() > 0
                && dataLogin.getJOBCODE().trim().length() > 0)
                || (Const.checkInternetConnection(context) && dataLogin.getJOBGRADE().trim().equals(Const.SE))) {
            NetworkManager nm = new NetworkManager(Const.IP, Const.PORT);
            nm.setTerminalInfo(Const.PHONENUMBER, idLogin, dataLogin.getDEPTCD(), dataLogin.getJOBCODE());

            if (Const.checkInternetConnection(context)) {
                try {
                    nm.setTR('A', Const.A0004);
                    nm.connect(10);// set timeout
                    Const.WriteFileLog(dataLogin.getUSERID(), Const.A0004, "");
                    nm.send("");
                    contents = nm.receive();
//                Log.d(TAG, "API_4: " + contents);
//                Const.longInfo("getRoute", contents);
                } catch (EksysNetworkException e) {
                    Log.e(TAG, "API_4 Error(" + e.getErrorCode() + "): " + e.toString());
                } finally {
                    nm.close();
                }
            }
        }
        return contents;
    }

    public class API_6 extends AsyncTask<String, String, String> {
        DataLogin dataLogin;
        String EMPID;
        String MESSAGE;
        String AREACD;
        String BRANCHCD;
        IF_2 callback;

        public API_6(DataLogin dataLogin, String EMPID, String MESSAGE, String AREACD, String BRANCHCD, IF_2 callback) {
            this.dataLogin = dataLogin;
            this.EMPID = EMPID;
            this.MESSAGE = MESSAGE;
            this.AREACD = AREACD;
            this.BRANCHCD = BRANCHCD;
            this.callback = callback;
        }

        @Override
        protected String doInBackground(String... strings) {
            String S = call_6(dataLogin, EMPID, MESSAGE, AREACD, BRANCHCD);
            return S;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            callback.onSuccess(s);
        }
    }


    public String call_6(DataLogin dataLogin, String EMPID, String MESSAGE, String AREACD, String BRANCHCD) {
        String contents = "";
        String idLogin = dataLogin.getUSERID() + "";
        if ((Const.checkInternetConnection(context)
                && Const.PHONENUMBER.length() > 0
                && idLogin.trim().length() > 0
                && dataLogin.getDEPTCD().trim().length() > 0
                && dataLogin.getJOBCODE().trim().length() > 0)
                || (Const.checkInternetConnection(context) && dataLogin.getJOBGRADE().trim().equals(Const.SE))) {
            NetworkManager nm = new NetworkManager(Const.IP, Const.PORT);
            nm.setTerminalInfo(Const.PHONENUMBER, idLogin, dataLogin.getDEPTCD(), dataLogin.getJOBCODE());
            try {
                nm.setTR('A', Const.A0006);
                nm.connect(10);// set timeout
                Map<String, Object> map = new HashMap<>();
                map.put("EMPID", EMPID);
                map.put("MESSAGE", MESSAGE);
                map.put("AREACD", AREACD);
                map.put("BRANCHCD", BRANCHCD);
                String data = new Gson().toJson(map);
                Log.d(TAG, "API_6" + data);
                Const.WriteFileLog(dataLogin.getUSERID(), Const.A0006, data);
                nm.send(data);
                contents = nm.receive();
                Log.d(TAG, "API_6: " + contents);
//                Const.longInfo("getRoute", contents);
            } catch (EksysNetworkException e) {
                Log.e(TAG, "API_6 Error(" + e.getErrorCode() + "): " + e.toString());
            } finally {
                nm.close();
            }
        }
        return contents;
    }

    public class API_7 extends AsyncTask<String, String, String> {
        DataLogin dataLogin;
        String ADMINCODE;
        String LON;
        String LAT;
        IF_7 callback;

        public API_7(DataLogin dataLogin, String ADMINCODE, String LON, String LAT, IF_7 callback) {
            this.dataLogin = dataLogin;
            this.ADMINCODE = ADMINCODE;
            this.LON = LON;
            this.LAT = LAT;
            this.callback = callback;
        }

        @Override
        protected String doInBackground(String... strings) {
            String s = call_7(dataLogin, ADMINCODE, LON, LAT);
            return s;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d(TAG, "data7:" + s);


            // for test data
//            List<NearShop>lst=new ArrayList<>();
//            for(int i=0;i<10;i++)
//            {
//                NearShop obj=new NearShop();
//                obj.setV1("S11111");
//                obj.setV2("Huy");
//                obj.setV3("38373830");
//                obj.setV4("3846246");
//                obj.setV5("D7/6E, QL 1A, T창n T첬c, B챙nh Ch찼nh, Tp. H沼?Ch챠 Minh");
//                obj.setV6("30000");
//                lst.add(obj);
//            }
//            ListNearShop nearShop = new ListNearShop();
//            nearShop.setRESULT(0);
//            nearShop.setLIST(lst);
//            s=new Gson().toJson(nearShop);

            if (s.length() == 0) {
                callback.onNoInternet();
            } else {
                if (s.startsWith("Error")) {
                    callback.onError(s);
                } else {
                    ListNearShop obj = new Gson().fromJson(s, ListNearShop.class);
                    int RESULT = obj.getRESULT();
                    if (RESULT == 0) {
                        callback.onSuccess(obj.getLIST());
                    } else {
                        callback.onFail();
                    }
                }
            }
        }
    }

    public String call_7(DataLogin dataLogin, String ADMINCODE, String LON, String LAT) {
        String contents = "";
        String idLogin = dataLogin.getUSERID() + "";
        if ((Const.checkInternetConnection(context)
                && Const.PHONENUMBER.length() > 0
                && idLogin.trim().length() > 0
                && dataLogin.getDEPTCD().trim().length() > 0
                && dataLogin.getJOBCODE().trim().length() > 0)
                || (Const.checkInternetConnection(context) && dataLogin.getJOBGRADE().trim().equals(Const.SE))) {
            NetworkManager nm = new NetworkManager(Const.IP, Const.PORT);
            nm.setTerminalInfo(Const.PHONENUMBER, idLogin, dataLogin.getDEPTCD(), dataLogin.getJOBCODE());
            try {
                nm.setTR('A', Const.A0007);
                nm.connect(10);// set timeout
                Map<String, Object> map = new HashMap<>();
                map.put("ADMINCODE", ADMINCODE);
                map.put("LON", LON);
                map.put("LAT", LAT);

                String data = new Gson().toJson(map);
                Log.d(TAG, "API_7" + data);
                Const.WriteFileLog(dataLogin.getUSERID(), Const.A0007, data);
                nm.send(data);
                contents = nm.receive();
                Const.longInfo(TAG, "API_7: " + contents);
            } catch (EksysNetworkException e) {
                contents = "Error(" + e.getErrorCode() + "): " + e.toString();
                Log.e(TAG, "API_7 Error(" + e.getErrorCode() + "): " + e.toString());
            } finally {
                nm.close();
            }
        }
        return contents;
    }

    public class API_3 extends AsyncTask<String, String, String> {
        DataLogin dataLogin;
        IF_2 callback;

        public API_3(DataLogin dataLogin, IF_2 callback) {
            this.dataLogin = dataLogin;
            this.callback = callback;
        }

        @Override
        protected String doInBackground(String... strings) {
            String S = call_3(dataLogin);
            return S;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            callback.onSuccess(s);

        }
    }


    public String call_3(DataLogin dataLogin) {
        String contents = "";

        String idLogin = dataLogin.getUSERID() + "";
        if ((Const.checkInternetConnection(context)
                && Const.PHONENUMBER.length() > 0
                && idLogin.trim().length() > 0
                && dataLogin.getDEPTCD().trim().length() > 0
                && dataLogin.getJOBCODE().trim().length() > 0)
                || (Const.checkInternetConnection(context) && dataLogin.getJOBGRADE().trim().equals(Const.SE))) {
            NetworkManager nm = new NetworkManager(Const.IP, Const.PORT);
            nm.setTerminalInfo(Const.PHONENUMBER, idLogin, dataLogin.getDEPTCD(), dataLogin.getJOBCODE());
            try {
                nm.setTR('A', Const.A0003);
                nm.connect(10);// set timeout
                Const.WriteFileLog(dataLogin.getUSERID(), Const.A0003, "");
                nm.send("");
                contents = nm.receive();
                Log.d(TAG, "API_3: " + contents);
//                Const.longInfo("getRoute", contents);
            } catch (EksysNetworkException e) {
                Log.e(TAG, "API_3 Error(" + e.getErrorCode() + "): " + e.toString());
            } finally {
                nm.close();
            }
        }
        return contents;
    }

    public class API_5 extends AsyncTask<String, String, String> {
        DataLogin dataLogin;
        String CDATE;
        String CTIME;
        String AUTHORID;
        IF_2 callback;

        public API_5(DataLogin dataLogin, String CDATE, String CTIME, String AUTHORID, IF_2 callback) {
            this.dataLogin = dataLogin;
            this.CDATE = CDATE;
            this.CTIME = CTIME;
            this.AUTHORID = AUTHORID;
            this.callback = callback;
        }

        @Override
        protected String doInBackground(String... strings) {
            String S = call_5(dataLogin, CDATE, CTIME, AUTHORID);
            return S;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            callback.onSuccess(s);

        }
    }


    public String call_5(DataLogin dataLogin, String CDATE, String CTIME, String AUTHORID) {
        String contents = "";
        String idLogin = dataLogin.getUSERID() + "";
        if ((Const.checkInternetConnection(context)
                && Const.PHONENUMBER.length() > 0
                && idLogin.trim().length() > 0
                && dataLogin.getDEPTCD().trim().length() > 0
                && dataLogin.getJOBCODE().trim().length() > 0)
                || (Const.checkInternetConnection(context) && dataLogin.getJOBGRADE().trim().equals(Const.SE))) {
            NetworkManager nm = new NetworkManager(Const.IP, Const.PORT);
            nm.setTerminalInfo(Const.PHONENUMBER, idLogin, dataLogin.getDEPTCD(), dataLogin.getJOBCODE());
            try {
                nm.setTR('A', Const.A0005);
                nm.connect(10);// set timeout
                Map<String, String> map = new HashMap<>();
                map.put("CDATE", CDATE);
                map.put("CTIME", CTIME);
                map.put("AUTHORID", AUTHORID);
                String data = new Gson().toJson(map);
                Log.d(TAG, "DATA_5:" + data);
                Const.WriteFileLog(dataLogin.getUSERID(), Const.A0005, data);
                nm.send(data);
                contents = nm.receive();
                Log.d(TAG, "API_5: " + contents);
//                Const.longInfo("getRoute", contents);
            } catch (EksysNetworkException e) {
                Log.e(TAG, "API_5 Error(" + e.getErrorCode() + "): " + e.toString());
            } finally {
                nm.close();
            }
        }
        return contents;
    }

    public void doJsonObjectRequest(final String userId, final String branchCd, final OrionFamily callback) {
//        Log.d(TAG, "doJsonObjectRequest");
//        final String TAG = "doJsonObjectRequest";
        String url = Const.rootURL + Const.ORION_FAMILY;
        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        map.put("branchCd", branchCd);
        JSONObject bodyParam = new JSONObject(map);
        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, bodyParam, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                if (response != null) {
                    Log.d(TAG, response.toString());
                    String jsonArray = "";
                    List<CpnInfo_ListT> list = new ArrayList<>();
                    try {
                        jsonArray = response.getString("d");
                        if (jsonArray.startsWith("[")) {
                            Type listType = new TypeToken<List<CpnInfo_ListT>>() {
                            }.getType();
                            list = new Gson().fromJson(jsonArray, listType);
                        }


                    } catch (JSONException e) {
                        list = new ArrayList<>();
                        e.printStackTrace();
                    }
                    callback.onSuccess(list);
//                    Log.d(TAG, "jsonArray:"+new Gson().toJson(list));

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.onFail();
                Log.d(TAG, "VolleyError");
            }
        });

        MyApplication.getInstance().addToRequestQueue(jsonObjectRequest);
    }

    public class A0127 extends AsyncTask<String, String, String> {
        DataLogin dataLogin;
        String CDATE;
        String CUSTCD;
        IF_127 callback;

        public A0127(DataLogin dataLogin, String CDATE, String CUSTCD, IF_127 callback) {
            this.dataLogin = dataLogin;
            this.CDATE = CDATE;
            this.CUSTCD = CUSTCD;
            this.callback = callback;
        }

        @Override
        protected String doInBackground(String... strings) {
            String s = call_A0127(dataLogin, CDATE, CUSTCD);
            return s;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if (s == null || s.length() == 0) {
                callback.onFail();
            } else {
                if (s.startsWith("Error")) {
                    callback.onError(s);
                } else {
                    ListA0127 list = new Gson().fromJson(s, ListA0127.class);
                    if (list.getRESULT() == 0) {
                        if (list.getLIST() != null && list.getLIST().size() > 0) {
                            callback.onSuccess(list.getLIST());
                        } else {
                            callback.onFail();
                        }
                    } else {
                        callback.onFail();
                    }
                }


            }
        }
    }

    public String call_A0127(DataLogin dataLogin, String CDATE, String CUSTCD) {
        String contents = "";
        String idLogin = dataLogin.getUSERID() + "";
        if ((Const.checkInternetConnection(context)
                && Const.PHONENUMBER.length() > 0
                && idLogin.trim().length() > 0
                && dataLogin.getDEPTCD().trim().length() > 0
                && dataLogin.getJOBCODE().trim().length() > 0)
                || (Const.checkInternetConnection(context) && dataLogin.getJOBGRADE().trim().equals(Const.SE))) {
            NetworkManager nm = new NetworkManager(Const.IP, Const.PORT);
            nm.setTerminalInfo(Const.PHONENUMBER, idLogin, dataLogin.getDEPTCD(), dataLogin.getJOBCODE());
            try {
                nm.setTR('A', Const.A0127);
                nm.connect(10);// set timeout
                Map<String, String> map = new HashMap<>();
                map.put("CDATE", CDATE);
                map.put("CUSTCD", CUSTCD);
                String data = new Gson().toJson(map);
                Log.d(TAG, "A0127:" + data);
                Const.WriteFileLog(dataLogin.getUSERID(), Const.A0127, data);
                nm.send(data);
                contents = nm.receive();
                Log.d(TAG, "A0127: " + contents);
            } catch (EksysNetworkException e) {
                contents = "Error(" + e.getErrorCode() + "): " + e.toString();
                Log.e(TAG, "A0127 Error(" + e.getErrorCode() + "): " + e.toString());
            } finally {
                nm.close();
            }
        }
        return contents;
    }

    public class A0128 extends AsyncTask<String, String, String> {
        DataLogin dataLogin;
        String CDATE;
        IF_128 callback;

        public A0128(DataLogin dataLogin, String CDATE, IF_128 callback) {
            this.dataLogin = dataLogin;
            this.CDATE = CDATE;
            this.callback = callback;
        }

        @Override
        protected String doInBackground(String... strings) {
            String s = call_A0128(dataLogin, CDATE);
            return s;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            DatabaseHandler db = new DatabaseHandler(context);
            List<DataBase128> list128 = db.getList128();
            if (s.length() > 0) {
                ListA1028 getApi = new Gson().fromJson(s, ListA1028.class);
                int RESULT = getApi.getRESULT();
                if (RESULT == 0) {
                    if (getApi.getLIST() != null) {
                        if (getApi.getLIST().size() > 0) {
                            List<ObjectA0128> LIST = getApi.getLIST();
                            if (list128 != null && list128.size() > 0) {
                                for (ObjectA0128 obj : LIST) {
                                    boolean flag = true;
                                    for (DataBase128 obj128 : list128) {
                                        if (obj128.getCDATE().equals(CDATE) && obj128.getCUSTCD_128().equals(obj.getV1())) {
                                            // update
                                            flag = false;
                                            db.update_128_Order(obj.getV2(), obj.getV3(), obj128.getId());
                                            break;
                                        }
                                    }
                                    if (flag) {
                                        // add data
                                        DataBase128 ob = new DataBase128(obj.getV1(), obj.getV2(), obj.getV3(), CDATE);
                                        db.add_128(ob);
                                    }
                                }
                            } else {
                                // add data

                                for (ObjectA0128 obj : LIST) {
                                    DataBase128 ob = new DataBase128(obj.getV1(), obj.getV2(), obj.getV3(), CDATE);
                                    db.add_128(ob);
                                }
                            }
                            callback.onSuccess(getApi.getLIST());
                        } else {

                            if (list128 != null && list128.size() > 0) {
                                for (DataBase128 obj : list128) {
                                    if (obj.getCDATE().equals(CDATE)) {
                                        db.DELETE_128_ID(obj.getId());
                                    }
                                }
                            }
                            callback.onFail();
                        }
                    } else {

                        if (list128 != null && list128.size() > 0) {
                            for (DataBase128 obj : list128) {
                                if (obj.getCDATE().equals(CDATE)) {
                                    db.DELETE_128_ID(obj.getId());
                                }
                            }
                        }
                        callback.onFail();
                    }
                } else if (RESULT == 1) {
                    if (list128 != null && list128.size() > 0) {
                        for (DataBase128 obj : list128) {
                            if (obj.getCDATE().equals(CDATE)) {
                                db.DELETE_128_ID(obj.getId());
                            }
                        }
                    }
                    List<ObjectA0128> LIST = new ArrayList<>();
                    callback.onSuccess(LIST);
                } else {
                    callback.onFail();
                }
            } else {
                callback.onFail();
            }
        }
    }

    public String call_A0128(DataLogin dataLogin, String CDATE) {
        String contents = "";
        String idLogin = dataLogin.getUSERID() + "";
        if ((Const.checkInternetConnection(context)
                && Const.PHONENUMBER.length() > 0
                && idLogin.trim().length() > 0
                && dataLogin.getDEPTCD().trim().length() > 0
                && dataLogin.getJOBCODE().trim().length() > 0)
                || (Const.checkInternetConnection(context) && dataLogin.getJOBGRADE().trim().equals(Const.SE))) {
            NetworkManager nm = new NetworkManager(Const.IP, Const.PORT);
            nm.setTerminalInfo(Const.PHONENUMBER, idLogin, dataLogin.getDEPTCD(), dataLogin.getJOBCODE());
            try {
                nm.setTR('A', Const.A0128);
                nm.connect(10);// set timeout
                Map<String, String> map = new HashMap<>();
                map.put("CDATE", CDATE);

                String data = new Gson().toJson(map);
                Log.d(TAG, "A0128:" + data);
                Const.WriteFileLog(dataLogin.getUSERID(), Const.A0128, data);
                nm.send(data);
                contents = nm.receive();


                Log.d(TAG, "A0128: CDATE:" + CDATE + " ->" + contents);
            } catch (EksysNetworkException e) {
                Log.e(TAG, "A0128 Error(" + e.getErrorCode() + "): " + e.toString());
            } finally {
                nm.close();
            }
        }
        return contents;
    }


    public class A0129 extends AsyncTask<String, String, String> {
        DataLogin dataLogin;
        IF_129 callback;
        String CDATE;

        public A0129(DataLogin dataLogin, IF_129 callback, String CDATE) {
            this.dataLogin = dataLogin;
            this.callback = callback;
            this.CDATE = CDATE;
        }

        @Override
        protected String doInBackground(String... strings) {
            String s = call_A0129(dataLogin, CDATE);
            return s;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (s.length() > 0) {
                DatabaseHandler db = new DatabaseHandler(context);
                List<DataBase129> lst129 = db.getList129();
                ObjectA0129 objectA0129 = new Gson().fromJson(s, ObjectA0129.class);
                int RESULT = objectA0129.getRESULT();
                if (RESULT == 0) {
                    if (lst129 != null && lst129.size() > 0) {
                        for (DataBase129 ob : lst129) {
                            if (ob.getCDATE().equals(CDATE)) {
                                db.DELETE_129_ID(ob.getId());
                            }
                        }
                    }
                    DataBase129 addDb = new DataBase129(s, CDATE);
                    db.add_129(addDb);
                    callback.onSuccess();
                } else if (RESULT == 1) {
                    if (lst129 != null && lst129.size() > 0) {
                        for (DataBase129 ob : lst129) {
                            if (ob.getCDATE().equals(CDATE)) {
                                db.DELETE_129_ID(ob.getId());
                            }
                        }
                    }
                    callback.onSuccess();
                } else {
                    callback.onFail();
                }
//
//                Const.longInfo(TAG,"getORDERLIST:"+new Gson().toJson(objectA0129.getORDERLIST()));
//                Const.longInfo(TAG,"getDISPLAYLIST:"+new Gson().toJson(objectA0129.getDISPLAYLIST()));
//                Const.longInfo(TAG,"getSTOCKLIST:"+new Gson().toJson(objectA0129.getSTOCKLIST()));
            } else {
                callback.onFail();
            }

        }
    }

    public String call_A0129(DataLogin dataLogin, String CDATE) {
        String contents = "";
        String idLogin = dataLogin.getUSERID() + "";
        if ((Const.checkInternetConnection(context)
                && Const.PHONENUMBER.length() > 0
                && idLogin.trim().length() > 0
                && dataLogin.getDEPTCD().trim().length() > 0
                && dataLogin.getJOBCODE().trim().length() > 0)
                || (Const.checkInternetConnection(context) && dataLogin.getJOBGRADE().trim().equals(Const.SE))) {
            NetworkManager nm = new NetworkManager(Const.IP, Const.PORT);
            nm.setTerminalInfo(Const.PHONENUMBER, idLogin, dataLogin.getDEPTCD(), dataLogin.getJOBCODE());
            try {
                nm.setTR('A', Const.A0129);
                nm.connect(10);// set timeout
                Map<String, String> map = new HashMap<>();
                map.put("CDATE", CDATE);

                String data = new Gson().toJson(map);
                Log.d(TAG, "A0129:" + data);
                Const.WriteFileLog(dataLogin.getUSERID(), Const.A0129, data);
                nm.send(data);
                contents = nm.receive();

//                Const.longInfo(TAG, "A0129: CDATE:" + CDATE + " ->" + contents);
//                Const.WriteFileLog(dataLogin.getUSERID(), Const.A0129, contents);
            } catch (EksysNetworkException e) {
                Log.e(TAG, "A0129 Error(" + e.getErrorCode() + "): " + e.toString());
            } finally {
                nm.close();
            }
        }
        return contents;
    }

    public class API_8 extends AsyncTask<String, String, String> {
        DataLogin dataLogin;
        String data;
        IF_SE_BRANCH callback;

        public API_8(DataLogin dataLogin, String data, IF_SE_BRANCH callback) {
            this.dataLogin = dataLogin;
            this.data = data;
            this.callback = callback;
        }

        @Override
        protected String doInBackground(String... strings) {
            String s = call_A0008(dataLogin, data);
            return s;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (s.length() > 0) {
                ListSeBranch obj = new Gson().fromJson(s, ListSeBranch.class);
                if (obj.getRESULT() == 0) {
                    List<SeBranch> lst = obj.getLIST();
                    if (lst != null && lst.size() > 0) {
                        callback.onSuccess(lst);
                    } else {
                        callback.onFail();
                    }
                } else {
                    callback.onFail();
                }
            } else {
                // timeout
                callback.onFail();
            }

        }
    }

    public String call_A0008(DataLogin dataLogin, String data) {
        String contents = "";
        String idLogin = dataLogin.getUSERID() + "";
        if ((Const.checkInternetConnection(context)
                && Const.PHONENUMBER.length() > 0
                && idLogin.trim().length() > 0
                && dataLogin.getDEPTCD().trim().length() > 0
                && dataLogin.getJOBCODE().trim().length() > 0)
                || (Const.checkInternetConnection(context) && dataLogin.getJOBGRADE().trim().equals(Const.SE))) {

            NetworkManager nm = new NetworkManager(Const.IP, Const.PORT);
            nm.setTerminalInfo(Const.PHONENUMBER, idLogin, dataLogin.getDEPTCD(), dataLogin.getJOBCODE());
            try {
                nm.setTR('A', Const.A0008);
                nm.connect(10);// set timeout

                Log.d(TAG, "A0008:" + data);
                Const.WriteFileLog(dataLogin.getUSERID(), Const.A0008, data);
                nm.send(data);
                contents = nm.receive();
                Const.longInfo(TAG, "A0008:" + contents);
            } catch (EksysNetworkException e) {
                Log.e(TAG, "A0008 Error(" + e.getErrorCode() + "): " + e.toString());
            } finally {
                nm.close();
            }
        }
        return contents;
    }

    public class API_140 extends AsyncTask<String, String, String> {
        DataLogin dataLogin;
        IF_140 callback;

        public API_140(DataLogin dataLogin, IF_140 callback) {
            this.dataLogin = dataLogin;
            this.callback = callback;
        }

        @Override
        protected String doInBackground(String... strings) {
            String s = call_A0140(dataLogin);
            return s;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d(TAG, "API_140:" + s);
            if (s.length() == 0) {
                callback.onFail();
            } else {
                ListObj140 obj = new Gson().fromJson(s, ListObj140.class);
                if (obj.getRESULT() == 0) {
                    callback.onSuccess(obj.getLIST());
                } else {
                    callback.onFail();
                }

            }
        }
    }

    public String call_A0140(DataLogin dataLogin) {
        String contents = "";
        String idLogin = dataLogin.getUSERID() + "";
        if ((Const.checkInternetConnection(context)
                && Const.PHONENUMBER.length() > 0
                && idLogin.trim().length() > 0
                && dataLogin.getDEPTCD().trim().length() > 0
                && dataLogin.getJOBCODE().trim().length() > 0)
                || (Const.checkInternetConnection(context) && dataLogin.getJOBGRADE().trim().equals(Const.SE))) {
            NetworkManager nm = new NetworkManager(Const.IP, Const.PORT);
            nm.setTerminalInfo(Const.PHONENUMBER, idLogin, dataLogin.getDEPTCD(), dataLogin.getJOBCODE());
            try {
                nm.setTR('A', Const.A0140);
                nm.connect(10);// set timeout
                nm.send("");
                contents = nm.receive();
                Const.longInfo(TAG, "A0140:" + contents);
            } catch (EksysNetworkException e) {
                Log.e(TAG, "A0140 Error(" + e.getErrorCode() + "): " + e.toString());
            } finally {
                nm.close();
            }
        }
        return contents;
    }

    public class API_114 extends AsyncTask<String, String, String> {
        DataLogin dataLogin;
        String DATE;
        IF_114 callback;

        public API_114(DataLogin dataLogin, String DATE, IF_114 callback) {
            this.dataLogin = dataLogin;
            this.DATE = DATE;
            this.callback = callback;
        }

        @Override
        protected String doInBackground(String... strings) {
            String s = call_114(dataLogin, DATE);
            return s;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (s.length() == 0) {
                callback.onFail();
            } else {
                getSMWorkingDayAPI routeList = new Gson().fromJson(s, getSMWorkingDayAPI.class);
                if (routeList.getRESULT() == 0 || routeList.getRESULT() == 1) {
                    callback.onSuccess();
                } else {
                    callback.onFail();
                }
            }
        }
    }

    public String call_114(DataLogin dataLogin, String DATE) {
        String contents = "";
        String idLogin = dataLogin.getUSERID() + "";
        if ((Const.checkInternetConnection(context)
                && Const.PHONENUMBER.length() > 0
                && idLogin.trim().length() > 0
                && dataLogin.getDEPTCD().trim().length() > 0
                && dataLogin.getJOBCODE().trim().length() > 0)
                || (Const.checkInternetConnection(context) && dataLogin.getJOBGRADE().trim().equals(Const.SE))) {
            NetworkManager nm = new NetworkManager(Const.IP, Const.PORT);
            nm.setTerminalInfo(Const.PHONENUMBER, idLogin, dataLogin.getDEPTCD(), dataLogin.getJOBCODE());

            try {
                nm.setTR('A', Const.GetSMWorkDay);
                nm.connect(10);// set timeout
                nm.send("");
                contents = nm.receive();
                getSMWorkingDayAPI routeList = new Gson().fromJson(contents, getSMWorkingDayAPI.class);
                if (routeList.getRESULT() == 0) {
                    OfflineObject offlineObject = new OfflineObject(DATE, contents);
                    new PrefManager(context).setSmWorkDay(new Gson().toJson(offlineObject));
                } else {
                    new PrefManager(context).setSmWorkDay("");
                    Log.e(TAG, "API 114 Fail");
                }
//            Const.longInfo("getSMWorkingDay",contents);
            } catch (EksysNetworkException e) {
                Log.e(TAG, "Error(" + e.getErrorCode() + "): " + e.toString());
            } finally {
                nm.close();
            }
        }
        return contents;
    }

    public class DELETE_IMAGE extends AsyncTask<String, String, String> {
        IF_DELETE_IMAGE callback;
        String FILE_NAME;

        public DELETE_IMAGE(String FILE_NAME, IF_DELETE_IMAGE callback) {
            this.FILE_NAME = FILE_NAME;
            this.callback = callback;
        }

        @Override
        protected String doInBackground(String... strings) {
            return handleBackground(strings);
        }

        protected String handleBackground(String... params) {
            return uploadFileToServer(Const.UpLoadImage, FILE_NAME);

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d(TAG, "resutl" + s);
            callback.onComplete(s);
        }
    }

    public static String uploadFileToServer(String targetUrl, String FILE_NAME) {
        String response = "error";
        HttpURLConnection connection = null;
        DataOutputStream outputStream = null;


        String responseString = null;
        String urlServer = targetUrl;
        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";

        int bytesRead, bufferSize;
        byte[] buffer;
        int maxBufferSize = 1 * 1024;

        try {
            URL url = new URL(urlServer);
            connection = (HttpURLConnection) url.openConnection();
            // Allow Inputs & Outputs
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setUseCaches(false);
            connection.setChunkedStreamingMode(1024);
            // Enable POST method
            connection.setRequestMethod("POST");
            connection.setConnectTimeout(Const.TIME_OUT_UPLOAD_IMAGE);
            connection.setReadTimeout(Const.TIME_OUT_UPLOAD_IMAGE);
            connection.setRequestProperty("Connection", "Keep-Alive");
            connection.setRequestProperty("Content-Type",
                    "multipart/form-data; boundary=" + boundary);
            String fn = Const.PDA_SHOP_IMAGE + "/" + FILE_NAME;
            Log.d("HttpRequest","fileNam:"+fn);
            connection.setRequestProperty("filename", fn);

            connection.setRequestProperty("id", "DELETE"); // delete
            outputStream = new DataOutputStream(connection.getOutputStream());
            outputStream.writeBytes(twoHyphens + boundary + lineEnd);

            String token = "anyvalye";
            outputStream.writeBytes("Content-Disposition: form-data; name=\"Token\"" + lineEnd);
            outputStream.writeBytes("Content-Type: text/plain;charset=UTF-8" + lineEnd);
            outputStream.writeBytes("Content-Length: " + token.length() + lineEnd);
            outputStream.writeBytes(lineEnd);
            outputStream.writeBytes(token + lineEnd);
            outputStream.writeBytes(twoHyphens + boundary + lineEnd);

            String taskId = "anyvalue";
            outputStream.writeBytes("Content-Disposition: form-data; name=\"TaskID\"" + lineEnd);
            outputStream.writeBytes("Content-Type: text/plain;charset=UTF-8" + lineEnd);
            outputStream.writeBytes("Content-Length: " + taskId.length() + lineEnd);
            outputStream.writeBytes(lineEnd);
            outputStream.writeBytes(taskId + lineEnd);
            outputStream.writeBytes(twoHyphens + boundary + lineEnd);

            outputStream.writeBytes(lineEnd);

            outputStream.writeBytes(lineEnd);
            outputStream.writeBytes(twoHyphens + boundary + twoHyphens
                    + lineEnd);

            // Responses from the server (code and message)
            int serverResponseCode = connection.getResponseCode();
            String serverResponseMessage = connection.getResponseMessage();
            System.out.println("Server Response Code " + " " + serverResponseCode);
            System.out.println("Server Response Message " + serverResponseMessage);

            if (serverResponseCode == 200) {
                response = "true";
            } else {
                response = "false";
            }

            outputStream.flush();

            connection.getInputStream();
            //for android InputStream is = connection.getInputStream();
            java.io.InputStream is = connection.getInputStream();

            int ch;
            StringBuffer b = new StringBuffer();
            while ((ch = is.read()) != -1) {
                b.append((char) ch);
            }

            responseString = b.toString();
            System.out.println("response string is" + responseString); //Here is the actual output

            //Util.deleteFile(file);
            outputStream.close();
        } catch (Exception ex) {
            // Exception handling
            response = "error";
            System.out.println("Send file Exception" + ex.getMessage() + "");
            ex.printStackTrace();
        } finally {

            if (outputStream != null)
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
        if (response.equalsIgnoreCase("true"))
            return responseString;
        else {
            return "error ";
        }
    }
}
