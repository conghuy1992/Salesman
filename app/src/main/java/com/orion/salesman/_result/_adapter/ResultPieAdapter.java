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

import com.orion.salesman.R;
import com.orion.salesman._class.Const;
import com.orion.salesman._object.Object_304;
import com.orion.salesman._result._fragment.ResultGumFragment;
import com.orion.salesman._result._fragment.ResultPieFragment;
import com.orion.salesman._result._fragment.ResultSnackFragment;
import com.orion.salesman._result._object.ResultPie;

import java.util.List;

/**
 * Created by maidinh on 5/8/2016.
 */
public class ResultPieAdapter extends RecyclerView.Adapter<ResultPieAdapter.MyViewHolder> {
    String TAG = "ResultPieAdapter";
    private List<Object_304> moviesList;
    private Context context;
    ResultPieFragment fmPie;
    ResultSnackFragment fmSnack;
    ResultGumFragment fmGum;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvOne, tvTwo, tvThree, tvFour, tvFive;
        public LinearLayout lnDetails, lnChild;
        public RecyclerView recycler_view;

        public MyViewHolder(View view) {
            super(view);
            tvOne = (TextView) view.findViewById(R.id.tvOne);
            tvTwo = (TextView) view.findViewById(R.id.tvTwo);
            tvThree = (TextView) view.findViewById(R.id.tvThree);
            tvFour = (TextView) view.findViewById(R.id.tvFour);
            tvFive = (TextView) view.findViewById(R.id.tvFive);
            lnDetails = (LinearLayout) view.findViewById(R.id.lnDetails);
            lnChild = (LinearLayout) view.findViewById(R.id.lnChild);
            recycler_view = (RecyclerView) view.findViewById(R.id.recycler_view);

        }
    }

    public ResultPieAdapter(Context context, List<Object_304> moviesList, ResultPieFragment fmPie, ResultSnackFragment fmSnack, ResultGumFragment fmGum) {
        this.context = context;
        this.moviesList = moviesList;
        this.fmPie = fmPie;
        this.fmSnack = fmSnack;
        this.fmGum = fmGum;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_result_pie, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final Object_304 movie = moviesList.get(position);
//        Const.setBackGroundTextOne(holder.tvOne);
        holder.tvOne.setText(movie.getV3());
//        holder.tvTwo.setText(Const.RoundNumber(movie.getV6()));
        holder.tvTwo.setText(Const.formatAMTFloat(Float.parseFloat(movie.getV6())));
        holder.tvThree.setText(Const.RoundNumber(movie.getV7()));
        holder.tvFour.setText(Const.RoundNumber(movie.getV8()));
//        holder.tvFive.setText(Const.RoundNumber(movie.getV9()));
        holder.tvFive.setText(Const.formatAMTFloat(Float.parseFloat(movie.getV9())));
        holder.lnChild.setVisibility(movie.isShow() ? View.VISIBLE : View.GONE);
        holder.lnDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (movie.isShow())
                    movie.setShow(false);
                else movie.setShow(true);
                notifyItemChanged(position);
//                holder.lnChild.setVisibility(movie.isShow() ? View.VISIBLE : View.GONE);
                if (fmPie != null) {
                    fmPie.ScrollEndList(position);
                } else if (fmSnack != null) {
                    fmSnack.ScrollEndList(position);
                } else if (fmGum != null) {
                    fmGum.ScrollEndList(position);
                }
            }
        });
        holder.recycler_view.setLayoutManager(new LinearLayoutManager(context));
        holder.recycler_view.setItemAnimator(new DefaultItemAnimator());
        holder.recycler_view.setAdapter(new ResultPieAdapterChild(moviesList.get(position).getLIST()));
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }
}