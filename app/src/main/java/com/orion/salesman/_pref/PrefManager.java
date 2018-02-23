package com.orion.salesman._pref;

import android.content.Context;
import android.content.SharedPreferences;

import com.orion.salesman._app.MyApplication;

/**
 * Created by maidinh on 15/8/2016.
 */
public class PrefManager {
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;

    // shared pref mode
    int PRIVATE_MODE = 0;

    // Shared preferences file name
    private static final String KEY_USER_LOGIN = "KEY_USER_LOGIN";
    private static final String PREF_NAME = "salesmanPDA";
    private static final String ENG_LISH = "ENG_LISH";
    private static final String ADD_SHOP = "ADD_SHOP";
    private static final String SAVE_DATA = "SAVE_DATA";
    private static final String CHECK_PIE = "CHECK_PIE";
    private static final String CHECK_SNACK = "CHECK_SNACK";
    private static final String USERNAME = "USERNAME";
    private static final String PASSWORD = "PASSWORD";
    private static final String IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch";
    private static final String TIME_SAVE_DB = "TIME_SAVE_DB";
    private static final String TIME_SAVE_DB_2 = "TIME_SAVE_DB_2";
    private static final String INFO_LOGIN = "INFO_LOGIN";
    private static final String INFO_LOGIN_SE = "INFO_LOGIN_SE";
    private static final String SUMMARY_SALE_REPORT = "SUMMARY_SALE_REPORT";
    private static final String SUMMARY_SALE_SHOP = "SUMMARY_SALE_SHOP";
    private static final String SUMMARY_DELIVERY = "SUMMARY_DELIVERY";
    private static final String SUMMARY_DISPLAY = "SUMMARY_DISPLAY";
    private static final String ROUTE = "ROUTE";
    private static final String ROUTE_SCHEDULE = "ROUTE_SCHEDULE";
    private static final String ROUTE_STOCK_SHOP = "ROUTE_STOCK_SHOP";
    private static final String SM_WORK_DAY = "SM_WORK_DAY";
    private static final String ROUTE_STOCK_PIE = "ROUTE_STOCK_PIE";
    private static final String ROUTE_STOCK_SNACK = "ROUTE_STOCK_SNACK";
    private static final String ROUTE_STOCK_GUM = "ROUTE_STOCK_GUM";
    private static final String ROUTE_ORDER_PIE = "ROUTE_ORDER_PIE";
    private static final String ROUTE_ORDER_SNACK = "ROUTE_ORDER_SNACK";
    private static final String ROUTE_ORDER_GUM = "ROUTE_ORDER_GUM";
    private static final String ROUTE_DISPLAY_PIE = "ROUTE_DISPLAY_PIE";
    private static final String ROUTE_DISPLAY_SNACK = "ROUTE_DISPLAY_SNACK";
    private static final String ROUTE_SHOP_OFFLINE = "ROUTE_SHOP_OFFLINE";
    private static final String API_110 = "API_110";
    private static final String API_112 = "API_112";
    private static final String UPDATE_POSITION = "UPDATE_POSITION";
    private static final String CHECK_UPDATE = "CHECK_UPDATE";
    private static final String CHECK_UPDATE_CLICK = "CHECK_UPDATE_CLICK";
    private static final String UPDATE_VISIT_SHOP = "UPDATE_VISIT_SHOP";
    private static final String OMI_SHOP = "OMI_SHOP";
    private static final String CHECK_DOWN_LOAD = "CHECK_DOWN_LOAD";
    private static final String LIST_UNREAD = "LIST_UNREAD"; // 0 unread - 1 read
    private static final String updateListMsg = "updateListMsg";
    private static final String updateListAtPosition = "updateListAtPosition";
    private static final String updateRoute = "updateRoute";
    private static final String GPSINTERVALSEND = "GPSINTERVALSEND";
    private static final String SENDINTERVAL = "SENDINTERVAL";
    private static final String compareStringOne = "compareStringOne";
    private static final String compareStringTwo = "compareStringTwo";
    private static final String updateOutBox = "updateOutBox";
    private static final String result_1 = "result_1";
    private static final String result_2 = "result_2";
    private static final String result_3 = "result_3";
    private static final String result_4 = "result_4";
    private static final String summary_1 = "summary_1";
    private static final String summary_2 = "summary_2";
    private static final String summary_3 = "summary_3";
    private static final String summary_4 = "summary_4";
    private static final String ID_NEWS = "ID_NEWS";
    private static final String UPDATE_NEWS = "UPDATE_NEWS";


