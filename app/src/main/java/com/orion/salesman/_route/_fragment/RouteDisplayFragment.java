package com.orion.salesman._route._fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.orion.salesman.MainActivity;
import com.orion.salesman.OrderActivity;
import com.orion.salesman.R;
import com.orion.salesman._adaper.ViewPagerAdapter;
import com.orion.salesman._app.MyApplication;
import com.orion.salesman._class.Const;
import com.orion.salesman._class.CustomViewPager;
import com.orion.salesman._pref.PrefManager;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by maidinh on 9/8/2016.
 */
public class RouteDisplayFragment extends Fragment {
    private String TAG = "RouteDisplayFragment";
    private TabLayout tabLayout;
    private CustomViewPager viewPager;
    private List<com.orion.salesman._route._object.RouteDisplayPie> ListPie = new ArrayList<>();
    private List<com.orion.salesman._route._object.RouteDisplayPie> ListSnack = new ArrayList<>();

    public RouteDisplayFragment() {

    }

    @SuppressLint("ValidFragment")
    public RouteDisplayFragment(List<com.orion.salesman._route._object.RouteDisplayPie> ListPie, List<com.orion.salesman._route._object.RouteDisplayPie> ListSnack) {
        this.ListPie = ListPie;
        this.ListSnack = ListSnack;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.route_display_fragment_layout, container, false);
        init(v);
        return v;
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    public void init(View v) {
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
        MyApplication.getInstance().setChooseTab(MainActivity.TEAM, viewPager, tabLayout);
    }

    public void customTabLayout(TabLayout tabLayout) {
        View tabTwo = LayoutInflater.from(getActivity()).inflate(R.layout.tab_kind, null);
        TextView tvTitle_2 = (TextView) tabTwo.findViewById(R.id.tvTitle);
        tvTitle_2.setText(getResources().getString(R.string.snack));
        tabLayout.getTabAt(1).setCustomView(tabTwo);

        View tabOne = LayoutInflater.from(getActivity()).inflate(R.layout.tab_kind, null);
        TextView tvTitle = (TextView) tabOne.findViewById(R.id.tvTitle);
        tvTitle.setText(getResources().getString(R.string.pie));

        if (MainActivity.TEAM.equals(Const.PIE_TEAM)) {
            tvTitle_2.setTextColor(getResources().getColor(R.color.teamColor));
        } else if (MainActivity.TEAM.equals(Const.SNACK_TEAM)) {
            tvTitle.setTextColor(getResources().getColor(R.color.teamColor));
        }

        tabLayout.getTabAt(0).setCustomView(tabOne);
    }

    void initListPie() {
        PrefManager pref = new PrefManager(getActivity());
        String s = pref.getRouteDisplayPie();
        if (s.length() > 0) {
            Type listType = new TypeToken<List<com.orion.salesman._route._object.RouteDisplayPie>>() {
            }.getType();
            this.ListPie = new Gson().fromJson(s, listType);
        } else {
        }
    }

    void initListSnack() {
        PrefManager pref = new PrefManager(getActivity());
        String s = pref.getRouteDisplaySnack();
        if (s.length() > 0) {
            Type listType = new TypeToken<List<com.orion.salesman._route._object.RouteDisplayPie>>() {
            }.getType();
            this.ListSnack = new Gson().fromJson(s, listType);
        } else {
        }
    }

    void initList() {
        initListPie();
        initListSnack();
    }

    private void setupViewPager(CustomViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment(new RouteDisplayPie(ListPie, 0), getResources().getString(R.string.pie));
        adapter.addFragment(new RouteDisplayPie(ListSnack, 1), getResources().getString(R.string.snack));
//        adapter.addFragment(new RouteDisplayPie(arrayList), getResources().getString(R.string.gum));
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(1);
    }

    @Override
    public void onResume() {
        super.onResume();
        OrderActivity.instance.showBtnBack();
        OrderActivity.instance.showBtnNext();
        OrderActivity.flag = 1;
        OrderActivity.instance.setTvStock(getResources().getString(R.string.display));
    }
//    @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        final InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
//        imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
//    }
}
