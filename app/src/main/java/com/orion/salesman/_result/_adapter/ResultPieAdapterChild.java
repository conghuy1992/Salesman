package com.orion.salesman._result._adapter;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.orion.salesman.R;
import com.orion.salesman._class.Const;
import com.orion.salesman._object.Object_304;

import java.util.List;

/**
 * Created by Huy on 17/9/2016.
 */
public class ResultPieAdapterChild  extends RecyclerView.Adapter<ResultPieAdapterChild.MyViewHolder> {

    private List<Object_304> moviesList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvOne, tvTwo, tvThree, tvFour, tvFive;

        public MyViewHolder(View view) {
            super(view);
            tvOne = (TextView) view.findViewById(R.id.tvOne);
            tvTwo = (TextView) view.findViewById(R.id.tvTwo);
            tvThree = (TextView) view.findViewById(R.id.tvThree);
            tvFour = (TextView) view.findViewById(R.id.tvFour);
            tvFive = (TextView) view.findViewById(R.id.tvFive);

        }
    }

    public ResultPieAdapterChild(List<Object_304> moviesList) {
        this.moviesList = moviesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_result_pie_child, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final Object_304 movie = moviesList.get(position);

        holder.tvOne.setText(movie.getV5());
//        holder.tvTwo.setText(Const.RoundNumber(movie.getV6()));
        holder.tvTwo.setText(Const.formatAMTFloat(Float.parseFloat(movie.getV6())));
        holder.tvThree.setText(Const.RoundNumber(movie.getV7()));
        holder.tvFour.setText(Const.RoundNumber(movie.getV8()));
//        holder.tvFive.setText(Const.RoundNumber(movie.getV9()));
        holder.tvFive.setText(Const.formatAMTFloat(Float.parseFloat(movie.getV9())));
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }
}