package com.orion.salesman._summary._adapter;

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
import com.orion.salesman._result._object.DATA_API_130;
import com.orion.salesman._result._object.RouteSalesShop;
import com.orion.salesman._summary._fragment.SalesShopFragment;

import java.util.List;

/**
 * Created by maidinh on 14/9/2016.
 */
public class SalesShopFragmentAdapter extends RecyclerView.Adapter<SalesShopFragmentAdapter.MyViewHolder> {
    private String TAG = "RouteSalesShopAdapter";
    private List<RouteSalesShop> moviesList;
    private Context context;
    private DATA_API_130 data_api_130;
    private String TTL;

    public void updateReceiptsList(List<RouteSalesShop> moviesList, String TTL) {
        if (moviesList.size() > 0)
            moviesList.remove(moviesList.size() - 1);
        this.TTL = TTL;
        this.moviesList = moviesList;
        this.notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvOne, tvTwo, tvThree, tvFour;
        public RecyclerView recyclerView;
        public LinearLayout lnClick, lnContainsList;
        public SalesShopFragmentAdapterChild adapterChild;
        public MyViewHolder(View view) {
            super(view);
            tvOne = (TextView) view.findViewById(R.id.tvOne);
            tvTwo = (TextView) view.findViewById(R.id.tvTwo);
            tvThree = (TextView) view.findViewById(R.id.tvThree);
            tvFour = (TextView) view.findViewById(R.id.tvFour);
            recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            adapterChild = new SalesShopFragmentAdapterChild();
            recyclerView.setAdapter(adapterChild);
            lnClick = (LinearLayout) view.findViewById(R.id.lnClick);
            lnContainsList = (LinearLayout) view.findViewById(R.id.lnContainsList);
        }
    }

    SalesShopFragment fm;

    public SalesShopFragmentAdapter(Context context, List<RouteSalesShop> moviesList, String TTL, SalesShopFragment fm) {
        if (moviesList.size() > 0)
            moviesList.remove(moviesList.size() - 1);
        this.TTL = TTL;
        this.context = context;
        this.moviesList = moviesList;
        this.fm = fm;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_summary_sale_shop_adapter, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final RouteSalesShop movie = moviesList.get(position);
//        Const.setBackGroundTextOne(holder.tvOne);
        holder.lnContainsList.setVisibility(movie.isShow() ? View.VISIBLE : View.GONE);
        holder.tvOne.setText(movie.getV1());
        holder.tvTwo.setText(TTL);
        holder.tvThree.setText(movie.getV5());
        holder.tvFour.setText(Const.RoundNumber(movie.getV6()) + "%");
        holder.lnClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                movie.setIsShow(movie.isShow() ? false : true);
                notifyItemChanged(position);
                fm.ScrollEndList(position);

            }
        });


        holder.adapterChild.setData(movie.getArrSalesShops(),TTL);


//        holder.recyclerView.setLayoutManager(new LinearLayoutManager(context));
//        List<RouteSalesShop> arrayList = movie.getArrSalesShops();
//        holder.recyclerView.setAdapter(new SalesShopFragmentAdapterChild(context, arrayList, TTL));
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }
}