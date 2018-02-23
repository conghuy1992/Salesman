package com.orion.salesman._result._fragment;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;
import com.orion.salesman.MainActivity;
import com.orion.salesman.R;
import com.orion.salesman._adaper.CustomSpinnerStock;
import com.orion.salesman._adaper.ViewPagerAdapter;
import com.orion.salesman._app.MyApplication;
import com.orion.salesman._class.Const;
import com.orion.salesman._class.CustomViewPager;
import com.orion.salesman._object.API_303;
import com.orion.salesman._object.API_304;
import com.orion.salesman._object.DATA_303;
import com.orion.salesman._object.LIST_303;
import com.orion.salesman._object.ListAPI_304;
import com.orion.salesman._object.Object_304;
import com.orion.salesman._pref.PrefManager;
import com.orion.salesman._result._object.ResultPie;
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
public class ResultStockFragment extends Fragment {
    public static ResultStockFragment instance;
    private TabLayout tabLayout;
    private CustomViewPager viewPager;
    String TAG = "ResultStockFragment";
    private String DATE = "";
    private String HIDEPTCD = "A301000000";
    private String DEALERID = "00000004";
    private Spinner spDealer;
    private List<Object_304> arrayList = new ArrayList<>();
    private LinearLayout llDate;
    private TextView tvDate;
    private ProgressBar progressbar;

    public ResultStockFragment() {

    }

    @SuppressLint("ValidFragment")
    public ResultStockFragment(String DATE) {
        this.DATE = DATE;
    }

    public void updateDate(final String s) {
        PrefManager pref = new PrefManager(getActivity());
        boolean flag = pref.getResult_4();
        pref.setResult_4(false);
        if (LIST == null) LIST = new ArrayList<>();
        if (!DATE.equals(s) || LIST.size() == 0 || flag) {
            DATE = s;
            tvDate.setText(Const.formatDate(DATE));

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    send_303(s);
                }
            }, 1000);
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
                send_303(MainActivity.REPORT_TODAY);
            }
        }, year, month - 1, date);
        dpd.show();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.result_stock_fragment, container, false);
