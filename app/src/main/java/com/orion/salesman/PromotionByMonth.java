package com.orion.salesman;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.orion.salesman._adaper.ViewPagerAdapter;
import com.orion.salesman._app.MyApplication;
import com.orion.salesman._class.Const;
import com.orion.salesman._class.CustomViewPager;
import com.orion.salesman._route._fragment.PromotionPie;

/**
 * Created by maidinh on 13/10/2016.
 */
public class PromotionByMonth extends AppCompatActivity {
    String TAG = "PromotionByMonth";
    private TabLayout tabLayout;
    private CustomViewPager viewPager;
    private ImageView onBackShop;
    private LinearLayout lnTitle;
    String SHOP_NAME = "";
    String SHOP_ADDRESS = "";
    TextView tvName, tvAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.promotion_activity);
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.color_bar));
        }
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        SHOP_NAME = getIntent().getStringExtra(Const.SHOP_NAME);
        SHOP_ADDRESS = getIntent().getStringExtra(Const.SHOP_ADDRESS);
        OrderActivity.flag = 4;
        init();
    }

    void init() {
        tvName = (TextView) findViewById(R.id.tvName);
        tvAddress = (TextView) findViewById(R.id.tvAddress);
        tvName.setText(SHOP_NAME);
        tvAddress.setText(SHOP_ADDRESS);
        viewPager = (CustomViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        customTabLayout(tabLayout);
        int mg_10 = MyApplication.getInstance().getDimens(this, R.dimen.tablayoutmargin10);
        int mg_5 = MyApplication.getInstance().getDimens(this, R.dimen.tablayoutmargin5);
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
        onBackShop = (ImageView) findViewById(R.id.onBackShop);
        onBackShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        lnTitle = (LinearLayout) findViewById(R.id.lnTitle);
        lnTitle.setPadding(MainActivity.widthTabLayout, 0, 0, 0);
    }

    private void setupViewPager(CustomViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new PromotionPie(0), getResources().getString(R.string.pie));
        adapter.addFragment(new PromotionPie(1), getResources().getString(R.string.snack));
        adapter.addFragment(new PromotionPie(2), getResources().getString(R.string.gum));
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(2);
    }

    public void customTabLayout(TabLayout tabLayout) {
        View tabThree = LayoutInflater.from(this).inflate(R.layout.tab_kind, null);
        TextView tvTitle_3 = (TextView) tabThree.findViewById(R.id.tvTitle);
        tvTitle_3.setText(getResources().getString(R.string.gum));
        tabLayout.getTabAt(2).setCustomView(tabThree);

        View tabTwo = LayoutInflater.from(this).inflate(R.layout.tab_kind, null);
        TextView tvTitle_2 = (TextView) tabTwo.findViewById(R.id.tvTitle);
        tvTitle_2.setText(getResources().getString(R.string.snack));
        tabLayout.getTabAt(1).setCustomView(tabTwo);

        View tabOne = LayoutInflater.from(this).inflate(R.layout.tab_kind, null);
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
}
