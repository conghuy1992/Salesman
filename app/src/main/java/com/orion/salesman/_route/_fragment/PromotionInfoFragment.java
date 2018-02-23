package com.orion.salesman._route._fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.orion.salesman.MainActivity;
import com.orion.salesman.OrderActivity;
import com.orion.salesman.R;
import com.orion.salesman._class.Const;
import com.orion.salesman._object.API_121;
import com.orion.salesman._object.API_122;
import com.orion.salesman._object.API_123;
import com.orion.salesman._object.CodeH;
import com.orion.salesman._object.GetNewShopPromotionList;
import com.orion.salesman._object.ListSKUPromotion;
import com.orion.salesman._object.NewShopPromotionList;
import com.orion.salesman._object.PMPRODUCTLIST;
import com.orion.salesman._object.PmList;
import com.orion.salesman._object.SKUPromotion;
import com.orion.salesman._offline.OfflineObject;
import com.orion.salesman._pref.PrefManager;
import com.orion.salesman._route._adapter.PromotionInforAdapter;
import com.orion.salesman._route._object.PromotionInfor;
import com.orion.salesman._route._object.PromotionPieObject;
import com.orion.salesman._sqlite.DataBaseCodeH;
import com.orion.salesman._sqlite.DatabaseHandler;

import java.util.ArrayList;
import java.util.List;

public class PromotionInfoFragment extends Fragment {
    String TAG = "PromotionInfoFragment";
    private List<PromotionInfor> arrayList = new ArrayList<>();
    private RecyclerView recyclerView;
    private PromotionInforAdapter mAdapter;

    //    public void initDB() {
//        PromotionInfor ob = new PromotionInfor("1", "2", "3");
//        arrayList.add(ob);
//        ob = new PromotionInfor("1", "2", "3");
//        arrayList.add(ob);
//        ob = new PromotionInfor("1", "2", "3");
//        arrayList.add(ob);
//    }
//    private List<PromotionPieObject> arrayList = new ArrayList<>();
    private List<CodeH> codeHList = new ArrayList<>();
    private List<CodeH> listPMT = new ArrayList<>();

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

