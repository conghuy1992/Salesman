package com.orion.salesman._infor._fragment._fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.orion.salesman.MainActivity;
import com.orion.salesman.R;
import com.orion.salesman.UserOrg;
import com.orion.salesman._adaper.ViewPagerAdapter;
import com.orion.salesman._app.MyApplication;
import com.orion.salesman._class.Const;
import com.orion.salesman._class.CustomViewPager;
import com.orion.salesman._class.HttpRequest;
import com.orion.salesman._infor._fragment._adapter.AdapterNews;
import com.orion.salesman._infor._fragment._adapter.MsgAdapter;
import com.orion.salesman._infor._fragment._object.ListObjA0004;
import com.orion.salesman._infor._fragment._object.News;
import com.orion.salesman._infor._fragment._object.ObjA0004;
import com.orion.salesman._interface.IF_2;
import com.orion.salesman._object.A0004;
import com.orion.salesman._pref.PrefManager;
import com.orion.salesman._sqlite.DatabaseHandler;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by maidinh on 28/10/2016.
 */
public class fragment_three extends Fragment {
    private String TAG = "RouteStockFragment";
    private TabLayout tabLayout;
    private CustomViewPager viewPager;
    private String DATE = "";
    private FrameLayout frAdd;
    public static int POSITION = 0;
    public static int MsgId = 0;

    public fragment_three() {

    }

    @SuppressLint("ValidFragment")
    public fragment_three(String DATE) {
        this.DATE = DATE;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_infor_three, container, false);
        init(v);
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    void init(View v) {
        frAdd = (FrameLayout) v.findViewById(R.id.frAdd);
        frAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), UserOrg.class);
                startActivity(intent);
            }
        });
        viewPager = (CustomViewPager) v.findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        tabLayout = (TabLayout) v.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        customTabLayout(tabLayout);
        int mg_10 = MyApplication.getInstance().getDimens(getActivity(), R.dimen.tablayoutmargin10);
        int mg_5 = MyApplication.getInstance().getDimens(getActivity(), R.dimen.tablayoutmargin5);
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            View tab = ((ViewGroup) tabLayout.getChildAt(0)).getChildAt(i);
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) tab.getLayoutParams();
            if (i == 0) {
                p.setMargins(mg_10, 0, mg_5, 0);
            } else if (i == 1) {
                p.setMargins(mg_5, 0, mg_10, 0);
            }
            tab.requestLayout();
        }
//        MyApplication.getInstance().setChooseTab(MainActivity.TEAM, viewPager, tabLayout);
    }

    private void setupViewPager(CustomViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment(new MsgInbox(), getResources().getString(R.string.inbox));
        adapter.addFragment(new MsgOutbox(), getResources().getString(R.string.outbox));
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(1);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                fragment_three.POSITION = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    public void customTabLayout(TabLayout tabLayout) {
        View tabTwo = LayoutInflater.from(getActivity()).inflate(R.layout.tab_kind, null);
        TextView tvTitle_2 = (TextView) tabTwo.findViewById(R.id.tvTitle);
        tvTitle_2.setText(getResources().getString(R.string.outbox));
        tabLayout.getTabAt(1).setCustomView(tabTwo);

        View tabOne = LayoutInflater.from(getActivity()).inflate(R.layout.tab_kind, null);
        TextView tvTitle = (TextView) tabOne.findViewById(R.id.tvTitle);
        tvTitle.setText(getResources().getString(R.string.inbox));


        tabLayout.getTabAt(0).setCustomView(tabOne);
    }

}