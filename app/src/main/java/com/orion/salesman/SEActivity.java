package com.orion.salesman;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.orion.salesman._adaper.SeAdapterBranch;
import com.orion.salesman._class.Const;
import com.orion.salesman._class.CustomDialog;
import com.orion.salesman._class.HttpRequest;
import com.orion.salesman._interface.IF_140;
import com.orion.salesman._interface.IF_SE_BRANCH;
import com.orion.salesman._interface.OnClickDialog;
import com.orion.salesman._object.CheckUpdate;
import com.orion.salesman._object.DataLogin;
import com.orion.salesman._object.Login;
import com.orion.salesman._object.Obj140;
import com.orion.salesman._object.SeBranch;
import com.orion.salesman._pref.PrefManager;
import com.orion.salesman._sqlite.DatabaseHandler;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kr.co.eksys.nativelib.EksysNetworkException;
import kr.co.eksys.nativelib.NetworkManager;

import static com.orion.salesman._class.Const.A0008;

/**
 * Created by maidinh on 13/12/2016.
 */

public class SEActivity extends AppCompatActivity implements View.OnClickListener {
    String TAG = "SEActivity";
    LinearLayout lnTitle;
    DataLogin dataLogin;
    PrefManager prefManager;
    Spinner spBranch, spArea, spOffice, spSM, spGS, spManager;
    String BRANCH = "", AREA = "";
    TextView tvManager;
    Button btnApply;
    ProgressBar progressbar;
    TextView tvGs;
    FrameLayout frGS, frManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.se_layout);
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.color_bar));
        }
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        Const.CHA_WKDAY = "";
        prefManager = new PrefManager(getApplicationContext());
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int width = displaymetrics.widthPixels;
        int widthTabLayout = width / 4;
        lnTitle = (LinearLayout) findViewById(R.id.lnTitle);
        lnTitle.setPadding(widthTabLayout, 0, 0, 0);
        dataLogin = new Gson().fromJson(prefManager.getInfoLoginSE(), DataLogin.class);
        initView();
        getBranch();

    }

    SeAdapterBranch adapterBranch, adapterArea, adapterOffice, adapterGS, adapterSM, adapterMNG;
    List<SeBranch> listBranch = new ArrayList<>();
    List<SeBranch> listArea = new ArrayList<>();
    List<SeBranch> listAreaTemp = new ArrayList<>();
    List<SeBranch> listOffice = new ArrayList<>();
    List<SeBranch> listOfficeTemp = new ArrayList<>();
    List<SeBranch> listSM = new ArrayList<>();
    List<SeBranch> listGS = new ArrayList<>();
    List<SeBranch> listMNG = new ArrayList<>();
    String userName = "";
    String passWord = "";

    void showProgreebar() {
        if (!progressbar.isShown())
            progressbar.setVisibility(View.VISIBLE);
    }

    void hideProgreebar() {
        btnApply.setEnabled(true);
        if (progressbar.isShown())
            progressbar.setVisibility(View.GONE);
    }

    void initView() {
        progressbar = (ProgressBar) findViewById(R.id.progressbar);
        btnApply = (Button) findViewById(R.id.btnApply);
        btnApply.setOnClickListener(this);
        tvManager = (TextView) findViewById(R.id.tvManager);
        tvGs = (TextView) findViewById(R.id.tvGs);
        frGS = (FrameLayout) findViewById(R.id.frGS);
        frManager = (FrameLayout) findViewById(R.id.frManager);

        adapterBranch = new SeAdapterBranch(SEActivity.this, listBranch, 1);
        spBranch = (Spinner) findViewById(R.id.spBranch);
        spBranch.setAdapter(adapterBranch);
        spBranch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d(TAG, "adapterBranch position:" + i);
                SeBranch obj = listBranch.get(i);
                BRANCH = obj.getV1();
                getArea("A", BRANCH, "", "", "" + dataLogin.getUSERID());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        adapterArea = new SeAdapterBranch(SEActivity.this, listArea, 1);
        spArea = (Spinner) findViewById(R.id.spArea);
        spArea.setAdapter(adapterArea);
        spArea.setOnItemSelectedListener(selectedListener);

        adapterOffice = new SeAdapterBranch(SEActivity.this, listOffice, 1);
        spOffice = (Spinner) findViewById(R.id.spOffice);
        spOffice.setAdapter(adapterOffice);
        spOffice.setOnItemSelectedListener(selectedListenerOffice);

        adapterSM = new SeAdapterBranch(SEActivity.this, listSM, 2);
        spSM = (Spinner) findViewById(R.id.spSM);
        spSM.setAdapter(adapterSM);
        spSM.setOnItemSelectedListener(selectedListenerSM);

        adapterGS = new SeAdapterBranch(SEActivity.this, listGS, 2);
        spGS = (Spinner) findViewById(R.id.spGS);
        spGS.setAdapter(adapterGS);
        spGS.setOnItemSelectedListener(selectedListenerGS);

        adapterMNG = new SeAdapterBranch(SEActivity.this, listMNG, 2);
        spManager = (Spinner) findViewById(R.id.spManager);
        spManager.setAdapter(adapterMNG);
        spManager.setOnItemSelectedListener(selectedListenerMNG);
    }

    AdapterView.OnItemSelectedListener selectedListenerMNG = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            Log.d(TAG, "adapterMNG position:" + i);
            SeBranch obj = listMNG.get(i);
            AREA = obj.getV1().trim();
            getOffice("O", BRANCH, AREA, "", "" + dataLogin.getUSERID());
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    };
    AdapterView.OnItemSelectedListener selectedListenerGS = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            Log.d(TAG, "adapterGS position:" + i);
            SeBranch obj = listGS.get(i);
            getSM("S", "", "", obj.getV3().trim(), "" + dataLogin.getUSERID());
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    };
    AdapterView.OnItemSelectedListener selectedListenerSM = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            Log.d(TAG, "adapterSM position:" + i);

            SeBranch obj = listSM.get(i);
            userName = obj.getV3().trim();
            passWord = obj.getV5().trim();
            Const.smName = obj.getV4().trim();
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    };
    AdapterView.OnItemSelectedListener selectedListenerOffice = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            Log.d(TAG, "adapterOffice position:" + i);
            SeBranch obj = listOffice.get(i);
            tvGs.setText(obj.getV4().trim());
            String DEPTCD = obj.getV1().trim();
            // get list GS
            listGS.clear();
            for (SeBranch sb : listOfficeTemp) {
                if (DEPTCD.equals(sb.getV1().trim())) {
                    listGS.add(sb);
                }
            }
            if (listGS.size() > 1) {
                tvGs.setVisibility(View.GONE);
                frGS.setVisibility(View.VISIBLE);
            } else {
                tvGs.setVisibility(View.VISIBLE);
                frGS.setVisibility(View.GONE);
            }
            adapterGS.updateList(listGS);
            spGS.setSelection(0);
            selectedListenerGS.onItemSelected(null, null, 0, 0);
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    };


    AdapterView.OnItemSelectedListener selectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            Log.d(TAG, "adapterArea position:" + i);
            SeBranch obj = listArea.get(i);
            tvManager.setText(obj.getV4().trim());


            String DEPTCD = obj.getV1().trim();
            listMNG.clear();

            for (SeBranch ob : listAreaTemp) {
                if (ob.getV1().trim().equals(DEPTCD)) {
                    listMNG.add(ob);
                }
            }
            if (listMNG.size() > 1) {
                tvManager.setVisibility(View.GONE);
                frManager.setVisibility(View.VISIBLE);
            } else {
                tvManager.setVisibility(View.VISIBLE);
                frManager.setVisibility(View.GONE);
            }

            adapterMNG.updateList(listMNG);
            spManager.setSelection(0);
            selectedListenerMNG.onItemSelected(null, null, 0, 0);

        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    };


    String getJsonData(String TYPE, String BRANCH, String AREA, String GSID, String SEID) {
        Map<String, String> map = new HashMap<>();
        map.put("TYPE", TYPE);
        map.put("BRANCH", BRANCH);
        map.put("AREA", AREA);
        map.put("GSID", GSID);
        map.put("SEID", SEID);
        String strBranch = new Gson().toJson(map);
        return strBranch;
    }


    void getSM(String TYPE, String BRANCH, String AREA, String GSID, String SEID) {
        showProgreebar();
        String strBranch = getJsonData(TYPE, BRANCH, AREA, GSID, SEID);
        new HttpRequest(getApplicationContext()).new API_8(dataLogin, strBranch, new IF_SE_BRANCH() {
            @Override
            public void onSuccess(List<SeBranch> lst) {
                Log.d(TAG, "getSM onSuccess");
                listSM = lst;
                adapterSM.updateList(listSM);
                spSM.setSelection(0);
                selectedListenerSM.onItemSelected(null, null, 0, 0);
                hideProgreebar();
            }

            @Override
            public void onFail() {
                showToast("getSM onFail");
                hideProgreebar();
            }
        }).execute();
    }

    void getOffice(String TYPE, String BRANCH, String AREA, String GSID, String SEID) {
        showProgreebar();
        String strBranch = getJsonData(TYPE, BRANCH, AREA, GSID, SEID);
        new HttpRequest(getApplicationContext()).new API_8(dataLogin, strBranch, new IF_SE_BRANCH() {
            @Override
            public void onSuccess(List<SeBranch> lst) {
                Log.d(TAG, "getOffice onSuccess");
                listOfficeTemp = lst;
                listOffice.clear();

                for (SeBranch obj : lst) {
                    String DEPTCD = obj.getV1().trim();
                    boolean flag = true;
                    for (SeBranch sb : listOffice) {
                        if (DEPTCD.equals(sb.getV1().trim())) {
                            flag = false;
                            break;
                        }
                    }
                    if (flag) {
                        listOffice.add(obj);
                    }
                }

                adapterOffice.updateList(listOffice);
                spOffice.setSelection(0);
                selectedListenerOffice.onItemSelected(null, null, 0, 0);
            }

            @Override
            public void onFail() {
                showToast("getOffice onFail");
                hideProgreebar();
            }
        }).execute();
    }

    void getArea(String TYPE, String BRANCH, String AREA, String GSID, String SEID) {
        showProgreebar();
        String strBranch = getJsonData(TYPE, BRANCH, AREA, GSID, SEID);
        new HttpRequest(getApplicationContext()).new API_8(dataLogin, strBranch, new IF_SE_BRANCH() {
            @Override
            public void onSuccess(List<SeBranch> lst) {
                Log.d(TAG, "getArea onSuccess");
                listAreaTemp = lst;
                listArea.clear();
                for (SeBranch obj : lst) {
                    String DEPTCD = obj.getV1().trim();
                    boolean flag = true;
                    for (SeBranch ob : listArea) {
                        if (DEPTCD.equals(ob.getV1().trim())) {
                            flag = false;
                            break;
                        }
                    }
                    if (flag)
                        listArea.add(obj);
                }

                adapterArea.updateList(listArea);
                spArea.setSelection(0);
                selectedListener.onItemSelected(null, null, 0, 0);
            }

            @Override
            public void onFail() {
                showToast("getArea onFail");
                hideProgreebar();
            }
        }).execute();
    }

    void getBranch() {
        showProgreebar();
        String strBranch = getJsonData("B", "", "", "", "" + dataLogin.getUSERID());
        Log.d(TAG, "strBranch:" + strBranch);
        new HttpRequest(getApplicationContext()).new API_8(dataLogin, strBranch, new IF_SE_BRANCH() {
            @Override
            public void onSuccess(List<SeBranch> lst) {
                Log.d(TAG, "getParamBranch onSuccess");
                listBranch = lst;
                adapterBranch.updateList(listBranch);
            }

            @Override
            public void onFail() {
                showToast("getParamBranch onFail");
                hideProgreebar();
            }
        }).execute();
    }

    void showToast(String s) {
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
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
        nm.setTerminalInfo("", userName, "", "");
        try {
            nm.setTR('A', Const.Login);
            nm.connect(10);          // set timeout
            Login ob = new Login(userName, passWord, "" + BuildConfig.VERSION_NAME);
            String data = new Gson().toJson(ob);
            Log.d(TAG, "login param:" + data);
            Const.WriteFileLog(1, Const.Login, data);
            nm.send(data);// send body
            contents = nm.receive();
        } catch (EksysNetworkException e) {
            contents = "Error(" + e.getErrorCode() + ")";
            Log.e(TAG, "Error(" + e.getErrorCode() + "): " + e.toString());
        } finally {
            nm.close();
        }
        handler.obtainMessage(MSG_UPDATE_DISPLAY, contents).sendToTarget();
    }

    private final static int MSG_UPDATE_DISPLAY = 2;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case MSG_UPDATE_DISPLAY:

                    final String text = (String) msg.obj;
                    Log.d(TAG, "text:" + text);
                    if (text.startsWith("Error")) {
                        hideProgreebar();
                        showToast(getResources().getString(R.string.fail));
                    } else {
                        dataLogin = new Gson().fromJson(text, DataLogin.class);
                        int RESULT = dataLogin.getRESULT();
                        if (RESULT == 2) {
                            hideProgreebar();
                            showToast(getResources().getString(R.string.wrongpass));
                        } else if (RESULT == 0) {
                            if (dataLogin.getJOBGRADE().trim().equalsIgnoreCase("SM")) {
                                if (dataLogin.getMNGEMPID().length() == 0) {
                                    hideProgreebar();
                                    dialogProblem();
                                } else {
                                    DatabaseHandler db = new DatabaseHandler(SEActivity.this);
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
                                    db.DELETE_115_ALL();
                                    db.DELETE_116_ALL();
                                    db.DELETE_117_ALL();
                                    db.DELETE_TABLE_A0004();
                                    db.DELETE_ALL_TABLE_INPUT_DATA();
                                    db.DELETE_128_ALL();
                                    db.DELETE_129_ALL();
                                    hideProgreebar();

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

                                    Calendar calendar = Calendar.getInstance();
                                    int date = calendar.get(Calendar.DAY_OF_MONTH);
                                    String s2 = date + "";
                                    CheckUpdate ob = new CheckUpdate(s2, false);
                                    prefManager.setCheckUpdate(new Gson().toJson(ob));

                                    prefManager.setInfoLogin(text);
                                    Intent intent = new Intent(SEActivity.this, DownLoadActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            } else {
                                hideProgreebar();
                                Toast.makeText(getApplicationContext(), getResources().getString(R.string.notsm), Toast.LENGTH_LONG).show();
                            }
                        } else if (RESULT == 1) {
                            hideProgreebar();
                            showToast(getResources().getString(R.string.nodata));
                        } else {
                            hideProgreebar();
                            showToast(getResources().getString(R.string.unkEr));
                        }
                    }
                    break;
            }
        }
    };

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

    private void send() {
        new Thread() {
            @Override
            public void run() {
                sendTest();
            }
        }.start();
    }

    @Override
    public void onClick(View v) {
        if (v == btnApply) {
            if (userName.length() > 0 && passWord.length() > 0) {
                btnApply.setEnabled(false);
                showProgreebar();
                send();
            } else {
                if (userName.length() == 0)
                    Toast.makeText(getApplicationContext(), "can not get username", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(getApplicationContext(), "can not get password", Toast.LENGTH_SHORT).show();
            }

        }
    }

    @Override
    public void onBackPressed() {
        doBack();
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
}
