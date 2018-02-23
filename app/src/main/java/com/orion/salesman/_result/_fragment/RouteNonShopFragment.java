package com.orion.salesman._result._fragment;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.graphics.Color;
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
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ActionMenuView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.orion.salesman.MainActivity;
import com.orion.salesman.R;
import com.orion.salesman._adaper.CustomSpinnerNonShop;
import com.orion.salesman._app.MyApplication;
import com.orion.salesman._class.Const;
import com.orion.salesman._class.NonShopRow;
import com.orion.salesman._interface.DownloadDB;
import com.orion.salesman._interface.DownloadOrderNonShop;
import com.orion.salesman._object.API_112;
import com.orion.salesman._object.API_131;
import com.orion.salesman._object.API_132;
import com.orion.salesman._object.DATA_132;
import com.orion.salesman._object.DATA_API_131;
import com.orion.salesman._object.DataLogin;
import com.orion.salesman._object.ProductInfor;
import com.orion.salesman._object.list132;
import com.orion.salesman._offline.OfflineObject;
import com.orion.salesman._pref.PrefManager;
import com.orion.salesman._result._object.RouteNonShop;
import com.orion.salesman._route._adapter.RouteNonShopAdapter;
import com.orion.salesman._route._object.InforShopDetails;
import com.orion.salesman._route._object.ListInforShop;
import com.orion.salesman._route._object.getSMWorkingDayAPI;
import com.orion.salesman._route._object.getSMWorkingDayObject;
import com.orion.salesman._sqlite.DataBaseHelper_2;
import com.orion.salesman._sqlite.DatabaseHandler;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kr.co.eksys.nativelib.EksysNetworkException;
import kr.co.eksys.nativelib.NetworkManager;

/**
 * Created by maidinh on 5/8/2016.
 */
public class RouteNonShopFragment extends Fragment {
    private String TAG = "RouteNonShopFragment";
    private List<RouteNonShop> arrayList = new ArrayList<>();
    private RecyclerView recyclerView;
    private RouteNonShopAdapter mAdapter;
    private String DATE;
    private TextView tvNodata, tvSKUname;
    private ProgressBar progressbar;
    private Spinner spSKU;
    private List<ProductInfor> productInforList = new ArrayList<>();
    private List<ProductInfor> display = new ArrayList<>();
    private List<ProductInfor> listSKU = new ArrayList<>();
    private List<list132> list_PRDCLS1 = new ArrayList<>();
    private LinearLayout lnTitle, lnRoot;
    private CustomSpinnerNonShop adapter;
    private HorizontalScrollView svTitle, svContent;
    private TextView tvDate;
    private LinearLayout llDate;
    public static RouteNonShopFragment instance;

    public boolean checkListDisplay(List<ProductInfor> arrStrings, String s) {
        for (int i = 0; i < arrStrings.size(); i++) {
            if (arrStrings.get(i).getBRANDNM().equals(s))
                return true;
        }
        return false;
    }

    void initDB() {
        DataBaseHelper_2 db = new DataBaseHelper_2(getActivity());
        db.openDB();
        productInforList = db.getListProduct();
//        Collections.sort(productInforList, new Comparator<ProductInfor>() {
//            @Override
//            public int compare(ProductInfor addressSQLite, ProductInfor t1) {
//                return addressSQLite.getTPRDDSC().compareTo(t1.getTPRDDSC());
//            }
//        });
        for (ProductInfor pi : productInforList) {
            if (MainActivity.TEAM.equals(Const.PIE_TEAM)) {
                if (pi.getTEAM().equals("PTEAM") && !checkListDisplay(display, pi.getBRANDNM())) {
                    display.add(pi);
                }
            } else if (MainActivity.TEAM.equals(Const.GUM_TEAM)) {
                if (pi.getTEAM().equals("GTEAM") && !checkListDisplay(display, pi.getBRANDNM())) {
                    display.add(pi);
                }
            } else if (MainActivity.TEAM.equals(Const.SNACK_TEAM)) {
                if (pi.getTEAM().equals("STEAM") && !checkListDisplay(display, pi.getBRANDNM())) {
                    display.add(pi);
                }
            } else if (MainActivity.TEAM.equals(Const.MIX_TEAM)) {
                if (!checkListDisplay(display, pi.getBRANDNM())) {
                    display.add(pi);
                }
            }
        }
    }

