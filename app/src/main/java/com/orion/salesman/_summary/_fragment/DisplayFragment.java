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
import com.orion.salesman._object.API_103;
import com.orion.salesman._object.CodeH;
import com.orion.salesman._object.DataLogin;
import com.orion.salesman._offline.OfflineObject;
import com.orion.salesman._pref.PrefManager;
import com.orion.salesman._result._fragment.ResultSalesFragment;
import com.orion.salesman._route._fragment.RouteFragment;
import com.orion.salesman._sqlite.DataBaseCodeH;
import com.orion.salesman._sqlite.DatabaseHandler;
import com.orion.salesman._summary._adapter.DataSummaryDisplay;
import com.orion.salesman._summary._adapter.DataSummarySales;
import com.orion.salesman._summary._adapter.DisplayAdapter;
import com.orion.salesman._summary._adapter.SalesReportAdapter;
import com.orion.salesman._summary._object.SalesReport;
import com.orion.salesman._summary._object.SummarySales_API;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import kr.co.eksys.nativelib.EksysNetworkException;
import kr.co.eksys.nativelib.NetworkManager;

/**
 * Created by maidinh on 5/9/2016.
 */
public class DisplayFragment extends Fragment {
    public static DisplayFragment instance = null;
    private String TAG = "DisplayFragment";
    private List<SalesReport> arrayList = new ArrayList<>();
    private RecyclerView recyclerView;
    private DisplayAdapter mAdapter;
    private String DATE;
    private DataLogin dataLogin;
    private int API;
    private ProgressBar progressbar;
    private TextView tvNodata, tvSumOne, tvSumTwo, tvSumThree, tvSumFour, tvFinish;
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
    public DisplayFragment(DataLogin dataLogin, int vitri, String DATE, int API) {
        this.dataLogin = dataLogin;
        this.vitri = vitri;
        this.DATE = DATE;
        this.API = API;
    }

    public DisplayFragment() {

    }


    SummarySales_API summarySales_api;