    public PrefManager(Context context) {
        this._context = context;
        pref = MyApplication.getInstance().getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void ClearData() {
        editor.clear();
    }


    public void setSummary_1(boolean s) {
        editor.putBoolean(summary_1, s);
        editor.commit();
    }

    public boolean getSummary_1() {
        return pref.getBoolean(summary_1, false);
    }

    public void setSummary_2(boolean s) {
        editor.putBoolean(summary_2, s);
        editor.commit();
    }

    public boolean getSummary_2() {
        return pref.getBoolean(summary_2, false);
    }

    public void setSummary_3(boolean s) {
        editor.putBoolean(summary_3, s);
        editor.commit();
    }

    public boolean getSummary_3() {
        return pref.getBoolean(summary_3, false);
    }

    public void setSummary_4(boolean s) {
        editor.putBoolean(summary_4, s);
        editor.commit();
    }

    public boolean getSummary_4() {
        return pref.getBoolean(summary_4, false);
    }

    public void setResult_1(boolean s) {
        editor.putBoolean(result_1, s);
        editor.commit();
    }

    public boolean getResult_1() {
        return pref.getBoolean(result_1, false);
    }

    public void setResult_2(boolean s) {
        editor.putBoolean(result_2, s);
        editor.commit();
    }

    public boolean getResult_2() {
        return pref.getBoolean(result_2, false);
    }

    public void setResult_3(boolean s) {
        editor.putBoolean(result_3, s);
        editor.commit();
    }

    public boolean getResult_3() {
        return pref.getBoolean(result_3, false);
    }

    public void setResult_4(boolean s) {
        editor.putBoolean(result_4, s);
        editor.commit();
    }

    public boolean getResult_4() {
        return pref.getBoolean(result_4, false);
    }

    public void setOutBox(boolean s) {
        editor.putBoolean(updateOutBox, s);
        editor.commit();
    }

    public boolean getOutBox() {
        return pref.getBoolean(updateOutBox, false);
    }

    public void setcompareStringTwo(String s) {
        editor.putString(compareStringTwo, s);
        editor.commit();
    }

    public String getcompareStringTwo() {
        return pref.getString(compareStringTwo, "18:00");
    }

    public void setcompareStringOne(String s) {
        editor.putString(compareStringOne, s);
        editor.commit();
    }

    public String getcompareStringOne() {
        return pref.getString(compareStringOne, "07:00");
    }

    public void setSENDINTERVAL(int sendinterval) {
        editor.putInt(SENDINTERVAL, sendinterval);
        editor.commit();
    }

    public int getSENDINTERVAL() {
        return pref.getInt(SENDINTERVAL, 30000);
    }

    public void setGPSINTERVALSEND(int interval) {
        editor.putInt(GPSINTERVALSEND, interval);
        editor.commit();
    }

    public int getGPSINTERVALSEND() {
        return pref.getInt(GPSINTERVALSEND, 30000);
    }

    public void setUpdateRoute(boolean b) {
        editor.putBoolean(updateRoute, b);
        editor.commit();
    }

    public boolean isUpdateRoute() {
        return pref.getBoolean(updateRoute, false);
    }

    public void setLogout(boolean isFirstTime) {
        editor.putBoolean(KEY_USER_LOGIN, isFirstTime);
        editor.commit();
    }

    public boolean isLogout() {
        return pref.getBoolean(KEY_USER_LOGIN, true);
    }

    public int getUpdateAtPosition() {
        return pref.getInt(updateListAtPosition, 0);
    }

    public void setUpdateAtPosition(int position) {
        editor.putInt(updateListAtPosition, position);
        editor.commit();
    }

    public boolean isUpdateListMsg() {
        return pref.getBoolean(updateListMsg, false);
    }

    public void setUpdateListMsg(boolean isFirstTime) {
        editor.putBoolean(updateListMsg, isFirstTime);
        editor.commit();
    }

    public void setFirstTimeLaunch(boolean isFirstTime) {
        editor.putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTime);
        editor.commit();
    }

    public boolean isFirstTimeLaunch() {
        return pref.getBoolean(IS_FIRST_TIME_LAUNCH, true);
    }

    public void setSaveData(boolean s) {
        editor.putBoolean(SAVE_DATA, s);
        editor.commit();
    }

    public boolean isSaveData() {
        return pref.getBoolean(SAVE_DATA, false);
    }