    int year = 0, month = 0, date = 0;

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

    boolean flag = true;

    private void initView(View v) {
        initDB();
        findShop();
        getListWorkingDay();

        tvListNodata = (TextView) v.findViewById(R.id.tvListNodata);
        tvDate = (TextView) v.findViewById(R.id.tvDate);
        tvDate.setText(Const.formatDate(DATE));
        tvSKUname = (TextView) v.findViewById(R.id.tvSKUname);
        lnRoot = (LinearLayout) v.findViewById(R.id.lnRoot);
        lnTitle = (LinearLayout) v.findViewById(R.id.lnTitle);
        llDate = (LinearLayout) v.findViewById(R.id.llDate);
        llDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowDateDialog();
//                MyApplication.getInstance().showDialogDate(getActivity(), tvDate, 7);
            }
        });
        spSKU = (Spinner) v.findViewById(R.id.spSKU);
        progressbar = (ProgressBar) v.findViewById(R.id.progressbar);
        tvNodata = (TextView) v.findViewById(R.id.tvNodata);
        mAdapter = new RouteNonShopAdapter(getActivity(), arrayList, listAllShopInfor, listWorkingDay);
        recyclerView = (RecyclerView) v.findViewById(R.id.rcNonShop);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        recyclerView.setAdapter(mAdapter);
        recyclerView.setNestedScrollingEnabled(false);

        swipeRefreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.swipeRefreshLayout);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // update data here
                flag = false;
                send(DATE);
            }
        });

        svTitle = (HorizontalScrollView) v.findViewById(R.id.svTitle);
        svContent = (HorizontalScrollView) v.findViewById(R.id.svContent);
        svTitle.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View view, int i, int i1, int i2, int i3) {
                svContent.scrollTo(svTitle.getScrollX(), svTitle.getScrollY());
            }
        });
        svContent.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View view, int i, int i1, int i2, int i3) {
                svTitle.scrollTo(svContent.getScrollX(), svContent.getScrollY());
            }
        });

    }

    List<getSMWorkingDayObject> listWorkingDay = new ArrayList<>();

    public void getListWorkingDay() {
        String s = new PrefManager(getActivity()).getSmWorkDay();
        if (s.length() > 0) {
            OfflineObject ob = new Gson().fromJson(s, OfflineObject.class);
            getSMWorkingDayAPI routeList = new Gson().fromJson(ob.getDATA(), getSMWorkingDayAPI.class);
            if (routeList.getRESULT() == 0) {
                listWorkingDay = routeList.getLIST();
            }
        }
    }

    List<InforShopDetails> listAllShopInfor = new ArrayList<>();

    public void findShop() {
//        Log.d(TAG, "findShop");
        DatabaseHandler db = new DatabaseHandler(getActivity());
        List<API_112> listAPI_112 = db.getAllContacts();
        for (int i = 0; i < listAPI_112.size(); i++) {
            API_112 obj = listAPI_112.get(i);
//            Log.d(TAG, obj.getDATE());
            if (Const.getToday().equals(obj.getDATE())) {
                ListInforShop inforShop = new Gson().fromJson(obj.getDATA(), ListInforShop.class);
                listAllShopInfor = inforShop.getLIST();
                break;
            }
        }
    }


    String name = "";

    void initSP() {
        adapter = new CustomSpinnerNonShop(getActivity(), display);
        spSKU.setAdapter(adapter);
        spSKU.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                name = display.get(position).getBRANDNM();
                tvSKUname.setText(name);
//                new loadAPI_132(display.get(position).getPRDCLS1()).execute();
                if (display != null && display.size() > 0)
                    send_132(display.get(position).getPRDCLS1());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @SuppressLint("ValidFragment")
    public RouteNonShopFragment(String DATE) {
        this.DATE = DATE;
    }

    public RouteNonShopFragment() {

    }

    public void updateDate(final String s) {
        PrefManager pref = new PrefManager(getActivity());
        boolean flag = pref.getResult_3();
        pref.setResult_3(false);
        if (arrayList == null) arrayList = new ArrayList<>();
        if (!DATE.equals(s) || arrayList.size() == 0 || flag) {
            Log.d(TAG, "updateDate");
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

    TextView tvListNodata;
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.nonshop_fragment, container, false);
        instance = this;

        initView(v);
        return v;
    }

    public void startLoad() {
        lnRoot.removeAllViews();
        tvNodata.setVisibility(View.GONE);
        recyclerView.setVisibility(View.GONE);
        if (flag)
            progressbar.setVisibility(View.VISIBLE);
    }

    public void endLoad() {
        tvListNodata.setText(getResources().getString(R.string.nodata));
        if (contents_List.length() == 0) {
            tvListNodata.setText(getResources().getString(R.string.cannotconnect));
        } else {
            if (contents_List.startsWith("Error")) {
                tvListNodata.setText(contents_List);
            } else {

            }
        }
        progressbar.setVisibility(View.GONE);
        if (arrayList == null || arrayList.size() == 0) {
            tvNodata.setVisibility(View.VISIBLE);
            tvListNodata.setVisibility(View.VISIBLE);
        } else {
            tvListNodata.setVisibility(View.GONE);
            mAdapter.updateList(arrayList);
            recyclerView.setVisibility(View.VISIBLE);
        }
    }

    public void send(final String time) {
        tvListNodata.setVisibility(View.GONE);
        Log.d(TAG, "start send");
        startLoad();
        new Thread() {
            @Override
            public void run() {
                callAPI(time);
                handler.obtainMessage(MSG_UPDATE_131_2, "").sendToTarget();
            }
        }.start();
    }


    private void send_132(final String s) {
        progressbar.setVisibility(View.VISIBLE);
        lnRoot.removeAllViews();
        new Thread() {
            @Override
            public void run() {
                API_132(s);
            }
        }.start();
    }

    DATA_API_131 data_api_131;
    String contents_List = "";

    void callAPI(String time) {
        contents_List = "";
        this.DATE = time;
        if (arrayList != null)
            arrayList.clear();
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
                nm.setTR('A', Const.API_1311);
                nm.connect(10);// set timeout
                Map<String, Object> map = new HashMap<>();
                map.put("DATE", DATE);

                String data = new Gson().toJson(map);
                Log.d(TAG, "param:" + data);
                Const.WriteFileLog(dataLogin.getUSERID(), Const.API_1311, data);
                nm.send(data);
                contents_List = nm.receive();
                data_api_131 = new Gson().fromJson(contents_List, DATA_API_131.class);
                if (data_api_131.getRESULT() == 0) {
                    arrayList = data_api_131.getLIST();
                    if (arrayList == null)
                        arrayList = new ArrayList<>();
//                    API_131 api_131 = new API_131(DATE, contents);
//                    db.addContact_131(api_131);
                } else {
                    Log.d(TAG, "API_131 fail");
                }
                Log.d(TAG, "API_131 online: " + contents_List);
//            Const.longInfo("getRoute",contents);
            } catch (EksysNetworkException e) {
                contents_List = "Error(" + e.getErrorCode() + "): " + e.toString();
                Log.e(TAG, "API_131 Error(" + e.getErrorCode() + "): " + e.toString());
            } finally {
                nm.close();
            }
        } else {
            contents_List = "";
        }

    }

    private final static int MSG_UPDATE_131_2 = 3;

    private final static int MSG_UPDATE_132 = 2;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case MSG_UPDATE_131_2:
                    endLoad();
                    initSP();
                    break;
                case MSG_UPDATE_132:
                    handlerUI();
                    break;
            }
        }
    };

    public boolean API_132_contains(List<API_132> arr, String s, String PRDCLS1) {
        // true : data off
        // false: data online
        for (API_132 p : arr) {
            if (p.getDATE().equalsIgnoreCase(s) && p.getPRDCLS1().equals(PRDCLS1))
                return true;
        }
        return false;
    }


    public boolean checkListSKU(List<ProductInfor> arrStrings, String s) {
        for (int i = 0; i < arrStrings.size(); i++) {
            if (arrStrings.get(i).getTPRDDSC().equals(s))
                return true;
        }
        return false;
    }


    void TextStyle(TextView v, String colorBG, String colorText) {
        v.setGravity(Gravity.CENTER);
        v.setBackgroundColor(Color.parseColor(colorBG));
        v.setTextColor(Color.parseColor(colorText));
    }


    void handlerUI() {
//        if (contents_132.length() == 0) {
//
//        }

        listSKU.clear();
        for (ProductInfor pi : productInforList) {
            if (name.equals(pi.getBRANDNM()) && !checkListSKU(listSKU, pi.getTPRDDSC()))
                listSKU.add(pi);
        }
        int width;
        if (listSKU.size() >= 4)
            width = (int) MainActivity.width * 70 / 100 / 4;
        else width = (int) MainActivity.width * 70 / 100 / listSKU.size();
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, (int) getResources().getDimension(R.dimen.size_29dp));
        params.setMargins(0, -1, 0, -1);
        lnTitle.removeAllViews();
        for (int i = 0; i < listSKU.size(); i++) {
            TextView button = new TextView(getActivity());
            TextStyle(button, "#e94545", "#FFFFFF");
            button.setText(listSKU.get(i).getTPRDDSC());
            button.setLayoutParams(params);
            lnTitle.addView(button);
        }
