package com.orion.salesman._result._fragment;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
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
import com.orion.salesman.MainActivity;
import com.orion.salesman.R;
import com.orion.salesman._app.MyApplication;
import com.orion.salesman._class.Const;
import com.orion.salesman._class.GeneralSwipeRefreshLayout;
import com.orion.salesman._object.API_130;
import com.orion.salesman._object.DataLogin;
import com.orion.salesman._pref.PrefManager;
import com.orion.salesman._result._object.BRANDLIST_OBJECT;
import com.orion.salesman._result._object.DATA_API_130;
import com.orion.salesman._result._object.RouteSalesShop;
import com.orion.salesman._result._object.SKULIST_OBJECT;
import com.orion.salesman._route._adapter.RouteSalesShopAdapter;
import com.orion.salesman._sqlite.DatabaseHandler;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kr.co.eksys.nativelib.EksysNetworkException;
import kr.co.eksys.nativelib.NetworkManager;

/**
 * Created by maidinh on 5/8/2016.
 */
public class RouteSalesShopFragment extends Fragment {
    private String TAG = "RouteSalesShopFragment";
    private List<RouteSalesShop> arrayList = new ArrayList<>();
    private RecyclerView recyclerView;
    private RouteSalesShopAdapter mAdapter;
    private String DATE = "";
    private ProgressBar progressbar;
    private TextView tvFinish, T1, T2, T3, T4, T5, T6, T7, T8, tvDate;
    private View viewTotal;
    public static RouteSalesShopFragment instance = null;
    private LinearLayout lnFinish, llDate;
    private TextView tvNodata;
    int year = 0, month = 0, date = 0;
    GeneralSwipeRefreshLayout swipeRefreshLayout;

    public void ShowDateDialog() {
        try {
            year = Integer.parseInt(MainActivity.REPORT_TODAY.substring(0, 4));
            month = Integer.parseInt(MainActivity.REPORT_TODAY.substring(4, 6));
            date = Integer.parseInt(MainActivity.REPORT_TODAY.substring(6, 8));
        } catch (Exception e) {
            Calendar calendar = Calendar.getInstance();
            year = calendar.get(Calendar.YEAR);
            month = calendar.get(Calendar.MONTH) + 1;
            date = calendar.get(Calendar.DATE);
            e.printStackTrace();
        }

        DatePickerDialog dpd = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int y, int monthOfYear, int dayOfMonth) {

                year = y;
                month = monthOfYear + 1;
                date = dayOfMonth;
                tvDate.setText(year + "-" + Const.setFullDate(month) + "-" + Const.setFullDate(date));
                MainActivity.REPORT_TODAY = year + Const.setFullDate(month) + Const.setFullDate(date);
                Log.d(TAG, MainActivity.REPORT_TODAY);
                send(MainActivity.REPORT_TODAY);
            }
        }, year, month - 1, date);
        dpd.show();
    }

    LinearLayoutManager linearLayoutManager;

    public void ScrollEndList(int Size) {
        recyclerView.smoothScrollToPosition(Size);
    }
    boolean isScrolling = false;
    public void initView(View v) {



        progressbar = (ProgressBar) v.findViewById(R.id.progressbar);
        tvFinish = (TextView) v.findViewById(R.id.tvFinish);
        tvNodata = (TextView) v.findViewById(R.id.tvNodata);
        lnFinish = (LinearLayout) v.findViewById(R.id.lnFinish);
        tvDate = (TextView) v.findViewById(R.id.tvDate);
        tvDate.setText(Const.formatDate(DATE));
        llDate = (LinearLayout) v.findViewById(R.id.llDate);
        llDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowDateDialog();
//                MyApplication.getInstance().showDialogDate(getActivity(), tvDate, 6);
            }
        });
        T1 = (TextView) v.findViewById(R.id.tvOne);
        T2 = (TextView) v.findViewById(R.id.tvTwo);
        T3 = (TextView) v.findViewById(R.id.tvThree);
        T4 = (TextView) v.findViewById(R.id.tvFour);
        T5 = (TextView) v.findViewById(R.id.tvFive);
        T6 = (TextView) v.findViewById(R.id.tvSix);
        T7 = (TextView) v.findViewById(R.id.tvSeven);
        T8 = (TextView) v.findViewById(R.id.tvEight);
        Const.setTextColorSum(T1);
        Const.setTextColorSum(T2);
        Const.setTextColorSum(T3);
        Const.setTextColorSum(T4);
        Const.setTextColorSum(T5);
        Const.setTextColorSum(T6);
        Const.setTextColorSum(T7);
        Const.setTextColorSum(T8);
        viewTotal = (View) v.findViewById(R.id.viewTotal);
        viewTotal.setVisibility(View.GONE);
        RouteSalesShopFragment fragment = this;
        mAdapter = new RouteSalesShopAdapter(getActivity(), arrayList, fragment);
        recyclerView = (RecyclerView) v.findViewById(R.id.rcSalesShop);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(mAdapter);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                isScrolling = false;
