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
import com.google.gson.reflect.TypeToken;
import com.orion.salesman.MainActivity;
import com.orion.salesman.R;
import com.orion.salesman._app.MyApplication;
import com.orion.salesman._class.Const;
import com.orion.salesman._class.GeneralSwipeRefreshLayout;
import com.orion.salesman._object.API_100;
import com.orion.salesman._object.DataLogin;
import com.orion.salesman._offline.OfflineObject;
import com.orion.salesman._pref.PrefManager;
import com.orion.salesman._result._adapter.ResultSalesAdapter;
import com.orion.salesman._result._object.RouteSales;
import com.orion.salesman._route._adapter.RouteSalesAdapter;
import com.orion.salesman._sqlite.DatabaseHandler;
import com.orion.salesman._summary._adapter.DataSummaryDisplay;
import com.orion.salesman._summary._adapter.DataSummarySales;
import com.orion.salesman._summary._adapter.SalesReportAdapter;
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
 * Created by maidinh on 5/8/2016.
 */
public class ResultSalesFragment extends Fragment {
    public static ResultSalesFragment instance = null;
    private String TAG = "ResultSalesFragment";
    private List<SalesReport> arrayList = new ArrayList<>();
    private RecyclerView recyclerView;
    private ResultSalesAdapter mAdapter;
    private String DATE;
    private DataLogin dataLogin;
    GeneralSwipeRefreshLayout swipeRefreshLayout;
    private ProgressBar progressbar;
    private TextView tvNodata, tvSumOne, tvSumTwo, tvSumThree, tvSumFour, tvFinish, tvSumToday, tvSumFive, tvSumSix, tvSumSeven, tvSumEight;
    private LinearLayout lnFinish;
    /*
   * vitri:0 -> sales report
   * vitri:1 -> sales shop
   * vitri:2 -> display
   * */
    private TextView tvDate;
    private LinearLayout llDate, lnTotal;

    @SuppressLint("ValidFragment")
    public ResultSalesFragment(String DATE) {
        this.DATE = DATE;
    }

    public ResultSalesFragment() {

    }

    public void updateDate(final String s) {

        PrefManager pref = new PrefManager(getActivity());
        boolean flag = pref.getResult_1();
        pref.setResult_1(false);
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
    }

    SummarySales_API summarySales_api = null;
    String status = "0";

