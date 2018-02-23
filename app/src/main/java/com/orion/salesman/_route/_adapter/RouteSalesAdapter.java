package com.orion.salesman._route._adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.orion.salesman.R;
import com.orion.salesman._result._object.RouteSales;

import java.util.List;

/**
 * Created by maidinh on 5/8/2016.
 */
public class RouteSalesAdapter extends RecyclerView.Adapter<RouteSalesAdapter.MyViewHolder> {
    private List<RouteSales> moviesList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvOne, tvTwo, tvThree, tvFour, tvFive, tvSix;

        public MyViewHolder(View view) {
            super(view);
            tvOne = (TextView) view.findViewById(R.id.tvOne);
            tvTwo = (TextView) view.findViewById(R.id.tvTwo);
            tvThree = (TextView) view.findViewById(R.id.tvThree);
            tvFour = (TextView) view.findViewById(R.id.tvFour);
            tvFive = (TextView) view.findViewById(R.id.tvFive);
        }
    }

    public RouteSalesAdapter(List<RouteSales> moviesList) {
        this.moviesList = moviesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_sales, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        RouteSales movie = moviesList.get(position);
        holder.tvOne.setText(movie.getColumnsOne());
        holder.tvTwo.setText(movie.getColumnsTwo());
        holder.tvThree.setText(movie.getColumnsThree());
        holder.tvFour.setText(movie.getColumnsFour());
        holder.tvFive.setText(movie.getColumnsFive());
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }
}