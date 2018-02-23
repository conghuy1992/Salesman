package com.orion.salesman._result._fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.orion.salesman.R;
import com.orion.salesman._class.Const;
import com.orion.salesman._class.GeneralSwipeRefreshLayout;
import com.orion.salesman._object.ListAPI_304;
import com.orion.salesman._object.Object_304;
import com.orion.salesman._result._adapter.ResultPieAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by maidinh on 19/9/2016.
 */
public class ResultSnackFragment extends Fragment {
    private String TAG = "ResultSnackFragment";
    private List<Object_304> arrayList = new ArrayList<>();
    private RecyclerView recyclerView;
    private ResultPieAdapter mAdapter;
    private TextView tv1, tv2, tv3, tv4, tv5;
    Button tvNodata;
    private LinearLayout lnBottom;
    public static ResultSnackFragment instance;

    GeneralSwipeRefreshLayout swipeRefreshLayout;
    LinearLayoutManager linearLayoutManager;

    public void ScrollEndList(int Size) {
        recyclerView.smoothScrollToPosition(Size);
    }

    public void initView(View v) {
        lnBottom = (LinearLayout) v.findViewById(R.id.lnBottom);
        tv1 = (TextView) v.findViewById(R.id.tv1);
        tv2 = (TextView) v.findViewById(R.id.tv2);
        tv3 = (TextView) v.findViewById(R.id.tv3);
        tv4 = (TextView) v.findViewById(R.id.tv4);
        tv5 = (TextView) v.findViewById(R.id.tv5);
        Const.setTextColorSum(tv1);
        Const.setTextColorSum(tv2);
        Const.setTextColorSum(tv3);
        Const.setTextColorSum(tv4);
        Const.setTextColorSum(tv5);
        tvNodata = (Button) v.findViewById(R.id.tvNodata);
        ResultSnackFragment fm = this;
        mAdapter = new ResultPieAdapter(getActivity(), arrayList, null, fm, null);
        recyclerView = (RecyclerView) v.findViewById(R.id.rcResultPie);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(mAdapter);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                isScrolling = false;
//                Log.d(TAG, "onScrollStateChanged");
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                isScrolling = true;
//                Log.d(TAG, "onScrolled");
            }
        });
        swipeRefreshLayout = (GeneralSwipeRefreshLayout) v.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnChildScrollUpListener(new GeneralSwipeRefreshLayout.OnChildScrollUpListener() {
            @Override
            public boolean canChildScrollUp() {
//                return linearLayoutManager.findFirstVisibleItemPosition() > 0 ||
//                        linearLayoutManager.getChildAt(0) == null ||
//                        linearLayoutManager.getChildAt(0).getTop() < 0;
                if (!isScrolling) {
                    return linearLayoutManager.findFirstVisibleItemPosition() > 0 ||
                            linearLayoutManager.getChildAt(0) == null ||
                            linearLayoutManager.getChildAt(0).getTop() < 0;
                } else {
                    return true;
                }
            }
        });
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                ResultStockFragment.instance.send_304(false);
            }
        });
    }

    boolean isScrolling = false;

    public void dismissSwipe() {
        if (swipeRefreshLayout != null) {
            if (swipeRefreshLayout.isRefreshing()) {
                swipeRefreshLayout.setRefreshing(false);
            }
        }

    }

    public ResultSnackFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.result_pie_fragment, container, false);
        instance = this;
//        Calendar calendar = Calendar.getInstance();
//        calendar.add(Calendar.DATE, -1);
//        int year = calendar.get(Calendar.YEAR);
//        int month = calendar.get(Calendar.MONTH) + 1;
//        int date = calendar.get(Calendar.DATE);
//        DATE = year + Const.setFullDate(month) + Const.setFullDate(date);
        initView(v);

        return v;
    }

    public void reload() {
        recyclerView.setVisibility(View.GONE);
        tvNodata.setVisibility(View.GONE);
        lnBottom.setVisibility(View.GONE);
    }

    public void updateList(String contents) {
        tvNodata.setText(getResources().getString(R.string.nodata));
        if (contents.length() == 0) {
            // no internet
            tvNodata.setText(getResources().getString(R.string.cannotconnect));
            recyclerView.setVisibility(View.GONE);
            tvNodata.setVisibility(View.VISIBLE);
            lnBottom.setVisibility(View.GONE);
        } else {
            if (contents.startsWith("Error")) {
                // error
                tvNodata.setText("" + contents);
                recyclerView.setVisibility(View.GONE);
                tvNodata.setVisibility(View.VISIBLE);
                lnBottom.setVisibility(View.GONE);
            } else {
                ListAPI_304 listAPI_304 = new Gson().fromJson(contents, ListAPI_304.class);
                arrayList.clear();
                if (listAPI_304 != null && listAPI_304.getRESULT() == 0) {
                    List<Object_304> LIST = listAPI_304.getLIST();
                    for (int i = 0; i < LIST.size(); i++) {
                        Object_304 ob = LIST.get(i);
                        if (ob.getV1().equals(Const.STEAM) && ob.getV4().equals("T") && !ob.getV2().equals("T")) {
                            arrayList.add(ob);
                        }
                    }
                    for (int i = 0; i < arrayList.size(); i++) {
                        String PRDCLS1 = arrayList.get(i).getV2();
                        List<Object_304> object_304ArrayList = new ArrayList<>();
                        for (int k = 0; k < LIST.size(); k++) {
                            Object_304 ob = LIST.get(k);
                            if (ob.getV1().equals(Const.STEAM)
                                    && !ob.getV4().equals("T")
                                    && !ob.getV2().equals("T")
                                    && PRDCLS1.equals(ob.getV2())) {
                                object_304ArrayList.add(ob);
                            }
                        }
                        arrayList.get(i).setLIST(object_304ArrayList);
                    }
                }

                if (arrayList != null && arrayList.size() > 0) {
                    Log.d(TAG, "null");
                    recyclerView.setVisibility(View.VISIBLE);
                    mAdapter.notifyDataSetChanged();
                    tvNodata.setVisibility(View.GONE);
                    List<Object_304> LIST = listAPI_304.getLIST();
                    List<Object_304> listEnd = new ArrayList<>();
                    for (int i = 0; i < LIST.size(); i++) {
                        Object_304 ob = LIST.get(i);
                        if (ob.getV1().equals(Const.STEAM) && ob.getV4().equals("T") && ob.getV2().equals("T")) {
                            listEnd.add(ob);
                            break;
                        }
                    }
                    if (listEnd != null && listEnd.size() > 0) {
                        lnBottom.setVisibility(View.VISIBLE);
                        Object_304 object304 = listEnd.get(0);
//                tv2.setText(Const.RoundNumber(object304.getV6()));
                        tv2.setText(Const.formatAMTFloat(Float.parseFloat(object304.getV6())));

                        tv3.setText(Const.RoundNumber(object304.getV7()));
                        tv4.setText(Const.RoundNumber(object304.getV8()));
//                tv5.setText(Const.RoundNumber(object304.getV9()));
                        tv5.setText(Const.formatAMTFloat(Float.parseFloat(object304.getV9())));
                    }
                } else {
                    Log.d(TAG, "! null");
                    recyclerView.setVisibility(View.GONE);
                    tvNodata.setVisibility(View.VISIBLE);
                    lnBottom.setVisibility(View.GONE);
                }
            }
        }


    }
}
