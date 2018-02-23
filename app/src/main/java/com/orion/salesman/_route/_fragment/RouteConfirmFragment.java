package com.orion.salesman._route._fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.FloatRange;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.orion.salesman.MainActivity;
import com.orion.salesman.OrderActivity;
import com.orion.salesman.R;
import com.orion.salesman._app.MyApplication;
import com.orion.salesman._class.Const;
import com.orion.salesman._class.CustomDialog;
import com.orion.salesman._class.HttpRequest;
import com.orion.salesman._class.getListPromotion;
import com.orion.salesman._draw.DrawingView;
import com.orion.salesman._interface.IF_115;
import com.orion.salesman._interface.IF_118;
import com.orion.salesman._interface.IF_119;
import com.orion.salesman._interface.IF_124;
import com.orion.salesman._interface.OnClickDialog;
import com.orion.salesman._interface.Order;
import com.orion.salesman._object.API_121;
import com.orion.salesman._object.API_122;
import com.orion.salesman._object.API_123;
import com.orion.salesman._object.BUY;
import com.orion.salesman._object.ConvertUnit;
import com.orion.salesman._object.DataBase128;
import com.orion.salesman._object.DataBase129;
import com.orion.salesman._object.DataLogin;
import com.orion.salesman._object.GetNewShopPromotionList;
import com.orion.salesman._object.ListSKUPromotion;
import com.orion.salesman._object.NewShopPromotionList;
import com.orion.salesman._object.Obj_124;
import com.orion.salesman._object.Obj_125;
import com.orion.salesman._object.ObjectA0129;
import com.orion.salesman._object.OrderBuying;
import com.orion.salesman._object.OrderGifItem;
import com.orion.salesman._object.OrderGift;
import com.orion.salesman._object.OrderResult;
import com.orion.salesman._object.OrderTable;
import com.orion.salesman._object.PMPRODUCTLIST;
import com.orion.salesman._object.PmList;
import com.orion.salesman._object.ProductInfor;
import com.orion.salesman._object.RESEND_125;
import com.orion.salesman._object.SKUPromotion;
import com.orion.salesman._object.SaveInputData;
import com.orion.salesman._object.SendPosition;
import com.orion.salesman._object.TABLE_INPUT;
import com.orion.salesman._object.TeamDisplay;
import com.orion.salesman._object.VisitShop;
import com.orion.salesman._offline.OfflineObject;
import com.orion.salesman._pref.PrefManager;
import com.orion.salesman._route._adapter.PromotionProductAdapter;
import com.orion.salesman._route._adapter.RouteConfirmFragmentAdapter;
import com.orion.salesman._route._object.DisplayInput;
import com.orion.salesman._route._object.InputStock;
import com.orion.salesman._route._object.Pie;
import com.orion.salesman._route._object.PromotionInfor;
import com.orion.salesman._route._object.PromotionProduct;
import com.orion.salesman._route._object.RouteConfirm;
import com.orion.salesman._route._object.Snack;
import com.orion.salesman._route._object.StockInputObject;
import com.orion.salesman._sqlite.DataBaseHelper_2;
import com.orion.salesman._sqlite.DatabaseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
import java.util.UUID;

import kr.co.eksys.nativelib.EksysNetworkException;
import kr.co.eksys.nativelib.NetworkManager;

/**
 * Created by maidinh on 16/8/2016.
 */
public class RouteConfirmFragment extends Fragment {
    public static RouteConfirmFragment confirmFragment;
    private String TAG = "RouteConfirmFragment";
    private List<RouteConfirm> arrayList = new ArrayList<>();
    private List<PromotionProduct> productList = new ArrayList<>();
    private RecyclerView recyclerView, recyclerView_2;
    private RouteConfirmFragmentAdapter mAdapter;
    private PromotionProductAdapter productAdapter;
    private DrawingView drawView;
    private RelativeLayout rlConfirm;
    private TextView tvNodata;
    private View viewTotal;
    private TextView T1, T2, T3, T4, T5, T6, tvOrderRS, tvOrderDS;
    private ProgressBar progressbar;

    public RouteConfirmFragment() {

    }

    List<Pie> ListPie = new ArrayList<>();
    List<Pie> ListGum = new ArrayList<>();
    List<Snack> ListSnack = new ArrayList<>();

    void saveListOrder() {
        PrefManager pref = new PrefManager(mActivity);
        String sPie = pref.getRouteOrderPie();
        String sGum = pref.getRouteOrderGum();
        String sSnack = pref.getRouteOrderSnack();

        Type listType = new TypeToken<List<Pie>>() {
        }.getType();
        List<Pie> ListPie = new Gson().fromJson(sPie, listType);
        for (int i = 0; i < ListPie.size(); i++) {
            RouteConfirm ob = new RouteConfirm();
            ob.setV1(ListPie.get(i).getColumnsOne());
            List<Pie> pieList = ListPie.get(i).getArrPies();
            for (int k = 0; k < pieList.size(); k++) {
                if (pieList.get(k).getColumnsFive() != null && !pieList.get(k).getColumnsFive().equals("")) {
                    pieList.get(k).setIsSave(true);
                }
            }
        }
        pref.setRouteOrderPie(new Gson().toJson(ListPie));

        listType = new TypeToken<List<Pie>>() {
        }.getType();
        List<Pie> ListGum = new Gson().fromJson(sGum, listType);
        for (int i = 0; i < ListGum.size(); i++) {
            RouteConfirm ob = new RouteConfirm();
            ob.setV1(ListGum.get(i).getColumnsOne());
            List<Pie> pieList = ListGum.get(i).getArrPies();
            for (int k = 0; k < pieList.size(); k++) {
                if (pieList.get(k).getColumnsFive() != null && !pieList.get(k).getColumnsFive().equals("")) {
                    pieList.get(k).setIsSave(true);
                }
            }
        }
        pref.setRouteOrderGum(new Gson().toJson(ListGum));

        Type snackType = new TypeToken<List<Snack>>() {
        }.getType();
        List<Snack> ListSnack = new Gson().fromJson(sSnack, snackType);
        for (int i = 0; i < ListSnack.size(); i++) {
            RouteConfirm ob = new RouteConfirm();
            ob.setV1(ListSnack.get(i).getColumnsOne());
            List<Snack> pieList = ListSnack.get(i).getArrSnacks();
            for (int k = 0; k < pieList.size(); k++) {
                if (pieList.get(k).getColumnsSix() != null && !pieList.get(k).getColumnsSix().equals("")) {
                    pieList.get(k).setIsSave(true);
                }
            }
        }
        pref.setRouteOrderSnack(new Gson().toJson(ListSnack));
    }

    void initDB(View v) {

        arrayList.clear();
        PrefManager pref = new PrefManager(mActivity);
        String sPie = pref.getRouteOrderPie();
        String sGum = pref.getRouteOrderGum();
        String sSnack = pref.getRouteOrderSnack();

        Type listType = new TypeToken<List<Pie>>() {
        }.getType();
        ListPie = new Gson().fromJson(sPie, listType);
        for (int i = 0; i < ListPie.size(); i++) {
            RouteConfirm ob = new RouteConfirm();
            ob.setV1(ListPie.get(i).getColumnsOne());
            List<Pie> pieList = ListPie.get(i).getArrPies();
            for (int k = 0; k < pieList.size(); k++) {
                if (pieList.get(k).getColumnsFive() != null && !pieList.get(k).getColumnsFive().equals("")) {
                    RouteConfirm routeConfirm = new RouteConfirm();
                    routeConfirm.setV1(pieList.get(k).getColumnsTwo());
                    routeConfirm.setV2(pieList.get(k).getColumnsFive());
                    routeConfirm.setTPRDCD(pieList.get(k).getTPRDCD());
                    routeConfirm.setV6(pieList.get(k).ispmt());
                    routeConfirm.setPRDCD(pieList.get(k).getPRDCD());
                    routeConfirm.setBXEAQTY(pieList.get(k).getBXEAQTY());
                    routeConfirm.setCSEEAQTY(pieList.get(k).getCSEEAQTY());
                    routeConfirm.setBXCSEQTY(pieList.get(k).getBXCSEQTY());
                    String tv_3 = "";

                    long a = Long.parseLong(pieList.get(k).getColumnsFive());
                    if (pieList.get(k).getEASALE().equals("Y")) {
                        routeConfirm.setKIND(1);
                        long b = (long) Double.parseDouble(pieList.get(k).getEAPRICE());
                        Log.d(TAG, pieList.get(k).getEAPRICE());
                        long c = a * b;
                        String s = "" + c;
//                        tv_3 = Const.RoundNumber(s);
                        tv_3 = s;
                    } else if (pieList.get(k).getCASESALE().equalsIgnoreCase("Y")) {
                        routeConfirm.setKIND(2);
                        long b = (long) Double.parseDouble(pieList.get(k).getCASEPRICE());
                        Log.d(TAG, pieList.get(k).getCASEPRICE());
                        long c = a * b;
                        String s = "" + c;
//                        tv_3 = Const.RoundNumber(s);
                        tv_3 = s;
                    } else {
                        routeConfirm.setKIND(3);
                        long b = (long) Double.parseDouble(pieList.get(k).getSTOREPRICE());
                        Log.d(TAG, pieList.get(k).getSTOREPRICE());
                        long c = a * b;
                        String s = "" + c;
//                        tv_3 = Const.RoundNumber(s);
                        tv_3 = s;
                    }
                    routeConfirm.setV3(tv_3);
                    routeConfirm.setIsCheck(pieList.get(k).isCheck());
                    routeConfirm.setPMID(pieList.get(k).getPMID());
                    routeConfirm.setGIFTAMTPERCENT(pieList.get(k).getGIFTAMTPERCENT());
                    routeConfirm.setIsSave(pieList.get(k).isSave());
                    routeConfirm.setPRDCLS1(pieList.get(k).getPRDCLS1());
                    arrayList.add(routeConfirm);
                }
            }
        }
        listType = new TypeToken<List<Pie>>() {
        }.getType();
        ListGum = new Gson().fromJson(sGum, listType);
        for (int i = 0; i < ListGum.size(); i++) {
            RouteConfirm ob = new RouteConfirm();
            ob.setV1(ListGum.get(i).getColumnsOne());
            List<Pie> pieList = ListGum.get(i).getArrPies();
            for (int k = 0; k < pieList.size(); k++) {
                if (pieList.get(k).getColumnsFive() != null && !pieList.get(k).getColumnsFive().equals("")) {
                    RouteConfirm routeConfirm = new RouteConfirm();
                    routeConfirm.setV1(pieList.get(k).getColumnsTwo());
                    routeConfirm.setV2(pieList.get(k).getColumnsFive());
                    routeConfirm.setTPRDCD(pieList.get(k).getTPRDCD());
                    routeConfirm.setPRDCD(pieList.get(k).getPRDCD());
                    routeConfirm.setV6(pieList.get(k).ispmt());
                    routeConfirm.setBXEAQTY(pieList.get(k).getBXEAQTY());
                    routeConfirm.setCSEEAQTY(pieList.get(k).getCSEEAQTY());
                    routeConfirm.setBXCSEQTY(pieList.get(k).getBXCSEQTY());
                    String tv_3 = "";
                    double a = Double.parseDouble(pieList.get(k).getColumnsFive());
                    String UNIT = "";
                    if (pieList.get(k).getEASALE().equals("Y")) {
                        routeConfirm.setKIND(1);
                        double b = Double.parseDouble(pieList.get(k).getEAPRICE());
                        double c = a * b;
                        String s = "" + c;
                        tv_3 = Const.RoundNumber(s);
                    } else if (pieList.get(k).getCASESALE().equalsIgnoreCase("Y")) {
                        routeConfirm.setKIND(2);
                        double b = Double.parseDouble(pieList.get(k).getCASEPRICE());
                        double c = a * b;
                        String s = "" + c;
                        tv_3 = Const.RoundNumber(s);
                    } else {
                        routeConfirm.setKIND(3);
                        double b = Double.parseDouble(pieList.get(k).getSTOREPRICE());
                        double c = a * b;
                        String s = "" + c;
                        tv_3 = Const.RoundNumber(s);
                    }
                    routeConfirm.setV3(tv_3);
                    routeConfirm.setIsCheck(pieList.get(k).isCheck());
                    routeConfirm.setPMID(pieList.get(k).getPMID());
                    routeConfirm.setGIFTAMTPERCENT(pieList.get(k).getGIFTAMTPERCENT());
                    routeConfirm.setIsSave(pieList.get(k).isSave());
                    routeConfirm.setPRDCLS1(pieList.get(k).getPRDCLS1());
                    arrayList.add(routeConfirm);
                }
            }
        }
        Type snackType = new TypeToken<List<Snack>>() {
        }.getType();
        ListSnack = new Gson().fromJson(sSnack, snackType);
        for (int i = 0; i < ListSnack.size(); i++) {
            RouteConfirm ob = new RouteConfirm();
            ob.setV1(ListSnack.get(i).getColumnsOne());
            List<Snack> pieList = ListSnack.get(i).getArrSnacks();
            for (int k = 0; k < pieList.size(); k++) {
                if (pieList.get(k).getColumnsSix() != null && !pieList.get(k).getColumnsSix().equals("")) {
                    RouteConfirm routeConfirm = new RouteConfirm();
                    routeConfirm.setV1(pieList.get(k).getColumnsTwo());
                    routeConfirm.setV2(pieList.get(k).getColumnsSix());
                    routeConfirm.setTPRDCD(pieList.get(k).getTPRDCD());
                    routeConfirm.setPRDCD(pieList.get(k).getPRDCD());
                    routeConfirm.setV6(pieList.get(k).ispmt());


                    routeConfirm.setBXEAQTY(pieList.get(k).getBXEAQTY());
                    routeConfirm.setCSEEAQTY(pieList.get(k).getCSEEAQTY());
                    routeConfirm.setBXCSEQTY(pieList.get(k).getBXCEQTY());
                    String tv_3 = "";
                    double a = Double.parseDouble(pieList.get(k).getColumnsSix());
//                    Log.d(TAG,"a:"+a);
//                    Log.d(TAG,"pieList.get(k).getTPRDCD():"+pieList.get(k).getTPRDCD());
                    if (pieList.get(k).getEASALE().equals("Y")
                            && !Const.formatAMT(Long.parseLong(Const.getPartInt(pieList.get(k).getEAPRICE()))).equals("0")) {
//                        Log.d(TAG, "1:");
                        routeConfirm.setKIND(1);
                        double b = Double.parseDouble(pieList.get(k).getEAPRICE());
                        double c = a * b;
                        String s = "" + c;
                        tv_3 = Const.RoundNumber(s);
                    } else if (pieList.get(k).getCASESALE().equalsIgnoreCase("Y")
                            && !Const.formatAMT(Long.parseLong(Const.getPartInt(pieList.get(k).getCASEPRICE()))).equals("0")) {
//                        Log.d(TAG, "2:");
                        routeConfirm.setKIND(2);
                        double b = Double.parseDouble(pieList.get(k).getCASEPRICE());
                        double c = a * b;
                        String s = "" + c;
                        tv_3 = Const.RoundNumber(s);
                    } else {
//                        Log.d(TAG, "3:");
                        routeConfirm.setKIND(3);
                        double b = Double.parseDouble(pieList.get(k).getSTOREPRICE());
                        double c = a * b;
                        String s = "" + c;
                        tv_3 = Const.RoundNumber(s);
                    }
//                    Log.d(TAG, "tv3:" + tv_3);
                    routeConfirm.setV3(tv_3);
                    routeConfirm.setIsCheck(pieList.get(k).isCheck());
                    routeConfirm.setPMID(pieList.get(k).getPMID());
                    routeConfirm.setGIFTAMTPERCENT(pieList.get(k).getGIFTAMTPERCENT());
                    routeConfirm.setIsSave(pieList.get(k).isSave());
                    routeConfirm.setPRDCLS1(pieList.get(k).getPRDCLS1());
                    arrayList.add(routeConfirm);
                }
            }
        }

//        for(RouteConfirm obj:arrayList)
//        {
//            Log.d(TAG,"RouteConfirm:"+new Gson().toJson(obj));
//        }

        viewTotal = (View) v.findViewById(R.id.viewTotal);
        T1 = (TextView) viewTotal.findViewById(R.id.tvOne);
        T2 = (TextView) viewTotal.findViewById(R.id.tvTwo);
        T3 = (TextView) viewTotal.findViewById(R.id.tvThree);
        T4 = (TextView) viewTotal.findViewById(R.id.tvFour);
        T5 = (TextView) viewTotal.findViewById(R.id.tvFive);
        T6 = (TextView) viewTotal.findViewById(R.id.tvSix);
        Const.setTextColorSum(T1);
        Const.setTextColorSum(T2);
        Const.setTextColorSum(T3);
        Const.setTextColorSum(T4);
        Const.setTextColorSum(T5);
//        Const.setTextColorSum(T6);
        Const.setColorSum(T1);
        Const.setColorSum(T2);
        Const.setColorSum(T3);
        Const.setColorSum(T4);
        Const.setColorSum(T5);
        Const.setColorSum(T6);

        tvOrderRS = (TextView) viewTotal.findViewById(R.id.tvOrderRS);
        tvOrderDS = (TextView) viewTotal.findViewById(R.id.tvOrderDS);
        tvOrderRS.setVisibility(View.GONE);
        tvOrderDS.setVisibility(View.GONE);

        tvNodata = (TextView) v.findViewById(R.id.tvNodata);
        if (arrayList.size() == 0) {
            tvNodata.setVisibility(View.VISIBLE);
            viewTotal.setVisibility(View.GONE);
        } else {
            tvNodata.setVisibility(View.GONE);
            T1.setText(getResources().getString(R.string.total));
            long tv2 = 0;
            long tv4 = 0;
            long tv3 = 0;
            long tv5 = 0;
            for (int i = 0; i < arrayList.size(); i++) {
                if (!arrayList.get(i).isCheck()) {
                    if (arrayList.get(i).getV2() != null)
                        tv2 += Integer.parseInt(arrayList.get(i).getV2());
                    if (arrayList.get(i).getV3() != null)
                        tv3 += (long) Double.parseDouble(arrayList.get(i).getV3());
                } else {
                    if (arrayList.get(i).getV2() != null)
                        tv4 += Integer.parseInt(arrayList.get(i).getV2());
                    if (arrayList.get(i).getV3() != null)
                        tv5 += (long) Double.parseDouble(arrayList.get(i).getV3());
                }
            }
            T2.setText("" + tv2);
            T4.setText("" + tv4);
//            int rs = (int) tv3;
//            int ds = (int) tv5;

            T3.setText(Const.formatAMT(tv3) + "");
            T5.setText(Const.formatAMT(tv5) + "");
            long sum = tv3 + tv5;
            T6.setText(Const.formatAMT(sum) + "");
            if (sum == 0) {
                tvNodata.setVisibility(View.VISIBLE);
                viewTotal.setVisibility(View.GONE);
            }
        }

        initBD2();
    }

