package com.orion.salesman._route._fragment;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.orion.salesman.OrderActivity;
import com.orion.salesman.R;
import com.orion.salesman._adaper.ViewPagerAdapter;
import com.orion.salesman._app.MyApplication;
import com.orion.salesman._object.API_121;
import com.orion.salesman._object.CodeH;
import com.orion.salesman._object.PMPRODUCTLIST;
import com.orion.salesman._object.PmList;
import com.orion.salesman._offline.OfflineObject;
import com.orion.salesman._pref.PrefManager;
import com.orion.salesman._route._adapter.PromotionPieAdapter;
import com.orion.salesman._route._object.PromotionInfor;
import com.orion.salesman._route._object.PromotionPieObject;
import com.orion.salesman._sqlite.DataBaseCodeH;
import com.orion.salesman._sqlite.DatabaseHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by maidinh on 18/8/2016.
 */
public class PromotionPie extends Fragment {
    String TAG = "PromotionPie";

    private RecyclerView recyclerView;
    private PromotionPieAdapter mAdapter;
    private List<CodeH> listPMT = new ArrayList<>();
    /*
    * position:0 -> pie
    * position:1 -> snack
    * position:2 -> gum
    * */
    private int position;
    private TextView tvNodata;

    String getTypeString(String type) {
        String s = "";
        for (int i = 0; i < listPMT.size(); i++) {
            if (type.equalsIgnoreCase(listPMT.get(i).getCODEKEY()))
                s = listPMT.get(i).getCODEDESC();
        }
        return s;
    }

    String TEAM = "";
    private List<PromotionPieObject> arrayList = new ArrayList<>();
    private List<CodeH> codeHList = new ArrayList<>();
    List<API_121> api_121List = new ArrayList<>();

    void initDB() {
        codeHList.clear();
        arrayList.clear();
        api_121List.clear();
        DatabaseHandler db = new DatabaseHandler(getActivity());
        api_121List = db.getListAPI_121();
        String monthPromotion = RouteFragment.DATE.substring(0, 6);
        if (api_121List != null && api_121List.size() > 0) {
//            API_121 api_121 = api_121List.get(api_121List.size() - 1);
            API_121 api_121 = null;
            for (int i = 0; i < api_121List.size(); i++) {
                api_121 = api_121List.get(i);
                if (monthPromotion.equals(api_121.getDATE().substring(0, 6))) {
                    break;
                }
            }

            DataBaseCodeH dataBaseCodeH = new DataBaseCodeH(getActivity());
            dataBaseCodeH.openDB();
            codeHList = dataBaseCodeH.getData();
            for (CodeH s : codeHList) {
                if (s.getCODEGROUP().equalsIgnoreCase("PMTLEVEL"))
                    listPMT.add(s);
            }
//        String s = new PrefManager(getActivity()).getApi121();
//        OfflineObject ob = new Gson().fromJson(s, OfflineObject.class);
            PmList pmList = new Gson().fromJson(api_121.getDATA(), PmList.class);
            if (pmList.getPMLIST() != null && pmList.getPMLIST().size() > 0) {
                for (int i = 0; i < pmList.getPMLIST().size(); i++) {
                    PMPRODUCTLIST pmproductlist = pmList.getPMLIST().get(i);
                    String PM_LEVEL = pmproductlist.getV4();
                    String GIFTAMTPERCENT = pmproductlist.getV17();
                    String buy = "", gift = "";
                    List<PMPRODUCTLIST> aList = pmproductlist.getPMPRODUCTLIST();
                    if (aList != null && aList.size() > 0) {
                        for (int k = 0; k < aList.size(); k++) {
                            PMPRODUCTLIST ppd = aList.get(k);
                            if (ppd.getV1().equals("BUY")) {
                                String UNIT = "";
                                if (ppd.getV7().equals("1"))
                                    UNIT = "Box";
                                else if (ppd.getV7().equals("2"))
                                    UNIT = "CASE";
                                else if (ppd.getV7().equals("3"))
                                    UNIT = "EA";
                                TEAM = ppd.getV10();
                                if (buy.length() == 0) {
                                    buy += ppd.getV8() + " " + UNIT + " " + ppd.getV4() + " " + ppd.getV6();
                                } else {
                                    String CONDITION = "";
                                    if (ppd.getV9().equals("0"))
                                        CONDITION = " + ";
                                    else
                                        CONDITION = " or ";
                                    buy += CONDITION + ppd.getV8() + " " + UNIT + " " + ppd.getV4() + " " + ppd.getV6();
                                }
                            } else if (ppd.getV1().equals("GIFT")) {
                                String UNIT = "";
                                if (ppd.getV7().equals("1"))
                                    UNIT = "Box";
                                else if (ppd.getV7().equals("2"))
                                    UNIT = "CASE";
                                else if (ppd.getV7().equals("3"))
                                    UNIT = "EA";
//                                Log.d(TAG, "V6:" + ppd.getV6() + "  -  V12:" + ppd.getV12());
                                if (gift.length() == 0) {
                                    gift += ppd.getV8() + " " + UNIT + " " + ppd.getV4() + " " + ppd.getV12();
                                } else {
                                    String CONDITION = "";
                                    if (ppd.getV9().equals("0"))
                                        CONDITION = " + ";
                                    else
                                        CONDITION = " or ";
//                                    gift += CONDITION + ppd.getV8() + " " + UNIT + " " + ppd.getV4() + " " + ppd.getV6();
                                    String result = ppd.getV8() + " " + UNIT + " " + ppd.getV4() + " " + ppd.getV12();
                                    if (!gift.contains(result))
                                        gift += CONDITION + result;
                                }
                            }
                        }
                    }
                    String content = buy + " => " + gift;
                    PromotionPieObject promotionPieObject = new PromotionPieObject(pmproductlist.getV2(), pmproductlist.getV3(), content, getTypeString(pmproductlist.getV4()),
                            PM_LEVEL, GIFTAMTPERCENT);
                    if (position == 0) {
                        if (TEAM.equalsIgnoreCase("PIE"))
                            arrayList.add(promotionPieObject);
                    } else if (position == 1) {
                        if (TEAM.equalsIgnoreCase("SNACK"))
                            arrayList.add(promotionPieObject);
                    } else if (position == 2) {
                        if (TEAM.equalsIgnoreCase("GUM "))
                            arrayList.add(promotionPieObject);
                    }
                }
            }
        }
    }

