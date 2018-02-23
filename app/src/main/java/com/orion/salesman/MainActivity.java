package com.orion.salesman;
// com.SNSV.RS;

import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.DatePicker;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.orion.salesman._adaper.ViewPagerAdapter;
import com.orion.salesman._app.MyApplication;
import com.orion.salesman._app.RootActivity;
import com.orion.salesman._class.AddressConverter;
import com.orion.salesman._class.Compress;
import com.orion.salesman._class.Const;
import com.orion.salesman._class.CustomDialog;
import com.orion.salesman._class.CustomDialogTryAgain;
import com.orion.salesman._class.CustomViewPager;
import com.orion.salesman._class.GMailSender;
import com.orion.salesman._class.GpsManager;
import com.orion.salesman._class.HttpRequest;
import com.orion.salesman._class.IOUtil;
import com.orion.salesman._class.InputStreamVolleyRequest;
import com.orion.salesman._class.LocationTrackerPostGPS;
import com.orion.salesman._class.LocationTrackerSendGPS;
import com.orion.salesman._class.QuickLZ;
import com.orion.salesman._download._fragment.DownloadFragment;
import com.orion.salesman._infor._fragment.InforFragment;
import com.orion.salesman._infor._fragment._fragment.MsgInbox;
import com.orion.salesman._infor._fragment._object.CpnInfo_ListT;
import com.orion.salesman._infor._fragment._object.OrionFamily;
import com.orion.salesman._interface.DownloadOrderNonShop;
import com.orion.salesman._interface.IF_100;
import com.orion.salesman._interface.IF_115;
import com.orion.salesman._interface.IF_117;
import com.orion.salesman._interface.IF_129;
import com.orion.salesman._interface.IF_140;
import com.orion.salesman._interface.IF_2;
import com.orion.salesman._interface.LocationChangeCallback;
import com.orion.salesman._interface.OnClickDialog;
import com.orion.salesman._object.API_112;
import com.orion.salesman._object.API_121;
import com.orion.salesman._object.API_122;
import com.orion.salesman._object.API_123;
import com.orion.salesman._object.CalculatorDistance;
import com.orion.salesman._object.CheckUpdate;
import com.orion.salesman._object.DataLogin;
import com.orion.salesman._object.DownData;
import com.orion.salesman._object.DownList;
import com.orion.salesman._object.GetNewShopPromotionList;
import com.orion.salesman._object.Obj140;
import com.orion.salesman._object.PmList;
import com.orion.salesman._object.PositionAPI;
import com.orion.salesman._object.ProductInfor;
import com.orion.salesman._object.SavePositionLocaiton;
import com.orion.salesman._object.SendPosition;
import com.orion.salesman._object.TeamDisplay;
import com.orion.salesman._offline.OfflineObject;
import com.orion.salesman._pref.PrefManager;
import com.orion.salesman._result._fragment.ResultFragment;
import com.orion.salesman._result._fragment.ResultSalesFragment;
import com.orion.salesman._route._fragment.RouteFragment;
import com.orion.salesman._route._object.InforShopDetails;
import com.orion.salesman._route._object.ListInforShop;
import com.orion.salesman._route._object.RouteStockShopAPI;
import com.orion.salesman._route._object.Snack;
import com.orion.salesman._route._object.getSMWorkingDayAPI;
import com.orion.salesman._route._object.getSMWorkingDayObject;
import com.orion.salesman._services.SendGPS;
import com.orion.salesman._sqlite.DatabaseHandler;
import com.orion.salesman._summary._fragment.SalesReportFragment;
import com.orion.salesman._summary._fragment.SummaryFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.Timer;
import java.util.TimerTask;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import kr.co.eksys.nativelib.EksysNetworkException;
import kr.co.eksys.nativelib.NetworkManager;

