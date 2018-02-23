package com.orion.salesman._route._fragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.orion.salesman.MainActivity;
import com.orion.salesman.R;
import com.orion.salesman.ShopInfoActivity;
import com.orion.salesman.WebViewMaps;
import com.orion.salesman._app.MyApplication;
import com.orion.salesman._class.Const;
import com.orion.salesman._class.DefaultItemAnimatorNoChange;
import com.orion.salesman._class.HttpRequest;
import com.orion.salesman._interface.IF_100;
import com.orion.salesman._interface.IF_112;
import com.orion.salesman._interface.IF_115;
import com.orion.salesman._interface.IF_117;
import com.orion.salesman._interface.IF_118;
import com.orion.salesman._interface.IF_119;
import com.orion.salesman._interface.IF_124;
import com.orion.salesman._interface.UpLoadImg;
import com.orion.salesman._object.API_110;
import com.orion.salesman._object.API_111;
import com.orion.salesman._object.API_112;
import com.orion.salesman._object.API_120;
import com.orion.salesman._object.AddressSQLite;
import com.orion.salesman._object.AddressSQLiteStreets;
import com.orion.salesman._object.Check115;
import com.orion.salesman._object.CodeH;
import com.orion.salesman._object.DataBase128;
import com.orion.salesman._object.DataLogin;
import com.orion.salesman._object.GET_DATA_INPUT;
import com.orion.salesman._object.LISTPOSITION;
import com.orion.salesman._object.Obj_124;
import com.orion.salesman._object.OrderNonShopList;
import com.orion.salesman._object.OrderNonShopListAPI;
import com.orion.salesman._object.PARSE_115;
import com.orion.salesman._object.PARSE_117;
import com.orion.salesman._object.RESENDNEWSHOP;
import com.orion.salesman._object.RESEND_115;
import com.orion.salesman._object.RESEND_116;
import com.orion.salesman._object.RESEND_125;
import com.orion.salesman._object.RouteScheduleShop;
import com.orion.salesman._object.SaveInputData;
import com.orion.salesman._object.SavePositionLocaiton;
import com.orion.salesman._object.TABLE_INPUT;
import com.orion.salesman._offline.OfflineObject;
import com.orion.salesman._pref.PrefManager;
import com.orion.salesman._route._adapter.CustomAdapterSpinner;
import com.orion.salesman._route._adapter.RouteAdapter;
import com.orion.salesman._route._object.DisplayInfo;
import com.orion.salesman._route._object.IF_128;
import com.orion.salesman._route._object.InforShopDetails;
import com.orion.salesman._route._object.ListInforShop;
import com.orion.salesman._route._object.ObjectA0128;
import com.orion.salesman._route._object.ParamsRouteShop;
import com.orion.salesman._route._object.RequestRoute;
import com.orion.salesman._route._object.Route;
import com.orion.salesman._route._object.RouteList;
import com.orion.salesman._route._object.RouteObject;
import com.orion.salesman._route._object.RouteScheduleList;
import com.orion.salesman._route._object.getSMWorkingDayAPI;
import com.orion.salesman._sqlite.DataBaseCodeH;
import com.orion.salesman._sqlite.DataBaseHelper;
import com.orion.salesman._sqlite.DatabaseHandler;

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

import kr.co.eksys.nativelib.EksysNetworkException;
import kr.co.eksys.nativelib.NetworkManager;

/**
 * Created by maidinh on 3/8/2016.
 */
public class RouteFragment extends Fragment {
    private String TAG = "RouteFragment";
    private List<Route> arrayList = new ArrayList<>();
    private RecyclerView recyclerView;
    private RouteAdapter mAdapter;
    private Button btnNewShop, btnMaps;
    private TextView tvTitle, tvDate;
    private Spinner tvDayOfWeek;
    private DataLogin dataLogin;
    public static String DATE;
    private ProgressBar progressbar;
    private LinearLayout LN_POSITION, LN_NEWSHOP;
    public static RouteFragment instance = null;
    private TextView tvNodata;
    private EditText edSearch;
    private LinearLayout llDate;

    public void showPR() {
        progressbar.setVisibility(View.VISIBLE);
    }

    public void hidePR() {
        progressbar.setVisibility(View.GONE);
    }

    @SuppressLint("ValidFragment")
    public RouteFragment(DataLogin dataLogin) {
        this.dataLogin = dataLogin;
    }

    public RouteFragment() {

    }


    public void updateOrderList(int pos, SaveInputData ob) {
        mAdapter.updateOrderList(pos, ob);
    }

    public void updateOrderInfor(int pos, String V20, String V21, String V22, String V23, String V24, String V25) {
        mAdapter.updateOrderInfor(pos, V20, V21, V22, V23, V24, V25);
    }

    void reload() {
        progressbar.setVisibility(View.VISIBLE);
        tvNodata.setVisibility(View.GONE);
        recyclerView.setVisibility(View.GONE);
    }

    List<DisplayInfo> ListInfor = new ArrayList<DisplayInfo>();

    public String getListInfo(String s) {
        List<DisplayInfo> arrayList = new ArrayList<DisplayInfo>();
        for (DisplayInfo d : ListInfor) {

            if (d.getV5().equals(s))
                arrayList.add(d);
        }
        return new Gson().toJson(arrayList);
    }

    List<InforShopDetails> inforShopDetailsList = new ArrayList<>();


    private void getRouteShop(DataLogin dataLogin, String USE_DATE) {
        arrayList.clear();
//        Const.longInfo(TAG,new PrefManager(getActivity()).getApi112());
        DatabaseHandler db = new DatabaseHandler(getActivity());
        List<API_112> listAPI_112 = db.getAllContacts();
        int check = 0;
        if (listAPI_112 == null || listAPI_112.size() == 0) {
            check = 1;
        } else {
            if (Const.API_112_contains(listAPI_112, DATE, KEY_ROUTE)) {

                ListInforShop listInforShop = null;
                for (int i = 0; i < listAPI_112.size(); i++) {
                    Log.d(TAG, listAPI_112.get(i).getDATE());
                    if (listAPI_112.get(i).getDATE().equalsIgnoreCase(DATE)
                            && listAPI_112.get(i).getWEEK().equalsIgnoreCase(KEY_ROUTE))
                        listInforShop = new Gson().fromJson(listAPI_112.get(i).getDATA(), ListInforShop.class);
                }
                if (listInforShop.getRESULT() == 0) {
                    Log.d(TAG, "getRouteShop offline:" + "DATE:" + DATE + " KEY_ROUTE:" + KEY_ROUTE);
                    getRoute(dataLogin, listInforShop.getLIST(), USE_DATE);
                } else {
                    Log.e(TAG, "getRouteShop offline Fail");
                }

            } else {
                check = 1;
            }
        }
        Log.d(TAG, "check:" + check);
        String idLogin = dataLogin.getUSERID() + "";
        if ((check == 1 && Const.checkInternetConnection(getActivity())
                && Const.PHONENUMBER.length() > 0
                && idLogin.trim().length() > 0
                && dataLogin.getDEPTCD().trim().length() > 0
                && dataLogin.getJOBCODE().trim().length() > 0)
                || (Const.checkInternetConnection(getActivity()) && dataLogin.getJOBGRADE().trim().equals(Const.SE))) {
            NetworkManager nm = new NetworkManager(Const.IP, Const.PORT);
            nm.setTerminalInfo(Const.PHONENUMBER, idLogin, dataLogin.getDEPTCD(), dataLogin.getJOBCODE());
            String contents = "";
            try {
                nm.setTR('A', Const.getRouteShop);
                nm.connect(10);// set timeout
//                ParamsRouteShop ob = new ParamsRouteShop("0", "", USE_DATE);
                Map<String, Object> map = new HashMap<>();
                map.put("MODE", "0");
                map.put("CUSTCD", "");
                map.put("DATE", USE_DATE);
                String data = new Gson().toJson(map);
                Log.d(TAG, "send param:" + data);
                Const.WriteFileLog(dataLogin.getUSERID(), Const.getRouteShop, data);
                nm.send(data);
                contents = nm.receive();
                ListInforShop routeList = new Gson().fromJson(contents, ListInforShop.class);
                if (routeList.getRESULT() == 0) {
                    Log.d(TAG, "size:" + routeList.getLIST().size());
                    getRoute(dataLogin, routeList.getLIST(), USE_DATE);

                    API_112 api_112 = new API_112(DATE, KEY_ROUTE, new Gson().toJson(routeList));
                    db.addContact(api_112);
                } else {
                    Log.e(TAG, "getRouteShop Fail:" + contents);
                }
//                Const.longInfo(TAG, "getRouteShop online: " + contents);
//                Const.longInfo("getRoute", contents);
            } catch (EksysNetworkException e) {
                Log.e(TAG, "getRouteShop Error(" + e.getErrorCode() + "): " + e.toString());
            } finally {
                nm.close();
            }
        }
    }

    public void reloadRouteShop(String USE_DATE, String s) {
        Log.d(TAG, "reloadRouteShop");
        progressbar.setVisibility(View.VISIBLE);
        int id = 0;
        SavePositionLocaiton savePositionLocaiton = new Gson().fromJson(s, SavePositionLocaiton.class);
        int pos = savePositionLocaiton.getPosition();
        long LON = savePositionLocaiton.getLON();
        long LAT = savePositionLocaiton.getLAT();
        String V4 = savePositionLocaiton.getADDR1();
        String V5 = savePositionLocaiton.getADDR2();
        String V6 = savePositionLocaiton.getADDR3();

        DatabaseHandler db = new DatabaseHandler(getActivity());
        List<API_112> listAPI_112 = db.getAllContacts();
        if (listAPI_112 != null && listAPI_112.size() > 0) {
            for (int i = 0; i < listAPI_112.size(); i++) {
                API_112 routeShopOffline = listAPI_112.get(i);
                if (routeShopOffline.getDATE().equals(USE_DATE) && routeShopOffline.getWEEK().equals(KEY_ROUTE)) {
                    id = routeShopOffline.getId();
                    ListInforShop routeList = new Gson().fromJson(routeShopOffline.getDATA(), ListInforShop.class);
                    List<InforShopDetails> LIST = routeList.getLIST();
                    Log.d(TAG, "LIST:" + LIST.size());
                    if (LIST != null && LIST.size() > 0) {
                        for (int k = 0; k < LIST.size(); k++) {
                            InforShopDetails inforShopDetails = LIST.get(k);
                            if (inforShopDetails.getV1().equals(MainActivity.SHOP_CODE)) {
                                inforShopDetails.setV14("" + LON);
                                inforShopDetails.setV15("" + LAT);
                                inforShopDetails.setV4("" + V4);
                                inforShopDetails.setV5("" + V5);
                                inforShopDetails.setV29(V4 + V5 + V6);
                                Log.d(TAG, "ADMINCODE:" + V4 + V5 + V6);
                                break;
                            }
                        }
                    }
                    db.updateContact(new Gson().toJson(routeList), id);
                    break;
                }
            }
        }
        progressbar.setVisibility(View.GONE);
    }


