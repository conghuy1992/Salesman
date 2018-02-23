package com.orion.salesman._adaper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.orion.salesman.R;
import com.orion.salesman._object.ProductInfor;

import java.util.List;

/**
 * Created by Huy on 15/9/2016.
 */
public class CustomSpinnerNonShop extends BaseAdapter {
    private Context context;
    List<ProductInfor> productInforList;

    public CustomSpinnerNonShop(Context context, List<ProductInfor> productInforList) {
        this.context = context;
        this.productInforList = productInforList;
    }

    @Override
    public int getCount() {
        return productInforList.size();
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
        private TextView tvTitle;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
//        view = inflater.inflate(R.layout.custom_adapter_new_shop, null);
//        TextView tvTitle = (TextView) view.findViewById(R.id.tvTitle);
//        tvTitle.setText(productInforList.get(i).getBRANDNM());

        ViewHolder holder;

        if (view == null) {
            view = inflater.inflate(R.layout.custom_adapter_new_shop, null);
            holder = new ViewHolder();
            holder.tvTitle = (TextView) view.findViewById(R.id.tvTitle);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        holder.tvTitle.setText(productInforList.get(i).getBRANDNM());


        return view;
    }
}
