package com.orion.salesman._download._fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.orion.salesman.MainActivity;
import com.orion.salesman.R;
import com.orion.salesman.SEActivity;
import com.orion.salesman._app.MyApplication;
import com.orion.salesman._class.Const;
import com.orion.salesman._class.HttpRequest;
import com.orion.salesman._class.IF_122;
import com.orion.salesman._class.IOUtil;
import com.orion.salesman._class.InputStreamVolleyRequest;
import com.orion.salesman._class.QuickLZ;
import com.orion.salesman._interface.DownloadDB;
import com.orion.salesman._interface.IF_10;
import com.orion.salesman._interface.IF_100;
import com.orion.salesman._interface.IF_103;
import com.orion.salesman._interface.IF_104;
import com.orion.salesman._interface.IF_110;
import com.orion.salesman._interface.IF_111;
import com.orion.salesman._interface.IF_112_FIRST;
import com.orion.salesman._interface.IF_114;
import com.orion.salesman._interface.IF_121;
import com.orion.salesman._interface.IF_123;
import com.orion.salesman._interface.IF_129;
import com.orion.salesman._interface.IF_130;
import com.orion.salesman._interface.IF_131;
import com.orion.salesman._interface.IF_303;
import com.orion.salesman._object.API_100;
import com.orion.salesman._object.CheckUpdate;
import com.orion.salesman._object.DataLogin;
import com.orion.salesman._object.DownData;
import com.orion.salesman._object.DownList;
import com.orion.salesman._object.DownLoadObject;
import com.orion.salesman._pref.PrefManager;
import com.orion.salesman._route._fragment.RouteFragment;
import com.orion.salesman._route._object.IF_128;
import com.orion.salesman._route._object.ObjectA0128;
import com.orion.salesman._sqlite.DatabaseHandler;
import com.orion.salesman._summary._adapter.DataSummarySales;
import com.orion.salesman._summary._object.SummarySales_API;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import kr.co.eksys.nativelib.EksysNetworkException;
import kr.co.eksys.nativelib.NetworkManager;

/**
 * Created by maidinh on 12/8/2016.
 */
public class DownloadFragment extends Fragment {
    String TAG = "DownloadFragment";
    public static DownloadFragment instance;
    private List<ObjectDownload> movieList = new ArrayList<>();
    private RecyclerView recyclerView;
    private adapterDownload mAdapter;
    private Button btnTotal;
    private Button btnSelect, btnSelectUser;

    public DownloadFragment() {

    }

    String today = "";
    String yesterday = "";

