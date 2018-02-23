package com.orion.salesman._adaper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.orion.salesman.R;
import com.orion.salesman._object.AddressSQLite;

import java.util.ArrayList;

/**
 * Created by maidinh on 29/8/2016.
 */
public class CustomAdapterNewShop extends BaseAdapter {
    private Context context;
    private ArrayList<AddressSQLite> arrayList;
    // check   0:province - 1:district - 2:Delivery -3: street
    private int check;

    public CustomAdapterNewShop(Context context, ArrayList<AddressSQLite> arrayList, int check) {
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

     class ViewHolder {
        public TextView tvTitle;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
//        view = inflater.inflate(R.layout.custom_adapter_new_shop, null);
//        TextView tvTitle = (TextView) view.findViewById(R.id.tvTitle);
//        if (check == 0)
//            tvTitle.setText(arrayList.get(i).getADDR4());
//        else if (check == 1)
//            tvTitle.setText(arrayList.get(i).getADDR5());
//        else if (check == 2)
//            tvTitle.setText(arrayList.get(i).getADDR9());
//        else if (check == 3)
//            tvTitle.setText(arrayList.get(i).getADDR6());


        ViewHolder holder;

        if (view == null) {
            view = inflater.inflate(R.layout.custom_adapter_new_shop, null);
            holder = new ViewHolder();
            holder.tvTitle = (TextView) view.findViewById(R.id.tvTitle);
            view.setTag(holder);
        }
        else {
            holder = (ViewHolder) view.getTag();
        }

        if (check == 0)
            holder.tvTitle.setText(arrayList.get(i).getADDR4());
        else if (check == 1)
            holder.tvTitle.setText(arrayList.get(i).getADDR5());
        else if (check == 2)
            holder.tvTitle.setText(arrayList.get(i).getADDR9());
        else if (check == 3)
            holder.tvTitle.setText(arrayList.get(i).getADDR6());

        return view;
    }
}