    class API_111_ extends AsyncTask<String, String, String> {
        DataLogin dataLogin;
        String USE_DATE;

        public API_111_(DataLogin dataLogin, String USE_DATE) {
            this.dataLogin = dataLogin;
            this.USE_DATE = USE_DATE;
        }

        @Override
        protected String doInBackground(String... strings) {
            getRouteSchedule(dataLogin, USE_DATE);
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
    }

    private void getRouteSchedule(DataLogin dataLogin, String USE_DATE) {
        int check = 0;
        DatabaseHandler db = new DatabaseHandler(getActivity());
        List<API_111> api_111List = db.getListAPI_111();
        if (api_111List != null && api_111List.size() > 0) {
            API_111 api_111 = api_111List.get(api_111List.size() - 1);
            String date = api_111.getDATE();
            String route = api_111.getWKDAY();
            Log.d(TAG, "getRouteSchedule:" + date + ":" + route);
            if (!date.equals(USE_DATE) || !route.equals(KEY_ROUTE)) {
                check = 1;
            } else {
//                Log.d(TAG, "getRouteSchedule: " + ob.getDATA());
                RouteScheduleList routeList = new Gson().fromJson(api_111.getDATA(), RouteScheduleList.class);
                if (routeList.getRESULT() == 0) {
                    ListInfor.clear();
                    ListInfor = routeList.getLIST();
                    Log.e(TAG, "getRouteSchedule Offline");
                } else {
                    Log.e(TAG, "getRouteSchedule Fail");
                }
            }
        } else {
            check = 1;
        }
        String idLogin = dataLogin.getUSERID() + "";
        if ((check == 1 && Const.checkInternetConnection(getActivity())
                && Const.PHONENUMBER.length() > 0
                && idLogin.trim().length() > 0
                && dataLogin.getDEPTCD().trim().length() > 0
                && dataLogin.getJOBCODE().trim().length() > 0)
                || (Const.checkInternetConnection(getActivity()) && dataLogin.getJOBGRADE().trim().equals(Const.SE))) {
            NetworkManager nm = new NetworkManager(Const.IP, Const.PORT);
            nm.setTerminalInfo(Const.PHONENUMBER, idLogin, dataLogin.getDEPTCD(), dataLogin.getJOBCODE());
            String contents = "";
            try {
                nm.setTR('A', Const.getRouteScheduleShop);
                nm.connect(10);// set timeout
//                RouteScheduleShop routeScheduleShop = new RouteScheduleShop();
//                routeScheduleShop.setDATE(USE_DATE);
//                routeScheduleShop.setWKDAY(KEY_ROUTE);

                Map<String, Object> map = new HashMap<>();
                map.put("DATE", USE_DATE);
                map.put("WKDAY", KEY_ROUTE);
                String data = new Gson().toJson(map);
                Const.WriteFileLog(dataLogin.getUSERID(), Const.getRouteScheduleShop, data);
                nm.send(data);
                Log.d(TAG, "getRouteSchedule:" + data);
                contents = nm.receive();
                RouteScheduleList routeList = new Gson().fromJson(contents, RouteScheduleList.class);
                if (routeList.getRESULT() == 0) {
                    ListInfor.clear();
                    ListInfor = routeList.getLIST();
                    if (api_111List != null && api_111List.size() > 0)
                        db.delete_All_API_111();
                    API_111 offlineObject = new API_111(USE_DATE, contents, "" + KEY_ROUTE);
                    db.addContact_111(offlineObject);
                } else {
                    Log.e(TAG, "getRouteSchedule Fail");
                }
                Log.d(TAG, "getRouteSchedule: " + contents);
//            Const.longInfo("getRoute",contents);
            } catch (EksysNetworkException e) {
                Log.e(TAG, "getRouteSchedule Error(" + e.getErrorCode() + "): " + e.toString());
            } finally {
                nm.close();
            }
        }

    }

    SaveInputData ObjectSave(String date, String ShopID) {
        SaveInputData ob = new SaveInputData();
        DatabaseHandler db = new DatabaseHandler(getActivity());
        List<SaveInputData> ListOrder = db.getListOrder();
        if (ListOrder != null && ListOrder.size() > 0) {
            for (int i = 0; i < ListOrder.size(); i++) {
                if (ListOrder.get(i).getDATE().equals(date) && ListOrder.get(i).getSHOPID().equals(ShopID)) {
                    ob = ListOrder.get(i);
                    break;
                }
            }
        }
        return ob;
    }


    public void updateCloseShop(String idShop) {
        int id = 0;
        RouteList routeList = null;
        DatabaseHandler db = new DatabaseHandler(getActivity());
        List<API_110> api_110List = db.getListAPI_110();
        if (api_110List != null && api_110List.size() > 0) {
            for (int i = 0; i < api_110List.size(); i++) {
                API_110 api_110 = api_110List.get(i);
                if (api_110.getDATE().equalsIgnoreCase(DATE) && api_110.getKEY_ROUTE().equals(KEY_ROUTE)) {
                    id = api_110.getId();
//                    Log.e(TAG, "id:" + id + " - " + api_110.getDATA());
                    routeList = new Gson().fromJson(api_110List.get(i).getDATA(), RouteList.class);
                    List<Route> LIST = routeList.getLIST();
                    for (int k = 0; k < LIST.size(); k++) {
                        Route route = LIST.get(k);
                        if (route.getV2().equals(idShop)) {
                            route.setIsClose(true);
                            break;
                        }
                    }
                    break;
                }
            }
            db.update_110(new Gson().toJson(routeList), id);
        }
    }

    private void getRoute(DataLogin dataLogin, List<InforShopDetails> inforShopDetailses, String USE_DATE) {
        int check = 0;
        DatabaseHandler db = new DatabaseHandler(getActivity());
        List<API_110> api_110List = db.getListAPI_110();
        if (api_110List != null && api_110List.size() > 0) {
            if (Const.API_110_contains(api_110List, DATE, KEY_ROUTE)) {
                RouteList listInforShop = null;
                for (int i = 0; i < api_110List.size(); i++) {
//                    Log.d(TAG, api_110List.get(i).getDATE());
                    if (api_110List.get(i).getDATE().equalsIgnoreCase(DATE)
                            && api_110List.get(i).getKEY_ROUTE().equalsIgnoreCase(KEY_ROUTE))
                        listInforShop = new Gson().fromJson(api_110List.get(i).getDATA(), RouteList.class);
                }
                if (listInforShop.getRESULT() == 0) {
                    arrayList.clear();
                    arrayList = listInforShop.getLIST();
                    inforShopDetailsList.clear();
//                    ListNonShop.clear();
                    for (int i = 0; i < arrayList.size(); i++) {
                        final String id = arrayList.get(i).getV2();
                        for (int k = 0; k < inforShopDetailses.size(); k++) {
                            if (id.equals(inforShopDetailses.get(k).getV1())) {
                                inforShopDetailsList.add(inforShopDetailses.get(k));
                                break;
                            }
                        }
                    }
                } else {
                    Log.e(TAG, "getRoute Fail");
                }
                Log.d(TAG, "getRoute offline");
            } else {
                check = 1;
            }
        } else {
            check = 1;
        }
        String idLogin = dataLogin.getUSERID() + "";
        if ((check == 1 && Const.checkInternetConnection(getActivity())
                && Const.PHONENUMBER.length() > 0
                && idLogin.trim().length() > 0
                && dataLogin.getDEPTCD().trim().length() > 0
                && dataLogin.getJOBCODE().trim().length() > 0)
                || (Const.checkInternetConnection(getActivity()) && dataLogin.getJOBGRADE().trim().equals(Const.SE))) {
            NetworkManager nm = new NetworkManager(Const.IP, Const.PORT);
            nm.setTerminalInfo(Const.PHONENUMBER, idLogin, dataLogin.getDEPTCD(), dataLogin.getJOBCODE());
            String contents = "";
            try {
                nm.setTR('A', Const.Route);
                nm.connect(10);          // set timeout
                RequestRoute ob = new RequestRoute(USE_DATE, KEY_ROUTE);
                String data = new Gson().toJson(ob);
                Const.WriteFileLog(dataLogin.getUSERID(), Const.Route, data);
                nm.send(data);// send body
                contents = nm.receive();
                RouteList routeList = new Gson().fromJson(contents, RouteList.class);
                if (routeList.getRESULT() == 0) {
                    arrayList.clear();
                    arrayList = routeList.getLIST();
                    inforShopDetailsList.clear();
//                    ListNonShop.clear();
                    for (int i = 0; i < arrayList.size(); i++) {
                        final String id = arrayList.get(i).getV2();
                        for (int k = 0; k < inforShopDetailses.size(); k++) {
                            if (id.equals(inforShopDetailses.get(k).getV1())) {
                                inforShopDetailsList.add(inforShopDetailses.get(k));
                                break;
                            }
                        }
                    }
                    API_110 offlineObject = new API_110(DATE, KEY_ROUTE, contents);
                    db.addContact_110(offlineObject);
                } else {
                }
//                Log.d(TAG, "getRoute online: " + contents);
                Const.longInfo(TAG, "getRoute online" + contents);
            } catch (EksysNetworkException e) {
                Log.e(TAG, "getRoute Error(" + e.getErrorCode() + "): " + e.toString());
            } finally {
                nm.close();
            }
        }

        handlerList();

    }

    List<AddressSQLiteStreets> getAddressStreet = new ArrayList<>();
    List<AddressSQLite> getAddress = new ArrayList<>();
    List<RouteObject> LIST = new ArrayList<>();

    String getStreet(String code) {
        String s = "";
        for (AddressSQLiteStreets str : getAddressStreet)
            if (code.equals(str.getSTREETCD()))
                s = str.getSTREETNM().trim();
        return s;
    }