//        Calendar calendar = Calendar.getInstance();
//        calendar.add(Calendar.DATE, -1);
//        int YEAR = calendar.get(Calendar.YEAR);
//        int MONTH = calendar.get(Calendar.MONTH) + 1;
//        int DAY_OF_MONTH = calendar.get(Calendar.DAY_OF_MONTH);
//        DATE = YEAR + Const.setFullDate(MONTH) + Const.setFullDate(DAY_OF_MONTH);
        instance = this;
        llDate = (LinearLayout) v.findViewById(R.id.llDate);
        tvDate = (TextView) v.findViewById(R.id.tvDate);
        tvDate.setText(Const.formatDate(DATE));
        llDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                MyApplication.getInstance().showDialogDate(getActivity(), tvDate, 8);
                ShowDateDialog();
            }
        });
        progressbar = (ProgressBar) v.findViewById(R.id.progressbar);
        viewPager = (CustomViewPager) v.findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        tabLayout = (TabLayout) v.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        customTabLayout(tabLayout);
        int mg_10 = MyApplication.getInstance().getDimens(getActivity(), R.dimen.tablayoutmargin10);
        int mg_5 = MyApplication.getInstance().getDimens(getActivity(), R.dimen.tablayoutmargin5);
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            View tab = ((ViewGroup) tabLayout.getChildAt(0)).getChildAt(i);
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) tab.getLayoutParams();
            if (i == 0) {
                p.setMargins(mg_10, 0, mg_5, 0);
            } else if (i == 1) {
                p.setMargins(mg_5, 0, mg_5, 0);
            } else if (i == 2) {
                p.setMargins(mg_5, 0, mg_10, 0);
            }
            tab.requestLayout();
        }
        MyApplication.getInstance().setChooseTab(MainActivity.TEAM, viewPager, tabLayout);
        init(v);
        return v;

    }

    public void customTabLayout(TabLayout tabLayout) {
        View tabThree = LayoutInflater.from(getActivity()).inflate(R.layout.tab_kind, null);
        TextView tvTitle_3 = (TextView) tabThree.findViewById(R.id.tvTitle);
        tvTitle_3.setText(getResources().getString(R.string.gum));
        tabLayout.getTabAt(2).setCustomView(tabThree);

        View tabTwo = LayoutInflater.from(getActivity()).inflate(R.layout.tab_kind, null);
        TextView tvTitle_2 = (TextView) tabTwo.findViewById(R.id.tvTitle);
        tvTitle_2.setText(getResources().getString(R.string.snack));
        tabLayout.getTabAt(1).setCustomView(tabTwo);

        View tabOne = LayoutInflater.from(getActivity()).inflate(R.layout.tab_kind, null);
        TextView tvTitle = (TextView) tabOne.findViewById(R.id.tvTitle);
        tvTitle.setText(getResources().getString(R.string.pie));

        if (MainActivity.TEAM.equals(Const.PIE_TEAM)) {
            tvTitle_2.setTextColor(getResources().getColor(R.color.teamColor));
//            tvTitle_3.setTextColor(getResources().getColor(R.color.teamColor));
        } else if (MainActivity.TEAM.equals(Const.SNACK_TEAM)) {
            tvTitle.setTextColor(getResources().getColor(R.color.teamColor));
            tvTitle_3.setTextColor(getResources().getColor(R.color.teamColor));
        } else if (MainActivity.TEAM.equals(Const.GUM_TEAM)) {
            tvTitle_2.setTextColor(getResources().getColor(R.color.teamColor));
            tvTitle.setTextColor(getResources().getColor(R.color.teamColor));
        }

        tabLayout.getTabAt(0).setCustomView(tabOne);
    }

    List<LIST_303> LIST = new ArrayList<>();
    CustomSpinnerStock adapter;

    void init(View v) {
        spDealer = (Spinner) v.findViewById(R.id.spDealer);
//        send_303(DATE);
    }

    private void setupViewPager(CustomViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment(new ResultPieFragment(), getResources().getString(R.string.pie));
        adapter.addFragment(new ResultSnackFragment(), getResources().getString(R.string.snack));
        adapter.addFragment(new ResultGumFragment(), getResources().getString(R.string.gum));
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(3);
    }

    public void send_303(final String time) {
        progressbar.setVisibility(View.VISIBLE);
        new Thread() {
            @Override
            public void run() {
                callAPI(time);
                handler.obtainMessage(MSG_UPDATE_DISPLAY, "").sendToTarget();
            }
        }.start();
    }

    boolean flag = true;

    public void send_304(boolean flag) {

        if (flag)
            progressbar.setVisibility(View.VISIBLE);
        if (MainActivity.TEAM.equals(Const.PIE_TEAM))
            ResultPieFragment.instance.reload();
        else if (MainActivity.TEAM.equals(Const.SNACK_TEAM))
            ResultSnackFragment.instance.reload();
        else if (MainActivity.TEAM.equals(Const.GUM_TEAM))
            ResultGumFragment.instance.reload();
        else if (MainActivity.TEAM.equals(Const.MIX_TEAM)) {
            ResultPieFragment.instance.reload();
            ResultSnackFragment.instance.reload();
            ResultGumFragment.instance.reload();
        }
        new Thread() {
            @Override
            public void run() {
                callAPI_304();
                handler.obtainMessage(MSG_UPDATE_DISPLAY_304, "").sendToTarget();
            }
        }.start();
    }

    boolean API_304_contains(List<API_304> arr, String DATE, String HIDEPTCD, String DEALERID) {
        // true : data off
        // false: data online
        for (API_304 p : arr) {
            if (p.getDATE().equalsIgnoreCase(DATE) && p.getHIDEPTCD().equals(HIDEPTCD) && p.getDEALERID().equals(DEALERID))
                return true;
        }
        return false;
    }


    String contents = "";

    public void callAPI_304() {

        contents = "";
        int check = 0;

        DatabaseHandler db = new DatabaseHandler(getActivity());
        String idLogin = MainActivity.dataLogin.getUSERID() + "";
        if ((Const.checkInternetConnection(getActivity())
                && Const.PHONENUMBER.length() > 0
                && idLogin.trim().length() > 0
                && MainActivity.dataLogin.getDEPTCD().trim().length() > 0
                && MainActivity.dataLogin.getJOBCODE().trim().length() > 0)
                || (Const.checkInternetConnection(getActivity()) && MainActivity.dataLogin.getJOBGRADE().trim().equals(Const.SE))) {
            NetworkManager nm = new NetworkManager(Const.IP, Const.PORT);
            nm.setTerminalInfo(Const.PHONENUMBER, idLogin, MainActivity.dataLogin.getDEPTCD(), MainActivity.dataLogin.getJOBCODE());
            try {
                nm.setTR('A', Const.API_304);
                nm.connect(10);          // set timeout
                Map<String, Object> map = new HashMap<>();
                map.put("DATE", DATE);
                map.put("HIDEPTCD", HIDEPTCD);
                map.put("DEALERID", DEALERID);
                String senddata = new Gson().toJson(map);
                Log.d(TAG, senddata);
                Const.WriteFileLog(MainActivity.dataLogin.getUSERID(), Const.API_304, senddata);
                nm.send(senddata);// send body
                contents = nm.receive();

                Log.d(TAG, "listAPI_304 online" + contents);
            } catch (EksysNetworkException e) {
                contents = "Error(" + e.getErrorCode() + "): " + e.toString();
                Log.e(TAG, "API_304 Error(" + e.getErrorCode() + "): " + e.toString());
            } finally {
                nm.close();
            }
        }
    }

    private final static int MSG_UPDATE_DISPLAY = 303;
    private final static int MSG_UPDATE_DISPLAY_304 = 304;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case MSG_UPDATE_DISPLAY:

                    Log.d(TAG, "MSG_UPDATE_DISPLAY");
                    progressbar.setVisibility(View.GONE);
                    LIST.clear();
                    if (data_303 != null) {
                        if (data_303.getRESULT() == 0) {
                            LIST = data_303.getLIST();
                        }
                    }