public class MainActivity extends RootActivity {
    public static MainActivity instance = null;
    private String TAG = "MainActivity";
    private TabLayout tabLayout;
    private CustomViewPager viewPager;
    public static int checkNewOrEditShop = 0; // 0: -> new shop     1: -> edit shop  2:->trade
    private String DATA_LOGIN = "";
    public static DataLogin dataLogin;
    public static String SALESMANTYPE = "DS";
    public static String TPRDCD = "";
    public static String DEALERCD = "";
    public static String ADDRESS = "";
    public static String CUSTCD = "";
    public static String TEAM = "01";
    public static String SEQ = "";
    public static String AREA = "";
    public static String CHANNEL = "";
    public static String V7 = "";
    public static String V6 = "";
    public static int INFOR_PAGE = 0;
    public static int SHOP_POSITION = 0;
    public static int SHOP_POSITION_UPDATE_GPS;
    public static String UPDATE_GPS_LON = "";
    public static String SHOP_NAME = "";
    public static String UPDATE_GPS_LAT = "";
    public static String SHOP_CODE = "";
    public static String SHOP_ID = "";
    public static boolean STATUS_ORDER = false;
    public static int widthTabLayout = 0;
    public static int position_main = 0;
    public static int display_icon_update_stop_shop = 0;
    String DATE = "20160808";
    String DATE_SHOW = "";



    public static int width;
    public static String DATE_SYNC = "20160808";
    public static int YEAR = 0;
    public static int MONTH = 0;
    public static int DAYOFMONTH = 0;
    public static String SHOP_GRADE = "A";
    private FrameLayout frDontTouch;
    public static String MY_ADDRESS = "";
    public static int MAIN_POS = 0;
    public static String REPORT_TODAY = "";
    public static String PATH_IMG = "";
    public static String imgSaved = "";
    public static String valueOrder = "0";
    public static String valueOMI = "N";
    public static int positionInfor = 0;
    public static int positionNews = 0;

