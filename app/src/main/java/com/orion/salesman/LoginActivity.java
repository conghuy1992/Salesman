package com.orion.salesman;

import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.Dialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.orion.salesman._app.MyApplication;
import com.orion.salesman._app.RootActivity;
import com.orion.salesman._class.AESCrypt;
import com.orion.salesman._class.Const;
import com.orion.salesman._class.GMailSender;
import com.orion.salesman._class.HttpRequest;
import com.orion.salesman._class.IOUtil;
import com.orion.salesman._class.InputStreamVolleyRequest;
import com.orion.salesman._class.QuickLZ;
import com.orion.salesman._interface.DownloadDB;
import com.orion.salesman._interface.DownloadOrderNonShop;
import com.orion.salesman._interface.IF_10;
import com.orion.salesman._interface.IF_100;
import com.orion.salesman._interface.IF_140;
import com.orion.salesman._interface.IF_DELETE_IMAGE;
import com.orion.salesman._interface.IF_GET_IP;
import com.orion.salesman._object.A0004;
import com.orion.salesman._object.API_112;
import com.orion.salesman._object.API_120;
import com.orion.salesman._object.CheckUpdate;
import com.orion.salesman._object.DataLogin;
import com.orion.salesman._object.DownData;
import com.orion.salesman._object.DownList;
import com.orion.salesman._object.Login;
import com.orion.salesman._object.Obj140;
import com.orion.salesman._object.OrderNonShopListAPI;
import com.orion.salesman._offline.OfflineObject;
import com.orion.salesman._pref.PrefManager;
import com.orion.salesman._receive.LogoutReceiver;
import com.orion.salesman._services.SendGPS;
import com.orion.salesman._sqlite.DatabaseHandler;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import kr.co.eksys.nativelib.EksysNetworkException;
import kr.co.eksys.nativelib.NetworkManager;

/**
 * Created by maidinh on 5/8/2016.
 */
public class LoginActivity extends RootActivity {
    private String TAG = "LoginActivity";
    public static int check = 0;
    private EditText UserName = null;
    private EditText Password = null;
    private Button btnLogin = null;
    private ArrayList<String> sendLog = new ArrayList<String>();
    private CheckBox cbSave;
    private PrefManager prefManager;
    private Spinner spLanguage;
    private ProgressBar progressbar;
    DataLogin dataLogin;

    void hideKeyboard() {
        try {
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        } catch (Exception e) {

        }
    }

    void setLanguage(String s) {
        String languageToLoad = s; // your language
        Locale locale = new Locale(languageToLoad);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());
    }

    private List<String> listLanguage = new ArrayList<>();
    private TextView tvInfo;
    String version = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prefManager = new PrefManager(this);
        String s = prefManager.getEngLish();
        setLanguage(s);
        setContentView(R.layout.login);
        Const.inforSE = "";
        Const.CHA_WKDAY = "";
