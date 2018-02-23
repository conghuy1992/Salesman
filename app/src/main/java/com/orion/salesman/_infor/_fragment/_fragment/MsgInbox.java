package com.orion.salesman._infor._fragment._fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.orion.salesman.MainActivity;
import com.orion.salesman.R;
import com.orion.salesman.UserOrg;
import com.orion.salesman._infor._fragment._adapter.MsgAdapter;
import com.orion.salesman._infor._fragment._object.ObjA0004;
import com.orion.salesman._object.A0004;
import com.orion.salesman._pref.PrefManager;
import com.orion.salesman._sqlite.DatabaseHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by maidinh on 8/11/2016.
 */
public class MsgInbox extends Fragment implements View.OnClickListener {
    private String DATE;
    private MsgAdapter mAdapter;
    private List<ObjA0004> arrayList = new ArrayList<>();
    private RecyclerView recyclerView;
    String TAG = "MsgInbox";
    public static MsgInbox instance = null;

    public MsgInbox() {

    }

    @SuppressLint("ValidFragment")
    public MsgInbox(String DATE) {
        this.DATE = DATE;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.msg_inbox_layout, container, false);
        instance = this;
        init(v);
        handler.post(ThreadSend);
        return v;
    }


    TextView tvNodata;
    ProgressBar progressbar;


    void init(View v) {

        tvNodata = (TextView) v.findViewById(R.id.tvNodata);
        progressbar = (ProgressBar) v.findViewById(R.id.progressbar);
        recyclerView = (RecyclerView) v.findViewById(R.id.recycler_view);
        mAdapter = new MsgAdapter(getActivity(), arrayList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(mAdapter);
    }

    void startApi() {
        progressbar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
        tvNodata.setVisibility(View.GONE);
    }

    void noData() {
        progressbar.setVisibility(View.GONE);
        recyclerView.setVisibility(View.GONE);
        tvNodata.setVisibility(View.VISIBLE);
    }

    @Override
    public void onPause() {
        super.onPause();
//        handler.removeCallbacks(ThreadSend);
    }


    @Override
    public void onResume() {
        super.onResume();

        PrefManager prefManager = new PrefManager(getActivity());
        boolean flag = prefManager.isUpdateListMsg();
        if (flag) {
            DatabaseHandler db = new DatabaseHandler(getActivity());
            List<A0004> getTABLE_A0004 = db.getTABLE_A0004();
            int pos = prefManager.getUpdateAtPosition();
            prefManager.setUpdateListMsg(false);
            int realPos = getTABLE_A0004.size() - 1 - pos;
            int id = getTABLE_A0004.get(realPos).getId();
            db.updateIsRead("1", fragment_three.MsgId);

            mAdapter.updateAtPosition(pos);
            int count = 0;
            for (A0004 msg : getTABLE_A0004) {
                if (!msg.getISREAD().equals("1")) {
                    count++;
                }
            }
            count--;
            if (count > 0)
                MainActivity.instance.showTvMsg("" + count);
            else MainActivity.instance.hideTvMsg();
        }


    }

    public void stopUpdatelist() {
        handler.removeCallbacks(ThreadSend);
    }

    int countSend = 0;
    boolean isRunning = false;
    Handler handler = new Handler();
    Runnable ThreadSend = new Runnable() {

        @Override
        public void run() {
            if (!isRunning) {
                countSend++;
//                Log.d(TAG, "countSend:" + countSend);
                if (countSend % 3 == 0) {
//                    Log.d(TAG, "% 3 == 0");
                    updateList();
                    if (countSend == 300000) {
                        countSend = 0;
                    }
                }
                handler.postDelayed(ThreadSend, 1000);
            }
        }
    };

    public void updateList() {
        if (MainActivity.INFOR_PAGE == 2 && MainActivity.positionInfor == 3) {
            Log.d(TAG, "updateList");
            A00044();
        }
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                updateList();
//            }
//        }, 3000);

    }


    public void A00044() {
//        Log.d(TAG, "A00044");

        DatabaseHandler db = new DatabaseHandler(getActivity());
        List<A0004> getTABLE_A0004 = db.getTABLE_A0004();
        if (getTABLE_A0004 != null && getTABLE_A0004.size() > 0) {
            List<ObjA0004> list = new ArrayList<>();
            for (A0004 obj : getTABLE_A0004) {
                if (!obj.getOUTBOX().equals("1")) {
                    ObjA0004 objA0004 = new ObjA0004(obj.getCDATE(), obj.getCTIME(), obj.getAUTHOR(), obj.getAUTHORNM(), obj.getMSGBODY(), obj.getISREAD(), obj.getId());
                    list.add(objA0004);
                }
            }
            Collections.reverse(list);
            if (arrayList.size() != list.size()) {
                arrayList = list;
                mAdapter.updateList(list);
                progressbar.setVisibility(View.GONE);
                tvNodata.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
            }

        } else {
            noData();
        }
    }

    @Override
    public void onClick(View view) {
//        if (view == btnAdd) {
//            Intent intent = new Intent(getActivity(), UserOrg.class);
//            startActivity(intent);
//        }
    }
}