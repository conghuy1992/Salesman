package com.orion.salesman._infor._fragment._fragment;


import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.orion.salesman.MainActivity;
import com.orion.salesman.R;
import com.orion.salesman._class.HttpRequest;
import com.orion.salesman._infor._fragment._adapter.AdapterNews;
import com.orion.salesman._infor._fragment._adapter.FmlAdapter;
import com.orion.salesman._infor._fragment._object.CpnInfo_ListT;
import com.orion.salesman._infor._fragment._object.News;
import com.orion.salesman._infor._fragment._object.OrionFamily;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by maidinh on 4/10/2016.
 */
public class fragment_two extends Fragment {
    String DATE = "";
    private FmlAdapter mAdapter;
    private List<CpnInfo_ListT> arrayList = new ArrayList<>();
    private RecyclerView recyclerView;

    public fragment_two() {

    }

    @SuppressLint("ValidFragment")
    public fragment_two(String DATE) {
        this.DATE = DATE;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_infor_two, container, false);
        init(v);
        callAPI();
        return v;
    }

    void callAPI() {
        new HttpRequest(getActivity()).doJsonObjectRequest("" + MainActivity.dataLogin.getUSERID(), MainActivity.dataLogin.getHIDEPTCD(), new OrionFamily() {
            @Override
            public void onSuccess(List<CpnInfo_ListT> list) {
                if (list != null && list.size() > 0) {
                    arrayList = list;
                    mAdapter.updateList(arrayList);
                    progressbar.setVisibility(View.GONE);
                    tvNodata.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                } else {
                    noData();
                }
            }

            @Override
            public void onFail() {
                noData();
            }
        });
    }

    TextView tvNodata;
    ProgressBar progressbar;

    void noData() {
        progressbar.setVisibility(View.GONE);
        recyclerView.setVisibility(View.GONE);
        tvNodata.setVisibility(View.VISIBLE);
    }

    void init(View v) {
        tvNodata = (TextView) v.findViewById(R.id.tvNodata);
        progressbar = (ProgressBar) v.findViewById(R.id.progressbar);
        recyclerView = (RecyclerView) v.findViewById(R.id.recycler_view);
        mAdapter = new FmlAdapter(getActivity(), arrayList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(mAdapter);
    }
}