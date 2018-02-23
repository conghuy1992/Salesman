package com.orion.salesman._summary._fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.orion.salesman.MainActivity;
import com.orion.salesman.R;
import com.orion.salesman._adaper.ViewPagerAdapter;
import com.orion.salesman._class.Const;
import com.orion.salesman._object.DataLogin;


public class SummaryFragment extends Fragment {
    private String TAG = "SummaryFragment";
    private DataLogin dataLogin;
    private String DATE;

    @SuppressLint("ValidFragment")
    public SummaryFragment(String DATE, DataLogin dataLogin) {
        this.DATE = DATE;
        this.dataLogin = dataLogin;
    }

    public SummaryFragment() {
    }

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Context context;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_summary, container, false);
        init(v);
        return v;
    }

    public void init(View v) {
        viewPager = (ViewPager) v.findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        tabLayout = (TabLayout) v.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) tabLayout.getLayoutParams();
        params.setMargins(MainActivity.widthTabLayout, 0, 0, 0);
        tabLayout.setLayoutParams(params);
        customTabLayout(tabLayout);
    }

    public void customTabLayout(TabLayout tabLayout) {
        View tabFour = LayoutInflater.from(getActivity()).inflate(R.layout.custom_tablayout_title, null);
        TextView tvTitle_4 = (TextView) tabFour.findViewById(R.id.tvTitle);
        tvTitle_4.setText(getResources().getString(R.string.delivery));
        tabLayout.getTabAt(3).setCustomView(tabFour);

        View tabThree = LayoutInflater.from(getActivity()).inflate(R.layout.custom_tablayout_title, null);
        TextView tvTitle_3 = (TextView) tabThree.findViewById(R.id.tvTitle);
        tvTitle_3.setText(getResources().getString(R.string.display));
        tabLayout.getTabAt(2).setCustomView(tabThree);

        View tabTwo = LayoutInflater.from(getActivity()).inflate(R.layout.custom_tablayout_title, null);
        TextView tvTitle_2 = (TextView) tabTwo.findViewById(R.id.tvTitle);
        tvTitle_2.setText(getResources().getString(R.string.salesshop));
        tabLayout.getTabAt(1).setCustomView(tabTwo);

        View tabOne = LayoutInflater.from(getActivity()).inflate(R.layout.custom_tablayout_title, null);
        TextView tvTitle = (TextView) tabOne.findViewById(R.id.tvTitle);
        tvTitle.setText(getResources().getString(R.string.salesreport));
        tabLayout.getTabAt(0).setCustomView(tabOne);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment(new SalesReportFragment(dataLogin, 0, DATE, Const.SummarySales), getResources().getString(R.string.salesreport));
        adapter.addFragment(new SalesShopFragment(DATE), getResources().getString(R.string.salesshop));
        adapter.addFragment(new DisplayFragment(dataLogin, 2, DATE, Const.SummaryDisplay), getResources().getString(R.string.display));
        adapter.addFragment(new DeliveryFragment(dataLogin, DATE, Const.SummaryDelivery), getResources().getString(R.string.delivery));
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
                    SalesReportFragment.instance.updateDate(MainActivity.DATE_SYNC);
                } else if (position == 1) {
                    SalesShopFragment.instance.updateDate(MainActivity.DATE_SYNC);
                } else if (position == 3) {
                    DeliveryFragment.instance.updateDate(MainActivity.DATE_SYNC);
                } else if (position == 2) {
                    DisplayFragment.instance.updateDate(MainActivity.DATE_SYNC);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}