    String getCity(String s1, String s2, String s3) {
        String s = "";
        String add1 = "";
        String add2 = "";
        String add3 = "";
        if (s3.length() > 0) {
//            Log.d(TAG, s1 + ":" + s2 + ":" + s3);
//            for (AddressSQLite as : getAddress) {
//                if (s1.equals(as.getADDR1()) && s2.equals(as.getADDR2()) && s3.equals(as.getADDR3())) {
//                    s = as.getADDR6() + ", " + as.getADDR5() + ", " + as.getADDR4();
//                }
//            }
            for (int i = 0; i < getAddress.size(); i++) {
                AddressSQLite as = getAddress.get(i);
                if (s1.equals(as.getADDR1())) {
                    add1 = as.getADDR4();
                    break;
                }
            }
            for (int i = 0; i < getAddress.size(); i++) {
                AddressSQLite as = getAddress.get(i);
                if (s2.equals(as.getADDR2())) {
                    add2 = as.getADDR5();
                    break;
                }
            }
            for (int i = 0; i < getAddress.size(); i++) {
                AddressSQLite as = getAddress.get(i);
                if (s3.equals(as.getADDR3())) {
                    add3 = as.getADDR6();
                    break;
                }
            }
            s = add3 + ", " + add2 + ", " + add1;

        } else {
//            for (AddressSQLite as : getAddress) {
//                if (s1.equals(as.getADDR1()) && s2.equals(as.getADDR2())) {
//                    s = as.getADDR5() + ", " + as.getADDR4();
//                }
//            }
            for (int i = 0; i < getAddress.size(); i++) {
                AddressSQLite as = getAddress.get(i);
                if (s1.equals(as.getADDR1())) {
                    add1 = as.getADDR4();
                    break;
                }
            }
            for (int i = 0; i < getAddress.size(); i++) {
                AddressSQLite as = getAddress.get(i);
                if (s2.equals(as.getADDR2())) {
                    add2 = as.getADDR5();
                    break;
                }
            }
            s = add2 + ", " + add1;
        }

        return s;
    }

    List<LISTPOSITION> listpositions = new ArrayList<>();
    String strLISTPOSITION = "";

    void getListPosition() {
        listpositions.clear();
        strLISTPOSITION = "";
        List<RouteObject> getList = mAdapter.getLIST();
        if (getList != null && getList.size() > 0) {
            for (int i = 0; i < getList.size(); i++) {
                InforShopDetails isd = getList.get(i).getInforShopDetails();
                if (isd.getV14() != null && !isd.getV14().equals("0") && !isd.getV14().equals("0.0") && isd.getV14().length() > 0
                        && isd.getV15() != null && !isd.getV15().equals("0") && !isd.getV15().equals("0.0") && isd.getV15().length() > 0) {
                    double lon = (double) Integer.parseInt(isd.getV14()) / Const.GPS_WGS84;
                    double lat = (double) Integer.parseInt(isd.getV15()) / Const.GPS_WGS84;
                    int pos = 1;
                    if (getList.get(i).isClose())
                        pos = 3;
                    else if (getList.get(i).isVisit())
                        pos = 1;
                    else pos = 2;
                    LISTPOSITION ob = new LISTPOSITION(lon, lat, "" + pos, getList.get(i).getSHOPNAME(), getList.get(i).getSEQ());
                    listpositions.add(ob);
                }
            }
        }
//        if (listpositions.size() > 0)
        strLISTPOSITION = new Gson().toJson(listpositions);
//        Log.d(TAG,"strLISTPOSITION:"+strLISTPOSITION);
//        else strLISTPOSITION = "";
    }

    List<TABLE_INPUT> LIST_INPUT = new ArrayList<>();

    GET_DATA_INPUT get_data_input(String DATE, String SHOP_ID) {
        GET_DATA_INPUT ob = new GET_DATA_INPUT();
        if (LIST_INPUT != null && LIST_INPUT.size() > 0) {
            for (int i = 0; i < LIST_INPUT.size(); i++) {
                if (DATE.equals(LIST_INPUT.get(i).getTABLE_INPUT_DATE()) && SHOP_ID.equals(LIST_INPUT.get(i).getTABLE_INPUT_SHOP_ID())) {
                    ob.setVISIT("1");
                    String OMI = LIST_INPUT.get(i).getTABLE_INPUT_OMI();
                    if (OMI.equals("1"))
                        OMI = "Y";
                    else OMI = "N";
                    ob.setOMI(OMI);
                    ob.setORDER(LIST_INPUT.get(i).getTABLE_INPUT_ORDER());
                    break;
                }
            }
        }
        return ob;
    }

    //    public static String routeLon = "";
//    public static String routeLat = "";
    public static String ADMINCODE = "";

    void handlerList() {
        LIST.clear();
        DatabaseHandler db = new DatabaseHandler(getActivity());
        LIST_INPUT = db.GET_LIST_INPUT();
        if (arrayList != null && arrayList.size() > 0) {
            for (int i = 0; i < arrayList.size(); i++) {
                RouteObject ob = new RouteObject();

                if (i == 0) {
                    if (inforShopDetailsList.get(i).getV14() != null
                            && inforShopDetailsList.get(i).getV14().length() > 0
                            && !inforShopDetailsList.get(i).getV14().equals("0")
                            && !inforShopDetailsList.get(i).getV14().equals("0.0")
                            && inforShopDetailsList.get(i).getV15() != null
                            && inforShopDetailsList.get(i).getV15().length() > 0
                            && !inforShopDetailsList.get(i).getV15().equals("0")
                            && !inforShopDetailsList.get(i).getV15().equals("0.0")) {
                        ob.setDISTANCE("0");
                    } else {
                        ob.setDISTANCE("");
                    }
                } else {
                    if (inforShopDetailsList.get(i).getV14() != null
                            && inforShopDetailsList.get(i).getV14().length() > 0
                            && !inforShopDetailsList.get(i).getV14().equals("0")
                            && !inforShopDetailsList.get(i).getV14().equals("0.0")
                            && inforShopDetailsList.get(i).getV15() != null
                            && inforShopDetailsList.get(i).getV15().length() > 0
                            && !inforShopDetailsList.get(i).getV15().equals("0")
                            && !inforShopDetailsList.get(i).getV15().equals("0.0")) {
                        if (inforShopDetailsList.get(i - 1).getV14() != null
                                && inforShopDetailsList.get(i - 1).getV14().length() > 0
                                && !inforShopDetailsList.get(i - 1).getV14().equals("0")
                                && !inforShopDetailsList.get(i - 1).getV14().equals("0.0")
                                && inforShopDetailsList.get(i - 1).getV15() != null
                                && inforShopDetailsList.get(i - 1).getV15().length() > 0
                                && !inforShopDetailsList.get(i - 1).getV15().equals("0")
                                && !inforShopDetailsList.get(i - 1).getV15().equals("0.0")) {
                            double LAT1 = (double) Integer.parseInt(inforShopDetailsList.get(i - 1).getV15()) / Const.GPS_WGS84;
                            double LON1 = (double) Integer.parseInt(inforShopDetailsList.get(i - 1).getV14()) / Const.GPS_WGS84;
                            double LAT2 = (double) Integer.parseInt(inforShopDetailsList.get(i).getV15()) / Const.GPS_WGS84;
                            double LON2 = (double) Integer.parseInt(inforShopDetailsList.get(i).getV14()) / Const.GPS_WGS84;
                            long DISTANCE = Const.getDistanceBetweenTwoPoints(LAT1, LON1, LAT2, LON2);
                            if (DISTANCE < 0)
                                DISTANCE = 0;
                            ob.setDISTANCE("" + DISTANCE);
                        } else {
                            ob.setDISTANCE("-1");
                        }
                    } else {
                        ob.setDISTANCE("");
                    }
                }


                ob.setSEQ(arrayList.get(i).getV1());
                ob.setSHOPCODE(arrayList.get(i).getV2());
                ob.setSHOPNAME(arrayList.get(i).getV3());
                String tvAdd = "";
                if (inforShopDetailsList.get(i).getV29().length() == 10) {
                    String CODE = inforShopDetailsList.get(i).getV29();
                    tvAdd = inforShopDetailsList.get(i).getV7() + ", "
                            + getStreet(inforShopDetailsList.get(i).getV6())
                            + ", "
                            + getCity(CODE.substring(0, 2), CODE.substring(2, 5), CODE.substring(5, 10));
                } else {
                    tvAdd = inforShopDetailsList.get(i).getV7() + ", "
                            + getStreet(inforShopDetailsList.get(i).getV6())
                            + ", "
                            + getCity(inforShopDetailsList.get(i).getV4(), inforShopDetailsList.get(i).getV5(), "");
                }

                ob.setADDRESS(tvAdd);
                ob.setMAG(arrayList.get(i).getV6());
                ob.setMTD(arrayList.get(i).getV5());
                String order = "0";
                String omi = "N";
                boolean VS = false;
                GET_DATA_INPUT DATA_INPUT = get_data_input(DATE, arrayList.get(i).getV2());
                if (DATA_INPUT.getVISIT().equals("1")) {
                    order = DATA_INPUT.getORDER();
                    omi = DATA_INPUT.getOMI();
                    VS = true;
                }

                if (list128 != null && list128.size() > 0) {

                    for (ObjectA0128 obj : list128) {
                        if (obj.getV1().equals(arrayList.get(i).getV2())) {
                            order = obj.getV3();
                            omi = obj.getV2();
                            VS = true;
                        }
                    }
                }

                ob.setVisit(VS);
                ob.setOMI(omi);
                ob.setORDER(order);
                ob.setNONSHOP(getNonShop(inforShopDetailsList.get(i).getV1()));
                ob.setOWNER(inforShopDetailsList.get(i).getV26());
                ob.setTEL(inforShopDetailsList.get(i).getV28());
                ob.setLON(inforShopDetailsList.get(i).getV14());
                ob.setLAT(inforShopDetailsList.get(i).getV15());

                ADMINCODE = inforShopDetailsList.get(i).getV29();
//                routeLon = inforShopDetailsList.get(i).getV14();
//                routeLat = inforShopDetailsList.get(i).getV15();

                String grade = "";
                if (MainActivity.TEAM.equals(Const.PIE_TEAM))
                    grade = inforShopDetailsList.get(i).getV8();
                else if (MainActivity.TEAM.equals(Const.SNACK_TEAM))
                    grade = inforShopDetailsList.get(i).getV9();
                else grade = inforShopDetailsList.get(i).getV10();
                ob.setGRADE(grade);
                String type = "";
                if (inforShopDetailsList.get(i).getV2().equals("1")) {
                    type = "DS";
                } else {
                    type = "PS";
                }
                ob.setSHOPTYPE(type);

                ob.setClose(arrayList.get(i).isClose());
                ob.setInforShopDetails(inforShopDetailsList.get(i));
                LIST.add(ob);
            }
        }

    }

