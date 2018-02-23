package com.orion.salesman._adaper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.orion.salesman.R;
import com.orion.salesman._object.SeBranch;

import java.util.List;

/**
 * Created by maidinh on 13/12/2016.
 */

public class SeAdapterBranch extends BaseAdapter {
    private Context context;
    private List<SeBranch> listData;
    private int kind;// 1: setV2()  -  2: setV4()

    public void updateList(List<SeBranch> listData) {
        if (listData != null && listData.size() > 0) {
            this.listData = listData;
            this.notifyDataSetChanged();
        }
    }

    public SeAdapterBranch(Context context, List<SeBranch> listData, int kind) {
        this.context = context;
        this.listData = listData;
        this.kind = kind;
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
        ViewHolder holder;

        if (view == null) {
            view = inflater.inflate(R.layout.custom_spinner, null);
            holder = new ViewHolder();
            holder.tvTitle = (TextView) view.findViewById(R.id.tvTitle);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        String title = "";
        if (kind == 1)
            title = listData.get(i).getV2().trim();
        else
            title = listData.get(i).getV4().trim();
        holder.tvTitle.setText(title);

        return view;
    }
}