//        lnRoot.addView(lnTitle);
        if (list_PRDCLS1 != null && list_PRDCLS1.size() > 0) {
            for (int i = 0; i < arrayList.size(); i++) {
                final String SHOP_ID = arrayList.get(i).getV1();
                NonShopRow nonShopRow = new NonShopRow(getActivity(), SHOP_ID, listSKU, list_PRDCLS1);
                lnRoot.addView(nonShopRow);
            }
        } else {
            list_PRDCLS1 = new ArrayList<>();
            for (int i = 0; i < arrayList.size(); i++) {
                final String SHOP_ID = arrayList.get(i).getV1();
                NonShopRow nonShopRow = new NonShopRow(getActivity(), SHOP_ID, listSKU, list_PRDCLS1);
                lnRoot.addView(nonShopRow);
            }

//            TextView tv = new TextView(getActivity());
//            tv.setGravity(Gravity.CENTER_HORIZONTAL);
//            tv.setText(getResources().getString(R.string.nodata));
//            lnRoot.addView(tv);
        }
        progressbar.setVisibility(View.GONE);
        flag = true;
        if (swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    String contents_132 = "";

    void API_132(String PRDCLS1) {
        list_PRDCLS1.clear();
//        int check = 0;
        contents_132 = "";
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
                nm.setTR('A', Const.API_132);
                nm.connect(10);// set timeout
                Map<String, Object> map = new HashMap<>();
                map.put("DATE", DATE);
                map.put("PRDCLS1", PRDCLS1);
                String data = new Gson().toJson(map);
                Log.d(TAG, data);
                Const.WriteFileLog(dataLogin.getUSERID(), Const.API_132, data);
                Log.d(TAG, "data:" + data);
                nm.send(data);
                contents_132 = nm.receive();
                DATA_132 data_132 = new Gson().fromJson(contents_132, DATA_132.class);
                if (data_132.getRESULT() == 0) {
                    list_PRDCLS1 = data_132.getLIST();
                }
                Log.d(TAG, "API_132: " + contents_132);
//            Const.longInfo("getRoute",contents);
            } catch (EksysNetworkException e) {
                contents_132 = "Error(" + e.getErrorCode() + ")";
                Log.e(TAG, "API_132 Error(" + e.getErrorCode() + "): " + e.toString());
            } finally {
                nm.close();
            }
        }
        handler.obtainMessage(MSG_UPDATE_132, contents_132).sendToTarget();
    }
}