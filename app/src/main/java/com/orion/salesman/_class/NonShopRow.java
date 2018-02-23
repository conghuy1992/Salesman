package com.orion.salesman._class;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.orion.salesman.MainActivity;
import com.orion.salesman.R;
import com.orion.salesman._object.ProductInfor;
import com.orion.salesman._object.list132;

import java.util.List;

/**
 * Created by maidinh on 16/9/2016.
 */
public class NonShopRow extends LinearLayout {
    Context context;
    String SHOP_ID;
    List<ProductInfor> listSKU;
    LinearLayout lnroot;
    List<list132> list_PRDCLS1;

    public NonShopRow(Context context, String SHOP_ID, List<ProductInfor> listSKU, List<list132> list_PRDCLS1) {
        super(context);
        this.context = context;
        this.SHOP_ID = SHOP_ID;
        this.listSKU = listSKU;
        this.list_PRDCLS1 = list_PRDCLS1;
        init(context, SHOP_ID, listSKU, list_PRDCLS1);
    }

    void TextStyle(TextView v, String colorBG, String colorText) {
        v.setGravity(Gravity.CENTER);
        v.setBackgroundColor(Color.parseColor(colorBG));
        v.setTextColor(Color.parseColor(colorText));
    }

    String findList(String SHOP_ID, String TPRDCD) {
        String s = "N";
        if (list_PRDCLS1 != null && list_PRDCLS1.size() > 0) {
            for (int i = 0; i < list_PRDCLS1.size(); i++) {
                list132 ob = list_PRDCLS1.get(i);
                if (ob.getV1().equals(SHOP_ID) && ob.getV2().equals(TPRDCD)) {
                    s = ob.getV3();
                    break;
                }
            }
        }

        return s;
    }

    private void init(Context context, String SHOP_ID, List<ProductInfor> listSKU, List<list132> list_PRDCLS1) {
        LayoutInflater li = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        li.inflate(R.layout.nonshop_row, this, true);
        lnroot = (LinearLayout) findViewById(R.id.lnroot);
        int width;
        if (listSKU.size() >= 4)
            width = (int) MainActivity.width * 70 / 100 / 4;
        else width = (int) MainActivity.width * 70 / 100 / listSKU.size();
        LinearLayout.LayoutParams paramsChild = new LinearLayout.LayoutParams(width, LinearLayout.LayoutParams.MATCH_PARENT);
        paramsChild.setMargins(0, 1, 0, 1);
        for (int k = 0; k < listSKU.size(); k++) {
            TextView button = new TextView(context);
            button.setLayoutParams(paramsChild);
            TextStyle(button, "#FFFFFF", "#000000");
            String s = findList(SHOP_ID, listSKU.get(k).getTPRDCD());
            if (!s.equals("N")) {
                s = Const.RoundNumber(s);
                button.setBackgroundColor(Color.parseColor("#ff66cc"));
            } else {
            }
            button.setText(s);
            lnroot.addView(button);
        }
    }
}
