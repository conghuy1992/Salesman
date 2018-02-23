package com.orion.salesman._adaper;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.orion.salesman.R;
import com.orion.salesman._class.Const;
import com.orion.salesman._object.ObjA0127;

import java.util.List;

/**
 * Created by maidinh on 9/11/2016.
 */
public class AdapterSales extends RecyclerView.Adapter<AdapterSales.MyViewHolder> {
    private List<ObjA0127> moviesList;
    private int type; // 0:sales   1:promotion
    private Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView S1, S2, S3, S4, S5;

        public MyViewHolder(View view) {
            super(view);
            S1 = (TextView) view.findViewById(R.id.S1);
            S2 = (TextView) view.findViewById(R.id.S2);
            S3 = (TextView) view.findViewById(R.id.S3);
            S4 = (TextView) view.findViewById(R.id.S4);
            S5 = (TextView) view.findViewById(R.id.S5);
        }
    }

    public AdapterSales(Context context, List<ObjA0127> moviesList, int type) {
        this.context = context;
        this.moviesList = moviesList;
        this.type = type;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_history_sales, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        ObjA0127 movie = moviesList.get(position);
        if (type == 0) {
            holder.S1.setText(movie.getV3() + " " + movie.getV4());
            holder.S2.setText(movie.getV5());
            holder.S3.setText(movie.getV6());

            long TGT = (long) Float.parseFloat(movie.getV9());
//            TGT = (float) Math.round(TGT * 1000) / 1000;

            holder.S4.setText(Const.formatAMT(TGT));
            holder.S5.setText(movie.getV11());
        } else {
            holder.S1.setText(movie.getV3() + " " + movie.getV4());
            holder.S2.setText(movie.getV7());
            holder.S3.setText(movie.getV8());

            long TGT =  (long)Float.parseFloat(movie.getV10());
//            TGT = (float) Math.round(TGT * 1000) / 1000;

            holder.S4.setText(Const.formatAMT(TGT));
            holder.S5.setText(movie.getV12());
        }
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }
}