    String getNonShop(String SHOPCODE) {
        String nonshop = "";
        if (getListNonShopMonth != null && getListNonShopMonth.size() > 0) {
            for (int i = 0; i < getListNonShopMonth.size(); i++) {
                if (getListNonShopMonth.get(i).getV1().equals(SHOPCODE)) {
                    String s1 = "";
                    String s2 = "";
                    if (getListNonShopMonth.get(i).getV2().equals(Const.PIE_TEAM))
                        s1 = "P";
                    else if (getListNonShopMonth.get(i).getV2().equals(Const.SNACK_TEAM))
                        s1 = "S";
                    else if (getListNonShopMonth.get(i).getV2().equals(Const.GUM_TEAM))
                        s1 = "G";
                    if (getListNonShopMonth.get(i).getV3().equals("Y"))
                        s2 = "3";
                    else if (getListNonShopMonth.get(i).getV2().equals("Y"))
                        s2 = "2";
                    else s2 = "1";
                    nonshop = s1 + s2;
                    break;
                }
            }
        } else {
            nonshop = "";
        }
        return nonshop;
    }

    public void initRC() {

        if (LIST == null || LIST.size() == 0) {
            ShowTVNodata();
        } else {
            mAdapter.notifyDataSetChanged();
            recyclerView.setVisibility(View.VISIBLE);
            progressbar.setVisibility(View.GONE);
        }
    }

    public void ShowTVNodata() {
        recyclerView.setVisibility(View.GONE);
        tvNodata.setVisibility(View.VISIBLE);
        progressbar.setVisibility(View.GONE);
    }

    void getDB(View v) {
        getAddress.clear();
        getAddressStreet.clear();
        DataBaseHelper db = new DataBaseHelper(getActivity());
        db.openDB();
//        getAddressStreet = db.getAddressStreet();
//        getAddress = db.getAddress();
        new getStreet(db, v).execute();
    }

    class getStreet extends AsyncTask<String, String, String> {
        DataBaseHelper db;
        View v;

        public getStreet(DataBaseHelper db, View v) {
            this.db = db;
            this.v = v;
        }

        @Override
        protected String doInBackground(String... strings) {
            getAddressStreet = db.getAddressStreet();
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            new getAdd(db, v).execute();
        }
    }

    class getAdd extends AsyncTask<String, String, String> {
        DataBaseHelper db;
        View v;

        public getAdd(DataBaseHelper db, View v) {
            this.db = db;
            this.v = v;
        }

        @Override
        protected String doInBackground(String... strings) {
            getAddress = db.getAddress();
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            getList();
            initSpinner(v);
//            initView(v);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    void initDBFirst(View v) {
        getDB(v);
//        getList();
//        initView(v);
    }

    int checkLoadFinish = 0;

    public int getLoadFinish() {
        return checkLoadFinish;
    }

    public void updateGPS(int pos, boolean flag) {
        mAdapter.updateGPS(pos, flag);
    }

    View v;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_route, container, false);
        instance = this;
        KEY_ROUTE = "1";
        Calendar cal = Calendar.getInstance();
        int YEAR = cal.get(Calendar.YEAR);
        int MONTH = cal.get(Calendar.MONTH) + 1;
        int DAY_OF_MONTH = cal.get(Calendar.DAY_OF_MONTH);
        DATE = YEAR + Const.setFullDate(MONTH) + Const.setFullDate(DAY_OF_MONTH);
        initView(v);
        ReSendData();
        initDBFirst(v);
        return v;
    }

    public void LoadUI() {
        Log.d(TAG, "updateDate");
        PrefManager pref = new PrefManager(getActivity());
        if (LIST == null)
            LIST = new ArrayList<>();
        if (LIST.size() == 0 || pref.isUpdateRoute()) {
            pref.setUpdateRoute(false);
            send_112(dataLogin, DATE);
        }
    }

    public void updateDate() {
        if (checkLoadFinish == 0) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    LoadUI();
                }
            }, 1000);
        } else {
            LoadUI();
        }


    }

    public void ScrollEndList(int Size) {
        recyclerView.smoothScrollToPosition(Size);
    }

    LinearLayoutManager layoutManager;

    void initView(View v) {
        mAdapter = new RouteAdapter(getActivity(), LIST);
        recyclerView = (RecyclerView) v.findViewById(R.id.recycler_view);

        layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimatorNoChange());
        recyclerView.setAdapter(mAdapter);
        tvNodata = (TextView) v.findViewById(R.id.tvNodata);
        progressbar = (ProgressBar) v.findViewById(R.id.progressbar);
        tvTitle = (TextView) v.findViewById(R.id.tvTitle);
        tvDate = (TextView) v.findViewById(R.id.tvDate);
        tvDate.setText(Const.formatDate(DATE));
        llDate = (LinearLayout) v.findViewById(R.id.llDate);
        llDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogDate(getActivity(), tvDate);
            }
        });
        tvTitle.setPadding(MainActivity.widthTabLayout, 0, 0, 0);
        LN_POSITION = (LinearLayout) v.findViewById(R.id.LN_POSITION);
        LN_NEWSHOP = (LinearLayout) v.findViewById(R.id.LN_NEWSHOP);
        LN_NEWSHOP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.checkNewOrEditShop = 0;
                Intent intent = new Intent(getActivity(), ShopInfoActivity.class);
                getActivity().startActivityForResult(intent, Const.REQUEST_NEW_SHOP);

            }
        });
        LN_POSITION.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getListPosition();
                Intent intent = new Intent(getActivity(), WebViewMaps.class);
                intent.putExtra(Const.LISTPOSITION, strLISTPOSITION);
                getActivity().startActivity(intent);
            }
        });

