package com.orion.salesman._summary._fragment;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.graphics.Color;
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
import com.orion.salesman._object.API_104;
import com.orion.salesman._object.DataLogin;
import com.orion.salesman._offline.OfflineObject;
import com.orion.salesman._pref.PrefManager;
import com.orion.salesman._sqlite.DatabaseHandler;
import com.orion.salesman._summary._adapter.DataSummarySales;
import com.orion.salesman._summary._adapter.DeliveryAdapter;
import com.orion.salesman._summary._object.Delivery;
import com.orion.salesman._summary._object.SummaryDelivery;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import kr.co.eksys.nativelib.EksysNetworkException;
import kr.co.eksys.nativelib.NetworkManager;

public class DeliveryFragment extends Fragment {
    public static DeliveryFragment instance = null;
    private String TAG = "DeliveryFragment";
    private List<Delivery> arrayList = new ArrayList<>();
    private RecyclerView recyclerView;
    private DeliveryAdapter mAdapter;
    private TextView tvDate, tvOne, tvTwo, tvThree, tvFour, tvFive, tvSix;
    private LinearLayout llDate;
    private DataLogin dataLogin;
    private String DATE;
    private int API;
    private TextView tvNodata;
    private ProgressBar progressbar;
    private LinearLayout lnBottom;

    @SuppressLint("ValidFragment")
    public DeliveryFragment(DataLogin dataLogin, String DATE, int API) {
        this.dataLogin = dataLogin;
        this.DATE = DATE;
        this.API = API;
    }

    public DeliveryFragment() {
    }


    SummaryDelivery summarySales_api = null;