    public void getDataSumary(DataLogin dataLogin, String DATE) {
        this.DATE = DATE;
        if (arrayList != null)
            arrayList.clear();

        String MNGEMPID = dataLogin.getMNGEMPID();
        String contents = "";
        if (vitri == 2) {
            int check = 0;
            DatabaseHandler db = new DatabaseHandler(getActivity());
            List<API_103> api_103List = db.getListAPI_103();
            if (api_103List != null && api_103List.size() > 0) {
                if (Const.API_103_contains(api_103List, DATE)) {
                    for (int i = 0; i < api_103List.size(); i++) {
                        Log.d(TAG, api_103List.get(i).getDATE());
                        if (api_103List.get(i).getDATE().equalsIgnoreCase(DATE))
                            summarySales_api = new Gson().fromJson(api_103List.get(i).getDATA(), SummarySales_API.class);
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
//            if (check == 1 && Const.checkInternetConnection(getActivity())) {
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
                    String TEAM = dataLogin.getTEAM();
                    nm.setTR('A', Const.SummaryDisplay);
                    nm.connect(10);          // set timeout
                    DataSummaryDisplay ob = new DataSummaryDisplay(DATE, TEAM, MNGEMPID);
                    String data = new Gson().toJson(ob);
                    Const.WriteFileLog(dataLogin.getUSERID(), Const.SummaryDisplay, data);
                    Log.d(TAG, "data:" + data);
                    nm.send(data);// send body
                    contents = nm.receive();
                    summarySales_api = new Gson().fromJson(contents, SummarySales_API.class);
                    if (summarySales_api.getRESULT() == 0) {
                        arrayList = summarySales_api.getLIST();
                        API_103 offlineObject = new API_103(DATE, contents);
                        db.addContact_103(offlineObject);
                    } else {

                    }
                    Log.d(TAG, "DisplayFragment online: " + contents);
                } catch (EksysNetworkException e) {
                    Log.e(TAG, "DisplayFragment Error(" + e.getErrorCode() + "): " + e.toString());
                } finally {
                    nm.close();
                }
            }
        }
        if (arrayList != null && arrayList.size() > 0) {
            List<SalesReport> arr = new ArrayList<>();
            for (int i = 0; i < arrayList.size(); i++) {
                SalesReport ob = arrayList.get(i);
                if (Integer.parseInt(ob.getV4()) != 0) {
                    SalesReport salesReport = new SalesReport();
                    salesReport.setV2(ob.getV2() + "- SHOP");
                    salesReport.setV3("" + ob.getV4());
                    salesReport.setV5("" + ob.getV6());
                    salesReport.setV7("" + ob.getV8());
                    salesReport.setKIND("1");
                    arr.add(salesReport);
                }
            }
            for (SalesReport s : arr) {
                arrayList.add(s);
            }
        }

    }

    private final static int MSG_UPDATE_DISPLAY = 2;


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
        Log.e(TAG, "updateDate:" + s);
        PrefManager pref = new PrefManager(getActivity());
        boolean flag = pref.getSummary_4();
        pref.setSummary_4(false);
        if (arrayList == null)
            arrayList = new ArrayList<>();
        if (!DATE.equals(s) || arrayList.size() == 0 || flag) {
            Log.e(TAG, vitri + " DATE:" + DATE);
            DATE = s;
            tvDate.setText(Const.formatDate(DATE));
            reload();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    send(dataLogin, s);
                }
            }, 1000);
        }
    }


    int year;
    int month;
    int date;

    private void init(final View v) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1);
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH) + 1;
        date = calendar.get(Calendar.DATE);

        mAdapter = new DisplayAdapter(getActivity(), arrayList);
        recyclerView = (RecyclerView) v.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        tvNodata = (TextView) v.findViewById(R.id.tvNodata);
        progressbar = (ProgressBar) v.findViewById(R.id.progressbar);
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

    boolean removeItem(String codeKey) {
        for (CodeH obj : codeHList) {
            if (obj.getCODEKEY().equals(codeKey)) {
                if (obj.getCODEVALUE3().equals("Y"))
                    return true;
            }
        }
        return false;
    }

    public void initRC() {
        if (arrayList == null || arrayList.size() == 0) {
            Log.d(TAG, "null");
            ShowTVNodata();
        } else {
            Log.d(TAG, "else null");

            for (int i = 0; i < arrayList.size(); i++) {
                SalesReport obj = arrayList.get(i);
                if (!obj.getKIND().equals("1")) {
                    if (!removeItem(obj.getV1().trim())) {
                        arrayList.remove(i);
                        i--;
                        Log.d(TAG, "remove item");
                    }
                }

//                String[] lst = obj.getV2().trim().split(" ");
//                if (lst[lst.length - 1].trim().equalsIgnoreCase("FACE")) {
//                    arrayList.remove(i);
//                    i--;
//                    Log.d(TAG,"remove item");
//                }
            }


            mAdapter.update(arrayList);
            tvNodata.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            progressbar.setVisibility(View.GONE);
            Const.setTextColorSum(tvSumOne);
            Const.setTextColorSum(tvSumTwo);
            Const.setTextColorSum(tvSumThree);
            Const.setTextColorSum(tvSumFour);
        }
    }

    List<CodeH> codeHList = new ArrayList<>();

    class getDBCodeH extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... strings) {
            initDBCodeH();
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
    }

    void initDBCodeH() {
        DataBaseCodeH dataBaseCodeH = new DataBaseCodeH(getActivity());
        dataBaseCodeH.openDB();
        List<CodeH> lst = dataBaseCodeH.getData();
        if (lst != null && lst.size() > 0) {
            for (CodeH obj : lst) {
                if (obj.getCODEGROUP().trim().equalsIgnoreCase("TOOLKIND")) {
                    codeHList.add(obj);
                }
            }
        }

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_display_report, container, false);
        instance = this;
        new getDBCodeH().execute();
        init(v);
        return v;
    }

    public void send(final DataLogin dataLogin, final String DATE) {
        new Thread() {
            @Override
            public void run() {
                getDataSumary(dataLogin, DATE);
                handler.obtainMessage(MSG_UPDATE_DISPLAY, "").sendToTarget();
            }
        }.start();
    }


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