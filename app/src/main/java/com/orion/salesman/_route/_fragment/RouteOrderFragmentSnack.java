package com.orion.salesman._route._fragment;

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
import com.orion.salesman._route._adapter.OrderParentSnack;
import com.orion.salesman._route._object.Snack;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by CongHuy on 21/8/2016.
 */
public class RouteOrderFragmentSnack extends Fragment {
    private String TAG = "RouteOrderFragmentSnack";
    private List<Snack> arrayList = new ArrayList<>();
    private RecyclerView recyclerView;
    private TextView tvPmt, tvSaleManType;
    private CheckBox cbSaleManType;
    private OrderParentSnack adapter;
    RouteOrderFragmentSnack instance;

    public RouteOrderFragmentSnack() {

    }

    @Override
    public void onPause() {
        super.onPause();
        if (getData().size() > 0)
            new PrefManager(getActivity()).setRouteOrderSnack(new Gson().toJson(getData()));
    }

    List<?> getData() {
        if (adapter != null) {
            return adapter.getData();
        } else {
            List<Snack> moviesList = new ArrayList<>();
            return moviesList;
        }
    }
    String  PRDCLS1="";
    public LinearLayoutManager layoutManager;
    List<ParentListItem> parentListItems = new ArrayList<>();
    public void ScrollEndList(String s) {
        if(s.equals(PRDCLS1)){
            Log.d(TAG,"ScrollEndList");
            recyclerView.smoothScrollToPosition(300);
        }
    }
    public void createRC_1(View v) {
        parentListItems.clear();
        PRDCLS1=arrayList.get(arrayList.size()-1).getColumnsOne();
        for (int i = 0; i < arrayList.size(); i++) {
            parentListItems.add(arrayList.get(i));
        }
        adapter = new OrderParentSnack(getActivity(), parentListItems, instance);
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
//        Log.e(TAG, "setCB:" + c);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    void initview(View v) {
        tvSaleManType = (TextView) v.findViewById(R.id.tvSaleManType);
//        tvSaleManType.setText(MainActivity.SALESMANTYPE);
        cbSaleManType = (CheckBox) v.findViewById(R.id.cbSaleManType);
        cbSaleManType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                mAdapter.updateListCheckBox(cbSaleManType.isChecked());
                updateList(cbSaleManType.isChecked());
            }
        });
        if (MainActivity.SALESMANTYPE.equalsIgnoreCase(Const.DS)) {
            cbSaleManType.setChecked(true);
        }
        tvPmt = (TextView) v.findViewById(R.id.tvPmt);
        tvPmt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OrderActivity.instance.gotoPromotionInfoByMonth();
            }
        });
        createRC_1(v);
    }

    public void updateList(boolean isCheck) {
        String S = new Gson().toJson(getData());
        Type listType = new TypeToken<ArrayList<Snack>>() {
        }.getType();
        List<Snack> moviesList = new Gson().fromJson(S, listType);
        for (int i = 0; i < moviesList.size(); i++) {
//            Log.d(TAG, moviesList.get(i).getColumnsOne());
            Snack s = moviesList.get(i);
            List<Snack> arPieList = s.getArrSnacks();
            for (Snack p : arPieList) {
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


    void initDB() {
//        Toast.makeText(getActivity(),"abc:"+OrderActivity.flag,Toast.LENGTH_SHORT).show();
        PrefManager pref = new PrefManager(getActivity());
        String sSnack = pref.getRouteOrderSnack();
        if (sSnack.length() == 0)
            sSnack = pref.getRouteStockSnack();
//        String sSnack = "";
//        if (OrderActivity.flag == 3 || OrderActivity.flag == 4)
//            sSnack = pref.getRouteOrderSnack();
//        else
//            sSnack = pref.getRouteStockSnack();

        Type listType = new TypeToken<List<Snack>>() {
        }.getType();
        arrayList = new Gson().fromJson(sSnack, listType);
        if (pref.getRouteOrderSnack().length() != 0) {
            Type type = new TypeToken<List<Snack>>() {
            }.getType();
            List<Snack> StockList = new Gson().fromJson(pref.getRouteStockSnack(), type);
            for (int i = 0; i < StockList.size(); i++) {
                List<Snack> List1 = StockList.get(i).getArrSnacks();
                List<Snack> List2 = arrayList.get(i).getArrSnacks();
                for (int k = 0; k < List1.size(); k++) {
//                        Log.d(TAG, List1.get(k).getColumnsThree());
                    List2.get(k).setColumnsThree(List1.get(k).getColumnsThree());
                }
            }
        }

        if (arrayList != null && arrayList.size() > 0) {
            for (Snack obj : arrayList) {
                List<Snack> lst = obj.getArrSnacks();
                for (Snack sn : lst) {
                    String Order = sn.getColumnsSix();
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
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                initDB();
                initview(v);
            }
        }, 500);
        return v;
    }

}