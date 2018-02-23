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
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bignerdranch.expandablerecyclerview.Model.ParentListItem;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.orion.salesman.OrderActivity;
import com.orion.salesman.R;
import com.orion.salesman._pref.PrefManager;
import com.orion.salesman._route._object.Pie;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by maidinh on 5/8/2016.
 */
public class PieFragment extends Fragment {
    String TAG = "PieFragment";
    private List<Pie> arrayList = new ArrayList<>();
    private RecyclerView recyclerView;
    public static PieFragment instance = null;

    @Override
    public void onResume() {
        super.onResume();
    }

    /*
    * vitri:0 -> Pie
    * vitri:1 -> Gum
    * */
    private int vitri;

    @SuppressLint("ValidFragment")
    public PieFragment(List<Pie> arrayList, int vitri) {
        this.arrayList = arrayList;
        this.vitri = vitri;
    }

    public PieFragment() {

    }

    @Override
    public void onPause() {
        super.onPause();
//        Log.e(TAG,"onPause");
        if (getListPie().size() > 0) {
            if (vitri == 0)
                new PrefManager(getActivity()).setRouteStockPie(new Gson().toJson(getListPie()));
            else if (vitri == 1)
                new PrefManager(getActivity()).setRouteStockGum(new Gson().toJson(getListPie()));
        }

    }

    public List<?> getListPie() {
        if (expandable != null) {
            return expandable.getData();
        } else {
            List<Pie> arr = new ArrayList<>();
            return arr;
        }
    }

    StockParent expandable;
    List<ParentListItem> parentListItems = new ArrayList<>();

    public void ScrollEndList(String s) {
        if (s.equals(PRDCLS1)) {
            Log.d(TAG, "ScrollEndList");
            recyclerView.smoothScrollToPosition(300);
        }
    }

    public LinearLayoutManager layoutManager;
    String PRDCLS1 = "";

    public void initView(View v) {
        instance = this;
        parentListItems.clear();
        PRDCLS1 = arrayList.get(arrayList.size() - 1).getColumnsOne();
        for (int i = 0; i < arrayList.size(); i++) {
            parentListItems.add(arrayList.get(i));
        }
        PieFragment pieFragment = this;
        expandable = new StockParent(getActivity(), parentListItems, pieFragment);

        recyclerView = (RecyclerView) v.findViewById(R.id.rcPie);
        layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(expandable);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private ProgressBar progressbar;

    void initDB() {
        PrefManager pref = new PrefManager(getActivity());
        String s = "";
        if (vitri == 0)
            s = pref.getRouteStockPie();
        else if (vitri == 1)
            s = pref.getRouteStockGum();
        if (s.length() > 0) {
            Type listType = new TypeToken<List<Pie>>() {
            }.getType();
            this.arrayList = new Gson().fromJson(s, listType);
        } else {
        }

        if (arrayList != null && arrayList.size() > 0) {
            for (Pie obj : arrayList) {
                List<Pie> lst = obj.getArrPies();
//                Log.d(TAG,obj.getColumnsOne()+" -> "+new Gson().toJson(lst));
                for (Pie p : lst) {
                    String Stock = p.getColumnsThree();
                    String Goods = p.getGOODS();
                    if ((Stock.length() > 0 && !Stock.equals("0")) || (Goods.length() > 0 && !Goods.equals("0"))) {
                        obj.setIsCheck(true);
                    }
                }
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.pie_fragment, container, false);
        progressbar = (ProgressBar) v.findViewById(R.id.progressbar);

        initDB();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                progressbar.setVisibility(View.GONE);
                initView(v);
                if (vitri == 1)
                    OrderActivity.checkClick = true;
            }
        }, 500);
        return v;
    }
}
