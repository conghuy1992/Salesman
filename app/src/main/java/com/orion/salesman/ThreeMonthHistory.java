package com.orion.salesman;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.orion.salesman._class.Const;
import com.orion.salesman._class.HistoryMonthRow;
import com.orion.salesman._class.HttpRequest;
import com.orion.salesman._interface.IF_127;
import com.orion.salesman._object.ObjA0127;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by maidinh on 4/11/2016.
 */
public class ThreeMonthHistory extends AppCompatActivity implements View.OnClickListener {
    LinearLayout lnTitle;
    ImageView onBackShop;
    ProgressBar progressbar;
    TextView tvNodata, tvSum;
    LinearLayout lnRoot;
    FrameLayout lnSum;

    String TAG = "ThreeMonthHistory";
    private ArrayList<ObjA0127> arraylist = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.three_month_history_layout);
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.color_bar));
        }
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        lnRoot = (LinearLayout) findViewById(R.id.lnRoot);
        lnSum = (FrameLayout) findViewById(R.id.lnSum);
        lnTitle = (LinearLayout) findViewById(R.id.lnTitle);
        lnTitle.setPadding(MainActivity.widthTabLayout, 0, 0, 0);
        onBackShop = (ImageView) findViewById(R.id.onBackShop);
        onBackShop.setOnClickListener(this);
        progressbar = (ProgressBar) findViewById(R.id.progressbar);

        tvNodata = (TextView) findViewById(R.id.tvNodata);
        tvSum = (TextView) findViewById(R.id.tvSum);
        callAPI();
    }

    void callAPI() {
        Calendar now = Calendar.getInstance();
        int YEAR = now.get(Calendar.YEAR);
        int MONTH = now.get(Calendar.MONTH) + 1;
        int DATE = now.get(Calendar.DATE);

        String CDATE = YEAR + Const.formatFullDate(MONTH) + Const.formatFullDate(DATE);
        String CUSTCD = "";
        CUSTCD = getIntent().getStringExtra(Const.CUSTCD);
        if (CUSTCD.length() == 0) {
            CUSTCD = MainActivity.CUSTCD;
        }

        new HttpRequest(getApplicationContext()).new A0127(MainActivity.dataLogin, CDATE, CUSTCD, new IF_127() {
            @Override
            public void onSuccess(ArrayList<ObjA0127> lst) {
                arraylist.clear();
                //add date
                for (ObjA0127 dateParent : lst) {
//                    Log.d(TAG,new Gson().toJson(dateParent));
                    String CDATE = dateParent.getV1();
                    boolean flag = true;
                    for (ObjA0127 dateChild : arraylist) {
                        if (CDATE.equals(dateChild.getV1())) {
                            flag = false;
                            break;
                        }
                    }
                    if (flag) {
                        arraylist.add(dateParent);
                    }
                }

                for (ObjA0127 ob : arraylist) {
                    String CDATE = ob.getV1();
                    for (ObjA0127 ob2 : lst) {
                        if (CDATE.equals(ob2.getV1())) {
                            String str = new Gson().toJson(ob2);
                            ObjA0127 getObj = new Gson().fromJson(str, ObjA0127.class);
//                            if (getObj.getV5().equals("0")) {
//                                ob.getLstPromotion().add(getObj);
//                            } else {
//                                ob.getLstSales().add(getObj);
//                            }
                            if (!getObj.getV7().equals("0")) {
                                ob.getLstPromotion().add(getObj);
                            }
                            if (!getObj.getV5().equals("0")) {
                                ob.getLstSales().add(getObj);
                            }
                        }
                    }
                }

                for (ObjA0127 ob : arraylist) {
                    HistoryMonthRow row = new HistoryMonthRow(getApplicationContext(), ob);
                    lnRoot.addView(row);
                }
                long Sum = 0;
                for (ObjA0127 obj : arraylist) {
                    List<ObjA0127> lstSale = obj.getLstSales();
                    List<ObjA0127> lstPro = obj.getLstPromotion();
                    for (ObjA0127 object : lstSale) {
                        if (object.getV9().trim().length() > 0) {
                            long TGT = (long) Float.parseFloat(object.getV9());
                            Sum += TGT;
                        }
                    }
                    for (ObjA0127 object : lstPro) {
                        if (object.getV10().trim().length() > 0) {
                            long TGT = (long) Float.parseFloat(object.getV10());
                            Sum += TGT;
                        }
                    }
                }
                tvSum.setText(Const.formatAMT(Sum));
                lnSum.setVisibility(View.VISIBLE);
//                Const.longInfo(TAG, new Gson().toJson(arraylist));

                progressbar.setVisibility(View.GONE);
                tvNodata.setVisibility(View.GONE);

            }

            @Override
            public void onFail() {
//                Toast.makeText(getApplicationContext(),"onFail",Toast.LENGTH_SHORT).show();
                progressbar.setVisibility(View.GONE);
                tvNodata.setVisibility(View.VISIBLE);
                lnSum.setVisibility(View.GONE);
                tvNodata.setText(getResources().getString(R.string.nodata));

            }

            @Override
            public void onError(String s) {
                progressbar.setVisibility(View.GONE);
                tvNodata.setVisibility(View.VISIBLE);
                lnSum.setVisibility(View.GONE);
                tvNodata.setText("" + s);
            }
        }).execute();
    }

    @Override
    public void onClick(View view) {
        if (view == onBackShop) {
            finish();
        }
    }
}
