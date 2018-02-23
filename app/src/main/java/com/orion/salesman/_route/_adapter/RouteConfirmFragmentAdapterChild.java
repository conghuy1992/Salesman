package com.orion.salesman._route._adapter;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.orion.salesman.R;
import com.orion.salesman._class.Const;
import com.orion.salesman._route._object.RouteConfirm;

import java.util.List;

/**
 * Created by maidinh on 22/8/2016.
 */
public class RouteConfirmFragmentAdapterChild extends RecyclerView.Adapter<RouteConfirmFragmentAdapterChild.MyViewHolder> {
    private List<RouteConfirm> moviesList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvOne, tvTwo, tvThree, tvFour, tvFive, tvSix, tvSeven, tvOrderRS, tvOrderDS;

        public MyViewHolder(View view) {
            super(view);
            tvOne = (TextView) view.findViewById(R.id.tvOne);
            tvTwo = (TextView) view.findViewById(R.id.tvTwo);
            tvThree = (TextView) view.findViewById(R.id.tvThree);
            tvFour = (TextView) view.findViewById(R.id.tvFour);
            tvFive = (TextView) view.findViewById(R.id.tvFive);
            tvSix = (TextView) view.findViewById(R.id.tvSix);
            tvSeven = (TextView) view.findViewById(R.id.tvSeven);
            tvOrderRS = (TextView) view.findViewById(R.id.tvOrderRS);
            tvOrderDS = (TextView) view.findViewById(R.id.tvOrderDS);
        }
    }

    public RouteConfirmFragmentAdapterChild(List<RouteConfirm> moviesList) {
        this.moviesList = moviesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_order_product, parent, false);

        return new MyViewHolder(itemView);
    }

    public void setTextColorBlack(TextView v) {
        v.setTextColor(Color.BLACK);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        RouteConfirm movie = moviesList.get(position);
        holder.tvOrderRS.setVisibility(View.GONE);
        holder.tvOrderDS.setVisibility(View.GONE);
        holder.tvOne.setText(movie.getV1());
        String tv_2 = movie.getV2();
        if (tv_2.length() == 0)
            tv_2 = "0";
        if (!movie.isCheck()) {
            holder.tvTwo.setText(tv_2);
            holder.tvThree.setText(movie.getV3());
            holder.tvFour.setText("");
            holder.tvFive.setText("");
        } else {
            holder.tvTwo.setText("");
            holder.tvThree.setText("");
            holder.tvFour.setText(tv_2);
            holder.tvFive.setText(movie.getV3());
        }
        holder.tvSix.setText("");
        holder.tvSeven.setText("");
        setTextColorBlack(holder.tvOne);
        setTextColorBlack(holder.tvTwo);
        setTextColorBlack(holder.tvThree);
        setTextColorBlack(holder.tvFour);
        setTextColorBlack(holder.tvFive);
        setTextColorBlack(holder.tvSix);
        setTextColorBlack(holder.tvSeven);
        Const.setColorRowChild(holder.tvOne);
        Const.setColorRowChild(holder.tvTwo);
        Const.setColorRowChild(holder.tvThree);
        Const.setColorRowChild(holder.tvFour);
        Const.setColorRowChild(holder.tvFive);
        Const.setColorRowChild(holder.tvSix);
        Const.setColorRowChild(holder.tvSeven);
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }
}