    public void setUsername(String s) {
        editor.putString(USERNAME, s);
        editor.commit();
    }

    public String getUsername() {
        return pref.getString(USERNAME, "");
    }

    public void setListUnread(String s) {
        editor.putString(LIST_UNREAD, s);
        editor.commit();
    }

    public String getListUnRead() {
        return pref.getString(LIST_UNREAD, "");
    }

    public void setPassword(String s) {
        editor.putString(PASSWORD, s);
        editor.commit();
    }

    public String getPassword() {
        return pref.getString(PASSWORD, "");
    }

    public void setTimeSaveDb(String s, int i) {
        if (i == 0)
            editor.putString(TIME_SAVE_DB, s);
        else if (i == 1)
            editor.putString(TIME_SAVE_DB_2, s);
        editor.commit();
    }

    public String getTimeSaveDb(int i) {
        String s = "";
        if (i == 0)
            s = pref.getString(TIME_SAVE_DB, "");
        else if (i == 1)
            s = pref.getString(TIME_SAVE_DB_2, "");
        return s;
    }
    public void setInfoLoginSE(String s) {
        editor.putString(INFO_LOGIN_SE, s);
        editor.commit();
    }

    public String getInfoLoginSE() {
        return pref.getString(INFO_LOGIN_SE, "");
    }
    public void setInfoLogin(String s) {
        editor.putString(INFO_LOGIN, s);
        editor.commit();
    }

    public String getInfoLogin() {
        return pref.getString(INFO_LOGIN, "");
    }

    public void setSummarySaleReport(String s) {
        editor.putString(SUMMARY_SALE_REPORT, s);
        editor.commit();
    }

    public String getSummarySaleReport() {
        return pref.getString(SUMMARY_SALE_REPORT, "");
    }

    public void setSummarySaleShop(String s) {
        editor.putString(SUMMARY_SALE_SHOP, s);
        editor.commit();
    }

    public String getSummarySaleShop() {
        return pref.getString(SUMMARY_SALE_SHOP, "");
    }

    public void setSummaryDelivery(String s) {
        editor.putString(SUMMARY_DELIVERY, s);
        editor.commit();
    }

    public String getSummaryDelivery() {
        return pref.getString(SUMMARY_DELIVERY, "");
    }

    public void setSummaryDisplay(String s) {
        editor.putString(SUMMARY_DISPLAY, s);
        editor.commit();
    }

    public String getSummaryDisplay() {
        return pref.getString(SUMMARY_DISPLAY, "");
    }

    public void setRoute(String s) {
        editor.putString(ROUTE, s);
        editor.commit();
    }

    public String getRoute() {
        return pref.getString(ROUTE, "");
    }

    public void setRouteSchedule(String s) {
        editor.putString(ROUTE_SCHEDULE, s);
        editor.commit();
    }

    public String getRouteSchedule() {
        return pref.getString(ROUTE_SCHEDULE, "");
    }

    public void setRouteStockShop(String s) {
        editor.putString(ROUTE_STOCK_SHOP, s);
        editor.commit();
    }

    public String getRouteStockShop() {
        return pref.getString(ROUTE_STOCK_SHOP, "");
    }

    public void setSmWorkDay(String s) {
        editor.putString(SM_WORK_DAY, s);
        editor.commit();
    }

    public String getSmWorkDay() {
        return pref.getString(SM_WORK_DAY, "");
    }

    public void setRouteStockPie(String s) {
        editor.putString(ROUTE_STOCK_PIE, s);
        editor.commit();
    }

    public String getRouteStockPie() {
        return pref.getString(ROUTE_STOCK_PIE, "");
    }

    public void setRouteStockGum(String s) {
        editor.putString(ROUTE_STOCK_GUM, s);
        editor.commit();
    }

    public String getRouteStockGum() {
        return pref.getString(ROUTE_STOCK_GUM, "");
    }

    public void setRouteStockSnack(String s) {
        editor.putString(ROUTE_STOCK_SNACK, s);
        editor.commit();
    }

    public String getRouteStockSnack() {
        return pref.getString(ROUTE_STOCK_SNACK, "");
    }

    public void setRouteOrderPie(String s) {
        editor.putString(ROUTE_ORDER_PIE, s);
        editor.commit();
    }

    public String getRouteOrderPie() {
        return pref.getString(ROUTE_ORDER_PIE, "");
    }

