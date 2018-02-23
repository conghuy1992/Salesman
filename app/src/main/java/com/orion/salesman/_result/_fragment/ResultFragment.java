package com.orion.salesman._result._fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.orion.salesman.MainActivity;
import com.orion.salesman.R;
import com.orion.salesman._adaper.ViewPagerAdapter;
import com.orion.salesman._result._object.RouteNonShop;
import com.orion.salesman._result._object.RouteSales;
import com.orion.salesman._result._object.RouteSalesShop;

import java.util.ArrayList;
import java.util.List;

public class ResultFragment extends Fragment {
    private TabLayout tabLayout;
    private ViewPager viewPager;

    String DATE;

    public ResultFragment() {

    }

    @SuppressLint("ValidFragment")
    public ResultFragment(String DATE) {
        this.DATE = DATE;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_result, container, false);
        viewPager = (ViewPager) v.findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        tabLayout = (TabLayout) v.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        customTabLayout(tabLayout);
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) tabLayout.getLayoutParams();
        params.setMargins(MainActivity.widthTabLayout, 0, 0, 0);
        tabLayout.setLayoutParams(params);
        return v;
    }

    public void customTabLayout(TabLayout tabLayout) {
        View tabFour = LayoutInflater.from(getActivity()).inflate(R.layout.custom_tablayout_title, null);
        TextView tvTitle_4 = (TextView) tabFour.findViewById(R.id.tvTitle);
        tvTitle_4.setText(getResources().getString(R.string.stock));
        tabLayout.getTabAt(3).setCustomView(tabFour);

        View tabThree = LayoutInflater.from(getActivity()).inflate(R.layout.custom_tablayout_title, null);
        TextView tvTitle_3 = (TextView) tabThree.findViewById(R.id.tvTitle);
        tvTitle_3.setText(getResources().getString(R.string.nonShop));
        tabLayout.getTabAt(2).setCustomView(tabThree);

        View tabTwo = LayoutInflater.from(getActivity()).inflate(R.layout.custom_tablayout_title, null);
        TextView tvTitle_2 = (TextView) tabTwo.findViewById(R.id.tvTitle);
        tvTitle_2.setText(getResources().getString(R.string.salesshop));
        tabLayout.getTabAt(1).setCustomView(tabTwo);

        View tabOne = LayoutInflater.from(getActivity()).inflate(R.layout.custom_tablayout_title, null);
        TextView tvTitle = (TextView) tabOne.findViewById(R.id.tvTitle);
        tvTitle.setText(getResources().getString(R.string.sales));
        tabLayout.getTabAt(0).setCustomView(tabOne);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment(new ResultSalesFragment(DATE), getResources().getString(R.string.sales));
        adapter.addFragment(new RouteSalesShopFragment(DATE), getResources().getString(R.string.salesshop));
        adapter.addFragment(new RouteNonShopFragment(DATE), getResources().getString(R.string.nonShop));
        adapter.addFragment(new ResultStockFragment(DATE), getResources().getString(R.string.stock));
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(3);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
//                Log.e(TAG, "position:" + position);
                if (position == 0) {
                    ResultSalesFragment.instance.updateDate(MainActivity.REPORT_TODAY);
                } else if (position == 1) {
                    RouteSalesShopFragment.instance.updateDate(MainActivity.REPORT_TODAY);
                } else if (position == 2) {
                    RouteNonShopFragment.instance.updateDate(MainActivity.REPORT_TODAY);
                } else if (position == 3) {
                    ResultStockFragment.instance.updateDate(MainActivity.REPORT_TODAY);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}