package com.orion.salesman._infor._fragment._fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.orion.salesman.R;
import com.orion.salesman._infor._fragment._adapter.NewOneAdapter;
import com.orion.salesman._infor._fragment._object.OneNew;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by maidinh on 19/10/2016.
 */
public class three_new extends Fragment {
    String TAG = "one_new";
    private String DATE;
    private NewOneAdapter mAdapter;
    private List<OneNew> arrayList = new ArrayList<>();
    private RecyclerView recyclerView;
    public static three_new instance;
    TextView tvNodata;

    public three_new() {

    }

    @SuppressLint("ValidFragment")
    public three_new(List<OneNew> arrayList) {
        this.arrayList = arrayList;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.title_one_news_layout, container, false);
        instance=this;
        init(v);
        reload();
        return v;
    }

    public void reload() {
        recyclerView.setVisibility(View.GONE);
        tvNodata.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
    }

    public void updateList(List<OneNew> arrayList) {
        progressBar.setVisibility(View.GONE);
        if (arrayList != null && arrayList.size() > 0) {
            this.arrayList = arrayList;
            recyclerView.setVisibility(View.VISIBLE);
            tvNodata.setVisibility(View.GONE);
            mAdapter.updateList(arrayList);
        } else {
            recyclerView.setVisibility(View.GONE);
            tvNodata.setVisibility(View.VISIBLE);
        }
    }
    ProgressBar progressBar;
    void init(View v) {
        progressBar = (ProgressBar) v.findViewById(R.id.progressbar);
        tvNodata = (TextView) v.findViewById(R.id.tvNodata);
        recyclerView = (RecyclerView) v.findViewById(R.id.recycler_view);
        mAdapter = new NewOneAdapter(getActivity(), arrayList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(mAdapter);
    }
}