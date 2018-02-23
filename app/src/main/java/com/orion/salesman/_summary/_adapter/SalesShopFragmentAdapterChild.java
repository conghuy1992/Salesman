package com.orion.salesman._summary._adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.orion.salesman.R;
import com.orion.salesman._class.Const;
import com.orion.salesman._result._object.RouteSalesShop;

import java.util.List;

/**
 * Created by maidinh on 14/9/2016.
 */
public class SalesShopFragmentAdapterChild extends RecyclerView.Adapter<SalesShopFragmentAdapterChild.MyViewHolder> {

    private List<RouteSalesShop> moviesList;
    private Context context;
    private String TTL;

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

    public SalesShopFragmentAdapterChild() {

    }

    public void setData(List<RouteSalesShop> data,String TTL) {
        if (moviesList != data) {
            this.moviesList = data;
            this.TTL=TTL;
            notifyDataSetChanged();
        }
    }

    public SalesShopFragmentAdapterChild(Context context, List<RouteSalesShop> moviesList, String TTL) {
        this.context = context;
        this.moviesList = moviesList;
        this.TTL = TTL;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_summary_sale_shop_adapter_new, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        RouteSalesShop movie = moviesList.get(position);

        holder.tvOne.setText(movie.getV1());
        holder.tvTwo.setText(TTL);
        holder.tvThree.setText(movie.getV5());
        holder.tvFour.setText(Const.RoundNumber(movie.getV6()) + "%");
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }
}