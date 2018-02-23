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
import com.orion.salesman._summary._fragment.SalesReportFragment;
import com.orion.salesman._summary._object.SalesReport;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by maidinh on 3/8/2016.
 */
public class SalesReportAdapter extends RecyclerView.Adapter<SalesReportAdapter.MyViewHolder> {
    private String TAG = "SalesReportAdapter";
    private List<SalesReport> moviesList;
    private Context context;
    private List<String> arrChild = new ArrayList<>();

    public void updateReceiptsList(List<SalesReport> moviesList, List<String> arrChild) {
        this.moviesList = moviesList;
        this.arrChild = arrChild;
        this.notifyDataSetChanged();
    }

    /*
    * vitri:0 -> sales report
    * vitri:1 -> sales shop
    * vitri:2 -> display
    * */

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvOne, tvTwo, tvThree, tvFour, tvFive;
        public LinearLayout lnDetails, llrow;
        public RecyclerView recycler_view;
        public SalesReportAdapterChild adapterChild;
        public MyViewHolder(View view) {
            super(view);
            tvOne = (TextView) view.findViewById(R.id.tvOne);
            tvTwo = (TextView) view.findViewById(R.id.tvTwo);
            tvThree = (TextView) view.findViewById(R.id.tvThree);
            tvFour = (TextView) view.findViewById(R.id.tvFour);
            tvFive = (TextView) view.findViewById(R.id.tvFive);
            lnDetails = (LinearLayout) view.findViewById(R.id.lnDetails);
            llrow = (LinearLayout) view.findViewById(R.id.llrow);
            recycler_view = (RecyclerView) view.findViewById(R.id.recycler_view);
            recycler_view.setLayoutManager(new LinearLayoutManager(context));
            adapterChild = new SalesReportAdapterChild();
            recycler_view.setAdapter(adapterChild);
        }
    }

    SalesReportFragment fm;

    public SalesReportAdapter(Context context, List<SalesReport> moviesList, List<String> arrChild, SalesReportFragment fm) {
        this.context = context;
        this.moviesList = moviesList;
        this.arrChild = arrChild;
        this.fm = fm;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_sales_report, parent, false);

        return new MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final SalesReport movie = moviesList.get(position);
        holder.lnDetails.setVisibility(movie.isShow() ? View.VISIBLE : View.GONE);
        holder.tvOne.setText(movie.getV2());
        holder.tvTwo.setText(Const.RoundNumber(movie.getV6()));
        holder.tvThree.setText(Const.RoundNumber(movie.getV5()));
        float TGT = Float.parseFloat(movie.getV7());
        TGT = (float) Math.round(TGT * 10) / 10;
        holder.tvFour.setText(TGT + "%");

        float TGT_TODAY = Float.parseFloat(movie.getV12());
        TGT_TODAY = (float) Math.round(TGT_TODAY * 10) / 10;
        holder.tvFive.setText("" + TGT_TODAY);

        holder.llrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                movie.setIsShow(movie.isShow() ? false : true);
                notifyItemChanged(position);
                fm.ScrollEndList(position);
            }
        });
        Type listType = new TypeToken<ArrayList<SalesReport>>() {
        }.getType();
        ArrayList<SalesReport> saleChild = new Gson().fromJson(arrChild.get(position), listType);
        holder.adapterChild.setData(saleChild);



//        holder.recycler_view.setLayoutManager(new LinearLayoutManager(context));
//        Type listType = new TypeToken<ArrayList<SalesReport>>() {
//        }.getType();
//        ArrayList<SalesReport> saleChild = new Gson().fromJson(arrChild.get(position), listType);
//        holder.recycler_view.setAdapter(new SalesReportAdapterChild(context, saleChild));
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }
}