    void INIT_REPORT_TODAY() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;
        int date = cal.get(Calendar.DATE);
        REPORT_TODAY = year + Const.formatFullDate(month) + Const.formatFullDate(date);
//        Log.d(TAG, "REPORT_TODAY:" + REPORT_TODAY);
    }

    TextView tvNoty;

    public void showTV(String s) {
        tvNoty.setText(s);
        tvNoty.setVisibility(View.VISIBLE);
    }

    public void hideTV() {
        tvNoty.setVisibility(View.GONE);
    }

    public void showTvMsg(String s) {
        tvMsg.setText(s);
        tvMsg.setVisibility(View.VISIBLE);
    }

    public void hideTvMsg() {
        tvMsg.setVisibility(View.GONE);
    }

    public void showFR() {
        frLoading.setVisibility(View.VISIBLE);
    }

    public void setStatusDownload(String s, int progress, int cur, int sumDownload) {
        if (tvStatus != null && progressBar != null && tvCurrentDownload != null) {
            tvStatus.setText(s);
            tvCurrentDownload.setText("" + cur + "/" + sumDownload);
            progressBar.setProgress(progress);
        }
    }

    public void hideFR() {
        frLoading.setVisibility(View.GONE);
    }

    FrameLayout frLoading;
    TextView btnSendFileLog, tvStatus, tvCurrentDownload;
    ProgressBar progressBar;
    LinearLayout lnStatus;


    int countSend = 0;
    boolean isRunning = false;
    Handler handler = new Handler();
    CustomDialogTryAgain customDialogTryAgain;

    List<File> getAllFile() {
        List<File> list = new ArrayList<>();
        String path = Environment.getExternalStorageDirectory().toString() + Const.folder;

        File directory = new File(path);
        File[] files = directory.listFiles();

        for (int i = 0; i < files.length; i++) {
            list.add(files[i]);
        }
        return list;
    }

    String FileZip = "";

    private void zipFile() {
        List<File> files = getAllFile();

        String[] s = new String[files.size()];    //declare an array for storing the files i.e the path of your source files
        for (int i = 0; i < s.length; i++) {
            s[i] = files.get(i).getPath();
        }
        String path = Environment.getExternalStorageDirectory() + "/";
        String date = Const.getToday();
        FileZip = "ORION_SM_" + date + "_log.zip";
        String zFile = path + "ORION_SM_" + date + "_log.zip";
        Compress c = new Compress(s, zFile);   //first parameter is d files second parameter is zip file name
        c.zip();
    }


    void dialogTryAgain() {
        customDialogTryAgain = new CustomDialogTryAgain(MainActivity.this, getResources().getString(R.string.sendLogFile)
                , getResources().getString(R.string.ok), getResources().getString(R.string.cancel), new OnClickDialog() {
            @Override
            public void btnOK() {

                zipFile();
                String file_path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/";
                File dir = new File(file_path);
                if (!dir.exists())
                    dir.mkdirs();
                final File yourFile = new File(dir, FileZip);
                if (yourFile.exists()) {

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                ArrayList<File> files = new ArrayList<>();
                                files.add(yourFile);
                                ArrayList<String> sendToMail = new ArrayList<>();
                                sendToMail.add("itorionlog@gmail.com");
                                sendToMail.add(Const.S3);

                                GMailSender sender = new GMailSender(Const.S1, Const.S2);
                                String subject = FileZip + " - " + MainActivity.dataLogin.getUSERID();
                                sender.sendMail(subject,
                                        subject,
                                        Const.S3,
                                        sendToMail, files);
//                                        "itorionlog@gmail.com", yourFile);
                            } catch (Exception e) {
                                Log.e("SendMail", e.getMessage(), e);
                            }
                        }
                    }).start();
                } else {

                }
                customDialogTryAgain.dismiss();
            }

            @Override
            public void btnCancel() {
                customDialogTryAgain.dismiss();
            }
        });
        customDialogTryAgain.show();
    }

    Runnable ThreadSend = new Runnable() {

        @Override
        public void run() {
            if (!isRunning) {
                countSend++;
                Log.d(TAG, "countSend:" + countSend);
                if (countSend == 5) {
                    dialogTryAgain();
                }
                handler.postDelayed(ThreadSend, 1000);
            }
        }
    };


    private Timer timerGetMsg;
    private TimerTask timerTaskGetMsg = new TimerTask() {
        @Override
        public void run() {
            Log.d(TAG, "call API 4");
            Calendar now = Calendar.getInstance();
            int hour = now.get(Calendar.HOUR_OF_DAY);
            int minute = now.get(Calendar.MINUTE);
            Date date = Const.parseDate(hour + ":" + minute);
            if (dateCompareOne.before(date) && dateCompareTwo.after(date)) {
                new HttpRequest(MainActivity.this).new API_4(MainActivity.dataLogin, new IF_2() {
                    @Override
                    public void onSuccess(String s) {

                    }
                }).execute();
            } else {

            }
        }
    };

    void callA0004() {
        if (timerGetMsg == null)
            timerGetMsg = new Timer();
        timerGetMsg.scheduleAtFixedRate(timerTaskGetMsg, 0, Const.timeReload);
    }

    public static boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) MyApplication.getInstance().getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    TextView tvMsg;
    PrefManager prefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        prefManager = new PrefManager(getApplicationContext());
        INIT_REPORT_TODAY();
        valueOrder = "0";

        DISTANCE = 0;
        positionInfor = 0;
        positionNews = 0;
        lnStatus = (LinearLayout) findViewById(R.id.lnStatus);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        tvCurrentDownload = (TextView) findViewById(R.id.tvCurrentDownload);
        tvStatus = (TextView) findViewById(R.id.tvStatus);
        btnSendFileLog = (TextView) findViewById(R.id.btnSendFileLog);
        btnSendFileLog.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    handler.post(ThreadSend);
                    Log.d(TAG, "ACTION_DOWN");
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    countSend = 0;
                    handler.removeCallbacks(ThreadSend);
                    Log.d(TAG, "ACTION_UP");
                }
                return true;
            }
        });


        frLoading = (FrameLayout) findViewById(R.id.frLoading);
        frLoading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "frLoading");
            }
        });
        frDontTouch = (FrameLayout) findViewById(R.id.frDontTouch);
        frDontTouch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        tvNoty = (TextView) findViewById(R.id.tvNoty);
        tvMsg = (TextView) findViewById(R.id.tvMsg);

        instance = this;
        LON = 0.0;
        LAT = 0.0;
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
        DATE = TODAY;
        DATE_SYNC = TODAY;
        DATE_SHOW = year + "-" + Const.formatFullDate(month) + "-" + Const.formatFullDate(date);
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
//        int height = displaymetrics.heightPixels;
        width = displaymetrics.widthPixels;
        widthTabLayout = width / 4;
        dataLogin = new Gson().fromJson(prefManager.getInfoLogin(), DataLogin.class);

        SALESMANTYPE = "DS";
        TEAM = dataLogin.getTEAM();
        CHECKSHOPDISTANCE = dataLogin.getCHECKSHOPDISTANCE();
        SENDINTERVAL = dataLogin.getSENDINTERVAL();
        GPSINTERVAL = dataLogin.getGPSINTERVAL();
        SEARCHINTERVAL = dataLogin.getSEARCHINTERVAL();
        GPSINTERVALSEND = GPSINTERVAL * 1000;
        prefManager.setGPSINTERVALSEND(GPSINTERVALSEND);
        prefManager.setSENDINTERVAL(SENDINTERVAL);
        initVP();
