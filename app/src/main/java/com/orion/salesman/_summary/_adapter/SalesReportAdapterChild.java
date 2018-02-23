package com.orion.salesman._summary._adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.orion.salesman.R;
import com.orion.salesman._class.Const;
import com.orion.salesman._summary._object.SalesReport;

import java.util.List;

/**
 * Created by maidinh on 11/8/2016.
 */
public class SalesReportAdapterChild extends RecyclerView.Adapter<SalesReportAdapterChild.MyViewHolder> {
    private String TAG = "SalesReportAdapter";
    private List<SalesReport> moviesList;
    private Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvOne, tvTwo, tvThree, tvFour,tvFive;

        public MyViewHolder(View view) {
            super(view);
            tvOne = (TextView) view.findViewById(R.id.tvOne);
            tvTwo = (TextView) view.findViewById(R.id.tvTwo);
            tvThree = (TextView) view.findViewById(R.id.tvThree);
            tvFour = (TextView) view.findViewById(R.id.tvFour);
            tvFive = (TextView) view.findViewById(R.id.tvFive);
        }
    }
    public SalesReportAdapterChild()
    {

    }

    public void setData(List<SalesReport> data) {
        if (moviesList != data) {
            moviesList = data;
            notifyDataSetChanged();
        }
    }
    public SalesReportAdapterChild(Context context, List<SalesReport> moviesList) {
        this.context = context;
        this.moviesList = moviesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_sales_report_new, parent, false);

        return new MyViewHolder(itemView);
    }

    public void setColorTitle(TextView tv) {
        tv.setBackgroundColor(Color.parseColor("#ebf5fc"));
    }


    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        SalesReport movie = moviesList.get(position);
//        Const.fixMargin(holder.tvOne);
//        Const.fixMargin(holder.tvTwo);
//        Const.fixMargin(holder.tvThree);
//        Const.fixMargin(holder.tvFour);
//
//        Const.setBackGroundChild(holder.tvOne);
//        Const.setBackGroundChild_2(holder.tvTwo);
//        Const.setBackGroundChild_2(holder.tvThree);
//        Const.setBackGroundChild_2(holder.tvFour);
//
//        Const.setTextBlack(holder.tvOne);
//        Const.setTextBlack(holder.tvTwo);
//        Const.setTextBlack(holder.tvThree);
//        Const.setTextBlack(holder.tvFour);
        holder.tvOne.setText("" + movie.getV4());
        holder.tvTwo.setText(Const.RoundNumber(movie.getV6()));
        holder.tvThree.setText(Const.RoundNumber(movie.getV5()));
        float TGT = Float.parseFloat(movie.getV7());
        TGT = (float) Math.round(TGT * 10) / 10;
        holder.tvFour.setText(TGT + "%");


        float TGT_TODAY = Float.parseFloat(movie.getV12());
        TGT_TODAY = (float) Math.round(TGT_TODAY * 10) / 10;
        holder.tvFive.setText(""+TGT_TODAY);

    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }
}