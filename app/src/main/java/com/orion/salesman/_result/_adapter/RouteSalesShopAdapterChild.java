package com.orion.salesman._result._adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.orion.salesman.R;
import com.orion.salesman._class.Const;
import com.orion.salesman._result._object.DATA_API_130;
import com.orion.salesman._result._object.RouteSalesShop;

import java.util.List;

/**
 * Created by maidinh on 13/9/2016.
 */
public class RouteSalesShopAdapterChild extends RecyclerView.Adapter<RouteSalesShopAdapterChild.MyViewHolder> {
    private String TAG = "RouteSalesShopAdapterChild";
    private List<RouteSalesShop> moviesList;
    private Context context;
    private int mRowIndex = -1;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvOne, tvTwo, tvThree, tvFour, tvFive, tvSix, tvSeven, tvEight;

        public MyViewHolder(View view) {
            super(view);
            Log.d("MyViewHolder", "MyViewHolder");
            tvOne = (TextView) view.findViewById(R.id.tvOne);
            tvTwo = (TextView) view.findViewById(R.id.tvTwo);
            tvThree = (TextView) view.findViewById(R.id.tvThree);
            tvFour = (TextView) view.findViewById(R.id.tvFour);
            tvFive = (TextView) view.findViewById(R.id.tvFive);
            tvSix = (TextView) view.findViewById(R.id.tvSix);
            tvSeven = (TextView) view.findViewById(R.id.tvSeven);
            tvEight = (TextView) view.findViewById(R.id.tvEight);
        }
    }

    public void setData(List<RouteSalesShop> data) {
        if (moviesList != data) {
            moviesList = data;
            notifyDataSetChanged();
        }
    }

    public void setRowIndex(int index) {
        mRowIndex = index;
    }

    public RouteSalesShopAdapterChild() {

    }

    public RouteSalesShopAdapterChild(Context context, List<RouteSalesShop> moviesList) {
        this.context = context;
        this.moviesList = moviesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_result_sale_shop_adapter_new, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        RouteSalesShop movie = moviesList.get(position);

        holder.tvOne.setText(movie.getV1());
        holder.tvTwo.setText(movie.getV2());
        holder.tvThree.setText(movie.getV3());
        holder.tvFour.setText(Const.RoundNumber(movie.getV4())+"%");
        holder.tvFive.setText(movie.getV5());
        holder.tvSix.setText(Const.RoundNumber(movie.getV6())+"%");
        holder.tvSeven.setText(movie.getV7());
        holder.tvEight.setText(Const.RoundNumber(movie.getV8())+"%");
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }
}