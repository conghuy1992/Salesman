package com.orion.salesman;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PersistableBundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.orion.salesman._app.MyApplication;
import com.orion.salesman._class.AndroidBug5497Workaround;
import com.orion.salesman._class.Const;
import com.orion.salesman._class.HttpRequest;
import com.orion.salesman._interface.IF_115;
import com.orion.salesman._object.API_121;
import com.orion.salesman._object.API_122;
import com.orion.salesman._object.API_123;
import com.orion.salesman._object.AddressSQLite;
import com.orion.salesman._object.AddressSQLiteStreets;
import com.orion.salesman._object.CodeH;
import com.orion.salesman._object.DISPLAYLIST;
import com.orion.salesman._object.DataBase129;
import com.orion.salesman._object.DataLogin;
import com.orion.salesman._object.GetNewShopPromotionList;
import com.orion.salesman._object.ListSKUPromotion;
import com.orion.salesman._object.NewShopPromotionList;
import com.orion.salesman._object.ORDERLIST;
import com.orion.salesman._object.Obj_124;
import com.orion.salesman._object.ObjectA0129;
import com.orion.salesman._object.PMPRODUCTLIST;
import com.orion.salesman._object.PmList;
import com.orion.salesman._object.ProductInfor;
import com.orion.salesman._object.RESEND_115;
import com.orion.salesman._object.SKUPromotion;
import com.orion.salesman._object.STOCKLIST;
import com.orion.salesman._object.SaveInputData;
import com.orion.salesman._object.TABLE_INPUT;
import com.orion.salesman._object.TeamDisplay;
import com.orion.salesman._object.VisitShop;
import com.orion.salesman._offline.OfflineObject;
import com.orion.salesman._pref.PrefManager;
import com.orion.salesman._route._fragment.PieFragment;
import com.orion.salesman._route._fragment.PromotionInfoFragment;
import com.orion.salesman._route._fragment.PromotionInfoFragmentByMonth;
import com.orion.salesman._route._fragment.RouteConfirmFragment;
import com.orion.salesman._route._fragment.RouteDisplayFragment;
import com.orion.salesman._route._fragment.RouteFragment;
import com.orion.salesman._route._fragment.RouteOrderFragment;
import com.orion.salesman._route._fragment.RouteStockFragment;
import com.orion.salesman._route._object.InforShopDetails;
import com.orion.salesman._route._object.Pie;
import com.orion.salesman._route._object.RouteDisplayPie;
import com.orion.salesman._route._object.Snack;
import com.orion.salesman._route._object._RouteOrderFragmentPie;
import com.orion.salesman._sqlite.DataBaseCodeH;
import com.orion.salesman._sqlite.DataBaseHelper;
import com.orion.salesman._sqlite.DataBaseHelper_2;
import com.orion.salesman._sqlite.DatabaseHandler;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kr.co.eksys.nativelib.EksysNetworkException;
import kr.co.eksys.nativelib.NetworkManager;

/**
 * Created by maidinh on 5/8/2016.
 */
public class OrderActivity extends AppCompatActivity {
    public static boolean checkClick = false;
    public static String URL_IMG = "";
    private String TAG = "OrderActivity";
    private ImageView ivStock, ivStockPre;
    public static int flag = 0;
    private TextView tvStock;
    public static OrderActivity instance = null;
    private LinearLayout lnTitle;
    private TextView tvName, tvAddress;
    String PTEAM = "PTEAM";
    String GTEAM = "GTEAM";
    String STEAM = "STEAM";
    private List<Pie> ListPie = new ArrayList<>();
    private List<Pie> ListGum = new ArrayList<>();
    private List<Snack> ListSnack = new ArrayList<>();

    boolean checkContains(List<Pie> arList, String s) {
        for (Pie p : arList) {
            if (s.equals(p.getColumnsOne()))
                return true;
        }
        return false;
    }

    List<API_122> api_122List = new ArrayList<>();
    List<SKUPromotion> LIST = new ArrayList<>();

    boolean get_API_122(String shopId, String TPRDCD, String NON) {
//        Log.d(TAG, "id:" + shopId + " TPRDCD:" + TPRDCD + " NON:" + NON);
//        if(NON.length()==0)
        boolean flag = false;
        if (LIST != null && LIST.size() > 0) {
            for (int i = 0; i < LIST.size(); i++) {
                if (shopId.equalsIgnoreCase(LIST.get(i).getV1())) {
                    if (TPRDCD.equalsIgnoreCase(LIST.get(i).getV2())) {
                        String str = "";
                        if (LIST.get(i).getV3().equalsIgnoreCase("Y") && NON.equals("3"))
                            flag = true;
                        else if (LIST.get(i).getV4().equalsIgnoreCase("Y") && NON.equals("2"))
                            flag = true;
                        else if (LIST.get(i).getV5().equalsIgnoreCase("Y") && NON.equals("1"))
                            flag = true;
//                        if (NON.equalsIgnoreCase(str))
//                            flag = true;
                    }
                }
            }
        }
        return flag;
    }

