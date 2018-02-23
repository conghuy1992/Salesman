package com.orion.salesman;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.orion.salesman._adaper.DownLoadAdapter;
import com.orion.salesman._class.Const;
import com.orion.salesman._class.CustomDialog;
import com.orion.salesman._class.HttpRequest;
import com.orion.salesman._class.IF_122;
import com.orion.salesman._download._fragment.ObjectDownload;
import com.orion.salesman._download._fragment.adapterDownload;
import com.orion.salesman._interface.IF_10;
import com.orion.salesman._interface.IF_100;
import com.orion.salesman._interface.IF_103;
import com.orion.salesman._interface.IF_104;
import com.orion.salesman._interface.IF_110;
import com.orion.salesman._interface.IF_112_FIRST;
import com.orion.salesman._interface.IF_114;
import com.orion.salesman._interface.IF_121;
import com.orion.salesman._interface.IF_123;
import com.orion.salesman._interface.IF_129;
import com.orion.salesman._interface.IF_130;
import com.orion.salesman._interface.IF_131;
import com.orion.salesman._interface.IF_303;
import com.orion.salesman._interface.OnClickDialog;
import com.orion.salesman._object.CheckUpdate;
import com.orion.salesman._object.DataLogin;
import com.orion.salesman._object.DownList;
import com.orion.salesman._object.DownLoadObject;
import com.orion.salesman._pref.PrefManager;
import com.orion.salesman._route._object.IF_128;
import com.orion.salesman._route._object.ObjectA0128;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by maidinh on 3/11/2016.
 */
public class DownLoadActivity extends AppCompatActivity {
    private DataLogin dataLogin;
    String TAG = "DownloadFragment";
    public static DownLoadActivity instance;
    private List<ObjectDownload> movieList = new ArrayList<>();
    private RecyclerView recyclerView;
    private DownLoadAdapter mAdapter;
    private Button btnTotal;
    public String DATE_SYNC = "20160808";
    public static String REPORT_TODAY = "";

