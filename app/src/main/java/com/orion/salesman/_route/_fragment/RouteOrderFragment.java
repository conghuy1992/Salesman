package com.orion.salesman._route._fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.orion.salesman.MainActivity;
import com.orion.salesman.OrderActivity;
import com.orion.salesman.R;
import com.orion.salesman._adaper.ViewPagerAdapter;
import com.orion.salesman._app.MyApplication;
import com.orion.salesman._class.AndroidBug5497Workaround;
import com.orion.salesman._class.Const;
import com.orion.salesman._class.CustomViewPager;
import com.orion.salesman._pref.PrefManager;
import com.orion.salesman._route._object.Pie;
import com.orion.salesman._route._object.Snack;
import com.orion.salesman._route._object._RouteOrderFragmentPie;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by maidinh on 10/8/2016.
 */
public class RouteOrderFragment extends Fragment {
    private String TAG = "RouteOrderFragment";
    private TabLayout tabLayout;
    private CustomViewPager viewPager;

    public RouteOrderFragment() {

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
                p.setMargins(mg_5, 0, mg_5, 0);
            } else if (i == 2) {
                p.setMargins(mg_5, 0, mg_10, 0);
            }
            tab.requestLayout();
        }
        MyApplication.getInstance().setChooseTab(MainActivity.TEAM, viewPager, tabLayout);
    }

    public void customTabLayout(TabLayout tabLayout) {
        View tabThree = LayoutInflater.from(getActivity()).inflate(R.layout.tab_kind, null);
        TextView tvTitle_3 = (TextView) tabThree.findViewById(R.id.tvTitle);
        tvTitle_3.setText(getResources().getString(R.string.gum));
        tabLayout.getTabAt(2).setCustomView(tabThree);

        View tabTwo = LayoutInflater.from(getActivity()).inflate(R.layout.tab_kind, null);
        TextView tvTitle_2 = (TextView) tabTwo.findViewById(R.id.tvTitle);
        tvTitle_2.setText(getResources().getString(R.string.snack));
        tabLayout.getTabAt(1).setCustomView(tabTwo);

        View tabOne = LayoutInflater.from(getActivity()).inflate(R.layout.tab_kind, null);
        TextView tvTitle = (TextView) tabOne.findViewById(R.id.tvTitle);
        tvTitle.setText(getResources().getString(R.string.pie));

        if (MainActivity.TEAM.equals(Const.PIE_TEAM)) {
            tvTitle_2.setTextColor(getResources().getColor(R.color.teamColor));
//            tvTitle_3.setTextColor(getResources().getColor(R.color.teamColor));
        } else if (MainActivity.TEAM.equals(Const.SNACK_TEAM)) {
            tvTitle.setTextColor(getResources().getColor(R.color.teamColor));
            tvTitle_3.setTextColor(getResources().getColor(R.color.teamColor));
        } else if (MainActivity.TEAM.equals(Const.GUM_TEAM)) {
            tvTitle_2.setTextColor(getResources().getColor(R.color.teamColor));
            tvTitle.setTextColor(getResources().getColor(R.color.teamColor));
        }

        tabLayout.getTabAt(0).setCustomView(tabOne);
    }

    private void setupViewPager(CustomViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment(new RouteOrderFragmentPie(0), getResources().getString(R.string.pie));
        adapter.addFragment(new RouteOrderFragmentSnack(), getResources().getString(R.string.snack));
        adapter.addFragment(new RouteOrderFragmentPie(1), getResources().getString(R.string.gum));
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(2);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.route_order_fragment, container, false);

        init(v);
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        OrderActivity.flag = 2;
        OrderActivity.instance.showBtnBack();
        OrderActivity.instance.showBtnNext();
        OrderActivity.instance.setTvStock(getResources().getString(R.string.order));
    }
//    @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        final InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
//        imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
//    }
}