    public void getDataSummaryDelivery(DataLogin dataLogin, String DATE) {
        this.DATE = DATE;
        if (arrayList != null)
            arrayList.clear();
        DatabaseHandler db = new DatabaseHandler(getActivity());
        List<API_104> api_104List = db.getListAPI_104();
        int check = 0;
        if (api_104List != null && api_104List.size() > 0) {
            if (Const.API_104_contains(api_104List, DATE)) {
                for (int i = 0; i < api_104List.size(); i++) {
                    Log.d(TAG, api_104List.get(i).getDATE());
                    if (api_104List.get(i).getDATE().equalsIgnoreCase(DATE))
                        summarySales_api = new Gson().fromJson(api_104List.get(i).getDATA(), SummaryDelivery.class);
                }
                if (summarySales_api.getRESULT() == 0) {
                    arrayList = summarySales_api.getLIST();

                } else {

                }
                Log.d(TAG, TAG + " offline");
            } else {
                check = 1;
            }
        } else {
            check = 1;
        }

        String idLogin = dataLogin.getUSERID() + "";
        if ((Const.checkInternetConnection(getActivity())
                && Const.PHONENUMBER.length() > 0
                && idLogin.trim().length() > 0
                && dataLogin.getDEPTCD().trim().length() > 0
                && dataLogin.getJOBCODE().trim().length() > 0)
                || (Const.checkInternetConnection(getActivity()) && dataLogin.getJOBGRADE().trim().equals(Const.SE))) {
            NetworkManager nm = new NetworkManager(Const.IP, Const.PORT);
            nm.setTerminalInfo(Const.PHONENUMBER, idLogin, dataLogin.getDEPTCD(), dataLogin.getJOBCODE());
            String contents = "";
            String MNGEMPID = dataLogin.getMNGEMPID();
            try {
                nm.setTR('A', Const.SummaryDelivery);
                nm.connect(10);          // set timeout
                DataSummarySales ob = new DataSummarySales(DATE, MNGEMPID);
                String data = new Gson().toJson(ob);
                Const.WriteFileLog(dataLogin.getUSERID(), Const.SummaryDelivery, data);
                Log.d(TAG,"data:"+data);
                nm.send(data);// send body
                contents = nm.receive();
                summarySales_api = new Gson().fromJson(contents, SummaryDelivery.class);
                if (summarySales_api.getRESULT() == 0) {
                    arrayList = summarySales_api.getLIST();
                    API_104 offlineObject = new API_104(DATE, contents);
                    db.addContact_104(offlineObject);
                } else {

                }
                Log.d(TAG, "getDataSummaryDelivery online: " + contents);
            } catch (EksysNetworkException e) {
                Log.e(TAG, "getDataSummaryDelivery Error(" + e.getErrorCode() + "): " + e.toString());
            } finally {
                nm.close();
            }
        }
        Log.e(TAG, "check:" + check);

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

    public void initRC() {
        mAdapter.updateReceiptsList(arrayList);
        if (arrayList == null || arrayList.size() == 0) {
            ShowTVNodata();
        } else {
            tvNodata.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            progressbar.setVisibility(View.GONE);
            lnBottom.setVisibility(View.VISIBLE);
            Delivery movie = arrayList.get(arrayList.size() - 1);
            tvTwo.setText(Const.RoundNumber(movie.getV3()));
            tvThree.setText(Const.RoundNumber(movie.getV4()));
            tvFour.setText(Const.RoundNumber(movie.getV5()));
            tvFive.setText(Const.RoundNumber(movie.getV6()));
            tvSix.setText(Const.RoundNumber(movie.getV7()));

            Const.setTextColorSum(tvOne);
            Const.setTextColorSum(tvTwo);
            Const.setTextColorSum(tvThree);
            Const.setTextColorSum(tvFour);
            Const.setTextColorSum(tvFive);
            Const.setTextColorSum(tvSix);

            Const.setColorSum(tvOne);
            Const.setColorSum(tvTwo);
            Const.setColorSum(tvThree);
            Const.setColorSum(tvFour);
            Const.setColorSum(tvFive);
            Const.setColorSum(tvSix);
        }
    }

    public void ShowTVNodata() {
        recyclerView.setVisibility(View.GONE);
        tvNodata.setVisibility(View.VISIBLE);
        progressbar.setVisibility(View.GONE);
        lnBottom.setVisibility(View.GONE);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }


    int year, month, date;

    public void reload() {
        tvNodata.setVisibility(View.GONE);
        progressbar.setVisibility(View.VISIBLE);
        lnBottom.setVisibility(View.GONE);
        recyclerView.setVisibility(View.GONE);
    }

    public void updateDate(final String s) {
        Log.e(TAG, "updateDate:" + s);
        PrefManager pref = new PrefManager(getActivity());
        boolean flag = pref.getSummary_3();
        pref.setSummary_3(false);
        if (arrayList == null)
            arrayList = new ArrayList<>();
        if (!DATE.equals(s) || arrayList.size() == 0 || flag) {
            Log.e(TAG, " DATE:" + DATE);
            DATE = s;
            tvDate.setText(Const.formatDate(DATE));
            reload();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    send(dataLogin, DATE);
                }
            }, 1000);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_delivery, container, false);
        instance = this;

        mAdapter = new DeliveryAdapter(getActivity(), arrayList);
        recyclerView = (RecyclerView) v.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        llDate = (LinearLayout) v.findViewById(R.id.llDate);
        llDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyApplication.getInstance().showDialogDate(getActivity(), tvDate, 3);
            }
        });
        tvNodata = (TextView) v.findViewById(R.id.tvNodata);
        progressbar = (ProgressBar) v.findViewById(R.id.progressbar);
        lnBottom = (LinearLayout) v.findViewById(R.id.lnBottom);
        tvDate = (TextView) v.findViewById(R.id.tvDate);
        tvOne = (TextView) v.findViewById(R.id.tvOne);
        tvTwo = (TextView) v.findViewById(R.id.tvTwo);
        tvThree = (TextView) v.findViewById(R.id.tvThree);
        tvFour = (TextView) v.findViewById(R.id.tvFour);
        tvFive = (TextView) v.findViewById(R.id.tvFive);
        tvSix = (TextView) v.findViewById(R.id.tvSix);
        tvDate.setText(Const.formatDate(DATE));
        return v;
    }

    public void send(final DataLogin dataLogin, final String DATE) {
        new Thread() {
            @Override
            public void run() {
                getDataSummaryDelivery(dataLogin, DATE);
                handler.obtainMessage(MSG_UPDATE_DISPLAY, "").sendToTarget();
            }
        }.start();
    }


}