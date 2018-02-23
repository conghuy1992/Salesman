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
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.orion.salesman.MainActivity;
import com.orion.salesman.R;
import com.orion.salesman._app.MyApplication;
import com.orion.salesman._class.Const;
import com.orion.salesman._object.API_100;
import com.orion.salesman._object.CheckUpdate;
import com.orion.salesman._object.DataLogin;
import com.orion.salesman._pref.PrefManager;
import com.orion.salesman._result._fragment.ResultSalesFragment;
import com.orion.salesman._sqlite.DatabaseHandler;
import com.orion.salesman._summary._adapter.DataSummaryDisplay;
import com.orion.salesman._summary._adapter.DataSummarySales;
import com.orion.salesman._summary._adapter.SalesReportAdapter;
import com.orion.salesman._summary._object.SalesReport;
import com.orion.salesman._summary._object.SummarySales_API;

import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kr.co.eksys.nativelib.EksysNetworkException;
import kr.co.eksys.nativelib.NetworkManager;

public class SalesReportFragment extends Fragment {
    public static SalesReportFragment instance = null;
    private String TAG = "SalesReportFragment";
    private List<SalesReport> arrayList = new ArrayList<>();
    private RecyclerView recyclerView;
    private SalesReportAdapter mAdapter;
    private String DATE = "";
    private DataLogin dataLogin;
    private int API;
    private ProgressBar progressbar;
    private TextView tvNodata, tvSumOne, tvSumTwo, tvSumThree, tvSumFour, tvSumFive, tvFinish;
    private LinearLayout lnFinish;
    /*
   * vitri:0 -> sales report
   * vitri:1 -> sales shop
   * vitri:2 -> display
   * */
    private int vitri;
    private TextView tvOne, tvTwo, tvThree, tvFour, tvDate;
    private LinearLayout llDate, lnTotal;

    @SuppressLint("ValidFragment")
    public SalesReportFragment(DataLogin dataLogin, int vitri, String DATE, int API) {
        this.dataLogin = dataLogin;
        this.vitri = vitri;
        this.DATE = DATE;
        this.API = API;
    }

    public SalesReportFragment() {

    }


    public void send_100(final DataLogin dataLogin, final String DATE) {
        new Thread() {
            @Override
            public void run() {
                getDataSumary(dataLogin, DATE);
                handler.obtainMessage(MSG_UPDATE_100, "").sendToTarget();
            }
        }.start();
    }


    SummarySales_API summarySales_api = null;