//        checkDownLoad();
//        GetRouteShopStock();
        compareStringOne = dataLogin.getGPSSTARTTIME().substring(0, 2) + ":" + dataLogin.getGPSSTARTTIME().substring(2, 4);
        compareStringTwo = dataLogin.getGPSENDTIME().substring(0, 2) + ":" + dataLogin.getGPSENDTIME().substring(2, 4);
        prefManager.setcompareStringOne(compareStringOne);
        prefManager.setcompareStringTwo(compareStringTwo);

        dateCompareOne = Const.parseDate(compareStringOne);
        dateCompareTwo = Const.parseDate(compareStringTwo);
        Log.d(TAG, compareStringOne + ":" + compareStringTwo);
//        new GET_API_114().execute();
        callA0004();
        if (!isMyServiceRunning(SendGPS.class)) {
            Log.d(TAG, "startService");
            startService();
        } else {
            MyApplication.getInstance().stopService(new Intent(MyApplication.getInstance(), SendGPS.class));
            startService();
            Log.d(TAG, "isMyServiceRunning");
        }


    }


    private String compareStringOne = "07:00";
    private String compareStringTwo = "18:00";
    boolean firsttime = false;

    Date dateCompareOne;
    Date dateCompareTwo;


    int GPSINTERVALSEND = 30000;

    class GET_API_114 extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            getSMWorkingDay();
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
    }


    public void initVP() {
        viewPager = (CustomViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        customTabLayout(tabLayout);

    }


    public void gotoTabOne() {
        viewPager.setCurrentItem(0);
    }

    private void setupViewPager(final CustomViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new SummaryFragment(DATE, dataLogin), getResources().getString(R.string.menu_summary));
        adapter.addFragment(new RouteFragment(dataLogin), getResources().getString(R.string.menu_route));
        adapter.addFragment(new ResultFragment(MainActivity.REPORT_TODAY), getResources().getString(R.string.report));
        adapter.addFragment(new InforFragment(DATE), getResources().getString(R.string.menu_infor));
        adapter.addFragment(new DownloadFragment(), getResources().getString(R.string.download));
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(4);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
//                    Log.d(TAG,"position = 0");
                    SalesReportFragment.instance.updateDate(MainActivity.DATE_SYNC);
                } else if (position == 1) {
                    MAIN_POS = 1;
                    RouteFragment.instance.updateDate();
                } else if (position == 2) {
                    ResultSalesFragment.instance.updateDate(MainActivity.REPORT_TODAY);
                }
                MainActivity.position_main = position;

                MainActivity.positionInfor = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        Calendar calendar = Calendar.getInstance();
        int date = calendar.get(Calendar.DAY_OF_MONTH);
        String s = new PrefManager(this).getCheckUpdate();
        String s2 = date + "";
        if (s.length() == 0) {
            viewPager.setCurrentItem(4);
            CheckUpdate ob = new CheckUpdate(s2, false);
            new PrefManager(this).setCheckUpdate(new Gson().toJson(ob));
            DisableViewPager();
        } else {
            CheckUpdate ob = new Gson().fromJson(s, CheckUpdate.class);
            if (!ob.getDATE().equals(s2)) {
                DisableViewPager();
                viewPager.setCurrentItem(4);
                CheckUpdate checkUpdate = new CheckUpdate(s2, false);
                new PrefManager(this).setCheckUpdate(new Gson().toJson(checkUpdate));
            } else {
                if (!ob.isCheck()) {
                    DisableViewPager();
                    viewPager.setCurrentItem(4);
                } else {

                }
            }
        }
    }

    public void DisableViewPager() {
        frDontTouch.setVisibility(View.VISIBLE);
        viewPager.setPagingEnabled(false);
    }

    public void EnableViewPager() {
        viewPager.setPagingEnabled(true);
        frDontTouch.setVisibility(View.GONE);
    }

    public void customTabLayout(TabLayout tabLayout) {
        View tabOne = LayoutInflater.from(this).inflate(R.layout.custom_tablayout_layout, null);
        TextView tvTitle = (TextView) tabOne.findViewById(R.id.tvTitle);
        tvTitle.setText(getResources().getString(R.string.menu_summary));
        ImageView imgIcon = (ImageView) tabOne.findViewById(R.id.imgIcon);
        imgIcon.setImageResource(R.drawable.icon_tab_summary);
        tabLayout.getTabAt(0).setCustomView(tabOne);

        View tabTwo = LayoutInflater.from(this).inflate(R.layout.custom_tablayout_layout, null);
        TextView tvTitleTwo = (TextView) tabTwo.findViewById(R.id.tvTitle);
        tvTitleTwo.setText(getResources().getString(R.string.menu_route));
        ImageView imgIcon2 = (ImageView) tabTwo.findViewById(R.id.imgIcon);
        imgIcon2.setImageResource(R.drawable.icon_tab_route);
        tabLayout.getTabAt(1).setCustomView(tabTwo);

        View tabThree = LayoutInflater.from(this).inflate(R.layout.custom_tablayout_layout, null);
        TextView tvTitleThree = (TextView) tabThree.findViewById(R.id.tvTitle);
        tvTitleThree.setText(getResources().getString(R.string.report));
        ImageView imgIcon3 = (ImageView) tabThree.findViewById(R.id.imgIcon);
        imgIcon3.setImageResource(R.drawable.icon_tab_result);
        tabLayout.getTabAt(2).setCustomView(tabThree);

        View tabFour = LayoutInflater.from(this).inflate(R.layout.custom_tablayout_layout, null);
        TextView tvTitleFour = (TextView) tabFour.findViewById(R.id.tvTitle);
        tvTitleFour.setText(getResources().getString(R.string.menu_infor));
        ImageView imgIcon4 = (ImageView) tabFour.findViewById(R.id.imgIcon);
        imgIcon4.setImageResource(R.drawable.icon_tab_info);
        tabLayout.getTabAt(3).setCustomView(tabFour);

        View tabFive = LayoutInflater.from(this).inflate(R.layout.custom_tablayout_layout, null);
        TextView tvTitleFive = (TextView) tabFive.findViewById(R.id.tvTitle);
        tvTitleFive.setText(getResources().getString(R.string.download));
        ImageView imgIcon5 = (ImageView) tabFive.findViewById(R.id.imgIcon);
        imgIcon5.setImageResource(R.drawable.icon_tab_download);
        tabLayout.getTabAt(4).setCustomView(tabFive);

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
//                Log.d(TAG, "position:" + tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    CustomDialog mydialog;

    void doBack() {
        mydialog = new CustomDialog(this, getResources().getString(R.string.wantexit), new OnClickDialog() {
            @Override
            public void btnOK() {
                mydialog.dismiss();
                finish();
//                android.os.Process.killProcess(android.os.Process.myPid());
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


    private void getSMWorkingDay() {
        int check = 0;
        String s = new PrefManager(this).getSmWorkDay();
        if (s.length() != 0) {
            OfflineObject ob = new Gson().fromJson(s, OfflineObject.class);
            String date = ob.getDATE();
            if (!date.equals(DATE)) {
                check = 1;
            } else {
                Log.d(TAG, "getSMWorkingDay offline ");
                getSMWorkingDayAPI routeList = new Gson().fromJson(ob.getDATA(), getSMWorkingDayAPI.class);
                if (routeList.getRESULT() == 0) {
                    List<getSMWorkingDayObject> lst = routeList.getLIST();
                    for (getSMWorkingDayObject obj : lst) {
                        Log.d(TAG, "obj:" + obj.getV3() + "   -    " + obj.getV1());
                    }

                } else {
                    Log.e(TAG, "getSMWorkingDay Fail");
                }
            }
        } else {
            check = 1;
        }
        String idLogin = dataLogin.getUSERID() + "";
        if ((Const.checkInternetConnection(this)
                && Const.PHONENUMBER.length() > 0
                && idLogin.trim().length() > 0
                && dataLogin.getDEPTCD().trim().length() > 0
                && dataLogin.getJOBCODE().trim().length() > 0)
                || (Const.checkInternetConnection(this) && dataLogin.getJOBGRADE().trim().equals(Const.SE))) {
            NetworkManager nm = new NetworkManager(Const.IP, Const.PORT);
            nm.setTerminalInfo(Const.PHONENUMBER, idLogin, dataLogin.getDEPTCD(), dataLogin.getJOBCODE());
            String contents = "";
            try {
                nm.setTR('A', Const.GetSMWorkDay);
                nm.connect(10);// set timeout
//            RouteShopStockAPI ob = new RouteShopStockAPI("" + DAY_OF_WEEK);
                nm.send("");
                contents = nm.receive();
                getSMWorkingDayAPI routeList = new Gson().fromJson(contents, getSMWorkingDayAPI.class);
                if (routeList.getRESULT() == 0) {
                    OfflineObject offlineObject = new OfflineObject(DATE, contents);
                    new PrefManager(this).setSmWorkDay(new Gson().toJson(offlineObject));
                } else {
                    Log.e(TAG, "getSMWorkingDay Fail");
                }
                Log.d(TAG, "getSMWorkingDay: " + contents);
//            Const.longInfo("getRoute",contents);
            } catch (EksysNetworkException e) {
                contents = "getSMWorkingDay Error(" + e.getErrorCode() + ")";
                Log.e(TAG, "getSMWorkingDay Error(" + e.getErrorCode() + "): " + e.toString());
            } finally {
                nm.close();
            }
        }
    }

    Handler handlerUpdateGPS = new Handler();
    boolean isUpdateGPS = false;
    int SENDINTERVAL = 30;
    int GPSINTERVAL = 10;
    public static int CHECKSHOPDISTANCE = 50;
    public static int SEARCHINTERVAL = 10;


    @Override
    protected void onPause() {
        super.onPause();
        resume = false;

    }

    public static double LON = 0.0, LAT = 0.0;

    @Override
    protected void onResume() {
        super.onResume();
        MainActivity.display_icon_update_stop_shop = 0;
        PATH_IMG = "";
        imgSaved = "";
        resume = true;
        if (!new PrefManager(this).isLogout()) {
            finish();
        }


    }


    boolean resume = false;
    int b = 0;
    Runnable runnableUpdateGPS = new Runnable() {

        @Override
        public void run() {
            if (!isUpdateGPS) {
                b++;
                if (b % SEARCHINTERVAL == 0) {
                    if (resume && MainActivity.position_main == 1) {
                        updateAdapter();
                    }
                }
                if (b == 1000)
                    b = 0;
                handlerUpdateGPS.postDelayed(runnableUpdateGPS, 1000);
            }
        }
    };

    public static long DISTANCE = 0;

    public void updateAdapter() {
        Log.d(TAG, "updateAdapter");
        double LAT = MainActivity.LAT;
        double LON = MainActivity.LON;
        if (LAT != 0 && LON != 0 && MainActivity.UPDATE_GPS_LAT.length() != 0
                && MainActivity.UPDATE_GPS_LON.length() != 0) {
            double lat1 = (double) Integer.parseInt(MainActivity.UPDATE_GPS_LAT) / Const.GPS_WGS84;
            double lon1 = (double) Integer.parseInt(MainActivity.UPDATE_GPS_LON) / Const.GPS_WGS84;
            long distance = Const.getDistanceBetweenTwoPoints(lat1, lon1, LAT, LON);
            if (distance < 0)
                distance = 0;
            DISTANCE = distance;
            if (distance <= MainActivity.CHECKSHOPDISTANCE) {
                RouteFragment.instance.updateGPS(MainActivity.SHOP_POSITION_UPDATE_GPS, true);
            } else {
                RouteFragment.instance.updateGPS(MainActivity.SHOP_POSITION_UPDATE_GPS, false);
            }
            Log.d(TAG, "distance: " + distance + " m");

        } else {
            DISTANCE = 999999;
            RouteFragment.instance.updateGPS(MainActivity.SHOP_POSITION_UPDATE_GPS, false);
        }
    }


    public void updateGPS() {
        handlerUpdateGPS.post(runnableUpdateGPS);
    }

    public void removeUpdateGPS() {
        handlerUpdateGPS.removeCallbacks(runnableUpdateGPS);
        b = 0;
    }

    // Method to start the service
    public void startService() {
        Intent intent = new Intent(MyApplication.getInstance(), SendGPS.class);
        MyApplication.getInstance().startService(intent);
    }

    // Method to stop the service
    public void stopService() {
//        stopService(new Intent(this, SendGPS.class));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocationTrackerSendGPS.getInstance().stopLocationUpdates();
        removeUpdateGPS();
        compareStringOne = "";
        compareStringTwo = "";
        stopService();
        if (MsgInbox.instance != null) {
            MsgInbox.instance.stopUpdatelist();
        }
        if (timerGetMsg != null)
            timerGetMsg.cancel();
        Log.d(TAG, "onDestroy");
//        android.os.Process.killProcess(android.os.Process.myPid());
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            super.onActivityResult(requestCode, resultCode, data);
            if (requestCode == Const.REQUEST_CODE) {
                String requiredValue = data.getStringExtra(Const.UPDATE_POSITION);
                SavePositionLocaiton savePositionLocaiton = new Gson().fromJson(requiredValue, SavePositionLocaiton.class);
                int pos = savePositionLocaiton.getPosition();
                long LON = savePositionLocaiton.getLON();
                long LAT = savePositionLocaiton.getLAT();
                MainActivity.UPDATE_GPS_LON = "" + LON;
                MainActivity.UPDATE_GPS_LAT = "" + LAT;
                MainActivity.instance.removeUpdateGPS();
                MainActivity.instance.updateGPS();
                RouteFragment.instance.updateLocation(requiredValue);
            } else if (requestCode == Const.REQUEST_NEW_SHOP) {
                String requiredValue = data.getStringExtra(Const.UPDATE_POSITION);
                RouteFragment.instance.AfterNewShop(MainActivity.SHOP_POSITION, requiredValue);
            } else if (requestCode == Const.REQUEST_UPDATE_SHOP) {
                String requiredValue = data.getStringExtra(Const.UPDATE_POSITION);
                RouteFragment.instance.reloadListShop(MainActivity.SHOP_POSITION, requiredValue);
            } else if (requestCode == Const.REQUEST_CONFIRM_SHOP) {

            }

        } catch (Exception ex) {
//            Toast.makeText(getApplicationContext(), ex.toString(),
//                    Toast.LENGTH_SHORT).show();
        }
    }
}
