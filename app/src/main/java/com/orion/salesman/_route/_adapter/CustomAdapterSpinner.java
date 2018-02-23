package com.orion.salesman._route._adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.orion.salesman.R;
import com.orion.salesman._object.CodeH;

import java.util.List;

/**
 * Created by maidinh on 22/8/2016.
 */
public class CustomAdapterSpinner extends BaseAdapter {
    private Context context;
    private List<CodeH> listData;

    public CustomAdapterSpinner(Context context, List<CodeH> listData) {
        this.context = context;
        this.listData = listData;
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    static class ViewHolder {
        private TextView tvTitle;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
//        view = inflater.inflate(R.layout.custom_spinner, null);
//        TextView tvTitle = (TextView) view.findViewById(R.id.tvTitle);
//        tvTitle.setText(listData.get(i).getCODEDESC());
        ViewHolder holder;

        if (view == null) {
            view = inflater.inflate(R.layout.custom_spinner, null);
            holder = new ViewHolder();
            holder.tvTitle = (TextView) view.findViewById(R.id.tvTitle);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        holder.tvTitle.setText(listData.get(i).getCODEDESC());

        return view;
    }
}