//        initSpinner(v);
    }

    private List<CodeH> listData = new ArrayList<>();
    private CustomAdapterSpinner adapterSpinner;
    int RouteNo = 0;

    void initListSpinner() {
        listData.clear();
        try {
            RouteNo = Integer.parseInt(dataLogin.getROUTENO());
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d(TAG, "RouteNo:" + RouteNo);
        try {
            DataBaseCodeH db = new DataBaseCodeH(getActivity());
            db.openDB();
            List<CodeH> LIST = db.getData();
            for (CodeH s : LIST) {
                if (RouteNo == 0 || RouteNo == 1) {
                    if (s.getCODEGROUP().equalsIgnoreCase("RouteWeek") && s.getCODEVALUE2().equals("1"))
                        listData.add(s);
                } else if (RouteNo == 0 || RouteNo == 2) {
                    if (s.getCODEGROUP().equalsIgnoreCase("RouteWeek") && s.getCODEVALUE3().equals("2"))
                        listData.add(s);
                } else if (RouteNo == 0 || RouteNo == 3) {
                    if (s.getCODEGROUP().equalsIgnoreCase("RouteWeek") && s.getCODEVALUE4().equals("3"))
                        listData.add(s);
                }
            }
            if (RouteNo == 0 || RouteNo == 1) {
                listData.remove(listData.size() - 1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    int first = 0;

    void showDialogChangeRoute() {
        final Dialog dialog = new Dialog(getActivity());
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(R.layout.custom_dialog);
        dialog.setCanceledOnTouchOutside(false);
        TextView tvMsg = (TextView) dialog.findViewById(R.id.tvMsg);
        tvMsg.setText(getResources().getString(R.string.change_route));
        Button btnOK = (Button) dialog.findViewById(R.id.btnOK);
        Button btnCancel = (Button) dialog.findViewById(R.id.btnCancel);
        btnCancel.setVisibility(View.GONE);
        btnOK.setText("OK");
        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    void initSpinner(View v) {
        initListSpinner();
        adapterSpinner = new CustomAdapterSpinner(getActivity(), listData);
        Calendar cal = Calendar.getInstance();
        int pos = cal.get(Calendar.DAY_OF_WEEK);
        pos = pos - 2;
        if (pos < 0)
            pos = 0;


        if (RouteNo == 0) {
            for (int i = 0; i < listData.size(); i++) {
                if (i == 3) listData.get(i).setCODEKEY(listData.get(0).getCODEKEY());
                else if (i == 4) listData.get(i).setCODEKEY(listData.get(1).getCODEKEY());
                else if (i == 5) listData.get(i).setCODEKEY(listData.get(2).getCODEKEY());
            }
        }
        for (CodeH obj : listData) {
            Log.d(TAG, obj.getCODEKEY());
        }


//        Const.CHA_WKDAY = "1";// for test
        if (Const.CHA_WKDAY.length() > 0) {
            Log.d(TAG, "Const.CHA_WKDAY:" + Const.CHA_WKDAY);
            if (!Const.CHA_WKDAY.equals(listData.get(pos).getCODEKEY())) {
                changeRoute = true;
                int nextPos = -1;
                int prePos = -1;
                for (int i = pos; i < listData.size(); i++) {
                    CodeH obj = listData.get(i);
                    if (obj.getCODEKEY().trim().equals(Const.CHA_WKDAY)) {
                        nextPos = i;
                        break;
                    }
                }
                for (int i = pos; i >= 0; i--) {
                    CodeH obj = listData.get(i);
                    if (obj.getCODEKEY().trim().equals(Const.CHA_WKDAY)) {
                        prePos = i;
                        break;
                    }
                }

                if (prePos != -1 && nextPos != -1) {
                    if (nextPos < prePos) {
                        pos = nextPos;

                    } else {
                        pos = prePos;

                    }
                } else if (prePos != -1) {
                    pos = prePos;

                } else if (nextPos != -1) {
                    pos = nextPos;

                }

                Log.d(TAG, "nextPos:" + nextPos);
                Log.d(TAG, "prePos:" + prePos);
            }
        }


        tvDayOfWeek = (Spinner) v.findViewById(R.id.tvDayOfWeek);
        tvDayOfWeek.setAdapter(adapterSpinner);
        tvDayOfWeek.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                NAME_ROUTE = listData.get(i).getCODEDESC();
                if (RouteNo == 0) {
                    if (i == 3) KEY_ROUTE = listData.get(0).getCODEKEY();
                    else if (i == 4) KEY_ROUTE = listData.get(1).getCODEKEY();
                    else if (i == 5) KEY_ROUTE = listData.get(2).getCODEKEY();
                    else KEY_ROUTE = listData.get(i).getCODEKEY();
                } else {
                    KEY_ROUTE = listData.get(i).getCODEKEY();
                }
                LIST.clear();
                arrayList.clear();
                inforShopDetailsList.clear();
                Log.d(TAG, "KEY_ROUTE:" + KEY_ROUTE);
                if (first != 0) {
                    send_112(dataLogin, DATE);
                    if (changeRoute && first == 1) {
                        showDialogChangeRoute();
                    }
                }
                first++;
                MainActivity.instance.removeUpdateGPS();
                new API_111_(dataLogin, DATE).execute();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        tvDayOfWeek.setSelection(pos);
        Log.d(TAG, "pos:" + pos);


    }

    boolean changeRoute = false;
//    int checkPositionAddNew(List<Route> arrayList) {
//        int a = 0;
//        for (int i = 0; i < arrayList.size(); i++) {
//            if (arrayList.get(i).getOrder().equals("1"))
//                a = i + 1;
//        }
//        return a;
//    }

    int checkPositionAddNew() {
        int a = 0;

        if (LIST != null && LIST.size() > 0) {
            for (int i = 0; i < LIST.size(); i++) {
                RouteObject ob = LIST.get(i);
                if (!ob.getORDER().equals("0") || !ob.getORDER().equals("0.0")) {
                    a = i + 1;
                }
            }
        }

        return a;
    }


    public void AfterNewShop(int pos, String data) {
        LIST = mAdapter.getLIST();
        InforShopDetails isd = new Gson().fromJson(data, InforShopDetails.class);
        int posAdd = 0;
        DatabaseHandler db = new DatabaseHandler(getActivity());
        List<API_110> api_110List = db.getListAPI_110();
        int idshop = 0;
        if (api_110List != null && api_110List.size() > 0) {
            if (Const.API_110_contains(api_110List, DATE, KEY_ROUTE)) {
                RouteList listInforShop = null;
                for (int i = 0; i < api_110List.size(); i++) {
                    if (api_110List.get(i).getDATE().equalsIgnoreCase(DATE)
                            && api_110List.get(i).getKEY_ROUTE().equalsIgnoreCase(KEY_ROUTE)) {
                        listInforShop = new Gson().fromJson(api_110List.get(i).getDATA(), RouteList.class);
                        idshop = api_110List.get(i).getId();
                        if (listInforShop.getRESULT() == 0) {

//                            Const.longInfo(TAG,new Gson().toJson(arrayList));

                            posAdd = checkPositionAddNew();
                            Log.d(TAG, "posadd:" + posAdd);
                            Route route = new Route();
//                            route.setV1("" + posAdd);
                            route.setV1("0");
                            route.setV2(isd.getV1());
                            route.setV3(isd.getV3());
                            route.setV4("0");
                            route.setV5("0.0");
                            route.setV6("0.0");
                            route.setOrder("0");
                            route.setIsClose(false);
                            route.setIsClose(false);
                            route.setIsTouch(false);
                            listInforShop.getLIST().add(posAdd, route);
                            db.update_110(new Gson().toJson(listInforShop), idshop);
                        }
                        break;
                    }
                }
            }
        }

        List<API_112> listAPI_112 = db.getAllContacts();
        if (listAPI_112 == null || listAPI_112.size() == 0) {
        } else {
            if (Const.API_112_contains(listAPI_112, DATE, KEY_ROUTE)) {
                ListInforShop listInforShop = null;
                for (int i = 0; i < listAPI_112.size(); i++) {
                    if (listAPI_112.get(i).getDATE().equalsIgnoreCase(DATE)
                            && listAPI_112.get(i).getWEEK().equalsIgnoreCase(KEY_ROUTE)) {
                        listInforShop = new Gson().fromJson(listAPI_112.get(i).getDATA(), ListInforShop.class);
                        listInforShop.getLIST().add(posAdd, isd);
                        db.updateContact(new Gson().toJson(listInforShop), idshop);
                        break;
                    }
                }
            } else {
            }
        }

//        LIST = mAdapter.getLIST();
        RouteObject ob = new RouteObject();
        ob.setInforShopDetails(isd);
        ob.setSEQ("0");
        ob.setSHOPCODE(isd.getV1());
        Log.d(TAG, "V3:" + isd.getV3());
        ob.setSHOPNAME(isd.getV3());
        String tvAdd = "";
        if (isd.getV29().length() == 10) {
            String CODE = isd.getV29();
            tvAdd = isd.getV7() + ", "
                    + getStreet(isd.getV6())
                    + ", "
                    + getCity(CODE.substring(0, 2), CODE.substring(2, 5), CODE.substring(5, 10));
        } else {
            tvAdd = isd.getV7() + ", "
                    + getStreet(isd.getV6())
                    + ", "
                    + getCity(isd.getV4(), isd.getV5(), "");
        }

        ob.setADDRESS(tvAdd);
        ob.setMAG("0.0");
        ob.setMTD("0.0");

        ob.setOMI("N");
        ob.setORDER("0");
        ob.setNONSHOP(getNonShop(isd.getV1()));
        ob.setOWNER(isd.getV26());
        ob.setTEL(isd.getV28());
        ob.setLON(isd.getV14());
        ob.setLAT(isd.getV15());
        String grade = "";
        if (MainActivity.TEAM.equals(Const.PIE_TEAM))
            grade = isd.getV8();
        else if (MainActivity.TEAM.equals(Const.SNACK_TEAM))
            grade = isd.getV9();
        else grade = isd.getV10();
        ob.setGRADE(grade);
        String type = "";
        if (isd.getV2().equals("0")) {
            type = "PS";
        } else {
            type = "DS";
        }
        ob.setSHOPTYPE(type);
        ob.setVisit(false);
        ob.setClose(false);
        if (posAdd < LIST.size())
            LIST.add(posAdd, ob);
        else LIST.add(ob);
        mAdapter.notifyDataSetChanged();
    }

    public void reloadListShop(int pos, String data) {
        InforShopDetails isd = new Gson().fromJson(data, InforShopDetails.class);
        String CODE = isd.getV29();
        String tvAdd = "";
        if (isd.getV29().length() == 10) {
            tvAdd = isd.getV7() + ", "
                    + getStreet(isd.getV6())
                    + ", "
                    + getCity(CODE.substring(0, 2), CODE.substring(2, 5), CODE.substring(5, 10));
        } else {
            tvAdd = isd.getV7() + ", "
                    + getStreet(isd.getV6())
                    + ", "
                    + getCity(isd.getV4(), isd.getV5(), "");
        }
        DatabaseHandler db = new DatabaseHandler(getActivity());
        List<API_112> listAPI_112 = db.getAllContacts();
        int id = 0;
        if (listAPI_112 != null && listAPI_112.size() > 0) {
            for (int i = 0; i < listAPI_112.size(); i++) {
                id = listAPI_112.get(i).getId();
                if (DATE.equals(listAPI_112.get(i).getDATE()) && KEY_ROUTE.equals(listAPI_112.get(i).getWEEK())) {
                    ListInforShop routeList = new Gson().fromJson(listAPI_112.get(i).getDATA(), ListInforShop.class);
                    List<InforShopDetails> LIST = routeList.getLIST();
                    if (LIST != null && LIST.size() > 0) {
                        for (int k = 0; k < LIST.size(); k++) {
                            InforShopDetails details = LIST.get(k);
                            if (details.getV1().equals(MainActivity.SHOP_CODE)) {
                                LIST.set(k, isd);
                                db.updateContact(new Gson().toJson(routeList), id);
                                break;
                            }
                        }
                    }
                    break;
                }
            }
        }
        mAdapter.updateAfterEdit(pos, isd, tvAdd);
    }


    private List<OrderNonShopList> getListNonShopMonth = new ArrayList<>();

    void getList() {
        DatabaseHandler db = new DatabaseHandler(getActivity());
        List<API_120> api_120List = db.getListAPI_120();
        if (api_120List != null && api_120List.size() > 0) {
            API_120 api_120 = api_120List.get(api_120List.size() - 1);
            OrderNonShopListAPI routeList = new Gson().fromJson(api_120.getDATA(), OrderNonShopListAPI.class);
            getListNonShopMonth = routeList.getLIST();
        }
        checkLoadFinish = 1;
    }

    public static String NAME_ROUTE = "";
    public static String KEY_ROUTE = "1";

    @Override
    public void onPause() {
        super.onPause();
        updateNoti = false;
        new PrefManager(getActivity()).setUpdatePosition("");
    }

    public void updateLocation(String s) {

        SavePositionLocaiton savePositionLocaiton = new Gson().fromJson(s, SavePositionLocaiton.class);
        String V4 = savePositionLocaiton.getADDR1();
        String V5 = savePositionLocaiton.getADDR2();
        String V6 = savePositionLocaiton.getADDR3();
        String tvAdd = MainActivity.V7 + ", "
                + getStreet(MainActivity.V6)
                + ", "
                + getCity(V4, V5, V6);
        MainActivity.ADDRESS = tvAdd;

        mAdapter.updateLocation(s, DATE, KEY_ROUTE, tvAdd);
        Log.d(TAG, "updateLocation");
        reloadRouteShop(DATE, s);
    }

    String V20 = "", V21 = "", V22 = "", V23 = "", V24 = "", V25 = "";

    void reLoadAfterOrder(final int position) {
        progressbar.setVisibility(View.VISIBLE);
        Log.d(TAG, "reLoadAfterOrder");
        new HttpRequest(getActivity()).new API_112(dataLogin, DATE, new IF_112() {
            @Override
            public void onSuccess(List<InforShopDetails> LIST) {
                if (LIST != null && LIST.size() > 0) {
                    int id = 0;
                    DatabaseHandler db = new DatabaseHandler(getActivity());
                    List<API_112> listAPI_112 = db.getAllContacts();
                    if (listAPI_112 != null && listAPI_112.size() > 0) {
                        for (int i = 0; i < listAPI_112.size(); i++) {
                            if (DATE.equals(listAPI_112.get(i).getDATE()) && KEY_ROUTE.equals(listAPI_112.get(i).getWEEK())) {
                                ListInforShop routeList = new Gson().fromJson(listAPI_112.get(i).getDATA(), ListInforShop.class);
                                id = listAPI_112.get(i).getId();
                                InforShopDetails isd = null;
                                for (int k = 0; k < LIST.size(); k++) {
                                    isd = LIST.get(k);
                                    if (isd.getV1().equals(MainActivity.SHOP_CODE)) {
                                        V20 = isd.getV20();
                                        V21 = isd.getV21();
                                        V22 = isd.getV22();
                                        V23 = isd.getV23();
                                        V24 = isd.getV24();
                                        V25 = isd.getV25();
                                        List<InforShopDetails> list_offline = routeList.getLIST();
                                        for (int bb = 0; bb < list_offline.size(); bb++) {
                                            if (list_offline.get(bb).getV1().equals(MainActivity.SHOP_CODE)) {
                                                list_offline.get(bb).setV20(V20);
                                                list_offline.get(bb).setV21(V21);
                                                list_offline.get(bb).setV22(V22);
                                                list_offline.get(bb).setV23(V23);
                                                list_offline.get(bb).setV24(V24);
                                                list_offline.get(bb).setV25(V25);
                                                db.updateContact(new Gson().toJson(routeList), id);
                                                updateOrderInfor(position, V20, V21, V22, V23, V24, V25);
                                                break;
                                            }
                                        }
                                        break;
                                    }
                                }
                                break;
                            }
                        }
                    }
                }
                progressbar.setVisibility(View.GONE);
            }

            @Override
            public void onFail() {
                progressbar.setVisibility(View.GONE);
                Log.e(TAG, "onFail");

            }
        }).execute();
    }

    boolean updateNoti = true;
    String DATA_116 = "", DATA_117 = "", DATA_115 = "", DATA_125 = "";
    int ID_116 = 0, ID_117 = 0, ID_115 = 0, ID_125 = 0;

    void reSend_115() {
        check_115 = false;
        DATA_115 = "";
        DatabaseHandler db = new DatabaseHandler(getActivity());
        List<RESEND_115> List115 = db.getList115();
        if (List115 != null && List115.size() > 0) {
            for (int i = 0; i < List115.size(); i++) {
                ID_115 = List115.get(i).getID();
                DATA_115 = List115.get(i).getDATA();
            }
        }
        if (DATA_115.length() > 0 && DATA_115.startsWith("{")) {
            Check115 obj = new Gson().fromJson(DATA_115, Check115.class);
            if (obj != null) {
                if (obj.getEMPID().length() == 0
                        || obj.getISINSHOP().length() == 0
                        || obj.getWKDAY().length() == 0
                        || obj.getSEQ().length() == 0
                        || obj.getCUSTCD().length() == 0
                        || obj.getISOPEN().length() == 0
                        || obj.getDATE().length() == 0
                        || obj.getMODE().length() == 0) {
                    DATA_115 = "";
                } else {
                    Log.d(TAG, "Check115 ok");
                }
            }
        }

        if (DATA_115.length() > 0) {
            API_115();
            Log.d(TAG, "START RESEND 115:" + DATA_115);
        } else {
            check_115 = true;
            Log.d(TAG, "END RESEND 115");
        }
    }

    void API_115() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int date = calendar.get(Calendar.DAY_OF_MONTH);
        String DAY = year + Const.formatFullDate(month) + Const.formatFullDate(date);
        PARSE_115 parse_115 = new Gson().fromJson(DATA_115, PARSE_115.class);
        if (!DAY.equals(parse_115.getDATE())) {
            Log.d(TAG, "DELETE DATA 115: TODAY:" + DAY + " DATEDATA:" + parse_115.getDATE());
            DatabaseHandler db = new DatabaseHandler(getActivity());
            db.DELETE_115_ID(ID_115);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    reSend_115();
                }
            }, MainActivity.SEARCHINTERVAL * 1000);
        } else {
            new HttpRequest(getActivity()).new API_115(MainActivity.dataLogin, DATA_115, new IF_115() {
                @Override
                public void onSuccess() {
                    DatabaseHandler db = new DatabaseHandler(getActivity());
                    db.DELETE_115_ID(ID_115);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            reSend_115();
                        }
                    }, MainActivity.SEARCHINTERVAL * 1000);
                    Log.d(TAG, "RESEND 115 OK");
                }

                @Override
                public void onFail() {
                    Log.d(TAG, "RESEND 115 FAIL");
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            API_115();
                        }
                    }, MainActivity.SEARCHINTERVAL * 1000);
                }
            }).execute();
        }
    }

    void reSend_116() {
        check_116 = false;
        DATA_116 = "";
        DatabaseHandler db = new DatabaseHandler(getActivity());
        List<RESEND_116> List116 = db.getList116();
        if (List116 != null && List116.size() > 0) {
            for (int i = 0; i < List116.size(); i++) {
                ID_116 = List116.get(i).getID();
                DATA_116 = List116.get(i).getDATA();
            }
        }
        if (DATA_116.length() > 0) {
            API_116();
            Log.d(TAG, "START RESEND 116:" + DATA_116);
        } else {
            check_116 = true;
            Log.d(TAG, "END RESEND 116");
        }
    }

    void reSend_117() {
        check_117 = false;
        DATA_117 = "";
        DatabaseHandler db = new DatabaseHandler(getActivity());
        List<RESENDNEWSHOP> List117 = db.getLISTNEWSHOP();
        if (List117 != null && List117.size() > 0) {
            for (int i = 0; i < List117.size(); i++) {
                ID_117 = List117.get(i).getID();
                DATA_117 = List117.get(i).getDATA();
            }
        }
        if (DATA_117.length() > 0) {
            API_117();
            Log.d(TAG, "START RESEND 117:" + DATA_117);
        } else {
            check_117 = true;
            Log.d(TAG, "END RESEND 117");
        }
    }

    String SHOP_ID_125 = "";
    String DATE_125 = "";

    void reSend_125() {
        check_125 = false;
        DATA_125 = "";
        SHOP_ID_125 = "";
        DATE_125 = "";
        List_125.clear();
        DatabaseHandler db = new DatabaseHandler(getActivity());
        List<RESEND_125> List125 = db.getList125();
        if (List125 != null && List125.size() > 0) {
            for (int i = 0; i < List125.size(); i++) {
                ID_125 = List125.get(i).getID();
                DATA_125 = List125.get(i).getDATA();
                SHOP_ID_125 = List125.get(i).getSHOPID();
                DATE_125 = List125.get(i).getDATE();
            }
        }
        if (DATA_125.length() > 0) {
            Type type = new TypeToken<List<String>>() {
            }.getType();
            List_125 = new Gson().fromJson(DATA_125, type);
            if (List_125 != null && List_125.size() > 0 && SHOP_ID_125.length() > 0 && DATE_125.length() > 0) {
                API_125();
                Log.d(TAG, "START RESEND 125:" + DATA_125);
            } else {
                db.DELETE_125_ID(ID_125);
                DATA_125 = "";
                List_125 = new ArrayList<>();
                reSend_125();
            }
        } else {
            check_125 = true;
            Log.d(TAG, "END RESEND 125");
        }
    }

    void API_125() {
        new HttpRequest(getActivity()).new API_125(dataLogin, List_125.get(count_125), new IF_124() {
            @Override
            public void onSuccess() {
                count_125++;
                if (count_125 >= List_125.size()) {
                    //finish call api
                    Log.d(TAG, "API 125 OK");
                    count_125 = 0;
                    List_125.clear();
                    DatabaseHandler db = new DatabaseHandler(getActivity());
                    db.DELETE_125_ID(ID_125);
                } else {
                    API_125();
                }
            }

            @Override
            public void onFail() {
                Log.e(TAG, "API 125 Fail...");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        API_125();
                    }
                }, MainActivity.SEARCHINTERVAL * 1000);
            }
        }).execute();
    }

    void API_117() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int date = calendar.get(Calendar.DAY_OF_MONTH);
        String DAY = year + Const.formatFullDate(month) + Const.formatFullDate(date);
        PARSE_117 parse_117 = new Gson().fromJson(DATA_117, PARSE_117.class);
        if (!DAY.equals(parse_117.getCDATETIME().substring(0, 8))) {
            Log.d(TAG, "DELETE DATA 117: TODAY:" + DAY + " DATEDATA:" + parse_117.getCDATETIME().substring(0, 8));
            DatabaseHandler db = new DatabaseHandler(getActivity());
            db.DELETE_117_ID(ID_117);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    reSend_117();
                }
            }, MainActivity.SEARCHINTERVAL * 1000);
        } else {
            new HttpRequest(getActivity()).new API_117(dataLogin, DATA_117, new IF_117() {
                @Override
                public void onSuccess(String ID) {
                    Log.d(TAG, "API 117 OK");
                    DatabaseHandler db = new DatabaseHandler(getActivity());
                    db.DELETE_117_ID(ID_117);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            reSend_117();
                        }
                    }, MainActivity.SEARCHINTERVAL * 1000);
                }

                @Override
                public void onFail() {
                    Log.e(TAG, "API 117 Fail...");
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            API_117();
                        }
                    }, MainActivity.SEARCHINTERVAL * 1000);
                }
            }).execute();
        }
    }

    void API_116() {
        new HttpRequest(getActivity()).new API_116(MainActivity.dataLogin, DATA_116, new IF_100() {
            @Override
            public void onSuccess() {
                DatabaseHandler db = new DatabaseHandler(getActivity());
                db.DELETE_116_ID(ID_116);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        reSend_116();
                    }
                }, MainActivity.SEARCHINTERVAL * 1000);
                Log.d(TAG, "RESEND 116 OK");
            }

            @Override
            public void onFail() {
                Log.d(TAG, "RESEND 116 FAIL");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        API_116();
                    }
                }, MainActivity.SEARCHINTERVAL * 1000);
            }
        }).execute();

    }

    boolean check_115 = true;
    boolean check_116 = true;
    boolean check_117 = true;
    boolean check_125 = true;

    @Override
    public void onResume() {
        super.onResume();
        updateNoti = true;
        PrefManager pref = new PrefManager(getActivity());
        pref.setRouteStockPie("");
        pref.setRouteStockGum("");
        pref.setRouteStockSnack("");
        pref.setRouteOrderPie("");
        pref.setRouteOrderGum("");
        pref.setRouteOrderSnack("");
        pref.setRouteDisplayPie("");
        pref.setRouteDisplaySnack("");
        if (check_115) {
            try {
                reSend_115();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (check_116) {
            try {
                reSend_116();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (check_117) {
            try {
                reSend_117();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

//        if (check_125) {
//            try {
//                reSend_125();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }

        boolean isUpdate = pref.isUpdateVisitShop();

        if (isUpdate) {
            if (updateNoti) {
                int count = GetDataNotSend();
                if (count == 0) {
                    MainActivity.instance.hideTV();
                } else {
                    MainActivity.instance.showTV("" + count);
                }
            }
            if (flagReSend) {
                try {
                    ReSendData();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            try {
                String today = RouteFragment.DATE;
                SaveInputData obSave = ObjectSave(today, MainActivity.SHOP_ID);
                MainActivity.STATUS_ORDER = true;
                if (obSave.getIS_124().equals("1") && obSave.getDATA_124().length() > 10)
                    reLoadAfterOrder(MainActivity.SHOP_POSITION);
                updateOrderList(MainActivity.SHOP_POSITION, obSave);
                pref.setUpdateVisitShop(false);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }


    boolean flagReSend = true; // if true: stop check

    void ReSendData() {
        if (updateNoti) {
            int count = GetDataNotSend();
            if (count == 0) {
                MainActivity.instance.hideTV();
            } else {
                MainActivity.instance.showTV("" + count);
            }
        }
        flagReSend = false;
        if (Const.checkInternetConnection(getActivity())) {
            if (CheckDataNotSend()) {
                List_124.clear();
                data115 = "";
                data118 = "";
                data119 = "";
                countOrder = 0;
                URL_IMG = "";
                PATH_IMG = "";
                Log.d(TAG, "start resend");

                initDBreSend();
                reSendBackground();


            } else {
                flagReSend = true;
                Log.d(TAG, "dont have data");
            }
        } else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    ReSendData();
                }
            }, MainActivity.SEARCHINTERVAL * 1000);
        }

    }

    void initDBreSend() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;
        int date = cal.get(Calendar.DATE);
        String today = year + Const.setFullDate(month) + Const.setFullDate(date);
        db = new DatabaseHandler(getActivity());
        List<SaveInputData> ListOrder = db.getListOrder();
        if (ListOrder != null && ListOrder.size() > 0) {
            for (int i = 0; i < ListOrder.size(); i++) {
                if (ListOrder.get(i).getDATE().equals(today) &&
                        ((!ListOrder.get(i).getIS_124().equals("1") && ListOrder.get(i).getDATA_124().length() > 10) ||
                                (ListOrder.get(i).getURL_IMG().length() < 10 && ListOrder.get(i).getDATA_124().length() > 10) ||
                                (!ListOrder.get(i).getIS_115().equals("1") && ListOrder.get(i).getDATA_115().length() > 0) ||
                                (!ListOrder.get(i).getIS_118().equals("1") && ListOrder.get(i).getDATA_118().length() > 0) ||
                                (!ListOrder.get(i).getIS_119().equals("1") && ListOrder.get(i).getDATA_119().length() > 0))) {

                    idOrder = ListOrder.get(i).getId();
                    countOrder = Integer.parseInt(ListOrder.get(i).getCOUNT_124());
                    URL_IMG = ListOrder.get(i).getURL_IMG();
                    PATH_IMG = ListOrder.get(i).getFILE_PATH_IMG();
                    DATE_126 = ListOrder.get(i).getDATE();
                    SHOP_126 = ListOrder.get(i).getSHOPID();

                    if (!ListOrder.get(i).getIS_124().equals("1") && ListOrder.get(i).getDATA_124().length() > 10) {
                        Type type = new TypeToken<List<String>>() {
                        }.getType();
                        Log.d(TAG, "ListOrder.get(i).getDATA_124():" + ListOrder.get(i).getDATA_124());
                        List_124 = new Gson().fromJson(ListOrder.get(i).getDATA_124(), type);
                    }
                    if (!ListOrder.get(i).getIS_115().equals("1") && ListOrder.get(i).getDATA_115().length() > 0) {
                        data115 = ListOrder.get(i).getDATA_115();

                        if (data115.length() > 0 && data115.startsWith("{")) {
                            Check115 obj = new Gson().fromJson(data115, Check115.class);
                            if (obj != null) {
                                if (obj.getEMPID().length() == 0
                                        || obj.getISINSHOP().length() == 0
                                        || obj.getWKDAY().length() == 0
                                        || obj.getSEQ().length() == 0
                                        || obj.getCUSTCD().length() == 0
                                        || obj.getISOPEN().length() == 0
                                        || obj.getDATE().length() == 0
                                        || obj.getMODE().length() == 0) {
                                    data115 = "";
                                } else {
                                    Log.d(TAG, "Check115 ok");
                                }
                            }
                        }


                    }
                    if (!ListOrder.get(i).getIS_118().equals("1") && ListOrder.get(i).getDATA_118().length() > 0) {
                        data118 = ListOrder.get(i).getDATA_118();
                    }
                    if (!ListOrder.get(i).getIS_119().equals("1") && ListOrder.get(i).getDATA_119().length() > 0) {
                        data119 = ListOrder.get(i).getDATA_119();
                    }
                    Log.d(TAG, "countOrder:" + countOrder);
                    Log.d(TAG, "URL_IMG:" + URL_IMG);
                    Log.d(TAG, "PATH_IMG:" + PATH_IMG);
                    Log.d(TAG, "List_124:" + List_124.size());

                    Log.d(TAG, "data115:" + data115);
                    Log.d(TAG, "data118:" + data118);
                    Log.d(TAG, "data119:" + data119);
                    break;
                }
            }
        }
    }

    String DATE_126 = "";
    String SHOP_126 = "";

    void reSendBackground() {

        if (Const.checkInternetConnection(getActivity())) {
            Log.d(TAG, "PATH_IMG:" + PATH_IMG);
            if (PATH_IMG.contains(Const.PATH_IMG)) {
                Log.d(TAG, "contains");
                new UploadFileToServer(PATH_IMG).execute();
            } else {
                Log.d(TAG, "dont contains");
                API_126();
            }
        } else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    reSendBackground();
                }
            }, MainActivity.SEARCHINTERVAL * 1000);
        }


    }

    private final static int MSG_UPDATE_112 = 112;
    List<ObjectA0128> list128 = new ArrayList<>();

    public void send_112(final DataLogin dataLogin, final String USE_DATE) {
        list128.clear();

        DatabaseHandler db = new DatabaseHandler(getActivity());
        List<DataBase128> lst128 = db.getList128();

        if (lst128 != null && lst128.size() > 0) {

            for (DataBase128 obj : lst128) {
                if (obj.getCDATE().equals(USE_DATE)) {

                    ObjectA0128 ob = new ObjectA0128(obj.getCUSTCD_128(), obj.getOMI_128(), obj.getORDERQTY_128());
                    list128.add(ob);
                }
            }
        } else {

        }

        reload();
        new Thread() {
            @Override
            public void run() {
                getRouteShop(dataLogin, USE_DATE);
                handler.obtainMessage(MSG_UPDATE_112, "").sendToTarget();
            }
        }.start();


//        new HttpRequest(getActivity()).new A0128(MainActivity.dataLogin, RouteFragment.DATE, new IF_128() {
//            @Override
//            public void onSuccess(List<ObjectA0128> list) {
//                list128 = list;
//                reload();
//                new Thread() {
//                    @Override
//                    public void run() {
//                        getRouteShop(dataLogin, USE_DATE);
//                        handler.obtainMessage(MSG_UPDATE_112, "").sendToTarget();
//                    }
//                }.start();
//            }
//
//            @Override
//            public void onFail() {
//                list128.clear();
//                reload();
//                new Thread() {
//                    @Override
//                    public void run() {
//                        getRouteShop(dataLogin, USE_DATE);
//                        handler.obtainMessage(MSG_UPDATE_112, "").sendToTarget();
//                    }
//                }.start();
//            }
//        }).execute();


    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case MSG_UPDATE_112:
                    initRC();
                    break;
            }
        }
    };
    List<String> List_124 = new ArrayList<>();
    List<String> List_125 = new ArrayList<>();
    int countOrder = 0;
    int count_125 = 0;
    String URL_IMG = "", PATH_IMG = "", data115 = "", data118 = "", data119 = "";
    int idOrder = 0;
    DatabaseHandler db;

    int GetDataNotSend() {
        int a = 0;
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;
        int date = cal.get(Calendar.DATE);
        String today = year + Const.setFullDate(month) + Const.setFullDate(date);
        DatabaseHandler db = new DatabaseHandler(getActivity());
        List<SaveInputData> ListOrder = db.getListOrder();
        if (ListOrder != null && ListOrder.size() > 0) {
            for (int i = 0; i < ListOrder.size(); i++) {
                if (ListOrder.get(i).getDATE().equals(today) &&
                        ((!ListOrder.get(i).getIS_124().equals("1") && ListOrder.get(i).getDATA_124().length() > 10) ||
                                (ListOrder.get(i).getURL_IMG().length() < 10 && ListOrder.get(i).getDATA_124().length() > 10) ||
                                (!ListOrder.get(i).getIS_115().equals("1") && ListOrder.get(i).getDATA_115().length() > 0) ||
                                (!ListOrder.get(i).getIS_118().equals("1") && ListOrder.get(i).getDATA_118().length() > 0) ||
                                (!ListOrder.get(i).getIS_119().equals("1") && ListOrder.get(i).getDATA_119().length() > 0))) {
                    a++;

                }
            }
        }
        return a;
    }

    boolean CheckDataNotSend() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;
        int date = cal.get(Calendar.DATE);
        String today = year + Const.setFullDate(month) + Const.setFullDate(date);
        db = new DatabaseHandler(getActivity());
        List<SaveInputData> ListOrder = db.getListOrder();
        if (ListOrder != null && ListOrder.size() > 0) {
            for (int i = 0; i < ListOrder.size(); i++) {
                if (ListOrder.get(i).getDATE().equals(today) &&
                        ((!ListOrder.get(i).getIS_124().equals("1") && ListOrder.get(i).getDATA_124().length() > 10) ||
                                (ListOrder.get(i).getURL_IMG().length() < 10 && ListOrder.get(i).getDATA_124().length() > 10) ||
                                (!ListOrder.get(i).getIS_115().equals("1") && ListOrder.get(i).getDATA_115().length() > 0) ||
                                (!ListOrder.get(i).getIS_118().equals("1") && ListOrder.get(i).getDATA_118().length() > 0) ||
                                (!ListOrder.get(i).getIS_119().equals("1") && ListOrder.get(i).getDATA_119().length() > 0))) {
                    return true;
                }
            }
        }
        return false;
    }

    void call_API_124() {
        if (List_124.size() > 0 && countOrder < List_124.size()) {

            new HttpRequest(getActivity()).new API_124(dataLogin, List_124.get(countOrder), new IF_124() {
                @Override
                public void onSuccess() {
                    countOrder++;
                    db.updateOrderCount("" + countOrder, idOrder);
                    if (countOrder >= List_124.size()) {
                        callAPI();
                        db.updateOrder124("1", idOrder);
                    } else {
                        call_API_124();
                    }
                }

                @Override
                public void onFail() {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            call_API_124();
                        }
                    }, MainActivity.SEARCHINTERVAL * 1000);
                }
            }).execute();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        } else {
            db.updateOrder124("1", idOrder);
            callAPI();
        }
    }

    void callAPI() {

        if (data118.length() > 0) {
            new HttpRequest(getActivity()).new API_118(dataLogin, data118, new IF_118() {
                @Override
                public void onSuccess() {
                    db.updateOrder118("1", idOrder);

                    callAPIVisit();
                }

                @Override
                public void onFail() {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            callAPI();
                        }
                    }, MainActivity.SEARCHINTERVAL * 1000);
                }
            }).execute();

        } else {
            db.updateOrder118("1", idOrder);
            callAPIVisit();
        }
    }

    void callAPIVisit() {
        Log.d(TAG, "data115:" + data115);


        if (data115.length() > 0) {
            new HttpRequest(getActivity()).new API_115(dataLogin, data115, new IF_115() {
                @Override
                public void onSuccess() {
                    db.updateOrder115("1", idOrder);
                    getDataDisplayInput();
                }

                @Override
                public void onFail() {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            callAPIVisit();
                        }
                    }, MainActivity.SEARCHINTERVAL * 1000);
                }
            }).execute();
        } else {
            db.updateOrder115("1", idOrder);
            getDataDisplayInput();
        }


    }

    void DELETE_PICTURE() {
        File file = new File(PATH_IMG);
        if (file.exists()) {
            file.delete();
        }
    }


    void API_126() {
        if (DATE_126.length() > 0 && SHOP_126.length() > 0) {
            Map<String, Object> map = new HashMap<>();
            map.put("CDATE", DATE_126);
            map.put("CUSTCD", SHOP_126);
            String paramSend = new Gson().toJson(map);
            new HttpRequest(getActivity()).new API_126(dataLogin, paramSend, new IF_124() {
                @Override
                public void onSuccess() {
                    call_API_124();
                }

                @Override
                public void onFail() {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            API_126();
                        }
                    }, MainActivity.SEARCHINTERVAL * 1000);
                }
            }).execute();
        } else {

        }

    }

    void getDataDisplayInput() {
        if (data119.length() > 0) {
            new HttpRequest(getActivity()).new API_119(dataLogin, data119, new IF_119() {
                @Override
                public void onSuccess() {
                    db.updateOrder119("1", idOrder);
                    DELETE_PICTURE();
                    reSend_125();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            ReSendData();
                        }
                    }, MainActivity.SEARCHINTERVAL * 1000);
                }

                @Override
                public void onFail() {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            getDataDisplayInput();
                        }
                    }, MainActivity.SEARCHINTERVAL * 1000);
                }
            }).execute();

        } else {
            reSend_125();
            db.updateOrder119("1", idOrder);
            DELETE_PICTURE();

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    ReSendData();
                }
            }, MainActivity.SEARCHINTERVAL * 1000);
        }
    }


    int isScale = 0;

    public class UploadFileToServer extends AsyncTask<String, Integer, String> {
        String filePath;


        public UploadFileToServer(String filePath) {
            this.filePath = filePath;


        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
        }

        @Override
        protected String doInBackground(String... params) {
            return handleBackground(params);
        }

        protected String handleBackground(String... params) {
            return uploadFileToServer(filePath, Const.UpLoadImage, isScale);

        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (result.startsWith("Error") || result.startsWith("error")) {

                Log.d(TAG, "result FAIL");
//                Toast.makeText(getActivity(), "resend sign picture " + getResources().getString(R.string.cannotuploadsign), Toast.LENGTH_LONG).show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        reSendBackground();
                    }
                }, MainActivity.SEARCHINTERVAL * 1000);
            } else {

                Log.d(TAG, "result OK");
//                Toast.makeText(getActivity(), "resend  sign picture ok", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "result:" + result);
                result = result.substring(1, result.length() - 1);

                URL_IMG = Const.serverUrl + result;
                Log.d(TAG, "URL_IMG:" + URL_IMG);
                if (List_124.size() > 0) {

                    String url = new Gson().toJson(List_124);
                    url = url.replace(Const.NO_PIC, URL_IMG);
                    Type type = new TypeToken<List<String>>() {
                    }.getType();
                    Log.d(TAG, "List_124:" + List_124);

                    List_124 = new Gson().fromJson(url, type);


                }
                db.updateOrderIMG(URL_IMG, idOrder);
                API_126();
            }
        }
    }

    public static File file;
    private static int fileSize;

    public static String uploadFileToServer(String filename, String targetUrl, int Scale) {
        String response = "error";
        HttpURLConnection connection = null;
        DataOutputStream outputStream = null;

        String pathToOurFile = filename;
        String responseString = null;
        String urlServer = targetUrl;
        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";

        int bytesRead, bytesAvailable, bufferSize;
        byte[] buffer;
        int maxBufferSize = 1 * 1024;
        FileInputStream fileInputStream = null;
        try {
            if (Scale == 0) {
                fileInputStream = new FileInputStream(new File(
                        pathToOurFile));
            } else {
                Bitmap bitmap = Const.createScaleBitmap(pathToOurFile, 100);
                String timeStamp = new SimpleDateFormat(Const.DATE_FORMAT_PICTURE,
                        Locale.getDefault()).format(new Date());
                String file_path = Environment.getExternalStorageDirectory().getAbsolutePath() +
                        "/Scale";
                File dir = new File(file_path);
                if (!dir.exists())
                    dir.mkdirs();
                file = new File(dir, "scale" + timeStamp + Const.IMAGE_JPG);
                FileOutputStream fOut = new FileOutputStream(file);
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
                fOut.flush();
                fOut.close();
                fileInputStream = new FileInputStream(file);
            }

            URL url = new URL(urlServer);
            connection = (HttpURLConnection) url.openConnection();
            // Allow Inputs & Outputs

            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setUseCaches(false);
            connection.setChunkedStreamingMode(1024);
            // Enable POST method
            connection.setRequestMethod("POST");
//            connection.setConnectTimeout(Const.TIME_OUT_UPLOAD_IMAGE);
//            connection.setReadTimeout(Const.TIME_OUT_UPLOAD_IMAGE);
            connection.setRequestProperty("Connection", "Keep-Alive");
            connection.setRequestProperty("Content-Type",
                    "multipart/form-data; boundary=" + boundary);

            connection.setRequestProperty("filename", Const.PDA_SHOP_IMAGE);
            String id = MainActivity.SHOP_ID;
            connection.setRequestProperty("id", id);


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

            String connstr = null;
            connstr = "Content-Disposition: form-data; name=\"UploadFile\";filename=\""
                    + new URI(pathToOurFile).toASCIIString() + "\"" + lineEnd;
            outputStream.writeBytes(connstr);
            outputStream.writeBytes(lineEnd);

            bytesAvailable = fileInputStream.available();
            bufferSize = Math.min(bytesAvailable, maxBufferSize);
            buffer = new byte[bufferSize];
            bytesRead = fileInputStream.read(buffer, 0, bufferSize);
            System.out.println("Image length " + bytesAvailable + "");
            fileSize = bytesAvailable;
            try {
                while (bytesRead > 0) {
                    try {
                        outputStream.write(buffer, 0, bufferSize);
                    } catch (OutOfMemoryError e) {
                        e.printStackTrace();
                        response = "outofmemoryerror";
                        return response;
                    }
                    bytesAvailable = fileInputStream.available();
                    bufferSize = Math.min(bytesAvailable, maxBufferSize);
                    bytesRead = fileInputStream.read(buffer, 0, bufferSize);
                }
            } catch (Exception e) {
                e.printStackTrace();
                response = "error";
                return response;
            }
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
            fileInputStream.close();
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
            if (fileInputStream != null)
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
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

    public void showDialogDate(final Context context, final TextView tv) {
        String YESTERDAY = "";

        Calendar cal = Calendar.getInstance();
        int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;
        int date = cal.get(Calendar.DATE);
        final String TODAY = year + Const.formatFullDate(month) + Const.formatFullDate(date);
        //Sunday = 1, Monday = 2,...

        if (dayOfWeek == 2) {
            YESTERDAY = Const.getToday(-2);
        } else {
            YESTERDAY = Const.getToday(-1);
        }

        final Dialog dialog = new Dialog(context);
        dialog.getWindow().setTitleColor(Color.RED);

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialog_date);
        dialog.setCanceledOnTouchOutside(false);

        TextView tvMonth1 = (TextView) dialog.findViewById(R.id.tvMonth1);
        TextView tvMonth2 = (TextView) dialog.findViewById(R.id.tvMonth2);
        tvMonth1.setText("" + YESTERDAY.substring(0, 4) + "-" + YESTERDAY.substring(4, 6));
        tvMonth2.setText("" + TODAY.substring(0, 4) + "-" + TODAY.substring(4, 6));
        TextView tvpreDate = (TextView) dialog.findViewById(R.id.tvpreDate);
        tvpreDate.setText("" + YESTERDAY.substring(6, 8));
        TextView tvToday = (TextView) dialog.findViewById(R.id.tvToday);
        tvToday.setText("" + TODAY.substring(6, 8));
        TextView tvClose = (TextView) dialog.findViewById(R.id.tvClose);
        LinearLayout lnyesterday = (LinearLayout) dialog.findViewById(R.id.lnyesterday);
        LinearLayout lntoday = (LinearLayout) dialog.findViewById(R.id.lntoday);
        FrameLayout frCalendar = (FrameLayout) dialog.findViewById(R.id.frCalendar);
        frCalendar.setVisibility(View.GONE);
        tvClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        final String finalYESTERDAY = YESTERDAY;
        lnyesterday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!DATE.equals(finalYESTERDAY)) {
                    list128.clear();
                    LIST.clear();
                    DATE = finalYESTERDAY;
                    tv.setText(Const.formatDate(DATE));
                    send_112(dataLogin, DATE);
                }
                dialog.dismiss();
            }
        });
        lntoday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!DATE.equals(TODAY)) {
                    list128.clear();
                    LIST.clear();
                    DATE = TODAY;
                    tv.setText(Const.formatDate(DATE));
                    send_112(dataLogin, DATE);
                }
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}
