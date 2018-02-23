package com.orion.salesman._summary._adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.orion.salesman.R;
import com.orion.salesman._class.Const;
import com.orion.salesman._summary._object.Delivery;

import java.util.List;

/**
 * Created by maidinh on 3/8/2016.
 */
public class DeliveryAdapter extends RecyclerView.Adapter<DeliveryAdapter.MyViewHolder> {

    private List<Delivery> moviesList;
    private Context context;

    public void updateReceiptsList(List<Delivery> newlist) {
        if (newlist != null) {
            moviesList.clear();
            moviesList.addAll(newlist);
            if (moviesList.size() > 0)
                moviesList.remove(moviesList.size() - 1);
            this.notifyDataSetChanged();
        }
    }

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

    public DeliveryAdapter(Context context, List<Delivery> moviesList) {
        if (moviesList.size() > 0)
            moviesList.remove(moviesList.size() - 1);
        this.context = context;
        this.moviesList = moviesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_delivery_new, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Delivery movie = moviesList.get(position);
//        Const.setTextBlack(holder.tvOne);
//        Const.setColor_0(holder.tvOne);
//        Const.setColor_0(holder.tvTwo);
//        Const.setColor_0(holder.tvThree);
//        Const.setColor_0(holder.tvFour);
//        Const.setColor_0(holder.tvFive);
//        Const.setColor_0(holder.tvSix);
        holder.tvOne.setText(movie.getV2());
        holder.tvTwo.setText(Const.RoundNumber(movie.getV3()));
        holder.tvThree.setText(Const.RoundNumber(movie.getV4()));
        holder.tvFour.setText(Const.RoundNumber(movie.getV5()));
        holder.tvFive.setText(Const.RoundNumber(movie.getV6()));
        holder.tvSix.setText(Const.RoundNumber(movie.getV7()));
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }
}