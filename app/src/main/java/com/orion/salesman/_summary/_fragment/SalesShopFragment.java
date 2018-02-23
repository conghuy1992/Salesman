package com.orion.salesman._summary._fragment;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.orion.salesman.MainActivity;
import com.orion.salesman.R;
import com.orion.salesman._app.MyApplication;
import com.orion.salesman._class.Const;
import com.orion.salesman._object.API_130;
import com.orion.salesman._object.DataLogin;
import com.orion.salesman._offline.OfflineObject;
import com.orion.salesman._pref.PrefManager;
import com.orion.salesman._result._fragment.RouteSalesShopFragment;
import com.orion.salesman._result._object.BRANDLIST_OBJECT;
import com.orion.salesman._result._object.DATA_API_130;
import com.orion.salesman._result._object.RouteSalesShop;
import com.orion.salesman._result._object.SKULIST_OBJECT;
import com.orion.salesman._route._adapter.RouteSalesShopAdapter;
import com.orion.salesman._sqlite.DatabaseHandler;
import com.orion.salesman._summary._adapter.DataSummaryDisplay;
import com.orion.salesman._summary._adapter.DataSummarySales;
import com.orion.salesman._summary._adapter.SalesReportAdapter;
import com.orion.salesman._summary._adapter.SalesShopFragmentAdapter;
import com.orion.salesman._summary._object.Delivery;
import com.orion.salesman._summary._object.SalesReport;
import com.orion.salesman._summary._object.SummarySales_API;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kr.co.eksys.nativelib.EksysNetworkException;
import kr.co.eksys.nativelib.NetworkManager;

/**
 * Created by maidinh on 5/9/2016.
 */
public class SalesShopFragment extends Fragment {
    public static SalesShopFragment instance = null;
    private String TAG = "SalesShopFragment";
    private List<RouteSalesShop> arrayList = new ArrayList<>();
    private RecyclerView recyclerView;
    private SalesShopFragmentAdapter mAdapter;
    private String DATE = "";
    private ProgressBar progressbar;
    private TextView tvFinish, T1, T2, T3, T4, tvDate, tvNodata;
    private LinearLayout lnBottom, llDate;

    public void initView(View v) {
//        Calendar calendar = Calendar.getInstance();
//        calendar.add(Calendar.DATE, -1);
//        int YEAR = calendar.get(Calendar.YEAR);
//        int MONTH = calendar.get(Calendar.MONTH) + 1;
//        int DAY_OF_MONTH = calendar.get(Calendar.DAY_OF_MONTH);
//        DATE = YEAR + Const.setFullDate(MONTH) + Const.setFullDate(DAY_OF_MONTH);
        progressbar = (ProgressBar) v.findViewById(R.id.progressbar);
        tvNodata = (TextView) v.findViewById(R.id.tvNodata);
        tvFinish = (TextView) v.findViewById(R.id.tvFinish);
        llDate = (LinearLayout) v.findViewById(R.id.llDate);
        llDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyApplication.getInstance().showDialogDate(getActivity(), tvDate, 1);
            }
        });
        tvDate = (TextView) v.findViewById(R.id.tvDate);
        tvDate.setText(Const.formatDate(DATE));
        T1 = (TextView) v.findViewById(R.id.tvOne);
        T2 = (TextView) v.findViewById(R.id.tvTwo);
        T3 = (TextView) v.findViewById(R.id.tvThree);
        T4 = (TextView) v.findViewById(R.id.tvFour);
        Const.setColorSum(T1);
        Const.setColorSum(T2);
        Const.setColorSum(T3);
        Const.setColorSum(T4);
        Const.setTextColorSum(T1);
        Const.setTextColorSum(T2);
        Const.setTextColorSum(T3);
        Const.setTextColorSum(T4);
        lnBottom = (LinearLayout) v.findViewById(R.id.lnBottom);
        SalesShopFragment fm = this;
        mAdapter = new SalesShopFragmentAdapter(getActivity(), arrayList, TTL, fm);
        recyclerView = (RecyclerView) v.findViewById(R.id.rcSalesShop);
        layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
