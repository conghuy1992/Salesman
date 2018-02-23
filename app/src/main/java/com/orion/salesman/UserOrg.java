package com.orion.salesman;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.orion.salesman._class.Const;
import com.orion.salesman._class.HttpRequest;
import com.orion.salesman._class.UserTree;
import com.orion.salesman._infor._fragment._object.ListA0003;
import com.orion.salesman._infor._fragment._object.ObjA0003;
import com.orion.salesman._infor._fragment._object.TreeMsg;
import com.orion.salesman._interface.IF_2;
import com.orion.salesman._object.A0004;
import com.orion.salesman._object.RESULTOBJECT;
import com.orion.salesman._pref.PrefManager;
import com.orion.salesman._sqlite.DatabaseHandler;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by maidinh on 31/10/2016.
 */
public class UserOrg extends AppCompatActivity implements View.OnClickListener {
    String TAG = "UserOrg";
    LinearLayout rootTree;
    LinearLayout lnChild;
    ImageView onBackShop;
    LinearLayout lnTitle;
    EditText edMsg;
    Button btnSend;
    ProgressBar progressbar;
    TextView tvNodata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.user_org_layout);
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.color_bar));
        }
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        lnTitle = (LinearLayout) findViewById(R.id.lnTitle);
        lnTitle.setPadding(MainActivity.widthTabLayout, 0, 0, 0);
        rootTree = (LinearLayout) findViewById(R.id.rootTree);
        progressbar = (ProgressBar) findViewById(R.id.progressbar);
        tvNodata = (TextView) findViewById(R.id.tvNodata);
        btnSend = (Button) findViewById(R.id.btnSend);
        btnSend.setOnClickListener(this);
        edMsg = (EditText) findViewById(R.id.edMsg);
        lnChild = (LinearLayout) findViewById(R.id.lnChild);
        onBackShop = (ImageView) findViewById(R.id.onBackShop);
        onBackShop.setOnClickListener(this);
        callA0003();
    }

    List<TreeMsg> treeMsgList = new ArrayList<>();

    void callA0003() {
        Log.d(TAG, "callA0003");
        new HttpRequest(this).new API_3(MainActivity.dataLogin, new IF_2() {
            @Override
            public void onSuccess(String s) {
                if (s != null && s.length() > 0) {
                    Log.d(TAG, "data:" + s);
                    ListA0003 list = new Gson().fromJson(s, ListA0003.class);
                    if (list.getRESULT() == 0) {
                        List<ObjA0003> lst = list.getLIST();
                        if (lst != null && lst.size() > 0) {
                            for (ObjA0003 obj1 : lst) {
                                boolean flag = true;
                                for (TreeMsg obj2 : treeMsgList) {
                                    if (obj2.getV1().equals(obj1.getV1())) {
                                        flag = false;
                                        break;
                                    }
                                }
                                if (flag) {
                                    TreeMsg obj = new TreeMsg();
                                    obj.setV1(obj1.getV1());
                                    obj.setV2(obj1.getV2());
                                    obj.setImage(R.drawable.img_open);
                                    obj.setLevel(1);
                                    obj.setMarginLeft((int) getResources().getDimension(R.dimen.mg_left_tree_lv1));
                                    treeMsgList.add(obj);
                                }
                            }
                            for (ObjA0003 obj1 : lst) {
                                for (TreeMsg obj2 : treeMsgList) {
                                    if (obj2.getV1().equals(obj1.getV1())) {
                                        boolean flag = true;
                                        List<TreeMsg> lstMsg = obj2.getListChild();
                                        for (TreeMsg msgChild : lstMsg) {
                                            if (msgChild.getV1().equals(obj1.getV3())) {
                                                flag = false;
                                                break;
                                            }
                                        }
                                        if (flag) {
                                            TreeMsg obj = new TreeMsg();
                                            obj.setV1(obj1.getV3());
                                            obj.setV2(obj1.getV4());
                                            obj.setImage(R.drawable.img_open);
                                            obj.setMarginLeft((int) getResources().getDimension(R.dimen.mg_left_tree_lv2));
                                            obj.setLevel(2);
                                            lstMsg.add(obj);
                                            obj2.setListChild(lstMsg);
                                        }

                                    }
                                }
                            }
                            for (ObjA0003 obj1 : lst) {
                                for (TreeMsg obj2 : treeMsgList) {
                                    if (obj2.getV1().equals(obj1.getV1())) {
                                        List<TreeMsg> lstMsg = obj2.getListChild();// list area
                                        for (TreeMsg area : lstMsg) {
                                            boolean flag = true;
                                            List<TreeMsg> lstArea = area.getListChild();
                                            for (TreeMsg of : lstArea) {
                                                if (obj1.getV5().equals(of.getV1())) {
                                                    flag = false;
                                                    break;
                                                }
                                            }
                                            if (flag) {
                                                TreeMsg office = new TreeMsg();
                                                office.setV1(obj1.getV5());
                                                office.setV2(obj1.getV6());
                                                office.setLevel(3);
                                                office.setImage(R.drawable.img_open);
                                                office.setMarginLeft((int) getResources().getDimension(R.dimen.mg_left_tree_lv3));
                                                lstArea.add(office);
                                                area.setListChild(lstArea);
                                            }
                                        }
                                    }
                                }
                            }
                            for (ObjA0003 obj1 : lst) {
                                for (TreeMsg obj2 : treeMsgList) {
                                    if (obj2.getV1().equals(obj1.getV1())) {
                                        List<TreeMsg> lstMsg = obj2.getListChild();// list area
                                        for (TreeMsg area : lstMsg) {
                                            List<TreeMsg> lstOffice = area.getListChild();// list office
                                            for (TreeMsg msgOf : lstOffice) {
                                                List<TreeMsg> lstGS = msgOf.getListChild();
                                                boolean flag = true;
                                                for (TreeMsg GS : lstGS) {
                                                    if (obj1.getV7().equals(GS.getV1())) {
                                                        flag = false;
                                                        break;
                                                    }
                                                }

                                                if (flag) {
                                                    TreeMsg gsid = new TreeMsg();
                                                    gsid.setV1(obj1.getV7());
                                                    gsid.setV2(obj1.getV8());
                                                    gsid.setImage(R.drawable.img_sm);
                                                    gsid.setMarginLeft((int) getResources().getDimension(R.dimen.mg_left_tree_lv4));
                                                    gsid.setLevel(4);
                                                    lstGS.add(gsid);
                                                    msgOf.setListChild(lstGS);
                                                }
                                            }
                                        }
                                    }
                                }
                            }

                            for (ObjA0003 obj1 : lst) {
                                for (TreeMsg obj2 : treeMsgList) {
                                    if (obj2.getV1().equals(obj1.getV1())) {
                                        List<TreeMsg> lstMsg = obj2.getListChild();// list area
                                        for (TreeMsg area : lstMsg) {
                                            List<TreeMsg> lstOffice = area.getListChild();// list office
                                            for (TreeMsg msgOf : lstOffice) {
                                                List<TreeMsg> lstGS = msgOf.getListChild();
                                                for (TreeMsg sm : lstGS) {
                                                    List<TreeMsg> listsm = sm.getListChild();
                                                    boolean flag = true;
                                                    for (TreeMsg lstSM : listsm) {
                                                        if (lstSM.getV1().equals(obj1.getV9())) {
                                                            flag = false;
                                                            break;
                                                        }
                                                    }
                                                    if (flag) {
                                                        TreeMsg smId = new TreeMsg();
                                                        smId.setV1(obj1.getV9());
                                                        smId.setV2(obj1.getV10());
                                                        smId.setImage(R.drawable.img_sm);
                                                        smId.setLevel(5);
                                                        smId.setMarginLeft((int) getResources().getDimension(R.dimen.mg_left_tree_lv5));
                                                        listsm.add(smId);
                                                        sm.setListChild(listsm);
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }

                            if (treeMsgList != null && treeMsgList.size() > 0) {
                                draw(treeMsgList, rootTree);
                                progressbar.setVisibility(View.GONE);
                            } else {
                                progressbar.setVisibility(View.GONE);
                                tvNodata.setVisibility(View.VISIBLE);
                            }


                            Const.longInfo(TAG, "data:" + new Gson().toJson(treeMsgList));

                        } else {
                            // no data
                        }
                    } else {
                        // no data
                    }
                } else {
                    // time out
                }
            }
        }).execute();
    }

    void draw(List<TreeMsg> treeMsgList, LinearLayout layout) {
        for (final TreeMsg obj : treeMsgList) {
            TextView tvMsg;
            LinearLayout lnRoot;
            final LinearLayout child_list;
            final Button folderIcon;
            CheckBox cbSend;
//            UserTree brandLayout = new UserTree(UserOrg.this, obj, obj.getMarginLeft(), obj.getImage());
//            layout.addView(brandLayout);
            LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.tree_msg_layout, null);
//            view.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            layout.addView(view);
            lnRoot = (LinearLayout) view.findViewById(R.id.lnRoot);
            child_list = (LinearLayout) view.findViewById(R.id.child_list);
            folderIcon = (Button) view.findViewById(R.id.img);
            tvMsg = (TextView) view.findViewById(R.id.tvMsg);
            cbSend = (CheckBox) view.findViewById(R.id.cbSend);
            String UserID = "" + MainActivity.dataLogin.getUSERID();
//            if (obj.getLevel() == 1 || obj.getLevel() == 2 || obj.getV1().equals(UserID)) {
            if (obj.getV1().equals(UserID)) {
                cbSend.setEnabled(false);
            }
            cbSend.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (b) {
                        obj.setCheckBox(true);
                    } else {
                        obj.setCheckBox(false);
                    }
                }
            });
            String userID = "" + MainActivity.dataLogin.getUSERID();
            if (userID.equals(obj.getV1())) {
                folderIcon.setBackgroundTintList(getResources().getColorStateList(R.color.tint_list));
            }
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    (int) getResources().getDimension(R.dimen.heightTree));
            params.setMargins(obj.getMarginLeft(), 0, 0, 0);
            lnRoot.setLayoutParams(params);
            lnRoot.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d(TAG, obj.getV2());
                    if (obj.getListChild() != null && obj.getListChild().size() > 0) {
                        showHideSubMenuView(child_list, folderIcon, obj.getLevel());
                    }
                }
            });

            tvMsg.setText(obj.getV2());
            folderIcon.setBackgroundResource(obj.getImage());


            if (obj.getListChild() != null && obj.getListChild().size() > 0) {
                draw(obj.getListChild(), child_list);
            }
        }
    }

    private void showHideSubMenuView(LinearLayout child_list, Button icon, int level) {
        if (child_list.getVisibility() == View.VISIBLE) {
            child_list.setVisibility(View.GONE);
            if (level == 1 || level == 2 || level == 3)
                icon.setBackgroundResource(R.drawable.img_close);
        } else {
            child_list.setVisibility(View.VISIBLE);
            if (level == 1 || level == 2 || level == 3)
                icon.setBackgroundResource(R.drawable.img_open);

        }
    }

    @Override
    public void onClick(View view) {
        if (view == onBackShop) {
            finish();
        } else if (view == btnSend) {
            final String edit = edMsg.getText().toString().trim();
            if (edit.length() == 0) {
                Toast.makeText(getApplicationContext(), "You must input message", Toast.LENGTH_SHORT).show();
            } else {
                if (treeMsgList != null && treeMsgList.size() > 0) {
                    btnSend.setEnabled(false);
                    lstSend.clear();
                    getListMsgSend(treeMsgList);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (lstSend.size() > 0) {
                                for (TreeMsg obj : lstSend) {
                                    final String V1 = obj.getV1();
                                    final String V2 = obj.getV2();

                                    Calendar now = Calendar.getInstance();

                                    int YEAR = now.get(Calendar.YEAR);
                                    int MONTH = now.get(Calendar.MONTH) + 1;
                                    int DATE = now.get(Calendar.DATE);

                                    int HOUR_OF_DAY = now.get(Calendar.HOUR_OF_DAY);
                                    int MINUTE = now.get(Calendar.MINUTE);
                                    int SECOND = now.get(Calendar.SECOND);
                                    String CDATE = YEAR + Const.formatFullDate(MONTH) + Const.formatFullDate(DATE);
                                    String CTIME = Const.formatFullDate(HOUR_OF_DAY) + Const.formatFullDate(MINUTE) + Const.formatFullDate(SECOND);
//                                    Log.d(TAG,"CDATE:"+CDATE+" CTIME:"+CTIME);
                                    DatabaseHandler db = new DatabaseHandler(UserOrg.this);
                                    A0004 a0004 = new A0004(CDATE, CTIME, V1, V2, edit, "1", "1", Const.getToday());
                                    db.ADD_TABLE_A0004(a0004);
                                    new PrefManager(getApplicationContext()).setOutBox(true);

                                    String id = "";
                                    String AREACD = "";
                                    String BRANCHCD = "";
                                    int level = obj.getLevel();
                                    if (level == 4 || level == 5) {
                                        id = obj.getV1();
                                    } else {
                                        if (level == 1) {
                                            BRANCHCD = obj.getV1();
                                        } else if (level == 2) {
                                            AREACD = obj.getV1();
                                        }
                                    }
                                    new HttpRequest(getApplicationContext()).new API_6(MainActivity.dataLogin, id, edit, AREACD, BRANCHCD, new IF_2() {
                                        @Override
                                        public void onSuccess(String s) {
                                            Log.d(TAG, "onSuccess:" + s);
                                            if (s != null && s.length() > 0) {
                                                RESULTOBJECT ob = new Gson().fromJson(s, RESULTOBJECT.class);
                                                if (ob.getRESULT() == 0) {
                                                    Toast.makeText(getApplicationContext(), "send msg ok", Toast.LENGTH_SHORT).show();
                                                    countSend++;
                                                    if (countSend == lstSend.size()) {
                                                        finish();
                                                    }
                                                } else {
                                                    countSend = 0;
                                                    Toast.makeText(getApplicationContext(), "send msg fail", Toast.LENGTH_SHORT).show();
                                                }
                                            } else {
                                                countSend = 0;
                                                Toast.makeText(getApplicationContext(), "send msg fail", Toast.LENGTH_SHORT).show();

                                            }
                                        }
                                    }).execute();
                                }
                                btnSend.setEnabled(true);
                            } else {
                                btnSend.setEnabled(true);
                                Toast.makeText(getApplicationContext(), "Please specify at least one recipient", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }, 500);
//                    Toast.makeText(getApplicationContext(), "Please specify at least one recipient", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(getApplicationContext(), "can not send because dont exist list Org", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    int countSend = 0;
    List<TreeMsg> lstSend = new ArrayList<>();

    void getListMsgSend(List<TreeMsg> treeMsgList) {
        for (TreeMsg obj : treeMsgList) {
            if (obj.isCheckBox()) {
                if (obj.getLevel() == 3) {
                    if (obj.getListChild() != null && obj.getListChild().size() > 0) {
                        for (TreeMsg lstOff : obj.getListChild()) {
                            boolean flag = true;
                            for (TreeMsg msg : lstSend) {
                                if (msg.getV1().equals(lstOff.getV1())) {
                                    flag = false;
                                    break;
                                }
                            }
                            if (flag)
                                lstSend.add(lstOff);
                        }
                    }
                } else {
                    boolean flag = true;
                    for (TreeMsg msg : lstSend) {
                        if (msg.getV1().equals(obj.getV1())) {
                            flag = false;
                            break;
                        }
                    }
                    if (flag)
                        lstSend.add(obj);
                }
            }
            if (obj.getListChild() != null && obj.getListChild().size() > 0) {
                getListMsgSend(obj.getListChild());
            }
        }
    }

}
