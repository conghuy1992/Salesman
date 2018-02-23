package com.orion.salesman._adaper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.orion.salesman.R;
import com.orion.salesman._object.AddressSQLite;
import com.orion.salesman._object.CodeH;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by maidinh on 29/8/2016.
 */
public class CustomAdapterCodeHTable extends BaseAdapter {
    private Context context;
    private List<CodeH> arrayList;
    private int check; // check:0->Route ; check:1->Channel, 2:reason

    public CustomAdapterCodeHTable(Context context, List<CodeH> arrayList, int check) {
        this.context = context;
        this.arrayList = arrayList;
        this.check = check;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.custom_adapter_new_shop, null);
        TextView tvTitle = (TextView) view.findViewById(R.id.tvTitle);
        if (check == 0)
            tvTitle.setText(arrayList.get(i).getCODEDESC());
        else if (check == 1)
            tvTitle.setText(arrayList.get(i).getCODEDESC());
        else if (check == 2)
            tvTitle.setText(arrayList.get(i).getCODEDESC());
        return view;
    }
}
