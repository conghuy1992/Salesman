package com.orion.salesman;

import android.app.Activity;
import android.graphics.drawable.Drawable;
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

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.bumptech.glide.request.target.Target;
import com.orion.salesman._adaper.ViewPagerAdapter;
import com.orion.salesman._app.MyApplication;
import com.orion.salesman._class.Const;
import com.orion.salesman._class.CustomViewPager;
import com.orion.salesman._fragment.FragmentImage;

/**
 * Created by maidinh on 14/10/2016.
 */
public class ZoomImage extends AppCompatActivity {
    ImageView onBackShop;
    LinearLayout lnTitle;
    private TabLayout tabLayout;
    private CustomViewPager viewPager;
    String URL_1 = "";
    String URL_2 = "";
    int POS = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.zoom_image);
        URL_1 = getIntent().getStringExtra(Const.SEND_URL);
        URL_2 = getIntent().getStringExtra(Const.SEND_URL_2);
        POS = getIntent().getIntExtra(Const.POSITION_IMAGE, 0);
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.color_bar));
        }
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
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
                p.setMargins(mg_5, 0, mg_10, 0);
            }
            tab.requestLayout();
        }

        lnTitle = (LinearLayout) findViewById(R.id.lnTitle);
        lnTitle.setPadding(MainActivity.widthTabLayout, 0, 0, 0);

        onBackShop = (ImageView) findViewById(R.id.onBackShop);
        onBackShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    public void customTabLayout(TabLayout tabLayout) {

        View tabTwo = LayoutInflater.from(this).inflate(R.layout.tab_kind, null);
        TextView tvTitle_2 = (TextView) tabTwo.findViewById(R.id.tvTitle);
        tvTitle_2.setText(getResources().getString(R.string.PHOTO2));
        tabLayout.getTabAt(1).setCustomView(tabTwo);

        View tabOne = LayoutInflater.from(this).inflate(R.layout.tab_kind, null);
        TextView tvTitle = (TextView) tabOne.findViewById(R.id.tvTitle);
        tvTitle.setText(getResources().getString(R.string.PHOTO1));

        tabLayout.getTabAt(0).setCustomView(tabOne);
    }

    private void setupViewPager(CustomViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new FragmentImage(URL_1), getResources().getString(R.string.PHOTO1));
        adapter.addFragment(new FragmentImage(URL_2), getResources().getString(R.string.PHOTO2));
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(POS);
    }
}