    void initYesterday() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int date = calendar.get(Calendar.DATE);
        yesterday = year + Const.setFullDate(month) + Const.setFullDate(date);
    }

    boolean flag = true;
    boolean download = false;

    void prepareMovieData() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int date = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR);
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);

        today = year + Const.setFullDate(month) + Const.setFullDate(date);

        String s2 = new PrefManager(getActivity()).getCheckUpdate();
        CheckUpdate checkUpdate = new Gson().fromJson(s2, CheckUpdate.class);

        if (checkUpdate.isCheck() && checkUpdate.getDATE().equals("" + date)) {
            flag = false;
            download = true;
        }

        String time = year + " - " + Const.setFullDate(month) + " - " + Const.setFullDate(date);

        String Str[] = getResources().getStringArray(R.array.listDownload);

        for (int i = 0; i < Str.length; i++) {
            ObjectDownload ob = new ObjectDownload(Str[i], time, flag, download);
            movieList.add(ob);
        }
        String s = new PrefManager(getActivity()).getCheckDownLoad();
        if (s.length() > 0) {
            DownList routeList = new Gson().fromJson(s, DownList.class);
            List<DownLoadObject> LIST = routeList.getLIST();
            for (int i = 0; i < LIST.size(); i++) {
                movieList.get(i).setV2(LIST.get(i).getV2());
            }
        }
    }

    private CheckBox checkBox;
    private DataLogin dataLogin;

    public void selectDownLoad() {
        ArrayList<Integer> listDownLoad = new ArrayList<>();
        List<ObjectDownload> getList = mAdapter.getList();
        for (int i = 0; i < getList.size(); i++) {
            if (getList.get(i).isCheck()) {
                Log.d(TAG, "position:" + i);
                listDownLoad.add(i);
            }
        }
        if (listDownLoad.size() > 0) {

            int _CURRENT = 0;
            int SumCount = 0;
            MainActivity.instance.showFR();
            for (int i = 0; i < listDownLoad.size(); i++) {
                if (listDownLoad.get(i) == 6 || listDownLoad.get(i) == 10 || listDownLoad.get(i) == 11 || listDownLoad.get(i) == 12) {
                    SumCount += 2;
                } else {
                    SumCount += 1;
                }
            }
            Log.d(TAG, "SumCount:" + SumCount);
            int cur = 0;
            MainActivity.instance.setStatusDownload("Loading....", Const.setStatusProgressbar(cur, SumCount), cur, SumCount);

            for (int i = 0; i < listDownLoad.size(); i++) {
                int n = 0;
                if (i == listDownLoad.size() - 1)
                    n = 1;
                if (listDownLoad.get(i) == 6 || listDownLoad.get(i) == 10 || listDownLoad.get(i) == 11 || listDownLoad.get(i) == 12) {
                    _CURRENT += 2;
                } else {
                    _CURRENT += 1;
                }
                Log.d(TAG, "SumCount:" + SumCount);
                Log.d(TAG, "_CURRENT:" + _CURRENT);
                mAdapter.selectDownload(listDownLoad.get(i), n, SumCount, _CURRENT);
//                try {
//                    Thread.sleep(500);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
            }
        }


    }

    void init(View v) {
        dataLogin = new Gson().fromJson(new PrefManager(getActivity()).getInfoLogin(), DataLogin.class);
        initYesterday();
        prepareMovieData();
        recyclerView = (RecyclerView) v.findViewById(R.id.recycler_view);
        mAdapter = new adapterDownload(getActivity(), movieList, instance);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(mAdapter);

        checkBox = (CheckBox) v.findViewById(R.id.cbTitle);
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAdapter.updateCheck_UnCheck(checkBox.isChecked());
            }
        });
        List<ObjectDownload> arr = mAdapter.getList();
        checkBox.setChecked(checkList(arr));
        btnSelect = (Button) v.findViewById(R.id.btnSelect);
        btnSelectUser = (Button) v.findViewById(R.id.btnSelectUser);
        if (Const.inforSE.length() > 0) {
            btnSelectUser.setVisibility(View.VISIBLE);
        } else {
            btnSelectUser.setVisibility(View.GONE);
        }
        btnSelectUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), SEActivity.class));
                getActivity().finish();

            }
        });
        btnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                selectDownLoad();
            }
        });
        btnTotal = (Button) v.findViewById(R.id.btnTotal);

        btnTotal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAdapter.REMOVE_ALL_CHECK_DOWNLOAD();
                MainActivity.instance.showFR();

                btnTotal.setEnabled(false);
                API_10_1();
            }
        });
    }

    void API_10_2() {
        new HttpRequest(getActivity()).new API_10(MainActivity.dataLogin, 1, new IF_10() {
            @Override
            public void onSuccess(String s) {
                mAdapter.updateDownLoadSuccess(1);
                int cur = 2;
                MainActivity.instance.setStatusDownload("Download Product & Display Data", Const.setStatusProgressbar(cur, Const.sumDownload), cur, Const.sumDownload);
                API_100();
            }

            @Override
            public void onFail() {
                finishDownload();
                Toast.makeText(getActivity(), "Download Product & Display Data FAIL", Toast.LENGTH_SHORT).show();
            }
        }).execute();
    }

    void API_10_1() {
        new HttpRequest(getActivity()).new API_10(MainActivity.dataLogin, 0, new IF_10() {
            @Override
            public void onSuccess(String s) {
                mAdapter.updateDownLoadSuccess(0);
                int cur = 1;
                MainActivity.instance.setStatusDownload("Download Address & Common Data", Const.setStatusProgressbar(cur, Const.sumDownload), cur, Const.sumDownload);
                API_10_2();
            }

            @Override
            public void onFail() {
                finishDownload();
                Toast.makeText(getActivity(), "Download Address & Common Data FAIL", Toast.LENGTH_SHORT).show();
            }
        }).execute();
    }

    void API_100() {
        new HttpRequest(getActivity()).new API_100(MainActivity.DATE_SYNC, MainActivity.dataLogin, new IF_100() {
            @Override
            public void onSuccess() {
                mAdapter.updateDownLoadSuccess(2);
                int cur = 3;
                MainActivity.instance.setStatusDownload(getResources().getString(R.string.API_100), Const.setStatusProgressbar(cur, Const.sumDownload), cur, Const.sumDownload);
                API_130();
            }

            @Override
            public void onFail() {
                finishDownload();
                Toast.makeText(getActivity(), getResources().getString(R.string.API_100) + " FAIL", Toast.LENGTH_SHORT).show();
            }
        }).execute();
    }

    void finishDownload() {
        btnTotal.setEnabled(true);
        MainActivity.instance.hideFR();
    }

    void API_129_TODAY() {
        new HttpRequest(getActivity()).new A0129(dataLogin, new IF_129() {
            @Override
            public void onSuccess() {
                API_129_YESTERDAY();
                int cur = 21;
                MainActivity.instance.setStatusDownload("Get Salesman Order OMI Detail Info " + TODAY, Const.setStatusProgressbar(cur, Const.sumDownload), cur, Const.sumDownload);
            }

            @Override
            public void onFail() {
                finishDownload();
                Toast.makeText(getActivity(), "Order OMI Detail Info FAIL", Toast.LENGTH_SHORT).show();
            }
        }, TODAY).execute();
    }

    void API_128_YESTERDAY() {
        new HttpRequest(getActivity()).new A0128(dataLogin, YESTERDAY, new IF_128() {
            @Override
            public void onSuccess(List<ObjectA0128> list) {
                mAdapter.updateDownLoadSuccess(11);
                int cur = 20;
                MainActivity.instance.setStatusDownload("Order OMI Info " + YESTERDAY, Const.setStatusProgressbar(cur, Const.sumDownload), cur, Const.sumDownload);
                API_129_TODAY();

            }

            @Override
            public void onFail() {
                finishDownload();
                Toast.makeText(getActivity(), "Order OMI Info FAIL", Toast.LENGTH_SHORT).show();
            }
        }).execute();
    }

    void API_128_TODAY() {

        new HttpRequest(getActivity()).new A0128(dataLogin, TODAY, new IF_128() {
            @Override
            public void onSuccess(List<ObjectA0128> list) {
                API_128_YESTERDAY();
                int cur = 19;
                MainActivity.instance.setStatusDownload("Get Salesman Order OMI Info " + TODAY, Const.setStatusProgressbar(cur, Const.sumDownload), cur, Const.sumDownload);
            }

            @Override
            public void onFail() {
                finishDownload();
                Toast.makeText(getActivity(), "Order OMI Info FAIL", Toast.LENGTH_SHORT).show();
            }
        }).execute();
    }

    void API_129_YESTERDAY() {
        new HttpRequest(getActivity()).new A0129(dataLogin, new IF_129() {
            @Override
            public void onSuccess() {
                mAdapter.updateDownLoadSuccess(12);
                int cur = 22;
                MainActivity.instance.setStatusDownload("Get Salesman Order OMI Detail Info " + YESTERDAY, Const.setStatusProgressbar(cur, Const.sumDownload), cur, Const.sumDownload);

                PrefManager pref = new PrefManager(getActivity());
                finishDownload();
                mAdapter.updateCheck_UnCheck(false);
                btnTotal.setEnabled(true);
                checkBox.setChecked(false);
                String s2 = new PrefManager(getActivity()).getCheckUpdate();
                CheckUpdate checkUpdate = new Gson().fromJson(s2, CheckUpdate.class);
                checkUpdate.setCheck(true);
                pref.setCheckUpdate(new Gson().toJson(checkUpdate));
                MainActivity.instance.EnableViewPager();
                if (flag) {
                    MainActivity.instance.gotoTabOne();
                } else {

                }


                pref.setResult_1(true);
                pref.setResult_2(true);
                pref.setResult_3(true);
                pref.setResult_4(true);
                pref.setSummary_1(true);
                pref.setSummary_2(true);
                pref.setSummary_3(true);
                pref.setSummary_4(true);

            }

            @Override
            public void onFail() {
                finishDownload();
                Toast.makeText(getActivity(), "Order OMI Detail Info FAIL", Toast.LENGTH_SHORT).show();
            }
        }, YESTERDAY).execute();
    }

    void API_120() {
        new HttpRequest(getActivity()).new API_120_(MainActivity.dataLogin, new IF_100() {
            @Override
            public void onSuccess() {
                mAdapter.updateDownLoadSuccess(7);
                int cur = 14;
                MainActivity.instance.setStatusDownload(getResources().getString(R.string.API_120), Const.setStatusProgressbar(cur, Const.sumDownload), cur, Const.sumDownload);
                API_122();

            }

            @Override
            public void onFail() {
                finishDownload();
                Toast.makeText(getActivity(), getResources().getString(R.string.API_120) + " FAIL ", Toast.LENGTH_SHORT).show();
            }
        }).execute();
    }

    void API_122() {
        new HttpRequest(getActivity()).new API_122_(MainActivity.dataLogin, new IF_122() {
            @Override
            public void onSuccess() {
                int cur = 15;
                MainActivity.instance.setStatusDownload(getResources().getString(R.string.API_122), Const.setStatusProgressbar(cur, Const.sumDownload), cur, Const.sumDownload);
                mAdapter.updateDownLoadSuccess(8);
                API_123();
            }

            @Override
            public void onFail() {
                finishDownload();
                Toast.makeText(getActivity(), getResources().getString(R.string.API_122) + " FAIL ", Toast.LENGTH_SHORT).show();
            }
        }).execute();
    }

    void API_110_YESTERDAY() {
        int RouteNo = 0;
        try {
            RouteNo = Integer.parseInt(dataLogin.getROUTENO());
        } catch (Exception e) {
            e.printStackTrace();
        }
        String KEY_ROUTE = "1";
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        int pos = cal.get(Calendar.DAY_OF_WEEK);// sunday = 1, sat = 7
        pos -= 1;
        if (pos <= 0)
            pos = 1;

        if (RouteNo == 0) {
            if (pos == 4) KEY_ROUTE = "1";
            else if (pos == 5) KEY_ROUTE = "2";
            else if (pos == 6) KEY_ROUTE = "3";
            else KEY_ROUTE = "" + pos;
        } else {
            KEY_ROUTE = "" + pos;
        }

        new HttpRequest(getActivity()).new API_110_(dataLogin, KEY_ROUTE, new IF_110() {
            @Override
            public void onSuccess() {
                API_114();

                Calendar cal = Calendar.getInstance();
                int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
                //Sunday = 1, Monday = 2,...
                String s = "";
                if (dayOfWeek == 2) {
                    s = Const.getToday(-2);
                } else {
                    s = Const.getToday(-1);
                }
                int cur = 12;
                MainActivity.instance.setStatusDownload(getResources().getString(R.string.API_110) + " " + s, Const.setStatusProgressbar(cur, Const.sumDownload), cur, Const.sumDownload);
            }

            @Override
            public void onFail() {
                finishDownload();
                Toast.makeText(getActivity(), getResources().getString(R.string.API_110) + " FAIL ", Toast.LENGTH_SHORT).show();
            }
        }, 0).execute();
    }


    void API_114() {
        new HttpRequest(getActivity()).new API_114(dataLogin, Const.getToday(), new IF_114() {
            @Override
            public void onSuccess() {
                API_120();
                int cur = 13;
                MainActivity.instance.setStatusDownload("Get SM WorkDay Route List", Const.setStatusProgressbar(cur, Const.sumDownload), cur, Const.sumDownload);
            }

            @Override
            public void onFail() {
                Toast.makeText(getActivity(), "Get SM WorkDay Route List FAIL ", Toast.LENGTH_SHORT).show();
            }
        }).execute();
    }


    void API_110() {
        new HttpRequest(getActivity()).new API_110_(MainActivity.dataLogin, RouteFragment.KEY_ROUTE, new IF_110() {
            @Override
            public void onSuccess() {
                new PrefManager(getActivity()).setUpdateRoute(true);
                mAdapter.updateDownLoadSuccess(6);
                int cur = 11;
                MainActivity.instance.setStatusDownload(getResources().getString(R.string.API_110) + Const.getToday(-1), Const.setStatusProgressbar(cur, Const.sumDownload), cur, Const.sumDownload);
                API_110_YESTERDAY();
            }

            @Override
            public void onFail() {
                finishDownload();
                Toast.makeText(getActivity(), getResources().getString(R.string.API_110) + " FAIL ", Toast.LENGTH_SHORT).show();
            }
        }, 1).execute();
    }

    void API_123() {
        new HttpRequest(getActivity()).new API_123_(MainActivity.dataLogin, new IF_123() {
            @Override
            public void onSuccess() {
                mAdapter.updateDownLoadSuccess(9);
                int cur = 16;
                MainActivity.instance.setStatusDownload(getResources().getString(R.string.API_123), Const.setStatusProgressbar(cur, Const.sumDownload), cur, Const.sumDownload);
                API_121();

            }

            @Override
            public void onFail() {
                finishDownload();
                Toast.makeText(getActivity(), getResources().getString(R.string.API_123) + " FAIL ", Toast.LENGTH_SHORT).show();
            }
        }).execute();
    }

    void API_121() {
        new HttpRequest(getActivity()).new API_121_(MainActivity.dataLogin, new IF_121() {
            @Override
            public void onSuccess() {

                API_121_Pre();
                int cur = 17;
                MainActivity.instance.setStatusDownload("Get Promotion List " + Const.getToday(), Const.setStatusProgressbar(cur, Const.sumDownload), cur, Const.sumDownload);


            }

            @Override
            public void onFail() {
                finishDownload();
                Toast.makeText(getActivity(), getResources().getString(R.string.API_121) + " FAIL ", Toast.LENGTH_SHORT).show();
            }
        }, Const.getToday()).execute();
    }

    void API_121_Pre() {
        final String preMonth = Const.getPreMonth() + "15";
        new HttpRequest(getActivity()).new API_121_(MainActivity.dataLogin, new IF_121() {
            @Override
            public void onSuccess() {
                mAdapter.updateDownLoadSuccess(10);
                API_128_TODAY();
                int cur = 18;
                MainActivity.instance.setStatusDownload(getResources().getString(R.string.API_121) + " " + Const.getPreMonth(), Const.setStatusProgressbar(cur, Const.sumDownload), cur, Const.sumDownload);

            }

            @Override
            public void onFail() {
                finishDownload();
                Toast.makeText(getActivity(), getResources().getString(R.string.API_121) + " FAIL ", Toast.LENGTH_SHORT).show();
            }
        }, preMonth).execute();
    }

    void API_112_YESTERDAY() {
        int RouteNo = 0;
        try {
            RouteNo = Integer.parseInt(dataLogin.getROUTENO());
        } catch (Exception e) {
            e.printStackTrace();
        }
        String KEY_ROUTE = "1";
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        int pos = cal.get(Calendar.DAY_OF_WEEK);// sunday = 1, sat = 7
        pos -= 1;
        if (pos <= 0)
            pos = 1;

        if (RouteNo == 0) {
            if (pos == 4) KEY_ROUTE = "1";
            else if (pos == 5) KEY_ROUTE = "2";
            else if (pos == 6) KEY_ROUTE = "3";
            else KEY_ROUTE = "" + pos;
        } else {
            KEY_ROUTE = "" + pos;
        }
        new HttpRequest(getActivity()).new API_112_(dataLogin, KEY_ROUTE, new IF_112_FIRST() {
            @Override
            public void onSuccess() {
                API_110();
                int cur = 10;
                Calendar cal = Calendar.getInstance();
                int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
                String s = "";
                if (dayOfWeek == 2) {
                    s = Const.getToday(-2);
                } else {
                    s = Const.getToday(-1);
                }
                MainActivity.instance.setStatusDownload("Get Route All Shop’s Information " + s, Const.setStatusProgressbar(cur, Const.sumDownload), cur, Const.sumDownload);
            }

            @Override
            public void onFail() {
                finishDownload();
                Toast.makeText(getActivity(), "API 112 FAIL ", Toast.LENGTH_SHORT).show();
            }
        }, 0).execute();
    }

    void API_112() {
        new HttpRequest(getActivity()).new API_112_(MainActivity.dataLogin, RouteFragment.KEY_ROUTE, new IF_112_FIRST() {
            @Override
            public void onSuccess() {
                API_112_YESTERDAY();
                int cur = 9;
                MainActivity.instance.setStatusDownload("Get Route All Shop’s Information " + Const.getToday(-1), Const.setStatusProgressbar(cur, Const.sumDownload), cur, Const.sumDownload);
            }

            @Override
            public void onFail() {
                finishDownload();
                Toast.makeText(getActivity(), "API 112 FAIL ", Toast.LENGTH_SHORT).show();
            }
        }, 1).execute();
    }

    void API_131() {
        new HttpRequest(getActivity()).new API_131_(MainActivity.DATE_SYNC, MainActivity.dataLogin, new IF_131() {
            @Override
            public void onSuccess() {

                API_112();
                int cur = 8;
                MainActivity.instance.setStatusDownload("RESULT NON SHOP Get Shop + Route List", Const.setStatusProgressbar(cur, Const.sumDownload), cur, Const.sumDownload);
            }

            @Override
            public void onFail() {
                finishDownload();
                Toast.makeText(getActivity(), "API 131 FAIL ", Toast.LENGTH_SHORT).show();
            }
        }).execute();
    }

    void API_303() {
        new HttpRequest(getActivity()).new API_303_(MainActivity.REPORT_TODAY, MainActivity.dataLogin, new IF_303() {
            @Override
            public void onSuccess() {
                API_131();
                int cur = 7;
                MainActivity.instance.setStatusDownload("Get Managed Dealer List", Const.setStatusProgressbar(cur, Const.sumDownload), cur, Const.sumDownload);
            }

            @Override
            public void onFail() {
                finishDownload();
                Toast.makeText(getActivity(), "API 303 FAIL ", Toast.LENGTH_SHORT).show();
            }
        }).execute();
    }

    void API_103() {
        new HttpRequest(getActivity()).new API_103_(MainActivity.DATE_SYNC, MainActivity.dataLogin, new IF_103() {
            @Override
            public void onSuccess() {
                mAdapter.updateDownLoadSuccess(5);
                int cur = 6;
                MainActivity.instance.setStatusDownload(getResources().getString(R.string.API_103), Const.setStatusProgressbar(cur, Const.sumDownload), cur, Const.sumDownload);
                API_303();
            }

            @Override
            public void onFail() {
                finishDownload();
                Toast.makeText(getActivity(), getResources().getString(R.string.API_103) + " FAIL ", Toast.LENGTH_SHORT).show();
            }
        }).execute();
    }

    void API_104() {
        new HttpRequest(getActivity()).new API_104_(MainActivity.DATE_SYNC, MainActivity.dataLogin, new IF_104() {
            @Override
            public void onSuccess() {
                mAdapter.updateDownLoadSuccess(4);
                int cur = 5;
                MainActivity.instance.setStatusDownload(getResources().getString(R.string.API_104), Const.setStatusProgressbar(cur, Const.sumDownload), cur, Const.sumDownload);
                API_103();
            }

            @Override
            public void onFail() {
                finishDownload();
                Toast.makeText(getActivity(), getResources().getString(R.string.API_104) + " FAIL", Toast.LENGTH_SHORT).show();
            }
        }).execute();
    }

    void API_130() {
        new HttpRequest(getActivity()).new API_130_(MainActivity.DATE_SYNC, MainActivity.dataLogin, new IF_130() {
            @Override
            public void onSuccess() {
                mAdapter.updateDownLoadSuccess(3);
                API_104();
                int cur = 4;
                MainActivity.instance.setStatusDownload(getResources().getString(R.string.API_101), Const.setStatusProgressbar(cur, Const.sumDownload), cur, Const.sumDownload);
            }

            @Override
            public void onFail() {
                finishDownload();
                Toast.makeText(getActivity(), "API 130 FAIL", Toast.LENGTH_SHORT).show();
            }
        }).execute();
    }

    public void setCheckCB(boolean checkCB) {
        checkBox.setChecked(checkCB);
    }

    boolean checkList(List<ObjectDownload> arr) {
        for (int i = 0; i < arr.size(); i++) {
            if (!arr.get(i).isCheck())
                return false;
        }
        return true;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_download, container, false);
        instance = this;
        initTodayYesterday();
        init(v);
        return v;
    }

    String TODAY = "";
    String YESTERDAY = "";

    void initTodayYesterday() {

        Calendar cal = Calendar.getInstance();
        int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;
        int date = cal.get(Calendar.DATE);
        TODAY = year + Const.formatFullDate(month) + Const.formatFullDate(date);
        //Sunday = 1, Monday = 2,...

        if (dayOfWeek == 2) {
            YESTERDAY = Const.getToday(-2);
        } else {
            YESTERDAY = Const.getToday(-1);
        }
    }
}