//        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
    }

    LinearLayoutManager layoutManager;

    public void ScrollEndList(int Size) {
        recyclerView.smoothScrollToPosition(Size);
    }

    public void reload() {
        tvNodata.setVisibility(View.GONE);
        progressbar.setVisibility(View.VISIBLE);
        lnBottom.setVisibility(View.GONE);
        recyclerView.setVisibility(View.GONE);
    }

    public void updateDate(final String s) {
        PrefManager pref = new PrefManager(getActivity());
        boolean flag = pref.getSummary_2();
        pref.setSummary_2(false);
        if (arrayList == null)
            arrayList = new ArrayList<>();
        if (!DATE.equals(s) || arrayList.size() == 0 || flag) {
            DATE = s;
            tvDate.setText(Const.formatDate(DATE));
            reload();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    send(s);
                }
            }, 1000);
        }
//        tvDate.setText(Const.formatDate(s));
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                send(s);
//            }
//        }, 1000);
    }

    public SalesShopFragment() {

    }

    @SuppressLint("ValidFragment")
    public SalesShopFragment(String DATE) {
        this.DATE = DATE;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.summary_sales_shop_fragment, container, false);
        instance = this;
        initView(v);
//        send(DATE);
//        send_FIRST(DATE);
        return v;
    }


    void startCallAPI() {
        progressbar.setVisibility(View.VISIBLE);
        tvNodata.setVisibility(View.GONE);
        recyclerView.setVisibility(View.GONE);
    }

    void callAPIFinish() {
        progressbar.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);

    }

    private String TTL = "";

    void updateList() {
        if (data_api_130 != null) {
//            Log.d(TAG, "API:" + new Gson().toJson(data_api_130));
            int RESULT = data_api_130.getRESULT();
//            tvFinish.setText("= " + data_api_130.getTTLSHOP());
            TTL = data_api_130.getTTLSHOP();
            if (RESULT == 0) {
                arrayList.clear();
                List<SKULIST_OBJECT> SKULIST = data_api_130.getSKULIST();
                List<BRANDLIST_OBJECT> BRANDLIST = data_api_130.getBRANDLIST();
                for (int i = 0; i < BRANDLIST.size(); i++) {
                    BRANDLIST_OBJECT ob = BRANDLIST.get(i);
                    RouteSalesShop routeSalesShop = new RouteSalesShop();
                    routeSalesShop.setV1(ob.getV2());
                    routeSalesShop.setV2(ob.getV3());
                    routeSalesShop.setV3(ob.getV4());
                    routeSalesShop.setV4(ob.getV10());
                    routeSalesShop.setV5(ob.getV5());
                    routeSalesShop.setV6(ob.getV6());
                    routeSalesShop.setV7(ob.getV7());
                    routeSalesShop.setV8(ob.getV8());
                    arrayList.add(routeSalesShop);
                    if (i == BRANDLIST.size() - 1) {
                        T1.setText(getResources().getString(R.string.Sum));
                        T2.setText(TTL);
                        T3.setText(ob.getV5());
                        T4.setText(Const.RoundNumber(ob.getV6()));
                    }
                }

                for (int i = 0; i < arrayList.size() - 1; i++) {
                    String PRDCLS1 = arrayList.get(i).getV1();
//                    Log.e(TAG, "PRDCLS1:" + PRDCLS1);
                    List<RouteSalesShop> arr = new ArrayList<>();
                    for (int k = 0; k < SKULIST.size(); k++) {
                        SKULIST_OBJECT brandlist_object = SKULIST.get(k);
//                        Log.e(TAG, brandlist_object.getV2() + " - " + brandlist_object.getV3());
                        if (PRDCLS1.equals(brandlist_object.getV2()) && !brandlist_object.getV3().equals("T")) {
//                            Log.e(TAG, "add " + brandlist_object.getV1() + " - " + brandlist_object.getV3());
                            RouteSalesShop routeSalesShop = new RouteSalesShop();
                            routeSalesShop.setV1(brandlist_object.getV4());
                            routeSalesShop.setV2(brandlist_object.getV5());
                            routeSalesShop.setV3(brandlist_object.getV6());
                            routeSalesShop.setV4(brandlist_object.getV12());
                            routeSalesShop.setV5(brandlist_object.getV7());
                            routeSalesShop.setV6(brandlist_object.getV8());
                            routeSalesShop.setV7(brandlist_object.getV9());
                            routeSalesShop.setV8(brandlist_object.getV10());
                            arr.add(routeSalesShop);
                        }
                    }
                    arrayList.get(i).setArrSalesShops(arr);
                }
                mAdapter.updateReceiptsList(arrayList, TTL);
//                mAdapter.notifyDataSetChanged();
            }
        }
        if (arrayList.size() > 0) {
            lnBottom.setVisibility(View.VISIBLE);
            RouteSalesShop ob = arrayList.get(arrayList.size() - 1);
            Log.d(TAG, "ob:" + new Gson().toJson(ob));
//            T1.setText(getResources().getString(R.string.Sum));
//            T2.setText(TTL);
//            T3.setText(ob.getV5());
//            T4.setText(Const.RoundNumber(ob.getV6()));
        } else {
            tvNodata.setVisibility(View.VISIBLE);
            lnBottom.setVisibility(View.GONE);
        }
    }

    private DATA_API_130 data_api_130;

    public void send(final String DATE) {
//        startCallAPI();
        new Thread() {
            @Override
            public void run() {
                callAPI(DATE);
                handler.obtainMessage(MSG_UPDATE_DISPLAY, "").sendToTarget();
            }
        }.start();
    }


    void callAPI(String DATE) {
        this.DATE = DATE;
        int check = 0;
        String contents = "";
        DatabaseHandler db = new DatabaseHandler(getActivity());
        List<API_130> api_130List = db.getListAPI_130();
        Log.d(TAG, new Gson().toJson(api_130List));
        if (api_130List != null && api_130List.size() > 0) {
            if (Const.API_130_contains(api_130List, DATE)) {
                for (int i = 0; i < api_130List.size(); i++) {
                    if (DATE.equals(api_130List.get(i).getDATE())) {
                        data_api_130 = new Gson().fromJson(api_130List.get(i).getDATA(), DATA_API_130.class);
                        break;
                    }
                }
                Log.d(TAG, "offline");
            } else {
                check = 1;
            }
        } else {
            check = 1;
        }
        String idLogin = MainActivity.dataLogin.getUSERID() + "";
        if ((check == 1 && Const.checkInternetConnection(getActivity())
                && Const.PHONENUMBER.length() > 0
                && idLogin.trim().length() > 0
                && MainActivity.dataLogin.getDEPTCD().trim().length() > 0
                && MainActivity.dataLogin.getJOBCODE().trim().length() > 0)
                || (Const.checkInternetConnection(getActivity()) && MainActivity.dataLogin.getJOBGRADE().trim().equals(Const.SE))) {
            NetworkManager nm = new NetworkManager(Const.IP, Const.PORT);
            nm.setTerminalInfo(Const.PHONENUMBER, idLogin, MainActivity.dataLogin.getDEPTCD(), MainActivity.dataLogin.getJOBCODE());
            try {
                nm.setTR('A', Const.API_130);
                nm.connect(10);// set timeout
                Map<String, Object> map = new HashMap<>();
                map.put("DATE", DATE);
                String data = new Gson().toJson(map);
                Const.WriteFileLog(MainActivity.dataLogin.getUSERID(), Const.API_130, data);
                nm.send(data);
                contents = nm.receive();
                data_api_130 = new Gson().fromJson(contents, DATA_API_130.class);
                if (data_api_130.getRESULT() == 0) {
                    API_130 api_130 = new API_130(DATE, contents);
                    db.addContact_130(api_130);
                }
//                Log.d(TAG, "API_130: " + contents);
                Const.longInfo("getRoute", contents);
            } catch (EksysNetworkException e) {
                contents = "API_130 Error(" + e.getErrorCode() + ")";
                Log.e(TAG, "API_130 Error(" + e.getErrorCode() + "): " + e.toString());
            } finally {
                nm.close();
            }
        }

    }

    private final static int MSG_UPDATE_DISPLAY = 2;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case MSG_UPDATE_DISPLAY:
                    callAPIFinish();
                    updateList();
                    break;
            }
        }
    };
}