    List<NewShopPromotionList> LIST_PMT = new ArrayList<>();

    void getDBNewShopPromotionList() {
        restoreDataInput();
        DatabaseHandler db = new DatabaseHandler(this);
        List<API_123> listAPI_123 = db.getListAPI_123();
        if (listAPI_123 != null && listAPI_123.size() > 0) {
            API_123 api_123 = listAPI_123.get(listAPI_123.size() - 1);
            GetNewShopPromotionList getNewShopPromotionList = new Gson().fromJson(api_123.getDATA(), GetNewShopPromotionList.class);
            LIST_PMT = getNewShopPromotionList.getLIST();
        }

        api_122List = db.getListAPI_122();
//        Const.longInfo(TAG,new Gson().toJson(api_122List));
        if (api_122List != null && api_122List.size() > 0) {
            API_122 api_122 = api_122List.get(api_122List.size() - 1);
            ListSKUPromotion listSKUPromotion = new Gson().fromJson(api_122.getDATA(), ListSKUPromotion.class);
            LIST = listSKUPromotion.getLIST();
//            for (int i = 0; i < LIST.size(); i++) {
//                Log.d(TAG, LIST.get(i).getV1() + ":" + LIST.get(i).getV2() + ":" + LIST.get(i).getV3() + ":" + LIST.get(i).getV4() + ":" + LIST.get(i).getV5());
//            }
        }
        api_121List = db.getListAPI_121();
        String monthPromotion = RouteFragment.DATE.substring(0, 6);
        if (api_121List != null && api_121List.size() > 0) {

            for (int i = 0; i < api_121List.size(); i++) {
                API_121 api_121 = api_121List.get(i);
                if (api_121 != null) {
                    String datePromotion = api_121.getDATE();
                    if (monthPromotion.equals(datePromotion.substring(0, 6))) {
                        pmList = new Gson().fromJson(api_121.getDATA(), PmList.class);
//                        Log.d(TAG, "monthPromotion:" + monthPromotion);
//                        Const.longInfo(TAG, "pmList:" + new Gson().toJson(pmList));
                        break;
                    }
                }
            }
        }
    }

    List<API_121> api_121List = new ArrayList<>();
    PmList pmList = new PmList();
    String PMID = "";
    String GIFTAMTPERCENT = "";