    void INIT_REPORT_TODAY() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;
        int date = cal.get(Calendar.DATE);
        REPORT_TODAY = year + Const.formatFullDate(month) + Const.formatFullDate(date);
//        Log.d(TAG, "REPORT_TODAY:" + REPORT_TODAY);
    }

    void initDateSync() {
        Calendar cal = Calendar.getInstance();
//        cal.add(Calendar.DATE, -1);
        int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;
        int date = cal.get(Calendar.DATE);
        String TODAY = "";
        if (dayOfWeek == 2) {
            TODAY = Const.getToday(-2);
        } else if (dayOfWeek == 3) {
            TODAY = Const.getToday(-1);
        } else {
            TODAY = Const.getToday(-1);
        }
        DATE_SYNC = TODAY;
    }

    CheckBox checkBox;
    FrameLayout frLoading;
    int RouteNo = 0;
    Button btnSelectUser;
    TextView tvStatus, tvCurrentDownload;
    ProgressBar progressBar;

    public void setStatusDownload(String s, int progress, int cur) {
        if (tvStatus != null && progressBar != null && tvCurrentDownload != null) {
            tvStatus.setText(s);
            tvCurrentDownload.setText("" + cur + "/" + Const.sumDownload);
            progressBar.setProgress(progress);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.download_layout);


        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.color_bar));
        }
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        initTodayYesterday();
        initDateSync();
        INIT_REPORT_TODAY();
        init();
    }

    String today = "";
    String yesterday = "";

    public void setCheckCB(boolean checkCB) {
        checkBox.setChecked(checkCB);
    }

    void initYesterday() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int date = calendar.get(Calendar.DATE);
        yesterday = year + Const.setFullDate(month) + Const.setFullDate(date);
    }


    void prepareMovieData() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int date = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR);
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);

        today = year + Const.setFullDate(month) + Const.setFullDate(date);

        String time = year + " - " + Const.setFullDate(month) + " - " + Const.setFullDate(date);

        String Str[] = getResources().getStringArray(R.array.listDownload);

        for (int i = 0; i < Str.length; i++) {
            ObjectDownload ob = new ObjectDownload(Str[i], time, true, false);
            movieList.add(ob);
        }
        String s = new PrefManager(getApplicationContext()).getCheckDownLoad();
        if (s.length() > 0) {
            DownList routeList = new Gson().fromJson(s, DownList.class);
            List<DownLoadObject> LIST = routeList.getLIST();
            for (int i = 0; i < LIST.size(); i++) {
                movieList.get(i).setV2(LIST.get(i).getV2());
            }
        }
    }

    boolean checkList(List<ObjectDownload> arr) {
        for (int i = 0; i < arr.size(); i++) {
            if (!arr.get(i).isCheck())
                return false;
        }
        return true;
    }

    CustomDialog mydialog;

    void doBack() {
        mydialog = new CustomDialog(this, getResources().getString(R.string.wantexit), new OnClickDialog() {
            @Override
            public void btnOK() {
                mydialog.dismiss();
                finish();
            }

            @Override
            public void btnCancel() {
                mydialog.dismiss();
            }
        });
        mydialog.show();
    }

    @Override
    public void onBackPressed() {
        doBack();
    }

    void init() {
        try {
            RouteNo = Integer.parseInt(dataLogin.getROUTENO());
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d(TAG, "RouteNo:" + RouteNo);
        Calendar cal = Calendar.getInstance();
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
        Log.d(TAG, "KEY_ROUTE:" + KEY_ROUTE);

        frLoading = (FrameLayout) findViewById(R.id.frLoading);
        tvStatus = (TextView) findViewById(R.id.tvStatus);
        tvCurrentDownload = (TextView) findViewById(R.id.tvCurrentDownload);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        dataLogin = new Gson().fromJson(new PrefManager(getApplicationContext()).getInfoLogin(), DataLogin.class);
        initYesterday();
        prepareMovieData();
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mAdapter = new DownLoadAdapter(getApplicationContext(), movieList, instance);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(mAdapter);

        checkBox = (CheckBox) findViewById(R.id.cbTitle);
        checkBox.setChecked(true);
        checkBox.setEnabled(false);

        btnTotal = (Button) findViewById(R.id.btnTotal);

        btnTotal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                mAdapter.REMOVE_ALL_CHECK_DOWNLOAD();
                showFR();
                btnTotal.setEnabled(false);
                API_10_1();
            }
        });


        btnSelectUser = (Button) findViewById(R.id.btnSelectUser);
        if (Const.inforSE.length() > 0) {
            btnSelectUser.setVisibility(View.VISIBLE);
        } else {
            btnSelectUser.setVisibility(View.GONE);
        }
        btnSelectUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DownLoadActivity.this, SEActivity.class));
                finish();
            }
        });
    }

    void showFR() {
        frLoading.setVisibility(View.VISIBLE);
    }

    String YESTERDAY = "";
    String TODAY = "";

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


    void API_129_YESTERDAY() {
        new HttpRequest(getApplicationContext()).new A0129(dataLogin, new IF_129() {
            @Override
            public void onSuccess() {
                int cur = 22;
                setStatusDownload("Order OMI Detail Info " + YESTERDAY, Const.setStatusProgressbar(cur, Const.sumDownload), cur);
                String s2 = new PrefManager(getApplicationContext()).getCheckUpdate();
                CheckUpdate checkUpdate = new Gson().fromJson(s2, CheckUpdate.class);
                checkUpdate.setCheck(true);
                new PrefManager(getApplicationContext()).setCheckUpdate(new Gson().toJson(checkUpdate));
                Intent intent = new Intent(DownLoadActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onFail() {
                finishDownload();
                Toast.makeText(getApplicationContext(), "Order OMI Detail Info FAIL", Toast.LENGTH_SHORT).show();
            }
        }, YESTERDAY).execute();
    }

    boolean flag_129 = true;

    void API_129_TODAY() {
        if (flag_129) {
            new HttpRequest(getApplicationContext()).new A0129(dataLogin, new IF_129() {
                @Override
                public void onSuccess() {
                    flag_129 = false;
                    API_129_YESTERDAY();
                    int cur = 21;
                    setStatusDownload("Order OMI Detail Info " + TODAY, Const.setStatusProgressbar(cur, Const.sumDownload), cur);
                }

                @Override
                public void onFail() {
                    finishDownload();
                    Toast.makeText(getApplicationContext(), "Order OMI Detail Info FAIL", Toast.LENGTH_SHORT).show();
                }
            }, TODAY).execute();
        } else {
            API_129_YESTERDAY();
        }

    }

    boolean flag_128_Y = true;

    void API_128_YESTERDAY() {
        if (flag_128_Y) {
            new HttpRequest(getApplicationContext()).new A0128(dataLogin, YESTERDAY, new IF_128() {
                @Override
                public void onSuccess(List<ObjectA0128> list) {
                    mAdapter.updateDownLoadSuccess(11);
                    flag_128_Y = false;
                    int cur = 20;
                    setStatusDownload("Order OMI Info " + YESTERDAY, Const.setStatusProgressbar(cur, Const.sumDownload), cur);
                    API_129_TODAY();

                }

                @Override
                public void onFail() {
                    finishDownload();
                    Toast.makeText(getApplicationContext(), "Order OMI Info FAIL", Toast.LENGTH_SHORT).show();
                }
            }).execute();
        } else {
            API_129_TODAY();
        }

    }

    boolean flag_128 = true;

    void API_128_TODAY() {
        if (flag_128) {
            new HttpRequest(getApplicationContext()).new A0128(dataLogin, TODAY, new IF_128() {
                @Override
                public void onSuccess(List<ObjectA0128> list) {
                    flag_128 = false;
                    API_128_YESTERDAY();
                    int cur = 19;
                    setStatusDownload("Get Salesman Order OMI Info " + Const.getToday(), Const.setStatusProgressbar(cur, Const.sumDownload), cur);
                }

                @Override
                public void onFail() {
                    finishDownload();
                    Toast.makeText(getApplicationContext(), "Order OMI Info FAIL", Toast.LENGTH_SHORT).show();
                }
            }).execute();
        } else {
            API_128_YESTERDAY();
        }


    }

    boolean flag_10_2 = true;

    void API_10_2() {
        if (flag_10_2) {
            new HttpRequest(getApplicationContext()).new API_10(dataLogin, 1, new IF_10() {
                @Override
                public void onSuccess(String s) {
                    flag_10_2 = false;
                    mAdapter.updateDownLoadSuccess(1);
                    int cur = 2;
                    setStatusDownload("Download Product & Display Data", Const.setStatusProgressbar(cur, Const.sumDownload), cur);
                    API_100();
                }

                @Override
                public void onFail() {
                    finishDownload();
                    Toast.makeText(getApplicationContext(), "Download Product & Display Data FAIL", Toast.LENGTH_SHORT).show();
                }
            }).execute();
        } else {
            API_100();
        }

    }

    boolean flag_10 = true;

    void API_10_1() {
        if (flag_10) {
            new HttpRequest(getApplicationContext()).new API_10(dataLogin, 0, new IF_10() {
                @Override
                public void onSuccess(String s) {
                    flag_10 = false;
                    mAdapter.updateDownLoadSuccess(0);
                    int cur = 1;
                    setStatusDownload("Download Address & Common Data", Const.setStatusProgressbar(cur, Const.sumDownload), cur);
                    API_10_2();
                }

                @Override
                public void onFail() {
                    finishDownload();
                    Toast.makeText(getApplicationContext(), "Download Address & Common Data FAIL", Toast.LENGTH_SHORT).show();
                }
            }).execute();
        } else {
            API_10_2();
        }

    }

    boolean flag_100 = true;

    void API_100() {
        if (flag_100) {
            new HttpRequest(getApplicationContext()).new API_100(DATE_SYNC, dataLogin, new IF_100() {
                @Override
                public void onSuccess() {
                    flag_100 = false;
                    mAdapter.updateDownLoadSuccess(2);
                    int cur = 3;
                    setStatusDownload(getResources().getString(R.string.API_100), Const.setStatusProgressbar(cur, Const.sumDownload), cur);
                    API_130();
                }

                @Override
                public void onFail() {
                    finishDownload();
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.API_100) + " FAIL", Toast.LENGTH_SHORT).show();
                }
            }).execute();
        } else {
            API_130();
        }

    }

    boolean flag_120 = true;

    void API_120() {
        if (flag_120) {
            new HttpRequest(getApplicationContext()).new API_120_(dataLogin, new IF_100() {
                @Override
                public void onSuccess() {
                    flag_120 = false;
                    mAdapter.updateDownLoadSuccess(7);
                    API_122();
                    int cur = 14;
                    setStatusDownload(getResources().getString(R.string.API_120), Const.setStatusProgressbar(cur, Const.sumDownload), cur);
                }

                @Override
                public void onFail() {
                    finishDownload();
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.API_120) + " FAIL ", Toast.LENGTH_SHORT).show();
                }
            }).execute();
        } else {
            API_122();
        }

    }

    boolean flag_122 = true;

    void API_122() {
        if (flag_122) {
            new HttpRequest(getApplicationContext()).new API_122_(dataLogin, new IF_122() {
                @Override
                public void onSuccess() {
                    flag_122 = false;
                    int cur = 15;
                    setStatusDownload(getResources().getString(R.string.API_122), Const.setStatusProgressbar(cur, Const.sumDownload), cur);
                    mAdapter.updateDownLoadSuccess(8);
                    API_123();
                }

                @Override
                public void onFail() {
                    finishDownload();
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.API_122) + " FAIL ", Toast.LENGTH_SHORT).show();
                }
            }).execute();
        } else {
            API_123();
        }

    }

    boolean flag_110_Y = true;

    void API_110_YESTERDAY() {
        if (flag_110_Y) {
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
            Log.d(TAG, "KEY_ROUTE:" + KEY_ROUTE);


            new HttpRequest(getApplicationContext()).new API_110_(dataLogin, KEY_ROUTE, new IF_110() {
                @Override
                public void onSuccess() {
                    flag_110_Y = false;
                    API_114();
                    Calendar cal = Calendar.getInstance();
                    int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
                    String s = "";
                    if (dayOfWeek == 2) {
                        s = Const.getToday(-2);
                    } else {
                        s = Const.getToday(-1);
                    }
                    int cur = 12;
                    setStatusDownload(getResources().getString(R.string.API_110) + " " + s, Const.setStatusProgressbar(cur, Const.sumDownload), cur);
                }

                @Override
                public void onFail() {
                    finishDownload();
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.API_110) + " FAIL ", Toast.LENGTH_SHORT).show();
                }
            }, 0).execute();
        } else {
            API_114();
        }

    }

    boolean flag_114 = true;

    void API_114() {
        if (flag_114) {
            new HttpRequest(getApplicationContext()).new API_114(dataLogin, Const.getToday(), new IF_114() {
                @Override
                public void onSuccess() {
                    flag_114 = false;
                    API_120();
                    int cur = 13;
                    setStatusDownload("Get SM WorkDay Route List", Const.setStatusProgressbar(cur, Const.sumDownload), cur);
                }

                @Override
                public void onFail() {
                    Toast.makeText(getApplicationContext(), "Get SM WorkDay Route List FAIL ", Toast.LENGTH_SHORT).show();
                }
            }).execute();

        } else {
            API_120();
        }
    }

    boolean flag_110 = true;

    void API_110() {
        if (flag_110) {
            new HttpRequest(getApplicationContext()).new API_110_(dataLogin, KEY_ROUTE, new IF_110() {
                @Override
                public void onSuccess() {
                    flag_110 = false;
                    mAdapter.updateDownLoadSuccess(6);
                    int cur = 11;
                    setStatusDownload(getResources().getString(R.string.API_110) + " " + Const.getToday(), Const.setStatusProgressbar(cur, Const.sumDownload), cur);
                    API_110_YESTERDAY();
                }

                @Override
                public void onFail() {
                    finishDownload();
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.API_110) + " FAIL ", Toast.LENGTH_SHORT).show();
                }
            }, 1).execute();
        } else {
            API_110_YESTERDAY();
        }

    }

    boolean flag_123 = true;

    void API_123() {
        if (flag_123) {
            new HttpRequest(getApplicationContext()).new API_123_(dataLogin, new IF_123() {
                @Override
                public void onSuccess() {
                    flag_123 = false;
                    mAdapter.updateDownLoadSuccess(9);
                    int cur = 16;
                    setStatusDownload(getResources().getString(R.string.API_123), Const.setStatusProgressbar(cur, Const.sumDownload), cur);
                    API_121();
                }

                @Override
                public void onFail() {
                    finishDownload();
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.API_123) + " FAIL ", Toast.LENGTH_SHORT).show();
                }
            }).execute();
        } else {
            API_121();
        }

    }

    boolean flag_121 = true;

    void API_121() {
        if (flag_121) {
            new HttpRequest(getApplicationContext()).new API_121_(dataLogin, new IF_121() {
                @Override
                public void onSuccess() {
                    flag_121 = false;

                    int cur = 17;
                    setStatusDownload(getResources().getString(R.string.API_121) + " " + Const.getToday(), Const.setStatusProgressbar(cur, Const.sumDownload), cur);
                    API_121_Pre();
                }

                @Override
                public void onFail() {
                    finishDownload();
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.API_121) + " FAIL ", Toast.LENGTH_SHORT).show();
                }
            }, Const.getToday()).execute();
        } else {
            API_121_Pre();
        }
    }

    boolean flag_121_P = true;

    void API_121_Pre() {
        if (flag_121_P) {
            String preMonth = Const.getPreMonth() + "15";
            new HttpRequest(getApplicationContext()).new API_121_(dataLogin, new IF_121() {
                @Override
                public void onSuccess() {
                    flag_121_P = false;
                    mAdapter.updateDownLoadSuccess(10);
                    int cur = 18;
                    setStatusDownload(getResources().getString(R.string.API_121) + " " + Const.getPreMonth(), Const.setStatusProgressbar(cur, Const.sumDownload), cur);
                    API_128_TODAY();
                }

                @Override
                public void onFail() {
                    finishDownload();
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.API_121) + " FAIL ", Toast.LENGTH_SHORT).show();
                }
            }, preMonth).execute();
        } else {
            API_128_TODAY();
        }

    }

    String KEY_ROUTE = "1";
    boolean flag_112_Y = true;

    void API_112_YESTERDAY() {
        if (flag_112_Y) {
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
            Log.d(TAG, "KEY_ROUTE:" + KEY_ROUTE);

            Calendar calendar = Calendar.getInstance();
            int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
            String y = "";
            if (dayOfWeek == 2) {
                y = Const.getToday(-2);
            } else {
                y = Const.getToday(-1);
            }
            final String s = y;
            new HttpRequest(getApplicationContext()).new API_112_(dataLogin, KEY_ROUTE, new IF_112_FIRST() {
                @Override
                public void onSuccess() {
                    flag_112_Y = false;
                    API_110();
                    int cur = 10;
                    setStatusDownload("Get Route All Shop’s Information " + s, Const.setStatusProgressbar(cur, Const.sumDownload), cur);
                }

                @Override
                public void onFail() {
                    finishDownload();
                    Toast.makeText(getApplicationContext(), "API 112 FAIL ", Toast.LENGTH_SHORT).show();
                }
            }, 0).execute();
        } else {
            API_110();
        }

    }

    boolean flag_112 = true;

    void API_112() {
        if (flag_112) {
            new HttpRequest(getApplicationContext()).new API_112_(dataLogin, KEY_ROUTE, new IF_112_FIRST() {
                @Override
                public void onSuccess() {
                    flag_112 = false;
                    API_112_YESTERDAY();
                    int cur = 9;
                    setStatusDownload("Get Route All Shop’s Information " + Const.getToday(), Const.setStatusProgressbar(cur, Const.sumDownload), cur);
                }

                @Override
                public void onFail() {
                    finishDownload();
                    Toast.makeText(getApplicationContext(), "API 112 FAIL ", Toast.LENGTH_SHORT).show();
                }
            }, 1).execute();
        } else {
            API_112_YESTERDAY();
        }

    }

    boolean flag_131 = true;

    void API_131() {
        if (flag_131) {
            new HttpRequest(getApplicationContext()).new API_131_(REPORT_TODAY, dataLogin, new IF_131() {
                @Override
                public void onSuccess() {
                    flag_131 = false;
                    API_112();
                    int cur = 8;
                    setStatusDownload("RESULT NON SHOP Get Shop + Route List", Const.setStatusProgressbar(cur, Const.sumDownload), cur);
                }

                @Override
                public void onFail() {
                    finishDownload();
                    Toast.makeText(getApplicationContext(), "API 131 FAIL ", Toast.LENGTH_SHORT).show();
                }
            }).execute();
        } else {
            API_112();
        }

    }

    boolean flag_303 = true;

    void API_303() {
        if (flag_303) {
            new HttpRequest(getApplicationContext()).new API_303_(REPORT_TODAY, dataLogin, new IF_303() {
                @Override
                public void onSuccess() {
                    flag_303 = false;
                    API_131();
                    int cur = 7;
                    setStatusDownload("Get Managed Dealer List", Const.setStatusProgressbar(cur, Const.sumDownload), cur);
                }

                @Override
                public void onFail() {
                    finishDownload();
                    Toast.makeText(getApplicationContext(), "API 303 FAIL ", Toast.LENGTH_SHORT).show();
                }
            }).execute();
        } else {
            API_131();
        }

    }

    boolean flag_103 = true;

    void API_103() {
        if (flag_103) {
            new HttpRequest(getApplicationContext()).new API_103_(DATE_SYNC, dataLogin, new IF_103() {
                @Override
                public void onSuccess() {
                    flag_103 = false;
                    mAdapter.updateDownLoadSuccess(5);
                    int cur = 6;
                    setStatusDownload(getResources().getString(R.string.API_103), Const.setStatusProgressbar(cur, Const.sumDownload), cur);
                    API_303();
                }

                @Override
                public void onFail() {
                    finishDownload();
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.API_103) + " FAIL ", Toast.LENGTH_SHORT).show();
                }
            }).execute();
        } else {
            API_303();
        }

    }

    boolean flag_104 = true;

    void API_104() {
        if (flag_104) {
            new HttpRequest(getApplicationContext()).new API_104_(DATE_SYNC, dataLogin, new IF_104() {
                @Override
                public void onSuccess() {
                    flag_104 = false;
                    mAdapter.updateDownLoadSuccess(4);
                    int cur = 5;
                    setStatusDownload(getResources().getString(R.string.API_104), Const.setStatusProgressbar(cur, Const.sumDownload), cur);
                    API_103();
                }

                @Override
                public void onFail() {
                    finishDownload();
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.API_104) + " FAIL ", Toast.LENGTH_SHORT).show();
                }
            }).execute();
        } else {
            API_103();
        }

    }

    boolean flag_130 = true;

    void API_130() {
        if (flag_130) {
            new HttpRequest(getApplicationContext()).new API_130_(DATE_SYNC, dataLogin, new IF_130() {
                @Override
                public void onSuccess() {
                    flag_130 = false;
                    mAdapter.updateDownLoadSuccess(3);
                    API_104();
                    int cur = 4;
                    setStatusDownload(getResources().getString(R.string.API_101), Const.setStatusProgressbar(cur, Const.sumDownload), cur);

                }

                @Override
                public void onFail() {
                    finishDownload();
                    Toast.makeText(getApplicationContext(), "API 130 FAIL ", Toast.LENGTH_SHORT).show();
                }
            }).execute();
        } else {
            API_104();
        }

    }

    public void hideFR() {
        frLoading.setVisibility(View.GONE);
    }

    void finishDownload() {
        btnTotal.setEnabled(true);
        hideFR();
    }
}
