package com.orion.salesman;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.orion.salesman._class.Const;
import com.orion.salesman._route._fragment.EditFragment;
import com.orion.salesman._route._fragment.InforShopFragment;
import com.orion.salesman._route._fragment.RouteFragment;
import com.orion.salesman._route._object.DisplayInfo;
import com.orion.salesman._route._object.InforShopDetails;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by maidinh on 4/8/2016.
 */
public class ShopInfoActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView tvTitle;
    private ImageView onBackShop;
    public static ShopInfoActivity instance = null;
    private String TAG = "ShopInfoActivity";
    public static boolean flag = false;
    private LinearLayout lnTitle;
    private List<DisplayInfo> infoArrayList = new ArrayList<>();
    private InforShopDetails shopDetailsList = new InforShopDetails();
    private String SEQ = "";

    public static ShopInfoActivity getInstance() {
        return instance;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.route_shop_info);
        checkClick = true;
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.color_bar));
        }
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

        if (getIntent().getStringExtra(Const.LIST_INFO_SHOP) != null) {
            String response = getIntent().getStringExtra(Const.LIST_INFO_SHOP);
            Type listType = new TypeToken<ArrayList<DisplayInfo>>() {
            }.getType();
            infoArrayList = new Gson().fromJson(response, listType);
        }
        if (getIntent().getStringExtra(Const.LIST_DETAILS) != null) {
            String response = getIntent().getStringExtra(Const.LIST_DETAILS);
            shopDetailsList = new Gson().fromJson(response, InforShopDetails.class);
        }
        if (getIntent().getStringExtra(Const.SEQ) != null) {
            SEQ = getIntent().getStringExtra(Const.SEQ);
        }

        lnTitle = (LinearLayout) findViewById(R.id.lnTitle);
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) lnTitle.getLayoutParams();
        params.setMargins(MainActivity.widthTabLayout, 0, 0, 0);
        lnTitle.setLayoutParams(params);
        instance = this;
        onBackShop = (ImageView) findViewById(R.id.onBackShop);
        onBackShop.setOnClickListener(this);
        tvTitle = (TextView) findViewById(R.id.tvTitle);

        if (savedInstanceState == null) {
            if (MainActivity.checkNewOrEditShop == 0) {
                getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(
                                R.anim.fragment_slide_right_enter,
                                R.anim.fragment_slide_left_exit,
                                R.anim.fragment_slide_left_enter,
                                R.anim.fragment_slide_right_exit)
                        .replace(R.id.flShopInfo, new EditFragment())
                        .commit();
            } else {
                getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(
                                R.anim.fragment_slide_right_enter,
                                R.anim.fragment_slide_left_exit,
                                R.anim.fragment_slide_left_enter,
                                R.anim.fragment_slide_right_exit)
                        .replace(R.id.flShopInfo, new InforShopFragment(infoArrayList, shopDetailsList, SEQ))
                        .commit();
            }

        }
    }

    public void setTitleForText(String s) {
        tvTitle.setText(s);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            super.onActivityResult(requestCode, resultCode, data);
            if (requestCode == Const.REQUEST_CODE_INFO) {
                String requiredValue = data.getStringExtra(Const.UPDATE_POSITION);
                String admincode = data.getStringExtra(Const.UPDATE_ADMINCODE);
//                Log.e(TAG, "UPDATE_POSITION:" + requiredValue);
//                RouteFragment.instance.updateLocation(requiredValue);
                EditFragment.instance.updateLocation(requiredValue);
                if (admincode.trim().length() != 10) {
                    Toast.makeText(getApplicationContext(), "can not update AdminCode", Toast.LENGTH_SHORT).show();
                } else {
                    EditFragment.instance.updateAdminCode(admincode);
                }
            }
        } catch (Exception ex) {
//            Toast.makeText(getApplicationContext(), ex.toString(),
//                    Toast.LENGTH_SHORT).show();
        }

    }

    public static boolean checkClick = true;

    void handlerBack() {
        if (checkClick) {
            if (MainActivity.checkNewOrEditShop == 1) {
                if (flag)
                    finish();
                else
                    getSupportFragmentManager().popBackStack();
            } else {
                finish();
            }
        }
    }

    @Override
    public void onBackPressed() {
        handlerBack();
    }

    @Override
    public void onClick(View v) {
        if (v == onBackShop) {
            handlerBack();
        }
    }
}