    boolean getDBPromotionList(String TPRDCD, String TEAM) {
        boolean checkPR = false;
//        String PMID = "";
        GIFTAMTPERCENT = "";
        PMID = "";
        if (pmList != null) {
            if (pmList.getPMLIST() != null && pmList.getPMLIST().size() > 0) {
                for (int i = 0; i < pmList.getPMLIST().size(); i++) {
                    if (checkPR)
                        break;
                    else {
                        PMPRODUCTLIST pmproductlist = pmList.getPMLIST().get(i);
                        final String PM_LEVEL = pmproductlist.getV4();
                        PMID = pmproductlist.getV1();
                        GIFTAMTPERCENT = pmproductlist.getV17();
                        List<PMPRODUCTLIST> aList = pmproductlist.getPMPRODUCTLIST();
                        if (aList != null && aList.size() > 0) {
                            for (int k = 0; k < aList.size(); k++) {
                                PMPRODUCTLIST ppd = aList.get(k);
                                if (ppd.getV1().equalsIgnoreCase("BUY") && ppd.getV10().equalsIgnoreCase(TEAM) && ppd.getV5().equals(TPRDCD)) {
//                            if (ppd.getV1().equalsIgnoreCase("BUY") && ppd.getV5().equals(TPRDCD)) {
                                    if (PM_LEVEL.equals("1")) {
                                        String nonShop = "";
                                        if (pmproductlist.getV7().equalsIgnoreCase("Y"))
                                            nonShop = "3";
                                        else if (pmproductlist.getV6().equalsIgnoreCase("Y"))
                                            nonShop = "2";
                                        else if (pmproductlist.getV5().equalsIgnoreCase("Y"))
                                            nonShop = "1";
//                                        checkPR = get_API_122(MainActivity.SHOP_ID, TPRDCD, nonShop);
                                        if (get_API_122(MainActivity.SHOP_ID, TPRDCD, nonShop)) {
                                            checkPR = true;
                                            break;
                                        }
                                    }
                                    if (PM_LEVEL.equals("2")) {
//                                        checkPR = Const.getDBNewShop(LIST_PMT, MainActivity.SHOP_ID);
                                        if (Const.getDBNewShop(LIST_PMT, MainActivity.SHOP_ID)) {
                                            checkPR = true;
                                            break;
                                        }
                                    }
                                    if (PM_LEVEL.equals("3")) {
                                        String CHANNEL = pmproductlist.getV9();
                                        if (CHANNEL.equalsIgnoreCase(MainActivity.CHANNEL)) {
//                                            Log.d(TAG, "CHANNEL:" + MainActivity.CHANNEL + " TPRDCD:" + TPRDCD);
                                            checkPR = true;
                                            break;
                                        }
                                    }
                                    if (PM_LEVEL.equals("4")) {
                                        if (MainActivity.SHOP_GRADE.equalsIgnoreCase("A")) {
                                            if (pmproductlist.getV10().equalsIgnoreCase("Y"))
                                                if (MainActivity.AREA.equals("1")) {
                                                    if (pmproductlist.getV14().equals("Y")) {
                                                        checkPR = true;
                                                        break;
                                                    }
                                                } else if (MainActivity.AREA.equals("2")) {
                                                    if (pmproductlist.getV15().equals("Y")) {
                                                        checkPR = true;
                                                        break;
                                                    }
                                                } else if (MainActivity.AREA.equals("3")) {
                                                    if (pmproductlist.getV16().equals("Y")) {
                                                        checkPR = true;
                                                        break;
                                                    }
                                                }
                                        } else if (MainActivity.SHOP_GRADE.equalsIgnoreCase("B")) {
                                            if (pmproductlist.getV11().equalsIgnoreCase("Y"))
                                                if (MainActivity.AREA.equals("1")) {
                                                    if (pmproductlist.getV14().equals("Y")) {
                                                        checkPR = true;
                                                        break;
                                                    }
                                                } else if (MainActivity.AREA.equals("2")) {
                                                    if (pmproductlist.getV15().equals("Y")) {
                                                        checkPR = true;
                                                        break;
                                                    }
                                                } else if (MainActivity.AREA.equals("3")) {
                                                    if (pmproductlist.getV16().equals("Y")) {
                                                        checkPR = true;
                                                        break;
                                                    }
                                                }
                                        } else if (MainActivity.SHOP_GRADE.equalsIgnoreCase("C")) {
                                            if (pmproductlist.getV12().equalsIgnoreCase("Y"))
                                                if (MainActivity.AREA.equals("1")) {
                                                    if (pmproductlist.getV14().equals("Y")) {
                                                        checkPR = true;
                                                        break;
                                                    }
                                                } else if (MainActivity.AREA.equals("2")) {
                                                    if (pmproductlist.getV15().equals("Y")) {
                                                        checkPR = true;
                                                        break;
                                                    }
                                                } else if (MainActivity.AREA.equals("3")) {
                                                    if (pmproductlist.getV16().equals("Y")) {
                                                        checkPR = true;
                                                        break;
                                                    }
                                                }
                                        } else if (MainActivity.SHOP_GRADE.equalsIgnoreCase("D")) {
                                            if (pmproductlist.getV13().equalsIgnoreCase("Y"))
                                                if (MainActivity.AREA.equals("1")) {
                                                    if (pmproductlist.getV14().equals("Y")) {
                                                        checkPR = true;
                                                        break;
                                                    }
                                                } else if (MainActivity.AREA.equals("2")) {
                                                    if (pmproductlist.getV15().equals("Y")) {
                                                        checkPR = true;
                                                        break;
                                                    }
                                                } else if (MainActivity.AREA.equals("3")) {
                                                    if (pmproductlist.getV16().equals("Y")) {
                                                        checkPR = true;
                                                        break;
                                                    }
                                                }
                                        }
                                    }
                                    if (PM_LEVEL.equals("5")) {
                                        checkPR = true;
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return checkPR;
    }

    List<ProductInfor> getListProduct = new ArrayList<>();
    List<TeamDisplay> ListDisplay = new ArrayList<>();

    void getListProductDB() {
        try {
            DataBaseHelper_2 db = new DataBaseHelper_2(this);
            db.openDB();
            getListProduct = db.getListProduct();
            ListDisplay = db.getListDisplay();
        } catch (Exception e) {
            e.printStackTrace();
//            MyApplication.getInstance().showToast("can not get DB");
        }
    }

    class getListProduct extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... strings) {
            getListProductDB();
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            new Load_Data_1().execute();
        }
    }

    List<com.orion.salesman._object.DISPLAYLIST> DISPLAYLIST = new ArrayList<>();
    List<com.orion.salesman._object.STOCKLIST> STOCKLIST = new ArrayList<>();
    List<com.orion.salesman._object.ORDERLIST> ORDERLIST = new ArrayList<>();

    com.orion.salesman._object.ORDERLIST getOrderlist(String PRDCD) {
        com.orion.salesman._object.ORDERLIST orderlist = new com.orion.salesman._object.ORDERLIST();
        if (ORDERLIST != null) {
            if (ORDERLIST.size() > 0) {
                for (com.orion.salesman._object.ORDERLIST obj : ORDERLIST) {
                    if (obj.getV1().equals(MainActivity.SHOP_CODE) && obj.getV2().equals(PRDCD)) {
                        orderlist = obj;
                        if (orderlist.getV3().equals("0"))
                            orderlist.setV3("");
                        if (orderlist.getV4().equals("0"))
                            orderlist.setV4("");
                        if (orderlist.getV5().equals("0"))
                            orderlist.setV5("");
                        break;
                    }
                }
            }
        }

        // pie and gum
//        if (PRDCD.equals("A515190") || PRDCD.equals("A508020")) {
//            Log.d(TAG, "A515190:" + new Gson().toJson(orderlist));
//        }
        //snack
//        if(PRDCD.equals("A510500"))
//        {
//            Log.d(TAG,"A510500:"+new Gson().toJson(stocklist));
//        }
        return orderlist;
    }

    com.orion.salesman._object.DISPLAYLIST getDisplay(String TOOLCD) {
        com.orion.salesman._object.DISPLAYLIST displaylist = new com.orion.salesman._object.DISPLAYLIST();
        if (DISPLAYLIST != null) {
            if (DISPLAYLIST.size() > 0) {
                for (com.orion.salesman._object.DISPLAYLIST obj : DISPLAYLIST) {
                    if (obj.getV2().equals(TOOLCD) && obj.getV1().equals(MainActivity.SHOP_CODE)) {
                        displaylist = obj;
                        if (displaylist.getV3().equals("0"))
                            displaylist.setV3("");
                        break;
                    }
                }
            }
        }
        return displaylist;
    }


    com.orion.salesman._object.STOCKLIST getStock(String PRDCD) {
        com.orion.salesman._object.STOCKLIST stocklist = new com.orion.salesman._object.STOCKLIST();
        if (STOCKLIST != null) {
            if (STOCKLIST.size() > 0) {
                for (com.orion.salesman._object.STOCKLIST obj : STOCKLIST) {
                    if (obj.getV2().equals(PRDCD) && MainActivity.SHOP_CODE.equals(obj.getV1())) {
                        stocklist = obj;
                        if (stocklist.getV3().equals("0"))
                            stocklist.setV3("");
                        if (stocklist.getV4().equals("0"))
                            stocklist.setV4("");
                        if (stocklist.getV5().equals("0"))
                            stocklist.setV5("");
                        if (stocklist.getV6().equals("0"))
                            stocklist.setV6("");
                        break;
                    }
                }
            }
        }
        // pie and gum
//        if(PRDCD.equals("A515190")||PRDCD.equals("A508020"))
//        {
//            Log.d(TAG,"A515190:"+new Gson().toJson(stocklist));
//        }
        //snack
//        if(PRDCD.equals("A510500"))
//        {
//            Log.d(TAG,"A510500:"+new Gson().toJson(stocklist));
//        }
        return stocklist;
    }

    void getListProduct() {
        DatabaseHandler db = new DatabaseHandler(OrderActivity.this);
        List<DataBase129> lst129 = db.getList129();
        if (lst129 != null && lst129.size() > 0) {
            for (DataBase129 ob : lst129) {
                if (ob.getCDATE().equals(RouteFragment.DATE)) {
                    String data = ob.getDATA();
                    ObjectA0129 objectA0129 = new Gson().fromJson(data, ObjectA0129.class);
                    int RESULT = objectA0129.getRESULT();
                    if (RESULT == 0) {
                        ORDERLIST = objectA0129.getORDERLIST();
                        DISPLAYLIST = objectA0129.getDISPLAYLIST();
                        STOCKLIST = objectA0129.getSTOCKLIST();
//                        Const.longInfo(TAG, "getORDERLIST:" + new Gson().toJson(ORDERLIST));
//                        Const.longInfo(TAG, "getDISPLAYLIST:" + new Gson().toJson(DISPLAYLIST));
//                        Const.longInfo(TAG, "getSTOCKLIST:" + new Gson().toJson(STOCKLIST));
                    }
                    break;
                }
            }
        }

        ListPie.clear();
        ListSnack.clear();
        ListGum.clear();

        for (ProductInfor s : getListProduct) {
            String RS = "1";
            com.orion.salesman._object.ORDERLIST orderlist = getOrderlist(s.getPRDCD());
            boolean flag = false;
            if (orderlist.getV6().trim().equals("D")) {
                RS = "2";
                flag = true;
            }
            String QTY = "";
            if (orderlist.getV3().length() > 0 && !orderlist.getV3().equals("0"))
                QTY = orderlist.getV3();
            else if (orderlist.getV4().length() > 0 && !orderlist.getV4().equals("0"))
                QTY = orderlist.getV4();
            else QTY = orderlist.getV5();

            if (s.getTEAM().equals(PTEAM)) {
                com.orion.salesman._object.STOCKLIST stocklist = getStock(s.getPRDCD());
                String S1 = stocklist.getV3();
                String S2 = stocklist.getV6();

                Pie ob = new Pie(s.getBRANDNM(), s.getPRDDSC(), S1, s.getTPRDDSC(), flag,
                        s.getBOXSALESYN(), s.getCASESALESYN(), s.getEASALESYN(), s.getSTOREPRICE(), s.getSTORECASEPRICE(),
                        s.getSTOREEAPRICE(), s.getBXCSEQTY(), s.getBXEAQTY(), s.getPRDCD(), s.getTPRDCD(), s.getCSEEAQTY(), s.getBXCSEQTY(), s.getPRDCLS1(), S2, QTY);
                ListPie.add(ob);


            } else if (s.getTEAM().equals(STEAM)) {
                com.orion.salesman._object.STOCKLIST stocklist = getStock(s.getPRDCD());
                String S1 = stocklist.getV4();
                String S2 = stocklist.getV6();

                Snack ob = new Snack(s.getBRANDNM(), s.getPRDDSC(), S1, S2, s.getTPRDDSC(), flag,
                        s.getBOXSALESYN(), s.getCASESALESYN(), s.getEASALESYN(), s.getSTOREPRICE(), s.getSTORECASEPRICE(), s.getSTOREEAPRICE(),
                        s.getBXCSEQTY(), s.getBXEAQTY(), s.getPRDCD(), s.getTPRDCD(), s.getCSEEAQTY(), s.getPRDCLS1(), QTY);
                ListSnack.add(ob);
            } else if (s.getTEAM().equals(GTEAM)) {
                com.orion.salesman._object.STOCKLIST stocklist = getStock(s.getPRDCD());
                String S1 = stocklist.getV4();
                String S2 = stocklist.getV5();

                Pie ob = new Pie(s.getBRANDNM(), s.getPRDDSC(), S1, s.getTPRDDSC(), flag,
                        s.getBOXSALESYN(), s.getCASESALESYN(), s.getEASALESYN(), s.getSTOREPRICE(), s.getSTORECASEPRICE(),
                        s.getSTOREEAPRICE(), s.getBXCSEQTY(), s.getBXEAQTY(), s.getPRDCD(), s.getTPRDCD(), s.getCSEEAQTY(), s.getBXCSEQTY(), s.getPRDCLS1(), S2, QTY);
                ListGum.add(ob);

            }
        }
            /*
            * for Pie
            * */

//            Const.longInfo(TAG,new Gson().toJson(ListPie));
        List<String> arrChild = new ArrayList<>();
        List<Pie> arrPies = new ArrayList<>();
        List<Pie> temp = new ArrayList<>();
        if (ListPie.size() > 0) {
            for (Pie s : ListPie) {
                if (!checkContains(arrPies, s.getColumnsOne())) {
                    arrPies.add(s);
                    for (Pie p : ListPie) {
                        if (p.getColumnsOne().equals(s.getColumnsOne())) {
                            if (MainActivity.SALESMANTYPE.equalsIgnoreCase(Const.DS)) {
                                p.setIsCheck(true);
                            }
                            p.setIspmt(getDBPromotionList(p.getTPRDCD(), "PIE"));
                            p.setPMID(PMID);
                            p.setGIFTAMTPERCENT(GIFTAMTPERCENT);
                            temp.add(p);
                        }
                    }
                    arrChild.add(new Gson().toJson(temp));
                    temp.clear();
                } else {

                }
            }
            for (int i = 0; i < arrPies.size(); i++) {
                Type listType = new TypeToken<ArrayList<Pie>>() {
                }.getType();
                ArrayList<Pie> saleChild = new Gson().fromJson(arrChild.get(i), listType);
                arrPies.get(i).setArrPies(saleChild);
            }
        }
        ListPie = arrPies;
        for (Pie s : ListPie) {
            s.setIsCheck(false);
        }
            /*
            * for Gum
            * */
        arrChild = new ArrayList<>();
        arrPies = new ArrayList<>();
        temp = new ArrayList<>();
        if (ListGum.size() > 0) {
            for (Pie s : ListGum) {
                if (!checkContains(arrPies, s.getColumnsOne())) {
                    arrPies.add(s);
                    for (Pie p : ListGum) {
                        if (p.getColumnsOne().equals(s.getColumnsOne())) {
                            if (MainActivity.SALESMANTYPE.equalsIgnoreCase(Const.DS)) {
                                p.setIsCheck(true);
                            }
                            p.setIspmt(getDBPromotionList(p.getTPRDCD(), "GUM"));
                            p.setPMID(PMID);
                            p.setGIFTAMTPERCENT(GIFTAMTPERCENT);
                            temp.add(p);
                        }
                    }
                    arrChild.add(new Gson().toJson(temp));
                    temp.clear();
                } else {

                }
            }
            for (int i = 0; i < arrPies.size(); i++) {
                Type listType = new TypeToken<ArrayList<Pie>>() {
                }.getType();
                ArrayList<Pie> saleChild = new Gson().fromJson(arrChild.get(i), listType);
                arrPies.get(i).setArrPies(saleChild);
            }
        }
        ListGum = arrPies;
        for (Pie s : ListGum) {
            s.setIsCheck(false);

        }
            /*
            * for Snack
            * */
        arrChild = new ArrayList<>();
        List<Snack> arrSnacks = new ArrayList<>();
        List<Snack> tempSnacks = new ArrayList<>();
        if (ListSnack.size() > 0) {
            for (Snack s : ListSnack) {
                if (!Const.checkContainSnack(arrSnacks, s.getColumnsOne())) {
                    arrSnacks.add(s);
                    for (Snack p : ListSnack) {
                        if (p.getColumnsOne().equals(s.getColumnsOne())) {
                            if (MainActivity.SALESMANTYPE.equalsIgnoreCase(Const.DS)) {
                                p.setIsCheck(true);
                            }
                            p.setIspmt(getDBPromotionList(p.getTPRDCD(), "SNACK"));
                            p.setPMID(PMID);
                            p.setGIFTAMTPERCENT(GIFTAMTPERCENT);
                            tempSnacks.add(p);
                        }
                    }
                    arrChild.add(new Gson().toJson(tempSnacks));
                    tempSnacks.clear();
                } else {

                }
            }
            for (int i = 0; i < arrSnacks.size(); i++) {
                Type listType = new TypeToken<ArrayList<Snack>>() {
                }.getType();
                ArrayList<Snack> saleChild = new Gson().fromJson(arrChild.get(i), listType);
                arrSnacks.get(i).setArrSnacks(saleChild);
            }
        }
        ListSnack = arrSnacks;
        for (Snack s : ListSnack) {
            s.setIsCheck(false);
        }


        for (TeamDisplay s : ListDisplay) {
            com.orion.salesman._object.DISPLAYLIST obj = getDisplay(s.getTOOLCD());
            String TOOLQTY = obj.getV3();
            if (s.getTEAMCD().equals("01")) {
                RouteDisplayPie ob = new RouteDisplayPie(s.getTOOLNM(), s.getBRAND(), TOOLQTY, s.getTOOLCD());
                arrayList_Pie.add(ob);
            } else if (s.getTEAMCD().equals("02")) {
                RouteDisplayPie ob = new RouteDisplayPie(s.getTOOLNM(), s.getBRAND(), TOOLQTY, s.getTOOLCD());
                arrayList_Snack.add(ob);
            }

        }
    }

    ProgressBar progressbar;
    int count = 0;

    class Load_Data_1 extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            getDBNewShopPromotionList();
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            new Load_Data_2().execute();
        }
    }

    class Load_Data_2 extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            getListProduct();
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            init();
            progressbar.setVisibility(View.GONE);
            getInforShop();
        }
    }


    boolean isFinish = false;

    void restoreDataInput() {
        if (MainActivity.STATUS_ORDER) {
            Log.d(TAG, "STATUS_ORDER");
            DatabaseHandler db = new DatabaseHandler(this);
            PrefManager pref = new PrefManager(this);
            List<SaveInputData> list = db.getListOrder();
            List<TABLE_INPUT> LIST_INPUT = db.GET_LIST_INPUT();
            String today = RouteFragment.DATE;
            if (list != null && list.size() > 0) {
                for (int i = 0; i < list.size(); i++) {
                    SaveInputData ob = list.get(i);
                    if (MainActivity.SHOP_CODE.equals(ob.getSHOPID()) && today.equals(ob.getDATE())) {
                        URL_IMG = ob.getURL_IMG();
                        break;
                    }
                }
            }

            if (LIST_INPUT != null && LIST_INPUT.size() > 0) {
                for (int i = 0; i < LIST_INPUT.size(); i++) {
                    TABLE_INPUT ob = LIST_INPUT.get(i);

                    if (MainActivity.SHOP_CODE.equals(ob.getTABLE_INPUT_SHOP_ID()) && today.equals(ob.getTABLE_INPUT_DATE())) {
                        pref.setRouteStockPie(ob.getTABLE_INPUT_STOCK_PIE());
                        pref.setRouteStockSnack(ob.getTABLE_INPUT_STOCK_SNACK());
                        pref.setRouteStockGum(ob.getTABLE_INPUT_STOCK_GUM());
                        pref.setRouteDisplayPie(ob.getTABLE_INPUT_DISPLAY_PIE());
                        pref.setRouteDisplaySnack(ob.getTABLE_INPUT_DISPLAY_SNACK());
                        pref.setRouteOrderPie(ob.getTABLE_INPUT_ORDER_PIE());
                        pref.setRouteOrderSnack(ob.getTABLE_INPUT_ORDER_SNACK());
                        pref.setRouteOrderGum(ob.getTABLE_INPUT_ORDER_GUM());
                        break;
                    }
                }
            } else {
                Log.d(TAG, "LIST_INPUT null");
            }
        } else {
            Log.d(TAG, "NOT STATUS_ORDER");
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.order);


        if (Const.inforSE.length() > 0) {
            Log.d(TAG, "dont send visit shop for SE");
        } else {
            initDB_115();
            API_115();
        }


        AndroidBug5497Workaround.assistActivity(this);
        URL_IMG = "";
        lnTitle = (LinearLayout) findViewById(R.id.lnTitle);
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) lnTitle.getLayoutParams();
        params.setMargins(MainActivity.widthTabLayout, 0, 0, 0);
        lnTitle.setLayoutParams(params);
        checkClick = false;
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.color_bar));
        }
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        progressbar = (ProgressBar) findViewById(R.id.progressbar);
        instance = this;