    public void setRouteOrderSnack(String s) {
        editor.putString(ROUTE_ORDER_SNACK, s);
        editor.commit();
    }

    public String getRouteOrderSnack() {
        return pref.getString(ROUTE_ORDER_SNACK, "");
    }

    public void setRouteOrderGum(String s) {
        editor.putString(ROUTE_ORDER_GUM, s);
        editor.commit();
    }

    public String getRouteOrderGum() {
        return pref.getString(ROUTE_ORDER_GUM, "");
    }

    public void setCheckPie(boolean s) {
        editor.putBoolean(CHECK_PIE, s);
        editor.commit();
    }

    public boolean isCheckPie() {
        return pref.getBoolean(CHECK_PIE, false);
    }

    public void setCheckSnack(boolean s) {
        editor.putBoolean(CHECK_SNACK, s);
        editor.commit();
    }

    public boolean isCheckSnack() {
        return pref.getBoolean(CHECK_SNACK, false);
    }

    public String getAddShop() {
        return pref.getString(ADD_SHOP, "");
    }

    public void setAddShop(String s) {
        editor.putString(ADD_SHOP, s);
        editor.commit();
    }

    public String getRouteDisplayPie() {
        return pref.getString(ROUTE_DISPLAY_PIE, "");
    }

    public void setRouteDisplayPie(String s) {
        editor.putString(ROUTE_DISPLAY_PIE, s);
        editor.commit();
    }

    public String getRouteDisplaySnack() {
        return pref.getString(ROUTE_DISPLAY_SNACK, "");
    }

    public void setRouteDisplaySnack(String s) {
        editor.putString(ROUTE_DISPLAY_SNACK, s);
        editor.commit();
    }

    public String getEngLish() {
        return pref.getString(ENG_LISH, "vi");
    }

    public void setEngLish(String s) {
        editor.putString(ENG_LISH, s);
        editor.commit();
    }

    public String getRouteShopOffline() {
        return pref.getString(ROUTE_SHOP_OFFLINE, "");
    }

    public void setRouteShopOffline(String s) {
        editor.putString(ROUTE_SHOP_OFFLINE, s);
        editor.commit();
    }

    public String getApi110() {
        return pref.getString(API_110, "");
    }

    public void setApi110(String s) {
        editor.putString(API_110, s);
        editor.commit();
    }

    public String getApi112() {
        return pref.getString(API_112, "");
    }

    public void setApi112(String s) {
        editor.putString(API_112, s);
        editor.commit();
    }

    public String getUpdatePosition() {
        return pref.getString(UPDATE_POSITION, "");
    }

    public void setUpdatePosition(String s) {
        editor.putString(UPDATE_POSITION, s);
        editor.commit();
    }


    public String getCheckUpdate() {
        return pref.getString(CHECK_UPDATE, "");
    }

    public void setCheckUpdate(String s) {
        editor.putString(CHECK_UPDATE, s);
        editor.commit();
    }

    public void setCheckUpdateClick(boolean s) {
        editor.putBoolean(CHECK_UPDATE_CLICK, s);
        editor.commit();
    }

    public boolean getCheckUpdateClick() {
        return pref.getBoolean(CHECK_UPDATE_CLICK, false);
    }


    public void setUpdateVisitShop(boolean s) {
        editor.putBoolean(UPDATE_VISIT_SHOP, s);
        editor.commit();
    }

    public boolean isUpdateVisitShop() {
        return pref.getBoolean(UPDATE_VISIT_SHOP, false);
    }

    public void setOmiShop(boolean s) {
        editor.putBoolean(OMI_SHOP, s);
        editor.commit();
    }

    public boolean getOmiShop() {
        return pref.getBoolean(OMI_SHOP, false);
    }

    public String getCheckDownLoad() {
        return pref.getString(CHECK_DOWN_LOAD, "");
    }

    public void setCheckDownLoad(String s) {
        editor.putString(CHECK_DOWN_LOAD, s);
        editor.commit();
    }

    public void setID_NEWS(String s) {
        editor.putString(ID_NEWS, s);
        editor.commit();
    }

    public String getID_NEWS() {
        return pref.getString(ID_NEWS, "");
    }


    public void setUPDATE_NEWS(boolean s) {
        editor.putBoolean(UPDATE_NEWS, s);
        editor.commit();
    }

    public boolean getUPDATE_NEWS() {
        return pref.getBoolean(UPDATE_NEWS, false);
    }

}