//                Log.d(TAG, "onScrollStateChanged");
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                isScrolling = true;
//                Log.d(TAG, "onScrolled");
            }
        });
        swipeRefreshLayout = (GeneralSwipeRefreshLayout) v.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnChildScrollUpListener(new GeneralSwipeRefreshLayout.OnChildScrollUpListener() {
            @Override
            public boolean canChildScrollUp() {
//                return linearLayoutManager.findFirstVisibleItemPosition() > 0 ||
//                        linearLayoutManager.getChildAt(0) == null ||
//                        linearLayoutManager.getChildAt(0).getTop() < 0;
                if (!isScrolling) {
                    return linearLayoutManager.findFirstVisibleItemPosition() > 0 ||
                            linearLayoutManager.getChildAt(0) == null ||
                            linearLayoutManager.getChildAt(0).getTop() < 0;
                } else {
                    return true;
                }
            }
        });
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // update data here
                flag = false;
                send(DATE);
            }
        });
    }

    @SuppressLint("ValidFragment")
    public RouteSalesShopFragment(String DATE) {
        this.DATE = DATE;
    }

    public RouteSalesShopFragment() {

    }

    public void updateDate(final String s) {
        PrefManager pref = new PrefManager(getActivity());
        boolean flag = pref.getResult_2();
        pref.setResult_2(false);
        if (arrayList == null) arrayList = new ArrayList<>();
        if (!DATE.equals(s) || arrayList.size() == 0 || flag) {
            DATE = s;
            tvDate.setText(Const.formatDate(DATE));

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    send(s);
                }
            }, 1000);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.route_sales_shop_fragment, container, false);
        instance = this;
        initView(v);
        return v;
    }

    boolean flag = true;

    void startCallAPI() {
        viewTotal.setVisibility(View.GONE);
        tvNodata.setVisibility(View.GONE);
        if (flag)
            progressbar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
    }

    void callAPIFinish() {
        flag = true;
        tvNodata.setVisibility(View.GONE);
        progressbar.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
        if (swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    void updateList() {
        if (data_api_130 != null) {
            int RESULT = data_api_130.getRESULT();
//            Const.longInfo(TAG, new Gson().toJson(data_api_130));
            tvFinish.setText("= " + data_api_130.getTTLSHOP());
            lnFinish.setVisibility(View.VISIBLE);
            if (RESULT == 0) {
                status = "0";
                arrayList.clear();
                List<SKULIST_OBJECT> SKULIST = data_api_130.getSKULIST();
                List<BRANDLIST_OBJECT> BRANDLIST = data_api_130.getBRANDLIST();

                if (BRANDLIST.size() > 0) {

                    BRANDLIST_OBJECT ob = BRANDLIST.get(BRANDLIST.size() - 1);
                    Log.d(TAG, "ob:" + new Gson().toJson(ob));
                    viewTotal.setVisibility(View.VISIBLE);
//            T1.setText(ob.getV1());
                    T2.setText(ob.getV3());
                    T3.setText(ob.getV4());
                    T4.setText(Const.RoundNumber(ob.getV10()) + "%");
                    T5.setText(ob.getV5());
                    T6.setText(Const.RoundNumber(ob.getV6()) + "%");
                    T7.setText(ob.getV7());
                    T8.setText(Const.RoundNumber(ob.getV8()) + "%");
                    Const.setColorSum(T1);
                    Const.setColorSum(T2);
                    Const.setColorSum(T3);
                    Const.setColorSum(T4);
                    Const.setColorSum(T5);
                    Const.setColorSum(T6);
                    Const.setColorSum(T7);
                    Const.setColorSum(T8);
                } else {
                    viewTotal.setVisibility(View.GONE);
                }

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
                }
                for (int i = 0; i < arrayList.size() - 1; i++) {
                    String PRDCLS1 = arrayList.get(i).getV1();
                    List<RouteSalesShop> arr = new ArrayList<>();
                    for (int k = 0; k < SKULIST.size(); k++) {
                        SKULIST_OBJECT brandlist_object = SKULIST.get(k);
                        if (PRDCLS1.equals(brandlist_object.getV2()) && !brandlist_object.getV3().equals("T")) {
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
                mAdapter.updateReceiptsList(arrayList);
            } else {
                status = "1";
                tvNodata.setVisibility(View.VISIBLE);
            }
        } else {
            tvNodata.setVisibility(View.VISIBLE);
        }
        if (status.startsWith("Error")) {
            tvNodata.setText(status);
        } else if (status.equals("2")) {
            tvNodata.setText(getResources().getString(R.string.cannotconnect));
        } else {
            tvNodata.setText(getResources().getString(R.string.nodata));
        }
    }

    private DATA_API_130 data_api_130;
    String status = "0";

    void callAPI(String time) {
        status = "0";
        Log.d(TAG, "TIME:" + time);
        this.DATE = time;
        if (arrayList != null)
            arrayList.clear();
        String contents = "";
        String s = new PrefManager(getActivity()).getInfoLogin();
        DataLogin dataLogin = new Gson().fromJson(s, DataLogin.class);
        String idLogin = dataLogin.getUSERID() + "";
        if ((Const.checkInternetConnection(getActivity())
                && Const.PHONENUMBER.length() > 0
                && idLogin.trim().length() > 0
                && dataLogin.getDEPTCD().trim().length() > 0
                && dataLogin.getJOBCODE().trim().length() > 0)
                || (Const.checkInternetConnection(getActivity()) && dataLogin.getJOBGRADE().trim().equals(Const.SE))) {
            NetworkManager nm = new NetworkManager(Const.IP, Const.PORT);
            nm.setTerminalInfo(Const.PHONENUMBER, idLogin, dataLogin.getDEPTCD(), dataLogin.getJOBCODE());
            try {
                nm.setTR('A', Const.API_130);
                nm.connect(10);// set timeout
//            RouteShopStockAPI ob = new RouteShopStockAPI("" + DAY_OF_WEEK);
                Map<String, Object> map = new HashMap<>();
                map.put("DATE", DATE);
                String data = new Gson().toJson(map);
                Const.WriteFileLog(dataLogin.getUSERID(), Const.API_130, data);
                Log.d(TAG,"data:"+data);
                nm.send(data);
                contents = nm.receive();
                data_api_130 = new Gson().fromJson(contents, DATA_API_130.class);
                Const.longInfo(TAG, "API_130 online: " + contents);
            } catch (EksysNetworkException e) {
                status = "Error(" + e.getErrorCode() + "): " + e.toString();
                Log.e(TAG, "API_130 Error(" + e.getErrorCode() + "): " + e.toString());
            } finally {
                nm.close();
            }
        } else {
            status = "2";
        }


    }

    private final static int MSG_UPDATE_DISPLAY = 2;

    public void send(final String time) {
        startCallAPI();
        new Thread() {
            @Override
            public void run() {
                callAPI(time);
                handler.obtainMessage(MSG_UPDATE_DISPLAY, "").sendToTarget();
            }
        }.start();
    }


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