//                    Log.d(TAG, "S:" + LIST.size());
                    if (LIST != null && LIST.size() > 0) {
                        for (int i = 0; i < LIST.size(); i++) {
                            Log.d(TAG, LIST.get(i).getV1() + "-" + LIST.get(i).getV2() + "-" + LIST.get(i).getV3());
                        }
                        adapter = new CustomSpinnerStock(getActivity(), LIST);
                        spDealer.setAdapter(adapter);
                        spDealer.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                HIDEPTCD = LIST.get(i).getV3();
                                DEALERID = LIST.get(i).getV1();
//                            Log.d(TAG, "HIDEPTCD:" + HIDEPTCD);
//                            Log.d(TAG, "DEALERID:" + DEALERID);
                                send_304(true);
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {

                            }
                        });
                    } else {
                        handler.obtainMessage(MSG_UPDATE_DISPLAY_304, "").sendToTarget();
                    }
                    break;
                case MSG_UPDATE_DISPLAY_304:
                    progressbar.setVisibility(View.GONE);
                    Log.d(TAG, "MSG_UPDATE_DISPLAY_304");
                    if (MainActivity.TEAM.equals(Const.PIE_TEAM)) {
                        Log.d(TAG, "PIE_TEAM");
                        ResultPieFragment.instance.updateList(contents);
                        ResultGumFragment.instance.updateList(contents);
                    } else if (MainActivity.TEAM.equals(Const.SNACK_TEAM)) {
                        Log.d(TAG, "SNACK_TEAM");
                        ResultSnackFragment.instance.updateList(contents);
                    } else if (MainActivity.TEAM.equals(Const.GUM_TEAM)) {
                        Log.d(TAG, "GUM_TEAM");
                        ResultGumFragment.instance.updateList(contents);
                    } else if (MainActivity.TEAM.equals(Const.MIX_TEAM)) {
                        ResultPieFragment.instance.updateList(contents);
                        ResultSnackFragment.instance.updateList(contents);
                        ResultGumFragment.instance.updateList(contents);
                    }
                    if (ResultPieFragment.instance != null) {
                        ResultPieFragment.instance.dismissSwipe();
                    }
                    if (ResultGumFragment.instance != null) {
                        ResultGumFragment.instance.dismissSwipe();
                    }
                    if (ResultSnackFragment.instance != null) {
                        ResultSnackFragment.instance.dismissSwipe();
                    }
                    break;
            }
        }
    };
    DATA_303 data_303;

//    boolean API_303_contains(List<API_303> arr, String s) {
//        // true : data off
//        // false: data online
//        for (API_303 p : arr) {
//            if (p.getDATE().equalsIgnoreCase(s))
//                return true;
//        }
//        return false;
//    }

    public void callAPI(String time) {
        this.DATE = time;
        String contents = "";
        int check = 0;
        DatabaseHandler db = new DatabaseHandler(getActivity());
        List<API_303> api_303List = db.getListAPI_303();
        if (api_303List != null && api_303List.size() > 0) {
            if (Const.API_303_contains(api_303List, DATE)) {
                for (int i = 0; i < api_303List.size(); i++) {
                    API_303 ob = api_303List.get(i);
                    if (ob.getDATE().equals(DATE)) {
                        data_303 = new Gson().fromJson(ob.getDATA(), DATA_303.class);
                        break;
                    }
                }
                Log.d(TAG, "303 offline");
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
                nm.setTR('A', Const.API_303);
                nm.connect(10);          // set timeout
                Map<String, Object> map = new HashMap<>();
                map.put("MODE", "S");
                map.put("DATE", DATE);
                map.put("GSID", MainActivity.dataLogin.getMNGEMPID());
//                map.put("GSID", "2090469");
                String senddata = new Gson().toJson(map);
                Log.d(TAG, DATE + " : " + senddata);
                Const.WriteFileLog(MainActivity.dataLogin.getUSERID(), Const.API_303, senddata);
                nm.send(senddata);// send body
                contents = nm.receive();
                data_303 = new Gson().fromJson(contents, DATA_303.class);
                if (data_303.getRESULT() == 0) {
                    API_303 api_303 = new API_303(DATE, contents);
                    db.addContact_303(api_303);
                }
                Log.d(TAG, "303 online: " + contents);
            } catch (EksysNetworkException e) {
                Log.e(TAG, "303 Error(" + e.getErrorCode() + "): " + e.toString());
            } finally {
                nm.close();
            }
        }
    }
}