//        getListProductDB();
        new getListProduct().execute();

    }

    private InforShopDetails shopDetailsList = new InforShopDetails();
    FragmentManager manager;

    void init() {
        manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction
//                .setCustomAnimations(
//                        R.anim.fragment_slide_right_enter,
//                        R.anim.fragment_slide_left_exit,
//                        R.anim.fragment_slide_left_enter,
//                        R.anim.fragment_slide_right_exit)
                .replace(R.id.frOrder, new RouteStockFragment(ListPie, ListSnack, ListGum))
                .commitAllowingStateLoss();
//                .commit();

        tvStock = (TextView) findViewById(R.id.tvStock);
        ivStock = (ImageView) findViewById(R.id.ivStock);
        ivStockPre = (ImageView) findViewById(R.id.ivStockPre);
        ivStock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkClick) {
                    hideKeyboard();
                    if (flag == 0) {
                        getSupportFragmentManager().beginTransaction().setCustomAnimations(
                                R.anim.fragment_slide_right_enter,
                                R.anim.fragment_slide_left_exit,
                                R.anim.fragment_slide_left_enter,
                                R.anim.fragment_slide_right_exit)
                                .replace(R.id.frOrder, new RouteDisplayFragment(arrayList_Pie, arrayList_Snack))
                                .addToBackStack(null)
                                .commit();
                    } else if (flag == 1) {
                        getSupportFragmentManager().beginTransaction().setCustomAnimations(
                                R.anim.fragment_slide_right_enter,
                                R.anim.fragment_slide_left_exit,
                                R.anim.fragment_slide_left_enter,
                                R.anim.fragment_slide_right_exit)
                                .replace(R.id.frOrder, new RouteOrderFragment())
                                .addToBackStack(null)
                                .commit();
                    } else if (flag == 2) {
                        getSupportFragmentManager().beginTransaction().setCustomAnimations(
                                R.anim.fragment_slide_right_enter,
                                R.anim.fragment_slide_left_exit,
                                R.anim.fragment_slide_left_enter,
                                R.anim.fragment_slide_right_exit)
                                .replace(R.id.frOrder, new RouteConfirmFragment())
                                .addToBackStack(null)
                                .commit();
                    }
                    checkClick = false;
                }
            }
        });
        ivStockPre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleBack();
            }
        });