    public void UpdateTextSum(long t2, long t3, long t4, long t5) {
        if (t2 == 0 && t3 == 0 && t4 == 0 && t5 == 0) {
            viewTotal.setVisibility(View.GONE);
            tvNodata.setVisibility(View.VISIBLE);
        } else {
            T2.setText("" + t2);
            T4.setText("" + t4);
            T3.setText(Const.formatAMT(t3) + "");
            T5.setText(Const.formatAMT(t5) + "");
        }
    }

    boolean checkContains(PMPRODUCTLIST pmproductlist, String TPRDCD) {
        List<PMPRODUCTLIST> aList = pmproductlist.getPMPRODUCTLIST();
        for (int k = 0; k < aList.size(); k++) {
            PMPRODUCTLIST ppd = aList.get(k);
            if (TPRDCD.equalsIgnoreCase(ppd.getV5()) && ppd.getV1().equals("BUY"))
                return true;
        }
        return false;
    }

    List<API_121> api_121List = new ArrayList<>();
    PmList pmList = new PmList();
//    List<PMPRODUCTLIST> LIST_PROMOTION = new ArrayList<>();


    List<NewShopPromotionList> LIST_PMT = new ArrayList<>();


    List<OrderBuying> lstBuy = new ArrayList<>();

    void updateListPromotion() {
        lstBuy.clear();
//        LIST_PROMOTION.clear();
        productList.clear();

        String QTY = "";
        arrayList = mAdapter.getList();
        for (int h = 0; h < arrayList.size(); h++) {
            if (arrayList.get(h).isV6()) {
                String V2 = arrayList.get(h).getV2();
                if (V2.equals("") || V2.equals("0") || V2 == null) {
                    QTY = arrayList.get(h).getV4();
                } else {
                    QTY = V2;
                }
                if (QTY.length() > 0) {
                    int INPUT_QTY = Integer.parseInt(QTY);
                    int KIND = arrayList.get(h).getKIND();
                    int UNIT = arrayList.get(h).getKIND();
                    if (UNIT == 1)
                        UNIT = 3;
                    if (UNIT == 3)
                        UNIT = 1;
                    if (KIND == 2) {
                        if (arrayList.get(h).getCSEEAQTY().length() > 0 && !arrayList.get(h).getCSEEAQTY().equals("0")) {
                            INPUT_QTY = INPUT_QTY * Integer.parseInt(arrayList.get(h).getCSEEAQTY());
                        }
                    } else if (KIND == 3) {
                        if (arrayList.get(h).getBXEAQTY().length() > 0 && !arrayList.get(h).getBXEAQTY().equals("0")) {
                            INPUT_QTY = INPUT_QTY * Integer.parseInt(arrayList.get(h).getBXEAQTY());
                        }
                    }


                    OrderBuying obj = new OrderBuying();
                    obj.setPRDCD(arrayList.get(h).getPRDCD());
                    obj.setSKUCD(arrayList.get(h).getTPRDCD());
                    obj.setBRNCD(arrayList.get(h).getPRDCLS1());
                    obj.setUNIT(UNIT);
                    obj.setQTY(INPUT_QTY);

                    obj.setDS(arrayList.get(h).isCheck());


                    int CSES = Integer.parseInt(arrayList.get(h).getCSEEAQTY());
                    int BXCS = Integer.parseInt(arrayList.get(h).getBXCSEQTY());
                    int BXES = Integer.parseInt(arrayList.get(h).getBXEAQTY());

                    if (CSES == 0)
                        CSES = 1;
                    if (BXES == 0) {
                        if (BXCS == 0) {
                            BXCS = 1;
                            BXES = CSES;
                        } else
                            BXES = BXCS * CSES;
                    }
                    if (BXCS == 0) {
                        BXCS = BXES / CSES;
                    }

                    obj.setCSES(CSES);
                    obj.setBXCS(BXCS);
                    obj.setBXES(BXES);
//                    Log.d(TAG, "CSES:" + CSES + " BXCS:" + BXCS + " BXES:" + BXES);
                    obj.setLIST_PROMOTION(getPromotionListForProduct(arrayList.get(h).getTPRDCD(), CSES, BXCS, BXES));
                    lstBuy.add(obj);
//                    getDBPromotionList(arrayList.get(h).getTPRDCD());
//                    if (LIST_PROMOTION != null && LIST_PROMOTION.size() > 0) {
//                        for (int i = 0; i < LIST_PROMOTION.size(); i++) {
//                            for (int j = 0; j <= i; j++) {
//                                if (Float.parseFloat(LIST_PROMOTION.get(i).getV17()) >= Float.parseFloat(LIST_PROMOTION.get(j).getV17())) {
//                                    PMPRODUCTLIST pp = LIST_PROMOTION.get(i);
//                                    LIST_PROMOTION.set(i, LIST_PROMOTION.get(j));
//                                    LIST_PROMOTION.set(j, pp);
//                                }
//                            }
//                        }
//                        for (int i = 0; i < LIST_PROMOTION.size(); i++) {
//                            for (int j = 0; j <= i; j++) {
//                                if (Float.parseFloat(LIST_PROMOTION.get(i).getV4()) < Float.parseFloat(LIST_PROMOTION.get(j).getV4())) {
//                                    PMPRODUCTLIST pp = LIST_PROMOTION.get(i);
//                                    LIST_PROMOTION.set(i, LIST_PROMOTION.get(j));
//                                    LIST_PROMOTION.set(j, pp);
//                                }
//                            }
//                        }
//                    }

                }
            }
        }

//        Const.longInfo(TAG, "LIST_PROMOTION:" + new Gson().toJson(LIST_PROMOTION));
        Log.d(TAG, "lstBuy:" + new Gson().toJson(lstBuy));

        LIST_PM = getListPromotion.ORD_GiftListPromotion(lstBuy);
        productList.clear();
        listKM.clear();
        if (LIST_PM == null)
            Log.d(TAG, "LIST_PM null");
        else {
            Log.d(TAG, "LIST_PM != null");
            Const.longInfo(TAG, "LIST_PM:" + new Gson().toJson(LIST_PM));

            if (LIST_PM != null) {
                if (LIST_PM.getLstBuy() != null && LIST_PM.getLstBuy().size() > 0) {
                    for (int i = 0; i < LIST_PM.getLstBuy().size(); i++) {
                        OrderGift orderGift = LIST_PM.getLstBuy().get(i);
                        if (orderGift.getBuyList() != null && orderGift.getBuyList().size() > 0) {
                            for (int j = 0; j < orderGift.getBuyList().size(); j++) {
                                OrderBuying orderBuying = orderGift.getBuyList().get(j);
                                boolean flag = true;
                                for (String s : listKM) {
                                    if (s.equals(orderBuying.getPRDCD())) {
                                        flag = false;
                                        break;
                                    }
                                }
                                if (flag) {
                                    listKM.add(orderBuying.getPRDCD());
                                }
                            }
                        }


                        int CONDITION = orderGift.getCONDITION();
                        String PMID = orderGift.getPMID();

                        String V2 = orderGift.getPMDES();
                        String V4 = orderGift.getPMID();
                        if (CONDITION == 0 && orderGift.getGiftList() != null && orderGift.getGiftList().size() > 0) {
                            for (int j = 0; j < orderGift.getGiftList().size(); j++) {
                                OrderGifItem orderGifItem = orderGift.getGiftList().get(j);
                                PromotionProduct product = new PromotionProduct();

                                product.setV2(V2);
                                product.setV4(V4);
                                List<OrderGifItem> itemList = new ArrayList<>();
                                itemList.add(orderGifItem);
                                product.setCONDITION(CONDITION);
                                product.setItemList(itemList);
                                product.setPMID(PMID);
                                productList.add(product);

                            }
                        } else if (CONDITION == 1 && orderGift.getGiftList() != null && orderGift.getGiftList().size() > 0) {
                            List<OrderGifItem> itemList = new ArrayList<>();
                            for (int j = 0; j < orderGift.getGiftList().size(); j++) {
                                OrderGifItem orderGifItem = orderGift.getGiftList().get(j);
                                itemList.add(orderGifItem);
                            }
                            PromotionProduct product = new PromotionProduct();
                            product.setV2(V2);
                            product.setV4(V4);
                            product.setCONDITION(CONDITION);
                            product.setItemList(itemList);
                            product.setPMID(PMID);

                            productList.add(product);
                        }
                    }
                }
            }
        }
        if (arrayList != null && arrayList.size() > 0) {
            mAdapter.updateListPM(listKM);

        }


        productAdapter.updateList(productList);
        if (productList != null && productList.size() > 0) {
            tvTOTAL.setText(getResources().getString(R.string.total) + " : " + productList.size());
        } else {
            tvTOTAL.setText(getResources().getString(R.string.total) + " : 0");
        }
    }

    List<String> listKM = new ArrayList<>();

