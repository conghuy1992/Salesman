package com.orion.salesman._result._adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.orion.salesman.R;
import com.orion.salesman._class.Const;
import com.orion.salesman._summary._object.SalesReport;

import java.util.List;

/**
 * Created by maidinh on 13/9/2016.
 */
public class ResultSalesAdapterChild extends RecyclerView.Adapter<ResultSalesAdapterChild.MyViewHolder> {
    private String TAG = "SalesReportAdapter";
    private List<SalesReport> moviesList;
    private Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvOne, tvTwo, tvThree, tvFour, tvToday, tvFive, tvSix, tvSeven, tvEight;

        public MyViewHolder(View view) {
            super(view);

            tvOne = (TextView) view.findViewById(R.id.tvOne);
            tvTwo = (TextView) view.findViewById(R.id.tvTwo);
            tvThree = (TextView) view.findViewById(R.id.tvThree);
            tvFour = (TextView) view.findViewById(R.id.tvFour);
            tvToday = (TextView) view.findViewById(R.id.tvToday);

            tvFive = (TextView) view.findViewById(R.id.tvFive);
            tvSix = (TextView) view.findViewById(R.id.tvSix);
            tvSeven = (TextView) view.findViewById(R.id.tvSeven);
            tvEight = (TextView) view.findViewById(R.id.tvEight);
        }
    }
    public void setData(List<SalesReport> data) {
        if (moviesList != data) {
            moviesList = data;
            notifyDataSetChanged();
        }
    }
    public ResultSalesAdapterChild()
    {

    }
    public ResultSalesAdapterChild(Context context, List<SalesReport> moviesList) {
        this.context = context;
        this.moviesList = moviesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.result_row_sales_report_new, parent, false);

        return new MyViewHolder(itemView);
    }

    public void setColorTitle(TextView tv) {
        tv.setBackgroundColor(Color.parseColor("#ebf5fc"));
    }


    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        SalesReport movie = moviesList.get(position);


        holder.tvFive.setText("" + Const.routeTGT(movie.getV13()));
        holder.tvSix.setText("" + Const.routeTGT(movie.getV14()));
        holder.tvSeven.setText("" + Const.routeTGT(movie.getV15()));
        holder.tvEight.setText("" + Const.routeTGT(movie.getV16()) + "%");

        holder.tvToday.setText(Const.RoundNumber(movie.getV9()));
        holder.tvOne.setText("" + movie.getV4());
        holder.tvTwo.setText(Const.RoundNumber(movie.getV6()));
        holder.tvThree.setText(Const.RoundNumber(movie.getV5()));
        float TGT = Float.parseFloat(movie.getV7());
        TGT = (float) Math.round(TGT * 10) / 10;
        holder.tvFour.setText(TGT + "%");
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }
}