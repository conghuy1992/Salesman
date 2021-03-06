package com.orion.salesman;

import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
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
import com.orion.salesman._route._adapter.PromotionInforAdapter;
import com.orion.salesman._route._fragment.RouteFragment;
import com.orion.salesman._route._object.PromotionInfor;
import com.orion.salesman._sqlite.DataBaseCodeH;
import com.orion.salesman._sqlite.DatabaseHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by maidinh on 13/10/2016.
 */
public class PromotionItem extends AppCompatActivity {
    String TAG = "PromotionItem";
    private List<PromotionInfor> arrayList = new ArrayList<>();
    private RecyclerView recyclerView;
    private PromotionInforAdapter mAdapter;
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
        DatabaseHandler db = new DatabaseHandler(this);
        api_121List = db.getListAPI_121();
        DataBaseCodeH dataBaseCodeH = new DataBaseCodeH(this);
        dataBaseCodeH.openDB();
        codeHList = dataBaseCodeH.getData();
        for (CodeH s : codeHList) {
            if (s.getCODEGROUP().equalsIgnoreCase("PMTLEVEL"))
                listPMT.add(s);
        }
//        API_121 api_121 = api_121List.get(api_121List.size() - 1);
        String monthPromotion = RouteFragment.DATE.substring(0, 6);
        if (api_121List != null && api_121List.size() > 0) {
            for (int i = 0; i < api_121List.size(); i++) {
                API_121 api_121 = api_121List.get(i);
                if (monthPromotion.equals(api_121.getDATE().substring(0, 6))) {
                    pmList = new Gson().fromJson(api_121.getDATA(), PmList.class);
                    break;
                }
            }
        }


        if (pmList.getPMLIST() != null && pmList.getPMLIST().size() > 0) {
//            for (int i = 0; i < pmList.getPMLIST().size(); i++) {
//                for (int j = 0; j <= i; j++) {
//                    if (Float.parseFloat(pmList.getPMLIST().get(i).getV17()) >= Float.parseFloat(pmList.getPMLIST().get(j).getV17())) {
//                        PMPRODUCTLIST pp = pmList.getPMLIST().get(i);
//                        pmList.getPMLIST().set(i, pmList.getPMLIST().get(j));
//                        pmList.getPMLIST().set(j, pp);
//                    }
//                }
//            }

//            for (int i = 0; i < pmList.getPMLIST().size(); i++) {
//                for (int j = 0; j <= i; j++) {
//                    if (Float.parseFloat(pmList.getPMLIST().get(i).getV4()) < Float.parseFloat(pmList.getPMLIST().get(j).getV4())) {
//                        PMPRODUCTLIST pp = pmList.getPMLIST().get(i);
//                        pmList.getPMLIST().set(i, pmList.getPMLIST().get(j));
//                        pmList.getPMLIST().set(j, pp);
//                    }
//                }
//            }
        }


        getDBPromotionList(MainActivity.TPRDCD);

