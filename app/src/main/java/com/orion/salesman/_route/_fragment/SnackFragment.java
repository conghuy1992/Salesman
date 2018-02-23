package com.orion.salesman._route._fragment;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.bignerdranch.expandablerecyclerview.Model.ParentListItem;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.orion.salesman.R;
import com.orion.salesman._pref.PrefManager;
import com.orion.salesman._route._adapter.StockParentSnack;
import com.orion.salesman._route._object.Snack;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by maidinh on 5/8/2016.
 */
public class SnackFragment extends Fragment {
    private String TAG = "SnackFragment";
    private List<Snack> arrayList = new ArrayList<>();
    private RecyclerView recyclerView;
    String  PRDCLS1="";
    StockParentSnack adapter;
    public LinearLayoutManager layoutManager;
    List<ParentListItem> parentListItems = new ArrayList<>();
    public void ScrollEndList(String s) {
        if(s.equals(PRDCLS1)){
            Log.d(TAG,"ScrollEndList");
            recyclerView.smoothScrollToPosition(300);
        }
    }
    public void initView(View v) {
        progressbar.setVisibility(View.GONE);
        parentListItems.clear();
        PRDCLS1=arrayList.get(arrayList.size()-1).getColumnsOne();
        for (int i = 0; i < arrayList.size(); i++) {
            parentListItems.add(arrayList.get(i));
        }
        SnackFragment snackFragment=this;
        adapter = new StockParentSnack(getActivity(), parentListItems,snackFragment);
        recyclerView = (RecyclerView) v.findViewById(R.id.rcSnack);
        layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
    }

    @SuppressLint("ValidFragment")
    public SnackFragment(List<Snack> arrayList) {
        this.arrayList = arrayList;
    }

    public SnackFragment() {

    }

    @Override
    public void onPause() {
        super.onPause();
        if (getListPie().size() > 0)
            new PrefManager(getActivity()).setRouteStockSnack(new Gson().toJson(getListPie()));
    }

    public List<?> getListPie() {
        if (adapter != null) {
            return adapter.getData();
        } else {
            List<Snack> arr = new ArrayList<>();
            return arr;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private ProgressBar progressbar;

    void initDB() {
        PrefManager pref = new PrefManager(getActivity());
        String s = pref.getRouteStockSnack();
        if (s.length() > 0) {
            Type listType = new TypeToken<List<Snack>>() {
            }.getType();
            this.arrayList = new Gson().fromJson(s, listType);
        } else {
        }
        if (arrayList != null && arrayList.size() > 0) {
            for (Snack obj : arrayList) {
                List<Snack> lst = obj.getArrSnacks();
                for (Snack sn : lst) {
                    String Stock = sn.getColumnsThree();
                    String Goods = sn.getColumnsFour();
                    if ((Stock.length() > 0 && !Stock.equals("0")) || (Goods.length() > 0 && !Goods.equals("0"))) {
                        obj.setIsCheck(true);
                    }
                }
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.snack_fragment, container, false);

        progressbar = (ProgressBar) v.findViewById(R.id.progressbar);
        initDB();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                initView(v);
            }
        }, 500);
        return v;
    }
}
