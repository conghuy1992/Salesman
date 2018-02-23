package com.orion.salesman._route._adapter;

import android.content.Context;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.orion.salesman.R;
import com.orion.salesman._class.Const;
import com.orion.salesman._result._adapter.RouteSalesShopAdapterChild;
import com.orion.salesman._result._fragment.RouteSalesShopFragment;
import com.orion.salesman._result._object.DATA_API_130;
import com.orion.salesman._result._object.RouteSalesShop;

import java.util.List;

/**
 * Created by maidinh on 5/8/2016.
 */
public class RouteSalesShopAdapter extends RecyclerView.Adapter<RouteSalesShopAdapter.MyViewHolder> {
    private String TAG = "RouteSalesShopAdapter";
    private List<RouteSalesShop> moviesList;
    private Context context;
    private DATA_API_130 data_api_130;
    RouteSalesShopFragment fragment;

    public void updateReceiptsList(List<RouteSalesShop> moviesList) {
        if (moviesList.size() > 0)
            moviesList.remove(moviesList.size() - 1);
        this.moviesList = moviesList;
        this.notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvOne, tvTwo, tvThree, tvFour, tvFive, tvSix, tvSeven, tvEight;
        public RecyclerView recyclerView;
        public LinearLayout lnClick, lnContainsList;
        private RouteSalesShopAdapterChild adapterChild;

        public MyViewHolder(View view) {
            super(view);
            tvOne = (TextView) view.findViewById(R.id.tvOne);
            tvTwo = (TextView) view.findViewById(R.id.tvTwo);
            tvThree = (TextView) view.findViewById(R.id.tvThree);
            tvFour = (TextView) view.findViewById(R.id.tvFour);
            tvFive = (TextView) view.findViewById(R.id.tvFive);
            tvSix = (TextView) view.findViewById(R.id.tvSix);
            tvSeven = (TextView) view.findViewById(R.id.tvSeven);
            tvEight = (TextView) view.findViewById(R.id.tvEight);
            recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            adapterChild = new RouteSalesShopAdapterChild();
            recyclerView.setAdapter(adapterChild);

            lnClick = (LinearLayout) view.findViewById(R.id.lnClick);
            lnContainsList = (LinearLayout) view.findViewById(R.id.lnContainsList);
        }
    }

    public RouteSalesShopAdapter(Context context, List<RouteSalesShop> moviesList, RouteSalesShopFragment fragment) {
        if (moviesList.size() > 0)
            moviesList.remove(moviesList.size() - 1);
        this.context = context;
        this.moviesList = moviesList;
        this.fragment = fragment;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_result_sale_shop_adapter, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final RouteSalesShop movie = moviesList.get(position);
//        Const.setBackGroundTextOne(holder.tvOne);
        holder.tvOne.setText(movie.getV1());
        holder.tvTwo.setText(movie.getV2());
        holder.tvThree.setText(movie.getV3());
        holder.tvFour.setText(Const.RoundNumber(movie.getV4()) + "%");
        holder.tvFive.setText(movie.getV5());
        holder.tvSix.setText(Const.RoundNumber(movie.getV6()) + "%");
        holder.tvSeven.setText(movie.getV7());
        holder.tvEight.setText(Const.RoundNumber(movie.getV8()) + "%");
        holder.lnContainsList.setVisibility(movie.isShow() ? View.VISIBLE : View.GONE);
        holder.lnClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                movie.setIsShow(movie.isShow() ? false : true);
                notifyItemChanged(position);
//                holder.lnContainsList.setVisibility(movie.isShow() ? View.VISIBLE : View.GONE);
                fragment.ScrollEndList(position);
            }
        });
        holder.adapterChild.setData(movie.getArrSalesShops()); // List of Strings
//        holder.adapterChild.setRowIndex(position);
//        holder.recyclerView.setLayoutManager(new LinearLayoutManager(context));
//        List<RouteSalesShop> arrayList = movie.getArrSalesShops();
//        holder.recyclerView.setAdapter(new RouteSalesShopAdapterChild(context, arrayList));
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }
}