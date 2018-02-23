package com.orion.salesman._route._fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.orion.salesman.MainActivity;
import com.orion.salesman.OrderActivity;
import com.orion.salesman.R;
import com.orion.salesman._adaper.ViewPagerAdapter;
import com.orion.salesman._app.MyApplication;
import com.orion.salesman._class.Const;
import com.orion.salesman._class.CustomViewPager;
import com.orion.salesman._route._adapter.PromotionInforAdapter;
import com.orion.salesman._route._object.PromotionInfor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by maidinh on 18/8/2016.
 */
public class PromotionInfoFragmentByMonth extends Fragment {
    String TAG = "PromotionInfoFragmentByMonth";
    private TabLayout tabLayout;
    private CustomViewPager viewPager;

    void init(View v) {
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
    public PromotionInfoFragmentByMonth() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.promotion_bymonth_info_fragment, container, false);
        init(v);
        return v;
    }

    private void setupViewPager(CustomViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment(new PromotionPie(0), getResources().getString(R.string.pie));
        adapter.addFragment(new PromotionPie(1), getResources().getString(R.string.snack));
        adapter.addFragment(new PromotionPie(2), getResources().getString(R.string.gum));
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(3);
    }

    @Override
    public void onResume() {
        super.onResume();
        OrderActivity.flag = 4;
        OrderActivity.instance.hideBtnNext();
        OrderActivity.instance.setTvStock(getResources().getString(R.string.proinforbymonth));
    }
}