    public void getDataSumary(DataLogin dataLogin, String DATE) {
        this.DATE = DATE;
        if (arrayList != null)
            arrayList.clear();

        String MNGEMPID = dataLogin.getMNGEMPID();
        String contents = "";
        int check = 0;
        DatabaseHandler db = new DatabaseHandler(getActivity());
        List<API_100> api_100List = db.getListAPI_100();
        if (api_100List != null && api_100List.size() > 0) {
            if (Const.API_100_contains(api_100List, DATE)) {
                for (int i = 0; i < api_100List.size(); i++) {
                    Log.d(TAG, api_100List.get(i).getDATE());
                    if (api_100List.get(i).getDATE().equalsIgnoreCase(DATE))
                        summarySales_api = new Gson().fromJson(api_100List.get(i).getDATA(), SummarySales_API.class);
                }
                if (summarySales_api.getRESULT() == 0) {
                    arrayList = summarySales_api.getLIST();

                } else {

                }
                Log.d(TAG, " offline");
            } else {
                check = 1;
            }
        } else {
            check = 1;
        }
//        if (check == 1 && Const.checkInternetConnection(getActivity())) {
        String idLogin = dataLogin.getUSERID() + "";
        if ((check == 1 && Const.checkInternetConnection(getActivity())
                && Const.PHONENUMBER.length() > 0
                && idLogin.trim().length() > 0
                && dataLogin.getDEPTCD().trim().length() > 0
                && dataLogin.getJOBCODE().trim().length() > 0)
                || (Const.checkInternetConnection(getActivity()) && dataLogin.getJOBGRADE().trim().equals(Const.SE))) {
            NetworkManager nm = new NetworkManager(Const.IP, Const.PORT);
            nm.setTerminalInfo(Const.PHONENUMBER, idLogin, dataLogin.getDEPTCD(), dataLogin.getJOBCODE());
            try {
                nm.setTR('A', Const.SummarySales);
                nm.connect(10);          // set timeout
                Map<String, Object> map = new HashMap<>();
                map.put("DATE", DATE);
                map.put("MNGEMPID", MNGEMPID);
                String data = new Gson().toJson(map);
                Log.d(TAG, "PARAM 100:" + data);
                Const.WriteFileLog(dataLogin.getUSERID(), Const.SummarySales, data);
                nm.send(data);// send body
                contents = nm.receive();
                summarySales_api = new Gson().fromJson(contents, SummarySales_API.class);
                if (summarySales_api.getRESULT() == 0) {
                    arrayList = summarySales_api.getLIST();
                    API_100 offlineObject = new API_100(DATE, contents);
                    db.addContact_100(offlineObject);
                } else {

                }
                Const.longInfo(TAG, "SummarySales online: " + contents);
            } catch (EksysNetworkException e) {
                Log.e(TAG, "SummarySales Error(" + e.getErrorCode() + "): " + e.toString());
            } finally {
                nm.close();
            }
        }
        arrChild.clear();
        arrUpdate.clear();
        if (arrayList != null && arrayList.size() > 0) {

            List<SalesReport> temp = new ArrayList<>();
            if (arrayList.size() > 0) {
                for (int i = 0; i < arrayList.size(); i++) {
                    SalesReport ob = arrayList.get(i);
                    int ISGROUP = Integer.parseInt(ob.getV8());
                    if (ISGROUP == 1) {
                        arrUpdate.add(ob);
                        arrChild.add(new Gson().toJson(temp));
                        temp = new ArrayList<>();
                    } else if (ISGROUP == 0) {
                        temp.add(ob);
                    }
                }
                arrUpdate.remove(arrUpdate.size() - 1);
            }
        }
    }

    List<String> arrChild = new ArrayList<>();
    List<SalesReport> arrUpdate = new ArrayList<>();