    void initBD2() {
        lstBuy.clear();
//        LIST_PROMOTION.clear();
        productList.clear();

        String QTY = "";
        for (int h = 0; h < arrayList.size(); h++) {
            if (arrayList.get(h).isV6()) {
                String V2 = arrayList.get(h).getV2();
                if (V2.equals("") || V2.equals("0") || V2 == null) {
                    QTY = arrayList.get(h).getV4();
                } else {
                    QTY = V2;
                }
                if (QTY.length() > 0) {
                    int INPUT_QTY = Integer.parseInt(QTY);
                    int KIND = arrayList.get(h).getKIND();
                    int UNIT = arrayList.get(h).getKIND();
                    if (UNIT == 1)
                        UNIT = 3;
                    if (UNIT == 3)
                        UNIT = 1;
                    if (KIND == 2) {
                        if (arrayList.get(h).getCSEEAQTY().length() > 0 && !arrayList.get(h).getCSEEAQTY().equals("0")) {
                            INPUT_QTY = INPUT_QTY * Integer.parseInt(arrayList.get(h).getCSEEAQTY());
                        }
                    } else if (KIND == 3) {
                        if (arrayList.get(h).getBXEAQTY().length() > 0 && !arrayList.get(h).getBXEAQTY().equals("0")) {
                            INPUT_QTY = INPUT_QTY * Integer.parseInt(arrayList.get(h).getBXEAQTY());
                        }
                    }


                    OrderBuying obj = new OrderBuying();
                    obj.setPRDCD(arrayList.get(h).getPRDCD());
                    obj.setSKUCD(arrayList.get(h).getTPRDCD());
                    obj.setBRNCD(arrayList.get(h).getPRDCLS1());
                    obj.setUNIT(UNIT);
                    obj.setQTY(INPUT_QTY);

                    obj.setDS(arrayList.get(h).isCheck());

                    int CSES = Integer.parseInt(arrayList.get(h).getCSEEAQTY());
                    int BXCS = Integer.parseInt(arrayList.get(h).getBXCSEQTY());
                    int BXES = Integer.parseInt(arrayList.get(h).getBXEAQTY());

                    if (CSES == 0)
                        CSES = 1;
                    if (BXES == 0) {
                        if (BXCS == 0) {
                            BXCS = 1;
                            BXES = CSES;
                        } else
                            BXES = BXCS * CSES;
                    }
                    if (BXCS == 0) {
                        BXCS = BXES / CSES;
                    }

                    obj.setCSES(CSES);
                    obj.setBXCS(BXCS);
                    obj.setBXES(BXES);


                    obj.setLIST_PROMOTION(getPromotionListForProduct(arrayList.get(h).getTPRDCD(), CSES, BXCS, BXES));

//                    Log.d(TAG, "CSES:" + CSES + " BXCS:" + BXCS + " BXES:" + BXES);
                    lstBuy.add(obj);


//                    getDBPromotionList(arrayList.get(h).getTPRDCD());
//                    if (LIST_PROMOTION != null && LIST_PROMOTION.size() > 0) {
//                        for (int i = 0; i < LIST_PROMOTION.size(); i++) {
//                            for (int j = 0; j <= i; j++) {
//                                if (Float.parseFloat(LIST_PROMOTION.get(i).getV17()) >= Float.parseFloat(LIST_PROMOTION.get(j).getV17())) {
//                                    PMPRODUCTLIST pp = LIST_PROMOTION.get(i);
//                                    LIST_PROMOTION.set(i, LIST_PROMOTION.get(j));
//                                    LIST_PROMOTION.set(j, pp);
//                                }
//                            }
//                        }
//                        for (int i = 0; i < LIST_PROMOTION.size(); i++) {
//                            for (int j = 0; j <= i; j++) {
//                                if (Float.parseFloat(LIST_PROMOTION.get(i).getV4()) < Float.parseFloat(LIST_PROMOTION.get(j).getV4())) {
//                                    PMPRODUCTLIST pp = LIST_PROMOTION.get(i);
//                                    LIST_PROMOTION.set(i, LIST_PROMOTION.get(j));
//                                    LIST_PROMOTION.set(j, pp);
//                                }
//                            }
//                        }
//                    }

                }
            }
        }

//        Const.longInfo(TAG, "LIST_PROMOTION:" + new Gson().toJson(LIST_PROMOTION));
        Const.longInfo(TAG, "lstBuy:" + new Gson().toJson(lstBuy));

        LIST_PM = getListPromotion.ORD_GiftListPromotion(lstBuy);
        productList.clear();
        listKM.clear();
        if (LIST_PM == null)
            Log.d(TAG, "LIST_PM null");
        else {
            Log.d(TAG, "LIST_PM != null");
            Const.longInfo(TAG, "LIST_PM:" + new Gson().toJson(LIST_PM));

            if (LIST_PM != null) {
                if (LIST_PM.getLstBuy() != null && LIST_PM.getLstBuy().size() > 0) {
                    for (int i = 0; i < LIST_PM.getLstBuy().size(); i++) {
                        OrderGift orderGift = LIST_PM.getLstBuy().get(i);

                        if (orderGift.getBuyList() != null && orderGift.getBuyList().size() > 0) {
                            for (int j = 0; j < orderGift.getBuyList().size(); j++) {
                                OrderBuying orderBuying = orderGift.getBuyList().get(j);
                                boolean flag = true;
                                for (String s : listKM) {
                                    if (s.equals(orderBuying.getPRDCD())) {
                                        flag = false;
                                        break;
                                    }
                                }
                                if (flag) {
                                    listKM.add(orderBuying.getPRDCD());
                                }
                            }
                        }

                        int CONDITION = orderGift.getCONDITION();

                        String PMID = orderGift.getPMID();
                        String V2 = orderGift.getPMDES();
                        String V4 = orderGift.getPMID();
                        if (CONDITION == 0 && orderGift.getGiftList() != null && orderGift.getGiftList().size() > 0) {
                            for (int j = 0; j < orderGift.getGiftList().size(); j++) {
                                OrderGifItem orderGifItem = orderGift.getGiftList().get(j);
                                PromotionProduct product = new PromotionProduct();

                                product.setV2(V2);
                                product.setV4(V4);
                                List<OrderGifItem> itemList = new ArrayList<>();
                                itemList.add(orderGifItem);
                                product.setCONDITION(CONDITION);
                                product.setItemList(itemList);
                                product.setPMID(PMID);
                                productList.add(product);
                            }
                        } else if (CONDITION == 1 && orderGift.getGiftList() != null && orderGift.getGiftList().size() > 0) {
                            List<OrderGifItem> itemList = new ArrayList<>();
                            for (int j = 0; j < orderGift.getGiftList().size(); j++) {
                                OrderGifItem orderGifItem = orderGift.getGiftList().get(j);
                                itemList.add(orderGifItem);
                            }
                            PromotionProduct product = new PromotionProduct();

                            product.setV2(V2);
                            product.setV4(V4);
                            product.setCONDITION(CONDITION);
                            product.setItemList(itemList);
                            product.setPMID(PMID);
                            productList.add(product);
                        }
                    }
                }
            }
//            productList
        }

        if (productList != null && productList.size() > 0) {
            tvTOTAL.setText(getResources().getString(R.string.total) + " : " + productList.size());
        } else {
            tvTOTAL.setText(getResources().getString(R.string.total) + " : 0");
        }
    }

    OrderResult LIST_PM;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private TextView pmtNodata, tvSignHere;

    public void hidetvSign() {
        if (tvSignHere != null)
            if (tvSignHere.isShown())
                tvSignHere.setVisibility(View.GONE);
    }

    boolean flagSign = false;

    private String getRealPathFromURI(Uri contentUri) {
        String[] proj = {MediaStore.Images.Media.DATA};
        CursorLoader loader = new CursorLoader(mActivity, contentUri, proj, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result = cursor.getString(column_index);
        cursor.close();
        return result;
    }

    void dialogSign() {
        final Dialog dialog = new Dialog(mActivity);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(R.layout.layout_sign);
        tvSignHere = (TextView) dialog.findViewById(R.id.tvSignHere);
        drawView = (DrawingView) dialog.findViewById(R.id.drawing);
        drawView.setColor("#0033cc");
        FrameLayout btnResign = (FrameLayout) dialog.findViewById(R.id.resign);
        btnResign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawView.startNew();
            }
        });
        Button btnOk = (Button) dialog.findViewById(R.id.btnOK);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flagSign = true;
                PIC_NAME = UUID.randomUUID().toString();
                drawView.setDrawingCacheEnabled(true);
                Bitmap b = drawView.getDrawingCache();
                FileOutputStream fos = null;
                String root = Environment.getExternalStorageDirectory().toString();
                File myDir = new File(root);
                myDir.mkdirs();
                File file = new File(myDir, PIC_NAME + ".jpg");
                try {
                    fos = new FileOutputStream(file);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

                b.compress(Bitmap.CompressFormat.PNG, 95, fos);
//                imgSaved = MediaStore.Images.Media
//                        .insertImage(mActivity.getContentResolver(), drawView
//                                        .getDrawingCache(), UUID.randomUUID()
//                                        .toString() + ".png",
//                                "drawing");
                imgSaved = MediaStore.Images.Media
                        .insertImage(mActivity.getContentResolver(), drawView
                                        .getDrawingCache(), "huy",
                                "drawing");
                Log.d(TAG, "imgSaved:" + imgSaved);
                MainActivity.imgSaved = imgSaved;
                drawView.destroyDrawingCache();
                String path = Const.PATH_IMG + PIC_NAME + ".jpg";
//                Log.d(TAG, "path:" + path);
                MainActivity.PATH_IMG = path;
                File imgFile = new File(path);
                if (imgFile.exists()) {
                    Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                    imgSign.setImageBitmap(myBitmap);
                } else {
                }
                if (imgSaved != null && imgSaved.length() > 0) {
                    try {
                        Uri myUri = Uri.parse(imgSaved);
                        String PATH_FILE = getRealPathFromURI(myUri);
                        File fd = new File(PATH_FILE);
                        if (fd.exists()) {
                            fd.delete();
                            mActivity.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(new File(PATH_FILE))));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                dialog.dismiss();
            }
        });
        Button btnCancel = (Button) dialog.findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    private ImageView imgSign;

    public void init(View v) {
        initDB(v);

        imgSign = (ImageView) v.findViewById(R.id.imgSign);
        if (MainActivity.PATH_IMG.length() > 0) {
            flagSign = true;
//            imgSaved = MainActivity.imgSaved;
            File imgFile = new File(MainActivity.PATH_IMG);
            if (imgFile.exists()) {
                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                imgSign.setImageBitmap(myBitmap);
            } else {
            }
        }

        imgSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogSign();
            }
        });
        if (OrderActivity.URL_IMG.startsWith("http")) {
            flagSign = true;
            SIGNURL = OrderActivity.URL_IMG;
            Glide.with(mActivity)
                    .load(OrderActivity.URL_IMG)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(new GlideDrawableImageViewTarget(imgSign) {
                        @Override
                        public void onLoadFailed(Exception e, Drawable errorDrawable) {

                        }
                    });
        }
        progressbar = (ProgressBar) v.findViewById(R.id.progressbar);
        pmtNodata = (TextView) v.findViewById(R.id.pmtNodata);
        mAdapter = new RouteConfirmFragmentAdapter(mActivity, arrayList, confirmFragment);
        recyclerView = (RecyclerView) v.findViewById(R.id.recycler_view_1);
//        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(mActivity);
        recyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        recyclerView.setNestedScrollingEnabled(false);

