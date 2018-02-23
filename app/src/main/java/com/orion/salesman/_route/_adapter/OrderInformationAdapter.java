package com.orion.salesman._route._adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.orion.salesman.R;
import com.orion.salesman._class.Const;
import com.orion.salesman._route._object.OrderInformation;

import java.util.List;

/**
 * Created by maidinh on 4/8/2016.
 */
public class OrderInformationAdapter extends RecyclerView.Adapter<OrderInformationAdapter.MyViewHolder> {
    private List<OrderInformation> moviesList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvOne, tvTwo, tvThree, tvFour, tvFive, tvSix;

        public MyViewHolder(View view) {
            super(view);
            tvOne = (TextView) view.findViewById(R.id.tvOne);
            tvTwo = (TextView) view.findViewById(R.id.tvTwo);
            tvThree = (TextView) view.findViewById(R.id.tvThree);
            tvFour = (TextView) view.findViewById(R.id.tvFour);
            tvFive = (TextView) view.findViewById(R.id.tvFive);
            tvSix = (TextView) view.findViewById(R.id.tvSix);
        }
    }

    public OrderInformationAdapter(List<OrderInformation> moviesList) {
        this.moviesList = moviesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_order_info, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        OrderInformation movie = moviesList.get(position);
        holder.tvOne.setText(Const.RoundNumber(movie.getColumnsOne()));
        holder.tvTwo.setText(Const.RoundNumber(movie.getColumnsTwo()));
        holder.tvThree.setText(Const.RoundNumber(movie.getColumnsThree()));
        holder.tvFour.setText(Const.RoundNumber(movie.getColumnsFour()));
        holder.tvFive.setText(Const.RoundNumber(movie.getColumnsFive()));
        holder.tvSix.setText(Const.RoundNumber(movie.getColumnsSix()));
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }
}