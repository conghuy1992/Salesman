package com.orion.salesman._route._fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.Model.ParentListItem;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.orion.salesman.MainActivity;
import com.orion.salesman.OrderActivity;
import com.orion.salesman.R;
import com.orion.salesman._class.Const;
import com.orion.salesman._pref.PrefManager;
import com.orion.salesman._route._adapter.OrderParent;
import com.orion.salesman._route._object.Pie;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by maidinh on 10/8/2016.
 */
public class RouteOrderFragmentPie extends Fragment {
    private String TAG = "RouteOrderFragmentPie";
    private List<Pie> arrayList = new ArrayList<>();
    private RecyclerView recyclerView;
    OrderParent adapter;
    private TextView tvPmt;
    private TextView tvSaleManType;
    private CheckBox cbSaleManType;
    public static RouteOrderFragmentPie instance = null;
    /*
    * vitri:0 -> pie
    * vitri:1 -> gum
    * */
    private int vitri;

    public List<?> getListPie() {

        if (adapter != null) {
            return adapter.getData();
        } else {
            List<Pie> moviesList = new ArrayList<>();
            return moviesList;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (getListPie().size() > 0) {
            if (vitri == 0)
                new PrefManager(getActivity()).setRouteOrderPie(new Gson().toJson(getListPie()));
            else if (vitri == 1)
                new PrefManager(getActivity()).setRouteOrderGum(new Gson().toJson(getListPie()));
        }
    }

    public void updateList(boolean isCheck) {
        String S = new Gson().toJson(getListPie());
        Type listType = new TypeToken<ArrayList<Pie>>() {
        }.getType();
        List<Pie> moviesList = new Gson().fromJson(S, listType);
        for (int i = 0; i < moviesList.size(); i++) {
            Pie s = moviesList.get(i);
            List<Pie> arPieList = s.getArrPies();
            for (Pie p : arPieList) {
                p.setIsCheck(isCheck);
            }
        }
        parentListItems.clear();
        for (int i = 0; i < moviesList.size(); i++) {
            parentListItems.add(moviesList.get(i));
        }
        for (int i = 0; i < parentListItems.size(); i++) {
            adapter.notifyParentItemChanged(i);
        }
    }

    @SuppressLint("ValidFragment")
    public RouteOrderFragmentPie(int vitri) {
        this.vitri = vitri;
    }

    public RouteOrderFragmentPie() {

    }

    String PRDCLS1 = "";

    public void ScrollEndList(String s) {
        Log.d(TAG,"PRDCLS1:"+PRDCLS1+" : "+s);
        if (s.equals(PRDCLS1)) {
            Log.d(TAG, "ScrollEndList");
            recyclerView.smoothScrollToPosition(300);
        }
    }

    List<ParentListItem> parentListItems = new ArrayList<>();
    public LinearLayoutManager layoutManager;
    public void createRC_1(View v) {

        parentListItems.clear();
        PRDCLS1 = arrayList.get(arrayList.size() - 1).getColumnsOne();
        Log.d(TAG,"init PRDCLS1:"+PRDCLS1);
        for (int i = 0; i < arrayList.size(); i++) {
            parentListItems.add(arrayList.get(i));
        }
        RouteOrderFragmentPie orderFragmentPie=this;
        adapter = new OrderParent(getActivity(), parentListItems, orderFragmentPie);
        recyclerView = (RecyclerView) v.findViewById(R.id.recycler_view);
        layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
        progressbar.setVisibility(View.GONE);
    }

    public void setCB(boolean c) {
        cbSaleManType.setChecked(c);
        Log.e(TAG, "setCB:" + c);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    void initview(View v) {

        tvSaleManType = (TextView) v.findViewById(R.id.tvSaleManType);
        tvPmt = (TextView) v.findViewById(R.id.tvPmt);
        tvPmt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OrderActivity.instance.gotoPromotionInfoByMonth();
            }
        });
        cbSaleManType = (CheckBox) v.findViewById(R.id.cbSaleManType);
        cbSaleManType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateList(cbSaleManType.isChecked());
            }
        });
        if (MainActivity.SALESMANTYPE.equalsIgnoreCase(Const.DS)) {
            cbSaleManType.setChecked(true);
        }
        createRC_1(v);
    }


    void initDB() {
        PrefManager pref = new PrefManager(getActivity());
        String sPie = pref.getRouteOrderPie();
        String sGum = pref.getRouteOrderGum();
        if (sPie.length() == 0)
            sPie = pref.getRouteStockPie();
        if (sGum.length() == 0)
            sGum = pref.getRouteStockGum();

        Type listType = new TypeToken<List<Pie>>() {
        }.getType();
        if (vitri == 0) {
            arrayList = new Gson().fromJson(sPie, listType);
            if (pref.getRouteOrderPie().length() != 0) {
                Type type = new TypeToken<List<Pie>>() {
                }.getType();
                List<Pie> StockList = new Gson().fromJson(pref.getRouteStockPie(), type);
                for (int i = 0; i < StockList.size(); i++) {
                    List<Pie> List1 = StockList.get(i).getArrPies();
                    List<Pie> List2 = arrayList.get(i).getArrPies();
                    for (int k = 0; k < List1.size(); k++) {
                        List2.get(k).setColumnsThree(List1.get(k).getColumnsThree());
                    }
                }
            }
        } else {
            arrayList = new Gson().fromJson(sGum, listType);
            if (pref.getRouteOrderGum().length() != 0) {
                Type type = new TypeToken<List<Pie>>() {
                }.getType();
                List<Pie> StockList = new Gson().fromJson(pref.getRouteStockGum(), type);
                for (int i = 0; i < StockList.size(); i++) {
                    List<Pie> List1 = StockList.get(i).getArrPies();
                    List<Pie> List2 = arrayList.get(i).getArrPies();
                    for (int k = 0; k < List1.size(); k++) {
                        List2.get(k).setColumnsThree(List1.get(k).getColumnsThree());
                    }
                }
            }
        }
        if (arrayList != null && arrayList.size() > 0) {
            for (Pie obj : arrayList) {
                List<Pie> lst = obj.getArrPies();
                for (Pie p : lst) {
                    String Order = p.getColumnsFive();
                    if (Order.length() > 0 && !Order.equals("0")) {
                        obj.setIsCheck(true);
                    }
                }

            }
        }
    }

    ProgressBar progressbar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.route_order_fragment_pie, container, false);
        instance = this;
        progressbar = (ProgressBar) v.findViewById(R.id.progressbar);
//        initDB();
//        initview(v);
//        if (vitri == 1)
//            OrderActivity.checkClick = true;


//        initDB();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                initDB();
                initview(v);
                if (vitri == 1)
                    OrderActivity.checkClick = true;
            }
        }, 500);

        return v;
    }

}