//        for(PromotionProduct obj:productList)
//        {
//            Log.d(TAG,"abcdef:"+new Gson().toJson(obj));
//        }

        productAdapter = new PromotionProductAdapter(mActivity, productList);
        recyclerView_2 = (RecyclerView) v.findViewById(R.id.recycler_view_2);
        recyclerView_2.setLayoutManager(new LinearLayoutManager(mActivity));
        recyclerView_2.setItemAnimator(new DefaultItemAnimator());
        recyclerView_2.setAdapter(productAdapter);
        recyclerView_2.setNestedScrollingEnabled(false);
        pmtNodata.setVisibility(productList.size() > 0 ? View.GONE : View.VISIBLE);
        tvConfirm = (TextView) v.findViewById(R.id.tvConfirm);

        if (arrayList == null || arrayList.size() == 0) {
            tvConfirm.setText(getResources().getString(R.string.cffinish));
            tvTotalBuy.setText(getResources().getString(R.string.total) + " : 0");
        }
        if (arrayList != null && arrayList.size() > 0) {
            mAdapter.updateListPM(listKM);
            tvTotalBuy.setText(getResources().getString(R.string.total) + " : " + arrayList.size());
        }

        rlConfirm = (RelativeLayout) v.findViewById(R.id.rlConfirm);
        rlConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                initDB_124(); // for test
                actionConfirm();

            }
        });
    }

    public void actionConfirm() {
        OrderActivity.checkClick = false;
        rlConfirm.setEnabled(false);
        if (arrayList == null || arrayList.size() == 0) {
            initDBInputToServer();
            Log.d(TAG, "initDBInputToServer");
            call_API_126();
        } else {
            for (RouteConfirm obj : arrayList) {
                Log.d(TAG, new Gson().toJson(obj));
            }
            ConfirmAction();
            Log.d(TAG, "ConfirmAction");
        }
    }

    boolean initDB = true;

    void ConfirmAction() {
        customDialog = new CustomDialog(mActivity, getResources().getString(R.string.sureconfirm), new OnClickDialog() {
            @Override
            public void btnOK() {
                customDialog.dismiss();
                if (flagSign) {
                    doConfirm();
                } else {
                    progressbar.setVisibility(View.GONE);
                    OrderActivity.checkClick = true;
                    rlConfirm.setEnabled(true);
                    Toast.makeText(mActivity, getResources().getString(R.string.mustsign), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void btnCancel() {
                OrderActivity.checkClick = true;
                rlConfirm.setEnabled(true);
                customDialog.dismiss();
            }
        });
        customDialog.show();
    }

    CustomDialog customDialog;
    String PIC_NAME = "";
    String imgSaved = "";

    void doConfirm() {
        if (Const.checkInternetConnection(mActivity)) {
            if (OrderActivity.URL_IMG.startsWith("http")) {
                initDBInputToServer();
                call_API_126();
            } else {
//                if (imgSaved != null && imgSaved.length() > 0) {
                if (MainActivity.PATH_IMG.length() > 0) {
                    progressbar.setVisibility(View.VISIBLE);
                    String PATH = MainActivity.PATH_IMG;
//                    else PATH = Const.PATH_IMG + PIC_NAME + ".jpg";
                    new UploadFileToServer(PATH).execute();

                } else {
//                    actionConfirm();
                    Toast unsavedToast = Toast.makeText(
                            mActivity, "Oops! Image could not be saved.", Toast.LENGTH_SHORT);
                    unsavedToast.show();
                }
            }
        } else {
//            Toast.makeText(mActivity, getResources().getString(R.string.cannotconnect) + " data will be resend late", Toast.LENGTH_LONG).show();
//
//            finishOrder();
            dialogProblem(0);
        }
    }

    SaveInputData ob = new SaveInputData();

    void savedataOrder() {
        String today = RouteFragment.DATE;
        DatabaseHandler db = new DatabaseHandler(mActivity);

        if (!flag_125 && listHistory != null && listHistory.size() > 0) {
            RESEND_125 ob = new RESEND_125(new Gson().toJson(listHistory), MainActivity.SHOP_CODE, RouteFragment.DATE);
            db.add_125(ob);
        }


        PrefManager pref = new PrefManager(mActivity);
        List<TABLE_INPUT> LIST_INPUT = db.GET_LIST_INPUT();
        List<SaveInputData> dataList = db.getListOrder();
        if (dataList != null && dataList.size() > 0) {
            if (checkListSave(dataList, today, MainActivity.SHOP_CODE)) {
                for (int i = 0; i < dataList.size(); i++) {
                    if (dataList.get(i).getDATE().equals(today) && dataList.get(i).getSHOPID().equals(MainActivity.SHOP_CODE)) {
                        int id = dataList.get(i).getId();
                        db.DELETE_TABLE_ORDER(id);

//                        break;
                    }
                }
            } else {

            }
        }

        if (LIST_INPUT != null && LIST_INPUT.size() > 0) {
            if (CHECK_LIST_INPUT(LIST_INPUT, today, MainActivity.SHOP_CODE)) {
                for (int i = 0; i < LIST_INPUT.size(); i++) {
                    if (LIST_INPUT.get(i).getTABLE_INPUT_DATE().equals(today) && LIST_INPUT.get(i).getTABLE_INPUT_SHOP_ID().equals(MainActivity.SHOP_CODE)) {
                        int id = LIST_INPUT.get(i).getTABLE_INPUT_KEY();
                        db.DELETE_TABLE_INPUT_DATA(id);
//                        break;
                    }

                }
            } else {
            }
        }

        String PATH = Const.PATH_IMG + PIC_NAME + ".jpg";
        if (PIC_NAME.length() == 0)
            PATH = "error";
        ob.setCOUNT_124("" + countOrder);
        ob.setDATA_124(new Gson().toJson(List_124));
        ob.setFILE_PATH_IMG(PATH);
        ob.setDATA_118(data118);
        ob.setDATA_119(data119);
        ob.setDATA_115(data115);
        ob.setDATE(today);
        ob.setSHOPID(MainActivity.SHOP_CODE);
        ob.setURL_IMG(SIGNURL);

        if (mAdapter.getList() != null) {
            if (mAdapter.getList().size() > 0) {
                db.addTABLEORDER(ob);
            }
        }


        TABLE_INPUT table_input = new TABLE_INPUT();
        table_input.setTABLE_INPUT_DATE(today);
        table_input.setTABLE_INPUT_SHOP_ID(MainActivity.SHOP_CODE);
        table_input.setTABLE_INPUT_STOCK_PIE(pref.getRouteStockPie());
        table_input.setTABLE_INPUT_STOCK_SNACK(pref.getRouteStockSnack());
        table_input.setTABLE_INPUT_STOCK_GUM(pref.getRouteStockGum());
        table_input.setTABLE_INPUT_DISPLAY_PIE(pref.getRouteDisplayPie());
        table_input.setTABLE_INPUT_DISPLAY_SNACK(pref.getRouteDisplaySnack());
        table_input.setTABLE_INPUT_ORDER_PIE(pref.getRouteOrderPie());
        table_input.setTABLE_INPUT_ORDER_SNACK(pref.getRouteOrderSnack());
        table_input.setTABLE_INPUT_ORDER_GUM(pref.getRouteOrderGum());
        if (List_124 != null && List_124.size() > 0)
            table_input.setTABLE_INPUT_JSON_ORDER(new Gson().toJson(List_124));
        else table_input.setTABLE_INPUT_JSON_ORDER("");

        String OMI = "N";
        if (data119.length() > 0)
            OMI = "Y";
        MainActivity.valueOMI = OMI;
        table_input.setTABLE_INPUT_OMI(OMI);
        String ORDER = "0";
//        if (List_124 != null && List_124.size() > 0){
//            ORDER = "1";
//        }

        double valueOrder = 0.0;
        List<RouteConfirm> lst = mAdapter.getList();
        if (lst != null && lst.size() > 0) {
            for (RouteConfirm confirm : lst) {
                int a_nOrderBox = 0;
                int a_nOrderCase = 0;
                int a_nOrderEA = 0;

                int nCaseBox = Integer.parseInt(confirm.getBXCSEQTY());
                int nEABox = Integer.parseInt(confirm.getBXEAQTY());
                int qty = Integer.parseInt(confirm.getV2());
                int UNIT = confirm.getKIND(); // 1: EA, 2: Cs, 3: Bx

                if (UNIT == 1) {
                    a_nOrderEA = qty;
                } else if (UNIT == 2) {
                    a_nOrderCase = qty;
                } else {
                    a_nOrderBox = qty;
                }

//                Log.d(TAG, "a_nOrderBox:" + a_nOrderBox);
//                Log.d(TAG, "a_nOrderCase:" + a_nOrderCase);
//                Log.d(TAG, "a_nOrderEA:" + a_nOrderEA);
//                Log.d(TAG, "nCaseBox:" + nCaseBox);
//                Log.d(TAG, "nEABox:" + nEABox);
//                Log.d(TAG, "calulator:" + Const.GetBoxQty(a_nOrderBox, a_nOrderCase, a_nOrderEA, nCaseBox, nEABox));
//                Log.d(TAG, "----------------");

                valueOrder += Const.GetBoxQty(a_nOrderBox, a_nOrderCase, a_nOrderEA, nCaseBox, nEABox);


            }
            ORDER = "" + valueOrder;
        }
        MainActivity.valueOrder = ORDER;


        List<DataBase128> lst128 = db.getList128();
        if (lst128 != null && lst128.size() > 0) {
            for (DataBase128 obj : lst128) {
                if (obj.getCUSTCD_128().equals(MainActivity.SHOP_CODE)) {
                    db.update_128_Order(OMI, ORDER, obj.getId());
                }
            }
        } else {
            DataBase128 ob = new DataBase128(MainActivity.SHOP_CODE, OMI, ORDER, RouteFragment.DATE);
            db.add_128(ob);
        }

        table_input.setTABLE_INPUT_ORDER(ORDER);
        db.ADD_INPUT_DATA(table_input);
    }

    boolean checkListSave(List<SaveInputData> dataList, String DATE, String SHOPID) {
        if (dataList != null && dataList.size() > 0) {
            for (int i = 0; i < dataList.size(); i++) {
                SaveInputData ob = dataList.get(i);
                if (ob.getDATE().equals(DATE) && ob.getSHOPID().equals(SHOPID)) {
                    return true;
                }
            }
        }
        return false;
    }

    boolean CHECK_LIST_INPUT(List<TABLE_INPUT> dataList, String DATE, String SHOPID) {
        if (dataList != null && dataList.size() > 0) {
            for (int i = 0; i < dataList.size(); i++) {
                TABLE_INPUT ob = dataList.get(i);

                if (ob.getTABLE_INPUT_DATE().equals(DATE) && ob.getTABLE_INPUT_SHOP_ID().equals(SHOPID)) {
                    return true;
                }
            }
        }
        return false;
    }

    private List<Pie> listPie = new ArrayList<>();
    private List<Pie> listGum = new ArrayList<>();
    private List<Snack> listSnack = new ArrayList<>();
    private List<StockInputObject> inputPie = new ArrayList<>();

    //    private List<StockInputObject> inputGum = new ArrayList<>();
//    private List<StockInputObject> inputSnack = new ArrayList<>();
    void initDB_118() {
        data118 = "";
        PrefManager prefManager = new PrefManager(mActivity);
        listPie.clear();
        inputPie.clear();
        String pie = prefManager.getRouteStockPie();
//            Const.longInfo(TAG, pie);
        Type listType = new TypeToken<List<Pie>>() {
        }.getType();
        listPie = new Gson().fromJson(pie, listType);
        for (Pie p : listPie) {
            if (p.getArrPies() != null && p.getArrPies().size() > 0) {
                for (int i = 0; i < p.getArrPies().size(); i++) {
                    Pie e = p.getArrPies().get(i);
                    if ((e.getColumnsThree() != null && !e.getColumnsThree().equals(""))
                            || (e.getGOODS() != null && !e.getGOODS().equals(""))) {
                        e.setIsSave(true);
                        StockInputObject ob = new StockInputObject();
                        ob.setCUSTCD(MainActivity.SHOP_ID);
                        ob.setPRDCD(e.getPRDCD());
                        String S1 = e.getColumnsThree();
                        if (S1.equals(""))
                            S1 = "0";
                        ob.setCASEQTY(S1);
                        ob.setEAQTY("0");
                        String S2 = e.getGOODS();
                        if (S2.equals(""))
                            S2 = "0";
                        ob.setRETURNQTY(S2);
                        ob.setSTOCKCATEGORY("01");
                        inputPie.add(ob);
                    }
                }
            }
        }
        prefManager.setRouteStockPie(new Gson().toJson(listPie));

        listGum.clear();
        String gum = prefManager.getRouteStockGum();
//            Const.longInfo(TAG, pie);
        Type listTypeg = new TypeToken<List<Pie>>() {
        }.getType();
        listGum = new Gson().fromJson(gum, listTypeg);
        for (Pie p : listGum) {
            if (p.getArrPies() != null && p.getArrPies().size() > 0) {
                for (int i = 0; i < p.getArrPies().size(); i++) {
                    Pie e = p.getArrPies().get(i);
                    if ((e.getColumnsThree() != null && !e.getColumnsThree().equals(""))
                            || (e.getGOODS() != null && !e.getGOODS().equals(""))) {
                        e.setIsSave(true);
                        StockInputObject ob = new StockInputObject();
                        ob.setCUSTCD(MainActivity.SHOP_ID);
                        ob.setPRDCD(e.getPRDCD());
                        ob.setCASEQTY("0");
                        String S1 = e.getColumnsThree();
                        if (S1.equals(""))
                            S1 = "0";
                        ob.setEAQTY(S1);
                        String S2 = e.getGOODS();
                        if (S2.equals(""))
                            S2 = "0";
                        ob.setRETURNQTY(S2);
                        ob.setSTOCKCATEGORY("02");
                        inputPie.add(ob);
                    }
                }
            }
        }
        prefManager.setRouteStockGum(new Gson().toJson(listGum));

        listSnack.clear();
        String snack = prefManager.getRouteStockSnack();

//            Const.longInfo(TAG, pie);
        Type listTypeS = new TypeToken<List<Snack>>() {
        }.getType();
        listSnack = new Gson().fromJson(snack, listTypeS);
        for (Snack p : listSnack) {
            if (p.getArrSnacks() != null && p.getArrSnacks().size() > 0) {
                for (int i = 0; i < p.getArrSnacks().size(); i++) {
                    Snack e = p.getArrSnacks().get(i);
                    if (e.getColumnsThree() != null && !e.getColumnsThree().equals("")
                            || e.getColumnsFour() != null && !e.getColumnsFour().equals("")) {
                        if (e.getColumnsThree() == null || e.getColumnsThree().equals(""))
                            e.setColumnsThree("0");
                        if (e.getColumnsFour() == null || e.getColumnsFour().equals(""))
                            e.setColumnsFour("0");
                        e.setIsSave(true);
                        StockInputObject ob = new StockInputObject();
                        ob.setCUSTCD(MainActivity.SHOP_ID);
                        ob.setPRDCD(e.getPRDCD());
                        ob.setCASEQTY("0");
                        String S1 = e.getColumnsThree();
                        if (S1.equals(""))
                            S1 = "0";
                        ob.setEAQTY(S1);
                        String S2 = e.getColumnsFour();
                        if (S2.equals(""))
                            S2 = "0";
                        ob.setRETURNQTY(S2);
                        ob.setSTOCKCATEGORY("03");
                        inputPie.add(ob);
                    }
                }
            }
        }
        prefManager.setRouteStockSnack(new Gson().toJson(listSnack));

        if (inputPie.size() > 0) {
            JSONObject jObject = new JSONObject();
            try {
                JSONArray jArray = new JSONArray();
                for (StockInputObject s : inputPie) {
                    JSONObject studentJSON = new JSONObject();
                    studentJSON.put("CDATE", RouteFragment.DATE);
                    studentJSON.put("CUSTCD", s.getCUSTCD());
                    studentJSON.put("PRDCD", s.getPRDCD());
                    studentJSON.put("CASEQTY", s.getCASEQTY());
                    studentJSON.put("EAQTY", s.getEAQTY());
                    studentJSON.put("RETURNQTY", s.getRETURNQTY());
                    studentJSON.put("STOCKCATEGORY", s.getSTOCKCATEGORY());
                    jArray.put(studentJSON);
                }
                jObject.put("LIST", jArray);
                data118 = jObject.toString();

            } catch (Exception jse) {
                jse.printStackTrace();
            }
        }
    }

    String data118 = "";
    String data119 = "";

    void callAPI() {

        if (data118.length() > 0) {
            new HttpRequest(mActivity).new API_118(dataLogin, data118, new IF_118() {
                @Override
                public void onSuccess() {
                    ob.setIS_118("1");
//                    Toast.makeText(mActivity, "118 OK", Toast.LENGTH_SHORT).show();
                    callAPIVisit();
                }

                @Override
                public void onFail() {
//                    Toast.makeText(mActivity, "118 FAIL " + getResources().getString(R.string.problemsenddata), Toast.LENGTH_LONG).show();
//                    finishOrder();
                    dialogProblem(1);
                }
            }).execute();

        } else {
            ob.setIS_118("1");
            callAPIVisit();
        }
    }

    TextView tvConfirm, tvTOTAL, tvTotalBuy;
    DataLogin dataLogin;

    protected OrderActivity mActivity;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = (OrderActivity) activity;
        Log.d(TAG, "onAttach");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mActivity = null;
        Log.d(TAG, "onDetach");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(TAG, "onActivityCreated");
    }


    List<ProductInfor> getListProduct = new ArrayList<>();

    ConvertUnit convertData(String PRDCD) {
        ConvertUnit convertUnit = null;
        for (ProductInfor obj : getListProduct) {
            if (PRDCD.equals(obj.getPRDCD().trim())) {
                convertUnit = new ConvertUnit(obj.getCSEEAQTY(), obj.getBXCSEQTY(), obj.getBXEAQTY());
                break;
            }
        }
        if (convertUnit == null) convertUnit = new ConvertUnit();
//        Log.d(TAG,"getCSEEAQTY:"+convertUnit.getCSES()+" getBXCSEQTY:"+convertUnit.getBXCS()+" getBXEAQTY:"+convertUnit.getBXES());
        return convertUnit;
    }


    String getPrice(String TPRDCD, String UNIT) {
        //  Unit. 1=Box, 2=CASE, 3=EA
        String s = "0";
        for (ProductInfor obj : getListProduct) {
            if (obj.getTPRDCD().trim().equals(TPRDCD)) {
                if (UNIT.equals("1")) s = obj.getSTOREPRICE();
                else if (UNIT.equals("2")) s = obj.getSTORECASEPRICE();
                else s = obj.getSTOREEAPRICE();
                break;
            }
        }
        return s;
    }

    void getListProductDB() {
        try {
            DataBaseHelper_2 db = new DataBaseHelper_2(getActivity());
            db.openDB();
            getListProduct = db.getListProduct();
        } catch (Exception e) {
            e.printStackTrace();
//            MyApplication.getInstance().showToast("can not get DB");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView");
        View v = inflater.inflate(R.layout.route_confirm_fragment, container, false);
        dataLogin = new Gson().fromJson(new PrefManager(mActivity).getInfoLogin(), DataLogin.class);
        confirmFragment = this;
        DatabaseHandler db = new DatabaseHandler(mActivity);

        List<API_123> listAPI_123 = db.getListAPI_123();
        if (listAPI_123 != null && listAPI_123.size() > 0) {
            API_123 api_123 = listAPI_123.get(listAPI_123.size() - 1);
            GetNewShopPromotionList getNewShopPromotionList = new Gson().fromJson(api_123.getDATA(), GetNewShopPromotionList.class);
            LIST_PMT = getNewShopPromotionList.getLIST();
//            Log.d(TAG, "LIST_PMT:" + new Gson().toJson(LIST_PMT));
        }

        api_121List = db.getListAPI_121();
        String monthPromotion = RouteFragment.DATE.substring(0, 6);
        if (api_121List != null && api_121List.size() != 0) {
            for (int i = 0; i < api_121List.size(); i++) {
                API_121 api_121 = api_121List.get(i);
                if (monthPromotion.equals(api_121.getDATE().substring(0, 6))) {
                    pmList = new Gson().fromJson(api_121.getDATA(), PmList.class);
                    break;
                }
            }

//            API_121 api_121 = api_121List.get(api_121List.size() - 1);
//            pmList = new Gson().fromJson(api_121.getDATA(), PmList.class);
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
        tvTOTAL = (TextView) v.findViewById(R.id.tvTOTAL);
        tvTotalBuy = (TextView) v.findViewById(R.id.tvTotalBuy);
        getListProductDB();
        init(v);
        OrderActivity.checkClick = true;
        return v;
    }


    @Override
    public void onResume() {
        super.onResume();
        OrderActivity.flag = 3;
        OrderActivity.instance.showBtnBack();
        OrderActivity.instance.hideBtnNext();
        OrderActivity.instance.setTvStock(getResources().getString(R.string.confirm));
    }

    String data115 = "";

    void initDB_115() {
        if (RouteFragment.KEY_ROUTE.length() > 0 && MainActivity.SHOP_ID.length() > 0 && MainActivity.SEQ.length() > 0) {
            int type = 2;
            data115 = "";
            Calendar cal = Calendar.getInstance();
            int year = cal.get(Calendar.YEAR);
            int month = cal.get(Calendar.MONTH) + 1;
            int date = cal.get(Calendar.DATE);
            int hour = cal.get(Calendar.HOUR_OF_DAY);
            int minute = cal.get(Calendar.MINUTE);
            int second = cal.get(Calendar.SECOND);
            String TIME = Const.setFullDate(hour) + Const.setFullDate(minute) + Const.setFullDate(second);
//        String _DATE = year + Const.setFullDate(month) + Const.setFullDate(date);

            String contents = "";
            Map<String, String> map = new HashMap<>();
            map.put("MODE", "" + type);
//        map.put("LON", "38418724");
//        map.put("LAT", "3887956");
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
            data115 = new Gson().toJson(map);
        }

    }

//    int check = 0;

    void callAPIVisit() {
        if (Const.inforSE.length() > 0) {
            Log.d(TAG, "dont send visit shop for SE");
            ob.setIS_115("1");
            getDataDisplayInput();
        } else {
            Log.d(TAG, "data115:" + data115);
            if (data115.length() > 0) {
                new HttpRequest(mActivity).new API_115(dataLogin, data115, new IF_115() {
                    @Override
                    public void onSuccess() {
                        ob.setIS_115("1");
                        getDataDisplayInput();
                        Log.d(TAG, "API 115 ok");
                    }

                    @Override
                    public void onFail() {
                        Log.d(TAG, "API 115 FAIL");
                        dialogProblem(1);
                    }
                }).execute();
            } else {
                ob.setIS_115("1");
                getDataDisplayInput();
                Log.d(TAG, "API 115 ok");
            }
        }


    }

    private List<com.orion.salesman._route._object.RouteDisplayPie> listDisplayPie = new ArrayList<>();
    private List<com.orion.salesman._route._object.RouteDisplayPie> listDisplaySnack = new ArrayList<>();
    private List<DisplayInput> displayInputArrayListPie = new ArrayList<>();
    private List<DisplayInput> displayInputArrayListSnack = new ArrayList<>();

    void initDB_119() {
        String MNGEMPID = dataLogin.getMNGEMPID();
        PrefManager prefManager = new PrefManager(mActivity);
        String s = prefManager.getRouteDisplayPie();
        String snack = prefManager.getRouteDisplaySnack();
        displayInputArrayListPie.clear();
        displayInputArrayListSnack.clear();
        Type listType = new TypeToken<List<com.orion.salesman._route._object.RouteDisplayPie>>() {
        }.getType();
        listDisplayPie = new Gson().fromJson(s, listType);
        listDisplaySnack = new Gson().fromJson(snack, listType);

        if (listDisplayPie != null && listDisplayPie.size() > 0) {
            for (int i = 0; i < listDisplayPie.size(); i++) {
                if (listDisplayPie.get(i).getColumnsThree() != null
                        && !listDisplayPie.get(i).getColumnsThree().equals("")) {
                    listDisplayPie.get(i).setIsSave(true);
                    DisplayInput ob = new DisplayInput();
                    ob.setCDATE(RouteFragment.DATE);
                    ob.setCUSTCD(MainActivity.SHOP_ID);
                    ob.setTOOLCD(listDisplayPie.get(i).getTOOLCD());
                    ob.setTOOLQTY(listDisplayPie.get(i).getColumnsThree());
                    ob.setMNGEMPID(MNGEMPID);
                    displayInputArrayListPie.add(ob);
                }
            }
            prefManager.setRouteDisplayPie(new Gson().toJson(listDisplayPie));
        }

        if (listDisplaySnack != null && listDisplaySnack.size() > 0) {
            for (int i = 0; i < listDisplaySnack.size(); i++) {
                if (listDisplaySnack.get(i).getColumnsThree() != null
                        && !listDisplaySnack.get(i).getColumnsThree().equals("")) {
                    listDisplaySnack.get(i).setIsSave(true);
                    DisplayInput ob = new DisplayInput();
                    ob.setCDATE(RouteFragment.DATE);
                    ob.setCUSTCD(MainActivity.SHOP_ID);
                    ob.setTOOLCD(listDisplaySnack.get(i).getTOOLCD());
                    ob.setTOOLQTY(listDisplaySnack.get(i).getColumnsThree());
                    ob.setMNGEMPID(MNGEMPID);
                    displayInputArrayListPie.add(ob);
                }
            }
            prefManager.setRouteDisplaySnack(new Gson().toJson(listDisplaySnack));
        }


        if (displayInputArrayListPie != null && displayInputArrayListPie.size() > 0) {
            JSONObject jObject = new JSONObject();
            try {
                JSONArray jArray = new JSONArray();
                for (DisplayInput d : displayInputArrayListPie) {
                    JSONObject studentJSON = new JSONObject();
                    studentJSON.put("CDATE", RouteFragment.DATE);
                    studentJSON.put("CUSTCD", d.getCUSTCD());
                    studentJSON.put("TOOLCD", d.getTOOLCD());
                    studentJSON.put("TOOLQTY", d.getTOOLQTY());
                    studentJSON.put("MNGEMPID", dataLogin.getMNGEMPID());
                    jArray.put(studentJSON);
                }
                jObject.put("LIST", jArray);
                data119 = jObject.toString();

            } catch (Exception jse) {
                jse.printStackTrace();
            }
        }
    }

    void call_API_126() {
        Map<String, Object> map = new HashMap<>();
        map.put("CDATE", RouteFragment.DATE);
        map.put("CUSTCD", MainActivity.SHOP_ID);
        String paramSend = new Gson().toJson(map);
        new HttpRequest(mActivity).new API_126(dataLogin, paramSend, new IF_124() {
            @Override
            public void onSuccess() {
                call_API_124();
            }

            @Override
            public void onFail() {
                dialogProblem(1);
            }
        }).execute();
    }

    boolean flag_125 = false;

    void call_API_125() {
        progressbar.setVisibility(View.VISIBLE);
        if (listHistory.size() > 0 && count_125 < listHistory.size()) {

            new HttpRequest(mActivity).new API_125(dataLogin, listHistory.get(count_125), new IF_124() {
                @Override
                public void onSuccess() {
//                    Log.d(TAG, "125 ok");
                    count_125++;
                    if (count_125 >= Size_125) {
                        flag_125 = true;
                        deletePICTURE_RESIGN();
                        finishOrder();
                    } else {
                        call_API_125();
                    }
                }

                @Override
                public void onFail() {
                    Log.d(TAG, "125 fail");
                    dialogProblem(1);

                }
            }).execute();
//                try {
//                    Thread.sleep(1000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }

        } else {

            deletePICTURE_RESIGN();
            finishOrder();
        }
    }

    void getDataDisplayInput() {
        if (data119.length() > 0) {
            new HttpRequest(mActivity).new API_119(dataLogin, data119, new IF_119() {
                @Override
                public void onSuccess() {
                    ob.setIS_119("1");
                    call_API_125();
                }

                @Override
                public void onFail() {
//                    Toast.makeText(mActivity, "API 119 FAIL", Toast.LENGTH_SHORT).show();
                    dialogProblem(1);
                }
            }).execute();

        } else {
            ob.setIS_119("1");
            call_API_125();
        }
    }

    void deletePICTURE_RESIGN() {
        String URL = Const.PATH_IMG + PIC_NAME + ".jpg";
        File file = new File(URL);
        if (file.exists()) {
            file.delete();
        }
    }

    void finishOrder() {
        progressbar.setVisibility(View.GONE);
        if (Const.inforSE.length() > 0) {
            Log.d(TAG, "SE dont save local db");
        } else {
            savedataOrder();
        }

        new PrefManager(mActivity).setUpdateVisitShop(true);
        mActivity.finish();
//        Log.d(TAG, "VISIT FINISH");
    }

    int countOrder = 0;
    int count_125 = 0;
    int SIZE = 0;
    int Size_125 = 0;
    String SIGNURL = Const.NO_PIC;

    public void updateListPromotion(String TPRDCD, String PRDCD, boolean isPromotion, int size) {
        tvTotalBuy.setText(getResources().getString(R.string.total) + " : " + size);
        Log.d(TAG, "update");
        if (MainActivity.TEAM.equals(Const.PIE_TEAM)) {
            Log.d(TAG, "PIE");
            for (int i = 0; i < ListPie.size(); i++) {
                List<Pie> pieList = ListPie.get(i).getArrPies();
                for (int k = 0; k < pieList.size(); k++) {
                    if (pieList.get(k).getTPRDCD().equals(TPRDCD) && pieList.get(k).getPRDCD().equals(PRDCD)) {
                        if (pieList.get(k).isSave())
                            pieList.get(k).setColumnsFive("0");
                        else pieList.get(k).setColumnsFive("");
                        if (!MainActivity.SALESMANTYPE.equals(Const.DS))
                            pieList.get(k).setIsCheck(false);
                        new PrefManager(mActivity).setRouteOrderPie(new Gson().toJson(ListPie));
                        break;
                    }
                }
            }
            // update gum
            Log.d(TAG, "GUM");
            for (int i = 0; i < ListGum.size(); i++) {
                List<Pie> pieList = ListGum.get(i).getArrPies();
                for (int k = 0; k < pieList.size(); k++) {
                    if (pieList.get(k).getTPRDCD().equals(TPRDCD) && pieList.get(k).getPRDCD().equals(PRDCD)) {
                        if (pieList.get(k).isSave())
                            pieList.get(k).setColumnsFive("0");
                        else pieList.get(k).setColumnsFive("");
                        if (!MainActivity.SALESMANTYPE.equals(Const.DS))
                            pieList.get(k).setIsCheck(false);
                        new PrefManager(mActivity).setRouteOrderGum(new Gson().toJson(ListGum));
                        break;
                    }
                }
            }

        } else if (MainActivity.TEAM.equals(Const.GUM_TEAM)) {
            Log.d(TAG, "GUM");
            for (int i = 0; i < ListGum.size(); i++) {
                List<Pie> pieList = ListGum.get(i).getArrPies();
                for (int k = 0; k < pieList.size(); k++) {
                    if (pieList.get(k).getTPRDCD().equals(TPRDCD) && pieList.get(k).getPRDCD().equals(PRDCD)) {
                        if (pieList.get(k).isSave())
                            pieList.get(k).setColumnsFive("0");
                        else pieList.get(k).setColumnsFive("");
                        if (!MainActivity.SALESMANTYPE.equals(Const.DS))
                            pieList.get(k).setIsCheck(false);
                        new PrefManager(mActivity).setRouteOrderGum(new Gson().toJson(ListGum));
                        break;
                    }
                }
            }

        } else if (MainActivity.TEAM.equals(Const.SNACK_TEAM)) {
            Log.d(TAG, "SNACK");
            for (int i = 0; i < ListSnack.size(); i++) {
                List<Snack> pieList = ListSnack.get(i).getArrSnacks();
                for (int k = 0; k < pieList.size(); k++) {
                    if (pieList.get(k).getTPRDCD().equals(TPRDCD) && pieList.get(k).getPRDCD().equals(PRDCD)) {
                        if (pieList.get(k).isSave())
                            pieList.get(k).setColumnsSix("0");
                        else pieList.get(k).setColumnsSix("");
                        if (!MainActivity.SALESMANTYPE.equals(Const.DS))
                            pieList.get(k).setIsCheck(false);
                        new PrefManager(mActivity).setRouteOrderSnack(new Gson().toJson(ListSnack));
                        break;
                    }
                }
            }

        } else if (MainActivity.TEAM.equals(Const.MIX_TEAM)) {
            Log.d(TAG, "MIX");
            for (int i = 0; i < ListPie.size(); i++) {
                List<Pie> pieList = ListPie.get(i).getArrPies();
                for (int k = 0; k < pieList.size(); k++) {
                    if (pieList.get(k).getTPRDCD().equals(TPRDCD) && pieList.get(k).getPRDCD().equals(PRDCD)) {
                        if (pieList.get(k).isSave())
                            pieList.get(k).setColumnsFive("0");
                        else pieList.get(k).setColumnsFive("");
                        if (!MainActivity.SALESMANTYPE.equals(Const.DS))
                            pieList.get(k).setIsCheck(false);
                        new PrefManager(mActivity).setRouteOrderPie(new Gson().toJson(ListPie));
                        break;
                    }
                }
            }
            for (int i = 0; i < ListGum.size(); i++) {
                List<Pie> pieList = ListGum.get(i).getArrPies();
                for (int k = 0; k < pieList.size(); k++) {
                    if (pieList.get(k).getTPRDCD().equals(TPRDCD) && pieList.get(k).getPRDCD().equals(PRDCD)) {
                        if (pieList.get(k).isSave())
                            pieList.get(k).setColumnsFive("0");
                        else pieList.get(k).setColumnsFive("");
                        new PrefManager(mActivity).setRouteOrderGum(new Gson().toJson(ListGum));
                        break;
                    }
                }
            }
            for (int i = 0; i < ListSnack.size(); i++) {
                List<Snack> pieList = ListSnack.get(i).getArrSnacks();
                for (int k = 0; k < pieList.size(); k++) {
                    if (pieList.get(k).getTPRDCD().equals(TPRDCD) && pieList.get(k).getPRDCD().equals(PRDCD)) {
                        if (pieList.get(k).isSave())
                            pieList.get(k).setColumnsSix("0");
                        else pieList.get(k).setColumnsSix("");
                        new PrefManager(mActivity).setRouteOrderSnack(new Gson().toJson(ListSnack));
                        break;
                    }
                }
            }

        }

        if (isPromotion) {
            updateListPromotion();
        }

    }

    List<String> List_124 = new ArrayList<>();
    List<String> List_124_Old = new ArrayList<>();
    List<String> listHistory = new ArrayList<>();

    void initDBInputToServer() {
        initDB = false;
        initDB_124();
        initDB_118();
        initDB_119();
        initDB_115();
    }

    List<PromotionProduct> lstPromotionAdapter = new ArrayList<>();

    void initDB_124() {
        List_124.clear();
        List_124_Old.clear();
        listHistory.clear();


        if (MainActivity.STATUS_ORDER) {
            Log.d(TAG, "STATUS_ORDER");
            DatabaseHandler db = new DatabaseHandler(mActivity);
            List<TABLE_INPUT> LIST_INPUT = db.GET_LIST_INPUT();
            if (LIST_INPUT != null && LIST_INPUT.size() > 0) {
                if (CHECK_LIST_INPUT(LIST_INPUT, RouteFragment.DATE, MainActivity.SHOP_CODE)) {
                    Log.d(TAG, "STATUS_ORDER  MATCH");
                    for (int i = 0; i < LIST_INPUT.size(); i++) {
                        if (LIST_INPUT.get(i).getTABLE_INPUT_DATE().equals(RouteFragment.DATE) && LIST_INPUT.get(i).getTABLE_INPUT_SHOP_ID().equals(MainActivity.SHOP_CODE)) {
                            String oldData = "";
                            oldData = LIST_INPUT.get(i).getTABLE_INPUT_JSON_ORDER();
                            if (oldData.length() > 0) {
                                Type type = new TypeToken<List<String>>() {
                                }.getType();
                                List_124_Old = new Gson().fromJson(oldData, type);
                            }
                            break;
                        }
                    }
                } else {
                    Log.d(TAG, "STATUS_ORDER NOT MATCH");
                }
            }

//            if (List_124_Old != null && List_124_Old.size() > 0 && List_124 != null && List_124.size() > 0) {
            if (List_124_Old != null && List_124_Old.size() > 0 && List_124 != null) {
                for (String old : List_124_Old) {
                    Obj_124 objOld = new Gson().fromJson(old, Obj_124.class);
//                    String PRDCD = objOld.getPRDCD();
//                    if (!checkListOldOrder(List_124, PRDCD)) {
                    objOld.setBOXQTY("0");
                    objOld.setCASEQTY("0");
                    objOld.setEAQTY("0");
                    objOld.setPROBOXQTY("0");
                    objOld.setPROCASEQTY("0");
                    objOld.setPROEAQTY("0");

                    List_124.add(new Gson().toJson(objOld));
//                    }
                }
            }

            // get product from server
            DatabaseHandler db2 = new DatabaseHandler(getActivity());
            List<DataBase129> lst129 = db2.getList129();
            List<com.orion.salesman._object.ORDERLIST> ORDERLIST = new ArrayList<>();
            if (lst129 != null && lst129.size() > 0) {
                for (DataBase129 ob : lst129) {
                    if (ob.getCDATE().equals(RouteFragment.DATE)) {
                        String data = ob.getDATA();
                        ObjectA0129 objectA0129 = new Gson().fromJson(data, ObjectA0129.class);
                        int RESULT = objectA0129.getRESULT();
                        if (RESULT == 0) {
                            ORDERLIST = objectA0129.getORDERLIST();
                        }
                        break;
                    }
                }
                if (ORDERLIST.size() > 0) {
                    List<com.orion.salesman._object.ORDERLIST> lstTemp = new ArrayList<>();
                    for (com.orion.salesman._object.ORDERLIST obj : ORDERLIST) {
                        if (obj.getV1().trim().equals(MainActivity.SHOP_CODE)) {
                            lstTemp.add(obj);
                        }
                    }
                    if (lstTemp.size() > 0) {
                        String sTemp = new Gson().toJson(List_124);
                        for (com.orion.salesman._object.ORDERLIST obj : lstTemp) {
                            String PRDCD = obj.getV2();
                            if (!sTemp.contains(PRDCD)) {
                                String MODE = "1";
                                if (obj.getV6().trim().equals("DS"))
                                    MODE = "2";
                                Obj_124 obj124 = new Obj_124(MODE, RouteFragment.DATE, MainActivity.DEALERCD, MainActivity.SHOP_CODE,
                                        "" + PRDCD, "0", "0", "0", "0", "0", "0", "http://snsv.orionvina.vn/SNSVUploads/PDA_SHOP_IMAGE/" + MainActivity.SHOP_CODE + "_20170112090338.jpg");
                                List_124.add(new Gson().toJson(obj124));
                            }
                        }
                    }
                }
            }
        }


        List<RouteConfirm> confirmList = new ArrayList<>();
        confirmList = mAdapter.getList();


        if (productList != null && productList.size() > 0) {
            lstPromotionAdapter = productAdapter.getList();
        }


//        Calendar calendar = Calendar.getInstance();
//        int y = calendar.get(Calendar.YEAR);
//        int m = calendar.get(Calendar.MONTH) + 1;
//        int d = calendar.get(Calendar.DAY_OF_MONTH);
//        String CDATE = y + Const.setFullDate(m) + Const.setFullDate(d);


        if (LIST_PM != null) {
            if (LIST_PM.getLstBuy() != null && LIST_PM.getLstBuy().size() > 0) {
                List<OrderGift> lstBuy = LIST_PM.getLstBuy();
                for (int i = 0; i < lstBuy.size(); i++) {
                    int SEQ = 0;
                    OrderGift orderGift = lstBuy.get(i);
                    String PMID = orderGift.getPMID();

                    int CONDITION = orderGift.getCONDITION();
                    List<OrderBuying> BuyList = orderGift.getBuyList();
                    List<OrderGifItem> GiftList = orderGift.getGiftList();
                    if (BuyList != null && BuyList.size() > 0 && GiftList != null && GiftList.size() > 0) {
                        if (CONDITION == 0) {
                            if (BuyList.size() <= GiftList.size()) {
                                for (int j = 0; j < GiftList.size(); j++) {
                                    String BUY_PRDCD = "";
                                    int BOXQTY = 0;
                                    int CASEQTY = 0;
                                    int EAQTY = 0;
                                    if (BuyList.size() > j) {
                                        OrderBuying orderBuying = BuyList.get(j);
                                        BUY_PRDCD = orderBuying.getPRDCD();
                                        int UNIT = orderBuying.getUNIT();
                                        if (UNIT == 1) {
                                            BOXQTY = orderBuying.getQTY();
                                        } else if (UNIT == 2) {
                                            CASEQTY = orderBuying.getQTY();
                                        } else {
                                            EAQTY = orderBuying.getQTY();
                                        }
                                    }
                                    OrderGifItem orderGifItem = GiftList.get(j);
                                    String PRO_PRDCD = orderGifItem.getPRDCD();
                                    int PROBOXQTY = 0;
                                    int PROCASEQTY = 0;
                                    int PROEAQTY = 0;
                                    int unit = orderGifItem.getUNIT();
                                    if (unit == 1) {
                                        PROBOXQTY = orderGifItem.getQTY();
                                    } else if (unit == 2) {
                                        PROCASEQTY = orderGifItem.getQTY();
                                    } else {
                                        PROEAQTY = orderGifItem.getQTY();
                                    }
                                    Obj_125 ob = new Obj_125("" + SEQ, PMID, RouteFragment.DATE, MainActivity.DEALERCD, MainActivity.SHOP_ID,
                                            "" + BUY_PRDCD, "" + BOXQTY, "" + CASEQTY, "" + EAQTY, "" + PRO_PRDCD, "" + PROBOXQTY, "" + PROCASEQTY, "" + PROEAQTY);
                                    listHistory.add(new Gson().toJson(ob));
                                    SEQ++;
                                }
                            } else {
                                for (int j = 0; j < BuyList.size(); j++) {
                                    String BUY_PRDCD = "";
                                    int BOXQTY = 0;
                                    int CASEQTY = 0;
                                    int EAQTY = 0;
                                    OrderBuying orderBuying = BuyList.get(j);
                                    BUY_PRDCD = orderBuying.getPRDCD();
                                    int UNIT = orderBuying.getUNIT();
                                    if (UNIT == 1) {
                                        BOXQTY = orderBuying.getQTY();
                                    } else if (UNIT == 2) {
                                        CASEQTY = orderBuying.getQTY();
                                    } else {
                                        EAQTY = orderBuying.getQTY();
                                    }
                                    String PRO_PRDCD = "";
                                    int PROBOXQTY = 0;
                                    int PROCASEQTY = 0;
                                    int PROEAQTY = 0;
                                    if (GiftList.size() > j) {
                                        OrderGifItem orderGifItem = GiftList.get(j);
                                        PRO_PRDCD = orderGifItem.getPRDCD();
                                        int unit = orderGifItem.getUNIT();
                                        if (unit == 1) {
                                            PROBOXQTY = orderGifItem.getQTY();
                                        } else if (unit == 2) {
                                            PROCASEQTY = orderGifItem.getQTY();
                                        } else {
                                            PROEAQTY = orderGifItem.getQTY();
                                        }
                                    }
                                    Obj_125 ob = new Obj_125("" + SEQ, PMID, RouteFragment.DATE, MainActivity.DEALERCD, MainActivity.SHOP_ID,
                                            "" + BUY_PRDCD, "" + BOXQTY, "" + CASEQTY, "" + EAQTY, "" + PRO_PRDCD, "" + PROBOXQTY, "" + PROCASEQTY, "" + PROEAQTY);
                                    listHistory.add(new Gson().toJson(ob));
                                    SEQ++;
                                }
                            }
                        } else {
                            for (int j = 0; j < BuyList.size(); j++) {
                                OrderBuying orderBuying = BuyList.get(j);
                                String BUY_PRDCD = orderBuying.getPRDCD();
                                int BOXQTY = 0;
                                int CASEQTY = 0;
                                int EAQTY = 0;
                                int UNIT = orderBuying.getUNIT();
                                if (UNIT == 1) {
                                    BOXQTY = orderBuying.getQTY();
                                } else if (UNIT == 2) {
                                    CASEQTY = orderBuying.getQTY();
                                } else {
                                    EAQTY = orderBuying.getQTY();
                                }
                                String PRO_PRDCD = "";
                                int PROBOXQTY = 0;
                                int PROCASEQTY = 0;
                                int PROEAQTY = 0;
                                if (j == 0) {
                                    int pos = 0;
                                    if (lstPromotionAdapter != null && lstPromotionAdapter.size() > 0) {
                                        for (int k = 0; k < lstPromotionAdapter.size(); k++) {
                                            String PM = lstPromotionAdapter.get(k).getPMID();
                                            if (PMID.trim().equals(PM.trim())) {
                                                pos = lstPromotionAdapter.get(k).getPositionChoose();
                                                break;
                                            }
                                        }
                                    }
                                    OrderGifItem orderGifItem = GiftList.get(pos);
                                    PRO_PRDCD = orderGifItem.getPRDCD();
                                    int unit = orderGifItem.getUNIT();
                                    if (unit == 1) {
                                        PROBOXQTY = orderGifItem.getQTY();
                                    } else if (unit == 2) {
                                        PROCASEQTY = orderGifItem.getQTY();
                                    } else {
                                        PROEAQTY = orderGifItem.getQTY();
                                    }
                                }
                                Obj_125 ob = new Obj_125("" + SEQ, PMID, RouteFragment.DATE, MainActivity.DEALERCD, MainActivity.SHOP_ID,
                                        "" + BUY_PRDCD, "" + BOXQTY, "" + CASEQTY, "" + EAQTY, "" + PRO_PRDCD, "" + PROBOXQTY, "" + PROCASEQTY, "" + PROEAQTY);
                                listHistory.add(new Gson().toJson(ob));
                                SEQ++;
                            }
                        }
                    }
                }
            }

//            if (LIST_PM.getLstEx() != null && LIST_PM.getLstEx().size() > 0) {
//                List<OrderBuying> lstEx = LIST_PM.getLstEx();
//                for (int i = 0; i < lstEx.size(); i++) {
//                    OrderBuying orderBuying = lstEx.get(i);
//                    int qty = orderBuying.getQTY();
//                    if (qty > 0) {
//                        String BUY_PRDCD = orderBuying.getPRDCD();
//                        int BOXQTY = 0;
//                        int CASEQTY = 0;
//                        int EAQTY = 0;
//                        int unit = orderBuying.getUNIT();
//                        if (unit == 1) {
//                            BOXQTY = orderBuying.getQTY();
//                        } else if (unit == 2) {
//                            CASEQTY = orderBuying.getQTY();
//                        } else {
//                            EAQTY = orderBuying.getQTY();
//                        }
//                        Obj_125 ob = new Obj_125(0, "", RouteFragment.DATE, MainActivity.DEALERCD, MainActivity.SHOP_ID,
//                                BUY_PRDCD, BOXQTY, CASEQTY, EAQTY, "", 0, 0, 0);
//                        listHistory.add(new Gson().toJson(ob));
//
//                    }
//                }
//            }
        }
        List<OrderTable> listBuy = new ArrayList<>();
        List<OrderTable> listGift = new ArrayList<>();
        if (confirmList != null && confirmList.size() > 0) {
            for (int i = 0; i < confirmList.size(); i++) {
                RouteConfirm confirm = confirmList.get(i);
                int unit = confirm.getKIND();
                if (unit == 1)
                    unit = 3;
                else if (unit == 3)
                    unit = 1;
                int qty = Integer.parseInt(confirm.getV2());
                OrderTable orderTable = new OrderTable(confirm.getPRDCD(), unit, qty, confirm.isCheck());
                listBuy.add(orderTable);
            }
        }
        if (lstPromotionAdapter != null && lstPromotionAdapter.size() > 0) {
            for (int i = 0; i < lstPromotionAdapter.size(); i++) {
                PromotionProduct product = lstPromotionAdapter.get(i);
                int pos = product.getPositionChoose();
                String PRDCD = product.getItemList().get(pos).getPRDCD();
                int unit = product.getItemList().get(pos).getUNIT();
                int qty = product.getItemList().get(pos).getQTY();
                boolean DS = product.getItemList().get(pos).isDS();
                OrderTable orderTable = new OrderTable(PRDCD, unit, qty, DS);
                listGift.add(orderTable);
            }
        }


        if (listBuy.size() > 0) {
            for (OrderTable orderTable : listBuy) {
                String PRDCD = orderTable.getPRDCD().trim();
                boolean flag = orderTable.isCheck();
                int BOXQTY = 0;
                int EAQTY = 0;
                int CASEQTY = 0;
                int PROBOXQTY = 0;
                int PROCASEQTY = 0;
                int PROEAQTY = 0;
                int unit = orderTable.getUNIT();
                if (unit == 1) {
                    BOXQTY = orderTable.getQTY();
                } else if (unit == 2) {
                    CASEQTY = orderTable.getQTY();
                } else {
                    EAQTY = orderTable.getQTY();
                }
                for (OrderTable km : listGift) {
                    if (PRDCD.equals(km.getPRDCD().trim())) {
                        int _unit = km.getUNIT();
                        if (_unit == 1) {
                            PROBOXQTY += km.getQTY();
                        } else if (_unit == 2) {
                            PROCASEQTY += km.getQTY();
                        } else {
                            PROEAQTY += km.getQTY();
                        }
                        if (km.isCheck()) {
                            if (!flag)
                                flag = true;
                        }
                    }

                }
                int MODE = 1;
                if (flag)
                    MODE = 2;
                else
                    MODE = 1;
                Obj_124 obj124 = new Obj_124("" + MODE, RouteFragment.DATE, MainActivity.DEALERCD, MainActivity.SHOP_ID,
                        "" + PRDCD, "" + BOXQTY, "" + CASEQTY, "" + EAQTY, "" + PROBOXQTY, "" + PROCASEQTY, "" + PROEAQTY, SIGNURL);
                List_124.add(new Gson().toJson(obj124));
            }
        }
        List<String> listTemp = new ArrayList<>();
        if (listGift != null && listGift.size() > 0) {
            for (OrderTable KM : listGift) {
                boolean isDS = false;
                String PRDCD = KM.getPRDCD().trim();
                int BOXQTY = 0;
                int CASEQTY = 0;
                int EAQTY = 0;
                int PROBOXQTY = 0;
                int PROCASEQTY = 0;
                int PROEAQTY = 0;
                boolean flag = false;
                for (OrderTable buy : listBuy) {
                    if (PRDCD.equals(buy.getPRDCD().trim())) {
                        flag = true;
                        break;
                    }
                }
                if (!flag) {
                    for (String s : listTemp) {
                        if (PRDCD.equals(s)) {
                            flag = true;
                            break;
                        }
                    }
                    if (!flag) {
                        for (OrderTable orderTable : listGift) {
                            if (PRDCD.equals(orderTable.getPRDCD())) {
                                int unit = orderTable.getUNIT();
                                if (unit == 1) {
                                    PROBOXQTY += orderTable.getQTY();
                                } else if (unit == 2) {
                                    PROCASEQTY += orderTable.getQTY();
                                } else {
                                    PROEAQTY += orderTable.getQTY();
                                }
                                isDS = orderTable.isCheck();
                            }
                        }
                        int MODE = 1;
                        if (isDS)
                            MODE = 2;
                        else
                            MODE = 1;
                        Obj_124 obj124 = new Obj_124("" + MODE, RouteFragment.DATE, MainActivity.DEALERCD, MainActivity.SHOP_ID,
                                "" + PRDCD, "" + BOXQTY, "" + CASEQTY, "" + EAQTY, "" + PROBOXQTY, "" + PROCASEQTY, "" + PROEAQTY, SIGNURL);
                        List_124.add(new Gson().toJson(obj124));
                        listTemp.add(PRDCD);
                    }
                }
            }
        }


        for (String s : listHistory) {
            Log.d(TAG, "listHistory:" + s);
        }
        for (String s : List_124) {
            Log.d(TAG, "List_124:" + s);
        }
        SIZE = List_124.size();
        Size_125 = listHistory.size();
        saveListOrder();
    }

    boolean checkListOldOrder(List<String> list, String PRDCD) {
        for (String _new : list) {
            Obj_124 objNew = new Gson().fromJson(_new, Obj_124.class);
            String ID = objNew.getPRDCD().trim();
            if (ID.equals(PRDCD.trim()))
                return true;
        }
        return false;
    }

    void call_API_124() {
        Log.d(TAG, "call_API_124 List_124.size:" + List_124.size());
        progressbar.setVisibility(View.VISIBLE);
        if (List_124.size() > 0 && countOrder < List_124.size()) {
            Const.longInfo(TAG, "dataSend:" + List_124.get(countOrder));
            new HttpRequest(mActivity).new API_124(dataLogin, List_124.get(countOrder), new IF_124() {
                @Override
                public void onSuccess() {
                    countOrder++;
                    ob.setCOUNT_124("" + countOrder);
                    if (countOrder >= List_124.size()) {
                        callAPI();
                        ob.setIS_124("1");
                    } else {
                        call_API_124();
                    }
                }

                @Override
                public void onFail() {
                    dialogProblem(1);

                }
            }).execute();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        } else {
            ob.setIS_124("1");
            callAPI();
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
//            String filePath = params[0];
//            String serverUrl = params[1];
//            int isScale = Integer.parseInt(params[2]);
            return uploadFileToServer(filePath, Const.UpLoadImage, isScale);

        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Log.d(TAG, "result:" + result);
            if (result.startsWith("Error") || result.startsWith("error")) {
//                Log.e(TAG, "ERROR IMG");
//                Toast.makeText(mActivity, getResources().getString(R.string.cannotuploadsign), Toast.LENGTH_LONG).show();
//
//                finishOrder();
                dialogProblem(0);
            } else {
//                Toast.makeText(mActivity, "Upload sign picture ok", Toast.LENGTH_SHORT).show();
                result = result.substring(1, result.length() - 1);
                SIGNURL = Const.serverUrl + result;
                OrderActivity.URL_IMG = Const.serverUrl + result;
                initDBInputToServer();
                call_API_126();
            }
        }

    }

    private static int fileSize;
    public static File file;


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
            connection.setConnectTimeout(Const.TIME_OUT_UPLOAD_IMAGE);
            connection.setReadTimeout(Const.TIME_OUT_UPLOAD_IMAGE);
            connection.setRequestProperty("Connection", "Keep-Alive");
            connection.setRequestProperty("Content-Type",
                    "multipart/form-data; boundary=" + boundary);

            connection.setRequestProperty("filename", Const.PDA_ORDER_IMAGE);
            String id = MainActivity.SHOP_CODE;
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

    void dialogProblem(final int save) {
        OrderActivity.checkClick = true;
        rlConfirm.setEnabled(true);
        progressbar.setVisibility(View.GONE);
//        save : 0 -> upload image sign fail else 1
        final Dialog dialog = new Dialog(mActivity);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(R.layout.custom_dialog);
        dialog.setCanceledOnTouchOutside(false);
        TextView tvMsg = (TextView) dialog.findViewById(R.id.tvMsg);
        tvMsg.setText(getResources().getString(R.string.problemupload));
        Button btnOK = (Button) dialog.findViewById(R.id.btnOK);
        Button btnCancel = (Button) dialog.findViewById(R.id.btnCancel);
        btnOK.setText(getResources().getString(R.string.exit));
        btnCancel.setText(getResources().getString(R.string.tryagain));
        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog.dismiss();
                if (save == 0)
                    initDBInputToServer();
                finishOrder();


            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (progressbar != null) {
                    progressbar.setVisibility(View.VISIBLE);
                }
                dialog.dismiss();
//                doConfirm();


                OrderActivity.checkClick = false;
                rlConfirm.setEnabled(false);
                if (arrayList == null || arrayList.size() == 0) {
                    initDBInputToServer();
                    call_API_126();
                } else {
                    doConfirm();
                }
            }
        });

        dialog.show();
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

    String PMID = "";

    List<PMPRODUCTLIST> getPromotionListForProduct(String TPRDCD, int CSES, int BXCS, int BXES) {
//        Log.d(TAG,"input:"+TPRDCD);
        List<PMPRODUCTLIST> LIST_PROMOTION = new ArrayList<>();
        PMID = "";
        if (pmList != null) {
            if (pmList.getPMLIST() != null && pmList.getPMLIST().size() > 0) {
                for (int i = 0; i < pmList.getPMLIST().size(); i++) {
                    PMPRODUCTLIST pmproductlist = pmList.getPMLIST().get(i);
                    final String PM_LEVEL = pmproductlist.getV4();
                    PMID = pmproductlist.getV1();
                    List<PMPRODUCTLIST> aList = pmproductlist.getPMPRODUCTLIST();
                    if (aList != null && aList.size() > 0) {
                        for (int k = 0; k < aList.size(); k++) {
                            PMPRODUCTLIST ppd = aList.get(k);

                            if (ppd.getV1().equalsIgnoreCase("BUY") && ppd.getV5().equals(TPRDCD)) {
                                Log.d(TAG, "BUY:" + pmproductlist.getV1() + " TPRDCD:" + TPRDCD);
                                // set hightGift
                                int hightGift = 0;
                                for (PMPRODUCTLIST obj : aList) {
                                    if (obj.getV1().trim().equalsIgnoreCase("GIFT")) {
                                        String PRDCD = obj.getV11().trim();
                                        Log.d(TAG, "GIFT of " + TPRDCD + " is: " + ":" + PRDCD);
                                        String CONDITION = obj.getV9().trim();
                                        String UNIT = obj.getV7().trim();
                                        ConvertUnit dto = convertData(PRDCD);
                                        int CSES_2 = 0;
                                        int BXES_2 = 0;
                                        int BXCS_2 = 0;

                                        int QTY = 0;
                                        try {
                                            CSES_2 = Integer.parseInt(dto.getCSES());
                                            BXES_2 = Integer.parseInt(dto.getBXES());
                                            BXCS_2 = Integer.parseInt(dto.getBXCS());
                                            QTY = Integer.parseInt(obj.getV8().trim());
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                        if (CSES_2 == 0)
                                            CSES_2 = 1;
                                        if (BXES_2 == 0) {
                                            if (BXCS_2 == 0) {
                                                BXCS_2 = 1;
                                                BXES_2 = CSES_2;
                                            } else
                                                BXES_2 = BXCS_2 * CSES_2;
                                        }
                                        if (BXCS_2 == 0) {
                                            BXCS_2 = BXES_2 / CSES_2;
                                        }


                                        if (CONDITION.equalsIgnoreCase("0")) {
                                            //and
                                            int sumQty = UNIT.equals("3") ? QTY : UNIT.equals("2") ? QTY * CSES_2 : QTY * BXES_2;
                                            hightGift += sumQty;
                                        } else {
                                            //or
                                            int sumQty = UNIT.equals("3") ? QTY : UNIT.equals("2") ? QTY * CSES_2 : QTY * BXES_2;
                                            if (sumQty > hightGift) hightGift = sumQty;
                                        }

                                    }
                                }
                                Log.d(TAG, "PMID:" + PMID + " TPRDCD:" + TPRDCD + " sumQty:" + hightGift);
                                pmproductlist.setHightGift(hightGift);


                                // set sumQty
                                String UNIT = ppd.getV7().trim();
                                String strQTY = ppd.getV8().trim();
                                int sumQty = 0;
                                try {
                                    int QTY = Integer.parseInt(strQTY);
                                    sumQty = UNIT.equals("3") ? QTY : UNIT.equals("2") ? QTY * CSES : QTY * BXES;
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
//                                Log.d(TAG, "PMID:" + PMID + " TPRDCD:" + TPRDCD + " sumQty:" + sumQty);
                                pmproductlist.setSumQty(sumQty);


                                if (PM_LEVEL.equals("1")) {
                                    String nonShop = "";
                                    if (pmproductlist.getV7().equalsIgnoreCase("Y"))
                                        nonShop = "3";
                                    else if (pmproductlist.getV6().equalsIgnoreCase("Y"))
                                        nonShop = "2";
                                    else if (pmproductlist.getV5().equalsIgnoreCase("Y"))
                                        nonShop = "1";
                                    if (get_API_122(MainActivity.SHOP_ID, TPRDCD, nonShop)) {
                                        LIST_PROMOTION.add(pmproductlist);
                                    }
                                }
                                if (PM_LEVEL.equals("2")) {
                                    if (Const.getDBNewShop(LIST_PMT, MainActivity.SHOP_ID)) {
                                        LIST_PROMOTION.add(pmproductlist);
                                    }
                                }
                                if (PM_LEVEL.equals("3")) {
                                    String CHANNEL = pmproductlist.getV9();
                                    if (CHANNEL.equalsIgnoreCase(MainActivity.CHANNEL)) {
                                        Log.d(TAG, "CHANNEL:" + MainActivity.CHANNEL + " TPRDCD:" + TPRDCD);
                                        LIST_PROMOTION.add(pmproductlist);
                                    }
                                }
                                if (PM_LEVEL.equals("4")) {
                                    if (MainActivity.SHOP_GRADE.equalsIgnoreCase("A")) {
                                        if (pmproductlist.getV10().equalsIgnoreCase("Y"))
                                            if (MainActivity.AREA.equals("1")) {
                                                if (pmproductlist.getV14().equals("Y")) {
                                                    LIST_PROMOTION.add(pmproductlist);
                                                }
                                            } else if (MainActivity.AREA.equals("2")) {
                                                if (pmproductlist.getV15().equals("Y")) {
                                                    LIST_PROMOTION.add(pmproductlist);
                                                }
                                            } else if (MainActivity.AREA.equals("3")) {
                                                if (pmproductlist.getV16().equals("Y")) {
                                                    LIST_PROMOTION.add(pmproductlist);
                                                }
                                            }
                                    } else if (MainActivity.SHOP_GRADE.equalsIgnoreCase("B")) {
                                        if (pmproductlist.getV11().equalsIgnoreCase("Y"))
                                            if (MainActivity.AREA.equals("1")) {
                                                if (pmproductlist.getV14().equals("Y")) {
                                                    LIST_PROMOTION.add(pmproductlist);
                                                }
                                            } else if (MainActivity.AREA.equals("2")) {
                                                if (pmproductlist.getV15().equals("Y")) {
                                                    LIST_PROMOTION.add(pmproductlist);
                                                }
                                            } else if (MainActivity.AREA.equals("3")) {
                                                if (pmproductlist.getV16().equals("Y")) {
                                                    LIST_PROMOTION.add(pmproductlist);
                                                }
                                            }
                                    } else if (MainActivity.SHOP_GRADE.equalsIgnoreCase("C")) {
                                        if (pmproductlist.getV12().equalsIgnoreCase("Y"))
                                            if (MainActivity.AREA.equals("1")) {
                                                if (pmproductlist.getV14().equals("Y")) {
                                                    LIST_PROMOTION.add(pmproductlist);
                                                }
                                            } else if (MainActivity.AREA.equals("2")) {
                                                if (pmproductlist.getV15().equals("Y")) {
                                                    LIST_PROMOTION.add(pmproductlist);
                                                }
                                            } else if (MainActivity.AREA.equals("3")) {
                                                if (pmproductlist.getV16().equals("Y")) {
                                                    LIST_PROMOTION.add(pmproductlist);
                                                }
                                            }
                                    } else if (MainActivity.SHOP_GRADE.equalsIgnoreCase("D")) {
                                        if (pmproductlist.getV13().equalsIgnoreCase("Y"))
                                            if (MainActivity.AREA.equals("1")) {
                                                if (pmproductlist.getV14().equals("Y")) {
                                                    LIST_PROMOTION.add(pmproductlist);
                                                }
                                            } else if (MainActivity.AREA.equals("2")) {
                                                if (pmproductlist.getV15().equals("Y")) {
                                                    LIST_PROMOTION.add(ppd);
                                                }
                                            } else if (MainActivity.AREA.equals("3")) {
                                                if (pmproductlist.getV16().equals("Y")) {
                                                    LIST_PROMOTION.add(pmproductlist);
                                                }
                                            }
                                    }
                                }
                                if (PM_LEVEL.equals("5")) {
                                    LIST_PROMOTION.add(pmproductlist);
                                }
                            }
                        }
                    }
                }
            }
        }
        if (LIST_PROMOTION != null && LIST_PROMOTION.size() > 0) {


            /** KM 20170411 -> calculator sum price
             for (int k = 0; k < LIST_PROMOTION.size(); k++) {
             PMPRODUCTLIST pmproductlist = LIST_PROMOTION.get(k);
             int ConBuying = 0;
             List<PMPRODUCTLIST> LIST = pmproductlist.getPMPRODUCTLIST();
             for (PMPRODUCTLIST b : LIST) {
             if (b.getV1().trim().equals("BUY")) {
             ConBuying = Integer.parseInt(b.getV9());
             if (ConBuying == 0) {
             // and
             int price = 0;
             for (PMPRODUCTLIST obj : LIST) {
             if (obj.getV1().trim().equals("BUY")) {
             String unit = obj.getV7().trim();
             String SKU = obj.getV5().trim();
             String getPrice = Const.getPartInt(getPrice(SKU, unit));

             try {
             int QTY = Integer.parseInt(obj.getV8().trim());
             price += Integer.parseInt(getPrice) * QTY;
             } catch (Exception e) {
             e.printStackTrace();
             }
             Log.d(TAG, "SKU:" + SKU + " * " + unit + " * " + getPrice);
             //                                    price+=
             }
             }
             Log.d(TAG, "price:" + price);
             LIST_PROMOTION.get(k).setPRICE(price);
             } else {
             // or
             int price = 0;
             for (PMPRODUCTLIST obj : LIST) {
             if (obj.getV1().trim().equals("BUY")) {
             String unit = obj.getV7().trim();
             String SKU = obj.getV5().trim();
             String getPrice = Const.getPartInt(getPrice(SKU, unit));
             try {
             int QTY = Integer.parseInt(obj.getV8().trim());
             int a = Integer.parseInt(getPrice) * QTY;
             if (a > price) price = a;
             } catch (Exception e) {
             e.printStackTrace();
             }
             Log.d(TAG, "SKU:" + SKU + " * " + unit + " * " + getPrice);
             //                                    price+=
             }
             }
             Log.d(TAG, "price:" + price);
             LIST_PROMOTION.get(k).setPRICE(price);
             }
             break;
             }
             }
             String promotionCode = pmproductlist.getV1();
             String PERCENT = pmproductlist.getV17();
             Log.d(TAG, "ConBuying:" + ConBuying + " PM:" + promotionCode + " PERCENT:" + PERCENT);
             }
             */


        }
        return LIST_PROMOTION;
    }

//    void getDBPromotionList(String TPRDCD) {
//        PMID = "";
//        if (pmList != null) {
//            if (pmList.getPMLIST() != null && pmList.getPMLIST().size() > 0) {
//                for (int i = 0; i < pmList.getPMLIST().size(); i++) {
//                    PMPRODUCTLIST pmproductlist = pmList.getPMLIST().get(i);
//                    final String PM_LEVEL = pmproductlist.getV4();
//                    PMID = pmproductlist.getV1();
//                    List<PMPRODUCTLIST> aList = pmproductlist.getPMPRODUCTLIST();
//                    if (aList != null && aList.size() > 0) {
//                        for (int k = 0; k < aList.size(); k++) {
//                            PMPRODUCTLIST ppd = aList.get(k);
//                            if (ppd.getV1().equalsIgnoreCase("BUY") && ppd.getV5().equals(TPRDCD)) {
//                                if (PM_LEVEL.equals("1")) {
//                                    String nonShop = "";
//                                    if (pmproductlist.getV7().equalsIgnoreCase("Y"))
//                                        nonShop = "3";
//                                    else if (pmproductlist.getV6().equalsIgnoreCase("Y"))
//                                        nonShop = "2";
//                                    else if (pmproductlist.getV5().equalsIgnoreCase("Y"))
//                                        nonShop = "1";
//                                    if (get_API_122(MainActivity.SHOP_ID, TPRDCD, nonShop)) {
//                                        LIST_PROMOTION.add(pmproductlist);
//                                    }
//                                }
//                                if (PM_LEVEL.equals("2")) {
//                                    if (Const.getDBNewShop(LIST_PMT, MainActivity.SHOP_ID)) {
//                                        LIST_PROMOTION.add(pmproductlist);
//                                    }
//                                }
//                                if (PM_LEVEL.equals("3")) {
//                                    String CHANNEL = pmproductlist.getV9();
//                                    if (CHANNEL.equalsIgnoreCase(MainActivity.CHANNEL)) {
//                                        Log.d(TAG, "CHANNEL:" + MainActivity.CHANNEL + " TPRDCD:" + TPRDCD);
//                                        LIST_PROMOTION.add(pmproductlist);
//                                    }
//                                }
//                                if (PM_LEVEL.equals("4")) {
//                                    if (MainActivity.SHOP_GRADE.equalsIgnoreCase("A")) {
//                                        if (pmproductlist.getV10().equalsIgnoreCase("Y"))
//                                            if (MainActivity.AREA.equals("1")) {
//                                                if (pmproductlist.getV14().equals("Y")) {
//                                                    LIST_PROMOTION.add(pmproductlist);
//                                                }
//                                            } else if (MainActivity.AREA.equals("2")) {
//                                                if (pmproductlist.getV15().equals("Y")) {
//                                                    LIST_PROMOTION.add(pmproductlist);
//                                                }
//                                            } else if (MainActivity.AREA.equals("3")) {
//                                                if (pmproductlist.getV16().equals("Y")) {
//                                                    LIST_PROMOTION.add(pmproductlist);
//                                                }
//                                            }
//                                    } else if (MainActivity.SHOP_GRADE.equalsIgnoreCase("B")) {
//                                        if (pmproductlist.getV11().equalsIgnoreCase("Y"))
//                                            if (MainActivity.AREA.equals("1")) {
//                                                if (pmproductlist.getV14().equals("Y")) {
//                                                    LIST_PROMOTION.add(pmproductlist);
//                                                }
//                                            } else if (MainActivity.AREA.equals("2")) {
//                                                if (pmproductlist.getV15().equals("Y")) {
//                                                    LIST_PROMOTION.add(pmproductlist);
//                                                }
//                                            } else if (MainActivity.AREA.equals("3")) {
//                                                if (pmproductlist.getV16().equals("Y")) {
//                                                    LIST_PROMOTION.add(pmproductlist);
//                                                }
//                                            }
//                                    } else if (MainActivity.SHOP_GRADE.equalsIgnoreCase("C")) {
//                                        if (pmproductlist.getV12().equalsIgnoreCase("Y"))
//                                            if (MainActivity.AREA.equals("1")) {
//                                                if (pmproductlist.getV14().equals("Y")) {
//                                                    LIST_PROMOTION.add(pmproductlist);
//                                                }
//                                            } else if (MainActivity.AREA.equals("2")) {
//                                                if (pmproductlist.getV15().equals("Y")) {
//                                                    LIST_PROMOTION.add(pmproductlist);
//                                                }
//                                            } else if (MainActivity.AREA.equals("3")) {
//                                                if (pmproductlist.getV16().equals("Y")) {
//                                                    LIST_PROMOTION.add(pmproductlist);
//                                                }
//                                            }
//                                    } else if (MainActivity.SHOP_GRADE.equalsIgnoreCase("D")) {
//                                        if (pmproductlist.getV13().equalsIgnoreCase("Y"))
//                                            if (MainActivity.AREA.equals("1")) {
//                                                if (pmproductlist.getV14().equals("Y")) {
//                                                    LIST_PROMOTION.add(pmproductlist);
//                                                }
//                                            } else if (MainActivity.AREA.equals("2")) {
//                                                if (pmproductlist.getV15().equals("Y")) {
//                                                    LIST_PROMOTION.add(ppd);
//                                                }
//                                            } else if (MainActivity.AREA.equals("3")) {
//                                                if (pmproductlist.getV16().equals("Y")) {
//                                                    LIST_PROMOTION.add(pmproductlist);
//                                                }
//                                            }
//                                    }
//                                }
//                                if (PM_LEVEL.equals("5")) {
//                                    LIST_PROMOTION.add(pmproductlist);
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//        }
//    }
}