//        initDB_115();
//        API_115();
    }

    private List<AddressSQLiteStreets> getAddressStreet = new ArrayList<>();
    private List<AddressSQLite> getAddress = new ArrayList<>();

    void initDB() {
        getAddressStreet.clear();
        getAddress.clear();
        DataBaseHelper db = new DataBaseHelper(this);
        db.openDB();
        getAddressStreet = db.getAddressStreet();
        getAddress = db.getAddress();
    }

    void getInforShop() {
        initDB();
        String response = getIntent().getStringExtra(Const.INFOR_SHOP);
        Type listType = new TypeToken<ArrayList<String>>() {
        }.getType();
        ArrayList<String> listAddress = new Gson().fromJson(response, listType);
        tvName = (TextView) findViewById(R.id.tvName);
        tvAddress = (TextView) findViewById(R.id.tvAddress);
        SHOP_NAME = listAddress.get(0) + " - " + listAddress.get(1);
        tvName.setText(SHOP_NAME);
        if (getIntent().getStringExtra(Const.LIST_DETAILS) != null) {
            String s = getIntent().getStringExtra(Const.LIST_DETAILS);
            shopDetailsList = new Gson().fromJson(s, InforShopDetails.class);
            SHOP_ADDRESS = getResources().getString(R.string.address) + ": " + MainActivity.ADDRESS;
            tvAddress.setText(SHOP_ADDRESS);
        }
    }

    String SHOP_NAME = "";
    String SHOP_ADDRESS = "";

    private List<RouteDisplayPie> arrayList_Pie = new ArrayList<>();
    private List<RouteDisplayPie> arrayList_Snack = new ArrayList<>();

    public void showBtnNext() {
        if (ivStock != null)
            ivStock.setVisibility(View.VISIBLE);
    }

    public void hideBtnNext() {
        if (ivStock != null)
            ivStock.setVisibility(View.GONE);
    }

    public void showBtnBack() {
        if (ivStockPre != null)
            ivStockPre.setVisibility(View.VISIBLE);
    }

    public void setTvStock(String s) {
        if (tvStock != null)
            tvStock.setText(s);
    }

    private void handleBack() {
        if (checkClick) {
            checkClick = false;
            if (flag == 0) {
                finish();
            } else {
                getSupportFragmentManager().popBackStack();
            }
            hideKeyboard();
        }
    }

    public void hideKeyboard() {
        try {
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        } catch (Exception e) {

        }
    }

    public void gotoPromotionInfo() {
//        getSupportFragmentManager().beginTransaction()
//                .setCustomAnimations(
//                        R.anim.fragment_slide_right_enter,
//                        R.anim.fragment_slide_left_exit,
//                        R.anim.fragment_slide_left_enter,
//                        R.anim.fragment_slide_right_exit)
//                .replace(R.id.frOrder, new PromotionInfoFragment())
//                .addToBackStack(null)
//                .commit();
        Intent intent = new Intent(OrderActivity.this, PromotionItem.class);
        intent.putExtra(Const.SHOP_NAME, SHOP_NAME);
        intent.putExtra(Const.SHOP_ADDRESS, SHOP_ADDRESS);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void gotoPromotionInfoByMonth() {
//        getSupportFragmentManager().beginTransaction()
//                .setCustomAnimations(
//                        R.anim.fragment_slide_right_enter,
//                        R.anim.fragment_slide_left_exit,
//                        R.anim.fragment_slide_left_enter,
//                        R.anim.fragment_slide_right_exit)
//                .replace(R.id.frOrder, new PromotionInfoFragmentByMonth())
//                .addToBackStack(null)
//                .commit();
        Intent intent = new Intent(OrderActivity.this, PromotionByMonth.class);
        intent.putExtra(Const.SHOP_NAME, SHOP_NAME);
        intent.putExtra(Const.SHOP_ADDRESS, SHOP_ADDRESS);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        handleBack();
    }

    String data_115 = "";

    void API_115() {
        if (data_115.length() > 0) {
            new HttpRequest(this).new API_115(MainActivity.dataLogin, data_115, new IF_115() {
                @Override
                public void onSuccess() {
//                    Toast.makeText(getApplicationContext(), "API 115 OK", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "API 115 ok");
                }

                @Override
                public void onFail() {
                    if (data_115.length() > 0) {
                        DatabaseHandler db = new DatabaseHandler(OrderActivity.this);
                        RESEND_115 ob = new RESEND_115(data_115);
                        db.add_115(ob);
                    }
                    Log.d(TAG, "API 115 FAIL");

                }
            }).execute();
        }
    }

    void initDB_115() {
        if (RouteFragment.KEY_ROUTE.length() > 0 && MainActivity.SHOP_ID.length() > 0 && MainActivity.SEQ.length() > 0) {
            int type = 1;
            String TIME = Const.getNowTime();
            DataLogin dataLogin = new Gson().fromJson(new PrefManager(this).getInfoLogin(), DataLogin.class);
            Map<String, String> map = new HashMap<>();
            map.put("MODE", "" + type);
            map.put("DATE", "" + Const.getToday());
            map.put("EMPID", "" + dataLogin.getUSERID());
            map.put("WKDAY", "" + RouteFragment.KEY_ROUTE);
            map.put("CUSTCD", MainActivity.SHOP_ID);
            map.put("SEQ", MainActivity.SEQ);
            if (type == 1) {
                map.put("INTIME", TIME);
                map.put("OUTTIME", "");
            } else if (type == 2) {
                map.put("INTIME", "");
                map.put("OUTTIME", TIME);
            }
            map.put("ISOPEN", "N");
            String ISINSHOP = "N";
            if (MainActivity.DISTANCE <= MainActivity.CHECKSHOPDISTANCE)
                ISINSHOP = "Y";
            map.put("ISINSHOP", ISINSHOP);
            data_115 = new Gson().toJson(map);
            Log.d(TAG, "data_115:" + data_115);
        }
    }
}