//        if (Build.VERSION.SDK_INT >= 21) {
//            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.color_bar));
//        }






        NotificationManager nMgr = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        nMgr.cancelAll();

        deleteDataReSend();
        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        tvInfo = (TextView) findViewById(R.id.tvInfo);
        version = "Ver " + BuildConfig.VERSION_NAME;
        String imei = "IMEI : " + telephonyManager.getDeviceId();
        Log.d(TAG, "imei:" + imei);
        tvInfo.setText(version + " - " + imei);


        check = 0;
        spLanguage = (Spinner) findViewById(R.id.spLanguage);
        listLanguage.add("Tiếng Việt");
        listLanguage.add("English");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listLanguage);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spLanguage.setAdapter(dataAdapter);
        spLanguage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    prefManager.setEngLish(Const.VI);
                    setLanguage(Const.VI);
                } else if (position == 1) {
                    prefManager.setEngLish(Const.EN);
                    setLanguage(Const.EN);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        if (s.equals(Const.VI))
            spLanguage.setSelection(0);
        else if (s.equals(Const.EN))
            spLanguage.setSelection(1);
        progressbar = (ProgressBar) findViewById(R.id.progressbar);
        cbSave = (CheckBox) findViewById(R.id.cbSave);
        UserName = (EditText) findViewById(R.id.UserName);
        Password = (EditText) findViewById(R.id.Password);
        btnLogin = (Button) findViewById(R.id.btnLogin);


        UserName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                if (editable.toString().length() == 7) {
                    Password.requestFocus();
                }
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new Const.getIpFromDomain(new IF_GET_IP() {
                    @Override
                    public void onSuccess(String ip) {
                        Log.d(TAG, "ip:" + ip);
                        if (ip != null && ip.length() > 0) {
                            Const.IP = ip;
                        } else {
//                    Toast.makeText(getApplicationContext(), "can not get IP", Toast.LENGTH_SHORT).show();
                        }

                    }
                }).execute();

                if (UserName.getText().toString().trim().length() == 0
                        || Password.getText().toString().trim().length() == 0) {
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.mustinput), Toast.LENGTH_SHORT).show();
                } else {
                    if (Const.checkInternetConnection(LoginActivity.this)) {
                        btnLogin.setEnabled(false);
                        hideKeyboard();
                        progressbar.setVisibility(View.VISIBLE);
                        send();
                    } else {
                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.cannotconnect), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


        boolean isCheck = prefManager.isSaveData();
        if (isCheck) {
            cbSave.setChecked(true);
            UserName.setText(prefManager.getUsername());
            String PASS = prefManager.getPassword();
            try {
                PASS = AESCrypt.decrypt(Const.keyEncrypt, PASS);
            } catch (GeneralSecurityException e) {
                e.printStackTrace();
            }
            Password.setText(PASS);
        } else {
            UserName.setText("");
            Password.setText("");
        }
//        handler.sendEmptyMessage(MSG_SEND);
        if (Const.checkInternetConnection(this)) {
//            checkDownLoad();
        } else {
            MyApplication.getInstance().showToast(getResources().getString(R.string.cannotconnect));
        }


        DatabaseHandler db = new DatabaseHandler(this);
        List<A0004> getTABLE_A0004 = db.getTABLE_A0004();
        if (getTABLE_A0004 != null && getTABLE_A0004.size() > 0) {
            for (A0004 ob : getTABLE_A0004) {
                String DATE = ob.getDATEADD();
                int ID = ob.getId();
                if (DATE.length() == 0) {
                    DATE = Const.getToday();
                }
                int year = Integer.parseInt(DATE.substring(0, 4));
                int month = Integer.parseInt(DATE.substring(4, 6)) - 1;
                int day = Integer.parseInt(DATE.substring(6, 8));

                Calendar cal1 = Calendar.getInstance();
                cal1.set(year, month, day);
                Calendar now = Calendar.getInstance();
                long diff = Const.calculateDeltaInDays(cal1, now);
                Log.d(TAG, "calculateDeltaInDays:" + diff);
                if (diff >= 7) {
                    db.DELETE_TABLE_A0004_ROW(ID);
                }
            }
        }

        removeFile();
//        try {
//            backupDatabase(this);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    public static void backupDatabase(Context context) throws IOException {
        //Open your local db as the input stream
        String inFileName = "/data/data/" +
                context.getPackageName() +
                "/databases/DATA_OFFLINE";
        File dbFile = new File(inFileName);
        FileInputStream fis = new FileInputStream(dbFile);

        String outFileName = Environment.getExternalStorageDirectory()+"/DATA_OFFLINE.db";
        //Open the empty db as the output stream
        OutputStream output = new FileOutputStream(outFileName);
        //transfer bytes from the inputfile to the outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = fis.read(buffer))>0){
            output.write(buffer, 0, length);
        }
        //Close the streams
        output.flush();
        output.close();
        fis.close();
    }

    void removeFile() {
        String path = Environment.getExternalStorageDirectory().toString() + Const.folder;
        File directory = new File(path);
        File[] files = directory.listFiles();
        if (files != null) {
            if (files.length > 0) {
                List<Integer> lstRemove = new ArrayList<>();
                for (int i = 0; i < files.length; i++) {
                    String fileName = files[i].getName();
                    Log.d(TAG, "getName:" + files[i].getName());
//            files[i].delete();

                    if (fileName.startsWith("fileLog")) {
                        lstRemove.add(i);
                    } else {
                        if (fileName.length() == 12) {
                            int year = Integer.parseInt(fileName.substring(0, 4));
                            int month = Integer.parseInt(fileName.substring(4, 6)) - 1;
                            int day = Integer.parseInt(fileName.substring(6, 8));

                            Calendar cal1 = Calendar.getInstance();
                            cal1.set(year, month, day);
                            Calendar now = Calendar.getInstance();
                            long diff = Const.calculateDeltaInDays(cal1, now);
//                    Log.d(TAG, "calculateDeltaInDays:" + diff);
                            if (diff >= 5) {
                                lstRemove.add(i);
                            }
                        }
                    }
                }
                if (lstRemove.size() > 0) {
                    for (Integer i : lstRemove) {
                        files[i].delete();
//                Log.d(TAG, "i:" + i);
                    }
                }
            }
        }

    }


    void gotoDownload() {
        Intent intent;
        if (Const.inforSE.length() > 0) {
            intent = new Intent(LoginActivity.this, SEActivity.class);
        } else {
            intent = new Intent(LoginActivity.this, DownLoadActivity.class);
        }
        startActivity(intent);
        finish();
    }

    void initCheckUpdate() {
        Calendar calendar = Calendar.getInstance();
        int date = calendar.get(Calendar.DAY_OF_MONTH);
        String s = new PrefManager(this).getCheckUpdate();
        String s2 = date + "";
        if (s.length() == 0) {
            gotoDownload();
            CheckUpdate ob = new CheckUpdate(s2, false);
            new PrefManager(this).setCheckUpdate(new Gson().toJson(ob));
        } else {
            CheckUpdate ob = new Gson().fromJson(s, CheckUpdate.class);
            if (!ob.getDATE().equals(s2)) {
                gotoDownload();
                CheckUpdate checkUpdate = new CheckUpdate(s2, false);
                new PrefManager(this).setCheckUpdate(new Gson().toJson(checkUpdate));
            } else {
                if (!ob.isCheck()) {
                    gotoDownload();
                } else {
                    API_10_1(0);
                }
            }
        }
    }

    void API_10_2(final int i) {
        new HttpRequest(getApplicationContext()).new API_10(dataLogin, i, new IF_10() {
            @Override
            public void onSuccess(String s) {
                DownList routeList = new Gson().fromJson(s, DownList.class);
                try {
                    if (routeList.getRESULT() == 0) {
                        PrefManager prefManager = new PrefManager(getApplicationContext());
                        prefManager.setCheckDownLoad(s);
                        String time = routeList.getLIST().get(i).getV2();
                        String url = routeList.getLIST().get(i).getV3();
                        String time_saved = prefManager.getTimeSaveDb(i);
                        if (!time.equals(time_saved)) {
                            gotoDownload();
                        } else {
                            Intent intent;
                            if (Const.inforSE.length() > 0) {
                                intent = new Intent(LoginActivity.this, SEActivity.class);
                            } else {
                                intent = new Intent(LoginActivity.this, MainActivity.class);
                            }

                            startActivity(intent);
                            finish();
                        }
                    } else {
                        gotoDownload();
                    }
                } catch (Exception e) {
                    gotoDownload();
                }
            }

            @Override
            public void onFail() {
                gotoDownload();
            }
        }).execute();
    }

    void API_10_1(final int i) {
        new HttpRequest(getApplicationContext()).new API_10(dataLogin, i, new IF_10() {
            @Override
            public void onSuccess(String s) {
                DownList routeList = new Gson().fromJson(s, DownList.class);
                try {
                    if (routeList.getRESULT() == 0) {
                        PrefManager prefManager = new PrefManager(getApplicationContext());
                        prefManager.setCheckDownLoad(s);
                        String time = routeList.getLIST().get(i).getV2();
                        String url = routeList.getLIST().get(i).getV3();
                        String time_saved = prefManager.getTimeSaveDb(i);
                        if (!time.equals(time_saved)) {
                            gotoDownload();
                        } else {
                            API_10_2(1);
                        }
                    } else {
                        gotoDownload();
                    }
                } catch (Exception e) {
                    gotoDownload();
                }

            }

            @Override
            public void onFail() {
                gotoDownload();
            }
        }).execute();
    }

    private final static int MSG_SEND = 1;
    private final static int MSG_UPDATE_DISPLAY = 2;


    void hideLoading() {
        progressbar.setVisibility(View.GONE);
        btnLogin.setEnabled(true);
    }

    void dialogProblem() {
        final Dialog dialog = new Dialog(this);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(R.layout.custom_dialog);
        dialog.setCanceledOnTouchOutside(false);
        TextView tvMsg = (TextView) dialog.findViewById(R.id.tvMsg);
        tvMsg.setText(getResources().getString(R.string.notGS));
        Button btnOK = (Button) dialog.findViewById(R.id.btnOK);
        Button btnCancel = (Button) dialog.findViewById(R.id.btnCancel);
        btnOK.setText(getResources().getString(R.string.tryagain));
        btnCancel.setText(getResources().getString(R.string.exit));
        btnOK.setVisibility(View.GONE);
        btnCancel.setText("OK");
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case MSG_SEND:
                    send();

                    break;
                case MSG_UPDATE_DISPLAY:

                    final String text = (String) msg.obj;
                    Log.d(TAG, "text:" + text);
                    if (text.startsWith("Error")) {
                        hideLoading();
                        MyApplication.getInstance().showToast(getResources().getString(R.string.fail));
                    } else {
                        dataLogin = new Gson().fromJson(text, DataLogin.class);
                        int RESULT = dataLogin.getRESULT();
                        if (RESULT == 2) {
                            hideLoading();
                            MyApplication.getInstance().showToast(getResources().getString(R.string.wrongpass));
                        } else if (RESULT == 0) {
                            if (dataLogin.getJOBGRADE().trim().equalsIgnoreCase("SM") || dataLogin.getJOBGRADE().trim().equalsIgnoreCase("SE")) {
                                if (dataLogin.getJOBGRADE().trim().equalsIgnoreCase("SE")) {
                                    Const.inforSE = text;
//                                    if (dataLogin.getMNGEMPID().length() == 0) {
//                                        hideLoading();
//                                        dialogProblem();
//                                    } else {
                                    prefManager.ClearData();
                                    prefManager.setCheckUpdate("");
                                    if (isMyServiceRunning(SendGPS.class)) {
                                        stopService();
                                        Log.d(TAG, "stopService");
                                    }
                                    DatabaseHandler db = new DatabaseHandler(LoginActivity.this);
                                    db.delete_All_API_112();
                                    db.delete_All_API_100();
                                    db.delete_All_API_103();
                                    db.delete_All_API_104();
                                    db.delete_All_API_110();
                                    db.delete_All_API_111();
                                    db.delete_All_API_120();
                                    db.delete_All_API_121();
                                    db.delete_All_API_122();
                                    db.delete_All_API_123();
                                    db.delete_All_API_130();
                                    db.delete_All_API_131();
                                    db.delete_All_API_132();
                                    db.delete_All_API_304();
                                    db.delete_All_API_303();
                                    db.delete_All_ORDER();
                                    db.DELETE_115_ALL();
                                    db.DELETE_116_ALL();
                                    db.DELETE_117_ALL();
                                    db.DELETE_TABLE_A0004();
                                    db.DELETE_ALL_TABLE_INPUT_DATA();
                                    db.DELETE_128_ALL();
                                    db.DELETE_129_ALL();
                                    db.deleteGPS();
                                    prefManager.setInfoLoginSE(text);
                                    setAutoLogout();
                                    new PrefManager(LoginActivity.this).setLogout(true);
                                    initCheckUpdate();
//                                    }
                                } else {
                                    prefManager.setInfoLoginSE("");
                                    if (dataLogin.getMNGEMPID().length() == 0) {
                                        hideLoading();
                                        dialogProblem();
                                    } else {
                                        String so = prefManager.getInfoLogin();
                                        final DataLogin dbOld = new Gson().fromJson(so, DataLogin.class);
                                        if (so.length() > 0) {
                                            if (dbOld.getUSERID() != dataLogin.getUSERID()) {
                                                prefManager.ClearData();
                                                prefManager.setCheckUpdate("");
                                                if (isMyServiceRunning(SendGPS.class)) {
                                                    stopService();
                                                    Log.d(TAG, "isMyServiceRunning");
                                                }

                                                DatabaseHandler db = new DatabaseHandler(LoginActivity.this);
                                                db.delete_All_API_112();
                                                db.delete_All_API_100();
                                                db.delete_All_API_103();
                                                db.delete_All_API_104();
                                                db.delete_All_API_110();
                                                db.delete_All_API_111();
                                                db.delete_All_API_120();
                                                db.delete_All_API_121();
                                                db.delete_All_API_122();
                                                db.delete_All_API_123();
                                                db.delete_All_API_130();
                                                db.delete_All_API_131();
                                                db.delete_All_API_132();
                                                db.delete_All_API_304();
                                                db.delete_All_API_303();
                                                db.delete_All_ORDER();
                                                db.DELETE_115_ALL();
                                                db.DELETE_116_ALL();
                                                db.DELETE_117_ALL();
                                                db.DELETE_TABLE_A0004();
                                                db.DELETE_ALL_TABLE_INPUT_DATA();
                                                db.DELETE_128_ALL();
                                                db.DELETE_129_ALL();
                                                db.deleteGPS();
                                            }
                                        }

                                        new HttpRequest(getApplicationContext()).new API_140(dataLogin, new IF_140() {
                                            @Override
                                            public void onSuccess(List<Obj140> LIST) {
                                                Const.CHA_WKDAY = "";
                                                if (LIST.size() > 0) {
                                                    String today = Const.getToday();
                                                    for (Obj140 obj : LIST) {
                                                        if (obj.getV1().trim().equals(today)) {
                                                            Const.CHA_WKDAY = obj.getV3().trim();
                                                            break;
                                                        }
                                                    }
                                                }

                                            }

                                            @Override
                                            public void onFail() {
                                                Const.CHA_WKDAY = "";
                                            }
                                        }).execute();

                                        prefManager.setInfoLogin(text);
                                        setAutoLogout();
                                        new PrefManager(LoginActivity.this).setLogout(true);
                                        initCheckUpdate();
                                    }
                                }
                            } else {
                                hideLoading();
                                Toast.makeText(getApplicationContext(), getResources().getString(R.string.notsm), Toast.LENGTH_LONG).show();
                            }
                        } else if (RESULT == 1) {
                            hideLoading();
                            MyApplication.getInstance().showToast(getResources().getString(R.string.nodata));
                        } else {
                            hideLoading();
                            MyApplication.getInstance().showToast(getResources().getString(R.string.unkEr));
                        }
                    }
                    break;
            }
        }
    };

    public static boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) MyApplication.getInstance().getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    public void stopService() {
        MyApplication.getInstance().stopService(new Intent(MyApplication.getInstance(), SendGPS.class));
    }


    private void setAutoLogout() {
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTimeInMillis(System.currentTimeMillis());
        calendar1.set(Calendar.HOUR_OF_DAY, 23);
        calendar1.set(Calendar.MINUTE, 59);
        calendar1.set(Calendar.SECOND, 59);

        Intent intent = new Intent(LoginActivity.this, LogoutReceiver.class);
        PendingIntent sender = PendingIntent.getBroadcast(LoginActivity.this, 0, intent, 0);

        // Get the AlarmManager service
        AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
        am.set(AlarmManager.RTC_WAKEUP, calendar1.getTimeInMillis(), sender);
    }

    private void send() {
        new Thread() {
            @Override
            public void run() {
                sendTest();
            }
        }.start();
    }


    private void sendTest() {
        String contents = "..";//lib.invokeNativeFunction("192.168.0.64", 1210);
        String ip = Const.DOMAIN;
        int port = 0;
        try {
            port = Const.PORT;
        } catch (Exception e) {
            Log.e(TAG, "Error: invalid port ");
            return;
        }
        Log.d(TAG, "load: " + ip + ":" + port);
        NetworkManager nm = new NetworkManager(ip, port);
        nm.setTerminalInfo("", UserName.getText().toString().trim(), "", "");
        try {
            nm.setTR('A', Const.Login);
            nm.connect(10);          // set timeout
            Login ob = new Login(UserName.getText().toString().trim(), Password.getText().toString().trim(), "" + BuildConfig.VERSION_NAME);
            String data = new Gson().toJson(ob);
            Log.d(TAG, "login param:" + data);


            String INFOR = "DEVICEVERSION:" + ob.getDEVICEVERSION() + " - ID:" + ob.getID()
                    + "\n" + "********************************************";

            Const.WriteFileLog(1, Const.Login, INFOR);
            nm.send(data);// send body
            contents = nm.receive();

//            Log.d(TAG, "recevied: " + contents);
        } catch (EksysNetworkException e) {
            contents = "Error(" + e.getErrorCode() + ")";
            Log.e(TAG, "Error(" + e.getErrorCode() + "): " + e.toString());
        } finally {
            nm.close();
        }
        handler.obtainMessage(MSG_UPDATE_DISPLAY, contents).sendToTarget();
    }

    @Override
    protected void onPause() {
        super.onPause();
//        if(rb)
        if (cbSave.isChecked()) {
            prefManager.setSaveData(true);
            prefManager.setUsername(UserName.getText().toString().trim());
            String strPassword = Password.getText().toString().trim();
            try {
                strPassword = AESCrypt.encrypt(Const.keyEncrypt, strPassword);
//                Log.d(TAG,"strPassword:"+strPassword);
            } catch (GeneralSecurityException e) {
                e.printStackTrace();
            }
            prefManager.setPassword(strPassword);
        } else {
            prefManager.setSaveData(false);
//            prefManager.ClearData();
        }

    }

    void deleteDataReSend() {
        DatabaseHandler db = new DatabaseHandler(this);
        Calendar calendar = Calendar.getInstance();
        int date = calendar.get(Calendar.DAY_OF_MONTH);
        String s = new PrefManager(this).getCheckUpdate();
        String s2 = date + "";
        if (s.length() == 0) {
            db.DELETE_115_ALL();
            db.DELETE_116_ALL();
            db.DELETE_117_ALL();
        } else {
            CheckUpdate ob = new Gson().fromJson(s, CheckUpdate.class);
            if (!ob.getDATE().equals(s2)) {
                db.DELETE_115_ALL();
                db.DELETE_116_ALL();
                db.DELETE_117_ALL();
            } else {
                if (!ob.isCheck()) {
                    db.DELETE_115_ALL();
                    db.DELETE_116_ALL();
                    db.DELETE_117_ALL();
                } else {

                }
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
//       Log.d(TAG,"getPreMonth:"+Const.getPreMonth());
    }
}