    public void getDataSumary(String time) {
        status = "0";
        Log.d(TAG, "TIME:" + time);
        this.DATE = time;
        if (arrayList != null)
            arrayList.clear();


        String MNGEMPID = dataLogin.getMNGEMPID();
        String contents = "";
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
                nm.setTR('A', Const.A0133);
                nm.connect(10);          // set timeout
                Map<String, Object> map = new HashMap<>();
                map.put("DATE", DATE);
                map.put("MNGEMPID", MNGEMPID);
                String data = new Gson().toJson(map);
                Log.d(TAG, "PARAM A0133:" + data);
                Const.WriteFileLog(dataLogin.getUSERID(), Const.A0133, data);
                nm.send(data);// send body
                contents = nm.receive();
                summarySales_api = new Gson().fromJson(contents, SummarySales_API.class);
                if (summarySales_api.getRESULT() == 0) {
                    arrayList = summarySales_api.getLIST();
                    status = "0";
                } else {
                    status = "1";
                }
                Const.longInfo(TAG, "A0133 online: " + contents);
            } catch (EksysNetworkException e) {
                status = "Error(" + e.getErrorCode() + "): " + e.toString();
                Log.e(TAG, "Error(" + e.getErrorCode() + "): " + e.toString());
            } finally {
                nm.close();
            }
        } else {
            status = "2";
        }
    }

    boolean flag = true;

    void reload() {
        recyclerView.setVisibility(View.GONE);
        tvNodata.setVisibility(View.GONE);
        if (flag)
            progressbar.setVisibility(View.VISIBLE);
        lnTotal.setVisibility(View.GONE);
    }

    public void ShowTVNodata() {
        recyclerView.setVisibility(View.GONE);
        tvNodata.setVisibility(View.VISIBLE);
        progressbar.setVisibility(View.GONE);
        lnTotal.setVisibility(View.GONE);
    }

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

    int year;
    int month;
    int date;
    LinearLayoutManager linearLayoutManager;

    public void ScrollEndList(int Size) {
        recyclerView.smoothScrollToPosition(Size);
    }

    private void init(final View v) {

        ResultSalesFragment rsf = this;
        mAdapter = new ResultSalesAdapter(getActivity(), arrayList, rsf);
        recyclerView = (RecyclerView) v.findViewById(R.id.recycler_view);
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
        tvNodata = (TextView) v.findViewById(R.id.tvNodata);
        progressbar = (ProgressBar) v.findViewById(R.id.progressbar);
        llDate = (LinearLayout) v.findViewById(R.id.llDate);
        lnTotal = (LinearLayout) v.findViewById(R.id.lnTotal);
        lnFinish = (LinearLayout) v.findViewById(R.id.lnFinish);
        tvSumOne = (TextView) v.findViewById(R.id.tvSumOne);
        tvSumTwo = (TextView) v.findViewById(R.id.tvSumTwo);

        tvSumToday = (TextView) v.findViewById(R.id.tvSumToday);
        tvSumThree = (TextView) v.findViewById(R.id.tvSumThree);
        tvSumFour = (TextView) v.findViewById(R.id.tvSumFour);


        tvSumFive = (TextView) v.findViewById(R.id.tvSumFive);
        tvSumSix = (TextView) v.findViewById(R.id.tvSumSix);
        tvSumSeven = (TextView) v.findViewById(R.id.tvSumSeven);
        tvSumEight = (TextView) v.findViewById(R.id.tvSumEight);

        tvDate = (TextView) v.findViewById(R.id.tvDate);
        tvDate.setText(Const.formatDate(DATE));
        tvFinish = (TextView) v.findViewById(R.id.tvFinish);
        llDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowDateDialog();
//                MyApplication.getInstance().showDialogDate(getActivity(), tvDate, 5);
            }
        });
    }

    boolean isScrolling = false;

    public void initRC() {
        flag = true;
        if (swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(false);
        }
        if (summarySales_api != null) {
            tvFinish.setText(Const.RoundNumber(summarySales_api.getFINISH()) + " %");
            lnFinish.setVisibility(View.VISIBLE);
        }
        mAdapter.updateReceiptsList(arrayList);


        if (arrayList == null || arrayList.size() == 0) {
            ShowTVNodata();
        } else {
            tvNodata.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            progressbar.setVisibility(View.GONE);


            Const.setTextColorSum(tvSumOne);
            Const.setTextColorSum(tvSumTwo);
            Const.setTextColorSum(tvSumThree);
            Const.setTextColorSum(tvSumFour);
            Const.setTextColorSum(tvSumToday);


            Const.setTextColorSum(tvSumFive);
            Const.setTextColorSum(tvSumSix);
            Const.setTextColorSum(tvSumSeven);
            Const.setTextColorSum(tvSumEight);


            Const.setColorSum(tvSumOne);
            Const.setColorSum(tvSumTwo);
            Const.setColorSum(tvSumThree);
            Const.setColorSum(tvSumFour);
            Const.setColorSum(tvSumToday);


            Const.setColorSum(tvSumFive);
            Const.setColorSum(tvSumSix);
            Const.setColorSum(tvSumSeven);
            Const.setColorSum(tvSumEight);

            lnTotal.setVisibility(View.VISIBLE);
            SalesReport movie = arrayList.get(arrayList.size() - 1);
            tvSumToday.setText(Const.RoundNumber(movie.getV9()));
            tvSumTwo.setText(Const.RoundNumber(movie.getV6()));


            tvSumThree.setText(Const.RoundNumber(movie.getV5()));
            float TGT = Float.parseFloat(movie.getV7());
            TGT = (float) Math.round(TGT * 10) / 10;
            tvSumFour.setText(TGT + "%");

            tvSumFive.setText("" + Const.routeTGT(movie.getV13()));
            tvSumSix.setText("" + Const.routeTGT(movie.getV14()));
            tvSumSeven.setText("" + Const.routeTGT(movie.getV15()));
            tvSumEight.setText("" + Const.routeTGT(movie.getV16()) + "%");
        }
        if (status.startsWith("Error")) {
            tvNodata.setText(status);
        } else if (status.equals("2")) {
            tvNodata.setText(getResources().getString(R.string.cannotconnect));
        } else {
            tvNodata.setText(getResources().getString(R.string.nodata));
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.result_fragment_sales_report, container, false);
        instance = this;

        String s = new PrefManager(getActivity()).getInfoLogin();
        dataLogin = new Gson().fromJson(s, DataLogin.class);
        init(v);
        return v;
    }


    public void send(final String time) {
        reload();

        new Thread() {
            @Override
            public void run() {
                getDataSumary(time);
                handler.obtainMessage(MSG_UPDATE_DISPLAY, "").sendToTarget();
            }
        }.start();
    }

    private final static int MSG_UPDATE_DISPLAY = 2;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case MSG_UPDATE_DISPLAY:
                    initRC();
                    break;
            }
        }
    };
}