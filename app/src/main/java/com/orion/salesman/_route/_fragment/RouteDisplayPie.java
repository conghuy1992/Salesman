package com.orion.salesman._route._fragment;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.orion.salesman.OrderActivity;
import com.orion.salesman.R;
import com.orion.salesman._pref.PrefManager;
import com.orion.salesman._route._adapter.RouteDisplayPieAdapter;
import com.orion.salesman._route._object.Pie;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by maidinh on 10/8/2016.
 */
public class RouteDisplayPie extends Fragment {
    private List<com.orion.salesman._route._object.RouteDisplayPie> arrayList = new ArrayList<>();
    private RecyclerView recyclerView;
    private RouteDisplayPieAdapter mAdapter;
    private int vitri; // 0->pie  - 1->:snack

    @SuppressLint("ValidFragment")
    public RouteDisplayPie(List<com.orion.salesman._route._object.RouteDisplayPie> arrayList, int vitri) {
        this.arrayList = arrayList;
        this.vitri = vitri;
    }

    public RouteDisplayPie() {

    }

    @Override
    public void onPause() {
        super.onPause();
        if (getListPie().size() > 0) {
            if (vitri == 0)
                new PrefManager(getActivity()).setRouteDisplayPie(new Gson().toJson(getListPie()));
            else
                new PrefManager(getActivity()).setRouteDisplaySnack(new Gson().toJson(getListPie()));
        }

    }

    public List<com.orion.salesman._route._object.RouteDisplayPie> getListPie() {
        if (mAdapter != null) {
            return mAdapter.getData();
        } else {
            List<com.orion.salesman._route._object.RouteDisplayPie> moviesList = new ArrayList<>();
            return moviesList;
        }
    }

    public void createRC_1(View v) {

        mAdapter = new RouteDisplayPieAdapter(arrayList);
        recyclerView = (RecyclerView) v.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(mAdapter);
        progressbar.setVisibility(View.GONE);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    class load_data extends AsyncTask<String, String, String> {
        View v;

        public load_data(View v) {
            this.v = v;
        }

        @Override
        protected String doInBackground(String... params) {
            initDB();
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            createRC_1(v);
            if (vitri == 1)
                OrderActivity.checkClick = true;
        }
    }

    void initDB() {
        PrefManager pref = new PrefManager(getActivity());
        String s = "";
        if (vitri == 0)
            s = pref.getRouteDisplayPie();
        else if (vitri == 1)
            s = pref.getRouteDisplaySnack();
        if (s.length() > 0) {
            Type listType = new TypeToken<List<com.orion.salesman._route._object.RouteDisplayPie>>() {
            }.getType();
            this.arrayList = new Gson().fromJson(s, listType);
        } else {
        }
    }
    ProgressBar progressbar;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.route_display_pie, container, false);
        progressbar = (ProgressBar)v.findViewById(R.id.progressbar);
//        new load_data(v).execute();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                new load_data(v).execute();
            }
        },500);
        return v;
    }

}
