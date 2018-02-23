package com.orion.salesman._adaper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.orion.salesman.R;
import com.orion.salesman._object.PromotionCal;

import java.util.List;

/**
 * Created by huy on 10/10/2016.
 */

public class PromotionSP extends BaseAdapter{
    Context context;
    List<PromotionCal> LIST;
    public PromotionSP(Context context,List<PromotionCal>LIST){
        this.context=context;
        this.LIST=LIST;
    }
    @Override
    public int getCount() {
        return LIST.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.custom_sp_promotion, null);
        TextView tvTitle = (TextView)convertView.findViewById(R.id.tvTitle);
        tvTitle.setText(LIST.get(position).getS1());
        return convertView;
    }
}