    void initDB() {
        codeHList.clear();
        arrayList.clear();
        api_121List.clear();
        DatabaseHandler db = new DatabaseHandler(getActivity());
        api_121List = db.getListAPI_121();
        DataBaseCodeH dataBaseCodeH = new DataBaseCodeH(getActivity());
        dataBaseCodeH.openDB();
        codeHList = dataBaseCodeH.getData();
        for (CodeH s : codeHList) {
            if (s.getCODEGROUP().equalsIgnoreCase("PMTLEVEL"))
                listPMT.add(s);
        }
        API_121 api_121 = api_121List.get(api_121List.size() - 1);
        pmList = new Gson().fromJson(api_121.getDATA(), PmList.class);
        for (int i = 0; i < pmList.getPMLIST().size(); i++) {
            PMPRODUCTLIST pmproductlist = pmList.getPMLIST().get(i);
            String PM_LEVEL = pmproductlist.getV4();
            String GIFTAMTPERCENT = pmproductlist.getV17();
            String ID_PROMOTION = pmproductlist.getV1();
            if (checkContains(pmproductlist, MainActivity.TPRDCD)) {
                String buy = "", gift = "";
                List<PMPRODUCTLIST> aList = pmproductlist.getPMPRODUCTLIST();
                String TPRDCD = "";
                String PRDCLS1NM = "";
                for (int k = 0; k < aList.size(); k++) {
                    PMPRODUCTLIST ppd = aList.get(k);
                    TPRDCD = ppd.getV5();
//                    Log.d(TAG, "TPRDCD:" + TPRDCD + " ID_PROMOTION:" + ID_PROMOTION + " K:" + k);
                    if (ppd.getV1().equals("BUY") && TPRDCD.equals(MainActivity.TPRDCD)) {
                        PRDCLS1NM = ppd.getV4();
                        String UNIT = "";
                        if (ppd.getV7().equals("1"))
                            UNIT = "Box";
                        else if (ppd.getV7().equals("2"))
                            UNIT = "CASE";
                        else if (ppd.getV7().equals("3"))
                            UNIT = "EA";
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
                        if (gift.length() == 0) {
                            gift += ppd.getV8() + " " + UNIT + " " + ppd.getV4() + " " + ppd.getV12();
                        } else {
                            String CONDITION = "";
                            if (ppd.getV9().equals("0"))
                                CONDITION = " + ";
                            else
                                CONDITION = " or ";
                            String result = ppd.getV8() + " " + UNIT + " " + ppd.getV4() + " " + ppd.getV12();
                            if (!gift.contains(result))
                                gift += CONDITION + result;
                        }
                    }

                }
                String content = buy + " => " + gift;
                Log.d(TAG, "content:" + content);
                PromotionInfor promotionPieObject = new PromotionInfor(pmproductlist.getV2(), pmproductlist.getV3(), content, pmproductlist, PM_LEVEL, GIFTAMTPERCENT);
                arrayList.add(promotionPieObject);
            }
        }

        if (arrayList.size() == 1) {
            Log.d(TAG, "TPRDCD:" + MainActivity.TPRDCD);
            PromotionInfor infor = arrayList.get(0);
            PMPRODUCTLIST pmproductlist = infor.getPmproductlist();
            String PM_LEVEL = pmproductlist.getV4();
            Log.d(TAG, "START PM_LEVEL:" + PM_LEVEL);
        }
        Log.d(TAG, "arrayList:" + arrayList.size());
        if (arrayList != null && arrayList.size() > 1) {
            for (int i = 0; i < arrayList.size(); i++) {
                Log.d(TAG, "TPRDCD:" + MainActivity.TPRDCD);
                PromotionInfor infor = arrayList.get(i);
                PMPRODUCTLIST pmproductlist = infor.getPmproductlist();
                String PM_LEVEL = pmproductlist.getV4();
                Log.d(TAG, "START PM_LEVEL:" + PM_LEVEL);
                if (PM_LEVEL.equals("1")) {
                    String nonShop = "";
                    if (pmproductlist.getV7().equalsIgnoreCase("Y"))
                        nonShop = "3";
                    else if (pmproductlist.getV6().equalsIgnoreCase("Y"))
                        nonShop = "2";
                    else if (pmproductlist.getV5().equalsIgnoreCase("Y"))
                        nonShop = "1";
                    Log.d(TAG, "nonShop:" + nonShop);
//                    Log.d(TAG, "I:" + i + " CHECK:" + get_API_122(MainActivity.SHOP_ID, MainActivity.TPRDCD, nonShop));
                    if (!get_API_122(MainActivity.SHOP_ID, MainActivity.TPRDCD, nonShop)) {
                        arrayList.remove(i);
                        i--;
                        Log.d(TAG, "PM_LEVEL:" + PM_LEVEL + " FALSE" + " ID:" + pmproductlist.getV1() + " TPRDCD:" + MainActivity.TPRDCD);
                    } else {
                        Log.d(TAG, "PM_LEVEL:" + PM_LEVEL + " TRUE" + " ID:" + pmproductlist.getV1() + " TPRDCD:" + MainActivity.TPRDCD);
                    }
                }
                if (PM_LEVEL.equals("2")) {
                    Log.d(TAG, "PM_LEVEL:" + PM_LEVEL + " TRUE" + " ID:" + pmproductlist.getV1() + " TPRDCD:" + MainActivity.TPRDCD);
                    if (!Const.getDBNewShop(LIST_PMT, MainActivity.SHOP_ID)) {
                        arrayList.remove(i);
                        i--;
                    }
                }
                if (PM_LEVEL.equals("3")) {
                    String CHANNEL = pmproductlist.getV9();
                    if (!CHANNEL.equalsIgnoreCase(MainActivity.CHANNEL)) {
                        arrayList.remove(i);
                        i--;
                        Log.d(TAG, "PM_LEVEL:" + PM_LEVEL + " FALSE");
                    } else {
                        Log.d(TAG, "PM_LEVEL:" + PM_LEVEL + " TRUE");
                    }
                }
                if (PM_LEVEL.equals("4")) {
                    boolean checkPR = false;
                    if (MainActivity.SHOP_GRADE.equalsIgnoreCase("A")) {
                        if (pmproductlist.getV10().equalsIgnoreCase("Y"))
                            if (MainActivity.AREA.equals("1")) {
                                if (pmproductlist.getV14().equals("Y"))
                                    checkPR = true;
                            } else if (MainActivity.AREA.equals("2")) {
                                if (pmproductlist.getV15().equals("Y"))
                                    checkPR = true;
                            } else if (MainActivity.AREA.equals("3")) {
                                if (pmproductlist.getV16().equals("Y"))
                                    checkPR = true;
                            }
                    } else if (MainActivity.SHOP_GRADE.equalsIgnoreCase("B")) {
                        if (pmproductlist.getV11().equalsIgnoreCase("Y"))
                            if (MainActivity.AREA.equals("1")) {
                                if (pmproductlist.getV14().equals("Y"))
                                    checkPR = true;
                            } else if (MainActivity.AREA.equals("2")) {
                                if (pmproductlist.getV15().equals("Y"))
                                    checkPR = true;
                            } else if (MainActivity.AREA.equals("3")) {
                                if (pmproductlist.getV16().equals("Y"))
                                    checkPR = true;
                            }
                    } else if (MainActivity.SHOP_GRADE.equalsIgnoreCase("C")) {
                        if (pmproductlist.getV12().equalsIgnoreCase("Y"))
                            if (MainActivity.AREA.equals("1")) {
                                if (pmproductlist.getV14().equals("Y"))
                                    checkPR = true;
                            } else if (MainActivity.AREA.equals("2")) {
                                if (pmproductlist.getV15().equals("Y"))
                                    checkPR = true;
                            } else if (MainActivity.AREA.equals("3")) {
                                if (pmproductlist.getV16().equals("Y"))
                                    checkPR = true;
                            }
                    } else if (MainActivity.SHOP_GRADE.equalsIgnoreCase("D")) {
                        if (pmproductlist.getV13().equalsIgnoreCase("Y"))
                            if (MainActivity.AREA.equals("1")) {
                                if (pmproductlist.getV14().equals("Y"))
                                    checkPR = true;
                            } else if (MainActivity.AREA.equals("2")) {
                                if (pmproductlist.getV15().equals("Y"))
                                    checkPR = true;
                            } else if (MainActivity.AREA.equals("3")) {
                                if (pmproductlist.getV16().equals("Y"))
                                    checkPR = true;
                            }
                    }
                    if (!checkPR) {
                        arrayList.remove(i);
                        i--;
                    }
                }
            }
            for (int i = 0; i < arrayList.size(); i++) {
                for (int j = 0; j <= i; j++) {
//                        if (Float.parseFloat(productList.get(i).getGIFTAMTPERCENT()) <= Float.parseFloat(productList.get(j).getGIFTAMTPERCENT())) {
                    if (Float.parseFloat(arrayList.get(i).getGIFTAMTPERCENT()) >= Float.parseFloat(arrayList.get(j).getGIFTAMTPERCENT())) {
                        PromotionInfor pp = arrayList.get(i);
                        arrayList.set(i, arrayList.get(j));
                        arrayList.set(j, pp);
                    }
                }
            }
            for (int i = 0; i < arrayList.size(); i++) {
                for (int j = 0; j <= i; j++) {
                    if (Float.parseFloat(arrayList.get(i).getPM_LEVEL()) < Float.parseFloat(arrayList.get(j).getPM_LEVEL())) {
//                        if (Float.parseFloat(productList.get(i).getGIFTAMTPERCENT()) >= Float.parseFloat(productList.get(j).getGIFTAMTPERCENT())) {
                        PromotionInfor pp = arrayList.get(i);
                        arrayList.set(i, arrayList.get(j));
                        arrayList.set(j, pp);
                    }
                }
            }
            for (int i = 0; i < arrayList.size(); i++) {
                Log.d(TAG, "GIFTAMTPERCENT" + arrayList.get(i).getGIFTAMTPERCENT() + " LEVEL:" + arrayList.get(i).getPM_LEVEL() + " ID:" + arrayList.get(i).getPmproductlist().getV1());
            }
        }
    }

