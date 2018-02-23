package com.orion.salesman._result._adapter;

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

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.orion.salesman.R;
import com.orion.salesman._class.Const;
import com.orion.salesman._result._fragment.ResultSalesFragment;
import com.orion.salesman._summary._object.SalesReport;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by maidinh on 13/9/2016.
 */
public class ResultSalesAdapter extends RecyclerView.Adapter<ResultSalesAdapter.MyViewHolder> {
    private String TAG = "SalesReportAdapter";
    private List<SalesReport> moviesList;
    private Context context;
    private List<String> arrChild = new ArrayList<>();
    private ResultSalesFragment fragment;

    public void updateReceiptsList(List<SalesReport> newlist) {
        if (newlist != null) {
            moviesList.clear();
            arrChild.clear();
            List<SalesReport> arr = new ArrayList<>();
            List<SalesReport> temp = new ArrayList<>();
            if (newlist.size() > 0) {
                for (int i = 0; i < newlist.size(); i++) {
                    SalesReport ob = newlist.get(i);
                    int ISGROUP = Integer.parseInt(ob.getV8());
                    if (ISGROUP == 1) {
                        arr.add(ob);
                        arrChild.add(new Gson().toJson(temp));
                        temp = new ArrayList<>();
                    } else if (ISGROUP == 0) {
                        temp.add(ob);
                    }
                }
                arr.remove(arr.size() - 1);
            }
            this.moviesList = arr;
            this.notifyDataSetChanged();
        }
    }

    /*
    * vitri:0 -> sales report
    * vitri:1 -> sales shop
    * vitri:2 -> display
    * */

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvOne, tvTwo, tvThree, tvFour, tvToday, tvFive, tvSix, tvSeven, tvEight;
        public LinearLayout lnDetails, llrow;
        public RecyclerView recycler_view;
        private ResultSalesAdapterChild adapterChild;

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


            tvToday = (TextView) view.findViewById(R.id.tvToday);
            lnDetails = (LinearLayout) view.findViewById(R.id.lnDetails);
            llrow = (LinearLayout) view.findViewById(R.id.llrow);
            recycler_view = (RecyclerView) view.findViewById(R.id.recycler_view);
            recycler_view.setLayoutManager(new LinearLayoutManager(context));
            adapterChild = new ResultSalesAdapterChild();
            recycler_view.setAdapter(adapterChild);
        }
    }

    public ResultSalesAdapter(Context context, List<SalesReport> moviesList, ResultSalesFragment fragment) {
        List<SalesReport> arr = new ArrayList<>();
        List<SalesReport> temp = new ArrayList<>();
        if (moviesList.size() > 0) {
            for (int i = 0; i < moviesList.size(); i++) {
                SalesReport ob = moviesList.get(i);
                int ISGROUP = Integer.parseInt(ob.getV8());
                if (ISGROUP == 1) {
                    arr.add(ob);
                    arrChild.add(new Gson().toJson(temp));
                    temp = new ArrayList<>();
                } else if (ISGROUP == 0) {
                    temp.add(ob);
                }
            }
            arr.remove(arr.size() - 1);
        }
        this.context = context;
        this.moviesList = arr;
        this.fragment = fragment;


    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.result_row_sales_report, parent, false);

        return new MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final SalesReport movie = moviesList.get(position);


        holder.tvFive.setText("" + Const.routeTGT(movie.getV13()));
        holder.tvSix.setText("" + Const.routeTGT(movie.getV14()));
        holder.tvSeven.setText("" + Const.routeTGT(movie.getV15()));
        holder.tvEight.setText("" + Const.routeTGT(movie.getV16()) + "%");

        holder.tvToday.setText(Const.RoundNumber(movie.getV9()));
        holder.tvOne.setText(movie.getV2());
        holder.tvTwo.setText(Const.RoundNumber(movie.getV6()));
        holder.tvThree.setText(Const.RoundNumber(movie.getV5()));
        float TGT = Float.parseFloat(movie.getV7());
        TGT = (float) Math.round(TGT * 10) / 10;
        holder.tvFour.setText(TGT + "%");
        holder.lnDetails.setVisibility(movie.isShow() ? View.VISIBLE : View.GONE);
        holder.llrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                movie.setIsShow(movie.isShow() ? false : true);
                notifyItemChanged(position);
                fragment.ScrollEndList(position);

            }
        });
        Type listType = new TypeToken<ArrayList<SalesReport>>() {
        }.getType();
        ArrayList<SalesReport> saleChild = new Gson().fromJson(arrChild.get(position), listType);
        holder.adapterChild.setData(saleChild);


//        holder.recycler_view.setLayoutManager(new LinearLayoutManager(context));
//        holder.recycler_view.setItemAnimator(new DefaultItemAnimator());
//        Type listType = new TypeToken<ArrayList<SalesReport>>() {
//        }.getType();
//        ArrayList<SalesReport> saleChild = new Gson().fromJson(arrChild.get(position), listType);
//        holder.recycler_view.setAdapter(new ResultSalesAdapterChild(context, saleChild));


    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }
}