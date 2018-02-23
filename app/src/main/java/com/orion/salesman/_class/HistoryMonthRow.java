package com.orion.salesman._class;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.orion.salesman.R;
import com.orion.salesman._adaper.AdapterSales;
import com.orion.salesman._object.ObjA0127;

import java.util.List;

/**
 * Created by maidinh on 9/11/2016.
 */
public class HistoryMonthRow extends LinearLayout {
    Context context;
    ObjA0127 obj;
    TextView tvDate, tvSales, tvPro, tvSum;
    List<ObjA0127> lstSale;
    List<ObjA0127> lstPro;
    RecyclerView recycler_view, recycler_view_2;
    AdapterSales adapterSales, AdapterPro;
    LinearLayout lnSales, lnPro;

    public HistoryMonthRow(Context context, ObjA0127 obj) {
        super(context);
        this.context = context;
        this.obj = obj;
        init(context, obj);
    }

    long Sum = 0;

    private void init(Context context, ObjA0127 obj) {
        LayoutInflater li = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        li.inflate(R.layout.history_month_row, this, true);
        lstSale = obj.getLstSales();
        lstPro = obj.getLstPromotion();
        lnSales = (LinearLayout) findViewById(R.id.lnSales);
        lnPro = (LinearLayout) findViewById(R.id.lnPro);
        recycler_view = (RecyclerView) findViewById(R.id.recycler_view);
        recycler_view_2 = (RecyclerView) findViewById(R.id.recycler_view_2);
        tvSales = (TextView) findViewById(R.id.tvSales);
        tvDate = (TextView) findViewById(R.id.tvDate);
        tvPro = (TextView) findViewById(R.id.tvPro);
        tvSum = (TextView) findViewById(R.id.tvSum);
        tvDate.setText(Const.formatDate(obj.getV1()));
        if (lstSale != null && lstSale.size() > 0) {

            for (ObjA0127 object : lstSale) {
                if (object.getV9().trim().length() > 0) {
                    long TGT = (long) Float.parseFloat(object.getV9());
                    Sum += TGT;
                }
            }

            tvSales.setText("Sales");
            adapterSales = new AdapterSales(context, lstSale, 0);
            recycler_view.setLayoutManager(new LinearLayoutManager(context));
            recycler_view.setAdapter(adapterSales);
            recycler_view.setNestedScrollingEnabled(false);
        } else {
            lnSales.setVisibility(View.GONE);
        }
        if (lstPro != null && lstPro.size() > 0) {

            for (ObjA0127 object : lstPro) {
                if (object.getV10().trim().length() > 0) {
                    long TGT = (long) Float.parseFloat(object.getV10());
                    Sum += TGT;
                }
            }

            tvPro.setText("Pro");
            AdapterPro = new AdapterSales(context, lstPro, 1);
            recycler_view_2.setLayoutManager(new LinearLayoutManager(context));
            recycler_view_2.setAdapter(AdapterPro);
            recycler_view_2.setNestedScrollingEnabled(false);
        } else {
            lnPro.setVisibility(View.GONE);
        }
        tvSum.setText(Const.formatAMT(Sum));
    }
}