    List<NewShopPromotionList> LIST_PMT = new ArrayList<>();

    public void createRC_1(View v) {
        initDB();
        mAdapter = new PromotionInforAdapter(getActivity(),arrayList);
        recyclerView = (RecyclerView) v.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
    }

    public PromotionInfoFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.promotion_info_fragment, container, false);
        DatabaseHandler db = new DatabaseHandler(getActivity());

        List<API_123> listAPI_123 = db.getListAPI_123();
        if (listAPI_123 != null && listAPI_123.size() > 0) {
            API_123 api_123 = listAPI_123.get(listAPI_123.size() - 1);
            GetNewShopPromotionList getNewShopPromotionList = new Gson().fromJson(api_123.getDATA(), GetNewShopPromotionList.class);
            LIST_PMT = getNewShopPromotionList.getLIST();
//            Log.d(TAG, "LIST_PMT:" + new Gson().toJson(LIST_PMT));
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
        createRC_1(v);
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        OrderActivity.flag = 4;
        OrderActivity.instance.hideBtnNext();
        OrderActivity.instance.setTvStock(getResources().getString(R.string.proinfor));

    }

    List<API_122> api_122List = new ArrayList<>();
    List<SKUPromotion> LIST = new ArrayList<>();
    String GIFTAMTPERCENT = "";
    String PMID = "";

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
}