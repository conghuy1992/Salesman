package com.orion.salesman._summary._adapter;

import android.content.Context;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.orion.salesman.R;
import com.orion.salesman._class.Const;
import com.orion.salesman._summary._object.SalesReport;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Huy on 20/9/2016.
 */
public class DisplayAdapter extends RecyclerView.Adapter<DisplayAdapter.MyViewHolder> {
    private String TAG = "SalesReportAdapter";
    private List<SalesReport> moviesList;
    private Context context;

    public void update(List<SalesReport> moviesList) {
        this.moviesList = moviesList;
        this.notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvOne, tvTwo, tvThree, tvFour;

        public MyViewHolder(View view) {
            super(view);
            tvOne = (TextView) view.findViewById(R.id.tvOne);
            tvTwo = (TextView) view.findViewById(R.id.tvTwo);
            tvThree = (TextView) view.findViewById(R.id.tvThree);
            tvFour = (TextView) view.findViewById(R.id.tvFour);
        }
    }

    public DisplayAdapter(Context context, List<SalesReport> moviesList) {
        this.context = context;
        this.moviesList = moviesList;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_display_report, parent, false);

        return new MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        SalesReport movie = moviesList.get(position);
//        Const.setTextBlack(holder.tvOne);
//        Const.setTextBlack(holder.tvTwo);
//        Const.setTextBlack(holder.tvThree);
//        Const.setTextBlack(holder.tvFour);
//        Const.setBackGroundTextOne(holder.tvOne);
//        Const.setColor_0(holder.tvTwo);
//        Const.setColor_0(holder.tvThree);
//        Const.setColor_0(holder.tvFour);

        holder.tvOne.setText(movie.getV2());
        holder.tvTwo.setText(Const.RoundNumber(movie.getV5()));
        holder.tvThree.setText(Const.RoundNumber(movie.getV3()));
        float TGT = Float.parseFloat(movie.getV7());
        TGT = (float) Math.round(TGT * 10) / 10;
        holder.tvFour.setText(TGT + "%");
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }
}