    private final static int MSG_UPDATE_100 = 1;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case MSG_UPDATE_100:
                    initRC();
                    break;
            }
        }
    };

    public void reload() {
        recyclerView.setVisibility(View.GONE);
        tvNodata.setVisibility(View.GONE);
        progressbar.setVisibility(View.VISIBLE);
        lnTotal.setVisibility(View.GONE);
    }

    public void ShowTVNodata() {
        recyclerView.setVisibility(View.GONE);
        tvNodata.setVisibility(View.VISIBLE);
        progressbar.setVisibility(View.GONE);
        lnTotal.setVisibility(View.GONE);
    }


    public void updateDate(final String s) {
//        Log.e(TAG, "updateDate:" + s);
        PrefManager pref = new PrefManager(getActivity());
        boolean flag = pref.getSummary_1();
        pref.setSummary_1(false);
        if (arrayList == null)
            arrayList = new ArrayList<>();
        if (!DATE.equals(s) || arrayList.size() == 0 || flag) {
            Log.d(TAG, vitri + " DATE:" + DATE);
            DATE = s;
            tvDate.setText(Const.formatDate(DATE));
            reload();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    send_100(dataLogin, s);
                }
            }, 1000);
        }
    }


    int year;
    int month;
    int date;
    LinearLayoutManager layoutManager;

    public void ScrollEndList(int Size) {
        recyclerView.smoothScrollToPosition(Size);
    }

    private void init(final View v) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1);
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH) + 1;
        date = calendar.get(Calendar.DATE);
        SalesReportFragment fm = this;
        mAdapter = new SalesReportAdapter(getActivity(), arrayList, arrChild, fm);
        recyclerView = (RecyclerView) v.findViewById(R.id.recycler_view);
        layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        tvNodata = (TextView) v.findViewById(R.id.tvNodata);

        llDate = (LinearLayout) v.findViewById(R.id.llDate);
        lnTotal = (LinearLayout) v.findViewById(R.id.lnTotal);
        llDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                ShowDateDialog(v);
                MyApplication.getInstance().showDialogDate(getActivity(), tvDate, vitri);
            }
        });
        lnFinish = (LinearLayout) v.findViewById(R.id.lnFinish);
        tvSumOne = (TextView) v.findViewById(R.id.tvSumOne);
        tvSumTwo = (TextView) v.findViewById(R.id.tvSumTwo);
        tvSumThree = (TextView) v.findViewById(R.id.tvSumThree);
        tvSumFour = (TextView) v.findViewById(R.id.tvSumFour);
        tvSumFive = (TextView) v.findViewById(R.id.tvSumFive);
        tvDate = (TextView) v.findViewById(R.id.tvDate);
        tvDate.setText(Const.formatDate(DATE));
        tvOne = (TextView) v.findViewById(R.id.tvOne);
        tvTwo = (TextView) v.findViewById(R.id.tvTwo);
        tvThree = (TextView) v.findViewById(R.id.tvThree);
        tvFour = (TextView) v.findViewById(R.id.tvFour);
        tvFinish = (TextView) v.findViewById(R.id.tvFinish);
        if (vitri == 1) {
            tvTwo.setText(getResources().getString(R.string.totalshop));
            tvThree.setText(getResources().getString(R.string.salesshop));
        } else if (vitri == 2) {
            tvOne.setText(getResources().getString(R.string.item));
            tvTwo.setText(getResources().getString(R.string.targer));
            tvThree.setText(getResources().getString(R.string.act));
        }
    }

    public void initRC() {
        if (summarySales_api != null) {
            tvFinish.setText(Const.RoundNumber(summarySales_api.getFINISH()) + " %");
            lnFinish.setVisibility(View.VISIBLE);
        }
//        mAdapter.notifyDataSetChanged();
        if (arrUpdate == null || arrUpdate.size() == 0) {
            ShowTVNodata();
        } else {
            progressbar.setVisibility(View.GONE);
            tvNodata.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            mAdapter.updateReceiptsList(arrUpdate, arrChild);
            Const.setTextColorSum(tvSumOne);
            Const.setTextColorSum(tvSumTwo);
            Const.setTextColorSum(tvSumThree);
            Const.setTextColorSum(tvSumFour);
            Const.setTextColorSum(tvSumFive);


            Const.setColorSum(tvSumOne);
            Const.setColorSum(tvSumTwo);
            Const.setColorSum(tvSumThree);
            Const.setColorSum(tvSumFour);
            Const.setColorSum(tvSumFive);
            lnTotal.setVisibility(View.VISIBLE);
            SalesReport movie = arrayList.get(arrayList.size() - 1);
            tvSumTwo.setText(Const.RoundNumber(movie.getV6()));
            tvSumThree.setText(Const.RoundNumber(movie.getV5()));
            float TGT = Float.parseFloat(movie.getV7());
            TGT = (float) Math.round(TGT * 10) / 10;
            tvSumFour.setText(TGT + "%");

            float TGT_TODAY = Float.parseFloat(movie.getV12());
            TGT_TODAY = (float) Math.round(TGT_TODAY * 10) / 10;
            tvSumFive.setText("" + TGT_TODAY);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    void checkLoad() {
        Calendar calendar = Calendar.getInstance();
        int date = calendar.get(Calendar.DAY_OF_MONTH);
        String s = new PrefManager(getActivity()).getCheckUpdate();
        String s2 = date + "";
        if (s.length() == 0) {
        } else {
            CheckUpdate ob = new Gson().fromJson(s, CheckUpdate.class);
            if (!ob.getDATE().equals(s2)) {
            } else {
                if (!ob.isCheck()) {
                } else {
                    SalesReportFragment.instance.updateDate(MainActivity.DATE_SYNC);
                }
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_sales_report, container, false);
        instance = this;
        if (dataLogin == null)
            dataLogin = new Gson().fromJson(new PrefManager(getActivity()).getInfoLogin(), DataLogin.class);

        progressbar = (ProgressBar) v.findViewById(R.id.progressbar);
        init(v);
        checkLoad();
        return v;
    }
}