        if (LIST_PROMOTION != null && LIST_PROMOTION.size() > 0) {

            for (int i = 0; i < LIST_PROMOTION.size(); i++) {
                for (int j = 0; j <= i; j++) {
                    if (Float.parseFloat(LIST_PROMOTION.get(i).getV17()) >= Float.parseFloat(LIST_PROMOTION.get(j).getV17())) {
                        PMPRODUCTLIST pp = LIST_PROMOTION.get(i);
                        LIST_PROMOTION.set(i, LIST_PROMOTION.get(j));
                        LIST_PROMOTION.set(j, pp);
                    }
                }
            }
            for (int i = 0; i < LIST_PROMOTION.size(); i++) {
                for (int j = 0; j <= i; j++) {
                    if (Float.parseFloat(LIST_PROMOTION.get(i).getV4()) < Float.parseFloat(LIST_PROMOTION.get(j).getV4())) {
                        PMPRODUCTLIST pp = LIST_PROMOTION.get(i);
                        LIST_PROMOTION.set(i, LIST_PROMOTION.get(j));
                        LIST_PROMOTION.set(j, pp);
                    }
                }
            }

            for (int k = 0; k < LIST_PROMOTION.size(); k++) {
                PMPRODUCTLIST pmproductlist = LIST_PROMOTION.get(k);
                String promotionCode = pmproductlist.getV1();
                Log.d(TAG, "PM_ID:" + promotionCode + " level:" + pmproductlist.getV4() + "  %:" + pmproductlist.getV17());
                String strPMBuy = "";
                String strPMGift = "";
                List<PMPRODUCTLIST> LIST = pmproductlist.getPMPRODUCTLIST();
                for (PMPRODUCTLIST b : LIST) {
                    if (b.getV1().trim().equals("BUY")) {
                        if (!strPMBuy.equals(""))
                            strPMBuy += (b.getV9().equals("0") ? " AND " : " OR ");
                        strPMBuy += b.getV8() + (b.getV7().trim().equals("1") ? "BX" : b.getV7().trim().equals("2") ? "CS" : "EA") +
                                " " + b.getV4() + " " + b.getV6();
                    } else {
                        if (!strPMGift.equals(""))
                            strPMGift += (b.getV9().trim().equals("0") ? " AND " : " OR ");
                        strPMGift += b.getV8() + (b.getV7().trim().equals("1") ? "BX" : b.getV7().trim().equals("2") ? "CS" : "EA") + " " + b.getV12();
                    }
                }
                PromotionInfor promotionPieObject = new PromotionInfor(pmproductlist.getV2(), pmproductlist.getV3(), strPMBuy + " => " + strPMGift,
                        pmproductlist, pmproductlist.getV4(), pmproductlist.getV17());
                arrayList.add(promotionPieObject);
            }
        }


//        for (int i = 0; i < pmList.getPMLIST().size(); i++) {
//            PMPRODUCTLIST pmproductlist = pmList.getPMLIST().get(i);
//            String PM_LEVEL = pmproductlist.getV4();
//            String GIFTAMTPERCENT = pmproductlist.getV17();
//            String ID_PROMOTION = pmproductlist.getV1();
//            if (checkContains(pmproductlist, MainActivity.TPRDCD)) {
//                String buy = "", gift = "";
//                List<PMPRODUCTLIST> aList = pmproductlist.getPMPRODUCTLIST();
//                String TPRDCD = "";
//                String PRDCLS1NM = "";
//                for (int k = 0; k < aList.size(); k++) {
//                    PMPRODUCTLIST ppd = aList.get(k);
//                    TPRDCD = ppd.getV5();
////                    Log.d(TAG, "TPRDCD:" + TPRDCD + " ID_PROMOTION:" + ID_PROMOTION + " K:" + k);
//                    if (ppd.getV1().equals("BUY") && TPRDCD.equals(MainActivity.TPRDCD)) {
//                        PRDCLS1NM = ppd.getV4();
//                        String UNIT = "";
//                        if (ppd.getV7().equals("1"))
//                            UNIT = "Box";
//                        else if (ppd.getV7().equals("2"))
//                            UNIT = "CASE";
//                        else if (ppd.getV7().equals("3"))
//                            UNIT = "EA";
//                        if (buy.length() == 0) {
//                            buy += ppd.getV8() + " " + UNIT + " " + ppd.getV4() + " " + ppd.getV6();
//                        } else {
//                            String CONDITION = "";
//                            if (ppd.getV9().equals("0"))
//                                CONDITION = " + ";
//                            else
//                                CONDITION = " or ";
//                            buy += CONDITION + ppd.getV8() + " " + UNIT + " " + ppd.getV4() + " " + ppd.getV6();
//                        }
//                    } else if (ppd.getV1().equals("GIFT")) {
//                        String UNIT = "";
//                        if (ppd.getV7().equals("1"))
//                            UNIT = "Box";
//                        else if (ppd.getV7().equals("2"))
//                            UNIT = "CASE";
//                        else if (ppd.getV7().equals("3"))
//                            UNIT = "EA";
//                        if (gift.length() == 0) {
//                            gift += ppd.getV8() + " " + UNIT + " " + ppd.getV4() + " " + ppd.getV12();
//                        } else {
//                            String CONDITION = "";
//                            if (ppd.getV9().equals("0"))
//                                CONDITION = " + ";
//                            else
//                                CONDITION = " or ";
//                            String result = ppd.getV8() + " " + UNIT + " " + ppd.getV4() + " " + ppd.getV12();
//                            if (!gift.contains(result))
//                                gift += CONDITION + result;
//                        }
//                    }
//
//                }
//                String content = buy + " => " + gift;
//                Log.d(TAG, "content:" + content);
//                PromotionInfor promotionPieObject = new PromotionInfor(pmproductlist.getV2(), pmproductlist.getV3(), content, pmproductlist, PM_LEVEL, GIFTAMTPERCENT);
//                arrayList.add(promotionPieObject);
//            }
//        }

//        if (arrayList.size() == 1) {
//            Log.d(TAG, "TPRDCD:" + MainActivity.TPRDCD);
//            PromotionInfor infor = arrayList.get(0);
//            PMPRODUCTLIST pmproductlist = infor.getPmproductlist();
//            String PM_LEVEL = pmproductlist.getV4();
//            Log.d(TAG, "START PM_LEVEL:" + PM_LEVEL);
//        }
//        Log.d(TAG, "arrayList:" + arrayList.size());
//        if (arrayList != null && arrayList.size() > 1) {
//            for (int i = 0; i < arrayList.size(); i++) {
//                Log.d(TAG, "TPRDCD:" + MainActivity.TPRDCD);
//                PromotionInfor infor = arrayList.get(i);
//                PMPRODUCTLIST pmproductlist = infor.getPmproductlist();
//                String PM_LEVEL = pmproductlist.getV4();
//                Log.d(TAG, "START PM_LEVEL:" + PM_LEVEL);
//                if (PM_LEVEL.equals("1")) {
//                    String nonShop = "";
//                    if (pmproductlist.getV7().equalsIgnoreCase("Y"))
//                        nonShop = "3";
//                    else if (pmproductlist.getV6().equalsIgnoreCase("Y"))
//                        nonShop = "2";
//                    else if (pmproductlist.getV5().equalsIgnoreCase("Y"))
//                        nonShop = "1";
//                    Log.d(TAG, "nonShop:" + nonShop);
////                    Log.d(TAG, "I:" + i + " CHECK:" + get_API_122(MainActivity.SHOP_ID, MainActivity.TPRDCD, nonShop));
//                    if (!get_API_122(MainActivity.SHOP_ID, MainActivity.TPRDCD, nonShop)) {
//                        arrayList.remove(i);
//                        i--;
//                        Log.d(TAG, "PM_LEVEL:" + PM_LEVEL + " FALSE" + " ID:" + pmproductlist.getV1() + " TPRDCD:" + MainActivity.TPRDCD);
//                    } else {
//                        Log.d(TAG, "PM_LEVEL:" + PM_LEVEL + " TRUE" + " ID:" + pmproductlist.getV1() + " TPRDCD:" + MainActivity.TPRDCD);
//                    }
//                }
//                if (PM_LEVEL.equals("2")) {
//                    Log.d(TAG, "PM_LEVEL:" + PM_LEVEL + " TRUE" + " ID:" + pmproductlist.getV1() + " TPRDCD:" + MainActivity.TPRDCD);
//                    if (!Const.getDBNewShop(LIST_PMT, MainActivity.SHOP_ID)) {
//                        arrayList.remove(i);
//                        i--;
//                    }
//                }
//                if (PM_LEVEL.equals("3")) {
//                    String CHANNEL = pmproductlist.getV9();
//                    if (!CHANNEL.equalsIgnoreCase(MainActivity.CHANNEL)) {
//                        arrayList.remove(i);
//                        i--;
//                        Log.d(TAG, "PM_LEVEL:" + PM_LEVEL + " FALSE");
//                    } else {
//                        Log.d(TAG, "PM_LEVEL:" + PM_LEVEL + " TRUE");
//                    }
//                }
//                if (PM_LEVEL.equals("4")) {
//                    boolean checkPR = false;
//                    if (MainActivity.SHOP_GRADE.equalsIgnoreCase("A")) {
//                        if (pmproductlist.getV10().equalsIgnoreCase("Y"))
//                            if (MainActivity.AREA.equals("1")) {
//                                if (pmproductlist.getV14().equals("Y"))
//                                    checkPR = true;
//                            } else if (MainActivity.AREA.equals("2")) {
//                                if (pmproductlist.getV15().equals("Y"))
//                                    checkPR = true;
//                            } else if (MainActivity.AREA.equals("3")) {
//                                if (pmproductlist.getV16().equals("Y"))
//                                    checkPR = true;
//                            }
//                    } else if (MainActivity.SHOP_GRADE.equalsIgnoreCase("B")) {
//                        if (pmproductlist.getV11().equalsIgnoreCase("Y"))
//                            if (MainActivity.AREA.equals("1")) {
//                                if (pmproductlist.getV14().equals("Y"))
//                                    checkPR = true;
//                            } else if (MainActivity.AREA.equals("2")) {
//                                if (pmproductlist.getV15().equals("Y"))
//                                    checkPR = true;
//                            } else if (MainActivity.AREA.equals("3")) {
//                                if (pmproductlist.getV16().equals("Y"))
//                                    checkPR = true;
//                            }
//                    } else if (MainActivity.SHOP_GRADE.equalsIgnoreCase("C")) {
//                        if (pmproductlist.getV12().equalsIgnoreCase("Y"))
//                            if (MainActivity.AREA.equals("1")) {
//                                if (pmproductlist.getV14().equals("Y"))
//                                    checkPR = true;
//                            } else if (MainActivity.AREA.equals("2")) {
//                                if (pmproductlist.getV15().equals("Y"))
//                                    checkPR = true;
//                            } else if (MainActivity.AREA.equals("3")) {
//                                if (pmproductlist.getV16().equals("Y"))
//                                    checkPR = true;
//                            }
//                    } else if (MainActivity.SHOP_GRADE.equalsIgnoreCase("D")) {
//                        if (pmproductlist.getV13().equalsIgnoreCase("Y"))
//                            if (MainActivity.AREA.equals("1")) {
//                                if (pmproductlist.getV14().equals("Y"))
//                                    checkPR = true;
//                            } else if (MainActivity.AREA.equals("2")) {
//                                if (pmproductlist.getV15().equals("Y"))
//                                    checkPR = true;
//                            } else if (MainActivity.AREA.equals("3")) {
//                                if (pmproductlist.getV16().equals("Y"))
//                                    checkPR = true;
//                            }
//                    }
//                    if (!checkPR) {
//                        arrayList.remove(i);
//                        i--;
//                    }
//                }
//            }
//            for (int i = 0; i < arrayList.size(); i++) {
//                for (int j = 0; j <= i; j++) {
////                        if (Float.parseFloat(productList.get(i).getGIFTAMTPERCENT()) <= Float.parseFloat(productList.get(j).getGIFTAMTPERCENT())) {
//                    if (Float.parseFloat(arrayList.get(i).getGIFTAMTPERCENT()) >= Float.parseFloat(arrayList.get(j).getGIFTAMTPERCENT())) {
//                        PromotionInfor pp = arrayList.get(i);
//                        arrayList.set(i, arrayList.get(j));
//                        arrayList.set(j, pp);
//                    }
//                }
//            }
//            for (int i = 0; i < arrayList.size(); i++) {
//                for (int j = 0; j <= i; j++) {
//                    if (Float.parseFloat(arrayList.get(i).getPM_LEVEL()) < Float.parseFloat(arrayList.get(j).getPM_LEVEL())) {
////                        if (Float.parseFloat(productList.get(i).getGIFTAMTPERCENT()) >= Float.parseFloat(productList.get(j).getGIFTAMTPERCENT())) {
//                        PromotionInfor pp = arrayList.get(i);
//                        arrayList.set(i, arrayList.get(j));
//                        arrayList.set(j, pp);
//                    }
//                }
//            }
//            for (int i = 0; i < arrayList.size(); i++) {
//                Log.d(TAG, "GIFTAMTPERCENT" + arrayList.get(i).getGIFTAMTPERCENT() + " LEVEL:" + arrayList.get(i).getPM_LEVEL() + " ID:" + arrayList.get(i).getPmproductlist().getV1());
//            }
//        }
    }

    List<NewShopPromotionList> LIST_PMT = new ArrayList<>();

    public void createRC_1() {
        initDB();
        mAdapter = new PromotionInforAdapter(this, arrayList);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
    }

    TextView tvName, tvAddress;
    String SHOP_NAME = "", SHOP_ADDRESS = "";
    LinearLayout lnTitle;
    ImageView onBackShop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.layout_promotion_item);
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.color_bar));
        }
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        OrderActivity.flag = 4;
        tvName = (TextView) findViewById(R.id.tvName);
        tvAddress = (TextView) findViewById(R.id.tvAddress);
        SHOP_NAME = getIntent().getStringExtra(Const.SHOP_NAME);
        SHOP_ADDRESS = getIntent().getStringExtra(Const.SHOP_ADDRESS);
        tvName.setText(SHOP_NAME);
        tvAddress.setText(SHOP_ADDRESS);
        lnTitle = (LinearLayout) findViewById(R.id.lnTitle);
        lnTitle.setPadding(MainActivity.widthTabLayout, 0, 0, 0);
        onBackShop = (ImageView) findViewById(R.id.onBackShop);
        onBackShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        DatabaseHandler db = new DatabaseHandler(this);

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
        createRC_1();
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

    List<PMPRODUCTLIST> LIST_PROMOTION = new ArrayList<>();

    void getDBPromotionList(String TPRDCD) {
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
    }
}