    void init(View v) {
        initDB();
        tvNodata = (TextView) v.findViewById(R.id.tvNodata);
        if (arrayList == null || arrayList.size() == 0)
            tvNodata.setVisibility(View.VISIBLE);
        else tvNodata.setVisibility(View.GONE);

        if (arrayList != null && arrayList.size() > 1) {
            for (int i = 0; i < arrayList.size(); i++) {
                for (int j = 0; j <= i; j++) {
//                        if (Float.parseFloat(productList.get(i).getGIFTAMTPERCENT()) <= Float.parseFloat(productList.get(j).getGIFTAMTPERCENT())) {
                    if (Float.parseFloat(arrayList.get(i).getGIFTAMTPERCENT()) >= Float.parseFloat(arrayList.get(j).getGIFTAMTPERCENT())) {
                        PromotionPieObject pp = arrayList.get(i);
                        arrayList.set(i, arrayList.get(j));
                        arrayList.set(j, pp);
                    }
                }
            }
            for (int i = 0; i < arrayList.size(); i++) {
                for (int j = 0; j <= i; j++) {
                    if (Float.parseFloat(arrayList.get(i).getLEVEL()) < Float.parseFloat(arrayList.get(j).getLEVEL())) {
//                        if (Float.parseFloat(productList.get(i).getGIFTAMTPERCENT()) >= Float.parseFloat(productList.get(j).getGIFTAMTPERCENT())) {
                        PromotionPieObject pp = arrayList.get(i);
                        arrayList.set(i, arrayList.get(j));
                        arrayList.set(j, pp);
                    }
                }
            }

        }


        mAdapter = new PromotionPieAdapter(getActivity(), arrayList);
        recyclerView = (RecyclerView) v.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(mAdapter);
    }

    @SuppressLint("ValidFragment")
    public PromotionPie(int position) {
        this.position = position;
    }

    public PromotionPie() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.promotion_pie_fragment, container, false);
        init(v);
        return v;
    }
}