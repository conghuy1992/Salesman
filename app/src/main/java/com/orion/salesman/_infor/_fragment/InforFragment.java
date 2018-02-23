package com.orion.salesman._infor._fragment;

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
import com.orion.salesman._infor._fragment._fragment.five_new;
import com.orion.salesman._infor._fragment._fragment.four_new;
import com.orion.salesman._infor._fragment._fragment.fragment_one;
import com.orion.salesman._infor._fragment._fragment.fragment_three;
import com.orion.salesman._infor._fragment._fragment.fragment_two;
import com.orion.salesman._infor._fragment._fragment.one_new;
import com.orion.salesman._infor._fragment._fragment.three_new;
import com.orion.salesman._infor._fragment._fragment.two_new;
import com.orion.salesman._summary._fragment.DeliveryFragment;
import com.orion.salesman._summary._fragment.DisplayFragment;
import com.orion.salesman._summary._fragment.SalesReportFragment;
import com.orion.salesman._summary._fragment.SalesShopFragment;

public class InforFragment extends Fragment {
    String TAG = "InforFragment";
    private String DATE;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Context context;
    private TextView TV1, TV2, TV3, TV4;

    public InforFragment() {

    }

    @SuppressLint("ValidFragment")
    public InforFragment(String DATE) {
        this.DATE = DATE;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_infor, container, false);
        init(v);
        return v;
    }

    public void init(View v) {
        TV1 = (TextView) v.findViewById(R.id.TV1);
        TV2 = (TextView) v.findViewById(R.id.TV2);
        TV3 = (TextView) v.findViewById(R.id.TV3);
        TV4 = (TextView) v.findViewById(R.id.TV4);
        TV1.setText("ID: " + MainActivity.dataLogin.getUSERID());
        TV3.setText(MainActivity.dataLogin.getPERSHORTDSC());
        TV2.setText("DEPT: " + MainActivity.dataLogin.getDEPTCD());
        TV4.setText("TEAM: " + getTEAM(MainActivity.TEAM));
        viewPager = (ViewPager) v.findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        tabLayout = (TabLayout) v.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) tabLayout.getLayoutParams();
        params.setMargins(MainActivity.widthTabLayout, 0, 0, 0);
        tabLayout.setLayoutParams(params);
        customTabLayout(tabLayout);
    }

    String getTEAM(String id) {
        String s = "Pie";
        if (id.equals("01"))
            s = "Pie";
        else if (id.equals("02"))
            s = "Snack";
        else if (id.equals("03"))
            s = "Gum";
        else
            s = "Mix";
        return s;
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment(new fragment_one(), getResources().getString(R.string.News));
        adapter.addFragment(new fragment_two(), getResources().getString(R.string.orionfamily));
        adapter.addFragment(new fragment_three(), getResources().getString(R.string.msg));
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(2);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                MainActivity.INFOR_PAGE = position;
                Log.d(TAG, "position:" + position);
                MainActivity.positionNews = position;
                if (position == 0) {
                    if (fragment_one.instance != null) {
                        if (one_new.instance != null) {
                            one_new.instance.reload();
                        }
                        if (two_new.instance != null) {
                            two_new.instance.reload();
                        }
                        if (three_new.instance != null) {
                            three_new.instance.reload();
                        }
                        if (four_new.instance != null) {
                            four_new.instance.reload();
                        }
                        if (five_new.instance != null) {
                            five_new.instance.reload();
                        }


                        fragment_one.instance.API_2();
                    }
                }


            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public void customTabLayout(TabLayout tabLayout) {

        View tabThree = LayoutInflater.from(getActivity()).inflate(R.layout.custom_tablayout_title, null);
        TextView tvTitle_3 = (TextView) tabThree.findViewById(R.id.tvTitle);
        tvTitle_3.setText(getResources().getString(R.string.msg));
        tabLayout.getTabAt(2).setCustomView(tabThree);

        View tabTwo = LayoutInflater.from(getActivity()).inflate(R.layout.custom_tablayout_title, null);
        TextView tvTitle_2 = (TextView) tabTwo.findViewById(R.id.tvTitle);
        tvTitle_2.setText(getResources().getString(R.string.orionfamily));
        tabLayout.getTabAt(1).setCustomView(tabTwo);

        View tabOne = LayoutInflater.from(getActivity()).inflate(R.layout.custom_tablayout_title, null);
        TextView tvTitle = (TextView) tabOne.findViewById(R.id.tvTitle);
        tvTitle.setText(getResources().getString(R.string.News));
        tabLayout.getTabAt(0).setCustomView(tabOne);
    }
}